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
