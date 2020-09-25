package code.formathtml.exec;

import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.exec.ClassCategory;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.analyze.opers.EnumValueOfOperation;
import code.expressionlanguage.exec.opers.ExecInvokingOperation;
import code.expressionlanguage.fwd.blocks.ForwardInfos;
import code.formathtml.Configuration;
import code.formathtml.util.AdvancedExiting;
import code.util.CustList;
import code.util.IdMap;

public final class RendEnumValueOfOperation extends RendAbstractUnaryOperation implements RendCallable {

    private int argOffset;
    private ExecRootBlock rootBlock;

    public RendEnumValueOfOperation(EnumValueOfOperation _e, AnalyzedPageEl _page) {
        super(_e);
        argOffset = _e.getValuesContent().getArgOffset();
        rootBlock = ForwardInfos.fetchType(_e.getValuesContent().getNumberEnum(), _page);
    }


    @Override
    public void calculate(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, Configuration _conf) {
        Argument argres_ = processCall(this, this, Argument.createVoid(),_nodes, _conf, null);
        setSimpleArgument(argres_,_conf,_nodes);
    }

    Argument getCommonArgument(Argument _argument, Configuration _conf) {
        setRelativeOffsetPossibleLastPage(getIndexInEl()+argOffset, _conf);
        return ExecInvokingOperation.tryGetEnumValue(new AdvancedExiting(_conf),_conf.getContext(),rootBlock, ClassCategory.ENUM,_argument);
    }

    @Override
    public Argument getArgument(Argument _previous, IdMap<RendDynOperationNode, ArgumentsPair> _all, Configuration _conf, Argument _right) {
        CustList<RendDynOperationNode> list_ = getChildrenNodes();
        CustList<Argument> first_ = RendInvokingOperation.listNamedArguments(_all, list_).getArguments();
        return getCommonArgument(first_.first(),_conf);
    }
}
