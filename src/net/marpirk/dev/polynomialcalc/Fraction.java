package net.marpirk.dev.polynomialcalc;

import java.util.HashMap;
import java.util.Set;

import net.marpirk.dev.polynomialcalc.exceptions.DivisionByZeroFractionException;
import net.marpirk.dev.polynomialcalc.exceptions.NotNumberFractionException;
import net.marpirk.dev.polynomialcalc.i18n.i18n;

/**
 * Fraction class.
 * 
 * @author Marek Pikuła 
 */
public class Fraction {
    
    /** Numerator in Fraction. */
    protected HashMap<String, Long> numerator;
    /** Denominator in Fraction. */
    protected HashMap<String, Long> denominator;
    
    /** Creates number Fraction = 1/1. */
    public Fraction() {
        try { set(1, 1);
        } catch (DivisionByZeroFractionException ex) { }    //will never occur
    }
    
    /**
     * Creates number Fraction with denominator = 1.
     * @param numerator Fraction number numerator
     */
    public Fraction(double numerator) {
        try { set(numerator, 1);
        } catch (DivisionByZeroFractionException ex) { }    //will never occur
    }
    
    /**
     * Creates Fraction with number denominator = 1.
     * @param numerator Fraction numerator
     */
    public Fraction(HashMap<String, Long> numerator) {
        HashMap<String, Long> den = new HashMap<>();
        den.put("", 1L);
        try { set(numerator, den);
        } catch (DivisionByZeroFractionException ex) { }    //will never occur
    }
    
    /**
     * Creates number Fraction with given double numerator and denominator.
     * @param numerator Fraction numerator
     * @param denominator Fraction denominator
     * @throws DivisionByZeroFractionException thrown when denominator is 0
     */
    public Fraction(double numerator, double denominator) throws DivisionByZeroFractionException {
        set(numerator, denominator);
    }
    
    /**
     * Creates Fraction with given numerator and denominator.
     * @param numerator Fraction numerator
     * @param denominator Fraction denominator
     * @throws DivisionByZeroFractionException thrown when all denominator elements have 0 value
     */
    public Fraction(HashMap<String, Long> numerator, HashMap<String, Long> denominator) throws DivisionByZeroFractionException {
        set(numerator, denominator);
    }
    
    /**
     * Creates Fraction basing on given Fraction
     * @param f Fraction to copy
     */
    public Fraction(Fraction f) {
        try { set(f.getNumerator(), f.getDenominator());
        } catch (DivisionByZeroFractionException ex) { }    //will never occur
    }
    
    /**
     * Checks if given Fraction kind of HashMap ({@literal <}String, Long{@literal >}) is zero.
     * @param h 
     * @return 
     */
    protected static boolean isHashMapZero(HashMap<String, Long> h) {
        if ( h.isEmpty() ) return true;
        int zeroCount = 0;
        zeroCount = h.keySet().parallelStream().filter((s) -> ( h.get(s) == 0 )).map((_item) -> 1).reduce(zeroCount, Integer::sum);
        return (zeroCount == h.size());
    }
    
