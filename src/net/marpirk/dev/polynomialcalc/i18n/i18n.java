package net.marpirk.dev.polynomialcalc.i18n;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class i18n {
    
    public static final ResourceBundle base = ResourceBundle.getBundle("net/marpirk/dev/kalkwielomianow/i18n/base");
    public static final ResourceBundle ex = ResourceBundle.getBundle("net/marpirk/dev/kalkwielomianow/i18n/exceptions");
    
    public static String getMessage(i18nt type, String key, Object ... arguments) {
        return MessageFormat.format(i18nt.geti18n(type).getString(key), arguments);
    }

}
