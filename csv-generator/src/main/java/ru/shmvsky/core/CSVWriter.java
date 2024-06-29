package ru.shmvsky.core;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.shmvsky.annotation.Attribute;
import ru.shmvsky.annotation.Csv;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CSVWriter <T> {

    private final Logger log = LogManager.getLogger(CSVWriter.class);

    private final char delimiter;
    private final String lineSeparator;
    private final String nullValue;
    private final int skipLines;
    private final boolean writeHeader;

    public CSVWriter(Map<String, Object> settings) {
        this.delimiter = settings.containsKey("delimiter") ? (char) settings.get("delimiter") : ',';
        this.lineSeparator = settings.containsKey("lineSeparator") ? (String) settings.get("lineSeparator") : "\n";
        this.nullValue = settings.containsKey("nullValue") ? (String) settings.get("nullValue") : "";
        this.skipLines = settings.containsKey("skipLines") ? (int) settings.get("skipLines") : 0;
        this.writeHeader = !settings.containsKey("writeHeader") || (boolean) settings.get("writeHeader");
    }

    public void write(List<T> list, String filePath) {

        if (list.isEmpty()) {
            return;
        }

        Class<?> clazz = list.get(0).getClass();
        if (!clazz.isAnnotationPresent(Csv.class)) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " is not annotated with @Csv");
        }

        try (Writer writer = new FileWriter(filePath)) {
            for (int i = 0; i < skipLines; i++) {
                writer.write(lineSeparator);
            }

            if (writeHeader) {
                writeHeader(writer, clazz);
            }

            for (Object obj : list) {
                writeLine(writer, obj);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void writeHeader(Writer writer, Class<?> clazz) throws IOException {
        Field[] fields = clazz.getDeclaredFields();
        List<String> headers = new ArrayList<>();
        for (Field field : fields) {
            if (field.isAnnotationPresent(Attribute.class)) {
                Attribute attribute = field.getAnnotation(Attribute.class);
                if (!attribute.ignore()) {
                    String name = attribute.name().isEmpty() ? field.getName() : attribute.name();
                    headers.add(name);
                }
            } else {
                headers.add(field.getName());
            }
        }
        writer.write(String.join(String.valueOf(delimiter), headers) + lineSeparator);
    }

    private void writeLine(Writer writer, Object obj) throws IOException {
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<String> values = new ArrayList<>();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                if (field.isAnnotationPresent(Attribute.class)) {
                    Attribute attribute = field.getAnnotation(Attribute.class);
                    if (attribute.ignore()) {
                        continue;
                    }
                }
                Object value = field.get(obj);
                values.add(value != null ? value.toString() : nullValue);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage());
            }
        }
        writer.write(String.join(String.valueOf(delimiter), values) + lineSeparator);
    }

}
