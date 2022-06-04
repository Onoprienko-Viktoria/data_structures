package com.onoprienko.datastructures.list;

import java.util.Iterator;
import java.util.Objects;

public class ArrayList<T> extends AbstractList<T> {
    private static final int DEFAULT_INITIAL_CAPACITY = 10;
    private T[] array;

    public ArrayList() {
        this(DEFAULT_INITIAL_CAPACITY);
    }


    @SuppressWarnings("unchecked")
    public ArrayList(int initialCapacity) {
        array = (T[]) new Object[initialCapacity];
    }

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndexForMethodAdd(index);
        ensureCapacity();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = value;
        size++;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);
        T deletedValue = array[index];
        System.arraycopy(array, index + 1, array, index, size - index);
        array[size - 1] = null;
        size--;
        return deletedValue;

    }

    @Override
    public T get(int index) {
        validateIndex(index);
        return array[index];
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);
        T oldValue = array[index];
        array[index] = value;
        return oldValue;
    }

    @Override
    public void clear() {
        for (int i = 0; i < size(); i++) {
            array[i] = null;
        }
        size = 0;
    }

    @Override
    public int indexOf(Object value) {
        for (int i = 0; i < size; i++) {
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


    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        if (array.length == size) {
            T[] newArray = (T[]) new Object[(array.length * 3 / 2 + 1)];
            System.arraycopy(array, 0, newArray, 0, array.length);
            array = newArray;
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new ArrayListIterator();
    }

    private class ArrayListIterator implements Iterator<T> {
        private int index = 0;
        private boolean canRemove;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public T next() {
            T value = array[index];
            index++;
            canRemove = true;
            return value;
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("Value already removed!");

            }

            ArrayList.this.remove(index - 1);
            canRemove = false;
            index--;
        }
    }
}
