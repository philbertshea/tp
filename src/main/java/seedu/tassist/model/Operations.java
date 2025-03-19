package seedu.tassist.model;

import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.person.Person;

import java.util.ArrayList;
import java.util.List;

import static seedu.tassist.model.Model.PREDICATE_SHOW_ALL_PERSONS;

public class Operations {


    public enum CommandType {ADD, EDIT, DELETE, CLEAR, ATTENDANCE, LABSCORE, IGNORED, UNDO, REDO}
    public static Model model;
    private static ArrayList<Snapshot> pastStates = new ArrayList<>();
    private static ArrayList<Snapshot> futureStates = new ArrayList<>();
    private static Snapshot currentState;

    private static String ERROR_MESSAGE = "You have reached the limit of %s";
    public static final String COMMAND_IGNORED = "%1$s was the last command, no changes has occurred";
    public static void recordCurrentCommand(CommandType commandType) {
//        System.out.println("entered");
//        System.out.println(currentState.commandString);
        Snapshot snapshot = new Snapshot(currentState.commandString, currentState.commandType);
        //snapshot.setAddressBook(model.getAddressBook());
        Person person =  model.getAddressBook().getPersonList().get(currentState.getIndex());
        snapshot.setPerson(person);


        if (commandType == CommandType.UNDO) {
            futureStates.add(snapshot);
        } else {
            pastStates.add(snapshot);
        }
    }

    public static void recordCurrentCommand(String command, CommandType commandType){
        Snapshot snapshot = new Snapshot(command, commandType);
        currentState = snapshot;
        System.out.println("somehow running");
        System.out.println(currentState);

        System.out.println(model.getAddressBook().getPersonList().get(0).getLabScoreList());
        CommandType currentType = currentState.commandType;
        System.out.println(currentType);
        System.out.println(currentState.getIndex());
        if (currentState.getIndex() == -1) return;
        //System.out.println(currentType == CommandType.CLEAR);
        if (currentType == CommandType.CLEAR) {
            //save person list
            //currentState.setAddressBook(model.getAddressBook());
        } else if (currentType == CommandType.EDIT || currentType == CommandType.DELETE ||
                currentType == CommandType.LABSCORE || currentType == CommandType.ATTENDANCE ||
                currentType == CommandType.UNDO || currentType == CommandType.REDO) {
            //save person involved
            //currentState.setAddressBook(model.getAddressBook());
            Person person =  model.getAddressBook().getPersonList().get(currentState.getIndex());
            currentState.setPerson(person);
        }

        System.out.println(currentState.person.getLabScoreList());

        pastStates.add(currentState);
        //if current command is not redo/undo, clear the future states
        //Branching timeline
        //futureStates.clear();
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
        //futureStates.add(currentState);
        System.out.println("before undo");
        System.out.println(currentState.person.getLabScoreList());
        runCommand(model, currentState);
        System.out.println(currentState);
        return currentState.commandType.toString();
    }

    public static String redo(Model model) throws CommandException{
        if (futureStates.isEmpty()) {
            pastStates.remove(pastStates.size() - 1);
            throw new CommandException(String.format(ERROR_MESSAGE, "redo"));
        }
        //Update timeline
        currentState =  futureStates.remove(futureStates.size() - 1);
        System.out.println("before redo" + futureStates.size());
        System.out.println(currentState.person.getLabScoreList());
        //recordCurrentCommand(currentState.commandString, currentState.commandType);
        //pastStates.add(currentState);
        runCommand(model, currentState);
        return currentState.commandType.toString();
    }

    public static void runCommand(Model model, Snapshot currentState) {
        System.out.println("param" + currentState.person.getLabScoreList());
        //System.out.println(currentState.addressBook.getPersonList().get(0).getLabScoreList());
        CommandType currentCommand = currentState.commandType;

        if (currentCommand == CommandType.EDIT || currentCommand == CommandType.ATTENDANCE ||
                currentCommand == CommandType.LABSCORE) {
            //for edit, attendance, labscore == set back the previous person
            List<Person> lastShownList = model.getFilteredPersonList();
            Person personToUpdate = lastShownList.get(currentState.getIndex());

            //currentState.extractPerson();
            Person updatedPerson = currentState.person;
            model.setPerson(personToUpdate, updatedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        } else if (currentCommand == CommandType.DELETE) {
            //for delete, add back the person
            ArrayList<Person> addAll = new ArrayList<>();
            addAll.add(currentState.person);
            for (int i = currentState.getIndex() + 1; i < model.getFilteredPersonList().size(); i++) {
                addAll.add(model.getFilteredPersonList().get(i));
            }
            model.getFilteredPersonList().setAll(addAll);
        } else if (currentCommand == CommandType.ADD) {
            //for add, delete the newly added person
            Person personToDelete = model.getFilteredPersonList().get(model.getFilteredPersonList().size() - 1);
            model.deletePerson(personToDelete);
        } else if (currentCommand == CommandType.CLEAR) {
            //for clear, add everything back
            //model.setAddressBook(currentState.addressBook);
        } else {
            //for ignored, show the message string
        }
        System.out.println("finish undo");
        System.out.println(model.getAddressBook().getPersonList().get(0).getLabScoreList());
        //recordCurrentCommand(currentState.commandString, currentState.commandType);
    }
}
