package code.formathtml;

import code.expressionlanguage.files.OffsetsBlock;

public abstract class RendParentBlock extends RendBlock {

    private RendBlock firstChild;
    RendParentBlock(OffsetsBlock _offset) {
        super(_offset);
    }

    public final void appendChild(RendBlock _child) {
        _child.setParent(this);
        if (firstChild == null) {
            firstChild = _child;
            return;
        }
        RendBlock child_ = firstChild;
        while (true) {
            RendBlock sibling_ = child_.getNextSibling();
            if (sibling_ == null) {
                _child.setPreviousSibling(child_);
                child_.setNextSibling(_child);
                return;
            }
            child_ = sibling_;
        }
    }

    @Override
    public final RendBlock getFirstChild() {
        return firstChild;
    }

    public final void removeLocalVars(ImportingPage _ip) {
        for (RendBlock s: getDirectChildren(this)) {
            if (s instanceof RendInitVariable) {
                for (String v: ((RendInitVariable)s).getVariableNames()) {
                    _ip.removeLocalVar(v);
                }
            }
        }
    }

    public void exitStack(Configuration _conf) {
        //overrides
    }

    public void removeVarAndLoop(ImportingPage _ip) {
        _ip.removeRendLastBlock();
    }
}
