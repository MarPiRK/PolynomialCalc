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
