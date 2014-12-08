package net.marpirk.dev.kalkwielomianow;

public enum Operation {
    ADD, MULTIPLY, DIVIDE;
    
    private static boolean checkArg(String arg, String toCheck) {
        return arg.length() <= toCheck.length() &&
               arg.equalsIgnoreCase(toCheck.substring(0, arg.length()));
    }
    
    public static boolean isOperator(String s) {
        return checkArg(s, Calc.i18n.getString("OPERATION_ADD")) ||        //add
                checkArg(s, Calc.i18n.getString("OPERATION_MULTIPLY")) ||  //multiply
                checkArg(s, Calc.i18n.getString("OPERATION_DIVIDE"));      //divide
    }
    
    public static boolean isOperator(char c) {
        return ((c == '+') || (c == '-') || (c == '/'));
    }
    
    public static Operation getFromString(String s) {
        if ( checkArg(s, Calc.i18n.getString("OPERATION_ADD")) ) return ADD;
        if ( checkArg(s, Calc.i18n.getString("OPERATION_MULTIPLY")) ) return MULTIPLY;
        if ( checkArg(s, Calc.i18n.getString("OPERATION_DIVIDE")) ) return DIVIDE;
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
