package se.devcode.csign.api.service;

import org.springframework.stereotype.Service;
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

import javax.inject.Inject;
import java.util.List;

@Service
public class CSignService {

    private final CSignClient cSignClient;

    @Inject
    CSignService(CSignClient cSignClient) {
        this.cSignClient = cSignClient;
    }

    /**
     * {@inheritDoc}
     */
    public ScenarioLoginResponse prepareLogin(int scenario,
                                              String sessionId,
                                              String returnUrl) {
        return cSignClient.prepareLogin(scenario, sessionId, returnUrl);
    }

    /**
     * {@inheritDoc}
     */
    public ScenarioLoginCallbackGetResponse getLoginResult(String sessionId) {
        return cSignClient.getLoginResult(sessionId);
    }

    /**
     * {@inheritDoc}
     */
    public ConsumerDataGetResponse getPersonInfo(String ssn) {
        return cSignClient.getPersonInfo(ssn);
    }

    /**
     * {@inheritDoc}
     */
    TransactionStartResponse startTransaction(String title,
                                              String description,
                                              int procurementId,
                                              String ownerRegistrationNumber,
                                              List<VirtualFile> files) {
        return cSignClient.startTransaction(title, description, procurementId, ownerRegistrationNumber, files);
    }

    /**
     * {@inheritDoc}
     */
    TransactionAddSignatureResponse addSignature(String sessionId,
                                                 int scenario,
                                                 int transactionId,
                                                 String returnUrl) {
        return cSignClient.addSignature(sessionId, scenario, transactionId, returnUrl);
    }

    /**
     * {@inheritDoc}
     */
    public TransactionGetResponse getTransaction(int transactionId) {
        return cSignClient.getTransaction(transactionId);
    }

    /**
     * {@inheritDoc}
     */
    TransactionFinalizeResponse finalizeTransaction(int transactionId) {
        return cSignClient.finalizeTransaction(transactionId);
    }

    /**
     * {@inheritDoc}
     */
    GetPDFReceiptResponse getReceipt(int transactionId) {
        return cSignClient.getReceipt(transactionId);
    }

}
