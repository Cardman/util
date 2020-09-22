package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.instr.OperationsSequence;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.stds.PrimitiveTypes;
import code.util.CustList;
import code.util.StringList;

public final class ArrayFieldOperation extends AbstractFieldOperation {

    public ArrayFieldOperation(int _indexInEl, int _indexChild,
            MethodOperation _m, OperationsSequence _op) {
        super(_indexInEl, _indexChild, _m, _op);
    }

    @Override
    public void analyze(AnalyzedPageEl _page) {
        OperationsSequence op_ = getOperations();
        int relativeOff_ = op_.getOffset();
        String originalStr_ = op_.getValues().getValue(CustList.FIRST_INDEX);
        String str_ = originalStr_.trim();
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+relativeOff_, _page);
        LgNames stds_ = _page.getStandards();
        AnaClassArgumentMatching cl_ = getPreviousResultClass();
        String aliasLength_ = _page.getStandards().getAliasLength();
        if (StringList.quickEq(str_, aliasLength_)) {
            setResultClass(new AnaClassArgumentMatching(stds_.getAliasPrimInteger(),PrimitiveTypes.INT_WRAP));
            return;
        }
        FoundErrorInterpret und_ = new FoundErrorInterpret();
        und_.setFileName(_page.getLocalizer().getCurrentFileName());
        und_.setIndexFile(_page.getLocalizer().getCurrentLocationIndex());
        //str_ len
        und_.buildError(_page.getAnalysisMessages().getUndefinedAccessibleField(),
                str_,
                StringList.join(cl_.getNames(), "&"));
        _page.getLocalizer().addError(und_);
        getErrs().add(und_.getBuiltError());
        setResultClass(new AnaClassArgumentMatching(stds_.getAliasPrimInteger(),PrimitiveTypes.INT_WRAP));
    }

}
