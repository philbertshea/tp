package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_NUMBER_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_SCORE_A;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_SCORE;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.logic.parser.AddressBookParser;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.Operations;
import seedu.tassist.model.UserPrefs;

public class UndoCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void successfulUndoLabScore() {
        Operations.update(model);
        String labCommandString = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A + " "
                + PREFIX_LAB_SCORE + " " + VALID_LAB_SCORE_A;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        try {
            Command command = new AddressBookParser().parseCommand(labCommandString);
            command.execute(model);
            assertNotEquals(model.getFilteredPersonList().get(0).getLabScoreList(),
                    expectedModel.getFilteredPersonList().get(0).getLabScoreList());
            UndoCommand undoCommand = new UndoCommand();
            undoCommand.execute(model);
        } catch (CommandException | ParseException e) {
            fail();
        }

        assertEquals(model.getFilteredPersonList().get(0).getLabScoreList(),
                expectedModel.getFilteredPersonList().get(0).getLabScoreList());
    }

    @Test
    public void successfulUndoEdit() {
        Operations.update(model);
        String editCommandString = "edit -i 1 -n temporary";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        try {
            Command command = new AddressBookParser().parseCommand(editCommandString);
            command.execute(model);
            assertNotEquals(model.getFilteredPersonList().get(0).getName(),
                    expectedModel.getFilteredPersonList().get(0).getName());
            UndoCommand undoCommand = new UndoCommand();
            undoCommand.execute(model);
        } catch (ParseException | CommandException e) {
            fail();
        }

        assertEquals(model.getFilteredPersonList().get(0).getName(),
                expectedModel.getFilteredPersonList().get(0).getName());
    }

    @Test
    public void successfulUndoClear() {
        Operations.update(model);
        String clearCommandString = "clear";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        try {
            Command command = new AddressBookParser().parseCommand(clearCommandString);
            command.execute(model);
            assertNotEquals(model.getFilteredPersonList().size(),
                    expectedModel.getFilteredPersonList().size());
            UndoCommand undoCommand = new UndoCommand();
            undoCommand.execute(model);
        } catch (ParseException | CommandException e) {
            fail();
        }

        assertEquals(model.getFilteredPersonList().size(),
                expectedModel.getFilteredPersonList().size());
        for (int i = 0; i < expectedModel.getFilteredPersonList().size(); i++) {
            assertTrue(model.getFilteredPersonList().contains(expectedModel.getFilteredPersonList().get(i)));
        }
    }

    @Test
    public void successfulUndoDelete() {
        Operations.update(model);
        String clearCommandString = "del -i 1";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        try {
            Command command = new AddressBookParser().parseCommand(clearCommandString);
            command.execute(model);
            assertNotEquals(model.getFilteredPersonList().size(),
                    expectedModel.getFilteredPersonList().size());
            assertFalse(model.getFilteredPersonList().contains(expectedModel.getFilteredPersonList().get(0)));
            UndoCommand undoCommand = new UndoCommand();
            undoCommand.execute(model);
        } catch (ParseException | CommandException e) {
            fail();
        }

        assertEquals(model.getFilteredPersonList().size(),
                expectedModel.getFilteredPersonList().size());
        assertTrue(model.getFilteredPersonList().contains(expectedModel.getFilteredPersonList().get(0)));
    }

    @Test
    public void successfulUndoAdd() {
        Operations.update(model);
        String addCommandString = "add -n Aohn Doe -e Aohnd@example.com -m A1123456J -p 98765432 -tg @johnDoe -t T01";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        try {
            Command command = new AddressBookParser().parseCommand(addCommandString);
            command.execute(model);
            assertNotEquals(model.getFilteredPersonList().size(),
                    expectedModel.getFilteredPersonList().size());
            UndoCommand undoCommand = new UndoCommand();
            undoCommand.execute(model);
        } catch (ParseException | CommandException e) {
            fail();
        }

        assertEquals(model.getFilteredPersonList().size(),
                expectedModel.getFilteredPersonList().size());
    }
}
