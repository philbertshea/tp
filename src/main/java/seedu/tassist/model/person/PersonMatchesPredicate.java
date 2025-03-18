package seedu.tassist.model.person;

import java.util.function.Predicate;

import seedu.tassist.commons.util.StringUtil;

/**
 * Tests if a {@code Person} matches the given search criteria.
 */
public class PersonMatchesPredicate implements Predicate<Person> {
    private final String name;
    private final String matNum;
    private final String tutGroup;
    private final String labGroup;
    private final String faculty;
    private final String year;

    public PersonMatchesPredicate(String name, String matNum, String tutGroup, String labGroup, String faculty,
                                  String year) {
        this.name = name;
        this.matNum = matNum;
        this.tutGroup = tutGroup;
        this.labGroup = labGroup;
        this.faculty = faculty;
        this.year = year;
    }

    @Override
    public boolean test(Person person) {
        if (name == null && matNum == null && tutGroup == null && labGroup == null && faculty == null && year == null) {
            return false;
        }
        boolean matches = (name == null || StringUtil.containsIgnoreCase(person.getName().fullName, name))
                && (matNum == null || StringUtil.containsIgnoreCase(person.getMatNum().value, matNum))
                && (tutGroup == null || StringUtil.containsIgnoreCase(person.getTutGroup().value, tutGroup))
                && (labGroup == null || StringUtil.containsIgnoreCase(person.getLabGroup().value, labGroup))
                && (faculty == null || StringUtil.containsIgnoreCase(person.getFaculty().value, faculty))
                && (year == null || StringUtil.containsIgnoreCase(person.getYear().value, year));
        return matches;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof PersonMatchesPredicate)) {
            return false;
        }
        PersonMatchesPredicate otherPredicate = (PersonMatchesPredicate) other;
        return name.equals(otherPredicate.name)
                && matNum.equals(otherPredicate.matNum)
                && tutGroup.equals(otherPredicate.tutGroup)
                && labGroup.equals(otherPredicate.labGroup)
                && faculty.equals(otherPredicate.faculty)
                && year.equals(otherPredicate.year);
    }
}
