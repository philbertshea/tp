package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK_A;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEEK_B;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.MarkAttendanceCommand.MESSAGE_ARGUMENTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

public class MarkAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute() {
        assertCommandFailure(
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, VALID_WEEK_A),
                model,
                String.format(MESSAGE_ARGUMENTS, INDEX_FIRST_PERSON.getOneBased(), VALID_WEEK_A)
        );
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
