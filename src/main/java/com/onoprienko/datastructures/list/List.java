package com.onoprienko.datastructures.list;

import java.util.Iterator;

public interface List<T> extends Iterable{
    void add(T value);

    void add(T value, int index);

    T remove(int index);

    T get(int index);

    T set(T value, int index);

    void clear();

    int size();

    boolean isEmpty();

    boolean contains(T value);

    int indexOf(T value);

    int lastIndexOf(T value);

    String toString();

    @Override
    Iterator iterator();

    default void validateIndex(int index) {
        int size = this.size();
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
    }

    default void validateIndexForMethodAdd(int index, int size) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
    }
}