    /**
     * Reduces Fraction and checks signs (just for esthetic).
     * @return reduced Fraction
     */
    public final Fraction check() throws DivisionByZeroFractionException {
        //removes unneeded (zero) elements
        Set<String> keySet = numerator.keySet();
        keySet.parallelStream().filter((s) -> ( numerator.get(s) == 0 )).forEach((s) -> {
            numerator.remove(s);
        });
        
        keySet = denominator.keySet();
        keySet.parallelStream().filter((s) -> ( denominator.get(s) == 0 )).forEach((s) -> {
            denominator.remove(s);
        });
        
        if ( numerator.isEmpty() ) {
            denominator.clear();        //just to save memory
            denominator.put("", 1L);
        } else if ( denominator.isEmpty() ) throw new DivisionByZeroFractionException(partToString(numerator, false, false, true), partToString(denominator, false, false, true));
        
        //check if changing sign is necessary
        boolean changeSign = false;
        if ( isNumberFraction() && denominator.get("") < 0 ) {  //in case of for example: -2/-25 (to 2/25) or 2/-25 (to -2/25)
            changeSign = true;
        } else {    //checks denominator - if there is more minuses than pluses then it changes sign (only visual aspect)
            int denPlusCount = 0;
            denPlusCount = denominator.values().parallelStream().filter((l) -> ( l > 0 )).map((_item) -> 1).reduce(denPlusCount, Integer::sum); //thanks NetBeans - if you did it wrong I'll be very angry!!!
            if ( denPlusCount < denominator.values().size() - denPlusCount ) changeSign = true;
        }
        
        //check greatest common divisor to reduce
        long gcd = (Long) numerator.values().toArray()[0];
        for (Long l : numerator.values()) {
            gcd = A.gcd(gcd, l);
        }
        for (Long l : denominator.values()) {
            gcd = A.gcd(gcd, l);
        }
        if ( gcd > 1 ) {
            for ( String s : numerator.keySet() ) {
                numerator.replace(s, ( changeSign ? -1 : 1 ) * numerator.get(s) / gcd);
            }
            for ( String s : denominator.keySet() ) {
                denominator.replace(s, ( changeSign ? -1 : 1 ) * denominator.get(s) / gcd);
            }
        }
        return this;
    }
    
    public final boolean isNumberFraction() {
        return numerator.size() == 1 && numerator.containsKey("") && denominator.size() == 1 && denominator.containsKey("");
    }
    
    /**
     * Returns biggest count of decimal places between given numerator
     * and denominator.
     * @param numerator Fraction numerator
     * @param denominator Fraction denominator
     * @return biggest decimal places count
     */
    protected final int checkDecPlaces(double numerator, double denominator) {
        int cDecPlaces, dDecPlaces;
        if ( numerator != (int) numerator ) {
            String text = Double.toString(Math.abs(numerator));
            cDecPlaces = text.length() - text.indexOf('.') - 1;
        } else {
            cDecPlaces = 0;
        }
        if ( denominator != (int) denominator ) {
            String text = Double.toString(Math.abs(denominator));
            dDecPlaces = text.length() - text.indexOf('.') - 1;
        } else {
            dDecPlaces = 0;
        }
        if ( cDecPlaces >= dDecPlaces ) {
            return cDecPlaces;
        } else {
            return dDecPlaces;
        }
    }
    
    /**
     * Returns exact value of Fraction division.
     * @return exact value of Fraction
     * @throws NotNumberFractionException thrown if Fraction isn't number Fraction
     */
    public double getValue() throws NotNumberFractionException {
        if ( !isNumberFraction() ) throw new NotNumberFractionException(i18n.ex.getString("NOT_NUMBER_FRACTION_GETVALUE"), this);
        return numerator.get("")/denominator.get("");
    }
    
    /**
     * Returns exact value of Fraction division rounded to given decimal places count.
     * @param decimalPlaces count of decimal places to round to
     * @return exact value of Fraction
     * @throws NotNumberFractionException thrown if Fraction isn't number Fraction
     */
    public double getValue(int decimalPlaces) throws NotNumberFractionException {
        if ( !isNumberFraction() ) throw new NotNumberFractionException(i18n.ex.getString("NOT_NUMBER_FRACTION_GETVALUE"), this);
        return Math.round((numerator.get("") * (Math.pow(10, decimalPlaces)))
                    /
                    (denominator.get("") * (Math.pow(10, decimalPlaces))))
                / Math.pow(10, decimalPlaces);
    }
    
    
    
