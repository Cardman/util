package code.expressionlanguage.opers;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Delimiters;
import code.expressionlanguage.ElResolver;
import code.expressionlanguage.ElUtil;
import code.expressionlanguage.Mapping;
import code.expressionlanguage.OperationsSequence;
import code.expressionlanguage.PrimitiveTypeUtil;
import code.expressionlanguage.Templates;
import code.expressionlanguage.exceptions.ErrorCausingException;
import code.expressionlanguage.exceptions.InvokeException;
import code.expressionlanguage.exceptions.StaticAccessException;
import code.expressionlanguage.exceptions.UnwrappingException;
import code.expressionlanguage.exceptions.VoidArgumentException;
import code.expressionlanguage.methods.Classes;
import code.expressionlanguage.methods.ConstructorBlock;
import code.expressionlanguage.methods.InterfaceBlock;
import code.expressionlanguage.methods.MethodBlock;
import code.expressionlanguage.methods.RootBlock;
import code.expressionlanguage.methods.util.ArgumentsPair;
import code.expressionlanguage.methods.util.TypeVar;
import code.expressionlanguage.opers.util.ArgumentsGroup;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.opers.util.ClassMatching;
import code.expressionlanguage.opers.util.ClassMetaInfo;
import code.expressionlanguage.opers.util.ClassMethodId;
import code.expressionlanguage.opers.util.ClassMethodIdResult;
import code.expressionlanguage.opers.util.ClassMethodIdReturn;
import code.expressionlanguage.opers.util.ClassName;
import code.expressionlanguage.opers.util.ConstructorId;
import code.expressionlanguage.opers.util.ConstructorInfo;
import code.expressionlanguage.opers.util.ConstructorMetaInfo;
import code.expressionlanguage.opers.util.ConstrustorIdVarArg;
import code.expressionlanguage.opers.util.Fcts;
import code.expressionlanguage.opers.util.FieldInfo;
import code.expressionlanguage.opers.util.FieldMetaInfo;
import code.expressionlanguage.opers.util.FieldResult;
import code.expressionlanguage.opers.util.Identifiable;
import code.expressionlanguage.opers.util.MethodId;
import code.expressionlanguage.opers.util.MethodInfo;
import code.expressionlanguage.opers.util.MethodMetaInfo;
import code.expressionlanguage.opers.util.MethodModifier;
import code.expressionlanguage.opers.util.ParametersGroup;
import code.expressionlanguage.opers.util.Parametrable;
import code.expressionlanguage.opers.util.Parametrables;
import code.expressionlanguage.opers.util.SearchingMemberStatus;
import code.expressionlanguage.opers.util.Struct;
import code.expressionlanguage.types.NativeTypeUtil;
import code.serialize.ConverterMethod;
import code.serialize.exceptions.InvokingException;
import code.serialize.exceptions.NoSuchDeclaredConstructorException;
import code.serialize.exceptions.NoSuchDeclaredFieldException;
import code.serialize.exceptions.NoSuchDeclaredMethodException;
import code.util.CustList;
import code.util.EntryCust;
import code.util.EqList;
import code.util.IdMap;
import code.util.NatTreeMap;
import code.util.ObjectNotNullMap;
import code.util.StringList;
import code.util.StringMap;
import code.util.exceptions.NullObjectException;
import code.util.exceptions.RuntimeClassNotFoundException;

public abstract class OperationNode {

    public static final String VOID_RETURN = "$void";
    public static final String METH_NAME = "name";
    public static final String METH_ORDINAL = "ordinal";
    public static final String METH_VALUES = "values";
    public static final String METH_VALUEOF = "valueOf";

    protected static final char ESCAPE_META_CHAR = '\\';
    protected static final char DELIMITER_CHAR = 39;
    protected static final char DELIMITER_STRING = 34;
    protected static final char UNICODE = 'u';
    protected static final char IND_FORM = 'f';
    protected static final char IND_LINE = 'n';
    protected static final char IND_LINE_FEED = 'r';
    protected static final char IND_TAB = 't';
    protected static final char IND_BOUND = 'b';
    protected static final char ARR_LEFT = '[';
    protected static final char ARR_RIGHT = ']';
    protected static final char PAR_LEFT = '(';
    protected static final char PAR_RIGHT = ')';
    protected static final String PAR_RIGHT_STR = ")";
    protected static final char SEP_ARG = ',';
    protected static final char FIRST_VAR_ARG = '?';
    protected static final char GET_VAR = ';';
    protected static final char DOT_VAR = '.';
    protected static final char EXTERN_CLASS = '^';
    protected static final char INTERN_CLASS = '$';
    protected static final String SUPER_ACCESS = "super";
    protected static final String CURRENT = "this";
    protected static final String INSTANCE = "new";
    protected static final String STATIC_ACCESS = "static";
    protected static final String NULL_REF_STRING = "null";
    protected static final String TRUE_STRING = "true";
    protected static final String FALSE_STRING = "false";
    protected static final String INSTANCEOF = "instanceof";
    protected static final String BOOLEAN = "boolean";
    protected static final String CAST = "class";
    protected static final char MIN_ENCODE_DIGIT = '0';
    protected static final char MAX_ENCODE_DIGIT = '9';
    protected static final char MIN_ENCODE_LOW_LETTER = 'a';
    protected static final char MAX_ENCODE_LOW_LETTER = 'f';
    protected static final char MIN_ENCODE_UPP_LETTER = 'A';
    protected static final char MAX_ENCODE_UPP_LETTER = 'F';
    protected static final String GET_INDEX = ";;";
    protected static final String GET_CATCH_VAR = ";..";
    protected static final String GET_LOC_VAR = ";.";
    protected static final String GET_ATTRIBUTE = ";";
    protected static final char MATH_INTERPRET = '`';
    protected static final String GET_PARAM = ";.;";
    protected static final String GET_FIELD = ";;;";
    protected static final String CURRENT_INTANCE = "^this";
    protected static final String STATIC_CALL = "^^";
    protected static final String CLASS_CHOICE = "classchoice";
    protected static final String VAR_ARG = "vararg";
    protected static final String FIRST_OPT = "firstopt";

    protected static final String CLASS_CHOICE_PREF = EXTERN_CLASS + CLASS_CHOICE + EXTERN_CLASS;

    protected static final String FCT = "(";

    protected static final String ARR = "[";

    protected static final String ARR_DYN = "[]";

    protected static final String DOT = ".";

    protected static final String NEG_BOOL = "!";

    protected static final String UNARY_PLUS = "+";

    protected static final String UNARY_MINUS = "-";

    protected static final String MULT = "*";

    protected static final String DIV = "/";

    protected static final String MOD = "%";

    protected static final String PLUS = "+";

    protected static final String MINUS = "-";

    protected static final String LOWER_EQ = "<=";

    protected static final String LOWER = "<";

    protected static final String GREATER_EQ = ">=";

    protected static final String GREATER = ">";

    protected static final String EQ = "=";

    protected static final String DIFF = "!=";

    protected static final String AND = "&";

    protected static final String OR = "|";
    protected static final String EMPTY_STRING = "";
    protected static final String RETURN_LINE = "\n";
    protected static final String SPACE = " ";
    protected static final String RETURN_TAB = RETURN_LINE+"\t";

    protected static final String GET_CLASS = "getClass";

    protected static final String VARARG_SUFFIX = "...";

    private MethodOperation parent;

    private boolean initializedNextSibling;

    private OperationNode previousSibling;

    private OperationNode nextSibling;

    private Argument previousArgument;

    private Argument argument;

    private OperationsSequence operations;

    private int indexInEl;

    private int order = CustList.INDEX_NOT_FOUND_ELT;

    private ContextEl conf;

    private final int indexChild;

    private boolean vararg;
    private boolean firstOptArg;
    private ClassArgumentMatching previousResultClass;
    private ClassArgumentMatching resultClass;

    private boolean needGlobalArgument;
    private boolean staticAccess;
    private boolean staticBlock;

    OperationNode(int _indexInEl, ContextEl _importingPage, int _indexChild, MethodOperation _m, OperationsSequence _op) {
        parent = _m;
        indexInEl = _indexInEl;
        operations = _op;
        conf = _importingPage;
        indexChild = _indexChild;
    }

    public abstract void analyze(CustList<OperationNode> _nodes, ContextEl _conf, String _fieldName, String _op);

    public abstract void calculate(CustList<OperationNode> _nodes, ContextEl _conf, String _op);

    public abstract Argument calculate(IdMap<OperationNode, ArgumentsPair> _nodes, ContextEl _conf, String _op);


    public final void setRelativeOffsetPossibleLastPage(int _offset, ContextEl _cont) {
        _cont.getLastPage().setOffset(operations.getDelimiter().getIndexBegin()+_offset);
    }

    public static void addRelativeToOffsetPossibleLastPage(int _offset, ContextEl _cont) {
        _cont.getLastPage().addToOffset(_offset);
    }

