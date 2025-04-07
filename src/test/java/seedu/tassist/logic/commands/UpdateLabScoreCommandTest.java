package seedu.tassist.logic.commands;

import static seedu.tassist.logic.commands.CommandTestUtil.DEFAULT_LAB_MAX_SCORE;
import static seedu.tassist.logic.commands.CommandTestUtil.DEFAULT_LAB_SCORE_COUNT;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_NUMBER_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_SCORE_A;
import static seedu.tassist.logic.commands.CommandTestUtil.VALID_LAB_SCORE_B;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.tassist.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.tassist.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.tassist.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.model.Model;
import seedu.tassist.model.ModelManager;
import seedu.tassist.model.UserPrefs;
import seedu.tassist.model.person.LabScoreList;
import seedu.tassist.model.person.Person;
import seedu.tassist.testutil.PersonBuilder;

public class UpdateLabScoreCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void successCaseUpdateScore() {
        // EP: success case for updating lab score
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(firstPerson)
                .withLabScores("4.20/25|-/25|-/25|-/25").build();

        UpdateLabScoreCommand command = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, VALID_LAB_SCORE_A, false);
        String expectedMessage = String.format(UpdateLabScoreCommand.MESSAGE_UPDATE_LAB_SCORE_SUCCESS,
                VALID_LAB_NUMBER_A, INDEX_FIRST_PERSON.getOneBased(),
                editedPerson.getLabScoreList().getLabScores().get(VALID_LAB_NUMBER_A - 1).toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void successCaseUpdateMaxScore() {
        // EP: success case for updating max lab score
        UpdateLabScoreCommand command = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, VALID_LAB_SCORE_B, true);
        String expectedMessage = String.format(UpdateLabScoreCommand.MESSAGE_UPDATE_LAB_MAX_SCORE_SUCCESS,
                VALID_LAB_NUMBER_A, VALID_LAB_SCORE_B);
        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        for (int i = 0; i < model.getFilteredPersonList().size(); i++) {
            Person person = model.getFilteredPersonList().get(i);
            LabScoreList labScoreList = person.getLabScoreList().refreshLabScore(VALID_LAB_NUMBER_A, VALID_LAB_SCORE_B);
            Person editedPerson = new PersonBuilder(person).withLabScores(labScoreList.toString()).build();
            expectedModel.setPerson(person, editedPerson);
        }

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }

    @Test
    public void successCaseUpdateBothScore() {
        // EP: success case for updating both lab scores
        Person firstPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedFirstPerson = new PersonBuilder(firstPerson)
                .withLabScores("4.20/30|-/25|-/25|-/25").build();

        UpdateLabScoreCommand command = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, VALID_LAB_SCORE_A, VALID_LAB_SCORE_B);
        String expectedMessage = String.format(UpdateLabScoreCommand.MESSAGE_UPDATE_BOTH_SCORES_SUCCESS,
                VALID_LAB_NUMBER_A, VALID_LAB_SCORE_B, INDEX_FIRST_PERSON.getOneBased(),
                editedFirstPerson.getLabScoreList().getLabScores().get(VALID_LAB_NUMBER_A - 1).toString());

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.setPerson(model.getFilteredPersonList().get(0), editedFirstPerson);
        for (int i = 1; i < model.getFilteredPersonList().size(); i++) {
            Person person = model.getFilteredPersonList().get(i);
            LabScoreList labScoreList = person.getLabScoreList().refreshLabScore(VALID_LAB_NUMBER_A, VALID_LAB_SCORE_B);
            Person editedPerson = new PersonBuilder(person).withLabScores(labScoreList.toString()).build();
            expectedModel.setPerson(person, editedPerson);
        }

        assertCommandSuccess(command, model, expectedMessage, expectedModel);
    }



    @Test
    public void indexOutOfBoundFail() {
        // EP: invalid index
        // Boundary value: just above limit
        // Note: Boundary value below limit ( <= 0) were validated in parser
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        UpdateLabScoreCommand command = new UpdateLabScoreCommand(outOfBoundIndex,
                VALID_LAB_NUMBER_A, VALID_LAB_SCORE_A, false);
        assertCommandFailure(command, model, UpdateLabScoreCommand.MESSAGE_INVALID_INDEX);
    }

    @Test
    public void labNumberOutOfBoundFail() {
        String validErrorMessage = String.format(UpdateLabScoreCommand.MESSAGE_INVALID_LAB_NUMBER,
                DEFAULT_LAB_SCORE_COUNT);
        // EP: invalid index
        // Boundary value: below limit (-1)
        UpdateLabScoreCommand negativeLabNumber =
                new UpdateLabScoreCommand(INDEX_FIRST_PERSON, -1, VALID_LAB_SCORE_A, false);

        assertCommandFailure(negativeLabNumber, model, validErrorMessage);

        // EP: invalid index
        // Boundary value: below limit (0)
        UpdateLabScoreCommand zeroLabNumber =
                new UpdateLabScoreCommand(INDEX_FIRST_PERSON, -1, VALID_LAB_SCORE_A, false);
        assertCommandFailure(zeroLabNumber, model, validErrorMessage);

        // EP: invalid index
        // Boundary value: above limit (5)
        UpdateLabScoreCommand exceedLabNumber =
                new UpdateLabScoreCommand(INDEX_FIRST_PERSON, 5, VALID_LAB_SCORE_A, false);
        assertCommandFailure(exceedLabNumber, model, validErrorMessage);
    }

    @Test
    public void testScoreOutOfBoundFail() {
        // EP: exceed maximum score
        // Boundary value: 26 (Default max value + 1)
        int invalidScore = DEFAULT_LAB_MAX_SCORE + 1;
        UpdateLabScoreCommand command = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, invalidScore, false);
        String validErrorMessage = String.format(UpdateLabScoreCommand.MESSAGE_INVALID_SCORE, invalidScore,
                DEFAULT_LAB_MAX_SCORE);
        assertCommandFailure(command, model, validErrorMessage);
    }

    @Test
    public void testNegativeScoreOutOfBoundFail() {
        // EP: negative score
        // Boundary value: -1
        int invalidScore = -1;
        UpdateLabScoreCommand command = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, invalidScore, false);
        String validErrorMessage = String.format(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        assertCommandFailure(command, model, validErrorMessage);
    }

    @Test
    public void testMaxScoreOutOfBoundFail() {
        // EP: max score below score
        // Boundary value: (valid score - 1)
        int invalidMaxScore = VALID_LAB_SCORE_A - 1;
        UpdateLabScoreCommand invalidMaxScoreCommand = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, VALID_LAB_SCORE_A, invalidMaxScore);

        String validErrorMessage = String.format(LabScoreList.INVALID_LAB_MAX_SCORE, 4, invalidMaxScore);

        assertCommandFailure(invalidMaxScoreCommand, model, validErrorMessage);

        // EP: max score above limit
        // Boundary value: 101
        UpdateLabScoreCommand exceedMaxScoreLimit = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, VALID_LAB_SCORE_A, 101);
        String exceedLimitMessage = LabScoreList.EXCEED_MAX_LAB_SCORE_LIMIT;
        assertCommandFailure(exceedMaxScoreLimit, model, exceedLimitMessage);
    }

    @Test
    public void testNegativeMaxScoreOutOfBoundFail() {
        // EP: max score below 0
        // Boundary value: -1
        int invalidScore = -1;
        UpdateLabScoreCommand command = new UpdateLabScoreCommand(INDEX_FIRST_PERSON,
                VALID_LAB_NUMBER_A, invalidScore, true);
        String validErrorMessage = String.format(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        assertCommandFailure(command, model, validErrorMessage);
    }

}
