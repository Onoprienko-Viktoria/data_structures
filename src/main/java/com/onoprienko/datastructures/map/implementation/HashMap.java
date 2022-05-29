package com.onoprienko.datastructures.map.implementation;

import com.onoprienko.datastructures.map.Map;

import java.util.*;

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
        buckets[bucketIndex] = buckets[bucketIndex] == null ? new ArrayList<>(1) : buckets[bucketIndex];
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

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(", ", "[", "]");
        for (Entry entry : this) {
            result.add(String.valueOf(entry));
        }
        return result.toString();
    }


    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashMapIterator();
    }


    private int getIndex(K key) {
        return getIndex(key, buckets.length);
    }

    private int getIndex(K key, int size) {
        int hashCode = key == null ? 0 : Math.abs(key.hashCode() == Integer.MIN_VALUE ? key.hashCode() - 1 : key.hashCode());
        int index = hashCode % size;
        return index;
    }

    private void ensureCapacity() {
        int capacity = buckets.length;
        if (size > (capacity * LOAD_FACTOR)) {
            List<Entry<K, V>>[] newBuckets = new List[capacity * GROW_FACTOR];
            for (Entry entry : this) {
                int bucketIndex = getIndex((K) entry.getKey(), newBuckets.length);
                newBuckets[bucketIndex] = new ArrayList<>(1);
                newBuckets[bucketIndex].add(entry);
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

    public class HashMapIterator<Entry> implements Iterator<Entry> {
        private int currentBucket = 0;
        private Iterator<HashMap.Entry<K, V>> iterator = buckets[currentBucket] == null ? null : buckets[currentBucket].iterator();

        @Override
        public boolean hasNext() {
            int length = buckets.length - 1;
            if (currentBucket >= length) {
                return false;
            }
            if (iterator != null) {
                if (iterator.hasNext()) {
                    return true;
                }
            }
            currentBucket++;
            if (buckets[currentBucket] == null) {
                while (currentBucket <= length - 1) {
                    if (buckets[currentBucket] != null) {
                        break;
                    }
                    currentBucket++;
                }
            }
            iterator = buckets[currentBucket] == null ? null : buckets[currentBucket].iterator();
            return iterator != null;
        }

        @Override
        public Entry next() {
            if (hasNext() && iterator.hasNext()) {
                return (Entry) iterator.next();
            } else {
                throw new NoSuchElementException("There no next value");
            }
        }

        @Override
        public void remove() {
            if (iterator != null) {
                iterator.remove();
                size--;
            } else {
                throw new IllegalStateException("No element to remove");
            }
        }
    }

    public static class Entry<K, V> {
        private final K key;
        private V value;

        private Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        public K getKey() {
            return key;
        }

        @Override
        public String toString() {
            return "Entry{" +
                    "key=" + key +
                    ", value=" + value +
                    '}';
        }
    }
}
