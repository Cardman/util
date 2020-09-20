package code.expressionlanguage.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.DefaultExiting;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.exec.ClassCategory;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.analyze.opers.ValuesOperation;
import code.util.IdMap;

public final class ExecValuesOperation extends ExecLeafOperation implements
        AtomicExecCalculableOperation {

    private int argOffset;
    private ExecRootBlock rootBlock;

    public ExecValuesOperation(ValuesOperation _v, AnalyzedPageEl _page) {
        super(_v);
        argOffset = _v.getArgOffset();
        rootBlock = fetchType(_v.getNumberEnum(), _page);
    }


    @Override
    public void calculate(IdMap<ExecOperationNode, ArgumentsPair> _nodes,
                          ContextEl _conf) {
        Argument arg_ = getCommonArgument(_conf);
        setSimpleArgument(arg_, _conf, _nodes);
    }
    Argument getCommonArgument(ContextEl _conf) {
        setRelativeOffsetPossibleLastPage(getIndexInEl()+argOffset, _conf);
        return ExecInvokingOperation.tryGetEnumValues(new DefaultExiting(_conf), _conf,rootBlock,ClassCategory.ENUM);
    }

}
