package se.devcode.csign.api.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import se.csign.integration.data.v1.ArrayOfVirtualFile;
import se.csign.integration.data.v1.AuthorizationData;
import se.csign.integration.data.v1.ConsumerDataGetRequest;
import se.csign.integration.data.v1.ConsumerParameter;
import se.csign.integration.data.v1.GetReceiptRequest;
import se.csign.integration.data.v1.IndividualParameter;
import se.csign.integration.data.v1.LoginUser;
import se.csign.integration.data.v1.ObjectFactory;
import se.csign.integration.data.v1.ScenarioLoginCallbackGetRequest;
import se.csign.integration.data.v1.ScenarioLoginRequest;
import se.csign.integration.data.v1.SignatureUser;
import se.csign.integration.data.v1.TransactionAddSignatureRequest;
import se.csign.integration.data.v1.TransactionFinalizeRequest;
import se.csign.integration.data.v1.TransactionGetRequest;
import se.csign.integration.data.v1.TransactionStartRequest;
import se.csign.integration.data.v1.VirtualFile;
import se.csign.integration.service.v1.ConsumerDataGet;
import se.csign.integration.service.v1.ConsumerDataGetResponse;
import se.csign.integration.service.v1.GetPDFReceipt;
import se.csign.integration.service.v1.GetPDFReceiptResponse;
import se.csign.integration.service.v1.ScenarioLogin;
import se.csign.integration.service.v1.ScenarioLoginCallbackGet;
import se.csign.integration.service.v1.ScenarioLoginCallbackGetResponse;
import se.csign.integration.service.v1.ScenarioLoginResponse;
import se.csign.integration.service.v1.TransactionAddSignature;
import se.csign.integration.service.v1.TransactionAddSignatureResponse;
import se.csign.integration.service.v1.TransactionFinalize;
import se.csign.integration.service.v1.TransactionFinalizeResponse;
import se.csign.integration.service.v1.TransactionGet;
import se.csign.integration.service.v1.TransactionGetResponse;
import se.csign.integration.service.v1.TransactionStart;
import se.csign.integration.service.v1.TransactionStartResponse;
import se.devcode.csign.api.util.CSignHelper;

import javax.inject.Inject;
import java.util.List;
import java.util.Locale;

public class CSignClient extends WebServiceGatewaySupport {

    @Value("${csign.support.email}")
    private String csignSupportEmail;

    private final CSignHelper cSignHelper;

    @Inject
    public CSignClient(CSignHelper cSignHelper) {
        this.cSignHelper = cSignHelper;
    }

    /**
     * Requests preparation data for login
     *
     * @param scenario  The scenario
     * @param sessionId The session id
     * @return Returns login preparation data
     */
    public ScenarioLoginResponse prepareLogin(int scenario,
                                              String sessionId,
                                              String returnUrl) {
        ObjectFactory dataFactory = new ObjectFactory();
        se.csign.integration.service.v1.ObjectFactory serviceFactory = new se.csign.integration.service.v1.ObjectFactory();
        AuthorizationData authorizationData = cSignHelper.getAuthorization();
        LoginUser loginUser = withLoginUser(dataFactory);
        ScenarioLoginRequest request = new ScenarioLoginRequest();
        request
                .withAuthorizationData(authorizationData)
                .withScenario(scenario)
                .withSessionId(sessionId)
                .withRetUrl(dataFactory.createScenarioLoginRequestRetUrl(returnUrl))
                .withLoginUser(loginUser);

        ScenarioLogin scenarioLogin = serviceFactory.createScenarioLogin()
                .withRequest(serviceFactory.createScenarioLoginRequest(request));

        return (ScenarioLoginResponse) getWebServiceTemplate().marshalSendAndReceive(scenarioLogin,
                new SoapActionCallback(withSoapAction("ScenarioLogin")));
    }

