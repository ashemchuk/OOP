package ru.ashemchuk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.ConcurrentModificationException;
import java.util.stream.Collectors;

/**
 * A hash table implementation that extends Dictionary and implements Map interface.
 * This class provides a custom hash table with separate chaining for collision resolution.
 *
 * @param <K> the type of keys maintained by this map
 * @param <V> the type of mapped values
 */
public class HashTable<K, V> extends Dictionary<K, V> implements Map<K, V>, Iterable<Node<K, V>> {
    private List<List<Node<K, V>>> list;
    private final double refillRatio = 0.75;
    private int tableSize;
    private int tableCapacity;
    private final int initSize = 256;
    private int modCount = 0;

    /**
     * Constructs an empty HashTable with the default initial capacity (256).
     */
    public HashTable() {
        this.tableSize = 0;
        this.tableCapacity = initSize;
        this.list = new ArrayList<>(Collections.nCopies(tableCapacity, null));
    }

    /**
     * Calculates the bucket index for a given key.
     *
     * @param key the key for which to calculate the bucket index
     * @return the bucket index for the key
     */
    private int getBucket(Object key) {
        return Math.abs(key.hashCode()) % tableCapacity;
    }

    /**
     * Returns the number of key-value mappings in this hash table.
     *
     * @return the number of key-value mappings in this hash table
     */
    @Override
    public int size() {
        return tableSize;
    }

    /**
     * Returns true if this hash table contains no key-value mappings.
     *
     * @return true if this hash table contains no key-value mappings
     */
    @Override
    public boolean isEmpty() {
        return tableSize == 0;
    }

    /**
     * Returns an enumeration of the keys in this hash table.
     * Not implemented in this version.
     *
     * @return null (not implemented)
     */
    @Override
    public Enumeration<K> keys() {
        return null;
    }

    /**
     * Returns an enumeration of the values in this hash table.
     * Not implemented in this version.
     *
     * @return null (not implemented)
     */
    @Override
    public Enumeration<V> elements() {
        return null;
    }

    /**
     * Returns true if this hash table contains a mapping for the specified key.
     *
     * @param key the key whose presence in this map is to be tested
     * @return true if this map contains a mapping for the specified key
     */
    @Override
    public boolean containsKey(Object key) {
        return getNode(key) != null;
    }

