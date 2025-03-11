package seedu.tassist.logic.parser;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.commands.DeleteCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new DeleteCommand object
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    public static final String MESSAGE_DELETE_INVALID_ARGUMENTS = "Invalid arguments detected! \n%1$s";
    public static final String MESSAGE_DELETE_MISSING_ARGUMENTS = "Missing arguments! \n%1$s";

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_DELETE_MISSING_ARGUMENTS, DeleteCommand.MESSAGE_USAGE));
        }

        String[] tokens = trimmedArgs.split("\\s+");
        if (tokens.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_DELETE_INVALID_ARGUMENTS, DeleteCommand.MESSAGE_USAGE));
        }

        String indexStr = tokens[0];
        if (!indexStr.matches("\\d+")) {
            throw new ParseException(
                    String.format(MESSAGE_DELETE_INVALID_ARGUMENTS, DeleteCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(indexStr);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_DELETE_INVALID_ARGUMENTS, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }

}
