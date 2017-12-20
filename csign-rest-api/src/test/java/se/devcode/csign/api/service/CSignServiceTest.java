package se.devcode.csign.api.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;
import se.csign.integration.data.v1.ArrayOfMessage;
import se.csign.integration.data.v1.ArrayOfMetaData;
import se.csign.integration.data.v1.ArrayOfReceiptAccess;
import se.csign.integration.data.v1.ArrayOfReceiptOwner;
import se.csign.integration.data.v1.ArrayOfRight;
import se.csign.integration.data.v1.ArrayOfSignature;
import se.csign.integration.data.v1.ArrayOfVirtualFile;
import se.csign.integration.data.v1.Consumer;
import se.csign.integration.data.v1.GetReceiptResponse;
import se.csign.integration.data.v1.IdentifiedUser;
import se.csign.integration.data.v1.Individual;
import se.csign.integration.data.v1.LegalEntity;
import se.csign.integration.data.v1.Message;
import se.csign.integration.data.v1.MetaData;
import se.csign.integration.data.v1.ObjectFactory;
import se.csign.integration.data.v1.Receipt;
import se.csign.integration.data.v1.ReceiptAccess;
import se.csign.integration.data.v1.ReceiptOwner;
import se.csign.integration.data.v1.ScenarioLoginCallback;
import se.csign.integration.data.v1.Signature;
import se.csign.integration.data.v1.Transaction;
import se.csign.integration.data.v1.VirtualFile;
import se.csign.integration.service.v1.ConsumerDataGetResponse;
import se.csign.integration.service.v1.GetPDFReceiptResponse;
import se.csign.integration.service.v1.ScenarioLoginCallbackGetResponse;
import se.csign.integration.service.v1.ScenarioLoginResponse;
import se.csign.integration.service.v1.TransactionAddSignatureResponse;
import se.csign.integration.service.v1.TransactionFinalizeResponse;
import se.csign.integration.service.v1.TransactionGetResponse;
import se.csign.integration.service.v1.TransactionStartResponse;
import se.devcode.csign.api.client.CSignClient;
import se.devcode.csign.api.model.ScenarioCode;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.BDDMockito.given;


@RunWith(SpringRunner.class)
public class CSignServiceTest {

    private static final String BIRTH_DAY = "230921";
    private static final int ANY_INDIVIDUAL = ScenarioCode.ANY_INDIVIDUAL.getCode();
    private static final String LOGIN_URL = "http://integration.trunk.test.csign.se/CSign.Services.Integration/" +
            "CSign.Services.Integration.svc";
    private static final String POST_CODE = "11359";
    private static final String TAXATION_YEAR = "2017";
    private static final String BOARD_MEMBER = "N";
    private static final String REAL_ESTATE_AVAILABLE_SEPARATELY = "N";
    private static final String TOWN = "Stockholm";
    private static final String SOLE_PROPRIETOR = "N";
    private static final String POSTAL_ADDRESS_LINE_1 = "Sveavägen 49 Lgh 1401";
    private static final String CULTURE = "SE";
    private static final String NAME_CODE = "20";
    private static final String CONSUMER_NAME = "Kula, Kalle";
    private static final int TRANSACTION_ID = 1;
    private static final int DOCUMENT_SIGNING_ID = 122332;
    private static final int ORGANIZATION_ID = 556572344;
    private static final int ORGANIZATION_UNIT_ID = 556272344;
    private static final int USER_ID = 1111111;
    private static final int MESSAGE_CODE = 200;
    private static final int PROCUREMENT_ID = 11111;
    private static final String OWNER_REGISTRATION_NUMBER = "4566788-7889";
    private static final String TRANSACTION_DESCRIPTION = "Test";
    private static final String TRANSACTION_TITLE = "Test";
    private static final String NAME = "Test";
    private static final String VALUE = "Test";
    private static final String TITLE = "Test doc";
    private static final String FILE_NAME = "Test";
    private static final int FILE_SIZE = 2000;
    private static final String ORIGINAL_LOCATION = "test";
    private static final String SOURCE_HASH = DigestUtils.md5DigestAsHex(ORIGINAL_LOCATION.getBytes());
    private static final String EMAIL = "kalle@kula.se";
    private static final String FIRST_NAME = "Kalle";
    private static final String LAST_NAME = "Kula";
    private static final String SSN = "2109230579";
    private static final String SESSION_ID = UUID.randomUUID().toString();
    private static final byte[] PDF = "transaction.pdf".getBytes();
    private static final String SIGNATURE_URL = "signature.url.se";
    private static final String RETURN_URL = "return.url.se";
    private static final String FILE_PACKAGED_ID = "1311314";
    private static final ZonedDateTime TIME_STAMP = ZonedDateTime.now(ZoneId.systemDefault());
    private static final String DESC = "Test desc";


    @MockBean
    private CSignClient cSignClient;

