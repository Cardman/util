package code.expressionlanguage.opers.exec;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ExecutableCode;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.opers.ArrayFieldOperation;
import code.expressionlanguage.structs.ArrayStruct;
import code.expressionlanguage.structs.ErrorStruct;
import code.expressionlanguage.structs.IntStruct;
import code.expressionlanguage.structs.Struct;
import code.util.StringList;

public final class ExecArrayFieldOperation extends ExecAbstractFieldOperation {

    public ExecArrayFieldOperation(ArrayFieldOperation _a) {
        super(_a);
    }

    @Override
    Argument getCommonArgument(Argument _previous, ExecutableCode _conf) {
        Argument a_;
        setRelativeOffsetPossibleLastPage(getIndexInEl()+getOff(), _conf);
        Struct inst_ = _previous.getStruct();
        if (inst_ instanceof ArrayStruct) {
            ArrayStruct arr_ = (ArrayStruct) inst_;
            a_ = new Argument();
            a_.setStruct(new IntStruct(arr_.getInstance().length));
            return a_;
        }
        String npe_;
        npe_ = _conf.getStandards().getAliasNullPe();
        setRelativeOffsetPossibleLastPage(getIndexInEl(), _conf);
        String argCl_ = _previous.getObjectClassName(_conf.getContextEl());
        String arrObj_ = _conf.getStandards().getAliasObject();
        arrObj_ = PrimitiveTypeUtil.getPrettyArrayType(arrObj_);
        _conf.setException(new ErrorStruct(_conf, StringList.concat(argCl_,RETURN_LINE,arrObj_,RETURN_LINE),npe_));
        a_ = new Argument();
        return a_;
    }

}
