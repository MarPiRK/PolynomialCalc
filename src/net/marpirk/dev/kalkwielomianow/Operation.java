package net.marpirk.dev.kalkwielomianow;

public enum Operation {
    ADD, MULTIPLY, DIVIDE;
    
    private static boolean checkArg(String arg, String toCheck) {
        return arg.length() <= toCheck.length() &&
               arg.equalsIgnoreCase(toCheck.substring(0, arg.length()));
    }
    
    public static boolean isOperator(String s) {
        return checkArg(s, "add") ||        //add
                checkArg(s, "multiply") ||  //multiply
                checkArg(s, "divide");      //divide
    }
    
    public static Operation getFromString(String s) {
        if ( checkArg(s, "add") ) return ADD;
        if ( checkArg(s, "multiply") ) return MULTIPLY;
        if ( checkArg(s, "divide") ) return DIVIDE;
        return null;
    }
    
    public static Character getOpChar(Operation o) {
        switch ( o ) {
            case ADD: return '+';
            case MULTIPLY: return '*';
            case DIVIDE: return ':';
            default: return null;
        }
    }
    
}
