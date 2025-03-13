package seedu.tassist.model.person;

import java.io.Console;
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

    public LabScoreList(String[] labs){
        for (int i = 0; i < labs.length; i++) {
            if(labs[i].equals("-")){
                labScoreList.add(new LabScore());
            } else {
                String[] scoreSplit = labs[i].split("/");
                labScoreList.add(new LabScore(Integer.parseInt(scoreSplit[0]), Integer.parseInt(scoreSplit[1])));
            }
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

    public static boolean isValidSaveString(String saveString){
        int total;
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

        for (int i = 0; i < total; i++) {
            System.out.println(labs[i]);
            if(labs[i].equals("-")) {
                continue;
            }
            String[] scoreSplit = labs[i].split("/");
            if (scoreSplit.length != 2) {
                return false;
            }

            try {
                Integer.parseInt(scoreSplit[0]);
                Integer.parseInt(scoreSplit[1]);
            } catch (NumberFormatException e) {
                return false;
            }

        }

        return true;
    }

    public static LabScoreList loadLabScores(String saveString){

        int split = saveString.indexOf(".");
        String[] labs = saveString.substring(split + 1).split("\\Q|\\E");
        System.out.println("here");
        return new LabScoreList(labs);
    }

    @Override
    public String toString(){
        StringBuilder returnString = new StringBuilder();
        returnString.append(String.format("%d.", labTotal));
        for (int i = 0; i < labScoreList.size(); i++){
            returnString.append(labScoreList.get(i).toString());
            if (i == labScoreList.size() - 1) {
                break;
            }
            returnString.append("|");
        }
        return returnString.toString();
    }

}