    private CSignService target;

    @Before
    public void setup() {
        target = new CSignService(cSignClient);
    }

    @Test
    public void prepareLogin_ShouldReturn() {
        final ScenarioLoginResponse scenarioLoginResponse = createScenarioLoginResponse();
        given(cSignClient.prepareLogin(ANY_INDIVIDUAL, SESSION_ID, RETURN_URL)).willReturn(scenarioLoginResponse);
        ScenarioLoginResponse responseHolder = target.prepareLogin(ANY_INDIVIDUAL, SESSION_ID, RETURN_URL);
        assertThat(responseHolder, notNullValue());
        assertThat(responseHolder.getScenarioLoginResult().getValue(), notNullValue());
        assertThat(responseHolder.getScenarioLoginResult().getValue().getSessionId(), is(SESSION_ID));
        assertThat(responseHolder.getScenarioLoginResult().getValue().getLoginUrl(), is(LOGIN_URL));
        assertThat(responseHolder.getScenarioLoginResult().getValue().getMessages(), notNullValue());
        responseHolder.getScenarioLoginResult().getValue().getMessages().getMessage().stream().findFirst().ifPresent(this::assertMessage);
    }

    @Test
    public void getLoginResult_ShouldReturn() throws DatatypeConfigurationException {
        final ScenarioLoginCallbackGetResponse scenarioLoginCallbackGetResponse = createScenarioLoginCallbackResponse();
        given(cSignClient.getLoginResult(SESSION_ID)).willReturn(scenarioLoginCallbackGetResponse);
        ScenarioLoginCallbackGetResponse responseHolder = target.getLoginResult(SESSION_ID);
        assertThat(responseHolder, notNullValue());
        assertThat(responseHolder.getScenarioLoginCallbackGetResult().getValue().getTimeStamp(), is(notNullValue()));
        ZonedDateTime zonedDateTime = responseHolder.getScenarioLoginCallbackGetResult().getValue()
                .getTimeStamp().toGregorianCalendar().toInstant().atZone(ZoneId.systemDefault());
        assertThat(zonedDateTime, is(TIME_STAMP));
        assertThat(responseHolder.getScenarioLoginCallbackGetResult().getValue().getMessage(), notNullValue());
        assertThat(responseHolder.getScenarioLoginCallbackGetResult().getValue().getMessage().getCode(), is(MESSAGE_CODE));
        assertThat(responseHolder.getScenarioLoginCallbackGetResult().getValue().getCallback(), notNullValue());
        assertThat(responseHolder.getScenarioLoginCallbackGetResult().getValue().getCallback().getSessionId(), is(SESSION_ID));
        assertThat(responseHolder.getScenarioLoginCallbackGetResult().getValue().getCallback().getScenario(), is(ANY_INDIVIDUAL));
        assertThat(responseHolder.getScenarioLoginCallbackGetResult().getValue().getCallback().getIdentifiedUser(), notNullValue());
        assertThat(responseHolder.getScenarioLoginCallbackGetResult().getValue().getCallback().getMessages(), notNullValue());
        final IdentifiedUser identifiedUser = responseHolder.getScenarioLoginCallbackGetResult().getValue()
                .getCallback().getIdentifiedUser().getValue();
        assertThat(identifiedUser, notNullValue());
        assertThat(identifiedUser.getIndividual(), notNullValue());
        assertThat(identifiedUser.getIndividual().getCulture().getValue(), is(Locale.getDefault().toLanguageTag()));
        assertThat(identifiedUser.getIndividual().getEmail().getValue(), is(EMAIL));
        assertThat(identifiedUser.getIndividual().getFirstName().getValue(), is(FIRST_NAME));
        assertThat(identifiedUser.getIndividual().getLastName().getValue(), is(LAST_NAME));
        assertThat(identifiedUser.getIndividual().getSSN().getValue(), is(SSN));
        Message message = responseHolder.getScenarioLoginCallbackGetResult().getValue()
                .getCallback().getMessages().getValue().getMessage().get(0);
        assertThat(message, notNullValue());
        assertThat(message.getCode(), is(MESSAGE_CODE));
    }

