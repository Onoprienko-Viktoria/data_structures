package com.onoprienko.datastructures.map.implementation;

import com.onoprienko.datastructures.map.Map;

import java.util.ArrayList;
import java.util.Objects;

public class HashMap<K, V> implements Map<K, V> {
    private ArrayList<Entry<K, V>>[] buckets = new ArrayList[16];
    private int size;

    private static final double LOAD_FACTOR = 0.5;

    public HashMap() {
        for (int i = 0; i < buckets.length; i++) {
            buckets[i] = new ArrayList<>();
        }
    }

    @Override
    public V put(K key, V value) {
        ensureCapacity();
        Entry<K, V> entry = getEntry(key);
        if (entry != null) {
            V oldValue = entry.value;
            entry.value = value;
            return oldValue;
        }

        int bucketIndex = getIndex(key);
        ArrayList<Entry<K, V>> bucket = buckets[bucketIndex];

        bucket.add(new Entry<>(key, value));
        size++;
        return null;
    }

    @Override
    public V get(K key) {
        Entry<K, V> entry = getEntry(key);
        return entry == null ? null : entry.value;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V remove(K key) {
        Entry<K, V> entry = getEntry(key);
        if (entry == null) {
            return null;
        }
        int bucketIndex = getIndex(key);
        ArrayList<Entry<K, V>> bucket = buckets[bucketIndex];
        bucket.remove(entry);
        size--;
        return entry.value;
    }

    @Override
    public boolean containsKey(K key) {
        Entry<K, V> entry = getEntry(key);
        return entry != null;
    }

    private int getIndex(K key) {
        return getIndex(key, buckets.length);
    }

    private int getIndex(K key, int size) {
        if (Objects.equals(key, null)) {
            return 0;
        }
        int hashCode = key.hashCode();
        int index = hashCode % size;
        return index;
    }

    private void ensureCapacity() {
        int capacity = buckets.length;
        if (size > (capacity * LOAD_FACTOR)) {
            ArrayList<Entry<K, V>>[] newBuckets = new ArrayList[capacity * 2];
            for (int i = 0; i < newBuckets.length; i++) {
                newBuckets[i] = new ArrayList<>();
            }
            buckets = relocateEntries(newBuckets);
        }
    }

    private ArrayList<Entry<K, V>>[] relocateEntries(ArrayList<Entry<K, V>>[] newBuckets) {
        for (ArrayList<Entry<K, V>> bucket : buckets) {
            for (Entry<K, V> entry : bucket) {
                int bucketIndex = getIndex(entry.key, newBuckets.length);
                ArrayList<Entry<K, V>> newBucket = newBuckets[bucketIndex];
                newBucket.add(entry);
            }
        }
        return newBuckets;
    }

    private Entry<K, V> getEntry(K key) {
        int bucketIndex = getIndex(key);
        ArrayList<Entry<K, V>> bucket = buckets[bucketIndex];
        for (Entry<K, V> entry : bucket) {
            if (Objects.equals(entry.key, key)) {
                return entry;
            }
        }
        return null;
    }

    private static class Entry<K, V> {
        private final K key;
        private V value;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }
    }
}
