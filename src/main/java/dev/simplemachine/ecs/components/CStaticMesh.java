package dev.simplemachine.ecs.components;

import dev.simplemachine.ecs.Component;
import dev.simplemachine.model.StaticMesh;

public class CStaticMesh implements Component {
    private StaticMesh mesh;

    public void setMesh(StaticMesh mesh) {
        this.mesh = mesh;
    }

    public StaticMesh getMesh() {
        return mesh;
    }
}