    @Test
    public void getPersonInfo_ShouldReturn() {
        given(cSignClient.getPersonInfo(SSN)).willReturn(createConsumerDataGetResponse());
        ConsumerDataGetResponse consumerDataGetResponse = target.getPersonInfo(SSN);
        assertThat(consumerDataGetResponse.getConsumerDataGetResult().getValue(), notNullValue());
        final Consumer consumer = consumerDataGetResponse.getConsumerDataGetResult().getValue().getConsumer().getValue();
        assertThat(consumer, notNullValue());
        assertThat(consumer.getBirthDateDDMMYY().getValue(), is(BIRTH_DAY));
        assertThat(consumer.getBoardMember().getValue(), is(BOARD_MEMBER));
        assertThat(consumer.getConsumerName().getValue(), is(CONSUMER_NAME));
        assertThat(consumer.getCulture().getValue(), is(CULTURE));
        assertThat(consumer.getFirstName().getValue(), is(FIRST_NAME));
        assertThat(consumer.getNameCode().getValue(), is(NAME_CODE));
        assertThat(consumer.getNationalRegistrationAddressPostalAddressLine1().getValue(), is(POSTAL_ADDRESS_LINE_1));
        assertThat(consumer.getNationalRegistrationAddressPostCode().getValue(), is(POST_CODE));
        assertThat(consumer.getNationalRegistrationAddressTown().getValue(), is(TOWN));
        assertThat(consumer.getPostalAdressLine1().getValue(), is(POSTAL_ADDRESS_LINE_1));
        assertThat(consumer.getPostCode().getValue(), is(POST_CODE));
        assertThat(consumer.getRealEstateDataAvailableSeparately().getValue(), is(REAL_ESTATE_AVAILABLE_SEPARATELY));
        assertThat(consumer.getSocialSecurityNumber().getValue(), is(SSN));
        assertThat(consumer.getSoleProprietor().getValue(), is(SOLE_PROPRIETOR));
        assertThat(consumer.getTown().getValue(), is(TOWN));
        assertThat(consumer.getSurname().getValue(), is(LAST_NAME));
        assertThat(consumer.getTaxationYearCCYY().getValue(), is(TAXATION_YEAR));
    }

    @Test
    public void startTransaction_ShouldReturn() throws DatatypeConfigurationException {
        final List<VirtualFile> virtualFiles = Collections.singletonList(buildVirtualFile());
        given(cSignClient.startTransaction(
                TITLE,
                DESC,
                PROCUREMENT_ID,
                OWNER_REGISTRATION_NUMBER,
                virtualFiles)).willReturn(buildTransactionStartResponse());
        TransactionStartResponse responseHolder = target.startTransaction(TITLE,
                DESC,
                PROCUREMENT_ID,
                OWNER_REGISTRATION_NUMBER,
                virtualFiles);
        assertThat(responseHolder, notNullValue());
        assertThat(responseHolder.getTransactionStartResult().getValue().getMessages().getMessage(), notNullValue());
        assertThat(responseHolder.getTransactionStartResult().getValue().getMessages().getMessage().size(), is(1));
        assertThat(responseHolder.getTransactionStartResult().getValue().getTransaction(), notNullValue());
        assertTransaction(responseHolder.getTransactionStartResult().getValue().getTransaction());
    }

    @Test
    public void addSignature_ShouldReturn() throws DatatypeConfigurationException {
        given(cSignClient.addSignature(
                SESSION_ID,
                ScenarioCode.ANY_INDIVIDUAL.getCode(),
                TRANSACTION_ID,
                RETURN_URL))
                .willReturn(buildTransactionAddSignatureResponse());

        TransactionAddSignatureResponse responseHolder = target.addSignature(
                SESSION_ID,
                ScenarioCode.ANY_INDIVIDUAL.getCode(),
                TRANSACTION_ID,
                RETURN_URL);
        assertThat(responseHolder, notNullValue());
        assertThat(responseHolder.getTransactionAddSignatureResult().getValue().getSessionId(), is(SESSION_ID));
        assertThat(responseHolder.getTransactionAddSignatureResult().getValue().getSignatureURL(), is(SIGNATURE_URL));
        responseHolder.getTransactionAddSignatureResult().getValue().getMessages().getMessage().stream().findFirst().ifPresent(this::assertMessage);
        Transaction transaction = responseHolder.getTransactionAddSignatureResult().getValue().getTransaction();
        assertTransaction(transaction);
    }

    @Test
    public void getTransaction_ShouldReturn() throws DatatypeConfigurationException {
        given(cSignClient.getTransaction(TRANSACTION_ID)).willReturn(buildTransactionGetResponse());
        TransactionGetResponse responseHolder = target.getTransaction(TRANSACTION_ID);
        assertThat(responseHolder, notNullValue());
        assertThat(responseHolder.getTransactionGetResult().getValue().getMessages().getMessage(), notNullValue());
        assertThat(responseHolder.getTransactionGetResult().getValue().getMessages().getMessage().size(), is(1));
        responseHolder.getTransactionGetResult().getValue().getMessages().getMessage()
                .stream().findFirst().ifPresent(this::assertMessage);
        Transaction transaction = responseHolder.getTransactionGetResult().getValue().getTransaction();
        assertTransaction(transaction);

        transaction.getFiles()
                .getValue()
                .getVirtualFile()
                .stream()
                .findFirst()
                .ifPresent(this::assertVirtualFile);

        transaction.getMetadata()
                .getValue()
                .getMetaData()
                .stream()
                .findFirst()
                .ifPresent(this::assertMetaData);

        transaction.getSignatures()
                .getValue()
                .getSignature()
                .stream()
                .findFirst()
                .ifPresent(this::assertSignature);
    }

