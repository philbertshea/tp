package seedu.tassist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_SCORE;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.commons.exceptions.IllegalValueException;
import seedu.tassist.logic.commands.UpdateLabScoreCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and create a new UpdateLabScoreCommand object.
 */
public class UpdateLabScoreCommandParser implements Parser<UpdateLabScoreCommand> {

    /**
     * Parses input arguments and create a new UpdateLabScoreCommand object.
     * @param args the given argument.
     * @return UpdateLabScoreCommand.
     * @throws ParseException If the string is not of the correct format.
     */
    public UpdateLabScoreCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_INDEX, PREFIX_LAB_NUMBER, PREFIX_LAB_SCORE);

        Index index;
        int labNumber;
        int labScore;
        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).orElse(""));
            labNumber = ParserUtil.parseLabNumber(argMultimap.getValue(PREFIX_LAB_NUMBER).orElse(""));
            labScore = ParserUtil.parseLabScore(argMultimap.getValue(PREFIX_LAB_SCORE).orElse(""));

        } catch (IllegalValueException ive) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    UpdateLabScoreCommand.MESSAGE_USAGE), ive);
        }

        return new UpdateLabScoreCommand(index, labNumber, labScore);
    }
}
