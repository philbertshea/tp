package seedu.tassist.logic.commands;

import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.Operations;


public class UndoCommand extends Command {

    public static final String COMMAND_WORD = "undo";

    public static final String MESSAGE_UNDO_SUCCESS = "Undo %1$s successfully";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        System.out.println("running here ");
        String response = Operations.undo(model);

        return new CommandResult(String.format(MESSAGE_UNDO_SUCCESS, response));
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
