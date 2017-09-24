package code.expressionlanguage.methods;
import java.util.Iterator;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.CustEnum;
import code.expressionlanguage.ElUtil;
import code.expressionlanguage.PageEl;
import code.expressionlanguage.PrimitiveTypeUtil;
import code.expressionlanguage.Templates;
import code.expressionlanguage.methods.exceptions.AlreadyExistingClassException;
import code.expressionlanguage.methods.exceptions.AnalyzingErrorsException;
import code.expressionlanguage.methods.exceptions.BadClassNameException;
import code.expressionlanguage.methods.exceptions.BadFileNameException;
import code.expressionlanguage.methods.exceptions.UnknownBlockException;
import code.expressionlanguage.methods.util.BadAccessClass;
import code.expressionlanguage.methods.util.BadClassName;
import code.expressionlanguage.methods.util.BadFileName;
import code.expressionlanguage.methods.util.BadInheritedClass;
import code.expressionlanguage.methods.util.BadVariableName;
import code.expressionlanguage.methods.util.ClassEdge;
import code.expressionlanguage.methods.util.DeadCodeMethod;
import code.expressionlanguage.methods.util.EqualsEl;
import code.expressionlanguage.methods.util.FoundErrorInterpret;
import code.expressionlanguage.methods.util.MissingReturnMethod;
import code.expressionlanguage.methods.util.TypeVar;
import code.expressionlanguage.methods.util.UnexpectedTagName;
import code.expressionlanguage.methods.util.UnknownClassName;
import code.expressionlanguage.opers.Calculation;
import code.expressionlanguage.opers.ExpressionLanguage;
import code.expressionlanguage.opers.OperationNode;
import code.expressionlanguage.opers.util.ClassCategory;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.ClassMetaInfo;
import code.expressionlanguage.opers.util.ClassName;
import code.expressionlanguage.opers.util.ConstructorMetaInfo;
import code.expressionlanguage.opers.util.FctConstraints;
import code.expressionlanguage.opers.util.FieldMetaInfo;
import code.expressionlanguage.opers.util.MethodId;
import code.expressionlanguage.opers.util.MethodMetaInfo;
import code.expressionlanguage.opers.util.Struct;
import code.expressionlanguage.variables.LocalVariable;
import code.util.CustList;
import code.util.EntryCust;
import code.util.EqList;
import code.util.ObjectMap;
import code.util.ObjectNotNullMap;
import code.util.StringList;
import code.util.StringMap;
import code.util.consts.ConstClasses;
import code.util.exceptions.RuntimeClassNotFoundException;
import code.util.graphs.Graph;
import code.xml.ElementOffsetsNext;
import code.xml.RowCol;
import code.xml.XmlParser;
import code.xml.exceptions.XmlParseException;

public final class Classes {

    public static final String EXT = "cdm";
    public static final String TEMP_PREFIX = "tmp";
    private static final String ATTRIBUTE_PACKAGE = "package";
    private static final String ATTRIBUTE_NAME = "name";
    private static final String ATTRIBUTE_LEFT = "left";
    private static final String ATTRIBUTE_OPER = "oper";
    private static final String ATTRIBUTE_RIGHT = "right";
    private static final String ATTRIBUTE_VAR = "var";
    private static final String ATTRIBUTE_CLASS = "class";
    private static final String ATTRIBUTE_MODIFIER = "modifier";
    private static final String ATTRIBUTE_ACCESS = "access";
    private static final String ATTRIBUTE_SUPER_CLASS = "superclass";
    private static final String ATTRIBUTE_CLASS_INDEX = "classindex";
    private static final String ATTRIBUTE_CONDITION = "condition";
    private static final String ATTRIBUTE_VALUE = "value";
    private static final String ATTRIBUTE_EXPRESSION = "expression";
    private static final String ATTRIBUTE_EQ = "eq";
    private static final String ATTRIBUTE_INIT = "init";
    private static final String ATTRIBUTE_STEP = "step";

    private static final String EXT_PRO = "pro";
    private static final String NAT_EQ_FORMAT = "{0};.={1};.";
    private static final char SEP_FILE = '/';
    private static final char DOT = '.';
    private static final char LT = '<';
    private static final char GT = '>';
    private static final char ARR_BEG = '[';
    private static final char PREF = '#';
    private static final char PRIM = '$';
    private static final char COMMA = ',';
    private static final String LOC_VAR = ";.";
    private static final String ITERATOR = "iterator()";
    private static final String HAS_NEXT = "hasNext()";
    private static final String NEXT = "next()";
    private static final String EMPTY_STRING = "";

    private final StringMap<RootBlock> classesBodies;

    private final ObjectMap<ClassField,Struct> staticFields;
    private final StringMap<CustList<Struct>> values;
    private final StringMap<Boolean> initializedClasses;
    private final StringMap<Boolean> successfulInitializedClasses;

    private final CustList<FoundErrorInterpret> errorsDet;
    private final StringList localVariablesNames;
    private final StringList classesInheriting;
    private EqualsEl natEqEl;
    private String iteratorVar;
    private String hasNextVar;
    private String nextVar;
    private CustList<OperationNode> exps;
    private CustList<OperationNode> expsIterator;
    private CustList<OperationNode> expsHasNext;
    private CustList<OperationNode> expsNext;

