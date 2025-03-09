package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ToStringBuilder;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;

/**
 * Deletes a person identified using it's displayed index from the address book.
 */
public class DeleteCommand extends Command {

    public static final String COMMAND_WORD = "delete";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the person identified by the index number used in the displayed person list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_PERSON_SUCCESS = "Deleted Student: %1$s";
    public static final String MESSAGE_DELETE_PERSON_INVALID_INDEX = "Invalid index! You currently have %d records!";

    private final Index targetIndex;

    /**
     * Constructs a {@code DeleteCommand} to delete the person at the specified {@code targetIndex}.
     *
     * @param targetIndex Index of the person in the filtered list to delete.
     */
    public DeleteCommand(Index targetIndex) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
    }

    /**
     * Executes the deletion command.
     *
     * @param model {@code Model} which the command should operate on.
     * @return the result message of the deletion.
     * @throws CommandException if the target index is invalid.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToDelete = getTargetPerson(model);

        model.deletePerson(personToDelete);
        String feedback = String.format(MESSAGE_DELETE_PERSON_SUCCESS, Messages.format(personToDelete));
        return new CommandResult(feedback);
    }

    /**
     * Retrieves the person corresponding to {@code targetIndex} from the model's filtered list.
     * Uses a guard clause to handle an invalid index immediately.
     *
     * @param model the model to retrieve the person from.
     * @return the person to delete.
     * @throws CommandException if {@code targetIndex} is out of bounds.
     */
    private Person getTargetPerson(Model model) throws CommandException {
        List<Person> lastShownList = model.getFilteredPersonList();
        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(MESSAGE_DELETE_PERSON_INVALID_INDEX, lastShownList.size()));
        }
        return lastShownList.get(targetIndex.getZeroBased());
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteCommand)) {
            return false;
        }

        DeleteCommand otherDeleteCommand = (DeleteCommand) other;
        return targetIndex.equals(otherDeleteCommand.targetIndex);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .add("targetIndex", targetIndex)
                .toString();
    }
}
