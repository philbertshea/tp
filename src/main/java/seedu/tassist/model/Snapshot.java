package seedu.tassist.model;

import java.util.ArrayList;

import seedu.tassist.model.person.Person;

/**
 * Captures a frame of the command.
 */
public class Snapshot {

    private String commandString = "";
    private String commandTypeString = "";
    private Operations.CommandType commandType;
    private ArrayList<Person> people = new ArrayList<>();


    /**
     * Creates a new snapshot object for RECORD type command.
     *
     * @param commandString The command string.
     * @param commandTypeString The type of user command.
     * @param commandType The type of command.
     */
    public Snapshot(String commandString, String commandTypeString, Operations.CommandType commandType) {
        this.commandString = commandString;
        this.commandTypeString = commandTypeString;
        this.commandType = commandType;
    }

    /**
     * Creates a new snapshot object for IGNORE type command.
     *
     * @param commandTypeString The type of user command.
     * @param commandType The type of command.
     */
    public Snapshot(String commandTypeString, Operations.CommandType commandType) {
        this.commandTypeString = commandTypeString;
        this.commandType = commandType;
    }

    /**
     * Duplicates the given snapshot.
     *
     * @param currentSnapshot The snapshot to duplicate.
     */
    public Snapshot(Snapshot currentSnapshot) {
        this.commandString = currentSnapshot.commandString;
        this.commandTypeString = currentSnapshot.commandTypeString;
        this.commandType = currentSnapshot.commandType;
    }

    /**
     * Gets the command string that the user had input.
     *
     * @return The command string.
     */
    public String getCommandString() {
        return commandString;
    }


    /**
     * Gets the type of user command.
     *
     * @return The type of user command.
     */
    public String getCommandTypeString() {
        return commandTypeString.toLowerCase();
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
     * Gets the entire list of people involved in the command.
     *
     * @return The entire list of people.
     */
    public ArrayList<Person> getPeople() {
        return people;
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
                && this.commandTypeString.equals(o.commandTypeString)
                && this.people.equals(o.people);
    }
}
