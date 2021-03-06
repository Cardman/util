package code.expressionlanguage.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.ExecHelper;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.exec.variables.AbstractWrapper;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.expressionlanguage.fwd.opers.ExecVariableContent;
import code.expressionlanguage.structs.Struct;
import code.util.IdMap;
import code.util.StringList;

public final class ExecStdRefVariableOperation extends ExecLeafOperation implements
        AtomicExecCalculableOperation,ExecSettableElResult {
    private final ExecVariableContent variableContent;
    private final boolean declare;
    public ExecStdRefVariableOperation(ExecOperationContent _l, ExecVariableContent _variableContent, boolean _declare) {
        super(_l);
        variableContent = _variableContent;
        declare = _declare;
    }
    public ExecStdRefVariableOperation(ExecOperationContent _l, ExecVariableContent _variableContent) {
        super(_l);
        variableContent = _variableContent;
        declare = false;
    }

    @Override
    public void calculate(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf, StackCall _stack) {
        setRelOffsetPossibleLastPage(variableContent.getOff(), _stack);
        AbstractWrapper val_ = ExecTemplates.getWrapper(variableContent, _stack);
        ArgumentsPair pair_ = ExecHelper.getArgumentPair(_nodes, this);
        pair_.setWrapper(val_);
        Argument arg_ = ExecTemplates.getArgValue(val_, _conf, _stack);
        if (resultCanBeSet()) {
            setQuickNoConvertSimpleArgument(arg_, _conf, _nodes, _stack);
        } else {
            setSimpleArgument(arg_, _conf, _nodes, _stack);
        }

    }

    public String getVariableName() {
        return variableContent.getVariableName();
    }

    @Override
    public Argument calculateSetting(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf, Argument _right, StackCall _stack) {
        return trySetArgument(_conf, _right, _stack);
    }

    @Override
    public Argument calculateCompoundString(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf, Argument _right, StackCall _stack) {
        Argument a_ = getArgument(_nodes,this);
        Struct store_ = a_.getStruct();
        Argument left_ = new Argument(store_);
        Argument res_ = ExecCatOperation.localSumDiff(left_, _right, _conf);
        return trySetArgument(_conf, res_, _stack);
    }

    @Override
    public Argument calculateCompoundSetting(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf, Argument _right, StringList _cl, StackCall _stack) {
        Argument a_ = getArgument(_nodes,this);
        Struct store_ = a_.getStruct();
        Argument left_ = new Argument(store_);
        Argument res_ = ExecNumericOperation.calculateAffect(left_, _conf, _right, _cl, _stack);
        return trySetArgument(_conf, res_, _stack);
    }

    @Override
    public Argument calculateCompoundSetting(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf, String _op, Argument _right, byte _cast, StackCall _stack) {
        Argument a_ = getArgument(_nodes,this);
        Struct store_ = a_.getStruct();
        Argument left_ = new Argument(store_);
        Argument res_ = ExecNumericOperation.calculateAffect(left_, _conf, _right, _op, _cast, _stack);
        return trySetArgument(_conf, res_, _stack);
    }

    @Override
    public Argument calculateSemiSetting(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf, String _op, boolean _post, byte _cast, StackCall _stack) {
        Argument a_ = getArgument(_nodes,this);
        Struct store_ = a_.getStruct();
        Argument left_ = new Argument(store_);
        Argument res_ = ExecNumericOperation.calculateIncrDecr(left_, _op, _cast);
        trySetArgument(_conf, res_, _stack);
        return ExecSemiAffectationOperation.getPrePost(_post, left_, res_);
    }

    @Override
    public boolean resultCanBeSet() {
        return variableContent.isVariable();
    }

    @Override
    public Argument endCalculate(ContextEl _conf, IdMap<ExecOperationNode, ArgumentsPair> _nodes, Argument _right, StackCall _stack) {
        return trySetArgument(_conf, _right, _stack);
    }

    @Override
    public Argument endCalculate(ContextEl _conf, IdMap<ExecOperationNode, ArgumentsPair> _nodes, boolean _post, Argument _stored, Argument _right, StackCall _stack) {
        trySetArgument(_conf, _right, _stack);
        return ExecSemiAffectationOperation.getPrePost(_post, _stored, _right);
    }

    private Argument trySetArgument(ContextEl _conf, Argument _res, StackCall _stackCall) {
        return ExecTemplates.setWrapValue(_conf,variableContent, _res, _stackCall);
    }
    public boolean isDeclare() {
        return declare;
    }
}
