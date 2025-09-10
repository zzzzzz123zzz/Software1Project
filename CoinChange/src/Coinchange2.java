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
public final class Coinchange2 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Coinchange2() {

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

        // Coin denominations in cents
        int[] denominations = { 100, 50, 25, 10, 5, 1 };
        String[] denominationNames = { "Dollars", "Half-dollars", "Quarters", "Dimes",
                "Nickels", "Pennies" };

        //Create an array to store each type of coin
        int[] counts = new int[denominations.length];

        //Prompt User for the input
        out.print("Please enter the amount of change in cents: ");
        int amount = in.nextInteger();

        //Calculate the amount the coins for each denomination using a loop
        for (int i = 0; i < denominations.length; i++) {
            counts[i] = amount / denominations[i];
            amount = amount % denominations[i];
        }

        // Output the results
        out.println("The minimum number of coins needed:");
        for (int i = 0; i < denominations.length; i++) {
            out.println(denominationNames[i] + ":" + counts[i]);
        }
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
