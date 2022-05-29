package com.onoprienko.datastructures.map.implementation;

import com.onoprienko.datastructures.map.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class HashMap<K, V> implements Map<K, V> {
    private static final double LOAD_FACTOR = 0.5;
    private static final int GROW_FACTOR = 2;
    private static final int INITIAL_CAPACITY = 16;

    private List<Entry<K, V>>[] buckets;
    private int size;

    public HashMap() {
        buckets = new List[INITIAL_CAPACITY];
    }

    public HashMap(int capacity) {
        buckets = new List[capacity];
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
        buckets[bucketIndex] = buckets[bucketIndex] == null ? new ArrayList<>() : buckets[bucketIndex];
        List<Entry<K, V>> bucket = buckets[bucketIndex];
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
        List<Entry<K, V>> bucket = buckets[bucketIndex];
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
        int hashCode = key == null ? 0 : Math.abs(key.hashCode());
        int index = hashCode % size;
        return index;
    }

    private void ensureCapacity() {
        int capacity = buckets.length;
        if (size > (capacity * LOAD_FACTOR)) {
            List<Entry<K, V>>[] newBuckets = new List[capacity * GROW_FACTOR];

            for (List<Entry<K, V>> bucket : buckets) {
                if (bucket != null) {
                    for (Entry<K, V> entry : bucket) {
                        int bucketIndex = getIndex(entry.key, newBuckets.length);
                        newBuckets[bucketIndex] = new ArrayList<>();
                        List<Entry<K, V>> newBucket = newBuckets[bucketIndex];
                        newBucket.add(entry);
                    }
                }
            }
            buckets = newBuckets;
        }
    }


    private Entry<K, V> getEntry(K key) {
        int bucketIndex = getIndex(key);
        List<Entry<K, V>> bucket = buckets[bucketIndex];
        if (bucket != null) {
            for (Entry<K, V> entry : bucket) {
                if (Objects.equals(entry.key, key)) {
                    return entry;
                }
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
