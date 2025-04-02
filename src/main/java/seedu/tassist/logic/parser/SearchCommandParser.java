package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TELE_HANDLE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Arrays;
import java.util.List;

import seedu.tassist.logic.commands.SearchCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.person.PersonMatchesPredicate;

/**
 * Parses input arguments and creates a new SearchCommand object.
 */
public class SearchCommandParser implements Parser<SearchCommand> {
    @Override
    public SearchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_NAME, PREFIX_MAT_NUM, PREFIX_PHONE, PREFIX_TELE_HANDLE,
                        PREFIX_EMAIL, PREFIX_TAG, PREFIX_TUT_GROUP, PREFIX_LAB_GROUP,
                        PREFIX_FACULTY, PREFIX_YEAR);

        List<String> nameKeywords = null;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String trimmedName = argMultimap.getValue(PREFIX_NAME).get().trim();
            nameKeywords = trimmedName.isEmpty() ? List.of() : Arrays.asList(trimmedName.split("\\s+"));
        }

        String matNum = argMultimap.getValue(PREFIX_MAT_NUM).orElse(null);
        String phone = argMultimap.getValue(PREFIX_PHONE).orElse(null);
        String teleHandle = argMultimap.getValue(PREFIX_TELE_HANDLE).orElse(null);
        String email = argMultimap.getValue(PREFIX_EMAIL).orElse(null);
        String tag = argMultimap.getValue(PREFIX_TAG).orElse(null);
        String tutGroup = argMultimap.getValue(PREFIX_TUT_GROUP).orElse(null);
        String labGroup = argMultimap.getValue(PREFIX_LAB_GROUP).orElse(null);
        String faculty = argMultimap.getValue(PREFIX_FACULTY).orElse(null);
        String year = argMultimap.getValue(PREFIX_YEAR).orElse(null);

        if (nameKeywords == null
                && matNum == null
                && phone == null
                && teleHandle == null
                && email == null
                && tag == null
                && tutGroup == null
                && labGroup == null
                && faculty == null
                && year == null) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SearchCommand.MESSAGE_USAGE));
        }

        PersonMatchesPredicate predicate = new PersonMatchesPredicate(
                nameKeywords, matNum, phone, teleHandle, email, tag, tutGroup, labGroup, faculty, year);

        return new SearchCommand(predicate);
    }
}
