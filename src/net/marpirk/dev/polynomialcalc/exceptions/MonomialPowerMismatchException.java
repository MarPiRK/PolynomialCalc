package net.marpirk.dev.polynomialcalc.exceptions;


import net.marpirk.dev.polynomialcalc.i18n.i18n;
import net.marpirk.dev.polynomialcalc.i18n.i18nt;

/**
 *
 * @author Marek Pikuła
 */
public class MonomialPowerMismatchException extends Exception {
    
    public String operation, reason;
    public Integer p1, p2;
    
    public MonomialPowerMismatchException(String operation, String reason, int p1, int p2) {
        this.operation = operation;
        this.reason = reason;
        this.p1 = p1;
        this.p2 = p2;
    }
    
    @Override
    public String getMessage() {
        return i18n.getMessage(i18nt.EX, "MONOMIAL_POWER_MSG", operation, reason, p1, p2);
    }

}
