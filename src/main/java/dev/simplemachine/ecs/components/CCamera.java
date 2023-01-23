package dev.simplemachine.ecs.components;

import dev.simplemachine.ecs.Component;
import org.joml.Matrix4f;

public class CCamera extends Component {

    private Matrix4f projection;
    private Matrix4f view;
    private boolean active = true;

    public Matrix4f getProjection() {
        return projection;
    }

    public void setProjection(Matrix4f projection) {
        this.projection = projection;
    }

    public Matrix4f getView() {
        return view;
    }

    public void setView(Matrix4f view) {
        this.view = view;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isActive() {
        return active;
    }
}
