package seedu.tassist.logic.parser;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class QuotePatternTest {

    @Test
    public void isValidAttendanceString() {

        QuotePattern tester = new QuotePattern();

        // Invalid
        // Odd number of quotation marks.
        assertFalse(tester.test("\"hehehaha\"\""));
        // More than a pair of quotation marks within a flag.
        assertFalse(tester.test("-a \"hehehaha\"\"\" -g abc"));

        // Valid.
        // No quotation marks.
        assertTrue(tester.test("abc"));
        // One pair of quotation marks per flag.
        assertTrue(tester.test("-t \"abc\" -g \" abc \""));
    }

}
