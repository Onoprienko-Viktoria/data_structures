package com.onoprienko.datastructures.map;


import com.onoprienko.datastructures.map.implementation.HashMap.Entry;

import java.util.Iterator;

public interface Map<K, V> extends Iterable<Entry<K, V>> {
    V put(K key, V value);

    V get(K key);

    boolean containsKey(K key);

    V remove(K key);

    int size();

    Iterator iterator();

}