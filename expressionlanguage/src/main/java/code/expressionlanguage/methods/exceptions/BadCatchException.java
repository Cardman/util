package code.expressionlanguage.methods.exceptions;

public class BadCatchException extends RuntimeException {

    public BadCatchException() {
    }

    public BadCatchException(String _message) {
        super(_message);
    }

    public BadCatchException(Throwable _cause) {
        super(_cause);
    }

    public BadCatchException(String _message, Throwable _cause) {
        super(_message, _cause);
    }

}
