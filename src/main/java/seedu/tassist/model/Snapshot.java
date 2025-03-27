package seedu.tassist.model;

import static seedu.tassist.logic.parser.ParserUtil.parseMultipleIndexes;

import java.util.ArrayList;
import java.util.List;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.person.Person;

/**
 * Captures a frame of the command.
 */
public class Snapshot {
    private static final int emptyIndex = -1;
    private String commandString;
    private Operations.CommandType commandType;
    private ArrayList<Person> people = new ArrayList<>();

    private Integer index = null;


    /**
     * Creates a new snapshot object.
     *
     * @param commandString The command string.
     * @param commandType The type of command.
     */
    public Snapshot(String commandString, Operations.CommandType commandType) {
        this.commandString = commandString;
        this.commandType = commandType;
    }

    /**
     * Duplicates the given snapshot.
     *
     * @param currentSnapshot The snapshot to duplicate.
     */
    public Snapshot(Snapshot currentSnapshot) {
        this.commandString = currentSnapshot.commandString;
        this.commandType = currentSnapshot.commandType;
    }

    /**
     * Gets the current command type.
     *
     * @return The current command type.
     */
    public Operations.CommandType getCommandType() {
        return commandType;
    }

    /**
     * Sets the command type.
     *
     * @param newCommandType The new command type.
     */
    public void setCommandType(Operations.CommandType newCommandType) {
        commandType = newCommandType;
    }

    /**
     * Sets the people involved in the command.
     *
     * @param people The people involved.
     */
    public void setPerson(Person... people) {
        for (Person person : people) {
            this.people.add(person);
        }
    }

    /**
     * Gets the person involved in the command.
     *
     * @return The person involved.
     */
    public Person getPerson() {
        if (people.isEmpty()) {
            return null;
        }
        return people.get(0);
    }

    /**
     * Gets the entire list of people involved in the command.
     *
     * @return The entire list of people.
     */
    public ArrayList<Person> getPeople() {
        return people;
    }

    /**
     * Duplicates the given list as the current list.
     *
     * @param duplicatePeopleList The list of people to duplicate.
     */
    public void duplicatePeople(ArrayList<Person> duplicatePeopleList) {
        this.people = duplicatePeopleList;
    }

    /**
     * Sets the index of the person involved.
     *
     * @param newIndex The new index.
     */
    public void setIndex(Integer newIndex) {
        index = newIndex;
    }

    /**
     * Gets the index of the person involved.
     *
     * @return The integer of the person involved.
     */
    public int getIndex() {
        //Index field was set
        if (index != null) {
            return index;
        }

        int startSubstring = commandString.indexOf("-i");

        //Index field not found
        if (startSubstring < 0) {
            return emptyIndex;
        }

        //Index field found
        String commandIndex = commandString.substring(startSubstring + 3);

        int endSubstring = commandIndex.indexOf(" ");
        if (endSubstring > 0) {
            commandIndex = commandIndex.substring(0, endSubstring);
        }

        List<Index> allIndex;
        try {
            allIndex = parseMultipleIndexes(commandIndex);
        } catch (ParseException e) {
            return emptyIndex;
        }
        index = allIndex.get(0).getZeroBased();
        return index;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof Snapshot)) {
            return false;
        }

        Snapshot o = (Snapshot) other;
        return this.commandType.equals(o.commandType)
                && this.commandString.equals(o.commandString)
                && this.people.equals(o.people)
                && ((this.index == null && o.index == null)
                || this.index.equals(o.index));
    }
}
