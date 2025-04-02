package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceListTest {
    private final String nonEmptyValidAttendanceString = "2233000000111";
    private final String validAttendanceStringDifferent = "2233000002111";
    private final AttendanceList attendanceList =
            AttendanceList.generateAttendanceList(nonEmptyValidAttendanceString);
    private final AttendanceList attendanceListDuplicate =
            AttendanceList.generateAttendanceList(nonEmptyValidAttendanceString);
    private final AttendanceList attendanceListDifferent =
            AttendanceList.generateAttendanceList(validAttendanceStringDifferent);

    private final TutGroup emptyTutGroup = new TutGroup("");
    private final TutGroup validTutGroupOne = new TutGroup("T01");
    private final TutGroup validTutGroupTwo = new TutGroup("t99");


    // No Constructor tests as the constructor is private.

    @Test
    public void isValidNonEmptyAttendanceString_nullAttendanceString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () -> AttendanceList.isValidNonEmptyAttendanceString(null));
    }

    @Test
    public void isValidNonEmptyAttendanceString_invalidAttendanceString_returnsFalse() {
        // EP: Empty String.
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString(""));

        // EP: String of Invalid Length (too short).
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("0")); // Boundary value: length 1.
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("00000")); // Boundary value: length 5.
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("000000000000")); // Boundary value: length 12.

        // EP: String of Invalid Length (too long).
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("00000000000000")); // Boundary value: length 14.
        // Boundary value: length 30.
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("000000000000000000000000000000"));

        // EP: String of Valid Length (13) but Invalid Values
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("             ")); // All spaces.
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("A000000000000")); // Invalid Chars: Alphabet.
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("0?00000000000")); // Invalid Chars: Symbols.
        assertFalse(AttendanceList.isValidNonEmptyAttendanceString("0040000000000")); // Invalid Digits out of 0,1,2,3.
    }


    @Test
    public void isValidNonEmptyAttendanceString_validAttendanceString_returnsTrue() {
        // EP: String of Length 13, all zeros/ones/twos/threes.
        assertTrue(AttendanceList.isValidNonEmptyAttendanceString("0000000000000")); // All zeros.
        assertTrue(AttendanceList.isValidNonEmptyAttendanceString("1111111111111")); // All ones.
        assertTrue(AttendanceList.isValidNonEmptyAttendanceString("2222222222222")); // All twos.
        assertTrue(AttendanceList.isValidNonEmptyAttendanceString("3333333333333")); // All threes.

        // EP: String of Length 13, mix of valid digits zeros/ones/twos/threes.
        assertTrue(AttendanceList.isValidNonEmptyAttendanceString(AttendanceList.DEFAULT_ATTENDANCE_STRING));
        assertTrue(AttendanceList.isValidNonEmptyAttendanceString("0120123012012"));
        assertTrue(AttendanceList.isValidNonEmptyAttendanceString("0012013120011"));
    }

    @Test
    public void isValidAttendanceStringGivenTutGroup_nullAttendanceString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(null, validTutGroupOne));
    }

    @Test
    public void isValidAttendanceStringGivenTutGroup_nullTutGroupInput_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, null));
    }

    @Test
    public void isValidAttendanceStringGivenTutGroup_invalidAttendanceString_returnsFalse() {
        // EP: String of Invalid Length (too short).
        // Boundary value: length 1.
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup("0", validTutGroupOne));
        // Boundary value: length 5.
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup("00000", validTutGroupOne));
        // Boundary value: length 12.
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup(
                "000000000000", validTutGroupOne));

        // EP: String of Invalid Length (too long).
        // Boundary value: length 14.
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup(
                "00000000000000", validTutGroupOne));
        // Boundary value: length 30.
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup(
                "000000000000000000000000000000", validTutGroupOne));

        // EP: String of Valid Length (13) but Invalid Values
        // All spaces.
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup(
                "             ", validTutGroupOne));
        // Invalid Chars: Alphabet.
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup(
                "A000000000000", validTutGroupOne));
        // Invalid Chars: Symbols.
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup(
                "0?00000000000", validTutGroupOne));
        // Invalid Digits that are not within 0,1,2,3.
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup(
                "0040000000000", validTutGroupOne));
    }

    @Test
    public void isValidAttendanceStringGivenTutGroup_invalidTutGroupInput_throwsIllegalArgumentException() {
        // EP: Not starting with T or t.
        // Starts with alphabet that is not t or T.
        assertThrows(IllegalArgumentException.class, () -> AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, new TutGroup("A23")));
        // Starts with symbol.
        assertThrows(IllegalArgumentException.class, () -> AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, new TutGroup("!23")));
        // Starts with digit.
        assertThrows(IllegalArgumentException.class, () -> AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, new TutGroup("123")));

        // EP: Invalid second and third characters.
        // Second character is an alphabet.
        assertThrows(IllegalArgumentException.class, () -> AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, new TutGroup("Ta0")));
        // Second character is a symbol.
        assertThrows(IllegalArgumentException.class, () -> AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, new TutGroup("T!0")));
        // Third character is an alphabet.
        assertThrows(IllegalArgumentException.class, () -> AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, new TutGroup("T0a")));
        // Third character is a symbol.
        assertThrows(IllegalArgumentException.class, () -> AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, new TutGroup("T0!")));


        // EP: Invalid length.
        // Positive length but too short.
        assertThrows(IllegalArgumentException.class, () -> AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, new TutGroup("T")));
        assertThrows(IllegalArgumentException.class, () -> AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, new TutGroup("T0")));

        // Positive length but too long.
        assertThrows(IllegalArgumentException.class, () -> AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, new TutGroup("T010")));
        assertThrows(IllegalArgumentException.class, () -> AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, new TutGroup("T0001")));
    }

    @Test
    public void isValidAttendanceStringGivenTutGroup_validInputs_success() {
        // EP: Empty TutGroup and Empty AttendanceString -> Returns true.
        assertTrue(AttendanceList.isValidAttendanceStringGivenTutGroup("", emptyTutGroup));

        // EP: Empty TutGroup and Non-Empty, valid AttendanceString -> Returns false.
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, emptyTutGroup));
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup(
                validAttendanceStringDifferent, emptyTutGroup));

        // EP: Non-Empty TutGroup and Non-Empty, valid AttendanceString -> Returns true.
        assertTrue(AttendanceList.isValidAttendanceStringGivenTutGroup(
                nonEmptyValidAttendanceString, validTutGroupOne));
        assertTrue(AttendanceList.isValidAttendanceStringGivenTutGroup(
                validAttendanceStringDifferent, validTutGroupOne));

        // EP: Non-Empty TutGroup, and Empty AttendanceString -> Returns false.
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup("", validTutGroupOne));

    }

    @Test
    public void generateAttendanceList_nullAttendanceString_throwsNullPointerException() {
        assertThrows(NullPointerException.class, () ->
                AttendanceList.generateAttendanceList(null));
    }

    @Test
    public void generateAttendanceList_invalidAttendanceString_throwsIllegalArgumentException() {
        // EP: String of Invalid Length (too short).
        // Boundary value: length 1.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.generateAttendanceList("0"));
        // Other value: length 5.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.generateAttendanceList("00000"));
        // Boundary value: length 12.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.generateAttendanceList("000000000000"));

        // EP: String of Invalid Length (too long).
        // Boundary value: length 14.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.generateAttendanceList("00000000000000"));
        // Other value: length 30.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.generateAttendanceList("000000000000000000000000000000"));

        // EP: String of Valid Length (13) but Invalid Values
        // All spaces.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.generateAttendanceList("             "));
        // Invalid Chars: Alphabet.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.generateAttendanceList("A000000000000"));
        // Invalid Chars: Symbols.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.generateAttendanceList("0?00000000000"));
        // Invalid Digits that are not within 0,1,2,3.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.generateAttendanceList("0040000000000"));
    }

    @Test
    public void generateAttendanceList_validAttendanceString_success() {
        // Empty AttendanceString -> Return empty attendance list.
        assertTrue(AttendanceList.generateAttendanceList("")
                .equals(AttendanceList.EMPTY_ATTENDANCE_LIST));

        // Non-Empty AttendanceString -> Return correct attendance list.
        assertTrue(AttendanceList.generateAttendanceList(nonEmptyValidAttendanceString).toString()
                .equals(nonEmptyValidAttendanceString));
        assertTrue(AttendanceList.generateAttendanceList(validAttendanceStringDifferent).toString()
                .equals(validAttendanceStringDifferent));
    }

    @Test
    public void getAttendanceForWeek_invalidWeek_throwsIllegalArgumentException() {
        // EP: Week number is too small.
        // Boundary value: Minimum integer value.
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(Integer.MIN_VALUE));
        // Other value: Negative 100.
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(-100));
        // Boundary value: Zero.
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(0));

        // EP: Week number is too large.
        // Boundary value: Positive 14.
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(14));
        // Other value: Positive 100.
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(100));
        // Boundary value: Maximum integer value.
        assertThrows(IllegalArgumentException.class, () -> attendanceList.getAttendanceForWeek(Integer.MAX_VALUE));
    }

    @Test
    public void getAttendanceForWeek_validWeek_returnsAttendanceForWeek() {
        // Valid week from 1 to 13 inclusive -> returns the correct attendance value.
        for (int i = 1; i <= 13; i++) {
            assertTrue(attendanceList.getAttendanceForWeek(i)
                    == Integer.parseInt(nonEmptyValidAttendanceString.substring(i - 1, i)));
        }
    }

    @Test
    public void setAttendanceForWeek_invalidWeek_throwsIllegalArgumentException() {
        // EP: Week number is too small.
        // Boundary value: Minimum integer value.
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(Integer.MIN_VALUE, Attendance.ATTENDED));
        // Other value: Negative 100.
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(-100, Attendance.ATTENDED));
        // Boundary value: Zero.
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(0, Attendance.ATTENDED));

        // EP: Week number is too large.
        // Boundary value: Positive 14.
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(14, Attendance.ATTENDED));
        // Other value: Positive 100.
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(100, Attendance.ATTENDED));
        // Boundary value: Maximum integer value.
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(Integer.MAX_VALUE, Attendance.ATTENDED));
    }

    @Test
    public void setAttendanceForWeek_invalidAttendance_throwsIllegalArgumentException() {
        // EP: Attendance status value is too small.
        // Boundary value: Minimum integer value.
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(5, Integer.MIN_VALUE));
        // Other value: Negative 100.
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(5, -100));
        // Boundary value: Negative 1.
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(5, -1));

        // EP: Attendance status value is too large.
        // Boundary value: Positive 4.
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(5, 4));
        // Other value: Positive 100.
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(5, 100));
        // Boundary value: Maximum integer value.
        assertThrows(IllegalArgumentException.class, () ->
                attendanceList.setAttendanceForWeek(5, Integer.MAX_VALUE));
    }

    @Test
    public void setAttendanceForWeek_validWeekAndAttendance_success() {
        AttendanceList newAttendanceList = attendanceList.setAttendanceForWeek(10, Attendance.ON_MC);
        assertTrue(newAttendanceList.equals(attendanceListDifferent));
        newAttendanceList = newAttendanceList.setAttendanceForWeek(10, Attendance.NOT_ATTENDED);
        assertTrue(newAttendanceList.equals(attendanceList));
    }

    @Test
    public void getAttendanceStream() {
        assertTrue(attendanceList.getAttendanceStream()
                .reduce("", (acc, xs) -> acc + xs.toString(), (acc1, acc2) ->
                        acc1 + acc2)
                .equals(nonEmptyValidAttendanceString));

        assertTrue(attendanceListDuplicate.getAttendanceStream()
                .reduce("", (acc, xs) -> acc + xs.toString(), (acc1, acc2) ->
                        acc1 + acc2)
                .equals(nonEmptyValidAttendanceString));

        assertTrue(attendanceListDifferent.getAttendanceStream()
                .reduce("", (acc, xs) -> acc + xs.toString(), (acc1, acc2) ->
                        acc1 + acc2)
                .equals(validAttendanceStringDifferent));
    }

    @Test
    public void isEmpty_emptyList_returnsTrue() {
        assertTrue(AttendanceList.EMPTY_ATTENDANCE_LIST.isEmpty());
    }

    @Test
    public void isEmpty_nonEmptyList_returnsFalse() {
        assertFalse(attendanceList.isEmpty());
        assertFalse(attendanceListDuplicate.isEmpty());
        assertFalse(attendanceListDifferent.isEmpty());
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
