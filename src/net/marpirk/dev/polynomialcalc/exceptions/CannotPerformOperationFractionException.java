package net.marpirk.dev.polynomialcalc.exceptions;

import net.marpirk.dev.polynomialcalc.Fraction;
import net.marpirk.dev.polynomialcalc.i18n.i18n;
import net.marpirk.dev.polynomialcalc.i18n.i18nt;

/**
 *
 * @author Marek Pikuła
 */
public class CannotPerformOperationFractionException extends Exception {

    public String operation, reason, f;
    
    public CannotPerformOperationFractionException(String i18nPrefix, Fraction f) {
        this.operation = i18n.ex.getString(i18nPrefix + "_OP");
        this.reason = i18n.ex.getString(i18nPrefix + "_REASON");
        this.f = f.toString();
    }
    
    @Override
    public String getMessage() {
        return i18n.getMessage(i18nt.EX, "CANNOT_PERFORM_OP_FRACTION_MSG", operation, reason, f);
    }
    
}
