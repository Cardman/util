package code.expressionlanguage.methods;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Mapping;
import code.expressionlanguage.Templates;
import code.expressionlanguage.methods.exceptions.CyclicCallingException;
import code.expressionlanguage.methods.exceptions.UndefinedSuperConstructorException;
import code.expressionlanguage.methods.util.BadAccessMethod;
import code.expressionlanguage.methods.util.BadInheritedClass;
import code.expressionlanguage.methods.util.BadReturnTypeInherit;
import code.expressionlanguage.methods.util.ConstructorEdge;
import code.expressionlanguage.methods.util.DuplicateParamMethod;
import code.expressionlanguage.methods.util.ReservedMethod;
import code.expressionlanguage.methods.util.TypeVar;
import code.expressionlanguage.opers.OperationNode;
import code.expressionlanguage.opers.util.ClassMetaInfo;
import code.expressionlanguage.opers.util.ClassName;
import code.expressionlanguage.opers.util.ConstructorId;
import code.expressionlanguage.opers.util.ConstructorMetaInfo;
import code.expressionlanguage.opers.util.MethodId;
import code.sml.Element;
import code.sml.RowCol;
import code.util.CustList;
import code.util.EntryCust;
import code.util.EqList;
import code.util.NatTreeMap;
import code.util.ObjectNotNullMap;
import code.util.StringList;
import code.util.StringMap;
import code.util.graphs.Graph;

public final class EnumBlock extends RootBlock implements UniqueRootedBlock {

    private final String superClass;
    private final StringList allSuperClasses = new StringList();

    private final StringList allSuperTypes = new StringList();

    private final StringList directInterfaces = new StringList();

    private final StringList allDirectInterfaces = new StringList();

    private final StringList allInterfaces = new StringList();

    private final StringList allSortedInterfaces = new StringList();

    private final StringList allNeededSortedInterfaces = new StringList();

    public EnumBlock(Element _el, ContextEl _importingPage, int _indexChild,
            BracedBlock _m) {
        super(_el, _importingPage, _indexChild, _m);
        String superClass_ = _el.getAttribute(ATTRIBUTE_SUPER_CLASS);
        if (superClass_.trim().isEmpty()) {
            superClass_ = Object.class.getName();
        }
        superClass = superClass_;
        int i_ = CustList.FIRST_INDEX;
        while (_el.hasAttribute(ATTRIBUTE_CLASS+i_)) {
            directInterfaces.add(_el.getAttribute(ATTRIBUTE_CLASS+i_));
            i_++;
        }
        directInterfaces.add(PredefinedClasses.ENUM);
    }

