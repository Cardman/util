package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.analyze.variables.AnaLoopVariable;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.common.ConstType;
import code.expressionlanguage.analyze.instr.OperationsSequence;
import code.expressionlanguage.stds.LgNames;
import code.util.CustList;

public final class FinalVariableOperation extends LeafOperation {

    private String variableName = EMPTY_STRING;
    private String realVariableName = EMPTY_STRING;
    private int off;
    private int delta;
    private ConstType type;
    private String className = EMPTY_STRING;
    private int ref;
    private int deep;
    private boolean keyWord;

    public FinalVariableOperation(int _indexInEl, int _indexChild,
            MethodOperation _m, OperationsSequence _op) {
        this(_indexInEl, _indexChild, _m, _op,EMPTY_STRING,0,-1,false);
    }

    public FinalVariableOperation(int _indexInEl, int _indexChild,
            MethodOperation _m, OperationsSequence _op, String _className, int _ref, int _deep, boolean _keyWord) {
        super(_indexInEl, _indexChild, _m, _op);
        int relativeOff_ = _op.getOffset();
        delta = _op.getDelta();
        off = relativeOff_;
        type = _op.getConstType();
        className = _className;
        ref = _ref;
        deep = _deep;
        keyWord = _keyWord;
    }

    @Override
    public void analyze(AnalyzedPageEl _page) {
        OperationsSequence op_ = getOperations();
        String originalStr_ = op_.getValues().getValue(CustList.FIRST_INDEX);
        String str_ = originalStr_.trim();
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off, _page);
        LgNames stds_ = _page.getStandards();
        if (!className.isEmpty()) {
            variableName = StringExpUtil.skipPrefix(str_);
            realVariableName = str_;
            setResultClass(new AnaClassArgumentMatching(className, _page.getStandards()));
            return;
        }
        int deep_ = -1;
        String shortStr_ = str_;
        AnaLoopVariable val_ = _page.getLoopsVars().getVal(str_);
        if (val_ == null) {
            deep_ = StringExpUtil.countPrefix(str_);
            shortStr_ = StringExpUtil.skipPrefix(str_);
            AnaLoopVariable loc_ = _page.getLoopsVars().getVal(shortStr_);
            if (loc_ != null) {
                deep_--;
            }
            val_ = _page.getCache().getLoopVar(shortStr_,deep_);
        }
        if (val_ != null) {
            deep = deep_;
            ref = val_.getRef();
            variableName = shortStr_;
            realVariableName = str_;
            setResultClass(new AnaClassArgumentMatching(val_.getIndexClassName(), _page.getStandards()));
            return;
        }
        variableName = str_;
        realVariableName = str_;
        FoundErrorInterpret und_ = new FoundErrorInterpret();
        und_.setFileName(_page.getLocalizer().getCurrentFileName());
        und_.setIndexFile(_page.getLocalizer().getCurrentLocationIndex());
        //variable name len
        und_.buildError(_page.getAnalysisMessages().getUndefinedVariable(),
                variableName);
        _page.getLocalizer().addError(und_);
        getErrs().add(und_.getBuiltError());
        setResultClass(new AnaClassArgumentMatching(stds_.getAliasObject()));
    }

    public String getVariableName() {
        return variableName;
    }

    public String getRealVariableName() {
        return realVariableName;
    }

    public ConstType getType() {
        return type;
    }

    public int getOff() {
        return off;
    }

    public int getRef() {
        return ref;
    }

    public int getDelta() {
        return delta;
    }

    public boolean isKeyWord() {
        return keyWord;
    }

    public int getDeep() {
        return deep;
    }
}
