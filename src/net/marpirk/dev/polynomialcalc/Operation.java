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

package net.marpirk.dev.polynomialcalc;

import net.marpirk.dev.polynomialcalc.i18n.i18n;

public enum Operation {
    ADD, MULTIPLY, DIVIDE;
    
    private static boolean checkArg(String arg, String toCheck) {
        return arg.length() <= toCheck.length() &&
               arg.equalsIgnoreCase(toCheck.substring(0, arg.length()));
    }
    
    public static boolean isOperator(String s) {
        return checkArg(s, i18n.base.getString("OPERATION_ADD")) ||        //add
                checkArg(s, i18n.base.getString("OPERATION_MULTIPLY")) ||  //multiply
                checkArg(s, i18n.base.getString("OPERATION_DIVIDE"));      //divide
    }
    
    public static boolean isOperator(char c) {
        return ((c == '+') || (c == '-') || (c == '/'));
    }
    
    public static Operation getFromString(String s) {
        if ( checkArg(s, i18n.base.getString("OPERATION_ADD")) ) return ADD;
        if ( checkArg(s, i18n.base.getString("OPERATION_MULTIPLY")) ) return MULTIPLY;
        if ( checkArg(s, i18n.base.getString("OPERATION_DIVIDE")) ) return DIVIDE;
        return null;
    }
    
    public static Character getOpChar(Operation o) {
        switch ( o ) {
            case ADD: return '+';
            case MULTIPLY: return '*';
            case DIVIDE: return '/';
            default: return null;
        }
    }
    
}
