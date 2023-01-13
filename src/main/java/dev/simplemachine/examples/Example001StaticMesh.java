package dev.simplemachine.examples;

import dev.simplemachine.SimpleMachine;
import dev.simplemachine.ecs.components.CCamera;
import dev.simplemachine.ecs.components.CStaticMesh;
import dev.simplemachine.ecs.components.CTransform;
import dev.simplemachine.ecs.components.CTreeNode;
import dev.simplemachine.ecs.systems.SStaticMeshRenderer;
import dev.simplemachine.opengl.ProgramDatabase;
import dev.simplemachine.opengl.StaticMeshDatabase;
import org.joml.Vector3f;

public class Example001StaticMesh {

    static SimpleMachine machine;

    public static void main(String[] args) {
        machine = new SimpleMachine();
        machine.setInitCallback(Example001StaticMesh::init);
        machine.setLoopCallback(Example001StaticMesh::loop);
        machine.run();
    }

    static float t = 0.f;

    public static void init() {
        StaticMeshDatabase.load("full.glb");

        var ecs = machine.getEcs();
        ecs.registerSystem(SStaticMeshRenderer.class);

        var entity = ecs.createEntity();
        ecs.addComponent(entity, CTransform.class, CStaticMesh.class, CTreeNode.class);
        entity.getComponent(CStaticMesh.class).setMesh(StaticMeshDatabase.get("full.glb", "Wall"));

        var list = ecs.allEntitiesWith(CCamera.class);
        list.get(0).getComponent(CCamera.class).setPosition(new Vector3f(0, 3, -3));

        ProgramDatabase.getOrCreateStaticMeshProgram().setUniform("lightDir",new Vector3f((float)Math.sin(t), -1, (float)Math.cos(t)).normalize());
    }

    public static void loop() {
        t+= 0.1f;
        var lightDir = new Vector3f((float)Math.sin(t), -1, (float)Math.cos(t)).normalize();
        ProgramDatabase.getOrCreateStaticMeshProgram().setUniform("lightDir", lightDir);
    }

}
