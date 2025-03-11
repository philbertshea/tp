package seedu.tassist.model.util;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.tassist.model.AddressBook;
import seedu.tassist.model.ReadOnlyAddressBook;
import seedu.tassist.model.person.AttendanceList;
import seedu.tassist.model.person.Email;
import seedu.tassist.model.person.Faculty;
import seedu.tassist.model.person.LabGroup;
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
 * Contains utility methods for populating {@code AddressBook} with sample data.
 */
public class SampleDataUtil {

    // todo: zhenjie update all fields
    public static final TeleHandle DEFAULT_TELE_HANDLE = new TeleHandle("@tele");
    public static final MatNum DEFAULT_MAT_NUM = new MatNum("A0000000Y");
    public static final TutGroup DEFAULT_TUT_GROUP = new TutGroup("T01");
    public static final LabGroup DEFAULT_LAB_GROUP = new LabGroup("B01");
    public static final Faculty DEFAULT_FACULTY = new Faculty("SoC");
    public static final Year DEFAULT_YEAR = new Year("1");
    public static final Remark DEFAULT_REMARK = new Remark("");

    public static Person[] getSamplePersons() {
        return new Person[] {
            new Person(new Name("Alex Yeoh"), new Phone("87438807"), DEFAULT_TELE_HANDLE,
                    new Email("alexyeoh@example.com"), DEFAULT_MAT_NUM, DEFAULT_TUT_GROUP,
                    DEFAULT_LAB_GROUP, DEFAULT_FACULTY, DEFAULT_YEAR, DEFAULT_REMARK,
                    AttendanceList.generateAttendanceList("0000000000000"),
                    getTagSet("friends")),

            new Person(new Name("Bernice Yu"), new Phone("99272758"), DEFAULT_TELE_HANDLE,
                    new Email("berniceyu@example.com"), DEFAULT_MAT_NUM, DEFAULT_TUT_GROUP,
                    DEFAULT_LAB_GROUP, DEFAULT_FACULTY, DEFAULT_YEAR, DEFAULT_REMARK,
                    AttendanceList.generateAttendanceList("0000000000000"),
                    getTagSet("colleagues", "friends")),

            new Person(new Name("Charlotte Oliveiro"), new Phone("93210283"), DEFAULT_TELE_HANDLE,
                    new Email("charlotte@example.com"), DEFAULT_MAT_NUM, DEFAULT_TUT_GROUP,
                    DEFAULT_LAB_GROUP, DEFAULT_FACULTY, DEFAULT_YEAR, DEFAULT_REMARK,
                    AttendanceList.generateAttendanceList("0000000000000"),
                    getTagSet("neighbours")),

            new Person(new Name("David Li"), new Phone("91031282"), DEFAULT_TELE_HANDLE,
                    new Email("lidavid@example.com"), DEFAULT_MAT_NUM, DEFAULT_TUT_GROUP,
                    DEFAULT_LAB_GROUP, DEFAULT_FACULTY, DEFAULT_YEAR, DEFAULT_REMARK,
                    AttendanceList.generateAttendanceList("0000000000000"),
                    getTagSet("family")),

            new Person(new Name("Irfan Ibrahim"), new Phone("92492021"), DEFAULT_TELE_HANDLE,
                    new Email("irfan@example.com"), DEFAULT_MAT_NUM, DEFAULT_TUT_GROUP,
                    DEFAULT_LAB_GROUP, DEFAULT_FACULTY, DEFAULT_YEAR, DEFAULT_REMARK,
                    AttendanceList.generateAttendanceList("0000000000000"),
                    getTagSet("classmates")),

            new Person(new Name("Roy Balakrishnan"), new Phone("92624417"), DEFAULT_TELE_HANDLE,
                    new Email("royb@example.com"), DEFAULT_MAT_NUM, DEFAULT_TUT_GROUP,
                    DEFAULT_LAB_GROUP, DEFAULT_FACULTY, DEFAULT_YEAR, DEFAULT_REMARK,
                    AttendanceList.generateAttendanceList("0000000000000"),
                    getTagSet("colleagues"))
        };
    }

    public static ReadOnlyAddressBook getSampleAddressBook() {
        AddressBook sampleAb = new AddressBook();
        for (Person samplePerson : getSamplePersons()) {
            sampleAb.addPerson(samplePerson);
        }
        return sampleAb;
    }

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
