package com.onoprienko.datastructures.list;

public class LinkedListTest extends AbstractListTest {
    @Override
    protected List<Integer> getList() {
        return new LinkedList<>();
    }
}