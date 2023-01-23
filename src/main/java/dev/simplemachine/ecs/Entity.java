package dev.simplemachine.ecs;

import java.util.BitSet;
import java.util.HashMap;
import java.util.Map;

public final class Entity implements Comparable<Entity> {
    public final long id;
    final BitSet mask = new BitSet();
    private final Map<Class<? extends Component>, Component> components = new HashMap<>();
    boolean alive = false;

    private Entity entity;

    private Entity[] children;

    Entity(long id) {
        this.id = id;
    }

    void addComponent(Component component) {
        components.put(component.getClass(), component);
        component.setOwner(this);
    }

    void removeComponent(Class<? extends Component> clazz) {
        components.remove(clazz).setOwner(null);
    }

    public boolean hasComponent(Class<? extends Component> clazz) {
        return components.containsKey(clazz);
    }

    public <T extends Component> T getComponent(Class<T> clazz) {
        return (T) components.get(clazz);
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

    public boolean isAlive() {
        return alive;
    }

    @Override
    public int compareTo(Entity o) {
        return Long.compare(id, o.id);
    }

    public void setParent(Entity entity) {
        this.entity = entity;
    }
}
