package com.onoprienko.datastructures.list.implementation;

import com.onoprienko.datastructures.list.AbstractListTest;
import com.onoprienko.datastructures.list.List;

public class LinkedListTest extends AbstractListTest {
    @Override
    protected List getList() {
        return new LinkedList();
    }
}