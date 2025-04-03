package seedu.tassist.model;

import java.util.ArrayList;

import seedu.tassist.model.person.Person;

/**
 * Captures a frame of the command.
 */
public class Snapshot {

    private String commandString = "";
    private String commandTypeString = "";
    private Operations.RecordType recordType;
    private ArrayList<Person> people = new ArrayList<>();


    /**
     * Creates a new snapshot object for RECORD type command.
     *
     * @param commandString The command string.
     * @param commandTypeString The type of user command.
     * @param recordType The type of recording.
     */
    public Snapshot(String commandString, String commandTypeString, Operations.RecordType recordType) {
        this.commandString = commandString;
        this.commandTypeString = commandTypeString;
        this.recordType = recordType;
    }

    /**
     * Creates a new snapshot object for IGNORE type command.
     *
     * @param commandTypeString The type of user command.
     * @param recordType The type of recording.
     */
    public Snapshot(String commandTypeString, Operations.RecordType recordType) {
        this.commandTypeString = commandTypeString;
        this.recordType = recordType;
    }

    /**
     * Duplicates the given snapshot.
     *
     * @param currentSnapshot The snapshot to duplicate.
     */
    public Snapshot(Snapshot currentSnapshot) {
        this.commandString = currentSnapshot.commandString;
        this.commandTypeString = currentSnapshot.commandTypeString;
        this.recordType = currentSnapshot.recordType;
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
     * Gets the current record type.
     *
     * @return The current record type.
     */
    public Operations.RecordType getRecordType() {
        return recordType;
    }

    /**
     * Sets the record type.
     *
     * @param newRecordType The new record type.
     */
    public void setRecordType(Operations.RecordType newRecordType) {
        recordType = newRecordType;
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
        return this.recordType.equals(o.recordType)
                && this.commandString.equals(o.commandString)
                && this.commandTypeString.equals(o.commandTypeString)
                && this.people.equals(o.people);
    }
}