    /**
     * Requests login result
     *
     * @param sessionId The session id
     * @return Returns login result
     */
    public ScenarioLoginCallbackGetResponse getLoginResult(String sessionId) {
        se.csign.integration.service.v1.ObjectFactory serviceFactory = new se.csign.integration.service.v1.ObjectFactory();

        ScenarioLoginCallbackGetRequest request = new ScenarioLoginCallbackGetRequest();
        AuthorizationData authorizationData = cSignHelper.getAuthorization();
        request
                .withAuthorizationData(authorizationData)
                .withSessionId(sessionId);

        ScenarioLoginCallbackGet scenarioLoginCallbackGet = serviceFactory.createScenarioLoginCallbackGet()
                .withRequest(serviceFactory.createScenarioLoginCallbackGetRequest(request));

        return (ScenarioLoginCallbackGetResponse) getWebServiceTemplate()
                .marshalSendAndReceive(scenarioLoginCallbackGet,
                        new SoapActionCallback(withSoapAction("ScenarioLoginCallbackGet")));
    }

    /**
     * Requests user info
     *
     * @param ssn The social security number
     * @return Returns User info
     */
    public ConsumerDataGetResponse getPersonInfo(String ssn) {
        ObjectFactory objectFactory = new ObjectFactory();
        se.csign.integration.service.v1.ObjectFactory serviceFactory = new se.csign.integration.service.v1.ObjectFactory();
        ConsumerDataGetRequest request = new ConsumerDataGetRequest();
        AuthorizationData authorizationData = cSignHelper.getAuthorization();

        request
                .withAuthorizationData(authorizationData)
                .withConsumerParameter(objectFactory.createConsumerParameter(withConsumerParameter(ssn, objectFactory)));

        ConsumerDataGet consumerDataGet = serviceFactory.createConsumerDataGet()
                .withRequest(serviceFactory.createConsumerDataGetRequest(request));

        return (ConsumerDataGetResponse) getWebServiceTemplate()
                .marshalSendAndReceive(consumerDataGet,
                        new SoapActionCallback(withSoapAction("ConsumerDataGet")));
    }

    /**
     * @param title                   The title
     * @param description             The description
     * @param procurementId           The procurement id
     * @param ownerRegistrationNumber The owners registration number
     * @param files                   The files
     * @return Returns transaction start
     */
    public TransactionStartResponse startTransaction(String title,
                                                     String description,
                                                     int procurementId,
                                                     String ownerRegistrationNumber,
                                                     List<VirtualFile> files) {
        se.csign.integration.service.v1.ObjectFactory serviceFactory = new se.csign.integration.service.v1.ObjectFactory();
        ObjectFactory objectFactory = new ObjectFactory();
        ArrayOfVirtualFile arrayOfVirtualFile = new ArrayOfVirtualFile();
        arrayOfVirtualFile.withVirtualFile(files);
        TransactionStartRequest request = new TransactionStartRequest();
        request.withAuthorizationData(cSignHelper.getAuthorization())
                .withTitle(objectFactory.createTransactionStartRequestTitle(title))
                .withDescription(objectFactory.createTransactionStartRequestDescription(description))
                .withProcurementId(procurementId)
                .withOwnerRegistrationNumber(ownerRegistrationNumber)
                .withFiles(arrayOfVirtualFile);
        TransactionStart transactionStart = serviceFactory.createTransactionStart()
                .withRequest(serviceFactory.createTransactionStartRequest(request));
        return (TransactionStartResponse) getWebServiceTemplate().marshalSendAndReceive(transactionStart,
                new SoapActionCallback(withSoapAction("TransactionStart")));
    }

    /**
     * @param sessionId     The session id
     * @param scenario      The scenario code
     * @param transactionId The transaction id
     * @return Returns signature response
     */
    public TransactionAddSignatureResponse addSignature(String sessionId,
                                                        int scenario,
                                                        int transactionId,
                                                        String returnUrl) {
        se.csign.integration.service.v1.ObjectFactory serviceFactory = new se.csign.integration.service.v1.ObjectFactory();
        ObjectFactory objectFactory = new ObjectFactory();
        TransactionAddSignatureRequest request = new TransactionAddSignatureRequest();
        request.withAuthorizationData(cSignHelper.getAuthorization())
                .withSessionId(sessionId)
                .withScenario(scenario)
                .withTransactionId(transactionId)
                .withRetUrl(objectFactory.createScenarioLoginRequestRetUrl(returnUrl))
                .withSignatureUser(withSignatureUser(objectFactory));
        TransactionAddSignature transactionAddSignature = serviceFactory.createTransactionAddSignature()
                .withRequest(serviceFactory.createTransactionAddSignatureRequest(request));
        return (TransactionAddSignatureResponse) getWebServiceTemplate().marshalSendAndReceive(transactionAddSignature,
                new SoapActionCallback(withSoapAction("TransactionAddSignature")));
    }

