package seedu.tassist.logic;

import static java.util.Objects.requireNonNull;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import seedu.tassist.logic.parser.Prefix;
import seedu.tassist.model.person.Person;

/**
 * Container for user visible messages.
 */
public class Messages {

    public static final String MESSAGE_UNKNOWN_COMMAND = "This command is not recognised."
            + "Please try again.";
    public static final String MESSAGE_INVALID_COMMAND_FORMAT = "Invalid command format! \n%1$s";
    public static final String MESSAGE_INVALID_QUOTES = "Incorrect quote format detected!"
            + "\nA maximum of one quote should be found in between every flag."
            + "\nEvery quote should be paired.";
    public static final String MESSAGE_INVALID_PERSON_DISPLAYED_INDEX = "The person index provided "
            + "is invalid. You currently have %d records!";
    public static final String MESSAGE_PERSONS_LISTED_OVERVIEW = "%1$d persons listed!";
    public static final String MESSAGE_DUPLICATE_FIELDS =
                "Multiple values specified for the following single-valued field(s): ";
    // For missing arguments in delete
    public static final String MESSAGE_MISSING_ARGUMENTS =
            "Missing arguments!\nRequires -i <index>.";
    // For invalid arguments in delete
    public static final String MESSAGE_INVALID_ARGUMENTS =
            "Invalid arguments detected!\nValid arguments are -i <index>.";

    /**
     * Returns an error message given a list of duplicate prefixes.
     *
     * @param duplicatePrefixes List of duplicate prefixes.
     * @return Error message for duplicate prefixes.
     */
    public static String getErrorMessageForDuplicatePrefixes(Prefix... duplicatePrefixes) {
        requireNonNull(duplicatePrefixes);
        assert duplicatePrefixes.length > 0;

        Set<String> duplicateFields =
                Stream.of(duplicatePrefixes).map(Prefix::toString).collect(Collectors.toSet());

        return MESSAGE_DUPLICATE_FIELDS + String.join(" ", duplicateFields);
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String getFormattedPersonAttributes(Person person) {
        requireNonNull(person);
        final StringBuilder builder = new StringBuilder();
        builder.append(person.getName())
                .append("; Phone: ").append(person.getPhone())
                .append("; Telegram Handle: ").append(person.getTeleHandle())
                .append("; Email: ").append(person.getEmail())
                .append("; Matriculation Number: ").append(person.getMatNum())
                .append("; Tutorial Group: ").append(person.getTutGroup())
                .append("; Lab Group: ").append(person.getLabGroup())
                .append("; Faculty: ").append(person.getFaculty())
                .append("; Year: ").append(person.getYear())
                .append("; Remark: ").append(person.getRemark())
                .append("; AttendanceList: ").append(person.getAttendanceList())
                .append("; Tags: ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

    /**
     * Formats the {@code person} for display to the user.
     */
    public static String getFormattedPersonAttributesForDisplay(Person person) {
        requireNonNull(person);
        final StringBuilder builder = new StringBuilder();
        builder.append(" Name              : ").append(person.getName()).append("\n")
                .append(" Phone             : ")
                .append(person.getPhone().value.isEmpty() ? "-" : person.getPhone())
                .append("\n")
                .append(" Telegram Handle   : ")
                .append(person.getTeleHandle().value.isEmpty() ? "-" : person.getTeleHandle())
                .append("\n")
                .append(" Email             : ").append(person.getEmail())
                .append("\n")
                .append(" Matriculation No. : ").append(person.getMatNum())
                .append("\n")
                .append(" Tutorial Group    : ")
                .append(person.getTutGroup().value.isEmpty() ? "-" : person.getTutGroup())
                .append("\n")
                .append(" Lab Group         : ")
                .append(person.getLabGroup().value.isEmpty() ? "-" : person.getLabGroup())
                .append("\n")
                .append(" Faculty           : ")
                .append(person.getFaculty().value.isEmpty() ? "-" : person.getFaculty())
                .append("\n")
                .append(" Year              : ")
                .append(person.getYear().value.isEmpty() ? "-" : person.getYear())
                .append("\n")
                .append(" Attendance List   : ").append(person.getAttendanceList()).append("\n")
                .append(" Remarks           : ")
                .append(person.getRemark().value.isEmpty() ? "-" : person.getRemark())
                .append("\n")
                .append(" Tags              : ");
        person.getTags().forEach(builder::append);
        return builder.toString();
    }

}
