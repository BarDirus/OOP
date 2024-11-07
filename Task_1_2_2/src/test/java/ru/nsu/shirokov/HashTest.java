package ru.nsu.shirokov;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HashTableTest {
    private HashTable<String, Integer> hashTable;

    @BeforeEach
    void setUp() {
        hashTable = new HashTable<>();
    }

    @Test
    void testPutAndGet() {
        hashTable.put("apple", 1);
        hashTable.put("banana", 2);
        assertEquals(1, hashTable.get("apple"));
        assertEquals(2, hashTable.get("banana"));
    }

    @Test
    void testRemove() {
        hashTable.put("apple", 1);
        hashTable.remove("apple");
        assertNull(hashTable.get("apple"));
    }

    @Test
    void testContainsKey() {
        hashTable.put("apple", 1);
        assertTrue(hashTable.containsKey("apple"));
        assertFalse(hashTable.containsKey("banana"));
    }

    @Test
    void testEquals() {
        HashTable<String, Integer> otherTable = new HashTable<>();
        hashTable.put("apple", 1);
        otherTable.put("apple", 1);
        assertEquals(hashTable, otherTable);
    }

    @Test
    void testToString() {
        hashTable.put("apple", 1);
        assertEquals("{apple=1}", hashTable.toString());
    }

    @Test
    void testIteration() {
        hashTable.put("apple", 1);
        hashTable.put("banana", 2);

        int sum = 0;
        for (HashTable<String, Integer>.Entry entry : hashTable) {
            sum += entry.getValue();
        }
        assertEquals(3, sum);
    }

    @Test
    void testHashCode() {
        HashTable<String, Integer> otherTable = new HashTable<>();
        hashTable.put("apple", 1);
        hashTable.put("banana", 2);
        otherTable.put("apple", 1);
        otherTable.put("banana", 2);

        assertEquals(hashTable.hashCode(), otherTable.hashCode(), "Hash codes should match for equal tables");

        // Modify one table and check if hash codes differ
        hashTable.put("cherry", 3);
        assertNotEquals(hashTable.hashCode(), otherTable.hashCode(), "Hash codes should differ for non-equal tables");
    }

    @Test
    void testResize() {
        int initialCapacity = 4;
        hashTable = new HashTable<>(initialCapacity); // Create table with small initial capacity

        // Fill the table to exceed the load factor and trigger resize
        for (int i = 0; i < initialCapacity; i++) {
            hashTable.put("key" + i, i);
        }

        // Check that capacity has increased after resize
        int expectedCapacity = initialCapacity * 2;
        assertEquals(expectedCapacity, hashTable.capacity, "Capacity should double after resize");

        // Check that all elements are still present after resize
        for (int i = 0; i < initialCapacity; i++) {
            assertEquals(i, hashTable.get("key" + i));
        }
    }

    @Test
    void testUpdate() {
        hashTable.put("apple", 1);
        hashTable.update("apple", 5); // Update existing key

        assertEquals(5, hashTable.get("apple"), "Value should be updated to 5");

        hashTable.put("banana", 2);
        hashTable.update("banana", 10); // Update existing key to a new value

        assertEquals(10, hashTable.get("banana"), "Value should be updated to 10");

        // Ensure non-existent keys are not added
        hashTable.update("cherry", 3);
        assertNull(hashTable.get("cherry"), "Non-existent key should not be added");
    }

}
