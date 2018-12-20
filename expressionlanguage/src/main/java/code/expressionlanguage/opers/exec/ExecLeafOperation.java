package code.expressionlanguage.opers.exec;

import code.expressionlanguage.opers.LeafOperation;

public abstract class ExecLeafOperation extends ExecOperationNode {

    ExecLeafOperation(LeafOperation _l) {
        super(_l);
    }

    @Override
    public final ExecOperationNode getFirstChild() {
        return null;
    }
}
