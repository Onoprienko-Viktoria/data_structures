package com.onoprienko.datastructures.map;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringJoiner;

public class HashMap<K, V> implements Map<K, V> {
    private static final double LOAD_FACTOR = 0.5;
    private static final int GROW_FACTOR = 2;
    private static final int INITIAL_CAPACITY = 16;

    private HashMapEntry<K, V>[] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public HashMap() {
        buckets = new HashMapEntry[INITIAL_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public HashMap(int capacity) {
        buckets = new HashMapEntry[capacity];
    }


    @Override
    public V put(K key, V value) {
        ensureCapacity();
        int bucketIndex = getIndex(key);
        HashMapEntry<K, V> currentBucket = buckets[bucketIndex];

        if (currentBucket == null) {
            buckets[bucketIndex] = new HashMapEntry<>(key, value, getHash(key));
            size++;
            return null;
        }

        while (true) {
            if (currentBucket.getHash() == getHash(key)) {
                if (Objects.equals(currentBucket.getKey(), key)) {
                    return currentBucket.setValue(value);
                }
            }
            if (currentBucket.getNext() == null) {
                currentBucket.setNext(new HashMapEntry<>(key, value, getHash(key)));
                break;
            }
            currentBucket = currentBucket.getNext();
        }

        size++;
        return null;
    }

    @Override
    public V get(K key) {
        HashMapEntry<K, V> entry = getEntry(key);
        return entry == null ? null : entry.getValue();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public V remove(K key) {
        int bucketIndex = getIndex(key);
        HashMapEntry<K, V> current = buckets[bucketIndex];

        if (current == null) {
            return null;
        }

        if (current.getHash() == (getHash(key))) {
            if (Objects.equals(current.getKey(), key)) {
                V oldValue = current.getValue();
                buckets[bucketIndex] = current.getNext();
                size--;
                return oldValue;
            }
        }

        while (current != null) {
            HashMapEntry<K, V> next = current.getNext();
            if (next.getHash() == (getHash(key))) {
                if (Objects.equals(next.getKey(), key)) {
                    V oldValue = next.getValue();
                    current.setNext(next.getNext());
                    size--;
                    return oldValue;
                }
            }
            current = current.getNext();
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
        int hash = getHash(key);
        return hash % bucketsCount;
    }

    private int getHash(K key) {
        if (key == null) {
            return 0;
        }
        int hash = key.hashCode();
        if (Objects.equals(hash, Integer.MIN_VALUE)) {
            return 0;
        }
        return hash;
    }

    @SuppressWarnings("unchecked")
    private void ensureCapacity() {
        int capacity = buckets.length;
        if (size > (capacity * LOAD_FACTOR)) {
            HashMapEntry<K, V>[] newBuckets = new HashMapEntry[capacity * GROW_FACTOR];
            for (Entry<K, V> entry : this) {
                int bucketIndex = getIndex(entry.getKey(), newBuckets.length);
                HashMapEntry<K, V> currentEntry = newBuckets[bucketIndex];
                HashMapEntry<K, V> entryToMove = new HashMapEntry<>(entry.getKey(), entry.getValue(), getHash(entry.getKey()));

                if (currentEntry == null) {
                    newBuckets[bucketIndex] = entryToMove;
                    newBuckets[bucketIndex].setNext(null);
                } else {
                    while (true) {
                        if (currentEntry.next == null) {
                            currentEntry.setNext(entryToMove);
                            break;
                        } else {
                            currentEntry = currentEntry.getNext();
                        }
                    }
                }
            }
            buckets = newBuckets;
        }
    }


    private HashMapEntry<K, V> getEntry(K key) {
        int bucketIndex = getIndex(key);
        HashMapEntry<K, V> current = buckets[bucketIndex];
        while (current != null) {
            if (current.getHash() == getHash(key)) {
                if (Objects.equals(current.getKey(), key)) {
                    return current;
                }
            }
            current = current.getNext();
        }
        return null;
    }

    public class HashMapIterator implements Iterator<Entry<K, V>> {
        private int currentBucketIndex;
        private HashMapEntry<K, V> currentEntry;
        private HashMapEntry<K, V> oldEntry;
        private HashMapEntry<K, V> nextEntry;
        private boolean canRemove;

        HashMapIterator() {
            currentBucketIndex = 0;
            currentEntry = buckets[currentBucketIndex];
        }

        @Override
        public boolean hasNext() {
            int length = buckets.length - 1;
            if (currentBucketIndex > length) {
                return false;
            }
            if (nextEntry != null) {
                oldEntry = currentEntry;
                currentEntry = nextEntry;
                return true;
            }

            currentEntry = buckets[currentBucketIndex];

            if (currentEntry == null) {
                while (currentBucketIndex <= length - 1) {
                    currentBucketIndex++;
                    currentEntry = buckets[currentBucketIndex];
                    if (currentEntry != null) {
                        break;
                    }
                }
            }

            return currentEntry != null;
        }

        @Override
        public HashMapEntry<K, V> next() {
            if (currentEntry == null) {
                throw new NoSuchElementException("No next value found");
            }
            if (currentEntry.getNext() != null) {
                nextEntry = currentEntry.getNext();
            } else {
                nextEntry = null;
                currentBucketIndex++;
            }
            canRemove = true;
            return currentEntry;
        }

        @Override
        public void remove() {
            if (currentEntry == null || !canRemove) {
                throw new IllegalStateException("No values to remove");
            }
            if (oldEntry == null) {
                int index = currentBucketIndex == 0 ? 0 : currentBucketIndex - 1;
                buckets[index] = currentEntry.getNext();
            } else {
                oldEntry.setNext(currentEntry.getNext());
            }
            canRemove = false;
            size--;
        }
    }

    private static class HashMapEntry<K, V> implements Entry<K, V> {
        private final int hash;
        private final K key;
        private V value;
        private HashMapEntry<K, V> next;

        private HashMapEntry(K key, V value, int hash) {
            this.key = key;
            this.value = value;
            this.hash = hash;
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


        protected HashMapEntry<K, V> getNext() {
            return next;
        }


        protected void setNext(HashMapEntry<K, V> entry) {
            this.next = entry;
        }


        @Override
        public K getKey() {
            return key;
        }


        protected int getHash() {
            return hash;
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
