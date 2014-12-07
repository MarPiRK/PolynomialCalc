package net.marpirk.dev.kalkwielomianow;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.marpirk.dev.kalkwielomianow.A.Pair;
import net.marpirk.dev.kalkwielomianow.exceptions.ParamParseException;

public class Calc {

    private static ArrayList<Polynomial> po;    //polynomials
    private static ArrayList<Operation> op;     //operations
    
    private final static Logger log = Logger.getLogger(Calc.class.getName());
    
    public static void main(String[] args) {
        try {
            ArrayList<String> wTmpArr = new ArrayList<>();
            for ( String s : args ) {
                if ( Operation.isOperator(s) ) {
                    if ( wTmpArr.isEmpty() ) {
                        throw new ParamParseException(s, null, "operator powinien być między wielomianami");
                    } else {
                        op.add(Operation.getFromString(s));
                        po.add(new Polynomial(wTmpArr));
                        wTmpArr = new ArrayList<>();
                    }
                } else {
                    for ( char c : s.toCharArray() ) {
                        if ( !A.isInteger(c + "") && c != '|' && c != '-' && c != '+' && Character.isLetter(c) ) {
                            throw new ParamParseException(s, c + "", "błędny znak");
                        }
                    }
                    wTmpArr.add(s);
                }
            }
            po.add(new Polynomial(wTmpArr));
        } catch ( ParamParseException ex ) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
            System.exit(1);
        }
        
        Polynomial tmpRes = new Polynomial(po.get(0));
        System.out.println("Dane wejściowe:\n"
                + "( " + po.get(0).toString() + " ) ");
        for ( int i = 1; i < po.size(); i++ ) {
            System.out.print(Operation.getOpChar(op.get(i - 1))
                    + " ( " + po.get(i).toString() + " ) ");
            switch ( op.get(i - 1) ) {
                case ADD:
                    tmpRes = Polynomial.add(tmpRes, po.get(i)); break;
                case MULTIPLY:
                    tmpRes = Polynomial.multiply(tmpRes, po.get(i)); break;
                case DIVIDE:
                    Pair<Polynomial, Polynomial> p = Polynomial.divide(tmpRes, po.get(i));
                    r = p.key.toString() + "; r = " + p.value;
                    break;
            }
        }
        System.out.println("\nWynik: ");
        
        String r = "Błąd";  //Nigdy się nie powinno pojawićbet
        
        
        System.out.println(r);
        
    }
    
    
}
