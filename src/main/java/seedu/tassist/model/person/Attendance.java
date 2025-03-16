package seedu.tassist.model.person;


import static seedu.tassist.commons.util.AppUtil.checkArgument;

/**
 * Represents the attendance status for a week.
 */
public class Attendance {
    public static final int NOT_ATTENDED = 0;
    public static final int ATTENDED = 1;
    public static final int ON_MC = 2;
    public static final String CHECK_EMOJI_UNICODE = "\u2714";
    public static final String CROSS_EMOJI_UNICODE = "\u274C";
    public static final String SICK_EMOJI_UNICODE = "\uD83C\uDF21";

    public static final String MESSAGE_CONSTRAINTS = "Invalid week or attendance!\n"
            + "Week must be an integer from 1 to 13 inclusive.\n"
            + "Attendance must be an integer of value 0, 1, or 2.";

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
                || attendance == ON_MC;
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
    public String getTagName() {
        switch (this.attendance) {
        case NOT_ATTENDED:
            return "W" + this.week + ": " + CROSS_EMOJI_UNICODE;
        case ATTENDED:
            return "W" + this.week + ": " + CHECK_EMOJI_UNICODE;
        case ON_MC:
            return "W" + this.week + ": " + SICK_EMOJI_UNICODE;
        default:
            return "";
        }
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
