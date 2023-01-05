package dev.simplemachine.ecs.util;

import dev.simplemachine.ecs.ECS;
import dev.simplemachine.ecs.components.CTransform;
import dev.simplemachine.ecs.components.CTreeNode;
import dev.simplemachine.ecs.systems.SRenderer;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.junit.jupiter.api.Test;

class HierarchyTest {

    @Test
    public void test() {
        ECS ecs = new ECS();
        ecs.registerSystem(SRenderer.class);

        var entity1 = ecs.createEntity();
        var entity2 = ecs.createEntity();

        ecs.addComponent(entity1, CTransform.class, CTreeNode.class);
        ecs.addComponent(entity2, CTransform.class, CTreeNode.class);

        Hierarchy.set(entity1, entity2);


        entity1.getComponent(CTransform.class).setRotation(new Quaternionf().rotationAxis((float)Math.PI, 1, 0, 0));
        entity1.getComponent(CTransform.class).setTranslation(new Vector3f(10, 0, 0));
        entity2.getComponent(CTransform.class).setTranslation(new Vector3f(0, -5, 0));


        var translation = new Vector3f();
        Hierarchy.getModelTransform(entity1).getTranslation(translation);

        System.out.println(translation);

        Hierarchy.getModelTransform(entity2).getTranslation(translation);

        System.out.println(translation);


    }

}