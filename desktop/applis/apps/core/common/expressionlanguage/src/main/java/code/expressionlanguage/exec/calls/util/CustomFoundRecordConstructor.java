package code.expressionlanguage.exec.calls.util;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.ExecutingUtil;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.calls.AbstractPageEl;
import code.expressionlanguage.exec.util.ExecFormattedRootBlock;
import code.expressionlanguage.fwd.blocks.ExecTypeFunction;
import code.util.CustList;
import code.util.StringMap;

public final class CustomFoundRecordConstructor implements CallingState {

    private final ExecFormattedRootBlock className;
    private final ExecTypeFunction pair;

    private final StringMap<String> id;
    private final String fieldName;
    private final int childIndex;

    private final CustList<Argument> arguments;

    public CustomFoundRecordConstructor(ExecFormattedRootBlock _className,
                                        ExecTypeFunction _pair,
                                        StringMap<String> _id,
                                         CustList<Argument> _arguments) {
        this(_className,_pair,_id,"",-1,_arguments);
    }

    public CustomFoundRecordConstructor(ExecFormattedRootBlock _className,
                                        ExecTypeFunction _pair,
                                        StringMap<String> _id,
                                        String _fieldName, int _childIndex,
                                        CustList<Argument> _arguments) {
        className = _className;
        pair = _pair;
        id = _id;
        fieldName = _fieldName;
        childIndex = _childIndex;
        arguments = _arguments;
    }

    @Override
    public AbstractPageEl processAfterOperation(ContextEl _context, StackCall _stack) {
        return ExecutingUtil.createRecordInstancing(_context,this);
    }

    public StringMap<String> getId() {
        return id;
    }

    public int getChildIndex() {
        return childIndex;
    }

    public String getFieldName() {
        return fieldName;
    }

    public ExecTypeFunction getPair() {
        return pair;
    }

    public ExecFormattedRootBlock getClassName() {
        return className;
    }

    public CustList<Argument> getArguments() {
        return arguments;
    }

}