    @Test
    public void finalizeTransaction_ShouldReturn() throws DatatypeConfigurationException {
        given(cSignClient.finalizeTransaction(TRANSACTION_ID)).willReturn(buildFinalizeResponse());
        TransactionFinalizeResponse responseHolder = target.finalizeTransaction(TRANSACTION_ID);
        assertThat(responseHolder, notNullValue());
        assertThat(responseHolder.getTransactionFinalizeResult().getValue().getMessages(), notNullValue());
        assertThat(responseHolder.getTransactionFinalizeResult().getValue().getMessages().getMessage().size(), is(1));
        assertThat(responseHolder.getTransactionFinalizeResult().getValue().getTransaction(), notNullValue());
        responseHolder.getTransactionFinalizeResult().getValue().getMessages().getMessage()
                .stream().findFirst().ifPresent(this::assertMessage);
        assertThat(responseHolder.getTransactionFinalizeResult().getValue().getTransaction(), notNullValue());
        Transaction transaction = responseHolder.getTransactionFinalizeResult().getValue().getTransaction();
        assertTransaction(transaction);

        transaction.getFiles()
                .getValue()
                .getVirtualFile()
                .stream()
                .findFirst()
                .ifPresent(this::assertVirtualFile);

        transaction.getMetadata()
                .getValue()
                .getMetaData()
                .stream()
                .findFirst()
                .ifPresent(this::assertMetaData);

        transaction.getSignatures()
                .getValue()
                .getSignature()
                .stream()
                .findFirst()
                .ifPresent(this::assertSignature);
    }

    @Test
    public void getReceipt_ShouldReturn() throws DatatypeConfigurationException {
        given(cSignClient.getReceipt(TRANSACTION_ID)).willReturn(buildGetReceiptResponse());
        GetPDFReceiptResponse responseHolder = target.getReceipt(TRANSACTION_ID);
        assertReceipt(responseHolder.getGetPDFReceiptResult().getValue());

        responseHolder.getGetPDFReceiptResult().getValue().getReceipt().getReceiptOwners().getReceiptOwner()
                .stream()
                .findFirst()
                .ifPresent(this::assertReceiptOwner);

        responseHolder.getGetPDFReceiptResult().getValue().getReceipt().getReceiptAccessLists().getReceiptAccess()
                .stream()
                .findFirst()
                .ifPresent(this::assertReceiptAccess);
    }

    private ConsumerDataGetResponse createConsumerDataGetResponse() {
        ObjectFactory objectFactory = new ObjectFactory();
        se.csign.integration.data.v1.ConsumerDataGetResponse dataConsumerDataGetResponse = new se.csign.integration.data.v1.ConsumerDataGetResponse();

        Consumer consumer = new Consumer();
        consumer
                .withFirstName(objectFactory.createConsumerFirstName(FIRST_NAME))
                .withSurname(objectFactory.createConsumerSurname(LAST_NAME))
                .withNameCode(objectFactory.createConsumerNameCode(NAME_CODE))
                .withConsumerName(objectFactory.createConsumerConsumerName(CONSUMER_NAME))
                .withBirthDateDDMMYY(objectFactory.createConsumerBirthDateDDMMYY(BIRTH_DAY))
                .withTaxationYearCCYY(objectFactory.createConsumerTaxationYearCCYY(TAXATION_YEAR))
                .withBoardMember(objectFactory.createConsumerBoardMember(BOARD_MEMBER))
                .withCulture(objectFactory.createConsumerCulture(CULTURE))
                .withRealEstateDataAvailableSeparately(objectFactory
                        .createConsumerRealEstateDataAvailableSeparately(REAL_ESTATE_AVAILABLE_SEPARATELY))
                .withTown(objectFactory.createConsumerTown(TOWN))
                .withNationalRegistrationAddressTown(objectFactory.createConsumerNationalRegistrationAddressTown(TOWN))
                .withSocialSecurityNumber(objectFactory.createConsumerSocialSecurityNumber(SSN))
                .withSoleProprietor(objectFactory.createConsumerSoleProprietor(SOLE_PROPRIETOR))
                .withPostalAdressLine1(objectFactory.createConsumerPostalAdressLine1(POSTAL_ADDRESS_LINE_1))
                .withNationalRegistrationAddressPostalAddressLine1(
                        objectFactory.createConsumerNationalRegistrationAddressPostalAddressLine1(POSTAL_ADDRESS_LINE_1))
                .withNationalRegistrationAddressPostCode(
                        objectFactory.createConsumerNationalRegistrationAddressCode(POST_CODE))
                .withPostCode(objectFactory.createConsumerPostCode(POST_CODE));

        dataConsumerDataGetResponse
                .withConsumer(objectFactory.createConsumer(consumer))
                .withMessages(createArrayOfMessage(MESSAGE_CODE));

        return new ConsumerDataGetResponse()
                .withConsumerDataGetResult(objectFactory.createConsumerDataGetResponse(dataConsumerDataGetResponse));
    }

