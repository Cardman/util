package code.expressionlanguage.methods;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Mapping;
import code.expressionlanguage.OffsetAccessInfo;
import code.expressionlanguage.OffsetsBlock;
import code.expressionlanguage.PrimitiveTypeUtil;
import code.expressionlanguage.Templates;
import code.expressionlanguage.common.GeneMethod;
import code.expressionlanguage.common.GeneType;
import code.expressionlanguage.common.TypeUtil;
import code.expressionlanguage.methods.util.AbstractMethod;
import code.expressionlanguage.methods.util.BadFieldName;
import code.expressionlanguage.methods.util.BadMethodName;
import code.expressionlanguage.methods.util.BadParamName;
import code.expressionlanguage.methods.util.ConstructorEdge;
import code.expressionlanguage.methods.util.CyclicInheritingGraph;
import code.expressionlanguage.methods.util.DuplicateConstructor;
import code.expressionlanguage.methods.util.DuplicateField;
import code.expressionlanguage.methods.util.DuplicateMethod;
import code.expressionlanguage.methods.util.DuplicateParamName;
import code.expressionlanguage.methods.util.IncompatibilityReturnType;
import code.expressionlanguage.methods.util.TypeVar;
import code.expressionlanguage.methods.util.UndefinedSuperConstructor;
import code.expressionlanguage.methods.util.UnexpectedTagName;
import code.expressionlanguage.opers.ExpressionLanguage;
import code.expressionlanguage.opers.util.ClassFormattedMethodId;
import code.expressionlanguage.opers.util.ClassMethodId;
import code.expressionlanguage.opers.util.ConstructorId;
import code.expressionlanguage.opers.util.MethodId;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.stds.StandardMethod;
import code.expressionlanguage.stds.StandardType;
import code.sml.Element;
import code.sml.RowCol;
import code.util.CustList;
import code.util.EntryCust;
import code.util.EqList;
import code.util.NatTreeMap;
import code.util.Numbers;
import code.util.ObjectMap;
import code.util.StringList;
import code.util.StringMap;
import code.util.comparators.ComparatorIndexes;
import code.util.graphs.Graph;

public abstract class RootBlock extends BracedBlock implements GeneType, AccessingImportingBlock {

    private final String name;

    private final String packageName;

    private final AccessEnum access;

    private int accessOffset;

    private final String templateDef;

    private StringList imports = new StringList();

    private Numbers<Integer> importsOffset = new Numbers<Integer>();

    private ObjectMap<MethodId, EqList<ClassMethodId>> allOverridingMethods;

    private CustList<TypeVar> paramTypes = new CustList<TypeVar>();

    private StringMap<TypeVar> paramTypesMap = new StringMap<TypeVar>();

    private final StringList directSuperTypes = new StringList();

    private NatTreeMap<Integer, String> rowColDirectSuperTypes;
    private NatTreeMap<Integer, Boolean> explicitDirectSuperTypes = new NatTreeMap<Integer, Boolean>();

    private String realName;

    private String realPackageName;

    private int idRowCol;

    private int categoryOffset;

    private StringList staticInitInterfaces = new StringList();
    private StringList staticInitImportedInterfaces = new StringList();
    private Numbers<Integer> staticInitInterfacesOffset = new Numbers<Integer>();

    RootBlock(Element _el, ContextEl _importingPage, int _indexChild,
            BracedBlock _m) {
        super(_el, _importingPage, _indexChild, _m);
        allOverridingMethods = new ObjectMap<MethodId, EqList<ClassMethodId>>();
        name = _el.getAttribute(ATTRIBUTE_NAME);
        packageName = _el.getAttribute(ATTRIBUTE_PACKAGE);
        access = AccessEnum.getAccessByName(_el.getAttribute(ATTRIBUTE_ACCESS));
        templateDef = _el.getAttribute(ATTRIBUTE_TEMPLATE_DEF);
    }

    RootBlock(ContextEl _importingPage, int _indexChild,
            BracedBlock _m, int _idRowCol, int _categoryOffset ,String _name,
            String _packageName, OffsetAccessInfo _access, String _templateDef,
            NatTreeMap<Integer, String> _directSuperTypes, OffsetsBlock _offset) {
        super(_importingPage, _indexChild, _m, _offset);
        categoryOffset = _categoryOffset;
        allOverridingMethods = new ObjectMap<MethodId, EqList<ClassMethodId>>();
        name = _name.trim();
        packageName = ContextEl.removeDottedSpaces(_packageName);
        access = _access.getInfo();
        accessOffset = _access.getOffset();
        templateDef = _templateDef;
        realName = _name;
        realPackageName = _packageName;
        rowColDirectSuperTypes = _directSuperTypes;
        idRowCol = _idRowCol;
        for (EntryCust<Integer, String> t: _directSuperTypes.entryList()) {
            String type_ = ContextEl.removeDottedSpaces(t.getValue());
            directSuperTypes.add(type_);
            explicitDirectSuperTypes.put(t.getKey(), true);
        }
    }

    public abstract boolean isStaticType();
    public abstract StringList getImportedDirectSuperTypes();
    public NatTreeMap<Integer, Boolean> getExplicitDirectSuperTypes() {
        return explicitDirectSuperTypes;
    }

    public StringList getStaticInitInterfaces() {
        return staticInitInterfaces;
    }

    public StringList getStaticInitImportedInterfaces() {
        return staticInitImportedInterfaces;
    }

    public Numbers<Integer> getStaticInitInterfacesOffset() {
        return staticInitInterfacesOffset;
    }

    @Override
    public StringList getImports() {
        return imports;
    }

    @Override
    public Numbers<Integer> getImportsOffset() {
        return importsOffset;
    }

    public int getCategoryOffset() {
        return categoryOffset;
    }

    public int getAccessOffset() {
        return accessOffset;
    }

    public NatTreeMap<Integer, String> getRowColDirectSuperTypes() {
        return rowColDirectSuperTypes;
    }

    public int getIdRowCol() {
        return idRowCol;
    }

    public StringList getDirectSuperTypes() {
        return directSuperTypes;
    }

    public final StringList getCustomDirectSuperClasses(ContextEl _context) {
        StringList direct_ = getDirectSuperClasses(_context);
        direct_.removeAllObj(_context.getStandards().getAliasObject());
        return direct_;
    }

