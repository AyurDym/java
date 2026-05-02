package exception;

public class SaladException extends Exception {
    public SaladException(String message) {
        super(message);
    }

    public SaladException(String message, Throwable cause) {
        super(message, cause);
    }
}
