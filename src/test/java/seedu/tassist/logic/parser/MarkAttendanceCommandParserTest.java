package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_WEEK_A;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_NOT_ATTENDED;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_NO_TUTORIAL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_ON_MC;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.MarkAttendanceCommand;
import seedu.tassist.model.person.Attendance;
import seedu.tassist.model.person.TutGroup;

public class MarkAttendanceCommandParserTest {
    private MarkAttendanceCommandParser parser = new MarkAttendanceCommandParser();

    @Test
    public void parse_markAttendedValidIndexAndWeek_success() {
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A;
        MarkAttendanceCommand expectedCommand =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, VALID_WEEK_A, Attendance.ATTENDED);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_markNotAttendedValidIndexAndWeek_success() {
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_NOT_ATTENDED;
        MarkAttendanceCommand expectedCommand =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, VALID_WEEK_A, Attendance.NOT_ATTENDED);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_markOnMcValidIndexAndWeek_success() {
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_ON_MC;
        MarkAttendanceCommand expectedCommand =
                new MarkAttendanceCommand(INDEX_FIRST_PERSON, VALID_WEEK_A, Attendance.ON_MC);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_markAttendedValidTutGroupAndWeek_success() {
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_AMY + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A;
        MarkAttendanceCommand expectedCommand = new MarkAttendanceCommand(
                new TutGroup(VALID_TUT_GROUP_AMY), VALID_WEEK_A, Attendance.ATTENDED);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_markNotAttendedValidTutGroupAndWeek_success() {
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_AMY + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_NOT_ATTENDED;
        MarkAttendanceCommand expectedCommand = new MarkAttendanceCommand(
                new TutGroup(VALID_TUT_GROUP_AMY), VALID_WEEK_A, Attendance.NOT_ATTENDED);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_markOnMcValidTutGroupAndWeek_success() {
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_AMY + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_ON_MC;
        MarkAttendanceCommand expectedCommand = new MarkAttendanceCommand(
                new TutGroup(VALID_TUT_GROUP_AMY), VALID_WEEK_A, Attendance.ON_MC);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkAttendanceCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD, expectedMessage);

        // no index AND no tutorial group
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A, expectedMessage);

        // no week
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased(), expectedMessage);

        // MC and Unattended Flag together
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_ON_MC + " " + PREFIX_MARK_NOT_ATTENDED, expectedMessage);
    }

    @Test
    public void parse_conflictingFlags_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkAttendanceCommand.MESSAGE_USAGE);

        // Index, No Tutorial Flag
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_NO_TUTORIAL, expectedMessage);

        // Index, On MC and Not Attended Flag together
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_ON_MC + " " + PREFIX_MARK_NOT_ATTENDED, expectedMessage);

        // TutGroup, On MC and Not Attended Flag together
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_AMY + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_ON_MC + " " + PREFIX_MARK_NOT_ATTENDED, expectedMessage);

        // TutGroup, On MC and No Tutorial Flag together
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_AMY + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_ON_MC + " " + PREFIX_MARK_NO_TUTORIAL, expectedMessage);

        // TutGroup, Not Attended and No Tutorial Flag together
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_AMY + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_NOT_ATTENDED + " " + PREFIX_MARK_NO_TUTORIAL, expectedMessage);
    }
}
