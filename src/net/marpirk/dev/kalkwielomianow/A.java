package net.marpirk.dev.kalkwielomianow;

import java.io.InputStream;
import java.util.Scanner;

/**
 * A - od Additional Methods, wcześniej OtherMethods.
 * 
 * @author Marek Pikuła
 */
public class A {
    
    public static boolean isInteger(String s) {
        try { Integer.parseInt(s); 
        } catch(NumberFormatException e) { return false; }
        return true;
    }
    
    public static class Pair<K,V> {
        K key; V value; 
        public Pair(K key, V value) { this.key = key; this.value = value; }
    }
    
    /**
     * Dopełnia dany tekst danym znakiem do danej długości.<br />
     * Jeżeli długość tekstu jest większa niż dana, pozostawia tekst bez zmian.
     * 
     * @param toFill tekst do dopełnienia
     * @param filler znak dopełniający
     * @param lenght docelowa długość tekstu
     * @return dopełniony tekst
     */
    public static String fillWithChar(String toFill, char filler, int lenght) {
        if ( toFill.length() < lenght ) {
            String out = "";
            for ( int j = 0; j < (lenght - toFill.length()); j++ ) {
                out += filler;
            }
            return out + toFill;
        } else {
            return toFill;
        }
    }
    
    /**
     * Wyciąga ostatni człon, oddzielony danym separatorem, z danego tekstu.<br />
     * Np. w przypadku ścieżki pliku.
     * 
     * @param s tekst do rozdzielenia
     * @param separator separator tekstu
     * @return ostatni człon tekstu
     */
    public static String getLastPart(String s, String separator) {
        String[] parts = s.split(separator);
        return parts[parts.length - 1];
    }
    
    /**
     * Zwraca zasób (np. z pliku jar).
     * 
     * @param path ścieżka zasobu
     * @return zasób
     */
    public static InputStream getResource(String path) {
        return A.class.getResourceAsStream(path);
    }
    
    /**
     * Zwraca zasób w formie tekstu.
     * 
     * @param path ścieżka zasobu
     * @return zasób
     */
    public static String getStringFromResource(String path) {
        Scanner s = new Scanner(getResource(path)).useDelimiter("\\A");
        return s.hasNext() ? s.next() : "";
    }
    
    /**
     * Zaokrągla liczbę zmiennoprzecinkwą do danego miejsca po przecinku.
     * 
     * @param f liczba do zaokrąglenia
     * @param afterComma ilość miejsc po przecinku
     * @return zaokrąglona liczba
     */
    public static float roundFloat(float f, int afterComma) {
        String divider = "1";
        while ( divider.length() <= afterComma ) {
            divider += "0";
        }
        return Math.round(f * Integer.parseInt(divider)) / Float.parseFloat(divider);
    }
    
    /**
     * Zaokrągla liczbę zmiennoprzecinkową do danej liczby miejsc po przecinku
     * i dopełnia resztę miejsc zerami.
     * 
     * @param f liczba do zaokrąglenia
     * @param afterComma liczba miejsc po przecinku
     * @return zaokrągląna liczba
     */
    public static String roundFloatToString(float f, int afterComma) {
        String out = String.valueOf(roundFloat(f, afterComma));
        
        int i = out.split("\\.")[1].length();
        while ( i < afterComma ) {
            out += "0";
            i++;
        }
        
        return out;
    }
    
    /**
     * Konwertuje milisekundy na formę: godzina minuta sekunda milisekunda.
     * 
     * @param ms milisekundy do przekonwertowania
     * @param hStr tekst wyświetlany po liczbie godzin
     * @param mStr tekst wyświetlany po liczbie minut
     * @param sStr tekst wyświetlany po liczbie sekund
     * @param showMili czy ma wyświetlać milisekundy
     * @return przekonwertowany tekst
     */
    public static String msToString(long ms, String hStr, String mStr, String sStr, boolean showMili) {
        long s = ms / 1000;
        long ms2 = ms - s * 1000;
        long m = s / 60;
        s -= m * 60;
        long h = m / 60;
        m -= h * 60;
        return  ( h > 0 ? h + hStr : "" ) +
                ( m > 0 ? m + mStr : "" ) +
                ( ms2 > 0 && showMili ? s + "." + ms2 + sStr : s + sStr);
    }
    
    /**
     * Konwertuje milisekundy na tekst w formacie
     * "GODZINh MINUTm SEKUND.MILISEKUNDs".
     * 
     * @param ms milisekundy do przekonwertowania
     * @return przekonwertowany tekst
     * @see msToString(long ms, String hStr, String mStr, String sStr, boolean showMili)
     */
    public static String msToString(long ms) {
        return msToString(ms, "h ", "m ", "s ", true);
    }
    
}
