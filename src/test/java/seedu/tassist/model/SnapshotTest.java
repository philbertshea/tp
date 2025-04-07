package seedu.tassist.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.tassist.model.person.Person;

public class SnapshotTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void checkGetSetFunctions() {
        // Check object equality
        Snapshot snapshotA = new Snapshot("lab -i 1 -ln 1 -sc 20", Operations.RecordType.RECORD);
        Snapshot snapshotB = new Snapshot(snapshotA);
        assertEquals(snapshotA, snapshotB);

        // Check if all the get and set functions works correctly
        assertEquals(snapshotB.getRecordType(), Operations.RecordType.RECORD);

        snapshotA.setRecordType(Operations.RecordType.RECORD);
        assertEquals(snapshotA.getRecordType(), Operations.RecordType.RECORD);

        snapshotA.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        Person expectedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertEquals(snapshotA.getPeople().get(0), expectedPerson);
        assertEquals(snapshotA.getPeople().size(), 1);
    }
}
