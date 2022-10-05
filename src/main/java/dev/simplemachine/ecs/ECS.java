package dev.simplemachine.ecs;

import org.lwjgl.system.CallbackI;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class ECS {
    private int maskIndex = 0;
    private Map<Class<? extends Component>, BitSet> componentMaskMap = new HashMap<>();
    private Map<Class<? extends AbstractSystem>, AbstractSystem> systemMap = new HashMap<>();

    private List<AbstractSystem> systemOrder = new ArrayList<>();

    public void updateAll() {
        systemOrder.forEach(AbstractSystem::update);
    }

    void registerComponent(Class<? extends Component> component) {
        if (!componentMaskMap.containsKey(component)) {
            var bitSet = new BitSet();
            bitSet.set(maskIndex++);
            componentMaskMap.put(component, bitSet);
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
