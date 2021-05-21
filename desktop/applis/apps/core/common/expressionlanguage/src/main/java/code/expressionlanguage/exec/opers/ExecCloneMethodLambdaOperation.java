package code.expressionlanguage.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.util.ExecFormattedRootBlock;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.functionid.MethodModifier;
import code.expressionlanguage.fwd.opers.ExecLambdaCommonContent;
import code.expressionlanguage.fwd.opers.ExecLambdaMethodContent;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.expressionlanguage.structs.LambdaMethodStruct;
import code.expressionlanguage.structs.MethodMetaInfo;
import code.expressionlanguage.structs.Struct;
import code.util.IdMap;

public final class ExecCloneMethodLambdaOperation extends ExecAbstractLambdaOperation {

    private final ExecLambdaMethodContent lambdaMethodContent;

    public ExecCloneMethodLambdaOperation(ExecOperationContent _opCont, ExecLambdaCommonContent _lamCont, ExecLambdaMethodContent _lambdaMethodContent) {
        super(_opCont, _lamCont);
        lambdaMethodContent = _lambdaMethodContent;
    }

    @Override
    public void calculate(IdMap<ExecOperationNode, ArgumentsPair> _nodes,
                          ContextEl _conf, StackCall _stack) {
        Argument previous_ = getPreviousArg(this, _nodes, _stack);
        String clArg_ = getResultClass().getSingleNameOrEmpty();
        ExecFormattedRootBlock ownerType_ = getFoundClass();
        ownerType_ = _stack.formatVarType(ownerType_);
        clArg_ = _stack.formatVarType(clArg_);
        Argument res_ = new Argument(newLambda(getLambdaCommonContent(),lambdaMethodContent,previous_, _conf, ownerType_, clArg_));
        setSimpleArgument(res_, _conf, _nodes, _stack);
    }

    public static Struct newLambda(ExecLambdaCommonContent _common, ExecLambdaMethodContent _meth, Argument _previous, ContextEl _conf, ExecFormattedRootBlock _ownerType,
                                   String _clArg) {
        String from_ = StringExpUtil.getPrettyArrayType(_conf.getStandards().getContent().getCoreNames().getAliasObject());
        MethodModifier met_ = MethodModifier.NORMAL;
        MethodMetaInfo metaInfo_ = new MethodMetaInfo(_common,_ownerType,from_, _meth.getMethod(), met_,false);
        return new LambdaMethodStruct(metaInfo_,_previous,_common,_meth,_clArg);
    }


}
