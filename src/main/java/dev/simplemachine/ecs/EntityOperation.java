package dev.simplemachine.ecs;

record EntityOperation(ECS ecs, Entity entity, boolean add) implements Operation {
    @Override
    public void process() {
        if (add) {
            ecs.addEntity(entity);
        }else {
            ecs.removeEntity(entity);
        }
    }

}
