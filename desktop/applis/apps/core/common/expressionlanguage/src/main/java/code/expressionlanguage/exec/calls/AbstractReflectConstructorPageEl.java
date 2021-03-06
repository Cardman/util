package code.expressionlanguage.exec.calls;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.GeneType;
import code.expressionlanguage.exec.MetaInfoUtil;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.calls.util.CustomFoundExc;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.exec.util.ExecFormattedRootBlock;
import code.expressionlanguage.exec.variables.AbstractWrapper;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.ErrorStruct;

public abstract class AbstractReflectConstructorPageEl extends AbstractReflectPageEl {
    private boolean initClass;

    @Override
    public void receive(AbstractWrapper _wrap, Argument _argument, ContextEl _context, StackCall _stack) {
        setWrapper(_wrap);
        setReturnedArgument(_argument);
    }
    @Override
    public void processTagsBase(ContextEl _context, StackCall _stack) {
        if (!checkCondition(_context, _stack)) {
            return;
        }
        setNullReadWrite();
    }
    protected boolean keep(GeneType _gene,ContextEl _context, StackCall _stackCall) {
        LgNames stds_ = _context.getStandards();
        GeneType type_ = getFormatted().getRootBlock();
        if (!initClass) {
            initClass = true;
            boolean static_ = !MetaInfoUtil.isAbstractType(_gene) && _gene.withoutInstance();
            if (static_ && _context.getExiting().hasToExit(_stackCall, type_)) {
                setWrapException(true);
                return false;
            }
        }
        String className_ = getFormatted().getFormatted();
        if (MetaInfoUtil.isAbstractType(_gene)) {
            String null_ = stds_.getContent().getCoreNames().getAliasAbstractTypeErr();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_context, className_, null_, _stackCall)));
            setWrapException(false);
            return false;
        }
        String res_ = ExecTemplates.correctClassPartsDynamicWildCard(className_,_context);
        if (res_.isEmpty()) {
            String null_;
            null_ = stds_.getContent().getCoreNames().getAliasIllegalType();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_context, className_, null_, _stackCall)));
            setWrapException(false);
            return false;
        }
        return true;
    }
    public abstract boolean checkCondition(ContextEl _context, StackCall _stack);

    protected boolean end(ContextEl _context, StackCall _stack, Argument _arg) {
        if (_context.callsOrException(_stack)) {
            setWrapException(_stack.calls());
            return false;
        }
        setReturnedArgument(_arg);
        return true;
    }
    protected abstract ExecFormattedRootBlock getFormatted();
}
