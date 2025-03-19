package seedu.tassist.model;

import javafx.collections.ObservableList;
import seedu.tassist.model.person.Person;

import java.util.ArrayList;

public class Snapshot {
    String commandString;
    Operations.CommandType commandType;
    Person person;

    //public final ReadOnlyAddressBook addressBook;

    public Snapshot(String commandString, Operations.CommandType commandType) {
        this.commandString = commandString;
        this.commandType = commandType;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

//    public void setAddressBook(ReadOnlyAddressBook addressBook) {
//        this.addressBook = addressBook;
//    }

//    public void extractPerson() {
//        System.out.println("cannot extract");
//        System.out.println(getIndex());
//
//        this.person = addressBook.getPersonList().get(getIndex());
//        System.out.println(person.getLabScoreList());
//    }

    public int getIndex(){
        int start = commandString.indexOf("-i");

        //not found
        if (start < 0) return -1;

        //found
        String commandIndex = commandString.substring(start + 3);
        start = commandIndex.indexOf(" ");
        commandIndex = commandIndex.substring(0, start);
        return Integer.parseInt(commandIndex) - 1;
    }
}
