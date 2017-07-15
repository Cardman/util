package code.serialize.exceptions;

public class NoSuchDeclaredMethodException extends RuntimeException {

    public NoSuchDeclaredMethodException() {
    }

    public NoSuchDeclaredMethodException(String _message) {
        super(_message);
    }

    public NoSuchDeclaredMethodException(Throwable _t) {
        super(_t);
    }
}
