package net.marpirk.dev.polynomialcalc;

import java.util.ArrayList;
import java.util.HashMap;

import net.marpirk.dev.polynomialcalc.A.Pair;
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
    
    public Polynomial(ArrayList<String> j) throws NumberFormatException, ParamParseException {
        construct();
        String[] tmpS;
        for ( String s : j ) {
            tmpS = s.split("|");
            if ( tmpS.length > 2 ) throw new ParamParseException(s, null, i18n.ex.getString("PARAM_PARSE_SYNTAX_ERROR"));
            put(Integer.parseInt(tmpS[1]), new Monomial(tmpS[0], Integer.parseInt(tmpS[1])));
        }
        check();
    }
    
    public void check() {
        checkHighest();
        for ( int i = highest; i >= 0; i-- ) {
            if ( containsKey(i) && get(i).isEmpty() ) {
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
    public void afterNodeAccess(Integer key, Monomial value) {
        if ( key > highest ) {
            highest = key;
        }
    }
    
    @Override
    public void afterNodeInsertion(Integer key, Monomial value) {
        afterNodeAccess(key, value);
    }
    
    @Override
    public void afterNodeRemoval(Integer key, Monomial value) {
        if ( key.equals(highest) ) {
            checkHighest();
        }
    }
    
    @Override
    public boolean isEmpty() {
        check();
        return super.isEmpty();
    }
    
    @Override
    public String toString() {
        String tmpS = "";
        for ( int i = highest; i >= 0; i--) {
            if ( containsKey(i) ) {
                tmpS += get(i).toString(true) + " ";
            }
        }
        if ( tmpS.startsWith("+") ) tmpS = tmpS.substring(2);
        return tmpS;
    }
    
    //add nonstatic methods
    //CHECK!!!
    public static Polynomial add(Polynomial p1, Polynomial p2) {
        Polynomial pr = new Polynomial(p1.getHashMap()); //polynomial result
        p2.keySet().stream().forEach((i) -> {
            if ( pr.containsKey(i) ) {
                pr.replace(i, pr.get(i).add(p2.get(i)));
            } else {
                pr.put(i, p2.get(i));
            }
        });
        return pr;
    }
    
    //CHECK!!!
    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
        Polynomial pr = new Polynomial();   //polynomial result
        p1.keySet().stream().forEach((i) -> {
            p2.keySet().stream().forEach((j) -> {
                if ( pr.containsKey(i + j) ) {
                    pr.replace(i + j, pr.get(i + j).add(p1.get(i).multiply(p2.get(j))));
                } else {
                    pr.put(i + j, p1.get(i).multiply(p2.get(j)));
                }
            });
        });
        return pr;
    }
    
    //not yet implemendet
    public static Pair<Polynomial, Polynomial> divide(Polynomial p1, Polynomial p2) {
        
    }

}
