package code.formathtml.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.opers.ExecTernaryOperation;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.formathtml.Configuration;
import code.formathtml.util.BeanLgNames;
import code.util.CustList;
import code.util.IdMap;

public final class RendTernaryOperation extends RendMethodOperation implements RendCalculableOperation {

    private int offsetLocal;

    public RendTernaryOperation(ExecOperationContent _content, int _offsetLocal) {
        super(_content);
        offsetLocal = _offsetLocal;
    }

    @Override
    public void calculate(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, Configuration _conf, BeanLgNames _advStandards, ContextEl _context) {
        CustList<Argument> arguments_ = getArguments(_nodes,this);
        Argument res_ = getArgument(arguments_, _conf);
        setSimpleArgument(res_, _conf,_nodes, _context);
    }

    Argument  getArgument(CustList<Argument> _arguments, Configuration _conf) {
        setRelativeOffsetPossibleLastPage(getIndexInEl()+offsetLocal, _conf);
        return ExecTernaryOperation.getArgument(_arguments);
    }
}
