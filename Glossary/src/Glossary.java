import java.util.Comparator;

import components.map.Map;
import components.map.Map1L;
import components.queue.Queue;
import components.queue.Queue1L;
import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * A glossary generator program that creates HTML documentation from text input.
 *
 * <p>
 * Features:
 * <ul>
 * <li>Generates index page with sorted term list</li>
 * <li>Creates individual term pages with hyperlinked definitions</li>
 * <li>Handles multi-line definitions</li>
 * <li>Case-insensitive alphabetical sorting</li>
 * </ul>
 *
 * @author Jeng Zhuang
 */
public final class Glossary {

    /**
     * Private constructor to prevent instantiation.
     */
    private Glossary() {
    }

    /**
     * Generates the main index page listing the terms.
     *
     * @param glossary
     *            the map containing term-definition pairs
     * @param outputFolder
     *            target directory for generated files
     * @requires outputFolder is a valid directory
     * @ensures creates 'index.html' in outputFolder with sorted term list
     */
    private static void generateIndexPage(Map<String, String> glossary,
            String outputFolder) {
        SimpleWriter out = new SimpleWriter1L(outputFolder + "/index.html");

        // Basic structure for the index HTML page
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Glossary</title>");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Glossary</h2>");
        out.println("<hr>");
        out.println("<h3>Index</h3>");
        out.println("<ul>");

        // Get and sort terms
        Set<String> keys = new Set1L<>();
        for (Map.Pair<String, String> pair : glossary) {
            keys.add(pair.key());
        }
        sortSet(keys, new StringLT());

        // Generate list items
        for (String term : keys) {
            out.println("<li><a href=\"" + term + ".html\">" + term + "</a></li>");
        }

