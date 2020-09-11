package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.opers.util.MethodInfo;
import code.expressionlanguage.analyze.opers.util.NameParametersFilter;
import code.expressionlanguage.analyze.util.ClassMethodIdAncestor;
import code.expressionlanguage.analyze.util.ClassMethodIdReturn;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.functionid.*;
import code.expressionlanguage.inherits.ClassArgumentMatching;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.instr.PartOffset;
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
    public void preAnalyze(ContextEl _an) {
        int off_ = StringList.getFirstPrintableCharIndex(methodName);
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _an);
        String trimMeth_;
        boolean import_ = false;
        if (!isIntermediateDottedOperation()) {
            import_ = true;
            setStaticAccess(_an.getAnalyzing().getStaticContext());
        }
        String className_ = methodName.substring(0, methodName.lastIndexOf(PAR_RIGHT));
        int lenPref_ = methodName.indexOf(PAR_LEFT) + 1;
        className_ = className_.substring(lenPref_);
        int loc_ = StringList.getFirstPrintableCharIndex(className_)-off_;
        CustList<PartOffset> partOffsets_ = new CustList<PartOffset>();
        className_ = ResolvingImportTypes.resolveCorrectTypeWithoutErrors(_an,lenPref_+loc_,className_,true,partOffsets_);
        if (!className_.isEmpty()) {
            partOffsets.addAllElts(partOffsets_);
            typeInfer = className_;
        }
        int delta_ = methodName.lastIndexOf(PAR_RIGHT)+1;
        String mName_ = methodName.substring(delta_);
        trimMeth_ = mName_.trim();
        if (isTrueFalseKeyWord(_an, trimMeth_)) {
            return;
        }
        String clCurName_ = className_;
        StringList bounds_ = getBounds(clCurName_, _an);
        methodFound = trimMeth_;
        methodInfos = getDeclaredCustMethodByType(_an,isStaticAccess(), false,false,bounds_, trimMeth_, import_,null);
        boolean apply_ = applyMatching();
        filterByNameReturnType(_an, trimMeth_, apply_, methodInfos);
    }

    @Override
    public void analyze(ContextEl _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        int off_ = StringList.getFirstPrintableCharIndex(methodName);
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _conf);
        String trimMeth_;
        int varargOnly_ = lookOnlyForVarArg();
        ClassMethodIdAncestor idMethod_ = lookOnlyForId();
        boolean import_ = false;
        AnalyzedPageEl page_ = _conf.getAnalyzing();
        if (!isIntermediateDottedOperation()) {
            import_ = true;
            setStaticAccess(page_.getStaticContext());
        }
        String className_ = methodName.substring(0, methodName.lastIndexOf(PAR_RIGHT));
        int lenPref_ = methodName.indexOf(PAR_LEFT) + 1;
        className_ = className_.substring(lenPref_);
        int loc_ = StringList.getFirstPrintableCharIndex(className_)-off_;
        if (typeInfer.isEmpty()) {
            className_ = ResolvingImportTypes.resolveCorrectType(_conf,lenPref_+loc_,className_);
            partOffsets.addAllElts(page_.getCurrentParts());
        } else {
            className_ = typeInfer;
        }
        String clCurName_ = className_;
        StringList bounds_ = getBounds(clCurName_, _conf);
        String varargParam_ = getVarargParam(chidren_);
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _conf);
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
        NameParametersFilter name_ = buildFilter(_conf);
        if (!name_.isOk()) {
            setResultClass(new ClassArgumentMatching(page_.getStandards().getAliasObject()));
            return;
        }
        if (isTrueFalseKeyWord(_conf, trimMeth_)) {
            ClassMethodId f_ = getTrueFalse(_conf, feedBase_);
            ClassMethodIdReturn clMeth_;
            MethodAccessKind staticAccess_ = isStaticAccess();
            ClassArgumentMatching[] argsClass_ = OperationNode.toArgArray(name_.getPositional());
            clMeth_ = getDeclaredCustTrueFalse(this,_conf, staticAccess_,bounds_,trimMeth_,f_, argsClass_);
            if (!clMeth_.isFoundMethod()) {
                setResultClass(voidToObject(new ClassArgumentMatching(clMeth_.getReturnType()),_conf));
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
            unwrapArgsFct(chidren_, realId_, naturalVararg, lastType, name_.getPositional(), _conf);
            setResultClass(voidToObject(new ClassArgumentMatching(clMeth_.getReturnType()),_conf));
            return;
        }
        ClassMethodIdReturn clMeth_ = getDeclaredCustMethod(this,_conf, varargOnly_, isStaticAccess(), bounds_, trimMeth_, false, false, import_, feed_,varargParam_, name_);
        anc = clMeth_.getAncestor();
        if (!clMeth_.isFoundMethod()) {
            setResultClass(voidToObject(new ClassArgumentMatching(clMeth_.getReturnType()),_conf));
            return;
        }
        standardMethod = clMeth_.getStandardMethod();
        rootNumber = clMeth_.getRootNumber();
        memberNumber = clMeth_.getMemberNumber();
        if (clMeth_.isAbstractMethod()) {
            setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _conf);
            FoundErrorInterpret abs_ = new FoundErrorInterpret();
            abs_.setIndexFile(page_.getLocalizer().getCurrentLocationIndex());
            abs_.setFileName(page_.getLocalizer().getCurrentFileName());
            //method name len
            abs_.buildError(
                    _conf.getAnalysisMessages().getAbstractMethodRef(),
                    clMeth_.getRealClass(),
                    clMeth_.getRealId().getSignature(page_));
            page_.getLocalizer().addError(abs_);
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
        unwrapArgsFct(chidren_, realId_, naturalVararg, lastType, name_.getAll(), _conf);
        setResultClass(voidToObject(new ClassArgumentMatching(clMeth_.getReturnType()),_conf));
        if (isIntermediateDottedOperation() && !staticMethod) {
            Argument arg_ = getPreviousArgument();
            checkNull(arg_,_conf);
        }
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