    @Override
    public void setupBasicOverrides(ContextEl _context) {
        for (MethodBlock m: Classes.getMethodBlocks(this)) {
            if (m.getId().eq(new MethodId(false, OperationNode.METH_NAME, new EqList<ClassName>()))) {
                ReservedMethod r_ = new ReservedMethod();
                r_.setFileName(getFullName());
                r_.setRc(m.getRowCol(0, _context.getTabWidth(), EMPTY_STRING));
                r_.setMethodeId(m.getId());
                _context.getClasses().getErrorsDet().add(r_);
            }
            if (m.getId().eq(new MethodId(false, OperationNode.METH_ORDINAL, new EqList<ClassName>()))) {
                ReservedMethod r_ = new ReservedMethod();
                r_.setFileName(getFullName());
                r_.setRc(m.getRowCol(0, _context.getTabWidth(), EMPTY_STRING));
                r_.setMethodeId(m.getId());
                _context.getClasses().getErrorsDet().add(r_);
            }
            if (m.getId().eq(new MethodId(true, OperationNode.METH_VALUES, new EqList<ClassName>()))) {
                ReservedMethod r_ = new ReservedMethod();
                r_.setFileName(getFullName());
                r_.setRc(m.getRowCol(0, _context.getTabWidth(), EMPTY_STRING));
                r_.setMethodeId(m.getId());
                _context.getClasses().getErrorsDet().add(r_);
            }
            if (m.getId().eq(new MethodId(true, OperationNode.METH_VALUEOF, new EqList<ClassName>(new ClassName(String.class.getName(), false))))) {
                ReservedMethod r_ = new ReservedMethod();
                r_.setFileName(getFullName());
                r_.setRc(m.getRowCol(0, _context.getTabWidth(), EMPTY_STRING));
                r_.setMethodeId(m.getId());
                _context.getClasses().getErrorsDet().add(r_);
            }
        }
        Classes classesRef_ = _context.getClasses();
        StringList classNames_ = getAllGenericSuperClasses(classesRef_);
        String fullName_ = getFullName();
        for (String b: getCustomDirectSuperClasses()) {
            ClassBlock bBl_ = (ClassBlock) classesRef_.getClassBody(b);
            AccessEnum acc_ = bBl_.getMaximumAccessConstructors(_context);
            if (acc_.ordinal() <= AccessEnum.PROTECTED.ordinal()) {
                continue;
            }
            if (acc_ == AccessEnum.PACKAGE) {
                if (StringList.quickEq(getPackageName(), bBl_.getPackageName())) {
                    continue;
                }
            }
            BadInheritedClass inherit_;
            inherit_ = new BadInheritedClass();
            inherit_.setClassName(fullName_);
            inherit_.setFileName(fullName_);
            inherit_.setRc(new RowCol());
            classesRef_.getErrorsDet().add(inherit_);
        }
        for (Block b: Classes.getDirectChildren(this)) {
            if (!(b instanceof ConstructorBlock)) {
                continue;
            }
            ConstructorBlock c_ = (ConstructorBlock) b;
            for (String s: classNames_) {
                if (classesRef_.getConstructorBodiesByFormattedId(s, c_.getId()).size() > 1) {
                    DuplicateParamMethod duplicate_ = new DuplicateParamMethod();
                    duplicate_.setFileName(getFullName());
                    duplicate_.setRc(new RowCol());
                    duplicate_.setCommonSignature(c_.getId().getSignature());
                    duplicate_.setOtherType(s);
                    classesRef_.getErrorsDet().add(duplicate_);
                }
            }
        }
        useSuperTypesOverrides(_context);
        StringMap<StringList> vars_ = new StringMap<StringList>();
        for (TypeVar t: getParamTypes()) {
            vars_.put(t.getName(), t.getConstraints());
        }
        String gene_ = getGenericString();
        StringList classes_ = new StringList(gene_);
        classes_.addAllElts(classNames_);
        for (String s: getAllGenericInterfaces(classesRef_)) {
            String base_ = StringList.getAllTypes(s).first();
            RootBlock r_ = classesRef_.getClassBody(base_);
            for (MethodBlock m: Classes.getMethodBlocks(r_)) {
                if (m.isStaticMethod()) {
                    continue;
                }
                String formattedSuper_ = Templates.getFullTypeByBases(gene_, s, _context);
                MethodId id_ = m.getId().format(formattedSuper_, classesRef_);
                for (String c: classes_) {
                    CustList<MethodBlock> mBases_ = classesRef_.getMethodBodiesById(c, id_);
                    if (mBases_.isEmpty()) {
                        continue;
                    }
                    MethodBlock mBase_ = mBases_.first();
                    if (mBase_.isStaticMethod()) {
                        continue;
                    }
                    if (m.getAccess().ordinal() > mBase_.getAccess().ordinal()) {
                        BadAccessMethod err_;
                        err_ = new BadAccessMethod();
                        err_.setFileName(getFullName());
                        err_.setRc(m.getAttributes().getVal(ATTRIBUTE_ACCESS));
                        err_.setId(m.getId());
                        classesRef_.getErrorsDet().add(err_);
                    }
                    String retBase_ = m.getReturnType();
                    String retDerive_ = mBase_.getReturnType();
                    String formattedRetDer_ = Templates.format(c, retDerive_, classesRef_);
                    String formattedRetBase_ = Templates.format(s, retBase_, classesRef_);
                    Mapping mapping_ = new Mapping();
                    mapping_.getMapping().putAllMap(vars_);
                    mapping_.setArg(formattedRetDer_);
                    mapping_.setParam(formattedRetBase_);
                    if (StringList.quickEq(retBase_, OperationNode.VOID_RETURN)) {
                        if (!StringList.quickEq(retDerive_, OperationNode.VOID_RETURN)) {
                            BadReturnTypeInherit err_;
                            err_ = new BadReturnTypeInherit();
                            err_.setFileName(getFullName());
                            err_.setRc(mBase_.getAttributes().getVal(ATTRIBUTE_CLASS));
                            err_.setReturnType(retDerive_);
                            err_.setMethod(mBase_.getId());
                            err_.setParentClass(c);
                            classesRef_.getErrorsDet().add(err_);
                            //throw ex
                        }
                    } else if (!Templates.isCorrect(mapping_, _context)) {
                        //throw ex
                        BadReturnTypeInherit err_;
                        err_ = new BadReturnTypeInherit();
                        err_.setFileName(getFullName());
                        err_.setRc(mBase_.getAttributes().getVal(ATTRIBUTE_CLASS));
                        err_.setReturnType(retDerive_);
                        err_.setMethod(mBase_.getId());
                        err_.setParentClass(c);
                        classesRef_.getErrorsDet().add(err_);
                    }
                    break;
                }
            }
        }
    }

