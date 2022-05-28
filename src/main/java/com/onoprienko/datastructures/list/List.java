package com.onoprienko.datastructures.list;

public interface List {
    void add(Object value);

    void add(Object value, int index);

    Object remove(int index);

    Object get(int index);

    Object set(Object value, int index);

    void clear();

    int size();

    boolean isEmpty();

    boolean contains(Object value);

    int indexOf(Object value);

    int lastIndexOf(Object value);

    String toString();


    default void validateIndex(int index, int size) {
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
