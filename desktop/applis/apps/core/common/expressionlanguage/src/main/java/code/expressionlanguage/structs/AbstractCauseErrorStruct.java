package code.expressionlanguage.structs;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.NumParsers;
import code.expressionlanguage.exec.MetaInfoUtil;
import code.expressionlanguage.exec.StackCall;
import code.util.CollCapacity;
import code.util.StringList;
import code.util.core.StringUtil;

public abstract class AbstractCauseErrorStruct extends WithoutParentIdStruct implements ErroneousStruct {

    private final ArrayStruct stack;
    private final ArrayStruct fullStack;

    private final Struct cause;
    private final String message;

    protected AbstractCauseErrorStruct(String _message, Struct _cause, ContextEl _cont, StackCall _stackCall) {
        message = _message;
        cause = _cause;
        stack = MetaInfoUtil.newStackTraceElementArray(_cont, _stackCall);
        fullStack = MetaInfoUtil.newStackTraceElementArrayFull(_stackCall);
    }

    @Override
    public Struct getCause() {
        return cause;
    }

    @Override
    public StringStruct getDisplayedString(ContextEl _an) {
        return new StringStruct(getStringRep(_an, stack));
    }

    @Override
    public ArrayStruct getStack() {
        return stack;
    }

    @Override
    public ArrayStruct getFullStack() {
        return fullStack;
    }

    @Override
    public Struct getMessage() {
        return new StringStruct(message);
    }

    public String getStringRep(ContextEl _an, ArrayStruct _arrInst) {
        StringList str_ = new StringList(new CollCapacity(_arrInst.getLength()+2));
        str_.add(getClassName(_an));
        str_.add(message);
        for (Struct s: _arrInst.list()) {
            str_.add(NumParsers.getStack(s).getStringRep());
        }
        return StringUtil.join(str_, "\n");
    }

}
