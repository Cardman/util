package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.blocks.*;
import code.expressionlanguage.analyze.inherits.AnaTemplates;
import code.expressionlanguage.analyze.opers.util.*;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.analyze.types.AnaTypeUtil;
import code.expressionlanguage.analyze.util.*;
import code.expressionlanguage.common.AnaGeneType;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.inherits.Mapping;
import code.expressionlanguage.functionid.*;
import code.expressionlanguage.analyze.instr.OperationsSequence;
import code.expressionlanguage.options.KeyWords;
import code.expressionlanguage.stds.StandardConstructor;
import code.expressionlanguage.stds.StandardType;
import code.util.CustList;
import code.util.IntTreeMap;
import code.util.StringList;
import code.util.StringMap;
import code.util.core.IndexConstants;
import code.util.core.StringUtil;

public abstract class InvokingOperation extends MethodOperation implements PossibleIntermediateDotted {
    private AnaClassArgumentMatching previousResultClass;
    private MethodAccessKind staticAccess;
    private boolean intermediate;

    public InvokingOperation(int _index, int _indexChild, MethodOperation _m,
            OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
        previousResultClass = new AnaClassArgumentMatching(EMPTY_STRING);
    }

    @Override
    final void calculateChildren() {
        IntTreeMap< String> vs_ = getOperations().getValues();
        vs_.removeKey(vs_.firstKey());
        getChildren().putAllMap(vs_);
    }

    NameParametersFilter buildFilter(AnalyzedPageEl _page) {
        String staticCall_ = "";
        boolean apply_ = false;
        StaticCallAccessOperation st_ = null;
        if (getParent() instanceof AbstractDotOperation) {
            OperationNode firstChild_ = getParent().getFirstChild();
            if (firstChild_ instanceof StaticCallAccessOperation) {
                st_ = (StaticCallAccessOperation) firstChild_;
                staticCall_ = st_.getStCall();
                apply_ = applyMatching();
            }
        }
        String typeAff_ = EMPTY_STRING;
        if (apply_) {
            OperationNode parentMatching_ = getParentMatching();
            Block cur_ = _page.getCurrentBlock();
            if (parentMatching_ == null &&cur_ instanceof ReturnMethod) {
                typeAff_ = tryGetRetType(_page);
            } else {
                typeAff_ = tryGetTypeAff(parentMatching_, 1);
            }
        }
        NameParametersFilter out_ = buildQuickFilter(this);
        out_.setStaticCall(staticCall_);
        out_.setReturnType(typeAff_);
        out_.setStaticCallOp(st_);
        buildFilter(out_, _page);
        out_.setOk(out_.getParameterFilterErr().isEmpty());
        return out_;
    }

