import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber1L;

public class NNtoStringWithCommasTest {
    /**
     * Calls the method under test.
     *
     * @param n
     *            the number to pass to the method under test
     * @return the {@code String} returned by the method under test
     * @ensures <pre>
     * redirectToMethodUnderTest = [String returned by the method under test]
     * </pre>
     */

    private static String redirectToMethodUnderTest(NaturalNumber n) {
        return NNtoStringWithCommas5.toStringWithCommas(n);
    }

    /**
     * Test case for 0. Rationale: Ensures the method handles the smallest
     * natural number (assuming 0 is included).
     */
    @Test
    public void testZero() {
        NaturalNumber n = new NaturalNumber1L(0);
        assertEquals("0", redirectToMethodUnderTest(n));
    }

    /**
     * Test case for single-digit number. Rationale: Verifies no commas are
     * added for numbers with fewer than three digits.
     */
    @Test
    public void testSingleDigit() {
        NaturalNumber n = new NaturalNumber1L(5);
        assertEquals("5", redirectToMethodUnderTest(n));
    }

    /**
     * Test case for three-digit number. Rationale: Ensures no commas are added
     * for exactly three digits.
     */
    @Test
    public void testThreeDigits() {
        NaturalNumber n = new NaturalNumber1L(123);
        assertEquals("123", redirectToMethodUnderTest(n));
    }

    /**
     * Test case for four-digit number. Rationale: Checks comma placement for
     * numbers with one digit beyond a triplet.
     */
    @Test
    public void testFourDigits() {
        NaturalNumber n = new NaturalNumber1L(1234);
        assertEquals("1,234", redirectToMethodUnderTest(n));
    }

    /**
     * Test case for five-digit number. Rationale: Validates comma placement for
     * two digits beyond the first triplet.
     */
    @Test
    public void testFiveDigits() {
        NaturalNumber n = new NaturalNumber1L(12345);
        assertEquals("12,345", redirectToMethodUnderTest(n));
    }

    /**
     * Test case for six-digit number. Rationale: Ensures correct formatting for
     * two triplets.
     */
    @Test
    public void testSixDigits() {
        NaturalNumber n = new NaturalNumber1L(123456);
        assertEquals("123,456", redirectToMethodUnderTest(n));
    }

    /**
     * Test case for seven-digit number. Rationale: Verifies multiple commas in
     * larger numbers.
     */
    @Test
    public void testSevenDigits() {
        NaturalNumber n = new NaturalNumber1L(1234567);
        assertEquals("1,234,567", redirectToMethodUnderTest(n));
    }

    /**
     * Test case for nine-digit number. Rationale: Checks formatting for three
     * full triplets.
     */
    @Test
    public void testNineDigits() {
        NaturalNumber n = new NaturalNumber1L("123456789");
        assertEquals("123,456,789", redirectToMethodUnderTest(n));
    }

    /**
     * Test case with zeros in the middle. Rationale: Ensures zeros within
     * triplets do not affect comma placement.
     */
    @Test
    public void testZerosInMiddle() {
        NaturalNumber n = new NaturalNumber1L(1001);
        assertEquals("1,001", redirectToMethodUnderTest(n));
    }

    /**
     * Test case with zeros between digits. Rationale: Validates correct comma
     * placement with internal zeros.
     */
    @Test
    public void testZerosBetweenDigits() {
        NaturalNumber n = new NaturalNumber1L(10203);
        assertEquals("10,203", redirectToMethodUnderTest(n));
    }

    /**
     * Test case for multiple trailing zeros. Rationale: Checks formatting when
     * trailing zeros form a triplet.
     */
    @Test
    public void testTrailingZeros() {
        NaturalNumber n = new NaturalNumber1L(1000000);
        assertEquals("1,000,000", redirectToMethodUnderTest(n));
    }

    /**
     * Test case for four-digit value ending with 999. Rationale: Verifies comma
     * placement just above the three-digit threshold.
     */
    @Test
    public void testFourDigitsWithMaxTriplet() {
        NaturalNumber n = new NaturalNumber1L(9999);
        assertEquals("9,999", redirectToMethodUnderTest(n));
    }

    /**
     * Test case for a large twelve-digit number. Rationale: Validates correct
     * comma placement for multiple triplets.
     */
    @Test
    public void testLargeNumber() {
        NaturalNumber n = new NaturalNumber1L("123456789012");
        assertEquals("123,456,789,012", redirectToMethodUnderTest(n));
    }
}
