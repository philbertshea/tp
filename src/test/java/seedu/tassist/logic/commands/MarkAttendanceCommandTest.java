package seedu.tassist.logic.commands;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_AMY;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_TUT_GROUP_BOB;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_WEEK_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_WEEK_B;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tassist.logic.commands.CommandTestUtil.showPersonAtIndex;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_ATTENDED_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_MC_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_NOT_ATTENDED_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_NO_TUTORIAL_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_TUT_GROUP_MC_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_TUT_GROUP_NOT_ATTENDED_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS;
import static seedu.tassist.logic.commands.MarkAttendanceCommand.MESSAGE_MARK_WHEN_NO_TUTORIAL_FAILURE;
import static seedu.tassist.testutil.Assert.assertThrows;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.UserPrefs;
import seedu.tassist.model.person.Attendance;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.TutGroup;
import seedu.tassist.testutil.PersonBuilder;

public class MarkAttendanceCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void constructor_nullIndexList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new MarkAttendanceCommand(null, 1, Attendance.ATTENDED));
    }

    @Test
    public void constructor_nullTutGroupList_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                new MarkAttendanceCommand(1, Attendance.ATTENDED, null));
    }

    /**
     * Gets the replaced index and the String after replacement of the character
     * at that replaced index, concatenated as a String. This is a helper function for use in testing.
     *
     * This method
     *
     * @param newStatus The new status to replace a char in the String of some valid replacement index to.
     * @param existingAttendanceString The existing attendance string.
     * @param firstReplacementIndex The first potential index to be replaced.
     * @param secondReplacementIndex The second potential index to be replaced.
     * @return String with the replaced index and the new String after replacement.
     */
    private String getReplacedIndexAndNewString(
            String newStatus, String existingAttendanceString,
            int firstReplacementIndex, int secondReplacementIndex) {

        boolean firstReplacementFound = firstReplacementIndex != -1;
        boolean secondReplacementFound = secondReplacementIndex != -1;
        boolean noReplacementFound = !firstReplacementFound && !secondReplacementFound;

        if (noReplacementFound) {
            return "5" + existingAttendanceString;
        }

        int replacementIndex = 0;
        if (firstReplacementFound) {
            replacementIndex = firstReplacementIndex;
        } else if (secondReplacementFound) {
            replacementIndex = secondReplacementIndex;
        }

        return replacementIndex
                + existingAttendanceString.substring(0, replacementIndex)
                + newStatus
                + existingAttendanceString.substring(replacementIndex + 1);

    }

    /**
     * Takes in a valid {@code existingString} of length 13, a {@code week} integer from 1 to 13 inclusive,
     * and a {@code newAttendanceStatus} integer from 0 to 3 inclusive.
     *
     * @param existingString Existing Attendance String of length 13.
     * @param week Integer from 1 to 13 inclusive.
     * @param newAttendanceStatus Integer from 0 to 3 inclusive.
     * @return New Attendance String after substitution.
     */
    private String replaceAttendanceString(String existingString, int week, int newAttendanceStatus) {
        assert existingString != null && existingString.length() == 13;
        assert week > 0 && week < 14
                && (newAttendanceStatus == Attendance.ATTENDED || newAttendanceStatus == Attendance.NOT_ATTENDED
                || newAttendanceStatus == Attendance.ON_MC || newAttendanceStatus == Attendance.NO_TUTORIAL);

        return existingString.substring(0, week - 1) + newAttendanceStatus + existingString.substring(week);
    }

    @Test
    public void execute_markSingleIndexAttendedUnfilteredList_success() {

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String existingAttendanceString = firstPerson.getAttendanceList().toString();
        String replacedIndexAndNewString = getReplacedIndexAndNewString(
                String.valueOf(Attendance.ATTENDED), existingAttendanceString,
                existingAttendanceString.indexOf(String.valueOf(Attendance.NOT_ATTENDED)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.ON_MC)));

        String newAttendanceString = replacedIndexAndNewString.substring(1);
        int replacedIndex = Integer.parseInt(replacedIndexAndNewString.substring(0, 1)) + 1;
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAttendanceList(newAttendanceString).build();
        MarkAttendanceCommand command =
                new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON), replacedIndex, Attendance.ATTENDED);

        String expectedMessage = String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                editedPerson.getName(), editedPerson.getMatNum(), replacedIndex) + "\n";

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }


    @Test
    public void execute_markMultipleIndexesAttendedWeek3UnfilteredList_success() {
        // Boundary value: week 3.
        // Weeks 1 and 2 have attendance status of No Tutorial, hence it is invalid to mark
        // attendance for weeks 1 and 2 on an indexList. (Only for tutGroupList)
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.

        List<Index> indexes = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        int weekToMark = 3;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        StringBuilder expectedMessage = new StringBuilder();

        for (Index index: indexes) {
            Person personToMark = model.getFilteredPersonList().get(index.getZeroBased());
            String existingAttendanceString = personToMark.getAttendanceList().toString();
            String newAttendanceString = replaceAttendanceString(
                    existingAttendanceString, weekToMark, Attendance.ATTENDED);

            Person editedPerson = new PersonBuilder(personToMark)
                    .withAttendanceList(newAttendanceString).build();
            expectedModel.setPerson(model.getFilteredPersonList().get(index.getZeroBased()), editedPerson);
            expectedMessage.append(String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                    editedPerson.getName(), editedPerson.getMatNum(), weekToMark)).append("\n");
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(indexes, weekToMark, Attendance.ATTENDED);
        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);
    }

    @Test
    public void execute_markMultipleIndexesAttendedWeek10UnfilteredList_success() {
        // Other value: week 10.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.

        List<Index> indexes = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        int weekToMark = 10;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        StringBuilder expectedMessage = new StringBuilder();

        for (Index index: indexes) {
            Person personToMark = model.getFilteredPersonList().get(index.getZeroBased());
            String existingAttendanceString = personToMark.getAttendanceList().toString();
            String newAttendanceString = replaceAttendanceString(
                    existingAttendanceString, weekToMark, Attendance.ATTENDED);

            Person editedPerson = new PersonBuilder(personToMark)
                    .withAttendanceList(newAttendanceString).build();
            expectedModel.setPerson(model.getFilteredPersonList().get(index.getZeroBased()), editedPerson);
            expectedMessage.append(String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                    editedPerson.getName(), editedPerson.getMatNum(), weekToMark)).append("\n");
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(indexes, weekToMark, Attendance.ATTENDED);
        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);
    }

    @Test
    public void execute_markMultipleIndexesAttendedWeek13UnfilteredList_success() {
        // Boundary value: Week 13.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.

        List<Index> indexes = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        int weekToMark = 13;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        StringBuilder expectedMessage = new StringBuilder();

        for (Index index: indexes) {
            Person personToMark = model.getFilteredPersonList().get(index.getZeroBased());
            String existingAttendanceString = personToMark.getAttendanceList().toString();
            String newAttendanceString = replaceAttendanceString(
                    existingAttendanceString, weekToMark, Attendance.ATTENDED);

            Person editedPerson = new PersonBuilder(personToMark)
                    .withAttendanceList(newAttendanceString).build();
            expectedModel.setPerson(model.getFilteredPersonList().get(index.getZeroBased()), editedPerson);
            expectedMessage.append(String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                    editedPerson.getName(), editedPerson.getMatNum(), weekToMark)).append("\n");
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(indexes, weekToMark, Attendance.ATTENDED);
        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);
    }

    @Test
    public void execute_markSingleIndexNotAttendedUnfilteredList_success() {

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String existingAttendanceString = firstPerson.getAttendanceList().toString();
        String replacedIndexAndNewString = getReplacedIndexAndNewString(
                String.valueOf(Attendance.NOT_ATTENDED), existingAttendanceString,
                existingAttendanceString.indexOf(String.valueOf(Attendance.ATTENDED)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.ON_MC)));

        String newAttendanceString = replacedIndexAndNewString.substring(1);
        int replacedIndex = Integer.parseInt(replacedIndexAndNewString.substring(0, 1)) + 1;
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAttendanceList(newAttendanceString).build();
        MarkAttendanceCommand command =
                new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON), replacedIndex, Attendance.NOT_ATTENDED);

        String expectedMessage = String.format(MESSAGE_MARK_NOT_ATTENDED_SUCCESS,
                editedPerson.getName(), editedPerson.getMatNum(), replacedIndex) + "\n";

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }
    @Test
    public void execute_markMultipleIndexesNotAttendedWeek3UnfilteredList_success() {
        // Boundary value: week 3.
        // Weeks 1 and 2 have attendance status of No Tutorial, hence it is invalid to mark
        // attendance for weeks 1 and 2 on an indexList. (Only for tutGroupList)
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.

        List<Index> indexes = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        int weekToMark = 3;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        StringBuilder expectedMessage = new StringBuilder();

        for (Index index: indexes) {
            Person personToMark = model.getFilteredPersonList().get(index.getZeroBased());
            String existingAttendanceString = personToMark.getAttendanceList().toString();
            String newAttendanceString = replaceAttendanceString(
                    existingAttendanceString, weekToMark, Attendance.NOT_ATTENDED);

            Person editedPerson = new PersonBuilder(personToMark)
                    .withAttendanceList(newAttendanceString).build();
            expectedModel.setPerson(model.getFilteredPersonList().get(index.getZeroBased()), editedPerson);
            expectedMessage.append(String.format(MESSAGE_MARK_NOT_ATTENDED_SUCCESS,
                    editedPerson.getName(), editedPerson.getMatNum(), weekToMark)).append("\n");
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(indexes, weekToMark, Attendance.NOT_ATTENDED);
        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);
    }

    @Test
    public void execute_markMultipleIndexesNotAttendedWeek10UnfilteredList_success() {
        // Other value: week 10.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.

        List<Index> indexes = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        int weekToMark = 10;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        StringBuilder expectedMessage = new StringBuilder();

        for (Index index: indexes) {
            Person personToMark = model.getFilteredPersonList().get(index.getZeroBased());
            String existingAttendanceString = personToMark.getAttendanceList().toString();
            String newAttendanceString = replaceAttendanceString(
                    existingAttendanceString, weekToMark, Attendance.NOT_ATTENDED);

            Person editedPerson = new PersonBuilder(personToMark)
                    .withAttendanceList(newAttendanceString).build();
            expectedModel.setPerson(model.getFilteredPersonList().get(index.getZeroBased()), editedPerson);
            expectedMessage.append(String.format(MESSAGE_MARK_NOT_ATTENDED_SUCCESS,
                    editedPerson.getName(), editedPerson.getMatNum(), weekToMark)).append("\n");
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(indexes, weekToMark, Attendance.NOT_ATTENDED);
        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);
    }

    @Test
    public void execute_markMultipleIndexesNotAttendedWeek13UnfilteredList_success() {
        // Boundary value: Week 13.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.

        List<Index> indexes = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        int weekToMark = 13;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        StringBuilder expectedMessage = new StringBuilder();

        for (Index index: indexes) {
            Person personToMark = model.getFilteredPersonList().get(index.getZeroBased());
            String existingAttendanceString = personToMark.getAttendanceList().toString();
            String newAttendanceString = replaceAttendanceString(
                    existingAttendanceString, weekToMark, Attendance.NOT_ATTENDED);

            Person editedPerson = new PersonBuilder(personToMark)
                    .withAttendanceList(newAttendanceString).build();
            expectedModel.setPerson(model.getFilteredPersonList().get(index.getZeroBased()), editedPerson);
            expectedMessage.append(String.format(MESSAGE_MARK_NOT_ATTENDED_SUCCESS,
                    editedPerson.getName(), editedPerson.getMatNum(), weekToMark)).append("\n");
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(indexes, weekToMark, Attendance.NOT_ATTENDED);
        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);
    }

    @Test
    public void execute_markSingleIndexOnMcUnfilteredList_success() {

        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        String existingAttendanceString = firstPerson.getAttendanceList().toString();
        String replacedIndexAndNewString = getReplacedIndexAndNewString(
                String.valueOf(Attendance.ON_MC), existingAttendanceString,
                existingAttendanceString.indexOf(String.valueOf(Attendance.NOT_ATTENDED)),
                existingAttendanceString.indexOf(String.valueOf(Attendance.ATTENDED)));

        String newAttendanceString = replacedIndexAndNewString.substring(1);
        int replacedIndex = Integer.parseInt(replacedIndexAndNewString.substring(0, 1)) + 1;
        Person editedPerson = new PersonBuilder(firstPerson)
                .withAttendanceList(newAttendanceString).build();
        MarkAttendanceCommand command =
                new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON), replacedIndex, Attendance.ON_MC);

        String expectedMessage = String.format(MESSAGE_MARK_MC_SUCCESS,
                editedPerson.getName(), editedPerson.getMatNum(), replacedIndex) + "\n";

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);

    }

    @Test
    public void execute_markMultipleIndexesOnMcWeek3UnfilteredList_success() {
        // Boundary value: week 3.
        // Weeks 1 and 2 have attendance status of No Tutorial, hence it is invalid to mark
        // attendance for weeks 1 and 2 on an indexList. (Only for tutGroupList)
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.

        List<Index> indexes = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        int weekToMark = 3;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        StringBuilder expectedMessage = new StringBuilder();

        for (Index index: indexes) {
            Person personToMark = model.getFilteredPersonList().get(index.getZeroBased());
            String existingAttendanceString = personToMark.getAttendanceList().toString();
            String newAttendanceString = replaceAttendanceString(
                    existingAttendanceString, weekToMark, Attendance.ON_MC);

            Person editedPerson = new PersonBuilder(personToMark)
                    .withAttendanceList(newAttendanceString).build();
            expectedModel.setPerson(model.getFilteredPersonList().get(index.getZeroBased()), editedPerson);
            expectedMessage.append(String.format(MESSAGE_MARK_MC_SUCCESS,
                    editedPerson.getName(), editedPerson.getMatNum(), weekToMark)).append("\n");
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(indexes, weekToMark, Attendance.ON_MC);
        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);
    }

    @Test
    public void execute_markMultipleIndexesOnMcWeek10UnfilteredList_success() {
        // Other value: week 10.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.

        List<Index> indexes = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        int weekToMark = 10;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        StringBuilder expectedMessage = new StringBuilder();

        for (Index index: indexes) {
            Person personToMark = model.getFilteredPersonList().get(index.getZeroBased());
            String existingAttendanceString = personToMark.getAttendanceList().toString();
            String newAttendanceString = replaceAttendanceString(
                    existingAttendanceString, weekToMark, Attendance.ON_MC);

            Person editedPerson = new PersonBuilder(personToMark)
                    .withAttendanceList(newAttendanceString).build();
            expectedModel.setPerson(model.getFilteredPersonList().get(index.getZeroBased()), editedPerson);
            expectedMessage.append(String.format(MESSAGE_MARK_MC_SUCCESS,
                    editedPerson.getName(), editedPerson.getMatNum(), weekToMark)).append("\n");
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(indexes, weekToMark, Attendance.ON_MC);
        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);
    }

    @Test
    public void execute_markMultipleIndexesOnMcWeek13UnfilteredList_success() {
        // Boundary value: Week 13.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.

        List<Index> indexes = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);

        int weekToMark = 13;
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        StringBuilder expectedMessage = new StringBuilder();

        for (Index index: indexes) {
            Person personToMark = model.getFilteredPersonList().get(index.getZeroBased());
            String existingAttendanceString = personToMark.getAttendanceList().toString();
            String newAttendanceString = replaceAttendanceString(
                    existingAttendanceString, weekToMark, Attendance.ON_MC);

            Person editedPerson = new PersonBuilder(personToMark)
                    .withAttendanceList(newAttendanceString).build();
            expectedModel.setPerson(model.getFilteredPersonList().get(index.getZeroBased()), editedPerson);
            expectedMessage.append(String.format(MESSAGE_MARK_MC_SUCCESS,
                    editedPerson.getName(), editedPerson.getMatNum(), weekToMark)).append("\n");
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(indexes, weekToMark, Attendance.ON_MC);
        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);
    }


    @Test
    public void execute_markIndexGivenNoTutorialUnfilteredList_failure() {

        // First Person uses Default Attendance String, with No Tutorial in Weeks 1 and 2.
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        int weekToEdit = 1;
        MarkAttendanceCommand commandSetWeek1Attended =
                new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON), weekToEdit, Attendance.ATTENDED);
        MarkAttendanceCommand commandSetWeek1NotAttended =
                new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON), weekToEdit, Attendance.NOT_ATTENDED);
        MarkAttendanceCommand commandSetWeek1OnMc =
                new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON), weekToEdit, Attendance.ON_MC);

        String expectedMessage = String.format(MESSAGE_MARK_WHEN_NO_TUTORIAL_FAILURE,
                firstPerson.getName(), firstPerson.getMatNum(),
                firstPerson.getTutGroup(), weekToEdit);

        assertCommandFailure(commandSetWeek1Attended, model, expectedMessage);
        assertCommandFailure(commandSetWeek1NotAttended, model, expectedMessage);
        assertCommandFailure(commandSetWeek1OnMc, model, expectedMessage);

    }

    @Test
    public void execute_markSingleTutGroupNotAttendedWeek1UnfilteredList_success() {
        // Boundary value: week 1.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int weekToEdit = 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NOT_ATTENDED_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.NOT_ATTENDED);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NOT_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.NOT_ATTENDED, List.of(tutGroupToEdit));

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markSingleTutGroupNotAttendedWeek5UnfilteredList_success() {
        // Other value: week 5.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int weekToEdit = 5;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NOT_ATTENDED_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.NOT_ATTENDED);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NOT_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.NOT_ATTENDED, List.of(tutGroupToEdit));

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }


    @Test
    public void execute_markSingleTutGroupNotAttendedWeek13UnfilteredList_success() {
        // Boundary value: week 13.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int weekToEdit = 13;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NOT_ATTENDED_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.NOT_ATTENDED);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NOT_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.NOT_ATTENDED, List.of(tutGroupToEdit));

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }


    @Test
    public void execute_markMultipleTutGroupsNotAttendedWeek1UnfilteredList_success() {
        // Boundary value: week 1.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        List<TutGroup> tutGroupsToEdit = Arrays.asList(new TutGroup("T01"), new TutGroup("T02"), new TutGroup("T03"));
        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) ->
                        acc + tg.toString() + ", ", (x, y) -> x + y);
        tutGroupString = tutGroupString.substring(0, tutGroupString.length() - 2);

        int weekToEdit = 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NOT_ATTENDED_SUCCESS,
                tutGroupString, weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (tutGroupsToEdit.contains(person.getTutGroup())) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.NOT_ATTENDED);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NOT_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.NOT_ATTENDED, tutGroupsToEdit);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markMultipleTutGroupsNotAttendedWeek5UnfilteredList_success() {
        // Other value: week 5.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        List<TutGroup> tutGroupsToEdit = Arrays.asList(new TutGroup("T01"), new TutGroup("T02"), new TutGroup("T03"));
        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) ->
                        acc + tg.toString() + ", ", (x, y) -> x + y);
        tutGroupString = tutGroupString.substring(0, tutGroupString.length() - 2);

        int weekToEdit = 5;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NOT_ATTENDED_SUCCESS,
                tutGroupString, weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (tutGroupsToEdit.contains(person.getTutGroup())) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.NOT_ATTENDED);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NOT_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.NOT_ATTENDED, tutGroupsToEdit);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markMultipleTutGroupsNotAttendedWeek13UnfilteredList_success() {
        // Boundary value: week 13.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        List<TutGroup> tutGroupsToEdit = Arrays.asList(new TutGroup("T01"), new TutGroup("T02"), new TutGroup("T03"));
        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) ->
                        acc + tg.toString() + ", ", (x, y) -> x + y);
        tutGroupString = tutGroupString.substring(0, tutGroupString.length() - 2);

        int weekToEdit = 13;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NOT_ATTENDED_SUCCESS,
                tutGroupString, weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (tutGroupsToEdit.contains(person.getTutGroup())) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.NOT_ATTENDED);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NOT_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.NOT_ATTENDED, tutGroupsToEdit);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markSingleTutGroupAttendedWeek1UnfilteredList_success() {
        // Boundary value: week 1.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int weekToEdit = 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.ATTENDED);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.ATTENDED, List.of(tutGroupToEdit));

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markSingleTutGroupAttendedWeek5UnfilteredList_success() {
        // Other value: week 5.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int weekToEdit = 5;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.ATTENDED);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.ATTENDED, List.of(tutGroupToEdit));

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markSingleTutGroupAttendedWeek13UnfilteredList_success() {
        // Other value: week 13.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int weekToEdit = 13;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.ATTENDED);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.ATTENDED, List.of(tutGroupToEdit));

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markMultipleTutGroupsAttendedWeek1UnfilteredList_success() {
        // Boundary value: week 1.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        List<TutGroup> tutGroupsToEdit = Arrays.asList(new TutGroup("T01"), new TutGroup("T02"), new TutGroup("T03"));
        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) ->
                        acc + tg.toString() + ", ", (x, y) -> x + y);
        tutGroupString = tutGroupString.substring(0, tutGroupString.length() - 2);

        int weekToEdit = 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS,
                tutGroupString, weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (tutGroupsToEdit.contains(person.getTutGroup())) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.ATTENDED);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.ATTENDED, tutGroupsToEdit);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markMultipleTutGroupsAttendedWeek5UnfilteredList_success() {
        // Other value: week 5.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        List<TutGroup> tutGroupsToEdit = Arrays.asList(new TutGroup("T01"), new TutGroup("T02"), new TutGroup("T03"));
        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) ->
                        acc + tg.toString() + ", ", (x, y) -> x + y);
        tutGroupString = tutGroupString.substring(0, tutGroupString.length() - 2);

        int weekToEdit = 5;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS,
                tutGroupString, weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (tutGroupsToEdit.contains(person.getTutGroup())) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.ATTENDED);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.ATTENDED, tutGroupsToEdit);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markMultipleTutGroupsAttendedWeek13UnfilteredList_success() {
        // Boundary value: week 13.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        List<TutGroup> tutGroupsToEdit = Arrays.asList(new TutGroup("T01"), new TutGroup("T02"), new TutGroup("T03"));
        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) ->
                        acc + tg.toString() + ", ", (x, y) -> x + y);
        tutGroupString = tutGroupString.substring(0, tutGroupString.length() - 2);

        int weekToEdit = 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_ATTENDED_SUCCESS,
                tutGroupString, weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (tutGroupsToEdit.contains(person.getTutGroup())) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.ATTENDED);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_ATTENDED_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.ATTENDED, tutGroupsToEdit);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markSingleTutGroupOnMcWeek1UnfilteredList_success() {
        // Boundary value: week 1.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int weekToEdit = 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_MC_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.ON_MC);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_MC_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.ON_MC, List.of(tutGroupToEdit));

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markSingleTutGroupOnMcWeek5UnfilteredList_success() {
        // Other value: week 5.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int weekToEdit = 5;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_MC_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.ON_MC);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_MC_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.ON_MC, List.of(tutGroupToEdit));

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markSingleTutGroupOnMcWeek13UnfilteredList_success() {
        // Boundary value: week 13.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int weekToEdit = 13;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_MC_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.ON_MC);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_MC_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.ON_MC, List.of(tutGroupToEdit));

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markMultipleTutGroupsOnMcWeek1UnfilteredList_success() {
        // Boundary value: week 1.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        List<TutGroup> tutGroupsToEdit = Arrays.asList(new TutGroup("T01"), new TutGroup("T02"), new TutGroup("T03"));
        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) ->
                        acc + tg.toString() + ", ", (x, y) -> x + y);
        tutGroupString = tutGroupString.substring(0, tutGroupString.length() - 2);

        int weekToEdit = 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_MC_SUCCESS,
                tutGroupString, weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (tutGroupsToEdit.contains(person.getTutGroup())) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.ON_MC);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_MC_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.ON_MC, tutGroupsToEdit);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markMultipleTutGroupsOnMcWeek5UnfilteredList_success() {
        // Other value: week 5.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        List<TutGroup> tutGroupsToEdit = Arrays.asList(new TutGroup("T01"), new TutGroup("T02"), new TutGroup("T03"));
        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) ->
                        acc + tg.toString() + ", ", (x, y) -> x + y);
        tutGroupString = tutGroupString.substring(0, tutGroupString.length() - 2);

        int weekToEdit = 5;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_MC_SUCCESS,
                tutGroupString, weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (tutGroupsToEdit.contains(person.getTutGroup())) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.ON_MC);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_MC_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.ON_MC, tutGroupsToEdit);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markMultipleTutGroupsOnMcWeek13UnfilteredList_success() {
        // Boundary value: week 13.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        List<TutGroup> tutGroupsToEdit = Arrays.asList(new TutGroup("T01"), new TutGroup("T02"), new TutGroup("T03"));
        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) ->
                        acc + tg.toString() + ", ", (x, y) -> x + y);
        tutGroupString = tutGroupString.substring(0, tutGroupString.length() - 2);

        int weekToEdit = 13;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_MC_SUCCESS,
                tutGroupString, weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (tutGroupsToEdit.contains(person.getTutGroup())) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.ON_MC);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_MC_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.ON_MC, tutGroupsToEdit);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markSingleTutGroupNoTutorialWeek1UnfilteredList_success() {
        // Boundary value: week 1.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.

        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int weekToEdit = 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.NO_TUTORIAL);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NO_TUTORIAL_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.NO_TUTORIAL, List.of(tutGroupToEdit));

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markSingleTutGroupNoTutorialWeek5UnfilteredList_success() {
        // Other value: week 5.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int weekToEdit = 5;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.NO_TUTORIAL);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NO_TUTORIAL_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.NO_TUTORIAL, List.of(tutGroupToEdit));

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markSingleTutGroupNoTutorialWeek13UnfilteredList_success() {
        // Boundary value: week 13.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        TutGroup tutGroupToEdit = new TutGroup("T01");
        int weekToEdit = 13;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS,
                tutGroupToEdit.toString(), weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (person.getTutGroup().equals(tutGroupToEdit)) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.NO_TUTORIAL);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NO_TUTORIAL_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.NO_TUTORIAL, List.of(tutGroupToEdit));

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markMultipleTutGroupsNoTutorialWeek1UnfilteredList_success() {
        // Boundary value: week 1.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        List<TutGroup> tutGroupsToEdit = Arrays.asList(new TutGroup("T01"), new TutGroup("T02"), new TutGroup("T03"));
        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) ->
                        acc + tg.toString() + ", ", (x, y) -> x + y);
        tutGroupString = tutGroupString.substring(0, tutGroupString.length() - 2);

        int weekToEdit = 1;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS,
                tutGroupString, weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (tutGroupsToEdit.contains(person.getTutGroup())) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.NO_TUTORIAL);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NO_TUTORIAL_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.NO_TUTORIAL, tutGroupsToEdit);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markMultipleTutGroupsNoTutorialWeek5UnfilteredList_success() {
        // Other value: week 5.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        List<TutGroup> tutGroupsToEdit = Arrays.asList(new TutGroup("T01"), new TutGroup("T02"), new TutGroup("T03"));
        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) ->
                        acc + tg.toString() + ", ", (x, y) -> x + y);
        tutGroupString = tutGroupString.substring(0, tutGroupString.length() - 2);

        int weekToEdit = 5;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS,
                tutGroupString, weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (tutGroupsToEdit.contains(person.getTutGroup())) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.NO_TUTORIAL);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NO_TUTORIAL_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.NO_TUTORIAL, tutGroupsToEdit);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_markMultipleTutGroupsNoTutorialWeek13UnfilteredList_success() {
        // Boundary value: week 13.
        // Note: Testing is separated for different test week values, because
        // repeating this test within a single test method leads to errors.
        Model expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        StringBuilder expectedMessage = new StringBuilder();
        List<TutGroup> tutGroupsToEdit = Arrays.asList(new TutGroup("T01"), new TutGroup("T02"), new TutGroup("T03"));
        String tutGroupString = tutGroupsToEdit.stream()
                .reduce("", (acc, tg) ->
                        acc + tg.toString() + ", ", (x, y) -> x + y);
        tutGroupString = tutGroupString.substring(0, tutGroupString.length() - 2);

        int weekToEdit = 13;
        expectedMessage.append(String.format(MESSAGE_MARK_TUT_GROUP_NO_TUTORIAL_SUCCESS,
                tutGroupString, weekToEdit)).append("\n-------------\n");
        for (Person person : model.getFilteredPersonList()) {
            if (tutGroupsToEdit.contains(person.getTutGroup())) {
                String existingAttendanceString = person.getAttendanceList().toString();
                String newAttendanceString = replaceAttendanceString(
                        existingAttendanceString, weekToEdit, Attendance.NO_TUTORIAL);
                Person editedPerson = new PersonBuilder(person)
                        .withAttendanceList(newAttendanceString).build();
                expectedModel.setPerson(person, editedPerson);
                expectedMessage.append(String.format(MESSAGE_MARK_NO_TUTORIAL_SUCCESS,
                        editedPerson.getName(), editedPerson.getMatNum(), weekToEdit)).append("\n");
            }
        }

        MarkAttendanceCommand command = new MarkAttendanceCommand(
                weekToEdit, Attendance.NO_TUTORIAL, tutGroupsToEdit);

        assertCommandSuccess(command, model, expectedMessage.toString(), expectedModel);

    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        MarkAttendanceCommand command = new MarkAttendanceCommand(List.of(outOfBoundIndex), 1, Attendance.ATTENDED);
        assertCommandFailure(command, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, model.getFilteredPersonList().size()));
    }

    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showPersonAtIndex(model, INDEX_FIRST_PERSON);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // Ensures that outOfBoundIndex is still in bounds of address book list.
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        MarkAttendanceCommand command = new MarkAttendanceCommand(List.of(outOfBoundIndex), 1, Attendance.ATTENDED);
        assertCommandFailure(command, model,
                String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX, model.getFilteredPersonList().size()));
    }

    @Test
    public void checkIfIndexFlagCommandValid_emptyAttendanceList_throwsCommandException() {
        // Build a person with empty attendanceList.
        Person personWithEmptyAttendanceList = new PersonBuilder().withTutGroup("").build();
        MarkAttendanceCommand command = new MarkAttendanceCommand(
                List.of(INDEX_FIRST_PERSON), 5, Attendance.ATTENDED);
        assertThrows(CommandException.class, () ->
                command.checkIfIndexFlagCommandValid(personWithEmptyAttendanceList));
    }

    @Test
    public void checkIfIndexFlagCommandValid_existingNoTutorial_throwsCommandException() {
        // Build a person with No Tutorial on all weeks.
        Person personWithNoTutorialAllWeeks = new PersonBuilder().withAttendanceList("3333333333333").build();
        MarkAttendanceCommand command = new MarkAttendanceCommand(
                List.of(INDEX_FIRST_PERSON), 5, Attendance.ATTENDED);
        assertThrows(CommandException.class, () ->
                command.checkIfIndexFlagCommandValid(personWithNoTutorialAllWeeks));
    }

    @Test
    public void checkIfIndexFlagCommandValid_validPerson_doesNotThrowException() {
        // Build a person with empty attendanceList.
        Person personWithEmptyAttendanceList = new PersonBuilder()
                .withTutGroup("T01")
                .withAttendanceList(PersonBuilder.DEFAULT_ATTENDANCE_STRING).build();
        MarkAttendanceCommand command = new MarkAttendanceCommand(
                List.of(INDEX_FIRST_PERSON), 5, Attendance.ATTENDED);
        assertDoesNotThrow(() ->
                command.checkIfIndexFlagCommandValid(personWithEmptyAttendanceList));
    }

    // No tests for generating of success messages, as those are private methods
    // within the MarkAttendanceCommand class.

    @Test
    public void equals() {
        final MarkAttendanceCommand standardCommand =
                new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON), VALID_WEEK_A, Attendance.ATTENDED);

        // Same object -> returns true.
        assertTrue(standardCommand.equals(standardCommand));

        // Different object of same values -> returns true.
        MarkAttendanceCommand commandWithSameValues =
                new MarkAttendanceCommand(List.of(INDEX_FIRST_PERSON), VALID_WEEK_A, Attendance.ATTENDED);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // Null -> returns false.
        assertFalse(standardCommand.equals(null));

        // Different types -> returns false.
        assertFalse(standardCommand.equals(5.0f));

        // Different index list -> returns false.
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(
                List.of(INDEX_SECOND_PERSON), VALID_WEEK_A, Attendance.ATTENDED)));
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(
                List.of(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON), VALID_WEEK_A, Attendance.ATTENDED)));

        // Different week -> returns false.
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(
                List.of(INDEX_FIRST_PERSON), VALID_WEEK_B, Attendance.ATTENDED)));

        // Different attendanceStatus -> returns false.
        assertFalse(standardCommand.equals(new MarkAttendanceCommand(
                List.of(INDEX_FIRST_PERSON), VALID_WEEK_A, Attendance.NOT_ATTENDED)));

        final MarkAttendanceCommand standardCommandWithTutGroup =
                new MarkAttendanceCommand(VALID_WEEK_B, Attendance.NO_TUTORIAL,
                        List.of(new TutGroup(VALID_TUT_GROUP_AMY)));

        // Different tut group list -> returns false.
        assertFalse(standardCommandWithTutGroup.equals(
                new MarkAttendanceCommand(VALID_WEEK_B, Attendance.NO_TUTORIAL,
                List.of(new TutGroup(VALID_TUT_GROUP_BOB)))));

        assertFalse(standardCommandWithTutGroup.equals(
                new MarkAttendanceCommand(VALID_WEEK_B, Attendance.NO_TUTORIAL,
                        List.of(new TutGroup(VALID_TUT_GROUP_AMY), new TutGroup(VALID_TUT_GROUP_BOB)))));

    }

}
