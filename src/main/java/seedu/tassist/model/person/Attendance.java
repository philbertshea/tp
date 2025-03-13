package seedu.tassist.model.person;


/**
 * Represents the attendance status for a week.
 */
public class Attendance {
    public static final int NOT_ATTENDED = 0;
    public static final int ATTENDED = 1;
    public static final int ON_MC = 2;

    private int attendance;
    private final int week;

    /**
     * Instantiates the Attendance instance, assigning as not attended.
     */
    public Attendance(int week, int attendance) {
        this.week = week;
        this.attendance = attendance;
    }

    /**
     * Returns attendance to the parameter {@code attendance}.
     *
     * @return attendance value of the instance.
     */
    public int getAttendance() {
        return this.attendance;
    }

    /**
     * Returns true if a given attendance is a valid attendance.
     */
    public static boolean isValidAttendance(int attendance) {
        return attendance == ATTENDED
                || attendance == NOT_ATTENDED
                || attendance == ON_MC;
    }

    public String tagName() {
        return "W" + this.week + ": " + this.attendance;
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
        return this.week == otherAttendance.week;
    }
}
