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
