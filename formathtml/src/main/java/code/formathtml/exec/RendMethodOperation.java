package code.formathtml.exec;
import code.expressionlanguage.opers.exec.Operable;
import code.util.CustList;

public abstract class RendMethodOperation extends RendDynOperationNode {

    private RendDynOperationNode firstChild;

    public RendMethodOperation(Operable _m) {
        super(_m);
    }

    public final void appendChild(RendDynOperationNode _child) {
        _child.setParent(this);
        if (firstChild == null) {
            firstChild = _child;
            return;
        }
        RendDynOperationNode child_ = firstChild;
        while (true) {
            RendDynOperationNode sibling_ = child_.getNextSibling();
            if (sibling_ == null) {
                child_.setNextSibling(_child);
                return;
            }
            child_ = sibling_;
        }
    }

    public final CustList<RendDynOperationNode> getChildrenNodes() {
        CustList<RendDynOperationNode> list_ = new CustList<RendDynOperationNode>();
        RendDynOperationNode firstChild_ = getFirstChild();
        RendDynOperationNode elt_ = firstChild_;
        while (elt_ != null) {
            list_.add(elt_);
            elt_ = elt_.getNextSibling();
        }
        return list_;
    }

    public final RendDynOperationNode getFirstChild() {
        return firstChild;
    }

}
