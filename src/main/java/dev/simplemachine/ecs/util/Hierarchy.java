package dev.simplemachine.ecs.util;

import dev.simplemachine.ecs.Entity;
import dev.simplemachine.ecs.components.CTransform;
import dev.simplemachine.ecs.components.CTreeNode;
import org.joml.Matrix4f;

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

    public static Matrix4f getModelTransform(Entity entity) {
        var parent = entity.getComponent(CTreeNode.class).getParent();
        if (parent != null) {
            return new Matrix4f(getModelTransform(parent)).mul(entity.getComponent(CTransform.class).getTransform());
        }else {
            return entity.getComponent(CTransform.class).getTransform();
        }

    }
}
