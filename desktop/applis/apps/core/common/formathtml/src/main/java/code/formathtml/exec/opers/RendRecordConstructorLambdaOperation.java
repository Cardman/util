package code.formathtml.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.opers.ExecOperationNode;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.fwd.opers.ExecLambdaCommonContent;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.expressionlanguage.exec.opers.*;
import code.formathtml.Configuration;
import code.formathtml.exec.RendStackCall;
import code.formathtml.util.BeanLgNames;
import code.util.IdMap;
import code.util.StringMap;

public final class RendRecordConstructorLambdaOperation extends RendAbstractLambdaOperation {

    private final ExecRootBlock pair;
    private final StringMap<String> id;

    public RendRecordConstructorLambdaOperation(ExecOperationContent _opCont, ExecLambdaCommonContent _lamCont, ExecRootBlock _pair, StringMap<String> _id) {
        super(_opCont, _lamCont);
        pair = _pair;
        id = _id;
    }

    @Override
    public void calculate(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, Configuration _conf, BeanLgNames _advStandards, ContextEl _context, StackCall _stack, RendStackCall _rendStack) {
        String clArg_ = getResultClass().getSingleNameOrEmpty();
        String ownerType_ = getFoundClass();
        Argument res_ = new Argument(ExecRecordConstructorLambdaOperation.newLambda(ownerType_, clArg_, pair, id));
        setSimpleArgument(res_, _nodes, _context, _stack, _rendStack);
    }
}