    public static OperationNode createOperationNode(int _index, ContextEl _conf,
            int _indexChild, MethodOperation _m, OperationsSequence _op) {
        if (_op.getOperators().isEmpty()) {
            return new ConstantOperation(_index, _conf, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.FCT_OPER_PRIO) {
            if (_op.getFctName().trim().isEmpty()) {
                return new IdOperation(_index, _conf, _indexChild, _m, _op);
            }
            if (_op.getFctName().trim().startsWith(EXTERN_CLASS+INSTANCE+DOT_VAR)) {
                return new InstanceOperation(_index, _conf, _indexChild, _m, _op);
            }
            return new FctOperation(_index, _conf, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.ARR_OPER_PRIO) {
            return new ArrOperation(_index, _conf, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.DOT_PRIO) {
            return new DotOperation(_index, _conf, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.UNARY_PRIO) {
            int key_ = _op.getOperators().firstKey();
            if (StringList.quickEq(_op.getOperators().getVal(key_).trim(), NEG_BOOL)) {
                return new UnaryBooleanOperation(_index, _conf, _indexChild, _m, _op);
            }
            return new UnaryOperation(_index, _conf, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.MULT_PRIO) {
            return new MultOperation(_index, _conf, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.ADD_PRIO) {
            return new AddOperation(_index, _conf, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.CMP_PRIO) {
            return new CmpOperation(_index, _conf, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.EQ_PRIO) {
            return new EqOperation(_index, _conf, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.AND_PRIO) {
            return new AndOperation(_index, _conf, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.OR_PRIO) {
            return new OrOperation(_index, _conf, _indexChild, _m, _op);
        }
        return null;
    }

    final boolean isIntermediateDotted() {
        MethodOperation par_ = getParent();
        if (par_ instanceof ArrOperation && isFirstChild()) {
            return par_.isSimpleIntermediateDotted();
        }
        return isSimpleIntermediateDotted();
    }

    final boolean isSimpleIntermediateDotted() {
        MethodOperation par_ = getParent();
        return !isFirstChild() && par_ instanceof DotOperation;
    }

    abstract boolean isFirstChild();

    final boolean isAnalyzed() {
        return resultClass != null;
    }

    public final boolean isCalculated(IdMap<OperationNode, ArgumentsPair> _nodes) {
        if (isPossibleInitClass()) {
            return false;
        }
        OperationNode op_ = this;
        while (op_ != null) {
            if (_nodes.getVal(op_).getArgument() != null) {
                return true;
            }
            op_ = op_.getParent();
        }
        return false;
    }

    public final boolean isCalculated() {
        if (isPossibleInitClass()) {
            return false;
        }
        OperationNode op_ = this;
        while (op_ != null) {
            if (op_.getArgument() != null) {
                return true;
            }
            op_ = op_.getParent();
        }
        return false;
    }

    public abstract boolean isPossibleInitClass();

    public final boolean isSuperThis() {
        return isSuperConstructorCall() || isOtherConstructorClass();
    }

    public abstract boolean isSuperConstructorCall();

    public abstract boolean isOtherConstructorClass();

    public abstract ConstructorId getConstId();

    public abstract OperationNode getFirstChild();

    public final OperationNode getNextSibling() {
        if (initializedNextSibling) {
            return nextSibling;
        }
        initializedNextSibling = true;
        MethodOperation p_ = getParent();
        if (p_ == null) {
            return null;
        }
        NatTreeMap<Integer,String> children_ = p_.getChildren();
        if (indexChild + 1 >= children_.size()) {
            return null;
        }
        String value_ = children_.getValue(indexChild + 1);
        Delimiters d_ = getOperations().getDelimiter();
        int curKey_ = children_.getKey(indexChild + 1);
        d_.setChildOffest(curKey_);
        int offset_ = p_.getIndexInEl()+curKey_;
        OperationsSequence r_ = ElResolver.getOperationsSequence(offset_, value_, conf, d_);
        nextSibling = createOperationNode(offset_, conf, indexChild + 1, p_, r_);
        nextSibling.previousSibling = this;
        return nextSibling;
    }
    static boolean canBeUsed(AccessibleObject _field, ContextEl _conf) {
        if (_field instanceof Member) {
            if (Modifier.isPublic(((Member)_field).getModifiers())) {
                return true;
            }
        }
        return _conf.getAccessValue().canBeUsed(_field, _conf);
    }
    static void setAccess(AccessibleObject _field, ContextEl _conf) {
        if (_field instanceof Member) {
            if (Modifier.isPublic(((Member)_field).getModifiers())) {
                return;
            }
        }
        _conf.getAccessValue().setAccess(_field, _conf);
    }
    final void checkCorrect(ContextEl _cont, String _className,boolean _setOffset, int _offset) {
        StringMap<StringList> map_;
        map_ = new StringMap<StringList>();
        Classes classes_ = _cont.getClasses();
        String glClass_ = _cont.getLastPage().getGlobalClass();
        if (!isStaticBlock()) {
            for (TypeVar t: Templates.getConstraints(glClass_, classes_)) {
                map_.put(t.getName(), t.getConstraints());
            }
        }
        if (!Templates.correctClassParts(_className, map_, classes_)) {
            if (_setOffset) {
                setRelativeOffsetPossibleLastPage(_offset, _cont);
            }
            throw new RuntimeClassNotFoundException(_className+RETURN_LINE+_cont.joinPages());
        }
    }
    final void checkExistBase(ContextEl _cont, boolean _allowVarTypes, String _className,boolean _setOffset, int _offset) {
        StringMap<StringList> map_;
        map_ = new StringMap<StringList>();
        if (_allowVarTypes) {
            Classes classes_ = _cont.getClasses();
            String glClass_ = _cont.getLastPage().getGlobalClass();
            for (TypeVar t: Templates.getConstraints(glClass_, classes_)) {
                map_.put(t.getName(), t.getConstraints());
            }
        }
        Classes classes_ = _cont.getClasses();
        if (!Templates.existClassParts(_className, map_, classes_)) {
            if (_setOffset) {
                setRelativeOffsetPossibleLastPage(_offset, _cont);
            }
            throw new RuntimeClassNotFoundException(_className+RETURN_LINE+_cont.joinPages());
        }
    }
    static FieldResult getDeclaredCustField(ContextEl _cont, boolean _staticContext, ClassArgumentMatching _class, boolean _superClass, String _name) {
        FieldResult resIns_ = getDeclaredCustFieldByContext(_cont, false, _class, _superClass, _name);
        FieldResult resSt_ = getDeclaredCustFieldByContext(_cont, true, _class, _superClass, _name);
        if (resIns_.getStatus() == SearchingMemberStatus.UNIQ) {
            if (!_staticContext) {
                return resIns_;
            }
            throw new StaticAccessException(_cont.joinPages());
        }
        return resSt_;
    }
    static FieldResult getDeclaredCustFieldByContext(ContextEl _cont, boolean _static, ClassArgumentMatching _class, boolean _superClass, String _name) {
        String clCurName_ = _class.getName();
        String base_ = StringList.getAllTypes(clCurName_).first();
        Classes classes_ = _cont.getClasses();
        RootBlock root_ = classes_.getClassBody(base_);
        StringList classeNames_ = new StringList();
        classeNames_.add(root_.getFullName());
        classeNames_.addAllElts(root_.getAllSuperClasses());
        for (String s: classeNames_) {
            if (StringList.quickEq(s, Object.class.getName())) {
                continue;
            }
            String formatted_ = Templates.getFullTypeByBases(clCurName_, s, classes_);
            ClassMetaInfo custClass_;
            custClass_ = classes_.getClassMetaInfo(s);
            for (EntryCust<String, FieldMetaInfo> e: custClass_.getFields().entryList()) {
                if (!StringList.quickEq(e.getKey(), _name)) {
                    continue;
                }
                if (_static) {
                    if (!e.getValue().isStaticField()) {
                        break;
                    }
                } else {
                    if (e.getValue().isStaticField()) {
                        break;
                    }
                }
                FieldResult r_ = new FieldResult();
                String formattedType_ = e.getValue().getType();
                String realType_ = formattedType_;
                formattedType_ = Templates.generalFormat(formatted_, formattedType_, classes_);
                FieldInfo f_ = new FieldInfo(_name, formatted_, formattedType_, realType_, _static, e.getValue().isFinalField());
                r_.setId(f_);
                r_.setStatus(SearchingMemberStatus.UNIQ);
                return r_;
            }
            if (!_superClass) {
                FieldResult r_ = new FieldResult();
                r_.setStatus(SearchingMemberStatus.ZERO);
                return r_;
            }
        }
        FieldResult r_ = new FieldResult();
        r_.setStatus(SearchingMemberStatus.ZERO);
        return r_;
    }
    static Field getDeclaredField(ContextEl _cont,ClassArgumentMatching _class, String _name) {
        Class<?> class_ = _class.getClazz();
        StringList traces_ = new StringList();
        while (class_ != null) {
            try {
                return class_.getDeclaredField(_name);
            } catch (NoSuchFieldException _0) {
                String trace_ = class_.getName()+DOT+_name;
                traces_.add(trace_);
            }
            class_ = class_.getSuperclass();
        }
        throw new NoSuchDeclaredFieldException(traces_.join(RETURN_TAB)+RETURN_LINE+_cont.joinPages());
    }
    static ConstrustorIdVarArg getDeclaredCustConstructor(ContextEl _conf, int _varargOnly, ClassArgumentMatching _class, ClassArgumentMatching..._args) {
        Classes classes_ = _conf.getClasses();
        if (classes_ == null) {
            return null;
        }
        ClassMetaInfo custClass_ = null;
        String clCurName_ = _class.getName();
        custClass_ = classes_.getClassMetaInfo(clCurName_);
        if (custClass_ == null) {
            return null;
        }
        String glClass_ = _conf.getLastPage().getGlobalClass();
        CustList<ConstructorId> possibleMethods_ = new CustList<ConstructorId>();
        for (ClassArgumentMatching c:_args) {
            if (c.matchVoid()) {
                throw new VoidArgumentException(clCurName_+RETURN_LINE+_conf.joinPages());
            }
        }
        ObjectNotNullMap<ConstructorId, ConstructorMetaInfo> constructors_;
        constructors_ = custClass_.getConstructors();
        if (constructors_.isEmpty()) {
            if (_args.length == 0) {
                ConstrustorIdVarArg out_;
                out_ = new ConstrustorIdVarArg();
                out_.setRealId(new ConstructorId(clCurName_, new EqList<ClassName>()));
                out_.setConstId(out_.getRealId());
                return out_;
            }
        }
        for (EntryCust<ConstructorId, ConstructorMetaInfo> e: constructors_.entryList()) {
            ConstructorId ctor_ = e.getKey();
            if (_varargOnly > -1) {
                if (!ctor_.isVararg()) {
                    continue;
                }
            }
            ClassMatching[] p_ = getParameters(ctor_);
            if (!isPossibleMethod(_conf, clCurName_, _varargOnly, ctor_.isVararg(), p_, _args)) {
                continue;
            }
            possibleMethods_.add(ctor_);
        }
        if (possibleMethods_.isEmpty()) {
            String trace_ = clCurName_+PAR_LEFT;
            StringList classesNames_ = new StringList();
            for (ClassArgumentMatching c: _args) {
                classesNames_.add(c.getName());
            }
            trace_ += classesNames_.join(SEP_ARG);
            trace_ += PAR_RIGHT;
            throw new NoSuchDeclaredConstructorException(trace_+RETURN_LINE+_conf.joinPages());
        }
        if (possibleMethods_.size() == CustList.ONE_ELEMENT) {
            ConstructorId ctor_ = possibleMethods_.first();
            ConstrustorIdVarArg out_;
            out_ = new ConstrustorIdVarArg();
            if (ctor_.isVararg() && _varargOnly == -1) {
                if (varArgWrap(classes_, glClass_, clCurName_, ctor_, _args)) {
                    out_.setVarArgToCall(true);
                }
            }
            out_.setRealId(ctor_);
            out_.setConstId(ctor_.format(clCurName_, classes_));
            return out_;
        }
        CustList<ConstructorId> accessible_ = new CustList<ConstructorId>();
        String curClassBase_ = StringList.getAllTypes(glClass_).first();
        for (ConstructorId i: possibleMethods_) {
            CustList<ConstructorBlock> ctors_ = _conf.getClasses().getConstructorBodiesById(clCurName_, i);
            if (_conf.getClasses().canAccess(curClassBase_, ctors_.first())) {
                accessible_.add(i);
            }
        }
        possibleMethods_ = accessible_;
        if (possibleMethods_.isEmpty()) {
            String trace_ = clCurName_+PAR_LEFT;
            StringList classesNames_ = new StringList();
            for (ClassArgumentMatching c: _args) {
                classesNames_.add(c.getName());
            }
            trace_ += classesNames_.join(SEP_ARG);
            trace_ += PAR_RIGHT;
            throw new NoSuchDeclaredConstructorException(trace_+RETURN_LINE+_conf.joinPages());
        }
        StringMap<StringList> map_;
        map_ = new StringMap<StringList>();
        if (glClass_ != null) {
            for (TypeVar t: Templates.getConstraints(glClass_, classes_)) {
                map_.put(t.getName(), t.getConstraints());
            }
        }
        ArgumentsGroup gr_ = new ArgumentsGroup(classes_, map_, _args);
        gr_.setGlobalClass(glClass_);
        Parametrables<ConstructorInfo> signatures_ = new Parametrables<ConstructorInfo>();
        for (ConstructorId m: possibleMethods_) {
            ParametersGroup p_ = new ParametersGroup();
            for (String c: m.getParametersTypes()) {
                p_.add(new ClassMatching(c));
            }
            ConstructorInfo mloc_ = new ConstructorInfo();
            ConstructorBlock ctr_ = classes_.getConstructorBodiesById(clCurName_, m).first();
            mloc_.setConstr(ctr_.getGenericId());
            mloc_.setConstraints(m);
            mloc_.setParameters(p_);
            mloc_.setClassName(clCurName_);
            signatures_.add(mloc_);
        }
        sortCtors(signatures_, gr_);
        if (gr_.isAmbigous()) {
            String trace_ = clCurName_+PAR_LEFT;
            StringList classesNames_ = new StringList();
            for (ClassArgumentMatching c: _args) {
                classesNames_.add(c.getName());
            }
            trace_ += classesNames_.join(SEP_ARG);
            trace_ += PAR_RIGHT;
            throw new NoSuchDeclaredConstructorException(trace_+RETURN_LINE+_conf.joinPages());
        }
        ConstructorId ctor_ = signatures_.first().getConstraints();
        ConstrustorIdVarArg out_;
        out_ = new ConstrustorIdVarArg();
        if (ctor_.isVararg() && _varargOnly == -1) {
            if (varArgWrap(classes_, glClass_, clCurName_, ctor_, _args)) {
                out_.setVarArgToCall(true);
            }
        }
        out_.setRealId(ctor_);
        out_.setConstId(ctor_.format(clCurName_, classes_));
        return out_;
    }
    static Constructor<?> getDeclaredConstructor(ContextEl _conf, int _varargOnly, int _offsetIncr, ClassArgumentMatching _class, ClassArgumentMatching..._args) {
        String className_ = _class.getName();
        for (ClassArgumentMatching c:_args) {
            if (c.matchVoid()) {
                throw new VoidArgumentException(className_+RETURN_LINE+_conf.joinPages());
            }
        }
        Classes classes_ = _conf.getClasses();
        CustList<Constructor<?>> possibleConstructors_ = new CustList<Constructor<?>>();
        for (Constructor<?> m: _class.getClazz().getDeclaredConstructors()) {
            if (_varargOnly > -1) {
                if (!m.isVarArgs()) {
                    continue;
                }
            }
            Class<?>[] params_ = m.getParameterTypes();
            int nbParams_ = m.getTypeParameters().length;
            ClassMatching[] p_ = new ClassMatching[params_.length];
            int i_ = CustList.FIRST_INDEX;
            for (Class<?> c: params_) {
                Type type_ = m.getGenericParameterTypes()[i_];
                String pre_ = NativeTypeUtil.getFormattedType(c.getName(), type_.toString(), nbParams_, type_);
                p_[i_] = new ClassMatching(pre_);
                i_++;
            }
            if (!isPossibleMethod(_conf, className_, _varargOnly, m.isVarArgs(), p_, _args)) {
                continue;
            }
            possibleConstructors_.add(m);
        }
        if (possibleConstructors_.isEmpty()) {
            _conf.getLastPage().addToOffset(_offsetIncr);
            throw new NoSuchDeclaredConstructorException(className_+RETURN_LINE+_conf.joinPages());
        }
        if (possibleConstructors_.size() == CustList.ONE_ELEMENT) {
            return possibleConstructors_.first();
        }
        StringMap<StringList> map_;
        map_ = new StringMap<StringList>();
        String glClass_ = _conf.getLastPage().getGlobalClass();
        if (glClass_ != null) {
            for (TypeVar t: Templates.getConstraints(glClass_, classes_)) {
                map_.put(t.getName(), t.getConstraints());
            }
        }
        ArgumentsGroup gr_ = new ArgumentsGroup(classes_, map_, _args);
        gr_.setGlobalClass(glClass_);
        Parametrables<ConstructorInfo> signatures_ = new Parametrables<ConstructorInfo>();
        for (Constructor<?> m: possibleConstructors_) {
            ParametersGroup p_ = new ParametersGroup();
            int nbParams_ = m.getTypeParameters().length;
            int i_ = CustList.FIRST_INDEX;
            for (Class<?> c: m.getParameterTypes()) {
                Type type_ = m.getGenericParameterTypes()[i_];
                String pre_ = NativeTypeUtil.getFormattedType(c.getName(), type_.toString(), nbParams_, type_);
                p_.add(new ClassMatching(pre_));
                i_++;
            }
            ConstructorInfo mloc_ = new ConstructorInfo();
            mloc_.setClassName(className_);
            mloc_.setMethod(m);
            mloc_.setParameters(p_);
            signatures_.add(mloc_);
        }
        //signatures_.size() >= 2
        sortCtors(signatures_, gr_);
        if (gr_.isAmbigous()) {
            _conf.getLastPage().addToOffset(_offsetIncr);
            throw new NoSuchDeclaredConstructorException(className_+RETURN_LINE+_conf.joinPages());
        }
        return signatures_.first().getMethod();
    }
    static ClassMethodIdReturn getDeclaredCustMethod(boolean _failIfError, ContextEl _conf, int _varargOnly, boolean _staticContext, ClassArgumentMatching _class, String _name, boolean _superClass, ClassArgumentMatching... _argsClass) {
        Classes classes_ = _conf.getClasses();
        String clCurName_ = _class.getName();
        String baseClass_ = StringList.getAllTypes(clCurName_).first();
        for (ClassArgumentMatching c:_argsClass) {
            if (c.matchVoid()) {
                throw new VoidArgumentException(clCurName_+DOT+_name+RETURN_LINE+_conf.joinPages());
            }
        }
        if (classes_.getClassBody(baseClass_) instanceof InterfaceBlock) {
            ClassMethodIdResult resInst_ = getDeclaredCustMethodByInterfaceInherit(_conf, _varargOnly, false, _class, _name, _superClass, _argsClass);
            boolean foundInst_ = false;
            if (!_staticContext) {
                if (resInst_.getStatus() == SearchingMemberStatus.UNIQ) {
                    foundInst_ = true;
                }
            }
            ClassMethodIdResult resStatic_ = getDeclaredCustMethodByInterfaceInherit(_conf, _varargOnly, true, _class, _name, _superClass, _argsClass);
            if (foundInst_) {
                return toFoundMethod(_conf, resInst_);
            }
            if (!_staticContext && _conf.isAmbigous() && _failIfError) {
                String trace_ = clCurName_+DOT+_name+PAR_LEFT;
                StringList classesNames_ = new StringList();
                for (ClassArgumentMatching c: _argsClass) {
                    classesNames_.add(c.getName());
                }
                trace_ += classesNames_.join(SEP_ARG);
                trace_ += PAR_RIGHT;
                throw new NoSuchDeclaredMethodException(trace_+RETURN_LINE+_conf.joinPages());
            }
            if (resStatic_.getStatus() == SearchingMemberStatus.UNIQ) {
                return toFoundMethod(_conf, resStatic_);
            }
            if (_staticContext && resInst_.getStatus() == SearchingMemberStatus.UNIQ && _failIfError) {
                //static access
                throw new StaticAccessException(_conf.joinPages());
            }
            String trace_ = clCurName_+DOT+_name+PAR_LEFT;
            StringList classesNames_ = new StringList();
            for (ClassArgumentMatching c: _argsClass) {
                classesNames_.add(c.getName());
            }
            trace_ += classesNames_.join(SEP_ARG);
            trace_ += PAR_RIGHT;
            throw new NoSuchDeclaredMethodException(trace_+RETURN_LINE+_conf.joinPages());
        }
        ClassMethodIdResult resInst_ = getDeclaredCustMethodByClassInherit(_conf, _varargOnly, false, _class, _name, _superClass, _argsClass);
        boolean foundInst_ = false;
        if (!_staticContext) {
            if (resInst_.getStatus() == SearchingMemberStatus.UNIQ) {
                foundInst_ = true;
            }
        }
        ClassMethodIdResult resStatic_ = getDeclaredCustMethodByClassInherit(_conf, _varargOnly, true, _class, _name, _superClass, _argsClass);
        if (foundInst_) {
            return toFoundMethod(_conf, resInst_);
        }
        if (!_staticContext && _conf.isAmbigous()) {
            clCurName_ = _class.getName();
            String trace_ = clCurName_+DOT+_name+PAR_LEFT;
            StringList classesNames_ = new StringList();
            for (ClassArgumentMatching c: _argsClass) {
                classesNames_.add(c.getName());
            }
            trace_ += classesNames_.join(SEP_ARG);
            trace_ += PAR_RIGHT;
            throw new NoSuchDeclaredMethodException(trace_+RETURN_LINE+_conf.joinPages());
        }
        if (resStatic_.getStatus() == SearchingMemberStatus.UNIQ) {
            return toFoundMethod(_conf, resStatic_);
        }
        if (!_failIfError) {
            return new ClassMethodIdReturn(false);
        }
        if (resInst_.getStatus() == SearchingMemberStatus.UNIQ) {
            //static access
            throw new StaticAccessException(_conf.joinPages());
        }
        String trace_ = clCurName_+DOT+_name+PAR_LEFT;
        StringList classesNames_ = new StringList();
        for (ClassArgumentMatching c: _argsClass) {
            classesNames_.add(c.getName());
        }
        trace_ += classesNames_.join(SEP_ARG);
        trace_ += PAR_RIGHT;
        throw new NoSuchDeclaredMethodException(trace_+RETURN_LINE+_conf.joinPages());
    }
    private static ClassMethodIdReturn toFoundMethod(ContextEl _conf, ClassMethodIdResult _res){
        Classes classes_ = _conf.getClasses();
        ClassMethodIdReturn idRet_ = new ClassMethodIdReturn(true);
        ClassMethodId idCl_ = _res.getId();
        String clCurName_ = idCl_.getClassName();
        MethodId id_ = idCl_.getConstraints();
        MethodId realId_ = _res.getRealId();
        idRet_.setRealId(realId_);
        String realClass_ = _res.getRealClass();
        idRet_.setRealClass(realClass_);
        idRet_.setId(new ClassMethodId(clCurName_, id_));
        idRet_.setVarArgToCall(_res.isVarArgToCall());
        CustList<MethodBlock> methods_ = classes_.getMethodBodiesById(realClass_, realId_);
        MethodBlock m_ = methods_.first();
        idRet_.setMethod(m_);
        String ret_ = m_.getReturnType();
        String formatted_;
        if (!Templates.correctNbParameters(clCurName_, classes_)) {
            formatted_ = clCurName_;
        } else {
            formatted_ = Templates.getFullTypeByBases(clCurName_, realClass_, classes_);
        }
        ret_ = Templates.generalFormat(formatted_, ret_, classes_);
        idRet_.setReturnType(ret_);
        idRet_.setStaticMethod(m_.isStaticMethod());
        idRet_.setAbstractMethod(m_.isAbstractMethod());
        return idRet_;
    }
    private static ClassMethodIdResult getDeclaredCustMethodByClassInherit(ContextEl _conf, int _varargOnly, boolean _static, ClassArgumentMatching _class, String _name, boolean _superClass, ClassArgumentMatching... _argsClass) {
        Classes classes_ = _conf.getClasses();
        String clCurName_ = _class.getName();
        String base_ = StringList.getAllTypes(clCurName_).first();
        RootBlock r_ = classes_.getClassBody(base_);
        StringList classeNames_ = new StringList();
        classeNames_.add(r_.getFullName());
        classeNames_.addAllElts(r_.getAllSuperClasses());
        for (String s: classeNames_) {
            if (StringList.quickEq(s, Object.class.getName())) {
                continue;
            }
            String formatted_ = Templates.getFullTypeByBases(clCurName_, s, classes_);
            ObjectNotNullMap<ClassMethodId, MethodMetaInfo> methods_;
            methods_ = getDeclaredCustMethodByType(_conf, _varargOnly, _static, clCurName_, new ClassArgumentMatching(formatted_), _name, _argsClass);
            ClassMethodIdResult res_ = getCustResult(_conf, _varargOnly, _class, methods_, _name, _argsClass);
            if (res_.getStatus() == SearchingMemberStatus.ZERO) {
                if (!_superClass) {
                    return res_;
                }
                continue;
            }
            return res_;
        }
        ClassMethodIdResult res_ = new ClassMethodIdResult();
        res_.setStatus(SearchingMemberStatus.ZERO);
        return res_;
    }
    private static ClassMethodIdResult getDeclaredCustMethodByInterfaceInherit(ContextEl _conf, int _varargOnly, boolean _static, ClassArgumentMatching _class, String _name, boolean _superClass, ClassArgumentMatching... _argsClass) {
        Classes classes_ = _conf.getClasses();
        String clCurName_ = _class.getName();
        StringList classeNames_ = getAllSuperInterfaces(_conf, _static, _class);
        for (String s: classeNames_) {
            if (StringList.quickEq(s, Object.class.getName())) {
                continue;
            }
            String formatted_ = Templates.getFullTypeByBases(clCurName_, s, classes_);
            ObjectNotNullMap<ClassMethodId, MethodMetaInfo> methods_;
            methods_ = getDeclaredCustMethodByType(_conf, _varargOnly, _static, clCurName_, new ClassArgumentMatching(formatted_), _name, _argsClass);
            ClassMethodIdResult res_ = getCustResult(_conf, _varargOnly, _class, methods_, _name, _argsClass);
            if (res_.getStatus() == SearchingMemberStatus.ZERO) {
                if (!_superClass) {
                    return res_;
                }
                continue;
            }
            return res_;
        }
        ClassMethodIdResult res_ = new ClassMethodIdResult();
        res_.setStatus(SearchingMemberStatus.ZERO);
        return res_;
    }
    private static StringList getAllSuperInterfaces(ContextEl _conf, boolean _static, ClassArgumentMatching _class) {
         Classes classes_ = _conf.getClasses();
         String clCurName_ = _class.getName();
         String baseClass_ = StringList.getAllTypes(clCurName_).first();
         InterfaceBlock intBl_ = (InterfaceBlock) classes_.getClassBody(baseClass_);
         StringList superInts_ = new StringList(baseClass_);
         if (!_static) {
             superInts_.addAllElts(intBl_.getAllSuperClasses());
         }
         return superInts_;
    }
    private static ObjectNotNullMap<ClassMethodId, MethodMetaInfo>
            getDeclaredCustMethodByType(ContextEl _conf, int _varargOnly,
            boolean _static, String _fromClass, ClassArgumentMatching _class, String _name, ClassArgumentMatching... _argsClass) {
        Classes classes_ = _conf.getClasses();
        String clCurName_ = _class.getName();
        String baseCurName_ = StringList.getAllTypes(clCurName_).first();
        RootBlock root_ = classes_.getClassBody(baseCurName_);
        ObjectNotNullMap<ClassMethodId, MethodMetaInfo> methods_;
        methods_ = new ObjectNotNullMap<ClassMethodId, MethodMetaInfo>();
        if (_static) {
            for (MethodBlock e: Classes.getMethodBlocks(root_)) {
                if (e.isStaticMethod()) {
                    MethodId id_ = e.getId();
                    String returnType_ = e.getReturnType();
                    MethodMetaInfo info_ = new MethodMetaInfo(clCurName_, id_, MethodModifier.STATIC, returnType_);
                    ClassMethodId clId_ = new ClassMethodId(clCurName_, id_);
                    methods_.put(clId_, info_);
                }
            }
        } else {
            String generic_ = root_.getGenericString();
            for (EntryCust<MethodId, EqList<ClassMethodId>> e: root_.getAllOverridingMethods().entryList()) {
                MethodId id_ = e.getKey();
                CustList<MethodBlock> methodsLoc_ = classes_.getMethodBodiesById(generic_, id_);
                if (methodsLoc_.isEmpty()) {
                    continue;
                }
                MethodBlock m_ = methodsLoc_.first();
                String returnType_ = m_.getReturnType();
                returnType_ = Templates.generalFormat(clCurName_, returnType_, classes_);
                MethodMetaInfo info_ = new MethodMetaInfo(generic_, id_, MethodModifier.NORMAL, returnType_);
                ClassMethodId clId_ = new ClassMethodId(generic_, id_);
                methods_.put(clId_, info_);
            }
        }
        if (!_static) {
            for (EntryCust<MethodId, ClassMethodId> e: root_.getDefaultMethodIds().entryList()) {
                ClassMethodId idCl_ = e.getValue();
                String cl_ = idCl_.getClassName();
                MethodId id_ = idCl_.getConstraints();
                MethodBlock m_ = classes_.getMethodBodiesById(cl_, id_).first();
                String ret_ = m_.getReturnType();
                ret_ = Templates.generalFormat(clCurName_, ret_, classes_);
                MethodMetaInfo info_ = new MethodMetaInfo(cl_, id_, MethodModifier.NORMAL, ret_);
                methods_.put(new ClassMethodId(cl_, e.getKey()), info_);
            }
        }
        return methods_;
    }
    private static ClassMethodIdResult getCustResult(ContextEl _conf, int _varargOnly, ClassArgumentMatching _class,
            ObjectNotNullMap<ClassMethodId, MethodMetaInfo> _methods,
            String _name, ClassArgumentMatching... _argsClass) {
        Classes classes_ = _conf.getClasses();
        String clCurName_ = _class.getName();
        CustList<ClassMethodId> possibleMethods_ = new CustList<ClassMethodId>();
        String glClass_ = _conf.getLastPage().getGlobalClass();
        for (EntryCust<ClassMethodId, MethodMetaInfo> e: _methods.entryList()) {
            ClassMethodId key_ = e.getKey();
            String className_ = key_.getClassName();
            MethodId id_ = key_.getConstraints();
            if (_varargOnly > -1) {
                if (!id_.isVararg()) {
                    continue;
                }
            }
            if (!StringList.quickEq(id_.getName(), _name)) {
                continue;
            }
            ClassMatching[] p_ = getParameters(e.getValue().getRealId());
            String formattedType_;
            if (!Templates.correctNbParameters(clCurName_, classes_)) {
                formattedType_ = clCurName_;
            } else {
                formattedType_ = Templates.getFullTypeByBases(clCurName_, className_, classes_);
            }
            if (!isPossibleMethod(_conf, formattedType_, _varargOnly, id_.isVararg(), p_, _argsClass)) {
                continue;
            }
            possibleMethods_.add(key_);
        }
        if (possibleMethods_.isEmpty()) {
            ClassMethodIdResult res_ = new ClassMethodIdResult();
            res_.setStatus(SearchingMemberStatus.ZERO);
            return res_;
        }
        if (possibleMethods_.size() == CustList.ONE_ELEMENT) {
            ClassMethodId methodId_ = possibleMethods_.first();
            String className_ = methodId_.getClassName();
            MethodMetaInfo info_ = _methods.getVal(methodId_);
            MethodId realId_ = methodId_.getConstraints();
            if (!Templates.correctNbParameters(clCurName_, classes_)) {
                clCurName_ = Templates.getGenericString(clCurName_, classes_);
                ClassMethodId cl_ = new ClassMethodId(clCurName_, realId_);
                ClassMethodIdResult res_ = new ClassMethodIdResult();
                res_.setStatus(SearchingMemberStatus.UNIQ);
                res_.setId(cl_);
                className_ = Templates.getFullTypeByBases(clCurName_, className_, classes_);
                if (_varargOnly == -1 && varArgWrap(classes_, glClass_, className_, info_.getRealId(), _argsClass)) {
                    res_.setVarArgToCall(true);
                }
                res_.setRealId(info_.getRealId());
                res_.setRealClass(info_.getClassName());
                return res_;
            }
            MethodId id_ = realId_.format(clCurName_, classes_);
            ClassMethodId cl_ = new ClassMethodId(clCurName_, id_);
            ClassMethodIdResult res_ = new ClassMethodIdResult();
            res_.setStatus(SearchingMemberStatus.UNIQ);
            res_.setId(cl_);
            String formattedType_ = Templates.getFullTypeByBases(clCurName_, className_, classes_);
            if (_varargOnly == -1 && varArgWrap(classes_, glClass_, formattedType_, info_.getRealId(), _argsClass)) {
                res_.setVarArgToCall(true);
            }
            res_.setRealId(info_.getRealId());
            res_.setRealClass(info_.getClassName());
            return res_;
        }
        EqList<ClassMethodId> accessible_ = new EqList<ClassMethodId>();
        for (ClassMethodId i: possibleMethods_) {
            MethodMetaInfo info_ = _methods.getVal(i);
            CustList<MethodBlock> methods_ = classes_.getMethodBodiesById(i.getClassName(), info_.getRealId());
            MethodBlock m_;
            m_ = methods_.first();
            String curClassBase_ = StringList.getAllTypes(glClass_).first();
            if (_conf.getClasses().canAccess(curClassBase_, m_)) {
                accessible_.add(i);
            }
        }
        possibleMethods_ = accessible_;
        if (possibleMethods_.isEmpty()) {
            ClassMethodIdResult res_ = new ClassMethodIdResult();
            res_.setStatus(SearchingMemberStatus.ZERO);
            return res_;
        }
        StringMap<StringList> map_;
        map_ = new StringMap<StringList>();
        if (glClass_ != null) {
            for (TypeVar t: Templates.getConstraints(glClass_, classes_)) {
                map_.put(t.getName(), t.getConstraints());
            }
        }
        ArgumentsGroup gr_ = new ArgumentsGroup(classes_, map_, _argsClass);
        gr_.setGlobalClass(glClass_);
        Parametrables<MethodInfo> signatures_ = new Parametrables<MethodInfo>();
        for (ClassMethodId m: possibleMethods_) {
            ParametersGroup p_ = new ParametersGroup();
            MethodId realId_ = _methods.getVal(m).getRealId();
            for (String c: realId_.getParametersTypes()) {
                p_.add(new ClassMatching(c));
            }
            MethodInfo mloc_ = new MethodInfo();
            String formattedType_;
            if (!Templates.correctNbParameters(clCurName_, classes_)) {
                formattedType_ = m.getClassName();
            } else {
                formattedType_ = Templates.getFullTypeByBases(clCurName_, m.getClassName(), classes_);
            }
            mloc_.setClassName(formattedType_);
            mloc_.setStatic(_methods.getVal(m).getModifier() == MethodModifier.STATIC);
            mloc_.setConstraints(realId_);
            mloc_.setParameters(p_);
            mloc_.setReturnType(_methods.getVal(m).getReturnType());
            signatures_.add(mloc_);
        }
        _conf.setAmbigous(false);
        sortFct(signatures_, gr_);
        if (gr_.isAmbigous()) {
            _conf.setAmbigous(true);
            ClassMethodIdResult res_ = new ClassMethodIdResult();
            res_.setStatus(SearchingMemberStatus.ZERO);
            return res_;
        }
        MethodId constraints_ = signatures_.first().getConstraints();
        MethodId realId_ = constraints_;
        String className_ = signatures_.first().getClassName();
        MethodMetaInfo info_ = _methods.getVal(new ClassMethodId(className_, realId_));
        if (!Templates.correctNbParameters(clCurName_, classes_)) {
            clCurName_ = Templates.getGenericString(clCurName_, classes_);
            ClassMethodId cl_ = new ClassMethodId(clCurName_, constraints_);
            ClassMethodIdResult res_ = new ClassMethodIdResult();
            res_.setStatus(SearchingMemberStatus.UNIQ);
            res_.setId(cl_);
            if (_varargOnly == -1 && varArgWrap(classes_, glClass_, clCurName_, constraints_, _argsClass)) {
                res_.setVarArgToCall(true);
            }
            res_.setRealId(constraints_);
            res_.setRealClass(info_.getClassName());
            return res_;
        }
        MethodId id_ = constraints_.format(clCurName_, classes_);
        ClassMethodId cl_ = new ClassMethodId(clCurName_, id_);
        ClassMethodIdResult res_ = new ClassMethodIdResult();
        res_.setStatus(SearchingMemberStatus.UNIQ);
        res_.setId(cl_);
        String formattedType_ = Templates.getFullTypeByBases(clCurName_, className_, classes_);
        if (_varargOnly == -1 && varArgWrap(classes_, glClass_, formattedType_, constraints_, _argsClass)) {
            res_.setVarArgToCall(true);
        }
        res_.setRealId(constraints_);
        res_.setRealClass(info_.getClassName());
        return res_;
    }
    static Method getDeclaredMethod(boolean _failIfError, ContextEl _cont, int _varargOnly, boolean _staticContext, ClassArgumentMatching _class, String _name, ClassArgumentMatching... _argsClass) {
        Class<?> class_ = _class.getClazz();
        class_ = PrimitiveTypeUtil.toBooleanWrapper(class_, true);
        for (ClassArgumentMatching c:_argsClass) {
            if (c.matchVoid()) {
                throw new VoidArgumentException(_class.getName()+DOT+_name+RETURN_LINE+_cont.joinPages());
            }
        }
        ClassMethodIdResult resInst_ = getDeclaredMethodLoop(_cont, _varargOnly, false, _class, _name, _argsClass);
        ClassMethodIdResult resStatic_ = getDeclaredMethodLoop(_cont, _varargOnly, true, _class, _name, _argsClass);
        return getFoundMethod(_failIfError, _cont, _staticContext, resInst_, resStatic_, _class, _name, _argsClass);
    }
    private static Method getFoundMethod(boolean _failIfError, ContextEl _cont, boolean _staticContext,
            ClassMethodIdResult _resInst, ClassMethodIdResult _resStatic,
            ClassArgumentMatching _class, String _name, ClassArgumentMatching... _argsClass) {
        boolean foundInst_ = false;
        if (!_staticContext) {
            if (_resInst.getStatus() == SearchingMemberStatus.UNIQ) {
                foundInst_ = true;
            }
        }
        if (foundInst_) {
            return _resInst.getMethod();
        }
        if (!_staticContext && _cont.isAmbigous() && _failIfError) {
            String clCurName_ = _class.getName();
            String trace_ = clCurName_+DOT+_name+PAR_LEFT;
            StringList classesNames_ = new StringList();
            for (ClassArgumentMatching c: _argsClass) {
                classesNames_.add(c.getName());
            }
            trace_ += classesNames_.join(SEP_ARG);
            trace_ += PAR_RIGHT;
            throw new NoSuchDeclaredMethodException(trace_+RETURN_LINE+_cont.joinPages());
        }
        if (_resStatic.getStatus() == SearchingMemberStatus.UNIQ) {
            return _resStatic.getMethod();
        }
        if (!_failIfError) {
            return null;
        }
        if (_resInst.getStatus() == SearchingMemberStatus.UNIQ) {
            //static access
            throw new StaticAccessException(_cont.joinPages());
        }
        String clCurName_ = _class.getName();
        String trace_ = clCurName_+DOT+_name+PAR_LEFT;
        StringList classesNames_ = new StringList();
        for (ClassArgumentMatching c: _argsClass) {
            classesNames_.add(c.getName());
        }
        trace_ += classesNames_.join(SEP_ARG);
        trace_ += PAR_RIGHT;
        throw new NoSuchDeclaredMethodException(trace_+RETURN_LINE+_cont.joinPages());
    }
    static ClassMethodIdResult getDeclaredMethodLoop(ContextEl _cont, int _varargOnly, boolean _static, ClassArgumentMatching _class,
            String _name, ClassArgumentMatching... _argsClass) {
        StringList classNames_ = Templates.getAllGenericSuperTypes(_class.getName(), _cont.getClasses());
        for (String c: classNames_) {
            Class<?> cl_ = PrimitiveTypeUtil.getSingleNativeClass(c);
            CustList<Method> possibleMethods_ = new CustList<Method>(cl_.getDeclaredMethods());
            ClassMethodIdResult res_ = getResult(_cont, _varargOnly, _static, c, possibleMethods_, _name, _argsClass);
            if (res_.getStatus() == SearchingMemberStatus.ZERO) {
                continue;
            }
            return res_;
        }
        ClassMethodIdResult res_ = new ClassMethodIdResult();
        res_.setStatus(SearchingMemberStatus.ZERO);
        return res_;
    }
    private static ClassMethodIdResult getResult(ContextEl _conf, int _varargOnly, boolean _static, String _class,
            CustList<Method> _methods,
            String _name, ClassArgumentMatching... _argsClass) {
        Classes classes_ = _conf.getClasses();
        CustList<Method> possibleMethods_ = new CustList<Method>();
        for (Method m: _methods) {
            if (_static) {
                if (!Modifier.isStatic(m.getModifiers())) {
                    continue;
                }
            } else {
                if (Modifier.isStatic(m.getModifiers())) {
                    continue;
                }
            }
            if (_varargOnly > -1) {
                if (!m.isVarArgs()) {
                    continue;
                }
            }
            if (!StringList.quickEq(m.getName(), _name)) {
                continue;
            }
            Class<?>[] params_ = m.getParameterTypes();
            int nbParams_ = m.getTypeParameters().length;
            ClassMatching[] p_ = new ClassMatching[params_.length];
            int i_ = CustList.FIRST_INDEX;
            for (Class<?> c: params_) {
                Type type_ = m.getGenericParameterTypes()[i_];
                String pre_ = NativeTypeUtil.getFormattedType(c.getName(), type_.toString(), nbParams_, type_);
                p_[i_] = new ClassMatching(pre_);
                i_++;
            }
            if (!isPossibleMethod(_conf, _class, _varargOnly, m.isVarArgs(), p_, _argsClass)) {
                continue;
            }
            possibleMethods_.add(m);
        }
        if (possibleMethods_.isEmpty()) {
            ClassMethodIdResult res_ = new ClassMethodIdResult();
            res_.setStatus(SearchingMemberStatus.ZERO);
            return res_;
        }
        if (possibleMethods_.size() == CustList.ONE_ELEMENT) {
            ClassMethodIdResult res_ = new ClassMethodIdResult();
            res_.setStatus(SearchingMemberStatus.UNIQ);
            res_.setMethod(possibleMethods_.first());
            return res_;
        }
        StringMap<StringList> map_;
        map_ = new StringMap<StringList>();
        String glClass_ = _conf.getLastPage().getGlobalClass();
        if (glClass_ != null) {
            for (TypeVar t: Templates.getConstraints(glClass_, classes_)) {
                map_.put(t.getName(), t.getConstraints());
            }
        }
        ArgumentsGroup gr_ = new ArgumentsGroup(classes_, map_, _argsClass);
        gr_.setGlobalClass(glClass_);
        Parametrables<MethodInfo> signatures_ = new Parametrables<MethodInfo>();
        for (Method m: possibleMethods_) {
            ParametersGroup p_ = new ParametersGroup();
            int nbParams_ = m.getTypeParameters().length;
            int i_ = CustList.FIRST_INDEX;
            for (Class<?> c: m.getParameterTypes()) {
                Type type_ = m.getGenericParameterTypes()[i_];
                String pre_ = NativeTypeUtil.getFormattedType(c.getName(), type_.toString(), nbParams_, type_);
                p_.add(new ClassMatching(pre_));
                i_++;
            }
            MethodInfo mloc_ = new MethodInfo();
            mloc_.setMethod(m);
            mloc_.setClassName(_class);
            mloc_.setStatic(Modifier.isStatic(m.getModifiers()));
            mloc_.setParameters(p_);
            Type type_ = m.getGenericReturnType();
            String pre_ = NativeTypeUtil.getFormattedType(m.getReturnType().getName(), type_.toString(), nbParams_, type_);
            mloc_.setReturnType(pre_);
            signatures_.add(mloc_);
        }
        _conf.setAmbigous(false);
        sortFct(signatures_, gr_);
        if (gr_.isAmbigous()) {
            _conf.setAmbigous(true);
            ClassMethodIdResult res_ = new ClassMethodIdResult();
            res_.setStatus(SearchingMemberStatus.ZERO);
            return res_;
        }
        ClassMethodIdResult res_ = new ClassMethodIdResult();
        res_.setStatus(SearchingMemberStatus.UNIQ);
        res_.setMethod(signatures_.first().getMethod());
        return res_;
    }
    static boolean isPossibleMethod(ContextEl _context, String _class, int _varargOnly, boolean _vararg, ClassMatching[] _params, ClassArgumentMatching..._argsClass) {
        int startOpt_ = _argsClass.length;
        boolean checkOnlyDem_ = true;
        int nbDem_ = _params.length;
        if (!_vararg) {
            if (_params.length != _argsClass.length) {
                return false;
            }
        } else {
            if (_params.length > _argsClass.length + 1) {
                return false;
            }
            if (_varargOnly != 0) {
                checkOnlyDem_ = false;
                nbDem_--;
                startOpt_ = _params.length - 1;
            }
            if (_varargOnly > 0) {
                if (startOpt_ != _varargOnly - 1) {
                    return false;
                }
            }
        }
        Classes classes_ = _context.getClasses();
        String glClass_ = _context.getLastPage().getGlobalClass();
        CustList<TypeVar> vars_;
        if (glClass_ != null) {
            vars_ = Templates.getConstraints(glClass_, classes_);
        } else {
            vars_ = new CustList<TypeVar>();
        }
        int len_ = nbDem_;
        for (int i = CustList.FIRST_INDEX; i < len_; i++) {
            if (_argsClass[i].isVariable()) {
                if (_params[i].isPrimitive()) {
                    return false;
                }
                continue;
            }
            Mapping map_ = new Mapping();
            map_.setArg(_argsClass[i].getName());
            for (TypeVar t: vars_) {
                map_.getMapping().put(t.getName(), t.getConstraints());
            }
            map_.setParam(Templates.generalFormat(_class, _params[i].getClassName(), classes_));
            if (!Templates.isCorrect(map_, classes_)) {
                return false;
            }
        }
        if (checkOnlyDem_) {
            return true;
        }
        if (_params.length == _argsClass.length) {
            int last_ = _params.length - 1;
            Mapping map_ = new Mapping();
            map_.setArg(_argsClass[last_].getName());
            for (TypeVar t: vars_) {
                map_.getMapping().put(t.getName(), t.getConstraints());
            }
            String param_ = _params[last_].getClassName();
            map_.setParam(Templates.generalFormat(_class, param_, classes_));
            if (Templates.isCorrect(map_, classes_)) {
                return true;
            }
            param_ = PrimitiveTypeUtil.getQuickComponentType(param_);
            map_.setParam(Templates.generalFormat(_class, param_, classes_));
            return Templates.isCorrect(map_, classes_);
        }
        len_ = _argsClass.length;
        Mapping map_ = new Mapping();
        int last_ = _params.length - 1;
        String param_ = _params[last_].getClassName();
        param_ = PrimitiveTypeUtil.getQuickComponentType(param_);
        for (TypeVar t: vars_) {
            map_.getMapping().put(t.getName(), t.getConstraints());
        }
        map_.setParam(Templates.generalFormat(_class, param_, classes_));
        for (int i = startOpt_; i < len_; i++) {
            map_.setArg(_argsClass[i].getName());
            if (!Templates.isCorrect(map_, classes_)) {
                return false;
            }
        }
        return true;
    }
    static boolean varArgWrap(Classes _classes, String _globalClass, String _class, Identifiable _id, ClassArgumentMatching..._argsClass) {
        if (!_id.isVararg()) {
            return false;
        }
        ClassMatching[] p_ = getParameters(_id);
        if (p_.length != _argsClass.length) {
            return true;
        }
        Classes classes_ = _classes;
        String glClass_ = _globalClass;
        CustList<TypeVar> vars_;
        if (glClass_ != null) {
            vars_ = Templates.getConstraints(glClass_, classes_);
        } else {
            vars_ = new CustList<TypeVar>();
        }
        Mapping map_ = new Mapping();
        int last_ = p_.length - 1;
        String param_ = p_[last_].getClassName();
        for (TypeVar t: vars_) {
            map_.getMapping().put(t.getName(), t.getConstraints());
        }
        map_.setArg(_argsClass[last_].getName());
        map_.setParam(Templates.generalFormat(_class, param_, classes_));
        return !Templates.isCorrect(map_, classes_);
    }
    static ClassMatching[] getParameters(Identifiable _id) {
        StringList params_ = _id.getParametersTypes();
        int nbParams_ = params_.size();
        ClassMatching[] p_ = new ClassMatching[nbParams_];
        int i_ = CustList.FIRST_INDEX;
        if (!_id.isVararg()) {
            for (String c: params_) {
                p_[i_] = new ClassMatching(c);
                i_++;
            }
        } else {
            for (String c: params_) {
                if (i_ == nbParams_ - 1) {
                    String c_ = StringList.replace(c, VARARG_SUFFIX, EMPTY_STRING);
                    c_ = PrimitiveTypeUtil.getPrettyArrayType(c_);
                    p_[i_] = new ClassMatching(c_);
                } else {
                    p_[i_] = new ClassMatching(c);
                }
                i_++;
            }
        }
        return p_;
    }
    static Argument newInstance(ContextEl _conf, Argument _need, int _offsetIncr, boolean _natvararg, Constructor<?> _const, Argument... _args) {
        Struct[] args_ = getObjects(_args);
        checkArgumentsForInvoking(_conf, _natvararg, toClassNames(_const.getParameterTypes()), args_);
        try {
            Argument a_ = new Argument();
            Object o_ = ConverterMethod.newInstance(_const, adaptedArgs(_const.getParameterTypes(), args_));
            if (_need != null) {
                a_.setStruct(new Struct(o_, _need.getStruct()));
            } else {
                a_.setStruct(new Struct(o_));
            }
            return a_;
        } catch (InvokingException _0) {
            throw new InvokeException(_conf.joinPages(), new Struct(_0.getTarget()));
        } catch (Throwable _0) {
            throw new ErrorCausingException(_conf.joinPages(), new Struct(_0));
        }
    }

    static Struct invokeMethod(ContextEl _cont,int _offsetIncr, boolean _natvararg, String _className, Method _method, Object _instance, Argument... _args) {
        Struct[] args_ = getObjects(_args);
        checkArgumentsForInvoking(_cont, _natvararg, toClassNames(_method.getParameterTypes()), args_);
        try {
            Object o_ = ConverterMethod.invokeMethod(_method, _instance, adaptedArgs(_method.getParameterTypes(), args_));
            if (o_ == null) {
                return new Struct();
            }
            if (o_ instanceof Struct) {
                return (Struct) o_;
            }
            return new Struct(o_);
        } catch (InvokingException _0) {
            throw new InvokeException(_cont.joinPages(), new Struct(_0.getTarget()));
        } catch (Throwable _0) {
            throw new ErrorCausingException(_cont.joinPages(), new Struct(_0));
        }
    }
    static void sortFct(Parametrables<MethodInfo> _fct, ArgumentsGroup _context) {
        int len_ = _fct.size();
        for (int i = CustList.SECOND_INDEX; i < len_; i++) {
            process(_fct, i, _context);
        }
        if (_fct.first().getParameters().isError()) {
            _context.setAmbigous(true);
        }
    }
    static void sortCtors(Parametrables<ConstructorInfo> _fct, ArgumentsGroup _context) {
        int len_ = _fct.size();
        for (int i = CustList.SECOND_INDEX; i < len_; i++) {
            process(_fct, i, _context);
        }
        if (_fct.first().getParameters().isError()) {
            _context.setAmbigous(true);
        }
    }
    static void process(Fcts _list, int _i, ArgumentsGroup _context) {
        Parametrable pFirst_ = _list.first();
        Parametrable pCurrent_ = _list.get(_i);
        int res_ = compare(_context, pFirst_, pCurrent_);
        if (res_ == CustList.SWAP_SORT) {
            _list.swapIndexes(CustList.FIRST_INDEX, _i);
        }
    }
    static int compare(ArgumentsGroup _context, Parametrable _o1, Parametrable _o2) {
        int len_ = _o1.getParameters().size();
        Classes classes_ = _context.getClasses();
        StringMap<StringList> map_;
        map_ = _context.getMap();
        String glClass_ = _context.getGlobalClass();
        String glClassOne_ = _o1.getClassName();
        String glClassTwo_ = _o2.getClassName();
        if (_o1.isVararg()) {
            if (!_o2.isVararg()) {
                return CustList.SWAP_SORT;
            }
        }
        if (!_o1.isVararg()) {
            if (_o2.isVararg()) {
                return CustList.NO_SWAP_SORT;
            }
        }
        if (_o1.isVararg()) {
            if (_o2.isVararg()) {
                if (len_ < _o2.getParameters().size()) {
                    return CustList.SWAP_SORT;
                }
                if (len_ > _o2.getParameters().size()) {
                    return CustList.NO_SWAP_SORT;
                }
                boolean varOne_ = varArgWrap(classes_, glClass_, glClassOne_, _o1.getId(), _context.getArgumentsArray());
                boolean varTwo_ = varArgWrap(classes_, glClass_, glClassTwo_, _o2.getId(), _context.getArgumentsArray());
                if (varOne_ && !varTwo_) {
                    return CustList.SWAP_SORT;
                }
                if (!varOne_ && varTwo_) {
                    return CustList.NO_SWAP_SORT;
                }
            }
        }
        for (int i = CustList.FIRST_INDEX; i < len_; i++) {
            ClassArgumentMatching selected_ = _context.get(i);
            ClassMatching one_ = _o1.getParameters().get(i);
            String paramOne_ = one_.getClassName();
            if (paramOne_.endsWith(VARARG_SUFFIX)) {
                paramOne_ = StringList.replace(paramOne_, VARARG_SUFFIX, EMPTY_STRING);
                paramOne_ = PrimitiveTypeUtil.getPrettyArrayType(paramOne_);
            }
            ClassMatching two_ = _o2.getParameters().get(i);
            String paramTwo_ = two_.getClassName();
            if (paramTwo_.endsWith(VARARG_SUFFIX)) {
                paramTwo_ = StringList.replace(paramTwo_, VARARG_SUFFIX, EMPTY_STRING);
                paramTwo_ = PrimitiveTypeUtil.getPrettyArrayType(paramTwo_);
            }
            one_ = new ClassMatching(Templates.generalFormat(glClassOne_, paramOne_, classes_));
            two_ = new ClassMatching(Templates.generalFormat(glClassTwo_, paramTwo_, classes_));
            if (one_.matchClass(two_)) {
                continue;
            }
            if (selected_.isVariable()) {
                if (one_.isAssignableFrom(two_, map_, classes_)) {
                    return CustList.SWAP_SORT;
                }
                if (two_.isAssignableFrom(one_, map_, classes_)) {
                    return CustList.NO_SWAP_SORT;
                }
                _o1.getParameters().setError(true);
                _o2.getParameters().setError(true);
                return CustList.NO_SWAP_SORT;
            }
            ClassMatching toPrOne_ = one_;
            ClassMatching toPrTwo_ = two_;
            boolean onePrimExcl_ = false;
            boolean twoPrimExcl_ = false;
            if (one_.isPrimitive() && !two_.isPrimitive()) {
                onePrimExcl_ = true;
            }
            if (!one_.isPrimitive() && two_.isPrimitive()) {
                twoPrimExcl_ = true;
            }
            if (selected_.isPrimitive()) {
                if (onePrimExcl_) {
                    return CustList.NO_SWAP_SORT;
                }
                if (twoPrimExcl_) {
                    return CustList.SWAP_SORT;
                }
                toPrOne_ = PrimitiveTypeUtil.toAllPrimitive(one_);
                toPrTwo_ = PrimitiveTypeUtil.toAllPrimitive(two_);
            } else {
                ClassArgumentMatching clMatch_ = PrimitiveTypeUtil.toAllPrimitive(selected_, true);
                if (clMatch_.isPrimitive()) {
                    if (onePrimExcl_) {
                        return CustList.SWAP_SORT;
                    }
                    if (twoPrimExcl_) {
                        return CustList.NO_SWAP_SORT;
                    }
                    toPrOne_ = PrimitiveTypeUtil.toAllPrimitive(one_);
                    toPrTwo_ = PrimitiveTypeUtil.toAllPrimitive(two_);
                }
            }
            if (toPrOne_.isAssignableFrom(toPrTwo_, map_, classes_)) {
                return CustList.SWAP_SORT;
            }
            if (toPrTwo_.isAssignableFrom(toPrOne_, map_, classes_)) {
                return CustList.NO_SWAP_SORT;
            }
            _o1.getParameters().setError(true);
            _o2.getParameters().setError(true);
            return CustList.NO_SWAP_SORT;
        }
        Mapping mapping_ = new Mapping();
        mapping_.setMapping(map_);
        mapping_.setArg(_o2.getReturnType());
        mapping_.setParam(_o1.getReturnType());
        if (Templates.isCorrect(mapping_, classes_)) {
            return CustList.SWAP_SORT;
        }
        mapping_.setArg(_o1.getReturnType());
        mapping_.setParam(_o2.getReturnType());
        if (Templates.isCorrect(mapping_, classes_)) {
            return CustList.NO_SWAP_SORT;
        }
        _o1.getParameters().setError(true);
        _o2.getParameters().setError(true);
        return CustList.NO_SWAP_SORT;
    }
    static void checkArgumentsForInvoking(ContextEl _cont,boolean _natvararg, StringList _params,Struct... _args) {
        int len_ = _params.size();
        if (_natvararg) {
            len_--;
        }
        StringList traces_ = new StringList();
        for (int i = 0; i < len_; i++) {
            if (PrimitiveTypeUtil.primitiveTypeNullObject(_params.get(i), _args[i])) {
                traces_.add(i+RETURN_LINE+_params.get(i)+RETURN_LINE+null);
            }
        }
        if (!traces_.isEmpty()) {
            throw new UnwrappingException(traces_.join(SEP_ARG)+RETURN_LINE+_cont.joinPages());
        }
    }
    static StringList toClassNames(Class<?>[] _params) {
        StringList params_ = new StringList();
        for (Class<?> c: _params) {
            if (c.isPrimitive()) {
                params_.add(PrimitiveTypeUtil.PRIM+c.getName());
            } else {
                params_.add(PrimitiveTypeUtil.getAliasArrayClass(c));
            }
        }
        return params_;
    }
    static Object[] adaptedArgs(Class<?>[] _params,Struct... _args) {
        int len_ = _params.length;
        Object[] args_ = new Object[len_];
        for (int i = 0; i < len_; i++) {
            Struct argStruct_ = _args[i];
            if (argStruct_.isNull()) {
                continue;
            }
            if (!argStruct_.isJavaObject()) {
                args_[i] = argStruct_;
                continue;
            }
            Object a_ = argStruct_.getInstance();
            Class<?> p_ = _params[i];
            if (p_ == double.class || p_ == Double.class) {
                if (a_ instanceof Number) {
                    args_[i] = ((Number)a_).doubleValue();
                }
            } else if (p_ == float.class || p_ == Float.class) {
                if (a_ instanceof Number) {
                    args_[i] = ((Number)a_).floatValue();
                }
            } else if (p_ == long.class || p_ == Long.class) {
                if (a_ instanceof Number) {
                    args_[i] = ((Number)a_).longValue();
                }
            } else if (p_ == int.class || p_ == Integer.class) {
                if (a_ instanceof Number) {
                    args_[i] = ((Number)a_).intValue();
                }
            } else if (p_ == short.class || p_ == Short.class) {
                if (a_ instanceof Number) {
                    args_[i] = ((Number)a_).shortValue();
                }
            } else if (p_ == byte.class || p_ == Byte.class) {
                if (a_ instanceof Number) {
                    args_[i] = ((Number)a_).byteValue();
                }
            } else {
                args_[i] = a_;
            }
        }
        return args_;
    }

    static Struct[] getObjects(Argument... _args) {
        int len_ = _args.length;
        Struct[] classes_ = new Struct[len_];
        for (int i = CustList.FIRST_INDEX; i < len_; i++) {
            classes_[i] = _args[i].getStruct();
        }
        return classes_;
    }

    final void setNextSiblingsArg(Argument _arg, ContextEl _cont) {
        int res_ = processBooleanValues(_arg, _cont);
        if (res_ == 0) {
            return;
        }
        MethodOperation par_ = getParent();
        Object o_ = _arg.getObject();
        Boolean b_ = (Boolean) o_;
        if (res_ < 3) {
            CustList<OperationNode> l_ = ElUtil.getDirectChildren(par_);
            OperationNode opElt_ = (OperationNode) l_.get(res_);
            opElt_.setSimpleArgument(_arg);
            return;
        }
        QuickOperation q_ = (QuickOperation) par_;
        if (b_ == q_.absorbingValue()) {
            CustList<OperationNode> opers_ = new CustList<OperationNode>();
            for (OperationNode s: ElUtil.getDirectChildren(par_)) {
                opers_.add(s);
            }
            int len_ = opers_.size();
            for (int i = getIndexChild() + 1; i < len_; i++) {
                opers_.get(i).setSimpleArgument(_arg);
            }
        }
    }

    final void setNextSiblingsArg(Argument _arg, ContextEl _cont, IdMap<OperationNode, ArgumentsPair> _nodes) {
        int res_ = processBooleanValues(_arg, _cont);
        if (res_ == 0) {
            return;
        }
        Object o_ = _arg.getObject();
        MethodOperation par_ = getParent();
        Boolean b_ = (Boolean) o_;
        if (res_ < 3) {
            CustList<OperationNode> l_ = ElUtil.getDirectChildren(par_);
            OperationNode opElt_ = (OperationNode) l_.get(res_);
            _nodes.getVal(opElt_).setArgument(_arg);
            return;
        }
        QuickOperation q_ = (QuickOperation) par_;
        if (b_ == q_.absorbingValue()) {
            CustList<OperationNode> opers_ = new CustList<OperationNode>();
            for (OperationNode s: ElUtil.getDirectChildren(par_)) {
                opers_.add(s);
            }
            int len_ = opers_.size();
            for (int i = getIndexChild() + 1; i < len_; i++) {
                _nodes.getVal(opers_.get(i)).setArgument(_arg);
            }
        }
    }

    final int processBooleanValues(Argument _arg, ContextEl _cont) {
        Object o_ = _arg.getObject();
        MethodOperation par_ = getParent();
        if (o_ == null) {
            if (par_ instanceof QuickOperation) {
                setRelativeOffsetPossibleLastPage(getIndexInEl(), _cont);
                throw new NullObjectException(_cont.joinPages());
            }
            return 0;
        }
        if (!(o_ instanceof Boolean)) {
            return 0;
        }
        if (!(par_ instanceof QuickOperation)) {
            boolean ternaryParent_ = false;
            if (par_ instanceof FctOperation) {
                FctOperation op_ = (FctOperation) par_;
                ternaryParent_ = op_.isTernary();
            }
            if (!ternaryParent_) {
                return 0;
            }
            Boolean b_ = (Boolean) o_;
            if (b_) {
                return 2;
            }
            return 1;
        }
        return 3;
    }

    public final MethodOperation getParent() {
        return parent;
    }

    public final OperationsSequence getOperations() {
        return operations;
    }

    public final int getOrder() {
        return order;
    }

    public final void setOrder(int _order) {
        order = _order;
    }

    public final void setConf(ContextEl _conf) {
        conf = _conf;
    }

    public final ContextEl getConf() {
        return conf;
    }

    public final boolean isVararg() {
        return vararg;
    }

    final void setVararg(boolean _vararg) {
        vararg = _vararg;
    }

    public final boolean isFirstOptArg() {
        return firstOptArg;
    }

    final void setFirstOptArg(boolean _firstOptArg) {
        firstOptArg = _firstOptArg;
    }

    public final int getFullIndexInEl() {
        String meth_ = getOperations().getFctName();
        int off_ = StringList.getFirstPrintableCharIndex(meth_);
        return off_+operations.getDelimiter().getIndexBegin()+indexInEl;
    }

    public final int getIndexInEl() {
        return indexInEl;
    }

    public final int getIndexChild() {
        return indexChild;
    }

    public final boolean isStaticAccess() {
        return staticAccess;
    }

    public final Argument getPreviousArgument() {
        return previousArgument;
    }

    public final void setPreviousArgument(Argument _previousArgument) {
        previousArgument = _previousArgument;
    }

    public final Argument getArgument() {
        return argument;
    }

    public final void setSimpleArgument(Argument _argument) {
        argument = _argument;
    }

    public final void setArguments(Argument _argument) {
        argument = _argument;
        OperationNode n_ = getSiblingToSet();
        if (n_ == null) {
            return;
        }
        n_.setPreviousArgument(_argument);
    }

    public final void setSimpleArgument(Argument _argument, ContextEl _conf, IdMap<OperationNode, ArgumentsPair> _nodes) {
        OperationNode n_ = getSiblingToSet();
        if (n_ != null) {
            _nodes.getVal(n_).setPreviousArgument(_argument);
        }
        setNextSiblingsArg(_argument, _conf, _nodes);
    }

    public final void setSimpleArgument(Argument _argument, ContextEl _conf) {
        argument = _argument;
        OperationNode n_ = getSiblingToSet();
        if (n_ != null) {
            n_.setPreviousArgument(_argument);
        }
        setNextSiblingsArg(_argument, _conf);
    }

    public final ClassArgumentMatching getPreviousResultClass() {
        return previousResultClass;
    }

    public final void setPreviousResultClass(ClassArgumentMatching _previousResultClass) {
        setPreviousResultClass(_previousResultClass, false);
    }

    public final void setPreviousResultClass(ClassArgumentMatching _previousResultClass, boolean _staticAccess) {
        previousResultClass = _previousResultClass;
        staticAccess = _staticAccess;
    }

    public final boolean isStaticBlock() {
        return staticBlock;
    }

    public final void setStaticBlock(boolean _staticBlock) {
        staticBlock = _staticBlock;
    }

    public final boolean isVoidArg() {
        return StringList.quickEq(resultClass.getName(), OperationNode.VOID_RETURN);
    }

    public final ClassArgumentMatching getResultClass() {
        return resultClass;
    }

    public final void setResultClass(ClassArgumentMatching _resultClass) {
        resultClass = _resultClass;
        OperationNode n_ = getSiblingToSet();
        if (n_ == null) {
            return;
        }
        n_.setPreviousResultClass(resultClass);
    }

    public final void setResultClass(ClassArgumentMatching _resultClass, boolean _staticPrevious) {
        resultClass = _resultClass;
        OperationNode n_ = getSiblingToSet();
        if (n_ == null) {
            return;
        }
        n_.setPreviousResultClass(resultClass, _staticPrevious);
    }

    final OperationNode getSiblingToSet() {
        OperationNode n_ = getNextSibling();
        if (n_ == null) {
            return null;
        }
        if (!(getParent() instanceof DotOperation)) {
            return null;
        }
        if (n_ instanceof ArrOperation) {
            return n_.getFirstChild();
        }
        return n_;
    }

    protected final OperationNode getPreviousSibling() {
        return previousSibling;
    }

    public final boolean isNeedGlobalArgument() {
        return needGlobalArgument;
    }

    public final void needGlobalArgument() {
        if (isIntermediateDotted()) {
            return;
        }
        needGlobalArgument = true;
    }
}
