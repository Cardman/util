package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.opers.util.MethodInfo;
import code.expressionlanguage.analyze.opers.util.NameParametersFilter;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.analyze.util.ClassMethodIdAncestor;
import code.expressionlanguage.analyze.util.ClassMethodIdReturn;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.functionid.*;
import code.expressionlanguage.analyze.instr.OperationsSequence;
import code.expressionlanguage.analyze.instr.PartOffset;
import code.expressionlanguage.analyze.types.ResolvingImportTypes;
import code.expressionlanguage.stds.StandardMethod;
import code.util.CustList;
import code.util.StringList;

public final class ChoiceFctOperation extends InvokingOperation implements PreAnalyzableOperation,RetrieveMethod,AbstractCallFctOperation {

    private String methodName;

    private ClassMethodId classMethodId;

    private boolean staticMethod;

    private String lastType = EMPTY_STRING;

    private int naturalVararg = -1;
    private int anc;

    private int delta;
    private int lengthMethod;

    private CustList<PartOffset> partOffsets = new CustList<PartOffset>();
    private boolean trueFalse;
    private String typeInfer = EMPTY_STRING;
    private String methodFound = EMPTY_STRING;
    private CustList<CustList<MethodInfo>> methodInfos = new CustList<CustList<MethodInfo>>();
    private StandardMethod standardMethod;
    private int rootNumber = -1;
    private int memberNumber = -1;

    public ChoiceFctOperation(int _index, int _indexChild, MethodOperation _m,
            OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
        methodName = getOperations().getFctName();
    }

