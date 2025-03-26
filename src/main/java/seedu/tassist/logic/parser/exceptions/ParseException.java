package seedu.tassist.logic.parser.exceptions;

import seedu.tassist.commons.exceptions.IllegalValueException;
import seedu.tassist.model.Operations;

/**
 * Represents a parse error encountered by a parser.
 */
public class ParseException extends IllegalValueException {

    /**
     * Constructs a new {@code ParseException} with the
     * specified detail {@code message}.
     */
    public ParseException(String message) {

        super(message);
        Operations.removeRecording();
    }

    /**
     * Constructs a new {@code ParseException} with the
     * specified detail {@code message} and {@code cause}.
     */
    public ParseException(String message, Throwable cause) {

        super(message, cause);
        Operations.removeRecording();
    }
}
