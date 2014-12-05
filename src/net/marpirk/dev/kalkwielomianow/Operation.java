package net.marpirk.dev.kalkwielomianow;

public enum Operation {
    ADD, MULTIPLY, DIVIDE;
    
    public static boolean isOperator(String s) {
        return s.startsWith("a") ||     //add
                s.startsWith("m") ||    //multiply
                s.startsWith("d");      //divide
    }
    
    public static Operation getFromString(String s) {
        if ( s.startsWith("a") ) return ADD;
        if ( s.startsWith("m") ) return MULTIPLY;
        if ( s.startsWith("d") ) return DIVIDE;
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
