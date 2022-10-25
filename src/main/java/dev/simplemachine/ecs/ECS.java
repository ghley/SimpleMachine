package dev.simplemachine.ecs;

import org.lwjgl.system.CallbackI;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ECS {
    private int maskIndex = 0;
    private Map<Class<? extends Component>, BitSet> componentMaskMap = new HashMap<>();
    private Map<Class<? extends AbstractSystem>, AbstractSystem> systemMap = new HashMap<>();

    private Set<Entity> entities = new HashSet<>();

    private long currNextId = 0L; // could start at -Long max?
    private Deque<Long> openIds = new LinkedList<>();

    private List<AbstractSystem> systemOrder = new ArrayList<>();

    private Set<Entity> toBeDestroyed = new HashSet<>();
    private Set<Entity> toBeAdded = new HashSet<>();

    public void updateAll() {
        toBeDestroyed.forEach(this::remove);
        toBeAdded.forEach(this::insert);
        systemOrder.forEach(AbstractSystem::update);
    }

    public Entity createEntity() {
        long nextId = currNextId;
        if (!openIds.isEmpty()) {
            nextId = openIds.pop();
        }else {
            currNextId++;
        }
        var entity = new Entity(this, nextId);
        toBeAdded.add(entity);
        return entity;
    }

    void destroy(Entity entity) {
        toBeDestroyed.add(entity);
    }

    void insert(Entity entity) {

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

    public void addComponent(Entity entity, Class<? extends Component> clazz) {
        try {
            var component = clazz.getConstructor().newInstance();
            entity.mask.or(componentMaskMap.get(clazz));
            entity.addComponent(component);
        }catch (Exception e) {
            e.printStackTrace();
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

            systemMap.put(system, newInstance);
            systemOrder.add(newInstance);
        }
    }
}
