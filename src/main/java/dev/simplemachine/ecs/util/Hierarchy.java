package dev.simplemachine.ecs.util;

import dev.simplemachine.ecs.Entity;
import dev.simplemachine.ecs.components.CTreeNode;

public final class Hierarchy {
    private Hierarchy() {

    }

    public static void set(Entity parent, Entity child) {
        parent.getComponent(CTreeNode.class).getChildren().add(child);
        child.getComponent(CTreeNode.class).setParent(parent);
    }

    public static void prune(Entity child) {
        var treeNode = child.getComponent(CTreeNode.class);
        treeNode.getParent().getComponent(CTreeNode.class).getChildren().remove(child);
        treeNode.setParent(null);
    }

    public static void removeChildren(Entity parent) {
        parent.getComponent(CTreeNode.class).getChildren().forEach(Hierarchy::prune);
    }
}
