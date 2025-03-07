package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK_B;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.address.logic.commands.MarkAttendanceCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.testutil.PersonBuilder;

public class MarkAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_markAttendanceUnfilteredList_success() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson).withAttendanceList("1000000000000").build();

        MarkAttendanceCommand command = new MarkAttendanceCommand(INDEX_FIRST_PERSON, 1);
        String expectedMessage = String.format(MarkAttendanceCommand.MESSAGE_MARK_ATTENDED_SUCCESS,
                1, Messages.format(editedPerson));

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(firstPerson, editedPerson);

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

        // ensures that outOfBoundIndex is still in bounds of address book list
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
