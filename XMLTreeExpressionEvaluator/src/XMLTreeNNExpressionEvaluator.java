import components.naturalnumber.NaturalNumber;
import components.naturalnumber.NaturalNumber2;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.utilities.Reporter;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Jeng Zhuang
 *
 */
public final class XMLTreeNNExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeNNExpressionEvaluator() {
    }

    /**
     * Evaluate the given expression.
     *
     * @param exp
     *            the {@code XMLTree} representing the expression
     * @return the value of the expression
     * @requires <pre>
     * [exp is a subtree of a well-formed XML arithmetic expression]  and
     *  [the label of the root of exp is not "expression"]
     * </pre>
     * @ensures evaluate = [the value of the expression]
     */
    private static NaturalNumber evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        // Variable to store the result
        NaturalNumber num = new NaturalNumber2();

        // Base case: if the current node is a number, return its value
        if (exp.label().equals("number")) {
            // Get the value attribute
            String valueStr = exp.attributeValue("value");
            // Convert the value to an integer
            int value = Integer.parseInt(valueStr);
            // Set the NaturalNumber value
            num.setFromInt(value);
            return num;
        } else {

            // Recursive case: evaluate the left and right subtrees
            // Evaluate the left child
            NaturalNumber left = evaluate(exp.child(0));
            // Evaluate the right child
            NaturalNumber right = evaluate(exp.child(1));
            // Variable to store the result of the operation
            NaturalNumber result = new NaturalNumber2();

            // Determine the operator and compute the result
            String operator = exp.label();
            if (operator.equals("plus")) {
                result.copyFrom(left); // Copy the left operand
                result.add(right); // Perform addition

            } else if (operator.equals("minus")) {
                // Check if subtraction would result in a negative number
                if (left.compareTo(right) < 0) {
                    Reporter.fatalErrorToConsole("Subtraction result would be negative.");
                }
                result.copyFrom(left); // Copy the left operand
                result.subtract(right); // Perform subtraction

            } else if (operator.equals("times")) {
                result.copyFrom(left); // Copy the left operand
                result.multiply(right); // Perform multiplication

            } else if (operator.equals("divide")) {
                // Check for division by zero
                if (right.isZero()) {
                    Reporter.fatalErrorToConsole("Division by zero.");
                }
                result.copyFrom(left); // Copy the left operand
                result.divide(right); // Perform division
            }
            return result;
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

        out.print("Enter the name of an expression XML file: ");
        String file = in.nextLine();
        while (!file.equals("")) {
            XMLTree exp = new XMLTree1(file);
            out.println(evaluate(exp.child(0)));
            out.print("Enter the name of an expression XML file: ");
            file = in.nextLine();
        }

        in.close();
        out.close();
    }

}
