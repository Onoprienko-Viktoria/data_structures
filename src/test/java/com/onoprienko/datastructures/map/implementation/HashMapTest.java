package com.onoprienko.datastructures.map.implementation;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
        for (int i = 0; i < 17; i++) {
            map.put(String.valueOf(i), String.valueOf(i));
        }

        //THEN
        assertEquals(17, map.size());
        assertEquals("0", map.get("0"));
        assertEquals("1", map.get("1"));
        assertEquals("2", map.get("2"));
        assertEquals("3", map.get("3"));
        assertEquals("4", map.get("4"));
        assertEquals("5", map.get("5"));
        assertEquals("6", map.get("6"));
        assertEquals("7", map.get("7"));
        assertEquals("8", map.get("8"));
        assertEquals("9", map.get("9"));
        assertEquals("10", map.get("10"));
        assertEquals("11", map.get("11"));
        assertEquals("12", map.get("12"));
        assertEquals("13", map.get("13"));
        assertEquals("14", map.get("14"));
        assertEquals("15", map.get("15"));
        assertEquals("16", map.get("16"));
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
        assertEquals("A", map.get(null));
        assertEquals("B", map.get("0"));
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

}