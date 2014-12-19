package net.marpirk.dev.polynomialcalc;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.marpirk.dev.polynomialcalc.A.Pair;
import net.marpirk.dev.polynomialcalc.exceptions.DivisionByZeroFractionException;
import net.marpirk.dev.polynomialcalc.exceptions.ParamParseException;
import net.marpirk.dev.polynomialcalc.i18n.i18n;
import net.marpirk.dev.polynomialcalc.i18n.i18nt;

public class Calc {

    private final static ArrayList<Polynomial> po = new ArrayList<>();    //polynomials
    private final static ArrayList<Operation> op = new ArrayList<>();     //operations
    
    private final static Logger log = Logger.getLogger(Calc.class.getName());
    
    public static void main(String[] args) {
        try {
            ArrayList<String> wTmpArr = new ArrayList<>();
            for ( String s : args ) {
                if ( Operation.isOperator(s) ) {
                    if ( wTmpArr.isEmpty() ) {
                        throw new ParamParseException(s, null, i18n.ex.getString("PARAM_PARSE_OPERATOR_SHOULD_BE_BETWEEN"));
                    } else {
                        op.add(Operation.getFromString(s));
                        po.add(new Polynomial(wTmpArr));
                        wTmpArr = new ArrayList<>();
                    }
                } else {
                    wTmpArr.add(s);
                }
            }
            po.add(new Polynomial(wTmpArr));
        } catch ( ParamParseException|DivisionByZeroFractionException ex ) {
            log.log(Level.SEVERE, ex.getMessage(), ex);
            System.exit(1);
        }
        
        Pair<Polynomial, Polynomial> tmpRes = new Pair<>(po.get(0), new Polynomial());
        int i;
        System.out.print(i18n.base.getString("RESULT_INPUT_DATA") + ":\n"
                + "( " + po.get(0).toString() + " ) ");
        for ( i = 1; i < po.size(); i++ ) {
            System.out.print(Operation.getOpChar(op.get(i - 1))
                    + " ( " + po.get(i).toString() + " ) ");
            switch ( op.get(i - 1) ) {
                case ADD:
                    tmpRes.key = Polynomial.add(tmpRes.key, po.get(i)); break;
                case MULTIPLY:
                    tmpRes.key = Polynomial.multiply(tmpRes.key, po.get(i)); break;
                case DIVIDE:
                    tmpRes = Polynomial.divide(tmpRes.key, po.get(i));
                    if ( !tmpRes.value.isEmpty() ) {
                        break;
                    }
            }
        }
        System.out.print("\n" + i18n.base.getString("RESULT_RESULT") + ": " + tmpRes.key.toString());
        
        if ( !tmpRes.value.isEmpty() ) {
            if ( i == po.size() - 1 ) {
                System.out.println("; " + i18n.base.getString("RESULT_REST_1") + ": " + tmpRes.value.toString());
            } else {
                //calculating interrupted
                System.out.println("\n" + i18n.base.getString("RESULT_REST_2") + "\n"
                        + i18n.base.getString("RESULT_REST") + ": " + tmpRes.value.toString() + "\n"
                        + i18n.getMessage(i18nt.BASE, "RESULT_REST_2_OPERATION_NR", (i-1)));
            }
        }
        
        System.out.print("\n");
        
    }
    
    
}
