package seedu.tassist.logic.commands;

import static seedu.tassist.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

import seedu.tassist.logic.Messages;
import seedu.tassist.logic.commands.exceptions.CommandException;
import seedu.tassist.model.Model;
import seedu.tassist.storage.JsonAddressBookStorage;
import seedu.tassist.storage.StorageManager;

public class ExportDataCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the data in TAssist to a JSON or CSV file.\n"
            + "Parameters:"
            + "-f <file name>"
            + "-e <file extension>\n"
            + "Example: " + COMMAND_WORD + " -f userdata -e csv";

    public static final String MESSAGE_ARGUMENTS = "FileName: %1$s, Extension: %2$s";
    public static final String MESSAGE_SUCCESS = "Exported data to file: %1$s";
    public static final String INVALID_ARGUMENT_EXTENSION = "Invalid extension: %1$s";
    private static final Set<String> VALID_EXTENSIONS = Set.of("csv", "json");

    private final String fileName;
    private final String extension;

    public ExportDataCommand(String fileName, String extension) {
        requireAllNonNull(fileName, extension);

        this.fileName = fileName;
        this.extension = extension;
    }

    /**
     * Executes the command and returns the result message.
     *
     * @param model {@code Model} which the command should operate on.
     * @return feedback message of the operation result for display
     * @throws CommandException If an error occurs during command execution.
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        if(!VALID_EXTENSIONS.contains(extension)) {
            throw new CommandException(String.format(INVALID_ARGUMENT_EXTENSION, extension));
        }
        //TODO: Validate Filename

        if(extension.equals("json")) {
            Path p = Paths.get("data" , fileName + ".json");
            JsonAddressBookStorage jsonAddressBookStorage = new JsonAddressBookStorage(p);
            try {
                jsonAddressBookStorage.saveAddressBook(model.getAddressBook());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return new CommandResult(String.format(MESSAGE_SUCCESS, fileName + "." + extension));
        }
        else {
            throw new CommandException(String.format(MESSAGE_ARGUMENTS, fileName, extension));
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
}
