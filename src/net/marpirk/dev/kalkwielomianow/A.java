package net.marpirk.dev.kalkwielomianow;

import java.math.BigInteger;

/**
 * A - from Additional Methods.
 * 
 * @author Marek Pikuła
 */
public class A {
    
    public static long gcd(long a, long b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }
    
    public static boolean isInteger(String s) {
        try { Integer.parseInt(s); 
        } catch(NumberFormatException e) { return false; }
        return true;
    }
    
    public static class Pair<K,V> {
        K key; V value; 
        public Pair(K key, V value) { this.key = key; this.value = value; }
    }
    
}
