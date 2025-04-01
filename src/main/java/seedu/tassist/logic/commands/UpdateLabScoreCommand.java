package seedu.tassist.logic.commands;

import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tassist.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.person.LabScoreList;
import seedu.tassist.model.person.Person;

/**
 * Update the lab score of a student for the specified lab.
 */
public class UpdateLabScoreCommand extends Command {

    public static final String COMMAND_WORD = "lab";

    public static final String MESSAGE_UPDATE_LAB_SCORE_SUCCESS =
            "Update lab %1$d score for student %2$d as %3$s";

    public static final String MESSAGE_UPDATE_LAB_MAX_SCORE_SUCCESS =
            "Update lab %1$d max score to be %2$d";

    public static final String MESSAGE_UPDATE_BOTH_SCORES_SUCCESS =
            "Update lab %1$d max score to be %2$d and lab %1$d score for student %3$d as %4$s";


    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Update the lab score of a student that is identified by the index"
            + "with the provided lab number and marks. \n"
            + "Mandatory Parameters: -i INDEX (must be positive integer and within valid index range)"
            + "-ln LAB NUMBER (positive integer between 1 and 4 inclusive) \n"
            + "Conditional parameters: You must have at least one of these parameters. \n"
            + "-sc LAB SCORE (updated score, must be positive integer and smaller than max score)\n"
            + "-msc MAX SCORE (must be positive integer, set max score for the lab). \n"
            + "Example: " + COMMAND_WORD + " -i 1 -ln 1 -sc 10 -msc 10\n"
            + "This update student of index 1 as lab 1 score as 10/10. \n"
            + "Note: There default max score for all labs is 25.";

    public static final String MESSAGE_INVALID_LAB_NUMBER = "This lab does not exist."
            + "There are only %1$d labs";

    public static final String MESSAGE_INVALID_SCORE =
            "The updated score cannot exceed the maximum score for the lab."
                    + "Your input: %1$d. The maximum score for this lab: %2$d.";

    public static final String MESSAGE_INVALID_MAX_SCORE =
            "The updated max score cannot be lesser than the current score for the lab."
                    + "Your input: %1$d. The current score for this lab: %2$d.";

    public static final String MESSAGE_INVALID_NEGATIVE_SCORE =
            "The score cannot be a negative number";

    public static final String MESSAGE_INVALID_INDEX =
            "This index does not exist. It exceeds the maximum number of contacts";

    private static final int UNUSED_VALUE = -1;

    /**
     * Holds all the types of updates this command can handle.
     */
    public enum UpdateType { LABSCORE, MAXLABSCORE, BOTH };

    private final Index index;
    private final int labNumber;
    private final int labScore;
    private final int maxLabScore;
    private final UpdateType updateType;

    /**
     * Updates the lab score or max lab score for the specified student for the specified lab.
     *
     * @param index The student index.
     * @param labNumber The lab for the score to update.
     * @param labScore The new score for the specified lab.
     * @param isMaxScore checks if this is updating lab score of max lab score.
     */
    public UpdateLabScoreCommand(Index index, int labNumber, int labScore, boolean isMaxScore) {
        requireAllNonNull(index, labNumber, labScore);
        this.index = index;
        this.labNumber = labNumber;
        this.labScore = isMaxScore ? UNUSED_VALUE : labScore;
        this.maxLabScore = isMaxScore ? labScore : UNUSED_VALUE;
        this.updateType = isMaxScore ? UpdateType.MAXLABSCORE : UpdateType.LABSCORE;
    }

    /**
     * Updates both lab score and max lab score for the specified student for the specified lab.
     *
     * @param index The student index.
     * @param labNumber The lab for the score to update.
     * @param labScore The new score for the specified lab.
     * @param maxLabScore The new max lab score for the specified lab.
     */
    public UpdateLabScoreCommand(Index index, int labNumber, int labScore, int maxLabScore) {
        requireAllNonNull(index, labNumber, labScore);
        this.index = index;
        this.labNumber = labNumber;
        this.labScore = labScore;
        this.maxLabScore = maxLabScore;
        updateType = UpdateType.BOTH;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_INVALID_INDEX);
        }

        Person personToUpdate = lastShownList.get(index.getZeroBased());
        LabScoreList newLabScoreList;
        String successMessage;
        switch (updateType) {
        case LABSCORE:
            newLabScoreList = personToUpdate.getLabScoreList().updateLabScore(labNumber, labScore);
            updatePerson(model, personToUpdate, newLabScoreList);
            successMessage = String.format(MESSAGE_UPDATE_LAB_SCORE_SUCCESS, labNumber, index.getOneBased(),
                    newLabScoreList.getLabScores().get(labNumber - 1).toString());
            break;
        case MAXLABSCORE:
            newLabScoreList = personToUpdate.getLabScoreList().updateMaxLabScore(labNumber, maxLabScore, lastShownList);
            updatePerson(model, personToUpdate, newLabScoreList);
            refresh(model);
            successMessage = String.format(MESSAGE_UPDATE_LAB_MAX_SCORE_SUCCESS, labNumber, maxLabScore);
            break;
        case BOTH:
            newLabScoreList = personToUpdate.getLabScoreList()
                    .updateBothLabScore(labNumber, labScore, maxLabScore, lastShownList);
            updatePerson(model, personToUpdate, newLabScoreList);
            refresh(model);
            successMessage = String.format(MESSAGE_UPDATE_BOTH_SCORES_SUCCESS, labNumber, maxLabScore,
                    index.getOneBased(), newLabScoreList.getLabScores().get(labNumber - 1).toString());
            break;
        default:
            throw new CommandException("Error");
        }



        return new CommandResult(successMessage);
    }

    /**
     * Refreshes all the contacts maxLabScore with the updated version.
     *
     * @param model The provided model.
     */
    private void refresh(Model model) {
        List<Person> lastShownList = model.getFilteredPersonList();
        for (int i = 0; i < lastShownList.size(); i++) {
            if (i == index.getZeroBased()) {
                continue;
            }
            Person personToUpdate = lastShownList.get(i);
            LabScoreList newLabScoreList = personToUpdate.getLabScoreList().refreshLabScore(labNumber, maxLabScore);
            updatePerson(model, personToUpdate, newLabScoreList);
        }

    }

    /**
     * Updates the given person data.
     *
     * @param model The provided model.
     * @param personToUpdate The person's data to update.
     * @param newLabScoreList The new list of {@code LabScore} objects.
     */
    private void updatePerson(Model model, Person personToUpdate, LabScoreList newLabScoreList) {
        Person updatedPerson = new Person(personToUpdate.getName(), personToUpdate.getPhone(),
            personToUpdate.getTeleHandle(), personToUpdate.getEmail(), personToUpdate.getMatNum(),
            personToUpdate.getTutGroup(), personToUpdate.getLabGroup(), personToUpdate.getFaculty(),
            personToUpdate.getYear(), personToUpdate.getRemark(), personToUpdate.getAttendanceList(),
            newLabScoreList, personToUpdate.getTags());
        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof UpdateLabScoreCommand)) {
            return false;
        }

        UpdateLabScoreCommand o = (UpdateLabScoreCommand) other;
        return index.equals(o.index)
                && this.labNumber == o.labNumber
                && this.labScore == o.labScore
                && this.maxLabScore == o.maxLabScore;
    }
}