    private ScenarioLoginResponse createScenarioLoginResponse() {
        ObjectFactory dataFactory = new ObjectFactory();
        ArrayOfMessage arrayOfMessage = createArrayOfMessage(MESSAGE_CODE);
        ScenarioLoginResponse scenarioLoginResponse = new ScenarioLoginResponse();

        se.csign.integration.data.v1.ScenarioLoginResponse dataScenarioLoginResponse =
                new se.csign.integration.data.v1.ScenarioLoginResponse();

        dataScenarioLoginResponse
                .withSessionId(SESSION_ID)
                .withLoginUrl(LOGIN_URL)
                .withMessages(arrayOfMessage);
        scenarioLoginResponse
                .withScenarioLoginResult(dataFactory.createScenarioLoginResponse(dataScenarioLoginResponse));
        return scenarioLoginResponse;
    }

    private ArrayOfMessage createArrayOfMessage(int messageCode) {
        ArrayOfMessage arrayOfMessage = new ArrayOfMessage();
        Message message = new Message();
        message.withCode(messageCode);
        arrayOfMessage.withMessage(message);
        return arrayOfMessage;
    }

    private ScenarioLoginCallbackGetResponse createScenarioLoginCallbackResponse() throws DatatypeConfigurationException {
        ObjectFactory objectFactory = new ObjectFactory();

        IdentifiedUser identifiedUser = new IdentifiedUser();
        Individual individual = new Individual();
        individual
                .withCulture(objectFactory.createIndividualCulture(Locale.getDefault().toLanguageTag()))
                .withEmail(objectFactory.createIndividualEmail(EMAIL))
                .withFirstName(objectFactory.createIndividualFirstName(FIRST_NAME))
                .withLastName(objectFactory.createIndividualLastName(LAST_NAME))
                .withSSN(objectFactory.createIndividualSSN(SSN));
        identifiedUser.withIndividual(individual);

        ScenarioLoginCallback scenarioLoginCallback = new ScenarioLoginCallback();
        scenarioLoginCallback
                .withScenario(ScenarioCode.ANY_INDIVIDUAL.getCode())
                .withSessionId(SESSION_ID)
                .withMessages(objectFactory.createScenarioLoginCallbackMessages(createArrayOfMessage(MESSAGE_CODE)))
                .withIdentifiedUser(objectFactory.createScenarioLoginCallbackIdentifiedUser(identifiedUser));

        Message message = new Message();
        message.withCode(MESSAGE_CODE);
        se.csign.integration.data.v1.ScenarioLoginCallbackGetResponse dataScenarioLoginCallbackGetResponse =
                new se.csign.integration.data.v1.ScenarioLoginCallbackGetResponse();
        dataScenarioLoginCallbackGetResponse
                .withTimeStamp(createCurrentCalendar(TIME_STAMP))
                .withMessage(message)
                .withCallback(scenarioLoginCallback);
        ScenarioLoginCallbackGetResponse scenarioLoginCallbackGetResponse = new ScenarioLoginCallbackGetResponse();
        scenarioLoginCallbackGetResponse.
                withScenarioLoginCallbackGetResult(objectFactory.createScenarioLoginCallbackGetResponse(dataScenarioLoginCallbackGetResponse));

        return scenarioLoginCallbackGetResponse;
    }

    private TransactionStartResponse buildTransactionStartResponse() throws DatatypeConfigurationException {
        se.csign.integration.data.v1.TransactionStartResponse dataTransactionStartResponse =
                new se.csign.integration.data.v1.TransactionStartResponse();
        ArrayOfMessage arrayOfMessage = new ArrayOfMessage();
        Message message = buildMessage();
        arrayOfMessage.withMessage(message);
        ObjectFactory objectFactory = new ObjectFactory();
        ArrayOfVirtualFile arrayOfVirtualFile = new ArrayOfVirtualFile();
        VirtualFile virtualFile = buildVirtualFile();
        arrayOfVirtualFile.withVirtualFile(virtualFile);
        ArrayOfMetaData arrayOfMetaData = new ArrayOfMetaData();
        MetaData metaData = buildMetaData();
        arrayOfMetaData.withMetaData(metaData);
        Individual individual = buildIndividual(objectFactory);
        ArrayOfSignature arrayOfSignature = new ArrayOfSignature();
        Signature signature = buildSignature(individual);
        arrayOfSignature.withSignature(signature);
        Transaction transaction = buildTransaction(objectFactory, arrayOfVirtualFile, arrayOfMetaData, arrayOfSignature);
        dataTransactionStartResponse
                .withMessages(arrayOfMessage)
                .withTransaction(transaction);
        TransactionStartResponse transactionStartResponse = new TransactionStartResponse();
        transactionStartResponse.withTransactionStartResult(objectFactory.createTransactionStartResponse(dataTransactionStartResponse));
        return transactionStartResponse;
    }

