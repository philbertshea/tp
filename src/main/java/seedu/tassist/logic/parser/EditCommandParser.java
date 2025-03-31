package seedu.tassist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.Messages.MESSAGE_INVALID_QUOTES;
import static seedu.tassist.logic.parser.AddCommandParser.anyPrefixesPresent;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TELE_HANDLE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_YEAR;

import java.util.List;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.commands.EditCommand;
import seedu.tassist.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.tassist.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new EditCommand object.
 */
public class EditCommandParser implements Parser<EditCommand> {

    public static final String MESSAGE_INVALID_BATCH_FIELDS = "You can only edit the tutorial group, lab group,"
            + " faculty or year when doing a batch edit!";
    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);

        // Ensure an even number of quotes.
        if (!new QuotePattern().test(args)) {
            throw new ParseException(MESSAGE_INVALID_QUOTES);
        }

        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_INDEX, PREFIX_NAME, PREFIX_PHONE, PREFIX_TELE_HANDLE, PREFIX_EMAIL,
                        PREFIX_MAT_NUM, PREFIX_TUT_GROUP, PREFIX_LAB_GROUP, PREFIX_FACULTY,
                        PREFIX_YEAR, PREFIX_REMARK
                );

        // Checks if the index has been defined + if there are any fields the users wants to edit
        if (!argMultimap.getValue(PREFIX_INDEX).isPresent()
                || !anyPrefixesPresent(argMultimap, PREFIX_NAME, PREFIX_PHONE, PREFIX_TELE_HANDLE, PREFIX_EMAIL,
                PREFIX_MAT_NUM, PREFIX_TUT_GROUP, PREFIX_LAB_GROUP, PREFIX_FACULTY,
                PREFIX_YEAR, PREFIX_REMARK)
        ) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditCommand.MESSAGE_USAGE));
        }
        String rawIndexes = argMultimap.getValue(PREFIX_INDEX).orElse("");
        List<Index> targetIndexes;
        try {
            targetIndexes = ParserUtil.parseMultipleIndexes(rawIndexes);
        } catch (ParseException pe) {
            throw new ParseException(Index.MESSAGE_CONSTRAINTS, pe);
        }

        if (targetIndexes.size() > 1 && anyPrefixesPresent(argMultimap,
                PREFIX_NAME, PREFIX_PHONE, PREFIX_TELE_HANDLE, PREFIX_EMAIL,
                PREFIX_MAT_NUM, PREFIX_REMARK)
        ) {
            throw new ParseException(MESSAGE_INVALID_BATCH_FIELDS);
        }

        argMultimap.verifyNoDuplicatePrefixesAndWarnQuotesFor(
                PREFIX_INDEX,
                PREFIX_TUT_GROUP, PREFIX_LAB_GROUP, PREFIX_FACULTY,
                PREFIX_YEAR
        );
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (targetIndexes.size() == 1) {
            argMultimap.verifyNoDuplicatePrefixesAndWarnQuotesFor(
                    PREFIX_NAME, PREFIX_PHONE, PREFIX_TELE_HANDLE, PREFIX_EMAIL,
                    PREFIX_MAT_NUM, PREFIX_REMARK
            );
            if (argMultimap.getValue(PREFIX_NAME).isPresent()) {
                editPersonDescriptor.setName(ParserUtil.parseName(
                        argMultimap.getValue(PREFIX_NAME).get()));
            }
            if (argMultimap.getValue(PREFIX_PHONE).isPresent()) {
                editPersonDescriptor.setPhone(ParserUtil.parsePhone(
                        argMultimap.getValue(PREFIX_PHONE).get()));
            }
            if (argMultimap.getValue(PREFIX_TELE_HANDLE).isPresent()) {
                editPersonDescriptor.setTeleHandle(ParserUtil.parseTeleHandle(
                        argMultimap.getValue(PREFIX_TELE_HANDLE).get()));
            }
            if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
                editPersonDescriptor.setEmail(ParserUtil.parseEmail(
                        argMultimap.getValue(PREFIX_EMAIL).get()));
            }
            if (argMultimap.getValue(PREFIX_MAT_NUM).isPresent()) {
                editPersonDescriptor.setMatNum(ParserUtil.parseMatNum(
                        argMultimap.getValue(PREFIX_MAT_NUM).get()));
            }

            if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
                editPersonDescriptor.setRemark(ParserUtil.parseRemark(
                        argMultimap.getValue(PREFIX_REMARK).get()));
            }
        }
        if (argMultimap.getValue(PREFIX_TUT_GROUP).isPresent()) {
            editPersonDescriptor.setTutGroup(ParserUtil.parseTutGroup(
                    argMultimap.getValue(PREFIX_TUT_GROUP).get()));
        }
        if (argMultimap.getValue(PREFIX_LAB_GROUP).isPresent()) {
            editPersonDescriptor.setLabGroup(ParserUtil.parseLabGroup(
                    argMultimap.getValue(PREFIX_LAB_GROUP).get()));
        }
        if (argMultimap.getValue(PREFIX_FACULTY).isPresent()) {
            editPersonDescriptor.setFaculty(ParserUtil.parseFaculty(
                    argMultimap.getValue(PREFIX_FACULTY).get()));
        }
        if (argMultimap.getValue(PREFIX_YEAR).isPresent()) {
            editPersonDescriptor.setYear(ParserUtil.parseYear(
                    argMultimap.getValue(PREFIX_YEAR).get()));
        }

        if (argMultimap.getValue(PREFIX_REMARK).isPresent()) {
            editPersonDescriptor.setRemark(ParserUtil.parseRemark(
                    argMultimap.getValue(PREFIX_REMARK).get()));
        }

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(targetIndexes, editPersonDescriptor);
    }
}
