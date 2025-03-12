package seedu.tassist.commons.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.tassist.commons.core.LogsCenter;

/**
 * Converts a Java object instance to CSV
 */
public class CsvUtil {
    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    public static <T> void serializeObjectToCsvFile(Path csvFile, List<T> objects) throws IOException {
        FileUtil.writeToFile(csvFile, toCsvString(objects));
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
     * Retrives the name of the fields in the class and formats them into the CSV header
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
     * Converts a given instance of an object into is CSV data string representation
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
     * @return A properly escaped CSV string. If the input is {@code null}, an empty string is returned.
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
