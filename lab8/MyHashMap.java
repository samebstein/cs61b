import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class MyHashMap<K, V> implements Map61B<K, V> {
    private ArrayList<ULLMap<K, V>> buckets;
    private double loadFactor;
    private HashSet<K> hashSet = new HashSet<>();
    private int size = 0;
    private int capacity;


    public MyHashMap() {
        buckets = new ArrayList<>(16);
        loadFactor = 0.75;
        capacity = 16;
        for (int i = 0; i < capacity; i++) {
            buckets.add(i, new ULLMap<K, V>());
        }

    }

    public MyHashMap(int initialSize) {
        buckets = new ArrayList<>(initialSize);
        loadFactor = 0.75;
        capacity = initialSize;
        for (int i = 0; i < capacity; i++) {
            buckets.add(i, new ULLMap<K, V>());
        }

    }

    public MyHashMap(int initialSize, double loadFactor) {
        buckets = new ArrayList<>(initialSize);
        this.loadFactor = loadFactor;
        capacity = initialSize;
        for (int i = 0; i < capacity; i++) {
            buckets.add(i, new ULLMap<K, V>());
        }
    }

    /**
     * Removes all of the mappings from this map.
     */
    @Override
    public void clear() {
        for (int i = 0; i < capacity; i++) {
            buckets.add(i, new ULLMap<K, V>());
        }
        hashSet = new HashSet<>();
        size = 0;
    }


    /* Returns true if this map contains a mapping for the specified key. */
    @Override
    public boolean containsKey(K key) {
        int hash = hash(key, capacity);
        return buckets.get(hash).containsKey(key);
    }


    /* Returns the value to which the specified key is mapped, or null if this
     * map contains no mapping for the key.
     */
    @Override
    public V get(K key) {
        int hash = hash(key, capacity);
        V ret = buckets.get(hash).get(key);
        return ret;
    }

    /* Returns the number of key-value mappings in this map. */
    @Override
    public int size() {
        return size;
    }

    private int hash(K key, int curSize) {
        return (key.hashCode() & 0x7fffffff) % curSize;
    }

    /** Associates the specified value with the specified key in this map. */
    @Override
    public void put(K key, V value) {
        int hash = hash(key, capacity);
        if (buckets.get(hash).containsKey(key)) {
            buckets.get(hash).put(key, value);
            return;
        }
        buckets.get(hash).put(key, value);
        size += 1;
        hashSet.add(key);
        if (size() / capacity * 1.0 >= loadFactor) {
            resize();
        }
    }

    private void resize() {
        int M = capacity;
        ArrayList<ULLMap<K, V>> newBuckets = new ArrayList<>(M * 2);
        for (int i = 0; i < M * 2; i++) {
            newBuckets.add(i, new ULLMap<K, V>());
        }
        for (K key : hashSet) {
            int hash = hash(key,M * 2);
            newBuckets.get(hash).put(key, this.get(key));
        }
        buckets = newBuckets;
        capacity = M * 2;
    }

    @Override
    public Iterator iterator() {
        return hashSet.iterator();
    }

    /* Returns a Set view of the keys contained in this map. */
    @Override
    public Set<K> keySet() {
        return hashSet;
    }



    @Override
    public V remove(K key) {
        throw new UnsupportedOperationException();
    }
    @Override
    public V remove(K key, V value) {
        throw new UnsupportedOperationException();
    }
}

