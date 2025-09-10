import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;
import components.xmltree.XMLTree;
import components.xmltree.XMLTree1;

/**
 * This program inputs an XML RSS (version 2.0) feed from a given URL and
 * outputs various elements of the feed to the console.
 *
 * @author Put your name here
 *
 */
public final class RSSProcessing {

    /**
     * Private constructor so this utility class cannot be instantiated.
     */
    private RSSProcessing() {
    }

    /**
     * Finds the first occurrence of the given tag among the children of the
     * given {@code XMLTree} and return its index; returns -1 if not found.
     *
     * @param xml
     *            the {@code XMLTree} to search
     * @param tag
     *            the tag to look for
     * @return the index of the first child of the {@code XMLTree} matching the
     *         given tag or -1 if not found
     * @requires [the label of the root of xml is a tag]
     * @ensures <pre>
     * getChildElement =
     *  [the index of the first child of the {@code XMLTree} matching the
     *   given tag or -1 if not found]
     * </pre>
     */
    private static int getChildElement(XMLTree xml, String tag) {
        assert xml != null : "Violation of: xml is not null";
        assert tag != null : "Violation of: tag is not null";
        assert xml.isTag() : "Violation of: the label root of xml is a tag";

        for (int i = 0; i < xml.numberOfChildren(); i++) {
            if (xml.child(i).isTag() && xml.child(i).label().equals(tag)) {
                return i;
            }
        }
        return -1;
    }

    /**
     * Processes one news item and outputs the title, or the description if the
     * title is not present, and the link (if available) with appropriate
     * labels.
     *
     * @param item
     *            the news item
     * @param out
     *            the output stream
     * @requires [the label of the root of item is an <item> tag] and
     *           out.is_open
     * @ensures out.content = #out.content * [the title (or description) and
     *          link]
     */
    private static void processItem(XMLTree item, SimpleWriter out) {
        assert item != null : "Violation of: item is not null";
        assert out != null : "Violation of: out is not null";
        assert item.isTag() && item.label().equals("item")
                : "" + "Violation of: the label root of item is an <item> tag";
        assert out.isOpen() : "Violation of: out.is_open";

        int titleIndex = getChildElement(item, "title");
        if (titleIndex != -1 && item.child(titleIndex).numberOfChildren() > 0) {
            out.println("Title: " + item.child(titleIndex).child(0).label());
        } else {

            int descriptionIndex = getChildElement(item, "description");
            if (descriptionIndex != -1
                    && item.child(descriptionIndex).numberOfChildren() > 0) {
                out.println(
                        "Description: " + item.child(descriptionIndex).child(0).label());
            } else {
                out.println("No title or description available.");
            }
        }

        int linkIndex = getChildElement(item, "link");
        if (linkIndex != -1 && item.child(linkIndex).numberOfChildren() > 0) {
            out.println("Link: " + item.child(linkIndex).child(0).label());
        } else {
            out.println("Link: not available.");
        }

    }

    /**
     * Main method.
     *
     * @param args
     *            the command line arguments; unused here
     */
    public static void main(String[] args) {
        /*
         * Open I/O streams.
         */
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();
        /*
         * Input the source URL.
         */
        out.print("Enter the URL of an RSS 2.0 news feed: ");
        String url = in.nextLine();
        /*
         * Read XML input and initialize XMLTree. If input is not legal XML,
         * this statement will fail.
         */
        XMLTree xml = new XMLTree1(url);
        /*
         * Extract <channel> element.
         */
        XMLTree channel = xml.child(0);

        int titleIndex = getChildElement(channel, "title");
        if (titleIndex != -1 && channel.child(titleIndex).numberOfChildren() > 0) {
            out.println("Channel Title: " + channel.child(titleIndex).child(0).label());
        } else {
            out.println("Channel Title: not available.");
        }

        int descriptionIndex = getChildElement(channel, "description");
        if (descriptionIndex != -1
                && channel.child(descriptionIndex).numberOfChildren() > 0) {
            out.println("Channel Description: "
                    + channel.child(descriptionIndex).child(0).label());
        } else {
            out.println("Channel Description: not available.");
        }

        int linkIndex = getChildElement(channel, "link");
        if (linkIndex != -1 && channel.child(linkIndex).numberOfChildren() > 0) {
            out.println("Channel Link: " + channel.child(linkIndex).child(0).label());
        } else {
            out.println("Channel Link: not available.");
        }

        for (int i = 0; i < channel.numberOfChildren(); i++) {
            if (channel.child(i).label().equals("item")) {
                processItem(channel.child(i), out);
            }
        }

        /*
         * Close I/O streams.
         */
        in.close();
        out.close();
    }

}