    String getStCall() {
        if (getParent() instanceof AbstractDotOperation) {
            OperationNode firstChild_ = getParent().getFirstChild();
            if (firstChild_ instanceof StaticCallAccessOperation) {
                StaticCallAccessOperation st_ = (StaticCallAccessOperation) firstChild_;
                return st_.getStCall();
            }
        }
        return "";
    }
    private static void buildFilter(NameParametersFilter _filter, AnalyzedPageEl _page) {
        for (NamedArgumentOperation o: _filter.getParameterFilterErr()) {
            String name_ = o.getName();
            o.setRelativeOffsetPossibleAnalyzable(o.getIndexInEl()+ o.getOffsetTr(), _page);
            FoundErrorInterpret b_;
            b_ = new FoundErrorInterpret();
            b_.setFileName(_page.getLocalizer().getCurrentFileName());
            b_.setIndexFile(_page.getLocalizer().getCurrentLocationIndex());
            //param name len
            b_.buildError(_page.getAnalysisMessages().getDuplicatedParamName(),
                    name_);
            _page.getLocalizer().addError(b_);
            o.addErr(b_.getBuiltError());
        }
    }
    static NameParametersFilter buildQuickFilter(MethodOperation _par) {
        NameParametersFilter out_ = new NameParametersFilter();
        CustList<OperationNode> childrenNodes_ = _par.getChildrenNodes();
        CustList<NamedArgumentOperation> filter_ = out_.getParameterFilter();
        CustList<NamedArgumentOperation> filterErr_ = out_.getParameterFilterErr();
        CustList<OperationNode> positionalArgs_ = out_.getPositional();
        StringList names_ = new StringList();
        int delta_ = getDeltaCount(_par.getFirstChild());
        boolean ok_ = true;
        for (OperationNode o: childrenNodes_) {
            if (o instanceof NamedArgumentOperation) {
                String name_ = ((NamedArgumentOperation) o).getName();
                OperationNode next_ = o.getNextSibling();
                if (StringUtil.contains(names_,name_) || !(next_ instanceof NamedArgumentOperation)&& next_ != null) {
                    ok_ = false;
                    filterErr_.add(((NamedArgumentOperation) o));
                }
                names_.add(name_);
                filter_.add(((NamedArgumentOperation) o));
                if (out_.getIndex() == -1) {
                    out_.setIndex(o.getIndexChild()+delta_);
                }
            } else {
                if (!(o instanceof VarargOperation
                        || o instanceof IdFctOperation)) {
                    positionalArgs_.add(o);
                }
            }
        }
        out_.setOk(ok_);
        return out_;
    }
    static String getVarargParam(CustList<OperationNode> _children) {
        if (!_children.isEmpty() && _children.first() instanceof VarargOperation) {
            return ((VarargOperation)_children.first()).getClassName();
        }
        return "";
    }

    protected static String tryGetRetType(AnalyzedPageEl _page) {
        FunctionBlock f_ = _page.getCurrentFct();
        if (f_ instanceof NamedFunctionBlock) {
            NamedFunctionBlock n_ = (NamedFunctionBlock) f_;
            String ret_ = n_.getImportedReturnType();
            String void_ = _page.getAliasVoid();
            if (!StringUtil.quickEq(ret_, void_)) {
                return ret_;
            }
        }
        if (f_ instanceof SwitchMethodBlock) {
            SwitchMethodBlock n_ = (SwitchMethodBlock) f_;
            return n_.getRetType();
        }
        return EMPTY_STRING;
    }
    protected static String tryGetTypeAff(OperationNode _m, int _indexChild) {
        if (_m instanceof CastOperation) {
            CastOperation c_ = (CastOperation) _m;
            return c_.getClassName();
        } else if (_m instanceof WrappOperation) {
            return _m.getResultClass().getSingleNameOrEmpty();
        } else if (_m instanceof AffectationOperation) {
            AffectationOperation a_ = (AffectationOperation) _m;
            SettableElResult s_ = null;
            if (_indexChild > 0) {
                s_ = AffectationOperation.tryGetSettable(a_);
            }
            if (s_ != null) {
                AnaClassArgumentMatching c_ = s_.getResultClass();
                return c_.getSingleNameOrEmpty();
            }
        }
        return EMPTY_STRING;
    }

    protected static int getDeltaCount(OperationNode _firstChild) {
        int deltaCount_ = 0;
        if (_firstChild instanceof IdFctOperation || _firstChild instanceof VarargOperation) {
            deltaCount_++;
        }
        return deltaCount_;
    }

    protected static int getDeltaCount(boolean _list,OperationNode _firstChild) {
        if (_list) {
            return 0;
        }
        int deltaCount_ = 0;
        if (_firstChild instanceof IdFctOperation || _firstChild instanceof VarargOperation) {
            deltaCount_++;
        }
        return deltaCount_;
    }

