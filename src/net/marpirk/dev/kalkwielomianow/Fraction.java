package net.marpirk.dev.kalkwielomianow;

public class Fraction {
    
    protected long counter, denominator;
    
    public Fraction(double counter) {
        set(counter, 1);
    }
    
    public Fraction(double counter, double denominator) {
        set(counter, denominator);
    }
    
    /**
     * @return true if it had to do anything
     */
    public final Fraction check() {
        long gcd = A.gcd(counter, denominator);
        if ( gcd > 1 ) {
            counter = Math.round(counter / gcd);
            denominator = Math.round(denominator / gcd);
        }
        return this;
    }
    
    /**
     * @return dec places count
     */
    protected final int checkDecPlaces(double counter, double denominator) {
        int cDecPlaces, dDecPlaces;
        if ( counter != (int) counter ) {
            String text = Double.toString(Math.abs(counter));
            cDecPlaces = text.length() - text.indexOf('.') - 1;
        } else {
            cDecPlaces = 0;
        }
        if ( denominator != (int) denominator ) {
            String text = Double.toString(Math.abs(denominator));
            dDecPlaces = text.length() - text.indexOf('.') - 1;
        } else {
            dDecPlaces = 0;
        }
        if ( cDecPlaces >= dDecPlaces ) {
            return cDecPlaces;
        } else {
            return dDecPlaces;
        }
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
    
    public final Fraction set(double counter, double denominator) {
        int decPlaces = checkDecPlaces(counter, denominator);
        this.counter = Math.round(counter * Math.pow(10, decPlaces));
        this.denominator = Math.round(denominator * Math.pow(10, decPlaces));
        return check();
    }
    
    public long getCounter() {
        return counter;
    }
    
    public Fraction setCounter(double counter) {
        return set(counter, this.denominator);
    }
    
    public long getDenominator() {
        return denominator;
    }
    
    public Fraction setDenominator(double denominator) {
        return set(this.counter, denominator);
    }

}
