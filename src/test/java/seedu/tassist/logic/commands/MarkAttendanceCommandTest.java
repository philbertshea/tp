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
import seedu.tassist.model.person.Person;
import seedu.tassist.testutil.PersonBuilder;

public class MarkAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_markAttendanceUnfilteredList_success() {

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String newAttendanceString = "1" + firstPerson.getAttendanceList().toString().substring(1);
        Person editedPerson = new PersonBuilder(firstPerson).withAttendanceList(newAttendanceString).build();

        MarkAttendanceCommand command = new MarkAttendanceCommand(INDEX_FIRST_PERSON, 1);
        String expectedMessage = String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDED_SUCCESS,
                1, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkAttendanceCommand command = new MarkAttendanceCommand(outOfBoundIndex, 1);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of tassist book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MarkAttendanceCommand command = new MarkAttendanceCommand(outOfBoundIndex, 1);
        assertCommandFailure(command, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final MarkAttendanceCommand standardCommand = new MarkAttendanceCommand(INDEX_FIRST_PERSON, VALID_WEEK_A);

        // same values -> return true
        MarkAttendanceCommand commandWithSameValues = new MarkAttendanceCommand(INDEX_FIRST_PERSON, VALID_WEEK_A);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> return true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> return false
        assertFalse(standardCommand.equals(false));

        // different types -> return false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> return false
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(INDEX_SECOND_PERSON, VALID_WEEK_A)));

        // different week -> return false
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(INDEX_FIRST_PERSON, VALID_WEEK_B)));
    }

}
