package dev.simplemachine.ecs;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ECS {
    private int maskIndex = 0;
    private Map<Class<? extends Component>, BitSet> componentMaskMap = new HashMap<>();
    private Map<Class<? extends AbstractSystem>, AbstractSystem> systemMap = new HashMap<>();

    private Set<Entity> entities = new TreeSet<>();

    private long nextOpenId = 0L; // could start at -Long max?
    private Deque<Long> openIds = new LinkedList<>();
    private List<AbstractSystem> systemOrder = new ArrayList<>();
    private List<Entity> toBeDestroyed = new ArrayList<>();
    private List<Entity> toBeAdded = new ArrayList<>();

    public void updateAll() {
        toBeDestroyed.forEach(this::remove);
        toBeDestroyed.clear();
        toBeAdded.forEach(this::insert);
        toBeAdded.forEach(this::updateMask);
        toBeAdded.clear();
        systemOrder.forEach(AbstractSystem::updateSystem);
    }

    public Entity createEntity() {
        if (openIds.isEmpty()) {
            for (int q = 0; q < 64; q++) {
                openIds.add(nextOpenId++);
            }
        }
        var entity = new Entity(this, openIds.pollFirst());
        entity.alive = false;
        toBeAdded.add(entity);
        return entity;
    }

    void destroy(Entity entity) {
        toBeDestroyed.add(entity);
    }

    void insert(Entity entity) {
        entity.alive = true;
        entities.add(entity);
    }

    void updateMask(Entity entity) {
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

    void remove(Entity entity) {
        openIds.add(entity.id);
    }

    void registerComponent(Class<? extends Component> component) {
        if (!componentMaskMap.containsKey(component)) {
            var bitSet = new BitSet();
            bitSet.set(maskIndex++);
            componentMaskMap.put(component, bitSet);
        }
    }

    BitSet getComponentMask(Class<? extends Component> clazz) {
        return componentMaskMap.get(clazz);
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

            systemMap.put(system, newInstance);
            systemOrder.add(newInstance);
        }
    }
}