    public final Fraction set(double numerator, double denominator) throws DivisionByZeroFractionException {
        if ( denominator == 0 ) throw new DivisionByZeroFractionException(numerator + "");
        
        //extend to remove comma in num/den
        int decPlaces = checkDecPlaces(numerator, denominator);
        
        HashMap<String, Long> num = new HashMap<>();
        num.put("", Math.round(numerator * Math.pow(10, decPlaces)));       //round is pretty much unneeded, but Java needs it
        
        HashMap<String, Long> den = new HashMap<>();
        den.put("", Math.round(denominator * Math.pow(10, decPlaces)));
        
        return set(num, den);
    }
    
    /**
     * Sets value of Fraction basing on given rational numerator
     * and denominator.
     * @param numerator Fraction numerator
     * @param denominator Fraction denominator
     * @return checked and reduced Fraction
     * @throws DivisionByZeroFractionException thrown when denominator = 0
     * @see check()
     */
    public final Fraction set(HashMap<String, Long> numerator, HashMap<String, Long> denominator) throws DivisionByZeroFractionException {
        this.numerator = numerator;
        this.denominator = denominator;
        return check();
    }
    
    /**
     * Returns Fraction numerator.
     * @return numerator
     */
    public HashMap<String, Long> getNumerator() {
        return numerator;
    }
    
    /**
     * Sets Fraction numerator and checks it.
     * @param numerator Fraction numerator
     * @return checked and reduced Fraction
     * @see set(double numerator, double denumerator)
     */
    public Fraction setNumerator(HashMap<String, Long> numerator) {
        try { return set(numerator, this.denominator);
        } catch (DivisionByZeroFractionException ex) { return null; }   //will never occur
    }
    
    /**
     * Returns Fraction denominator.
     * @return denominator
     */
    public HashMap<String, Long> getDenominator() {
        return denominator;
    }
    
    /**
     * Sets Fraction denominator and checks it.
     * @param denominator Fraction denominator
     * @return checked and reduced Fraction
     * @see set(double numerator, double denumerator)
     */
    public Fraction setDenominator(HashMap<String, Long> denominator) throws DivisionByZeroFractionException {
        return set(this.numerator, denominator);
    }
    
    @Override
    public String toString() {
        return toString(false, true, false, true, '/');
    }
    
    protected static String partToString(HashMap<String, Long> m, boolean operator, boolean one, boolean spaces) {
        String r = "";
        Long num;
        for ( String s : m.keySet() ) {
            num = m.get(s);
            if ( operator ) r = ( num >= 0 ? "+" : "-" );
                else        r = ( num >=0 ? "" : "-" );
            if ( spaces && r.length() > 0 ) r += " ";
            if ( (one) || (num != 1 && num != -1) ) r += num;
            if ( spaces && r.length() > 0 ) r += " ";
            r += s + ( spaces ? " " : "" );
        }
        return r;
    };
    
    public String toString(boolean exact, boolean operator, boolean one, boolean spaces, char divisionChar) {
        String op = ( spaces ? ' ' + divisionChar + ' ' : divisionChar ) + "";
        if ( isNumberFraction() ) {
            if ( exact ) {
                try { return getValue() + ""; } catch (NotNumberFractionException ex) { return null; } //will never occur - on the beginning is isNumberFraction check
            } else {
                long num = numerator.get("");
                if ( num == 0 ) return "0";
                
                long den = denominator.get("");     //will never be lower than 0
                
                String numS, denS;
                if ( operator ) {
                    numS = ( num >=0 ? "+" : "-" );
                    denS = ( den >=0 ? "+" : "" );
                } else {
                    numS = ( num >=0 ? "" : "-" );
                    denS = "";
                }
                if ( spaces ) {
                    if ( numS.length() > 0 ) numS += " ";
                    if ( denS.length() > 0 ) denS += " ";
                }
                if ( one ) {
                    if ( (num != 1 && num != -1) || (num == 1 && num == -1 && den != 1) ) numS += num;   //if denominator is not 1 and numerator is +-1 then fraction would be like this: -1/123 or 1/23 - not -/123 or /23
                    if ( den != 1 ) denS += den;
                }
                return numS + ( den != 1 ? op + denS : "" );
            }
        } else {
            if ( numerator.isEmpty() ) return "0";
            
            String space = ( spaces ? " " : "" );
            
            String numS = partToString(numerator, operator, one, spaces);
                
            //if denominator map is small it's not necessary to check it like numerator
            if ( denominator.isEmpty() || (denominator.size() == 1 && denominator.containsKey("") && denominator.get("") == 1) ) {
                return numS;
            } else if (denominator.size() == 1 && denominator.containsKey("")) {
                return '(' + space + numS + space + ')' + op + denominator.get("");
            }
            
            String denS = partToString(denominator, operator, one, spaces);
            
            return '(' + space + numS + space + ')' + op + '(' + space + denS + space + ')';
        }
    }
    
