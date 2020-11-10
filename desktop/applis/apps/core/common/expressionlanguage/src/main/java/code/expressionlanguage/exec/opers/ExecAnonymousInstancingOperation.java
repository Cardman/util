package code.expressionlanguage.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.blocks.ExecNamedFunctionBlock;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.fwd.blocks.ExecTypeFunction;
import code.expressionlanguage.fwd.opers.ExecInstancingCommonContent;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.util.CustList;
import code.util.IdMap;
import code.util.core.StringUtil;

public final class ExecAnonymousInstancingOperation extends
        ExecInvokingOperation {

    private final ExecTypeFunction pair;
    private final ExecInstancingCommonContent instancingCommonContent;

    public ExecAnonymousInstancingOperation(ExecOperationContent _opCont, boolean _intermediateDottedOperation, ExecInstancingCommonContent _instancingCommonContent, ExecTypeFunction _pair) {
        super(_opCont, _intermediateDottedOperation);
        instancingCommonContent = _instancingCommonContent;
        pair = _pair;
    }

    @Override
    public void calculate(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf) {
        Argument previous_ = getPreviousArg(this, _nodes, _conf);
        Argument res_ = getArgument(previous_,_nodes, _conf);
        setSimpleArgument(res_, _conf, _nodes);
    }
    Argument getArgument(Argument _previous, IdMap<ExecOperationNode, ArgumentsPair> _nodes,
                         ContextEl _conf) {
        int off_ = StringUtil.getFirstPrintableCharIndex(instancingCommonContent.getMethodName());
        setRelOffsetPossibleLastPage(off_, _conf);
        String className_ = _conf.formatVarType(instancingCommonContent.getClassName());
        String base_ = StringExpUtil.getIdFromAllTypes(className_);
        if (_conf.getExiting().hasToExit(base_)) {
            return Argument.createVoid();
        }
        CustList<Argument> firstArgs_ = getArgs(_nodes, className_);
        return instancePrepareCust(_conf, className_, pair, _previous, firstArgs_, "", -1);
    }

    private CustList<Argument> getArgs(IdMap<ExecOperationNode, ArgumentsPair> _nodes, String _className) {
        return fectchInstFormattedArgs(_nodes, _className, pair.getType(), instancingCommonContent.getLastType(), instancingCommonContent.getNaturalVararg());
    }

}
