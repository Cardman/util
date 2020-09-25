package code.expressionlanguage.exec.opers;
import code.expressionlanguage.exec.types.ExecClassArgumentMatching;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.util.CustList;

public abstract class ExecMethodOperation extends ExecOperationNode {

    private ExecOperationNode firstChild;

    protected ExecMethodOperation(ExecOperationContent _m) {
        super(_m);
    }

    public ExecMethodOperation(int _indexChild, ExecClassArgumentMatching _res, int _order) {
        super(_indexChild,_res,_order);
    }

    public final void appendChild(ExecOperationNode _child) {
        _child.setParent(this);
        if (firstChild == null) {
            firstChild = _child;
            return;
        }
        ExecOperationNode child_ = firstChild;
        while (true) {
            ExecOperationNode sibling_ = child_.getNextSibling();
            if (sibling_ == null) {
                child_.setNextSibling(_child);
                return;
            }
            child_ = sibling_;
        }
    }

    public final CustList<ExecOperationNode> getChildrenNodes() {
        CustList<ExecOperationNode> list_ = new CustList<ExecOperationNode>();
        ExecOperationNode elt_ = getFirstChild();
        while (elt_ != null) {
            list_.add(elt_);
            elt_ = elt_.getNextSibling();
        }
        return list_;
    }

    @Override
    public final ExecOperationNode getFirstChild() {
        return firstChild;
    }

}
