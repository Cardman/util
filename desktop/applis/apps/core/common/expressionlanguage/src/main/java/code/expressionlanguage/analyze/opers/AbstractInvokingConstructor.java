package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.blocks.RootBlock;
import code.expressionlanguage.analyze.opers.util.ConstructorInfo;
import code.expressionlanguage.analyze.opers.util.ConstrustorIdVarArg;
import code.expressionlanguage.analyze.opers.util.NameParametersFilter;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.analyze.util.ClassMethodIdAncestor;
import code.expressionlanguage.functionid.ConstructorId;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.analyze.blocks.Block;
import code.expressionlanguage.analyze.blocks.ConstructorBlock;
import code.expressionlanguage.analyze.blocks.Line;
import code.expressionlanguage.stds.LgNames;
import code.util.CustList;
import code.util.StringList;

public abstract class AbstractInvokingConstructor extends InvokingOperation implements PreAnalyzableOperation,RetrieveConstructor {

    private String methodName;
    private ConstructorId constId;
    private String classFromName = EMPTY_STRING;

    private String lastType = EMPTY_STRING;

    private int naturalVararg = -1;
    private int offsetOper;
    private AnaClassArgumentMatching from;
    private CustList<ConstructorInfo> ctors = new CustList<ConstructorInfo>();
    private int rootNumber = -1;
    private int memberNumber = -1;
    public AbstractInvokingConstructor(int _index, int _indexChild,
            MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
        methodName = getOperations().getFctName();
        offsetOper = getOperations().getOperators().firstKey();
    }

    public int getOffsetOper() {
        return offsetOper;
    }

    @Override
    public void preAnalyze(AnalyzedPageEl _page) {
        int off_ = StringList.getFirstPrintableCharIndex(methodName);
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _page);
        from = getFrom(_page);
        if (from == null) {
            return;
        }
        String clCurName_ = from.getName();
        tryGetCtors(clCurName_,ctors, _page);
    }

    @Override
    public final void analyze(AnalyzedPageEl _page) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        int off_ = StringList.getFirstPrintableCharIndex(methodName);
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _page);
        int varargOnly_ = lookOnlyForVarArg();
        ClassMethodIdAncestor idMethod_ = lookOnlyForId();
        LgNames stds_ = _page.getStandards();
        String varargParam_ = getVarargParam(chidren_);
        if (from == null) {
            setResultClass(new AnaClassArgumentMatching(stds_.getAliasObject()));
            checkPositionBasis(_page);
            return;
        }
        ConstructorId feed_ = null;
        if (idMethod_ != null) {
            ClassMethodId id_ = idMethod_.getClassMethodId();
            String idClass_ = id_.getClassName();
            boolean vararg_ = id_.getConstraints().isVararg();
            StringList params_ = id_.getConstraints().getParametersTypes();
            feed_ = new ConstructorId(idClass_, params_, vararg_);
        }
        String clCurName_ = from.getName();
        classFromName = clCurName_;
        String id_ = StringExpUtil.getIdFromAllTypes(clCurName_);
        RootBlock type_ = _page.getAnaClassBody(id_);
        NameParametersFilter name_ = buildFilter(_page);
        if (!name_.isOk()) {
            setResultClass(new AnaClassArgumentMatching(_page.getStandards().getAliasObject()));
            return;
        }
        ConstrustorIdVarArg ctorRes_;
        ctorRes_ = getDeclaredCustConstructor(this, varargOnly_, from,id_,type_, feed_, varargParam_, name_, _page);
        if (ctorRes_.getRealId() == null) {
            setResultClass(new AnaClassArgumentMatching(stds_.getAliasObject()));
            checkPositionBasis(_page);
            return;
        }
        rootNumber = ctorRes_.getRootNumber();
        memberNumber = ctorRes_.getMemberNumber();
        constId = ctorRes_.getRealId();
        checkPositionBasis(_page);
        postAnalysis(ctorRes_, name_, _page);
    }

    abstract AnaClassArgumentMatching getFrom(AnalyzedPageEl _page);
    private void postAnalysis(ConstrustorIdVarArg _res, NameParametersFilter _args, AnalyzedPageEl _page) {
        if (_res.isVarArgToCall()) {
            naturalVararg = constId.getParametersTypes().size() - 1;
            lastType = constId.getParametersTypes().last();
        }
        unwrapArgsFct(constId, naturalVararg, lastType, _args.getAll(), _page);
        LgNames stds_ = _page.getStandards();
        setResultClass(new AnaClassArgumentMatching(stds_.getAliasObject()));
    }

    void checkPositionBasis(AnalyzedPageEl _page) {
        Block curBlock_ = _page.getCurrentBlock();
        if (getParent() != null) {
            //error
            FoundErrorInterpret call_ = new FoundErrorInterpret();
            call_.setFileName(curBlock_.getFile().getFileName());
            call_.setIndexFile(getFullIndexInEl());
            //key word len
            call_.buildError(_page.getAnalysisMessages().getCallCtorEnd());
            _page.addLocError(call_);
            getErrs().add(call_.getBuiltError());
        } else {
            if (!(curBlock_.getParent() instanceof ConstructorBlock)) {
                //error
                FoundErrorInterpret call_ = new FoundErrorInterpret();
                call_.setFileName(curBlock_.getFile().getFileName());
                call_.setIndexFile(getFullIndexInEl());
                //key word len
                call_.buildError(_page.getAnalysisMessages().getCallCtor());
                _page.addLocError(call_);
                getErrs().add(call_.getBuiltError());
            } else if (!(curBlock_ instanceof Line)) {
                //error
                FoundErrorInterpret call_ = new FoundErrorInterpret();
                call_.setFileName(curBlock_.getFile().getFileName());
                call_.setIndexFile(getFullIndexInEl());
                //key word len
                call_.buildError(_page.getAnalysisMessages().getCallCtorBeforeBlock());
                _page.addLocError(call_);
                getErrs().add(call_.getBuiltError());
            } else {
                checkPosition(_page);
            }
        }
    }
    void checkPosition(AnalyzedPageEl _page) {
        Block curBlock_ = _page.getCurrentBlock();
        if (curBlock_.getParent().getFirstChild() != curBlock_) {
            FoundErrorInterpret call_ = new FoundErrorInterpret();
            call_.setFileName(curBlock_.getFile().getFileName());
            call_.setIndexFile(getFullIndexInEl());
            //key word len
            call_.buildError(_page.getAnalysisMessages().getCallCtorFirstLine());
            _page.addLocError(call_);
            getErrs().add(call_.getBuiltError());
        }
    }

    public final ConstructorId getConstId() {
        return constId;
    }

    public String getClassFromName() {
        return classFromName;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getLastType() {
        return lastType;
    }

    public int getNaturalVararg() {
        return naturalVararg;
    }

    public CustList<ConstructorInfo> getCtors() {
        return ctors;
    }

    public int getRootNumber() {
        return rootNumber;
    }

    public int getMemberNumber() {
        return memberNumber;
    }
}
