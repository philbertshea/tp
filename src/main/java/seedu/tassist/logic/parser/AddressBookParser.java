package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.Messages.MESSAGE_UNKNOWN_COMMAND;

import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import seedu.tassist.commons.core.LogsCenter;
import seedu.tassist.logic.commands.AddCommand;
import seedu.tassist.logic.commands.ClearCommand;
import seedu.tassist.logic.commands.Command;
import seedu.tassist.logic.commands.DeleteCommand;
import seedu.tassist.logic.commands.EditCommand;
import seedu.tassist.logic.commands.ExitCommand;
import seedu.tassist.logic.commands.ExportDataCommand;
import seedu.tassist.logic.commands.HelpCommand;
import seedu.tassist.logic.commands.ListCommand;
import seedu.tassist.logic.commands.LoadDataCommand;
import seedu.tassist.logic.commands.MarkAttendanceCommand;
import seedu.tassist.logic.commands.RedoCommand;
import seedu.tassist.logic.commands.SearchCommand;
import seedu.tassist.logic.commands.TagCommand;
import seedu.tassist.logic.commands.UndoCommand;
import seedu.tassist.logic.commands.UpdateLabScoreCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.Operations;

/**
 * Parses user input.
 */
public class AddressBookParser {

    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern
            .compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final Logger logger = LogsCenter.getLogger(AddressBookParser.class);

    /**
     * Parses user input into command for execution.
     *
     * @param userInput full user input string
     * @return the command based on the user input
     * @throws ParseException if the user input does not conform the expected format
     */
    public Command parseCommand(String userInput) throws ParseException {
        Operations.resetRecording();
        final Matcher matcher = BASIC_COMMAND_FORMAT.matcher(userInput.trim());
        if (!matcher.matches()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    HelpCommand.MESSAGE_USAGE));
        }

        final String commandWord = matcher.group("commandWord");
        final String arguments = matcher.group("arguments");

        // Note to developers: Change the log level in config.json to enable lower level.
        // (i.e., FINE, FINER and lower)
        // log messages such as the one below.
        // Lower level log messages are used sparingly to minimize noise in the code.
        logger.fine("Command word: " + commandWord + "; Arguments: " + arguments);

        switch (commandWord) {

        case AddCommand.COMMAND_WORD:
            Operations.recordCurrentCommand(userInput, "Add", Operations.CommandType.RECORD);
            return new AddCommandParser().parse(arguments);

        case EditCommand.COMMAND_WORD:
            Operations.recordCurrentCommand(userInput, "Edit", Operations.CommandType.RECORD);
            return new EditCommandParser().parse(arguments);

        case DeleteCommand.COMMAND_WORD:
            Operations.recordCurrentCommand(userInput, "Delete", Operations.CommandType.RECORD);
            return new DeleteCommandParser().parse(arguments);

        case ClearCommand.COMMAND_WORD:
            Operations.recordCurrentCommand(userInput, "Clear", Operations.CommandType.RECORD);
            return new ClearCommand();

        case ListCommand.COMMAND_WORD:
            Operations.recordCurrentCommand("List", Operations.CommandType.IGNORE);
            return new ListCommand();

        case ExitCommand.COMMAND_WORD:
            return new ExitCommand();

        case HelpCommand.COMMAND_WORD:
            Operations.recordCurrentCommand("Help", Operations.CommandType.IGNORE);
            return new HelpCommand();

        case MarkAttendanceCommand.COMMAND_WORD:
            Operations.recordCurrentCommand(userInput, "Attendance" , Operations.CommandType.RECORD);
            return new MarkAttendanceCommandParser().parse(arguments);

        case SearchCommand.COMMAND_WORD:
            Operations.recordCurrentCommand("Search", Operations.CommandType.IGNORE);
            return new SearchCommandParser().parse(arguments);

        case UpdateLabScoreCommand.COMMAND_WORD:
            Operations.recordCurrentCommand(userInput, "Lab Score", Operations.CommandType.RECORD);
            return new UpdateLabScoreCommandParser().parse(arguments);

        case ExportDataCommand.COMMAND_WORD:
            Operations.recordCurrentCommand("Export data", Operations.CommandType.IGNORE);
            return new ExportDataCommandParser().parse(arguments);

        case TagCommand.COMMAND_WORD:
            Operations.recordCurrentCommand(userInput, "Tag", Operations.CommandType.RECORD);
            return new TagCommandParser().parse(arguments);

        case LoadDataCommand.COMMAND_WORD:
            Operations.recordCurrentCommand("Load data", Operations.CommandType.IGNORE);
            return new LoadDataCommandParser().parse(arguments);

        case UndoCommand.COMMAND_WORD:
            Operations.recordCurrentCommand(Operations.CommandType.UNDO);
            return new UndoCommand();

        case RedoCommand.COMMAND_WORD:
            Operations.recordCurrentCommand(Operations.CommandType.REDO);
            return new RedoCommand();

        default:
            logger.finer("This user input caused a ParseException: " + userInput);
            throw new ParseException(MESSAGE_UNKNOWN_COMMAND);
        }
    }

}
