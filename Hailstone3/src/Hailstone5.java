import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.FormatChecker;

/**
 * This program generates the Hailstone sequence for a given positive integer
 * and outputs the sequence's length and the maximum value encountered. The user
 * is prompted to continue generating series as long as they wish.
 *
 * @author Jeng Zhuang
 *
 */
public final class Hailstone5 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Hailstone5() {
    }

    /**
     * Repeatedly asks the user for a positive integer until the user enters
     * one. Returns the positive integer.
     *
     * @param in
     *            the input stream
     * @param out
     *            the output stream
     * @return a positive integer entered by the user
     */
    private static int getPositiveInteger(SimpleReader in, SimpleWriter out) {
        int result = -1; // Initialize result to an invalid value

        // Loop until the user provides a valid positive integer
        while (result <= 0) {
            out.print("Please enter a positive integer: ");
            String input = in.nextLine(); // Read input as a string

            // Check if the input can be parsed into an integer
            if (FormatChecker.canParseInt(input)) {
                result = Integer.parseInt(input); // Convert the string to an integer

                // Ensure the number is positive
                if (result <= 0) {
                    out.println("The number must be positive. Try again.");
                }
            } else {
                out.println("Invalid input. Please enter a valid integer.");
            }
        }

        return result;
    }

    /**
     * Generates and outputs the Hailstone series starting with the given
     * integer.
     *
     * @param n
     *            the starting integer
     * @param out
     *            the output stream
     */
    private static void generateSeries(int n, SimpleWriter out) {
        int current = n; // Create a local copy of n
        out.print(current); // Print the initial number in the sequence
        int count = 0; // Initialize count for the number of steps in the sequence
        int max = current; // Initialize max value as the starting number
        final int numberTwo = 2;
        final int numberThree = 3;

        // Loop to generate the Hailstone series until reaching 1
        while (current != 1) {
            count = count + 1; // Increment the count for each step
            if (current > max) { // Update max value if the current number is larger
                max = current;
            }
            // If the number is even, divide it by 2
            if (current % 2 == 0) {
                current = current / numberTwo;

            } else { // If the number is odd, multiply it by 3 and add 1
                current = numberThree * current + 1;
            }
            out.print(", " + current); // Print the next number in the sequence
        }
        out.println(); // Print a new line after the sequence ends
        count++; // Include the final step where n equals 1
        // Output the length of the series and the maximum value encountered
        out.println("Length of the series: " + count);
        out.println("Maximum value in the series: " + max);
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
        String response;
        /*
         * Main program loop using do-while to ensure at least one execution
         */
        do {
            int n = getPositiveInteger(in, out);

            generateSeries(n, out);

            // Ask the user if they want to calculate another series
            out.print("Would you like to calculate another series? (y/n): ");
            response = in.nextLine().toLowerCase();

        } while (response.equals("y")); // Continue if the user says "y"

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
