package dev.simplemachine.ecs.systems;

import dev.simplemachine.ecs.AbstractSystem;
import dev.simplemachine.ecs.Component;
import dev.simplemachine.ecs.Entity;
import dev.simplemachine.ecs.components.CStaticMesh;
import dev.simplemachine.ecs.components.CTransform;
import dev.simplemachine.opengl.ProgramDatabase;
import dev.simplemachine.util.UnmodifiableIterator;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

public class SStaticMeshRenderer extends AbstractSystem {
    @Override
    protected Class<? extends Component>[] requiredComponents() {
        return new Class[] {
                CStaticMesh.class, CTransform.class
        };
    }

    @Override
    public void update(UnmodifiableIterator<Entity> iterator) {
        var program = ProgramDatabase.getOrCreateStaticMeshProgram();
        program.use();
        while (iterator.hasNext()) {
            var entity = iterator.next();
            var transform = entity.getComponent(CTransform.class).getTransform();
            var meshComp = entity.getComponent(CStaticMesh.class);
            if (!meshComp.isVisible()) {
                continue;
            }
            var mesh = meshComp.getMesh();

            program.setUniform("model", transform);
            program.setUniform("modelTI", new Matrix3f(new Matrix4f().identity().set(transform).invert().transpose()));

            mesh.getVao().drawElements();
        }
    }
}
