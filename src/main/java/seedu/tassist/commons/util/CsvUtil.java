package seedu.tassist.commons.util;

import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import seedu.tassist.commons.core.LogsCenter;

public class CsvUtil {
    private static final Logger logger = LogsCenter.getLogger(CsvUtil.class);

    public static <T> void serializeObjectToCsvFile(Path csvFile, List<T> objects) throws IOException {
        FileUtil.writeToFile(csvFile, toCsvString(objects));
    }

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

    private static String getHeader(Class<?> clazz) {
        return List.of(clazz.getDeclaredFields())
                .stream()
                .map(Field::getName)
                .collect(Collectors.joining(","));
    }

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

    private static String escapeCsv(Object value) {
        if (value == null) return "";
        String str = value.toString();
        if (str.contains(",") || str.contains("\"") || str.contains("\n")) {
            str = "\"" + str.replace("\"", "\"\"") + "\""; // Escape double quotes
        }
        return str;
    }
}
