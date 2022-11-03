package dev.simplemachine.ecs;

import dev.simplemachine.util.UnmodifiableIterator;

import java.util.BitSet;
import java.util.TreeSet;

public abstract class AbstractSystem {
    final BitSet componentMask = new BitSet();
    protected abstract Class<? extends Component>[] requiredComponents();

    private final TreeSet<Entity> entities = new TreeSet<>();

    protected void updateSystem() {
        update(new UnmodifiableIterator(entities.iterator()));
    }

    public abstract void update(UnmodifiableIterator<Entity> iterator);

    protected void remove(Entity entity) {
        entities.remove(entity);
    }

    protected void add(Entity entity) {
        entities.add(entity);
    }
}
