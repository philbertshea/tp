package seedu.tassist.storage;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Optional;

import seedu.tassist.commons.exceptions.DataLoadingException;
import seedu.tassist.model.ReadOnlyUserPrefs;
import seedu.tassist.model.UserPrefs;

/**
 * Represents a storage for {@link seedu.tassist.model.UserPrefs}.
 */
public interface UserPrefsStorage {

    /**
     * Returns the file path of the UserPrefs data file.
     */
    Path getUserPrefsFilePath();

    /**
     * Returns UserPrefs data from storage.
     * Returns {@code Optional.empty()} if storage file is not found.
     *
     * @throws DataLoadingException if the loading of data from preference file failed.
     */
    Optional<UserPrefs> readUserPrefs() throws DataLoadingException;

    /**
     * Saves the given {@link seedu.tassist.model.ReadOnlyUserPrefs} to the storage.
     * @param userPrefs cannot be null.
     * @throws IOException if there was any problem writing to the file.
     */
    void saveUserPrefs(ReadOnlyUserPrefs userPrefs) throws IOException;

}
