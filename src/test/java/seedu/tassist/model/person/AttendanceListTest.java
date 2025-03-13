package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceListTest {
    private final String attendanceString = "2200000000111";
    private final String attendanceStringDifferent = "2200000002111";
    private final AttendanceList attendanceList =
            AttendanceList.generateAttendanceList(attendanceString);
    private final AttendanceList attendanceListDuplicate =
            AttendanceList.generateAttendanceList(attendanceString);
    private final AttendanceList attendanceListDifferent =
            AttendanceList.generateAttendanceList(attendanceStringDifferent);

    // No Constructor tests as the constructor is private.

    @Test
    public void isValidAttendanceString() {

        // invalid week
        assertFalse(AttendanceList.isValidAttendanceString(""));
        assertFalse(AttendanceList.isValidAttendanceString("00000")); // Invalid Length
        assertFalse(AttendanceList.isValidAttendanceString("000000000000000")); // Invalid Length
        assertFalse(AttendanceList.isValidAttendanceString("00000 00000000")); // No Spaces
        assertFalse(AttendanceList.isValidAttendanceString("000a*-0000000")); // Invalid Chars
        assertFalse(AttendanceList.isValidAttendanceString("0120120120123")); // Not within 0, 1 or 2

        // valid attendance string
        assertTrue(AttendanceList.isValidAttendanceString("0000000000000"));
        assertTrue(AttendanceList.isValidAttendanceString("1111111111111"));
        assertTrue(AttendanceList.isValidAttendanceString("2222222222222"));
        assertTrue(AttendanceList.isValidAttendanceString("0120120012012"));
        assertTrue(AttendanceList.isValidAttendanceString("0012011120011"));

    }

    public void generateAttendanceList_null_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.generateAttendanceList(null));
    }

    public void generateAttendanceList_invalidAttendanceString_throwsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.generateAttendanceList("00000"));
    }

    @Test
    public void getAttendanceForWeek() {
        // invalid week
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(-10000000));
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(-1));
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(0));
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(14));
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(15));
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(10000000));

        // valid week
        for (int i = 1; i <= 13; i++) {
            assertTrue(attendanceList.getAttendanceForWeek(i)
                    == Integer.parseInt(attendanceString.substring(i - 1, i)));
        }

    }

    @Test
    public void setAttendanceForWeek_invalidWeekOrAttendance_throwsIllegalArgumentException() {

        // invalid week
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(0, Attendance.ATTENDED));
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(14, Attendance.ATTENDED));

        // invalid attendance
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(1, -1));
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(1, 3));

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
    public void equals() {
        // same AttendanceList instance -> returns true
        assertTrue(attendanceList.equals(attendanceList));

        // same attendanceString -> returns true
        assertTrue(attendanceList.equals(attendanceListDuplicate));

        // different attendanceString -> returns false
        assertFalse(attendanceList.equals(attendanceListDifferent));
    }
}
