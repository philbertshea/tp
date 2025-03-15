package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_ARGUMENTS;
import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.Messages.MESSAGE_MISSING_ARGUMENTS;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.commands.DeleteCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a {@link DeleteCommand}.
 * Expected format: del -i (1-based integer).
 * Throws a {@link ParseException} if the input is missing, non-numeric, or incorrectly formatted.
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the DeleteCommand
     * and returns a DeleteCommand object for execution.
     *
     * @param args The user-input string, e.g. "-i 2".
     * @throws ParseException if the user input is invalid.
     */
    @Override
    public DeleteCommand parse(String args) throws ParseException {
        String trimmedArgs = args.trim();
        if (trimmedArgs.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }
        if (!trimmedArgs.startsWith("-i")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_ARGUMENTS, DeleteCommand.MESSAGE_USAGE));
        }

        String remaining = trimmedArgs.substring(2).trim();
        if (remaining.isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_MISSING_ARGUMENTS, DeleteCommand.MESSAGE_USAGE));
        }

        String[] tokens = remaining.split("\\s+");
        if (tokens.length != 1) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_ARGUMENTS, DeleteCommand.MESSAGE_USAGE));
        }

        String indexStr = tokens[0];
        if (!indexStr.matches("\\d+")) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_ARGUMENTS, DeleteCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(indexStr);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}
