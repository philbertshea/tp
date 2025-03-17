package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_ARGUMENTS;
import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.Messages.MESSAGE_MISSING_ARGUMENTS;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.commands.DeleteCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a {@link DeleteCommand}.
 * Expected format: del -i (1-based integer).
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    /**
     * Parses {@code userInput} in the context of the DeleteCommand, returning a {@code DeleteCommand} for execution.
     * <p>
     * If the user input is invalid or missing the {@code -i} flag, a {@link ParseException} is thrown.
     *
     * @param userInput the raw string following "delete", e.g. "-i 3".
     * @return a {@code DeleteCommand} with the parsed {@code Index}.
     * @throws ParseException if the input does not conform to the required format.
     */
    @Override
    public DeleteCommand parse(String userInput) throws ParseException {

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(userInput, PREFIX_INDEX);

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent()
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteCommand.MESSAGE_USAGE));
        }

        String indexStr = argMultimap.getValue(PREFIX_INDEX).orElse("");
        if (indexStr.isBlank()) {
            throw new ParseException(
                    String.format(MESSAGE_MISSING_ARGUMENTS, DeleteCommand.MESSAGE_USAGE));
        }

        try {
            Index index = ParserUtil.parseIndex(indexStr);
            return new DeleteCommand(index);
        } catch (ParseException pe) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_ARGUMENTS, DeleteCommand.MESSAGE_USAGE), pe);
        }
    }
}