package net.marpirk.dev.kalkwielomianow;

import java.util.ArrayList;
import java.util.HashMap;
import net.marpirk.dev.kalkwielomianow.exceptions.ParamParseException;

/**
 *
 * @author Marek Pikuła
 */
public class Wielomian {
    
    public HashMap<Integer, Integer> j;   //jednomiany
    
    public Wielomian() {
        j = new HashMap<>();
    }
    
    public Wielomian(HashMap<Integer, Integer> j) {
        this.j = new HashMap<>(j);
    }
    
    public Wielomian(ArrayList<String> j) throws NumberFormatException, ParamParseException {
        this.j = new HashMap<>();
        String[] tmpS;
        for ( String s : j ) {
            tmpS = s.split(":");
            if ( tmpS.length > 2 ) throw new ParamParseException();
            this.j.put(Integer.parseInt(tmpS[0]), Integer.parseInt(tmpS[1]));
        }
    }
    
    public static Wielomian add(Wielomian w1, Wielomian w2) {
        Wielomian wr = new Wielomian(w1.j);
        w2.j.keySet().parallelStream().filter((i) -> (wr.j.containsKey(i) )).forEach((i) -> {
            wr.j.replace(i, wr.j.get(i) + w2.j.get(i));
        });
        return wr;
    }

}
