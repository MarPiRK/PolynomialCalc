/**
 * Copyleft 2014 Marek Pikuła (marpirk@gmail.com)
 * 
 * This file is part of PolynomialCalc.
 * 
 * PolynomialCalc is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * PolynomialCalc is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * Copy of the GNU General Public License is included in PolynomialCalc default
 * package and in source root directory in file LICENSE
 */

package net.marpirk.dev.polynomialcalc;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.marpirk.dev.polynomialcalc.exceptions.HashMapDelegationException;

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
    
    public abstract void beforeNodeAccess(K key, V value) throws HashMapDelegationException;
    public abstract void afterNodeAccess(K key, V value);
    
    public abstract void beforeNodeInsertion(K key, V value) throws HashMapDelegationException;
    public abstract void afterNodeInsertion(K key, V value);
    
    public abstract void beforeNodeRemoval(K key, V value) throws HashMapDelegationException;
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
        try { 
            beforeNodeInsertion(key, value);
        } catch (HashMapDelegationException ex) {
            return null;
        }
        V res = p.put(key, value);
        afterNodeInsertion(key, value);
        return res;
    }
    
    public void putAll(Map<? extends K, ? extends V> m) {
        Map<? extends K, ? extends V> nm = null;   //new m
        for ( K key : m.keySet() ) {    //must be foreach loop
            try {
                beforeNodeInsertion(key, m.get(key) );
            } catch (HashMapDelegationException ex) {
                if ( nm == null ) nm = m;
                nm.remove(key);
            }
        }
        p.putAll( nm == null ? m : nm );
        m.keySet().parallelStream().forEach((key) -> {
            afterNodeInsertion(key, m.get(key) );
        });
    }
    
    public boolean remove(K key, V value) {
        try {
            beforeNodeRemoval(key, value);
        } catch (HashMapDelegationException ex) {
            return false;
        }
        boolean res = p.remove(key, value);
        afterNodeRemoval(key, value);
        return res;
    }
    
    public V remove(K key) {
        try {
            beforeNodeRemoval(key, p.get(key));
        } catch (HashMapDelegationException ex) {
            return null;
        }
        V res = p.remove(key);
        afterNodeRemoval(key, p.get(key));
        return res;
    }
    
    public V replace(K key, V value) {
        try {
            beforeNodeAccess(key, p.get(key));
        } catch (HashMapDelegationException ex) {
            return null;
        }
        V res = p.replace(key, value);
        afterNodeAccess(key, value);
        return res;
    }
    
    public boolean replace(K key, V oldValue, V newValue) {
        try {
            beforeNodeAccess(key, newValue);
        } catch (HashMapDelegationException ex) {
            return false;
        }
        boolean res = p.replace(key, oldValue, newValue);
        afterNodeAccess(key, newValue);
        return res;
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
    

}