    public final RootBlock getParentType() {
        BracedBlock p_ = getParent();
        while (!(p_ instanceof RootBlock)) {
            if (p_ == null) {
                return null;
            }
            p_ = p_.getParent();
        }
        return (RootBlock) p_;
    }

    public void buildMapParamType(ContextEl _analyze) {
        paramTypesMap = new StringMap<TypeVar>();
        RootBlock r_ = this;
        while (r_ != null) {
            RowCol rc_ = getRowCol(0, r_.idRowCol);
            for (TypeVar t: r_.paramTypes) {
                StringList const_ = new StringList();
                for (String c: t.getConstraints()) {
                    const_.add(_analyze.resolveTypeMapping(c,r_, rc_));
                }
                TypeVar t_ = new TypeVar();
                t_.setConstraints(const_);
                t_.setName(t.getName());
                r_.paramTypesMap.put(t.getName(), t_);
            }
            r_ = r_.getParentType();
        }
    }

    public int getIndex(String _varType) {
        int len_ = paramTypes.size();
        for (int i = CustList.FIRST_INDEX; i < len_; i++) {
            if (StringList.quickEq(paramTypes.get(i).getName(), _varType)) {
                return i;
            }
        }
        return CustList.INDEX_NOT_FOUND_ELT;
    }
    @Override
    public CustList<TypeVar> getParamTypesMapValues() {
        return paramTypesMap.values();
    }
    @Override
    public StringMap<TypeVar> getParamTypesMap() {
        return paramTypesMap;
    }

    @Override
    public CustList<TypeVar> getParamTypes() {
        return paramTypes;
    }

    @Override
    public String getGenericString() {
        String base_ = getFullName();
        if (paramTypesMap.isEmpty()) {
            return base_;
        }
        StringBuilder generic_ = new StringBuilder(base_);
        StringList vars_ = new StringList();
        for (TypeVar t:paramTypesMap.values()) {
            vars_.add(StringList.concat(Templates.PREFIX_VAR_TYPE,t.getName()));
        }
        generic_.append(Templates.TEMPLATE_BEGIN);
        generic_.append(vars_.join(Templates.TEMPLATE_SEP));
        generic_.append(Templates.TEMPLATE_END);
        return generic_.toString();
    }

    @Override
    public String getFullDefinition() {
        return StringList.concat(getFullName(),getTemplateDef());
    }

