import static org.junit.Assert.assertEquals;

import org.junit.Test;

import components.set.Set;
import components.set.Set1L;
import components.simplereader.SimpleReader;
import components.simplereader.SimpleReader1L;
import components.simplewriter.SimpleWriter;
import components.simplewriter.SimpleWriter1L;

/**
 * Test class for StringReassembly with comprehensive test cases.
 *
 * @author Jeng Zhuang
 */
public class StringReassemblyTest {

    /*
     * Tests of overlap method
     */

    /**
     * Tests overlap with "qwere" and "erewq" which should have 3-character
     * overlap.
     */
    @Test
    public void testOverlapRacecar() {
        String str1 = "qwere";
        String str2 = "erewq";
        final int three = 3;
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(three, overlap);
    }

    /**
     * Tests overlap with "columb" and "olumbus" which should have 5-character
     * overlap.
     */
    @Test
    public void testOverlapWashington() {
        String str1 = "columb";
        String str2 = "olumbus";
        final int five = 5;
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(five, overlap);
    }

    /**
     * Tests overlap with non-overlapping strings "Hey" and "hi".
     */
    @Test
    public void testOverlapHey() {
        String str1 = "Hey";
        String str2 = "hi";
        int overlap = StringReassembly.overlap(str1, str2);
        assertEquals(0, overlap);
    }

    /*
     * Tests of combination method
     */

    /**
     * Tests combination of "racec" and "cecar" with 3-character overlap.
     */
    @Test
    public void testCombinationRacecar() {
        String str1 = "qwere";
        String str2 = "erewq";
        final int three = 3;
        int overlap = three;
        String combine = StringReassembly.combination(str1, str2, overlap);
        assertEquals("qwerewq", combine);
    }

    /**
     * Tests combination of "Colum" and "umbus" with 2-character overlap.
     */
    @Test
    public void testCombinationWashington() {
        String str1 = "Colum";
        String str2 = "umbus";
        int overlap = 2;
        String combine = StringReassembly.combination(str1, str2, overlap);
        assertEquals("Columbus", combine);
    }

    /*
     * Tests of addToSetAvoidingSubstrings method
     */

    /**
     * Tests adding "welcome" to set containing substrings.
     */
    @Test
    public void testAddToSetAvoidingSubstrings1() {
        Set<String> strSet = new Set1L<>();
        strSet.add("hey");
        strSet.add("hello");
        strSet.add("come");
        String str = "welcome";
        Set<String> expect = new Set1L<>();
        expect.add("hey");
        expect.add("hello");
        expect.add("welcome");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(expect, strSet);
    }

    /**
     * Tests adding "fish" to set where it's a substring of existing element.
     */
    @Test
    public void testAddToSetAvoidingSubstrings2() {
        Set<String> strSet = new Set1L<>();
        strSet.add("bear");
        strSet.add("tiger");
        strSet.add("fish");
        String str = "fish";
        Set<String> expect = new Set1L<>();
        expect.add("bear");
        expect.add("tiger");
        expect.add("fish");
        StringReassembly.addToSetAvoidingSubstrings(strSet, str);
        assertEquals(expect, strSet);
    }

    /*
     * Tests of printWithLineSeparators method
     */

    /**
     * Tests printing with multiple tildes in middle of string.
     */
    @Test
    public void testPrintWithLineSeparators1() {
        SimpleWriter out = new SimpleWriter1L("cheer-8-2.txt");
        SimpleReader in = new SimpleReader1L("cheer-8-2.txt");
        String text = "Testing 1~2 3 4~hi";
        String expect = "Testing 1" + "\n" + "2 3 4" + "\n" + "hi";
        StringReassembly.printWithLineSeparators(text, out);
        String test = in.nextLine();
        String test2 = in.nextLine();
        String test3 = in.nextLine();
        in.close();
        out.close();
        assertEquals(expect, test + "\n" + test2 + "\n" + test3);
    }

    /**
     * Tests printing with single tilde at end of string.
     */
    @Test
    public void testPrintWithLineSeparators2() {
        SimpleWriter out = new SimpleWriter1L("cheer-8-2.txt");
        SimpleReader in = new SimpleReader1L("cheer-8-2.txt");
        String text = "Testing 1 2 3 4~hi";
        String expect = "Testing 1 2 3 4" + "\n" + "hi";
        StringReassembly.printWithLineSeparators(text, out);
        String test = in.nextLine();
        String test2 = in.nextLine();
        in.close();
        out.close();
        assertEquals(expect, test + "\n" + test2);
    }

    /**
     * Tests printing with tildes separating complete words.
     */
    @Test
    public void testPrintWithLineSeparators3() {
        SimpleWriter out = new SimpleWriter1L("cheer-8-2.txt");
        SimpleReader in = new SimpleReader1L("cheer-8-2.txt");
        String text = "Here is~The Ohio~State University";
        String expect = "Here is" + "\n" + "The Ohio" + "\n" + "State University";
        StringReassembly.printWithLineSeparators(text, out);
        String test = in.nextLine();
        String test2 = in.nextLine();
        String test3 = in.nextLine();
        in.close();
        out.close();
        assertEquals(expect, test + "\n" + test2 + "\n" + test3);
    }

    /*
     * Tests of assemble method
     */

    /**
     * Tests assembling overlapping string fragments.
     */
    @Test
    public void testAssemble1() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Wow i");
        strSet.add("ow it'");
        strSet.add("it's g");
        strSet.add("'s good!");
        Set<String> expect = new Set1L<>();
        expect.add("Wow it's good!");
        StringReassembly.assemble(strSet);
        assertEquals(expect, strSet);
    }

    /**
     * Tests assembling with non-overlapping fragments remaining.
     */
    @Test
    public void testAssemble2() {
        Set<String> strSet = new Set1L<>();
        strSet.add("Wow i");
        strSet.add("ow it'");
        strSet.add("come");
        strSet.add("it's g");
        strSet.add("'s good!");
        strSet.add("on");
        Set<String> expect = new Set1L<>();
        expect.add("Wow it's good!");
        expect.add("come");
        expect.add("on");
        StringReassembly.assemble(strSet);
        assertEquals(expect, strSet);
    }
}
