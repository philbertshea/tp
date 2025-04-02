package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_YEAR_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tassist.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.tassist.logic.commands.EditCommand.MESSAGE_EDIT_MULTIPLE_PERSON_SUCCESS;
import static seedu.tassist.logic.commands.EditCommand.MESSAGE_LAB_GROUP_REQUIRED;
import static seedu.tassist.logic.commands.EditCommand.MESSAGE_PHONE_REQUIRED;
import static seedu.tassist.logic.commands.EditCommand.MESSAGE_TELEHANDLE_REQUIRED;
import static seedu.tassist.logic.commands.EditCommand.MESSAGE_TUT_GROUP_REQUIRED;
import static seedu.tassist.logic.commands.EditCommand.getEditedStudentsSummary;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tassist.testutil.TypicalPersons.BADAMY;
import static seedu.tassist.testutil.TypicalPersons.BADBOB;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.tassist.model.AddressBook;
import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.UserPrefs;
import seedu.tassist.model.person.Person;
import seedu.tassist.testutil.EditPersonDescriptorBuilder;
import seedu.tassist.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    private final List<Index> expectedMultipleIndex = Arrays.asList(
            Index.fromOneBased(1),
            Index.fromOneBased(2)
    );

    // ================= JUnits for Singles Editing =================
    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = new EditCommand(List.of(INDEX_FIRST_PERSON), descriptor);
        String expectedMessage = String.format(
                EditCommand.MESSAGE_EDIT_SINGLE_PERSON_SUCCESS,
                Messages.getFormattedPersonAttributesForDisplay(editedPerson)
        );
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        Person lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

        PersonBuilder personInList = new PersonBuilder(lastPerson);
        Person editedPerson = personInList.withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
                .withTags(VALID_TAG_HUSBAND).build();

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
        EditCommand editCommand = new EditCommand(List.of(indexLastPerson), descriptor);

        String expectedMessage = String.format(
                EditCommand.MESSAGE_EDIT_SINGLE_PERSON_SUCCESS,
                Messages.getFormattedPersonAttributesForDisplay(editedPerson)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(lastPerson, editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        EditCommand editCommand = new EditCommand(List.of(INDEX_FIRST_PERSON), new EditPersonDescriptor());
        Person editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(
                EditCommand.MESSAGE_EDIT_SINGLE_PERSON_SUCCESS,
                Messages.getFormattedPersonAttributesForDisplay(editedPerson)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        Person personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(List.of(INDEX_FIRST_PERSON),
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        String expectedMessage = String.format(
                EditCommand.MESSAGE_EDIT_SINGLE_PERSON_SUCCESS,
                Messages.getFormattedPersonAttributesForDisplay(editedPerson)
        );

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() {
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        EditCommand editCommand = new EditCommand(List.of(INDEX_SECOND_PERSON), descriptor);

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);

        // edit person in filtered list into a duplicate in address book
        Person personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = new EditCommand(List.of(INDEX_FIRST_PERSON),
                new EditPersonDescriptorBuilder(personInList).build());

        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        EditCommand editCommand = new EditCommand(List.of(outOfBoundIndex), descriptor);

        assertCommandFailure(editCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        model.getFilteredPersonList().size()));
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // Ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        EditCommand editCommand = new EditCommand(List.of(outOfBoundIndex),
                new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

        assertCommandFailure(editCommand, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                        model.getFilteredPersonList().size()));
    }

    @Test
    public void equals() {
        final EditCommand standardCommand = new EditCommand(List.of(INDEX_FIRST_PERSON), DESC_AMY);

        // Same values -> returns true.
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(List.of(INDEX_FIRST_PERSON), copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Same object -> returns true.
        assertTrue(standardCommand.equals(standardCommand));

        // Null -> returns false.
        assertFalse(standardCommand.equals(null));

        // Different types -> returns false.
        assertFalse(standardCommand.equals(new ClearCommand()));

        // Different index -> returns false.
        assertFalse(standardCommand.equals(new EditCommand(List.of(INDEX_SECOND_PERSON), DESC_AMY)));

        // Different descriptor -> returns false.
        assertFalse(standardCommand.equals(new EditCommand(List.of(INDEX_FIRST_PERSON), DESC_BOB)));
    }

    @Test
    public void toStringMethod() {
        List<Index> index = List.of(Index.fromOneBased(1));
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        EditCommand editCommand = new EditCommand(index, editPersonDescriptor);
        String expected = EditCommand.class.getCanonicalName() + "{indexList=" + index + ", editPersonDescriptor="
                + editPersonDescriptor + "}";
        assertEquals(expected, editCommand.toString());
    }

    // ================= JUnits for Mandatory (Optional) Param =================
    @Test
    public void execute_validateAtLeastOneMandatoryFieldPresent_failure() {
        model.addPerson(BADBOB);
        Index lastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
        // Remove Phone when student only has phone (no telegram)
        EditCommand editCommand = new EditCommand(List.of(lastPerson),
                new EditPersonDescriptorBuilder().withPhone("").build());
        assertCommandFailure(editCommand, model, MESSAGE_PHONE_REQUIRED);

        // Remove Lab Group when student only has lab group (no tut group)
        editCommand = new EditCommand(List.of(lastPerson),
                new EditPersonDescriptorBuilder().withLabGroup("").build());
        assertCommandFailure(editCommand, model, MESSAGE_LAB_GROUP_REQUIRED);

        model.addPerson(BADAMY);
        lastPerson = Index.fromOneBased(model.getFilteredPersonList().size());

        // Remove TeleHandle when student only has telehandle (no phone)
        editCommand = new EditCommand(List.of(lastPerson),
                new EditPersonDescriptorBuilder().withTeleHandle("").build());
        assertCommandFailure(editCommand, model, MESSAGE_TELEHANDLE_REQUIRED);

        // Remove Tut Group when student only tut group (no lab group)
        editCommand = new EditCommand(List.of(lastPerson),
                new EditPersonDescriptorBuilder().withTutGroup("").build());
        assertCommandFailure(editCommand, model, MESSAGE_TUT_GROUP_REQUIRED);
    }

    // ================= JUnits for Batch Editing =================
    @Test
    public void execute_batchEdit_success() {

        Person personOneInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPersonOne = new PersonBuilder(personOneInFilteredList).withYear(VALID_YEAR_AMY).build();

        Person personTwoInFilteredList = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person editedPersonTwo = new PersonBuilder(personTwoInFilteredList).withYear(VALID_YEAR_AMY).build();
        List<Person> editedStudents = List.of(editedPersonOne, editedPersonTwo);
        EditCommand editCommand = new EditCommand(expectedMultipleIndex,
                new EditPersonDescriptorBuilder().withYear(VALID_YEAR_AMY).build());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPersonOne);
        expectedModel.setPerson(model.getFilteredPersonList().get(1), editedPersonTwo);
        assertCommandSuccess(editCommand, model, String.format(MESSAGE_EDIT_MULTIPLE_PERSON_SUCCESS,
                getEditedStudentsSummary(editedStudents)), expectedModel);
    }

}
