package seedu.tassist.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.Messages.MESSAGE_DUPLICATE_FIELDS;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.testutil.Assert.assertThrows;
import static seedu.tassist.testutil.TypicalPersons.AMY;

import org.junit.jupiter.api.Test;

import seedu.tassist.logic.parser.Prefix;
import seedu.tassist.model.person.Person;

public class MessagesTest {

    @Test
    public void getErrorMessagesForDuplicatePrefixes_nullList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                Messages.getErrorMessageForDuplicatePrefixes((Prefix) null));
    }

    @Test
    public void getErrorMessagesForDuplicatePrefixes_emptyList_throwsAssertionError() {
        Prefix[] prefixes = new Prefix[]{};
        assertThrows(AssertionError.class, () ->
                Messages.getErrorMessageForDuplicatePrefixes(prefixes));
    }

    @Test
    public void getErrorMessagesForDuplicatePrefixes_validList_success() {
        String expectedMessage = MESSAGE_DUPLICATE_FIELDS + PREFIX_EMAIL + " " + PREFIX_INDEX;
        assertEquals(expectedMessage, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_INDEX, PREFIX_EMAIL));
    }

    @Test
    public void getFormattedPersonAttributes_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                Messages.getFormattedPersonAttributes((Person) null));
    }

    @Test
    public void getFormattedPersonAttributes_validPerson_success() {
        // Requirement: This method should minimally display the required attributes.
        String actualOutput = Messages.getFormattedPersonAttributes(AMY);
        String[] attributesToPrint = new String[] {
                AMY.getName().toString(), AMY.getPhone().toString(), AMY.getEmail().toString(),
                AMY.getTeleHandle().toString(), AMY.getMatNum().toString(),
                AMY.getTutGroup().toString(), AMY.getLabGroup().toString(), AMY.getFaculty().toString(),
                AMY.getYear().toString(), AMY.getAttendanceList().toString(),
                AMY.getRemark().toString()
        };
        for (String attributeToPrint : attributesToPrint) {
            System.out.println(attributeToPrint);
            assertTrue(actualOutput.contains(attributeToPrint));
        }
    }

    @Test
    public void getFormattedPersonAttributesForDisplay_nullPerson_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                Messages.getFormattedPersonAttributesForDisplay((Person) null));
    }

    @Test
    public void getFormattedPersonAttributesForDisplay_validPerson_success() {
        // Requirement: This method should minimally display the required attributes.
        String actualOutput = Messages.getFormattedPersonAttributesForDisplay(AMY);
        String[] attributesToPrint = new String[] {
                AMY.getName().toString(), AMY.getPhone().toString(), AMY.getEmail().toString(),
                AMY.getTeleHandle().toString(), AMY.getMatNum().toString(),
                AMY.getTutGroup().toString(), AMY.getLabGroup().toString(), AMY.getFaculty().toString(),
                AMY.getYear().toString(), AMY.getAttendanceList().toString(),
                AMY.getRemark().toString()
        };
        for (String attributeToPrint : attributesToPrint) {
            System.out.println(attributeToPrint);
            assertTrue(actualOutput.contains(attributeToPrint));
        }
    }
}
