import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * Put a short phrase describing the program here.
 *
 * @author Put your name here
 *
 */
public final class XMLTreeExploration {

    /**
     * No argument constructor--private to prevent instantiation.
     */
    private XMLTreeExploration() {
    }

    /**
     * Output information about the middle child of the given {@code XMLTree}.
     *
     * @param xt
     *            the {@code XMLTree} whose middle child is to be printed
     * @param out
     *            the output stream
     * @updates out.content
     * @requires [the label of the root of xt is a tag] and [xt has at least one
     *           child] and out.is_open
     * @ensures out.content = #out.content * [the label of the middle child of
     *          xt, whether the root of the middle child is a tag or text, and
     *          if it is a tag, the number of children of the middle child]
     */
    private static void printMiddleNode(XMLTree xt, SimpleWriter out) {
        int numChildren = xt.numberOfChildren();
        int middleIndex = numChildren / 2;

        XMLTree middleChild = xt.child(middleIndex);
        out.println("Middle child's label: " + middleChild.label());
        out.println("Is the middle child a tag? " + middleChild.isTag());

        if (middleChild.isTag()) {
            out.println("Number of children of the middle child: "
                    + middleChild.numberOfChildren());
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

        // Load XML data
        XMLTree xml = new XMLTree1("https://cse22x1.engineering.osu.edu/2221/web-sw1/"
                + "extras/instructions/xmltree-model/columbus-weather.xml");

        // Output the textual representation of the XML
        out.println("Textual representation of the XML:");
        out.println(xml.toString());

        // Display the graphical representation of the XML tree
        out.println("Opening a new window to display the XML tree...");
        xml.display();

        // Check if the root is a tag or text
        if (xml.isTag()) {
            out.println("The root is a tag, and its label is: " + xml.label());
        } else {
            out.println("The root is text, and its content is: " + xml.label());
        }
        // Get the results node (first child of the root)
        XMLTree results = xml.child(0);
        out.println(
                "Number of children of the results node: " + results.numberOfChildren());

        // Get the channel node (first child of the results node)
        XMLTree channel = results.child(0);
        out.println(
                "Number of children of the channel node: " + channel.numberOfChildren());

        // Get the title node (second child of the channel node)
        XMLTree title = channel.child(1);

        // Get the titleText node (first child of the title node)
        XMLTree titleText = title.child(0);
        out.println("Content of the titleText node: " + titleText.label());

        // Challenge: Output the titleText content in one line
        out.println("Content of the titleText node (one-liner): "
                + xml.child(0).child(0).child(1).child(0).label());

        // Get the astronomy node (11th child of the channel node, index 10)
        XMLTree astronomy = channel.child(10);

        // Check if the astronomy node has sunset and midday attributes
        out.println("Does the astronomy node have a sunset attribute? "
                + astronomy.hasAttribute("sunset"));
        out.println("Does the astronomy node have a midday attribute? "
                + astronomy.hasAttribute("midday"));

        // Output the values of the sunrise and sunset attributes
        out.println(
                "Value of the sunrise attribute: " + astronomy.attributeValue("sunrise"));
        out.println(
                "Value of the sunset attribute: " + astronomy.attributeValue("sunset"));

        // Output information about the middle child of the channel node
        out.println("Information about the middle child of the channel node:");
        printMiddleNode(channel, out);
        /*
         * Close input and output streams
         */
        in.close();
        out.close();
    }

}
