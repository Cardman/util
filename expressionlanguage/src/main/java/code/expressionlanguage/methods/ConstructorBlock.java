package code.expressionlanguage.methods;
import code.expressionlanguage.Analyzable;
import code.expressionlanguage.AnalyzedPageEl;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.GeneConstructor;
import code.expressionlanguage.errors.custom.BadInheritedClass;
import code.expressionlanguage.errors.custom.UnassignedFinalField;
import code.expressionlanguage.files.OffsetAccessInfo;
import code.expressionlanguage.files.OffsetStringInfo;
import code.expressionlanguage.files.OffsetsBlock;
import code.expressionlanguage.inherits.Templates;
import code.expressionlanguage.opers.util.AssignedVariables;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.ConstructorId;
import code.expressionlanguage.opers.util.FieldInfo;
import code.expressionlanguage.opers.util.SimpleAssignment;
import code.util.CustList;
import code.util.EntryCust;
import code.util.IdMap;
import code.util.Numbers;
import code.util.StringList;
import code.util.StringMap;

public final class ConstructorBlock extends NamedFunctionBlock implements GeneConstructor {

    private ConstructorId constIdSameClass;

    private boolean implicitCallSuper;

    public ConstructorBlock(OffsetAccessInfo _access,
                            OffsetStringInfo _retType, OffsetStringInfo _fctName,
                            StringList _paramTypes, Numbers<Integer> _paramTypesOffset,
                            StringList _paramNames, Numbers<Integer> _paramNamesOffset, OffsetsBlock _offset) {
        super(_access, _retType, _fctName, _paramTypes, _paramTypesOffset, _paramNames, _paramNamesOffset, _offset);
    }

    @Override
    public ConstructorId getId() {
        RootBlock clBlock_ = (RootBlock) getParent();
        String name_ = clBlock_.getFullName();
        StringList types_ = getImportedParametersTypes();
        int len_ = types_.size();
        StringList pTypes_ = new StringList();
        for (int i = CustList.FIRST_INDEX; i < len_; i++) {
            String n_ = types_.get(i);
            pTypes_.add(n_);
        }
        return new ConstructorId(name_, pTypes_, isVarargs());
    }

    public ConstructorId getGenericId() {
        RootBlock clBlock_ = (RootBlock) getParent();
        String name_ = clBlock_.getGenericString();
        StringList types_ = getImportedParametersTypes();
        int len_ = types_.size();
        StringList pTypes_ = new StringList();
        for (int i = CustList.FIRST_INDEX; i < len_; i++) {
            String n_ = types_.get(i);
            pTypes_.add(n_);
        }
        return new ConstructorId(name_, pTypes_, isVarargs());
    }
    public void setupInstancingStep(ContextEl _cont) {
        AnalyzedPageEl page_ = _cont.getAnalyzing();
        page_.setGlobalOffset(getOffset().getOffsetTrim());
        page_.setOffset(0);
        Block first_ = getFirstChild();
        if (!(first_ instanceof Line)) {
            implicitCallSuper = true;
            return;
        }
        Line l_ = (Line) first_;
        if (l_.isCallInts()) {
            implicitCallSuper = true;
        }
        if (l_.isCallSuper() || l_.isCallInts()) {
            return;
        }
        if (l_.isCallThis()) {
            constIdSameClass = l_.getConstId();
            return;
        }
        implicitCallSuper = true;
    }

    public boolean implicitConstr() {
        return implicitCallSuper;
    }

    public ConstructorId getConstIdSameClass() {
        return constIdSameClass;
    }

    @Override
    public boolean isStaticContext() {
        return false;
    }

    @Override
    public void buildImportedReturnTypes(Analyzable _stds) {
        String void_ = _stds.getStandards().getAliasVoid();
        setImportedReturnType(void_);
    }

    @Override
    public String getName() {
        return EMPTY_STRING;
    }

    @Override
    public RootBlock belong() {
        return (RootBlock) getParent();
    }

    public String getDeclaringType() {
        RootBlock clBlock_ = (RootBlock) getParent();
        return clBlock_.getFullName();
    }
    @Override
    public void setAssignmentAfterCall(Analyzable _an, AnalyzingEl _anEl) {
        setAssignmentAfter(_an,_anEl);
        Block firstChild_ = getFirstChild();
        StringList ints_ = new StringList();
        StringList filteredCtor_ = _an.getNeedInterfaces();
        boolean checkThis_ = false;
        while (firstChild_ != null) {
            if (!(firstChild_ instanceof Line)) {
                break;
            }
            Line l_ = (Line) firstChild_;
            if (l_.isCallThis()) {
                checkThis_ = true;
                break;
            }
            if (l_.isCallInts()) {
                ints_.add(l_.getCalledInterface());
            }
            firstChild_ = firstChild_.getNextSibling();
        }
        if (!checkThis_) {
            if (!StringList.equalsSet(filteredCtor_, ints_)) {
                BadInheritedClass undef_;
                undef_ = new BadInheritedClass();
                undef_.setClassName(((RootBlock) getParent()).getFullName());
                undef_.setFileName(getFile().getFileName());
                undef_.setIndexFile(0);
                _an.getClasses().addError(undef_);
            }
        } else {
            if (!ints_.isEmpty()) {
                BadInheritedClass undef_;
                undef_ = new BadInheritedClass();
                undef_.setClassName(((RootBlock) getParent()).getFullName());
                undef_.setFileName(getFile().getFileName());
                undef_.setIndexFile(0);
                _an.getClasses().addError(undef_);
            }
        }
        
        IdMap<Block, AssignedVariables> id_ = _an.getAssignedVariables().getFinalVariables();
        for (EntryCust<ReturnMehod, StringMap<SimpleAssignment>> r: _anEl.getAssignments().entryList()) {
            for (EntryCust<String, SimpleAssignment> f: r.getValue().entryList()) {
                checkAssignments(_an, f);
            }
        }
        if (_anEl.canCompleteNormally(this)) {
            AssignedVariables assTar_ = id_.getVal(this);
            for (EntryCust<String, SimpleAssignment> f: assTar_.getFieldsRoot().entryList()) {
                checkAssignments(_an, f);
            }
        }
    }

    private void checkAssignments(Analyzable _an, EntryCust<String, SimpleAssignment> _pair) {
        String cl_ = Templates.getIdFromAllTypes(_an.getGlobalClass());
        ClassField key_ = new ClassField(cl_, _pair.getKey());
        FieldInfo finfo_ = _an.getFieldInfo(key_);
        if (!finfo_.isFinalField()) {
            return;
        }
        SimpleAssignment a_ = _pair.getValue();
        if (!a_.isAssignedAfter()) {
            //error
            UnassignedFinalField un_ = new UnassignedFinalField(key_);
            un_.setFileName(getFile().getFileName());
            un_.setIndexFile(getOffset().getOffsetTrim());
            _an.getClasses().addError(un_);
        }
    }
}
