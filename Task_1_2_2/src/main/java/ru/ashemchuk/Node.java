package ru.ashemchuk;

import java.util.Map;

/**
 * A simple implementation of Map.Entry that represents a key-value pair.
 * This class is used internally by the HashTable class to store entries.
 *
 * @param <K> the type of the key
 * @param <V> the type of the value
 */
class Node<K, V> implements Map.Entry<K,V> {
    private final K key;
    private V value;

    /**
     * Constructs a new Node with the specified key and value.
     *
     * @param key the key for this node
     * @param value the value for this node
     */
    public Node(K key, V value) {
        this.key = key;
        this.value = value;
    }

    /**
     * Returns the key corresponding to this entry.
     *
     * @return the key corresponding to this entry
     */
    @Override
    public K getKey() {
        return this.key;
    }

    /**
     * Returns the value corresponding to this entry.
     *
     * @return the value corresponding to this entry
     */
    @Override
    public V getValue() {
        return this.value;
    }

    /**
     * Replaces the value corresponding to this entry with the specified value.
     *
     * @param value the new value to be stored in this entry
     * @return the old value corresponding to the entry
     */
    @Override
    public Object setValue(Object value) {
        if (value == null) {
            return null;
        }
        this.value = (V) value;
        return value;
    }

    /**
     * Compares the specified object with this entry for equality.
     * Returns true if the given object is also a Node and both have
     * the same key and value.
     *
     * @param o the object to be compared for equality with this entry
     * @return true if the specified object is equal to this entry
     */
    @Override
    public boolean equals(Object o) {
        return this.value == ((Node<?, ?>) o).value && this.key == ((Node<?, ?>) o).key;
    }

    /**
     * Returns the hash code value for this map entry.
     * Note: This implementation returns 0 for all nodes, which may impact performance.
     *
     * @return the hash code value for this map entry
     */
    @Override
    public int hashCode() {
        return 0;
    }
}