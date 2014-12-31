/**
 * Copyleft 2014 Marek Pikuła (marpirk@gmail.com)
 * 
 * This file is part of PolynomialCalc.
 * 
 * PolynomialCalc is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * PolynomialCalc is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * Copy of the GNU General Public License is included in PolynomialCalc default
 * package and in source root directory in file LICENSE
 */

package net.marpirk.dev.polynomialcalc.i18n;

import java.text.MessageFormat;
import java.util.ResourceBundle;

public class i18n {
    
    public static final ResourceBundle base = ResourceBundle.getBundle("net/marpirk/dev/polynomialcalc/i18n/base");
    public static final ResourceBundle ex = ResourceBundle.getBundle("net/marpirk/dev/polynomialcalc/i18n/exceptions");
    
    public static String getMessage(i18nt type, String key, Object ... arguments) {
        return MessageFormat.format(i18nt.geti18n(type).getString(key), arguments);
    }

}
