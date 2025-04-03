package seedu.tassist.logic.commands;

import seedu.tassist.model.Model;

/**
 * Toggles full or hidden view of student record.
 */
public class ToggleCommand extends Command {

    public static final String COMMAND_WORD = "toggle";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Hides or shows person particulars (contact, email, year and faculty).\n"
            + "Example: " + COMMAND_WORD;

    public static final String SHOWING_TOGGLE_MESSAGE = "Toggled view.";

    @Override
    public CommandResult execute(Model model) {
        return new CommandResult(SHOWING_TOGGLE_MESSAGE,
                false, true, false);
    }
}
