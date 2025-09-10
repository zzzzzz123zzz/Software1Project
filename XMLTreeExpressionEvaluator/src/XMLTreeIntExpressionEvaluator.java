import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Program to evaluate XMLTree expressions of {@code int}.
 *
 * @author Jeng Zhuang
 *
 */
public final class XMLTreeIntExpressionEvaluator {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeIntExpressionEvaluator() {
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
    private static int evaluate(XMLTree exp) {
        assert exp != null : "Violation of: exp is not null";

        // Variable to store the result of the expression
        int result = 0;

        // Base case: if the current node is a number, return its value
        if (exp.label().equals("number")) {
            String value = exp.attributeValue("value");
            return Integer.parseInt(value);
        } else {

            // Recursive case: evaluate the left and right subtrees
            int left = evaluate(exp.child(0)); // Evaluate the left child
            int right = evaluate(exp.child(1)); // Evaluate the right child

            // Determine the operator and compute the result
            String operator = exp.label();
            if (operator.equals("plus")) {
                result = left + right; // Addition

            } else if (operator.equals("minus")) {
                result = left - right; // Subtraction

            } else if (operator.equals("times")) {
                result = left * right; // Multiplication

            } else if (operator.equals("divide")) {
                result = left / right; // Division

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
