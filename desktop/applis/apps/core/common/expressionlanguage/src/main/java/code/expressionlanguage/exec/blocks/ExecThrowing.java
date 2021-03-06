package code.expressionlanguage.exec.blocks;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.calls.AbstractPageEl;
import code.expressionlanguage.exec.calls.util.CustomFoundExc;
import code.expressionlanguage.exec.opers.ExecOperationNode;
import code.expressionlanguage.exec.ExpressionLanguage;
import code.expressionlanguage.structs.Struct;
import code.util.CustList;
import code.util.core.IndexConstants;

public final class ExecThrowing extends ExecLeaf implements WithNotEmptyEl {


    private final ExecOperationNodeListOff exp;
    public ExecThrowing(int _expressionOffset, CustList<ExecOperationNode> _opThrow) {
        exp = new ExecOperationNodeListOff(_opThrow,_expressionOffset);
    }

    @Override
    public CustList<ExecOperationNode> getEl(ContextEl _context, int _indexProcess) {
        return exp.getList();
    }

    @Override
    public void processEl(ContextEl _cont, StackCall _stack) {
        AbstractPageEl ip_ = _stack.getLastPage();
        ip_.globalOffset(exp.getOffset());
        ExpressionLanguage el_ = ip_.getCurrentEl(_cont, this, IndexConstants.FIRST_INDEX, IndexConstants.FIRST_INDEX);
        Argument arg_ = ExpressionLanguage.tryToCalculate(_cont,el_,0, _stack);
        if (_cont.callsOrException(_stack)) {
            return;
        }
        ip_.clearCurrentEls();
        Struct o_ = arg_.getStruct();
        _stack.setCallingState(new CustomFoundExc(o_));
    }

}
