package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.analyze.instr.OperationsSequence;
import code.util.IntTreeMap;
import code.util.core.StringUtil;

public final class RefTernaryOperation extends AbstractRefTernaryOperation {

    public RefTernaryOperation(int _index, int _indexChild, MethodOperation _m,
                               OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    void calculateChildren() {
        IntTreeMap< String> vs_ = getOperations().getValues();
        vs_.removeKey(vs_.firstKey());
        getChildren().putAllMap(vs_);
        String fct_ = getOperations().getFctName();
        setOffsetLocal(StringUtil.getFirstPrintableCharIndex(fct_));
    }

}
