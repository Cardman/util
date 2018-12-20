package code.expressionlanguage.opers;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.OperationsSequence;
import code.expressionlanguage.calls.AbstractPageEl;
import code.expressionlanguage.methods.util.ArgumentsPair;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.variables.LocalVariable;
import code.util.CustList;
import code.util.IdMap;
import code.util.StringList;

public final class InternVariableOperation extends LeafOperation implements AtomicCalculableOperation {

    private String variableName = EMPTY_STRING;

    public InternVariableOperation(int _indexInEl, int _indexChild,
            MethodOperation _m, OperationsSequence _op) {
        super(_indexInEl, _indexChild, _m, _op);
    }

    @Override
    public void analyze(Analyzable _conf) {
        OperationsSequence op_ = getOperations();
        int relativeOff_ = op_.getOffset();
        String originalStr_ = op_.getValues().getValue(CustList.FIRST_INDEX);
        String str_ = originalStr_.trim();
        int off_ = StringList.getFirstPrintableCharIndex(originalStr_) + relativeOff_;
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _conf);
        variableName = str_;
        LocalVariable locVar_ = _conf.getInternVars().getVal(str_);
        String c_ = locVar_.getClassName();
        setResultClass(new ClassArgumentMatching(c_));
    }

    @Override
    public void analyzeAssignmentAfter(Analyzable _conf) {
        boolean isBool_;
        isBool_ = getResultClass().isBoolType(_conf);
        analyzeAssignmentAfter(_conf, isBool_);
    }

    @Override
    public void tryCalculateNode(Analyzable _conf) {
    }

    @Override
    public Argument calculate(IdMap<OperationNode, ArgumentsPair> _nodes,
            ContextEl _conf) {
        int relativeOff_ = getOperations().getOffset();
        String originalStr_ = getOperations().getValues().getValue(CustList.FIRST_INDEX);
        int off_ = StringList.getFirstPrintableCharIndex(originalStr_)+relativeOff_;
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off_, _conf);
        AbstractPageEl ip_ = _conf.getLastPage();
        LocalVariable locVar_ = ip_.getInternVars().getVal(variableName);
        Argument a_ = new Argument();
        a_.setStruct(locVar_.getStruct());
        setSimpleArgument(a_, _conf, _nodes);
        return a_;
    }

    public String getVariableName() {
        return variableName;
    }
}
