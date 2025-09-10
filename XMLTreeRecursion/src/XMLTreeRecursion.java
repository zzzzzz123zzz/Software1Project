import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree2;

/**
 * Program to practice recursion on {@code XMLTree}s.
 *
 * @author Jeng
 *
 */
public final class XMLTreeRecursion {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private XMLTreeRecursion() {
    }

    /**
     * Returns the number of occurrences of the given tag in the given
     * {@code XMLTree}.
     *
     * @param xml
     *            the {@code XMLTree}
     * @param tag
     *            the tag name
     * @return the number of occurrences of the given tag in the given
     *         {@code XMLTree}
     * @ensures <pre>
     * tagCount =
     *    [the number of occurrences of the given tag in the given {@code XMLTree}]
     * </pre>
     */
    private static int tagCount(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";

        int count = 0;

        if (xml.isTag()) {
            // Check if current tag matches
            if (xml.label().equals(tag)) {
                count = 1;
            }

            // Recursively count in children
            for (int i = 0; i < xml.numberOfChildren(); i++) {
                count += tagCount(xml.child(i), tag);
            }
        }

        return count;
    }

    /**
     * Outputs the text nodes in the given {@code XMLTree} on separate lines.
     *
     * @param xml
     *            the {@code XMLTree}
     * @param out
     *            the output stream
     * @updates out.content
     * @requires out.is_open
     * @ensures <pre>
     * out.content = #out.content * [the text nodes of xml on separate lines]
     * </pre>
     */
    private static void outputTextNodes(XMLTree xml, SimpleWriter out) {
        assert xml != null : "Violation of: xml is not null";
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";

        if (xml.isTag()) {
            // Recurse into each child
            for (int i = 0; i < xml.numberOfChildren(); i++) {
                outputTextNodes(xml.child(i), out);
            }
        } else {
            // Output the text node
            out.println(xml.label());
        }

    }

    /**
     * Outputs n spaces.
     *
     * @param n
     *            the number of spaces
     * @param out
     *            the output stream
     * @updates out.content
     * @requires out.is_open and n >= 0
     * @ensures out.content = #out.content * [n spaces]
     */
    private static void outputSpaces(int n, SimpleWriter out) {
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        assert n >= 0 : "Violation of: n >= 0";

        for (int i = 0; i < n; i++) {
            out.print(' ');
        }

    }

    /**
     * Outputs the attributes ( name="value") of the given {@code XMLTree}'s
     * root node to the given output stream.
     *
     * @param xml
     *            the {@code XMLTree}
     * @param out
     *            the output stream
     * @updates out.content
     * @requires out.is_open and [the label of the root of xml is a tag]
     * @ensures <pre>
     * out.content =
     *    #out.content * [the attributes ( name="value") of the root of xml]
     * </pre>
     */
    private static void outputAttributes(XMLTree xml, SimpleWriter out) {
        assert xml != null : "Violation of: xml is not null";
        assert out != null : "Violation of: out is not null";
        assert xml.isTag() : "Violation of: the label of the root of xml is a tag";
        assert out.isOpen() : "Violation of: out.is_open";

        for (String attribute : xml.attributeNames()) {
            String value = xml.attributeValue(attribute);
            out.print(" " + attribute + "=\"" + value + "\"");
        }

    }

    /**
     * Output the XML textual representation of the given {@code XMLTree}.
     *
     * @param xml
     *            the {@code XMLTree}
     * @param out
     *            the output stream
     * @param indentationLevel
     *            the level of indentation
     * @updates out.content
     * @requires out.is_open and indentationLevel >= 0
     * @ensures <pre>
     * out.content = #out.content * [the XML textual representation of xml]
     * </pre>
     */
    private static void outputXML(XMLTree xml, SimpleWriter out, int indentationLevel) {
        assert xml != null : "Violation of: xml is not null";
        assert out != null : "Violation of: out is not null";
        assert out.isOpen() : "Violation of: out.is_open";
        assert indentationLevel >= 0 : "Violation of: indentationLevel >= 0";

        if (xml.isTag()) {
            // Indent and print opening tag
            outputSpaces(indentationLevel * 4, out);
            out.print("<" + xml.label());
            outputAttributes(xml, out);

            int numChildren = xml.numberOfChildren();
            if (numChildren == 0) {
                // Self-closing tag
                out.print("/>");
            } else {
                // Check if any child is a tag
                boolean hasChildTags = false;
                for (int i = 0; i < numChildren; i++) {
                    if (xml.child(i).isTag()) {
                        hasChildTags = true;
                        break;
                    }
                }

                if (hasChildTags) {
                    // Handle children on new lines
                    out.print(">");
                    out.println();

                    for (int i = 0; i < numChildren; i++) {
                        outputXML(xml.child(i), out, indentationLevel + 1);
                    }

                    // Print closing tag
                    outputSpaces(indentationLevel * 4, out);
                    out.print("</" + xml.label() + ">");
                } else {
                    // Handle inline text children
                    out.print(">");
                    for (int i = 0; i < numChildren; i++) {
                        outputXML(xml.child(i), out, 0);
                    }
                    out.print("</" + xml.label() + ">");
                }
            }

            out.println();
        } else {
            // Print text node (no indentation)
            out.print(xml.label());
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

        out.print("Enter a URL or file name for an XML source: ");
        String url = in.nextLine();
        XMLTree xml = new XMLTree2(url);

        out.print("Enter the name of a tag: ");
        String tag = in.nextLine();
        while (!tag.equals("")) {
            int count = tagCount(xml, tag);
            out.println("The tag <" + tag + "> appears " + count + " times.");
            out.println();
            out.print("Enter the name of a tag: ");
            tag = in.nextLine();
        }

        out.println();
        out.println("The text nodes:");
        outputTextNodes(xml, out);

        out.println();
        out.println("The XML:");
        outputXML(xml, out, 0);

        in.close();
        out.close();
    }

}
