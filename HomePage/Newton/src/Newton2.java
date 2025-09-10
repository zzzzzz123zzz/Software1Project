import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program calculates the square root of a positive number (including 0)
 * using Newton's method, ensuring a relative error of less than 0.01%. The user
 * can calculate multiple square roots in a single session.
 *
 * @author Jeng Zhuang
 *
 */
public final class Newton2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton2() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     *
     * @return estimate of square root
     */
    private static double sqrt(double x) {
        double r = x; // initial Guess
        final double epsilon = 0.0001; // Relative Error

        // Special case for x = 0; its square root is 0
        if (x == 0) {
            return r;
        } else {
            // Iteratively refine the estimate until the relative error is within bounds
            while (Math.abs(r * r - x) / x >= epsilon * epsilon) {
                r = (r + x / r) / 2;
            }
            return r;
        }
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        out.print("Do you want to calculate a square root? (y/n): ");
        String response = in.nextLine();

        // Loop to allow multiple calculations
        while (response.equals("y")) {
            out.print("Please enter a positive number: ");
            double x = in.nextDouble();

            // Compute the square root using the sqrt method
            double result = sqrt(x);

            out.println("The square root of " + x + " is approximately " + result);

            // Ask if the user wants to perform another calculation
            out.print("Do you want to calculate another square root? (y/n): ");
            response = in.nextLine();

        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
