package net.marpirk.dev.kalkwielomianow;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.marpirk.dev.kalkwielomianow.A.Pair;
import net.marpirk.dev.kalkwielomianow.exceptions.ParamParseException;

public class Kalkulator {

    private static Wielomian w1, w2;
    private final static Logger log = Logger.getLogger(Kalkulator.class.getName());
    private static Operation op;
    
    public static void main(String[] args) {
        try {
            ArrayList<String> wTmpArr = new ArrayList<>();
            for ( String s : args ) {
                if ( Operation.isOperator(s) ) {
                    if ( wTmpArr.isEmpty() ) {
                        throw new ParamParseException(s, null, "operator powinien być między wielomianami");
                    } else {
                        op = Operation.getFromString(s);
                        w1 = new Wielomian(wTmpArr);
                        wTmpArr = new ArrayList<>();
                    }
                } else {
                    for ( char c : s.toCharArray() ) {
                        if ( !A.isInteger(c + "") && c != ':' && c != '-' ) {
                            throw new ParamParseException(s, c + "", "błędny znak");
                        }
                    }
                    wTmpArr.add(s);
                }
            }
            if ( w1 == null ) {
                w1 = new Wielomian(wTmpArr);
            } else {
                w2 = new Wielomian(wTmpArr);
            }
        } catch ( ParamParseException ex ) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
            System.exit(1);
        }
        
        System.out.print("Dane wejściowe:\n"
                + "( " + w1.toString() + " ) "
                + Operation.getOpChar(op)
                + " ( " + w2.toString() + " )\n"
                + "Wynik: "
            );
        
        String r = "Błąd";  //Nigdy się nie powinno pojawićbet
        
        switch ( op ) {
            case ADD:
                Wielomian.add(w1, w2).toString();
                break;
            case MULTIPLY:
                r = Wielomian.multiply(w1, w2).toString();
                break;
            case DIVIDE:
                Pair<Wielomian, Integer> p = Wielomian.divide(w1, w2);
                r = p.key.toString() + "; r = " + p.value;
                break;
        }
        System.out.println(r);
        
    }
    
    
}
