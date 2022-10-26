package dev.simplemachine.util;

import java.util.Iterator;
import java.util.function.Consumer;

public class UnmodifiableIterator<E> implements Iterator<E> {
    private Iterator<? extends E> iterator;
    public UnmodifiableIterator(Iterator<E> iterator) {
        this.iterator = iterator;
    }

    @Override
    public boolean hasNext() {
        return iterator.hasNext();
    }

    @Override
    public E next() {
        return iterator.next();
    }

    @Override
    public void remove() {
        throw new RuntimeException("Don't!");
    }

    @Override
    public void forEachRemaining(Consumer<? super E> action) {
        iterator.forEachRemaining(action);
    }
}
