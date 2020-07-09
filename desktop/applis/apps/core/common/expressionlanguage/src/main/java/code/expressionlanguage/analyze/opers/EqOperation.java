package code.expressionlanguage.analyze.opers;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.inherits.ClassArgumentMatching;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.instr.PartOffset;
import code.expressionlanguage.linkage.LinkageUtil;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.BooleanStruct;
import code.util.CustList;
import code.util.*;
import code.util.StringList;

public final class EqOperation extends MethodOperation implements MiddleSymbolOperation {

    private String oper;
    private ClassMethodId classMethodId;
    private int opOffset;
    public EqOperation(int _index,
            int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
        oper = _op.getOperators().values().first();
        opOffset = _op.getOperators().firstKey();
    }

    private static boolean calculateEq(Argument _a, Argument _b) {
        return _a.getStruct().sameReference(_b.getStruct());
    }

    @Override
    public void analyze(ContextEl _conf) {
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+opOffset, _conf);
        if (StringList.quickEq(oper.trim(), NEG_BOOL)) {
            FoundErrorInterpret badEl_ = new FoundErrorInterpret();
            badEl_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
            int index_ = _conf.getAnalyzing().getLocalizer().getCurrentLocationIndex();
            badEl_.setIndexFile(index_);
            //oper len
            badEl_.buildError(_conf.getAnalysisMessages().getBadOperatorRef(),
                    oper.trim());
            _conf.getAnalyzing().getLocalizer().addError(badEl_);
            CustList<PartOffset> err_ = new CustList<PartOffset>();
            err_.add(new PartOffset("<a title=\""+LinkageUtil.transform(badEl_.getBuiltError()) +"\" class=\"e\">",index_));
            err_.add(new PartOffset("</a>",index_+1));
            getPartOffsetsChildren().add(err_);
        }
        String custOp_ = oper.trim();
        CustList<OperationNode> chidren_ = getChildrenNodes();
        ClassArgumentMatching first_ = chidren_.first().getResultClass();
        ClassArgumentMatching second_ = chidren_.last().getResultClass();
        ClassMethodId cl_ = getBinaryOperatorOrMethod(this,first_,second_, custOp_, _conf);
        if (cl_ != null) {
            classMethodId = cl_;
            return;
        }
        LgNames stds_ = _conf.getStandards();
        setResultClass(new ClassArgumentMatching(stds_.getAliasPrimBoolean()));
    }
    @Override
    public void quickCalculate(ContextEl _conf) {
        if (classMethodId != null) {
            return;
        }
        CustList<OperationNode> chidren_ = getChildrenNodes();
        Argument first_ = chidren_.first().getArgument();
        Argument second_ = chidren_.last().getArgument();
        boolean complement_ = false;
        String op_ = getOperations().getOperators().values().first().trim();
        if (StringList.quickEq(op_, DIFF)) {
            complement_ = true;
        }
        boolean b_ = calculateEq(first_, second_);
        if (complement_) {
            b_ = !b_;
        }
        Argument arg_ = new Argument(BooleanStruct.of(b_));
        setSimpleArgumentAna(arg_, _conf);
    }
    @Override
    void calculateChildren() {
        IntTreeMap< String> vs_ = getOperations().getValues();
        getChildren().putAllMap(vs_);
    }

    public String getOper() {
        return getOp();
    }

    @Override
    public String getOp() {
        return oper;
    }

    @Override
    public ClassMethodId getClassMethodId() {
        return classMethodId;
    }

    @Override
    public int getOpOffset() {
        return opOffset;
    }

    /**The execution cannot occur if there is only one character as symbol
    Sample: 1!2 leads to error even if there is two operands*/
    @Override
    public boolean isOkNum() {
        return true;
    }

}