        out.println("</ul>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    /**
     * Sorts a set of strings using the specified comparator.
     *
     * @param set
     *            the set to sort
     * @param comp
     *            the comparator defining the order
     * @requires comp != null
     * @ensures set's elements are ordered according to comp
     */
    public static void sortSet(Set<String> set, Comparator<String> comp) {
        Queue<String> tempQueue = new Queue1L<>();
        // Move elements to temporary queue
        while (set.size() > 0) {
            tempQueue.enqueue(set.removeAny());
        }
        // Sort using provided comparator
        tempQueue.sort(comp);
        // Restore sorted elements
        while (tempQueue.length() > 0) {
            set.add(tempQueue.dequeue());
        }
    }

    /**
     * Processes the definition to insert hyperlinks for glossary terms.
     *
     * @param definition
     *            the original definition
     * @param glossary
     *            map of all glossary terms
     * @return definition with terms wrapped in anchor tags
     * @requires glossary != null and definition != null
     */
    public static String processDefinition(String definition,
            Map<String, String> glossary) {
        Set<String> keys = new Set1L<>();
        for (Map.Pair<String, String> pair : glossary) {
            keys.add(pair.key());
        }
        // Critical: Sort by length first to prioritize longer terms
        sortSet(keys, new StringLengthDesc());

        Queue<String> words = new Queue1L<>();
        int i = 0;
        // Split definition into tokens (words and separators)
        while (i < definition.length()) {
            // Capture non-word characters
            while (i < definition.length()
                    && !Character.isLetterOrDigit(definition.charAt(i))) {
                words.enqueue(Character.toString(definition.charAt(i)));
                i++;
            }

            // Capture word characters
            int start = i;
            while (i < definition.length()
                    && Character.isLetterOrDigit(definition.charAt(i))) {
                i++;
            }
            if (start < i) {
                words.enqueue(definition.substring(start, i));
            }
        }

        // Replace terms with hyperlinks
        Queue<String> processed = new Queue1L<>();
        while (words.length() > 0) {
            String word = words.dequeue();
            if (keys.contains(word)) {
                processed.enqueue("<a href=\"" + word + ".html\">" + word + "</a>");
            } else {
                processed.enqueue(word);
            }
        }

        // Build final string
        StringBuilder result = new StringBuilder();
        for (String s : processed) {
            result.append(s);
        }
        return result.toString();
    }

    /**
     * Generates an HTML page for a single glossary term.
     *
     * @param term
     *            the term to document
     * @param definition
     *            the term's definition
     * @param glossary
     *            complete glossary data
     * @param outputFolder
     *            target directory for output
     * @requires term exists in glossary
     * @ensures creates [term].html in outputFolder
     */
    private static void generateTermPage(String term, String definition,
            Map<String, String> glossary, String outputFolder) {
        SimpleWriter out = new SimpleWriter1L(outputFolder + "/" + term + ".html");

        // Page header
        out.println("<html>");
        out.println("<head>");
        out.println("<title>" + term + "</title>");
        out.println("</head>");
        out.println("<body>");

        // Term title with styling
        out.println("<h2><b><i><font color=\"red\">" + term + "</font></i></b></h2>");
        out.println("<blockquote>");

        // Processed definition content
        String processedDef = processDefinition(definition, glossary);
        out.println(processedDef);

        // Page footer
        out.println("</blockquote>");
        out.println("<hr>");
        out.println("<p>Return to <a href=\"index.html\">index</a>.</p>");
        out.println("</body>");
        out.println("</html>");
        out.close();
    }

    /**
     * Main method.
     *
     * @param args
     *            command-line arguments
     */
    public static void main(String[] args) {
        SimpleReader in = new SimpleReader1L();
        SimpleWriter out = new SimpleWriter1L();

        // Get file paths
        out.print("Enter input file: ");
        String inputFile = in.nextLine();
        out.print("Enter output folder: ");
        String outputFolder = in.nextLine();

        // Read glossary data
        SimpleReader fileReader = new SimpleReader1L(inputFile);
        Map<String, String> glossary = new Map1L<>();

        String currentTerm = null;
        StringBuilder currentDefinition = new StringBuilder();

        // Parse input file
        while (!fileReader.atEOS()) {
            String line = fileReader.nextLine();
            if (line.equals("")) {
                // Term-definition separator
                if (currentTerm != null) {
                    glossary.add(currentTerm, currentDefinition.toString().trim());
                    currentTerm = null;
                    currentDefinition = new StringBuilder();
                }
            } else {
                if (currentTerm == null) {
                    // New term line
                    currentTerm = line;
                } else {
                    // Append to current definition
                    if (currentDefinition.length() > 0) {
                        currentDefinition.append(" ");
                    }
                    currentDefinition.append(line);
                }
            }
        }
        // Handle final entry
        if (currentTerm != null) {
            glossary.add(currentTerm, currentDefinition.toString().trim());
        }
        fileReader.close();

        // Generate documentation
        generateIndexPage(glossary, outputFolder);

        Set<String> terms = new Set1L<>();
        for (Map.Pair<String, String> pair : glossary) {
            terms.add(pair.key());
        }

        for (String term : terms) {
            String def = glossary.value(term);
            generateTermPage(term, def, glossary, outputFolder);
        }

        in.close();
        out.close();
    }

    /**
     * Case-insensitive alphabetical string comparator.
     */
    public static final class StringLT implements Comparator<String> {
        /**
         * {@inheritDoc}
         *
         * @return negative, zero, or positive if s1 is before, equal, or after
         *         s2
         */
        @Override
        public int compare(String s1, String s2) {
            return s1.compareToIgnoreCase(s2);
        }
    }

    /**
     * Length-descending string comparator. Prevents partial matches by
     * processing longer terms first.
     */
    public static final class StringLengthDesc implements Comparator<String> {
        /**
         * {@inheritDoc}
         *
         * @return positive if s2 is longer, negative if s1 is longer
         */
        @Override
        public int compare(String s1, String s2) {
            return Integer.compare(s2.length(), s1.length());
        }
    }
}
