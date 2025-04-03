package seedu.tassist.model;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import seedu.tassist.logic.commands.RedoCommand;
import seedu.tassist.logic.commands.UndoCommand;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.person.Person;



/**
 * Records all the operations for redo and undo.
 */
public class Operations {

    /**
     * Holds all the types of commands involved.
     */
    public enum CommandType { IGNORE, UNDO, REDO, RECORD }
    public static final String COMMAND_IGNORED = "%1$s command was the last command, no changes has occurred";
    private static final String ERROR_MESSAGE = "You have reached the limit of %s";
    private static final String EMPTY_ERROR_MESSAGE = "There is nothing to %s.";
    private static Model model = null;
    private static ArrayList<Snapshot> pastStates = new ArrayList<>();
    private static ArrayList<Snapshot> futureStates = new ArrayList<>();
    private static Snapshot currentState = null;

    private static CommandType lastCommand;

    private static boolean isRecorded = false;

    /**
     * Records the undo and redo command state.
     *
     * @param commandType The type of command (either undo or redo).
     * @throws ParseException If there is nothing to redo and undo.
     */
    public static void recordCurrentCommand(CommandType commandType) throws ParseException {
        assert commandType == CommandType.REDO || commandType == CommandType.UNDO;
        boolean isEmptyStates = pastStates.isEmpty() && futureStates.isEmpty();
        if (currentState == null || isEmptyStates) {
            throw new ParseException(String.format(EMPTY_ERROR_MESSAGE, commandType.toString().toLowerCase()));
        }

        lastCommand = commandType;

        Snapshot copyState;
        if (commandType == CommandType.UNDO) {
            copyState = pastStates.isEmpty() ? currentState : pastStates.get(pastStates.size() - 1);
        } else {
            copyState = futureStates.isEmpty() ? currentState : futureStates.get(futureStates.size() - 1);
        }

        Snapshot newState = new Snapshot(copyState);
        ObservableList<Person> personList = model.getAddressBook().getPersonList();
        newState.setPerson(personList.toArray(new Person[0]));

        if (commandType == CommandType.UNDO) {
            futureStates.add(newState);
        } else {
            pastStates.add(newState);
        }
    }

    /**
     * Records the ignore command state.
     *
     * @param commandTypeString The type of user command.
     * @param commandType The type of command (ignore command).
     */
    public static void recordCurrentCommand(String commandTypeString, CommandType commandType) {
        assert commandType == CommandType.IGNORE;
        if (model == null) {
            return;
        }

        currentState = new Snapshot(commandTypeString, commandType);
        lastCommand = commandType;
        pastStates.add(currentState);

        isRecorded = true;
    }

    /**
     * Records the record command state.
     *
     * @param command The user command that is executed.
     * @param commandTypeString The type of user command.
     * @param commandType The type of command (record command).
     */
    public static void recordCurrentCommand(String command, String commandTypeString, CommandType commandType) {
        assert commandType == CommandType.RECORD;
        if (model == null) {
            return;
        }

        currentState = new Snapshot(command, commandTypeString, commandType);
        lastCommand = commandType;

        //Record state data.
        ObservableList<Person> personList = model.getAddressBook().getPersonList();
        currentState.setPerson(personList.toArray(new Person[0]));

        pastStates.add(currentState);

        isRecorded = true;
    }


    /**
     * Resets {@code isRecorded} and {@code futureStates} if required at the start of new command.
     */
    public static void resetRecording() {
        if (!isRecorded) {
            // Error was thrown
            return;
        }

        // Resets future stack if timeline was changed
        boolean isUpdateStack = lastCommand == CommandType.RECORD || lastCommand == CommandType.IGNORE;
        if (isUpdateStack && !futureStates.isEmpty()) {
            futureStates.clear();
        }

        isRecorded = false;
    }

    /**
     * Removes the last recorded command if it did not run (errors thrown).
     */
    public static void removeRecording() {
        if (!isRecorded) {
            return;
        }
        pastStates.remove(pastStates.size() - 1);
        isRecorded = false;
    }

    /**
     * Updates the current model with the updated model at each execution.
     *
     * @param modelUpdate The new model.
     */
    public static void update(Model modelUpdate) {
        model = modelUpdate;
    }

    /**
     * Undoes the current command.
     *
     * @param model The current model.
     * @return A feedback String.
     * @throws CommandException If there is no command to undo.
     */
    public static String undo(Model model) throws CommandException {
        if (pastStates.isEmpty()) {
            futureStates.remove(futureStates.size() - 1);
            throw new CommandException(String.format(ERROR_MESSAGE, "undo"));
        }

        currentState = pastStates.remove(pastStates.size() - 1);

        CommandType currentCommandType = currentState.getCommandType();
        if (currentCommandType == CommandType.IGNORE) {
            return String.format(COMMAND_IGNORED, currentState.getCommandTypeString());
        }

        runCommand(model, currentState);

        return String.format(UndoCommand.MESSAGE_UNDO_SUCCESS,
                currentState.getCommandTypeString(), currentState.getCommandString());
    }

    /**
     * Redoes the current command.
     *
     * @param model The current model.
     * @return A feedback string.
     * @throws CommandException If there is nothing to redo.
     */
    public static String redo(Model model) throws CommandException {
        if (futureStates.isEmpty()) {
            pastStates.remove(pastStates.size() - 1);
            throw new CommandException(String.format(ERROR_MESSAGE, "redo"));
        }

        currentState = futureStates.remove(futureStates.size() - 1);

        CommandType currentCommandType = currentState.getCommandType();
        if (currentCommandType == CommandType.IGNORE) {
            return String.format(COMMAND_IGNORED, currentState.getCommandTypeString());
        }

        runCommand(model, currentState);

        return String.format(RedoCommand.MESSAGE_REDO_SUCCESS,
                currentState.getCommandTypeString(), currentState.getCommandString());
    }

    /**
     * Sets the states to the desired moment.
     *
     * @param model The current model.
     * @param currentState The current state.
     */
    public static void runCommand(Model model, Snapshot currentState) {
        CommandType currentCommand = currentState.getCommandType();

        if (currentCommand == CommandType.RECORD) {
            model.setAddressBook(new AddressBook());
            ArrayList<Person> addAll = currentState.getPeople();
            for (Person person : addAll) {
                model.addPerson(person);
            }
        }
    }
}
