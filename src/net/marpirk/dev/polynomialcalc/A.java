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

import java.math.BigInteger;
import java.util.Arrays;

/**
 * A - from Additional Methods.
 * Some methods, which are general pupose and would only make mess in classes,
 * where they're used.
 * 
 * @author Marek Pikuła
 */
public class A {
    
    /**
     * Returns greatest common divisor of given numbers.
     * @param a first number
     * @param b second number
     * @return greatest common divisor
     */
    public static long gcd(long a, long b) {
        return BigInteger.valueOf(a).gcd(BigInteger.valueOf(b)).intValue();
    }
    
    /**
     * Returns greatest common divisor from given iterables.
     * @param <I> some kind of iterable list (for example {@link java.util.Collection}, {@link java.util.ArrayList})
     * @param i iterable (list) to check
     * @return greatest common divisor of elements from given list
     */
    protected final static <I extends Iterable<Long>> long getIterableGCD(I i) {
        if ( !i.iterator().hasNext() ) return 0L;
        long gcd = i.iterator().next();
        for (Long l : i) gcd = A.gcd(gcd, l);
        return gcd;
    }
    
    /**
     * Counts decimal places in given rational number.
     * @param d rational number
     * @return count of decimal places
     */
    protected final static int getDecPlaces(double d) {
        int dec;
        if ( d != Math.round(d) ) {
            String text = Double.toString(Math.abs(d));
            dec = text.length() - text.indexOf('.') - 1;
        } else {
            dec = 0;
        }
        return dec;
    }
    
    /**
     * Compares count of decimal places between given rationals.
     * @param d1 first rational number
     * @param d2 second rational number
     * @return biggest decimal places count
     */
    protected final static int compareDecPlaces(double d1, double d2) {
        int dec1 = getDecPlaces(d1);
        int dec2 = getDecPlaces(d2);
        if ( dec1 >= dec2 ) {
            return dec1;
        } else {
            return dec2;
        }
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
    
    protected static String sortStringChars(String s) {
        char[] c = s.toCharArray();
        Arrays.sort(c);
        return String.copyValueOf(c);
    }
    
}
