package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.opers.util.ResultOperand;
import code.expressionlanguage.analyze.types.AnaTypeUtil;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.inherits.ClassArgumentMatching;
import code.expressionlanguage.instr.PartOffset;
import code.expressionlanguage.linkage.LinkageUtil;
import code.expressionlanguage.stds.AliasNumber;
import code.util.CustList;
import code.util.StringList;

public final class ShiftRightOperation extends NumericOperation {

    public ShiftRightOperation(int _index, int _indexChild, MethodOperation _m,
            OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    ResultOperand analyzeOper(ClassArgumentMatching _a, String _op,
                              ClassArgumentMatching _b, ContextEl _cont) {
        ResultOperand res_ = new ResultOperand();
        int oa_ = AnaTypeUtil.getIntOrderClass(_a, _cont);
        int ob_ = AnaTypeUtil.getIntOrderClass(_b, _cont);
        if (oa_ > 0 && ob_ > 0) {
            ClassArgumentMatching out_ = getQuickResultClass(_a, oa_, _cont, _b, ob_);
            _a.setUnwrapObject(out_);
            _b.setUnwrapObject(out_);
            res_.setResult(out_);
            return res_;
        }
        AnalyzedPageEl page_ = _cont.getAnalyzing();
        page_.setOkNumOp(false);
        String exp_ = page_.getStandards().getAliasNumber();
        FoundErrorInterpret un_ = new FoundErrorInterpret();
        int index_ = page_.getLocalizer().getCurrentLocationIndex();
        un_.setIndexFile(index_);
        un_.setFileName(page_.getLocalizer().getCurrentFileName());
        //oper
        un_.buildError(_cont.getAnalysisMessages().getUnexpectedOperandTypes(),
                StringList.join(new StringList(
                        StringList.join(_a.getNames(),"&"),
                        StringList.join(_b.getNames(),"&")
                ),";"),
                getOp());
        page_.getLocalizer().addError(un_);
        CustList<PartOffset> err_ = new CustList<PartOffset>();
        err_.add(new PartOffset("<a title=\""+LinkageUtil.transform(un_.getBuiltError()) +"\" class=\"e\">",index_));
        err_.add(new PartOffset("</a>",index_+getOp().length()));
        getPartOffsetsChildren().add(err_);
        ClassArgumentMatching arg_ = new ClassArgumentMatching(exp_);
        res_.setResult(arg_);
        return res_;
    }

    @Override
    Argument calculateOperAna(Argument _a, String _op, Argument _b,
            ContextEl _an) {
        return new Argument(AliasNumber.calculateShiftRight(ClassArgumentMatching.convertToNumber(_a.getStruct()),
                ClassArgumentMatching.convertToNumber(_b.getStruct()), getResultClass(), _an.getAnalyzing().getStandards()));
    }

    @Override
    void setCatenize(ResultOperand _res) {
    }

}
