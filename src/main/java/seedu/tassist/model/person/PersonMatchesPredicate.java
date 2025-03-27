package seedu.tassist.model.person;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

import seedu.tassist.commons.util.StringUtil;

/**
 * Tests if a {@code Person} matches the given search criteria.
 */
public class PersonMatchesPredicate implements Predicate<Person> {
    private final List<String> nameKeywords;
    private final String matNum;
    private final String phone;
    private final String teleHandle;
    private final String email;
    private final String tag;
    private final String tutGroup;
    private final String labGroup;
    private final String faculty;
    private final String year;

    /**
     * Constructs a {@code PersonMatchesPredicate} with the given search criteria.
     * Any null field will be ignored during matching.
     *
     * @param nameKeywords list of name keywords to match against person name
     * @param matNum       matriculation number
     * @param phone        phone number
     * @param teleHandle   telegram handle
     * @param email        email address
     * @param tag          tag
     * @param tutGroup     tutorial group
     * @param labGroup     lab group
     * @param faculty      faculty
     * @param year         year of study
     */
    public PersonMatchesPredicate(List<String> nameKeywords, String matNum, String phone, String teleHandle,
                                  String email, String tag, String tutGroup, String labGroup,
                                  String faculty, String year) {
        this.nameKeywords = nameKeywords;
        this.matNum = matNum;
        this.phone = phone;
        this.teleHandle = teleHandle;
        this.email = email;
        this.tag = tag;
        this.tutGroup = tutGroup;
        this.labGroup = labGroup;
        this.faculty = faculty;
        this.year = year;
    }

    @Override
    public boolean test(Person person) {
        if ((nameKeywords == null || nameKeywords.isEmpty())
                && matNum == null && phone == null && teleHandle == null
                && email == null && tag == null && tutGroup == null && labGroup == null
                && faculty == null && year == null) {
            return false;
        }

        return (nameKeywords == null || nameKeywords.stream()
                    .anyMatch(keyword -> StringUtil.containsIgnoreCase(person.getName().fullName, keyword)))
                && (matNum == null || StringUtil.containsIgnoreCase(person.getMatNum().value, matNum))
                && (phone == null || StringUtil.containsIgnoreCase(person.getPhone().value, phone))
                && (teleHandle == null || StringUtil.containsIgnoreCase(person.getTeleHandle().value, teleHandle))
                && (email == null || StringUtil.containsIgnoreCase(person.getEmail().value, email))
                && (tag == null || person.getTags().stream()
                    .anyMatch(t -> StringUtil.containsIgnoreCase(t.tagName, tag)))
                && (tutGroup == null || StringUtil.containsIgnoreCase(person.getTutGroup().value, tutGroup))
                && (labGroup == null || StringUtil.containsIgnoreCase(person.getLabGroup().value, labGroup))
                && (faculty == null || StringUtil.containsIgnoreCase(person.getFaculty().value, faculty))
                && (year == null || StringUtil.containsIgnoreCase(person.getYear().value, year));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonMatchesPredicate)) {
            return false;
        }
        PersonMatchesPredicate o = (PersonMatchesPredicate) other;
        return Objects.equals(nameKeywords, o.nameKeywords)
                && Objects.equals(matNum, o.matNum)
                && Objects.equals(phone, o.phone)
                && Objects.equals(teleHandle, o.teleHandle)
                && Objects.equals(email, o.email)
                && Objects.equals(tag, o.tag)
                && Objects.equals(tutGroup, o.tutGroup)
                && Objects.equals(labGroup, o.labGroup)
                && Objects.equals(faculty, o.faculty)
                && Objects.equals(year, o.year);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nameKeywords, matNum, phone, teleHandle, email, tag, tutGroup, labGroup, faculty, year);
    }
}
