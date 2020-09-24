package code.formathtml.exec;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.analyze.opers.ArrayFieldOperation;
import code.expressionlanguage.structs.ArrayStruct;
import code.expressionlanguage.structs.ErrorStruct;
import code.expressionlanguage.structs.IntStruct;
import code.expressionlanguage.structs.Struct;
import code.formathtml.Configuration;
import code.util.StringList;

public final class RendArrayFieldOperation extends RendAbstractFieldOperation {

    public RendArrayFieldOperation(ArrayFieldOperation _a) {
        super(_a);
    }

    @Override
    Argument getCommonArgument(Argument _previous, Configuration _conf) {
        setRelativeOffsetPossibleLastPage(getIndexInEl()+getOff(), _conf);
        Struct inst_ = _previous.getStruct();
        if (inst_ instanceof ArrayStruct) {
            ArrayStruct arr_ = (ArrayStruct) inst_;
            return new Argument(new IntStruct(arr_.getInstance().length));
        }
        ContextEl _context = _conf.getContext();
        String npe_;
        npe_ = _context.getStandards().getAliasNullPe();
        setRelativeOffsetPossibleLastPage(getIndexInEl(), _conf);
        _conf.setException(new ErrorStruct(_conf.getContext(),npe_));
        return new Argument();
    }
}
