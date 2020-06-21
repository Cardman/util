package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.inherits.ClassArgumentMatching;
import code.expressionlanguage.analyze.opers.util.ResultOperand;
import code.expressionlanguage.stds.AliasNumber;
import code.util.StringList;

public final class BitXorOperation extends NumericOperation {

    public BitXorOperation(int _index, int _indexChild, MethodOperation _m,
            OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    ResultOperand analyzeOper(ClassArgumentMatching _a, String _op,
            ClassArgumentMatching _b, ContextEl _cont) {
        ResultOperand res_ = new ResultOperand();
        if (_a.isBoolType(_cont) && _b.isBoolType(_cont)) {
            String bool_ = _cont.getStandards().getAliasPrimBoolean();
            _a.setUnwrapObject(bool_);
            _b.setUnwrapObject(bool_);
            res_.setResult(new ClassArgumentMatching(bool_));
            return res_;
        }
        int oa_ = PrimitiveTypeUtil.getIntOrderClass(_a, _cont);
        int ob_ = PrimitiveTypeUtil.getIntOrderClass(_b, _cont);
        if (oa_ > 0 && ob_ > 0) {
            ClassArgumentMatching out_ = getQuickResultClass(_a, oa_, _cont, _b, ob_);
            _a.setUnwrapObject(out_);
            _b.setUnwrapObject(out_);
            res_.setResult(out_);
            return res_;
        }
        _cont.getAnalyzing().setOkNumOp(false);
        String exp_ = _cont.getStandards().getAliasNumber();
        FoundErrorInterpret un_ = new FoundErrorInterpret();
        un_.setIndexFile(_cont.getAnalyzing().getLocalizer().getCurrentLocationIndex());
        un_.setFileName(_cont.getAnalyzing().getLocalizer().getCurrentFileName());
        //oper
        un_.buildError(_cont.getAnalysisMessages().getUnexpectedOperandTypes(),
                StringList.join(new StringList(
                        StringList.join(_a.getNames(),"&"),
                        StringList.join(_b.getNames(),"&")
                ),";"),
                getOp());
        _cont.getAnalyzing().getLocalizer().addError(un_);
        ClassArgumentMatching arg_ = new ClassArgumentMatching(exp_);
        res_.setResult(arg_);
        return res_;
    }

    @Override
    Argument calculateOperAna(Argument _a, String _op, Argument _b,
            ContextEl _an) {
        return new Argument(AliasNumber.calculateXor(_a.getStruct(), _b.getStruct(), _an, getResultClass()));
    }

    @Override
    void setCatenize(ResultOperand _res) {
    }

}
