package ru.shmvsky;

import ru.shmvsky.core.CSVWriter;
import ru.shmvsky.example.Person;

import java.util.*;

public class Main {
    public static void main(String[] args) throws Exception {

        Map<String, Object> settings = new HashMap<>();
        settings.put("delimiter", ',');
        settings.put("quoteChar", '"');
        settings.put("escapeChar", '\\');
        settings.put("lineSeparator", "\n");
        settings.put("writeHeader", true);
        settings.put("nullValue", "");
        settings.put("charset", "UTF-8");
        settings.put("dateFormat", "yyyy-MM-dd");
        settings.put("decimalSeparator", '.');
        settings.put("skipLines", 0);

        // Create CSVWriter instance with settings
        CSVWriter<Person> personWriter = new CSVWriter<>(settings);

        // Create a list of Person objects
        List<Person> list = new ArrayList<>();
        list.add(new Person("John Doe", 30, "john.doe@example.com"));
        list.add(new Person("Jane Smith", 25, "jane.smith@example.com"));
        list.add(new Person("Alice Johnson", 28, "alice.johnson@example.com"));

        personWriter.write(list, "out.csv");

    }
}