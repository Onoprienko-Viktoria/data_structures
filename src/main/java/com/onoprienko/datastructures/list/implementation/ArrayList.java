package com.onoprienko.datastructures.list.implementation;

import com.onoprienko.datastructures.list.List;

import java.util.Objects;
import java.util.StringJoiner;

public class ArrayList implements List {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private int size;
    private Object[] array;

    public ArrayList() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    public ArrayList(int initialCapacity) {
        array = new Object[initialCapacity];
    }

    @Override
    public void add(Object value) {
        add(value, size);
    }

    @Override
    public void add(Object value, int index) {
        validateIndexForMethodAdd(index, size);
        ensureCapacity();
        Object[] cloneArray = new Object[array.length];
        System.arraycopy(array, 0, cloneArray, 0, cloneArray.length);
        array[index] = value;
        if (array.length - (index + 1) >= 0)
            System.arraycopy(cloneArray, index + 1 - 1, array, index + 1, array.length - (index + 1));
        size++;
    }

    @Override
    public Object remove(int index) {
        validateIndex(index, size);
        Object deletedValue = array[index];
        int listEnd = size == 0 ? 0 : size - 1;
        if (listEnd - index >= 0) System.arraycopy(array, index + 1, array, index, listEnd - index);
        size--;
        return deletedValue;

    }

    @Override
    public Object get(int index) {
        validateIndex(index, size);
        return array[index];
    }

    @Override
    public Object set(Object value, int index) {
        validateIndex(index, size);
        Object oldValue = array[index];
        array[index] = value;
        return oldValue;
    }

    @Override
    public void clear() {
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public boolean contains(Object value) {
        return indexOf(value) != -1;
    }


    @Override
    public int indexOf(Object value) {
        for (int i = 0; i < size - 1; i++) {
            if (Objects.equals(value, array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(value, array[i])) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            result.add(String.valueOf(array[i]));
        }
        return result.toString();
    }

    private void ensureCapacity() {
        if (array.length == size) {
            Object[] newArray = new Object[(int) ((array.length + 1) * 1.5)];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
    }
}
