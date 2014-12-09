package net.marpirk.dev.kalkwielomianow;

import net.marpirk.dev.kalkwielomianow.i18n.i18n;

/**
 * Fraction class.
 * 
 * @author Marek Pikuła 
 */
public class Fraction {
    
    /** Numerator in Fraction. */
    protected long numerator;
    /** Denominator in Fraction. */
    protected long denominator;
    
    /** Creates Fraction=1/1. */
    public Fraction() {
        set(1, 1);
    }
    
    /**
     * Creates Fraction with denominator=1.
     * @param numerator Fraction numerator
     */
    public Fraction(double numerator) {
        set(numerator, 1);
    }
    
    /**
     * Creates Fraction with given numerator and denominator.
     * @param numerator Fraction numerator
     * @param denominator Fraction denominator
     * @throws ArithmeticException thrown when denominator is 0
     */
    public Fraction(double numerator, double denominator) throws ArithmeticException {
        set(numerator, denominator);
    }
    
    /**
     * Creates Fraction basing on given Fraction.
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
        if ( denominator < 0 ) {    //in case of for example: -2/-25 (to 2/25) or 2/-25 (to -2/25)
            numerator *= -1;
            denominator *= -1;
        }
        long gcd = A.gcd(numerator, denominator);
        if ( gcd > 1 ) {
            numerator = Math.round(numerator / gcd);        //round is pretty much unneeded, but Java needs it
            denominator = Math.round(denominator / gcd);
        }
        return this;
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
     */
    public double getValue() {
        return numerator/denominator;
    }
    
    /**
     * Returns exact value of Fraction division rounded to given decimal places
     * count.
     * @param decimalPlaces count of decimal places to round to
     * @return exact value of Fraction
     */
    public double getValue(int decimalPlaces) {
        return Math.round((numerator * (Math.pow(10, decimalPlaces)))
                    /
                    (denominator * (Math.pow(10, decimalPlaces))))
                / Math.pow(10, decimalPlaces);
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
    public final Fraction set(double numerator, double denominator) throws ArithmeticException {
        if ( denominator == 0 ) throw new ArithmeticException(i18n.base.getString("DIVISION_BY_0") + " " + i18n.base.getString("IN_FRACTION") + " " + numerator + "/" + denominator);
        int decPlaces = checkDecPlaces(numerator, denominator);
        this.numerator = Math.round(numerator * Math.pow(10, decPlaces));       //round is pretty much unneeded, but Java needs it
        this.denominator = Math.round(denominator * Math.pow(10, decPlaces));
        return check();
    }
    
    /**
     * Returns Fraction numerator.
     * @return numerator
     */
    public long getNumerator() {
        return numerator;
    }
    
    /**
     * Sets Fraction numerator and checks it.
     * @param numerator Fraction numerator
     * @return checked and reduced Fraction
     * @see set(double numerator, double denumerator)
     */
    public Fraction setNumerator(double numerator) {
        return set(numerator, this.denominator);
    }
    
    /**
     * Returns Fraction denominator.
     * @return denominator
     */
    public long getDenominator() {
        return denominator;
    }
    
    /**
     * Sets Fraction denominator and checks it.
     * @param denominator Fraction denominator
     * @return checked and reduced Fraction
     * @see set(double numerator, double denumerator)
     */
    public Fraction setDenominator(double denominator) {
        return set(this.numerator, denominator);
    }
    
    /**
     * Returns Fraction as String.
     * @return Fraction in String format <i>numerator</i>/<i>denominator</i>
     */
    @Override
    public String toString() {
        return toString(false);
    }
    
    public String toString(boolean exact) {
        return (exact ? getValue() + "" : numerator + ( denominator != 1 ? "/" + getDenominator() : "" ) );
    }
    
    public String toString(boolean exact, int decimalPlaces) {
        return (exact ? getValue(decimalPlaces) + "" : toString() );
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
