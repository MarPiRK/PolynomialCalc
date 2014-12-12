package net.marpirk.dev.polynomialcalc.i18n;

import java.util.ResourceBundle;

/**
 *
 * @author Marek Pikuła
 */
public enum i18nt {

    BASE, EX;
    
    public static ResourceBundle geti18n(i18nt type) {
        switch ( type ) {
            case BASE: return i18n.base;
            case EX: return i18n.ex;
            default: return null;
        }
    }
    
}
