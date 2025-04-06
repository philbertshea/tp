package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    public void constructor_invalidWeek_throwsIllegalArgumentException() {
        // EP: Week number is too small.
        // Boundary value: Minimum integer value.
        assertThrows(IllegalArgumentException.class, () -> new Attendance(Integer.MIN_VALUE, Attendance.ATTENDED));
        // Other value: Negative 100.
        assertThrows(IllegalArgumentException.class, () -> new Attendance(-100, Attendance.ATTENDED));
        // Boundary value: Zero.
        assertThrows(IllegalArgumentException.class, () -> new Attendance(0, Attendance.ATTENDED));

        // EP: Week number is too large.
        // Boundary value: Positive 14.
        assertThrows(IllegalArgumentException.class, () -> new Attendance(14, Attendance.ATTENDED));
        // Other value: Positive 100.
        assertThrows(IllegalArgumentException.class, () -> new Attendance(100, Attendance.ATTENDED));
        // Boundary value: Maximum integer value.
        assertThrows(IllegalArgumentException.class, () -> new Attendance(Integer.MAX_VALUE, Attendance.ATTENDED));
    }

    @Test
    public void constructor_invalidAttendance_throwsIllegalArgumentException() {
        // EP: Attendance status value is too small.
        // Boundary value: Minimum integer value.
        assertThrows(IllegalArgumentException.class, () -> new Attendance(5, Integer.MIN_VALUE));
        // Boundary value: Negative 100.
        assertThrows(IllegalArgumentException.class, () -> new Attendance(5, -100));
        // Boundary value: Negative 1.
        assertThrows(IllegalArgumentException.class, () -> new Attendance(5, -1));

        // EP: Attendance status value is too large.
        // Boundary value: Positive 4.
        assertThrows(IllegalArgumentException.class, () -> new Attendance(5, 4));
        // Boundary value: Positive 100.
        assertThrows(IllegalArgumentException.class, () -> new Attendance(5, 100));
        // Boundary value: Maximum integer value.
        assertThrows(IllegalArgumentException.class, () -> new Attendance(5, Integer.MAX_VALUE));
    }

    @Test
    public void isValidWeek_invalidWeek_returnsFalse() {
        // EP: Week number is too small.
        // Boundary value: Minimum integer value.
        assertFalse(Attendance.isValidWeek(Integer.MIN_VALUE));
        // Other value: Negative 100.
        assertFalse(Attendance.isValidWeek(-100));
        // Boundary value: Zero.
        assertFalse(Attendance.isValidWeek(0));

        // EP: Week number is too large.
        // Boundary value: 14.
        assertFalse(Attendance.isValidWeek(14));
        // Other value: 100.
        assertFalse(Attendance.isValidWeek(100));
        // Boundary value: Maximum integer value.
        assertFalse(Attendance.isValidWeek(Integer.MAX_VALUE));
    }

    @Test
    public void isValidWeek_validWeek_returnsTrue() {
        // Valid week -> returns true.

        // Boundary value: 1.
        assertTrue(Attendance.isValidWeek(1));

        // Other value: 5.
        assertTrue(Attendance.isValidWeek(5));

        // Boundary value: 13.
        assertTrue(Attendance.isValidWeek(13));
    }

    @Test
    public void isValidAttendance_invalidAttendance_returnsFalse() {
        // EP: Attendance value is too small.
        // Boundary value: Minimum integer value.
        assertFalse(Attendance.isValidAttendance(Integer.MIN_VALUE));
        // Other value: Negative 100.
        assertFalse(Attendance.isValidAttendance(-100));
        // Boundary value: Negative 1.
        assertFalse(Attendance.isValidAttendance(-1));

        // EP: Attendance value is too large.
        // Boundary value: 4.
        assertFalse(Attendance.isValidAttendance(4));
        // Other value: 100.
        assertFalse(Attendance.isValidAttendance(100));
        // Boundary value: Maximum integer value.
        assertFalse(Attendance.isValidAttendance(Integer.MAX_VALUE));
    }

    @Test
    public void isValidAttendance_validAttendance_returnsTrue() {
        // EP: Not attended.
        assertTrue(Attendance.isValidAttendance(0));

        // EP: Attended.
        assertTrue(Attendance.isValidAttendance(1));

        // EP: On MC.
        assertTrue(Attendance.isValidAttendance(2));

        // EP: No tutorial.
        assertTrue(Attendance.isValidAttendance(3));
    }

    @Test
    public void getWeekAsTagPrefix() {
        // Boundary value: 1.
        Attendance attendance = new Attendance(1, Attendance.ATTENDED);
        assertTrue(attendance.getWeekAsTagPrefix().equals("W1:"));

        // Other value: 5.
        attendance = new Attendance(5, Attendance.ATTENDED);
        assertTrue(attendance.getWeekAsTagPrefix().equals("W5:"));

        // Other value: 13.
        attendance = new Attendance(13, Attendance.ATTENDED);
        assertTrue(attendance.getWeekAsTagPrefix().equals("W13:"));
    }

    @Test
    public void equals() {
        Attendance attendance = new Attendance(3, Attendance.ATTENDED);

        // Same attendance instance -> returns true.
        assertTrue(attendance.equals(attendance));

        // Different instance of same week and same attendance -> returns true.
        assertTrue(attendance.equals(new Attendance(3, Attendance.ATTENDED)));

        // Null -> returns false.
        assertFalse(attendance.equals(null));

        // Different types -> returns false.
        assertFalse(attendance.equals(5.0f));

        // Different instance of different week and same attendance -> returns false.
        assertFalse(attendance.equals(new Attendance(5, Attendance.ATTENDED)));

        // Different instance of same week and different attendance -> returns false.
        assertFalse(attendance.equals(new Attendance(3, Attendance.NOT_ATTENDED)));
    }
}
