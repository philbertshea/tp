package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_WEEK_A;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.MarkAttendanceCommand;

public class MarkAttendanceCommandParserTest {
    private MarkAttendanceCommandParser parser = new MarkAttendanceCommandParser();

    @Test
    public void parse_validIndexAndWeek_success() {
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A;
        MarkAttendanceCommand expectedCommand =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, VALID_WEEK_A);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkAttendanceCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A, expectedMessage);

        // no week
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON, expectedMessage);
    }
}
