package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.inherits.ClassArgumentMatching;

public class BadDottedOperation extends LeafOperation {
    public BadDottedOperation(int _index, int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    public void analyze(ContextEl _conf) {
        OperationsSequence op_ = getOperations();
        int relativeOff_ = op_.getOffset();
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+relativeOff_, _conf);
        String argClName_;
        FoundErrorInterpret emptyPart_ = new FoundErrorInterpret();
        emptyPart_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
        emptyPart_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
        //if parent is null => all text
        //if parent is not null => use parent header
        emptyPart_.buildError(_conf.getAnalysisMessages().getEmptyExpressionPart());
        _conf.getAnalyzing().getLocalizer().addError(emptyPart_);
        getErrs().add(emptyPart_.getBuiltError());
        argClName_ = _conf.getStandards().getAliasObject();
        setResultClass(new ClassArgumentMatching(argClName_));
    }

}
