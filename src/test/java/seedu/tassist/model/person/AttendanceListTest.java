package seedu.tassist.model.person;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import org.junit.jupiter.api.Test;

public class AttendanceListTest {
    private final String nonEmptyValidAttendanceString = "2233000000111";
    private final String attendanceStringDifferent = "2233000002111";
    private final AttendanceList attendanceList =
            AttendanceList.generateAttendanceList(nonEmptyValidAttendanceString);
    private final AttendanceList attendanceListDuplicate =
            AttendanceList.generateAttendanceList(nonEmptyValidAttendanceString);
    private final AttendanceList attendanceListDifferent =
            AttendanceList.generateAttendanceList(attendanceStringDifferent);

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
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, new TutGroup("A23")));
        // Starts with symbol.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, new TutGroup("!23")));
        // Starts with digit.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, new TutGroup("123")));

        // EP: Invalid second and third characters.
        // Second character is an alphabet.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, new TutGroup("Ta0")));
        // Second character is a symbol.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, new TutGroup("T!0")));
        // Third character is an alphabet.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, new TutGroup("T0a")));
        // Third character is a symbol.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, new TutGroup("T0!")));


        // EP: Invalid length.
        // Positive length but too short.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, new TutGroup("T")));
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, new TutGroup("T0")));

        // Positive length but too long.
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, new TutGroup("T010")));
        assertThrows(IllegalArgumentException.class, () ->
                AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, new TutGroup("T0001")));
    }

    @Test
    public void isValidAttendanceStringGivenTutGroup_validInputs_success() {
        // EP: Empty TutGroup and Empty AttendanceString -> Returns true.
        assertTrue(AttendanceList.isValidAttendanceStringGivenTutGroup("", emptyTutGroup));

        // EP: Empty TutGroup and Non-Empty, valid AttendanceString -> Returns false.
        assertFalse(AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, emptyTutGroup));

        // EP: Non-Empty TutGroup and Non-Empty, valid AttendanceString -> Returns true.
        assertTrue(AttendanceList.isValidAttendanceStringGivenTutGroup(nonEmptyValidAttendanceString, validTutGroupOne));

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
                    == Integer.parseInt(nonEmptyValidAttendanceString.substring(i - 1, i)));
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
                .equals(nonEmptyValidAttendanceString));
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
