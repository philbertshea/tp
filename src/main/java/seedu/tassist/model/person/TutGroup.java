package seedu.tassist.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's tutorial group in the address book.
 * Optional field.
 * Guarantees: immutable; is valid as declared in {@link #isValidTutGroup(String)}
 */
public class TutGroup implements Comparable<TutGroup> {

    public static final String MESSAGE_CONSTRAINTS = "Invalid tutorial group!"
            + "\nTutorial group should either start with a 'T' or 't'"
            + "followed by a maximum of two digits larger than 0.";

    public static final String VALIDATION_REGEX = "^[Tt]([1-9]|0[1-9]|[1-9]\\d)$";

    public final String value;

    /**
     * Constructs a {@code TutGroup}.
     *
     * @param tutGroup A valid tutorial group.
     */
    public TutGroup(String tutGroup) {
        requireNonNull(tutGroup);
        // Hardcode, can change in future when relaxing assumptions.
        String processedTutGroup = tutGroup.length() == 2
                ? tutGroup.charAt(0) + "0" + tutGroup.charAt(1) : tutGroup;
        checkArgument(isValidTutGroup(processedTutGroup), MESSAGE_CONSTRAINTS);
        value = processedTutGroup.toUpperCase();
    }

    /**
     * Checks if value is empty.
     */
    public boolean isEmpty() {
        return value.isEmpty();
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidTutGroup(String test) {
        return test.matches(VALIDATION_REGEX) || test.isEmpty();
    }

    /**
     * Creates and returns a {@link TutGroup} instance
     * given an integer tutGroupNumber, if valid (i.e. between 1 and 99 inclusive).
     *
     * @param tutGroupNumber Number representing the tutorial group.
     * @return TutGroup created given tutGroupNumber.
     */
    public static TutGroup createTutGroupFromGroupNumber(int tutGroupNumber) {
        checkArgument(tutGroupNumber > 0 && tutGroupNumber < 100, MESSAGE_CONSTRAINTS);
        if (tutGroupNumber < 10) {
            return new TutGroup("T0" + tutGroupNumber);
        } else {
            return new TutGroup("T" + tutGroupNumber);
        }
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TutGroup)) {
            return false;
        }

        TutGroup otherTutGroup = (TutGroup) other;
        return value.equals(otherTutGroup.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @Override
    public int compareTo(TutGroup o) {
        int thisGroupNumber = Integer.parseInt(this.value.substring(1));
        int oGroupNumber = Integer.parseInt(o.value.substring(1));
        return thisGroupNumber - oGroupNumber;
    }
}
