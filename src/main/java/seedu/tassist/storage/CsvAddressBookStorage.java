package seedu.tassist.storage;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;
import java.util.logging.Logger;

import seedu.tassist.commons.core.LogsCenter;
import seedu.tassist.commons.exceptions.DataLoadingException;
import seedu.tassist.commons.util.CsvUtil;
import seedu.tassist.commons.util.FileUtil;
import seedu.tassist.model.ReadOnlyAddressBook;

/**
 * A class to save the AddressBook data as a CSV file on the hard disk.
 */
public class CsvAddressBookStorage implements AddressBookStorage {

    private static final Logger logger = LogsCenter.getLogger(JsonAddressBookStorage.class);

    private Path filePath;

    public CsvAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Returns the file path of the data file.
     *
     * @return The file path where the AddressBook is stored.
     */
    @Override
    public Path getAddressBookFilePath() {
        return filePath;
    }

    /**
     * Similar to {@link #readAddressBook(Path)}.
     *
     * @return An {@code Optional} containing the AddressBook data if available.
     * @throws DataLoadingException if loading the data from storage fails.
     */
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        // TODO: Future implementation goes here:
        return Optional.empty();
    }

    /**
     * Reads the AddressBook data from the specified file path.
     *
     * @param filePath The path of the CSV file to read from.
     * @return An {@code Optional} containing the AddressBook data if available.
     * @throws DataLoadingException if loading the data from storage fails.
     */
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath)
            throws DataLoadingException {
        // TODO: Future implementation goes here:
        return Optional.empty();
    }

    /**
     * Saves the AddressBook data to the default file path.
     *
     * @param addressBook The current AddressBook to be stored.
     * @throws IOException If an I/O error occurs while writing the data.
     */
    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * Similar to {@link #saveAddressBook(ReadOnlyAddressBook)} but saves to a specified file path.
     *
     * @param addressBook The current AddressBook to be stored.
     * @param filePath    The location to store the CSV data. Cannot be null.
     * @throws IOException If an I/O error occurs while writing the data.
     */
    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        CsvUtil.serializeObjectToCsvFile(filePath, addressBook.getPersonList());
    }
}
