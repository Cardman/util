package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.TokenErrorMessage;
import code.expressionlanguage.analyze.variables.AnaLoopVariable;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.inherits.ClassArgumentMatching;
import code.expressionlanguage.instr.ElUtil;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.options.KeyWords;
import code.util.CustList;
import code.util.StringList;

public final class MutableLoopVariableOperation extends LeafOperation implements SettableElResult {

    private boolean variable;

    private boolean catString;

    private String variableName = EMPTY_STRING;
    private int off;
    private String className;

    private final StringList nameErrors = new StringList();

    private int ref;

    public MutableLoopVariableOperation(int _indexInEl, int _indexChild,
            MethodOperation _m, OperationsSequence _op) {
        this(_indexInEl, _indexChild, _m, _op, EMPTY_STRING,0);
    }

    public MutableLoopVariableOperation(int _indexInEl, int _indexChild,
                                        MethodOperation _m, OperationsSequence _op, String _className, int _ref) {
        super(_indexInEl, _indexChild, _m, _op);
        off = _op.getOffset();
        className = _className;
        ref = _ref;
    }
    @Override
    public void analyze(ContextEl _conf) {
        OperationsSequence op_ = getOperations();
        int relativeOff_ = op_.getOffset();
        String originalStr_ = op_.getValues().getValue(CustList.FIRST_INDEX);
        String str_ = originalStr_.trim();
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+relativeOff_, _conf);
        if (ElUtil.isDeclaringLoopVariable(this, _conf)) {
            AnalyzedPageEl page_ = _conf.getAnalyzing();
            TokenErrorMessage res_ = _conf.getAnalyzing().getTokenValidation().isValidSingleToken(str_);
            variableName = str_;
            if (res_.isError()) {
                FoundErrorInterpret b_ = new FoundErrorInterpret();
                b_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
                b_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
                //variable name len
                b_.setBuiltError(res_.getMessage());
                _conf.getAnalyzing().getLocalizer().addError(b_);
                nameErrors.add(b_.getBuiltError());
                return;
            }
            ref = _conf.getAnalyzing().getLocalizer().getCurrentLocationIndex();
            String c_ = _conf.getAnalyzing().getCurrentVarSetting();
            KeyWords keyWords_ = _conf.getKeyWords();
            String keyWordVar_ = keyWords_.getKeyWordVar();
            if (StringList.quickEq(c_, keyWordVar_)) {
                _conf.getAnalyzing().putMutableLoopVar(str_);
                _conf.getAnalyzing().getVariablesNamesLoopToInfer().add(str_);
            }
            AnaLoopVariable lv_ = new AnaLoopVariable();
            String indexClassName_ = _conf.getAnalyzing().getLoopDeclaring().getIndexClassName();
            if (StringList.quickEq(c_, keyWordVar_)) {
                lv_.setClassName(_conf.getStandards().getAliasObject());
            } else {
                lv_.setClassName(c_);
            }
            lv_.setRef(ref);
            lv_.setIndexClassName(indexClassName_);
            lv_.setFinalVariable(_conf.getAnalyzing().isFinalVariable());
            page_.putMutableLoopVar(str_, lv_);
            page_.getVariablesNames().add(str_);
            setResultClass(new ClassArgumentMatching(_conf.getAnalyzing().getCurrentVarSetting()));
            return;
        }
        variableName = str_;
        setResultClass(new ClassArgumentMatching(className));
    }

    @Override
    public void setVariable(boolean _variable) {
        variable = _variable;
    }

    @Override
    public void setCatenizeStrings() {
        catString = true;
    }

    public String getVariableName() {
        return variableName;
    }
    public int getOff() {
        return off;
    }

    public boolean isVariable() {
        return variable;
    }

    public boolean isCatString() {
        return catString;
    }

    public StringList getNameErrors() {
        return nameErrors;
    }

    public int getRef() {
        return ref;
    }
}
