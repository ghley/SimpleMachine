package dev.simplemachine.ecs;

import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.logging.Logger;

public class ECS {
    private int maskIndex = 0;
    private Map<Class<? extends Component>, BitSet> componentMaskMap = new HashMap<>();
    private Map<Class<? extends AbstractSystem>, AbstractSystem> systemMap = new HashMap<>();

    private Set<Entity> entities = new TreeSet<>(); // list, but remove is slow, secondary map?

    private long nextOpenId = 0L; // could start at long.min?
    private Deque<Long> openIds = new LinkedList<>();
    private List<AbstractSystem> systemOrder = new ArrayList<>();

    private List<Operation> operations = new ArrayList<>();

    public void updateAll() {
        operations.forEach(Operation::process);
        operations.clear();
        systemOrder.forEach(AbstractSystem::updateSystem);
    }

    public Entity createEntity() {
        if (openIds.isEmpty()) {
            for (int q = 0; q < 64; q++) {
                openIds.add(nextOpenId++);
            }
        }
        var entity = new Entity(openIds.pollFirst());
        entity.alive = false;
        operations.add(new EntityOperation(this, entity, true));
        return entity;
    }

    public void destroy(Entity entity) {
        entity.alive = false;
        operations.add(new EntityOperation(this, entity, false));
    }

    public void addEntity(Entity entity) {
        entity.alive = true;
        entities.add(entity);
        postMaskUpdate(entity);
    }

    public void removeEntity(Entity entity) {
        entities.remove(entity);
        systemMap.values().forEach(s->s.remove(entity));
    }

    void postMaskUpdate(Entity entity) {
        // could be more efficient
        BitSet set = new BitSet();
        for (var system : systemMap.values()) {
            system.remove(entity);
            set.clear();
            set.or(system.componentMask);
            set.and(entity.mask);
            if (set.equals(system.componentMask)) {
                system.add(entity);
            }
        }
    }

    void registerComponent(Class<? extends Component> component) {
        if (!componentMaskMap.containsKey(component)) {
            var bitSet = new BitSet();
            bitSet.set(maskIndex++);
            componentMaskMap.put(component, bitSet);
            Logger.getAnonymousLogger().info(String.format("Component: %s has mask %s", component.getName(), bitSet));
        }
    }

    public void addComponent(Entity entity, Class<? extends Component>... classes) {
        try {
            for (var clazz : classes) {
                var comp = clazz.getConstructor().newInstance();
                entity.addComponent(comp);
                operations.add(new ModifyMaskOperation(this, entity, componentMaskMap.get(clazz), true));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void removeComponent(Entity entity, Class<? extends Component>... classes) {
        for (var clazz : classes) {
            entity.removeComponent(clazz);
            operations.add(new ModifyMaskOperation(this, entity, componentMaskMap.get(clazz), false));
        }
    }

    public void registerSystem(Class<? extends AbstractSystem> system) {
        if (!systemMap.containsKey(system)) {
            AbstractSystem newInstance = null;
            try {
                newInstance = system.getConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException |
                     InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            Arrays.stream(newInstance.requiredComponents())
                    .forEach(this::registerComponent);

            BitSet systemMask = new BitSet();
            Arrays.stream(newInstance.requiredComponents())
                    .map(componentMaskMap::get)
                    .forEach(systemMask::or);
            newInstance.componentMask.or(systemMask);

            systemMap.put(system, newInstance);
            systemOrder.add(newInstance);

            Logger.getAnonymousLogger().info(String.format("System %s has mask %s", system.getName(), newInstance.componentMask));
        }
    }

}
