package se.devcode.csign.demo.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Document
public class Person {

    @Id
    private String id;

    private String dob;
    private String boardMember;
    private String coAddress;
    private String consumerName;
    private String culture;
    private String firstName;
    private String internalReference;
    private String middleName;
    private String nameCode;
    private String nationalRegistrationAddressCoAddress;
    private String nationalRegistrationAddressCode;
    private String nationalRegistrationAddressPostCode;
    private String nationalRegistrationAddressPostalAddressLine1;
    private String nationalRegistrationAddressPostalAddressLine2Cont;
    private String nationalRegistrationAddressTown;
    private String postCode;
    private String postalAddressLine1;
    private String postalAddressLine2;
    private String realEstateDataAvailableSeparately;
    private String separateAddressCoAddress;
    private String separateAddressCode;
    private String separateAddressForeignCountry;
    private String separateAddressForeignCountryCode;
    private String separateAddressForeignPostCode;
    private String separateAddressForeignTown;
    private String separateAddressNationalPostCode;
    private String separateAddressNationalTown;
    private String separateAddressPostalAddressLine1;
    private String separateAddressPostalAddressLine2Cont;
    private String socialSecurityNumber;
    private String soleProprietor;
    private String sourceOfDatabase;
    private String status;
    private String surname;
    private String taxationYearCCYY;
    private String town;

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setBoardMember(String boardMember) {
        this.boardMember = boardMember;
    }

    public void setCoAddress(String coAddress) {
        this.coAddress = coAddress;
    }

    public void setConsumerName(String consumerName) {
        this.consumerName = consumerName;
    }

