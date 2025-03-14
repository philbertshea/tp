package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceTest {

    @Test
    public void constructor_invalidWeek_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Attendance(14, Attendance.ATTENDED));
    }

    @Test
    public void constructor_invalidAttendance_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () -> new Attendance(10, 3));
    }

    @Test
    public void isValidWeek() {

        // invalid week -> returns false
        assertFalse(Attendance.isValidWeek(-10000000));
        assertFalse(Attendance.isValidWeek(-1));
        assertFalse(Attendance.isValidWeek(0));
        assertFalse(Attendance.isValidWeek(14));
        assertFalse(Attendance.isValidWeek(15));
        assertFalse(Attendance.isValidWeek(10000000));

        // valid week -> returns true
        for (int i = 1; i <= 13; i++) {
            assertTrue(Attendance.isValidWeek(i));
        }
    }

    @Test
    public void getTagName() {
        Attendance attendance = new Attendance(3, Attendance.ATTENDED);
        Attendance attendanceDuplicate = new Attendance(3, Attendance.ATTENDED);
        Attendance attendanceDiffWeek = new Attendance(5, Attendance.ATTENDED);
        Attendance attendanceDiffAttendance = new Attendance(3, Attendance.NOT_ATTENDED);

        // tag names for same Attendance object -> equal
        assertTrue(attendance.getTagName().equals(attendance.getTagName()));

        // tag names for same week and attendance -> equal
        assertTrue(attendance.getTagName().equals(attendanceDuplicate.getTagName()));

        // tag names for different week -> not equal
        assertFalse(attendance.getTagName().equals(attendanceDiffWeek.getTagName()));

        // tag names for different attendance -> not equal
        assertFalse(attendance.getTagName().equals(attendanceDiffAttendance.getTagName()));
    }

    @Test
    public void equals() {
        Attendance attendance = new Attendance(3, Attendance.ATTENDED);

        // same values -> returns true
        assertTrue(attendance.equals(new Attendance(3, Attendance.ATTENDED)));

        // same object -> returns true
        assertTrue(attendance.equals(attendance));

        // null -> returns false
        assertFalse(attendance.equals(null));

        // different types -> returns false
        assertFalse(attendance.equals(5.0f));

        // different week -> returns false
        assertFalse(attendance.equals(new Attendance(5, Attendance.ATTENDED)));

        // different attendance -> returns false
        assertFalse(attendance.equals(new Attendance(5, Attendance.NOT_ATTENDED)));
    }
}
