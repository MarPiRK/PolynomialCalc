package net.marpirk.dev.kalkwielomianow;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashMapModInterface;

import net.marpirk.dev.kalkwielomianow.A.Pair;
import net.marpirk.dev.kalkwielomianow.exceptions.ParamParseException;

/**
 *
 * @author Marek Pikuła
 */
public class Polynomial extends HashMapModInterface<Integer, Monomial> {
    
    protected Integer highest = 0;
    
    public Polynomial() {
        super();
    }
    
    public Polynomial(HashMap<Integer, Monomial> j) {
        super(j);
        check();
    }
    
    public Polynomial(ArrayList<String> j) throws NumberFormatException, ParamParseException {
        super();
        String[] tmpS;
        for ( String s : j ) {
            tmpS = s.split("|");
            if ( tmpS.length > 2 ) throw new ParamParseException(s, null, "błąd składni");
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
    public void afterNodeAccess(Node<Integer, Monomial> p) {
        if ( p.getKey() > highest ) {
            highest = p.getKey();
        }
    }
    
    @Override
    public void afterNodeInsertion(boolean evict) {
        checkHighest();
    }
    
    @Override
    public void afterNodeRemoval(Node<Integer, Monomial> p) {
        if ( p.getKey().equals(highest) ) {
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
    
    public static Polynomial add(Polynomial p1, Polynomial p2) {
        Polynomial pr = new Polynomial(p1); //polynomial result
        p2.keySet().stream().forEach((i) -> {
            if ( pr.containsKey(i) ) {
                pr.replace(i, pr.get(i).add(p2.get(i)));
            } else {
                pr.put(i, p2.get(i));
            }
        });
        return pr;
    }
    
    public static Polynomial multiply(Polynomial p1, Polynomial p2) {
        Polynomial pr = new Polynomial();   //polynomial result
        p1.keySet().forEach((i) -> {
            p2.keySet().forEach((j) -> {
                if ( pr.containsKey(i + j) ) {
                    pr.replace(i + j, pr.get(i + j).add(p1.get(i)).multiply(p2.get(j)));
                } else {
                    pr.put(i + j, p1.get(i).multiply(p2.get(j)));
                }
            });
        });
        return pr;
    }
    
    //not yet implemendet
    public static Pair<Polynomial, Polynomial> divide(Polynomial p1, Polynomial p2) {
        if ( p2.size() == 2 && p2.containsKey(1) && p2.containsKey(2) ) {   //Horner's method
            
        } else {
            
        }
    }

}
