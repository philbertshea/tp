package seedu.tassist.model.person;

import seedu.tassist.logic.commands.UpdateLabScoreCommand;

public class LabScore {
    private int labScore;
    private int maxLabScore = 25;

    public LabScore(){
        labScore = -1;
    }

    public LabScore(int labScore){
        this.labScore = labScore;
    }

    public LabScore(int labScore, int maxLabScore) {
        this.labScore = labScore;
        this.maxLabScore = labScore;
    }

    public void updateLabScore(int labScore){
        this.labScore = labScore;
    }

    @Override
    public String toString(){
        if(labScore == -1) {
            return "-";
        }

        return String.format("%d/%d", labScore, maxLabScore);
    }

    @Override
    public boolean equals(Object other){
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LabScore)) {
            return false;
        }

        LabScore o = (LabScore) other;
        return this.labScore == o.labScore
                && this.maxLabScore == o.maxLabScore;
    }
}
