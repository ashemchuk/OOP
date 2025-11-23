package ru.ashemchuk;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class HashTableTest {
    private HashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    void testEmptyTable() {
        assertEquals(0, hashTable.size());
        assertTrue(hashTable.isEmpty());
    }

    @Test
    void testPutAndGet() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        assertEquals(3, hashTable.size());
        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertEquals(3, hashTable.get("three"));
    }

    @Test
    void testPutOverwrite() {
        hashTable.put("key", 1);
        assertEquals(1, hashTable.get("key"));

        // Overwrite value
        hashTable.put("key", 100);
        assertEquals(100, hashTable.get("key"));
        assertEquals(1, hashTable.size());
    }

    @Test
    void testGetNonExistentKey() {
        assertNull(hashTable.get("nonexistent"));
    }

    @Test
    void testRemove() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);

        Integer removed = hashTable.remove("one");
        assertEquals(1, removed);
        assertEquals(1, hashTable.size());
        assertNull(hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
    }

    @Test
    void testRemoveNonExistent() {
        assertNull(hashTable.remove("nonexistent"));
    }

    @Test
    void testContainsKey() {
        hashTable.put("key1", 1);
        hashTable.put("key2", 2);

        assertTrue(hashTable.containsKey("key1"));
        assertTrue(hashTable.containsKey("key2"));
        assertFalse(hashTable.containsKey("key3"));
    }

    @Test
    void testClear() {
        hashTable.put("one", 1);
        hashTable.put("two", 2);
        hashTable.put("three", 3);

        hashTable.clear();

        assertEquals(0, hashTable.size());
        assertTrue(hashTable.isEmpty());
        assertNull(hashTable.get("one"));
    }

    @Test
    void testKeySet() {
        hashTable.put("a", 1);
        hashTable.put("b", 2);
        hashTable.put("c", 3);

        Set<String> keys = hashTable.keySet();

        assertEquals(3, keys.size());
        assertTrue(keys.contains("a"));
        assertTrue(keys.contains("b"));
        assertTrue(keys.contains("c"));
        assertFalse(keys.contains("d"));
    }

    @Test
    void testValues() {
        hashTable.put("a", 1);
        hashTable.put("b", 2);
        hashTable.put("c", 3);

        Collection<Integer> values = hashTable.values();

        assertEquals(3, values.size());
        assertTrue(values.contains(1));
        assertTrue(values.contains(2));
        assertTrue(values.contains(3));
        assertFalse(values.contains(4));
    }

    @Test
    void testEntrySet() {
        hashTable.put("a", 1);
        hashTable.put("b", 2);

        Set<Map.Entry<String, Integer>> entries = hashTable.entrySet();

        assertEquals(2, entries.size());

        // Check if entries contain expected key-value pairs
        boolean foundA = false, foundB = false;
        for (Map.Entry<String, Integer> entry : entries) {
            if (entry.getKey().equals("a") && entry.getValue().equals(1)) {
                foundA = true;
            }
            if (entry.getKey().equals("b") && entry.getValue().equals(2)) {
                foundB = true;
            }
        }

        assertTrue(foundA);
        assertTrue(foundB);
    }

    @Test
    void testPutAll() {
        Map<String, Integer> sourceMap = new HashMap<>();
        sourceMap.put("one", 1);
        sourceMap.put("two", 2);
        sourceMap.put("three", 3);

        hashTable.putAll(sourceMap);

        assertEquals(3, hashTable.size());
        assertEquals(1, hashTable.get("one"));
        assertEquals(2, hashTable.get("two"));
        assertEquals(3, hashTable.get("three"));
    }

    @Test
    void testHashCollisions() {
        // Test with keys that might have same hash code
        hashTable.put("FB", 1);  // "FB" and "Ea" have same hash code in Java
        hashTable.put("Ea", 2);

        assertEquals(2, hashTable.size());
        assertEquals(1, hashTable.get("FB"));
        assertEquals(2, hashTable.get("Ea"));
    }

    @Test
    void testNullKey() {
        // Note: This might throw NPE depending on implementation
        assertThrows(NullPointerException.class, () -> {
            hashTable.put(null, 1);
        });
    }

    @Test
    void testWithDifferentTypes() {
        HashTable<Integer, String> intTable = new HashTable<>();
        intTable.put(1, "one");
        intTable.put(2, "two");

        assertEquals("one", intTable.get(1));
        assertEquals("two", intTable.get(2));
    }

    @Test
    void testPerformanceWithManyElements() {
        // Test with more elements than initial capacity
        for (int i = 0; i < 1000; i++) {
            hashTable.put("key" + i, i);
        }

        assertEquals(1000, hashTable.size());

        // Verify random access
        assertEquals(42, hashTable.get("key42"));
        assertEquals(999, hashTable.get("key999"));
    }

    @Test
    void testRemoveFromChain() {
        // Create a situation where multiple entries are in same bucket
        hashTable.put("FB", 1);
        hashTable.put("Ea", 2);  // Same bucket as "FB"

        hashTable.remove("FB");

        assertEquals(1, hashTable.size());
        assertNull(hashTable.get("FB"));
        assertEquals(2, hashTable.get("Ea"));
    }

    @Test
    void testIterator() {
        Set<String> keys = new HashSet<>();
        Set<Integer> values = new HashSet<>();

        hashTable.put("A", 1);
        hashTable.put("B", 2);
        hashTable.put("C", 3);

        for (Node<String, Integer> entry : hashTable) {
            keys.add(entry.getKey());
            values.add(entry.getValue());
        }

        assertEquals(keys, Set.of("A", "B", "C"));
        assertEquals(values, Set.of(1, 2, 3));
    }

    @Test
    void testIteratorConcurrentModificationExceptionThrowing() {
        assertThrows(ConcurrentModificationException.class, () -> {
            hashTable.put("A", 1);
            hashTable.put("B", 2);
            hashTable.put("C", 3);

            for (Node<String, Integer> entry : hashTable) {
                if (Objects.equals(entry.getKey(), "A")) {
                    hashTable.remove("C");
                }
            }
        });
    }

    @Test
    void testToString() {
        hashTable.put("A", 1);
        hashTable.put("B", 2);

        assertEquals(hashTable.toString(), "HashTable(('A',1),('B',2))");
    }

    @Test
    void testCompareHashTables() {
        HashTable<String, Integer> table1 = new HashTable<>();
        table1.put("A", 1);
        table1.put("B", 2);

        HashTable<String, Integer> table2 = new HashTable<>();
        table2.put("A", 1);
        table2.put("B", 2);

        assertEquals(table1, table2);
    }
}