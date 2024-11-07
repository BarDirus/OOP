package ru.nsu.shirokov;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class HashTable<K, V> implements Iterable<HashTable<K, V>.Entry> {
    private ArrayList<LinkedList<Entry>> table;
    private int size;
    public int capacity;
    private int modCount = 0; // для обработки ConcurrentModificationException

    private static final int DEFAULT_CAPACITY = 16;
    private static final double LOAD_FACTOR = 0.75;

    public HashTable() {
        this(DEFAULT_CAPACITY);
    }

    public HashTable(int initialCapacity) {
        this.capacity = initialCapacity;
        this.table = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            table.add(new LinkedList<>());
        }
        this.size = 0;
    }

    private int hash(K key) {
        return (key == null) ? 0 : Math.abs(key.hashCode() % capacity);
    }

    // Вложенный класс для пар (ключ-значение)
    public class Entry {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public void setValue(V value) {
            this.value = value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Entry entry = (Entry) o;
            return Objects.equals(key, entry.key) && Objects.equals(value, entry.value);
        }

        @Override
        public int hashCode() {
            return Objects.hash(key, value);
        }

        @Override
        public String toString() {
            return key + "=" + value;
        }
    }

    /**
     *  1. Добавление пары ключ-значение
     */
    public void put(K key, V value) {
        int index = hash(key);
        LinkedList<Entry> bucket = table.get(index);

        for (Entry entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }

        bucket.add(new Entry(key, value));
        size++;
        modCount++;

        if ((double) size / capacity >= LOAD_FACTOR) {
            resize();
        }
    }

    /**
     * 2. Поиск значения по ключу
     */
    public V get(K key) {
        int index = hash(key);
        LinkedList<Entry> bucket = table.get(index);

        for (Entry entry : bucket) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }
        return null;
    }

    /**
     * 3. Удаление пары ключ-значение
     */
    public boolean remove(K key) {
        int index = hash(key);
        LinkedList<Entry> bucket = table.get(index);

        Iterator<Entry> iterator = bucket.iterator();
        while (iterator.hasNext()) {
            Entry entry = iterator.next();
            if (entry.getKey().equals(key)) {
                iterator.remove();
                size--;
                modCount++;
                return true;
            }
        }
        return false;
    }

    /**
     * 4. Проверка наличия ключа
     */
    public boolean containsKey(K key) {
        return get(key) != null;
    }

    /**
     * 5. Обновление значения по ключу
     */
    public void update(K key, V newValue) {
        int index = hash(key);
        LinkedList<Entry> bucket = table.get(index);

        for (Entry entry : bucket) {
            if (entry.getKey().equals(key)) {
                entry.setValue(newValue);
                return;
            }
        }
    }

    /**
     * 6. Перегрузка equals для сравнения с другой таблицей
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HashTable<?, ?> hashTable = (HashTable<?, ?>) o;
        return size == hashTable.size && table.equals(hashTable.table);
    }

    @Override
    public int hashCode() {
        return Objects.hash(table, size);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("{");
        for (LinkedList<Entry> bucket : table) {
            for (Entry entry : bucket) {
                sb.append(entry).append(", ");
            }
        }
        if (sb.length() > 1) sb.setLength(sb.length() - 2);
        sb.append("}");
        return sb.toString();
    }

    private void resize() {
        capacity *= 2;
        ArrayList<LinkedList<Entry>> newTable = new ArrayList<>(capacity);
        for (int i = 0; i < capacity; i++) {
            newTable.add(new LinkedList<>());
        }

        for (LinkedList<Entry> bucket : table) {
            for (Entry entry : bucket) {
                int index = hash(entry.getKey());
                newTable.get(index).add(entry);
            }
        }

        table = newTable;
    }

    @Override
    public Iterator<Entry> iterator() {
        return new Iterator<Entry>() {
            private int expectedModCount = modCount;
            private int bucketIndex = 0;
            private Iterator<Entry> bucketIterator = table.get(bucketIndex).iterator();

            private void checkForModifications() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
            }

            @Override
            public boolean hasNext() {
                checkForModifications();
                while (bucketIndex < capacity - 1 && !bucketIterator.hasNext()) {
                    bucketIndex++;
                    bucketIterator = table.get(bucketIndex).iterator();
                }
                return bucketIterator.hasNext();
            }

            @Override
            public Entry next() {
                checkForModifications();
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return bucketIterator.next();
            }
        };
    }

}
