package code.expressionlanguage.analyze.opers;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.blocks.AnalyzedBlock;
import code.expressionlanguage.analyze.blocks.ReturnMethod;
import code.expressionlanguage.analyze.opers.util.MethodInfo;
import code.expressionlanguage.analyze.opers.util.NameParametersFilter;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.analyze.types.AnaTypeUtil;
import code.expressionlanguage.analyze.util.ClassMethodIdAncestor;
import code.expressionlanguage.analyze.util.ClassMethodIdReturn;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.functionid.*;
import code.expressionlanguage.analyze.instr.OperationsSequence;
import code.expressionlanguage.analyze.instr.PartOffset;
import code.expressionlanguage.linkage.LinkageUtil;
import code.expressionlanguage.stds.LgNames;
import code.util.CustList;
import code.util.IntTreeMap;
import code.util.StringList;

public final class ArrOperation extends InvokingOperation implements SettableElResult,PreAnalyzableOperation,RetrieveMethod {

    private boolean variable;
    private boolean getAndSet;

    private boolean catString;

    private ClassMethodId classMethodId;

    private String lastType = EMPTY_STRING;

    private int naturalVararg = -1;

    private int anc;

    private boolean staticChoiceMethod;

    private String nbErr = "";
    private String methodFound = EMPTY_STRING;
    private CustList<CustList<MethodInfo>> methodInfos = new CustList<CustList<MethodInfo>>();
    private int rootNumber = -1;
    private int memberNumber = -1;
    private int memberNumberSet = -1;

