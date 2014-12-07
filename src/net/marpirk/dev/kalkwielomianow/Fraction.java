package net.marpirk.dev.kalkwielomianow;

public class Fraction {
    
    public long counter, denominator;
    
    public Fraction(Double counter, Double denominator) {
        int cDecPlaces, dDecPlaces;
        if ( counter != (int) counter.floatValue() ) {
            String text = Double.toString(Math.abs(counter));
            cDecPlaces = text.length() - text.indexOf('.') - 1;
        } else {
            cDecPlaces = 0;
        }
        if ( denominator != (int) denominator.floatValue() ) {
            String text = Double.toString(Math.abs(denominator));
            dDecPlaces = text.length() - text.indexOf('.') - 1;
        } else {
            dDecPlaces = 0;
        }
        if ( cDecPlaces != 0 || dDecPlaces != 0 ) {
            if ( cDecPlaces > dDecPlaces ) {
                this.counter = Math.round(counter * Math.pow(10, cDecPlaces));
                this.denominator = Math.round(denominator * Math.pow(10, cDecPlaces));
            } else {
                this.counter = Math.round(counter * Math.pow(10, dDecPlaces));
                this.denominator = Math.round(denominator * Math.pow(10, dDecPlaces));
            }
        }
        check();
    }
    
    /**
     * @return true if it had to do anything
     */
    public boolean check() {
        long gcd = A.gcd(counter, denominator);
        if ( gcd > 1 ) {
            counter = Math.round(counter / gcd);
            denominator = Math.round(denominator / gcd);
            return true;
        }
        return false;
    }
    
    public double getValue() {
        return counter/denominator;
    }
    
    public double getValue(int decimalPlaces) {
        return Math.round(
                (counter * (Math.pow(10, decimalPlaces)))
                /
                (denominator * (Math.pow(10, decimalPlaces))))
            / Math.pow(10, decimalPlaces);
    }

}
