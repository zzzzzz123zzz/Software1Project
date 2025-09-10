import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * This program calculates the square root of a user-specified positive number
 * to within a relative error threshold specified by the user. The program
 * continues to accept numbers for square root calculation until a negative
 * number is entered.
 *
 * @author Jeng Zhuang
 *
 */
public final class Newton4 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Newton4() {
    }

    /**
     * Computes estimate of square root of x to within relative error 0.01%.
     *
     * @param x
     *            positive number to compute square root of
     * @param epsilon
     *            the relative error threshold
     * @return estimate of square root
     */
    private static double sqrt(double x, double epsilon) {
        double r = x; // initial Guess

        // If x is zero, the square root is zero
        if (x == 0) {
            return r;
        } else {
            // Refine the estimate until the relative error is within the threshold
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

        // Ask the user for epsilon value
        out.print("Please enter the value of epsilon: ");
        double epsilon = in.nextDouble();

        // Initialize x to a non-negative value to start the loop
        double x = 0;

        // Loop until the user enters a negative number
        while (x >= 0) {
            out.print("Please enter a positive number (or a negative number to quit): ");
            x = in.nextDouble();

            // If x is non-negative, calculate and display the square root
            if (x >= 0) {
                double result = sqrt(x, epsilon);
                out.println("The square root of " + x + " is approximately " + result);
            }

        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
