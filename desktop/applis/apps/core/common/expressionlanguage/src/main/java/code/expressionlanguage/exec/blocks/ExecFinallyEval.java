package code.expressionlanguage.exec.blocks;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.StackCall;

public final class ExecFinallyEval extends ExecBracedBlock implements StackableBlock {

    @Override
    public void processEl(ContextEl _cont, StackCall _stack) {
        ExecHelperBlocks.processFinally(_cont,this, _stack);
    }
}
