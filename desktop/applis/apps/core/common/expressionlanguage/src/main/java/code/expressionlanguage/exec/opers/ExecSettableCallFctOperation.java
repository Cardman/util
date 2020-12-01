package code.expressionlanguage.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.exec.types.ExecClassArgumentMatching;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.fwd.opers.ExecArrContent;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.expressionlanguage.structs.Struct;
import code.util.IdMap;

public abstract class ExecSettableCallFctOperation extends ExecInvokingOperation implements ExecSettableElResult {
    private final ExecArrContent arrContent;
    protected ExecSettableCallFctOperation(ExecOperationContent _opCont, boolean _intermediateDottedOperation, ExecArrContent _execArr) {
        super(_opCont, _intermediateDottedOperation);
        arrContent = _execArr;
    }

    protected ExecSettableCallFctOperation(int _indexChild, ExecClassArgumentMatching _res, int _order, boolean _intermediate, ExecArrContent _execArr) {
        super(_indexChild, _res, _order, _intermediate);
        arrContent = _execArr;
    }

    @Override
    public Argument calculateSetting(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf, Argument _right) {
        return trySetArgument(_nodes, _conf, _right);
    }

    @Override
    public Argument calculateCompoundSetting(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf, String _op, Argument _right, ExecClassArgumentMatching _cl, byte _cast) {
        ArgumentsPair pair_ = ExecTemplates.getArgumentPair(_nodes, this);
        Struct store_ = ExecTemplates.getValue(pair_.getWrapper(), _conf);
        return getCommonCompoundSetting(_nodes,_conf,store_,_op,_right,_cl,_cast);
    }

    @Override
    public Argument calculateSemiSetting(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf, String _op, boolean _post, byte _cast) {
        ArgumentsPair pair_ = ExecTemplates.getArgumentPair(_nodes, this);
        Struct store_ = ExecTemplates.getValue(pair_.getWrapper(), _conf);
        return getCommonSemiSetting(_nodes,_conf,store_,_op,_post,_cast);
    }
    private Argument getCommonCompoundSetting(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf, Struct _store, String _op, Argument _right, ExecClassArgumentMatching _arg, byte _cast) {
        Argument left_ = new Argument(_store);
        Argument res_ = ExecNumericOperation.calculateAffect(left_, _conf, _right, _op, arrContent.isCatString(), _arg.getNames(), _cast);
        return trySetArgument(_nodes, _conf, res_);
    }

    private Argument getCommonSemiSetting(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf, Struct _store, String _op, boolean _post, byte _cast) {
        Argument left_ = new Argument(_store);
        Argument res_ = ExecNumericOperation.calculateIncrDecr(left_, _op, _cast);
        trySetArgument(_nodes, _conf, res_);
        return ExecSemiAffectationOperation.getPrePost(_post, left_, res_);
    }
    @Override
    public boolean resultCanBeSet() {
        return arrContent.isVariable();
    }

    @Override
    public Argument endCalculate(ContextEl _conf, IdMap<ExecOperationNode, ArgumentsPair> _nodes, Argument _right) {
        return trySetArgument(_nodes, _conf, _right);
    }

    @Override
    public Argument endCalculate(ContextEl _conf, IdMap<ExecOperationNode, ArgumentsPair> _nodes, boolean _post, Argument _stored, Argument _right) {
        trySetArgument(_nodes, _conf, _right);
        return ExecSemiAffectationOperation.getPrePost(_post, _stored, _right);
    }

    private Argument trySetArgument(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf, Argument _res) {
        ArgumentsPair pair_ = ExecTemplates.getArgumentPair(_nodes, this);
        return ExecTemplates.trySetArgument(_conf, _res, pair_);
    }
}
