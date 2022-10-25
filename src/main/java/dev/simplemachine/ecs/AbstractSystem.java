package dev.simplemachine.ecs;

import java.util.BitSet;

public abstract class AbstractSystem {
    final BitSet componentMask = new BitSet();


    protected abstract Class<? extends Component>[] requiredComponents();

    protected abstract void update();

    public BitSet getComponentMask() {
        return componentMask;
    }

    void remove(Entity entity) {

    }
}