    public ArrOperation(int _index,
            int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    public void preAnalyze(AnalyzedPageEl _page) {
        ForwardOperation fwd_ = tryGetForward(this);
        boolean accessSuperTypes_ = true;
        boolean accessFromSuper_ = false;
        if (fwd_ != null) {
            accessSuperTypes_ = fwd_.isAccessSuperTypes();
            accessFromSuper_ = fwd_.isAccessFromSuper();
        }
        String trimMeth_ = "[]";
        AnaClassArgumentMatching class_ = getPreviousResultClass();
        String classType_ = "";
        if (fwd_ != null) {
            classType_ = fwd_.getClassType();
        }
        StringList l_;
        if (!classType_.isEmpty()) {
            l_ = new StringList(classType_);
        } else {
            l_ = class_.getNames();
        }
        StringList bounds_ = new StringList();
        for (String c: l_) {
            bounds_.addAllElts(getBounds(c, _page));
        }
        methodFound = trimMeth_;
        methodInfos = getDeclaredCustMethodByType(isStaticAccess(), accessFromSuper_,accessSuperTypes_,bounds_, trimMeth_, false,null, _page);
        int len_ = methodInfos.size();
        for (int i = 0; i < len_; i++) {
            int gr_ = methodInfos.get(i).size();
            CustList<MethodInfo> newList_ = new CustList<MethodInfo>();
            for (int j = 0; j < gr_; j++) {
                MethodInfo methodInfo_ = methodInfos.get(i).get(j);
                if (!StringList.quickEq(methodInfo_.getConstraints().getName(),trimMeth_)) {
                    continue;
                }
                newList_.add(methodInfo_);
            }
            methodInfos.set(i, newList_);
        }
        boolean apply_ = false;
        OperationNode curPar_ = getParent();
        if (curPar_ instanceof AbstractDotOperation) {
            if (curPar_.getParent() == null) {
                apply_ = true;
            }
        }
        String typeAff_ = EMPTY_STRING;
        AnalyzedBlock cur_ = _page.getCurrentAnaBlock();
        if (apply_ && cur_ instanceof ReturnMethod) {
            typeAff_ = tryGetRetType(_page);
        }
        filterByReturnType(typeAff_,methodInfos, _page);
    }

    @Override
    public void analyze(AnalyzedPageEl _page) {
        MethodOperation.processEmptyError(getFirstChild(),getErrs());
        CustList<OperationNode> chidren_ = getChildrenNodes();
        String varargParam_ = getVarargParam(chidren_);
        int varargOnly_ = lookOnlyForVarArg();
        ClassMethodIdAncestor idMethod_ = lookOnlyForId();
        ForwardOperation fwd_ = tryGetForward(this);
        boolean staticChoiceMethod_ = false;
        boolean accessSuperTypes_ = true;
        boolean accessFromSuper_ = false;
        if (fwd_ != null) {
            staticChoiceMethod_ = fwd_.isStaticChoiceMethod();
            accessSuperTypes_ = fwd_.isAccessSuperTypes();
            accessFromSuper_ = fwd_.isAccessFromSuper();
        }
        ClassMethodIdAncestor feed_ = null;
        ClassMethodIdAncestor feedSet_ = null;
        String trimMeth_ = "[]";
        String trimMethSet_ = "[]=";
        if (idMethod_ != null) {
            ClassMethodId id_ = idMethod_.getClassMethodId();
            String idClass_ = id_.getClassName();
            MethodId mid_ = id_.getConstraints();
            boolean vararg_ = mid_.isVararg();
            StringList params_ = mid_.getParametersTypes();
            feed_ = new ClassMethodIdAncestor(new ClassMethodId(idClass_, new MethodId(MethodAccessKind.INSTANCE, trimMeth_, params_, vararg_)),idMethod_.getAncestor());
            feedSet_ = new ClassMethodIdAncestor(new ClassMethodId(idClass_, new MethodId(MethodAccessKind.INSTANCE, trimMethSet_, params_, vararg_)),idMethod_.getAncestor());
        }
        AnaClassArgumentMatching class_ = getPreviousResultClass();
        String classType_ = "";
        if (fwd_ != null) {
            classType_ = fwd_.getClassType();
        }
        StringList l_;
        if (!classType_.isEmpty()) {
            l_ = new StringList(classType_);
        } else {
            l_ = class_.getNames();
        }
        StringList bounds_ = new StringList();
        for (String c: l_) {
            bounds_.addAllElts(getBounds(c, _page));
        }
        NameParametersFilter name_ = buildFilter(_page);
        if (!name_.isOk()) {
            setResultClass(new AnaClassArgumentMatching(_page.getStandards().getAliasObject()));
            return;
        }
        ClassMethodIdReturn clMeth_ = tryGetDeclaredCustMethod(varargOnly_, isStaticAccess(),false,
                bounds_, trimMeth_, accessSuperTypes_, accessFromSuper_, false, feed_,
                varargParam_, name_, _page);
        ClassMethodIdReturn clMethSet_ = tryGetDeclaredCustMethod(varargOnly_, isStaticAccess(),false,
                bounds_, trimMethSet_, accessSuperTypes_, accessFromSuper_, false, feedSet_,
                varargParam_, name_, _page);
        if (clMeth_.isFoundMethod()) {
            rootNumber = clMeth_.getRootNumber();
            memberNumber = clMeth_.getMemberNumber();
            memberNumberSet = clMethSet_.getMemberNumber();
            if (staticChoiceMethod_) {
                if (clMeth_.isAbstractMethod()) {
                    setRelativeOffsetPossibleAnalyzable(getIndexInEl(), _page);
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
            }
            staticChoiceMethod = staticChoiceMethod_;
            anc = clMeth_.getAncestor();
            String foundClass_ = clMeth_.getRealClass();
            if (!staticChoiceMethod_) {
                foundClass_ = StringExpUtil.getIdFromAllTypes(foundClass_);
            }
            MethodId id_ = clMeth_.getRealId();
            classMethodId = new ClassMethodId(foundClass_, id_);
            MethodId realId_ = clMeth_.getRealId();
            if (clMeth_.isVarArgToCall()) {
                StringList paramtTypes_ = clMeth_.getRealId().getParametersTypes();
                naturalVararg = paramtTypes_.size() - 1;
                lastType = paramtTypes_.last();
            }
            unwrapArgsFct(realId_, naturalVararg, lastType, name_.getAll(), _page);
            setResultClass(new AnaClassArgumentMatching(clMeth_.getReturnType(), _page.getStandards()));
            return;
        }
        if (chidren_.size() != 1) {
            getPartOffsetsChildren().add(new CustList<PartOffset>());
            IntTreeMap<String> operators_ =  getOperations().getOperators();
            setRelativeOffsetPossibleAnalyzable(getIndexInEl(), _page);
            int i_ = _page.getLocalizer().getCurrentLocationIndex() + operators_.getKey(1);
            FoundErrorInterpret badNb_ = new FoundErrorInterpret();
            badNb_.setFileName(_page.getLocalizer().getCurrentFileName());
            badNb_.setIndexFile(_page.getLocalizer().getCurrentLocationIndex());
            //second separator char
            badNb_.buildError(_page.getAnalysisMessages().getOperatorNbDiff(),
                    Integer.toString(1),
                    Integer.toString(chidren_.size()),
                    "[]"
            );
            _page.getLocalizer().addError(badNb_);
            CustList<PartOffset> list_ = new CustList<PartOffset>();
            list_.add(new PartOffset("<a title=\""+LinkageUtil.transform(badNb_.getBuiltError()) +"\" class=\"e\">",i_));
            list_.add(new PartOffset("</a>",i_+ 1));
            getPartOffsetsChildren().add(list_);
            setResultClass(new AnaClassArgumentMatching(_page.getStandards().getAliasObject()));
            return;
        }
        OperationNode right_ = chidren_.last();
        AnaClassArgumentMatching indexClass_ = right_.getResultClass();
        setRelativeOffsetPossibleAnalyzable(right_.getIndexInEl(), _page);
        LgNames stds_ = _page.getStandards();
        if (!indexClass_.isNumericInt(_page)) {
            ClassMethodIdReturn res_ = OperationNode.tryGetDeclaredImplicitCast(_page.getStandards().getAliasPrimInteger(), indexClass_, _page);
            if (res_.isFoundMethod()) {
                ClassMethodId cl_ = new ClassMethodId(res_.getId().getClassName(),res_.getRealId());
                indexClass_.getImplicits().add(cl_);
                indexClass_.setRootNumber(res_.getRootNumber());
                indexClass_.setMemberNumber(res_.getMemberNumber());
            } else {
                FoundErrorInterpret un_ = new FoundErrorInterpret();
                un_.setIndexFile(_page.getLocalizer().getCurrentLocationIndex());
                un_.setFileName(_page.getLocalizer().getCurrentFileName());
                //first separator char
                un_.buildError(_page.getAnalysisMessages().getUnexpectedType(),
                        StringList.join(indexClass_.getNames(),"&"));
                _page.getLocalizer().addError(un_);
                nbErr = un_.getBuiltError();
            }
        }
        setRelativeOffsetPossibleAnalyzable(chidren_.first().getIndexInEl(), _page);
        if (!class_.isArray()) {
            FoundErrorInterpret un_ = new FoundErrorInterpret();
            un_.setIndexFile(_page.getLocalizer().getCurrentLocationIndex());
            un_.setFileName(_page.getLocalizer().getCurrentFileName());
            //first separator char
            un_.buildError(_page.getAnalysisMessages().getUnexpectedType(),
                    StringList.join(class_.getNames(),"&"));
            _page.getLocalizer().addError(un_);
            getErrs().add(un_.getBuiltError());
            class_ = new AnaClassArgumentMatching(_page.getStandards().getAliasObject());
            setResultClass(class_);
            return;
        }
        indexClass_.setUnwrapObject(AnaTypeUtil.toPrimitive(indexClass_, _page), _page.getStandards());
        class_ = StringExpUtil.getQuickComponentType(class_);
        class_.setUnwrapObject(class_,stds_);
        setResultClass(class_);
    }

    private static ForwardOperation tryGetForward(MethodOperation _operation) {
        MethodOperation par_ = _operation.getParent();
        if (par_ == null) {
            return null;
        }
        OperationNode firstChild_ = par_.getFirstChild();
        ForwardOperation elt_;
        if (!(firstChild_ instanceof AbstractDotOperation)) {
            elt_ = castTo(firstChild_);
        } else {
            OperationNode beforeLast_ = ((MethodOperation)firstChild_).getChildrenNodes().last();
            elt_ = castTo(beforeLast_);
        }
        return elt_;
    }
    private static ForwardOperation castTo(OperationNode _op) {
        if (_op instanceof ForwardOperation) {
            return (ForwardOperation) _op;
        }
        return null;
    }

    public boolean isVariable() {
        return variable;
    }

    public boolean isGetAndSet() {
        return getAndSet;
    }

    @Override
    public void setVariable(boolean _variable) {
        getAndSet = true;
        variable = _variable;
    }

    public boolean isCatString() {
        return catString;
    }

    @Override
    public void setCatenizeStrings() {
        catString = true;
    }

    public ClassMethodId getClassMethodId() {
        return classMethodId;
    }

    public boolean isStaticChoiceMethod() {
        return staticChoiceMethod;
    }

    public int getNaturalVararg() {
        return naturalVararg;
    }

    public String getLastType() {
        return lastType;
    }

    public int getAnc() {
        return anc;
    }

    public String getNbErr() {
        return nbErr;
    }

    @Override
    public String getMethodFound() {
        return methodFound;
    }

    @Override
    public CustList<CustList<MethodInfo>> getMethodInfos() {
        return methodInfos;
    }

    public int getRootNumber() {
        return rootNumber;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public int getMemberNumberSet() {
        return memberNumberSet;
    }
}
