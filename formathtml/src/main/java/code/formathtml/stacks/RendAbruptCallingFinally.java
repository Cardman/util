package code.formathtml.stacks;

import code.formathtml.RendCallingFinally;

public class RendAbruptCallingFinally {
    private final RendCallingFinally callingFinally;

    public RendAbruptCallingFinally(RendCallingFinally _callingFinally) {
        callingFinally = _callingFinally;
    }

    public RendCallingFinally getCallingFinally() {
        return callingFinally;
    }
}
