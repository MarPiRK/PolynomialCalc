package net.marpirk.dev.kalkwielomianow;

import java.util.HashMap;

import net.marpirk.dev.kalkwielomianow.A.Pair;

/**
 *
 * @author Marek Pikuła
 */
public class Monomial extends HashMap<String, Fraction> {
    
    public Integer power; //wykładnik
    
    public Monomial(String toParse, Integer wykł) {
        super();
        this.power = wykł;
        
        for ( char c : toParse.toCharArray() ) {
            //not yet implemendet
        }
    }
    
    @Override
    public String toString() {
        return toString(true);
    }
    
    public String toString(boolean spacesOperators) {
        
    }
    
    public Monomial add(Monomial m2) {
        return add(this, m2);
    }
    
    //not yet implemendet
    public static Monomial add(Monomial m1, Monomial m2) {
        
    }
    
    public Monomial multiply(Monomial m2) {
        return multiply(this, m2);
    }
    
    //not yet implemendet
    public static Monomial multiply(Monomial m1, Monomial m2) {
        
    }
    
    public Pair<Monomial, Monomial> divide(Monomial m2) {
        return divide(this, m2);
    }
    
    //not yet implemendet
    public static Pair<Monomial, Monomial> divide(Monomial m1, Monomial m2) {
        
    }

}
