package dev.simplemachine.ecs;

import java.util.BitSet;

record  ModifyMaskOperation(ECS ecs, Entity entity, BitSet mask, boolean add) implements Operation {
    @Override
    public void process() {
        if (add) {
            entity.mask.or(mask);
            ecs.postMaskUpdate(entity);
        }else {
            entity.mask.andNot(mask);
            ecs.postMaskUpdate(entity);
        }
    }
}
