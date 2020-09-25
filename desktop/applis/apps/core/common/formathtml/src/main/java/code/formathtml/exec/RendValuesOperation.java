package code.formathtml.exec;

import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.exec.ClassCategory;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.analyze.opers.ValuesOperation;
import code.expressionlanguage.exec.opers.ExecInvokingOperation;
import code.expressionlanguage.fwd.blocks.ForwardInfos;
import code.formathtml.Configuration;
import code.formathtml.util.AdvancedExiting;
import code.util.IdMap;

public final class RendValuesOperation extends RendLeafOperation implements RendCalculableOperation,RendCallable {

    private int argOffset;
    private ExecRootBlock rootBlock;

    public RendValuesOperation(ValuesOperation _v, AnalyzedPageEl _page) {
        super(_v);
        argOffset = _v.getValuesContent().getArgOffset();
        rootBlock = ForwardInfos.fetchType(_v.getValuesContent().getNumberEnum(), _page);
    }

    @Override
    public void calculate(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, Configuration _conf) {
        Argument argres_ = processCall(this, this, Argument.createVoid(),_nodes, _conf, null);
        setSimpleArgument(argres_,_conf,_nodes);
    }

    Argument getCommonArgument(Configuration _conf) {
        setRelativeOffsetPossibleLastPage(getIndexInEl()+argOffset, _conf);
        return ExecInvokingOperation.tryGetEnumValues(new AdvancedExiting(_conf), _conf.getContext(),rootBlock,ClassCategory.ENUM);
    }

    @Override
    public Argument getArgument(Argument _previous, IdMap<RendDynOperationNode, ArgumentsPair> _all, Configuration _conf, Argument _right) {
        return getCommonArgument(_conf);
    }
}
