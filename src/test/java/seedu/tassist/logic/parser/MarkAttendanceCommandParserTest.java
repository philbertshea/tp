package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.Messages.MESSAGE_INVALID_INDEX;
import static seedu.tassist.logic.Messages.MESSAGE_INVALID_INDEX_RANGE;
import static seedu.tassist.logic.Messages.MESSAGE_MULTIPLE_INDEX_ERROR;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_INDEX_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_TUT_GROUP_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_WEEK_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_B;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_C;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_WEEK_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_WEEK_B;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_NOT_ATTENDED;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_NO_TUTORIAL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_ON_MC;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_WEEK;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tassist.logic.parser.ParserUtil.MESSAGE_INVALID_WEEK;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.MarkAttendanceCommand;
import seedu.tassist.model.person.Attendance;
import seedu.tassist.model.person.TutGroup;

public class MarkAttendanceCommandParserTest {
    private MarkAttendanceCommandParser parser = new MarkAttendanceCommandParser();

    @Test
    public void parse_markAttendedValidIndexAndWeek_success() {
        // EP: One Index - No comma, No hyphen.
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A;
        MarkAttendanceCommand expectedCommand =
                new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON), VALID_WEEK_A, Attendance.ATTENDED);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + ","
                + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_B;
        expectedCommand = new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON),
                VALID_WEEK_B, Attendance.ATTENDED);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + "-"
                + INDEX_THIRD_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A;
        expectedCommand = new MarkAttendanceCommand(
                List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                VALID_WEEK_A, Attendance.ATTENDED);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma and hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + "-"
                + INDEX_SECOND_PERSON.getOneBased() + ","
                + INDEX_THIRD_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A;
        expectedCommand = new MarkAttendanceCommand(
                List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                VALID_WEEK_A, Attendance.ATTENDED);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_markNotAttendedValidIndexAndWeek_success() {
        // EP: One Index - No comma, No hyphen.
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_NOT_ATTENDED;
        MarkAttendanceCommand expectedCommand =
                new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON), VALID_WEEK_A, Attendance.NOT_ATTENDED);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + ","
                + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_B + " " + PREFIX_MARK_NOT_ATTENDED;
        expectedCommand = new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON),
                VALID_WEEK_B, Attendance.NOT_ATTENDED);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + "-"
                + INDEX_THIRD_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_NOT_ATTENDED;
        expectedCommand = new MarkAttendanceCommand(
                List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                VALID_WEEK_A, Attendance.NOT_ATTENDED);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma and hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + "-"
                + INDEX_SECOND_PERSON.getOneBased() + ","
                + INDEX_THIRD_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_NOT_ATTENDED;
        expectedCommand = new MarkAttendanceCommand(
                List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                VALID_WEEK_A, Attendance.NOT_ATTENDED);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_markOnMcValidIndexAndWeek_success() {
        // EP: One Index - No comma, No hyphen.
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_ON_MC;
        MarkAttendanceCommand expectedCommand =
                new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON), VALID_WEEK_A, Attendance.ON_MC);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + ","
                + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_B + " " + PREFIX_MARK_ON_MC;
        expectedCommand = new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON),
                VALID_WEEK_B, Attendance.ON_MC);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + "-"
                + INDEX_THIRD_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_ON_MC;
        expectedCommand = new MarkAttendanceCommand(
                List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                VALID_WEEK_A, Attendance.ON_MC);
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma and hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + "-"
                + INDEX_SECOND_PERSON.getOneBased() + ","
                + INDEX_THIRD_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_ON_MC;
        expectedCommand = new MarkAttendanceCommand(
                List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON),
                VALID_WEEK_A, Attendance.ON_MC);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_markAttendedValidTutGroupAndWeek_success() {
        // EP: One Index - No comma, No hyphen.
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A;
        MarkAttendanceCommand expectedCommand = new MarkAttendanceCommand(
                VALID_WEEK_A, Attendance.ATTENDED, List.of(new TutGroup(VALID_TUT_GROUP_A)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + ","
                + VALID_TUT_GROUP_C + " "
                + PREFIX_WEEK + " " + VALID_WEEK_B;
        expectedCommand = new MarkAttendanceCommand(VALID_WEEK_B, Attendance.ATTENDED,
                List.of(new TutGroup(VALID_TUT_GROUP_A), new TutGroup(VALID_TUT_GROUP_C)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + "-"
                + VALID_TUT_GROUP_C + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A;
        expectedCommand = new MarkAttendanceCommand(VALID_WEEK_A, Attendance.ATTENDED,
                List.of(new TutGroup(VALID_TUT_GROUP_A),
                        new TutGroup(VALID_TUT_GROUP_B), new TutGroup(VALID_TUT_GROUP_C)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma and hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + ","
                + VALID_TUT_GROUP_B + "-" + VALID_TUT_GROUP_C + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A;
        expectedCommand = new MarkAttendanceCommand(VALID_WEEK_A, Attendance.ATTENDED,
                List.of(new TutGroup(VALID_TUT_GROUP_A),
                        new TutGroup(VALID_TUT_GROUP_B), new TutGroup(VALID_TUT_GROUP_C)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_markNotAttendedValidTutGroupAndWeek_success() {
        // EP: One Index - No comma, No hyphen.
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_NOT_ATTENDED;
        MarkAttendanceCommand expectedCommand = new MarkAttendanceCommand(
                VALID_WEEK_A, Attendance.NOT_ATTENDED, List.of(new TutGroup(VALID_TUT_GROUP_A)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + ","
                + VALID_TUT_GROUP_C + " "
                + PREFIX_WEEK + " " + VALID_WEEK_B + " " + PREFIX_MARK_NOT_ATTENDED;
        expectedCommand = new MarkAttendanceCommand(VALID_WEEK_B, Attendance.NOT_ATTENDED,
                List.of(new TutGroup(VALID_TUT_GROUP_A), new TutGroup(VALID_TUT_GROUP_C)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + "-"
                + VALID_TUT_GROUP_C + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_NOT_ATTENDED;
        expectedCommand = new MarkAttendanceCommand(VALID_WEEK_A, Attendance.NOT_ATTENDED,
                List.of(new TutGroup(VALID_TUT_GROUP_A),
                        new TutGroup(VALID_TUT_GROUP_B), new TutGroup(VALID_TUT_GROUP_C)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma and hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + ","
                + VALID_TUT_GROUP_B + "-" + VALID_TUT_GROUP_C + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_NOT_ATTENDED;
        expectedCommand = new MarkAttendanceCommand(VALID_WEEK_A, Attendance.NOT_ATTENDED,
                List.of(new TutGroup(VALID_TUT_GROUP_A),
                        new TutGroup(VALID_TUT_GROUP_B), new TutGroup(VALID_TUT_GROUP_C)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_markOnMcValidTutGroupAndWeek_success() {
        // EP: One Index - No comma, No hyphen.
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_ON_MC;
        MarkAttendanceCommand expectedCommand = new MarkAttendanceCommand(
                VALID_WEEK_A, Attendance.ON_MC, List.of(new TutGroup(VALID_TUT_GROUP_A)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + ","
                + VALID_TUT_GROUP_C + " "
                + PREFIX_WEEK + " " + VALID_WEEK_B + " " + PREFIX_MARK_ON_MC;
        expectedCommand = new MarkAttendanceCommand(VALID_WEEK_B, Attendance.ON_MC,
                List.of(new TutGroup(VALID_TUT_GROUP_A), new TutGroup(VALID_TUT_GROUP_C)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + "-"
                + VALID_TUT_GROUP_C + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_ON_MC;
        expectedCommand = new MarkAttendanceCommand(VALID_WEEK_A, Attendance.ON_MC,
                List.of(new TutGroup(VALID_TUT_GROUP_A),
                        new TutGroup(VALID_TUT_GROUP_B), new TutGroup(VALID_TUT_GROUP_C)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma and hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + ","
                + VALID_TUT_GROUP_B + "-" + VALID_TUT_GROUP_C + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_ON_MC;
        expectedCommand = new MarkAttendanceCommand(VALID_WEEK_A, Attendance.ON_MC,
                List.of(new TutGroup(VALID_TUT_GROUP_A),
                        new TutGroup(VALID_TUT_GROUP_B), new TutGroup(VALID_TUT_GROUP_C)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_markNoTutValidTutGroupAndWeek_success() {
        // EP: One Index - No comma, No hyphen.
        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_NO_TUTORIAL;
        MarkAttendanceCommand expectedCommand = new MarkAttendanceCommand(
                VALID_WEEK_A, Attendance.NO_TUTORIAL, List.of(new TutGroup(VALID_TUT_GROUP_A)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + ","
                + VALID_TUT_GROUP_C + " "
                + PREFIX_WEEK + " " + VALID_WEEK_B + " " + PREFIX_MARK_NO_TUTORIAL;
        expectedCommand = new MarkAttendanceCommand(VALID_WEEK_B, Attendance.NO_TUTORIAL,
                List.of(new TutGroup(VALID_TUT_GROUP_A), new TutGroup(VALID_TUT_GROUP_C)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + "-"
                + VALID_TUT_GROUP_C + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_NO_TUTORIAL;
        expectedCommand = new MarkAttendanceCommand(VALID_WEEK_A, Attendance.NO_TUTORIAL,
                List.of(new TutGroup(VALID_TUT_GROUP_A),
                        new TutGroup(VALID_TUT_GROUP_B), new TutGroup(VALID_TUT_GROUP_C)));
        assertParseSuccess(parser, userInput, expectedCommand);

        // EP: Comma and hyphen-separated list of indexes.
        userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_A + ","
                + VALID_TUT_GROUP_B + "-" + VALID_TUT_GROUP_C + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " " + PREFIX_MARK_NO_TUTORIAL;
        expectedCommand = new MarkAttendanceCommand(VALID_WEEK_A, Attendance.NO_TUTORIAL,
                List.of(new TutGroup(VALID_TUT_GROUP_A),
                        new TutGroup(VALID_TUT_GROUP_B), new TutGroup(VALID_TUT_GROUP_C)));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidWeek_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_INVALID_WEEK + " Or, check for invalid flags.\n"
                + MarkAttendanceCommand.MESSAGE_USAGE);

        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_AMY + " "
                + INVALID_WEEK_DESC + " ";
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MESSAGE_MULTIPLE_INDEX_ERROR + "\n"
                + MESSAGE_INVALID_INDEX + "\n" + MESSAGE_INVALID_INDEX_RANGE
                + " Or, check for invalid flags.\n"
                + MarkAttendanceCommand.MESSAGE_USAGE);

        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + INVALID_INDEX_DESC + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_invalidTutGroup_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                TutGroup.MESSAGE_CONSTRAINTS + " Or, check for invalid flags.\n"
                + MarkAttendanceCommand.MESSAGE_USAGE);

        String userInput = MarkAttendanceCommand.COMMAND_WORD + " "
                + INVALID_TUT_GROUP_DESC + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkAttendanceCommand.MESSAGE_USAGE);

        // No parameters.
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD, expectedMessage);

        // No index AND no tutorial group.
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A, expectedMessage);

        // No week.
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased(), expectedMessage);

        // MC and Not Attended Flag together.
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_ON_MC + " " + PREFIX_MARK_NOT_ATTENDED, expectedMessage);
    }

    @Test
    public void parse_conflictingFlags_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MarkAttendanceCommand.MESSAGE_USAGE);

        // Index, On MC and Not Attended Flag together.
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_ON_MC + " " + PREFIX_MARK_NOT_ATTENDED, expectedMessage);

        // TutGroup, On MC and Not Attended Flag together.
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_AMY + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_ON_MC + " " + PREFIX_MARK_NOT_ATTENDED, expectedMessage);

        // TutGroup, On MC and No Tutorial Flag together.
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_AMY + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_ON_MC + " " + PREFIX_MARK_NO_TUTORIAL, expectedMessage);

        // TutGroup, Not Attended and No Tutorial Flag together.
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_AMY + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_NOT_ATTENDED + " " + PREFIX_MARK_NO_TUTORIAL, expectedMessage);

        // Index and TutGroup Flag together.
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_TUT_GROUP + " " + VALID_TUT_GROUP_AMY + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " ", expectedMessage);

        // Index, No Tutorial Flag
        assertParseFailure(parser, MarkAttendanceCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_WEEK + " " + VALID_WEEK_A + " "
                + PREFIX_MARK_NO_TUTORIAL, expectedMessage);
    }

}
