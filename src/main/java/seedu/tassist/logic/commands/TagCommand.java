package seedu.tassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;
import static seedu.tassist.logic.commands.EditCommand.createEditedPerson;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EDIT_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.tassist.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.logic.parser.TagCommandParser.ActionType;
import seedu.tassist.model.Model;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.tag.Tag;

/**
 * Allows the user to add, edit, delete the tag of a person.
 */
public class TagCommand extends Command {

    public static final String COMMAND_WORD = "tag";

    public static final String MESSAGE_USAGE = String.format(
            "Usage: tag [%s|%s|%s] %s INDEX [OPTIONS]\n\n"
                    + "Commands:\n"
                    + "  %s          Adds new tags to the person identified by INDEX in the displayed list.\n"
                    + "  %s          Edits an existing tag of a person identified by INDEX in the displayed list.\n"
                    + "  %s          Deletes existing tags of person identified by INDEX in the displayed list.\n\n"
                    + "Options:\n"
                    + "  %s TAG    Specifies the tag(s) to add, edit, or delete "
                    + "(multiple tags allowed for add and delete).\n"
                    + "              Use -tag OLD_TAG -tag NEW_TAG to edit an existing tag.\n\n"
                    + "Examples:\n"
                    + "  tag %s %s 1 %s Friend %s Colleague\n"
                    + "  tag %s %s 2 %s Friend %s BestFriend\n"
                    + "  tag %s %s 3 %s OldTag\n",
            "a", "m", "d", PREFIX_INDEX, PREFIX_ADD_TAG, PREFIX_EDIT_TAG, PREFIX_DELETE_TAG,
            PREFIX_TAG,
            PREFIX_ADD_TAG, PREFIX_INDEX, PREFIX_TAG, PREFIX_TAG,
            PREFIX_EDIT_TAG, PREFIX_INDEX, PREFIX_TAG, PREFIX_TAG,
            PREFIX_DELETE_TAG, PREFIX_INDEX, PREFIX_TAG, PREFIX_TAG
    );

    public static final String MESSAGE_DEL_NO_MORE_ITEMS = "There are no more items to delete!";
    public static final String MESSAGE_INVALID_ACTION = "Action passed to Tag Command is invalid!";
    public static final String MESSAGE_TAG_SUCCESS = "Successfully %s tag(s): \n%s";
    public static final String MESSAGE_DUPLICATE_TAG = "The tags you want to add have already been already added!";
    public static final String MESSAGE_DEL_INVALID_TAGS = "The tags you want to delete dont exist";
    public static final String MESSAGE_EDIT_OLD_TAG_NOT_FOUND = "The tag you want to edit cannot be found!";
    public static final String MESSAGE_EDIT_EXISTENT_NEW_TAG = "The tag's new value you want to add already exists!";

    private final Index index;
    private final ActionType action;
    private final Set<Tag> tags;
    private final Tag oldTag;
    private final Tag newTag;

    /**
     * Creates an TagCommand to add, edit, delete {@code Tag} of a specific {@code Person}.
     *
     * @param index of the person in the filtered person list to tag
     * @param action action to be taken on the tag
     * @param tags list of tags to be added, deleted,
     *             or for edit, it will be {old_tag, new_tag}
     */
    public TagCommand(Index index, ActionType action, Set<Tag> tags, Tag oldTag, Tag newTag) {
        requireNonNull(index);
        requireNonNull(action);
        if (action == ActionType.ADD || action == ActionType.DEL) {
            requireNonNull(tags);
        } else if (action == ActionType.EDIT) {
            requireNonNull(oldTag);
            requireNonNull(newTag);
        }
        this.index = index;
        this.action = action;
        this.tags = tags; // for edit, the first tag is old, second tag is new
        this.oldTag = oldTag;
        this.newTag = newTag;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(String.format(
                    Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                    lastShownList.size()));
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());
        EditCommand.EditPersonDescriptor editPersonDescriptor = new EditCommand.EditPersonDescriptor();
        Set<Tag> editedTags = new HashSet<>(personToEdit.getTags());
        boolean res;
        if (action == ActionType.ADD) {
            res = editedTags.addAll(tags);
            if (!res) {
                throw new CommandException(MESSAGE_DUPLICATE_TAG);
            }
        } else if (action == ActionType.DEL) {
            if (editedTags.isEmpty()) {
                throw new CommandException(MESSAGE_DEL_NO_MORE_ITEMS);
            }
            res = editedTags.removeAll(tags);
            if (!res) {
                throw new CommandException(MESSAGE_DEL_INVALID_TAGS);
            }
        } else if (action == ActionType.EDIT) {
            res = editedTags.remove(oldTag);
            if (!res) {
                throw new CommandException(MESSAGE_EDIT_OLD_TAG_NOT_FOUND);
            }
            res = editedTags.add(newTag);
            if (!res) {
                throw new CommandException(MESSAGE_EDIT_EXISTENT_NEW_TAG);
            }
        } else {
            throw new CommandException(MESSAGE_INVALID_ACTION);
        }
        editPersonDescriptor.setTags(editedTags);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
        if (!personToEdit.isSamePerson(editedPerson) && model.hasPerson(editedPerson)) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_TAG_SUCCESS,
                action, getTagSummary(editedPerson)));
    }

    /**
     * Generates a summary of the tags.
     */
    public static String getTagSummary(Person student) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%s (%s) - %s",
                student.getName().fullName,
                student.getMatNum().value,
                student.getTags().isEmpty() ? "-"
                        : student.getTags().stream().sorted(Comparator.comparing(tag -> tag.tagName))
                                .map(tag -> tag.tagName) // Convert each tag to a string
                                .collect(Collectors.joining(", "))
                )
        );
        return sb.toString().trim();
    }

}
