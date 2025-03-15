package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.commands.CommandTestUtil.EMAIL_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.FACULTY_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.FACULTY_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_EMAIL_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_PHONE_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.tassist.logic.commands.CommandTestUtil.LAB_GROUP_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.LAB_GROUP_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.MAT_NUM_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.MAT_NUM_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.NAME_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.PHONE_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.PREAMBLE_NON_EMPTY;
import static seedu.tassist.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.tassist.logic.commands.CommandTestUtil.REMARK_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.tassist.logic.commands.CommandTestUtil.TAG_DESC_HUSBAND;
import static seedu.tassist.logic.commands.CommandTestUtil.TELE_HANDLE_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.TELE_HANDLE_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.TUT_GROUP_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.TUT_GROUP_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.tassist.logic.commands.CommandTestUtil.YEAR_DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.YEAR_DESC_BOB;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TELE_HANDLE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.tassist.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.tassist.testutil.TypicalPersons.AMY;
import static seedu.tassist.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.AddCommand;
import seedu.tassist.model.person.Email;
import seedu.tassist.model.person.Name;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.Phone;
import seedu.tassist.model.tag.Tag;
import seedu.tassist.testutil.PersonBuilder;

public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Person expectedPerson = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND).build();

        // whitespace only preamble
        // todo: zhenjie vet
        assertParseSuccess(parser, PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                + TELE_HANDLE_DESC_BOB + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND, new AddCommand(expectedPerson));


        // multiple tags - all accepted
        Person expectedPersonMultipleTags = new PersonBuilder(BOB).withTags(VALID_TAG_FRIEND, VALID_TAG_HUSBAND)
                .build();
        assertParseSuccess(parser,
                NAME_DESC_BOB + PHONE_DESC_BOB
                        + TELE_HANDLE_DESC_BOB + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                        + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                        + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                new AddCommand(expectedPersonMultipleTags));
    }

    @Test
    public void parse_repeatedNonTagValue_failure() {
        String validExpectedPersonString = NAME_DESC_BOB + PHONE_DESC_BOB
                + TELE_HANDLE_DESC_BOB + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND;

        // multiple names
        assertParseFailure(parser, NAME_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // multiple phones
        assertParseFailure(parser, PHONE_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // multiple emails
        assertParseFailure(parser, EMAIL_DESC_AMY + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // todo: zhenjie: change the order of arguments in future.
        // multiple fields repeated
        assertParseFailure(parser,
                validExpectedPersonString + PHONE_DESC_AMY + EMAIL_DESC_AMY + NAME_DESC_AMY
                        + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_TELE_HANDLE, PREFIX_MAT_NUM,
                        PREFIX_NAME, PREFIX_PHONE, PREFIX_LAB_GROUP, PREFIX_REMARK, PREFIX_TUT_GROUP,
                        PREFIX_EMAIL, PREFIX_YEAR, PREFIX_FACULTY));

        // invalid value followed by valid value

        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, INVALID_EMAIL_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, INVALID_PHONE_DESC + validExpectedPersonString,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));

        // valid value followed by invalid value

        // invalid name
        assertParseFailure(parser, validExpectedPersonString + INVALID_NAME_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));

        // invalid email
        assertParseFailure(parser, validExpectedPersonString + INVALID_EMAIL_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_EMAIL));

        // invalid phone
        assertParseFailure(parser, validExpectedPersonString + INVALID_PHONE_DESC,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_PHONE));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        // todo: zhenjie vet
        Person expectedPerson = new PersonBuilder(AMY).withTags().build();
        assertParseSuccess(parser, NAME_DESC_AMY + PHONE_DESC_AMY
                + TELE_HANDLE_DESC_AMY + EMAIL_DESC_AMY + MAT_NUM_DESC_AMY
                + TUT_GROUP_DESC_AMY + LAB_GROUP_DESC_AMY + FACULTY_DESC_AMY
                + YEAR_DESC_AMY + REMARK_DESC_AMY,
                new AddCommand(expectedPerson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        // todo: zhenjie vet
        assertParseFailure(parser, VALID_NAME_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, NAME_DESC_BOB + VALID_PHONE_BOB + EMAIL_DESC_BOB,
                expectedMessage);

        // missing email prefix
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + VALID_EMAIL_BOB,
                expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, VALID_NAME_BOB + VALID_PHONE_BOB + VALID_EMAIL_BOB,
                expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // todo: zhenjie vet
        // invalid name
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + TELE_HANDLE_DESC_BOB
                + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Name.MESSAGE_CONSTRAINTS);

        // invalid phone
        assertParseFailure(parser, NAME_DESC_BOB + INVALID_PHONE_DESC + TELE_HANDLE_DESC_BOB
                + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Phone.MESSAGE_CONSTRAINTS);

        // invalid email
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + TELE_HANDLE_DESC_BOB
                + INVALID_EMAIL_DESC + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND, Email.MESSAGE_CONSTRAINTS);

        // invalid tag
        assertParseFailure(parser, NAME_DESC_BOB + PHONE_DESC_BOB + TELE_HANDLE_DESC_BOB
                + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + INVALID_TAG_DESC + VALID_TAG_FRIEND, Tag.MESSAGE_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, INVALID_NAME_DESC + PHONE_DESC_BOB + TELE_HANDLE_DESC_BOB
                + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB,
                Name.MESSAGE_CONSTRAINTS);

        // non-empty preamble
        assertParseFailure(parser, PREAMBLE_NON_EMPTY + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + TAG_DESC_HUSBAND + TAG_DESC_FRIEND,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
    }
}
