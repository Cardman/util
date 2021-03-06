package code.formathtml.util;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.fwd.Forwards;

public final class DualAnalyzedContext {
    private final AnalyzedPageEl analyzed;
    private final BeanLgNames stds;
    private final DualConfigurationContext context;
    private final Forwards forwards;

    public DualAnalyzedContext(Forwards _forwards,AnalyzedPageEl _analyzed, BeanLgNames _stds,DualConfigurationContext _context) {
        forwards = _forwards;
        analyzed = _analyzed;
        stds = _stds;
        context = _context;
    }

    public Forwards getForwards() {
        return forwards;
    }

    public DualConfigurationContext getContext() {
        return context;
    }

    public BeanLgNames getStds() {
        return stds;
    }

    public AnalyzedPageEl getAnalyzed() {
        return analyzed;
    }
}
