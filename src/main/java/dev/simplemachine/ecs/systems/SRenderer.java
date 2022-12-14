package dev.simplemachine.ecs.systems;

import dev.simplemachine.ecs.AbstractSystem;
import dev.simplemachine.ecs.Component;
import dev.simplemachine.ecs.Entity;
import dev.simplemachine.ecs.components.CDrawable;
import dev.simplemachine.ecs.components.CTransform;
import dev.simplemachine.ecs.components.CTreeNode;
import dev.simplemachine.util.UnmodifiableIterator;

public class SRenderer extends AbstractSystem {

    @Override
    protected Class<? extends Component>[] requiredComponents() {
        return new Class[] {
                CDrawable.class, CTransform.class, CTreeNode.class
        };
    }

    @Override
    public void update(UnmodifiableIterator<Entity> iterator) {
        while (iterator.hasNext()) {
            var entity = iterator.next();

        }
    }
}
