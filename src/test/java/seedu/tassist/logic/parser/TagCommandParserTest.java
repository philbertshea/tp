package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.commands.TagCommand.MESSAGE_USAGE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EDIT_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.TagCommandParser.MESSAGE_INVALID_ACTION_TYPE;
import static seedu.tassist.logic.parser.TagCommandParser.MESSAGE_MISSING_OLD_NEW_TAG;
import static seedu.tassist.logic.parser.TagCommandParser.MESSAGE_MISSING_TAG;

import org.junit.jupiter.api.Test;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.model.tag.Tag;

public class TagCommandParserTest {

    private static final String INVALID_ACTION_TYPE = "-e";
    private TagCommandParser parser = new TagCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // No action type specified
        assertParseFailure(parser, " " + PREFIX_INDEX + " 1",
                String.format(MESSAGE_INVALID_ACTION_TYPE, MESSAGE_USAGE));

        // No index specified
        assertParseFailure(parser, " " + PREFIX_ADD_TAG + " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // No tags specified for add
        assertParseFailure(parser, " " + PREFIX_ADD_TAG + " " + PREFIX_INDEX + " 1",
                String.format(MESSAGE_MISSING_TAG, MESSAGE_USAGE));

        // No tags specified for edit
        assertParseFailure(parser, " " + PREFIX_EDIT_TAG + " " + PREFIX_INDEX + " 1",
                String.format(MESSAGE_MISSING_TAG, MESSAGE_USAGE));

        // Not tags specified for delete
        assertParseFailure(parser, " " + PREFIX_DELETE_TAG + " " + PREFIX_INDEX + " 1",
                String.format(MESSAGE_MISSING_TAG, MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidValue_failure() {
        // Invalid action type
        assertParseFailure(parser, " " + INVALID_ACTION_TYPE + " " + PREFIX_INDEX + " 1",
                String.format(MESSAGE_INVALID_ACTION_TYPE, MESSAGE_USAGE));

        // Invalid index
        assertParseFailure(parser, " " + PREFIX_ADD_TAG + " " + PREFIX_INDEX + " a",
                Index.MESSAGE_CONSTRAINTS);

        // Invalid tag
        assertParseFailure(parser, " " + PREFIX_ADD_TAG + " " + PREFIX_INDEX + " 1 " + PREFIX_TAG + " #friend",
                Tag.MESSAGE_CONSTRAINTS);

        // Only 1 tag specified for edit
        assertParseFailure(parser, " " + PREFIX_EDIT_TAG + " " + PREFIX_INDEX + " 1 " + PREFIX_TAG + " friend",
                String.format(MESSAGE_MISSING_OLD_NEW_TAG, MESSAGE_USAGE));
    }
}
