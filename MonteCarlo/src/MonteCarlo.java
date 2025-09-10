import components.random.Random;
import components.random.Random1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Monte Carlo Estimate: compute percentage of pseudo-random points in [0.0,1.0)
 * interval that fall in the left half subinterval [0.0,0.5).
 */
public final class MonteCarlo {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private MonteCarlo() {
    }

    /**
     * Checks whether the given point (xCoord, yCoord) is inside the circle of
     * radius 1.0 centered at the point (1.0, 1.0).
     *
     * @param xCoord
     *            the x coordinate of the point
     * @param yCoord
     *            the y coordinate of the point
     * @return true if the point is inside the circle, false otherwise
     */
    private static boolean pointIsInCircle(double xCoord, double yCoord) {
        // Calculate the distance squared from the point to the center of the circle
        double distanceSquared = Math.pow(xCoord - 1.0, 2) + Math.pow(yCoord - 1.0, 2);

        // Compare the distance squared to the square of the radius (1.0^2 = 1.0)
        return distanceSquared <= 1.0;

    }

    /**
     * Generates n pseudo-random points in the [0.0,2.0) x [0.0,2.0) square and
     * returns the number that fall in the circle of radius 1.0 centered at the
     * point (1.0, 1.0).
     *
     * @param n
     *            the number of points to generate
     * @return the number of points that fall in the circle
     */
    private static int numberOfPointsInCircle(int n) {
        // Create a Random object for generating random numbers
        Random rnd = new Random1L();

        int pointsInCircle = 0; // Counter for points inside the circle

        // Generate n random points
        for (int i = 0; i < n; i++) {
            // Generate random coordinates in the range [0.0, 2.0)
            double xCoord = rnd.nextDouble() * 2.0;
            double yCoord = rnd.nextDouble() * 2.0;

            // Check if the point is in the circle using pointIsInCircle
            if (pointIsInCircle(xCoord, yCoord)) {
                pointsInCircle++;
            }
        }

        return pointsInCircle; // Return the count of points in the circle
    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        // Open input and output streams
        SimpleReader input = new SimpleReader1L();
        SimpleWriter output = new SimpleWriter1L();

        //Ask user for number of points to generate
        output.print("Number of points: ");
        int n = input.nextInteger();

        output.println(n);

        //Use the numberOfPointsInCircle method to count points inside the circle
        int ptsInCircle = numberOfPointsInCircle(n);

        // Estimate percentage of points generated in [0.0,1.0) interval that
        // fall in the [0.0,0.5) subinterval
        double Hunderd = 100.0;
        double estimate = (ptsInCircle * Hunderd) / n;
        double piEstimate = (4.0 * ptsInCircle) / n;
        output.println("Estimate of percentage: " + estimate + "%");
        output.println("Estimate of π: " + piEstimate);
        /*
         * Close input and output streams
         */
        input.close();
        output.close();
    }

}