    public Classes(StringMap<String> _files, ContextEl _context) {
        classesBodies = new StringMap<RootBlock>();

        errorsDet = new CustList<FoundErrorInterpret>();
        StringList classes_ = new StringList();

        staticFields = new ObjectMap<ClassField,Struct>();
        values = new StringMap<CustList<Struct>>();
        initializedClasses = new StringMap<Boolean>();
        successfulInitializedClasses = new StringMap<Boolean>();
        classesInheriting = new StringList();
        localVariablesNames = new StringList();
        for (EntryCust<String,String> f: _files.entryList()) {
            String file_ = f.getKey();
            try {
                if (file_.isEmpty()) {
                    throw new BadFileNameException(file_);
                }
                for (char c: file_.toCharArray()) {
                    if (StringList.isWordChar(c)) {
                        continue;
                    }
                    if (c == DOT) {
                        continue;
                    }
                    if (c == SEP_FILE) {
                        continue;
                    }
                    throw new BadFileNameException(file_);
                }
                if (StringList.indexesOfChar(file_, DOT).size() != 1) {
                    throw new BadFileNameException(file_);
                }
                if (file_.lastIndexOf(SEP_FILE) > file_.indexOf(DOT)) {
                    throw new BadFileNameException(file_);
                }
                if (!file_.endsWith(DOT+EXT)) {
                    throw new BadFileNameException(file_);
                }
                for (String s: StringList.splitChars(file_, SEP_FILE)) {
                    if (StringList.isWord(s)) {
                        continue;
                    }
                    if (s.isEmpty()) {
                        throw new BadFileNameException(file_);
                    }
                    for (String e: StringList.splitChars(s, DOT)) {
                        if (StringList.isWord(e)) {
                            continue;
                        }
                        throw new BadFileNameException(file_);
                    }
                }
                String content_ = f.getValue();
                file_ = file_.substring(CustList.FIRST_INDEX, file_.indexOf(DOT));
                file_ = file_.replace(SEP_FILE, DOT);
                for (String e: classes_) {
                    if (e.equalsIgnoreCase(file_)) {
                        throw new AlreadyExistingClassException(file_);
                    }
                }
                classes_.add(file_);
                Document doc_ = XmlParser.parseSaxHtmlRowCol(content_);
                _context.setHtml(content_);
                _context.setElements(new ElementOffsetsNext(new RowCol(), 0, 0));
                Element root_ = doc_.getDocumentElement();
                Block bl_ = Block.createOperationNode(root_, _context, 0, null);
                if (!(bl_ instanceof RootBlock)) {
                    throw new XmlParseException();
                }
                int tabWidth_ = _context.getTabWidth();
                ElementOffsetsNext e_ = _context.getElements();
                ElementOffsetsNext ne_ = XmlParser.getIndexesOfElementOrAttribute(content_, e_, root_, tabWidth_);
                bl_.setAttributes(ne_.getAttributes());
                bl_.setEndHeader(ne_.getEndHeader());
                bl_.setTabs(ne_.getTabs());
                bl_.setOffsets(ne_.getOffsets());
                RootBlock cl_ = (RootBlock) bl_;
                String packageName_;
                packageName_ = cl_.getPackageName();
                if (packageName_.isEmpty()) {
                    throw new BadClassNameException(cl_.getFullName());
                }
                StringList elements_ = StringList.splitChars(packageName_, DOT);
                for (String e: elements_) {
                    if (!StringList.isWord(e)) {
                        throw new BadClassNameException(cl_.getFullName());
                    }
                }
                String className_;
                className_ = cl_.getName();
                if (!StringList.isWord(className_)) {
                    throw new BadClassNameException(cl_.getFullName());
                }
                if (!cl_.getFullName().equalsIgnoreCase(file_)) {
                    throw new BadClassNameException(cl_.getFullName());
                }
                String fullDef_ = cl_.getFullDefinition();
                if (StringList.getAllTypes(fullDef_) == null) {
                    throw new BadClassNameException(fullDef_);
                }
                StringList params_ = StringList.getAllTypes(fullDef_);
                StringList varTypes_ = new StringList();
                for (String p: params_.mid(CustList.SECOND_INDEX)) {
                    if (!p.startsWith(Templates.PREFIX_VAR_TYPE)) {
                        throw new BadClassNameException(fullDef_);
                    }
                    String name_ = p.substring(Templates.PREFIX_VAR_TYPE.length());
                    TypeVar type_ = new TypeVar();
                    int indexDef_ = name_.indexOf(Templates.EXTENDS_DEF);
                    StringList parts_ = StringList.splitInTwo(name_, indexDef_);
                    if (!StringList.isWord(parts_.first())) {
                        throw new BadClassNameException(fullDef_);
                    }
                    if (varTypes_.containsStr(parts_.first())) {
                        throw new BadClassNameException(fullDef_);
                    }
                    varTypes_.add(parts_.first());
                    StringList constraints_ = new StringList();
                    if (indexDef_ != CustList.INDEX_NOT_FOUND_ELT) {
                        for (String b: StringList.splitChars(parts_.last().substring(Templates.EXTENDS_DEF.length()), Templates.SEP_BOUNDS)) {
                            if (!Templates.isCorrectWrite(b)) {
                                throw new BadClassNameException(fullDef_);
                            }
                            for (char c: b.toCharArray()) {
                                if (StringList.isWordChar(c)) {
                                    continue;
                                }
                                if (c == DOT) {
                                    continue;
                                }
                                if (c == LT) {
                                    continue;
                                }
                                if (c == GT) {
                                    continue;
                                }
                                if (c == COMMA) {
                                    continue;
                                }
                                if (c == PREF) {
                                    continue;
                                }
                                if (c == PRIM) {
                                    continue;
                                }
                                if (c == ARR_BEG) {
                                    continue;
                                }
                                throw new BadClassNameException(fullDef_);
                            }
                            constraints_.add(b);
                        }
                    } else {
                        constraints_.add(Object.class.getName());
                    }
                    type_.setConstraints(constraints_);
                    type_.setName(parts_.first());
                    cl_.getParamTypes().add(type_);
                }
                cl_.buildMapParamType();
                try {
                    Class<?> clNat_ = ConstClasses.classForNameNotInit(file_);
                    throw new AlreadyExistingClassException(clNat_.getName());
                } catch (RuntimeClassNotFoundException _0) {
                }
                Block rootBl_ = (Block) cl_;
                CustList<Block> all_ = getSortedDescNodes(rootBl_);
                for (Block b: all_) {
                    b.setConf(null);
                    b.setupChars(content_);
                    b.setCompleteGroup();
                    b.setNullAssociateElement();
                }
                initializedClasses.put(file_, false);
                classesBodies.put(file_, cl_);
            } catch (UnknownBlockException _0) {
                RowCol where_ = _0.getRc();
                UnexpectedTagName t_ = new UnexpectedTagName();
                t_.setRc(where_);
                t_.setFileName(file_);
                t_.setFoundTag(_0.getMessage());
                errorsDet.add(t_);
            } catch (BadClassNameException _0) {
                BadClassName bad_ = new BadClassName();
                bad_.setClassName(_0.getMessage());
                bad_.setRc(new RowCol());
                bad_.setFileName(file_);
                errorsDet.add(bad_);
            } catch (BadFileNameException _0) {
                BadFileName bad_ = new BadFileName();
                bad_.setRc(new RowCol());
                bad_.setFileName(file_);
                errorsDet.add(bad_);
            } catch (XmlParseException _0) {
                //TODO change later class
                BadFileName bad_ = new BadFileName();
                bad_.setRc(_0.getRowCol());
                bad_.setFileName(file_);
                errorsDet.add(bad_);
            } catch (AlreadyExistingClassException _0) {
                //TODO change later class
                BadClassName bad_ = new BadClassName();
                bad_.setClassName(_0.getMessage());
                bad_.setRc(new RowCol());
                bad_.setFileName(file_);
                errorsDet.add(bad_);
            }
        }
        _context.setHtml(EMPTY_STRING);
    }
    public CustList<FoundErrorInterpret> getErrorsDet() {
        return errorsDet;
    }
    public static void validateAll(StringMap<String> _files, ContextEl _context) {
        Classes bk_ = _context.getClasses();
        Classes classes_ = new Classes(_files, _context);
        if (!classes_.errorsDet.isEmpty()) {
            throw new AnalyzingErrorsException(classes_.errorsDet);
        }
        try {
            _context.setClasses(classes_);
            classes_.validateInheritingClasses(_context);
            if (!classes_.errorsDet.isEmpty()) {
                throw new AnalyzingErrorsException(classes_.errorsDet);
            }
            classes_.validateIds(_context);
            if (!classes_.errorsDet.isEmpty()) {
                throw new AnalyzingErrorsException(classes_.errorsDet);
            }
            classes_.validateOverridingInherit(_context);
            if (!classes_.errorsDet.isEmpty()) {
                throw new AnalyzingErrorsException(classes_.errorsDet);
            }
            classes_.validateClassesAccess(_context);
            if (!classes_.errorsDet.isEmpty()) {
                throw new AnalyzingErrorsException(classes_.errorsDet);
            }
            classes_.validateLocalVariableNamesId(_context);
            if (!classes_.errorsDet.isEmpty()) {
                throw new AnalyzingErrorsException(classes_.errorsDet);
            }
            classes_.validateEl(_context);
            if (!classes_.errorsDet.isEmpty()) {
                throw new AnalyzingErrorsException(classes_.errorsDet);
            }
            classes_.validateReturns(_context);
            if (!classes_.errorsDet.isEmpty()) {
                throw new AnalyzingErrorsException(classes_.errorsDet);
            }
            _context.clearPages();
        } catch (AnalyzingErrorsException _0) {
            _context.setClasses(bk_);
            throw _0;
        } catch (RuntimeException _0) {
            _context.setClasses(bk_);
            throw _0;
        } catch (Error _0) {
            _context.setClasses(bk_);
            throw _0;
        }
    }
    public static CustList<Block> getSortedDescNodes(Block _root) {
        CustList<Block> list_ = new CustList<Block>();
        if (_root == null) {
            return list_;
        }
        Block c_ = _root;
        while (true) {
            if (c_ == null) {
                break;
            }
            list_.add(c_);
            c_ = getNext(c_, _root);
        }
        return list_;
    }

