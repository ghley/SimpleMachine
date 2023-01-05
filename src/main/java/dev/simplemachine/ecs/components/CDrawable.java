package dev.simplemachine.ecs.components;

import dev.simplemachine.ecs.Component;
import dev.simplemachine.model.Drawable;

public class CDrawable implements Component {
    private Drawable drawable;

    public void setDrawable(Drawable drawable) {
        this.drawable = drawable;
    }

    public Drawable getDrawable() {
        return drawable;
    }
}
