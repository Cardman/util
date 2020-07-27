package code.expressionlanguage.analyze.opers.util;

import code.expressionlanguage.analyze.opers.*;

public final class ParentInferring {
    private final OperationNode operation;
    private final int nbParentsInfer;

    private ParentInferring(OperationNode operation, int nbParentsInfer) {
        this.operation = operation;
        this.nbParentsInfer = nbParentsInfer;
    }

    public static ParentInferring getParentInferring(OperationNode _from) {
        OperationNode current_;
        MethodOperation m_;
        int nbParentsInfer_ = 0;
        current_ = _from;
        m_ = _from.getParent();
        while (m_ != null) {
            if (!(m_ instanceof ElementArrayInstancing) && !(m_ instanceof InferArrayInstancing)) {
                if (m_ instanceof IdOperation) {
                    current_ = current_.getParent();
                    m_ = m_.getParent();
                    continue;
                }
                if (m_ instanceof AbstractTernaryOperation) {
                    if (m_.getFirstChild() == current_) {
                        break;
                    }
                    current_ = current_.getParent();
                    m_ = m_.getParent();
                    continue;
                }
                break;
            }
            nbParentsInfer_++;
            current_ = current_.getParent();
            m_ = m_.getParent();
        }
        return new ParentInferring(m_,nbParentsInfer_);
    }
    public OperationNode getOperation() {
        return operation;
    }

    public int getNbParentsInfer() {
        return nbParentsInfer;
    }
}
