package com.onoprienko.datastructures.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public abstract class AbstractListTest {
    private List list;

    @BeforeEach
    public void before() {
        list = getList();
    }

    protected abstract List getList();

    @DisplayName("test add null into list dont throw exception")
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


    @DisplayName("test add with index bigger size returns exception")
    @Test
    void tryToAddWithIndexBiggerSizeReturnsException() {
        //WHEN
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("test", 3);
        });

        //THEN
        assertEquals("Index 3 out of bounds for length 0", exception.getMessage());
    }

    @DisplayName("test add with index less than zero returns exception")
    @Test
    void tryToAddWithIndexLessThanZeroReturnsException() {
        //GIVEN
        list.add("A");
        //WHEN
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.add("test", -2);
        });

        //THEN
        assertEquals("Index -2 out of bounds for length 1", exception.getMessage());
    }

    @DisplayName("test add in middle and start of list work correct")
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


    @DisplayName("test remove all elements returns empty list")
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


    @DisplayName("test get element after clear returns exception")
    @Test
    void getElementAfterClearThrowException() {
        //GIVEN
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");
        list.add("A");

        //WHEN
        list.clear();
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(0);
        });

        //THEN
        assertEquals("Index 0 out of bounds for length 0", exception.getMessage());
    }

    @DisplayName("test remove from the beginning of the list returns deleted value and delete it")
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

    @DisplayName("test remove from the middle of the list returns deleted value and delete it")
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


    @DisplayName("test remove from the list with size '1' returns deleted value and delete it")
    @Test
    void removeFromListWithSizeZeroClearListAndReturnDeletedValue() {
        //GIVEN
        list.add("A");

        //WHEN
        Object removed = list.remove(0);

        //THEN
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        assertEquals("A", removed);
    }

    @DisplayName("test remove from the ending of the list returns deleted value and delete it")
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

    @DisplayName("test remove with index bigger than size returns exception")
    @Test
    void tryToRemoveWithIndexBiggerThanSizeReturnsException() {
        //GIVEN
        list.add(1);

        //WHEN
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(10);
        });

        //THEN
        assertEquals("Index 10 out of bounds for length 1", exception.getMessage());
    }

    @DisplayName("test remove with index less than zero returns exception")
    @Test
    void tryToRemoveWithIndexLessThanZeroReturnsException() {
        //GIVEN
        list.add(1);

        //WHEN
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.remove(-1);
        });

        //THEN
        assertEquals("Index -1 out of bounds for length 1", exception.getMessage());
    }


    @DisplayName("test get with index bigger than size returns exception")
    @Test
    void tryToGetWithIndexBiggerThanSizeReturnsException() {
        //GIVEN
        list.add(1);

        //WHEN
        Exception exception = assertThrows(IndexOutOfBoundsException.class, () -> {
            list.get(10);
        });

        //THEN
        assertEquals("Index 10 out of bounds for length 1", exception.getMessage());
    }

    @DisplayName("test set work correct and set data into list")
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

    @DisplayName("test set with 'null' value dont throw Exception")
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

    @DisplayName("test set with index bigger than size throw Exception")
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

    @DisplayName("test clean list is empty and has size zero")
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

    @DisplayName("test get size work correct")
    @Test
    void getSizeReturnCorrectSize() {
        //WHEN
        list.add(1);
        list.add(2);
        list.add(3);

        //THEN
        assertEquals(3, list.size());

    }

    @DisplayName("test get size of empty list returns zero")
    @Test
    void getSizeOfEmptyListReturnZero() {
        //WHEN
        List list = new ArrayList();

        //THEN
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());

    }

    @DisplayName("test list with 'null' values not empty and returns correct size")
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

    @DisplayName("test is empty returns false on list with data")
    @Test
    void isEmptyReturnFalseOnListWithData() {
        //WHEN
        list.add("O");

        //THEN
        assertFalse(list.isEmpty());
    }

    @DisplayName("test contain 'null' value returns true")
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


    @DisplayName("test contain return true if list has object")
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

    @DisplayName("test contain return false if list has not object")
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

    @DisplayName("test index of 'null' return index")
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

    @DisplayName("test index of 'null' return first index of object among duplicates")
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


    @DisplayName("test index of returns '-1'")
    @Test
    public void indexOfReturnsMinusOne() {
        //WHEN
        list.add("A");
        list.add("B");
        list.add("C");

        //THEN
        assertEquals(-1, list.indexOf("T"));
    }


    @DisplayName("test last index of 'null' return index")
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
    @DisplayName("test last index of 'null' return first index of object among duplicates")
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

    @DisplayName("test last index of returns '-1'")
    @Test
    public void LastIndexOfReturnsMinusOne() {
        //WHEN
        list.add("A");
        list.add("B");
        list.add("C");

        //THEN
        assertEquals(-1, list.lastIndexOf("T"));
    }


    @DisplayName("test Iterator hasNext return True and next return value")
    @Test
    public void iteratorReturnCorrectData() {
        //GIVEN
        list.add("A");
        list.add("B");
        list.add("C");
        Iterator<Object> iterator = list.iterator();

        //THEN
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next());
        assertTrue(iterator.hasNext());
        assertEquals("C", iterator.next());
        assertEquals(3, list.size());

    }

    @DisplayName("test iterator remove all values will clear list")
    @Test
    public void iteratorRemoveAllValuesWillClearList() {
        //GIVEN
        list.add("A");
        list.add("B");
        list.add("C");
        Iterator<Object> iterator = list.iterator();

        //WHEN
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        iterator.remove();
        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next());
        iterator.remove();
        assertTrue(iterator.hasNext());
        assertEquals("C", iterator.next());
        iterator.remove();

        //THEN
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
    }

    @DisplayName("test iterator remove one value twice will throw exception")
    @Test
    public void iteratorRemoveOneValueTwiceWillThrowException() {
        //GIVEN
        list.add("A");
        Iterator<Object> iterator = list.iterator();

        //WHEN
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next());
        iterator.remove();
        Exception exception = assertThrows(IllegalStateException.class, iterator::remove);

        //THEN
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        assertEquals("Value already removed!", exception.getMessage());
    }

    @DisplayName("test iterator remove from empty list throws exception")
    @Test
    public void iteratorRemoveFromEmptyListWillThrowException() {
        //GIVEN
        Iterator<Object> iterator = list.iterator();

        //WHEN
        assertFalse(iterator.hasNext());
        Exception exception = assertThrows(IllegalStateException.class, iterator::remove);

        //THEN
        assertEquals(0, list.size());
        assertTrue(list.isEmpty());
        assertEquals("Value already removed!", exception.getMessage());
    }


    @DisplayName("test list toString")
    @Test
    void testToStringReturnCorrectStringWithNull() {
        //WHEN
        list.add("A");
        list.add(null);
        list.add("B");
        list.add("C");

        //THEN
        assertEquals("[A, null, B, C]", list.toString());
    }
}