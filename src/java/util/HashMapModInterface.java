package java.util;

public abstract class HashMapModInterface<K, V> extends HashMap<K, V> {
    
    public HashMapModInterface(Map<? extends K, ? extends V> m) { super(m); }
    public HashMapModInterface(int initialCapacity, float loadFactor) { super(initialCapacity, loadFactor);}
    public HashMapModInterface(int initialCapacity) { super(initialCapacity); }
    public HashMapModInterface() { super(); }
    
    public class Node<K,V> extends HashMap.Node<K,V> {
        public Node(int hash, K key, V value, Node<K,V> next) {
            super(hash, key, value, next);
        }
    }
    
    @Override
    public abstract void afterNodeInsertion(boolean evict);
    public abstract void afterNodeAccess(Node<K,V> p);
    public abstract void afterNodeRemoval(Node<K,V> p);

}