    protected static void tryGetCtors(String _typeInfer, CustList<ConstructorInfo> _ctors, AnalyzedPageEl _page, AnaGeneType _anaGeneType) {
        String base_ = StringExpUtil.getIdFromAllTypes(_typeInfer);
        if (_anaGeneType instanceof StandardType) {
            for (StandardConstructor e: ((StandardType)_anaGeneType).getConstructors()) {
                ConstructorId ctor_ = e.getId().copy(base_);
                ParametersGroup pg_ = new ParametersGroup();
                ConstructorInfo mloc_ = new ConstructorInfo();
                mloc_.setConstraints(ctor_);
                mloc_.setParametersNames(e.getParametersNames());
                mloc_.setParameters(pg_);
                mloc_.setClassName(_typeInfer);
                mloc_.format(_page);
                _ctors.add(mloc_);
            }
        }
        if (_anaGeneType instanceof RootBlock) {
            for (ConstructorBlock e: ((RootBlock)_anaGeneType).getConstructorBlocks()) {
                ConstructorId ctor_ = e.getId().copy(base_);
                if (excludeCust((RootBlock) _anaGeneType, null,-1, e, _page)) {
                    continue;
                }
                ParametersGroup pg_ = new ParametersGroup();
                ConstructorInfo mloc_ = new ConstructorInfo();
                mloc_.setConstraints(ctor_);
                mloc_.pair((RootBlock) _anaGeneType,e);
                mloc_.setParametersNames(e.getParametersNames());
                mloc_.setParameters(pg_);
                mloc_.setClassName(_typeInfer);
                mloc_.format(_page);
                _ctors.add(mloc_);
            }
        }

    }
    protected static String tryParamFormat(NameParametersFilter _filter, Parametrable _param, String _name, int _nbParentsInfer, String _type, StringMap<String> _vars, AnalyzedPageEl _page) {
        if (!isValidNameIndex(_filter,_param,_name)) {
            return null;
        }
        int ind_ = StringUtil.indexOf(_param.getParametersNames(), _name);
        return tryFormat(_param, ind_, _nbParentsInfer, _type, _vars, _page);
    }
    protected static StringList tryParamFormatFct(NameParametersFilter _filter, Parametrable _param, String _name, int _nbParentsInfer, String _type, String _full,StringMap<String> _vars, AnalyzedPageEl _page) {
        if (!isValidNameIndex(_filter,_param,_name)) {
            return new StringList();
        }
        int ind_ = StringUtil.indexOf(_param.getParametersNames(), _name);
        return tryFormatFct(_param, ind_, _nbParentsInfer, _type, _full, _vars, _page);
    }
    protected static StringList tryParamFormatFctRef(NameParametersFilter _filter, Parametrable _param, String _name, int _nbParentsInfer, String _type, StringMap<String> _vars, AnalyzedPageEl _page) {
        if (!isValidNameIndex(_filter,_param,_name)) {
            return new StringList();
        }
        int ind_ = StringUtil.indexOf(_param.getParametersNames(), _name);
        return tryFormatFctRef(_param, ind_, _nbParentsInfer, _type, _vars, _page);
    }
    protected static boolean isValidNameIndex(NameParametersFilter _filter, Parametrable _param, String _name) {
        int ind_ = StringUtil.indexOf(_param.getParametersNames(), _name);
        StringList formattedParams_ = _param.getFormattedParams();
        if (!formattedParams_.isValidIndex(ind_)) {
            return false;
        }
        int lengthArgs_ = _filter.getPositional().size();
        return ind_ >= Math.min(lengthArgs_, _filter.getIndex());
    }
    protected static String tryParamVarargFormat(Parametrable _param, String _name) {
        if (!_param.isVararg()) {
            return null;
        }
        int ind_ = StringUtil.indexOf(_param.getParametersNames(), _name);
        StringList formattedParams_ = _param.getFormattedParams();
        if (!formattedParams_.isValidIndex(ind_)) {
            return null;
        }
        Identifiable idMethod_ = _param.getGeneFormatted();
        if (ind_ != idMethod_.getParametersTypesLength() - 1) {
            return null;
        }
        return StringExpUtil.getPrettyArrayType(idMethod_.getParametersTypes().last());
    }
    protected static String tryFormat(Parametrable _param, int _indexChild, int _nbParentsInfer, String _type, StringMap<String> _vars, AnalyzedPageEl _page) {
        String cp_ = tryGetParamDim(_param, _indexChild, _nbParentsInfer);
        if (cp_ == null) {
            return null;
        }
        return tryInferOrImplicit(_type, _vars, _page, cp_);
    }
    protected static StringList tryFormatFct(Parametrable _param, int _indexChild, int _nbParentsInfer, String _type,String _full, StringMap<String> _vars, AnalyzedPageEl _page) {
        String cp_ = tryGetParamDim(_param, _indexChild, _nbParentsInfer);
        if (cp_ == null) {
            return new StringList();
        }
        return tryInferOrImplicitFct(_type, _full, _vars, _page, cp_);
    }

