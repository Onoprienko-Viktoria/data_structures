package com.onoprienko.datastructures.list;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class LinkedList<T> extends AbstractList<T> {
    private Node<T> head;
    private Node<T> tail;


    @Override
    public void add(T value, int index) {
        validateIndexForMethodAdd(index);

        Node<T> newNode = new Node<>(value);
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
        return remove(getNode(index));
    }

    private T remove(Node<T> nodeToRemove) {
        Node<T> nextNode = nodeToRemove.next;
        Node<T> prevNode = nodeToRemove.prev;


        if (size == 1) {
            head = tail = null;
        } else if (nodeToRemove == head) {
            head = nextNode;
        } else if (nodeToRemove == tail) {
            tail = prevNode;
            prevNode.next = null;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
        return nodeToRemove.value;
    }

    private Node<T> getNode(int index) {
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
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    public class LinkedListIterator implements Iterator<T> {
        private Node<T> current = head;
        private boolean canRemove;

        @Override
        public boolean hasNext() {
            return (current != null);
        }

        @Override
        public T next() {
            if (current == null) {
                throw new NoSuchElementException("No next value to iterate");
            }
            T value = current.value;
            current = current.next;
            canRemove = true;
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
                LinkedList.this.remove(current);
            }
            canRemove = false;
        }
    }

    private static class Node<T> {
        private Node<T> next;
        private Node<T> prev;
        private T value;

        Node(T value) {
            this.value = value;
        }
    }
}
