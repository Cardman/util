package code.expressionlanguage.exec.calls;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.NumParsers;
import code.expressionlanguage.structs.MethodMetaInfo;
import code.util.CustList;

public final class PolymorphRefectMethodPageEl extends AbstractRefectMethodPageEl {

    public PolymorphRefectMethodPageEl(CustList<Argument> _arguments) {
        super(_arguments);
    }

    @Override
    boolean initType(ContextEl _cont) {
        return initDefault(_cont);
    }

    @Override
    boolean isAbstract(ContextEl _cont) {
        return false;
    }

    @Override
    boolean isPolymorph(ContextEl _cont) {
        MethodMetaInfo method_ = NumParsers.getMethod(getGlobalStruct());
        return !method_.isWideStatic();
    }
}
