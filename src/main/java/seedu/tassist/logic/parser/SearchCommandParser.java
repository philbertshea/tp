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
import seedu.tassist.model.person.Email;
import seedu.tassist.model.person.Faculty;
import seedu.tassist.model.person.LabGroup;
import seedu.tassist.model.person.MatNum;
import seedu.tassist.model.person.PersonMatchesPredicate;
import seedu.tassist.model.person.Phone;
import seedu.tassist.model.person.TeleHandle;
import seedu.tassist.model.person.TutGroup;
import seedu.tassist.model.person.Year;
import seedu.tassist.model.tag.Tag;

/**
 * Parses input arguments and creates a new SearchCommand object.
 */
public class SearchCommandParser implements Parser<SearchCommand> {

    @Override
    public SearchCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args,
                PREFIX_NAME, PREFIX_MAT_NUM, PREFIX_PHONE, PREFIX_TELE_HANDLE,
                PREFIX_EMAIL, PREFIX_TAG, PREFIX_TUT_GROUP, PREFIX_LAB_GROUP,
                PREFIX_FACULTY, PREFIX_YEAR);

        // Name (supports multiple keywords)
        List<String> nameKeywords = null;
        if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
            String trimmedName = argMultimap.getValue(PREFIX_NAME).get().trim();
            nameKeywords = trimmedName.isEmpty() ? List.of() : Arrays.asList(trimmedName.split("\\s+"));
        }

        // Other fields (each validated)
        String matNum = argMultimap.getValue(PREFIX_MAT_NUM).orElse(null);
        if (matNum != null && !MatNum.isValidMatNum(matNum)) {
            throw new ParseException(MatNum.MESSAGE_CONSTRAINTS);
        }

        String phone = argMultimap.getValue(PREFIX_PHONE).orElse(null);
        if (phone != null && !Phone.isValidPhone(phone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }

        String teleHandle = argMultimap.getValue(PREFIX_TELE_HANDLE).orElse(null);
        if (teleHandle != null && !TeleHandle.isValidTeleHandle(teleHandle)) {
            throw new ParseException(TeleHandle.MESSAGE_CONSTRAINTS);
        }

        String email = argMultimap.getValue(PREFIX_EMAIL).orElse(null);
        if (email != null && !Email.isValidEmail(email)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }

        String tag = argMultimap.getValue(PREFIX_TAG).orElse(null);
        if (tag != null && !Tag.isValidTagName(tag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }

        String tutGroup = argMultimap.getValue(PREFIX_TUT_GROUP).orElse(null);
        if (tutGroup != null && !TutGroup.isValidTutGroup(tutGroup)) {
            throw new ParseException(TutGroup.MESSAGE_CONSTRAINTS);
        }

        String labGroup = argMultimap.getValue(PREFIX_LAB_GROUP).orElse(null);
        if (labGroup != null && !LabGroup.isValidLabGroup(labGroup)) {
            throw new ParseException(LabGroup.MESSAGE_CONSTRAINTS);
        }

        String faculty = argMultimap.getValue(PREFIX_FACULTY).orElse(null);
        if (faculty != null && !Faculty.isValidFaculty(faculty)) {
            throw new ParseException(Faculty.MESSAGE_CONSTRAINTS);
        }

        String year = argMultimap.getValue(PREFIX_YEAR).orElse(null);
        if (year != null && !Year.isValidYear(year)) {
            throw new ParseException(Year.MESSAGE_CONSTRAINTS);
        }

        // No input at all → error
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
