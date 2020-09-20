package code.expressionlanguage.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.exec.calls.util.InstancingStep;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.analyze.opers.InterfaceInvokingConstructor;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.util.CustList;
import code.util.IdMap;

public final class ExecInterfaceInvokingConstructor extends ExecAbstractInvokingConstructor {

    protected ExecInterfaceInvokingConstructor(InterfaceInvokingConstructor _int, AnalyzedPageEl _page) {
        super(_int, _page);
    }

    @Override
    public void calculate(IdMap<ExecOperationNode, ArgumentsPair> _nodes,
                          ContextEl _conf) {
        Argument res_ = getArgument(_nodes, _conf);
        setSimpleArgument(res_, _conf, _nodes);
    }

    Argument getArgument(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf) {
        CustList<ExecOperationNode> chidren_ = getChildrenNodes();
        int off_ = getOffsetOper();
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off_, _conf);

        CustList<Argument> firstArgs_;
        String superClass_ = _conf.getLastPage().formatVarType(getClassFromName(),_conf);
        String lastType_ = getLastType();
        lastType_ = ExecTemplates.quickFormat(getRootBlock(),superClass_, lastType_);
        int natvararg_ = getNaturalVararg();
        CustList<Argument> first_ = listNamedArguments(_nodes, chidren_).getArguments();
        firstArgs_ = listArguments(chidren_, natvararg_, lastType_, first_);
        checkParametersCtors(_conf, superClass_, getRootBlock(),getCtor(),  firstArgs_, InstancingStep.USING_SUPER);
        return Argument.createVoid();
    }

}
