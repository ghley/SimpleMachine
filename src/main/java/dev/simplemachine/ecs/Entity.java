package dev.simplemachine.ecs;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public class Entity {
    public final ECS ecs;
    public final long id;
    final BitSet mask = new BitSet();
    private final Map<Class<? extends Component>, Component> components = new HashMap<>();
    private boolean alive = false;

    Entity(ECS ecs, long id) {
        this.ecs = ecs;
        this.id = id;
    }

    void addComponent(Component component) {
        components.put(component.getClass(), component);
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        return (T) components.get(clazz);
    }

    public void destroy() {
        ecs.destroy(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Entity entity = (Entity) o;
        return id == entity.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }

    void setAlive(boolean alive) {
        this.alive = alive;
    }

    public boolean isAlive() {
        return alive;
    }
}
