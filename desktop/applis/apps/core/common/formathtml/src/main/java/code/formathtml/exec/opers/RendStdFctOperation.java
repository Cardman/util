package code.formathtml.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.ArgumentWrapper;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.expressionlanguage.fwd.opers.ExecStdFctContent;
import code.formathtml.exec.RendStackCall;
import code.formathtml.util.BeanLgNames;
import code.util.IdMap;
import code.util.core.StringUtil;

public final class RendStdFctOperation extends RendInvokingOperation implements RendCalculableOperation {

    private final ExecStdFctContent stdFctContent;

    public RendStdFctOperation(ExecOperationContent _content, boolean _intermediateDottedOperation, ExecStdFctContent _stdFctContent) {
        super(_content, _intermediateDottedOperation);
        stdFctContent = _stdFctContent;
    }

    @Override
    public void calculate(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, BeanLgNames _advStandards, ContextEl _context, RendStackCall _rendStack) {
        Argument previous_ = getPreviousArg(this, _nodes, _rendStack);
        int off_ = StringUtil.getFirstPrintableCharIndex(getMethodName());
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off_, _rendStack);
        ArgumentWrapper argres_ = RendDynOperationNode.processCall(_advStandards.getCommonFctArgument(this, previous_, _nodes, _context, _rendStack), _context, _rendStack);
        setSimpleArgument(argres_, _nodes, _context, _rendStack);
    }

    public ClassMethodId getClassMethodId() {
        return stdFctContent.getClassMethodId();
    }

    public int getNaturalVararg() {
        return stdFctContent.getNaturalVararg();
    }

    public String getLastType() {
        return stdFctContent.getLastType();
    }

    public String getMethodName() {
        return stdFctContent.getMethodName();
    }

    public boolean isStaticMethod() {
        return stdFctContent.isStaticMethod();
    }

}
