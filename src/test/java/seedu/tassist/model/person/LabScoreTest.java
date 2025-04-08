package seedu.tassist.model.person;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.tassist.logic.commands.CommandTestUtil.DEFAULT_LAB_MAX_SCORE;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_NUMBER_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_SCORE_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_SCORE_B;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.exceptions.CommandException;

public class LabScoreTest {

    @Test
    public void successUpdateLabScore() {
        // EP: valid lab score
        LabScore newLabScore = new LabScore();
        LabScore correctLabScore = new LabScore(15, 25);
        LabScore updatedLabScore = null;
        try {
            updatedLabScore = newLabScore.updateLabScore(15);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(updatedLabScore, correctLabScore);

    }

    @Test
    public void invalidLabScore() {
        // EP: Invalid lab score (negative score)
        // Boundary value: -1
        assertThrows(CommandException.class, () -> new LabScore().updateLabScore(-1));

        // EP: Invalid lab score (lab score exceeds max lab score)
        // Boundary value: 26 (since max score is 25)
        assertThrows(CommandException.class, () -> new LabScore().updateLabScore(DEFAULT_LAB_MAX_SCORE + 1));

        // EP: Invalid lab score (score larger than max score)
        // Boundary value: 11 (since max score is 10)
        assertThrows(CommandException.class, () -> new LabScore().updateBothLabScore(11, 10));
    }

    @Test
    public void successUpdateLabMaxScore() {
        // EP: valid max lab score
        LabScore newLabScore = new LabScore();

        LabScore correctLabScore = new LabScore(15, 30);

        LabScore updatedLabScore = null;
        try {
            newLabScore = newLabScore.updateLabScore(15);
            updatedLabScore = newLabScore.updateMaxLabScore(30);

        } catch (CommandException e) {
            fail();
        }

        assertEquals(updatedLabScore.toString(), correctLabScore.toString());

    }

    @Test
    public void invalidLabMaxScore() {
        // EP: invalid max lab score (negative score)
        // Boundary value: -1
        assertThrows(CommandException.class, () -> new LabScore().updateMaxLabScore(-1));

        // EP: invalid max lab score (max score < score)
        // Boundary value: 19 (since score is 20 (VALID_LAB_SCORE_A))
        assertThrows(CommandException.class, () -> new LabScore(VALID_LAB_SCORE_A, VALID_LAB_SCORE_B)
                .updateMaxLabScore(VALID_LAB_NUMBER_A - 1));
    }

    @Test
    public void successUpdateBothLabScore() {
        // EP: valid score and max lab score
        LabScore newLabScore = new LabScore();
        LabScore correctLabScore = new LabScore(100, 100);
        LabScore updatedLabScore = null;
        try {
            updatedLabScore = newLabScore.updateBothLabScore(100, 100);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(updatedLabScore, correctLabScore);

        LabScore correctLabScore2 = new LabScore(15, 35);
        try {
            updatedLabScore = newLabScore.updateBothLabScore(15, 35);
        } catch (CommandException e) {
            fail();
        }

        assertEquals(updatedLabScore, correctLabScore2);
    }

    @Test
    public void invalidUpdateBothScores() {
        // EP: invalid max lab score (negative score)
        // Boundary value: -1
        assertThrows(CommandException.class, () -> new LabScore().updateBothLabScore(VALID_LAB_SCORE_A, -1));

        // EP: invalid lab score (negative score)
        // Boundary value: -1
        assertThrows(CommandException.class, () -> new LabScore().updateBothLabScore(-1, VALID_LAB_SCORE_A));

        // EP: invalid max lab score and invalid lab score (negative score)
        // Boundary value: -1
        assertThrows(CommandException.class, () -> new LabScore().updateBothLabScore(-1, -1));
    }

}
