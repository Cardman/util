package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.AnaApplyCoreMethodUtil;
import code.expressionlanguage.analyze.inherits.AnaTemplates;
import code.expressionlanguage.analyze.opers.util.*;
import code.expressionlanguage.analyze.types.AnaTypeUtil;
import code.expressionlanguage.analyze.util.ContextUtil;
import code.expressionlanguage.common.AnaGeneType;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.inherits.Mapping;
import code.expressionlanguage.functionid.*;
import code.expressionlanguage.inherits.ClassArgumentMatching;
import code.expressionlanguage.inherits.Templates;
import code.expressionlanguage.instr.ElUtil;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.options.KeyWords;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.Struct;
import code.expressionlanguage.analyze.types.ResolvingImportTypes;
import code.util.CustList;
import code.util.StringList;
import code.util.StringMap;

public final class StandardInstancingOperation extends
        AbstractInstancingOperation implements PreAnalyzableOperation,RetrieveConstructor {

    private boolean hasFieldName;
    private String fieldName = EMPTY_STRING;
    private int blockIndex = -1;

    private CustList<ConstructorInfo> ctors = new CustList<ConstructorInfo>();

    public StandardInstancingOperation(int _index, int _indexChild,
            MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    public void setFieldName(String _fieldName) {
        fieldName = _fieldName;
    }

    public void setHasFieldName(boolean _hasFieldName) {
        hasFieldName = _hasFieldName;
    }

    @Override
    public void preAnalyze(ContextEl _an) {
        tryAnalyze(_an);
        if (getTypeInfer().isEmpty()) {
            return;
        }
        tryGetCtors(_an, getTypeInfer(), ctors);
    }



    @Override
    public void analyze(ContextEl _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        int off_ = StringList.getFirstPrintableCharIndex(getMethodName());
        setClassName(_conf.getStandards().getAliasObject());
        KeyWords keyWords_ = _conf.getKeyWords();
        String newKeyWord_ = keyWords_.getKeyWordNew();
        String realClassName_ = getMethodName().trim().substring(newKeyWord_.length());
        int j_ = realClassName_.indexOf("}");
        if (j_ > -1) {
            realClassName_ = realClassName_.substring(j_+1);
            off_ += j_+1;
        }
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _conf);
        CustList<OperationNode> filter_ = ElUtil.filterInvoking(chidren_);
        String varargParam_ = getVarargParam(filter_);
        CustList<ClassArgumentMatching> firstArgs_ = listClasses(filter_);
        if (!isIntermediateDottedOperation()) {
            setStaticAccess(_conf.getAnalyzing().getStaticContext());
            if (!getTypeInfer().isEmpty()) {
                realClassName_ = getTypeInfer();
            } else if (!hasFieldName) {
                int local_ = StringList.getFirstPrintableCharIndex(realClassName_);
                realClassName_ = ResolvingImportTypes.resolveCorrectType(_conf,newKeyWord_.length()+local_,realClassName_);
                getPartOffsets().addAllElts(_conf.getAnalyzing().getCurrentParts());
            } else {
                realClassName_ = realClassName_.trim();
            }
            checkInstancingType(_conf, realClassName_, isStaticAccess(), getErrs());
            analyzeCtor(_conf, realClassName_, varargParam_,firstArgs_);
            return;
        }
        if (!getTypeInfer().isEmpty()) {
            analyzeCtor(_conf, getTypeInfer(), varargParam_,firstArgs_);
            return;
        }
        int offset_ = StringList.getFirstPrintableCharIndex(realClassName_);
        realClassName_ = realClassName_.trim();
        ClassArgumentMatching arg_ = getPreviousResultClass();
        if (arg_.isArray()) {
            FoundErrorInterpret static_ = new FoundErrorInterpret();
            static_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
            static_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
            //key word len
            static_.buildError(_conf.getAnalysisMessages().getIllegalCtorUnknown(),
                    realClassName_);
            _conf.getAnalyzing().getLocalizer().addError(static_);
            getErrs().add(static_.getBuiltError());
            LgNames stds_ = _conf.getStandards();
            setResultClass(new ClassArgumentMatching(stds_.getAliasObject()));
            return;
        }
        StringMap<String> ownersMap_ = new StringMap<String>();
        String idClass_ = StringExpUtil.getIdFromAllTypes(realClassName_);
        offset_ += idClass_.length() + 1;
        for (String o: arg_.getNames()) {
            boolean ok_ = true;
            for (String p: StringExpUtil.getAllTypes(o).mid(1)) {
                if (p.startsWith(Templates.SUB_TYPE)) {
                    FoundErrorInterpret call_ = new FoundErrorInterpret();
                    call_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
                    call_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
                    //key word len
                    call_.buildError(_conf.getAnalysisMessages().getIllegalCtorBound(),
                            p,
                            o);
                    _conf.getAnalyzing().getLocalizer().addError(call_);
                    getErrs().add(call_.getBuiltError());
                    ok_ = false;
                }
                if (p.startsWith(Templates.SUP_TYPE)) {
                    FoundErrorInterpret call_ = new FoundErrorInterpret();
                    call_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
                    call_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
                    //key word len
                    call_.buildError(_conf.getAnalysisMessages().getIllegalCtorBound(),
                            p,
                            o);
                    _conf.getAnalyzing().getLocalizer().addError(call_);
                    getErrs().add(call_.getBuiltError());
                    ok_ = false;
                }
            }
            if (!ok_) {
                LgNames stds_ = _conf.getStandards();
                setResultClass(new ClassArgumentMatching(stds_.getAliasObject()));
                return;
            }
        }
        for (String o: arg_.getNames()) {
            StringList owners_ = AnaTypeUtil.getGenericOwners(o, idClass_, _conf);
            if (owners_.onlyOneElt()) {
                ownersMap_.put(o, owners_.first());
            }
        }
        if (ownersMap_.size() != 1) {
            FoundErrorInterpret static_ = new FoundErrorInterpret();
            static_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
            static_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
            //idClass_ len
            static_.buildError(_conf.getAnalysisMessages().getNotResolvedOwner(),
                    idClass_
            );
            _conf.getAnalyzing().getLocalizer().addError(static_);
            getErrs().add(static_.getBuiltError());
            LgNames stds_ = _conf.getStandards();
            setResultClass(new ClassArgumentMatching(stds_.getAliasObject()));
            return;
        }
        String sup_ = ownersMap_.values().first();
        StringList partsArgs_ = new StringList();
        for (String a: StringExpUtil.getAllTypes(realClassName_).mid(1)) {
            int loc_ = StringList.getFirstPrintableCharIndex(a);
            partsArgs_.add(ResolvingImportTypes.resolveCorrectType(_conf,offset_+loc_,a));
            getPartOffsets().addAllElts(_conf.getAnalyzing().getCurrentParts());
            offset_ += a.length() + 1;
        }
        StringMap<StringList> vars_ = _conf.getAnalyzing().getCurrentConstraints().getCurrentConstraints();
        realClassName_ = AnaTemplates.check(getErrs(),StringList.concat(sup_, "..", idClass_), partsArgs_, vars_, _conf);
        analyzeCtor(_conf, realClassName_, varargParam_,firstArgs_);
    }


    private void analyzeCtor(ContextEl _conf, String _realClassName, String _paramVargArg,CustList<ClassArgumentMatching> _firstArgs) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        CustList<OperationNode> filter_ = ElUtil.filterInvoking(chidren_);
        LgNames stds_ = _conf.getStandards();
        int varargOnly_ = lookOnlyForVarArg();
        ClassMethodIdAncestor idMethod_ = lookOnlyForId();
        String base_ = StringExpUtil.getIdFromAllTypes(_realClassName);
        AnaGeneType g_ = _conf.getAnalyzing().getAnaGeneType(_conf,base_);
        if (g_ == null) {
            FoundErrorInterpret call_ = new FoundErrorInterpret();
            call_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
            call_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
            //type len
            call_.buildError(_conf.getAnalysisMessages().getIllegalCtorUnknown(),
                    _realClassName);
            _conf.getAnalyzing().getLocalizer().addError(call_);
            setResultClass(new ClassArgumentMatching(stds_.getAliasObject()));
            getErrs().add(call_.getBuiltError());
            return;
        }
        OperationNode possibleInit_ = getFirstChild();
        if (possibleInit_ instanceof StaticInitOperation) {
            StaticInitOperation st_ = (StaticInitOperation) possibleInit_;
            boolean staticType_ = g_.isStaticType();
            st_.setInit(_conf,base_,staticType_);
        }
        for (String p:StringExpUtil.getAllTypes(_realClassName).mid(1)){
            if (p.startsWith(Templates.SUB_TYPE)) {
                FoundErrorInterpret call_ = new FoundErrorInterpret();
                call_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
                call_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
                //part type len
                call_.buildError(_conf.getAnalysisMessages().getIllegalCtorBound(),
                        p,
                        _realClassName);
                _conf.getAnalyzing().getLocalizer().addError(call_);
                getErrs().add(call_.getBuiltError());
            }
            if (p.startsWith(Templates.SUP_TYPE)) {
                FoundErrorInterpret call_ = new FoundErrorInterpret();
                call_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
                call_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
                //part type len
                call_.buildError(_conf.getAnalysisMessages().getIllegalCtorBound(),
                        p,
                        _realClassName);
                _conf.getAnalyzing().getLocalizer().addError(call_);
                getErrs().add(call_.getBuiltError());
            }
        }
        if (ContextUtil.isAbstractType(g_) && !ContextUtil.isEnumType(g_)) {
            FoundErrorInterpret call_ = new FoundErrorInterpret();
            call_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
            call_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
            //type len
            call_.buildError(_conf.getAnalysisMessages().getIllegalCtorAbstract(),
                    base_);
            _conf.getAnalyzing().getLocalizer().addError(call_);
            getErrs().add(call_.getBuiltError());
            setResultClass(new ClassArgumentMatching(_realClassName));
            return;
        }
        blockIndex = ContextUtil.getCurrentChildTypeIndex(_conf,this, g_,fieldName,_realClassName);
        if (blockIndex < -1) {
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
        ConstrustorIdVarArg ctorRes_ = getDeclaredCustConstructor(this,_conf, varargOnly_, new ClassArgumentMatching(_realClassName),g_, feed_, _paramVargArg, OperationNode.toArgArray(_firstArgs));
        if (ctorRes_.getRealId() == null) {
            setResultClass(new ClassArgumentMatching(_realClassName));
            return;
        }
        setConstId(ctorRes_.getRealId());
        setClassName(ctorRes_.getConstId().getName());
        if (ctorRes_.isVarArgToCall()) {
            setNaturalVararg(getConstId().getParametersTypes().size() - 1);
            setLastType(getConstId().getParametersTypes().last());
        }
        unwrapArgsFct(filter_, getConstId(), getNaturalVararg(), getLastType(), _firstArgs, _conf);
        setResultClass(new ClassArgumentMatching(_realClassName));
    }

    @Override
    public void quickCalculate(ContextEl _conf) {
        int off_ = StringList.getFirstPrintableCharIndex(getMethodName());
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+off_, _conf);
        tryGetArg(this,_conf, getNaturalVararg(), getConstId(), getLastType());
    }

    public static void tryGetArg(StandardInstancingOperation _current, ContextEl _conf,
                                 int _naturalVararg, ConstructorId _constId, String _lastType) {
        CustList<OperationNode> chidren_ = _current.getChildrenNodes();
        CustList<Argument> arguments_ = new CustList<Argument>();
        CustList<OperationNode> filter_ = new CustList<OperationNode>();
        for (OperationNode o: chidren_) {
            if (o instanceof StaticInitOperation) {
                continue;
            }
            arguments_.add(o.getArgument());
            filter_.add(o);
        }
        if (_constId == null) {
            return;
        }
        CustList<Argument> firstArgs_ = quickListArguments(filter_, _naturalVararg, _lastType, arguments_);
        Struct out_ = AnaApplyCoreMethodUtil.newAnalyzisInstanceStd(_conf, _constId, Argument.toArgArray(firstArgs_));
        if (out_ == null) {
            return;
        }
        Argument arg_ = Argument.createVoid();
        arg_.setStruct(out_);
        _current.setSimpleArgumentAna(arg_, _conf);
    }

    public String getFieldName() {
        return fieldName;
    }

    public boolean isHasFieldName() {
        return hasFieldName;
    }

    public int getBlockIndex() {
        return blockIndex;
    }

    public CustList<ConstructorInfo> getCtors() {
        return ctors;
    }
}
