package com.onoprienko.datastructures.list;

import java.util.StringJoiner;

public abstract class AbstractList<T> implements List<T> {
    protected int size;

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean contains(T value) {
        return indexOf(value) >= 0;
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(", ", "[", "]");
        for (T value : this) {
            result.add(String.valueOf(value));
        }
        return result.toString();

    }

    public void validateIndex(int index) {
        int size = this.size();
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
    }

    public void validateIndexForMethodAdd(int index) {
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for length " + size);
        }
    }
}
