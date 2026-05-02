package exception;

public class PoemException extends Exception {

    public PoemException(String message) {
        super(message);
    }

    public PoemException(String message, Throwable cause) {
        super(message, cause);
    }
}
