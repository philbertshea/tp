package seedu.tassist.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.AppUtil.checkArgument;
import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tassist.model.person.Attendance.isValidAttendance;
import static seedu.tassist.model.person.Attendance.isValidWeek;

import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Represents a list of Attendance instances.
 */
public class AttendanceList {
    public static final String MESSAGE_CONSTRAINTS =
            "Week number must be unsigned integer from 1 to 13 inclusive.\n"
            + "Attendance status value must be 0, 1, 2 or 3.\n"
            + "0 representing Not Attended, 1 representing Attended, \n"
            + "2 representing On MC, 3 representing No Tutorial.\n";
    public static final String ATTENDANCE_STRING_MESSAGE_CONSTRAINTS =
            "Attendance string must contain exactly 13 digits, each being 0, 1, 2 or 3\n"
            + "whereby the ith digit (from the left) represents the attendance for ith week\n"
            + "0 representing Not Attended, 1 representing Attended, \n"
            + "2 representing On MC, 3 representing No Tutorial.\n"
            + "Additional Restriction: A person WITHOUT a tutorial group must have an empty attendance string\n"
            + "while a person WITH a tutorial group must have a 13-digit attendance string.\n";
    public static final String DEFAULT_ATTENDANCE_STRING = "3300000000000";
    public static final AttendanceList EMPTY_ATTENDANCE_LIST = new AttendanceList();

    private ArrayList<Attendance> attendanceList;

    /**
     * Instantiates the AttendanceList instance,
     * to represent a list of Attendance for 13 weeks.
     */
    private AttendanceList() {
        this.attendanceList = new ArrayList<>();
    }

    /**
     * Checks if attendanceString has 13 digits, each digit being 0, 1, 2 or 3.
     *
     * @param attendanceString Attendance String to check.
     * @return Boolean representing whether attendanceString is valid.
     */
    public static boolean isValidNonEmptyAttendanceString(String attendanceString) {
        requireNonNull(attendanceString);
        return attendanceString.matches("^[0123]{13}$");
    }

    /**
     * Takes in an {@code attendanceString} and a {@code tutGroup}.
     * If {@code tutGroup} is empty, returns whether the {@code attendanceString} is also empty.
     * Else, returns whether the {@code attendanceString} is a valid non-empty Attendance String.
     * Rationale: Students with an empty {@code tutGroup} must have an empty {@code attendanceString}.
     * Students with a non-empty {@code tutGroup} must have a valid non-empty {@code attendanceString}.
     *
     * @param attendanceString Attendance String to be checked.
     * @param tutGroup Tutorial Group of the person to check attendance string for.
     * @return Boolean representing whether attendanceString is valid.
     */
    public static boolean isValidAttendanceStringGivenTutGroup(String attendanceString, TutGroup tutGroup) {
        requireAllNonNull(attendanceString, tutGroup);
        if (tutGroup.isEmpty()) {
            return attendanceString.isEmpty();
        } else {
            return isValidNonEmptyAttendanceString(attendanceString);
        }
    }

    /**
     * Generates AttendanceList based on a valid attendanceString.
     *
     * @param attendanceString Attendance String to generate AttendanceList from.
     * @return AttendanceList generated.
     */
    public static AttendanceList generateAttendanceList(String attendanceString) {
        requireNonNull(attendanceString);
        checkArgument(attendanceString.isEmpty() || isValidNonEmptyAttendanceString(attendanceString),
                ATTENDANCE_STRING_MESSAGE_CONSTRAINTS);

        if (attendanceString.isEmpty()) {
            return EMPTY_ATTENDANCE_LIST;
        }

        AttendanceList attendanceList = new AttendanceList();
        for (int i = 0; i < 13; i++) {
            attendanceList.attendanceList.add(
                    new Attendance(i + 1,
                            Integer.parseInt(attendanceString.charAt(i) + "")));
        }
        return attendanceList;
    }

    /**
     * Gets attendance for a particular week.
     *
     * @param week Week to get attendance for.
     * @return Attendance for that week.
     */
    public int getAttendanceForWeek(int week) {
        checkArgument(isValidWeek(week), MESSAGE_CONSTRAINTS);
        return this.attendanceList.get(week - 1).getAttendance();
    }

    /**
     * Sets attendance for a particular week.
     *
     * @param week Week to set attendance for.
     * @param attendance New attendance to be set to.
     */
    public AttendanceList setAttendanceForWeek(int week, int attendance) {
        checkArgument(isValidWeek(week), MESSAGE_CONSTRAINTS);
        checkArgument(isValidAttendance(attendance), MESSAGE_CONSTRAINTS);
        String oldAttendanceString = this.toString();
        String newAttendanceString =
                oldAttendanceString.substring(0, week - 1) + String.valueOf(attendance)
                + oldAttendanceString.substring(week);
        return AttendanceList.generateAttendanceList(newAttendanceString);
    }

    /**
     * Gets the Stream of Attendance objects in the Attendance List.
     *
     * @return Stream of Attendance objects in the Attendance List.
     */
    public Stream<Attendance> getAttendanceStream() {
        return this.attendanceList.stream();
    }

    /**
     * Checks whether the current instance is the Empty AttendanceList.
     *
     * @return Boolean representing whether the current instance is the Empty AttendanceList.
     */
    public boolean isEmpty() {
        return this.equals(EMPTY_ATTENDANCE_LIST);
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
