package seedu.tassist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.commons.exceptions.IllegalValueException;
import seedu.tassist.logic.commands.MarkAttendanceCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand object.
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     *
     * @param args String input to be parsed
     * @return MarkAttendanceCommand corresponding to the String input
     * @throws ParseException if the user input does not conform to the expected format
     */
    public MarkAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_INDEX, PREFIX_WEEK);

        Index index;
        int week;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).orElse(""));
            week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).orElse(""));
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MarkAttendanceCommand.MESSAGE_USAGE), ive
            );
        }

        return new MarkAttendanceCommand(index, week);
    }
}