    public static Block getNext(Block _current, Block _root) {
        Block n_ = _current.getFirstChild();
        if (n_ != null) {
            return n_;
        }
        n_ = _current.getNextSibling();
        if (n_ != null) {
            return n_;
        }
        n_ = _current.getParent();
        if (n_ == _root) {
            return null;
        }
        if (n_ != null) {
            Block next_ = n_.getNextSibling();
            while (next_ == null) {
                Block par_ = n_.getParent();
                if (par_ == _root) {
                    break;
                }
                if (par_ == null) {
                    break;
                }
                next_ = par_.getNextSibling();
                n_ = par_;
            }
            if (next_ != null) {
                return next_;
            }
        }
        return null;
    }
    public static CustList<Block> getDirectChildren(Block _element) {
        CustList<Block> list_ = new CustList<Block>();
        if (_element == null) {
            return list_;
        }
        Block firstChild_ = _element.getFirstChild();
        Block elt_ = firstChild_;
        while (elt_ != null) {
            list_.add(elt_);
            elt_ = elt_.getNextSibling();
        }
        return list_;
    }
    public static CustList<InterfaceNode> getSortedDescNodes(InterfaceNode _root) {
        CustList<InterfaceNode> list_ = new CustList<InterfaceNode>();
        if (_root == null) {
            return list_;
        }
        InterfaceNode c_ = _root;
        while (true) {
            if (c_ == null) {
                break;
            }
            list_.add(c_);
            c_ = getNext(c_, _root);
        }
        return list_;
    }

