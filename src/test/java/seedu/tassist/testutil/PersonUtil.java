package seedu.tassist.testutil;

import static seedu.tassist.logic.parser.CliSyntax.PREFIX_ATTENDANCE_LIST;
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

import seedu.tassist.logic.commands.AddCommand;
import seedu.tassist.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.tag.Tag;

/**
 * A utility class for Person.
 */
public class PersonUtil {

    /**
     * Returns an add command string for adding the {@code person}.
     */
    public static String getAddCommand(Person person) {
        return AddCommand.COMMAND_WORD + " " + getPersonDetails(person);
    }

    /**
     * Returns the part of command string for the given {@code person}'s details.
     */
    public static String getPersonDetails(Person person) {
        StringBuilder sb = new StringBuilder();
//        sb.append(PREFIX_NAME + person.getName().fullName + " ");
//        sb.append(PREFIX_PHONE + person.getPhone().value + " ");
//        sb.append(PREFIX_TELE_HANDLE + person.getTeleHandle().value + " ");
//        sb.append(PREFIX_EMAIL + person.getEmail().value + " ");
//        sb.append(PREFIX_MAT_NUM + person.getMatNum().value + " ");
//        sb.append(PREFIX_TUT_GROUP + person.getTutGroup().value + " ");
//        sb.append(PREFIX_LAB_GROUP + person.getLabGroup().value + " ");
//        sb.append(PREFIX_FACULTY + person.getFaculty().value + " ");
//        sb.append(PREFIX_YEAR + person.getYear().value + " ");
//        sb.append(PREFIX_REMARK + person.getRemark().value + " ");
//        person.getTags().stream().forEach(
//            s -> sb.append(PREFIX_TAG + s.tagName + " ")
//        );
        sb.append(PREFIX_NAME).append(person.getName().fullName).append(" ");
        sb.append(PREFIX_PHONE).append(person.getPhone().value).append(" ");
        sb.append(PREFIX_TELE_HANDLE).append(person.getTeleHandle().value).append(" ");
        sb.append(PREFIX_EMAIL).append(person.getEmail().value).append(" ");
        sb.append(PREFIX_MAT_NUM).append(person.getMatNum().value).append(" ");
        sb.append(PREFIX_TUT_GROUP).append(person.getTutGroup().value).append(" ");
        sb.append(PREFIX_LAB_GROUP).append(person.getLabGroup().value).append(" ");
        sb.append(PREFIX_FACULTY).append(person.getFaculty().value).append(" ");
        sb.append(PREFIX_YEAR).append(person.getYear().value).append(" ");
        sb.append(PREFIX_REMARK).append(person.getRemark().value).append(" ");
        person.getTags().stream().forEach(
                s -> sb.append(PREFIX_TAG).append(s.tagName).append(" ")
        );
        return sb.toString();
    }

    /**
     * Returns the part of command string for the given {@code EditPersonDescriptor}'s details.
     */
    public static String getEditPersonDescriptorDetails(EditPersonDescriptor descriptor) {
        StringBuilder sb = new StringBuilder();
        descriptor.getName().ifPresent(name -> sb.append(PREFIX_NAME).append(name.fullName).append(" "));
        descriptor.getPhone().ifPresent(phone -> sb.append(PREFIX_PHONE).append(phone.value).append(" "));
        descriptor.getTeleHandle().ifPresent(teleHandle -> sb.append(PREFIX_TELE_HANDLE).append(teleHandle.value).append(" "));
        descriptor.getEmail().ifPresent(email -> sb.append(PREFIX_EMAIL).append(email.value).append(" "));
        descriptor.getMatNum().ifPresent(matNum -> sb.append(PREFIX_MAT_NUM).append(matNum.value).append(" "));
        descriptor.getTutGroup().ifPresent(tutGroup -> sb.append(PREFIX_TUT_GROUP).append(tutGroup.value).append(" "));
        descriptor.getLabGroup().ifPresent(labGroup -> sb.append(PREFIX_LAB_GROUP).append(labGroup.value).append(" "));
        descriptor.getFaculty().ifPresent(faculty -> sb.append(PREFIX_FACULTY).append(faculty.value).append(" "));
        descriptor.getYear().ifPresent(year -> sb.append(PREFIX_REMARK).append(year.value).append(" "));
        descriptor.getRemark().ifPresent(remark -> sb.append(PREFIX_YEAR).append(remark.value).append(" "));
        descriptor.getAttendanceList().ifPresent(attendanceList -> sb.append(PREFIX_ATTENDANCE_LIST)
                .append(attendanceList.toString()).append(" "));
        if (descriptor.getTags().isPresent()) {
            Set<Tag> tags = descriptor.getTags().get();
            if (tags.isEmpty()) {
                sb.append(PREFIX_TAG);
            } else {
                tags.forEach(s -> sb.append(PREFIX_TAG).append(s.tagName).append(" "));
            }
        }
        return sb.toString();
    }
}
