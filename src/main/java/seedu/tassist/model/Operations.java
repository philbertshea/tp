package seedu.tassist.model;

import javafx.collections.ObservableList;

import seedu.tassist.logic.commands.UndoCommand;
import seedu.tassist.logic.commands.RedoCommand;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.person.Person;

import java.util.ArrayList;
import java.util.List;

import static seedu.tassist.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class Operations {


    public enum CommandType {ADD, EDIT, DELETE, CLEAR, ATTENDANCE, LABSCORE, IGNORED, UNDO, REDO}
    public static Model model = null;
    private static ArrayList<Snapshot> pastStates = new ArrayList<>();
    private static ArrayList<Snapshot> futureStates = new ArrayList<>();
    private static Snapshot currentState = null;

    private static String ERROR_MESSAGE = "You have reached the limit of %s";
    private static String EMPTY_ERROR_MESSAGE = "There is nothing to %s.";
    public static final String COMMAND_IGNORED = "%1$s was the last command, no changes has occurred";
    private static boolean isRecorded = false;
    public static void recordCurrentCommand(CommandType commandType) throws ParseException {
        if(currentState == null || pastStates.isEmpty() && futureStates.isEmpty()) {
            throw new ParseException(String.format(EMPTY_ERROR_MESSAGE, commandType.toString().toLowerCase()));
        }

        Snapshot snapshot = new Snapshot(currentState.commandString, currentState.commandType);
        //snapshot.setAddressBook(model.getAddressBook());
        if (currentState.commandType == CommandType.EDIT || currentState.commandType == CommandType.DELETE ||
                currentState.commandType == CommandType.LABSCORE || currentState.commandType == CommandType.ATTENDANCE) {
            Person person =  model.getAddressBook().getPersonList().get(currentState.getIndex());
            snapshot.setPerson(person);
        }

        if (currentState.commandType == CommandType.ADD) {
            snapshot.commandType = CommandType.DELETE;
            Person person =  model.getAddressBook().getPersonList().get(model.getAddressBook().getPersonList().size() - 1);
            snapshot.setPerson(person);
        }

        if (commandType == CommandType.UNDO) {
            futureStates.add(snapshot);
        } else {
            pastStates.add(snapshot);
        }
    }

    public static void recordCurrentCommand(String command, CommandType commandType){
        if(model == null) return;
        currentState = new Snapshot(command, commandType);
        System.out.println("recording");
        //System.out.println(model.getAddressBook().getPersonList().get(0).getLabScoreList());

        //get list and command type
        CommandType currentType = currentState.commandType;
        ObservableList<Person> personList = model.getAddressBook().getPersonList();
        System.out.println(currentType);
        //temp guard for commands not supported
        if (currentState.getIndex() != -1) saveIndexCommand(personList);
//        System.out.println(currentState.getIndex());


        if (currentType == CommandType.CLEAR) {
            //save person list
            currentState.setPerson(personList.toArray(new Person[0]));
        }

        pastStates.add(currentState);

        //New branch in timeline, clear future states
        if (!futureStates.isEmpty()) {
            futureStates.clear();
        }

        isRecorded = true;
    }

    public static void saveIndexCommand(ObservableList<Person> personList) {
//        else if (currentType == CommandType.EDIT || currentType == CommandType.DELETE ||
//                currentType == CommandType.LABSCORE || currentType == CommandType.ATTENDANCE) {
//
//        }
        //save person involved
        Person editedPerson = personList.get(currentState.getIndex());
        currentState.setPerson(editedPerson);
    }

    public static void resetRecording() {
        isRecorded = false;
    }

    public static void removeRecording() {
        System.out.println(pastStates.size());
        if (!isRecorded) {
            return;
        }
        pastStates.remove(pastStates.size() - 1);
        System.out.println(pastStates.size());
        isRecorded = false;
    }



    public static void update(Model modelUpdate){
        model = modelUpdate;
    }

    public static String undo(Model model) throws CommandException{
        if (pastStates.isEmpty()) {
            futureStates.remove(futureStates.size() - 1);
            throw new CommandException(String.format(ERROR_MESSAGE, "undo"));
        }
        //Update timeline
        currentState =  pastStates.remove(pastStates.size() - 1);
        System.out.println("before undo");
        //System.out.println(currentState.getPerson().getLabScoreList());
        runCommand(model, currentState);
        if (currentState.commandType == CommandType.IGNORED) {
            return String.format(COMMAND_IGNORED, currentState.commandType.toString());
        }
        return String.format(UndoCommand.MESSAGE_UNDO_SUCCESS, currentState.commandType.toString());
    }

    public static String redo(Model model) throws CommandException{
        if (futureStates.isEmpty()) {
            pastStates.remove(pastStates.size() - 1);
            throw new CommandException(String.format(ERROR_MESSAGE, "redo"));
        }
        //Update timeline
        currentState =  futureStates.remove(futureStates.size() - 1);
        System.out.println("before redo" + futureStates.size());
        System.out.println(currentState.getPerson().getLabScoreList());

        runCommand(model, currentState);
        if (currentState.commandType == CommandType.IGNORED) {
            return String.format(COMMAND_IGNORED, currentState.commandType.toString());
        }
        return String.format(RedoCommand.MESSAGE_REDO_SUCCESS, currentState.commandType.toString());
    }

    public static void runCommand(Model model, Snapshot currentState) {
        //System.out.println("param" + currentState.getPerson().getLabScoreList());
        //System.out.println(currentState.addressBook.getPersonList().get(0).getLabScoreList());
        CommandType currentCommand = currentState.commandType;

        if (currentCommand == CommandType.EDIT || currentCommand == CommandType.ATTENDANCE ||
                currentCommand == CommandType.LABSCORE) {
            //for edit, attendance, labscore == set back the previous person
            List<Person> lastShownList = model.getFilteredPersonList();
            Person personToUpdate = lastShownList.get(currentState.getIndex());

            //currentState.extractPerson();
            Person updatedPerson = currentState.getPerson();
            model.setPerson(personToUpdate, updatedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } else if (currentCommand == CommandType.DELETE) {
            //for delete, add back the person
            System.out.println("delete");
            ArrayList<Person> addAll = new ArrayList<>();
            addAll.add(currentState.getPerson());
//            for (int i = currentState.getIndex() + 1; i < model.getFilteredPersonList().size(); i++) {
//                addAll.add(model.getFilteredPersonList().get(i));
//            }
            model.getFilteredPersonList().setAll(addAll);
        } else if (currentCommand == CommandType.ADD) {
            //for add, delete the newly added person
            System.out.println(model.getFilteredPersonList().toString());
            Person personToDelete = model.getFilteredPersonList().get(model.getFilteredPersonList().size() - 1);
            model.deletePerson(personToDelete);
        } else if (currentCommand == CommandType.CLEAR) {
            //for clear, add everything back
            //model.setAddressBook(currentState.addressBook);
        } else {
            //for ignored, show the message string
        }
        System.out.println("finish");
        System.out.println(model.getAddressBook().getPersonList().get(0).getLabScoreList());
    }
}