    protected static StringList tryInferOrImplicitFct(String _type, String _full, StringMap<String> _vars, AnalyzedPageEl _page, String _cp) {
        String inferred_ = AnaTemplates.tryInfer(_type, _vars, _cp, _page);
        if (inferred_ != null) {
            return new StringList(inferred_);
        }
        return AnaTemplates.tryGetDeclaredImplicitCastFct(_cp, _vars, _full, _page, _page.getCurrentConstraints().getCurrentConstraints());
    }

    protected static StringList tryFormatFctRef(Parametrable _param, int _indexChild, int _nbParentsInfer, String _type, StringMap<String> _vars, AnalyzedPageEl _page) {
        String cp_ = tryGetParamDim(_param, _indexChild, _nbParentsInfer);
        if (cp_ == null) {
            return new StringList();
        }
        return tryInferOrImplicitFctRef(_type, _vars, _page, cp_);
    }

    protected static StringList tryInferOrImplicitFctRef(String _type, StringMap<String> _vars, AnalyzedPageEl _page, String _cp) {
        return tryInferOrImplicitFct(_type, _type, _vars, _page, _cp);
    }

    protected static String tryInferOrImplicit(String _type, StringMap<String> _vars, AnalyzedPageEl _page, String _cp) {
        String inferred_ = AnaTemplates.tryInfer(_type, _vars, _cp, _page);
        if (inferred_ != null) {
            return inferred_;
        }
        return AnaTemplates.tryGetDeclaredImplicitCast(_cp,_vars,_type,_page,_page.getCurrentConstraints().getCurrentConstraints());
    }

    protected static String tryGetParamDim(Parametrable _param, int _indexChild, int _nbParentsInfer) {
        String parametersType_ = tryGetParam(_param, _indexChild);
        if (parametersType_ == null) {
            return null;
        }
        boolean applyTwo_ = applyArrayOrElement(_param, _indexChild);
        String cp_ = StringExpUtil.getQuickComponentType(parametersType_, _nbParentsInfer);
        if (!applyTwo_) {
            if (isNotCorrectDim(cp_)) {
                return null;
            }
        } else {
            if (isNotCorrectDim(cp_)) {
                String cpTwo_ = StringExpUtil.getQuickComponentType(parametersType_, _nbParentsInfer -1);
                if (isNotCorrectDim(cpTwo_)) {
                    return null;
                }
                cp_ = cpTwo_;
            }
        }
        return cp_;
    }

    protected static String tryGetParamAny(Parametrable _param, int _indexChild, int _nbParentsInfer) {
        String parametersType_ = tryGetParam(_param, _indexChild);
        if (parametersType_ == null) {
            return null;
        }
        boolean applyTwo_ = applyArrayOrElement(_param, _indexChild);
        String cp_ = StringExpUtil.getQuickComponentType(parametersType_, _nbParentsInfer);
        if (applyTwo_) {
            if (cp_ == null) {
                cp_ = StringExpUtil.getQuickComponentType(parametersType_, _nbParentsInfer -1);
            }
        }
        return cp_;
    }

