package person.model;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class Person {

    private final int personId;

    private String firstName;

    private String lastName;

    private LocalDate dateOfBirth;

    public Person(int personId, String firstName, String lastName, LocalDate dateOfBirth) {
        this.personId = personId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

}
