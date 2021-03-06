package code.expressionlanguage.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.inherits.MethodParamChecker;
import code.expressionlanguage.exec.util.ExecFormattedRootBlock;
import code.expressionlanguage.exec.util.ExecOperationInfo;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.fwd.blocks.ExecTypeFunction;
import code.expressionlanguage.fwd.opers.ExecArrContent;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.expressionlanguage.fwd.opers.ExecStaticFctContent;
import code.util.CustList;
import code.util.IdMap;
import code.util.core.StringUtil;

public final class ExecStaticFctOperation extends ExecSettableCallFctOperation {

    private final ExecStaticFctContent staticFctContent;

    private final ExecTypeFunction pair;
    private final ExecFormattedRootBlock formattedType;

    public ExecStaticFctOperation(ExecTypeFunction _pair, ExecOperationContent _opCont, boolean _intermediateDottedOperation, ExecStaticFctContent _staticFctContent, ExecArrContent _arrContent) {
        super(_opCont, _intermediateDottedOperation,_arrContent);
        staticFctContent = _staticFctContent;
        pair = _pair;
        formattedType = _staticFctContent.getFormattedType();
    }

    @Override
    public void calculate(IdMap<ExecOperationNode, ArgumentsPair> _nodes,
                          ContextEl _conf, StackCall _stack) {
        int off_ = StringUtil.getFirstPrintableCharIndex(staticFctContent.getMethodName());
        setRelOffsetPossibleLastPage(off_, _stack);
        ExecFormattedRootBlock classNameFound_ = ExecFormattedRootBlock.formatType(formattedType, staticFctContent.getKind(), _stack);
        Argument res_;
        if (_conf.getExiting().hasToExit(_stack, classNameFound_.getRootBlock())) {
            res_ = Argument.createVoid();
        } else {
            String lastType_ = ExecFormattedRootBlock.formatType(classNameFound_, staticFctContent.getLastType(), staticFctContent.getKind());
            res_ = prep(_conf, _stack, classNameFound_, lastType_, buildInfos(_nodes), staticFctContent, pair);
        }
        setResult(res_, _conf, _nodes, _stack);
    }

    public static Argument prep(ContextEl _conf, StackCall _stack, ExecFormattedRootBlock _classNameFound, String _lastType, CustList<ExecOperationInfo> _infos, ExecStaticFctContent _staticFctContent, ExecTypeFunction _pair) {
        Argument prev_ = new Argument();
        return new MethodParamChecker(_pair, fectchArgs(_lastType, _staticFctContent.getNaturalVararg(), null, _conf, _stack, _infos), _staticFctContent.getKind()).checkParams(_classNameFound, prev_, null, _conf, _stack);
    }


}
