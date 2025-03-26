package seedu.tassist.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TELE_HANDLE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;

import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.person.PersonMatchesPredicate;

/**
 * Searches for persons in the address book based on given criteria.
 */
public class SearchCommand extends Command {

    public static final String COMMAND_WORD = "search";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Searches for students based on given parameters. "
            + "Parameters: "
            + "[" + PREFIX_NAME + " NAME (supports partial matches)] "
            + "[" + PREFIX_MAT_NUM + " MATRICULATION NUMBER] "
            + "[" + PREFIX_PHONE + " PHONE] "
            + "[" + PREFIX_TELE_HANDLE + " TELEGRAM HANDLE] "
            + "[" + PREFIX_EMAIL + " EMAIL] "
            + "[" + PREFIX_TAG + " TAG] "
            + "[" + PREFIX_TUT_GROUP + " TUTORIAL GROUP] "
            + "[" + PREFIX_LAB_GROUP + " LAB GROUP] "
            + "[" + PREFIX_FACULTY + " FACULTY] "
            + "[" + PREFIX_YEAR + " YEAR] "
            + "\nExample: " + COMMAND_WORD + " "
            + PREFIX_NAME + " Alice "
            + PREFIX_TAG + " friend";

    private final PersonMatchesPredicate predicate;

    /**
     * Constructs a SearchCommand with the given search predicate.
     *
     * @param predicate The predicate to apply to the person list.
     */
    public SearchCommand(PersonMatchesPredicate predicate) {
        requireNonNull(predicate);
        this.predicate = predicate;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredPersonList(predicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_PERSONS_LISTED_OVERVIEW, model.getFilteredPersonList().size()));
    }

    /**
     * Checks whether two SearchCommands are equal.
     *
     * @param other The object to compare to.
     * @return True if both are SearchCommands with equal predicates.
     */
    @Override
    public boolean equals(Object other) {
        return other == this || (other instanceof SearchCommand
                && predicate.equals(((SearchCommand) other).predicate));
    }

    @Override
    public String toString() {
        return getClass().getCanonicalName() + "{predicate=" + predicate + "}";
    }
}
