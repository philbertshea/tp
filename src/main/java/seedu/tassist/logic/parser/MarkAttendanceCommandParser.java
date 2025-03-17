package seedu.tassist.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.tassist.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_NO_TUTORIAL;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_ON_MC;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_MARK_UNATTENDED;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_TUT_GROUP;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_WEEK;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.commons.exceptions.IllegalValueException;
import seedu.tassist.logic.commands.MarkAttendanceCommand;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.person.Attendance;
import seedu.tassist.model.person.TutGroup;

/**
 * Parses input arguments and creates a new MarkAttendanceCommand object.
 */
public class MarkAttendanceCommandParser implements Parser<MarkAttendanceCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the MarkAttendanceCommand
     * and returns an MarkAttendanceCommand object for execution.
     *
     * @param args String input to be parsed
     * @return MarkAttendanceCommand corresponding to the String input
     * @throws ParseException If the user input does not conform to the expected format
     */
    public MarkAttendanceCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(
                args, PREFIX_INDEX, PREFIX_TUT_GROUP, PREFIX_WEEK,
                PREFIX_MARK_UNATTENDED, PREFIX_MARK_ON_MC, PREFIX_MARK_NO_TUTORIAL);

        Index index = null;
        TutGroup tutGroup = null;
        int week;
        boolean hasIndex = false;
        boolean hasTutGroup = false;
        boolean isUnattended = false;
        boolean isOnMc = false;
        boolean isNoTut = false;

        try {
            week = ParserUtil.parseWeek(argMultimap.getValue(PREFIX_WEEK).orElse(""));
            hasIndex = argMultimap.getValue(PREFIX_INDEX).isPresent();
            hasTutGroup = argMultimap.getValue(PREFIX_TUT_GROUP).isPresent();
            isUnattended = argMultimap.getValue(PREFIX_MARK_UNATTENDED).isPresent();
            isOnMc = argMultimap.getValue(PREFIX_MARK_ON_MC).isPresent();
            isNoTut = argMultimap.getValue(PREFIX_MARK_NO_TUTORIAL).isPresent();
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MarkAttendanceCommand.MESSAGE_USAGE), ive
            );
        }


        boolean hasAtLeastTwoConflictingFlags =
                (isUnattended && isOnMc)
                || (isUnattended && isNoTut)
                || (isOnMc && isNoTut)
                || (hasIndex && hasTutGroup);
        boolean hasNeitherIndexNorTutGroup = !hasIndex && !hasTutGroup;

        if (hasAtLeastTwoConflictingFlags || hasNeitherIndexNorTutGroup) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MarkAttendanceCommand.MESSAGE_USAGE)
            );
        }

        try {
            if (hasIndex) {
                index = ParserUtil.parseIndex(argMultimap.getValue(PREFIX_INDEX).orElse(""));
            } else {
                tutGroup = ParserUtil.parseTutGroup(argMultimap.getValue(PREFIX_TUT_GROUP).orElse(""));
            }
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                            MarkAttendanceCommand.MESSAGE_USAGE), ive
            );
        }


        if (isUnattended) {
            return new MarkAttendanceCommand(index, tutGroup, week, Attendance.NOT_ATTENDED);
        } else if (isOnMc) {
            return new MarkAttendanceCommand(index, tutGroup, week, Attendance.ON_MC);
        } else if (isNoTut) {
            return new MarkAttendanceCommand(index, tutGroup, week, Attendance.NO_TUTORIAL);
        } else {
            return new MarkAttendanceCommand(index, tutGroup, week, Attendance.ATTENDED);
        }


    }
}
