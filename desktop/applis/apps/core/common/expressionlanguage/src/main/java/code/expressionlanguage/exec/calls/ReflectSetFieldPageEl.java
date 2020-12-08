package code.expressionlanguage.exec.calls;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.calls.util.CustomFoundExc;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.ErrorStruct;
import code.expressionlanguage.structs.FieldMetaInfo;

public final class ReflectSetFieldPageEl extends AbstractReflectPageEl {

    private boolean initClass;
    private FieldMetaInfo metaInfo;

    private final Argument first;
    private final Argument last;

    public ReflectSetFieldPageEl(Argument _first, Argument _last, FieldMetaInfo _metaInfo) {
        first = _first;
        last = _last;
        setGlobalArgumentStruct(_metaInfo);
        metaInfo = _metaInfo;
    }

    @Override
    public boolean checkCondition(ContextEl _context) {
        LgNames stds_ = _context.getStandards();
        if (!initClass) {
            initClass = true;
            if (metaInfo.isStaticField()) {
                String baseClass_ = metaInfo.getDeclaringClass();
                baseClass_ = StringExpUtil.getIdFromAllTypes(baseClass_);
                if (_context.getExiting().hasToExit(baseClass_)) {
                    setWrapException(true);
                    return false;
                }
            }
        }
        setWrapException(false);
        String baseClass_ = metaInfo.getDeclaringClass();
        if (stds_.getStandards().contains(baseClass_)) {
            String ill_;
            ill_ = stds_.getContent().getCoreNames().getAliasIllegalArg();
            _context.setCallingState(new CustomFoundExc(new ErrorStruct(_context, ill_)));
            return false;
        }
        Argument arg_ = ExecTemplates.setField(metaInfo, first, last, _context);
        if (_context.callsOrException()) {
            return false;
        }
        setReturnedArgument(arg_);
        return true;
    }

}
