package dev.simplemachine.ecs;

import dev.simplemachine.util.UnmodifiableIterator;

public class MotionSystem extends AbstractSystem {

    public MotionSystem() {

    }

    @Override
    protected Class<? extends Component>[] requiredComponents() {
        return new Class[]{
                Position.class, Speed.class
        };
    }

    @Override
    public void update(UnmodifiableIterator<Entity> iterator) {
        while (iterator.hasNext()) {
            var entity = iterator.next();
            entity.getComponent(Position.class).x += entity.getComponent(Speed.class).v * 0.1f;
        }
    }
}
