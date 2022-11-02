package dev.simplemachine.ecs.components;

import dev.simplemachine.ecs.Component;
import dev.simplemachine.ecs.Entity;

import java.util.ArrayList;
import java.util.List;

public class CTreeNode implements Component {
    private Entity parent = null;
    private List<Entity> children = new ArrayList<>();

    public Entity getParent() {
        return parent;
    }

    public void setParent(Entity parent) {
        this.parent = parent;
    }

    public List<Entity> getChildren() {
        return children;
    }
}
