package ru.ashemchuk;

import java.util.Map;

public class Node<K, V> implements Map.Entry<K,V> {
    private final K key;
    private V value;

    public Node(K key, V value) {
        this.key = key;
        this.value = value;

    }

    @Override
    public K getKey() {
        return this.key;
    }

    @Override
    public V getValue() {
        return this.value;
    }

    @Override
    public Object setValue(Object value) {
        if (value == null) {
            return null;
        }
        this.value = (V) value;
        return value;
    }

    @Override
    public boolean equals(Object o) {
        return this.value == ((Node<?, ?>) o).value && this.key == ((Node<?, ?>) o).key;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
