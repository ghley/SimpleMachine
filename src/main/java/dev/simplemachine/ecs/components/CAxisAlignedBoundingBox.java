package dev.simplemachine.ecs.components;

import dev.simplemachine.ecs.Component;
import dev.simplemachine.physics.AxisAlignedBoundingBox;

public class CAxisAlignedBoundingBox extends Component {
    private AxisAlignedBoundingBox aabb;

    public AxisAlignedBoundingBox getAabb() {
        return aabb;
    }

    public void setAabb(AxisAlignedBoundingBox aabb) {
        this.aabb = aabb;
    }
}
