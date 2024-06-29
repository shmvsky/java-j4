import ru.shmvsky.annotation.Attribute;
import ru.shmvsky.annotation.Csv;

@Csv
class TestClass {

    @Attribute(name = "first_name")
    private String firstName;

    @Attribute(name = "last_name")
    private String lastName;

    public TestClass(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

}
