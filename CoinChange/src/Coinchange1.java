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
public final class Coinchange1 {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private Coinchange1() {

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
        final int DOLLAR = 100;
        final int HALF_DOLLAR = 50;
        final int QUARTER = 25;
        final int DIME = 10;
        final int NICKEL = 5;

        //Prompt the user for the input
        out.print("Enter the amount of change in cents: ");
        int amount = in.nextInteger();

        // Calculate the number of coins for each denomination
        int numDollars = amount / DOLLAR;
        amount = amount % DOLLAR;

        int numHalfdollars = amount / HALF_DOLLAR;
        amount = amount % HALF_DOLLAR;

        int numQuarters = amount / QUARTER;
        amount = amount % QUARTER;

        int numDimes = amount / DIME;
        amount = amount % DIME;

        int numNickels = amount / NICKEL;
        amount = amount % NICKEL;

        int numPennies = amount;

        // Output the results
        out.println("The minimum number of coins needed:");
        out.println("Dollars: " + numDollars);
        out.println("Half-dollars: " + numHalfdollars);
        out.println("Quarters: " + numQuarters);
        out.println("Dimes: " + numDimes);
        out.println("Nickels: " + numNickels);
        out.println("Pennies: " + numPennies);

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
