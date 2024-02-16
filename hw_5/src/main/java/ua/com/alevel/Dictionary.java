package ua.com.alevel;

import java.util.Arrays;

public class Dictionary<K, V> {

    private Node<K, V>[] map;
    private int size;

    public Dictionary() {
        clear();
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean containsKey(K key) {
        for (Node node : map) {
            if (node != null && node.key.equals(key)) {
                return true;
            }
        }
        return false;
    }

    public boolean containsValue(V value) {
        for (Node node : map) {
            if (node != null && node.value.equals(value)) {
                return true;
            }
        }
        return false;
    }

    public V get(K key) {
        if (containsKey(key)) {
            return map[index(key)].value;
        }
        return null;
    }

    public boolean put(K key, V value) {
        if (containsKey(key)) {
            if (!get(key).equals(value)) {
                remove(key);
                addNewNode(key, value);
                return true;
            }
        } else {
            addNewNode(key, value);
            return true;
        }
        return false;
    }

    public boolean remove(K key) {
        if (containsKey(key)) {
            int index = index(key);
            System.arraycopy(map, index + 1, map, index, size - index - 1);
            map[--size] = null;
            return true;
        }
        return false;
    }

    public boolean putAll(Dictionary<K, V> dictionary) {
        K[] keys = dictionary.keys();
        V[] values = dictionary.values();
        for (int i = 0; i < dictionary.size(); i++) {
            put(keys[i], values[i]);
        }
        return true;
    }

    public boolean clear() {
        this.map = new Node[10];
        this.size = 0;
        return true;
    }

    public K[] keys() {
        K[] keys = (K[]) new Object[size];
        for (int i = 0; i < size; i++) {
            keys[i] = map[i].key;
        }
        return keys;
    }

    public V[] values() {
        V[] values = (V[]) new Object[size];
        for (int i = 0; i < size; i++) {
            values[i] = map[i].value;
        }
        return values;
    }

    private void createNewArray() {
        Node[] copy = new Node[map.length * 2];
        System.arraycopy(map, 0, copy, 0, map.length);
        map = copy;
    }

    private void addNewNode(K key, V value) {
        if (map.length <= size) {
            createNewArray();
        }
        map[size++] = new Node<>(key, value);
    }

    private int index(K key) {
        for (int i = 0; i < size; i++) {
            if (map[i] != null && map[i].key.equals(key)) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return Arrays.toString(map);
    }

    static class Node<K, V> {
        K key;
        V value;

        public Node(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public String toString() {
            return "{" + "key=" + key +
                    ", value=" + value + '}';
        }
    }
}
