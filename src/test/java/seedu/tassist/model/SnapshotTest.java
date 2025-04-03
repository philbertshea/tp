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
        Snapshot snapshotA = new Snapshot("lab -i 1 -ln 1 -sc 20", Operations.CommandType.RECORD);
        Snapshot snapshotB = new Snapshot(snapshotA);
        assertEquals(snapshotA, snapshotB);

        assertEquals(snapshotB.getCommandType(), Operations.CommandType.RECORD);

        snapshotA.setCommandType(Operations.CommandType.RECORD);
        assertEquals(snapshotA.getCommandType(), Operations.CommandType.RECORD);

        snapshotA.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        Person expectedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        assertEquals(snapshotA.getPeople().get(0), expectedPerson);
        assertEquals(snapshotA.getPeople().size(), 1);
    }
}
