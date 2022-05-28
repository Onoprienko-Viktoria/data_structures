package com.onoprienko.datastructures.list.implementation;

import com.onoprienko.datastructures.list.List;

import java.util.Objects;
import java.util.StringJoiner;

public class LinkedList implements List {
    private Node head;
    private Node tail;
    private int size;

    @Override
    public void add(Object value) {
        add(value, size);
    }

    @Override
    public void add(Object value, int index) {
        validateIndexForMethodAdd(index, size);

        Node newNode = new Node(value);
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
            Node current = getNode(index);
            newNode.next = current;
            newNode.prev = current.prev;
            current.prev.next = newNode;
        }
        size++;
    }

    @Override
    public Object remove(int index) {
        validateIndex(index, size);

        Node removedValue = getNode(index);
        Node nextNode = removedValue.next;
        Node prevNode = removedValue.prev;


        if (size == 1) {
            head = tail = null;
        } else if (index == 0) {
            head = nextNode;
        } else if (index == size - 1) {
            tail = tail.prev;
            prevNode.next = null;
        } else {
            prevNode.next = nextNode;
            nextNode.prev = prevNode;
        }
        size--;
        return removedValue.value;
    }

    private Node getNode(int index) {
        validateIndex(index, size);

        if (index <= size / 2) {
            Node currentFromHead = head;
            for (int i = 0; i < index; i++) {
                currentFromHead = currentFromHead.next;
            }
            return currentFromHead;
        } else {
            Node currentFromTail = tail;
            for (int i = size - 1; i > index; i--) {
                currentFromTail = currentFromTail.prev;

            }
            return currentFromTail;
        }
    }

    @Override
    public Object get(int index) {
        validateIndex(index, size);

        return getNode(index).value;
    }

    @Override
    public Object set(Object value, int index) {
        validateIndex(index, size);

        Node newNode = getNode(index);
        Object oldValue = newNode.value;
        newNode.value = value;
        return oldValue;
    }

    @Override
    public void clear() {
        head = tail = null;
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
        return indexOf(value) >= 0;
    }


    @Override
    public int indexOf(Object value) {
        Node current = head;
        for (int i = 0; i < size - 1; i++) {
            if (Objects.equals(current.value, value)) {
                return i;
            }
            current = current.next;
        }
        return -1;
    }

    @Override
    public int lastIndexOf(Object value) {
        Node current = tail;
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(current.value, value)) {
                return i;
            }
            current = current.prev;
        }
        return -1;
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(", ", "[", "]");
        for (int i = 0; i < size; i++) {
            result.add(String.valueOf(get(i)));
        }
        return result.toString();
    }

    private static class Node {
        Node next;
        Node prev;
        Object value;

        public Node(Object value) {
            this.value = value;
        }
    }
}
