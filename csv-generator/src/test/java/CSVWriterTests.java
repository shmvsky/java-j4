import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.shmvsky.core.CSVWriter;
import ru.shmvsky.example.Person;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class CSVWriterTests {

    private CSVWriter<Person> personWriter;
    private Path tempFile;

    @BeforeEach
    public void setUp() throws IOException {
        Map<String, Object> settings = new HashMap<>();
        settings.put("delimiter", ',');
        settings.put("lineSeparator", "\n");
        settings.put("nullValue", "");
        settings.put("skipLines", 2);
        settings.put("writeHeader", true);

        personWriter = new CSVWriter<>(settings);

        tempFile = Files.createTempFile("test", ".csv");
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(tempFile);
    }

    @Test
    public void testWriteWithEmptyList() throws IOException {

        List<Person> emptyList = Collections.emptyList();

        personWriter.write(emptyList, tempFile.toString());

        List<String> lines = Files.readAllLines(tempFile);
        Assertions.assertTrue(lines.isEmpty());
    }

    @Test
    public void testWriteWithNonEmptyList() throws IOException {
        List<Person> list = Arrays.asList(
                new Person("John Doe", 30, "john.doe@example.com"),
                new Person("Jane Smith", 25, "jane.smith@example.com")
        );

        personWriter.write(list, tempFile.toString());

        List<String> lines = Files.readAllLines(tempFile);
        Assertions.assertEquals(5, lines.size());
        Assertions.assertEquals("", lines.get(0));
        Assertions.assertEquals("", lines.get(1));
        Assertions.assertEquals("name,email", lines.get(2));
        Assertions.assertEquals("John Doe,john.doe@example.com", lines.get(3));
        Assertions.assertEquals("Jane Smith,jane.smith@example.com", lines.get(4));
    }

    @Test
    public void testWriteWithNameAttribute() throws IOException {

        List<TestClass> list = Collections.singletonList(new TestClass("John", "Doe"));

        CSVWriter<TestClass> testWriter = new CSVWriter<>(new HashMap<>());

        testWriter.write(list, tempFile.toString());

        List<String> lines = Files.readAllLines(tempFile);

        Assertions.assertEquals(2, lines.size());
        Assertions.assertEquals("first_name,last_name", lines.get(0));
        Assertions.assertEquals("John,Doe", lines.get(1));
    }

}
