package net.marpirk.dev.kalkwielomianow;

import java.util.ArrayList;
import java.util.HashMap;
import net.marpirk.dev.kalkwielomianow.A.Pair;
import net.marpirk.dev.kalkwielomianow.exceptions.ParamParseException;

/**
 *
 * @author Marek Pikuła
 */
public final class Wielomian {
    
    public HashMap<Integer, Integer> j;   //jednomiany
    
    public Wielomian() {
        j = new HashMap<>();
    }
    
    public Wielomian(HashMap<Integer, Integer> j) {
        this.j = new HashMap<>(j);
        check();
    }
    
    public Wielomian(ArrayList<String> j) throws NumberFormatException, ParamParseException {
        this.j = new HashMap<>();
        String[] tmpS;
        for ( String s : j ) {
            tmpS = s.split(":");
            if ( tmpS.length > 2 ) throw new ParamParseException(s, null, "błąd składni");
            this.j.put(Integer.parseInt(tmpS[1]), Integer.parseInt(tmpS[0]));
        }
        check();
    }
    
    public void check() {
        for ( int i = getHighest(); i >= 0; i-- ) {
            if ( j.containsKey(i) && j.get(i) == 0 ) {
                j.remove(i);
            }
        }
    }
    
    public int getHighest() {
        int i = 0;
        for ( Integer tmpI : j.keySet() ) {
            if ( tmpI > i ) i = tmpI;
        }
        return i;
    }
    
    @Override
    public String toString() {
        String tmpS = "";
        for ( int i = getHighest(); i >= 0; i--) {
            if ( j.containsKey(i) ) {
                tmpS += (j.get(i) != 1
                            ? (tmpS.equals("")
                                ? j.get(i)
                                : (j.get(i) >= 0
                                    ? " + " + j.get(i)
                                    : " - " + (-j.get(i))
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
    
    public static Wielomian add(Wielomian w1, Wielomian w2) {
        Wielomian wr = new Wielomian(w1.j);
        w2.j.keySet().stream().forEach((i) -> {
            if ( wr.j.containsKey(i) ) {
                wr.j.replace(i, wr.j.get(i) + w2.j.get(i));
            } else {
                wr.j.put(i, w2.j.get(i));
            }
        });
        return wr;
    }
    
    public static Wielomian multiply(Wielomian w1, Wielomian w2) {
        Wielomian wr = new Wielomian();
        w1.j.keySet().forEach((i) -> {
            w2.j.keySet().forEach((j) -> {
                if ( wr.j.containsKey(i + j) ) {
                    wr.j.replace(i + j, wr.j.get(i + j) + w1.j.get(i) * w2.j.get(j));
                } else {
                    wr.j.put(i + j, w1.j.get(i) * w2.j.get(j));
                }
            });
        });
        return wr;
    }
    
    public static Pair<Wielomian, Integer> divide(Wielomian w1, Wielomian w2) {
        return null;
    }

}
