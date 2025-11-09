package ru.ashemchuk;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class HashTable<K, V> extends Dictionary<K, V> implements Map<K, V> {
    private List<List<Node<K, V>>> list;
    private final double refillRatio = 0.75;
    private int tableSize;
    private int tableCapacity;
    private final int initSize = 256;

    public HashTable() {
        this.tableSize = 0;
        this.tableCapacity = initSize;
        this.list = new ArrayList<>(Collections.nCopies(tableCapacity, null));
    }

    private int getBucket(Object key) {
        return Math.abs(key.hashCode()) % tableCapacity;
    }

    @Override
    public int size() {
        return tableSize;
    }

    @Override
    public boolean isEmpty() {
        return tableSize == 0;
    }

    @Override
    public Enumeration<K> keys() {
        return null;
    }

    @Override
    public Enumeration<V> elements() {
        return null;
    }

    @Override
    public boolean containsKey(Object key) {
        return getNode(key) != null;
    }

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

    @Override
    public V get(Object key) {
        Node<K, V> node = getNode(key);
        if (node != null) {
            return (V) node.getValue();
        }
        return null;
    }

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
        return value;
    }

    @Override
    public V remove(Object key) {
        Node<K, V> node = getNode(key);
        if (node != null) {
            list.get(getBucket(key)).remove(node);
            tableSize--;
            return (V) node.getValue();
        }
        return null;
    }

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
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        for (Entry<? extends K, ? extends V> e : m.entrySet()) {
            put(e.getKey(), e.getValue());
        }
    }

    @Override
    public void clear() {
        list = new ArrayList<>(Collections.nCopies(tableCapacity, null));
        tableSize = 0;
    }

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
}
