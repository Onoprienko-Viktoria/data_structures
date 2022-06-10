package com.onoprienko.datastructures.list;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ArrayListTest extends AbstractListTest {
    @Override
    protected List<Integer> getList() {
        return new ArrayList<>();
    }

    @Test
    void addMoreElementsToListThanItsCapacityWillIncreaseItsCapacity() {
        //GIVEN
        List<Integer> list = new ArrayList<>(2);

        //WHEN
        for (int i = 0; i < 30; i++) {
            list.add(i);
        }
        //THEN
        assertEquals(30, list.size());
    }

    @Test
    void listWithInitialCapacityOneGrow() {
        //GIVEN
        List<Integer> list = new ArrayList<>(1);

        //WHEN
        for (int i = 0; i < 30; i++) {
            list.add(i);
        }
        //THEN
        assertEquals(30, list.size());
    }
}
