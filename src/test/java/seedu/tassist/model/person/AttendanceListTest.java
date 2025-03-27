package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceListTest {
    private final String attendanceString = "2233000000111";
    private final String attendanceStringDifferent = "2233000002111";
    private final AttendanceList attendanceList =
            AttendanceList.generateAttendanceList(attendanceString);
    private final AttendanceList attendanceListDuplicate =
            AttendanceList.generateAttendanceList(attendanceString);
    private final AttendanceList attendanceListDifferent =
            AttendanceList.generateAttendanceList(attendanceStringDifferent);

    // No Constructor tests as the constructor is private.

    @Test
    public void isValidNonEmptyAttendanceString() {

        // Null -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> AttendanceList.isValidNonEmptyAttendanceString(null));

        // Invalid attendanceString -> returns false.
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("")); // Empty String
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("00000")); // Invalid Length
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("000000000000000")); // Invalid Length
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("00000 00000000")); // No Spaces
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("000a*-0000000")); // Invalid Chars
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("0120120120124")); // Not within 0, 1, 2 or 3

        // Valid attendanceString -> returns true.
        assertTrue(AttendanceList.isValidNonEmptyAttendanceString(AttendanceList.DEFAULT_ATTENDANCE_STRING));
        assertTrue(AttendanceList.isValidNonEmptyAttendanceString("1111111111111"));
        assertTrue(AttendanceList.isValidNonEmptyAttendanceString("2222222222222"));
        assertTrue(AttendanceList.isValidNonEmptyAttendanceString("0120123012012"));
        assertTrue(AttendanceList.isValidNonEmptyAttendanceString("0012013120011"));

    }

    @Test
    public void isValidAttendanceString() {

        // Null -> throws NullPointerException
        assertThrows(NullPointerException.class, () -> AttendanceList.isValidAttendanceString(null));

        // Invalid attendanceString -> returns false.
        assertFalse(AttendanceList.isValidAttendanceString("00000")); // Invalid Length
        assertFalse(AttendanceList.isValidAttendanceString("000000000000000")); // Invalid Length
        assertFalse(AttendanceList.isValidAttendanceString("00000 00000000")); // No Spaces
        assertFalse(AttendanceList.isValidAttendanceString("000a*-0000000")); // Invalid Chars
        assertFalse(AttendanceList.isValidAttendanceString("0120120120124")); // Not within 0, 1, 2 or 3

        // Valid attendanceString -> returns true.
        assertTrue(AttendanceList.isValidAttendanceString(""));
        assertTrue(AttendanceList.isValidAttendanceString(AttendanceList.DEFAULT_ATTENDANCE_STRING));
        assertTrue(AttendanceList.isValidAttendanceString("1111111111111"));
        assertTrue(AttendanceList.isValidAttendanceString("2222222222222"));
        assertTrue(AttendanceList.isValidAttendanceString("0120123012012"));
        assertTrue(AttendanceList.isValidAttendanceString("0012013120011"));

    }

    @Test
    public void generateAttendanceList_null_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                AttendanceList.generateAttendanceList(null));
    }

    @Test
    public void generateAttendanceList_invalidAttendanceString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.generateAttendanceList("00000"));
    }

    @Test
    public void generateAttendanceList_validAttendanceString_success() {
        // Empty AttendanceString -> Return EMPTY_ATTENDANCE_LIST.
        assertTrue(AttendanceList.generateAttendanceList("")
                .equals(AttendanceList.EMPTY_ATTENDANCE_LIST));

        // Non-Empty AttendanceString -> Return correct attendance list.
        String testAttendanceString = "0010230210112";
        String expectedStringValue = testAttendanceString;
        assertTrue(AttendanceList.generateAttendanceList(testAttendanceString).toString()
                .equals(expectedStringValue));
    }

    @Test
    public void getAttendanceForWeek() {
        // Invalid week -> throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(-10000000));
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(-1));
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(0));
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(14));
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(15));
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(10000000));

        // Valid week -> returns the correct attendance value
        for (int i = 1; i <= 13; i++) {
            assertTrue(attendanceList.getAttendanceForWeek(i)
                    == Integer.parseInt(attendanceString.substring(i - 1, i)));
        }

    }

    @Test
    public void setAttendanceForWeek_invalidWeekOrAttendance_throwsIllegalArgumentException() {

        // Invalid week -> throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(0, Attendance.ATTENDED));
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(14, Attendance.ATTENDED));

        // Invalid attendance -> throws IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(1, -1));
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(1, 4));

    }

    @Test
    public void setAttendanceForWeek_validWeekAndAttendance_success() {
        AttendanceList newAttendanceList = attendanceList.setAttendanceForWeek(10, Attendance.ON_MC);
        assertTrue(newAttendanceList.equals(attendanceListDifferent));
    }

    @Test
    public void getAttendanceStream() {
        assertTrue(attendanceList.getAttendanceStream()
                .reduce("", (acc, xs) -> acc + xs.toString(), (acc1, acc2) ->
                        acc1 + acc2)
                .equals(attendanceString));
    }

    @Test
    public void isEmpty() {
        // Empty AttendanceList -> returns true.
        assertTrue(AttendanceList.EMPTY_ATTENDANCE_LIST.isEmpty());

        // Non-empty AttendanceList -> returns false.
        assertFalse(attendanceList.isEmpty());
    }

    @Test
    public void equals() {
        // Same AttendanceList instance -> returns true.
        assertTrue(attendanceList.equals(attendanceList));

        // Same attendanceString -> returns true.
        assertTrue(attendanceList.equals(attendanceListDuplicate));

        // Null -> returns false.
        assertFalse(attendanceList.equals(null));

        // Different types -> returns false.
        assertFalse(attendanceList.equals(5.0f));

        // Different attendanceString -> returns false.
        assertFalse(attendanceList.equals(attendanceListDifferent));
    }
}
