package seedu.tassist.logic.commands;

import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tassist.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.person.Attendance;
import seedu.tassist.model.person.AttendanceList;
import seedu.tassist.model.person.Person;

/**
 * Marks the attendance of a student within an existing week.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "att";

    public static final String MESSAGE_MARK_ATTENDED_SUCCESS =
            "%1$s (%2$s) attended Tutorial Week %3$d.";

    public static final String MESSAGE_MARK_UNATTENDED_SUCCESS =
            "%1$s (%2$s) did not attend Tutorial Week %3$d.";

    public static final String MESSAGE_MARK_MC_SUCCESS =
            "%1$s (%2$s) on MC for Tutorial Week %3$d.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of a student as identified"
            + " by the index number provided, for a particular week"
            + " as identified by the week number provided. "
            + "Existing attendance status will be overwritten by the input.\n"
            + "Mandatory Parameters: -i INDEX (must be a positive integer "
            + "that is a valid index)"
            + " -w WEEK NUMBER (must be a positive integer from 1 to 13)\n"
            + "Optional Parameters: -u (mark as not attended) "
            + "-mc (mark as on MC)\n"
            + "Note that either -u OR -mc OR neither can be provided."
            + "Example: " + COMMAND_WORD + " -i 1 -w 3\n"
            + "This marks student of index 1 as attended in tutorial week 3.";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Week: %2$d";

    private final Index index;

    private final int week;

    private final int attendanceStatus;

    /**
     * Instantiates the MarkAttendanceCommand instance, with the provided
     * index and week.
     *
     * @param index Index of person to be marked attendance for.
     * @param week Week to mark attendance of person for.
     */
    public MarkAttendanceCommand(Index index, int week, int attendanceStatus) {
        requireAllNonNull(index, week, attendanceStatus);
        this.index = index;
        this.week = week;
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        AttendanceList newAttendanceList =
                personToEdit.getAttendanceList()
                        .setAttendanceForWeek(this.week, this.attendanceStatus);

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getTeleHandle(),
                personToEdit.getEmail(), personToEdit.getMatNum(), personToEdit.getTutGroup(),
                personToEdit.getLabGroup(), personToEdit.getFaculty(), personToEdit.getYear(),
                personToEdit.getRemark(), newAttendanceList, personToEdit.getLabScoreList(),
                personToEdit.getTags());

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates a command execution success message for
     * {@code personToEdit}.
     *
     * @return String with the Success Message once MarkAttendanceCommand
     *         is executed successfully.
     */
    private String generateSuccessMessage(Person personToEdit) {
        String message = "";
        switch (attendanceStatus) {
        case Attendance.ATTENDED:
            message = MESSAGE_MARK_ATTENDED_SUCCESS;
            break;
        case Attendance.NOT_ATTENDED:
            message = MESSAGE_MARK_UNATTENDED_SUCCESS;
            break;
        case Attendance.ON_MC:
            message = MESSAGE_MARK_MC_SUCCESS;
            break;
        default:
            message = MESSAGE_USAGE;
        }

        return String.format(message,
                personToEdit.getName(),
                personToEdit.getMatNum(),
                this.week);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkAttendanceCommand)) {
            return false;
        }

        MarkAttendanceCommand e = (MarkAttendanceCommand) other;
        return this.index.equals(e.index)
                && this.week == e.week
                && this.attendanceStatus == e.attendanceStatus;
    }

}
