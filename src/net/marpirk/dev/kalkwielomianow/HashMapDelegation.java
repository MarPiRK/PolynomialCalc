package net.marpirk.dev.kalkwielomianow;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class HashMapDelegation<K,V> {
    
    private HashMap<K,V> p;
    
    public HashMapDelegation() { construct(); }
    public HashMapDelegation(HashMap<K,V> j) { construct(j); }
    public HashMapDelegation(int initialCapacity) { construct(initialCapacity); }
    public HashMapDelegation(int initialCapacity, float loadFactor) { construct(initialCapacity, loadFactor); }
    
    protected final void construct() { p = new HashMap<>(); }
    protected final void construct(HashMap<K,V> j) { p = new HashMap<>(j); }
    protected final void construct(int initialCapacity) { p = new HashMap<>(initialCapacity); }
    protected final void construct(int initialCapacity, float loadFactor) { p = new HashMap<>(initialCapacity, loadFactor); }
    
    public abstract void afterNodeAccess(K key, V value);
    public abstract void afterNodeInsertion(K key, V value);
    public abstract void afterNodeRemoval(K key, V value);
    
    protected HashMap<K,V> getHashMap() {
        return new HashMap<>(p);
    }
    
    public boolean isEmpty() {
        return p.isEmpty();
    }
    
    public int size() {
        return p.size();
    }
    
    public V get(K key) {
        return p.get(key);
    }
    
    public V put(K key, V value) {
        afterNodeInsertion(key, value);
        return p.put(key, value);
    }
    
    public void putAll(Map<? extends K, ? extends V> m) {
        m.keySet().parallelStream().forEach((key) -> {
            afterNodeInsertion(key, m.get(key) );
        });
        p.putAll(m);
    }
    
    public boolean remove(K key, V value) {
        afterNodeRemoval(key, value);
        return p.remove(key, value);
    }
    
    public V remove(K key) {
        afterNodeRemoval(key, p.get(key));
        return p.remove(key);
    }
    
    public V replace(K key, V value) {
        afterNodeAccess(key, value);
        return p.replace(key, value);
    }
    
    public boolean replace(K key, V oldValue, V newValue) {
        afterNodeAccess(key, newValue);
        return p.replace(key, oldValue, newValue);
    }
    
    public boolean containsKey(K key) {
        return p.containsKey(key);
    }
    
    public boolean containsValue(V value) {
        return p.containsValue(value);
    }
    
    public Set<K> keySet() {
        return p.keySet();
    }
    
    angielski Challenge/8
    

}
