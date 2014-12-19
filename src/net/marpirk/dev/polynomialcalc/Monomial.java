package net.marpirk.dev.polynomialcalc;

import net.marpirk.dev.polynomialcalc.A.Pair;
import net.marpirk.dev.polynomialcalc.exceptions.DivisionByZeroFractionException;
import net.marpirk.dev.polynomialcalc.exceptions.MonomialPowerMismatchException;
import net.marpirk.dev.polynomialcalc.exceptions.CannotPerformOperationFractionException;
import net.marpirk.dev.polynomialcalc.exceptions.ParamParseException;
import net.marpirk.dev.polynomialcalc.i18n.i18n;

/**
 *
 * @author Marek Pikuła
 */
public class Monomial {
    
    public Fraction coefficient;
    public int power;
    
    public Monomial() {
        this(1);
    }
    
    public Monomial(int power) {
        this.power = power;
        this.coefficient = new Fraction();
    }
    
    public Monomial(int power, Fraction coefficient) {
        this.power = power;
        this.coefficient = coefficient;
    }
    
    public Monomial(Monomial j) {
        power = j.power;
        coefficient = new Fraction(j.coefficient);
    }
    
    public Monomial(String toParse, int power) throws ParamParseException, DivisionByZeroFractionException {
        this.power = power;
        this.coefficient = new Fraction(toParse);
    }
    
    protected String xtoString(boolean spaces) {
        return ( power == 0 ? "" : ( spaces ? " " : "" ) + "x" + ( power > 1 ? "^" + power : "" ) );
    }
    
    @Override
    public String toString() {
        return coefficient.toString() + xtoString(true);
    }
    
    public String toString(int decimalPlaces, boolean spaces) throws CannotPerformOperationFractionException {
        return coefficient.toString(decimalPlaces) + xtoString(spaces);
    }
    
    /**
     * Returns string representation of Monomial.
     * @param exact exact value or fraction
     * @param operator show leading operator (+)
     * @param one show every one - even when not needed (for example 1a or a)
     * @param spaces divide string elements with spaces
     * @param divisionChar char between numerator and doneminator
     * @return 
     */
    public String toString(boolean exact, boolean operator, boolean one, boolean spaces, char divisionChar) {
        String fr = coefficient.toString(exact, operator, one, spaces, divisionChar);
        return fr + xtoString(spaces && fr.length() > 0);
    }
    
    public Monomial add(Monomial m2) throws MonomialPowerMismatchException {
        return add(this, m2);
    }
    
    public static Monomial add(Monomial m1, Monomial m2) throws MonomialPowerMismatchException {
        if ( m1.power != m2.power ) throw new MonomialPowerMismatchException(i18n.base.getString("OPERATION_ADD"), i18n.ex.getString("MONOMIAL_POWER_REASON_ADD"), m1.power, m2.power);
        return new Monomial(m1.power, m1.coefficient.add(m2.coefficient));
    }
    
    public Monomial multiply(Monomial m2) {
        return multiply(this, m2);
    }
    
    //not yet implemendet
    public static Monomial multiply(Monomial m1, Monomial m2) {
        return new Monomial(m1.power + m2.power, m1.coefficient.multiply(m2.coefficient));
    }
    
    public Monomial divide(Monomial m2) throws MonomialPowerMismatchException, DivisionByZeroFractionException {
        return divide(this, m2);
    }
    
    public static Monomial divide(Monomial m1, Monomial m2) throws MonomialPowerMismatchException, DivisionByZeroFractionException {
        if ( m1.power < m2.power) throw new MonomialPowerMismatchException(i18n.base.getString("OPERATION_DIVIDE"), i18n.ex.getString("MONOMIAL_POWER_REASON_DIVIDE"), m1.power, m2.power);
        return new Monomial(m1.power - m2.power, m1.coefficient.divide(m2.coefficient));
    }
    
    public Pair<Monomial, Monomial> rdivide(Monomial m2) throws MonomialPowerMismatchException {
        return rdivide(this, m2);
    }
    
    //dividing with rest
    public static Pair<Monomial, Monomial> rdivide(Monomial m1, Monomial m2) throws MonomialPowerMismatchException {
        if ( m1.power < m2.power) throw new MonomialPowerMismatchException(i18n.base.getString("OPERATION_DIVIDE"), i18n.ex.getString("MONOMIAL_POWER_REASON_DIVIDE"), m1.power, m2.power);
        throw new UnsupportedOperationException();
    }

}
