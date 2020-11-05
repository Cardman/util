package code.formathtml.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.ErrorType;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.expressionlanguage.fwd.opers.ExecTypeCheckContent;
import code.expressionlanguage.structs.BooleanStruct;
import code.formathtml.Configuration;
import code.formathtml.util.BeanLgNames;
import code.util.CustList;
import code.util.IdMap;

public final class RendInstanceOfOperation extends RendAbstractUnaryOperation {

    private ExecTypeCheckContent typeCheckContent;
    public RendInstanceOfOperation(ExecOperationContent _content, ExecTypeCheckContent _typeCheckContent) {
        super(_content);
        typeCheckContent = _typeCheckContent;
    }

    @Override
    public void calculate(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, Configuration _conf, BeanLgNames _advStandards, ContextEl _context) {
        CustList<Argument> arguments_ = getArguments(_nodes,this);
        Argument argres_ = getArgument(arguments_, _conf, _context);
        setSimpleArgument(argres_, _conf,_nodes, _context);
    }

    Argument getArgument(CustList<Argument> _arguments, Configuration _conf, ContextEl _context) {
        setRelativeOffsetPossibleLastPage(getIndexInEl()+ typeCheckContent.getOffset(), _conf);
        Argument objArg_ = ExecTemplates.getFirstArgument(_arguments);
        if (objArg_.isNull()) {
            return new Argument(BooleanStruct.of(false));
        }
        String str_ = typeCheckContent.getClassName();
        boolean res_ = ExecTemplates.safeObject(str_, objArg_, _context) == ErrorType.NOTHING;
        return new Argument(BooleanStruct.of(res_));
    }
}
