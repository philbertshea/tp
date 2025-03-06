package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;

/**
 * Represents the attendance status for a week.
 */
public class Attendance {
    public static final int NOT_ATTENDED = 0;
    public static final int ATTENDED = 1;
    public static final int ON_MC = 2;

    private int attendance;

    /**
     * Instantiates the Attendance instance, assigning as not attended.
     */
    public Attendance() {
        this.attendance = NOT_ATTENDED;
    }

    /**
     * Takes in {@code atttendance} and sets attendance to that given value.
     *
     * @param attendance Attendance to set to.
     */
    public void setAttendance(int attendance) {
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
        return this.attendance == otherAttendance.attendance;
    }
}
