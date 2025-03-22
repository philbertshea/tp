package seedu.tassist.logic.parser;

import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_OUTPUT_FILE;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_PARENT_DIRECTORY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_EXPORT_FILE_PATH_CSV;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_EXPORT_FILE_PATH_JSON;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FILE_PATH;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tassist.logic.parser.ExportDataCommandParser.MESSAGE_INVALID_PARENT_DIR;
import static seedu.tassist.logic.parser.ExportDataCommandParser.MESSAGE_MISSING_FILE;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.ExportDataCommand;

public class ExportDataCommandParserTest {
    private ExportDataCommandParser parser = new ExportDataCommandParser();

    @Test
    public void parse_validFilePathCsvExtension_success() {
        String userInput = " " + PREFIX_FILE_PATH + " " + VALID_EXPORT_FILE_PATH_CSV;
        ExportDataCommand expectedCommand =
                new ExportDataCommand(Paths.get(VALID_EXPORT_FILE_PATH_CSV));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validFileNameAndJsonExtention_success() {
        String userInput = " " + PREFIX_FILE_PATH + " " + VALID_EXPORT_FILE_PATH_JSON;
        ExportDataCommand expectedCommand =
                new ExportDataCommand(Paths.get(VALID_EXPORT_FILE_PATH_JSON));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_emptyFilePath_failure() {
        String userInput = " " + PREFIX_FILE_PATH + " ";
        String expectedMessage = ExportDataCommandParser.MESSAGE_INVALID_PATH_EMPTY;
        assertParseFailure(parser, userInput, expectedMessage);
    }

    //TODO: Parent Directory not exist failure
    @Test
    public void parse_nonExistentParentDir_failure() {
        String userInput = " " + PREFIX_FILE_PATH + " " + INVALID_PARENT_DIRECTORY;
        Path path = Paths.get(INVALID_PARENT_DIRECTORY);
        String expectedMessage = String.format(MESSAGE_INVALID_PARENT_DIR, path.getParent());
        assertParseFailure(parser, userInput, expectedMessage);
    }

    //TODO: Missing file
    @Test
    public void parse_invalidFile_failure() {
        String userInput = " " + PREFIX_FILE_PATH + " " + INVALID_OUTPUT_FILE;
        assertParseFailure(parser, userInput, MESSAGE_MISSING_FILE);
    }
}
