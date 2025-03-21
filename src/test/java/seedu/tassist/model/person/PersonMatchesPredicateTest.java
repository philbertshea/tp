package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.TypicalPersons.AMY;
import static seedu.tassist.testutil.TypicalPersons.BOB;

import org.junit.jupiter.api.Test;

public class PersonMatchesPredicateTest {
    private final PersonMatchesPredicate amyPredicate =
            new PersonMatchesPredicate(AMY.getName().toString(), AMY.getMatNum().toString(),
                    AMY.getTutGroup().toString(), AMY.getLabGroup().toString(),
                    AMY.getFaculty().toString(), AMY.getYear().toString());

    private final PersonMatchesPredicate amyPredicateCopy =
            new PersonMatchesPredicate(AMY.getName().toString(), AMY.getMatNum().toString(),
                    AMY.getTutGroup().toString(), AMY.getLabGroup().toString(),
                    AMY.getFaculty().toString(), AMY.getYear().toString());


    @Test
    public void equalsMethod() {
        // Same object -> returns true.
        assertTrue(amyPredicate.equals(amyPredicate));

        // Null object -> returns false.
        assertFalse(amyPredicate.equals(null));

        // Same attributes -> returns true.
        assertTrue(amyPredicate.equals(amyPredicateCopy));

        // Different name -> returns false.
        PersonMatchesPredicate differentNamePredicate =
                new PersonMatchesPredicate(BOB.getName().toString(), AMY.getMatNum().toString(),
                        AMY.getTutGroup().toString(), AMY.getLabGroup().toString(),
                        AMY.getFaculty().toString(), AMY.getYear().toString());
        assertFalse(amyPredicate.equals(differentNamePredicate));

        // Different mat num -> returns false.
        PersonMatchesPredicate differentMatNumPredicate =
                new PersonMatchesPredicate(AMY.getName().toString(), BOB.getMatNum().toString(),
                        AMY.getTutGroup().toString(), AMY.getLabGroup().toString(),
                        AMY.getFaculty().toString(), AMY.getYear().toString());
        assertFalse(amyPredicate.equals(differentMatNumPredicate));

        // Different mat num -> returns false.
        PersonMatchesPredicate differentTutGroupPredicate =
                new PersonMatchesPredicate(AMY.getName().toString(), AMY.getMatNum().toString(),
                        BOB.getTutGroup().toString(), AMY.getLabGroup().toString(),
                        AMY.getFaculty().toString(), AMY.getYear().toString());
        assertFalse(amyPredicate.equals(differentTutGroupPredicate));

        // Different mat num -> returns false.
        PersonMatchesPredicate differentLabGroupPredicate =
                new PersonMatchesPredicate(AMY.getName().toString(), AMY.getMatNum().toString(),
                        AMY.getTutGroup().toString(), BOB.getLabGroup().toString(),
                        AMY.getFaculty().toString(), AMY.getYear().toString());
        assertFalse(amyPredicate.equals(differentLabGroupPredicate));

        // Different mat num -> returns false.
        PersonMatchesPredicate differentFacultyPredicate =
                new PersonMatchesPredicate(AMY.getName().toString(), AMY.getMatNum().toString(),
                        AMY.getTutGroup().toString(), AMY.getLabGroup().toString(),
                        BOB.getFaculty().toString(), AMY.getYear().toString());
        assertFalse(amyPredicate.equals(differentFacultyPredicate));

        // Different year -> returns false.
        PersonMatchesPredicate differentYearPredicate =
                new PersonMatchesPredicate(AMY.getName().toString(), AMY.getMatNum().toString(),
                        AMY.getTutGroup().toString(), AMY.getLabGroup().toString(),
                        AMY.getFaculty().toString(), BOB.getYear().toString());
        assertFalse(amyPredicate.equals(differentYearPredicate));

    }
}
