package net.marpirk.dev.kalkwielomianow;

import java.util.ArrayList;
import java.util.HashMap;
import net.marpirk.dev.kalkwielomianow.A.Pair;
import net.marpirk.dev.kalkwielomianow.exceptions.ParamParseException;

/**
 *
 * @author Marek Pikuła
 */
public final class Polynomial extends HashMap<Integer, Monomial> {
    
    public Polynomial() {
        super();
    }
    
    public Polynomial(HashMap<Integer, Monomial> j) {
        super(j);
        check();
    }
    
    public Polynomial(ArrayList<String> j) throws NumberFormatException, ParamParseException {
        super();
        String[] tmpS;
        for ( String s : j ) {
            tmpS = s.split("|");
            if ( tmpS.length > 2 ) throw new ParamParseException(s, null, "błąd składni");
            put(Integer.parseInt(tmpS[1]), new Monomial(tmpS[0], Integer.parseInt(tmpS[1])));
        }
        check();
    }
    
    public void check() {
        for ( int i = getHighest(); i >= 0; i-- ) {
            if ( containsKey(i) && get(i).isEmpty() ) {
                remove(i);
            }
        }
    }
    
    public int getHighest() {
        int i = 0;
        for ( Integer tmpI : keySet() ) {
            if ( tmpI > i ) i = tmpI;
        }
        return i;
    }
    
    @Override
    public String toString() {
        String tmpS = "";
        for ( int i = getHighest(); i >= 0; i--) {
            if ( containsKey(i) ) {
                tmpS += (get(i) != 1
                            ? (tmpS.equals("")
                                ? get(i)
                                : (get(i) >= 0
                                    ? " + " + get(i)
                                    : " - " + (-get(i))
                                )
                            )
                            : ""
                        ) + (i == 0
                            ? ""
                            : (i == 1
                                ? "x"
                                : "x^" + i
                            )
                        );
            }
        }
        return tmpS;
    }
    
    public static Polynomial add(Polynomial w1, Polynomial w2) {
        Polynomial wr = new Polynomial(w1.j);
        w2.keySet().stream().forEach((i) -> {
            if ( wr.containsKey(i) ) {
                wr.replace(i, wr.get(i) + w2.get(i));
            } else {
                wr.put(i, w2.get(i));
            }
        });
        return wr;
    }
    
    public static Polynomial multiply(Polynomial w1, Polynomial w2) {
        Polynomial wr = new Polynomial();
        w1.keySet().forEach((i) -> {
            w2.keySet().forEach((j) -> {
                if ( wr.containsKey(i + j) ) {
                    wr.replace(i + j, wr.get(i + j) + w1.get(i) * w2.get(j));
                } else {
                    wr.put(i + j, w1.get(i) * w2.get(j));
                }
            });
        });
        return wr;
    }
    
    public static Pair<Polynomial, Polynomial> divide(Polynomial w1, Polynomial w2) {
        return null;
    }

}
