package seedu.tassist.logic.commands;

import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tassist.logic.commands.ToggleCommand.SHOWING_TOGGLE_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;

public class ToggleCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_toggle_success() {
        CommandResult expectedCommandResult = new CommandResult(SHOWING_TOGGLE_MESSAGE, false,
                true, false);
        assertCommandSuccess(new ToggleCommand(), model, expectedCommandResult, expectedModel);
    }
}
