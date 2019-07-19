package code.formathtml;

import code.expressionlanguage.files.OffsetStringInfo;
import code.expressionlanguage.files.OffsetsBlock;
import code.formathtml.util.RendLoopBlockStack;
import code.formathtml.util.RendReadWrite;

public final class RendDoBlock extends RendParentBlock implements RendLoop {

    private String label;
    private int labelOffset;

    RendDoBlock(OffsetStringInfo _label, OffsetsBlock _offset) {
        super(_offset);
        label = _label.getInfo();
        labelOffset = _label.getOffset();
    }

    public String getLabel() {
        return label;
    }

    @Override
    public String getRealLabel() {
        return label;
    }

    public int getLabelOffset() {
        return labelOffset;
    }

    @Override
    public void processEl(Configuration _cont) {
        ImportingPage ip_ = _cont.getLastPage();
        RendReadWrite rw_ = ip_.getRendReadWrite();
        RendLoopBlockStack c_ = ip_.getLastLoopIfPossible();
        if (c_ != null && c_.getBlock() == this) {
            if (c_.isFinished()) {
                removeVarAndLoop(ip_);
                RendBlock next_ = getNextSibling();
                rw_.setRead(next_);
                next_.processBlock(_cont);
                return;
            }
            rw_.setRead(getFirstChild());
            return;
        }
        RendLoopBlockStack l_ = new RendLoopBlockStack();
        l_.setBlock(this);
        ip_.addBlock(l_);
        rw_.setRead(getFirstChild());
    }

    @Override
    public void exitStack(Configuration _context) {
        processLastElementLoop(_context);
    }

    @Override
    public void processLastElementLoop(Configuration _conf) {
        ImportingPage ip_ = _conf.getLastPage();
        RendReadWrite rw_ = ip_.getRendReadWrite();
        rw_.setRead(getNextSibling());
    }

    @Override
    public void buildExpressionLanguage(Configuration _cont,RendDocumentBlock _doc) {

    }
}
