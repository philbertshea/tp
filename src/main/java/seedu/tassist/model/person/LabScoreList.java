package seedu.tassist.model.person;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.tassist.logic.commands.UpdateLabScoreCommand;
import seedu.tassist.logic.commands.exceptions.CommandException;

/**
 * Handles the list of LabScore objects.
 */
public class LabScoreList {
    public static final String INVALID_LAB_SCORE = "Lab score needs to be a number";
    public static final String INVALID_LAB_SAVE = "Lab string is loaded incorrectly";
    public static final String EXCEED_MAX_LAB_SCORE_LIMIT = "Maximum limit of lab score is 100";
    public static final String INVALID_LAB_MAX_SCORE =
            "Person %d has score higher than the max lab score (%d) that you wish to set.";
    private static int labTotal = 4;
    public static final String LAB_NUMBER_CONSTRAINT = String.format(
            "Lab number must be provided and must be between 1 and %d", labTotal);
    private static boolean isInitialization = true;
    private static int limitMaxLabScore = 100;
    private static boolean isUpdatingBothScores = true;
    private ArrayList<LabScore> labScoreList = new ArrayList<>();

    /**
     * Creates a default LabScoreList object.
     */
    public LabScoreList() {
        for (int i = 0; i < labTotal; i++) {
            labScoreList.add(new LabScore(i));
        }
    }

    /**
     * Creates a LabScoreList object using the save file values.
     *
     * @param labs the array of values to initialize the list with.
     */
    public LabScoreList(String[] labs) {
        for (int i = 0; i < labs.length; i++) {
            String[] scoreSplit = labs[i].split("/");
            Integer labScore = scoreSplit[0].equals("-") ? -1 : Integer.parseInt(scoreSplit[0]);
            Integer maxLabScore = Integer.parseInt(scoreSplit[1]);
            labScoreList.add(new LabScore(labScore, maxLabScore));
            LabScore.updateMaxLabScore(i, maxLabScore);
        }
    }

    /**
     * Creates a LabScoreList object using the save file values.
     *
     * @param labs The array of values to initialize the list with.
     */
    public LabScoreList(LabScore[] labs) {
        Collections.addAll(labScoreList, labs);
    }

    /**
     * Updates the specified lab with the updated score.
     *
     * @param labNumber The LabScore object to update.
     * @param labScore The updated score for the lab.
     * @return Updated {@code LabScoreList}.
     * @throws CommandException If lab number is invalid.
     */
    public LabScoreList updateLabScore(int labNumber, int labScore) throws CommandException {
        LabScore[] copiedScores = getLabScoresWhenValid(labNumber);
        copiedScores[labNumber - 1] = copiedScores[labNumber - 1].updateLabScore(labScore);
        return new LabScoreList(copiedScores);
    }

    /**
     * Updates the specified lab with the updated max score.
     *
     * @param labNumber The LabScore object to update.
     * @param maxLabScore The updated max score for the lab.
     * @return Updated {@code LabScoreList}.
     * @throws CommandException If lab number is invalid.
     */
    public LabScoreList updateMaxLabScore(int labNumber, int maxLabScore, List<Person> allContacts)
            throws CommandException {
        validMaxLabScore(!isUpdatingBothScores, labNumber, maxLabScore, allContacts);

        LabScore[] copiedScores = getLabScoresWhenValid(labNumber);
        copiedScores[labNumber - 1] = copiedScores[labNumber - 1].updateMaxLabScore(maxLabScore);
        LabScore.updateMaxLabScore(labNumber - 1, maxLabScore);

        return new LabScoreList(copiedScores);
    }

    /**
     * Updates the specified lab with the updated score and max score.
     *
     * @param labNumber The lab of the LabScore object to update.
     * @param labScore The updated score for the lab.
     * @param maxLabScore The updated max score for the lab.
     * @return Updated {@code LabScoreList}.
     * @throws CommandException When lab number is invalid.
     */
    public LabScoreList updateBothLabScore(int labNumber, int labScore, int maxLabScore, List<Person> allContacts)
            throws CommandException {
        validMaxLabScore(isUpdatingBothScores, labNumber, maxLabScore, allContacts);

        LabScore[] copiedScores = getLabScoresWhenValid(labNumber);
        copiedScores[labNumber - 1] = copiedScores[labNumber - 1].updateBothLabScore(labScore, maxLabScore);
        LabScore.updateMaxLabScore(labNumber - 1, maxLabScore);

        return new LabScoreList(copiedScores);
    }

    /**
     * Refreshes the {@code maxLabScore} for all the labs involved.
     *
     * @param labNumber The LabScore object to update.
     * @param maxLabScore The updated max score for the lab.
     * @return Updated {@code LabScoreList}.
     */
    public LabScoreList refreshLabScore(int labNumber, int maxLabScore) {
        LabScore[] copiedScores = Arrays.copyOf(labScoreList.toArray(new LabScore[labTotal]), labTotal);
        copiedScores[labNumber - 1] = copiedScores[labNumber - 1].refreshMaxScore(maxLabScore);

        return new LabScoreList(copiedScores);
    }