    public String getTemplateDef() {
        return templateDef;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getPackageName() {
        Block par_ = this;
        while (par_.getParent() instanceof RootBlock) {
            par_ = par_.getParent();
        }
        if (!(par_ instanceof RootBlock)) {
            return packageName;
        }
        return ((RootBlock)par_).packageName;
    }

    @Override
    public AccessEnum getAccess() {
        return access;
    }

    @Override
    public ObjectMap<MethodId, EqList<ClassMethodId>> getAllOverridingMethods() {
        return allOverridingMethods;
    }

    @Override
    public final String getFullName() {
        String packageName_ = getPackageName();
        Block par_ = this;
        StringList names_ = new StringList(getName());
        while (par_.getParent() instanceof RootBlock) {
            par_ = par_.getParent();
            names_.add(((RootBlock)par_).getName());
        }
        if (packageName_.isEmpty()) {
            return getName();
        }
        return StringList.concat(packageName_,DOT,names_.getReverse().join(".."));
    }

    public final void validateIds(ContextEl _context) {
        EqList<MethodId> idMethods_ = new EqList<MethodId>();
        EqList<ConstructorId> idConstructors_ = new EqList<ConstructorId>();
        StringList idsField_ = new StringList();
        StringList idsAnnotMethods_ = new StringList();
        String className_ = getFullName();
        CustList<Block> bl_;
        bl_ = Classes.getDirectChildren(this);
        for (Block b: bl_) {
            if (b instanceof InfoBlock) {
                continue;
            }
            if (!(b instanceof FunctionBlock)) {
                //TODO intern classes
                RowCol where_ = b.getRowCol(0, b.getOffset().getOffsetTrim());
                String tagName_ = b.getTagName();
                UnexpectedTagName unexp_ = new UnexpectedTagName();
                unexp_.setFileName(getFullName());
                unexp_.setFoundTag(tagName_);
                unexp_.setRc(where_);
                _context.getClasses().addError(unexp_);
            }
        }
        for (Block b: bl_) {
            if (b instanceof Returnable) {
                Returnable method_ = (Returnable) b;
                String name_ = method_.getName();
                if (method_ instanceof MethodBlock) {
                    MethodBlock m_ = (MethodBlock) method_;
                    m_.buildImportedTypes(_context);
                    if (!StringList.isWord(name_) || m_.isConcreteInstanceDerivableMethod() != m_.isNormalMethod()) {
                        RowCol r_ = m_.getRowCol(0, m_.getNameOffset());
                        BadMethodName badMeth_ = new BadMethodName();
                        badMeth_.setFileName(className_);
                        badMeth_.setRc(r_);
                        badMeth_.setName(name_);
                        _context.getClasses().addError(badMeth_);
                    }
                }
                if (method_ instanceof AnnotationMethodBlock) {
                    AnnotationMethodBlock m_ = (AnnotationMethodBlock) method_;
                    m_.buildImportedTypes(_context);
                    if (!StringList.isWord(name_)) {
                        RowCol r_ = m_.getRowCol(0, m_.getNameOffset());
                        BadMethodName badMeth_ = new BadMethodName();
                        badMeth_.setFileName(className_);
                        badMeth_.setRc(r_);
                        badMeth_.setName(name_);
                        _context.getClasses().addError(badMeth_);
                    }
                }
                if (name_.isEmpty()) {
                    name_ = className_;
                }
                if (method_ instanceof MethodBlock) {
                    MethodId id_ = ((MethodBlock) method_).getId();
                    for (MethodId m: idMethods_) {
                        if (m.eq(id_)) {
                            RowCol r_ = method_.getRowCol(0, method_.getOffset().getOffsetTrim());
                            DuplicateMethod duplicate_;
                            duplicate_ = new DuplicateMethod();
                            duplicate_.setRc(r_);
                            duplicate_.setFileName(className_);
                            duplicate_.setId(id_);
                            _context.getClasses().addError(duplicate_);
                        }
                    }
                    idMethods_.add(id_);
                }
                if (method_ instanceof AnnotationMethodBlock) {
                    String id_ = ((AnnotationMethodBlock) method_).getName();
                    for (String m: idsAnnotMethods_) {
                        if (StringList.quickEq(m,id_)) {
                            RowCol r_ = method_.getRowCol(0, method_.getOffset().getOffsetTrim());
                            DuplicateMethod duplicate_;
                            duplicate_ = new DuplicateMethod();
                            duplicate_.setRc(r_);
                            duplicate_.setFileName(className_);
                            duplicate_.setId(new MethodId(false, id_, new StringList()));
                            _context.getClasses().addError(duplicate_);
                        }
                    }
                    idsAnnotMethods_.add(id_);
                }
                if (method_ instanceof ConstructorBlock) {
                    ((ConstructorBlock)method_).buildImportedTypes(_context);
                    ConstructorId idCt_ = ((ConstructorBlock)method_).getId();
                    for (ConstructorId m: idConstructors_) {
                        if (m.eq(idCt_)) {
                            RowCol r_ = method_.getRowCol(0, method_.getOffset().getOffsetTrim());
                            DuplicateConstructor duplicate_;
                            duplicate_ = new DuplicateConstructor();
                            duplicate_.setRc(r_);
                            duplicate_.setFileName(className_);
                            duplicate_.setId(idCt_);
                            _context.getClasses().addError(duplicate_);
                        }
                    }
                    idConstructors_.add(idCt_);
                }
                StringList l_ = method_.getParametersNames();
                StringList seen_ = new StringList();
                for (String v: l_) {
                    if (!StringList.isWord(v)) {
                        BadParamName b_;
                        b_ = new BadParamName();
                        b_.setFileName(className_);
                        b_.setRc(method_.getRowCol(0, method_.getOffset().getOffsetTrim()));
                        b_.setParamName(v);
                        _context.getClasses().addError(b_);
                    } else if (seen_.containsStr(v)){
                        DuplicateParamName b_;
                        b_ = new DuplicateParamName();
                        b_.setFileName(className_);
                        b_.setRc(method_.getRowCol(0, method_.getOffset().getOffsetTrim()));
                        b_.setParamName(v);
                        _context.getClasses().addError(b_);
                    } else {
                        seen_.add(v);
                    }
                }
            } else if (b instanceof InfoBlock) {
                InfoBlock method_ = (InfoBlock) b;
                method_.buildImportedType(_context);
                String name_ = method_.getFieldName();
                InfoBlock m_ = (InfoBlock) b;
                if (!StringList.isWord(name_)) {
                    RowCol r_ = m_.getRowCol(0, m_.getFieldNameOffset());
                    BadFieldName badMeth_ = new BadFieldName();
                    badMeth_.setFileName(className_);
                    badMeth_.setRc(r_);
                    badMeth_.setName(name_);
                    _context.getClasses().addError(badMeth_);
                }
                for (String m: idsField_) {
                    if (StringList.quickEq(m, name_)) {
                        RowCol r_ = method_.getRowCol(0, method_.getOffset().getOffsetTrim());
                        DuplicateField duplicate_;
                        duplicate_ = new DuplicateField();
                        duplicate_.setRc(r_);
                        duplicate_.setFileName(className_);
                        duplicate_.setId(name_);
                        _context.getClasses().addError(duplicate_);
                    }
                }
                idsField_.add(name_);
            }
        }
    }
    public abstract void setupBasicOverrides(ContextEl _context);

    final void useSuperTypesOverrides(ContextEl _context) {
        TypeUtil.buildOverrides(this, _context);
    }

    public abstract void buildDirectGenericSuperTypes(Analyzable _classes);

    public abstract StringList getAllGenericInterfaces(Analyzable _classes);

    public final StringList getAllGenericSuperTypes(Analyzable _classes) {
        StringList list_ = new StringList();
        Classes classes_ = _classes.getClasses();
        StringList current_ = new StringList(getGenericString());
        while (true) {
            StringList next_ = new StringList();
            for (String c: current_) {
                String baseType_ = Templates.getIdFromAllTypes(c);
                RootBlock curType_ = classes_.getClassBody(baseType_);
                StringList superTypes_ = curType_.getDirectGenericSuperTypes(_classes);
                for (String t: superTypes_) {
                    String format_ = Templates.format(c, t, _classes);
                    list_.add(format_);
                    next_.add(format_);
                }
            }
            if (next_.isEmpty()) {
                break;
            }
            current_ = next_;
        }
        return list_;
    }
    public final void checkCompatibilityBounds(ContextEl _context) {
        Classes classesRef_ = _context.getClasses();
        ObjectMap<MethodId, ClassMethodId> localSignatures_;
        localSignatures_ = new ObjectMap<MethodId, ClassMethodId>();
        StringMap<StringList> vars_ = new StringMap<StringList>();
        for (TypeVar t: getParamTypesMapValues()) {
            vars_.put(t.getName(), t.getConstraints());
        }
        LgNames stds_ = _context.getStandards();
        String objectClassName_ = stds_.getAliasObject();
        for (TypeVar t: getParamTypesMapValues()) {
            ObjectMap<MethodId, EqList<ClassMethodId>> signatures_;
            signatures_ = new ObjectMap<MethodId, EqList<ClassMethodId>>();
            StringList upper_ = Mapping.getAllUpperBounds(vars_, t.getName(),objectClassName_);
            for (String c: upper_) {
                String base_ = Templates.getIdFromAllTypes(c);
                if (classesRef_.isCustomType(base_)) {
                    RootBlock r_ = classesRef_.getClassBody(base_);
                    for (EntryCust<MethodId, EqList<ClassMethodId>> e: r_.getAllOverridingMethods().entryList()) {
                        for (ClassMethodId s: e.getValue()) {
                            String c_ = s.getClassName();
                            MethodBlock m_ = Classes.getMethodBodiesById(_context, c_, s.getConstraints()).first();
                            if (!Classes.canAccess(getFullName(), m_, _context)) {
                                continue;
                            }
                            if (m_.isStaticMethod()) {
                                continue;
                            }
                            MethodId id_ = m_.getFormattedId(c, _context);
                            String formattedType_ = Templates.format(c, c_, _context);
                            addClass(signatures_, id_, new ClassMethodId(formattedType_, m_.getId()));
                        }
                    }
                } else {
                    StandardType clBound_ = stds_.getStandards().getVal(base_);
                    for (StandardMethod m: clBound_.getMethods().values()) {
                        if (m.isStaticMethod()) {
                            continue;
                        }
                        MethodId id_ = m.getId();
                        MethodId realId_ = m.getId();
                        addClass(signatures_, id_, new ClassMethodId(c, realId_));
                    }
                }
            }
            for (EntryCust<MethodId, EqList<ClassMethodId>> e: signatures_.entryList()) {
                StringMap<MethodId> defs_ = new StringMap<MethodId>();
                StringList list_ = new StringList();
                for (ClassMethodId v: e.getValue()) {
                    defs_.put(v.getClassName(), v.getConstraints());
                    list_.add(v.getClassName());
                }
                StringMap<StringList> map_ = Classes.getBaseParams(list_);
                for (EntryCust<String,StringList> m:map_.entryList()) {
                    if (m.getValue().size() > 1) {
                        for (String s: m.getValue()) {
                            MethodId id_ = defs_.getVal(s);
                            _context.getClasses();
                            MethodBlock mDer_ = Classes.getMethodBodiesById(_context, s, id_).first();
                            IncompatibilityReturnType err_ = new IncompatibilityReturnType();
                            err_.setFileName(getFullName());
                            err_.setRc(getRowCol(0, idRowCol));
                            err_.setReturnType(mDer_.getImportedReturnType());
                            err_.setMethod(mDer_.getId());
                            err_.setParentClass(s);
                            _context.getClasses().addError(err_);
                        }
                    }
                }
            }
            ObjectMap<MethodId, EqList<ClassMethodId>> ov_;
            ov_ = RootBlock.getAllOverridingMethods(signatures_, _context);
            ObjectMap<MethodId, EqList<ClassMethodId>> er_;
            er_ = RootBlock.areCompatible(localSignatures_, vars_, ov_, _context);
            for (EntryCust<MethodId, EqList<ClassMethodId>> e: er_.entryList()) {
                for (ClassMethodId s: e.getValue()) {
                    String s_ = s.getClassName();
                    _context.getClasses();
                    MethodBlock mDer_ = Classes.getMethodBodiesById(_context, s_, s.getConstraints()).first();
                    IncompatibilityReturnType err_ = new IncompatibilityReturnType();
                    err_.setFileName(getFullName());
                    err_.setRc(getRowCol(0, idRowCol));
                    err_.setReturnType(mDer_.getImportedReturnType());
                    err_.setMethod(mDer_.getId());
                    err_.setParentClass(s_);
                    _context.getClasses().addError(err_);
                }
            }
            er_ = RootBlock.areModifierCompatible(ov_, vars_, _context);
            for (EntryCust<MethodId, EqList<ClassMethodId>> e: er_.entryList()) {
                for (ClassMethodId s: e.getValue()) {
                    String s_ = s.getClassName();
                    _context.getClasses();
                    MethodBlock mDer_ = Classes.getMethodBodiesById(_context, s_, s.getConstraints()).first();
                    IncompatibilityReturnType err_ = new IncompatibilityReturnType();
                    err_.setFileName(getFullName());
                    err_.setRc(getRowCol(0, idRowCol));
                    err_.setReturnType(mDer_.getImportedReturnType());
                    err_.setMethod(mDer_.getId());
                    err_.setParentClass(s_);
                    _context.getClasses().addError(err_);
                }
            }
        }
    }

    public final void checkCompatibility(ContextEl _context) {
        ObjectMap<MethodId, ClassMethodId> localSignatures_ = TypeUtil.getLocalSignatures(this,_context);
        StringMap<StringList> vars_ = new StringMap<StringList>();
        for (TypeVar t: getParamTypesMapValues()) {
            vars_.put(t.getName(), t.getConstraints());
        }
        for (EntryCust<MethodId, EqList<ClassMethodId>> e: getAllOverridingMethods().entryList()) {
            StringMap<MethodId> defs_ = new StringMap<MethodId>();
            StringList list_ = new StringList();
            for (ClassMethodId v: e.getValue()) {
                defs_.put(v.getClassName(), v.getConstraints());
                list_.add(v.getClassName());
            }
            StringMap<StringList> map_ = Classes.getBaseParams(list_);
            for (EntryCust<String,StringList> m:map_.entryList()) {
                if (m.getValue().size() > 1) {
                    for (String s: m.getValue()) {
                        MethodId id_ = defs_.getVal(s);
                        _context.getClasses();
                        MethodBlock mDer_ = Classes.getMethodBodiesById(_context, s, id_).first();
                        IncompatibilityReturnType err_ = new IncompatibilityReturnType();
                        err_.setFileName(getFullName());
                        err_.setRc(getRowCol(0, idRowCol));
                        err_.setReturnType(mDer_.getImportedReturnType());
                        err_.setMethod(mDer_.getId());
                        err_.setParentClass(s);
                        _context.getClasses().addError(err_);
                    }
                }
            }
        }
        ObjectMap<MethodId, EqList<ClassMethodId>> ov_;
        ov_ = getAllOverridingMethods();
        ov_ = RootBlock.getAllOverridingMethods(ov_, _context);
        ObjectMap<MethodId, EqList<ClassMethodId>> er_;
        er_ = RootBlock.areCompatible(localSignatures_, vars_, ov_, _context);
        for (EntryCust<MethodId, EqList<ClassMethodId>> e: er_.entryList()) {
            for (ClassMethodId s: e.getValue()) {
                String s_ = s.getClassName();
                _context.getClasses();
                MethodBlock mDer_ = Classes.getMethodBodiesById(_context, s_, s.getConstraints()).first();
                IncompatibilityReturnType err_ = new IncompatibilityReturnType();
                err_.setFileName(getFullName());
                err_.setRc(getRowCol(0, idRowCol));
                err_.setReturnType(mDer_.getImportedReturnType());
                err_.setMethod(mDer_.getId());
                err_.setParentClass(s_);
                _context.getClasses().addError(err_);
            }
        }
        er_ = RootBlock.areModifierCompatible(ov_, vars_, _context);
        for (EntryCust<MethodId, EqList<ClassMethodId>> e: er_.entryList()) {
            for (ClassMethodId s: e.getValue()) {
                String s_ = s.getClassName();
                _context.getClasses();
                MethodBlock mDer_ = Classes.getMethodBodiesById(_context, s_, s.getConstraints()).first();
                IncompatibilityReturnType err_ = new IncompatibilityReturnType();
                err_.setFileName(getFullName());
                err_.setRc(getRowCol(0, idRowCol));
                err_.setReturnType(mDer_.getImportedReturnType());
                err_.setMethod(mDer_.getId());
                err_.setParentClass(s_);
                _context.getClasses().addError(err_);
            }
        }
    }
    public final void checkImplements(ContextEl _context) {
        Classes classesRef_ = _context.getClasses();
        EqList<ClassFormattedMethodId> abstractMethods_ = new EqList<ClassFormattedMethodId>();
        boolean concreteClass_ = false;
        if (mustImplement()) {
            concreteClass_ = true;
        }
        StringMap<StringList> vars_ = new StringMap<StringList>();
        for (TypeVar t: getParamTypesMapValues()) {
            vars_.put(t.getName(), t.getConstraints());
        }
        for (Block b: Classes.getDirectChildren(this)) {
            if (!(b instanceof MethodBlock)) {
                continue;
            }
            MethodBlock mDer_ = (MethodBlock) b;
            MethodId idFor_ = mDer_.getId();
            if (mDer_.isAbstractMethod()) {
                if (mDer_.getFirstChild() != null) {
                    AbstractMethod err_;
                    err_ = new AbstractMethod();
                    err_.setFileName(getFullName());
                    err_.setRc(mDer_.getRowCol(0, mDer_.getNameOffset()));
                    err_.setSgn(idFor_.getSignature());
                    err_.setClassName(getFullName());
                    classesRef_.addError(err_);
                }
            }
        }
        if (concreteClass_) {
            for (GeneMethod b: Classes.getMethodBlocks(this)) {
                GeneMethod mDer_ = b;
                MethodId idFor_ = mDer_.getId();
                if (mDer_.isAbstractMethod()) {
                    AbstractMethod err_;
                    err_ = new AbstractMethod();
                    err_.setFileName(getFullName());
                    err_.setRc(((MethodBlock)mDer_).getRowCol(0, ((MethodBlock)mDer_).getNameOffset()));
                    err_.setSgn(idFor_.getSignature());
                    err_.setClassName(getFullName());
                    classesRef_.addError(err_);
                }
            }
            StringList allSuperClass_ = getAllGenericSuperTypes(_context);
            for (String s: allSuperClass_) {
                String base_ = Templates.getIdFromAllTypes(s);
                RootBlock superBl_ = classesRef_.getClassBody(base_);
                for (GeneMethod m: Classes.getMethodBlocks(superBl_)) {
                    if (m.isAbstractMethod()) {
                        abstractMethods_.add(new ClassFormattedMethodId(s, m.getId()));
                    }
                }
            }
            for (ClassFormattedMethodId m: abstractMethods_) {
                String baseClass_ = m.getClassName();
                baseClass_ = Templates.getIdFromAllTypes(baseClass_);
                RootBlock info_ = classesRef_.getClassBody(baseClass_);
                StringMap<ClassMethodId> map_ = TypeUtil.getConcreteMethodsToCall(info_, m.getConstraints(), _context);
                if (!map_.contains(getFullName())) {
                    AbstractMethod err_;
                    err_ = new AbstractMethod();
                    err_.setFileName(getFullName());
                    err_.setClassName(m.getClassName());
                    err_.setRc(getRowCol(0, idRowCol));
                    err_.setSgn(m.getConstraints().getSignature());
                    classesRef_.addError(err_);
                }
            }
        }
        ObjectMap<MethodId, EqList<ClassMethodId>> signatures_;
        signatures_ = new ObjectMap<MethodId, EqList<ClassMethodId>>();
        StringList allInterfaces_ = getAllGenericInterfaces(_context);
        for (String s: allInterfaces_) {
            String base_ = Templates.getIdFromAllTypes(s);
            InterfaceBlock superBl_ = (InterfaceBlock) classesRef_.getClassBody(base_);
            ObjectMap<MethodId, EqList<ClassMethodId>> signaturesInt_;
            signaturesInt_ = TypeUtil.getAllInstanceSignatures(superBl_, _context);
            for (EntryCust<MethodId, EqList<ClassMethodId>> m: signaturesInt_.entryList()) {
                MethodId key_ = m.getKey().format(s, _context);
                for (ClassMethodId c: m.getValue()) {
                    String c_ = c.getClassName();
                    String formatCl_ = Templates.format(s, c_, _context);
                    addClass(signatures_, key_, new ClassMethodId(formatCl_, c.getConstraints()));
                }
            }
        }
        ObjectMap<MethodId, EqList<ClassMethodId>> ov_;
        ov_ = RootBlock.getAllOverridingMethods(signatures_, _context);
        if (concreteClass_) {
            abstractMethods_ = RootBlock.remainingMethodsToImplement(ov_, getFullName(), _context);
            for (ClassFormattedMethodId m: abstractMethods_) {
                String baseClass_ = m.getClassName();
                baseClass_ = Templates.getIdFromAllTypes(baseClass_);
                RootBlock info_ = classesRef_.getClassBody(baseClass_);
                MethodId realId_ = m.getConstraints();
                StringMap<ClassMethodId> map_ = info_.getConcreteMethodsToCallFromClass(realId_, _context);
                if (!map_.contains(getFullName())) {
                    AbstractMethod err_;
                    err_ = new AbstractMethod();
                    err_.setFileName(getFullName());
                    err_.setClassName(m.getClassName());
                    err_.setRc(getRowCol(0, idRowCol));
                    err_.setSgn(m.getConstraints().getSignature());
                    classesRef_.addError(err_);
                }
            }
        }
    }
    public final StringList getDirectSubTypes(ContextEl _conf) {
        StringList subTypes_ = new StringList();
        Classes classes_ = _conf.getClasses();
        String baseClassFound_ = getFullName();
        for (RootBlock c: classes_.getClassBodies()) {
            String name_ = c.getFullName();
            RootBlock r_ = classes_.getClassBody(name_);
            if (r_ instanceof InterfaceBlock) {
                if (((InterfaceBlock) r_).getDirectSuperInterfaces().containsStr(baseClassFound_)) {
                    subTypes_.add(name_);
                }
            } else {
                if (r_.getDirectSuperClasses(_conf).containsStr(baseClassFound_)) {
                    subTypes_.add(name_);
                }
            }
        }
        return subTypes_;
    }
    final StringMap<ClassMethodId> getConcreteMethodsToCallFromClass(MethodId _realId, ContextEl _conf) {
        StringMap<ClassMethodId> eq_ = new StringMap<ClassMethodId>();
        Classes classes_ = _conf.getClasses();
        String baseClassFound_ = getFullName();
        for (RootBlock c: classes_.getClassBodies()) {
            String name_ = c.getFullName();
            if (!PrimitiveTypeUtil.canBeUseAsArgument(baseClassFound_, name_, _conf)) {
                continue;
            }
            if (!classes_.getClassBody(name_).mustImplement()) {
                continue;
            }
            UniqueRootedBlock subClassBlock_ = (UniqueRootedBlock) classes_.getClassBody(name_);
            StringList allBaseClasses_ = new StringList(name_);
            allBaseClasses_.addAllElts(subClassBlock_.getAllSuperClasses());
            for (String s: allBaseClasses_) {
                if (!PrimitiveTypeUtil.canBeUseAsArgument(baseClassFound_, s, _conf)) {
                    continue;
                }
                UniqueRootedBlock r_ = (UniqueRootedBlock) classes_.getClassBody(s);
                String gene_ = r_.getGenericString();
                String v_ = Templates.getFullTypeByBases(gene_, baseClassFound_, _conf);
                MethodId l_ = _realId.format(v_, _conf);
                ObjectMap<MethodId, EqList<ClassMethodId>> ov_ = r_.getAllOverridingMethods();
                if (!ov_.contains(l_)) {
                    continue;
                }
                boolean found_ = false;
                StringList foundSuperClasses_ = new StringList();
                StringList allSuperClasses_ = new StringList(gene_);
                for (String t: r_.getAllSuperClasses()) {
                    allSuperClasses_.add(Templates.getFullTypeByBases(gene_, t, _conf));
                }
                EqList<ClassMethodId> list_ = ov_.getVal(l_);
                //pkg.ExTwo & pkg.Int3
                for (ClassMethodId t: list_) {
                    String t_ = t.getClassName();
                    String baseSuperType_ = Templates.getIdFromAllTypes(t_);
                    if (StringList.quickEq(baseSuperType_, baseClassFound_)) {
                        found_ = true;
                    }
                    if (classes_.getClassBody(baseSuperType_) instanceof InterfaceBlock) {
                        continue;
                    }
                    foundSuperClasses_.add(t_);
                }
                if (!found_) {
                    continue;
                }
                String classNameFound_;
                MethodId realId_;
                foundSuperClasses_.sortElts(new ComparatorIndexes<String>(allSuperClasses_));
                if (foundSuperClasses_.isEmpty()) {
                    continue;
                }
                classNameFound_ = foundSuperClasses_.first();
                int i_ = 0;
                while (true) {
                    ClassMethodId methId_ = list_.get(i_);
                    if (StringList.quickEq(methId_.getClassName(), classNameFound_)) {
                        realId_ = methId_.getConstraints();
                        break;
                    }
                    i_++;
                }
                classNameFound_ = Templates.getIdFromAllTypes(classNameFound_);
                if (Classes.getMethodBodiesById(_conf, classNameFound_, realId_).first().isAbstractMethod()) {
                    continue;
                }
                eq_.put(name_, new ClassMethodId(classNameFound_, realId_));
                break;
            }
        }
        return eq_;
    }
    public static ObjectMap<MethodId, EqList<ClassMethodId>> getAllOverridingMethods(
            ObjectMap<MethodId, EqList<ClassMethodId>> _methodIds,
            ContextEl _conf) {
        ObjectMap<MethodId, EqList<ClassMethodId>> map_;
        map_ = new ObjectMap<MethodId, EqList<ClassMethodId>>();
        for (EntryCust<MethodId, EqList<ClassMethodId>> e: _methodIds.entryList()) {
            StringMap<MethodId> defs_ = new StringMap<MethodId>();
            StringList list_ = new StringList();
            for (ClassMethodId v: e.getValue()) {
                defs_.put(v.getClassName(), v.getConstraints());
                list_.add(v.getClassName());
            }
            list_ = PrimitiveTypeUtil.getSubclasses(list_, _conf);
            EqList<ClassMethodId> out_ = new EqList<ClassMethodId>();
            for (String v: list_) {
                out_.add(new ClassMethodId(v, defs_.getVal(v)));
            }
            map_.put(e.getKey(), out_);
        }
        return map_;
    }

    public static ObjectMap<MethodId, EqList<ClassMethodId>> areCompatible(
            ObjectMap<MethodId, ClassMethodId> _localMethodIds,
            StringMap<StringList> _vars,
            ObjectMap<MethodId, EqList<ClassMethodId>> _methodIds, ContextEl _context) {
        Classes classesRef_ = _context.getClasses();
        ObjectMap<MethodId, EqList<ClassMethodId>> output_;
        output_ = new ObjectMap<MethodId, EqList<ClassMethodId>>();
        LgNames stds_ = _context.getStandards();
        for (EntryCust<MethodId, EqList<ClassMethodId>> e: _methodIds.entryList()) {
            MethodId cst_ = e.getKey();
            EqList<ClassMethodId> classes_ = e.getValue();
            Mapping mapping_ = new Mapping();
            mapping_.getMapping().putAllMap(_vars);
            if (_localMethodIds.contains(e.getKey())) {
                //overridden by this interface
                ClassMethodId subInt_ = _localMethodIds.getVal(e.getKey());
                String name_ = subInt_.getClassName();
                MethodBlock sub_ = Classes.getMethodBodiesById(_context, name_, subInt_.getConstraints()).first();
                if (sub_.isStaticMethod()) {
                    StringMap<StringList> vars_ = new StringMap<StringList>();
                    StringList retClasses_ = new StringList();
                    for (ClassMethodId s: e.getValue()) {
                        String supName_ = s.getClassName();
                        MethodBlock sup_ = Classes.getMethodBodiesById(_context, supName_, s.getConstraints()).first();
                        if (sup_.isStaticMethod()) {
                            continue;
                        }
                        retClasses_.add(sup_.getImportedReturnType());
                    }
                    if (!retClasses_.isEmpty() && PrimitiveTypeUtil.getSubslass(retClasses_, vars_, _context).isEmpty()) {
                        for (ClassMethodId c: classes_) {
                            addClass(output_, e.getKey(), c);
                        }
                    }
                    continue;
                }
                String subType_ = sub_.getImportedReturnType();
                subType_ = Templates.format(name_, subType_, _context);
                mapping_.setArg(subType_);
                for (ClassMethodId s: e.getValue()) {
                    String supName_ = s.getClassName();
                    MethodBlock sup_ = Classes.getMethodBodiesById(_context, supName_, s.getConstraints()).first();
                    if (sup_.isStaticMethod()) {
                        continue;
                    }
                    String formattedSup_ = Templates.format(supName_, sup_.getImportedReturnType(), _context);
                    mapping_.setParam(formattedSup_);
                    if (StringList.quickEq(formattedSup_, subType_)) {
                        continue;
                    }
                    if (!Templates.isCorrect(mapping_, _context)) {
                        addClass(output_, e.getKey(), subInt_);
                        addClass(output_, e.getKey(), s);
                    }
                }
                continue;
            }
            StringList retClasses_ = new StringList();
            for (ClassMethodId s: e.getValue()) {
                String name_ = s.getClassName();
                String base_ = Templates.getIdFromAllTypes(name_);
                if (!classesRef_.isCustomType(base_)) {
                    StandardType clBound_ = stds_.getStandards().getVal(base_);
                    for (StandardMethod m: clBound_.getMethods().values()) {
                        if (m.isStaticMethod()) {
                            continue;
                        }
                        MethodId id_ = m.getId();
                        if (!id_.eq(cst_)) {
                            continue;
                        }
                        retClasses_.add(m.getReturnType());
                    }
                    continue;
                }
                MethodBlock sup_ = Classes.getMethodBodiesById(_context, name_, s.getConstraints()).first();
                if (sup_.isStaticMethod()) {
                    continue;
                }
                String ret_ = sup_.getImportedReturnType();
                retClasses_.add(Templates.format(name_, ret_, _context));
            }
            if (!retClasses_.isEmpty()) {
                if (PrimitiveTypeUtil.getSubslass(retClasses_, _vars, _context).isEmpty()) {
                    for (ClassMethodId c: classes_) {
                        addClass(output_, e.getKey(), c);
                    }
                } else {
                    StringMap<StringList> map_ = Classes.getBaseParams(retClasses_);
                    for (EntryCust<String,StringList> m:map_.entryList()) {
                        if (m.getValue().size() > 1) {
                            for (ClassMethodId c: classes_) {
                                addClass(output_, e.getKey(), c);
                            }
                            break;
                        }
                    }
                }
            }
        }
        return output_;
    }

    public static ObjectMap<MethodId, EqList<ClassMethodId>> areModifierCompatible(
            ObjectMap<MethodId, EqList<ClassMethodId>> _methodIds, StringMap<StringList> _vars, ContextEl _context) {
        ObjectMap<MethodId, EqList<ClassMethodId>> output_;
        output_ = new ObjectMap<MethodId, EqList<ClassMethodId>>();
        Classes classes_ = _context.getClasses();
        for (EntryCust<MethodId, EqList<ClassMethodId>> e: _methodIds.entryList()) {
            MethodId cst_ = e.getKey();
            EqList<ClassMethodId> fClasses_ = new EqList<ClassMethodId>();
            EqList<ClassMethodId> aClasses_ = new EqList<ClassMethodId>();
            for (ClassMethodId s: e.getValue()) {
                String name_ = s.getClassName();
                String base_ = Templates.getIdFromAllTypes(name_);
                if (classes_.getClassBody(base_) == null) {
                    continue;
                }
                MethodBlock sup_ = Classes.getMethodBodiesById(_context, name_, s.getConstraints()).first();
                if (sup_.isStaticMethod()) {
                    continue;
                }
                if (sup_.isAbstractMethod()) {
                    aClasses_.add(s);
                }
                if (sup_.isFinalMethod()) {
                    fClasses_.add(s);
                }
            }
            if (fClasses_.size() > 1) {
                for (ClassMethodId c: fClasses_) {
                    addClass(output_, cst_, c);
                }
                continue;
            }
            if (fClasses_.size() > 0 && aClasses_.size() > 0) {
                if (fClasses_.size() == 1) {
                    String name_ = fClasses_.first().getClassName();
                    String base_ = Templates.getIdFromAllTypes(name_);
                    if (classes_.getClassBody(base_) instanceof ClassBlock) {
                        continue;
                    }
                }
                for (ClassMethodId c: fClasses_) {
                    addClass(output_, cst_, c);
                }
                for (ClassMethodId c: aClasses_) {
                    addClass(output_, cst_, c);
                }
                continue;
            }
            if (fClasses_.size() == 1) {
                Mapping map_ = new Mapping();
                ClassMethodId subInt_ = fClasses_.first();
                String subIntName_ = subInt_.getClassName();
                map_.getMapping().putAllMap(_vars);
                MethodBlock sub_ = Classes.getMethodBodiesById(_context, subIntName_, subInt_.getConstraints()).first();
                String subType_ = sub_.getImportedReturnType();
                subType_ = Templates.format(subIntName_, subType_, _context);
                map_.setParam(subType_);
                for (ClassMethodId s: e.getValue()) {
                    String s_ = s.getClassName();
                    MethodBlock sup_ = Classes.getMethodBodiesById(_context, s_, s.getConstraints()).first();
                    if (sup_.isStaticMethod()) {
                        continue;
                    }
                    String supType_ = sup_.getImportedReturnType();
                    String formattedSupType_ = Templates.format(s_, supType_, _context);
                    map_.setArg(formattedSupType_);
                    if (StringList.quickEq(formattedSupType_, subType_)) {
                        continue;
                    }
                    if (!Templates.isCorrect(map_, _context)) {
                        addClass(output_, cst_, subInt_);
                        addClass(output_, cst_, s);
                    }
                }
            }
        }
        return output_;
    }
    public static EqList<ClassFormattedMethodId> remainingMethodsToImplement(
            ObjectMap<MethodId, EqList<ClassMethodId>> _methodIds,
            String _fullName,
            ContextEl _context) {
        EqList<ClassFormattedMethodId> rem_ = new EqList<ClassFormattedMethodId>();
        for (EntryCust<MethodId, EqList<ClassMethodId>> e: _methodIds.entryList()) {
            int nbConcrete_ = 0;
            int nbFinal_ = 0;
            for (ClassMethodId f: e.getValue()) {
                String f_ = f.getClassName();
                MethodBlock method_ = Classes.getMethodBodiesById(_context, f_, f.getConstraints()).first();
                if (method_.isStaticMethod()) {
                    continue;
                }
                if (method_.isFinalMethod() && Classes.canAccess(_fullName, method_, _context)) {
                    nbFinal_++;
                }
                if (method_.isConcreteMethod()) {
                    if (Classes.canAccess(_fullName, method_, _context)) {
                        nbConcrete_++;
                    }
                }
            }
            if (nbConcrete_ > 1 && nbFinal_ == 0) {
                for (ClassMethodId f: e.getValue()) {
                    String f_ = f.getClassName();
                    ClassFormattedMethodId id_ = new ClassFormattedMethodId(f_, f.getConstraints());
                    rem_.add(id_);
                }
            }
        }
        return rem_;
    }
    
    protected static void addClass(ObjectMap<MethodId, EqList<ClassMethodId>> _map, MethodId _key, ClassMethodId _class) {
        if (_map.contains(_key)) {
            _map.getVal(_key).add(_class);
            _map.getVal(_key).removeDuplicates();
        } else {
            _map.put(_key, new EqList<ClassMethodId>(_class));
        }
    }

    public void validateConstructors(ContextEl _cont) {
        boolean opt_ = optionalCallConstr(_cont);
        CustList<ConstructorBlock> ctors_ = new CustList<ConstructorBlock>();
        for (Block b: Classes.getDirectChildren(this)) {
            if (b instanceof ConstructorBlock) {
                ctors_.add((ConstructorBlock) b);
            }
        }
        String idType_ = getFullName();
        for (ConstructorBlock c: ctors_) {
            c.setupInstancingStep(_cont);
        }
        for (ConstructorBlock c: ctors_) {
            if (c.implicitConstr() && !opt_) {
                UndefinedSuperConstructor un_ = new UndefinedSuperConstructor();
                un_.setClassName(((UniqueRootedBlock)this).getImportedDirectGenericSuperClass());
                un_.setFileName(getFile().getFileName());
                un_.setRc(c.getRowCol(0, c.getOffset().getOffsetTrim()));
                _cont.getClasses().addError(un_);
            }
        }
        EqList<ConstructorId> l_ = new EqList<ConstructorId>();
        for (ConstructorBlock c: ctors_) {
            if (c.getConstIdSameClass() != null) {
                l_.add(c.getId());
            }
        }
        Graph<ConstructorEdge> graph_;
        graph_ = new Graph<ConstructorEdge>();
        for (ConstructorId f: l_) {
            ConstructorBlock b_ = (ConstructorBlock) Classes.getConstructorBodiesById(_cont, idType_, f).first();
            ConstructorId co_ = b_.getConstIdSameClass();
            ConstructorEdge f_ = new ConstructorEdge(f);
            ConstructorEdge t_ = new ConstructorEdge(co_);
            graph_.addSegment(f_, t_);
        }
        EqList<ConstructorEdge> cycle_ = graph_.elementsCycle();
        if (!cycle_.isEmpty()) {
            CyclicInheritingGraph cyclic_ = new CyclicInheritingGraph();
            cyclic_.setClassName(cycle_);
            cyclic_.setFileName(getFile().getFileName());
            cyclic_.setRc(getRowCol(0, getOffset().getOffsetTrim()));
            _cont.getClasses().addError(cyclic_);
        }
    }

    @Override
    public boolean isAccessibleType(String _type, Analyzable _cont) {
        RootBlock r_ = _cont.getClasses().getClassBody(_type);
        if (r_.getAccess().ordinal() <= AccessEnum.PROTECTED.ordinal()) {
            return true;
        }
        if (r_.getAccess().ordinal() == AccessEnum.PACKAGE.ordinal()) {
            if (StringList.quickEq(r_.getPackageName(), getPackageName())) {
                return true;
            }
        }
        return false;
    }
    private boolean optionalCallConstr(ContextEl _cont) {
        if (!(this instanceof UniqueRootedBlock)) {
            return true;
        }
        String superClass_ = ((UniqueRootedBlock)this).getSuperClass(_cont);
        RootBlock clMeta_ = _cont.getClasses().getClassBody(superClass_);
        if (clMeta_ == null) {
            return true;
        }
        CustList<ConstructorBlock> ctors_ = new CustList<ConstructorBlock>();
        for (Block b: Classes.getDirectChildren(clMeta_)) {
            if (b instanceof ConstructorBlock) {
                ctors_.add((ConstructorBlock) b);
            }
        }
        if (ctors_.isEmpty()) {
            return true;
        }
        for (ConstructorBlock c: ctors_) {
            if (!Classes.canAccess(getFullName(), c, _cont)) {
                continue;
            }
            if (c.getId().getParametersTypes().isEmpty()) {
                return true;
            }
        }
        return false;
    }
    @Override
    public ExpressionLanguage getEl(ContextEl _context, boolean _native,
            int _indexProcess) {
        return null;
    }

    public String getRealName() {
        return realName;
    }

    public String getRealPackageName() {
        return realPackageName;
    }

    @Override
    public void reach(Analyzable _an, AnalyzingEl _anEl) {
    }
    @Override
    public void abrupt(Analyzable _an, AnalyzingEl _anEl) {
    }
    @Override
    public boolean canAccessClass(String _class, Analyzable _analyzable) {
        return Classes.canAccessClass(getFullName(), _class, _analyzable);
    }
}
