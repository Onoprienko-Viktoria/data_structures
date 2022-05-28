package com.onoprienko.datastructures.list.implementation;

import com.onoprienko.datastructures.list.AbstractListTest;
import com.onoprienko.datastructures.list.List;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayListTest extends AbstractListTest {
    @Override
    protected List getList() {
        return new ArrayList();
    }

    @Test
    void addMoreElementsToListThanItsCapacityWillIncreaseItsCapacity() {
        //GIVEN
        List list = new ArrayList(2);

        //WHEN
        for (int i = 0; i < 30; i++) {
            list.add(i);
        }
        //THEN
        assertEquals(30, list.size());
    }
}