    /**
     * Returns true if this hash table maps one or more keys to the specified value.
     *
     * @param value the value whose presence in this map is to be tested
     * @return true if this map maps one or more keys to the specified value
     */
    @Override
    public boolean containsValue(Object value) {
        for (List<Node<K, V>> l : list) {
            if (l != null) {
                for (Node<K, V> n : l) {
                    if (n.getValue().equals(value)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns the value to which the specified key is mapped, or null if this map contains no mapping for the key.
     *
     * @param key the key whose associated value is to be returned
     * @return the value to which the specified key is mapped, or null if no mapping exists
     */
    @Override
    public V get(Object key) {
        Node<K, V> node = getNode(key);
        if (node != null) {
            return (V) node.getValue();
        }
        return null;
    }

    /**
     * Returns the node associated with the specified key, or null if no such node exists.
     *
     * @param key the key whose associated node is to be returned
     * @return the node associated with the specified key, or null if not found
     */
    private Node<K, V> getNode(Object key) {
        int idx = getBucket(key);
        if (list.get(idx) != null) {
            for (Node<K, V> n : list.get(idx)) {
                if (n.getKey().equals(key)) {
                    return n;
                }
            }
        }
        return null;
    }

    /**
     * Associates the specified value with the specified key in this hash table.
     * If the hash table previously contained a mapping for the key, the old value is replaced.
     *
     * @param key the key with which the specified value is to be associated
     * @param value the value to be associated with the specified key
     * @return the previous value associated with the key, or the new value if no previous mapping existed
     */
    @Override
    public V put(K key, V value) {
        Node<K, V> node = getNode(key);
        if (node != null) {
            node.setValue(value);
            return value;
        }
        if (tableSize > refillRatio * tableCapacity) {
            resize();
        }
        int idx = getBucket(key);
        if (list.get(idx) == null) {
            list.set(idx, new ArrayList<>());
        }
        list.get(idx).add(new Node<>(key, value));
        tableSize++;
        modCount++;
        return value;
    }

    /**
     * Removes the mapping for the specified key from this hash table if present.
     *
     * @param key the key whose mapping is to be removed from the map
     * @return the previous value associated with the key, or null if there was no mapping
     */
    @Override
    public V remove(Object key) {
        Node<K, V> node = getNode(key);
        if (node != null) {
            list.get(getBucket(key)).remove(node);
            tableSize--;
            modCount++;
            return (V) node.getValue();
        }
        return null;
    }

    /**
     * Doubles the capacity of the hash table and rehashes all existing key-value pairs.
     */
    private void resize() {
        tableCapacity *= 2;
        List<List<Node<K, V>>> newList = new ArrayList<>(Collections.nCopies(tableCapacity, null));
        for (Entry<K, V> n : entrySet()) {
            K key = n.getKey();
            V value = n.getValue();

            int idx = getBucket(key);
            if (newList.get(idx) == null) {
                newList.set(idx, new ArrayList<>());
            }
            newList.get(idx).add(new Node<>(key, value));
        }
        list = newList;
        modCount++;
    }

    /**
     * Copies all mappings from the specified map to this hash table.
     *
     * @param m the map whose mappings are to be copied to this hash table
     */
    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    /**
     * Removes all mappings from this hash table.
     */
    @Override
    public void clear() {
        list = new ArrayList<>(Collections.nCopies(tableCapacity, null));
        tableSize = 0;
        modCount++;
    }

    /**
     * Returns a Set view of the keys contained in this hash table.
     *
     * @return a set view of the keys contained in this hash table
     */
    @Override
    public Set<K> keySet() {
        Set<K> set = new HashSet<>(tableSize);
        for (List<Node<K, V>> l : list) {
            if (l != null) {
                for (Node<K, V> n : l) {
                    set.add((K) n.getKey());
                }
            }
        }
        return set;
    }

    /**
     * Returns a Collection view of the values contained in this hash table.
     *
     * @return a collection view of the values contained in this hash table
     */
    @Override
    public Collection<V> values() {
        List<V> c = new ArrayList<>(tableSize);
        for (List<Node<K, V>> l : list) {
            if (l != null) {
                for (Node<K, V> n : l) {
                    c.add((V) n.getValue());
                }
            }
        }
        return c;
    }

    /**
     * Returns a Set view of the mappings contained in this hash table.
     *
     * @return a set view of the mappings contained in this hash table
     */
    @Override
    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> set = new HashSet<>(tableSize);
        for (List<Node<K, V>> l : list) {
            if (l != null) {
                for (Node<K, V> n : l) {
                    set.add(new Node<K, V>((K) n.getKey(), (V) n.getValue()));
                }
            }
        }
        return set;
    }


    @Override
    public Iterator<Node<K, V>> iterator() {
        return new Iterator<Node<K, V>>() {
            private int expectedModCount = modCount;
            private int currentBucket = 0;
            private int currentIndex = -1;
            private Node<K, V> lastReturned = null;
            private Node<K, V> next = null;

            {
                findNext();
            }

            private void findNext() {
                while (currentBucket < tableCapacity) {
                    List<Node<K, V>> bucket = list.get(currentBucket);
                    if (bucket != null && currentIndex < bucket.size() - 1) {
                        currentIndex++;
                        next = bucket.get(currentIndex);
                        return;
                    } else {
                        currentBucket++;
                        currentIndex = -1;
                    }
                }
                next = null;
            }

            @Override
            public boolean hasNext() {
                checkForComodification();
                return next != null;
            }

            @Override
            public Node<K, V> next() {
                checkForComodification();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                lastReturned = next;
                findNext();
                return lastReturned;
            }

            @Override
            public void remove() {
                checkForComodification();
                if (lastReturned == null) {
                    throw new IllegalStateException();
                }

                HashTable.this.remove(lastReturned.getKey());
                expectedModCount = modCount;
                lastReturned = null;
            }

            private void checkForComodification() {
                if (modCount != expectedModCount) {
                    throw new ConcurrentModificationException();
                }
            }
        };
    }

    @Override
    public String toString() {
        return "HashTable("
            + this.entrySet()
                .stream()
                .map(entry -> "('" + entry.getKey() + "'," + entry.getValue() + ")")
                .collect(Collectors.joining(","))
            + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof HashTable<?, ?>)) {
            return false;
        }

        HashTable<K, V> other = (HashTable<K, V>) obj;

        if (this.tableSize != other.tableSize) {
            return false;
        }

        if (this.entrySet().equals(other.entrySet())) {
            return true;
        }

        return false;
    }
}