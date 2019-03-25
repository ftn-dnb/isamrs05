package ftn.dnb.dnbtravel.model;

import java.util.UUID;

public class User {

    private UUID id;
    private String firstName;
    private String lastName;

    public User(UUID id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }
}
