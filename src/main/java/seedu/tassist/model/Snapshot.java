package seedu.tassist.model;

import seedu.tassist.commons.core.index.Index;
import seedu.tassist.logic.parser.exceptions.ParseException;
import seedu.tassist.model.person.Person;

import java.util.ArrayList;
import java.util.List;

import static seedu.tassist.logic.parser.ParserUtil.parseMultipleIndexes;

public class Snapshot {
    String commandString;
    Operations.CommandType commandType;
    ArrayList<Person> people = new ArrayList<>();

    Integer index = null;

    //public final ReadOnlyAddressBook addressBook;

    public Snapshot(String commandString, Operations.CommandType commandType) {
        this.commandString = commandString;
        this.commandType = commandType;
    }

    public void setPerson(Person... people) {
        for (Person person : people) {
            this.people.add(person);
        }
    }

    public Person getPerson() {
        if (people.isEmpty()) {
            return null;
        }
        return people.get(0);
    }

    public ArrayList<Person> getPeople() {
        return people;
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
        if (index != null) {
            return index;
        }
        System.out.println(commandString);

        int start = commandString.indexOf("-i");

        //not found
        if (start < 0) return -1;

        //found
        String commandIndex = commandString.substring(start + 3);
        List<Index> allIndex = null;
        try {
            allIndex = parseMultipleIndexes(commandIndex);
        } catch (ParseException e) {
            return -1;
        }
        index = allIndex.get(0).getZeroBased();
        return allIndex.get(0).getZeroBased();
//        start = commandIndex.indexOf(" ");
//        if (start != -1) {
//            commandIndex = commandIndex.substring(0, start);
//        }
        //return Integer.parseInt(commandIndex) - 1;
    }
}
