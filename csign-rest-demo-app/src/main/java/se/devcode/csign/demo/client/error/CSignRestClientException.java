package se.devcode.csign.demo.client.error;

public class CSignRestClientException extends Exception {

    private final int status;

    public CSignRestClientException(int status,
                                    String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}