    protected static String tryGetParam(Parametrable _param, int _indexChild) {
        String parametersType_;
        Identifiable idMethod_ = _param.getGeneFormatted();
        if (!idMethod_.isVararg()) {
            if (idMethod_.getParametersTypesLength() <= _indexChild) {
                return null;
            }
            parametersType_ = idMethod_.getParametersType(_indexChild);
        } else {
            if (idMethod_.getParametersTypesLength() <= _indexChild) {
                parametersType_ = idMethod_.getParametersTypes().last();
            } else {
                parametersType_ = idMethod_.getParametersType(_indexChild);
            }
        }
        return parametersType_;
    }
    protected static boolean applyArrayOrElement(Parametrable _param, int _indexChild) {
        Identifiable idMethod_ = _param.getGeneFormatted();
        if (!idMethod_.isVararg()) {
            return false;
        } else {
            if (idMethod_.getParametersTypesLength() <= _indexChild) {
                return false;
            } else {
                return _indexChild + 1 == idMethod_.getParametersTypesLength();
            }
        }
    }

    protected void filterByNameReturnType(AnalyzedPageEl _page, String _trimMeth, CustList<CustList<MethodInfo>> _methodInfos) {
        boolean apply_ = applyMatching();
        filterByNameReturnType(_trimMeth, apply_, _methodInfos, _page, getParentMatching());
    }

    private boolean applyMatching() {
        boolean apply_ = false;
        OperationNode cur_ = this;
        OperationNode curPar_ = getParent();
        if (curPar_ instanceof AbstractDotOperation) {
            if (getIndexChild() > 0) {
                if (curPar_.getParent() == null) {
                    apply_ = true;
                } else {
                    cur_ = cur_.getParent();
                    curPar_ = curPar_.getParent();
                }
            }
        } else if (curPar_ instanceof WrappOperation){
            apply_ = true;
        } else if (curPar_ == null){
            apply_ = true;
        }
        while (curPar_ instanceof IdOperation && curPar_.getOperations().getValues().size() <= 1) {
            curPar_ = curPar_.getParent();
            cur_ = cur_.getParent();
        }
        if (curPar_ instanceof AffectationOperation) {
            if (cur_.getIndexChild() > 0) {
                apply_ = true;
            }
        }
        return apply_;
    }
    protected OperationNode getParentMatching() {
        OperationNode cur_ = this;
        OperationNode curPar_ = getParent();
        if (curPar_ instanceof AbstractDotOperation) {
            if (getIndexChild() > 0) {
                if (curPar_.getParent() == null) {
                    return null;
                } else {
                    cur_ = cur_.getParent();
                    curPar_ = curPar_.getParent();
                }
            }
        } else if (curPar_ instanceof WrappOperation){
            return curPar_;
        } else if (curPar_ == null){
            return null;
        }
        while (curPar_ instanceof IdOperation && curPar_.getOperations().getValues().size() <= 1) {
            curPar_ = curPar_.getParent();
            cur_ = cur_.getParent();
        }
        if (curPar_ instanceof AffectationOperation) {
            if (cur_.getIndexChild() > 0) {
                return curPar_;
            }
        }
        return null;
    }
    private static void filterByNameReturnType(String _trimMeth, boolean _apply, CustList<CustList<MethodInfo>> _methodInfos, AnalyzedPageEl _page, OperationNode _parentMatching) {
        int len_ = _methodInfos.size();
        for (int i = 0; i < len_; i++) {
            int gr_ = _methodInfos.get(i).size();
            CustList<MethodInfo> newList_ = new CustList<MethodInfo>();
            for (int j = 0; j < gr_; j++) {
                MethodInfo methodInfo_ = _methodInfos.get(i).get(j);
                if (!StringUtil.quickEq(methodInfo_.getConstraints().getName(),_trimMeth)) {
                    continue;
                }
                newList_.add(methodInfo_);
            }
            _methodInfos.set(i, newList_);
        }
        filterByReturnType(_apply, _methodInfos, _page, _parentMatching);
    }

