package dev.simplemachine.ecs;

public abstract class Component {
    private Entity owner;

    void setOwner(Entity owner) {
        this.owner = owner;
    }

    Entity getOwner() {
        return owner;
    }
}
