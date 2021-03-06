package code.expressionlanguage.analyze;

import code.expressionlanguage.analyze.util.ContextUtil;

public final class DefaultBuildingConstraints implements AbstractBuildingConstraints {
    private final AnalyzedPageEl context;

    public DefaultBuildingConstraints(AnalyzedPageEl _context) {
        context = _context;
    }

    @Override
    public void buildCurrentConstraintsFull() {
        ContextUtil.buildCurrentConstraintsFull(context);
    }
}
