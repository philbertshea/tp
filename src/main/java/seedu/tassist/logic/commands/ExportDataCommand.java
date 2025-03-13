package seedu.tassist.logic.commands;

import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_EXTENSION;
import static seedu.tassist.logic.parser.CliSyntax.PREFIX_FILENAME;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.model.ReadOnlyAddressBook;
import seedu.tassist.storage.CsvAddressBookStorage;
import seedu.tassist.storage.JsonAddressBookStorage;

/**
 * Allows a user to export stored in the address book into CSV or JSON format.
 */
public class ExportDataCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the data in TAssist to a JSON or CSV file.\n"
            + "Parameters: "
            + PREFIX_FILENAME + "FILE_NAME "
            + PREFIX_EXTENSION + "FILE_EXTENSION\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_FILENAME + "userdata " + PREFIX_EXTENSION + " csv";

    public static final String MESSAGE_SUCCESS = "Exported data to file: %1$s";
    public static final String INVALID_ARGUMENT_EXTENSION = "Invalid extension: %1$s";
    private static final Set<String> VALID_EXTENSIONS = Set.of("csv", "json");

    private final String fileName;
    private final String extension;

    /**
     * Instantiates the ExportDataCommand instance, with the provided
     * fileName and extension.
     *
     * @param fileName index of person to be marked attendance for.
     * @param extension week to mark attendance of person for.
     */
    public ExportDataCommand(String fileName, String extension) {
        requireAllNonNull(fileName, extension);

        this.fileName = fileName;
        this.extension = extension;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        validateExtension(extension);
        validateFileName(fileName);

        Path filePath = Paths.get("data", fileName + "." + extension);

        try {
            saveAddressBook(model.getAddressBook(), filePath, extension);
            return new CommandResult(String.format(MESSAGE_SUCCESS, fileName + "." + extension));
        } catch (IOException e) {
            throw new CommandException("Failed to save file: " + e.getMessage(), e);
        }
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportDataCommand)) {
            return false;
        }

        ExportDataCommand e = (ExportDataCommand) other;
        return fileName.equals(e.fileName)
                && extension.equals(e.extension);
    }

    /**
     * Validates if the provided extension is supported.
     */
    private void validateExtension(String extension) throws CommandException {
        if (!VALID_EXTENSIONS.contains(extension)) {
            throw new CommandException(String.format(INVALID_ARGUMENT_EXTENSION, extension));
        }
    }

    /**
     * Validates if the provided filename is allowed.
     */
    private void validateFileName(String fileName) throws CommandException {
        if (!fileName.matches("^[a-zA-Z0-9-_]+$")) { // Simple alphanumeric with dashes/underscores
            throw new CommandException(String.format("Invalid filename: %s\n"
                    + "File name should only contain alphanumeric characters, dashes, or underscores.", fileName));
        }
    }

    /**
     * Saves the AddressBook to the specified file path and format based on the file extension.
     */
    private void saveAddressBook(ReadOnlyAddressBook addressBook, Path filePath, String extension) throws IOException {
        switch (extension) {
        case "json":
            new JsonAddressBookStorage(filePath).saveAddressBook(addressBook);
            break;
        case "csv":
            new CsvAddressBookStorage(filePath).saveAddressBook(addressBook);
            break;
        default:
            throw new IllegalArgumentException("Unsupported file extension: " + extension);
        }
    }
}