    private TransactionAddSignatureResponse buildTransactionAddSignatureResponse() throws DatatypeConfigurationException {
        se.csign.integration.data.v1.TransactionAddSignatureResponse dataTransactionAddSignatureResponse =
                new se.csign.integration.data.v1.TransactionAddSignatureResponse();
        ArrayOfMessage arrayOfMessage = new ArrayOfMessage();
        Message message = buildMessage();
        arrayOfMessage.withMessage(message);

        ObjectFactory objectFactory = new ObjectFactory();
        ArrayOfMessage messageHolder = new ArrayOfMessage();
        messageHolder.withMessage(buildMessage());

        ArrayOfVirtualFile arrayOfVirtualFile = new ArrayOfVirtualFile();
        ArrayOfMetaData arrayOfMetaData = new ArrayOfMetaData();
        ArrayOfSignature arrayOfSignature = new ArrayOfSignature();

        MetaData metaData = buildMetaData();
        arrayOfMetaData.withMetaData(metaData);

        VirtualFile virtualFile = buildVirtualFile();
        arrayOfVirtualFile.withVirtualFile(virtualFile);

        Individual individual = buildIndividual(objectFactory);

        Signature signature = buildSignature(individual);
        arrayOfSignature.withSignature(signature);

        Transaction transaction = buildTransaction(
                objectFactory,
                arrayOfVirtualFile,
                arrayOfMetaData,
                arrayOfSignature);

        dataTransactionAddSignatureResponse
                .withMessages(arrayOfMessage)
                .withSessionId(SESSION_ID)
                .withSignatureURL(SIGNATURE_URL)
                .withTransaction(transaction);
        TransactionAddSignatureResponse transactionAddSignatureResponse = new TransactionAddSignatureResponse();
        transactionAddSignatureResponse
                .withTransactionAddSignatureResult(objectFactory.createTransactionAddSignatureResponse(dataTransactionAddSignatureResponse));

        return transactionAddSignatureResponse;
    }


    private TransactionGetResponse buildTransactionGetResponse() throws DatatypeConfigurationException {
        ObjectFactory objectFactory = new ObjectFactory();
        se.csign.integration.data.v1.TransactionGetResponse dataTransactionGetResponse =
                new se.csign.integration.data.v1.TransactionGetResponse();
        ArrayOfMessage arrayOfMessage = new ArrayOfMessage();
        arrayOfMessage.withMessage(buildMessage());

        ArrayOfVirtualFile arrayOfVirtualFile = new ArrayOfVirtualFile();
        ArrayOfMetaData arrayOfMetaData = new ArrayOfMetaData();
        ArrayOfSignature arrayOfSignature = new ArrayOfSignature();

        MetaData metaData = buildMetaData();
        arrayOfMetaData.withMetaData(metaData);

        VirtualFile virtualFile = buildVirtualFile();
        arrayOfVirtualFile.withVirtualFile(virtualFile);

        Individual individual = buildIndividual(objectFactory);

        Signature signature = buildSignature(individual);
        arrayOfSignature.withSignature(signature);

        Transaction transaction = buildTransaction(
                objectFactory,
                arrayOfVirtualFile,
                arrayOfMetaData,
                arrayOfSignature);

        dataTransactionGetResponse
                .withMessages(arrayOfMessage)
                .withTransaction(transaction);
        TransactionGetResponse transactionGetResponse = new TransactionGetResponse();
        transactionGetResponse
                .withTransactionGetResult(objectFactory.createTransactionGetResponse(dataTransactionGetResponse));
        return transactionGetResponse;
    }

    private void assertMetaData(MetaData metaData) {
        assertThat(metaData, notNullValue());
        assertThat(metaData.getName(), is(NAME));
        assertThat(metaData.getValue(), is(VALUE));
    }

    private void assertSignature(Signature signature) {
        assertThat(signature, notNullValue());
        ZonedDateTime zonedDateTime = signature.getTimeStamp().toGregorianCalendar().toInstant()
                .atZone(ZoneId.systemDefault());
        assertThat(zonedDateTime, is(TIME_STAMP));
        Individual individual = signature.getIndividual();
        assertThat(individual, notNullValue());
        assertThat(individual.getEmail().getValue(), is(EMAIL));
        assertThat(individual.getCulture().getValue(), is(Locale.getDefault().toLanguageTag()));
        assertThat(individual.getFirstName().getValue(), is(FIRST_NAME));
        assertThat(individual.getLastName().getValue(), is(LAST_NAME));
        assertThat(individual.getSSN().getValue(), is(SSN));
        assertThat(signature.getLegalEntity(), notNullValue());
        assertThat(signature.getRights(), notNullValue());
    }