    /**
     * @param transactionId The transaction id
     * @return transaction Returns the transaction
     */
    public TransactionGetResponse getTransaction(int transactionId) {
        se.csign.integration.service.v1.ObjectFactory serviceFactory = new se.csign.integration.service.v1.ObjectFactory();
        TransactionGetRequest request = new TransactionGetRequest();
        request.withAuthorizationData(cSignHelper.getAuthorization())
                .withTransactionId(transactionId);
        TransactionGet transactionGet = serviceFactory.createTransactionGet()
                .withRequest(serviceFactory.createTransactionGetRequest(request));
        return (TransactionGetResponse) getWebServiceTemplate().marshalSendAndReceive(transactionGet,
                new SoapActionCallback(withSoapAction("TransactionGet")));
    }

    /**
     * @param transactionId The transaction id
     * @return finalizes the transaction
     */
    public TransactionFinalizeResponse finalizeTransaction(int transactionId) {
        se.csign.integration.service.v1.ObjectFactory serviceFactory = new se.csign.integration.service.v1.ObjectFactory();
        TransactionFinalizeRequest request = new TransactionFinalizeRequest();
        request
                .withAuthorizationData(cSignHelper.getAuthorization())
                .withTransactionId(transactionId);
        TransactionFinalize transactionFinalize = serviceFactory.createTransactionFinalize()
                .withRequest(serviceFactory.createTransactionFinalizeRequest(request));
        return (TransactionFinalizeResponse) getWebServiceTemplate().marshalSendAndReceive(transactionFinalize,
                new SoapActionCallback(withSoapAction("TransactionFinalize")));
    }

    /**
     *
     * @param signatureObjectId The transaction/signature object id
     * @return Returns signing receipt in PDF
     */
    public GetPDFReceiptResponse getReceipt(int signatureObjectId) {
        se.csign.integration.service.v1.ObjectFactory serviceFactory = new se.csign.integration.service.v1.ObjectFactory();
        GetReceiptRequest request = new GetReceiptRequest();
        request
                .withAuthorizationData(cSignHelper.getAuthorization())
                .withGetPdf(true)
                .withSignatureObjectId(signatureObjectId);
        GetPDFReceipt getPDFReceipt = serviceFactory.createGetPDFReceipt()
                .withRequest(serviceFactory.createGetPDFReceiptRequest(request));
        return (GetPDFReceiptResponse) getWebServiceTemplate().marshalSendAndReceive(getPDFReceipt,
                new SoapActionCallback(withSoapAction("GetPDFReceipt")));
    }

    private SignatureUser withSignatureUser(ObjectFactory objectFactory) {
        SignatureUser signatureUser = new SignatureUser();
        IndividualParameter individualParameter = new IndividualParameter();
        individualParameter
                .withCulture(objectFactory.createConsumerCulture(Locale.getDefault().toLanguageTag()))
                .withEmail(objectFactory.createIndividualEmail(csignSupportEmail));
        signatureUser.withIndividualParameter(individualParameter);
        return signatureUser;
    }

    private ConsumerParameter withConsumerParameter(String ssn, ObjectFactory objectFactory) {
        return new ConsumerParameter()
                .withSSN(objectFactory.createConsumerParameterSSN(ssn));
    }

    private LoginUser withLoginUser(ObjectFactory objectFactory) {
        IndividualParameter individualParameter = new IndividualParameter();
        individualParameter
                .withCulture(objectFactory.createIndividualParameterCulture(Locale.getDefault().toLanguageTag()))
                .withEmail(objectFactory.createIndividualParameterEmail(csignSupportEmail));

        return new LoginUser()
                .withIndividualParameter(individualParameter)
                .withLegalEntityParameter(objectFactory.createLegalEntityParameter());
    }

    private String withSoapAction(String actionName) {
        final String baseActionString = "http://www.csign.se/integration/service/V1/IIntegrationService/%s";
        return String.format(baseActionString, actionName);
    }
}
