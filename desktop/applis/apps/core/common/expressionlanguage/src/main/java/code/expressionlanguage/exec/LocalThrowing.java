package code.expressionlanguage.exec;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.blocks.*;
import code.expressionlanguage.exec.calls.AbstractPageEl;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.exec.stacks.AbruptCallingFinally;
import code.expressionlanguage.exec.stacks.ExceptionCallingFinally;
import code.expressionlanguage.exec.stacks.RemovableVars;
import code.expressionlanguage.structs.NullStruct;
import code.expressionlanguage.structs.Struct;
import code.expressionlanguage.exec.variables.LocalVariable;

public final class LocalThrowing implements CallingFinally {

    @Override
    public void removeBlockFinally(ContextEl _conf) {
        Struct custCause_ = (Struct) _conf.getCallingState();
        ExecAbstractCatchEval catchElt_ = null;
        while (_conf.hasPages()) {
            AbstractPageEl bkIp_ = _conf.getLastPage();
            bkIp_.clearCurrentEls();
            _conf.setException(null);
            while (bkIp_.hasBlock()) {
                RemovableVars bl_ = bkIp_.getLastStack();
                ExecBlock currentBlock_ = bl_.getCurrentVisitedBlock();
                if (currentBlock_ instanceof ExecTryEval) {
                    ExecBlock n_ = currentBlock_.getNextSibling();
                    //process try block
                    while (n_ instanceof ExecAbstractCatchEval) {
                        if (n_ instanceof ExecCatchEval) {
                            ExecCatchEval ca_ = (ExecCatchEval) n_;
                            String name_ = ca_.getImportedClassName();
                            if (custCause_ == NullStruct.NULL_VALUE) {
                                n_ = n_.getNextSibling();
                                continue;
                            }
                            name_ = bkIp_.formatVarType(name_, _conf);
                            Argument arg_ = new Argument(custCause_);
                            if (ExecTemplates.safeObject(name_, arg_, _conf) == ErrorType.NOTHING) {
                                catchElt_ = ca_;
                                bl_.setCurrentVisitedBlock(ca_);
                                break;
                            }
                        } else {
                            ExecNullCatchEval ca_ = (ExecNullCatchEval) n_;
                            if (custCause_ == NullStruct.NULL_VALUE) {
                                catchElt_ = ca_;
                                bl_.setCurrentVisitedBlock(ca_);
                                break;
                            }
                        }
                        n_ = n_.getNextSibling();
                    }
                    if (catchElt_ != null) {
                        _conf.getCoverage().passCatches(_conf,catchElt_);
                        ExecBlock childCatch_ = catchElt_.getFirstChild();
                        if (catchElt_ instanceof ExecCatchEval) {
                            ExecCatchEval c_ = (ExecCatchEval) catchElt_;
                            String var_ = c_.getVariableName();
                            LocalVariable lv_ = LocalVariable.newLocalVariable(custCause_,_conf);
                            bkIp_.getCatchVars().put(var_, lv_);
                        }
                        bkIp_.getReadWrite().setBlock(childCatch_);
                        return;
                    }
                }
                if (AbstractPageEl.setRemovedCallingFinallyToProcess(bkIp_,bl_,this,custCause_)) {
                    return;
                }
            }
            custCause_ = _conf.getClasses().getLocks().processErrorClass(_conf, custCause_);
            _conf.removeLastPage();
        }
        _conf.setException(custCause_);
    }


    @Override
    public AbruptCallingFinally newAbruptCallingFinally(Struct _struct) {
        return new ExceptionCallingFinally(this,_struct);
    }
}
