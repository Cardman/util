package code.formathtml;

import code.expressionlanguage.errors.custom.UnexpectedTagName;
import code.expressionlanguage.files.OffsetsBlock;
import code.formathtml.util.RendIfStack;
import code.formathtml.util.RendReadWrite;

public final class RendElseCondition extends RendParentBlock implements RendWithEl, RendReducableOperations, RendBuildableElMethod,RendBreakableBlock {

    RendElseCondition(OffsetsBlock _offset) {
        super(_offset);
    }

    @Override
    public String getRealLabel() {
        RendBlock p_ = getPreviousSibling();
        while (!(p_ instanceof RendIfCondition)) {
            if (p_ == null) {
                return EMPTY_STRING;
            }
            p_ = p_.getPreviousSibling();
        }
        return ((RendIfCondition)p_).getLabel();
    }

    @Override
    public void buildExpressionLanguage(Configuration _cont,RendDocumentBlock _doc) {
        RendBlock pBlock_ = getPreviousSibling();
        if (!(pBlock_ instanceof RendIfCondition)) {
            if (!(pBlock_ instanceof RendElseIfCondition)) {
                if (!(pBlock_ instanceof RendPossibleEmpty)) {
                    UnexpectedTagName un_ = new UnexpectedTagName();
                    un_.setFileName(_cont.getCurrentFileName());
                    un_.setIndexFile(getOffset().getOffsetTrim());
                    _cont.getClasses().addError(un_);
                } else if (!(pBlock_.getPreviousSibling() instanceof RendIfCondition)){
                    if (!(pBlock_.getPreviousSibling() instanceof RendElseIfCondition)){
                        UnexpectedTagName un_ = new UnexpectedTagName();
                        un_.setFileName(_cont.getCurrentFileName());
                        un_.setIndexFile(getOffset().getOffsetTrim());
                        _cont.getClasses().addError(un_);
                    }
                }
            }
        }
    }

    @Override
    public void reduce(Configuration _context) {

    }

    @Override
    public void processEl(Configuration _cont) {
        ImportingPage ip_ = _cont.getLastPage();
        RendIfStack if_ = (RendIfStack) ip_.getRendLastStack();
        if_.setCurentVisitedBlock(this);
        if (!if_.isEntered()) {
            if_.setEntered(true);
            ip_.getRendReadWrite().setRead(getFirstChild());
            return;
        }
        ip_.removeRendLastBlock();
        processBlock(_cont);
    }

    @Override
    public void exitStack(Configuration _conf) {
        ImportingPage ip_ = _conf.getLastPage();
        RendReadWrite rw_ = ip_.getRendReadWrite();
        rw_.setRead(this);
    }
}
