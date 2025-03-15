package seedu.tassist.logic.parser;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.commands.DeleteCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;

/**
 * Parses user input to create a {@link DeleteCommand}.
 * Expected format: del -i (1-based integer).
 */
public class DeleteCommandParser implements Parser<DeleteCommand> {

    @Override
    public DeleteCommand parse(String args) throws ParseException {
        Index index = ParserUtil.parseDeleteArgs(args, DeleteCommand.MESSAGE_USAGE);
        return new DeleteCommand(index);
    }
}