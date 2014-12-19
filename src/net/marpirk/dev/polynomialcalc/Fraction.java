package net.marpirk.dev.polynomialcalc;

import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

import net.marpirk.dev.polynomialcalc.A.Pair;
import net.marpirk.dev.polynomialcalc.exceptions.DivisionByZeroFractionException;
import net.marpirk.dev.polynomialcalc.exceptions.CannotPerformOperationFractionException;
import net.marpirk.dev.polynomialcalc.exceptions.ParamParseException;
import net.marpirk.dev.polynomialcalc.i18n.i18n;

/**
 * Fraction class.
 * 
 * @author Marek Pikuła 
 */
public class Fraction {
    
    /** Numerator in Fraction. */
    protected HashMap<String, Long> numerator = new HashMap<>();
    /** Denominator in Fraction. */
    protected HashMap<String, Long> denominator = new HashMap<>();
    
    /** Creates number Fraction = 1/1. */
    public Fraction() {
        try { set(1, 1);
        } catch (DivisionByZeroFractionException ex) { }    //will never occur
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
     * Creates Fraction from given string.
     * @param toParse parse text
     * @throws ParamParseException thrown when in param was found wrong character
     * @throws DivisionByZeroFractionException thrown when denominator = 0
     */
    public Fraction(String toParse) throws ParamParseException, DivisionByZeroFractionException {
        HashMap<String, Long> num = new HashMap<>();
        HashMap<String, Long> den = new HashMap<>();
        
        Function<Boolean, HashMap<String, Long>> getPart = nd -> {
            if ( nd ) return num;
                else  return den;
        };
        
        class Element {
            public boolean op = true;   //operator: + is true; - is false; default +
            public String number = "";
            public String key = "";
        }
        
        Element e = new Element();
        boolean nd = true;  //numerator is true; denominator is false
        
        BiFunction<Element, Boolean, Element> add = (a, nominatordenominator) -> {
            HashMap<String, Long> cPart = getPart.apply(nominatordenominator);
            if ( cPart.containsKey(a.key) ) {
                cPart.replace(a.key, cPart.get(a.key) + Long.parseLong(( a.op ? '+' : '-' ) + a.number));
                } else {
                cPart.put(a.key, Long.parseLong(( a.op ? '+' : '-' ) + a.number));
            }
            return new Element();
        };
        
        for ( char c : toParse.toCharArray() ) {
            if ( c == '+' || c == '-' ) {
                if ( !getPart.apply(nd).isEmpty() ) {
                    e = add.apply(e, nd);
                }
                e.op = ( c == '+' );
            } else if ( A.isInteger(c + "") ) {
                e.number += c;
            } else if ( Character.isAlphabetic(c) ) {
                e.key += c;
            } else if ( c == '/' ) {
                if ( getPart.apply(nd).isEmpty() ) {
                    throw new ParamParseException(toParse, c + "" , i18n.ex.getString("PARAM_PARSE_NUM_NOT_FOUND"));
                } else {
                    e = add.apply(e, nd);
                }
                nd = !nd;
            } else if ( c == ' ' ) {    //ommit spaces
            } else {
                throw new ParamParseException(toParse, c + "", i18n.ex.getString("PARAM_PARSE_WRONG_CHARACTER"));
            }
        }
        add.apply(e, nd);
        if ( den.isEmpty() ) den.put("", 1L);
        set(num, den);
    }
    
    //setting and checking methods
    /**
     * Creates number Fraction from given rational values.
     * @param numerator Fraction numerator
     * @param denominator Fraction denominator
     * @return checked and reduced Fraction
     * @throws DivisionByZeroFractionException thrown when denominator = 0
     * @see check()
     */
    public final Fraction set(double numerator, double denominator) throws DivisionByZeroFractionException {
        if ( denominator == 0 ) throw new DivisionByZeroFractionException(numerator + "");
        
        //extend to remove comma in num/den
        int decPlaces = A.compareDecPlaces(numerator, denominator);
        
        HashMap<String, Long> num = new HashMap<>();
        num.put("", Math.round(numerator * Math.pow(10, decPlaces)));       //round is pretty much unneeded, but Java needs it
        
        HashMap<String, Long> den = new HashMap<>();
        den.put("", Math.round(denominator * Math.pow(10, decPlaces)));
        
        return set(num, den);
    }
    
    /**
     * Sets value of Fraction basing on preparsed values.
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
     * Removes unneeded elements, reduces Fraction and checks signs (just for esthetic).
     * @return reduced Fraction
     * @throws DivisionByZeroFractionException thrown when denominator = 0
     */
    public final Fraction check() throws DivisionByZeroFractionException {
        //removes unneeded (zero) elements
        Consumer<HashMap<String, Long>> removeUnneeded = hm -> {
            String[] keySet = (String[]) hm.keySet().toArray(new String[hm.keySet().size()]);
            for ( String s : keySet ) {
                if ( hm.get(s) == 0 ) {
                    numerator.remove(s);
                }
            }
        };
        removeUnneeded.accept(numerator);
        removeUnneeded.accept(denominator);
        
        if ( numerator.isEmpty() ) {
            denominator.clear();        //just to remove junk
            denominator.put("", 1L);
        } else if ( denominator.isEmpty() ) throw new DivisionByZeroFractionException(partToString(numerator, false, false, true));
        
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
        long gcd = A.gcd(A.getIterableGCD(numerator.values()), A.getIterableGCD(denominator.values()));
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

    /**
     * Checks if Fraction has only number elements.
     * @return
     */
    public final boolean isNumberFraction() {
        return numerator.size() == 1 && numerator.containsKey("") && denominator.size() == 1 && denominator.containsKey("");
    }
    
    /**
     * Checks if Fraction is actually whole number
     * @param numberFraction does it have to check if fraction is number ({@link isNumberFraction()})
     * @return
     */
    public final boolean isWholeNumber(boolean numberFraction) {
        if ( numberFraction ) {
            return isNumberFraction() && denominator.get("") == 1;
        } else {
            int wholeCount = 0;
            wholeCount = denominator.keySet().parallelStream().filter((s) -> ( denominator.get(s) == 1 )).map((_item) -> 1).reduce(wholeCount, Integer::sum);
            return wholeCount == denominator.size();
        }
    }
    
    /**
     * Throws CannotPerformOperationException if Fraction is not whole number with given i18n prefix.
     * @param f Fraction to check
     * @param i18nprefix prefix to i18n key name for message
     * @throws CannotPerformOperationFractionException 
     */
    protected final void throwNotWholeNumber(Fraction f, String i18nprefix) throws CannotPerformOperationFractionException {
        if ( !f.isWholeNumber(false) ) throw new CannotPerformOperationFractionException(i18nprefix, f);
    }
    
    /**
     * 
     * @return 
     */
    public final HashMap<String, Long> extractWhole() {
        throw new UnsupportedOperationException();
    }
    
    public final boolean isZero() {
        return (numerator.isEmpty()   || numerator.get("") == 0);
    }
    
    //getting values    
    /**
     * Returns Fraction numerator.
     * @return 
     */
    public HashMap<String, Long> getNumerator() {
        return numerator;
    }
    
    /**
     * Sets Fraction numerator and checks Fraction.
     * @param numerator Fraction numerator
     * @return checked and reduced Fraction
     * @see set(double numerator, double denominator) Fraction.set(double, double)
     */
    public Fraction setNumerator(HashMap<String, Long> numerator) {
        try { return set(numerator, this.denominator);
        } catch (DivisionByZeroFractionException ex) { return null; }   //will never occur
    }
    
    /**
     * Returns Fraction denominator.
     * @return 
     */
    public HashMap<String, Long> getDenominator() {
        return denominator;
    }
    
    /**
     * Sets Fraction denominator and checks Fraction.
     * @param denominator Fraction denominator
     * @return checked and reduced Fraction
     * @throws DivisionByZeroFractionException thrown when denominator = 0
     * @see set(double numerator, double denumerator) Fraction.set(double, double)
     */
    public Fraction setDenominator(HashMap<String, Long> denominator) throws DivisionByZeroFractionException {
        return set(this.numerator, denominator);
    }
    
    /**
     * Returns exact value of Fraction division.
     * @return 
     * @throws CannotPerformOperationFractionException thrown if Fraction isn't number Fraction
     */
    public double getValue() throws CannotPerformOperationFractionException {
        if ( !isNumberFraction() ) throw new CannotPerformOperationFractionException("CPOF_NOT_NUMBER_FRACTION", this);
        return numerator.get("")/denominator.get("");
    }
    
    /**
     * Returns exact value of Fraction division rounded to given decimal places count.
     * @param decimalPlaces count of decimal places to round to
     * @return exact value of Fraction
     * @throws CannotPerformOperationFractionException thrown if Fraction isn't number Fraction
     */
    public double getValue(int decimalPlaces) throws CannotPerformOperationFractionException {
        if ( !isNumberFraction() ) throw new CannotPerformOperationFractionException("CPOF_NOT_NUMBER_FRACTION_OP", this);
        return Math.round((numerator.get("") * (Math.pow(10, decimalPlaces)))
                    /
                    (denominator.get("") * (Math.pow(10, decimalPlaces))))
                / Math.pow(10, decimalPlaces);
    }
    
    //toString
    /**
     * Returns a string representation of Fraction with defaults:
     * <ul>
     * <li>not exact</li>
     * <li>with leading operators</li>
     * <li>without unneeded ones</li>
     * <li>with spaces</li>
     * <li>with '/' division char</li>
     * </ul>
     * @return 
     * @see toString(boolean, boolean, boolean, boolean, char)
     */
    @Override
    public String toString() {
        return toString(false, true, false, true, '/');
    }
    
    /**
     * Returns string representation of Fraction.
     * @param exact exact value or fraction
     * @param operator show leading operator (+)
     * @param one show every one - even when not needed (for example 1a or a)
     * @param spaces divide string elements with spaces
     * @param divisionChar char between numerator and doneminator
     * @return 
     */
    public String toString(boolean exact, boolean operator, boolean one, boolean spaces, char divisionChar) {
        String op = ( spaces ? " " + divisionChar + " " : divisionChar ) + "";
        if ( isNumberFraction() ) {
            if ( exact ) {
                try { return getValue() + ""; } catch (CannotPerformOperationFractionException ex) { return null; } //will never occur - on the beginning is isNumberFraction check
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
                if ( !one ) {
                    if ( (num != 1 && num != -1) || (num == 1 && num == -1 && den != 1) ) numS += Math.abs(num);   //if denominator is not 1 and numerator is +-1 then fraction would be like this: -1/123 or 1/23 - not -/123 or /23
                    if ( den != 1 ) denS += Math.abs(den);
                } else {
                    numS += Math.abs(num);
                    denS += Math.abs(den);
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
     * @return 
     * @throws CannotPerformOperationFractionException thrown when Fraction is not number Fraction
     * @see getValue(int)
     */
    public String toString(int decimalPlaces) throws CannotPerformOperationFractionException {
        return getValue(decimalPlaces) + "";
    }
    
    /**
     * Transforms part of fraction (numerator or denominator).
     * @param m HashMap with values
     * @param operator show leading operator (+)
     * @param one show every one - even ehn not needed (for example 1a or a)
     * @param spaces divide string elements with spaces
     * @return 
     */
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
    
    //operations on Fractions
    //inverse
    /**
     * Inverses Fraction.
     * @throws DivisionByZeroFractionException thrown when numerator = 0
     */
    public void inverse() throws DivisionByZeroFractionException {
        set(denominator, numerator);
    }
    
    /**
     * Inverses given Fraction.
     * @param f Fraction to invert
     * @return
     * @throws DivisionByZeroFractionException thrown when numerator = 0
     */
    public static Fraction inverse(Fraction f) throws DivisionByZeroFractionException {
        return new Fraction(f.getDenominator(), f.getNumerator());
    }
    
    //add
    /**
     * Adds two hash maps.
     * @param h1 first HashMap
     * @param h2 second HashMap
     * @return 
     */
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
    
    /**
     * Adds to this Fraction.
     * @param f2 Fraction to add
     * @return new Fraction = this + f2
     */
    public Fraction add(Fraction f2) {
        return add(this, f2);
    }
    
    /**
     * Adds Fractions.
     * @param f1 first Fraction
     * @param f2 second Fraction
     * @return new Fraction = f1 + f2
     */
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
    
    //multiply
    /**
     * Multiplies two hash maps.
     * @param h1 first HashMap
     * @param h2 second HashMap
     * @return 
     */
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
    
    /**
     * Multiplies this Fraction.
     * @param f2 Fraction to multiply with
     * @return new Fraction = this * f2
     */
    public Fraction multiply(Fraction f2) {
        return multiply(this, f2);
    }
    
    /**
     * Multiplies Fractions.
     * @param f1 first Fraction
     * @param f2 second Fraction
     * @return new Fraction = f1 * f2
     */
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
    
    //divide
    /**
     * Divides this Fraction without rest.
     * @param f2 Fraction to divide with
     * @return new Fraction = this / f2
     * @throws DivisionByZeroFractionException thrown when nominator of given Fraction = 0
     */
    public Fraction divide(Fraction f2) throws DivisionByZeroFractionException {
        return divide(this, f2);
    }
    
    /**
     * Divides Fractions without rest.
     * @param f1 first Fraction
     * @param f2 second Fraction
     * @return new Fraction = f1 / f2
     * @throws DivisionByZeroFractionException thrown when nominator of second Fraction = 0
     */
    public static Fraction divide(Fraction f1, Fraction f2) throws DivisionByZeroFractionException {
        return multiply(f1, inverse(f2));
    }
    
    //divide with rest
    /**
     * Divides this Fraction with rest.
     * @param f2 Fraction to divide with
     * @return Pair of result and rest
     * @throws CannotPerformOperationFractionException thrown when Fraction is not whole number
     * @throws DivisionByZeroFractionException thrown when given Fraction = 0
     */
    public Pair<HashMap<String, Long>, HashMap<String, Long>> rdivide(Fraction f2) throws CannotPerformOperationFractionException, DivisionByZeroFractionException {
        return rdivide(this, f2);
    }
    
    /**
     * Divides Fractions with rest.
     * @param f1 first Fraction
     * @param f2 second Fraction
     * @return Pair of result and rest
     * @throws CannotPerformOperationFractionException thrown when Fraction is not whole number
     * @throws DivisionByZeroFractionException thrown when f2 = 0
     */
    public Pair<HashMap<String, Long>, HashMap<String, Long>> rdivide(Fraction f1, Fraction f2) throws CannotPerformOperationFractionException, DivisionByZeroFractionException {
        throwNotWholeNumber(f1, "CPOF_RDIVIDE");
        throwNotWholeNumber(f2, "CPOF_RDIVIDE");
        Fraction f = divide(f1, f2);
        long multiplier = f.getDenominator().get(f.getDenominator().keySet().iterator().next());
        
        HashMap<String, Long> whole = f.extractWhole();
        
        HashMap<String, Long> tmpHM = new HashMap<>();
        tmpHM.put("", -1L);
        
        Fraction rest = f.add(new Fraction(whole, tmpHM));
        multiplier = multiplier / rest.getDenominator().get(rest.getDenominator().keySet().iterator().next());
        
        tmpHM = new HashMap<>();
        tmpHM.put("", multiplier);
        
        return new Pair<>(whole, multiplyHashMaps(f.getNumerator(), tmpHM));
    }
    
    
    //other
    /**
     * Checks if given Fraction kind of HashMap ({@literal <}String, Long{@literal >}) is zero.
     * @param h HashMap to check
     * @return 
     */
    protected static boolean isHashMapZero(HashMap<String, Long> h) {
        if ( h.isEmpty() ) return true;
        int zeroCount = 0;
        zeroCount = h.keySet().parallelStream().filter((s) -> ( h.get(s) == 0 )).map((_item) -> 1).reduce(zeroCount, Integer::sum);
        return (zeroCount == h.size());
    }

}
