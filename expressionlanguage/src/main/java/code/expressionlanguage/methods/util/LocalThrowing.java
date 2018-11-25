package code.expressionlanguage.methods.util;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Templates;
import code.expressionlanguage.calls.AbstractPageEl;
import code.expressionlanguage.methods.AbstractCatchEval;
import code.expressionlanguage.methods.Block;
import code.expressionlanguage.methods.CallingFinally;
import code.expressionlanguage.methods.CatchEval;
import code.expressionlanguage.methods.FinallyEval;
import code.expressionlanguage.methods.NullCatchEval;
import code.expressionlanguage.methods.TryEval;
import code.expressionlanguage.stacks.RemovableVars;
import code.expressionlanguage.stacks.TryBlockStack;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.Struct;
import code.expressionlanguage.variables.LocalVariable;

public final class LocalThrowing implements CallingFinally {

    @Override
    public void removeBlockFinally(ContextEl _conf) {
        LgNames lgNames_ = _conf.getStandards();
        AbstractCatchEval catchElt_ = null;
        while (!_conf.isEmptyPages()) {
            Struct custCause_ = _conf.getException();
            AbstractPageEl bkIp_ = _conf.getLastPage();
            while (!bkIp_.noBlock()) {
                RemovableVars bl_ = bkIp_.getLastStack();
                if (!(bl_ instanceof TryBlockStack)) {
                    bl_.removeVarAndLoop(bkIp_);
                    continue;
                }
                TryBlockStack try_ = (TryBlockStack)bl_;
                boolean addFinallyClause_ = true;
                if (!(try_.getLastBlock() instanceof FinallyEval)) {
                    addFinallyClause_ = false;
                }
                Block currentBlock_ = try_.getCurrentVisitedBlock();
                if (!(currentBlock_ instanceof TryEval)) {
                    if (!(currentBlock_ instanceof FinallyEval)) {
                        if (addFinallyClause_) {
                            try_.setException(custCause_);
                            try_.setCalling(this);
                            _conf.setException(null);
                            bkIp_.clearCurrentEls();
                            bkIp_.getReadWrite().setBlock(try_.getLastBlock());
                            return;
                        }
                    }
                    bkIp_.removeLastBlock();
                    continue;
                }
                Block n_ = currentBlock_.getNextSibling();
                //process try block
                while (n_ instanceof AbstractCatchEval) {
                    if (n_ instanceof CatchEval) {
                        CatchEval ca_ = (CatchEval) n_;
                        String name_ = ca_.getImportedClassName();
                        if (custCause_.isNull()) {
                            n_ = n_.getNextSibling();
                            continue;
                        }
                        String excepClass_ = lgNames_.getStructClassName(custCause_, _conf);
                        name_ = bkIp_.formatVarType(name_, _conf);
                        if (Templates.isCorrectExecute(excepClass_, name_ , _conf)) {
                            catchElt_ = ca_;
                            try_.setCurrentBlock(ca_);
                            break;
                        }
                    } else {
                        NullCatchEval ca_ = (NullCatchEval) n_;
                        if (custCause_.isNull()) {
                            catchElt_ = ca_;
                            try_.setCurrentBlock(ca_);
                            break;
                        }
                    }
                    n_ = n_.getNextSibling();
                }
                if (catchElt_ != null) {
                    Block catchElement_ = catchElt_;
                    try_.setCalling(null);
                    _conf.setException(null);
                    bkIp_.clearCurrentEls();
                    Block childCatch_ = catchElement_.getFirstChild();
                    if (childCatch_ != null) {
                        if (catchElement_ instanceof CatchEval) {
                            CatchEval c_ = (CatchEval) catchElement_;
                            String var_ = c_.getVariableName();
                            LocalVariable lv_ = new LocalVariable();
                            lv_.setStruct(custCause_);
                            lv_.setClassName(c_.getImportedClassName());
                            bkIp_.getCatchVars().put(var_, lv_);
                        }
                        bkIp_.getReadWrite().setBlock(childCatch_);
                        return;
                    }
                    bkIp_.getReadWrite().setBlock(catchElement_);
                    return;
                }
                if (addFinallyClause_) {
                    try_.setCalling(this);
                    _conf.setException(null);
                    try_.setException(custCause_);
                    bkIp_.clearCurrentEls();
                    bkIp_.getReadWrite().setBlock(try_.getLastBlock());
                    return;
                }
                bkIp_.removeLastBlock();
            }
            _conf.getClasses().getLocks().processErrorClass(_conf, custCause_);
            _conf.removeLastPage();
        }
    }

}
