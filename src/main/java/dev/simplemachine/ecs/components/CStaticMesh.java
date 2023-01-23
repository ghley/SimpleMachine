package dev.simplemachine.ecs.components;

import dev.simplemachine.ecs.Component;
import dev.simplemachine.model.StaticMesh;

public class CStaticMesh extends Component {
    private StaticMesh mesh;

    private boolean visible = true;

    public void setMesh(StaticMesh mesh) {
        this.mesh = mesh;
    }

    public StaticMesh getMesh() {
        return mesh;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }
}
