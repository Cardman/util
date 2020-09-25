package code.formathtml.exec;

import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.exec.blocks.ExecNamedFunctionBlock;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.analyze.opers.ExplicitOperatorOperation;
import code.expressionlanguage.exec.opers.ExecInvokingOperation;
import code.expressionlanguage.functionid.MethodAccessKind;
import code.expressionlanguage.fwd.blocks.ForwardInfos;
import code.formathtml.Configuration;
import code.formathtml.util.AdvancedExiting;
import code.util.CustList;
import code.util.IdMap;

public final class RendExplicitOperatorOperation extends RendInvokingOperation implements RendCalculableOperation,RendCallable {

    private String lastType;

    private int naturalVararg;

    private MethodAccessKind kind;
    private String className;
    private ExecNamedFunctionBlock named;
    private ExecRootBlock rootBlock;
    private int offsetOper;
    public RendExplicitOperatorOperation(ExplicitOperatorOperation _fct, AnalyzedPageEl _page) {
        super(_fct);
        named = ForwardInfos.fetchFunctionOp(_fct.getRootNumber(),_fct.getMemberNumber(), _page);
        rootBlock = ForwardInfos.fetchType(_fct.getRootNumber(), _page);
        kind = ForwardInfos.getKind(_fct.getClassMethodId());
        className = ForwardInfos.getType(_fct.getClassMethodId());
        lastType = _fct.getCallFctContent().getLastType();
        naturalVararg = _fct.getCallFctContent().getNaturalVararg();
        offsetOper = _fct.getOffsetOper();
    }

    @Override
    public void calculate(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, Configuration _conf) {
        setRelativeOffsetPossibleLastPage(getIndexInEl()+offsetOper, _conf);
        Argument argres_ = processCall(this, this, Argument.createVoid(),_nodes, _conf, null);
        setSimpleArgument(argres_,_conf,_nodes);
    }

    @Override
    public Argument getArgument(Argument _previous, IdMap<RendDynOperationNode, ArgumentsPair> _all, Configuration _conf, Argument _right) {
        CustList<RendDynOperationNode> chidren_ = getChildrenNodes();
        CustList<Argument> first_ = RendInvokingOperation.listNamedArguments(_all, chidren_).getArguments();
        CustList<Argument> firstArgs_ = listArguments(chidren_, naturalVararg, lastType, first_);
        ExecInvokingOperation.checkParametersOperators(new AdvancedExiting(_conf),_conf.getContext(), rootBlock,named, firstArgs_, className, kind);
        return Argument.createVoid();
    }
}
