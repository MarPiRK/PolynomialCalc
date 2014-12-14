package net.marpirk.dev.polynomialcalc.exceptions;

import net.marpirk.dev.polynomialcalc.i18n.i18n;
import net.marpirk.dev.polynomialcalc.i18n.i18nt;

/**
 *
 * @author Marek Pikuła
 */
public class DivisionByZeroFractionException extends Exception {

    public String numerator, denominator;
    
    public DivisionByZeroFractionException(String numerator) {
        this.numerator = numerator;
        this.denominator = "0";
    }
    
    public DivisionByZeroFractionException(String numerator, String denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }
    
    @Override
    public String getMessage() {
        return i18n.getMessage(i18nt.EX, "DIVISION_BY_0_FRACTION", numerator, denominator);
    }
    
}
