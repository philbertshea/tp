package seedu.tassist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.parser.AddCommandParser.anyPrefixesPresent;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_LAB_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MAT_NUM;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TAG;
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

    /**
     * Parses the given {@code String} of arguments in the context of the EditCommand
     * and returns an EditCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args,
                        PREFIX_INDEX, PREFIX_NAME, PREFIX_PHONE, PREFIX_TELE_HANDLE, PREFIX_EMAIL,
                        PREFIX_MAT_NUM, PREFIX_TUT_GROUP, PREFIX_LAB_GROUP, PREFIX_FACULTY,
                        PREFIX_YEAR, PREFIX_REMARK
                );

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
            throw new ParseException(
                    "You can only edit the tutorial group, lab group,"
                            + " faculty and year when doing a batch edit!");
        }

        argMultimap.verifyNoDuplicatePrefixesFor(
                PREFIX_INDEX,
                PREFIX_TUT_GROUP, PREFIX_LAB_GROUP, PREFIX_FACULTY,
                PREFIX_YEAR
        );
        EditPersonDescriptor editPersonDescriptor = new EditPersonDescriptor();
        if (targetIndexes.size() == 1) {
            argMultimap.verifyNoDuplicatePrefixesFor(
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

        if (!editPersonDescriptor.isAnyFieldEdited()) {
            throw new ParseException(EditCommand.MESSAGE_NOT_EDITED);
        }

        return new EditCommand(targetIndexes, editPersonDescriptor);
    }
}
