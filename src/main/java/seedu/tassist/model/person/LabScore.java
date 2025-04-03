package seedu.tassist.model.person;

import java.util.ArrayList;
import java.util.List;

import seedu.tassist.logic.commands.UpdateLabScoreCommand;
import seedu.tassist.logic.commands.exceptions.CommandException;

/**
 * Holds the score and max score for a lab.
 */
public class LabScore {
    private static ArrayList<Integer> allMaxScores = new ArrayList<>(List.of(25, 25, 25, 25));
    private int labScore;
    private int maxLabScore = 25; //Default max score is 25.

    /**
     * Creates default LabScore object.
     */
    public LabScore() {
        labScore = -1;
    }

    /**
     * Creates default LabScore object with standardized lab score.
     *
     * @param labNumber
     */
    public LabScore(int labNumber) {
        labScore = -1;
        maxLabScore = allMaxScores.get(labNumber);
    }

    /**
     * Creates a LabScore object with the provided parameters.
     *
     * @param labScore the score to initialize with.s
     * @param maxLabScore the max score of the lab to initialize.
     */
    public LabScore(int labScore, int maxLabScore) {
        this.labScore = labScore;
        this.maxLabScore = maxLabScore;
    }

    /**
     * Updates the lab score for this lab.
     *
     * @param labScore The updated lab score.
     * @return The updated LabScore object.
     * @throws CommandException If the lab score is invalid.
     */
    public LabScore updateLabScore(int labScore) throws CommandException {
        if (labScore > maxLabScore) {
            throw new CommandException(
                    String.format(UpdateLabScoreCommand.MESSAGE_INVALID_SCORE,
                            labScore, maxLabScore));
        } else if (labScore < 0) {
            throw new CommandException(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        }
        return new LabScore(labScore, maxLabScore);
    }

    /**
     * Updates the max lab score for this lab.
     *
     * @param maxLabScore The updated max lab score.
     * @return The updated LabScore object.
     * @throws CommandException If the lab score is invalid.
     */
    public LabScore updateMaxLabScore(int maxLabScore) throws CommandException {
        if (labScore > maxLabScore && labScore != -1) {
            throw new CommandException(
                    String.format(UpdateLabScoreCommand.MESSAGE_INVALID_MAX_SCORE,
                            maxLabScore, labScore));
        } else if (maxLabScore < 0) {
            throw new CommandException(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        }
        return new LabScore(labScore, maxLabScore);
    }

    /**
     * Updates the standardized max lab score for the updated lab.
     * This function is only called when it has just successfully updated
     * the maxLabScore for one LabScore object.
     *
     * @param labNumber The lab that the maxLabScore is associated with.
     * @param maxLabScore The updated maxLabScore.
     */
    public static void updateMaxLabScore(int labNumber, int maxLabScore) {
        assert maxLabScore >= 0;
        allMaxScores.set(labNumber, maxLabScore);
    }

    public static int getMaxLabScore(int labNumber) {
        return allMaxScores.get(labNumber);
    }

    /**
     * Updates the lab score and max lab score for this lab.
     *
     * @param labScore The updated lab score.
     * @param maxLabScore The updated max lab score.
     * @return The updated LabScore object.
     * @throws CommandException If the lab score or max lab score is invalid.
     */
    public LabScore updateBothLabScore(int labScore, int maxLabScore) throws CommandException {
        if (labScore < 0 || maxLabScore < 0) {
            throw new CommandException(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        } else if (labScore > maxLabScore) {
            throw new CommandException(
                    String.format(UpdateLabScoreCommand.MESSAGE_INVALID_MAX_SCORE,
                            maxLabScore, labScore));
        }
        System.out.println(String.format("%d/%d", labScore, maxLabScore));
        return new LabScore(labScore, maxLabScore);
    }

    /**
     * Tests if the new max score is always equal or larger than the given score.
     *
     * @param newMaxScore The new max score to test
     * @return True when the provided max score is larger or equal to current score.
     */
    public boolean testValidMaxScore(int newMaxScore) {
        return newMaxScore >= labScore;
    }

    /**
     * Refreshes the {@code LabScore} object with the updated max score.
     *
     * @param maxScore The updated max score.
     * @return Updated {@code LabScore} object.
     */
    public LabScore refreshMaxScore(int maxScore) {
        assert maxScore >= 0;
        assert maxScore >= labScore;
        return new LabScore(labScore, maxScore);
    }

    @Override
    public String toString() {
        if (labScore == -1) {
            return String.format("-/%d", maxLabScore);
        }

        return String.format("%d/%d", labScore, maxLabScore);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LabScore)) {
            return false;
        }

        LabScore o = (LabScore) other;
        return this.labScore == o.labScore
                && this.maxLabScore == o.maxLabScore;
    }
}
