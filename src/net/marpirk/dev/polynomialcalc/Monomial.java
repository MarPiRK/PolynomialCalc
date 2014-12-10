package net.marpirk.dev.polynomialcalc;

import java.util.Arrays;
import java.util.HashMap;

import net.marpirk.dev.polynomialcalc.A.Pair;
import net.marpirk.dev.polynomialcalc.exceptions.MonomialPowerMismatchException;
import net.marpirk.dev.polynomialcalc.i18n.i18n;

/**
 *
 * @author Marek Pikuła
 */
public class Monomial extends HashMap<String, Fraction> {
    
    public int power; //wykładnik
    
    public Monomial() {
        super();
    }
    
    public Monomial(Monomial j) {
        super(j);
        this.power = j.power;
    }
    
    public Monomial(String toParse, int power) {
        super();
        this.power = power;
        
        for ( char c : toParse.toCharArray() ) {
            //not yet implemendet
        }
    }
    
    @Override
    public String toString() {
        return toString(true);
    }
    
    public String toString(boolean spacesOperators) {
        return toString(spacesOperators, false);
    }
    
    public String toString(boolean spacesOperators, boolean exact) {
        if ( size() == 0 ) return "";
        String tmpS = "";
        String frac;
        for ( String s : keySet() ) {
            frac = get(s).toString(exact);
            if ( spacesOperators ) {
                frac = ( frac.startsWith("-") ? "- " + frac.substring(1) : "+ " + frac );
            } else {
                if ( !frac.startsWith("-") ) frac = "+" + frac;
            }
            tmpS += " " + frac;
        }
        tmpS = tmpS.substring(1);
        if ( tmpS.startsWith("+") ) {
            tmpS = spacesOperators ? tmpS.substring(2) : tmpS.substring(1);
        }
        return tmpS;
    }
    
    public Monomial add(Monomial m2) throws MonomialPowerMismatchException {
        return add(this, m2);
    }
    
    public static Monomial add(Monomial m1, Monomial m2) throws MonomialPowerMismatchException {
        if ( m1.power != m2.power ) throw new MonomialPowerMismatchException(i18n.base.getString("OPERATION_ADD"), i18n.ex.getString("MONOMIAL_POWER_REASON_ADD"), m1.power, m2.power);
        Monomial mr = new Monomial(m1); //monomial result
        m2.keySet().stream().forEach((i) -> {
            if ( mr.containsKey(i) ) {
                mr.replace(i, mr.get(i).add(m2.get(i)));
            } else {
                mr.put(i, m2.get(i));
            }
        });
        return mr;
    }
    
    public Monomial multiply(Monomial m2) {
        return multiply(this, m2);
    }
    
    //not yet implemendet
    public static Monomial multiply(Monomial m1, Monomial m2) {
        Monomial mr = new Monomial();   //polynomial result
        m1.keySet().stream().forEach((s1) -> {
            m2.keySet().stream().forEach((s2) -> {
                String key = setAlphabetically(s1 + s2);
                if ( mr.containsKey(key) ) {
                    mr.replace(key, mr.get(key).add(m1.get(s1).multiply(m2.get(s2))));
                } else {
                    mr.put(key, m1.get(s1).multiply(m2.get(s2)));
                }
            });
        });
        mr.power = m1.power + m2.power;
        return mr;
    }
    
    protected static String setAlphabetically(String s) {
        char[] c = s.toCharArray();
        Arrays.sort(c);
        return String.copyValueOf(c);
    }
    
    public Pair<Monomial, Monomial> divide(Monomial m2) {
        return divide(this, m2);
    }
    
    //not yet implemendet
    public static Pair<Monomial, Monomial> divide(Monomial m1, Monomial m2) {
        
    }

}
