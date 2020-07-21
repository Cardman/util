package code.expressionlanguage.analyze.opers;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.inherits.AnaTemplates;
import code.expressionlanguage.analyze.inherits.Mapping;
import code.expressionlanguage.analyze.opers.util.OperatorConverter;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.functionid.ClassMethodIdReturn;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.inherits.ClassArgumentMatching;
import code.expressionlanguage.instr.PartOffset;
import code.expressionlanguage.linkage.LinkageUtil;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.BooleanStruct;
import code.expressionlanguage.structs.Struct;
import code.util.CustList;
import code.util.IntTreeMap;
import code.util.StringList;


public abstract class QuickOperation extends MethodOperation {

    private boolean okNum;
    private ClassMethodId classMethodId;
    private ClassMethodId test;
    private ClassMethodId converter;
    private CustList<PartOffset> errFirst = new CustList<PartOffset>();
    private CustList<PartOffset> errSecond = new CustList<PartOffset>();

    public QuickOperation(int _index,
            int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    final void calculateChildren() {
        IntTreeMap< String> vs_ = getOperations().getValues();
        getChildren().putAllMap(vs_);
    }

    public static void tryGetResult(ContextEl _conf, MethodOperation _to, boolean _abs, boolean _okNum) {
        if (!_okNum) {
            return;
        }
        CustList<OperationNode> children_ = _to.getChildrenNodes();
        Argument f_ = children_.first().getArgument();
        Argument s_ = children_.last().getArgument();
        if (f_ == null) {
            return;
        }
        Struct v_ = f_.getStruct();
        if (_abs) {
            if (BooleanStruct.isTrue(v_)) {
                _to.setSimpleArgumentAna(f_, _conf);
                return;
            }
        } else {
            if (BooleanStruct.isFalse(v_)) {
                _to.setSimpleArgumentAna(f_, _conf);
                return;
            }
        }
        if (s_ == null) {
            return;
        }
        _to.setSimpleArgumentAna(s_, _conf);
    }
    @Override
    public final void analyze(ContextEl _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        LgNames stds_ = _conf.getStandards();
        String booleanPrimType_ = stds_.getAliasPrimBoolean();
        OperationNode left_ = chidren_.first();
        OperationNode right_ = chidren_.last();
        ClassArgumentMatching leftRes_ = left_.getResultClass();
        ClassArgumentMatching rightRes_ = right_.getResultClass();
        String oper_ = getOperations().getOperators().firstValue();
        OperatorConverter opConv_ = getBinaryOperatorOrMethod(this, leftRes_, rightRes_, oper_, _conf);
        if (opConv_.getSymbol() != null) {
            if (!PrimitiveTypeUtil.isPrimitive(opConv_.getSymbol().getClassName(),_conf)) {
                classMethodId = opConv_.getSymbol();
            }
            okNum = true;
            ClassMethodId test_ = opConv_.getTest();
            if (test_ == null) {
                return;
            }
            test = test_;
            leftRes_.getImplicitsTest().add(test_);
            Mapping map_ = new Mapping();
            map_.setArg(getResultClass());
            map_.setParam(leftRes_);
            if (!AnaTemplates.isCorrectOrNumbers(map_, _conf)) {
                ClassMethodIdReturn res_ = tryGetDeclaredImplicitCast(_conf, leftRes_.getSingleNameOrEmpty(), getResultClass());
                if (res_.isFoundMethod()) {
                    converter = new ClassMethodId(res_.getId().getClassName(),res_.getRealId());
                } else {
                    FoundErrorInterpret cast_ = new FoundErrorInterpret();
                    cast_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
                    cast_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
                    //oper len
                    cast_.buildError(_conf.getAnalysisMessages().getBadImplicitCast(),
                            StringList.join(getResultClass().getNames(),"&"),
                            StringList.join(leftRes_.getNames(),"&"));
                    _conf.getAnalyzing().getLocalizer().addError(cast_);
                    setRelativeOffsetPossibleAnalyzable(getIndexInEl()+getOperations().getOperators().firstKey(), _conf);
                    int index_ = _conf.getAnalyzing().getLocalizer().getCurrentLocationIndex();
                    errFirst.add(new PartOffset("<a title=\""+LinkageUtil.transform(cast_.getBuiltError()) +"\" class=\"e\">",index_));
                    errFirst.add(new PartOffset("</a>",index_+1));
                    okNum = false;
                }
                setResultClass(ClassArgumentMatching.copy(PrimitiveTypeUtil.toPrimitive(leftRes_,_conf)));
            }
            return;
        }
        okNum = true;
        if (!leftRes_.isBoolType(_conf)) {
            FoundErrorInterpret un_ = new FoundErrorInterpret();
            un_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
            un_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
            //first operator char or second operator char
            un_.buildError(_conf.getAnalysisMessages().getUnexpectedType(),
                    StringList.join(leftRes_.getNames(),"&"));
            _conf.getAnalyzing().getLocalizer().addError(un_);
            setRelativeOffsetPossibleAnalyzable(getIndexInEl()+getOperations().getOperators().firstKey(), _conf);
            int index_ = _conf.getAnalyzing().getLocalizer().getCurrentLocationIndex();
            errFirst.add(new PartOffset("<a title=\""+LinkageUtil.transform(un_.getBuiltError()) +"\" class=\"e\">",index_));
            errFirst.add(new PartOffset("</a>",index_+1));
            okNum = false;
        }
        if (!rightRes_.isBoolType(_conf)) {
            FoundErrorInterpret un_ = new FoundErrorInterpret();
            un_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
            un_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
            //first operator char or second operator char
            un_.buildError(_conf.getAnalysisMessages().getUnexpectedType(),
                    StringList.join(rightRes_.getNames(),"&"));
            _conf.getAnalyzing().getLocalizer().addError(un_);
            setRelativeOffsetPossibleAnalyzable(getIndexInEl()+getOperations().getOperators().firstKey(), _conf);
            int index_ = _conf.getAnalyzing().getLocalizer().getCurrentLocationIndex()+1;
            errSecond.add(new PartOffset("<a title=\""+LinkageUtil.transform(un_.getBuiltError()) +"\" class=\"e\">",index_));
            errSecond.add(new PartOffset("</a>",index_+1));
            okNum = false;
        }
        leftRes_.setUnwrapObject(booleanPrimType_);
        rightRes_.setUnwrapObject(booleanPrimType_);
        left_.cancelArgument();
        right_.cancelArgument();
        setResultClass(ClassArgumentMatching.copy(leftRes_));
    }

    public boolean isOkNum() {
        return okNum;
    }

    public CustList<PartOffset> getErrFirst() {
        return errFirst;
    }

    public CustList<PartOffset> getErrSecond() {
        return errSecond;
    }

    public ClassMethodId getClassMethodId() {
        return classMethodId;
    }

    public ClassMethodId getTest() {
        return test;
    }

    public ClassMethodId getConverter() {
        return converter;
    }
}