    protected static void filterByReturnType(boolean _apply, CustList<CustList<MethodInfo>> _methodInfos, AnalyzedPageEl _page, OperationNode _parentMatching) {
        String typeAff_ = EMPTY_STRING;
        Block cur_ = _page.getCurrentBlock();
        if (_apply) {
            if (_parentMatching == null &&cur_ instanceof ReturnMethod) {
                typeAff_ = tryGetRetType(_page);
            } else {
                typeAff_ = tryGetTypeAff(_parentMatching, 1);
            }
        }
        filterByReturnType(typeAff_, _methodInfos, _page);
    }

    private static void filterByReturnType(String _typeAff, CustList<CustList<MethodInfo>> _methodInfos, AnalyzedPageEl _page) {
        KeyWords keyWords_ = _page.getKeyWords();
        String keyWordVar_ = keyWords_.getKeyWordVar();
        if (isUndefined(_typeAff, keyWordVar_)) {
            return;
        }
        int len_ = _methodInfos.size();
        Mapping mapping_ = new Mapping();
        mapping_.setParam(_typeAff);
        StringMap<StringList> currVars_ = _page.getCurrentConstraints().getCurrentConstraints();
        mapping_.setMapping(currVars_);
        for (int i = 0; i < len_; i++) {
            int gr_ = _methodInfos.get(i).size();
            CustList<MethodInfo> newList_ = new CustList<MethodInfo>();
            for (int j = 0; j < gr_; j++) {
                MethodInfo methodInfo_ = _methodInfos.get(i).get(j);
                String returnType_ = methodInfo_.getReturnType();
                mapping_.setArg(returnType_);
                if (methodInfo_.getConstraints().isRetRef()) {
                    if (!StringUtil.quickEq(returnType_,_typeAff)) {
                        continue;
                    }
                } else {
                    if (!AnaTemplates.isCorrectOrNumbers(mapping_, _page)) {
                        ClassMethodIdReturn res_ = tryGetDeclaredImplicitCast(_typeAff, new AnaClassArgumentMatching(returnType_), _page);
                        if (!res_.isFoundMethod()) {
                            continue;
                        }
                    }
                }
                newList_.add(methodInfo_);
            }
            _methodInfos.set(i, newList_);
        }
    }

    protected static ClassMethodId getTrueFalse(ClassMethodId _feedBase, AnalyzedPageEl _page) {
        ClassMethodId f_ = _feedBase;
        if (f_ != null) {
            MethodId constraints_ = f_.getConstraints();
            String name_ = constraints_.getName();
            String className_ = f_.getClassName();
            StringList parametersTypes_ = constraints_.getParametersTypes();
            parametersTypes_.add(0, _page.getAliasPrimBoolean());
            f_ = new ClassMethodId(className_,new MethodId(MethodAccessKind.STATIC,name_,parametersTypes_));
        }
        return f_;
    }
    public static boolean isTrueFalseKeyWord(String _trimMeth, AnalyzedPageEl _page) {
        return StringUtil.quickEq(_trimMeth, _page.getKeyWords().getKeyWordTrue())
                || StringUtil.quickEq(_trimMeth, _page.getKeyWords().getKeyWordFalse());
    }
    protected static boolean isNotCorrectDim(String _cp) {
        return _cp == null||_cp.startsWith("[");
    }

    protected static boolean isUndefined(String _typeAff, String _keyWordVar) {
        return _typeAff.isEmpty() || StringUtil.quickEq(_typeAff, _keyWordVar);
    }

