package dev.simplemachine.ecs;

import java.util.BitSet;

public abstract class AbstractSystem {

    private BitSet componentMask;

    protected abstract Class<? extends Component>[] requiredComponents();

    protected abstract void update();

    public void setComponentMask(BitSet componentMask) {
        this.componentMask = componentMask;
    }

    public BitSet getComponentMask() {
        return componentMask;
    }
}
