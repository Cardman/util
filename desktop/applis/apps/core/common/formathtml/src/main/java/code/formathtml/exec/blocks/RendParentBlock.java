package code.formathtml.exec.blocks;

import code.formathtml.ImportingPage;

public abstract class RendParentBlock extends RendBlock {

    private RendBlock firstChild;
    protected RendParentBlock(int _offsetTrim) {
        super(_offsetTrim);
    }
    public static boolean isNextIfParts(RendBlock _n) {
        return isStrictNextIfParts(_n) || _n instanceof RendPossibleEmpty;
    }

    public static boolean isStrictNextIfParts(RendBlock _n) {
        return _n instanceof RendElseIfCondition || _n instanceof RendElseCondition;
    }

    public static boolean isNextTryParts(RendBlock _n) {
        return isStrictNextTryParts(_n) || _n instanceof RendPossibleEmpty;
    }

    public static boolean isStrictNextTryParts(RendBlock _n) {
        return _n instanceof RendAbstractCatchEval || _n instanceof RendFinallyEval;
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
            if (s instanceof RendDeclareVariable) {
                for (String v: ((RendDeclareVariable)s).getVariableNames()) {
                    _ip.removeLocalVar(v);
                }
            }
        }
    }

    public void removeAllVars(ImportingPage _ip) {
        removeLocalVars(_ip);
    }

}