    /**
     * Copies the list of lab scores.
     *
     * @param labNumber The lab of the LabScore object to update.
     * @return The copied list of lab scores.
     * @throws CommandException If lab number is invalid.
     */
    public LabScore[] getLabScoresWhenValid(int labNumber) throws CommandException {
        if (labNumber < 1 || labNumber > labTotal) {
            throw new CommandException(String.format(
                    UpdateLabScoreCommand.MESSAGE_INVALID_LAB_NUMBER, labTotal));
        }

        return Arrays.copyOf(labScoreList.toArray(new LabScore[labTotal]), labTotal);
    }

    /**
     * Checks if the lab number is valid.
     *
     * @param labNumber The string of the lab number to verify.
     * @return A boolean representing if the lab number is valid.
     */
    public static boolean isValidLabNumber(String labNumber) {
        int labNo;

        try {
            labNo = Integer.parseInt(labNumber);
        } catch (NumberFormatException e) {
            return false;
        }

        return labNo > 0 && labNo <= labTotal;
    }

    /**
     * Checks if the provided max score is valid.
     *
     * @param isUpdatingBoth Whether the function that calls this is updating both max score and score.
     * @param labNumber The lab of the score to check.
     * @param maxLabScore The score that is being checked.
     * @param allContacts The list of people that needs to be checked.
     * @throws CommandException If {@code maxLabScore} is not valid.
     */
    private void validMaxLabScore(boolean isUpdatingBoth, int labNumber, int maxLabScore, List<Person> allContacts)
            throws CommandException {
        if (maxLabScore < 0) {
            throw new CommandException(UpdateLabScoreCommand.MESSAGE_INVALID_NEGATIVE_SCORE);
        }

        if (maxLabScore > limitMaxLabScore) {
            throw new CommandException(EXCEED_MAX_LAB_SCORE_LIMIT);
        }

        for (int i = 0; i < allContacts.size(); i++) {
            LabScoreList checkList = allContacts.get(i).getLabScoreList();
            if (checkList == this && isUpdatingBoth) {
                continue;
            }

            boolean validMaxScore = checkList.labScoreList.get(labNumber - 1).testValidMaxScore(maxLabScore);
            if (!validMaxScore) {
                throw new CommandException(String.format(INVALID_LAB_MAX_SCORE, i + 1, maxLabScore));
            }
        }
    }

    /**
     * Gets the list of LabScore objects.
     *
     * @return The list of LabScore objects.
     */
    public ArrayList<LabScore> getLabScores() {
        return labScoreList;
    }

    /**
     * Checks if the save string is valid.
     *
     * @param saveString The string to validate if it is correct.
     * @return A boolean showing if the string is valid.
     */
    public static boolean isValidSaveString(String saveString) {
        int total;

        // Get total number of labs
        int split = saveString.indexOf(".");
        try {
            if (split == -1) {
                return false;
            }
            total = Integer.parseInt(saveString.substring(0, split));
        } catch (NumberFormatException e) {
            return false;
        }

        String[] labs = saveString.substring(split + 1).split("\\Q|\\E");
        if (labs.length != total) {
            return false;
        }

        // Validate individual lab string
        for (int i = 0; i < total; i++) {
            boolean isLabValid = isValidLabSaveString(labs[i], i);
            if (!isLabValid) {
                return false;
            }
        }

        if (isInitialization) {
            isInitialization = false;
        }
        return true;
    }

    /**
     * Checks if the lab save string is valid.
     *
     * @param labString The string to validate if it is correct.
     * @return A boolean showing if the string is valid.
     */
    private static boolean isValidLabSaveString(String labString, int labNumber) {
        String[] scoreSplit = labString.split("/");
        if (scoreSplit.length != 2) {
            return false;
        }

        try {
            if (!scoreSplit[0].equals("-")) {
                Integer.parseInt(scoreSplit[0]);
            }
            int fileLabMaxScore = Integer.parseInt(scoreSplit[1]);

            if (!isInitialization) {
                return fileLabMaxScore == LabScore.getMaxLabScore(labNumber);
            }

        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }

    /**
     * Loads the lab scores from the save file.
     *
     * @param saveString The string from the save file.
     * @return LabScoreList object.
     */
    public static LabScoreList loadLabScores(String saveString) {
        int split = saveString.indexOf(".");
        String[] labs = saveString.substring(split + 1).split("\\Q|\\E");

        return new LabScoreList(labs);
    }

    @Override
    public String toString() {
        StringBuilder labScoreListString = new StringBuilder();
        labScoreListString.append(String.format("%d.", labTotal));
        for (int i = 0; i < labScoreList.size(); i++) {
            labScoreListString.append(labScoreList.get(i).toString());
            if (i == labScoreList.size() - 1) {
                break;
            }
            labScoreListString.append("|");
        }
        return labScoreListString.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof LabScoreList)) {
            return false;
        }

        LabScoreList o = (LabScoreList) other;
        for (int i = 0; i < o.labScoreList.size(); i++) {
            if (!labScoreList.get(i).equals(o.labScoreList.get(i))) {
                return false;
            }
        }
        return true;
    }
}
