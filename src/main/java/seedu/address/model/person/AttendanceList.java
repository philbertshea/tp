package seedu.address.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.AppUtil.checkArgument;
import static seedu.address.model.person.Attendance.isValidAttendance;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a list of Attendance instances.
 */
public class AttendanceList {
    public static final String MESSAGE_CONSTRAINTS =
            "Week number must be unsigned integer from 1 to 13 inclusive.\n"
            + "Attendance value must be 0, 1 or 2.\n"
            + "0 representing Not Attended, 1 representing Attended, 2 representing On MC.";
    public static final String ATTENDANCE_STRING_MESSAGE_CONSTRAINTS =
            "Attendance string must contain exactly 13 digits, each being 0, 1, or 2\n"
            + "whereby the ith digit (from the left) represents the attendance for ith week\n"
            + "0 representing Not Attended, 1 representing Attended, 2 representing On MC.";

    private ArrayList<Attendance> attendanceList;

    /**
     * Instantiates the AttendanceList instance,
     * to represent a list of Attendance for 13 weeks.
     */
    public AttendanceList() {
        this.attendanceList = new ArrayList<>();
        for (int i = 0; i < 13; i++) {
            this.attendanceList.add(new Attendance(i + 1));
        }
    }

    /**
     * Checks if attendanceString has 13 digits, each digit being 0, 1, or 2.
     * @param attendanceString Attendance String to check.
     * @return boolean representing whether attendanceString is valid.
     */
    public static boolean isValidAttendanceString(String attendanceString) {
        return attendanceString.matches("^[012]{13}$");
    }

    /**
     * Generates AttendanceList based on attendanceString.
     * @param attendanceString Attendance String to generate AttendanceList from.
     * @return AttendanceList generated.
     */
    public static AttendanceList generateAttendanceList(String attendanceString) {
        requireNonNull(attendanceString);
        checkArgument(isValidAttendanceString(attendanceString), ATTENDANCE_STRING_MESSAGE_CONSTRAINTS);
        AttendanceList attendanceList = new AttendanceList();
        for (int i = 0; i < 13; i++) {
            attendanceList.setAttendanceForWeek(i + 1, Integer.parseInt(attendanceString.charAt(i) + ""));
        }
        return attendanceList;
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

    public Stream<Attendance> getAttendanceStream() {
        return this.attendanceList.stream();
    }

    @Override
    public String toString() {
        return attendanceList.stream()
                .map(attendance -> attendance.toString())
                .collect(Collectors.joining(""));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AttendanceList)) {
            return false;
        }

        AttendanceList otherAttendanceList = (AttendanceList) other;
        return this.toString().equals(otherAttendanceList.toString());
    }
}
