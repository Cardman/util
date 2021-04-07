package code.expressionlanguage.exec.calls;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.blocks.ExecAbstractSwitchMethod;
import code.expressionlanguage.exec.blocks.ExecBlock;
import code.expressionlanguage.exec.blocks.WithEl;
import code.expressionlanguage.exec.stacks.SwitchBlockStack;
import code.expressionlanguage.fwd.blocks.ExecTypeFunction;

public abstract class AbstractMethodPageEl extends AbstractCommonMethodPageEl {

    protected AbstractMethodPageEl(Argument _gl, String _glClass) {
        super(_gl, _glClass);
    }

    public void initReturnType(Argument _right) {
        if (_right != null) {
            setReturnedArgument(_right);
        }
    }

    public void blockRootType(ExecTypeFunction _pair) {
        setBlockRootType(_pair.getType());
        setBlockRoot(_pair.getFct());
    }
    @Override
    public void processTagsBase(ContextEl _context, StackCall _stack){
        //method walk through
        ExecBlock en_ = getBlock();
        processTagsBase(_context,_stack,en_);
    }

}
