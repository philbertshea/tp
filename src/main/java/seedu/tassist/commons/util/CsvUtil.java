package seedu.tassist.commons.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.tassist.commons.core.LogsCenter;
import seedu.tassist.model.person.AttendanceList;
import seedu.tassist.model.person.Email;
import seedu.tassist.model.person.Faculty;
import seedu.tassist.model.person.LabGroup;
import seedu.tassist.model.person.LabScoreList;
import seedu.tassist.model.person.MatNum;
import seedu.tassist.model.person.Name;
import seedu.tassist.model.person.Person;
import seedu.tassist.model.person.Phone;
import seedu.tassist.model.person.Remark;
import seedu.tassist.model.person.TeleHandle;
import seedu.tassist.model.person.TutGroup;
import seedu.tassist.model.person.Year;
import seedu.tassist.model.tag.Tag;

/**
 * Converts a Java object instance to CSV.
 */
public class CsvUtil {
    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    public static <T> void serializeObjectToCsvFile(Path csvFile, List<T> objects)
            throws IOException {
        FileUtil.writeToFile(csvFile, toCsvString(objects));
    }

    /**
     * Reads a CSV file from the given file path and converts each row into a {@code Person} object.
     * Assumes the first row is the header, and each subsequent row contains person data in a fixed order.
     *
     * @param filePath The path to the CSV file.
     * @return A list of {@code Person} objects parsed from the CSV file.
     * @throws IOException If an I/O error occurs reading the file.
     */
    public static List<Person> deserializeCsvToPersonList(Path filePath) throws IOException {
        List<String> lines = Files.readAllLines(filePath);
        List<Person> personList = new ArrayList<>();

        if (lines.isEmpty()) {
            return personList;
        }

        String header = lines.get(0);
        int expectedFields = 13;

        for (int i = 1; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",", -1);
            if (parts.length < expectedFields) {
                continue;
            }

            Name name = new Name(parts[0]);
            Phone phone = new Phone(parts[1]);
            TeleHandle teleHandle = new TeleHandle(parts[2]);
            Email email = new Email(parts[3]);
            MatNum matNum = new MatNum(parts[4]);
            TutGroup tutGroup = new TutGroup(parts[5]);
            LabGroup labGroup = new LabGroup(parts[6]);
            Faculty faculty = new Faculty(parts[7]);
            Year year = new Year(parts[8]);
            Remark remark = new Remark(parts[9]);
            AttendanceList attendanceList = AttendanceList.generateAttendanceList(parts[10]);
            LabScoreList labScoreList = LabScoreList.loadLabScores(parts[11]);

            List<Tag> tags = new ArrayList<>();
            String tagString = parts[12].trim();
            if (!tagString.isEmpty() && tagString.startsWith("[[") && tagString.endsWith("]]")) {
                String content = tagString.substring(2, tagString.length() - 2);
                String[] tagItems = content.split("\\],\\[");
                for (String t : tagItems) {
                    tags.add(new Tag(t.trim()));
                }
            }

            personList.add(new Person(name, phone, teleHandle, email,
                    matNum, tutGroup, labGroup, faculty, year, remark,
                    attendanceList, labScoreList, new java.util.HashSet<>(tags)));
        }

        return personList;
    }

    /**
     * Converts a given list of objects from a class into its CSV data string representation.
     * @param objects The list of objects to be converted into the CSV string
     * @param <T> The generic type to create an instance of
     * @return CSV data representation of the given list of class objects, in string
     */
    public static <T> String toCsvString(List<T> objects) {
        if (objects == null || objects.isEmpty()) {
            throw new IllegalArgumentException("Object list is empty.");
        }

        // Get class type from the first object
        Class<?> clazz = objects.get(0).getClass();

        // Get CSV header
        String header = getHeader(clazz);

        // Get CSV rows
        List<String> rows = objects.stream()
                .map(CsvUtil::getRow)
                .collect(Collectors.toList());

        // Combine header and rows
        return header + "\n" + String.join("\n", rows);
    }

    /**
     * Retrives the name of the fields in the class and formats them into the CSV header.
     * @param clazz The class object to get the header from
     * @return A string representation of the headers
     */
    private static String getHeader(Class<?> clazz) {
        return List.of(clazz.getDeclaredFields())
                .stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));
    }

    /**
     * Converts a given instance of an object into is CSV data string representation.
     * @param obj The T object to be converted into the CSV string
     * @param <T> The generic type to create an instance of
     * @return CSV data representation of the given class instance, in string
     */
    private static <T> String getRow(T obj) {
        try {
            return List.of(obj.getClass().getDeclaredFields())
                    .stream()
                    .map(field -> {
                        field.setAccessible(true); // Allow access to private fields
                        try {
                            return escapeCsv(field.get(obj));
                        } catch (IllegalAccessException e) {
                            return "";
                        }
                    })
                    .collect(Collectors.joining(","));
        } catch (Exception e) {
            throw new RuntimeException("Error processing object fields", e);
        }
    }

    /**
     * Escapes a given value for safe inclusion in a CSV file.
     * <p>
     * This method converts an object to its string representation and ensures that
     * special characters are properly escaped according to CSV format rules.
     * </p>
     * <p>
     * If the string contains a comma (`,`), double quote (`"`), or newline (`\n`),
     * it is enclosed in double quotes (`"`). Any existing double quotes within the
     * string are escaped by doubling them (`""`).
     * </p>
     * @param value The object to be converted to a CSV-safe string. Can be {@code null}.
     * @return A properly escaped CSV string. Returns an empty string if input is {@code null}.
     */
    private static String escapeCsv(Object value) {
        if (value == null) {
            return "";
        }
        String str = value.toString();
        if (str.contains(",") || str.contains("\"") || str.contains("\n")) {
            str = "\"" + str.replace("\"", "\"\"") + "\""; // Escape double quotes
        }
        return str;
    }
}
