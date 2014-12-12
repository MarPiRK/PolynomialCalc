package net.marpirk.dev.polynomialcalc.exceptions;

import net.marpirk.dev.polynomialcalc.Fraction;
import net.marpirk.dev.polynomialcalc.i18n.i18n;
import net.marpirk.dev.polynomialcalc.i18n.i18nt;

/**
 *
 * @author Marek Pikuła
 */
public class NotNumberFractionException extends Exception {

    public String operation;
    public Fraction f;
    
    public NotNumberFractionException(String operation, Fraction f) {
        this.operation = operation;
        this.f = f;
    }
    
    @Override
    public String getMessage() {
        return i18n.getMessage(i18nt.EX, "NOT_NUMBER_FRACTION_MSG", operation, f.toString());
    }
    
}
