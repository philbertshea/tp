package seedu.tassist.model.person;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.commons.util.AppUtil.checkArgument;

/**
 * Represents a Person's matriculation number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidMatNum(String)}
 */
public class MatNum {

    public static final String MESSAGE_CONSTRAINTS = "Invalid matriculation number!"
            + "\nMatriculation numbers should start with 'A' and contain 7 digits minimally.";

    public static final String VALIDATION_REGEX = "^[Aa]\\d{7}[A-Za-z]?$";

    public final String value;

    /**
     * Constructs a {@code MatNum}.
     *
     * @param matNum A valid matriculation number.
     */
    public MatNum(String matNum) {
        requireNonNull(matNum);
        checkArgument(isValidMatNum(matNum), MESSAGE_CONSTRAINTS);
        value = matNum;
    }

    /**
     * Returns true if a given string is a valid name.
     */
    public static boolean isValidMatNum(String test) {
        return test.matches(VALIDATION_REGEX);
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
        if (!(other instanceof MatNum)) {
            return false;
        }

        MatNum otherMatNum = (MatNum) other;
        return value.equals(otherMatNum.value);
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
