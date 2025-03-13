package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TELE_HANDLE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.Set;
import java.util.stream.Stream;

import seedu.tassist.logic.commands.AddCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.person.AttendanceList;
import seedu.tassist.model.person.Email;
import seedu.tassist.model.person.Faculty;
import seedu.tassist.model.person.LabGroup;
import seedu.tassist.model.person.LabScoreList;
import seedu.tassist.model.person.MatNum;
import seedu.tassist.model.person.Name;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.Phone;
import seedu.tassist.model.person.Remark;
import seedu.tassist.model.person.TeleHandle;
import seedu.tassist.model.person.TutGroup;
import seedu.tassist.model.person.Year;
import seedu.tassist.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object.
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_NAME, PREFIX_PHONE, PREFIX_TELE_HANDLE,
                        PREFIX_EMAIL, PREFIX_MAT_NUM, PREFIX_TUT_GROUP, PREFIX_LAB_GROUP,
                        PREFIX_FACULTY, PREFIX_YEAR, PREFIX_REMARK, PREFIX_TAG);

        if ((!arePrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_EMAIL, PREFIX_MAT_NUM)
                || !anyPrefixesPresent(argMultimap, PREFIX_PHONE, PREFIX_TELE_HANDLE)
                || !anyPrefixesPresent(argMultimap, PREFIX_TUT_GROUP, PREFIX_LAB_GROUP))
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    AddCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_NAME, PREFIX_PHONE, PREFIX_TELE_HANDLE,
                PREFIX_EMAIL, PREFIX_MAT_NUM, PREFIX_TUT_GROUP, PREFIX_LAB_GROUP,
                PREFIX_FACULTY, PREFIX_YEAR, PREFIX_REMARK);
        Name name = ParserUtil.parseName(argMultimap.getValue(PREFIX_NAME).get());
        Phone phone = ParserUtil.parsePhone(argMultimap.getValue(PREFIX_PHONE).get());
        TeleHandle teleHandle = ParserUtil.parseTeleHandle(argMultimap
                .getValue(PREFIX_TELE_HANDLE).get());
        Email email = ParserUtil.parseEmail(argMultimap.getValue(PREFIX_EMAIL).get());
        MatNum matNum = ParserUtil.parseMatNum(argMultimap.getValue(PREFIX_MAT_NUM).get());
        TutGroup tutGrp = ParserUtil.parseTutGroup(argMultimap.getValue(PREFIX_TUT_GROUP).get());
        LabGroup labGrp = ParserUtil.parseLabGroup(argMultimap.getValue(PREFIX_LAB_GROUP).get());
        Faculty faculty = ParserUtil.parseFaculty(argMultimap.getValue(PREFIX_FACULTY).get());
        Year year = ParserUtil.parseYear(argMultimap.getValue(PREFIX_YEAR).get());
        Remark remark = ParserUtil.parseRemark(argMultimap.getValue(PREFIX_REMARK).get());
        AttendanceList attendanceList =
                AttendanceList.generateAttendanceList(AttendanceList.DEFAULT_ATTENDANCE_STRING);
        LabScoreList labScoreList = new LabScoreList();
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG));

        // todo zhenjie
        Person person = new Person(name, phone, teleHandle, email,
                matNum, tutGrp, labGrp, faculty, year, remark, attendanceList, labScoreList, tagList);

        return new AddCommand(person);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
              Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(
                prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if either of the prefixes contains an {@code Optional} value in the given
     * {@Code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap,
            Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(
                prefix -> argumentMultimap.getValue(prefix).isPresent());
    }



}
