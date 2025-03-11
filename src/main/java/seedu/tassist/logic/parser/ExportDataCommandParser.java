package seedu.tassist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EXTENSION;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FILENAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_PHONE;

import java.util.stream.Stream;

import seedu.tassist.logic.commands.AddCommand;
import seedu.tassist.logic.commands.ExportDataCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;

public class ExportDataCommandParser implements Parser<ExportDataCommand>{

    /**
     * Parses {@code args} into a command and returns it.
     *
     * @param args
     * @throws ParseException if {@code args} does not conform the expected format
     */
    @Override
    public ExportDataCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_FILENAME, PREFIX_EXTENSION);

        if (!arePrefixesPresent(argMultimap, PREFIX_FILENAME, PREFIX_EXTENSION)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExportDataCommand.MESSAGE_USAGE));
        }

        argMultimap.verifyNoDuplicatePrefixesFor(PREFIX_FILENAME, PREFIX_EXTENSION);

        String fileName = argMultimap.getValue(PREFIX_FILENAME).orElse("");
        String extension = argMultimap.getValue(PREFIX_EXTENSION).orElse("").toLowerCase().trim();

        return new ExportDataCommand(fileName, extension);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
