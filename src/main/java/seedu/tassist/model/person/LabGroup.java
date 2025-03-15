package seedu.tassist.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's lab group in the address book.
 * Optional field.
 * Guarantees: immutable; is valid as declared in {@link #isValidLabGroup(String)}
 */
public class LabGroup {

    public static final String MESSAGE_CONSTRAINTS = "Invalid lab group!"
            + "\nLab group should either start with a 'B' followed by numbers.";

    public static final String VALIDATION_REGEX = "^[Bb]\\d+$";

    public final String value;

    /**
     * Constructs a {@code LabGrp}.
     *
     * @param labGroup A valid lab group.
     */
    public LabGroup(String labGroup) {
        requireNonNull(labGroup);
        checkArgument(isValidLabGroup(labGroup), MESSAGE_CONSTRAINTS);
        value = labGroup.toUpperCase();
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
    public static boolean isValidLabGroup(String test) {
        return test.matches(VALIDATION_REGEX)  || test.isEmpty();
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
        if (!(other instanceof LabGroup)) {
            return false;
        }

        LabGroup otherLabGroup = (LabGroup) other;
        return value.equals(otherLabGroup.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
