package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.analyze.instr.OperationsSequence;

public abstract class AbstractArrayInstancingOperation extends InvokingOperation {
    private String methodName;

    private String className;

    public AbstractArrayInstancingOperation(int _index, int _indexChild,
            MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
        methodName = getOperations().getFctName();
    }

    public final String getMethodName() {
        return methodName;
    }
    public final String getClassName() {
        return className;
    }
    public final void setClassName(String _className) {
        className = _className;
    }
}