    /**
     * Returns exact value of number fraction with spectified number of decimal places.
     * @param decimalPlaces number of decimal places to round to
     * @return exact value of number fraction
     * @throws NotNumberFractionException thrown if faction is not number fraction
     */
    public String toString(int decimalPlaces) throws NotNumberFractionException {
        return getValue(decimalPlaces) + "";
    }
    
    public void inverse() throws DivisionByZeroFractionException {
        set(denominator, numerator);
    }
    
    public static Fraction inverse(Fraction f) throws DivisionByZeroFractionException {
        return new Fraction(f.getDenominator(), f.getNumerator());
    }
    
    protected static HashMap<String, Long> addHashMaps(HashMap<String, Long> h1, HashMap<String, Long> h2){
        HashMap<String, Long> hr = new HashMap<>(h1);
        h2.keySet().parallelStream().forEach((k) -> {
            if ( hr.containsKey(k) ) {
                hr.replace(k, hr.get(k) + h2.get(k));
            } else {
                hr.put(k, h2.get(k));
            }
        });
        return hr;
    }
    
    public Fraction add(Fraction f2) {
        return add(this, f2);
    }
    
    public static Fraction add(Fraction f1, Fraction f2) {
        try {
            return new Fraction(
                    addHashMaps(
                            multiplyHashMaps(
                                    f1.getNumerator(),
                                    f2.getDenominator()
                            ),
                            multiplyHashMaps(
                                    f2.getNumerator(),
                                    f1.getDenominator()
                            )
                    ),
                    multiplyHashMaps(
                            f1.getDenominator(),
                            f2.getDenominator()
                    )
            );
        } catch (DivisionByZeroFractionException ex) { return null; }   //will never occur
    }
    
    protected static HashMap<String, Long> multiplyHashMaps(HashMap<String, Long> h1, HashMap<String, Long> h2) {
        HashMap<String, Long> hr = new HashMap<>();
        String sr;
        for ( String k1 : h1.keySet() ) {
            for ( String k2 : h2.keySet() ) {
                sr = A.sortStringChars(k1 + k2);
                if ( hr.containsKey(sr) ) {
                    hr.replace(sr, hr.get(sr) + h1.get(k1) * h2.get(k2));
                } else {
                    hr.put(sr, h1.get(k1) * h2.get(k2));
                }
            }
        }
        return hr;
    }
    
    public Fraction multiply(Fraction f2) {
        return multiply(this, f2);
    }
    
    public static Fraction multiply(Fraction f1, Fraction f2) {
        try {
            return new Fraction(
                    multiplyHashMaps(
                            f1.numerator,
                            f2.numerator
                    ),
                    multiplyHashMaps(
                            f1.denominator,
                            f2.denominator
                    )
            );
        } catch (DivisionByZeroFractionException ex) { return null; }   //will never occur
    }
    
    public Fraction divide(Fraction f2) throws DivisionByZeroFractionException {
        return divide(this, f2);
    }
    
    public static Fraction divide(Fraction f1, Fraction f2) throws DivisionByZeroFractionException {
        return multiply(f1, inverse(f2));
    }

}
