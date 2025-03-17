package seedu.tassist.logic.commands;

import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tassist.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.logic.parser.ParserUtil;
import seedu.tassist.model.Model;
import seedu.tassist.model.person.Attendance;
import seedu.tassist.model.person.AttendanceList;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.TutGroup;

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

    public static final String MESSAGE_MARK_NO_TUTORIAL_SUCCESS =
            "%1$s (%2$s) has No Tutorial for Tutorial Week %3$d.";

    public static final String MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS =
            "Everyone in %1$s attended Tutorial Week %2$d.";

    public static final String MESSAGE_MARK_TUT_GROUP_UNATTENDED_SUCCESS =
            "Everyone in %1$s did not attend Tutorial Week %2$d.";

    public static final String MESSAGE_MARK_TUT_GROUP_MC_SUCCESS =
            "Everyone in %1$s on MC Tutorial Week %2$d.";

    public static final String MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS =
            "Everyone in %1$s has No Tutorial for Tutorial Week %2$d.";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of a student as identified"
            + " by the index number provided, for a particular week"
            + " as identified by the week number provided. "
            + "Existing attendance status will be overwritten by the input.\n"
            + "Conditional Parameters: Either one of the following flags must "
            + "be provided, but both flags CANNOT be provided together.\n"
            + "-i INDEX (must be a positive integer that is a valid index)\n"
            + "-t TUTORIAL GROUP (capital T followed by a positive integer)\n"
            + "Mandatory Parameters: -w WEEK NUMBER (must be a positive integer "
            + "from 1 to 13)\n"
            + "Optional Parameters: -u (mark as not attended)\n"
            + "-mc (mark as on MC)\n"
            + "-nt (mark as no tutorial)\n"
            + "Additional restrictions: 1. Either -u OR -mc OR -nt OR neither can be provided.\n"
            + "2. -nt CANNOT be provided with -i (cannot mark a single student as no tutorial).\n\n"
            + "Example: " + COMMAND_WORD + " -i 1 -w 3\n"
            + "This marks student of index 1 as attended in tutorial week 3.\n"
            + "Example: " + COMMAND_WORD + " -i 3 -w 5 -mc\n"
            + "This marks student of index 3 as on MC in tutorial week 5.\n"
            + "Example: " + COMMAND_WORD + " -t T01 -w 2 -u\n"
            + "This marks all students in tutorial group T01 as not attended in tutorial week 2.\n"
            + "Example: " + COMMAND_WORD + " -t T01 -w 13 -nt\n"
            + "This marks all students in tutorial group T01 as no tutorial in tutorial week 13.\n";

    private final Index index;

    private final TutGroup tutGroup;

    private final int week;

    private final int attendanceStatus;

    /**
     * Instantiates the MarkAttendanceCommand instance, with the provided
     * index, week and attendanceStatus.
     *
     * @param index Index of person to mark attendance for.
     * @param week Week to mark attendance of person for.
     * @param attendanceStatus New Attendance Status to set the person or tutorial group to.
     */
    public MarkAttendanceCommand(Index index, int week, int attendanceStatus) {
        requireAllNonNull(index, week, attendanceStatus);
        this.index = index;
        this.week = week;
        this.tutGroup = null;
        this.attendanceStatus = attendanceStatus;
    }

    /**
     * Instantiates the MarkAttendanceCommand instance, with the provided
     * tutGroup, week and attendanceStatus.
     *
     * @param tutGroup Tutorial group to mark attendance for. Should be null if index is not null.
     * @param week Week to mark attendance of person for.
     * @param attendanceStatus New Attendance Status to set the person or tutorial group to.
     */
    public MarkAttendanceCommand(TutGroup tutGroup, int week, int attendanceStatus) {
        requireAllNonNull(tutGroup, week, attendanceStatus);
        this.index = null;
        this.week = week;
        this.tutGroup = tutGroup;
        this.attendanceStatus = attendanceStatus;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {

        List<Person> lastShownList = model.getFilteredPersonList();
        List<Person> personsToEdit;
        StringBuilder successMessage = new StringBuilder();

        if (tutGroup != null) {
            personsToEdit = ParserUtil.getPersonsInTutorialGroup(lastShownList, tutGroup);
            successMessage.append(generateSuccessMessage(tutGroup)).append("\n-------------\n");
        } else {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            personsToEdit = new ArrayList<>();
            Person personToEdit = lastShownList.get(index.getZeroBased());
            personsToEdit.add(personToEdit);
        }

        for (Person personToEdit : personsToEdit) {
            AttendanceList newAttendanceList =
                    personToEdit.getAttendanceList().setAttendanceForWeek(this.week, this.attendanceStatus);

            Person editedPerson = new Person(
                    personToEdit.getName(), personToEdit.getPhone(), personToEdit.getTeleHandle(),
                    personToEdit.getEmail(), personToEdit.getMatNum(), personToEdit.getTutGroup(),
                    personToEdit.getLabGroup(), personToEdit.getFaculty(), personToEdit.getYear(),
                    personToEdit.getRemark(), newAttendanceList, personToEdit.getLabScoreList(),
                    personToEdit.getTags());

            model.setPerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            successMessage.append(generateSuccessMessage(editedPerson)).append("\n");
        }

        return new CommandResult(successMessage.toString());

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
        case Attendance.NO_TUTORIAL:
            message = MESSAGE_MARK_NO_TUTORIAL_SUCCESS;
            break;
        default:
            message = MESSAGE_USAGE;
        }

        return String.format(message,
                personToEdit.getName(),
                personToEdit.getMatNum(),
                this.week);
    }

    /**
     * Generates a command execution success message for
     * {@code personToEdit}.
     *
     * @return String with the Success Message once MarkAttendanceCommand
     *         is executed successfully.
     */
    private String generateSuccessMessage(TutGroup tutGroupToEdit) {
        String message = "";
        switch (attendanceStatus) {
        case Attendance.ATTENDED:
            message = MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS;
            break;
        case Attendance.NOT_ATTENDED:
            message = MESSAGE_MARK_TUT_GROUP_UNATTENDED_SUCCESS;
            break;
        case Attendance.ON_MC:
            message = MESSAGE_MARK_TUT_GROUP_MC_SUCCESS;
            break;
        case Attendance.NO_TUTORIAL:
            message = MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS;
            break;
        default:
            message = MESSAGE_USAGE;
        }

        return String.format(message,
                tutGroupToEdit.toString(),
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
        if (this.week != e.week || this.attendanceStatus != e.attendanceStatus) {
            return false;
        }

        if (this.index == null && this.tutGroup == null) {
            return e.index == null && e.tutGroup == null;
        } else if (this.index == null) {
            return e.index == null && this.tutGroup.equals(e.tutGroup);
        } else if (this.tutGroup == null) {
            return e.tutGroup == null && this.index.equals(e.index);
        } else {
            return this.index.equals(e.index) && this.tutGroup.equals(e.tutGroup);
        }
    }

}
