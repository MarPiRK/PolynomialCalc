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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.marpirk.dev.polynomialcalc.A.Pair;
import net.marpirk.dev.polynomialcalc.exceptions.DivisionByZeroFractionException;
import net.marpirk.dev.polynomialcalc.exceptions.HashMapDelegationException;
import net.marpirk.dev.polynomialcalc.exceptions.MonomialPowerMismatchException;
import net.marpirk.dev.polynomialcalc.exceptions.ParamParseException;
import net.marpirk.dev.polynomialcalc.i18n.i18n;

/**
 *
 * @author Marek Pikuła
 */
public class Polynomial extends HashMapDelegation<Integer, Monomial> {
    
    protected Integer highest = 0;
    
    public Polynomial() {
        super();
    }
    
    public Polynomial(HashMap<Integer, Monomial> j) {
        super(j);
        check();
    }
    
    public Polynomial(ArrayList<String> j) throws NumberFormatException, ParamParseException, DivisionByZeroFractionException {
        construct();
        String[] tmpS;
        for ( String s : j ) {
            tmpS = s.split("\\|");
            if ( tmpS.length != 2 ) throw new ParamParseException(s, null, i18n.ex.getString("PARAM_PARSE_SYNTAX_ERROR"));
            put(Integer.parseInt(tmpS[1]), new Monomial(tmpS[0], Integer.parseInt(tmpS[1])));
        }
        check();
    }
    
    public final void check() {
        checkHighest();
        for ( int i = highest; i >= 0; i-- ) {
            if ( containsKey(i) && get(i).coefficient.isZero() ) {
                remove(i);
            }
        }
    }
    
    public int checkHighest() {
        int i = 0;
        for ( Integer tmpI : keySet() ) {
            if ( tmpI > i ) i = tmpI;
        }
        highest = i;
        return i;
    }
    
    @Override
    public void beforeNodeAccess(Integer key, Monomial value) throws HashMapDelegationException { }
    @Override
    public void afterNodeAccess(Integer key, Monomial value) {
        if ( key > highest ) {
            highest = key;
        }
    }
    
    @Override
    public void beforeNodeInsertion(Integer key, Monomial value) throws HashMapDelegationException {
        if ( value.coefficient.isZero() ) throw new HashMapDelegationException();
    }
    
    @Override
    public void afterNodeInsertion(Integer key, Monomial value) {
        afterNodeAccess(key, value);
        if ( value.coefficient.isZero() ) remove(key);
    }
    
    @Override
    public void beforeNodeRemoval(Integer key, Monomial value) throws HashMapDelegationException { }
    @Override
    public void afterNodeRemoval(Integer key, Monomial value) {
        if ( key.equals(highest) ) {
            checkHighest();
        }
    }
    
    @Override
    public String toString() {
        String tmpS = "";
        String space = ( Calc.spaces ? " " : "" );
        for ( int i = highest; i >= 0; i--) {
            if ( containsKey(i) ) {
                tmpS += get(i).toString(false, i < highest, i == 0, Calc.spaces, '/') + space;
            }
        }
        return ( tmpS.endsWith(space) ? tmpS.substring(0, tmpS.length() - space.length()) : tmpS );
    }
    
    public Polynomial add(Polynomial p2) {
        return add(this, p2);
    }
    
    //TO CHECK!!!
    public static Polynomial add(Polynomial p1, Polynomial p2) {
        Polynomial pr = new Polynomial(p1.getHashMap()); //polynomial result
        p2.keySet().stream().forEach((i) -> {
            if ( pr.containsKey(i) ) {
                try {
                    pr.replace(i, pr.get(i).add(p2.get(i)));
                } catch (MonomialPowerMismatchException ex) {
                    Logger.getLogger(Polynomial.class.getName()).log(Level.SEVERE, null, ex);   //for future removal - shouldn't never occur
                    System.exit(1000);
                }
            } else {
                pr.put(i, p2.get(i));
            }
        });
        return pr;
    }
    
    public Polynomial multiply(Polynomial p2) {
        return multiply(this, p2);
    }
    
    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
        Polynomial pr = new Polynomial();   //polynomial result
        p1.keySet().stream().forEach((i) -> {
            p2.keySet().stream().forEach((j) -> {
                if ( pr.containsKey(i + j) ) {
                    try {
                        pr.replace(i + j, pr.get(i + j).add(p1.get(i).multiply(p2.get(j))));
                    } catch (MonomialPowerMismatchException ex) {
                        Logger.getLogger(Polynomial.class.getName()).log(Level.SEVERE, null, ex);   //for future removal - shouldn't never occur
                        System.exit(1000);
                    }
                } else {
                    pr.put(i + j, p1.get(i).multiply(p2.get(j)));
                }
            });
        });
        return pr;
    }
    
    public Pair<Polynomial, Polynomial> divide(Polynomial p2) {
        return divide(this, p2);
    }
    
    //not yet implemendet
    public static Pair<Polynomial, Polynomial> divide(Polynomial p1, Polynomial p2) {
        throw new UnsupportedOperationException();
    }

}
