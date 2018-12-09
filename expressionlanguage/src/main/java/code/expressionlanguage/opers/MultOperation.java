package code.expressionlanguage.opers;
import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ExecutableCode;
import code.expressionlanguage.OperationsSequence;
import code.expressionlanguage.PrimitiveTypeUtil;
import code.expressionlanguage.errors.custom.UnexpectedTypeOperationError;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.opers.util.ResultOperand;
import code.expressionlanguage.structs.NumberStruct;
import code.util.StringList;


public final class MultOperation extends NumericOperation {

    public MultOperation(int _index,
            int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    Argument calculateOperAna(Argument _a, String _op, Argument _b,
            Analyzable _an) {
        if (_a.isNull() || _b.isNull()) {
            return Argument.createVoid();
        }
        if (_a.getObject() instanceof String || _b.getObject() instanceof String) {
            return Argument.createVoid();
        }
        if (StringList.quickEq(_op.trim(), MULT)) {
            return new Argument(NumberStruct.calculateMult((NumberStruct)_a.getStruct(),(NumberStruct) _b.getStruct(), _an, getResultClass()));
        }
        if (StringList.quickEq(_op.trim(), DIV)) {
            return new Argument(NumberStruct.calculateDiv((NumberStruct)_a.getStruct(),(NumberStruct) _b.getStruct(), _an, getResultClass()));
        }
        return new Argument(NumberStruct.calculateMod((NumberStruct)_a.getStruct(),(NumberStruct) _b.getStruct(), _an, getResultClass()));
    }

    @Override
    Argument calculateOper(Argument _a, String _op, Argument _b, ExecutableCode _cont) {
        if (StringList.quickEq(_op.trim(), MULT)) {
            return new Argument(NumberStruct.calculateMult((NumberStruct)_a.getStruct(),(NumberStruct) _b.getStruct(), _cont, getResultClass()));
        }
        if (StringList.quickEq(_op.trim(), DIV)) {
            return calculateDivEx(_a, _cont, _b, getResultClass());
        }
        return calculateModEx(_a, _cont, _b, getResultClass());
    }

    @Override
    ResultOperand analyzeOper(ClassArgumentMatching _a, String _op, ClassArgumentMatching _b, Analyzable _cont) {
        ResultOperand res_ = new ResultOperand();
        int oa_ = PrimitiveTypeUtil.getOrderClass(_a, _cont);
        int ob_ = PrimitiveTypeUtil.getOrderClass(_b, _cont);
        if (oa_ <= 0 || ob_ <= 0) {
            String exp_ = _cont.getStandards().getAliasNumber();
            UnexpectedTypeOperationError un_ = new UnexpectedTypeOperationError();
            un_.setIndexFile(_cont.getCurrentLocationIndex());
            un_.setFileName(_cont.getCurrentFileName());
            un_.setExpectedResult(exp_);
            un_.setOperands(_a,_b);
            _cont.getClasses().addError(un_);
            _cont.setOkNumOp(false);
            ClassArgumentMatching arg_ = new ClassArgumentMatching(exp_);
            res_.setResult(arg_);
            return res_;
        }
        ClassArgumentMatching out_ = getResultClass(_a, _cont, _b);
        _a.setUnwrapObject(out_);
        _b.setUnwrapObject(out_);
        res_.setResult(out_);
        return res_;
    }

    @Override
    void setCatenize(ResultOperand _res) {
    }

}
