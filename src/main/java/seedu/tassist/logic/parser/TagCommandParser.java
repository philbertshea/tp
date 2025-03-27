package seedu.tassist.logic.parser;

import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.commands.TagCommand.MESSAGE_USAGE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_DELETE_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EDIT_TAG;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Stream;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.commands.TagCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.tag.Tag;

/**
 *  Parses input arguments and creates a new TagCommand object
 */
public class TagCommandParser implements Parser<TagCommand> {

    public static final String MESSAGE_INVALID_ACTION_TYPE =
            "Either an add, edit or delete flag should be provided! \n%s";
    public static final String MESSAGE_MISSING_TAG = "Add a tag! \n%s";
    public static final String MESSAGE_MISSING_OLD_NEW_TAG = "You need an old tag and a new tag! \n%s";
    /**
     * The different action types for the tag parsing
     */
    public enum ActionType {
        ADD, EDIT, DEL
    }

    /**
     * Parses {@code userInput} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code userInput} does not conform the expected format
     */
    @Override
    public TagCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_ADD_TAG, PREFIX_EDIT_TAG, PREFIX_DELETE_TAG,
                        PREFIX_INDEX, PREFIX_TAG
                );

        if (!argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagCommand.MESSAGE_USAGE));
        }

        Index index;

        try {
            index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).orElse(""));
        } catch (ParseException pe) {
            throw new ParseException(Index.MESSAGE_CONSTRAINTS, pe);
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_INDEX, PREFIX_ADD_TAG, PREFIX_DELETE_TAG, PREFIX_EDIT_TAG);

        if (!anyPrefixesPresent(argMultimap, PREFIX_ADD_TAG, PREFIX_DELETE_TAG, PREFIX_EDIT_TAG)) {
            throw new ParseException(String.format(MESSAGE_INVALID_ACTION_TYPE, MESSAGE_USAGE));
        }

        if (!anyPrefixesPresent(argMultimap, PREFIX_TAG)) {
            throw new ParseException(String.format(MESSAGE_MISSING_TAG, MESSAGE_USAGE));
        }

        Set<Tag> tagList = new LinkedHashSet<>(ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)));

        if (arePrefixesPresent(argMultimap, PREFIX_ADD_TAG)) {
            return new TagCommand(index, ActionType.ADD, tagList, null, null);
        } else if (arePrefixesPresent(argMultimap, PREFIX_DELETE_TAG)) {
            return new TagCommand(index, ActionType.DEL, tagList, null, null);
        } else if (arePrefixesPresent(argMultimap, PREFIX_EDIT_TAG)) {
            if (tagList.size() != 2) {
                throw new ParseException(String.format(MESSAGE_MISSING_OLD_NEW_TAG, MESSAGE_USAGE));
            }
            int idx = 0;
            Tag oldTag = new Tag("new");
            Tag newTag = new Tag("new");
            for (Tag t: tagList) {
                if (idx == 0) {
                    oldTag = t;
                } else {
                    newTag = t;
                }
                idx++;
            }
            return new TagCommand(index, ActionType.EDIT, null, oldTag, newTag);
        } else {
            throw new ParseException("You have given an invalid tag command!");
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(
                prefix -> argumentMultimap.getValue(prefix).isPresent());
    }

    /**
     * Returns true if either of the prefixes contains an {@code Optional} value in the given
     * {@Code ArgumentMultimap}.
     */
    private static boolean anyPrefixesPresent(ArgumentMultimap argumentMultimap,
                                              Prefix... prefixes) {
        return Stream.of(prefixes).anyMatch(
                prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
