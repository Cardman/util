package code.expressionlanguage.opers;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.opers.util.ResultOperand;
import code.expressionlanguage.structs.NumberStruct;
import code.util.StringList;

public final class BitShiftLeftOperation extends NumericOperation {

    public BitShiftLeftOperation(int _index, int _indexChild, MethodOperation _m,
            OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    ResultOperand analyzeOper(ClassArgumentMatching _a, String _op,
            ClassArgumentMatching _b, Analyzable _cont) {
        ResultOperand res_ = new ResultOperand();
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
        un_.setIndexFile(_cont.getCurrentLocationIndex());
        un_.setFileName(_cont.getCurrentFileName());
        //oper
        un_.buildError(_cont.getContextEl().getAnalysisMessages().getUnexpectedOperandTypes(),
                StringList.join(new StringList(
                        StringList.join(_a.getNames(),"&"),
                        StringList.join(_b.getNames(),"&")
                ),";"),
                getOp());
        _cont.addError(un_);
        ClassArgumentMatching arg_ = new ClassArgumentMatching(exp_);
        res_.setResult(arg_);
        return res_;
    }

    @Override
    Argument calculateOperAna(Argument _a, String _op, Argument _b,
            Analyzable _an) {
        return new Argument(NumberStruct.calculateBitShiftLeft(ClassArgumentMatching.convertToNumber(_a.getStruct()),
                ClassArgumentMatching.convertToNumber(_b.getStruct()), _an, getResultClass()));
     }

    @Override
    void setCatenize(ResultOperand _res) {
    }

}
