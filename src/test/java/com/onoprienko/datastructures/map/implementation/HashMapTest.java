package com.onoprienko.datastructures.map.implementation;

import com.onoprienko.datastructures.map.HashMap;
import com.onoprienko.datastructures.map.Map.Entry;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class HashMapTest {

    HashMap<String, String> map = new HashMap<>();


    @DisplayName("test put and get values from map")
    @Test
    void putAndGetValuesFromMapWorkCorrect() {
        //WHEN
        map.put("A", "A");
        map.put("B", "B");
        map.put("C", "D");

        //THEN
        assertEquals(3, map.size());
        assertEquals("A", map.get("A"));
        assertEquals("B", map.get("B"));
        assertEquals("D", map.get("C"));
    }

    @DisplayName("test put value with same key will rewrite it")
    @Test
    void putValueWithSameKeyWillRewriteIt() {
        //WHEN
        map.put("A", "A");
        map.put("B", "B");
        map.put("A", "D");

        //THEN
        assertEquals(2, map.size());
        assertEquals("D", map.get("A"));
        assertEquals("B", map.get("B"));
    }

    @DisplayName("test get value with key that not exist will return null")
    @Test
    void getValueWithKeyThatNotInMapReturnsNull() {
        //WHEN
        map.put("A", "A");
        map.put("B", "B");

        //THEN
        assertEquals(2, map.size());
        assertEquals("A", map.get("A"));
        assertEquals("B", map.get("B"));
        assertNull(map.get("L"));
    }

    @DisplayName("test put and get null values from map work correct")
    @Test
    void putAndGetNullValuesWorkCorrect() {
        //WHEN
        map.put(null, "A");
        map.put("B", null);

        //THEN
        assertEquals(2, map.size());
        assertEquals("A", map.get(null));
        assertNull(map.get("B"));
    }

    @DisplayName("test put with same key will return old value")
    @Test
    void putWithSameKeyReturnOldValue() {
        //WHEN
        map.put("B", "A");
        String oldValue = map.put("B", "D");

        //THEN
        assertEquals(1, map.size());
        assertEquals("D", map.get("B"));
        assertEquals("A", oldValue);
    }

    @DisplayName("test put with new key will return null")
    @Test
    void putWithNewKeyReturnNull() {
        //WHEN
        map.put("B", "A");
        String oldValue = map.put("C", "D");

        //THEN
        assertEquals(2, map.size());
        assertEquals("A", map.get("B"));
        assertEquals("D", map.get("C"));
        assertNull(oldValue);
    }

    @DisplayName("test put with null as key twice will rewrite value")
    @Test
    void putWithNullKeyTwiceWillRewriteValue() {
        //WHEN
        map.put(null, "A");
        map.put(null, "K");

        //THEN
        assertEquals(1, map.size());
        assertEquals("K", map.get(null));
    }

    @DisplayName("test put over initial capacity(16) will increase its capacity")
    @Test
    void putOverInitialCapacityWillIncreaseItsCapacity() {
        //WHEN
        for (int i = 0; i < 10100; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }

        //THEN
        for (int i = 0; i < 10100; i++) {
            assertEquals(String.valueOf(i), map.get(String.valueOf(i)));
        }
        assertEquals(10100, map.size());
    }

    @DisplayName("test put values with keys 'null' and '0' will not rewrite each  other")
    @Test
    void putValueWithKeysNullAndZeroWillNotRewriteEachOther() {
        //WHEN
        map.put(null, "A");
        assertEquals("A", map.get(null));
        map.put(String.valueOf(0), "B");

        //THEN
        assertEquals(2, map.size());
        assertEquals("B", map.get("0"));
        assertEquals("A", map.get(null));
    }

    @DisplayName("test get size when add null value with null key will return one")
    @Test
    void getSizeWhenPutNullValueWillReturnOne() {
        //WHEN
        map.put(null, null);

        //THEN
        assertEquals(1, map.size());
        assertNull(map.get(null));
    }

    @DisplayName("test contains key will return true")
    @Test
    void containsKeyWorkCorrect() {
        //WHEN
        map.put("A", "B");
        map.put("C", "D");

        //THEN
        assertEquals(2, map.size());
        assertTrue(map.containsKey("A"));
        assertTrue(map.containsKey("C"));
    }

    @DisplayName("test contains key will return false if key not exist")
    @Test
    void containsKeyReturnFalse() {
        //WHEN
        map.put("A", "B");

        //THEN
        assertEquals(1, map.size());
        assertFalse(map.containsKey("B"));
        assertFalse(map.containsKey("L"));
        assertFalse(map.containsKey(null));
    }

    @DisplayName("test contains key will return false on empty map")
    @Test
    void containsKeyReturnFalseOnEmptyMap() {
        //WHEN
        boolean contains = map.containsKey(null);

        //THEN
        assertEquals(0, map.size());
        assertFalse(contains);
    }

    @DisplayName("test contains key will return true if key is null")
    @Test
    void containsKeyReturnTrueWithNullValue() {
        //WHEN
        map.put(null, "B");

        //THEN
        assertEquals(1, map.size());
        assertTrue(map.containsKey(null));
    }

    @DisplayName("test remove value by key will return removed value and decrease size")
    @Test
    void removeOneValueReturnRemovedValueAndWorkCorrect() {
        //GIVEN
        map.put("A", "AB");
        map.put("B", "BC");
        map.put("C", "CD");

        //WHEN
        String removed = map.remove("B");

        //THEN
        assertEquals(2, map.size());
        assertEquals("BC", removed);
        assertEquals("AB", map.get("A"));
        assertEquals("CD", map.get("C"));
    }

    @DisplayName("test remove already removed value return null")
    @Test
    void removeAlreadyRemovedValueReturnNull() {
        //GIVEN
        map.put("B", "BC");

        //WHEN
        String removedB = map.remove("B");
        String removedNull = map.remove("B");

        //THEN
        assertEquals(0, map.size());
        assertEquals("BC", removedB);
        assertNull(removedNull);
    }

    @DisplayName("test remove all values by key will return removed values and clear map")
    @Test
    void removeAllValuesReturnRemovedValuesAndClearMap() {
        //GIVEN
        map.put("A", "AB");
        map.put("B", "BC");
        map.put("C", "CD");

        //WHEN
        String removedA = map.remove("A");
        String removedB = map.remove("B");
        String removedC = map.remove("C");

        //THEN
        assertEquals(0, map.size());
        assertEquals("AB", removedA);
        assertEquals("BC", removedB);
        assertEquals("CD", removedC);
    }

    @DisplayName("test remove value with key 'null' work correct")
    @Test
    void removeNullWorkCorrect() {
        //GIVEN
        map.put(null, "AB");

        //WHEN
        String removedA = map.remove(null);

        //THEN
        assertEquals(0, map.size());
        assertEquals("AB", removedA);
    }


    @DisplayName("test remove value with non exist key return null")
    @Test
    void removeNonExistValueReturnNull() {
        //GIVEN
        map.put(null, "AB");

        //WHEN
        String removedA = map.remove("D");

        //THEN
        assertEquals(1, map.size());
        assertNull(removedA);
    }

    @DisplayName("test contains return false on removed value")
    @Test
    void containsReturnFalseOnDeletedValue() {
        //GIVEN
        map.put("A", "AB");

        //WHEN
        String removedA = map.remove("A");

        //THEN
        assertEquals(0, map.size());
        assertEquals("AB", removedA);
        assertFalse(map.containsKey("A"));
    }

    @DisplayName("test Iterator return true on hasNext return next values")
    @Test
    public void testIteratorWorkCorrect() {
        HashMap<String, String> map = new HashMap<>();
        //GIVEN
        map.put("A", "A");
        map.put("B", "B");
        map.put("C", "C");
        Iterator<Entry<String, String>> iterator = map.iterator();

        //WHEN
        assertTrue(iterator.hasNext());
        Entry<String, String> next = iterator.next();
        assertEquals("A", next.getValue());

        assertTrue(iterator.hasNext());
        next = iterator.next();
        assertEquals("B", next.getValue());

        assertTrue(iterator.hasNext());
        next = iterator.next();
        assertEquals("C", next.getValue());
    }

    @DisplayName("test Iterator work correct with two values in same cell")
    @Test
    public void testIteratorWorkCorrectWithTwoValuesWithSameCell() {
        //GIVEN
        map.put("A", "A");
        map.put(null, "B");
        map.put("0", "C");
        map.put("D", "D");
        Iterator<Entry<String, String>> iterator = map.iterator();

        //THEN
        assertEquals(4, map.size());
        assertTrue(iterator.hasNext());
        Entry<String, String> next = iterator.next();
        assertEquals("B", next.getValue());

        assertTrue(iterator.hasNext());
        next = iterator.next();
        assertEquals("C", next.getValue());

        assertTrue(iterator.hasNext());
        next = iterator.next();
        assertEquals("A", next.getValue());

        assertTrue(iterator.hasNext());
        next = iterator.next();
        assertEquals("D", next.getValue());
    }


    @DisplayName("test Iterator work correct with other initial capacity")
    @Test
    public void testIteratorWorkCorrectWithOtherInitialCapacity() {
        //GIVEN
        HashMap<String, String> map = new HashMap<>(4);
        map.put("A", "A");
        map.put("0", "C");
        map.put("D", "D");
        Iterator<Entry<String, String>> iterator = map.iterator();

        //THEN
        assertEquals(3, map.size());
        assertTrue(iterator.hasNext());
        Entry<String, String> next = iterator.next();
        assertEquals("C", next.getValue());
        assertEquals("0", next.getKey());

        assertTrue(iterator.hasNext());
        next = iterator.next();
        assertEquals("D", next.getValue());
        assertEquals("D", next.getKey());

        assertTrue(iterator.hasNext());
        next = iterator.next();
        assertEquals("A", next.getValue());
        assertEquals("A", next.getKey());
    }

    @DisplayName("test iterator remove element change size of map")
    @Test
    public void testIteratorRemoveOneValue() {
        //GIVEN
        map.put("A", "A");
        map.put(null, "B");
        map.put("0", "C");
        map.put("D", "D");
        Iterator<Entry<String, String>> iterator = map.iterator();

        //WHEN
        assertEquals(4, map.size());
        assertTrue(iterator.hasNext());
        iterator.next();
        iterator.remove();

        //THEN
        assertEquals(3, map.size());

    }

    @DisplayName("test iterator remove element change size of map")
    @Test
    public void testIteratorRemoveAllValues() {
        //GIVEN
        map.put("A", "A");
        map.put("0", "C");
        map.put("D", "D");
        assertEquals(3, map.size());
        Iterator<Entry<String, String>> iterator = map.iterator();

        //WHEN
        for (int i = 0; i < 3; i++) {
            assertTrue(iterator.hasNext());
            iterator.next();
            iterator.remove();
        }
        //THEN
        assertEquals(0, map.size());
        assertFalse(map.containsKey("A"));
        assertFalse(map.containsKey("0"));
        assertFalse(map.containsKey("C"));
        assertFalse(iterator.hasNext());

    }

    @DisplayName("test iterator hasNext return false and next return exception on empty map")
    @Test
    public void testIteratorHasNextReturnFalseAndNextReturnException() {
        //GIVEN
        map.put("A", "A");
        assertEquals(1, map.size());
        Iterator<Entry<String, String>> iterator = map.iterator();

        //WHEN
        assertTrue(iterator.hasNext());
        iterator.next();
        iterator.remove();
        assertFalse(iterator.hasNext());
        assertThrows(IllegalStateException.class, iterator::remove);

        //THEN
        assertEquals(0, map.size());
        assertFalse(iterator.hasNext());
    }

    @DisplayName("test iterator remove return exception when try remove twice")
    @Test
    public void testIteratorRemoveThrowExceptionWhenTryRemoveTwice() {
        //GIVEN
        map.put("A", "A");
        Iterator<Entry<String, String>> iterator = map.iterator();

        //WHEN
        assertTrue(iterator.hasNext());
        iterator.next();
        iterator.remove();
        Exception exception = assertThrows(IllegalStateException.class, iterator::remove);

        //THEN
        assertEquals(0, map.size());
        assertEquals("No values to remove", exception.getMessage());
        assertFalse(iterator.hasNext());

    }

    @DisplayName("test iterator remove throw exception when try remove on empty map")
    @Test
    public void testIteratorRemoveThrowExceptionWhenTryRemoveOnEmptyMap() {
        //GIVEN
        assertEquals(0, map.size());
        Iterator<Entry<String, String>> iterator = map.iterator();

        //WHEN
        Exception exceptionNext = assertThrows(NoSuchElementException.class, iterator::next);
        Exception exceptionRemove = assertThrows(IllegalStateException.class, iterator::remove);

        //THEN
        assertEquals(0, map.size());
        assertEquals("No next value found", exceptionNext.getMessage());
        assertEquals("No values to remove", exceptionRemove.getMessage());
        assertFalse(iterator.hasNext());

    }

    @DisplayName("test toString")
    @Test
    public void testToString() {
        //GIVEN
        map.put("A", "A");
        map.put(null, "B");
        map.put("0", "C");
        map.put("D", "D");

        //THEN
        assertEquals("[Entry{key=null, value=B}, Entry{key=0, value=C}, Entry{key=A, value=A}, Entry{key=D, value=D}]",
                map.toString());
    }


    @DisplayName("test put 3 entries in the same cell will return size 3")
    @Test
    public void testPutInSameCellThreeEntriesWillReturnCorrectSize() {
        //WHEN
        map.put("0", "A");
        map.put(null, "B");
        map.put(" ", "C");

        //THEN
        assertEquals(3, map.size());
        assertTrue(map.containsKey("0"));
        assertTrue(map.containsKey(null));
        assertTrue(map.containsKey(" "));

    }

    @DisplayName("test put 3 entries in the same cell and remove first entry by iterator work correct")
    @Test
    public void testPutInSameCellThreeEntriesAndRemoveFirstWorkCorrect() {
        //GIVEN
        map.put("0", "A");
        map.put(null, "B");
        map.put(" ", "C");

        //WHEN
        Iterator<Entry<String, String>> iterator = map.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next().getValue());
        iterator.remove();

        //THEN
        assertEquals(2, map.size());
        assertFalse(map.containsKey("0"));
        assertTrue(map.containsKey(null));
        assertTrue(map.containsKey(" "));
    }

    @DisplayName("test put 3 entries in the same cell and remove second entry by iterator work correct")
    @Test
    public void testPutInSameCellThreeEntriesAndRemoveByIteratorSecond() {
        //GIVEN
        map.put("0", "A");
        map.put(null, "B");
        map.put(" ", "C");

        //WHEN
        Iterator<Entry<String, String>> iterator = map.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next().getValue());
        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next().getValue());
        iterator.remove();

        //THEN
        assertEquals(2, map.size());
        assertTrue(map.containsKey("0"));
        assertFalse(map.containsKey(null));
        assertTrue(map.containsKey(" "));
    }

    @DisplayName("test put 3 entries in the same cell and remove last entry by iterator work correct")
    @Test
    public void testPutInSameCellThreeEntriesAndRemoveByIteratorLast() {
        //GIVEN
        map.put("0", "A");
        map.put(null, "B");
        map.put(" ", "C");

        //WHEN
        Iterator<Entry<String, String>> iterator = map.iterator();
        assertTrue(iterator.hasNext());
        assertEquals("A", iterator.next().getValue());
        assertTrue(iterator.hasNext());
        assertEquals("B", iterator.next().getValue());
        assertTrue(iterator.hasNext());
        assertEquals("C", iterator.next().getValue());
        iterator.remove();

        //THEN
        assertEquals(2, map.size());
        assertTrue(map.containsKey("0"));
        assertTrue(map.containsKey(null));
        assertFalse(map.containsKey(" "));

    }

    @DisplayName("test put 3 entries in the same cell and remove last entry work correct")
    @Test
    public void testPutInSameCellThreeEntriesAndRemoveFirst() {
        //GIVEN
        map.put("0", "A");
        map.put(null, "B");
        map.put(" ", "C");

        //WHEN
        assertEquals("A", map.remove("0"));

        //THEN
        assertEquals(2, map.size());
        assertFalse(map.containsKey("0"));
        assertTrue(map.containsKey(null));
        assertTrue(map.containsKey(" "));

    }

    @DisplayName("test put 3 entries in the same cell and remove second entry work correct")
    @Test
    public void testPutInSameCellThreeEntriesAndRemoveSecond() {
        //GIVEN
        map.put("0", "A");
        map.put(null, "B");
        map.put(" ", "C");

        //WHEN
        assertEquals("B", map.remove(null));

        //THEN
        assertEquals(2, map.size());
        assertTrue(map.containsKey("0"));
        assertFalse(map.containsKey(null));
        assertTrue(map.containsKey(" "));
    }

    @DisplayName("test put 3 entries in the same cell and remove last entry work correct")
    @Test
    public void testPutInSameCellThreeEntriesAndRemoveLast() {
        //GIVEN
        map.put("0", "A");
        map.put(null, "B");
        map.put(" ", "C");

        //WHEN
        assertEquals("C", map.remove(" "));

        //THEN
        assertEquals(2, map.size());
        assertTrue(map.containsKey("0"));
        assertTrue(map.containsKey(null));
        assertFalse(map.containsKey(" "));
    }
}