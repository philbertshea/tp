package seedu.tassist.model.person;

import java.util.ArrayList;
import java.util.List;

import seedu.tassist.logic.commands.UpdateLabScoreCommand;
import seedu.tassist.logic.commands.exceptions.CommandException;


/**
 * Holds the score and max score for a lab.
 */
public class LabScore {
    //Default max score is 25.
    private static ArrayList<Integer> maxLabScores = new ArrayList<Integer>(List.of(25, 25, 25, 25));

    private int labScore;

    private int labNo;


    public LabScore() {
        labScore = -1;
    }

    /**
     * Creates a LabScore object.
     *
     * @param labScore the score to initialize with.
     * @param labNo the lab number that this score is pointing to.
     */
    public LabScore(int labScore, int labNo) {
        this.labScore = labScore;
        this.labNo = labNo;
    }

    /**
     * Creates a LabScore object from save file
     *
     * @param labScore the score to initialize with.
     * @param maxLabScore the max lab score to update.
     * @param labNo the lab number that this score is pointing to.
     */
    public LabScore(int labScore, int maxLabScore, int labNo) {
        this.labScore = labScore;
        this.labNo = labNo;
        maxLabScores.set(labNo, maxLabScore);
    }


    /**
     * Updates the lab score for this lab.
     *
     * @param labScore The updated lab score.
     * @return The updated LabScore object.
     * @throws CommandException If the lab score is invalid.
     */
    public LabScore updateLabScore(int labScore, int labNo) throws CommandException {
        if (labScore > maxLabScores.get(labNo)) {
            throw new CommandException(
                    String.format(UpdateLabScoreCommand.MESSAGE_INVALID_SCORE,
                            labScore, maxLabScores.get(labNo)));
        } else if (labScore < 0) {
            throw new CommandException(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        }
        return new LabScore(labScore, labNo);
    }

    /**
     * Updates the max lab score for this lab.
     *
     * @param maxLabScore The updated max lab score.
     * @return The updated LabScore object.
     * @throws CommandException If the lab score is invalid.
     */
    public LabScore updateMaxLabScore(int maxLabScore, int labNo) throws CommandException {
        if (labScore > maxLabScore && labScore != -1) {
            throw new CommandException(
                    String.format(UpdateLabScoreCommand.MESSAGE_INVALID_MAX_SCORE,
                            maxLabScore, labScore));
        } else if (maxLabScore < 0) {
            throw new CommandException(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        }
        maxLabScores.set(labNo, maxLabScore);
        return new LabScore(labScore, labNo);
    }

    /**
     * Updates the lab score and max lab score for this lab.
     *
     * @param labScore The updated lab score.
     * @param maxLabScore The updated max lab score.
     * @return The updated LabScore object.
     * @throws CommandException If the lab score or max lab score is invalid.
     */
    public LabScore updateBothLabScore(int labScore, int maxLabScore, int labNo) throws CommandException {
        if (labScore < 0 || maxLabScore < 0) {
            throw new CommandException(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        } else if (labScore > maxLabScore) {
            throw new CommandException(
                    String.format(UpdateLabScoreCommand.MESSAGE_INVALID_MAX_SCORE,
                            maxLabScore, labScore));
        }
        updateMaxLabScore(maxLabScore, labNo);
        return updateLabScore(labScore, labNo);
    }

    @Override
    public String toString() {
        if (labScore == -1) {
            return "-";
        }

        return String.format("%d/%d", labScore, maxLabScores.get(labNo));
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
                && this.labNo == o.labNo;
    }
}
