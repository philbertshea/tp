package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class LabScoreListTest {
    @Test
    public void testLabNumberValidity() {
        // EP: valid lab number
        // Boundary: lower bound (1) and upper bound (4)
        assertTrue(LabScoreList.isValidLabNumber("1"));
        assertTrue(LabScoreList.isValidLabNumber("4"));

        // EP: invalid lab number (negative number or 0)
        // Boundary: -1 and 0
        assertFalse(LabScoreList.isValidLabNumber("-1"));
        assertFalse(LabScoreList.isValidLabNumber("0"));

        // EP: invalid lab number (above upper bound)
        // Boundary: 5
        assertFalse(LabScoreList.isValidLabNumber("5"));
    }

    @Test
    public void testSaveStringValidity() {
        // EP: valid save string
        assertTrue(LabScoreList.isValidSaveString("4.25/25|-/25|-/25|-/25"));

        // EP: invalid save string (missing lab count separator (the period))
        assertFalse(LabScoreList.isValidSaveString("4 25/25|-/25|-/25|-/25"));
        assertFalse(LabScoreList.isValidSaveString("425/25|-/25|-/25|-/25"));

        // EP: invalid save string (lab count is not a number)
        assertFalse(LabScoreList.isValidSaveString("a.25/25|-/25|-/25|-/25"));

        // EP: invalid save string (lab count != number of labs)
        // Boundary value: 3 (below limit) and 5 (above limit)
        assertFalse(LabScoreList.isValidSaveString("3.25/25|-/25|-/25|-/25"));
        assertFalse(LabScoreList.isValidSaveString("5.25/25|-/25|-/25|-/25"));

        // EP: invalid save string (lab score substring separator is missing (slash))
        assertFalse(LabScoreList.isValidSaveString("4.2525|-/25|-/25|-/25"));

        // EP: invalid save string (lab score substring is not a number)
        assertFalse(LabScoreList.isValidSaveString("4.ab/cd|-/25|-/25|-/25"));
    }
}
