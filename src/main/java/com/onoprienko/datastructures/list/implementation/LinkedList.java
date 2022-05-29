package com.onoprienko.datastructures.list.implementation;

import com.onoprienko.datastructures.list.AbstractList;

import java.util.Iterator;
import java.util.Objects;

public class LinkedList<T> extends AbstractList<T> {
    private Node<T> head;
    private Node<T> tail;

    @Override
    public void add(T value) {
        add(value, size);
    }

    @Override
    public void add(T value, int index) {
        validateIndexForMethodAdd(index, size);

        Node<T> newNode = new Node(value);
        if (size == 0) {
            head = tail = newNode;
        } else if (index == size) {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        } else if (index == 0) {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        } else {
            Node<T> current = getNode(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
        }
        size++;
    }

    @Override
    public T remove(int index) {
        validateIndex(index);

        Node<T> removedValue = getNode(index);
        Node<T> nextNode = removedValue.next;
        Node<T> prevNode = removedValue.prev;


        if (size == 1) {
            head = tail = null;
        } else if (index == 0) {
            head = nextNode;
        } else if (index == size - 1) {
            tail = prevNode;
            prevNode.next = null;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
        return removedValue.value;
    }

    private Node<T> getNode(int index) {
        validateIndex(index);

        if (index <= size / 2) {
            Node<T> currentFromHead = head;
            for (int i = 0; i < index; i++) {
                currentFromHead = currentFromHead.next;
            }
            return currentFromHead;
        } else {
            Node<T> currentFromTail = tail;
            for (int i = size - 1; i > index; i--) {
                currentFromTail = currentFromTail.prev;

            }
            return currentFromTail;
        }
    }

    @Override
    public T get(int index) {
        validateIndex(index);

        return getNode(index).value;
    }

    @Override
    public T set(T value, int index) {
        validateIndex(index);

        Node<T> newNode = getNode(index);
        T oldValue = newNode.value;
        newNode.value = value;
        return oldValue;
    }

    @Override
    public void clear() {
        head = tail = null;
        size = 0;
    }

    @Override
    public int indexOf(T value) {
        Node<T> current = head;
        for (int i = 0; i < size - 1; i++) {
            if (Objects.equals(current.value, value)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(T value) {
        Node<T> current = tail;
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(current.value, value)) {
                return i;
            }
            current = current.prev;
        }
        return -1;
    }


    @Override
    public Iterator iterator() {
        return new LinkedListIterator();
    }

    public class LinkedListIterator<T> implements Iterator<T> {
        private Node<T> current = (Node<T>) head;
        private int index = 0;
        private boolean canRemove;

        @Override
        public boolean hasNext() {
            return (index < size);
        }

        @Override
        public T next() {
            T value = current.value;
            current = current.next;
            canRemove = true;
            index++;
            return value;
        }

        @Override
        public void remove() {
            if (!canRemove) {
                throw new IllegalStateException("Value already removed!");
            }
            if (current == null) {
                tail = tail.prev;
                size--;
            } else {
                LinkedList.this.remove(index);
            }
            canRemove = false;
            index--;
        }
    }

    private static class Node<T> {
        Node<T> next;
        Node<T> prev;
        T value;

        public Node(T value) {
            this.value = value;
        }
    }
}
