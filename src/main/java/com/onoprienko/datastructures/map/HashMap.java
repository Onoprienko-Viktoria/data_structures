package com.onoprienko.datastructures.map;

import java.util.*;

public class HashMap<K, V> implements Map<K, V> {
    private static final double LOAD_FACTOR = 0.5;
    private static final int GROW_FACTOR = 2;
    private static final int INITIAL_CAPACITY = 16;

    private List<Entry<K, V>>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public HashMap() {
        buckets = new List[INITIAL_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
        buckets = new List[capacity];
    }


    @Override
    public V put(K key, V value) {
        ensureCapacity();
        Entry<K, V> entry = getEntry(key);
        if (entry != null) {
            return entry.setValue(value);
        }

        int bucketIndex = getIndex(key);

        if (buckets[bucketIndex] == null) {
            buckets[bucketIndex] = new ArrayList<>(1);
        }

        List<Entry<K, V>> bucket = buckets[bucketIndex];
        bucket.add(new HashMapEntry<>(key, value));

        size++;
        return null;
    }

    @Override
    public V get(K key) {
        Entry<K, V> entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V remove(K key) {
        int bucketIndex = getIndex(key);
        List<Entry<K, V>> bucket = buckets[bucketIndex];
        if (bucket == null) {
            return null;
        }
        Iterator<Entry<K, V>> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            Entry<K, V> current = iterator.next();
            if (Objects.equals(current.getKey(), key)) {
                iterator.remove();
                size--;
                return current.getValue();
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(K key) {
        Entry<K, V> entry = getEntry(key);
        return entry != null;
    }

    @Override
    public String toString() {
        StringJoiner result = new StringJoiner(", ", "[", "]");
        for (Entry<K, V> entry : this) {
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

    private int getIndex(K key, int bucketsCount) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        if (Objects.equals(hash, Integer.MIN_VALUE)) {
            return 0;
        }
        return hash % bucketsCount;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        int capacity = buckets.length;
        if (size > (capacity * LOAD_FACTOR)) {
            List<Entry<K, V>>[] newBuckets = new List[capacity * GROW_FACTOR];
            for (Entry<K, V> entry : this) {
                int bucketIndex = getIndex(entry.getKey(), newBuckets.length);
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
                if (Objects.equals(entry.getKey(), key)) {
                    return entry;
                }
            }
        }
        return null;
    }

    public class HashMapIterator implements Iterator<Entry<K, V>> {
        private int currentBucket = 0;
        private Iterator<Entry<K, V>> iterator = buckets[currentBucket] == null ? null : buckets[currentBucket].iterator();

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
        public Entry<K, V> next() {
            if (hasNext() && iterator.hasNext()) {
                return iterator.next();
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

    protected static class HashMapEntry<K, V> implements Entry<K, V> {
        private final K key;
        private V value;

        private HashMapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V value) {
            V oldValue = this.value;
            this.value = value;
            return oldValue;
        }

        @Override
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
