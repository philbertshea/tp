package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_WEEK_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_WEEK_B;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tassist.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.Messages;
import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.UserPrefs;
import seedu.tassist.model.person.Attendance;
import seedu.tassist.model.person.Person;
import seedu.tassist.testutil.PersonBuilder;

public class MarkAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    public String getReplacedIndexAndNewString(
            String newStatus, String existingAttendanceString,
            int firstReplacementIndex, int secondReplacementIndex, int thirdReplacementIndex) {

        boolean firstReplacementFound = firstReplacementIndex != -1;
        boolean secondReplacementFound = secondReplacementIndex != -1;
        boolean thirdReplacementFound = thirdReplacementIndex != -1;
        boolean noReplacementFound = !firstReplacementFound
                && !secondReplacementFound
                && !thirdReplacementFound;

        if (noReplacementFound) {
            return "0" + existingAttendanceString;
        }

        int replacementIndex = 0;
        if (firstReplacementFound) {
            replacementIndex = firstReplacementIndex;
        } else if (secondReplacementFound) {
            replacementIndex = secondReplacementIndex;
        } else if (thirdReplacementFound) {
            replacementIndex = thirdReplacementIndex;
        }

        return replacementIndex
                + existingAttendanceString.substring(0, replacementIndex)
                + newStatus
                + existingAttendanceString.substring(replacementIndex + 1);

    }

    @Test
    public void execute_markAttendedUnfilteredList_success() {

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String existingAttendanceString = firstPerson.getAttendanceList().toString();
        String replacedIndexAndNewString = getReplacedIndexAndNewString(
                String.valueOf(Attendance.ATTENDED), existingAttendanceString,
                existingAttendanceString.indexOf(String.valueOf(Attendance.NOT_ATTENDED)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.ON_MC)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.NO_TUTORIAL)));

        String newAttendanceString = replacedIndexAndNewString.substring(1);
        int replacedIndex = Integer.parseInt(replacedIndexAndNewString.substring(0, 1)) + 1;
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAttendanceList(newAttendanceString).build();
        MarkAttendanceCommand command =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, replacedIndex, Attendance.ATTENDED);

        String expectedMessage = String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDED_SUCCESS,
                editedPerson.getName(), editedPerson.getMatNum(), replacedIndex);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_markUnattendedUnfilteredList_success() {

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String existingAttendanceString = firstPerson.getAttendanceList().toString();
        String replacedIndexAndNewString = getReplacedIndexAndNewString(
                String.valueOf(Attendance.NOT_ATTENDED), existingAttendanceString,
                existingAttendanceString.indexOf(String.valueOf(Attendance.ATTENDED)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.ON_MC)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.NO_TUTORIAL)));

        String newAttendanceString = replacedIndexAndNewString.substring(1);
        int replacedIndex = Integer.parseInt(replacedIndexAndNewString.substring(0, 1)) + 1;
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAttendanceList(newAttendanceString).build();
        MarkAttendanceCommand command =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, replacedIndex, Attendance.NOT_ATTENDED);

        String expectedMessage = String.format(MarkAttendanceCommand.MESSAGE_MARK_UNATTENDED_SUCCESS,
                editedPerson.getName(), editedPerson.getMatNum(), replacedIndex);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_markOnMcUnfilteredList_success() {

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String existingAttendanceString = firstPerson.getAttendanceList().toString();
        String replacedIndexAndNewString = getReplacedIndexAndNewString(
                String.valueOf(Attendance.ON_MC), existingAttendanceString,
                existingAttendanceString.indexOf(String.valueOf(Attendance.NOT_ATTENDED)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.ATTENDED)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.NO_TUTORIAL)));

        String newAttendanceString = replacedIndexAndNewString.substring(1);
        int replacedIndex = Integer.parseInt(replacedIndexAndNewString.substring(0, 1)) + 1;
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAttendanceList(newAttendanceString).build();
        MarkAttendanceCommand command =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, replacedIndex, Attendance.ON_MC);

        String expectedMessage = String.format(MarkAttendanceCommand.MESSAGE_MARK_MC_SUCCESS,
                editedPerson.getName(), editedPerson.getMatNum(), replacedIndex);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_markNoTutorialUnfilteredList_success() {

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String existingAttendanceString = firstPerson.getAttendanceList().toString();
        String replacedIndexAndNewString = getReplacedIndexAndNewString(
                String.valueOf(Attendance.NO_TUTORIAL), existingAttendanceString,
                existingAttendanceString.indexOf(String.valueOf(Attendance.NOT_ATTENDED)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.ATTENDED)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.ON_MC)));

        String newAttendanceString = replacedIndexAndNewString.substring(1);
        int replacedIndex = Integer.parseInt(replacedIndexAndNewString.substring(0, 1)) + 1;
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAttendanceList(newAttendanceString).build();
        MarkAttendanceCommand command =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, replacedIndex, Attendance.NO_TUTORIAL);

        String expectedMessage = String.format(MarkAttendanceCommand.MESSAGE_MARK_NO_TUTORIAL_SUCCESS,
                editedPerson.getName(), editedPerson.getMatNum(), replacedIndex);

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkAttendanceCommand command = new MarkAttendanceCommand(outOfBoundIndex, 1, Attendance.ATTENDED);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MarkAttendanceCommand command = new MarkAttendanceCommand(outOfBoundIndex, 1, Attendance.ATTENDED);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final MarkAttendanceCommand standardCommand =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, VALID_WEEK_A, Attendance.ATTENDED);

        // same values -> return true
        MarkAttendanceCommand commandWithSameValues =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, VALID_WEEK_A, Attendance.ATTENDED);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> return true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> return false
        assertFalse(standardCommand.equals(false));

        // different types -> return false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> return false
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(
                INDEX_SECOND_PERSON, VALID_WEEK_A, Attendance.ATTENDED)));

        // different week -> return false
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(
                INDEX_FIRST_PERSON, VALID_WEEK_B, Attendance.ATTENDED)));

        // different attendanceStatus -> return false
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(
                INDEX_FIRST_PERSON, VALID_WEEK_B, Attendance.NOT_ATTENDED)));
    }

}
