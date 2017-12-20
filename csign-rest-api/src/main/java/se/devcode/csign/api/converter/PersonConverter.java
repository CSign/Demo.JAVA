package se.devcode.csign.api.converter;

import org.springframework.stereotype.Component;
import se.csign.integration.data.v1.Consumer;
import se.devcode.csign.api.model.Person;

@Component
public class PersonConverter implements Converter<Consumer, Person> {

    @Override
    public Person convert(Consumer from) {
        Person person = new Person();
        person.setBoardMember(from.getBoardMember().getValue());
        person.setCoAddress(from.getCoAdress().getValue());
        person.setConsumerName(from.getConsumerName().getValue());
        person.setCulture(from.getCulture().getValue());
        person.setDob(from.getBirthDateDDMMYY().getValue());
        person.setInternalReference(from.getFirstName().getValue());
        person.setInternalReference(from.getInternalReference().getValue());
        person.setMiddleName(from.getMiddleName().getValue());
        person.setNameCode(from.getNameCode().getValue());
        person.setNationalRegistrationAddressCoAddress(from.getNationalRegistrationAddressCoAddress().getValue());
        person.setNationalRegistrationAddressCode(from.getNationalRegistrationAddressCode().getValue());
        person.setNationalRegistrationAddressPostalAddressLine1(from.getNationalRegistrationAddressPostalAddressLine1().getValue());
        person.setNationalRegistrationAddressPostalAddressLine2Cont(from.getNationalRegistrationAddressPostalAddressLine2Cont().getValue());
        person.setNationalRegistrationAddressPostCode(from.getNationalRegistrationAddressPostCode().getValue());
        person.setNationalRegistrationAddressTown(from.getNationalRegistrationAddressTown().getValue());
        person.setPostalAddressLine1(from.getPostalAdressLine1().getValue());
        person.setPostalAddressLine2(from.getPostalAdressLine2().getValue());
        person.setPostCode(from.getPostCode().getValue());
        person.setRealEstateDataAvailableSeparately(from.getRealEstateDataAvailableSeparately().getValue());
        person.setSeparateAddressCoAddress(from.getSeparateAddressCoAddress().getValue());
        person.setSeparateAddressCode(from.getSeparateAddressCode().getValue());
        person.setSeparateAddressForeignCountry(from.getSeparateAddressForeignCountry().getValue());
        person.setSeparateAddressForeignPostCode(from.getSeparateAddressForeignPostCode().getValue());
        person.setSeparateAddressForeignCountryCode(from.getSeparateAddressForeignCountryCode().getValue());
        person.setSeparateAddressForeignTown(from.getSeparateAddressForeignTown().getValue());
        person.setSeparateAddressNationalPostCode(from.getSeparateAddressNationalPostCode().getValue());
        person.setSeparateAddressNationalTown(from.getSeparateAddressNationalTown().getValue());
        person.setSeparateAddressPostalAddressLine1(from.getSeparateAddressPostalAddressLine1().getValue());
        person.setSeparateAddressPostalAddressLine2Cont(from.getSeparateAddressPostalAddressLine2Cont().getValue());
        person.setSocialSecurityNumber(from.getSocialSecurityNumber().getValue());
        person.setSoleProprietor(from.getSoleProprietor().getValue());
        person.setSourceOfDatabase(from.getSourceOfDatabase().getValue());
        person.setFirstName(from.getFirstName().getValue());
        person.setSurname(from.getSurname().getValue());
        person.setStatus(from.getStatus().getValue());
        person.setTaxationYearCCYY(from.getTaxationYearCCYY().getValue());
        person.setTown(from.getTown().getValue());
        return person;
    }
}
