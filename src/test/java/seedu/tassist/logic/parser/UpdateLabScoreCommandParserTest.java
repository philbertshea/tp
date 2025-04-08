package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.Messages.MESSAGE_INVALID_INDEX;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_NUMBER_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_SCORE_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_SCORE_B;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_SCORE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAX_LAB_SCORE;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.UpdateLabScoreCommand;
import seedu.tassist.model.person.LabScoreList;

public class UpdateLabScoreCommandParserTest {
    private UpdateLabScoreCommandParser parser = new UpdateLabScoreCommandParser();

    @Test
    public void successCaseLabScore() {
        // EP: valid command string
        String userInput = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A + " "
                + PREFIX_LAB_SCORE + " " + VALID_LAB_SCORE_A;
        UpdateLabScoreCommand parsed =
                new UpdateLabScoreCommand(INDEX_FIRST_PERSON, VALID_LAB_NUMBER_A, VALID_LAB_SCORE_A, false);
        assertParseSuccess(parser, userInput, parsed);
    }

    @Test
    public void successCaseMaxLabScore() {
        // EP: valid command string
        String userInput = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A + " "
                + PREFIX_MAX_LAB_SCORE + " " + VALID_LAB_SCORE_A;
        UpdateLabScoreCommand parsed =
                new UpdateLabScoreCommand(INDEX_FIRST_PERSON, VALID_LAB_NUMBER_A, VALID_LAB_SCORE_A, true);
        assertParseSuccess(parser, userInput, parsed);
    }

    @Test
    public void successCaseBothLabScores() {
        // EP: valid command string
        String userInput = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A + " "
                + PREFIX_LAB_SCORE + " " + VALID_LAB_SCORE_A + " "
                + PREFIX_MAX_LAB_SCORE + " " + VALID_LAB_SCORE_B;
        UpdateLabScoreCommand parsed =
                new UpdateLabScoreCommand(INDEX_FIRST_PERSON, VALID_LAB_NUMBER_A, VALID_LAB_SCORE_A, VALID_LAB_SCORE_B);
        assertParseSuccess(parser, userInput, parsed);
    }

    @Test
    public void testMissingField() {
        // EP: invalid command string (missing field)
        String missingIndex = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A + " "
                + PREFIX_LAB_SCORE + " " + VALID_LAB_SCORE_A;

        assertParseFailure(parser, missingIndex, MESSAGE_INVALID_INDEX);


        String labErrorMessage = String.format(LabScoreList.LAB_NUMBER_CONSTRAINT);

        String missingLabNumber = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_SCORE + " " + VALID_LAB_SCORE_A;

        assertParseFailure(parser, missingLabNumber, labErrorMessage);


        String missingFieldMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateLabScoreCommand.MESSAGE_USAGE);

        String missingLabScore = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A;
        assertParseFailure(parser, missingLabScore, missingFieldMessage);
    }

    @Test
    public void testInvalidScore() {
        String invalidScoreValue = "text";

        // EP: invalid score (string instead of int)
        String invalidScore = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A + " "
                + PREFIX_LAB_SCORE + " " + invalidScoreValue;

        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateLabScoreCommand.MESSAGE_USAGE);

        assertParseFailure(parser, invalidScore, errorMessage);

        // EP: invalid max score (string instead of int)
        String invalidMaxScore = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A + " "
                + PREFIX_MAX_LAB_SCORE + " " + invalidScoreValue;

        assertParseFailure(parser, invalidMaxScore, errorMessage);
    }
}
