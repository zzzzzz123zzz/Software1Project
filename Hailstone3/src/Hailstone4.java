import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class Hailstone4 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Hailstone4() {
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
        out.print(n);
        int count = 0;
        int max = n;

        while (n != 1) {
            count = count + 1;
            if (n > max) {
                max = n;
            }
            if (n % 2 == 0) {
                n = n / 2;

            } else {
                n = 3 * n + 1;
            }
            out.print(", " + n);
        }
        out.println();
        count++;
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
        boolean continueProgram = true;
        /*
         * Put your main program code here; it may call myMethod as shown
         */
        while (continueProgram) {
            out.println("Enter a positive integer: ");
            int n = in.nextInteger();

            generateSeries(n, out);
            out.print("Would you like to calculate another series? (y/n): ");
            String response = in.nextLine().toLowerCase();
            if (!response.equals("y")) {
                continueProgram = false;
            }
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