    private void assertReceipt(GetReceiptResponse response) {
        assertThat(response, notNullValue());
        assertThat(response.getReceipt(), notNullValue());
        assertThat(response.getPdf(), notNullValue());
        assertThat(response.getReceipt().getDocumentSigningId(), is(DOCUMENT_SIGNING_ID));
        assertThat(response.getReceipt().getFilePackageId(), is(FILE_PACKAGED_ID));
        assertThat(response.getReceipt().getTitle(), is(TITLE));
        ZonedDateTime zonedDateTime = response.getReceipt().getCreatedDate().toGregorianCalendar().toInstant()
                .atZone(ZoneId.systemDefault());
        assertThat(zonedDateTime, equalTo(TIME_STAMP));
        assertThat(response.getPdf(), is(PDF));
    }

    private void assertTransaction(Transaction transaction) {
        assertThat(transaction.getDescription().getValue(), is(TRANSACTION_DESCRIPTION));
        assertThat(transaction.getTransactionId(), is(TRANSACTION_ID));
        assertThat(transaction.getProcurementId(), is(PROCUREMENT_ID));
        assertThat(transaction.getOwnerRegistrationNumber(), is(OWNER_REGISTRATION_NUMBER));
        assertThat(transaction.getTitle().getValue(), is(TRANSACTION_TITLE));
        ZonedDateTime zonedDateTime = transaction.getFinalized().getValue().toGregorianCalendar().toInstant()
                .atZone(ZoneId.systemDefault());
        assertThat(zonedDateTime, is(TIME_STAMP));
    }

    private void assertVirtualFile(VirtualFile virtualFile) {
        assertThat(virtualFile, notNullValue());
        assertThat(virtualFile.getSourceHash(), is(SOURCE_HASH));
        assertThat(virtualFile.getFileName(), is(FILE_NAME));
        assertThat(virtualFile.getFileSize(), is(FILE_SIZE));
        assertThat(virtualFile.getOriginalLocation(), is(ORIGINAL_LOCATION));
    }

    private void assertReceiptAccess(ReceiptAccess receiptAccess) {
        assertThat(receiptAccess.getUserId(), is(USER_ID));
        assertThat(receiptAccess.getOrganizationUnitId(), is(ORGANIZATION_UNIT_ID));
        assertThat(receiptAccess.getOrganizationId(), is(ORGANIZATION_ID));
    }

    private void assertReceiptOwner(ReceiptOwner receiptOwner) {
        assertThat(receiptOwner.getOrganizationId(), is(ORGANIZATION_ID));
        assertThat(receiptOwner.getOrganizationUnitId(), is(ORGANIZATION_UNIT_ID));
    }

    private TransactionFinalizeResponse buildFinalizeResponse() throws DatatypeConfigurationException {
        ObjectFactory dataFactory = new ObjectFactory();
        se.csign.integration.data.v1.TransactionFinalizeResponse dataTransactionFinalizeResponse =
                new se.csign.integration.data.v1.TransactionFinalizeResponse();
        Message message = buildMessage();
        ArrayOfMessage arrayOfMessages = new ArrayOfMessage();
        arrayOfMessages.withMessage(message);
        dataTransactionFinalizeResponse.withMessages(arrayOfMessages);

        ArrayOfVirtualFile arrayOfVirtualFile = new ArrayOfVirtualFile();
        ArrayOfMetaData arrayOfMetaData = new ArrayOfMetaData();
        ArrayOfSignature arrayOfSignature = new ArrayOfSignature();

        MetaData metaData = buildMetaData();
        arrayOfMetaData.withMetaData(metaData);

        VirtualFile virtualFile = buildVirtualFile();
        arrayOfVirtualFile.withVirtualFile(virtualFile);

        Individual individual = buildIndividual(dataFactory);

        Signature signature = buildSignature(individual);
        arrayOfSignature.withSignature(signature);

        Transaction transaction = buildTransaction(dataFactory, arrayOfVirtualFile, arrayOfMetaData, arrayOfSignature);

        dataTransactionFinalizeResponse
                .withTransaction(transaction)
                .withMessages(arrayOfMessages);

        TransactionFinalizeResponse transactionFinalizeResponse = new TransactionFinalizeResponse();
        transactionFinalizeResponse
                .withTransactionFinalizeResult(dataFactory.createTransactionFinalizeResponse(dataTransactionFinalizeResponse));

        return transactionFinalizeResponse;
    }

