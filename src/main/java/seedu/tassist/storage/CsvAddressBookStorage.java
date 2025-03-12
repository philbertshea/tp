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
     * Returns AddressBook data as a {@link ReadOnlyAddressBook}.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if loading the data from storage failed.
     */
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook() throws DataLoadingException {
        //TODO: Future implementation goes here:
        return Optional.empty();
    }

    /**
     * @param filePath
     * @see #getAddressBookFilePath()
     */
    @Override
    public Optional<ReadOnlyAddressBook> readAddressBook(Path filePath) throws DataLoadingException {
        //TODO: Future implementation goes here:
        return Optional.empty();
    }

    /**
     * Saves the given {@link ReadOnlyAddressBook} to the storage.
     *
     * @param addressBook cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, filePath);
    }

    /**
     * @param addressBook
     * @param filePath
     * @see #saveAddressBook(ReadOnlyAddressBook)
     */
    @Override
    public void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath) throws IOException {
        requireNonNull(addressBook);
        requireNonNull(filePath);

        FileUtil.createIfMissing(filePath);
        CsvUtil.serializeObjectToCsvFile(filePath, addressBook.getPersonList());
    }
}
