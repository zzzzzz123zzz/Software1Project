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
public final class CheckPassword {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private CheckPassword() {
    }

    /**
     * Checks whether the given String satisfies the OSU criteria for a valid
     * password. Prints an appropriate message to the given output stream.
     *
     * @param passwordCandidate
     *            the String to check
     * @param out
     *            the output stream
     */
    private static void checkPassword(String passwordCandidate, SimpleWriter out) {

        boolean lengthValid = passwordCandidate.length() >= 8;
        boolean hasUppercase = containsUppercaseLetter(passwordCandidate);
        boolean hasLowercase = containsLowercaseLetter(passwordCandidate);
        boolean hasDigit = containsDigitLetter(passwordCandidate);
        boolean hasSpecialcharacter = containsSpecialcharacterLetter(passwordCandidate);

        int criteriaMet = 0;
        if (hasUppercase) {
            criteriaMet++;
        }
        if (hasLowercase) {
            criteriaMet++;
        }
        if (hasDigit) {
            criteriaMet++;
        }
        if (hasSpecialcharacter) {
            criteriaMet++;
        }
        if (!lengthValid) {
            out.println("Password must be at least 8 characters long.");
        }
        if (criteriaMet < 3) {
            out.println("Password must contain at least three of the following:");
            out.println("- Upper case letter");
            out.println("- Lower case letter");
            out.println("- Digit");
            out.println("- Special character (`~!@#$%^&*()_-+={}[]|:;,<.>?/");
        }
        if (lengthValid && criteriaMet >= 3) {
            out.println("Password is valid.");
        } else {
            out.println("Password is invalid.");
        }
    }

    /**
     * Checks if the given String contains an upper case letter.
     *
     * @param str
     *            the String to check
     * @return true if str contains an upper case letter, false otherwise
     */
    private static boolean containsUppercaseLetter(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isUpperCase(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given String contains a lower case letter.
     *
     * @param str
     *            the String to check
     * @return true if str contains a lower case letter, false otherwise
     */
    private static boolean containsLowercaseLetter(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isLowerCase(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given String contains a digit.
     *
     * @param str
     *            the String to check
     * @return true if str contains a digit, false otherwise
     */
    private static boolean containsDigitLetter(String str) {
        for (int i = 0; i < str.length(); i++) {
            if (Character.isDigit(str.charAt(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks if the given String contains a special character.
     *
     * @param str
     *            the String to check
     * @return true if str contains a special character, false otherwise
     */
    private static boolean containsSpecialcharacterLetter(String str) {
        String specialCharacters = "`~!@#$%^&*()_-+={}[]|:;,<.>?/";
        for (int i = 0; i < str.length(); i++) {
            if (specialCharacters.indexOf(str.charAt(i)) >= 0) {
                return true;
            }
        }
        return false;
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

        out.println("Enter a password to check: ");
        String password = in.nextLine();

        while (!password.isEmpty()) {
            checkPassword(password, out);
            out.println();
            out.println("Enter a password to check: ");
            password = in.nextLine();
        }

        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