    private Transaction buildTransaction(ObjectFactory objectFactory, ArrayOfVirtualFile arrayOfVirtualFile, ArrayOfMetaData arrayOfMetaData, ArrayOfSignature arrayOfSignature) throws DatatypeConfigurationException {
        Transaction transaction = new Transaction();
        transaction
                .withDescription(objectFactory.createTransactionDescription(TRANSACTION_DESCRIPTION))
                .withTitle(objectFactory.createTransactionTitle(TRANSACTION_TITLE))
                .withFinalized(objectFactory.createTransactionFinalized(buildCurrentCalendar(TIME_STAMP)))
                .withTransactionId(TRANSACTION_ID)
                .withProcurementId(PROCUREMENT_ID)
                .withOwnerRegistrationNumber(OWNER_REGISTRATION_NUMBER)
                .withMetadata(objectFactory.createTransactionMetadata(arrayOfMetaData))
                .withFiles(objectFactory.createTransactionFiles(arrayOfVirtualFile))
                .withSignatures(objectFactory.createTransactionSignatures(arrayOfSignature));
        return transaction;
    }

    private Message buildMessage() {
        Message message = new Message();
        message.withCode(MESSAGE_CODE);
        return message;
    }

    private Individual buildIndividual(ObjectFactory objectFactory) {
        Individual individual = new Individual();
        individual
                .withCulture(objectFactory.createIndividualCulture(Locale.getDefault().toLanguageTag()))
                .withEmail(objectFactory.createIndividualEmail(EMAIL))
                .withFirstName(objectFactory.createIndividualFirstName(FIRST_NAME))
                .withLastName(objectFactory.createIndividualLastName(LAST_NAME))
                .withSSN(objectFactory.createIndividualSSN(SSN));
        return individual;
    }

    private Signature buildSignature(Individual individual) throws DatatypeConfigurationException {
        Signature signature = new Signature();
        signature
                .withTimeStamp(buildCurrentCalendar(TIME_STAMP))
                .withIndividual(individual)
                .withLegalEntity(new LegalEntity())
                .withRights(new ArrayOfRight());
        return signature;
    }

    private MetaData buildMetaData() {
        MetaData metaData = new MetaData();
        metaData.setName(NAME);
        metaData.setValue(VALUE);
        return metaData;
    }

    private VirtualFile buildVirtualFile() {
        VirtualFile virtualFile = new VirtualFile();
        virtualFile
                .withFileName(FILE_NAME)
                .withFileSize(FILE_SIZE)
                .withOriginalLocation(ORIGINAL_LOCATION)
                .withSourceHash(SOURCE_HASH);
        return virtualFile;
    }

    private GetPDFReceiptResponse buildGetReceiptResponse() throws DatatypeConfigurationException {
        ObjectFactory objectFactory = new ObjectFactory();
        Receipt receipt = new Receipt();
        ArrayOfReceiptAccess receiptAccessHolder = new ArrayOfReceiptAccess();
        receiptAccessHolder.withReceiptAccess(buildReceiptAccess());
        ArrayOfReceiptOwner receiptOwnerHolder = new ArrayOfReceiptOwner();
        receiptOwnerHolder.withReceiptOwner(buildReceiptOwner());
        receipt
                .withCreatedDate(buildCurrentCalendar(TIME_STAMP))
                .withDocumentSigningId(DOCUMENT_SIGNING_ID)
                .withFilePackageId(FILE_PACKAGED_ID)
                .withTitle(TITLE)
                .withReceiptAccessLists(receiptAccessHolder)
                .withReceiptOwners(receiptOwnerHolder);
        GetReceiptResponse response = new GetReceiptResponse();
        response.setPdf(PDF);
        response.setReceipt(receipt);

        GetPDFReceiptResponse getPDFReceiptResponse = new GetPDFReceiptResponse();
        getPDFReceiptResponse.withGetPDFReceiptResult(objectFactory.createGetReceiptResponse(response));
        return getPDFReceiptResponse;
    }

    private ReceiptAccess buildReceiptAccess() {
        ReceiptAccess receiptAccess = new ReceiptAccess();
        receiptAccess
                .withOrganizationId(ORGANIZATION_ID)
                .withOrganizationUnitId(ORGANIZATION_UNIT_ID)
                .withUserId(USER_ID);
        return receiptAccess;
    }

    private ReceiptOwner buildReceiptOwner() {
        ReceiptOwner receiptOwner = new ReceiptOwner();
        receiptOwner
                .withOrganizationId(ORGANIZATION_ID)
                .withOrganizationUnitId(ORGANIZATION_UNIT_ID);
        return receiptOwner;
    }

    private XMLGregorianCalendar buildCurrentCalendar(ZonedDateTime zonedDateTime) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(zonedDateTime);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }

    private void assertMessage(Message message) {
        assertThat(message, notNullValue());
        assertThat(message.getCode(), is(MESSAGE_CODE));
    }

    private XMLGregorianCalendar createCurrentCalendar(ZonedDateTime zonedDateTime) throws DatatypeConfigurationException {
        GregorianCalendar gregorianCalendar = GregorianCalendar.from(zonedDateTime);
        return DatatypeFactory.newInstance().newXMLGregorianCalendar(gregorianCalendar);
    }
}
