package seedu.address.storage;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.address.commons.core.LogsCenter;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.tag.Tag;

/**
 * Handles CSV file storage for AddressBook.
 */
public class CsvAddressBookStorage {
    private static final Logger logger = LogsCenter.getLogger(CsvAddressBookStorage.class);
    private static final String HEADER = "Name,Tags,Phone,Address,Email";
    private final Path filePath;

    public CsvAddressBookStorage(Path filePath) {
        this.filePath = filePath;
    }

    /**
     * Loads AddressBook from a CSV file.
     */
    public AddressBook readAddressBook() throws IOException {
        AddressBook addressBook = new AddressBook();

        if (!Files.exists(filePath)) {
            logger.warning("CSV file does not exist: " + filePath);
            return addressBook;
        }

        try (BufferedReader reader = Files.newBufferedReader(filePath)) {
            String line;
            boolean isFirstLine = true;
            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false;
                    continue;
                }
                String[] data = line.split(",");
                if (data.length < 5) {
                    logger.warning("Skipping malformed CSV line: " + line);
                    continue;
                }

                String name = data[0].trim();
                Set<String> tags = new HashSet<>(Arrays.asList(data[1].split("\\s+")));
                String phone = data[2].trim();
                String address = data[3].trim();
                String email = data[4].trim();

                Name personName = new Name(name);
                Phone personPhone = new Phone(phone);
                Email personEmail = new Email(email);
                Address personAddress = new Address(address);
                Set<Tag> personTags = tags.stream().map(Tag::new).collect(Collectors.toSet());

                Person person = new Person(personName, personPhone, personEmail, personAddress, personTags);
                addressBook.addPerson(person);

                logger.info("Loaded person from CSV: " + person);
            }
        }
        return addressBook;
    }

    /**
     * Saves AddressBook to a CSV file.
     */
    public void saveAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        logger.info("Saving AddressBook to CSV: " + filePath);

        try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
            writer.write(HEADER);
            writer.newLine();

            for (Person person : addressBook.getPersonList()) {
                String tagString = person.getTags().stream()
                        .map(Tag::toString)
                        .collect(Collectors.joining(" "));

                String line = String.join(",", 
                        person.getName().fullName,
                        tagString,
                        person.getPhone().value,
                        person.getAddress().value,
                        person.getEmail().value);

                writer.write(line);
                writer.newLine();
                logger.info("Saved person to CSV: " + line);
            }
        }
    }
}
