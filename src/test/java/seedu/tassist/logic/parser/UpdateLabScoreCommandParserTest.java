package seedu.tassist.logic.parser;

import org.junit.jupiter.api.Test;
import seedu.tassist.logic.commands.UpdateLabScoreCommand;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_NUMBER_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_SCORE_A;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_SCORE;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

public class UpdateLabScoreCommandParserTest {
    private UpdateLabScoreCommandParser parser = new UpdateLabScoreCommandParser();

    @Test
    public void SuccessCase() {
        String userInput = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A + " "
                + PREFIX_LAB_SCORE + " " + VALID_LAB_SCORE_A;
        UpdateLabScoreCommand parsed =
                new UpdateLabScoreCommand(INDEX_FIRST_PERSON, VALID_LAB_NUMBER_A, VALID_LAB_SCORE_A);
        assertParseSuccess(parser, userInput, parsed);
    }

    @Test
    public void TestMissingField(){
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                UpdateLabScoreCommand.MESSAGE_USAGE);

        String missingIndex = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A + " "
                + PREFIX_LAB_SCORE + " " + VALID_LAB_SCORE_A;

        assertParseFailure(parser, missingIndex, errorMessage);

        String missingLabNumber = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_SCORE + " " + VALID_LAB_SCORE_A;
        assertParseFailure(parser, missingLabNumber, errorMessage);

        String missingLabScore = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A;
        assertParseFailure(parser, missingLabScore, errorMessage);
    }
}
