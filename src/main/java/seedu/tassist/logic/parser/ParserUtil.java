package seedu.tassist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.commons.util.StringUtil;
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
 * Contains utility methods used for parsing strings in the various *Parser classes.
 */
public class ParserUtil {

    public static final String MESSAGE_INVALID_INDEX = "Index is not a non-zero unsigned integer.";
    public static final String MESSAGE_INVALID_WEEK =
            "Week is not an unsigned integer from 1 to 13.";

    /**
     * Parses {@code oneBasedIndex} into an {@code Index} and returns it.
     * Leading and trailing whitespaces will be trimmed.
     * @throws ParseException if the specified index is invalid (not non-zero unsigned integer).
     */
    public static Index parseIndex(String oneBasedIndex) throws ParseException {
        String trimmedIndex = oneBasedIndex.trim();
        if (!StringUtil.isNonZeroUnsignedInteger(trimmedIndex)) {
            throw new ParseException(MESSAGE_INVALID_INDEX);
        }
        return Index.fromOneBased(Integer.parseInt(trimmedIndex));
    }

    /**
     * Parses a {@code String name} into a {@code Name}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code name} is invalid.
     */
    public static Name parseName(String name) throws ParseException {
        requireNonNull(name);
        String trimmedName = name.trim();
        if (!Name.isValidName(trimmedName)) {
            throw new ParseException(Name.MESSAGE_CONSTRAINTS);
        }
        return new Name(trimmedName);
    }

    /**
     * Parses a {@code String phone} into a {@code Phone}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code phone} is invalid.
     */
    public static Phone parsePhone(String phone) throws ParseException {
        requireNonNull(phone);
        String trimmedPhone = phone.replaceAll("\\s+", "");
        if (!Phone.isValidPhone(trimmedPhone)) {
            throw new ParseException(Phone.MESSAGE_CONSTRAINTS);
        }
        return new Phone(trimmedPhone);
    }

    /**
     * Parses a {@code String teleHandle} into a {@code TeleHandle}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code teleHandle} is invalid.
     */
    public static TeleHandle parseTeleHandle(String teleHandle) throws ParseException {
        requireNonNull(teleHandle);
        String trimmedTeleHandle = teleHandle.replaceAll("\\s+", "");
        if (!TeleHandle.isValidTeleHandle(trimmedTeleHandle)) {
            throw new ParseException(TeleHandle.MESSAGE_CONSTRAINTS);
        }
        return new TeleHandle(trimmedTeleHandle);
    }

    /**
     * Parses a {@code String email} into an {@code Email}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code email} is invalid.
     */
    public static Email parseEmail(String email) throws ParseException {
        requireNonNull(email);
        String trimmedEmail = email.replaceAll("\\s+", "");
        if (!Email.isValidEmail(trimmedEmail)) {
            throw new ParseException(Email.MESSAGE_CONSTRAINTS);
        }
        return new Email(trimmedEmail);
    }

    /**
     * Parses a {@code String matNum} into a {@code MatNum}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code } is invalid.
     */
    public static MatNum parseMatNum(String matNum) throws ParseException {
        requireNonNull(matNum);
        String trimmedMatNum = matNum.replaceAll("\\s+", "");
        if (!MatNum.isValidMatNum(trimmedMatNum)) {
            throw new ParseException(MatNum.MESSAGE_CONSTRAINTS);
        }
        return new MatNum(trimmedMatNum);
    }

    /**
     * Parses a {@code String tutGroup} into a {@code TutGroup}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code tutGroup} is invalid.
     */
    public static TutGroup parseTutGroup(String tutGroup) throws ParseException {
        requireNonNull(tutGroup);
        String trimmedTutGroup = tutGroup.replaceAll("\\s+", "");
        if (!TutGroup.isValidTutGroup(trimmedTutGroup)) {
            throw new ParseException(TutGroup.MESSAGE_CONSTRAINTS);
        }
        return new TutGroup(trimmedTutGroup);
    }

    /**
     * Parses a {@code String labGroup} into a {@code LabGroup}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code labGroup} is invalid.
     */
    public static LabGroup parseLabGroup(String labGroup) throws ParseException {
        requireNonNull(labGroup);
        String trimmedLabGroup = labGroup.replaceAll("\\s+", "");
        if (!LabGroup.isValidLabGroup(trimmedLabGroup)) {
            throw new ParseException(LabGroup.MESSAGE_CONSTRAINTS);
        }
        return new LabGroup(trimmedLabGroup);
    }