    @Override
    public void preAnalyze(AnalyzedPageEl _page) {
        int off_ = StringList.getFirstPrintableCharIndex(methodName);
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _page);
        String trimMeth_;
        boolean import_ = false;
        if (!isIntermediateDottedOperation()) {
            import_ = true;
            setStaticAccess(_page.getStaticContext());
        }
        String className_ = methodName.substring(0, methodName.lastIndexOf(PAR_RIGHT));
        int lenPref_ = methodName.indexOf(PAR_LEFT) + 1;
        className_ = className_.substring(lenPref_);
        int loc_ = StringList.getFirstPrintableCharIndex(className_)-off_;
        CustList<PartOffset> partOffsets_ = new CustList<PartOffset>();
        className_ = ResolvingImportTypes.resolveCorrectTypeWithoutErrors(lenPref_+loc_,className_,true,partOffsets_, _page);
        if (!className_.isEmpty()) {
            partOffsets.addAllElts(partOffsets_);
            typeInfer = className_;
        }
        int delta_ = methodName.lastIndexOf(PAR_RIGHT)+1;
        String mName_ = methodName.substring(delta_);
        trimMeth_ = mName_.trim();
        if (isTrueFalseKeyWord(trimMeth_, _page)) {
            return;
        }
        String clCurName_ = className_;
        StringList bounds_ = getBounds(clCurName_, _page);
        methodFound = trimMeth_;
        methodInfos = getDeclaredCustMethodByType(isStaticAccess(), false,false,bounds_, trimMeth_, import_,null, _page);
        boolean apply_ = applyMatching();
        filterByNameReturnType(trimMeth_, apply_, methodInfos, _page);
    }

    @Override
    public void analyze(AnalyzedPageEl _page) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        int off_ = StringList.getFirstPrintableCharIndex(methodName);
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _page);
        String trimMeth_;
        int varargOnly_ = lookOnlyForVarArg();
        ClassMethodIdAncestor idMethod_ = lookOnlyForId();
        boolean import_ = false;
        if (!isIntermediateDottedOperation()) {
            import_ = true;
            setStaticAccess(_page.getStaticContext());
        }
        String className_ = methodName.substring(0, methodName.lastIndexOf(PAR_RIGHT));
        int lenPref_ = methodName.indexOf(PAR_LEFT) + 1;
        className_ = className_.substring(lenPref_);
        int loc_ = StringList.getFirstPrintableCharIndex(className_)-off_;
        if (typeInfer.isEmpty()) {
            className_ = ResolvingImportTypes.resolveCorrectType(lenPref_+loc_,className_, _page);
            partOffsets.addAllElts(_page.getCurrentParts());
        } else {
            className_ = typeInfer;
        }
        String clCurName_ = className_;
        StringList bounds_ = getBounds(clCurName_, _page);
        String varargParam_ = getVarargParam(chidren_);
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _page);
        lengthMethod = methodName.length();
        int deltaEnd_ = lengthMethod-StringList.getLastPrintableCharIndex(methodName)-1;
        delta = methodName.lastIndexOf(PAR_RIGHT)+1;
        String mName_ = methodName.substring(delta);
        delta += StringList.getFirstPrintableCharIndex(mName_);
        lengthMethod -= delta;
        lengthMethod -= deltaEnd_;
        trimMeth_ = mName_.trim();
        ClassMethodIdAncestor feed_ = null;
        ClassMethodId feedBase_ = null;
        if (idMethod_ != null) {
            ClassMethodId id_ = idMethod_.getClassMethodId();
            String idClass_ = id_.getClassName();
            MethodId mid_ = id_.getConstraints();
            boolean vararg_ = mid_.isVararg();
            StringList params_ = mid_.getParametersTypes();
            MethodAccessKind static_ = MethodId.getKind(isStaticAccess(), mid_.getKind());
            feedBase_ = new ClassMethodId(idClass_, new MethodId(static_, trimMeth_, params_, vararg_));
            feed_ = new ClassMethodIdAncestor(feedBase_,idMethod_.getAncestor());
        }
        NameParametersFilter name_ = buildFilter(_page);
        if (!name_.isOk()) {
            setResultClass(new AnaClassArgumentMatching(_page.getStandards().getAliasObject()));
            return;
        }
        if (isTrueFalseKeyWord(trimMeth_, _page)) {
            ClassMethodId f_ = getTrueFalse(feedBase_, _page);
            ClassMethodIdReturn clMeth_;
            MethodAccessKind staticAccess_ = isStaticAccess();
            AnaClassArgumentMatching[] argsClass_ = OperationNode.getResultsFromArgs(name_.getPositional());
            clMeth_ = getDeclaredCustTrueFalse(this, staticAccess_,bounds_,trimMeth_,f_, _page, argsClass_);
            if (!clMeth_.isFoundMethod()) {
                setResultClass(voidToObject(new AnaClassArgumentMatching(clMeth_.getReturnType()), _page));
                return;
            }
            rootNumber = clMeth_.getRootNumber();
            memberNumber = clMeth_.getMemberNumber();
            trueFalse = true;
            String foundClass_ = clMeth_.getRealClass();
            MethodId id_ = clMeth_.getRealId();
            classMethodId = new ClassMethodId(foundClass_, id_);
            MethodId realId_ = clMeth_.getRealId();
            staticMethod = true;
            unwrapArgsFct(realId_, naturalVararg, lastType, name_.getPositional(), _page);
            setResultClass(voidToObject(new AnaClassArgumentMatching(clMeth_.getReturnType(), _page.getStandards()), _page));
            return;
        }
        ClassMethodIdReturn clMeth_ = getDeclaredCustMethod(this, varargOnly_, isStaticAccess(), bounds_, trimMeth_, false, false, import_, feed_,varargParam_, name_, _page);
        anc = clMeth_.getAncestor();
        if (!clMeth_.isFoundMethod()) {
            setResultClass(voidToObject(new AnaClassArgumentMatching(clMeth_.getReturnType()), _page));
            return;
        }
        standardMethod = clMeth_.getStandardMethod();
        rootNumber = clMeth_.getRootNumber();
        memberNumber = clMeth_.getMemberNumber();
        if (clMeth_.isAbstractMethod()) {
            setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _page);
            FoundErrorInterpret abs_ = new FoundErrorInterpret();
            abs_.setIndexFile(_page.getLocalizer().getCurrentLocationIndex());
            abs_.setFileName(_page.getLocalizer().getCurrentFileName());
            //method name len
            abs_.buildError(
                    _page.getAnalysisMessages().getAbstractMethodRef(),
                    clMeth_.getRealClass(),
                    clMeth_.getRealId().getSignature(_page));
            _page.getLocalizer().addError(abs_);
            getErrs().add(abs_.getBuiltError());
        }
        classMethodId = clMeth_.getId();
        MethodId realId_ = clMeth_.getRealId();
        if (clMeth_.isVarArgToCall()) {
            StringList paramtTypes_ = clMeth_.getRealId().getParametersTypes();
            naturalVararg = paramtTypes_.size() - 1;
            lastType = paramtTypes_.last();
        }
        staticMethod = realId_.getKind() != MethodAccessKind.INSTANCE;
        unwrapArgsFct(realId_, naturalVararg, lastType, name_.getAll(), _page);
        setResultClass(voidToObject(new AnaClassArgumentMatching(clMeth_.getReturnType(), _page.getStandards()), _page));
    }

    public String getMethodName() {
        return methodName;
    }

    public ClassMethodId getClassMethodId() {
        return classMethodId;
    }

    public boolean isStaticMethod() {
        return staticMethod;
    }

    public String getLastType() {
        return lastType;
    }

    public int getNaturalVararg() {
        return naturalVararg;
    }

    public int getAnc() {
        return anc;
    }

    public int getDelta() {
        return delta;
    }

    public CustList<PartOffset> getPartOffsets() {
        return partOffsets;
    }

    public int getLengthMethod() {
        return lengthMethod;
    }

    public boolean isTrueFalse() {
        return trueFalse;
    }

    @Override
    public String getMethodFound() {
        return methodFound;
    }

    @Override
    public CustList<CustList<MethodInfo>> getMethodInfos() {
        return methodInfos;
    }

    @Override
    public StandardMethod getStandardMethod() {
        return standardMethod;
    }

    @Override
    public int getMemberNumber() {
        return memberNumber;
    }

    @Override
    public int getRootNumber() {
        return rootNumber;
    }

}
