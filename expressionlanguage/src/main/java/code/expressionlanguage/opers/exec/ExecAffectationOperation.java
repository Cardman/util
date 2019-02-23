package code.expressionlanguage.opers.exec;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.instr.ElUtil;
import code.expressionlanguage.methods.util.ArgumentsPair;
import code.expressionlanguage.opers.AffectationOperation;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.FieldInfo;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.Struct;
import code.util.IdMap;

public final class ExecAffectationOperation extends ExecReflectableOpering implements AffectationOperable {

    private ExecSettableElResult settable;

    public ExecAffectationOperation(AffectationOperation _a) {
        super(_a);
    }

    public void setup() {
        settable = tryGetSettable(this);
    }
    static ExecSettableElResult tryGetSettable(ExecMethodOperation _operation) {
        Operable root_ = AffectationOperation.getFirstToBeAnalyzed(_operation);
        ExecSettableElResult elt_;
        if (!(root_ instanceof ExecDotOperation)) {
            elt_ = castTo(root_);
        } else {
            ExecOperationNode beforeLast_ = ((ExecMethodOperation)root_).getChildrenNodes().last();
            elt_ = castTo(beforeLast_);
        }
        return elt_;
    }
    private static ExecSettableElResult castTo(Operable _op) {
        if (_op instanceof ExecSettableElResult) {
            return (ExecSettableElResult) _op;
        }
        return null;
    }
    public ExecSettableElResult getSettable() {
        return settable;
    }
    @Override
    public void quickCalculate(Analyzable _conf) {
        AffectationOperation.setArg(_conf, this, settable);
    }

    @Override
    public void calculate(IdMap<ExecOperationNode, ArgumentsPair> _nodes,
                          ContextEl _conf) {
        ExecOperationNode right_ = getChildrenNodes().last();
        Argument rightArg_ = getArgument(_nodes, right_);
        Argument arg_ = settable.calculateSetting(_nodes, _conf, rightArg_);
        setSimpleArgument(arg_, _conf, _nodes);
    }

}
