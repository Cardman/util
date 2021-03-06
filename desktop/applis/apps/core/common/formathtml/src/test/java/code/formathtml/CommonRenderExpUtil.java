package code.formathtml;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.opers.OperationNode;
import code.formathtml.exec.opers.RendDynOperationNode;
import code.formathtml.fwd.RendForwardInfos;
import code.util.CustList;

public abstract class CommonRenderExpUtil extends CommonRender {
    protected static CustList<RendDynOperationNode> getQuickExecutableNodes(AnalyzedTestConfiguration _an, CustList<OperationNode> _ops) {
        OperationNode root_ = _ops.last();
        CustList<RendDynOperationNode> executableNodes_ = RendForwardInfos.getExecutableNodes(root_, _an.getForwards());
        return CommonRender.getReducedNodes(executableNodes_.last());
    }

    protected static ContextEl getGenerate(AnalyzedTestConfiguration _an) {
        ContextEl ctx_ = _an.generate();
        _an.setContext(ctx_);
        return ctx_;
    }

}
