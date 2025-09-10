import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * A program to approximate a physical constant (μ) using the de Jager formula.
 * The program takes a target constant (μ) and four personal numbers (w, x, y,
 * z) from the user, then calculates the best combination of exponents (a, b, c,
 * d) to approximate μ using the formula: w^a * x^b * y^c * z^d.
 *
 * @author Jeng Zhuang
 *
 */
public final class ABCDGuesser1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private ABCDGuesser1() {
    }

    /**
     * Repeatedly asks the user for a positive real number until the user enters
     * one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number entered by the user
     */
    private static double getPositiveDouble(SimpleReader in, SimpleWriter out) {
        double value = -1; // Initialize with an invalid value
        while (value <= 0) { // Keep asking until a positive number is entered
            String input = in.nextLine(); // Read user input
            if (FormatChecker.canParseDouble(input)) { // Check if input is a valid double
                value = Double.parseDouble(input); // Parse the input to a double
                if (value <= 0) {
                    out.println("Error: Number must be positive.");
                }
            } else {
                out.println("Error: Invalid input. Please enter a real number.");
            }
        }
        return value;
    }

    /**
     * Repeatedly asks the user for a positive real number not equal to 1.0
     * until the user enters one. Returns the positive real number.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive real number not equal to 1.0 entered by the user
     */
    private static double getPositiveDoubleNotOne(SimpleReader in, SimpleWriter out) {
        double value = 1; // Initialize with an invalid value
        while (value == 1 || value <= 0) { // Keep asking until a valid number is entered
            String input = in.nextLine(); // Read user input
            if (FormatChecker.canParseDouble(input)) { // Check if input is a valid double
                value = Double.parseDouble(input); // Parse the input to a double
                if (value == 1 || value <= 0) {
                    out.println("Error: Number must be positive and not equal to 1.");
                }
            } else {
                out.println("Error: Invalid input. Please enter a real number.");
            }
        }
        return value;
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        // Initialize input and output streams
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Array of possible exponents to use in the de Jager formula
        final double[] exponents = { -5.0, -4.0, -3.0, -2.0, -1.0, -0.5, -1.0 / 3.0,
                -0.25, 0.0, 0.25, 1.0 / 3.0, 0.5, 1.0, 2.0, 3.0, 4.0, 5.0 };

        // Get the constant μ from the user
        out.println("Enter the constant μ(positive) to approximate:");
        double mu = getPositiveDouble(in, out);

        // Get the four personal numbers from the user
        out.println("Enter four personal numbers (positive, not equal to 1):");
        double w = getPositiveDoubleNotOne(in, out);
        double x = getPositiveDoubleNotOne(in, out);
        double y = getPositiveDoubleNotOne(in, out);
        double z = getPositiveDoubleNotOne(in, out);

        // Variables to store the best approximation and its details
        double bestApproximation = 0; // Stores the best approximation of μ
        double bestError = Double.MAX_VALUE; // Stores the smallest relative error found
        double bestA = 0, bestB = 0, bestC = 0, bestD = 0; // Stores the best exponents
        final double hundred = 100; // Used to convert error to a percentage

        // Iterate through all possible combinations of exponents
        int i = 0;
        while (i < exponents.length) {
            double a = exponents[i]; // Current exponent for w

            int j = 0;
            while (j < exponents.length) {
                double b = exponents[j]; // Current exponent for x

                int k = 0;
                while (k < exponents.length) {
                    double c = exponents[k]; // Current exponent for y

                    int l = 0;
                    while (l < exponents.length) {
                        double d = exponents[l]; // Current exponent for z

                        // Calculate the approximation using the de Jager formula
                        double approximation = Math.pow(w, a) * Math.pow(x, b)
                                * Math.pow(y, c) * Math.pow(z, d);

                        // Calculate the relative error
                        double error = Math.abs((approximation - mu) / mu) * hundred;

                        // Check if this is the best approximation so far
                        if (error < bestError) {
                            bestError = error; // Update the smallest error
                            // Update the best approximation
                            bestApproximation = approximation;
                            bestA = a; // Update the best exponent for w
                            bestB = b; // Update the best exponent for x
                            bestC = c; // Update the best exponent for y
                            bestD = d; // Update the best exponent for z
                        }

                        l++; // Move to the next exponent for d
                    }
                    k++; // Move to the next exponent for c
                }
                j++; // Move to the next exponent for b
            }
            i++; // Move to the next exponent for a
        }
        // Output the results
        out.println("Best exponents: a = " + bestA + ", b = " + bestB + ", c = " + bestC
                + ", d = " + bestD);
        out.println("Best approximation: " + bestApproximation);
        out.print("Relative error: ");
        out.print(bestError, 2, false); // Print the error with 2 decimal places
        out.println("%");

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
