package seedu.tassist.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.tassist.commons.core.LogsCenter;
import seedu.tassist.commons.exceptions.DataLoadingException;
import seedu.tassist.commons.util.CsvUtil;
import seedu.tassist.commons.util.FileUtil;
import seedu.tassist.model.AddressBook;
import seedu.tassist.model.ReadOnlyAddressBook;
import seedu.tassist.model.person.Person;

/**
 * A class to save the AddressBook data as a csv file on the hard disk
 */
public class CsvAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public CsvAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path of the data file.
     */
    @Override
    public Path getAddressBookFilePath() {
        return filePath;
    }

    /**
     * Similar to {@link #readAddressBook()}.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        return readAddressBook(filePath);
    }

    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        requireNonNull(filePath);
        logger.info("Reading AddressBook from CSV file: " + filePath);

        try {
            List<Person> persons = CsvUtil.deserializeCsvToPersonList(filePath);
            AddressBook addressBook = new AddressBook();
            for (Person p : persons) {
                addressBook.addPerson(p);
            }
            return Optional.of(addressBook);
        } catch (Exception e) {
            throw new DataLoadingException(e);
        }
    }

    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)}.
     *
     * @param addressBook the current addressbook to be stored.
     * @param filePath location to store the csv data. Cannot be null.
     */
    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        CsvUtil.serializeObjectToCsvFile(filePath, addressBook.getPersonList());
    }
}
