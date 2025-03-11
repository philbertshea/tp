package seedu.tassist.logic.commands;

import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.UserPrefs;

public class ExportDataCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

//    @Test
//    public void execute() {
//        assertCommandFailure(new ExportDataCommand(), model, MESSAGE_NOT_IMPLEMENTED_YET);
//    }
}