    public static InterfaceNode getNext(InterfaceNode _current, InterfaceNode _root) {
        InterfaceNode n_ = _current.getFirstChild();
        if (n_ != null) {
            return n_;
        }
        n_ = _current.getNextSibling();
        if (n_ != null) {
            return n_;
        }
        n_ = _current.getParent();
        if (n_ == _root) {
            return null;
        }
        if (n_ != null) {
            InterfaceNode next_ = n_.getNextSibling();
            while (next_ == null) {
                InterfaceNode par_ = n_.getParent();
                if (par_ == _root) {
                    break;
                }
                if (par_ == null) {
                    break;
                }
                next_ = par_.getNextSibling();
                n_ = par_;
            }
            if (next_ != null) {
                return next_;
            }
        }
        return null;
    }
    public static CustList<InterfaceNode> getDirectChildren(InterfaceNode _element) {
        CustList<InterfaceNode> list_ = new CustList<InterfaceNode>();
        if (_element == null) {
            return list_;
        }
        InterfaceNode firstChild_ = _element.getFirstChild();
        InterfaceNode elt_ = firstChild_;
        while (elt_ != null) {
            list_.add(elt_);
            elt_ = elt_.getNextSibling();
        }
        return list_;
    }
    public void validateInheritingClasses(ContextEl _context) {
        PageEl page_ = new PageEl();
        _context.clearPages();
        _context.addPage(page_);
        Graph<ClassEdge> inherit_;
        inherit_ = new Graph<ClassEdge>();
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            String d_ = c.getKey();
            RootBlock bl_ = c.getValue();
            boolean int_ = bl_ instanceof InterfaceBlock;
            for (String s: bl_.getDirectSuperClasses()) {
                if (!classesBodies.contains(s)) {
                    if (!StringList.quickEq(s, Object.class.getName())) {
                        UnknownClassName undef_;
                        undef_ = new UnknownClassName();
                        undef_.setClassName(s);
                        undef_.setFileName(d_);
                        undef_.setRc(bl_.getRowCol(0, _context.getTabWidth(), ATTRIBUTE_SUPER_CLASS));
                        errorsDet.add(undef_);
                    }
                } else {
                    RootBlock super_ = classesBodies.getVal(s);
                    if (int_) {
                        if (!(super_ instanceof InterfaceBlock)) {
                            BadInheritedClass enum_;
                            enum_ = new BadInheritedClass();
                            String n_ = s;
                            enum_.setClassName(n_);
                            enum_.setFileName(d_);
                            enum_.setRc(new RowCol());
                            errorsDet.add(enum_);
                        }
                    } else if (super_.isFinalType()) {
                        BadInheritedClass enum_;
                        enum_ = new BadInheritedClass();
                        String n_ = s;
                        enum_.setClassName(n_);
                        enum_.setFileName(d_);
                        enum_.setRc(new RowCol());
                        errorsDet.add(enum_);
                    }
                }
                inherit_.addSegment(new ClassEdge(d_), new ClassEdge(s));
            }
        }
        if (!errorsDet.isEmpty()) {
            return;
        }
        EqList<ClassEdge> cycle_ = inherit_.elementsCycle();
        if (!cycle_.isEmpty()) {
            for (ClassEdge c: cycle_) {
                BadInheritedClass b_;
                b_ = new BadInheritedClass();
                String n_ = c.getId();
                b_.setClassName(n_);
                b_.setFileName(n_);
                b_.setRc(new RowCol());
                errorsDet.add(b_);
            }
            return;
        }
        EqList<ClassEdge> elts_ = inherit_.getElementsListCopy();
        int order_ = 0;
        while (true) {
            EqList<ClassEdge> next_ = new EqList<ClassEdge>();
            for (ClassEdge e: elts_) {
                if (e.getOrder() > CustList.INDEX_NOT_FOUND_ELT) {
                    continue;
                }
                EqList<ClassEdge> list_ = inherit_.getChildren(e);
                boolean allNb_ = true;
                for (ClassEdge s: list_) {
                    ClassEdge s_ = inherit_.getElementByEq(s);
                    if (s_.getOrder() == CustList.INDEX_NOT_FOUND_ELT) {
                        allNb_ = false;
                        break;
                    }
                }
                if (!allNb_) {
                    continue;
                }
                next_.add(e);
            }
            if (next_.isEmpty()) {
                break;
            }
            for (ClassEdge o: next_) {
                o.setOrder(order_);
                order_++;
            }
        }
        elts_.sortElts(new ComparatorClassEdge());
        for (ClassEdge c: elts_) {
            classesInheriting.add(c.getId());
        }
        classesInheriting.removeAllObj(Object.class.getName());
        for (String c: classesInheriting) {
            RootBlock dBl_ = classesBodies.getVal(c);
            StringList all_ = dBl_.getAllSuperClasses();
            StringList direct_ = dBl_.getDirectSuperClasses();
            all_.addAllElts(direct_);
            for (String b: direct_) {
                if (StringList.quickEq(b, Object.class.getName())) {
                    continue;
                }
                RootBlock bBl_ = classesBodies.getVal(b);
                all_.addAllElts(bBl_.getAllSuperClasses());
            }
        }
        for (String c: classesInheriting) {
            if (!(classesBodies.getVal(c) instanceof UniqueRootedBlock)) {
                continue;
            }
            UniqueRootedBlock bl_ = (UniqueRootedBlock) classesBodies.getVal(c);
            StringList all_ = bl_.getAllInterfaces();
            StringList direct_ = bl_.getDirectInterfaces();
            StringList allDirect_ = bl_.getAllDirectInterfaces();
            allDirect_.addAllElts(direct_);
            all_.addAllElts(direct_);
            for (String i: direct_) {
                RootBlock i_ = classesBodies.getVal(i);
                all_.addAllElts(i_.getAllSuperClasses());
            }
            String superClass_ = bl_.getSuperClass();
            StringList needed_;
            if (!StringList.quickEq(superClass_, Object.class.getName())) {
                UniqueRootedBlock super_ = (UniqueRootedBlock) classesBodies.getVal(superClass_);
                all_.addAllElts(super_.getAllInterfaces());
                allDirect_.addAllElts(super_.getAllDirectInterfaces());
                needed_ = super_.getAllSortedInterfaces();
            } else {
                needed_ = new StringList();
            }
            all_.removeAllObj(Object.class.getName());
            all_.removeDuplicates();
            allDirect_.removeDuplicates();
            bl_.getAllSortedInterfaces().addAllElts(getSortedSuperInterfaces(all_));
            bl_.getAllNeededSortedInterfaces().addAllElts(bl_.getAllSortedInterfaces());
            bl_.getAllNeededSortedInterfaces().removeAllElements(needed_);
        }
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            RootBlock r_ = c.getValue();
            if (r_ instanceof UniqueRootedBlock) {
                r_.getAllSuperTypes().addAllElts(((UniqueRootedBlock)r_).getAllSuperClasses());
                r_.getAllSuperTypes().addAllElts(((UniqueRootedBlock)r_).getAllInterfaces());
            } else {
                r_.getAllSuperTypes().addAllElts(((InterfaceBlock)r_).getAllSuperClasses());
            }
        }
    }
    public StringList getSortedSuperInterfaces(StringList _interfaces) {
        StringList sortedSuperInterfaces_ = new StringList();
        for (String i: _interfaces) {
            StringList superInterfaces_ = new StringList(i);
            StringList currentInterfaces_ = new StringList(i);
            while (true) {
                StringList nextInterfaces_ = new StringList();
                for (String c: currentInterfaces_) {
                    if (StringList.quickEq(c, Object.class.getName())) {
                        continue;
                    }
                    InterfaceBlock int_ = (InterfaceBlock) getClassBody(c);
                    StringList directSuperInterfaces_ = int_.getDirectSuperClasses();
                    for (String s:directSuperInterfaces_) {
                        if (superInterfaces_.containsStr(s)) {
                            continue;
                        }
                        superInterfaces_.add(s);
                        nextInterfaces_.add(s);
                    }
                }
                if (nextInterfaces_.isEmpty()) {
                    break;
                }
                currentInterfaces_ = nextInterfaces_;
            }
            StringMap<InterfaceNode> is_ = new StringMap<InterfaceNode>();
            InterfaceNode i_ = new InterfaceNode();
            i_.setInterfaceName(superInterfaces_.first());
            is_.put(superInterfaces_.first(), i_);
            for (String s: superInterfaces_) {
                if (StringList.quickEq(s, Object.class.getName())) {
                    continue;
                }
                InterfaceBlock int_ = (InterfaceBlock) getClassBody(s);
                StringList directSuperInterfaces_ = int_.getDirectSuperClasses();
                InterfaceNode current_ = is_.getVal(s);
                for (String r: directSuperInterfaces_) {
                    InterfaceNode intNode_ = is_.getVal(r);
                    if (intNode_ != null) {
                        continue;
                    }
                    InterfaceNode child_ = current_.getFirstChild();
                    int index_ = CustList.FIRST_INDEX;
                    if (child_ != null) {
                        InterfaceNode loc_ = child_;
                        while (true) {
                            if (loc_ == null) {
                                break;
                            }
                            index_++;
                            InterfaceNode next_ = loc_.getNextSibling();
                            if (next_ == null) {
                                child_ = loc_;
                            }
                            loc_ = next_;
                        }
                    }
                    InterfaceNode ic_ = new InterfaceNode();
                    ic_.setParent(current_);
                    ic_.setInterfaceName(r);
                    is_.put(r, ic_);
                    if (child_ == null) {
                        ic_.setIndexChild(0);
                        current_.setFirstChild(ic_);
                        continue;
                    }
                    ic_.setIndexChild(index_);
                    child_.setNextSibling(ic_);
                }
            }
            CustList<InterfaceNode> all_ = new CustList<InterfaceNode>();
            for (InterfaceNode s: getSortedDescNodes(i_)) {
                all_.add(s);
            }
            int order_ = 0;
            while (true) {
                CustList<InterfaceNode> next_ = new CustList<InterfaceNode>();
                for (InterfaceNode e: all_) {
                    if (e.getOrder() > CustList.INDEX_NOT_FOUND_ELT) {
                        continue;
                    }
                    InterfaceNode cur_ = e;
                    boolean tonumber_ = true;
                    while (cur_ != null) {
                        int index_ = cur_.getIndexChild() - 1;
                        if (index_ >= CustList.FIRST_INDEX) {
                            CustList<InterfaceNode> sn_ = getDirectChildren(cur_.getParent());
                            InterfaceNode s_ = sn_.get(index_);
                            InterfaceNode prev_ = s_;
                            if (prev_.getOrder() == CustList.INDEX_NOT_FOUND_ELT) {
                                tonumber_ = false;
                                break;
                            }
                        }
                        cur_ = cur_.getParent();
                    }
                    if (!tonumber_) {
                        continue;
                    }
                    CustList<InterfaceNode> list_ = getDirectChildren(e);
                    if (!list_.isEmpty()) {
                        InterfaceNode op_ = (InterfaceNode) list_.last();
                        if (op_.getOrder() == CustList.INDEX_NOT_FOUND_ELT) {
                            continue;
                        }
                    }
                    next_.add(e);
                }
                if (next_.isEmpty()) {
                    break;
                }
                for (InterfaceNode o: next_) {
                    o.setOrder(order_);
                    order_++;
                }
            }
            all_.sortElts(new ComparatorInterfaceNode());
            for (InterfaceNode j: all_) {
                String name_ = j.getInterfaceName();
                if (StringList.quickEq(name_, Object.class.getName())) {
                    continue;
                }
                if (!sortedSuperInterfaces_.containsStr(name_)) {
                    sortedSuperInterfaces_.add(name_);
                }
            }
        }
        return sortedSuperInterfaces_;
    }
    public void validateIds(ContextEl _context) {
        PageEl page_ = new PageEl();
        _context.clearPages();
        _context.addPage(page_);
        for (String c: classesInheriting) {
            RootBlock bl_ = classesBodies.getVal(c);
            bl_.validateIds(_context);
        }
    }
    public void validateOverridingInherit(ContextEl _context) {
        PageEl page_ = new PageEl();
        _context.clearPages();
        _context.addPage(page_);
        for (String c: classesInheriting) {
            RootBlock bl_ = classesBodies.getVal(c);
            bl_.checkCompatibility(_context);
            bl_.setupBasicOverrides(_context);
            bl_.checkImplements(_context);
        }
    }
    public void validateClassesAccess(ContextEl _context) {
        PageEl page_ = new PageEl();
        _context.clearPages();
        _context.addPage(page_);
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            String className_ = c.getKey();
            CustList<Block> bl_ = getSortedDescNodes(c.getValue());
            for (Block e: bl_) {
                Block b_ = (Block) e;
                for (EntryCust<String, String> n: b_.getClassNames().entryList()) {
                    String classNameLoc_ = n.getValue();
                    try {
                        String base_ = PrimitiveTypeUtil.getQuickComponentBaseType(classNameLoc_).getComponent();
                        if (classesBodies.contains(base_)) {
                            if (!canAccessClass(className_, base_)) {
                                BadAccessClass err_ = new BadAccessClass();
                                err_.setFileName(className_);
                                err_.setRc(b_.getRowCol(0, _context.getTabWidth(), n.getKey()));
                                err_.setId(classNameLoc_);
                                errorsDet.add(err_);
                            }
                            continue;
                        }
                    } catch (RuntimeClassNotFoundException _0) {
                        UnknownClassName un_ = new UnknownClassName();
                        un_.setClassName(classNameLoc_);
                        un_.setFileName(className_);
                        un_.setRc(b_.getRowCol(0, _context.getTabWidth(), n.getKey()));
                        errorsDet.add(un_);
                    }
                }
            }
        }
    }
    //validate local variables names and loop variables names
    public void validateLocalVariableNamesId(ContextEl _context) {
        PageEl page_ = new PageEl();
        _context.clearPages();
        _context.addPage(page_);
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            String className_ = c.getKey();
            CustList<Block> bl_ = getSortedDescNodes(c.getValue());
            for (Block b: bl_) {
                Block block_ = (Block)b;
                if (b instanceof InitVariable) {
                    String var_ = ((InitVariable)b).getVariableName();
                    if (!StringList.isWord(var_)) {
                        BadVariableName b_ = new BadVariableName();
                        b_.setFileName(className_);
                        b_.setRc(block_.getRowCol(0, _context.getTabWidth(), ATTRIBUTE_VAR));
                        b_.setVarName(var_);
                        errorsDet.add(b_);
                    }
                    localVariablesNames.add(var_);
                }
                if (b instanceof ForLoop) {
                    String var_ = ((ForLoop)b).getVariableName();
                    if (!StringList.isWord(var_)) {
                        BadVariableName b_ = new BadVariableName();
                        b_.setFileName(className_);
                        b_.setRc(block_.getRowCol(0, _context.getTabWidth(), ATTRIBUTE_VAR));
                        b_.setVarName(var_);
                        errorsDet.add(b_);
                    }
                }
                if (b instanceof CatchEval) {
                    String var_ = ((CatchEval)b).getVariableName();
                    if (!StringList.isWord(var_)) {
                        BadVariableName b_ = new BadVariableName();
                        b_.setFileName(className_);
                        b_.setRc(block_.getRowCol(0, _context.getTabWidth(), ATTRIBUTE_VAR));
                        b_.setVarName(var_);
                        errorsDet.add(b_);
                    }
                }
            }
        }
        localVariablesNames.removeDuplicates();
        int i_ = CustList.FIRST_INDEX;
        while (localVariablesNames.containsStr(TEMP_PREFIX+i_)) {
            i_++;
        }
        int five_ = i_;
        i_++;
        while (localVariablesNames.containsStr(TEMP_PREFIX+i_)) {
            i_++;
        }
        int six_ = i_;
        String fifthArg_ = TEMP_PREFIX+five_;
        String sixthArg_ = TEMP_PREFIX+six_;
        localVariablesNames.add(fifthArg_);
        localVariablesNames.add(sixthArg_);
        String nateqt_ = StringList.simpleFormat(NAT_EQ_FORMAT, fifthArg_, sixthArg_);
        natEqEl = new EqualsEl(fifthArg_, sixthArg_);
        page_.getLocalVars().put(fifthArg_, new LocalVariable());
        page_.getLocalVars().put(sixthArg_, new LocalVariable());
        exps = ElUtil.getAnalyzedOperations(nateqt_, _context, Calculation.staticCalculation(true));
        String locName_ = page_.getNextTempVar(this);
        LocalVariable locVar_ = new LocalVariable();
        locVar_.setClassName(Iterable.class.getName());
        page_.getLocalVars().put(locName_, locVar_);
        iteratorVar = locName_;
        String exp_ = locName_ + LOC_VAR + ITERATOR;
        expsIterator = ElUtil.getAnalyzedOperations(exp_, _context, Calculation.staticCalculation(true));
        locName_ = page_.getNextTempVar(this);
        locVar_ = new LocalVariable();
        locVar_.setClassName(Iterator.class.getName());
        page_.getLocalVars().put(locName_, locVar_);
        hasNextVar = locName_;
        exp_ = locName_ + LOC_VAR + HAS_NEXT;
        expsHasNext = ElUtil.getAnalyzedOperations(exp_, _context, Calculation.staticCalculation(true));
        locName_ = page_.getNextTempVar(this);
        locVar_ = new LocalVariable();
        locVar_.setClassName(Iterator.class.getName());
        page_.getLocalVars().put(locName_, locVar_);
        nextVar = locName_;
        exp_ = locName_ + LOC_VAR + NEXT;
        expsNext = ElUtil.getAnalyzedOperations(exp_, _context, Calculation.staticCalculation(true));
        page_.getLocalVars().removeKey(fifthArg_);
        page_.getLocalVars().removeKey(sixthArg_);
        page_.getLocalVars().removeKey(iteratorVar);
        page_.getLocalVars().removeKey(hasNextVar);
        page_.getLocalVars().removeKey(nextVar);
    }

    public boolean canAccessField(String _className, String _accessedClass, String _name) {
        Block access_ = (Block) getClassBody(_accessedClass);
        CustList<Block> bl_ = getDirectChildren(access_);
        InfoBlock i_ = null;
        for (Block b: bl_) {
            if (b instanceof InfoBlock) {
                if (StringList.quickEq(((InfoBlock)b).getFieldName(), _name)) {
                    i_ = (InfoBlock)b;
                }
            }
        }
        return canAccess(_className, i_);
    }

    public boolean canAccessConstructor(String _className, String _accessedClass, FctConstraints _id) {
        Block access_ = (Block) getClassBody(_accessedClass);
        CustList<Block> bl_ = getDirectChildren(access_);
        ConstructorBlock i_ = null;
        for (Block b: bl_) {
            if (b instanceof ConstructorBlock) {
                if (((ConstructorBlock)b).getConstraints(this).eq(_id)) {
                    i_ = (ConstructorBlock)b;
                }
            }
        }
        if (i_ == null) {
            return true;
        }
        return canAccess(_className, i_);
    }

    public boolean canAccessMethod(String _className, String _accessedClass, FctConstraints _id) {
        RootBlock access_ = getClassBody(_accessedClass);
        CustList<Block> bl_ = getDirectChildren(access_);
        MethodBlock i_ = null;
        for (Block b: bl_) {
            if (b instanceof MethodBlock) {
                if (((MethodBlock)b).getConstraints(this).eq(_id)) {
                    i_ = (MethodBlock)b;
                }
            }
        }
        if (i_ == null) {
            String int_ = access_.getDefaultMethods().getVal(_id);
            i_ = getMethodBody(int_, _id);
        }
        return canAccess(_className, i_);
    }

    public boolean canAccessClass(String _className, String _accessedClass) {
        RootBlock access_ = getClassBody(_accessedClass);
        return canAccess(_className, access_);
    }

    public boolean canAccess(String _className, AccessibleBlock _block) {
        if (_block.getAccess() == AccessEnum.PUBLIC) {
            return true;
        }
        RootBlock root_ = getClassBody(_className);
        RootBlock belong_ = _block.belong();
        if (_block.getAccess() == AccessEnum.PROTECTED) {
            if (PrimitiveTypeUtil.canBeUseAsArgument(belong_.getFullName(), _className, this)) {
                return true;
            }
            if (StringList.quickEq(belong_.getPackageName(), root_.getPackageName())) {
                return true;
            }
            return false;
        }
        if (_block.getAccess() == AccessEnum.PACKAGE) {
            if (StringList.quickEq(belong_.getPackageName(), root_.getPackageName())) {
                return true;
            }
            return false;
        }
        if (StringList.quickEq(belong_.getFullName(), root_.getFullName())) {
            return true;
        }
        return false;
    }

    public EqualsEl getNatEqEl() {
        return natEqEl;
    }

    public String getIteratorVar() {
        return iteratorVar;
    }

    public String getHasNextVar() {
        return hasNextVar;
    }

    public String getNextVar() {
        return nextVar;
    }

    public ExpressionLanguage getEqIterator() {
        return new ExpressionLanguage(expsIterator);
    }

    public ExpressionLanguage getEqHasNext() {
        return new ExpressionLanguage(expsHasNext);
    }

    public ExpressionLanguage getEqNext() {
        return new ExpressionLanguage(expsNext);
    }

    public ExpressionLanguage getEqNatEl() {
        return new ExpressionLanguage(exps);
    }

    //validate el and its possible returned type
    public void validateEl(ContextEl _context) {
        PageEl page_ = new PageEl();
        _context.clearPages();
        _context.addPage(page_);
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            CustList<Block> bl_ = getDirectChildren(c.getValue());
            for (Block b: bl_) {
                if (b instanceof FunctionBlock) {
                    FunctionBlock method_ = (FunctionBlock) b;
                    method_.checkFctBlocksTree(_context);
                }
                if (b instanceof InfoBlock) {
                    InfoBlock method_ = (InfoBlock) b;
                    method_.checkBlocksTree(_context);
                }
            }
        }
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            CustList<Block> bl_ = getDirectChildren(c.getValue());
            for (Block b: bl_) {
                if (b instanceof InfoBlock) {
                    page_.setGlobalClass(c.getKey());
                    InfoBlock method_ = (InfoBlock) b;
                    method_.buildExpressionLanguage(_context);
                }
                if (b instanceof AloneBlock) {
                    page_.setGlobalClass(c.getKey());
                    AloneBlock method_ = (AloneBlock) b;
                    method_.buildFctInstructions(_context);
                    page_.getLocalVars().clear();
                    page_.getCatchVars().clear();
                    page_.getVars().clear();
                }
                if (b instanceof Returnable) {
                    page_.setGlobalClass(c.getKey());
                    Returnable method_ = (Returnable) b;
                    StringList params_ = method_.getParametersNames();
                    StringList types_ = method_.getParametersTypes();
                    int len_ = params_.size();
                    for (int i = CustList.FIRST_INDEX; i < len_; i++) {
                        String p_ = params_.get(i);
                        String c_ = types_.get(i);
                        LocalVariable lv_ = new LocalVariable();
                        lv_.setClassName(c_);
                        page_.getParameters().put(p_, lv_);
                    }
                    method_.buildFctInstructions(_context);
                    page_.getParameters().clear();
                    page_.getLocalVars().clear();
                    page_.getCatchVars().clear();
                    page_.getVars().clear();
                }
            }
        }
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            CustList<Block> bl_ = getDirectChildren(c.getValue());
            for (Block b: bl_) {
                if (b instanceof InfoBlock) {
                    page_.setGlobalClass(c.getKey());
                    InfoBlock method_ = (InfoBlock) b;
                    method_.checkCallConstructor(_context);
                }
                if (b instanceof FunctionBlock) {
                    page_.setGlobalClass(c.getKey());
                    FunctionBlock method_ = (FunctionBlock) b;
                    method_.checkFctConstrCalls(_context);
                }
            }
        }
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            RootBlock clblock_ = c.getValue();
            if (clblock_ instanceof UniqueRootedBlock) {
                ((UniqueRootedBlock)clblock_).validateConstructors(_context);
            }
        }
    }
    //validate missing return
    //validate break,continue ancestors / try/catch/finally / switch/case/default
    //validate dead code
    public void validateReturns(ContextEl _context) {
        PageEl page_ = new PageEl();
        _context.clearPages();
        _context.addPage(page_);
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            String className_ = c.getKey();
            CustList<Block> bl_ = getDirectChildren(c.getValue());
            for (Block b: bl_) {
                if (b instanceof AloneBlock) {
                    CustList<Block> chSort_ = getSortedDescNodes(b);
                    CustList<Block> all_ = new CustList<Block>();
                    for (Block s: chSort_) {
                        all_.add((Block) s);
                    }
                    int order_ = 0;
                    while (true) {
                        CustList<Block> next_ = new CustList<Block>();
                        for (Block e: all_) {
                            if (e.getOrder() > CustList.INDEX_NOT_FOUND_ELT) {
                                continue;
                            }
                            CustList<Block> list_ = getDirectChildren(e);
                            boolean allNb_ = true;
                            for (Block s: list_) {
                                Block op_ = (Block) s;
                                if (op_.getOrder() == CustList.INDEX_NOT_FOUND_ELT) {
                                    allNb_ = false;
                                    break;
                                }
                            }
                            if (!allNb_) {
                                continue;
                            }
                            next_.add(e);
                        }
                        if (next_.isEmpty()) {
                            break;
                        }
                        for (Block o: next_) {
                            o.setOrder(order_);
                            order_++;
                        }
                    }
                    all_.sortElts(new ComparatorBlockOrder());
                    for (Block d: all_) {
                        d.setAlwaysSkipped();
                    }
                    for (Block d: all_) {
                        d.setExitable();
                    }
                    for (Block d: all_) {
                        d.setStoppable();
                    }
                    String name_ = EMPTY_STRING;
                    EqList<ClassName> pTypes_ = new EqList<ClassName>();
                    MethodId id_ = new MethodId(name_, pTypes_);
                    for (Block d: all_) {
                        RowCol rc_ = d.existDeadCodeInBlock(0, _context.getTabWidth());
                        if (rc_.getRow() > 0) {
                            DeadCodeMethod deadCode_ = new DeadCodeMethod();
                            deadCode_.setFileName(className_);
                            deadCode_.setRc(rc_);
                            deadCode_.setId(id_);
                            errorsDet.add(deadCode_);
                        }
                    }
                }
                if (b instanceof Returnable) {
                    Returnable method_ = (Returnable) b;
                    if (method_ instanceof MethodBlock) {
                        if (((MethodBlock)b).isAbstractMethod()) {
                            continue;
                        }
                    }
                    CustList<Block> chSort_ = getSortedDescNodes(b);
                    CustList<Block> all_ = new CustList<Block>();
                    for (Block s: chSort_) {
                        all_.add((Block) s);
                    }
                    int order_ = 0;
                    while (true) {
                        CustList<Block> next_ = new CustList<Block>();
                        for (Block e: all_) {
                            if (e.getOrder() > CustList.INDEX_NOT_FOUND_ELT) {
                                continue;
                            }
                            CustList<Block> list_ = getDirectChildren(e);
                            boolean allNb_ = true;
                            for (Block s: list_) {
                                Block op_ = (Block) s;
                                if (op_.getOrder() == CustList.INDEX_NOT_FOUND_ELT) {
                                    allNb_ = false;
                                    break;
                                }
                            }
                            if (!allNb_) {
                                continue;
                            }
                            next_.add(e);
                        }
                        if (next_.isEmpty()) {
                            break;
                        }
                        for (Block o: next_) {
                            o.setOrder(order_);
                            order_++;
                        }
                    }
                    all_.sortElts(new ComparatorBlockOrder());
                    for (Block d: all_) {
                        d.setAlwaysSkipped();
                    }
                    for (Block d: all_) {
                        d.setExitable();
                    }
                    for (Block d: all_) {
                        d.setStoppable();
                    }
                    Block r_ = all_.last();
                    String name_ = method_.getName();
                    StringList types_ = method_.getParametersTypes();
                    int len_ = types_.size();
                    EqList<ClassName> pTypes_ = new EqList<ClassName>();
                    for (int i = CustList.FIRST_INDEX; i < len_; i++) {
                        String n_ = types_.get(i);
                        pTypes_.add(new ClassName(n_, i + 1 == len_ && method_.isVarargs()));
                    }
                    MethodId id_ = new MethodId(name_, pTypes_);
                    if (!r_.isExitable() && !StringList.quickEq(method_.getReturnType(), OperationNode.VOID_RETURN)) {
                        MissingReturnMethod miss_ = new MissingReturnMethod();
                        miss_.setRc(method_.getRowCol(0, _context.getTabWidth(), EMPTY_STRING));
                        miss_.setFileName(className_);
                        miss_.setId(id_);
                        miss_.setReturning(method_.getReturnType());
                        errorsDet.add(miss_);
                    }
                    for (Block d: all_) {
                        RowCol rc_ = d.existDeadCodeInBlock(0, _context.getTabWidth());
                        if (rc_.getRow() > 0) {
                            DeadCodeMethod deadCode_ = new DeadCodeMethod();
                            deadCode_.setFileName(className_);
                            deadCode_.setRc(rc_);
                            deadCode_.setId(id_);
                            errorsDet.add(deadCode_);
                        }
                    }
                }
            }
        }
    }

    public StringList getLocalVariablesNames() {
        return localVariablesNames;
    }

    public RootBlock getClassBody(String _className) {
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            if (!StringList.quickEq(c.getKey(), _className)) {
                continue;
            }
            return c.getValue();
        }
        return null;
    }

    public MethodBlock getMethodBody(String _className, FctConstraints _methodId) {
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            if (!StringList.quickEq(c.getKey(), _className)) {
                continue;
            }
            CustList<Block> bl_ = getDirectChildren(c.getValue());
            for (Block b: bl_) {
                if (!(b instanceof MethodBlock)) {
                    continue;
                }
                MethodBlock method_ = (MethodBlock) b;
                if (method_.getConstraints(this).eq(_methodId)) {
                    return method_;
                }
            }
        }
        return null;
    }
    public ConstructorBlock getConstructorBody(String _className, FctConstraints _methodId) {
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            if (!StringList.quickEq(c.getKey(), _className)) {
                continue;
            }
            CustList<Block> bl_ = getDirectChildren(c.getValue());
            for (Block b: bl_) {
                if (!(b instanceof ConstructorBlock)) {
                    continue;
                }
                ConstructorBlock method_ = (ConstructorBlock) b;
                if (method_.getConstraints(this).eq(_methodId)) {
                    return method_;
                }
            }
        }
        return null;
    }
    public boolean isInitialized(String _name) {
        return initializedClasses.getVal(_name);
    }
    public boolean isSuccessfulInitialized(String _name) {
        return successfulInitializedClasses.getVal(_name);
    }
    public void initialize(String _name) {
        initializedClasses.put(_name, true);
    }
    public void successInitClass(String _name) {
        successfulInitializedClasses.put(_name, true);
    }
    public void preInitializeStaticFields(String _className) {
        StringMap<FieldBlock> fieldsInfos_;
        fieldsInfos_ = new StringMap<FieldBlock>();
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            String k_ = c.getKey();
            if (!StringList.quickEq(k_, _className)) {
                continue;
            }
            CustList<Block> bl_ = getDirectChildren(c.getValue());
            for (Block b: bl_) {
                if (b instanceof FieldBlock) {
                    FieldBlock method_ = (FieldBlock) b;
                    String m_ = method_.getFieldName();
                    if (method_.isStaticField()) {
                        Struct str_ = method_.getDefaultStruct();
                        staticFields.put(new ClassField(_className, m_), str_);
                        continue;
                    }
                    fieldsInfos_.put(m_, method_);
                }
            }
        }
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            String k_ = c.getKey();
            if (!StringList.quickEq(k_, _className)) {
                continue;
            }
            CustList<Block> bl_ = getDirectChildren(c.getValue());
            int i_ = CustList.FIRST_INDEX;
            CustList<Struct> values_ = new CustList<Struct>();
            for (Block b: bl_) {
                if (b instanceof ElementBlock) {
                    ElementBlock method_ = (ElementBlock) b;
                    String m_ = method_.getFieldName();
                    CustEnum enum_;
                    enum_ = new CustEnum(_className, m_, i_);
                    ObjectMap<ClassField,Struct> fields_;
                    fields_ = new ObjectMap<ClassField,Struct>();
                    for (EntryCust<String, FieldBlock> f: fieldsInfos_.entryList()) {
                        String f_ = f.getKey();
                        FieldBlock inf_ = f.getValue();
                        fields_.put(new ClassField(_className, f_), inf_.getDefaultStruct());
                    }
                    Struct str_;
                    str_ = new Struct(enum_, _className, fields_);
                    staticFields.put(new ClassField(_className, m_), str_);
                    values_.add(str_);
                    i_++;
                }
            }
            values.put(_className, values_);
        }
    }
    public void initializeStaticField(ClassField _clField, Struct _str) {
        staticFields.put(_clField, _str);
    }
    public Struct getStaticField(ClassField _clField) {
        return staticFields.getVal(_clField);
    }
    public ClassMetaInfo getClassMetaInfo(String _name) {
        for (EntryCust<String, RootBlock> c: classesBodies.entryList()) {
            ObjectNotNullMap<FctConstraints, MethodMetaInfo> infos_;
            infos_ = new ObjectNotNullMap<FctConstraints, MethodMetaInfo>();
            StringMap<FieldMetaInfo> infosFields_;
            infosFields_ = new StringMap<FieldMetaInfo>();
            ObjectNotNullMap<FctConstraints, ConstructorMetaInfo> infosConst_;
            infosConst_ = new ObjectNotNullMap<FctConstraints, ConstructorMetaInfo>();
            String k_ = c.getKey();
            if (!StringList.quickEq(k_, _name)) {
                continue;
            }
            RootBlock clblock_ = c.getValue();
            CustList<Block> bl_ = getDirectChildren((Block)clblock_);
            for (Block b: bl_) {
                if (b instanceof InfoBlock) {
                    InfoBlock method_ = (InfoBlock) b;
                    String m_ = method_.getFieldName();
                    String ret_ = method_.getClassName();
                    boolean enumElement_ = b instanceof ElementBlock;
                    boolean staticElement_ = method_.isStaticField();
                    boolean finalElement_ = method_.isFinalField();
                    FieldMetaInfo met_ = new FieldMetaInfo(k_, m_, ret_, staticElement_, finalElement_, enumElement_);
                    infosFields_.put(m_, met_);
                }
                if (b instanceof MethodBlock) {
                    MethodBlock method_ = (MethodBlock) b;
                    FctConstraints id_ = method_.getConstraints(this);
                    String ret_ = method_.getReturnType();
                    MethodMetaInfo met_ = new MethodMetaInfo(method_.getDeclaringType(), method_.getModifier(), ret_);
                    infos_.put(id_, met_);
                }
                if (b instanceof ConstructorBlock) {
                    ConstructorBlock method_ = (ConstructorBlock) b;
                    FctConstraints id_ = method_.getConstraints(this);
                    String ret_ = OperationNode.VOID_RETURN;
                    ConstructorMetaInfo met_ = new ConstructorMetaInfo(ret_);
                    infosConst_.put(id_, met_);
                }
            }
            if (clblock_ instanceof InterfaceBlock) {
                return new ClassMetaInfo(((InterfaceBlock)clblock_).getDirectSuperClasses(), infosFields_,infos_, infosConst_, ClassCategory.INTERFACE);
            }
            ClassCategory cat_ = ClassCategory.CLASS;
            if (clblock_ instanceof EnumBlock) {
                cat_ = ClassCategory.ENUM;
            } else if (clblock_ instanceof InterfaceBlock) {
                cat_ = ClassCategory.INTERFACE;
            }
            boolean abs_ = clblock_.isAbstractType();
            boolean final_ = clblock_.isFinalType();
            return new ClassMetaInfo(((UniqueRootedBlock) clblock_).getSuperClass(), infosFields_,infos_, infosConst_, cat_, abs_, final_);
        }
        return null;
    }
}
