package seedu.tassist.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.tassist.logic.commands.CommandTestUtil.EMAIL_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.FACULTY_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.LAB_GROUP_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.MAT_NUM_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.NAME_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.PREAMBLE_WHITESPACE;
import static seedu.tassist.logic.commands.CommandTestUtil.REMARK_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.TAG_DESC_FRIEND;
import static seedu.tassist.logic.commands.CommandTestUtil.TELE_HANDLE_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.TUT_GROUP_DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_NUMBER_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_SCORE_A;
import static seedu.tassist.logic.commands.CommandTestUtil.YEAR_DESC_BOB;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_NUMBER;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_SCORE;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.commands.UpdateLabScoreCommand;

public class SnapshotTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void checkGetSetFunctions() {
        Snapshot snapshotA = new Snapshot("lab -i 1 -ln 1 -sc 20", Operations.CommandType.ADD);
        Snapshot snapshotB = new Snapshot(snapshotA);
        assertEquals(snapshotA, snapshotB);

        assertEquals(snapshotB.getCommandType(), Operations.CommandType.ADD);

        snapshotA.setCommandType(Operations.CommandType.DELETE);
        assertEquals(snapshotA.getCommandType(), Operations.CommandType.DELETE);

        snapshotA.setPerson(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        assertEquals(snapshotA.getPerson(), model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        assertEquals(snapshotA.getPeople().size(), 1);
    }

    @Test
    public void checkIndexParsing() {
        //index not present
        String addCommandIndexNotPresent = PREAMBLE_WHITESPACE + NAME_DESC_BOB + PHONE_DESC_BOB
                + TELE_HANDLE_DESC_BOB + EMAIL_DESC_BOB + MAT_NUM_DESC_BOB + TUT_GROUP_DESC_BOB
                + LAB_GROUP_DESC_BOB + FACULTY_DESC_BOB + YEAR_DESC_BOB + REMARK_DESC_BOB
                + TAG_DESC_FRIEND;
        Snapshot addCommandSnapshot = new Snapshot(addCommandIndexNotPresent, Operations.CommandType.ADD);
        int emptyIndex = -1; //Private static in snapshot, unable to import
        assertEquals(emptyIndex, addCommandSnapshot.getIndex());

        //index present
        String labCommandIndexPresent = UpdateLabScoreCommand.COMMAND_WORD + " "
                + PREFIX_INDEX + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                + PREFIX_LAB_NUMBER + " " + VALID_LAB_NUMBER_A + " "
                + PREFIX_LAB_SCORE + " " + VALID_LAB_SCORE_A;
        Snapshot labCommandSnapshot = new Snapshot(labCommandIndexPresent, Operations.CommandType.LABSCORE);
        assertEquals(INDEX_FIRST_PERSON.getZeroBased(), labCommandSnapshot.getIndex());

        labCommandSnapshot.setIndex(5);
        assertEquals(5, labCommandSnapshot.getIndex());
    }
}
