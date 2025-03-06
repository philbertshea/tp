package seedu.address.logic.commands;

import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Marks the attendance of a student within an existing week.
 */
public class MarkAttendanceCommand extends Command {

    public static final String COMMAND_WORD = "att";

    // Note that -u and -mc will NOT be implemented yet.
    // We will settle the mandatory parameters first.

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Marks the attendance of a student as identified"
            + " by the index number provided, for a particular week"
            + " as identified by the week number provided. "
            + "Existing attendance status will be overwritten by the input.\n"
            + "Mandatory Parameters: -i INDEX (must be a positive integer "
            + "that is a valid index)"
            + " -w WEEK NUMBER (must be a positive integer from 1 to 13)\n"
            + "Optional Parameters: -u (mark as not attended) "
            + "-mc (mark as on MC)\n"
            + "Note that either -u OR -mc OR neither can be provided."
            + "Example: " + COMMAND_WORD + " -i 1 -w 3\n"
            + "This marks student of index 1 as attended in tutorial week 3.";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Week: %2$d";

    private final Index index;

    private final int week;

    public MarkAttendanceCommand(Index index, int week) {
        requireAllNonNull(index, week);
        this.index = index;
        this.week = week;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        throw new CommandException(
                String.format(MESSAGE_ARGUMENTS, index.getOneBased(), week)
        );
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof MarkAttendanceCommand)) {
            return false;
        }

        MarkAttendanceCommand e = (MarkAttendanceCommand) other;
        return this.index.equals(e.index) && this.week == e.week;
    }

}
