package seedu.tassist.model.person;

import seedu.tassist.logic.commands.UpdateLabScoreCommand;
import seedu.tassist.logic.commands.exceptions.CommandException;

/**
 * Holds the score and max score for a lab.
 */
public class LabScore {
    private int labScore;
    private int maxLabScore = 25;

    public LabScore() {
        labScore = -1;
    }

    /**
     * Creates a LabScore object.
     * @param labScore the score to initialize with.
     * @param maxLabScore the max score of the lab to initialize.
     */
    public LabScore(int labScore, int maxLabScore) {
        this.labScore = labScore;
        this.maxLabScore = maxLabScore;
    }

    /**
     * Updates the lab score for this lab.
     * @param labScore the updated lab score.
     */
    public void updateLabScore(int labScore) throws CommandException {
        if (labScore > maxLabScore) {
            throw new CommandException(
                    String.format(UpdateLabScoreCommand.MESSAGE_INVALID_SCORE, labScore, maxLabScore));
        }
        this.labScore = labScore;
    }

    @Override
    public String toString() {
        if (labScore == -1) {
            return "-";
        }

        return String.format("%d/%d", labScore, maxLabScore);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LabScore)) {
            return false;
        }

        LabScore o = (LabScore) other;
        return this.labScore == o.labScore
                && this.maxLabScore == o.maxLabScore;
    }
}
