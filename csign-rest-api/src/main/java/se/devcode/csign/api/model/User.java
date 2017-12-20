package se.devcode.csign.api.model;

public class User {
    private String socialSecurityNumber;
    private String firstName;
    private String lastName;
    private String languageTag;

    public User(String socialSecurityNumber,
                String firstName,
                String lastName,
                String languageTag) {
        this.socialSecurityNumber = socialSecurityNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.languageTag = languageTag;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getLanguageTag() {
        return languageTag;
    }
}
