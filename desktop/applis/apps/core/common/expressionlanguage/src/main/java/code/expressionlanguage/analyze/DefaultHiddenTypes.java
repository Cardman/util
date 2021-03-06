package code.expressionlanguage.analyze;

import code.expressionlanguage.analyze.accessing.Accessed;
import code.expressionlanguage.analyze.blocks.RootBlock;
import code.expressionlanguage.analyze.blocks.AccessingImportingBlock;

public final class DefaultHiddenTypes implements AbstractHiddenTypes {
    private final AnalyzedPageEl context;

    public DefaultHiddenTypes(AnalyzedPageEl _context) {
        this.context = _context;
    }

    @Override
    public boolean isHidden(AccessingImportingBlock _global, RootBlock _type) {
        Accessed a_ = new Accessed(_type.getAccess(), _type.getPackageName(), _type.getParentType(), _type);
        return _global.isTypeHidden(a_, context);
    }
}
