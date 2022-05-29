package com.onoprienko.datastructures.map;

import java.util.Iterator;

public interface Map<K, V> extends Iterable {
    V put(K key, V value);

    V get(K key);

    boolean containsKey(K key);

    V remove(K key);

    int size();

    default Iterator iterator() {
        return null;
    }
}
