package seedu.tassist.model.person;

import java.util.ArrayList;

public class LabScoreList {
    private ArrayList<LabScore> labScoreList = new ArrayList<>();

    private static int labTotal = 4;

    public static final String LAB_NUMBER_CONSTRAINT = String.format("Lab number must be between 1 and %d", labTotal);
    public static final String INVALID_LAB_SCORE = "Lab score needs to be a number";

    public LabScoreList(){
        for (int i = 0; i < labTotal; i++) {
            labScoreList.add(new LabScore());
        }
    }


    public LabScoreList updateLabScore(int labNumber, int labScore){
        labScoreList.get(labNumber - 1).updateLabScore(labScore);
        return this;
    }
    public static boolean isValidLabNumber(String labNumber) {
        int labNo;
        try {
            labNo = Integer.parseInt(labNumber);
        } catch (NumberFormatException e) {
            return false;
        }

        return labNo > 0 && labNo < labTotal;
    }

    public ArrayList<LabScore> getLabScores(){
        return labScoreList;
    }


}