    @Override
    public StringList getDirectGenericSuperTypes() {
        StringList superTypes_ = new StringList();
        if (!StringList.quickEq(superClass, Object.class.getName())) {
            superTypes_.add(superClass);
        }
        superTypes_.addAllElts(directInterfaces);
        return superTypes_;
    }

    @Override
    public StringList getAllInterfaces() {
        return allInterfaces;
    }
    @Override
    public StringList getDirectInterfaces() {
        StringList direct_ = new StringList();
        for (String s: directInterfaces) {
            int index_ = s.indexOf(LT);
            if (index_ > CustList.INDEX_NOT_FOUND_ELT) {
                direct_.add(s.substring(CustList.FIRST_INDEX, index_));
            } else {
                direct_.add(s);
            }
        }
        return direct_;
    }

    @Override
    public StringList getDirectGenericInterfaces() {
        return directInterfaces;
    }

    @Override
    public StringList getAllDirectInterfaces() {
        return allDirectInterfaces;
    }

    @Override
    public StringList getAllNeededSortedInterfaces() {
        return allNeededSortedInterfaces;
    }

    @Override
    public StringList getAllSortedInterfaces() {
        return allSortedInterfaces;
    }

    @Override
    public void validateConstructors(ContextEl _cont) {
        boolean opt_ = optionalCallConstr(_cont);
        String idType_ = getFullName();
        ClassMetaInfo curMeta_ = _cont.getClasses().getClassMetaInfo(idType_);
        ObjectNotNullMap<ConstructorId, ConstructorMetaInfo> c_;
        c_ = curMeta_.getConstructors();
        for (EntryCust<ConstructorId, ConstructorMetaInfo> e: c_.entryList()) {
            ConstructorBlock b_ = _cont.getClasses().getConstructorBodiesById(idType_, e.getKey()).first();
            b_.setupInstancingStep(_cont);
        }
        for (EntryCust<ConstructorId, ConstructorMetaInfo> e: c_.entryList()) {
            ConstructorBlock b_ = _cont.getClasses().getConstructorBodiesById(idType_, e.getKey()).first();
            if (b_.implicitConstr() && !opt_) {
                throw new UndefinedSuperConstructorException(_cont.joinPages());
            }
        }
        EqList<ConstructorId> l_ = new EqList<ConstructorId>();
        for (EntryCust<ConstructorId, ConstructorMetaInfo> e: c_.entryList()) {
            ConstructorBlock b_ = _cont.getClasses().getConstructorBodiesById(idType_, e.getKey()).first();
            if (b_.getConstIdSameClass() != null) {
                l_.add(e.getKey());
            }
        }
        Graph<ConstructorEdge> graph_;
        graph_ = new Graph<ConstructorEdge>();
        for (ConstructorId f: l_) {
            ConstructorBlock b_ = _cont.getClasses().getConstructorBodiesById(idType_, f).first();
            ConstructorId co_ = b_.getConstIdSameClass();
            ConstructorEdge f_ = new ConstructorEdge(f);
            ConstructorEdge t_ = new ConstructorEdge(co_);
            graph_.addSegment(f_, t_);
        }
        EqList<ConstructorEdge> cycle_ = graph_.elementsCycle();
        if (!cycle_.isEmpty()) {
            //TODO souligner
            throw new CyclicCallingException(_cont.joinPages());
        }
    }

