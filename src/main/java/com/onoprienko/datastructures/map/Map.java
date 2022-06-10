package com.onoprienko.datastructures.map;


public interface Map<K, V> extends Iterable<Map.Entry<K, V>> {
    V put(K key, V value);

    V get(K key);

    boolean containsKey(K key);

    V remove(K key);

    int size();

    interface Entry<K, V> {
        K getKey();

        V getValue();

        V setValue(V value);

        Entry<K, V> getNext();

        Entry<K, V> setNext(Entry<K, V> entry);

    }

}