    /**
     * Parses a {@code String faculty} into a {@code Faculty}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code faculty} is invalid.
     */
    public static Faculty parseFaculty(String faculty) throws ParseException {
        requireNonNull(faculty);
        String trimmedFaculty = faculty.trim();
        if (!Faculty.isValidFaculty(trimmedFaculty)) {
            throw new ParseException(Faculty.MESSAGE_CONSTRAINTS);
        }
        return new Faculty(trimmedFaculty);
    }

    /**
     * Parses a {@code String year} into a {@code Year}.
     * All spaces will be trimmed.
     *
     * @throws ParseException if the given {@code year} is invalid.
     */
    public static Year parseYear(String year) throws ParseException {
        requireNonNull(year);
        String trimmedYear = year.replaceAll("\\s+", "");
        if (!Year.isValidYear(trimmedYear)) {
            throw new ParseException(Year.MESSAGE_CONSTRAINTS);
        }
        return new Year(trimmedYear);
    }

    /**
     * Parses a {@code String remark} into a {@code Remark}.
     *
     * @throws ParseException if the given {@code remark} is invalid.
     */
    public static Remark parseRemark(String remark) throws ParseException {
        requireNonNull(remark);
        if (!Remark.isValidRemark(remark)) {
            throw new ParseException(Remark.MESSAGE_CONSTRAINTS);
        }
        return new Remark(remark);
    }

    /**
     * Parses a {@code String tag} into a {@code Tag}.
     * Leading and trailing whitespaces will be trimmed.
     *
     * @throws ParseException if the given {@code tag} is invalid.
     */
    public static Tag parseTag(String tag) throws ParseException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();
        if (!Tag.isValidTagName(trimmedTag)) {
            throw new ParseException(Tag.MESSAGE_CONSTRAINTS);
        }
        return new Tag(trimmedTag);
    }

    /**
     * Parses {@code Collection<String> tags} into a {@code Set<Tag>}.
     */
    public static Set<Tag> parseTags(Collection<String> tags) throws ParseException {
        requireNonNull(tags);
        final Set<Tag> tagSet = new HashSet<>();
        for (String tagName : tags) {
            tagSet.add(parseTag(tagName));
        }
        return tagSet;
    }

    /**
     * Parses {@code String week} into an {@code int}.
     *
     * @throws ParseException if the given {@code week} is invalid.
     */
    public static int parseWeek(String week) throws ParseException {
        requireNonNull(week);
        if (!StringUtil.isValidWeek(week)) {
            throw new ParseException(MESSAGE_INVALID_WEEK);
        }
        return Integer.parseInt(week);
    }

    /**
     * Parses {@code String attendanceString} into an {@code AttendanceList}.
     *
     * @throws ParseException if the given {@code week} is invalid.
     */
    public static AttendanceList parseAttendanceList(String attendanceString)
            throws ParseException {
        requireNonNull(attendanceString);
        if (!AttendanceList.isValidAttendanceString(attendanceString)) {
            throw new ParseException(AttendanceList.ATTENDANCE_STRING_MESSAGE_CONSTRAINTS);
        }
        return AttendanceList.generateAttendanceList(attendanceString);
    }

    /**
     * Parses {@code String labNumber} into an {@code int}.
     *
     * @throws ParseException if the given {@code labNumber} is invalid.
     */
    public static int parseLabNumber(String labNumber) throws ParseException {
        requireNonNull(labNumber);
        if (!LabScoreList.isValidLabNumber(labNumber)) {
            throw new ParseException(LabScoreList.LAB_NUMBER_CONSTRAINT);
        }
        return Integer.parseInt(labNumber);
    }

    /**
     * Parses {@code String labScore} into an {@code int}.
     *
     * @throws ParseException if the given {@code labScore} is invalid.
     */
    public static int parseLabScore(String labScore) throws ParseException {
        requireNonNull(labScore);
        try {
            return Integer.parseInt(labScore);
        } catch (NumberFormatException e) {
            throw new ParseException(LabScoreList.INVALID_LAB_SCORE);
        }

    }

    /**
     * Returns a filtered list of persons from personList
     * who are in the given tutorial group.
     *
     * @param personList List of persons to filter from.
     * @param tutGroup TutGroup to match persons against.
     * @return List of persons from personList with the matching tutGroup.
     */
    public static List<Person> getPersonsInTutorialGroup(List<Person> personList, TutGroup tutGroup) {
        requireAllNonNull(personList, tutGroup);
        return personList.stream()
                .filter(person -> person.getTutGroup().equals(tutGroup))
                .toList();
    }
}
