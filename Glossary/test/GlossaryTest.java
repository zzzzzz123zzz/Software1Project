import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.map.Map;
import components.map.Map1L;
import components.set.Set;
import components.set.Set1L;

/**
 * JUnit test class for the Glossary utility methods.
 *
 */
public class GlossaryTest {

    //sortSet Tests

    /**
     * Tests {@code sortSet} with a regular set of unordered strings to check if
     * alphabetical order is achieved.
     */
    @Test
    public void testRoutine() {
        Set<String> testSet = new Set1L<>();
        testSet.add("banana");
        testSet.add("apple");
        testSet.add("cherry");

        Glossary.sortSet(testSet, new Glossary.StringLT());

        Set<String> expected = new Set1L<>();
        expected.add("apple");
        expected.add("banana");
        expected.add("cherry");

        assertEquals(expected, testSet);
    }

    /**
     * Tests {@code sortSet} with an empty set to ensure no exceptions occur and
     * the result is still an empty set.
     */
    @Test
    public void testBoundary() {
        Set<String> testSet = new Set1L<>();
        Glossary.sortSet(testSet, new Glossary.StringLT());
        assertEquals(new Set1L<>(), testSet);
    }

    /**
     * Tests {@code sortSet} with a custom comparator that sorts strings by
     * length in descending order.
     */
    @Test
    public void testChallenge() {
        Set<String> testSet = new Set1L<>();
        testSet.add("a");
        testSet.add("bbbb");
        testSet.add("cc");

        Glossary.sortSet(testSet, new Glossary.StringLengthDesc());

        Set<String> expected = new Set1L<>();
        expected.add("bbbb");
        expected.add("cc");
        expected.add("a");

        assertEquals(expected, testSet);
    }

    //processDefinition Tests

    /**
     * Tests {@code processDefinition} by linking a single glossary term present
     * in the definition.
     */
    @Test
    public void testProcessDefinitionRountine() {
        Map<String, String> glossary = new Map1L<>();
        glossary.add("term", "a definition");

        String definition = "This is a term.";
        String result = Glossary.processDefinition(definition, glossary);

        assertEquals("This is a <a href=\"term.html\">term</a>.", result);
    }

    /**
     * Tests {@code processDefinition} with an empty definition string.
     */
    @Test
    public void testProcessDefinitionBoundary() {
        Map<String, String> glossary = new Map1L<>();
        glossary.add("term", "a definition");

        String result = Glossary.processDefinition("", glossary);
        assertEquals("", result);
    }

    /**
     * Tests {@code processDefinition} with multiple glossary terms that should
     * be hyperlinked in the output.
     */
    @Test
    public void testProcessDefinitionChallenge1() {
        Map<String, String> glossary = new Map1L<>();
        glossary.add("apple", "a fruit");
        glossary.add("fruit", "a food");

        String definition = "An apple is a kind of fruit.";
        String result = Glossary.processDefinition(definition, glossary);

        assertEquals("An <a href=\"apple.html\">apple</a> is a kind of "
                + "<a href=\"fruit.html\">fruit</a>.", result);
    }

    /**
     * Tests {@code processDefinition} with overlapping glossary terms (e.g.,
     * "app" and "apple") to ensure both are correctly hyperlinked.
     */
    @Test
    public void testProcessDefinitionChallenge2() {
        Map<String, String> glossary = new Map1L<>();
        glossary.add("app", "short for application");
        glossary.add("apple", "a fruit");

        String definition = "I use an apple app.";
        String result = Glossary.processDefinition(definition, glossary);

        assertEquals(
                "I use an <a href=\"apple.html\">apple</a> <a href=\"app.html\">app</a>.",
                result);
    }

}
