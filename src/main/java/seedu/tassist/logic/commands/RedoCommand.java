package seedu.tassist.logic.commands;

import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.Operations;

/**
 * Redoes the last command.
 */
public class RedoCommand extends Command {

    public static final String COMMAND_WORD = "redo";

    public static final String MESSAGE_REDO_SUCCESS = "Redo %1$s successfully";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        String response = Operations.redo(model);

        return new CommandResult(String.format(MESSAGE_REDO_SUCCESS, response));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof UpdateLabScoreCommand)) {
            return false;
        }

        return true;
    }
}
