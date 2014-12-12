package net.marpirk.dev.polynomialcalc;

import java.util.HashMap;

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
    
    /** Creates Fraction=1/1. */
    public Fraction() {
        set(1, 1);
    }
    
    /**
     * Creates number Fraction with denominator=1.
     * @param numerator Fraction numerator
     */
    public Fraction(double numerator) {
        set(numerator, 1);
    }
    
    public Fraction(HashMap<String, Long> numerator) {
        HashMap<String, Long> den = new HashMap<>();
        den.put("", 1L);
        set(numerator, den);
    }
    
    /**
     * Creates number Fraction with given numerator and denominator.
     * @param numerator Fraction numerator
     * @param denominator Fraction denominator
     * @throws ArithmeticException thrown when denominator is 0
     */
    public Fraction(double numerator, double denominator) throws ArithmeticException {
        set(numerator, denominator);
    }
    
    public Fraction(HashMap<String, Long> numerator, HashMap<String, Long> denominator) throws ArithmeticException {
        set(numerator, denominator);
    }
    
    /**
     * Creates Fraction basing on given Fraction
     * @param f Fraction to copy
     */
    public Fraction(Fraction f) {
        set(f.getNumerator(), f.getDenominator());
    }
    
    /**
     * Reduces Fraction and checks signs (just for esthetic).
     * @return reduced Fraction
     */
    public final Fraction check() {
        boolean changeSign = false;
        if ( isNumberFraction() && denominator.get("") < 0 ) {  //in case of for example: -2/-25 (to 2/25) or 2/-25 (to -2/25)
            changeSign = true;
            numerator.replace("", numerator.get("") * -1);
            denominator.replace("", denominator.get("") * -1);
        } else {    //checks denominator - if there is more minuses than pluses then it changes sign (only visual aspect)
            int denPlusCount = 0;
            denPlusCount = denominator.values().parallelStream().filter((l) -> ( l > 0 )).map((_item) -> 1).reduce(denPlusCount, Integer::sum); //thanks NetBeans - if you did it wrong I'll be very angry!!!
            if ( denPlusCount < denominator.values().size() ) {
                changeSign = true;
            }
        }
        
        //check greatest common divisor to 
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
    
    public final Fraction set(double numerator, double denominator) {
        if ( denominator == 0 ) throw new ArithmeticException(i18n.base.getString("DIVISION_BY_0") + " " + i18n.base.getString("IN_FRACTION") + " " + numerator + "/" + denominator);
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
     * @throws ArithmeticException thrown when denominator=0
     * @see check()
     */
    public final Fraction set(HashMap<String, Long> numerator, HashMap<String, Long> denominator) throws ArithmeticException {
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
        return set(numerator, this.denominator);
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
    public Fraction setDenominator(HashMap<String, Long> denominator) {
        return set(this.numerator, denominator);
    }
    
    @Override
    public String toString() {
        return toString(false, true, false, true, '/');
    }
    
    public String toString(boolean exact, boolean operator, boolean one, boolean spaces, char divisionChar) {
        String op = ( spaces ? ' ' + divisionChar + ' ' : divisionChar ) + "";
        if ( isNumberFraction() ) {
            if ( exact ) {
                try { return getValue() + ""; } catch (NotNumberFractionException ex) { } //will never occur - on the beginning is isNumberFraction check
            } else {
                long num = numerator.get("");
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
            String space = ( spaces ? " " : "" );
            String numS = "";
            //to implement
                
            //if denominator map is small it's not necessary to check it like numerator
            if ( denominator.isEmpty() || (denominator.size() == 1 && denominator.containsKey("") && denominator.get("") == 1) ) {
                return numS;
            } else if (denominator.size() == 1 && denominator.containsKey("")) {
                return '(' + space + numS + space + ')' + op + denominator.get("");
            }
            
            String denS = "";
            //to implement
            
            return '(' + space + numS + space + ')' + op + '(' + space + denominator.get("") + space + ')';
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
    
    public void inverse() {
        set(denominator, numerator);
    }
    
    public static Fraction inverse(Fraction f) {
        return new Fraction(f.getDenominator(), f.getNumerator());
    }
    
    public Fraction add(Fraction f2) {
        return add(this, f2);
    }
    
    public static Fraction add(Fraction f1, Fraction f2) {
        return new Fraction( (f1.getNumerator() * f2.getDenominator() + f2.getNumerator() * f1.getDenominator()), (f1.getDenominator() * f2.getDenominator()) );
    }
    
    public Fraction multiply(Fraction f2) {
        return multiply(this, f2);
    }
    
    public static Fraction multiply(Fraction f1, Fraction f2) {
        return new Fraction(f1.numerator * f2.numerator, f1.denominator * f2.denominator);
    }
    
    public Fraction divide(Fraction f2) {
        return divide(this, f2);
    }
    
    public static Fraction divide(Fraction f1, Fraction f2) {
        return multiply(f1, inverse(f2));
    }

}
