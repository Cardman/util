package code.formathtml.stacks;

public class RendAbruptCallingFinally {
    private final Object callingFinally;

    public RendAbruptCallingFinally(Object _callingFinally) {
        callingFinally = _callingFinally;
    }

    public Object getCallingFinally() {
        return callingFinally;
    }
}
