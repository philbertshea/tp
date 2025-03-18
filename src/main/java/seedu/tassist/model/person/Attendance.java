package seedu.tassist.model.person;


import static seedu.tassist.commons.util.AppUtil.checkArgument;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

/**
 * Represents the attendance status for a week.
 */
public class Attendance {
    public static final int NOT_ATTENDED = 0;
    public static final int ATTENDED = 1;
    public static final int ON_MC = 2;
    public static final int NO_TUTORIAL = 3;
    public static final String ATTENDED_IMAGE_PATH = "images/check_icon.png";
    public static final String NOT_ATTENDED_IMAGE_PATH = "images/cross_icon.png";
    public static final String NO_TUTORIAL_IMAGE_PATH = "images/ban_icon.png";

    public static final String MESSAGE_CONSTRAINTS = "Invalid week or attendance!\n"
            + "Week must be an integer from 1 to 13 inclusive.\n"
            + "Attendance must be an integer of value 0, 1, 2 or 3.";

    private int attendance;
    private final int week;

    /**
     * Instantiates the Attendance instance, assigning as not attended.
     */
    public Attendance(int week, int attendance) {
        checkArgument(isValidAttendance(attendance), MESSAGE_CONSTRAINTS);
        checkArgument(isValidWeek(week), MESSAGE_CONSTRAINTS);
        this.week = week;
        this.attendance = attendance;
    }

    /**
     * Returns attendance to the parameter {@code attendance}.
     *
     * @return Attendance value of the instance.
     */
    public int getAttendance() {
        return this.attendance;
    }

    /**
     * Returns true if a given attendance is a valid attendance.
     *
     * @return Boolean
     */
    public static boolean isValidAttendance(int attendance) {
        return attendance == ATTENDED
                || attendance == NOT_ATTENDED
                || attendance == ON_MC
                || attendance == NO_TUTORIAL;
    }

    /**
     * Returns true if a given week is a valid week.
     *
     * @return Boolean value representing whether week is a valid week.
     */
    public static boolean isValidWeek(int week) {
        return week > 0 && week < 14;
    }

    /**
     * Returns the Tag name of the Attendance object.
     *
     * @return Tag name to be displayed for the Attendance object.
     */
    public HBox getTagHBox() {
        ImageView imgView = new ImageView();
        Label label = new Label("W" + this.week + ":");
        HBox hBox = new HBox(5);
        hBox.getStyleClass().add("hbox");
        switch (this.attendance) {
        case NOT_ATTENDED:
            imgView = new ImageView(new Image(NOT_ATTENDED_IMAGE_PATH));
            hBox.setStyle("-fx-background-color: #d70654;");
            break;
        case ATTENDED:
            imgView = new ImageView(new Image(ATTENDED_IMAGE_PATH));
            hBox.setStyle("-fx-background-color: #5cb338;");
            break;
        case ON_MC:
            // Text works best for Attendance Status ON_MC
            hBox.setStyle("-fx-background-color: #df6d14;");
            hBox.getChildren().add(new Label("W" + this.week + ": MC"));
            return hBox;
        case NO_TUTORIAL:
            imgView = new ImageView(new Image(NO_TUTORIAL_IMAGE_PATH));
            hBox.setStyle("-fx-background-color: #A9A9A9;");
            break;
        default:
            break;
        }
        imgView.setFitHeight(11);
        imgView.setFitWidth(11);
        hBox.getChildren().addAll(label, imgView);

        return hBox;

    }

    @Override
    public String toString() {
        return this.attendance + "";
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof Attendance)) {
            return false;
        }

        Attendance otherAttendance = (Attendance) other;

        // No two Attendance instances in the AttendanceList
        // should have the SAME week.
        return this.week == otherAttendance.week
                && this.attendance == otherAttendance.attendance;
    }
}
