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
import seedu.tassist.model.person.LabScoreList;

public class RedoCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void successfulRedoLabScore() {
        Operations.update(model);
        String labCommandString = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A + " "
                + PREFIX_LAB_SCORE + " " + VALID_LAB_SCORE_A;

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        LabScoreList copiedList = null;
        try {
            Command command = new AddressBookParser().parseCommand(labCommandString);
            command.execute(model);
            assertNotEquals(model.getFilteredPersonList().get(0).getLabScoreList(),
                    expectedModel.getFilteredPersonList().get(0).getLabScoreList());
            copiedList = model.getFilteredPersonList().get(0).getLabScoreList();
            runUndoCommand();
            assertEquals(model.getFilteredPersonList().get(0).getLabScoreList(),
                    expectedModel.getFilteredPersonList().get(0).getLabScoreList());
            runRedoCommand();
        } catch (CommandException | ParseException e) {
            fail();
        }

        assertEquals(model.getFilteredPersonList().get(0).getLabScoreList(), copiedList);

    }

    @Test
    public void successfulRedoEdit() {
        Operations.update(model);
        String name = "temporary";
        String editCommandString = "edit -i 1 -n " + name;
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        try {
            Command command = new AddressBookParser().parseCommand(editCommandString);
            command.execute(model);
            assertNotEquals(model.getFilteredPersonList().get(0).getName(),
                    expectedModel.getFilteredPersonList().get(0).getName());
            runUndoCommand();
            assertEquals(model.getFilteredPersonList().get(0).getName(),
                    expectedModel.getFilteredPersonList().get(0).getName());
            runRedoCommand();
        } catch (ParseException | CommandException e) {
            fail();
        }

        assertNotEquals(model.getFilteredPersonList().get(0).getName(),
                expectedModel.getFilteredPersonList().get(0).getName());
        assertEquals(model.getFilteredPersonList().get(0).getName().toString(), name);
    }

    @Test
    public void successfulRedoClear() {
        Operations.update(model);
        String clearCommandString = "clear";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        try {
            Command command = new AddressBookParser().parseCommand(clearCommandString);
            command.execute(model);
            assertTrue(model.getFilteredPersonList().isEmpty());
            runUndoCommand();
            assertEquals(model.getFilteredPersonList().size(),
                    expectedModel.getFilteredPersonList().size());
            for (int i = 0; i < expectedModel.getFilteredPersonList().size(); i++) {
                assertTrue(model.getFilteredPersonList().contains(expectedModel.getFilteredPersonList().get(i)));
            }
            runRedoCommand();
        } catch (ParseException | CommandException e) {
            fail();
        }

        assertTrue(model.getFilteredPersonList().isEmpty());
    }

    @Test
    public void successfulRedoDelete() {
        Operations.update(model);
        String deleteCommandString = "del -i 1";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        try {
            Command command = new AddressBookParser().parseCommand(deleteCommandString);
            command.execute(model);
            assertNotEquals(model.getFilteredPersonList().size(),
                    expectedModel.getFilteredPersonList().size());
            assertFalse(model.getFilteredPersonList().contains(expectedModel.getFilteredPersonList().get(0)));
            runUndoCommand();
            assertEquals(model.getFilteredPersonList().size(),
                    expectedModel.getFilteredPersonList().size());
            assertTrue(model.getFilteredPersonList().contains(expectedModel.getFilteredPersonList().get(0)));
            runRedoCommand();
        } catch (ParseException | CommandException e) {
            fail();
        }

        assertNotEquals(model.getFilteredPersonList().size(),
                expectedModel.getFilteredPersonList().size());
        assertFalse(model.getFilteredPersonList().contains(expectedModel.getFilteredPersonList().get(0)));
    }
    @Test
    public void successfulRedoAdd() {
        Operations.update(model);
        String addCommandString = "add -n Aohn Doe -e Aohnd@example.com -m A1123456J -p 98765432 -tg @johnDoe -t T01";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        try {
            Command command = new AddressBookParser().parseCommand(addCommandString);
            command.execute(model);
            assertNotEquals(model.getFilteredPersonList().size(),
                    expectedModel.getFilteredPersonList().size());
            runUndoCommand();
            assertEquals(model.getFilteredPersonList().size(),
                    expectedModel.getFilteredPersonList().size());
            runRedoCommand();
        } catch (ParseException | CommandException e) {
            fail();
        }

        assertNotEquals(model.getFilteredPersonList().size(),
                expectedModel.getFilteredPersonList().size());
    }

    @Test
    public void timelineChangeThrowRedoError() {
        Operations.update(model);
        String addCommandString = "add -n Aohn Doe -e Aohnd@example.com -m A1123456J -p 98765432 -tg @johnDoe -t T01";
        String deleteCommandString = "del -i 1";
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        try {
            Command command = new AddressBookParser().parseCommand(addCommandString);
            command.execute(model);
            runUndoCommand();
            Operations.update(model);
            command = new AddressBookParser().parseCommand(deleteCommandString);
            command.execute(model);
            runRedoCommand();
        } catch (ParseException | CommandException e) {
            fail();
        } catch (RuntimeException e) {
            // The error string was set private, and it is hard to use commandFailure for this case
            String errorMessage = "java.lang.RuntimeException: "
                    + "seedu.tassist.logic.commands.exceptions.CommandException: You have reached the limit of redo";
            assertEquals(e.toString(), errorMessage);
        }


    }


    private void runUndoCommand() {
        String undoCommandString = "undo";
        try {
            Command command = new AddressBookParser().parseCommand(undoCommandString);
            command.execute(model);
        } catch (ParseException | CommandException e) {
            throw new RuntimeException(e);
        }
    }

    private void runRedoCommand() {
        String redoCommandString = "redo";
        try {
            Command command = new AddressBookParser().parseCommand(redoCommandString);
            command.execute(model);
        } catch (ParseException | CommandException e) {
            throw new RuntimeException(e);
        }
    }
}
