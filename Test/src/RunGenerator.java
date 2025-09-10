import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Wrapper to run XMLTreeViewer inside Eclipse.
 */
public final class RunGenerator {

    /**
     * Finds the greatest common divisor of n and m.
     *
     * @param n
     *            one number
     * @param m
     *            the other number
     * @updates n
     * @clears m
     * @ensures n = [greatest common divisor of #n and #m]
     */
    public static void reduceToGCD(NaturalNumber n, NaturalNumber m) {

        /*
         * Use Euclid's algorithm; in pseudocode: if m = 0 then GCD(n, m) = n
         * else GCD(n, m) = GCD(m, n mod m)
         */
        if (m.isZero()) {
            // Base case: if m is 0, GCD is n
            return;
        } else {
            NaturalNumber temp = new NaturalNumber2(n);
            temp.divide(m);
            reduceToGCD(m, temp);
            n.copyFrom(m);
            m.clear();
        }

    }

    public static void main(String[] args) {
        SimpleWriter out = new SimpleWriter1L();
        NaturalNumber ok = new NaturalNumber2(75);
        NaturalNumber kk = new NaturalNumber2(66);
        reduceToGCD(ok, kk);
        out.print(ok);
    }

}