    static StringList getBounds(String _cl, AnalyzedPageEl _page) {
        String objectClassName_ = _page.getAliasObject();
        StringList bounds_ = new StringList();
        if (_cl.startsWith(AnaTemplates.PREFIX_VAR_TYPE)) {
            AnaGeneType gl_ = _page.getGlobalType();
            StringMap<StringList> mapping_ = new StringMap<StringList>();
            for (TypeVar t: ContextUtil.getParamTypesMapValues(gl_)) {
                mapping_.put(t.getName(), t.getConstraints());
            }
            bounds_.addAllElts(Mapping.getAllUpperBounds(mapping_, _cl.substring(1), objectClassName_));
        } else {
            bounds_.add(_cl);
        }
        return bounds_;
    }

    static void unwrapArgsFct(Identifiable _id, int _natvararg, String _lasttype, CustList<OperationNode> _args, AnalyzedPageEl _page) {
        if (_natvararg > -1) {
            int lenCh_ = _args.size();
            for (int i = IndexConstants.FIRST_INDEX; i < lenCh_; i++) {
                OperationNode a_ = _args.get(i);
                if (i >= _natvararg) {
                    if (AnaTypeUtil.isPrimitive(_lasttype, _page)) {
                        a_.getResultClass().setUnwrapObject(_lasttype, _page.getPrimitiveTypes());
                    }
                } else {
                    String param_ = _id.getParametersTypes().get(i);
                    if (AnaTypeUtil.isPrimitive(param_, _page)) {
                        a_.getResultClass().setUnwrapObject(param_, _page.getPrimitiveTypes());
                    }
                }
            }
        } else {
            int lenCh_ = _args.size();
            for (int i = IndexConstants.FIRST_INDEX; i < lenCh_; i++) {
                OperationNode a_ = _args.get(i);
                String param_ = _id.getParametersTypes().get(i);
                if (i + 1 == lenCh_ && _id.isVararg()) {
                    param_ = StringExpUtil.getPrettyArrayType(param_);
                }
                if (AnaTypeUtil.isPrimitive(param_, _page)) {
                    a_.getResultClass().setUnwrapObject(param_, _page.getPrimitiveTypes());
                }
            }
        }
    }
    final int lookOnlyForVarArg() {
        OperationNode first_ = getFirstChild();
        int from_ = 1;
        if (first_ == null) {
            return -1;
        }
        if (!(first_ instanceof VarargOperation)&&!(first_ instanceof IdFctOperation)) {
            return -1;
        }
        CustList<OperationNode> ch_ = getChildrenNodes();
        int firstOpt_ = 0;
        boolean found_ = false;
        int len_ = ch_.size();
        for (int i = from_; i < len_;i++) {
            if (ch_.get(i) instanceof FirstOptOperation) {
                firstOpt_ = i + 1 - from_;
                found_ = true;
                break;
            }
        }
        if (!found_ && first_ instanceof IdFctOperation) {
            return -1;
        }
        return firstOpt_;
    }
    final ClassMethodIdAncestor lookOnlyForId() {
        OperationNode first_ = getFirstChild();
        if (first_ == null) {
            return null;
        }
        if (!(first_ instanceof IdFctOperation)) {
            return null;
        }
        return ((IdFctOperation)first_).getMethod();
    }
    @Override
    public final void setIntermediateDotted() {
        intermediate = true;
    }

    @Override
    public final boolean isIntermediateDottedOperation() {
        return intermediate;
    }

    public final AnaClassArgumentMatching getPreviousResultClass() {
        return previousResultClass;
    }

    @Override
    public final void setPreviousResultClass(AnaClassArgumentMatching _previousResultClass, MethodAccessKind _staticAccess) {
        previousResultClass = _previousResultClass;
        staticAccess = _staticAccess;
    }

    public final MethodAccessKind isStaticAccess() {
        return staticAccess;
    }

    public final void setStaticAccess(MethodAccessKind _staticAccess) {
        staticAccess = _staticAccess;
    }

}
