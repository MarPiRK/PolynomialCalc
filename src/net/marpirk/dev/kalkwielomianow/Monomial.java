package net.marpirk.dev.kalkwielomianow;

import java.util.ArrayList;
import net.marpirk.dev.kalkwielomianow.A.Pair;

/**
 *
 * @author Marek Pikuła
 */
public class Monomial extends ArrayList<Pair<Float, String>> {
    
    public Integer wykł; //wykładnik
    
    public Monomial(String toParse, Integer wykł) {
        super();
        this.wykł = wykł;
        
        for ( char c : toParse.toCharArray() ) {
            
        }
    }

}
