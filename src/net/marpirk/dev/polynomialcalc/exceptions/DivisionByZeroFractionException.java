package net.marpirk.dev.polynomialcalc.exceptions;

import net.marpirk.dev.polynomialcalc.i18n.i18n;
import net.marpirk.dev.polynomialcalc.i18n.i18nt;

/**
 *
 * @author Marek Pikuła
 */
public class DivisionByZeroFractionException extends Exception {

    public String numerator;
    
    public DivisionByZeroFractionException(String numerator) {
        this.numerator = numerator;
    }
    
    @Override
    public String getMessage() {
        return i18n.getMessage(i18nt.EX, "DIVISION_BY_0_FRACTION", numerator);
    }
    
}