    private boolean optionalCallConstr(ContextEl _cont) {
        ClassMetaInfo clMeta_ = _cont.getClasses().getClassMetaInfo(superClass);
        if (clMeta_ == null) {
            return true;
        }
        ObjectNotNullMap<ConstructorId, ConstructorMetaInfo> m_;
        m_ = clMeta_.getConstructors();
        if (m_.isEmpty()) {
            return true;
        }
        for (EntryCust<ConstructorId, ConstructorMetaInfo> e: m_.entryList()) {
            CustList<ConstructorBlock> formatted_ = _cont.getClasses().getConstructorBodiesById(superClass, e.getKey());
            if (!_cont.getClasses().canAccess(getFullName(), formatted_.first(), _cont)) {
                continue;
            }
            if (e.getKey().getParametersTypes().isEmpty()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public StringList getAllSuperClasses() {
        return allSuperClasses;
    }

    @Override
    public StringList getAllSuperTypes() {
        return allSuperTypes;
    }

    @Override
    public NatTreeMap<String,String> getClassNames() {
        NatTreeMap<String,String> tr_ = new NatTreeMap<String,String>();
        tr_.put(ATTRIBUTE_NAME, getFullDefinition());
        tr_.put(ATTRIBUTE_SUPER_CLASS, superClass);
        int i_ = 0;
        for (String t: directInterfaces) {
            tr_.put(ATTRIBUTE_CLASS+i_, t);
            i_++;
        }
        return tr_;
    }

    @Override
    boolean canBeIncrementedNextGroup() {
        return false;
    }

    @Override
    boolean canBeIncrementedCurGroup() {
        return false;
    }

    @Override
    boolean canBeLastOfBlockGroup() {
        return false;
    }

    @Override
    public String getTagName() {
        return TAG_ENUM;
    }

    @Override
    public StringList getDirectGenericSuperClasses() {
        StringList classes_ = new StringList();
        classes_.add(superClass);
        return classes_;
    }

    @Override
    public StringList getDirectSuperClasses() {
        StringList classes_ = new StringList();
        int index_ = superClass.indexOf(LT);
        if (index_ > CustList.INDEX_NOT_FOUND_ELT) {
            classes_.add(superClass.substring(CustList.FIRST_INDEX, index_));
        } else {
            classes_.add(superClass);
        }
        return classes_;
    }

    @Override
    public String getSuperClass() {
        int index_ = superClass.indexOf(LT);
        if (index_ > CustList.INDEX_NOT_FOUND_ELT) {
            return superClass.substring(CustList.FIRST_INDEX, index_);
        }
        return superClass;
    }

    @Override
    public String getGenericSuperClass() {
        return superClass;
    }

    @Override
    public RootBlock belong() {
        return this;
    }

    @Override
    public boolean isFinalType() {
        return true;
    }

    @Override
    public boolean isAbstractType() {
        return true;
    }

    @Override
    public boolean mustImplement() {
        return true;
    }

    @Override
    public StringList getAllGenericSuperClasses(Classes _classes) {
        StringList allSuperTypes_ = getAllGenericSuperTypes(_classes);
        StringList allGenericSuperClasses_ = new StringList();
        for (String s: allSuperTypes_) {
            String base_ = StringList.getAllTypes(s).first();
            if (_classes.getClassBody(base_) instanceof ClassBlock) {
                allGenericSuperClasses_.add(s);
            }
        }
        return allGenericSuperClasses_;
    }

    @Override
    public StringList getAllGenericInterfaces(Classes _classes) {
        StringList allSuperTypes_ = getAllGenericSuperTypes(_classes);
        StringList allGenericInterfaces_ = new StringList();
        for (String s: allSuperTypes_) {
            String base_ = StringList.getAllTypes(s).first();
            if (_classes.getClassBody(base_) instanceof InterfaceBlock) {
                allGenericInterfaces_.add(s);
            }
        }
        return allGenericInterfaces_;
    }
    @Override
    public StringList getAllSuperClasses(ContextEl _classes) {
        StringList superClasses_ = new StringList();
        String current_ = getFullName();
        String objectAlias_ = _classes.getStandards().getAliasObject();
        while (!StringList.quickEq(current_, objectAlias_)) {
            UniqueRootedBlock r_ = (UniqueRootedBlock) _classes.getClasses().getClassBody(current_);
            String superClass_ = r_.getSuperClass();
            superClasses_.add(superClass_);
            current_ = superClass_;
        }
        return superClasses_;
    }
}
