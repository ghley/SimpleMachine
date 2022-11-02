package dev.simplemachine.ecs;

import dev.simplemachine.util.UnmodifiableIterator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ECSTest {

    @Test
    public void testESC() {
        ECS ecs = new ECS();
        ecs.registerSystem(MotionSystem.class);

        var mobile = ecs.createEntity();
        ecs.addComponent(mobile, Position.class, Speed.class);

        var immobile = ecs.createEntity();
        ecs.addComponent(immobile, Position.class);
        immobile.getComponent(Position.class).x = 3;

        mobile.getComponent(Speed.class).v = 1;

        assertEquals(0f, mobile.getComponent(Position.class).x);
        assertEquals(3, immobile.getComponent(Position.class).x);
        ecs.updateAll();
        ecs.removeComponent(mobile, Speed.class);
        ecs.updateAll();
        ecs.addComponent(mobile, Speed.class);
        mobile.getComponent(Speed.class).v = 1;
        ecs.updateAll();
        assertEquals(0.2f, mobile.getComponent(Position.class).x);
        assertEquals(3, immobile.getComponent(Position.class).x);
    }

}

