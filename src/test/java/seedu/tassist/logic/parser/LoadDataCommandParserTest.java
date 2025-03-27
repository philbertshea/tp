package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_EXTENSION_CSV;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_EXTENSION_JSON;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_FILE_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EXTENSION;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FILE_PATH;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.LoadDataCommand;

public class LoadDataCommandParserTest {

    private LoadDataCommandParser parser = new LoadDataCommandParser();

    @Test
    public void parse_validFileNameAndCsvExtension_success() {
        String userInput = " " + PREFIX_FILE_PATH + " " + VALID_FILE_NAME + " "
                         + PREFIX_EXTENSION + " " + VALID_FILE_EXTENSION_CSV;
        LoadDataCommand expectedCommand =
                new LoadDataCommand(VALID_FILE_NAME, VALID_FILE_EXTENSION_CSV);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validFileNameAndJsonExtension_success() {
        String userInput = " " + PREFIX_FILE_PATH + " " + VALID_FILE_NAME + " "
            + PREFIX_EXTENSION + " " + VALID_FILE_EXTENSION_JSON;
        LoadDataCommand expectedCommand =
                new LoadDataCommand(VALID_FILE_NAME, VALID_FILE_EXTENSION_JSON);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryFields_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, LoadDataCommand.MESSAGE_USAGE);
        String noFileName = LoadDataCommand.COMMAND_WORD + " " + PREFIX_EXTENSION + VALID_FILE_EXTENSION_CSV;
        String noFileExtension = LoadDataCommand.COMMAND_WORD + " " + PREFIX_FILE_PATH + VALID_FILE_NAME;

        // missing all parameters
        assertParseFailure(parser, LoadDataCommand.COMMAND_WORD, expectedMessage);

        // missing filename
        assertParseFailure(parser, noFileName, expectedMessage);

        // missing extension
        assertParseFailure(parser, noFileExtension, expectedMessage);
    }
}
