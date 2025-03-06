package seedu.address.logic.commands;

import seedu.address.model.Model;

/**
 * Marks the attendance of a person within an existing week.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "att";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult("test");
    }

}
