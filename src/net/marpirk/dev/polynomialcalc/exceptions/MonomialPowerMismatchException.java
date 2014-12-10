package net.marpirk.dev.polynomialcalc.exceptions;

import java.text.MessageFormat;
import net.marpirk.dev.polynomialcalc.i18n.i18n;

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
        return MessageFormat.format(i18n.ex.getString("MONOMIAL_POWER_MSG"), new Object[] {operation, reason, p1, p2});
    }

}
