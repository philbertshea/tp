package seedu.address.model.person;

import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.Attendance.isValidAttendance;

import java.util.ArrayList;

/**
 * Represents a list of Attendance instances.
 */
public class AttendanceList {
    public static final String MESSAGE_CONSTRAINTS
            = "Week number must be unsigned integer from 1 to 13 inclusive.";
    private ArrayList<Attendance> attendanceList;

    /**
     * Instantiates the AttendanceList instance,
     * to represent a list of Attendance for 13 weeks.
     */
    public AttendanceList() {
        this.attendanceList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            this.attendanceList.add(new Attendance());
        }
    }

    /**
     * Gets attendance for a particular week.
     *
     * @param week Week to get attendance for.
     * @return attendance for that week.
     */
    public int getAttendanceForWeek(int week) {
        checkArgument(week > 0 && week < 14, MESSAGE_CONSTRAINTS);
        return this.attendanceList.get(week - 1).getAttendance();
    }

    /**
     * Sets attendance for a particular week.
     *
     * @param week Week to set attendance for.
     * @param attendance New attendance to be set to.
     */
    public void setAttendanceForWeek(int week, int attendance) {
        checkArgument(week > 0 && week < 14, MESSAGE_CONSTRAINTS);
        checkArgument(isValidAttendance(attendance), MESSAGE_CONSTRAINTS);
        this.attendanceList.get(week - 1).setAttendance(attendance);
    }
}
