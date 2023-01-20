package dev.simplemachine.ecs.systems;

import dev.simplemachine.ecs.AbstractSystem;
import dev.simplemachine.ecs.Component;
import dev.simplemachine.ecs.Entity;
import dev.simplemachine.ecs.components.CCamera;
import dev.simplemachine.opengl.GlobalVariables;
import dev.simplemachine.util.UnmodifiableIterator;
import org.joml.Matrix4f;
import org.joml.Vector3f;

public class SUpdateCameraBuffer extends AbstractSystem {
    @Override
    protected Class<? extends Component>[] requiredComponents() {
        return new Class[] {
                CCamera.class
        };
    }

    @Override
    public void update(UnmodifiableIterator<Entity> iterator) {
        Matrix4f proj = new Matrix4f().identity();
        Matrix4f view = new Matrix4f().identity();
        while (iterator.hasNext()) {
            var entity = iterator.next();
            var camera = entity.getComponent(CCamera.class);
            if (camera.isActive()) {
                proj.set(camera.getProjection());
                view.set(camera.getView());
                break;
            }
        }
        var buffer = GlobalVariables.getInstance().getCameraBuffer();
        buffer.setData(0, proj.get(new float[16]));
        buffer.setData(16*4, view.get(new float[16]));
    }
}
