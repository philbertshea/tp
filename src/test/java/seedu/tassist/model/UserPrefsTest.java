package seedu.tassist.model;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.tassist.testutil.Assert.assertThrows;

import java.util.Objects;

import org.junit.jupiter.api.Test;

public class UserPrefsTest {

    @Test
    public void setGuiSettings_nullGuiSettings_throwsNullPointerException() {
        UserPrefs userPref = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPref.setGuiSettings(null));
    }

    @Test
    public void setAddressBookFilePath_nullPath_throwsNullPointerException() {
        UserPrefs userPrefs = new UserPrefs();
        assertThrows(NullPointerException.class, () -> userPrefs.setAddressBookFilePath(null));
    }

    @Test
    public void equalsMethod() {
        UserPrefs userPrefs = new UserPrefs();
        // Same object -> returns true.
        assertTrue(userPrefs.equals(userPrefs));

        // Null -> returns false.
        assertFalse(userPrefs.equals(null));

        // Different type -> returns false.
        assertFalse(userPrefs.equals(5.0f));

        // Same GuiSettings -> returns true.
        assertTrue(userPrefs.equals(new UserPrefs()));
    }

    @Test
    public void hashCodeMethod() {
        UserPrefs userPrefs = new UserPrefs();
        assertTrue(userPrefs.hashCode()
                == Objects.hash(userPrefs.getGuiSettings(), userPrefs.getAddressBookFilePath()));
    }

}