    public void setCulture(String culture) {
        this.culture = culture;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setInternalReference(String internalReference) {
        this.internalReference = internalReference;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public void setNameCode(String nameCode) {
        this.nameCode = nameCode;
    }

    public void setNationalRegistrationAddressCoAddress(String nationalRegistrationAddressCoAddress) {
        this.nationalRegistrationAddressCoAddress = nationalRegistrationAddressCoAddress;
    }

    public void setNationalRegistrationAddressCode(String nationalRegistrationAddressCode) {
        this.nationalRegistrationAddressCode = nationalRegistrationAddressCode;
    }

    public void setNationalRegistrationAddressPostCode(String nationalRegistrationAddressPostCode) {
        this.nationalRegistrationAddressPostCode = nationalRegistrationAddressPostCode;
    }

    public void setNationalRegistrationAddressPostalAddressLine1(String nationalRegistrationAddressPostalAddressLine1) {
        this.nationalRegistrationAddressPostalAddressLine1 = nationalRegistrationAddressPostalAddressLine1;
    }

    public void setNationalRegistrationAddressPostalAddressLine2Cont(String nationalRegistrationAddressPostalAddressLine2Cont) {
        this.nationalRegistrationAddressPostalAddressLine2Cont = nationalRegistrationAddressPostalAddressLine2Cont;
    }

    public void setNationalRegistrationAddressTown(String nationalRegistrationAddressTown) {
        this.nationalRegistrationAddressTown = nationalRegistrationAddressTown;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public void setPostalAddressLine1(String postalAddressLine1) {
        this.postalAddressLine1 = postalAddressLine1;
    }

    public void setPostalAddressLine2(String postalAddressLine2) {
        this.postalAddressLine2 = postalAddressLine2;
    }

    public void setRealEstateDataAvailableSeparately(String realEstateDataAvailableSeparately) {
        this.realEstateDataAvailableSeparately = realEstateDataAvailableSeparately;
    }

    public void setSeparateAddressCoAddress(String separateAddressCoAddress) {
        this.separateAddressCoAddress = separateAddressCoAddress;
    }

    public void setSeparateAddressCode(String separateAddressCode) {
        this.separateAddressCode = separateAddressCode;
    }

    public void setSeparateAddressForeignCountry(String separateAddressForeignCountry) {
        this.separateAddressForeignCountry = separateAddressForeignCountry;
    }

    public void setSeparateAddressForeignCountryCode(String separateAddressForeignCountryCode) {
        this.separateAddressForeignCountryCode = separateAddressForeignCountryCode;
    }

    public void setSeparateAddressForeignPostCode(String separateAddressForeignPostCode) {
        this.separateAddressForeignPostCode = separateAddressForeignPostCode;
    }

    public void setSeparateAddressForeignTown(String separateAddressForeignTown) {
        this.separateAddressForeignTown = separateAddressForeignTown;
    }

    public void setSeparateAddressNationalPostCode(String separateAddressNationalPostCode) {
        this.separateAddressNationalPostCode = separateAddressNationalPostCode;
    }

    public void setSeparateAddressNationalTown(String separateAddressNationalTown) {
        this.separateAddressNationalTown = separateAddressNationalTown;
    }

    public void setSeparateAddressPostalAddressLine1(String separateAddressPostalAddressLine1) {
        this.separateAddressPostalAddressLine1 = separateAddressPostalAddressLine1;
    }

    public void setSeparateAddressPostalAddressLine2Cont(String separateAddressPostalAddressLine2Cont) {
        this.separateAddressPostalAddressLine2Cont = separateAddressPostalAddressLine2Cont;
    }

    public void setSocialSecurityNumber(String socialSecurityNumber) {
        this.socialSecurityNumber = socialSecurityNumber;
    }

    public void setSoleProprietor(String soleProprietor) {
        this.soleProprietor = soleProprietor;
    }

    public void setSourceOfDatabase(String sourceOfDatabase) {
        this.sourceOfDatabase = sourceOfDatabase;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public void setTaxationYearCCYY(String taxationYearCCYY) {
        this.taxationYearCCYY = taxationYearCCYY;
    }

    public void setTown(String town) {
        this.town = town;
    }

    public String getDob() {
        return dob;
    }

    public String getBoardMember() {
        return boardMember;
    }

    public String getCoAddress() {
        return coAddress;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public String getCulture() {
        return culture;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getInternalReference() {
        return internalReference;
    }

    public String getMiddleName() {
        return middleName;
    }

    public String getNameCode() {
        return nameCode;
    }

    public String getNationalRegistrationAddressCoAddress() {
        return nationalRegistrationAddressCoAddress;
    }

    public String getNationalRegistrationAddressCode() {
        return nationalRegistrationAddressCode;
    }

    public String getNationalRegistrationAddressPostCode() {
        return nationalRegistrationAddressPostCode;
    }

    public String getNationalRegistrationAddressPostalAddressLine1() {
        return nationalRegistrationAddressPostalAddressLine1;
    }

    public String getNationalRegistrationAddressPostalAddressLine2Cont() {
        return nationalRegistrationAddressPostalAddressLine2Cont;
    }

    public String getNationalRegistrationAddressTown() {
        return nationalRegistrationAddressTown;
    }

    public String getPostCode() {
        return postCode;
    }

    public String getPostalAddressLine1() {
        return postalAddressLine1;
    }

    public String getPostalAddressLine2() {
        return postalAddressLine2;
    }

    public String getRealEstateDataAvailableSeparately() {
        return realEstateDataAvailableSeparately;
    }

    public String getSeparateAddressCoAddress() {
        return separateAddressCoAddress;
    }

    public String getSeparateAddressCode() {
        return separateAddressCode;
    }

    public String getSeparateAddressForeignCountry() {
        return separateAddressForeignCountry;
    }

    public String getSeparateAddressForeignCountryCode() {
        return separateAddressForeignCountryCode;
    }

    public String getSeparateAddressForeignPostCode() {
        return separateAddressForeignPostCode;
    }

    public String getSeparateAddressForeignTown() {
        return separateAddressForeignTown;
    }

    public String getSeparateAddressNationalPostCode() {
        return separateAddressNationalPostCode;
    }

    public String getSeparateAddressNationalTown() {
        return separateAddressNationalTown;
    }

    public String getSeparateAddressPostalAddressLine1() {
        return separateAddressPostalAddressLine1;
    }

    public String getSeparateAddressPostalAddressLine2Cont() {
        return separateAddressPostalAddressLine2Cont;
    }

    public String getSocialSecurityNumber() {
        return socialSecurityNumber;
    }

    public String getSoleProprietor() {
        return soleProprietor;
    }

    public String getSourceOfDatabase() {
        return sourceOfDatabase;
    }

    public String getStatus() {
        return status;
    }

    public String getSurname() {
        return surname;
    }

    public String getTaxationYearCCYY() {
        return taxationYearCCYY;
    }

    public String getTown() {
        return town;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
