package probability;

import java.math.BigInteger;

class Fraction implements Comparable<Fraction> {

    public static final Fraction ZERO = new Fraction(0, 1);

    BigInteger num, denom;

    public Fraction(long num, long denom) {
        this(BigInteger.valueOf(num), BigInteger.valueOf(denom));
    }

    public Fraction(BigInteger num, BigInteger denom) {
        BigInteger gcd = num.gcd(denom);
        this.num = num.divide(gcd);
        this.denom = denom.divide(gcd);
    }

    public Fraction add(Fraction other) {
        return new Fraction(num.multiply(other.denom).add(denom.multiply(other.num)), denom.multiply(other.denom));
    }


    public Fraction subtract(Fraction other) {
        return new Fraction(num.multiply(other.denom).subtract(denom.multiply(other.num)), denom.multiply(other.denom));
    }


    public Fraction multiply(Fraction other) {
        return new Fraction(num.multiply(other.num), denom.multiply(other.denom));
    }


    public Fraction divide(Fraction other) {
        return new Fraction(num.multiply(other.denom), denom.multiply(other.num));
    }

    @Override
    public int compareTo(Fraction other) {
        return num.multiply(other.denom).compareTo(denom.multiply(other.num));
    }

    @Override
    public String toString() {
        return num + "/" + denom;
    }
}