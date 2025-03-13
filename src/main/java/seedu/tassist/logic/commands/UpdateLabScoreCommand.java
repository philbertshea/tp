package seedu.tassist.logic.commands;

import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tassist.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.Messages;
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
            "Update lab score for index %1$d lab %2$d as %3$d/25";

    //note not implementing the max score optional parameter first, depending on time
    //currently will default to max score as 25
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Update the lab score of a student that is identified by the index"
            + "with the provided lab number and marks. \n"
            + "Mandatory Parameters: -i INDEX (must be positive integer and within valid index range)"
            + "-ln LAB NUMBER (positive integer between 1 and 4 inclusive)"
            + " -sc LAB SCORE (updated score, must be positive integer and smaller than max score)\n"
            + "Optional Parameters: -msc MAX SCORE (must be positive integer, set max score for the lab). \n"
            + "Example: " + COMMAND_WORD + " -i 1 -ln 1 -sc 10 -msc 10\n"
            + "This update student of index 1 as lab 1 score as 10/10.";

    private final Index index;
    private final int labNumber;
    private final int labScore;
    private final int maxLabScore = 25; //default 25 max until optional parameter is implemented

    /**
     * Updates the lab score for the specified student for the specified lab.
     * @param index The student index.
     * @param labNumber The lab for the score to update.
     * @param labScore The new score for the specified lab.
     */
    public UpdateLabScoreCommand(Index index, int labNumber, int labScore) {
        requireAllNonNull(index, labNumber, labScore);
        this.index = index;
        this.labNumber = labNumber;
        this.labScore = labScore;
    }


    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToUpdate = lastShownList.get(index.getZeroBased());
        LabScoreList newLabScoreList = personToUpdate.getLabScoreList().updateLabScore(labNumber, labScore);
        Person updatedPerson = new Person(personToUpdate.getName(), personToUpdate.getPhone(),
                personToUpdate.getTeleHandle(), personToUpdate.getEmail(), personToUpdate.getMatNum(),
                personToUpdate.getTutGroup(), personToUpdate.getLabGroup(), personToUpdate.getFaculty(),
                personToUpdate.getYear(), personToUpdate.getRemark(), personToUpdate.getAttendanceList(),
                newLabScoreList, personToUpdate.getTags());
        model.setPerson(personToUpdate, updatedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format("%s %s", MESSAGE_UPDATE_LAB_SCORE_SUCCESS,
                Messages.format(personToUpdate)));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
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
