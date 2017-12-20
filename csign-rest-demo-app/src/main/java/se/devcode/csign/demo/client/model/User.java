package se.devcode.csign.demo.client.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {
    private String socialSecurityNumber;
    private String firstName;
    private String lastName;
    private String languageTag;

    @JsonCreator
    public User(@JsonProperty("socialSecurityNumber") String socialSecurityNumber,
                @JsonProperty("firstName") String firstName,
                @JsonProperty("lastName") String lastName,
                @JsonProperty("languageTag") String languageTag) {
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
