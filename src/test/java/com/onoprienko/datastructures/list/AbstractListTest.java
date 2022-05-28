package com.onoprienko.datastructures.list;

import com.onoprienko.datastructures.list.implementation.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;



import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractListTest {
    private List list;

    @BeforeEach
    public void before() {
        list = getList();
    }

    protected abstract List getList();

    @Test
    void addNullWorkCorrectAndDontThrowExceptions() {
        //WHEN
        list.add(1);
        list.add(null);
        list.add(3);

        //THEN
        assertEquals(3, list.size());
        assertNull(list.get(1));
    }


    @Test
    void tryToAddToIndexOutOfBoundsReturnsException() {
        //WHEN
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add( "test", 3);
        });

        //THEN
        assertEquals("Index 3 out of bounds for length 0", exception.getMessage());
    }

    @Test
    void tryToAddToInStartAndMiddleOfListWorkCorrect() {
        //WHEN
        list.add(1);
        list.add(2);
        list.add(3);
        list.add(4, 0);
        list.add(5, 2);
        //list{4 1 5 2 3}

        //THEN
        assertEquals(4, list.get(0));
        assertEquals(1, list.get(1));
        assertEquals(5, list.get(2));
        assertEquals(2, list.get(3));
        assertEquals(3, list.get(4));
    }

    @Test
    void removeAllElementsReturnsEmptyList() {
        //GIVEN
        list.add(1);
        list.add(2);

        //WHEN
        list.remove(0);
        list.remove(0);
        //THEN
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void removeFromTheBeginningOfTheListShiftsTheListAndReturnDeletedValue() {
        //GIVEN
        list.add("A");
        list.add("B");
        list.add("C");

        //WHEN
        Object removed = list.remove(0);
        //THEN
        assertEquals(2, list.size());
        assertEquals("B", list.get(0));
        assertEquals("C", list.get(1));
        assertEquals("A", removed);
    }

    @Test
    void removeFromMiddleOfTheListShiftsTheListAndReturnDeletedValue() {
        //GIVEN
        list.add("A");
        list.add("B");
        list.add("C");
        list.add("D");

        //WHEN
        Object removed = list.remove(2);
        //THEN
        assertEquals(3, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("D", list.get(2));
        assertEquals("C", removed);
    }


    @Test
    void removeFromListWithSizeClearListAndReturnDeletedValue() {
        //GIVEN
        list.add("A");

        //WHEN
        Object removed = list.remove(0);

        //THEN
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        assertEquals("A", removed);
    }

    @Test
    void removeFromTheEndingOfTheListWorkCorrectAndReturnDeletedValue() {
        //GIVEN
        list.add("A");
        list.add("B");
        list.add("C");

        //WHEN
        Object removed = list.remove(2);
        //THEN
        assertEquals(2, list.size());
        assertEquals("A", list.get(0));
        assertEquals("B", list.get(1));
        assertEquals("C", removed);
    }

    @Test
    void tryToRemoveByIndexOutOfBoundsReturnsException() {
        //GIVEN
        list.add(1);

        //WHEN
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(10);
        });

        //THEN
        assertEquals("Index 10 out of bounds for length 1", exception.getMessage());
    }


    @Test
    void tryToGetByIndexOutOfBoundsReturnsException() {
        //GIVEN
        list.add(1);

        //WHEN
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(10);
        });

        //THEN
        assertEquals("Index 10 out of bounds for length 1", exception.getMessage());
    }

    @Test
    void setWorkCorrect() {
        //WHEN
        list.add(1);
        list.add(2);
        list.add(3);
        list.set(11, 0);
        list.set(12, 1);
        list.set(13, 2);

        //THEN
        assertEquals(3, list.size());
        assertEquals(11, list.get(0));
        assertEquals(12, list.get(1));
        assertEquals(13, list.get(2));
    }

    @Test
    void setWorkWithNullCorrect() {
        //WHEN
        list.add(1);
        list.add(2);
        list.add(3);
        list.set(null, 0);
        list.set(null, 1);
        list.set(null, 2);

        //THEN
        assertEquals(3, list.size());
        assertEquals(null, list.get(0));
        assertEquals(null, list.get(1));
        assertEquals(null, list.get(2));
    }

    @Test
    void tryToSetByIndexOutOfBoundsReturnsException() {
        //GIVEN
        list.add(1);

        //WHEN
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.set("h", 121);
        });

        //THEN
        assertEquals("Index 121 out of bounds for length 1", exception.getMessage());
    }

    @Test
    void listAfterClearHasSizeZeroAndIsEmpty() {
        //GIVEN
        list.add(1);
        list.add(2);
        list.add(3);

        //WHEN
        list.clear();

        //THEN
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @Test
    void getSizeReturnCorrectSize() {
        //WHEN
        list.add(1);
        list.add(2);
        list.add(3);

        //THEN
        assertEquals(3, list.size());

    }

    @Test
    void getSizeOfEmptyListReturnZero() {
        //WHEN
        List list = new ArrayList();

        //THEN
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

    }

    @Test
    void isEmptyOnListOfNullsReturnsFalse() {
        //WHEN
        list.add(null);
        list.add(null);
        list.add(null);

        //THEN
        assertEquals(3, list.size());
        assertFalse(list.isEmpty());
    }

    @Test
    public void testIsEmptyReturnFalseOnListWithData() {
        //WHEN
        list.add("O");

        //THEN
        assertFalse(list.isEmpty());
    }

    @Test
    void containsNullReturnTrue() {
        //WHEN
        list.add(1);
        list.add(null);
        list.add(3);

        //THEN
        assertEquals(3, list.size());
        assertTrue(list.contains(null));
    }


    @Test
    void containsReturnTrueIfListContainsObject() {
        //WHEN
        list.add("test");
        list.add(null);
        list.add(3);

        //THEN
        assertEquals(3, list.size());
        assertTrue(list.contains("test"));
    }

    @Test
    void containsReturnFalseIfListNotContainsObject() {
        //WHEN
        list.add("test");
        list.add(null);
        list.add(3);

        //THEN
        assertEquals(3, list.size());
        assertFalse(list.contains("t"));
    }

    @Test
    void indexOfWorkWithNull() {
        //WHEN
        list.add("test");
        list.add(null);
        list.add(3);

        //THEN
        assertEquals(3, list.size());
        assertEquals(1, list.indexOf(null));
    }

    @Test
    void indexOfReturnFirstIndexOfObject() {
        //WHEN
        list.add("test");
        list.add("test");
        list.add(3);
        list.add("test");

        //THEN
        assertEquals(4, list.size());
        assertEquals(0, list.indexOf("test"));
    }

    @Test
    public void indexOfAndIndexOfReturnsMinusOne() {
        //WHEN
        list.add("A");
        list.add("B");
        list.add("C");

        //THEN
        assertEquals(-1, list.indexOf("T"));
        assertEquals(-1, list.lastIndexOf("T"));
    }


    @Test
    void lastIndexOfWorkWithNull() {
        //WHEN
        list.add("test");
        list.add(null);
        list.add(3);

        //THEN
        assertEquals(3, list.size());
        assertEquals(1, list.lastIndexOf(null));
    }

    @Test
    void lastIndexOfReturnFirstIndexOfObject() {
        //WHEN
        list.add("test");
        list.add("test");
        list.add(3);
        list.add("test");

        //THEN
        assertEquals(4, list.size());
        assertEquals(3, list.lastIndexOf("test"));
    }

    @Test
    public void LastIndexOfAndIndexOfReturnsMinusOne() {
        //WHEN
        list.add("A");
        list.add("B");
        list.add("C");

        //THEN
        assertEquals(-1, list.lastIndexOf("T"));
    }


    @Test
    void testToString() {
        //WHEN
        list.add("A");
        list.add("B");
        list.add("C");

        //THEN
        assertEquals("[A, B, C]", list.toString());
    }
}