package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.*;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.ImportedField;
import code.expressionlanguage.analyze.ImportedMethod;
import code.expressionlanguage.analyze.MethodHeaderInfo;
import code.expressionlanguage.analyze.accessing.Accessed;
import code.expressionlanguage.analyze.blocks.*;
import code.expressionlanguage.analyze.inherits.AnaInherits;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.analyze.types.AnaTypeUtil;
import code.expressionlanguage.analyze.util.*;
import code.expressionlanguage.analyze.inherits.AnaTemplates;
import code.expressionlanguage.analyze.opers.util.*;
import code.expressionlanguage.analyze.variables.AnaLocalVariable;
import code.expressionlanguage.common.*;
import code.expressionlanguage.analyze.inherits.Mapping;
import code.expressionlanguage.functionid.*;
import code.expressionlanguage.common.ConstType;
import code.expressionlanguage.analyze.instr.ElResolver;
import code.expressionlanguage.analyze.instr.ElUtil;
import code.expressionlanguage.analyze.instr.OperationsSequence;
import code.expressionlanguage.fwd.opers.AnaOperationContent;
import code.expressionlanguage.options.KeyWords;
import code.expressionlanguage.analyze.types.ResolvingImportTypes;
import code.expressionlanguage.stds.StandardConstructor;
import code.expressionlanguage.stds.StandardMethod;
import code.expressionlanguage.stds.StandardType;
import code.util.*;
import code.util.core.IndexConstants;
import code.util.core.SortConstants;
import code.util.core.StringUtil;

public abstract class OperationNode {

    protected static final char PAR_LEFT = '(';
    protected static final char PAR_RIGHT = ')';

    protected static final String ARR = "[";

    protected static final String NEG_BOOL = "!";
    protected static final String NEG_BOOL_BIN = "~";

    protected static final String MULT = "*";

    protected static final String DIV = "/";

    protected static final String PLUS = "+";

    protected static final String MINUS = "-";

    protected static final String SHIFT_LEFT = "<<";
    protected static final String SHIFT_RIGHT = ">>";
    protected static final String BIT_SHIFT_LEFT = "<<<";
    protected static final String BIT_SHIFT_RIGHT = ">>>";
    protected static final String ROTATE_LEFT = "<<<<";

    protected static final String AFF = "=";

    protected static final String DIFF = "!=";

    protected static final String EMPTY_STRING = "";

    protected static final String VARARG_SUFFIX = "...";
    protected static final String AROBASE = "@";
    protected static final int BOOLEAN_ARGS = 3;

    private final MethodOperation parent;

    private OperationNode nextSibling;

    private final AnaOperationContent content;
//    private Argument argument;

    private final OperationsSequence operations;

    private final StringList errs = new StringList();
    private final StringList warns = new StringList();

    private int indexInExp = -1;
//    private int indexInEl;

//    private int order = CustList.INDEX_NOT_FOUND_ELT;

//    private final int indexChild;

//    private AnaClassArgumentMatching resultClass;

    OperationNode(int _indexInEl, int _indexChild, MethodOperation _m, OperationsSequence _op) {
        content = new AnaOperationContent(_indexInEl,_indexChild);
        parent = _m;
//        indexInEl = _indexInEl;
        operations = _op;
//        indexChild = _indexChild;
//        resultClass = new AnaClassArgumentMatching(EMPTY_STRING);
    }

    public static AnaClassArgumentMatching[] getResultsFromArgs(CustList<OperationNode> _args) {
        int len_ = _args.size();
        AnaClassArgumentMatching[] args_;
        args_ = new AnaClassArgumentMatching[len_];
        for (int i = IndexConstants.FIRST_INDEX; i < len_; i++) {
            args_[i] = _args.get(i).getResultClass();
        }
        return args_;
    }

    public static AnaClassArgumentMatching[] toArgArray(CustList<AnaClassArgumentMatching> _args) {
        int len_ = _args.size();
        AnaClassArgumentMatching[] args_;
        args_ = new AnaClassArgumentMatching[len_];
        for (int i = IndexConstants.FIRST_INDEX; i < len_; i++) {
            args_[i] = _args.get(i);
        }
        return args_;
    }

    public abstract void analyze(AnalyzedPageEl _page);

    static boolean isNotChildOfCallDyn(MethodOperation _m) {
        return isNotChildOfCall(_m) || _m instanceof CallDynMethodOperation;
    }

    static boolean isNotChildOfCall(MethodOperation _m) {
        return _m == null ||!_m.isCallMethodCtor();
    }
    final boolean isCallMethodCtor(){
        if (!(this instanceof InvokingOperation)) {
            return false;
        }
        if (this instanceof AbstractArrayInstancingOperation) {
            return false;
        }
        return !(this instanceof AnnotationInstanceOperation);
    }

    protected static boolean isLeftValue(SettableElResult _ch) {
        return _ch instanceof OperationNode && (!(_ch instanceof AbstractCallLeftOperation)
                ||isLeftValueCall((OperationNode)_ch));
    }

    protected static boolean isLeftValueCall(OperationNode _ch) {
        return _ch instanceof AbstractCallLeftOperation && !((AbstractCallLeftOperation) _ch).isErrLeftValue();
    }

    protected final boolean isLvalue() {
        return isSettable();
    }
    protected final boolean isSettable() {
        OperationNode c_ = this;
        OperationNode p_ = getParent();
        if (p_ instanceof AbstractDotOperation) {
            if (getIndexChild() == 0) {
                return false;
            }
            p_ = p_.getParent();
            c_ = c_.getParent();
        }
        while (p_ instanceof IdOperation && p_.getOperations().getValues().size() <= 1) {
            p_ = p_.getParent();
            c_ = c_.getParent();
        }
        if (c_.getIndexChild() > 0) {
            return false;
        }
        return isParentSetter(p_);
    }

    protected static boolean isParentSetter(OperationNode _p) {
        if (_p instanceof WrappOperation) {
            return true;
        }
        if (_p instanceof AffectationOperation) {
            return true;
        }
        if (_p instanceof SemiAffectationOperation) {
            return true;
        }
        return _p instanceof CompoundAffectationOperation;
    }

    public final void setRelativeOffsetPossibleAnalyzable(int _offset, AnalyzedPageEl _page) {
        _page.setOffset(_offset);
    }

    public static OperationNode createOperationNode(int _index,
                                                    int _indexChild, MethodOperation _m, OperationsSequence _op, AnalyzedPageEl _page) {
        OperationNode res_ = createOperationNodeBis(_index, _indexChild, _m, _op, _page);
        if (res_ instanceof MethodOperation) {
            ((MethodOperation)res_).calculateChildren();
        }
        if (_m instanceof AbstractDotOperation&&_m.getFirstChild() != null && !(res_ instanceof PossibleIntermediateDotted)) {
            return new ErrorPartOperation(_index, _indexChild, _m, _op);
        }
        return res_;
    }
    private static OperationNode createOperationNodeBis(int _index,
                                                        int _indexChild, MethodOperation _m, OperationsSequence _op, AnalyzedPageEl _page) {
        KeyWords keyWords_ = _page.getKeyWords();
        String keyWordBool_ = keyWords_.getKeyWordBool();
        String keyWordClasschoice_ = keyWords_.getKeyWordClasschoice();
        String keyWordDefault_ = keyWords_.getKeyWordDefault();
        String keyWordFirstopt_ = keyWords_.getKeyWordFirstopt();
        String keyWordInterfaces_ = keyWords_.getKeyWordInterfaces();
        String keyWordOperator_ = keyWords_.getKeyWordOperator();
        String keyWordSuper_ = keyWords_.getKeyWordSuper();
        String keyWordSuperaccess_ = keyWords_.getKeyWordSuperaccess();
        String keyWordThis_ = keyWords_.getKeyWordThis();
        String keyWordThat_ = keyWords_.getKeyWordThat();
        String keyWordValueOf_ = keyWords_.getKeyWordValueOf();
        ConstType ct_ = _op.getConstType();
        if (ct_ == ConstType.ERROR) {
            return new ErrorPartOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getOperators().isEmpty()) {
            return createLeaf(_index, _indexChild, _m, _op, _page);
        }
        if (_op.getPriority() == ElResolver.NAME_PRIO) {
            return new NamedArgumentOperation(_index, _indexChild, _m, _op);
        }
        if (_m == null) {
            if (_op.getPriority() == ElResolver.DECL_PRIO) {
                return new DeclaringOperation(_index, _indexChild, null, _op);
            }
        }
        if (_op.getPriority() == ElResolver.FCT_OPER_PRIO) {
            if (_page.getAnnotationAnalysis().isAnnotAnalysis(_m,_op)) {
                return new AnnotationInstanceArrOperation(_index, _indexChild, _m, _op);
            }
            String fctName_ = _op.getFctName().trim();
            if (fctName_.startsWith(AROBASE)) {
                return new AnnotationInstanceArobaseOperation(_index, _indexChild, _m, _op);
            }
        }
        int ternary_ = 0;
        if (_op.getPriority() == ElResolver.TERNARY_PRIO) {
            ternary_ = _op.getValues().size();
        } else if (_op.getPriority() == ElResolver.FCT_OPER_PRIO){
            String fctName_ = _op.getFctName().trim();
            if (StringUtil.quickEq(fctName_, keyWordBool_)) {
                ternary_ = _op.getValues().size()-1;
            }
        }
        if (ternary_ == BOOLEAN_ARGS) {
            MethodOperation m_ = _m;
            int indCh_ = _indexChild;
            while (atMostOne(m_)) {
                indCh_ = m_.getIndexChild();
                m_ = m_.getParent();
            }
            if (_op.getPriority() == ElResolver.TERNARY_PRIO) {
                if (isParentSetter(m_)&&indCh_==0) {
                    return new RefShortTernaryOperation(_index, _indexChild, _m, _op);
                }
                return new ShortTernaryOperation(_index, _indexChild, _m, _op);
            }
            if (isParentSetter(m_)&&indCh_==0) {
                return new RefTernaryOperation(_index, _indexChild, _m, _op);
            }
            return new TernaryOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.FCT_OPER_PRIO && _op.isCallDbArray()) {
            String fctName_ = _op.getFctName().trim();
            int delta_ = StringExpUtil.getOffset(_op.getFctName());
            if (_m instanceof AbstractDotOperation) {
                OperationNode ch_ = _m.getFirstChild();
                if (ch_ != null) {
                    String type_ = ch_.getResultClass().getSingleNameOrEmpty();
                    if (!type_.isEmpty()) {
                        String id_ = StringExpUtil.getIdFromAllTypes(type_);
                        String fct_ = _page.getAliasFct();
                        if (StringUtil.quickEq(id_, fct_)) {
                            return new CallDynMethodOperation(_index, _indexChild, _m, _op);
                        }
                    }
                }
            }
            if (_op.isInstance()) {
                if (fctName_.isEmpty()) {
                    if (_m instanceof NamedArgumentOperation) {
                        if (_m.getParent() instanceof CallDynMethodOperation) {
                            return new ArgumentListInstancing(_index, _indexChild, _m, _op);
                        }
                    }
                    return new InferArrayInstancing(_index, _indexChild, _m, _op);
                }
                String op_ = _op.getOperators().firstValue();
                if (StringUtil.quickEq(op_,"{")) {
                    return new ElementArrayInstancing(_index, _indexChild, _m, _op);
                }
                if (StringUtil.quickEq(op_,"[")) {
                    return new DimensionArrayInstancing(_index, _indexChild, _m, _op);
                }
                AbsBk block_ = _op.getBlock();
                if (block_ instanceof AnonymousTypeBlock) {
                    ((AnonymousTypeBlock)block_).setIndexEnd(((AnonymousTypeBlock)block_).getIdRowCol()+_op.getLength());
                    return new AnonymousInstancingOperation(_index,_indexChild,_m,_op,(AnonymousTypeBlock)block_);
                }
                return new StandardInstancingOperation(_index, _indexChild, _m, _op);
            }
            AbsBk block_ = _op.getBlock();
            if (block_ instanceof SwitchMethodBlock) {
                ((SwitchMethodBlock)block_).setIndexEnd(block_.getOffset()+_op.getLength());
                return new SwitchOperation(_index, _indexChild, _m, _op,(SwitchMethodBlock)block_,delta_);
            }
            if (fctName_.isEmpty()) {
                return new IdOperation(_index, _indexChild, _m, _op,_op.getFctName().length());
            }
            if (StringUtil.quickEq(fctName_, keyWordValueOf_)) {
                return new EnumValueOfOperation(_index, _indexChild, _m, _op);
            }
            if (StringUtil.quickEq(fctName_, keyWordBool_)) {
                return new BadTernaryOperation(_index, _indexChild, _m, _op);
            }
            if (StringExpUtil.startsWithKeyWord(fctName_, keyWordClasschoice_)) {
                return new ChoiceFctOperation(_index, _indexChild, _m, _op);
            }
            if (StringExpUtil.startsWithKeyWord(fctName_, keyWordSuperaccess_)) {
                return new SuperFctOperation(_index, _indexChild, _m, _op);
            }
            if (StringExpUtil.startsWithKeyWord(fctName_, keyWordInterfaces_)) {
                if (_m instanceof IdOperation) {
                    return new InterfaceFctConstructor(_index, _indexChild, _m, _op);
                }
                return new InterfaceInvokingConstructor(_index, _indexChild, _m, _op);
            }
            if (StringExpUtil.startsWithKeyWord(fctName_, keyWordOperator_)) {
                return new ExplicitOperatorOperation(_index, _indexChild, _m, _op);
            }
            if (StringUtil.quickEq(fctName_, keyWordThat_)) {
                return new WrappOperation(_index, _indexChild, _m, _op,delta_);
            }
            if (StringUtil.quickEq(fctName_, keyWordFirstopt_)) {
                return new FirstOptOperation(_index, _indexChild, _m, _op,delta_);
            }
            if (StringUtil.quickEq(fctName_, keyWordDefault_)) {
                return new DefaultOperation(_index, _indexChild, _m, _op,delta_);
            }
            if (StringUtil.quickEq(fctName_,keyWordThis_)) {
                return new CurrentInvokingConstructor(_index, _indexChild, _m, _op);
            }
            if (StringUtil.quickEq(fctName_,keyWordSuper_)) {
                return new SuperInvokingConstructor(_index, _indexChild, _m, _op);
            }
            return new FctOperation(_index, _indexChild, _m, _op);
        }
        if (_op.isArray()) {
            return new ArrOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.FCT_OPER_PRIO && !_op.getValues().isEmpty()) {
            if (_op.isErrorDot()) {
                return new BadDottedOperation(_index, _indexChild, _m, _op);
            }
            if (_op.getOperators().firstValue().contains("?")) {
                return new SafeDotOperation(_index, _indexChild, _m, _op);
            }
            return new DotOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.POST_INCR_PRIO) {
            return new SemiAffectationOperation(_index, _indexChild, _m, _op, true);
        }
        if (_op.getPriority() == ElResolver.UNARY_PRIO) {
            String value_ = _op.getOperators().firstValue().trim();
            if (StringUtil.quickEq(value_, NEG_BOOL)) {
                return new UnaryBooleanOperation(_index, _indexChild, _m, _op);
            }
            if (StringUtil.quickEq(value_, NEG_BOOL_BIN)) {
                return new UnaryBinOperation(_index, _indexChild, _m, _op);
            }
            if (StringUtil.quickEq(value_, MINUS) || StringUtil.quickEq(value_, PLUS)) {
                return new UnaryOperation(_index, _indexChild, _m, _op);
            }
            if (value_.startsWith(MINUS) || value_.startsWith(PLUS)) {
                return new SemiAffectationOperation(_index, _indexChild, _m, _op, false);
            }
            if (StringUtil.quickEq(value_, "*")) {
                return new RandCodeOperation(_index, _indexChild, _m, _op);
            }
            if (StringExpUtil.startsWithKeyWord(value_, _page.getKeyWords().getKeyWordExplicit())) {
                return new ExplicitOperation(_index, _indexChild, _m, _op);
            }
            if (StringExpUtil.startsWithKeyWord(value_, _page.getKeyWords().getKeyWordCast())) {
                String clName_ = _op.getOperators().firstValue();
                String extract_ = clName_.substring(clName_.indexOf(PAR_LEFT)+1, clName_.lastIndexOf(PAR_RIGHT));
                StringList types_ = StringExpUtil.getAllSepCommaTypes(extract_);
                if (types_.size() > 1) {
                    return new ImplicitOperation(_index, _indexChild, _m, _op);
                }
            }
            return new CastOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.MULT_PRIO) {
            return new MultOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.ADD_PRIO) {
            return new AddOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.SHIFT_PRIO) {
            String value_ = _op.getOperators().firstValue().trim();
            if (StringUtil.quickEq(value_, SHIFT_LEFT)) {
                return new ShiftLeftOperation(_index, _indexChild, _m, _op);
            }
            if (StringUtil.quickEq(value_, SHIFT_RIGHT)) {
                return new ShiftRightOperation(_index, _indexChild, _m, _op);
            }
            if (StringUtil.quickEq(value_, BIT_SHIFT_LEFT)) {
                return new BitShiftLeftOperation(_index, _indexChild, _m, _op);
            }
            if (StringUtil.quickEq(value_, BIT_SHIFT_RIGHT)) {
                return new BitShiftRightOperation(_index, _indexChild, _m, _op);
            }
            if (StringUtil.quickEq(value_, ROTATE_LEFT)) {
                return new RotateLeftOperation(_index, _indexChild, _m, _op);
            }
            return new RotateRightOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.CMP_PRIO) {
            if (_op.isInstanceTest()) {
                return new InstanceOfOperation(_index, _indexChild, _m, _op);
            }
            return new CmpOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.EQ_PRIO) {
            return new EqOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.BIT_AND_PRIO) {
            return new BitAndOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.BIT_XOR_PRIO) {
            return new BitXorOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.BIT_OR_PRIO) {
            return new BitOrOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.RANGE) {
            return new RangeOperation(_index,_indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.AND_PRIO) {
            return new AndOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.OR_PRIO) {
            return new OrOperation(_index, _indexChild, _m, _op);
        }
        if (_op.getPriority() == ElResolver.NULL_SAFE_PRIO) {
            return new NullSafeOperation(_index,_indexChild,_m,_op);
        }
        if (_op.getPriority() == ElResolver.AFF_PRIO) {
            if (_m instanceof AnnotationInstanceArobaseOperation) {
                String value_ = _op.getValues().firstValue();
                return new AssocationOperation(_index, _indexChild, _m, _op, value_);
            }
            String op_ = _op.getOperators().firstValue();
            if (!StringUtil.quickEq(op_, AFF)) {
                return new CompoundAffectationOperation(_index, _indexChild, _m, _op);
            }
            return new AffectationOperation(_index, _indexChild, _m, _op);
        }
        return new ErrorPartOperation(_index, _indexChild, _m, _op);
    }

    protected static boolean atMostOne(MethodOperation _m) {
        return _m instanceof IdOperation && _m.getOperations().getValues().size() <= 1;
    }

    private static OperationNode createLeaf(int _index, int _indexChild, MethodOperation _m, OperationsSequence _op, AnalyzedPageEl _page) {
        ConstType ct_ = _op.getConstType();
        String originalStr_ = _op.getValues().getValue(IndexConstants.FIRST_INDEX);
        String str_ = originalStr_.trim();
        AbsBk block_ = _op.getBlock();
        if (AbsBk.isAnonBlock(block_)) {
            ((NamedCalledFunctionBlock)block_).setIndexEnd(((NamedCalledFunctionBlock)block_).getNameOffset()+_op.getLength());
            return new AnonymousLambdaOperation(_index,_indexChild,_m,_op,(NamedCalledFunctionBlock)block_,_op.getResults());
        }
        if (ct_ == ConstType.CHARACTER) {
            return new ConstantOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.STRING) {
            return new ConstantOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.TEXT_BLOCK) {
            return new ConstantOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.SIMPLE_ANNOTATION) {
            return new AnnotationInstanceArobaseOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.STATIC_ACCESS) {
            return new StaticAccessOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.STATIC_CALL_ACCESS) {
            return new StaticCallAccessOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.VARARG) {
            return new VarargOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.DEFAULT_VALUE) {
            return new DefaultValueOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.ID) {
            return new IdFctOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.LAMBDA) {
            return new LambdaOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.VALUES) {
            return new ValuesOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.CLASS_INFO) {
            return new StaticInfoOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.NUMBER) {
            return new ConstantOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.ACCESS_INDEXER) {
            return new ForwardOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.THIS_KEYWORD) {
            return new ThisOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.PARENT_KEY_WORD) {
            return new ParentInstanceOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.TRUE_CST) {
            return new ConstantOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.FALSE_CST) {
            return new ConstantOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.NULL_CST) {
            return new ConstantOperation(_index, _indexChild, _m, _op);
        }
        if (ct_ == ConstType.CLASSCHOICE_KEYWORD) {
            return new SettableFieldOperation(_index, _indexChild, _m, _op,new ChoiceFieldOperation(_op));
        }
        if (ct_ == ConstType.SUPER_ACCESS_KEYWORD) {
            return new SettableFieldOperation(_index, _indexChild, _m, _op,new SuperFromFieldOperation(_op));
        }
        if (ct_ == ConstType.SUPER_KEYWORD) {
            return new SettableFieldOperation(_index, _indexChild, _m, _op,new SuperFieldOperation(_op));
        }
        if (_op.getDeclaringField() != null) {
            return new DeclaredFieldOperation(_index, _indexChild, _m, _op, _op.getDeclaringField());
        }
        if (ElUtil.isDeclaringLoopVariable(_m, _page)) {
            return new MutableLoopVariableOperation(_index, _indexChild, _m, _op);
        }
        if (ElUtil.isDeclaringVariable(_m, _page)) {
            return new VariableOperation(_index, _indexChild, _m, _op);
        }
        if (ElUtil.isDeclaringRefVariable(_m, _page)) {
            return new RefVariableOperation(_index, _indexChild, _m, _op);
        }
        if (_m instanceof AbstractDotOperation) {
            OperationNode ch_ = _m.getFirstChild();
            if (ch_ != null) {
                if (ch_.getResultClass().isArray()) {
                    return new ArrayFieldOperation(_index, _indexChild, _m, _op);
                }
                return new SettableFieldOperation(_index, _indexChild, _m, _op,new StandardFieldOperation(_op));
            }
        }
        if (ct_ == ConstType.LOOP_INDEX) {
            return new FinalVariableOperation(_index, _indexChild, _m, _op);
        }
        AnaLocalVariable val_ = _page.getInfosVars().getVal(str_);
        int deep_ = -1;
        if (val_ == null) {
            String shortStr_ = StringExpUtil.skipPrefix(str_);
            AnaLocalVariable loc_ = _page.getInfosVars().getVal(shortStr_);
            deep_ = StringExpUtil.countPrefix(str_, '#');
            if (loc_ != null) {
                deep_--;
            }
            AnaCache cache_ = _page.getCache();
            val_ = cache_.getLocalVar(shortStr_,deep_);

        }
        if (val_ != null) {
            if (val_.getConstType() == ConstType.REF_PARAM) {
                val_.setUsed(true);
                return new RefParamOperation(_index,_indexChild,val_.getClassName(), val_.getRef(),_m,_op, deep_);
            }
            if (val_.getConstType() == ConstType.REF_LOC_VAR) {
                return new RefVariableOperation(_index, _indexChild, _m, _op, val_.getClassName(), val_.getRef(), deep_);
            }
            if (val_.getConstType() == ConstType.LOC_VAR) {
                return new VariableOperation(_index, _indexChild, _m, _op, val_.getClassName(), val_.getRef(),deep_,val_.isFinalVariable());
            }
            if (val_.getConstType() == ConstType.MUTABLE_LOOP_VAR) {
                return new MutableLoopVariableOperation(_index, _indexChild, _m, _op, val_.getClassName(), val_.getRef(),deep_,val_.isFinalVariable());
            }
            val_.setUsed(true);
            return new FinalVariableOperation(_index, _indexChild, _m, _op,val_.getClassName(),val_.getRef(),deep_,val_.isKeyWord());
        }
        return new SettableFieldOperation(_index, _indexChild, _m, _op,new StandardFieldOperation(_op));
    }

    static String emptyToObject(String _str, AnalyzedPageEl _page) {
        if (_str.isEmpty()) {
            return _page.getAliasObject();
        }
        return _str;
    }

    final boolean isFirstChildInParent() {
        return getIndexChild() == 0;
    }

    public abstract OperationNode getFirstChild();

    public final OperationNode getNextSibling() {
        return nextSibling;
    }
    final void setNextSibling(OperationNode _nextSibling) {
        nextSibling = _nextSibling;
    }

    public static FieldResult resolveDeclaredCustField(boolean _staticContext, AnaClassArgumentMatching _class, String _name, boolean _import, boolean _aff, AnalyzedPageEl _page, ScopeFilter _scope) {
        if (!_staticContext) {
            FieldResult resIns_ = getDeclaredCustFieldByContext(MethodAccessKind.INSTANCE, _class, _name, _import, _aff, _page, _scope);
            if (resIns_.getStatus() == SearchingMemberStatus.UNIQ) {
                return resIns_;
            }
        }
        return getDeclaredCustFieldByContext(MethodAccessKind.STATIC, _class, _name, _import, _aff, _page, _scope);
    }
    private static FieldResult getDeclaredCustFieldByContext(MethodAccessKind _kind, AnaClassArgumentMatching _class,
                                                             String _name, boolean _import, boolean _aff, AnalyzedPageEl _page, ScopeFilter _scope) {
        StringMap<FieldResult> ancestors_ = new StringMap<FieldResult>();
        int maxAnc_ = 0;
        CustList<CustList<TypeInfo>> typesGroup_= typeLists(_class.getNames(),_kind, _page);
        for (CustList<TypeInfo> g: typesGroup_) {
            StringList baseTypes_ = new StringList();
            StringMap<String> superTypesBaseAncBis_ = new StringMap<String>();
            feedTypes(g, baseTypes_, superTypesBaseAncBis_);
            for (TypeInfo t: g) {
                fetchFieldsType(ancestors_, _page,
                        new ScopeFilterType(_scope,t, MethodId.getKind(t.getScope() == MethodAccessKind.STATIC), baseTypes_, superTypesBaseAncBis_, new FormattedFilter()),
                        new ScopeFilterField(_aff, _name, t));
                maxAnc_ = Math.max(maxAnc_, t.getAncestor());
            }
        }
        int max_ = maxAnc_;
        for (int i = 0; i <= maxAnc_; i++) {
            StringList subClasses_ = new StringList();
            for (EntryCust<String,FieldResult> e: ancestors_.entryList()) {
                if (e.getValue().getContent().getAnc() != i) {
                    continue;
                }
                subClasses_.add(e.getKey());
            }
            StringList subs_ = AnaTypeUtil.getSubclasses(subClasses_, _page);
            FieldResult res_ = getRes(ancestors_, subs_);
            if (res_ != null) {
                return res_;
            }
        }
        if (_import) {
            int maxLoc_ = maxAnc_ + 1;
            String glClass_ = _scope.getGlClass();
            String curClassBase_ = StringExpUtil.getIdFromAllTypes(glClass_);
            for (EntryCust<String, ImportedField> e: ResolvingImportTypes.lookupImportStaticFields(curClassBase_, _name, _page).entryList()) {
                ImportedField v_ = e.getValue();
                max_ = Math.max(max_, v_.getImported() +maxAnc_);
                FieldResult res_ = new FieldResult();
                String realType_ = v_.getType();
                boolean finalField_ = v_.isFinalField();
                String formatted_ = e.getKey();
                res_.setFormattedType(v_.buildFormatted(formatted_));
                res_.setFileName(v_.getFileName());
                res_.setMemberId(v_.getMemberId());
                res_.setFieldType(v_.getFieldType());
                res_.setValOffset(v_.getValueOffset());
                ClassField classField_ = new ClassField(formatted_, _name);
                res_.getContent().setClassField(classField_);
                res_.setDeclaringClass(formatted_);
                res_.getContent().setStaticField(true);
                res_.getContent().setFinalField(finalField_);
                res_.setType(realType_);
                res_.getContent().setRealType(realType_);
                res_.getContent().setAnc(v_.getImported() +maxAnc_);
                res_.setStatus(SearchingMemberStatus.UNIQ);
                ancestors_.addEntry(e.getKey(),res_);
            }
            for (int i = maxLoc_; i <= max_; i++) {
                StringList subClasses_ = new StringList();
                for (EntryCust<String,FieldResult> e: ancestors_.entryList()) {
                    if (e.getValue().getContent().getAnc() != i) {
                        continue;
                    }
                    subClasses_.add(e.getKey());
                }
                StringList subs_ = AnaTypeUtil.getSubclasses(subClasses_, _page);
                FieldResult res_ = getRes(ancestors_, subs_);
                if (res_ != null) {
                    return res_;
                }
            }
        }
        FieldResult r_ = new FieldResult();
        r_.setStatus(SearchingMemberStatus.ZERO);
        return r_;
    }

    private static void feedTypes(CustList<TypeInfo> _list, StringList _baseTypes, StringMap<String> _superTypesBaseAnc) {
        for (TypeInfo t: _list) {
            if (t.isBase()) {
                String id_ = t.getTypeId();
                _baseTypes.add(id_);
                _superTypesBaseAnc.put(id_, id_);
                for (String m: t.getSuperTypes()) {
                    _superTypesBaseAnc.put(m, id_);
                }
            }
        }
    }

    private static void fetchFieldsType(StringMap<FieldResult> _ancestors,
                                        AnalyzedPageEl _page, ScopeFilterType _scope, ScopeFilterField _scopeField) {
        _page.getFieldFilter().tryAddField(_scope, _scopeField, _ancestors, _page);
    }

    public static void tryAddField(FieldInfo _fi, StringMap<FieldResult> _ancestors, AnalyzedPageEl _page, ScopeFilterType _scope, ScopeFilterField _scopeField) {
        String fullName_ = _scope.getFullName();
        boolean staticField_ = _fi.isStaticField();
        if (_scope.getKind() == MethodAccessKind.STATIC) {
            if (!staticField_) {
                return;
            }
        } else {
            if (staticField_) {
                return;
            }
        }
        if (cannotAccess(fullName_, _fi.getAccessed(), _scope.getGlClass(), _scope.getSuperTypesBaseAncBis(), _page)) {
            return;
        }
        if (filterMember(_scope.isBaseClass(), _scope.isSuperClass(), _scope.getSuperTypesBase(),fullName_)) {
            return;
        }
        AnaFormattedRootBlock formattedPair_ = _scope.getFormatted();
        String formatted_ = formattedPair_.getFormatted();
        String realType_ = _fi.getType();
        String if_ = FieldInfo.newFieldInfo(formatted_, realType_, staticField_, _scopeField.isAff(), _page);
        if (if_.isEmpty()) {
            return;
        }
        FieldResult res_ = feedFieldResult(formattedPair_, _fi, _scope.getAnc(), if_);
        _ancestors.addEntry(_scope.getFullName(), res_);
    }

    private static FieldResult feedFieldResult(AnaFormattedRootBlock _formatted, FieldInfo _fi, int _anc, String _type) {
        FieldResult res_ = new FieldResult();
        res_.setFormattedType(_formatted);
        res_.setFileName(_fi.getFileName());
        res_.setMemberId(_fi.getMemberId());
        res_.setFieldType(_formatted.getRootBlock());
        res_.setValOffset(_fi.getValOffset());
        res_.getContent().setClassField(_fi.getClassField());
        res_.setDeclaringClass(_formatted.getFormatted());
        res_.getContent().setStaticField(_fi.isStaticField());
        res_.getContent().setFinalField(_fi.isFinalField());
        res_.setType(_type);
        res_.getContent().setRealType(_fi.getType());
        res_.getContent().setAnc(_anc);
        res_.setStatus(SearchingMemberStatus.UNIQ);
        return res_;
    }

    private static FieldResult getRes(StringMap<FieldResult> _ancestors, StringList _classes) {
        if (_classes.onlyOneElt()) {
            return _ancestors.getVal(_classes.first());
        }
        return null;
    }

    static ConstrustorIdVarArg getDeclaredCustConstructor(int _varargOnly, AnaClassArgumentMatching _class,
                                                          AnaGeneType _type,
                                                          ConstructorId _uniqueId, String _param, NameParametersFilter _filter, AnalyzedPageEl _page) {
        String clCurName_ = _class.getName();
        int varargOnly_ = ctorVarArgOnly(_varargOnly, _uniqueId);
        boolean uniq_ = isUniqCtor(_uniqueId, _varargOnly);
        if (noCtor(_type)) {
            if (_filter.isEmptyArg()) {
                return noCtorFound(clCurName_, _type);
            }
        }
        CustList<ConstructorInfo> ctors_ = new CustList<ConstructorInfo>();
        if (_type instanceof StandardType) {
            for (StandardConstructor s: ((StandardType)_type).getConstructors()) {
                if (exclude(_uniqueId,varargOnly_, s)) {
                    continue;
                }
                ConstructorInfo mloc_ = initCtorInfo((StandardType) _type, clCurName_, s);
                ctors_.add(mloc_);
            }
        }
        if (_type instanceof RootBlock){
            for (ConstructorBlock b: ((RootBlock)_type).getConstructorBlocks()) {
                if (excludeCust((RootBlock) _type, _uniqueId,varargOnly_, b, _page)) {
                    continue;
                }
                ConstructorInfo mloc_ = initCtorInfo((AbsBk) _type, clCurName_, b);
                ctors_.add(mloc_);
            }
        }
        CustList<ConstructorInfo> named_ = new CustList<ConstructorInfo>();
        for (ConstructorInfo c: ctors_) {
            c.format(_page);
            if (!isPossibleMethodNamed(c,_filter)) {
                continue;
            }
            named_.add(c);
        }
        CustList<ConstructorInfo> signatures_ = new CustList<ConstructorInfo>();
        for (ConstructorInfo c: named_) {
            if (!isPossibleArgs(uniq_, varargOnly_, c, _param, _page)) {
                continue;
            }
            signatures_.add(c);
        }
        return getConstrustorId(clCurName_,_type,_filter, _page, signatures_);
    }

    protected static ConstructorInfo initCtorInfo(AbsBk _type, String _clCurName, ConstructorBlock _b) {
        ConstructorId ctor_ = _b.getId().copy(StringExpUtil.getIdFromAllTypes(_clCurName));
        ConstructorInfo mloc_ = new ConstructorInfo();
        mloc_.setCustomCtor(_b);
        mloc_.setFileName(_type.getFile().getFileName());
        mloc_.memberId(((RootBlock) _type).getNumberAll(), _b.getCtorNumber());
        mloc_.setParametersNames(_b.getParametersNames());
        mloc_.constructorId(_clCurName, ctor_);
        mloc_.pair((RootBlock) _type, _b);
        return mloc_;
    }

    protected static ConstructorInfo initCtorInfo(StandardType _type, String _clCurName, StandardConstructor _s) {
        ConstructorId ctor_ = _s.getId().copy(StringExpUtil.getIdFromAllTypes(_clCurName));
        ConstructorInfo mloc_ = new ConstructorInfo();
        mloc_.setStandardType(_type);
        mloc_.setParametersNames(_s.getParametersNames());
        mloc_.constructorId(_clCurName, ctor_);
        return mloc_;
    }

    protected static boolean noCtor(AnaGeneType _type) {
        boolean empty_ = false;
        if (_type instanceof StandardType) {
            empty_ = ((StandardType)_type).getConstructors().isEmpty();
        }
        if (_type instanceof RootBlock){
            empty_ = ((RootBlock)_type).getConstructorBlocks().isEmpty();
        }
        return empty_;
    }
    private static boolean isUniqCtor(ConstructorId _uniqueId, int _varargOnly) {
        boolean uniq_ = false;
        if (_uniqueId != null) {
            uniq_ = isUniqCtor(_varargOnly);
        }
        return uniq_;
    }

    private static int ctorVarArgOnly(int _varargOnly, ConstructorId _uniqueId) {
        int varargOnly_ = _varargOnly;
        if (_uniqueId != null) {
            varargOnly_ = -1;
        }
        return varargOnly_;
    }

    private static ConstrustorIdVarArg getConstrustorId(String _curClassName,AnaGeneType _type,NameParametersFilter _filter, AnalyzedPageEl _page, CustList<ConstructorInfo> _signatures) {
        StringMap<StringList> map_ = _page.getCurrentConstraints().getCurrentConstraints();
        ArgumentsGroup gr_ = new ArgumentsGroup(_page, map_);
        Parametrable cInfo_ = sortCtors(_signatures, gr_);
        if (!(cInfo_ instanceof ConstructorInfo)) {
            return null;
        }
        CustList<CustList<ClassMethodIdReturn>> implicits_ = cInfo_.getImplicits();
        CustList<OperationNode> allOps_ = cInfo_.getAllOps();
        feedImplicitsInfos(implicits_, allOps_);
        Ints nameParametersFilterIndexes_ = cInfo_.getNameParametersFilterIndexes();
        NamedFunctionBlock custMethod_ = ((ConstructorInfo)cInfo_).getCustomCtor();
        feedNamedParams(_filter, nameParametersFilterIndexes_, custMethod_);
        return buildCtorInfo(_curClassName, _type, (ConstructorInfo) cInfo_);
    }

    protected static ConstrustorIdVarArg buildCtorInfo(String _curClassName, AnaGeneType _type, ConstructorInfo _cInfo) {
        ConstrustorIdVarArg out_;
        out_ = new ConstrustorIdVarArg();
        if (_cInfo.isVarArgWrap()) {
            out_.setVarArgToCall(true);
        }
        setupContainer(_type, _curClassName, out_);
        out_.setRealId(_cInfo.getConstraints());
        out_.setPair(_cInfo.getPair());
        out_.setConstId(_cInfo.getFormatted());
        out_.setFileName(_cInfo.getFileName());
        out_.setStandardType(_cInfo.getStandardType());
        out_.setMemberId(_cInfo.getMemberId());
        return out_;
    }

    private static void feedNamedParams(NameParametersFilter _filter, Ints _nameParametersFilterIndexes, NamedFunctionBlock _custMethod) {
        int parNameLen_ = _nameParametersFilterIndexes.size();
        for (int i = 0; i < parNameLen_; i++) {
            NamedArgumentOperation namedArgument_ = _filter.getParameterFilter().get(i);
            if (_custMethod != null) {
                namedArgument_.getCustomMethod().add(_custMethod);
            }
            namedArgument_.setIndex(_nameParametersFilterIndexes.get(i));
        }
    }

    private static void feedImplicitsInfos(CustList<CustList<ClassMethodIdReturn>> _implicits, CustList<OperationNode> _allOps) {
        int len_ = _implicits.size();
        for (int i = 0; i < len_; i++) {
            CustList<ClassMethodIdReturn> implicitInfos_ = _implicits.get(i);
            AnaClassArgumentMatching resultClass_ = _allOps.get(i).getResultClass();
            for (ClassMethodIdReturn j : implicitInfos_) {
                resultClass_.implicitInfos(j);
            }
        }
    }

    static ConstrustorIdVarArg getDeclaredCustConstructorLambda(int _varargOnly, String _class,
                                                                AnaGeneType _type,
                                                                ConstructorId _uniqueId, AnalyzedPageEl _page, StringList _args) {
        int varargOnly_ = ctorVarArgOnly(_varargOnly, _uniqueId);
        if (noCtor(_type)) {
            if (_args.isEmpty()) {
                return noCtorFound(_class, _type);
            }
        }
        CustList<ConstructorInfo> signatures_ = new CustList<ConstructorInfo>();
        if (_type instanceof StandardType) {
            for (StandardConstructor s: ((StandardType)_type).getConstructors()) {
                if (exclude(_uniqueId,varargOnly_, s)) {
                    continue;
                }
                ConstructorInfo mloc_ = initCtorInfo((StandardType) _type, _class, s);
                mloc_.format(_page);
                if (!isPossibleMethodLambda(mloc_, _page, _args)) {
                    continue;
                }
                signatures_.add(mloc_);
            }
        }
        if (_type instanceof RootBlock){
            for (ConstructorBlock b: ((RootBlock)_type).getConstructorBlocks()) {
                if (excludeCust((RootBlock) _type, _uniqueId,varargOnly_, b, _page)) {
                    continue;
                }
                ConstructorInfo mloc_ = initCtorInfo((AbsBk) _type, _class, b);
                mloc_.format(_page);
                if (!isPossibleMethodLambda(mloc_, _page, _args)) {
                    continue;
                }
                signatures_.add(mloc_);
            }
        }
        return getConstrustorIdLambda(_class,_type,_page, signatures_);
    }

    protected static ConstrustorIdVarArg noCtorFound(String _class, AnaGeneType _type) {
        ConstrustorIdVarArg out_;
        out_ = new ConstrustorIdVarArg();
        ConstructorId id_ = new ConstructorId(_class, new StringList(), false);
        out_.setRealId(id_);
        out_.setConstId(id_);
        setupContainer(_type, _class, out_);
        return out_;
    }

    protected static Parametrable tryGetFilterSignaturesInfer(CustList<ConstructorInfo> _list, AnaGeneType _type, AnalyzedPageEl _page, StringList _args, String _stCall, String _retType) {
        CustList<ConstructorInfo> signatures_ = new CustList<ConstructorInfo>();
        for (ConstructorInfo c: _list) {
            filter(_type, _page, _args, _stCall, _retType, signatures_, c.getConstraints(), c);
        }
        StringMap<StringList> map_ = _page.getCurrentConstraints().getCurrentConstraints();
        ArgumentsGroup gr_ = new ArgumentsGroup(_page, map_);
        return sortCtors(signatures_, gr_);
    }

    protected static CustList<ConstructorInfo> tryGetSignatures(AnaGeneType _type, AnalyzedPageEl _page, String _clCurName, String _stCall) {
        CustList<ConstructorInfo> signatures_ = new CustList<ConstructorInfo>();
        if (_type instanceof StandardType) {
            for (StandardConstructor s: ((StandardType)_type).getConstructors()) {
                ConstructorInfo mloc_ = initCtorInfo((StandardType) _type, _clCurName, s);
                mloc_.setStCall(_stCall);
                mloc_.format(_page);
                signatures_.add(mloc_);
            }
        }
        if (_type instanceof RootBlock){
            for (ConstructorBlock b: ((RootBlock)_type).getConstructorBlocks()) {
                if (excludeQuick((RootBlock) _type, b, _page)) {
                    continue;
                }
                ConstructorInfo mloc_ = initCtorInfo((AbsBk) _type, _clCurName, b);
                mloc_.setStCall(_stCall);
                mloc_.format(_page);
                signatures_.add(mloc_);
            }
        }
        return signatures_;
    }

    private static void filter(AnaGeneType _type, AnalyzedPageEl _page, StringList _args, String _stCall, String _retType, CustList<ConstructorInfo> _signatures, ConstructorId _ctor, ConstructorInfo _mloc) {
        if (_args != null && !_stCall.isEmpty()) {
            CustList<AnaClassArgumentMatching> args_ = new CustList<AnaClassArgumentMatching>();
            for (String o: _args) {
                args_.add(new AnaClassArgumentMatching(o));
            }
            String result_ = AnaTemplates.tryInferMethod(-1, _mloc.getClassName(), ConstructorId.to(_ctor), _stCall,
                    _page.getCurrentConstraints().getCurrentConstraints(), args_, _type.getGenericString(), _retType, _page);
            if (result_.isEmpty()) {
                return;
            }
            _mloc.reformat(result_, _page);
        }

        if (!isPossibleMethodLambdaInfer(_mloc, _page, _args)) {
            return;
        }
        _signatures.add(_mloc);
    }

    private static ConstrustorIdVarArg getConstrustorIdLambda(String _curClassName,AnaGeneType _type,AnalyzedPageEl _page, CustList<ConstructorInfo> _signatures) {
        StringMap<StringList> map_ = _page.getCurrentConstraints().getCurrentConstraints();
        ArgumentsGroup gr_ = new ArgumentsGroup(_page, map_);
        Parametrable cInfo_ = sortCtors(_signatures, gr_);
        if (!(cInfo_ instanceof ConstructorInfo)) {
            return null;
        }
        return buildCtorInfo(_curClassName, _type, (ConstructorInfo) cInfo_);
    }

    protected static void setupContainer(AnaGeneType _type,String _className, ConstrustorIdVarArg _out) {
        if (_type instanceof RootBlock) {
            _out.setFileName(((AbsBk)_type).getFile().getFileName());
            _out.getMemberId().setRootNumber(((RootBlock)_type).getNumberAll());
            _out.setFormattedType(new AnaFormattedRootBlock((RootBlock)_type,_className));
        }
        if (_type instanceof StandardType) {
            _out.setStandardType((StandardType)_type);
            _out.setFormattedType(new AnaFormattedRootBlock((RootBlock) null,_className));
        }
    }

    private static boolean isUniqCtor(int _varargOnly) {
        return _varargOnly > -1;
    }

    protected static boolean exclude(ConstructorId _uniqueId, int _varargOnly, StandardConstructor _e) {
        ConstructorId ctor_ = _e.getId();
        boolean varArg_ = ctor_.isVararg();
        return excludeBytFilter(_uniqueId, _varargOnly, ctor_, varArg_);
    }

    protected static boolean excludeCust(RootBlock _g, ConstructorId _uniqueId, int _varargOnly, ConstructorBlock _e, AnalyzedPageEl _page) {
        ConstructorId ctor_ = _e.getId();
        boolean varArg_ = ctor_.isVararg();
        if (excludeBytFilter(_uniqueId, _varargOnly, ctor_, varArg_)) {
            return true;
        }
        return excludeQuick(_g, _e,_page);
    }

    private static boolean excludeBytFilter(ConstructorId _uniqueId, int _varargOnly, ConstructorId _ctor, boolean _varArg) {
        if (_varargOnly > -1) {
            if (!_varArg) {
                return true;
            }
        }
        if (_uniqueId != null) {
            return !_uniqueId.eq(_ctor);
        }
        return false;
    }

    protected static boolean excludeQuick(RootBlock _g, ConstructorBlock _e, AnalyzedPageEl _page) {
        String glClass_ = _page.getGlobalClass();
        Accessed a_ = new Accessed(_e.getAccess(), _g.getPackageName(), _g);
        return !ContextUtil.canAccess(glClass_, a_, _page);
    }

    protected static AnaClassArgumentMatching voidToObject(AnaClassArgumentMatching _original, AnalyzedPageEl _page) {
        if (_original.matchVoid(_page)) {
            return new AnaClassArgumentMatching(_page.getAliasObject());
        }
        return _original;
    }

    protected static ClassMethodIdReturn tryGetDeclaredCustMethod(int _varargOnly,
                                                                  MethodAccessKind _staticContext,
                                                                  StringList _classes, String _name,
                                                                  boolean _import,
                                                                  String _param, NameParametersFilter _filter, AnalyzedPageEl _page, ScopeFilter _sc) {
        CustList<CustList<MethodInfo>> methods_ = getDeclaredCustMethodByType(_staticContext, _classes, _name, _import, _page, _sc, _filter.getFormattedFilter());
        boolean uniq_ = uniq(_sc.getId(),_varargOnly);
        int varargOnly_ = fetchVarargOnly(_varargOnly, _sc.getId());
        return getCustResult(uniq_, false, varargOnly_, methods_, _name, _param,_filter, _page);
    }

    protected static ClassMethodIdReturn tryGetDeclaredCustMethod(StringList _classes, String _name,
                                                                  CustList<OperationNode> _argsClass, AnalyzedPageEl _page) {
        CustList<CustList<MethodInfo>> methods_;
        methods_ = getDeclaredCustMethodByType(MethodAccessKind.STATIC, _classes, _name, false, _page, new ScopeFilter(null, true, false, false, _page.getGlobalClass()), new FormattedFilter());
        boolean uniq_ = uniq((ClassMethodIdAncestor) null, -1);
        int varargOnly_ = fetchVarargOnly(-1, null);
        return getCustResult(uniq_, varargOnly_, methods_, _name, _argsClass, _page);
    }

    private static int fetchVarargOnly(int _varargOnly, ClassMethodIdAncestor _uniqueId) {
        int varargOnly_ = _varargOnly;
        if (_uniqueId != null) {
            varargOnly_ = -1;
        }
        return varargOnly_;
    }

    protected static ClassMethodIdReturn tryGetDeclaredCustTrueFalse(MethodAccessKind _staticContext,
                                                                     StringList _classes, String _name,
                                                                     ClassMethodId _uniqueId,
                                                                     AnaClassArgumentMatching[] _argsClass, AnalyzedPageEl _page) {
        if (_argsClass.length != 1 || _staticContext == MethodAccessKind.STATIC) {
            return null;
        }
        ClassMethodIdReturn res_;
        AbstractComparer cmp_ = new DefaultComparer();
        if (StringUtil.quickEq(_name, _page.getKeyWords().getKeyWordTrue())) {
            res_ = fetchTestsOperator(_classes,_argsClass[0],_uniqueId, _page, cmp_, _page.getTrues());
        } else {
            res_ = fetchTestsOperator(_classes, _argsClass[0], _uniqueId, _page, cmp_, _page.getFalses());
        }
        possibleAdjust(res_);
        return res_;
    }
    protected static ClassMethodIdReturn tryGetDeclaredCustIncrDecrMethod(StringList _classes, String _name,
                                                                          AnaClassArgumentMatching _argsClass, AnalyzedPageEl _page) {
        CustList<CustList<MethodInfo>> methods_;
        methods_ = getDeclaredCustMethodByType(MethodAccessKind.STATIC, _classes, _name, false, _page, new ScopeFilter(null, true, false, false, _page.getGlobalClass()), new FormattedFilter());
        return getCustIncrDecrResult(methods_, _name, _argsClass, _page);
    }
    protected static ReversibleConversion tryGetPair(AnaClassArgumentMatching _argsClass, AnalyzedPageEl _page) {
        StringList conv_ = new StringList();
        conv_.add(_page.getAliasPrimInteger());
        conv_.add(_page.getAliasPrimLong());
        conv_.add(_page.getAliasPrimFloat());
        conv_.add(_page.getAliasPrimDouble());
        for (String p: conv_) {
            AnaClassArgumentMatching r_ = new AnaClassArgumentMatching(p);
            ClassMethodIdReturn from_ = tryGetDeclaredImplicitCast(p, _argsClass, _page);
            if (from_ == null) {
                continue;
            }
            String param_ = from_.getRealId().getParametersType(1);
            ClassMethodIdReturn to_ = tryGetDeclaredImplicitCast(param_, r_, _page);
            if (to_ == null) {
                continue;
            }
            return new ReversibleConversion(from_,to_);
        }
        return null;
    }
    protected static ClassMethodIdReturn tryGetDeclaredCustMethodLambda(int _varargOnly,
                                                                        MethodAccessKind _staticContext,
                                                                        StringList _classes, String _name,
                                                                        boolean _superClass, boolean _baseClass,
                                                                        ClassMethodIdAncestor _uniqueId,
                                                                        StringList _argsClass, AnalyzedPageEl _page) {
        CustList<CustList<MethodInfo>> methods_;
        methods_ = getDeclaredCustMethodByType(_staticContext, _classes, _name, false, _page, new ScopeFilter(_uniqueId, _baseClass, _superClass, false, _page.getGlobalClass()), new FormattedFilter());
        int varargOnly_ = fetchVarargOnly(_varargOnly, _uniqueId);
        return getCustResultLambda(varargOnly_, methods_, _name, _page, _argsClass);
    }

    protected static ClassMethodIdReturn tryGetDeclaredTests(String _classes, AnalyzedPageEl _page, StringMap<CustList<MethodHeaderInfo>> _tests) {
        CustList<MethodInfo> methods_;
        methods_ = new CustList<MethodInfo>();
        fetchTests(methods_,_classes, null, _page, new DefaultComparer(), _tests);
        return tryGetDeclaredTrueFalse(_classes, methods_);
    }

    private static ClassMethodIdReturn tryGetDeclaredTrueFalse(String _classes, CustList<MethodInfo> _methods) {
        CustList<MethodInfo> candidates_ = new CustList<MethodInfo>();
        for (MethodInfo m : _methods) {
            if (StringUtil.quickEq(
                    StringExpUtil.getIdFromAllTypes(m.getClassName()),
                    StringExpUtil.getIdFromAllTypes(_classes))){
                candidates_.add(m);
            }
        }
        if (candidates_.size() == 1) {
            MethodInfo m_ = candidates_.first();
            MethodId id_ = m_.getFormatted();
            MethodId extract_ = new MethodId(id_.getKind(), id_.getName(), id_.shiftFirst(), id_.isVararg());
            return buildResult(m_, extract_);
        }
        return null;
    }

    protected static ClassMethodIdReturn tryGetCast(String _classes, ClassMethodId _uniqueId, AnaClassArgumentMatching[] _argsClass, AnalyzedPageEl _page, StringMap<CustList<MethodHeaderInfo>> _one, StringMap<CustList<MethodHeaderInfo>> _two, StringMap<CustList<MethodHeaderInfo>> _three) {
        CustList<MethodInfo> methods_;
        AnaClassArgumentMatching cl_;
        if (_argsClass.length > 1) {
            cl_ = _argsClass[1];
        } else {
            cl_ = _argsClass[0];
        }
        AbstractComparer cmp_ = new DefaultComparer();
        methods_ = comCast(_uniqueId, _page, cmp_, _one, _two, _three, new PairMatchCast(cl_, _classes));
        ClassMethodIdReturn res_ = getCustCastResult(methods_, cl_, _page, _page.getCurrentConstraints().getCurrentConstraints(), cmp_);
        possibleAdjust(res_);
        return res_;
    }

    private static void possibleAdjust(ClassMethodIdReturn _res) {
        if (_res != null) {
            ClassMethodId id_ = _res.getId();
            MethodId cts_ = id_.getConstraints();
            _res.setId(new ClassMethodId(id_.getClassName(), new MethodId(cts_.getKind(), cts_.getName(), cts_.shiftFirst(), cts_.isVararg())));
        }
    }

    public static ClassMethodIdReturn tryGetDeclaredImplicitCast(String _classes, AnaClassArgumentMatching _arg, AnalyzedPageEl _page) {
        StringMap<StringList> vars_ = _page.getCurrentConstraints().getCurrentConstraints();
        return tryGetDeclaredImplicitCast(_classes, _arg, _page, vars_, new DefaultComparer());
    }

    public static ClassMethodIdReturn tryGetDeclaredImplicitCast(String _classes, AnaClassArgumentMatching _arg, AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        CustList<MethodInfo> methods_ = new CustList<MethodInfo>();
        for (String n: _arg.getNames()) {
            methods_.addAllElts(getDeclaredCustImplicitCast(_classes,n, _page, _vars, _cmp));
        }
        return getCustCastResult(methods_, _arg, _page, _vars, _cmp);
    }

    public static CustList<MethodInfo> tryGetImplSgn(String _classes, String _arg, AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        CustList<MethodInfo> methods_ = getDeclaredCustImplicitCastFct(_classes, _page, _vars, _cmp);
        return getImplSgn(methods_, _arg, _page, _vars, _cmp);
    }

    static OperatorConverter getUnaryOperatorOrMethod(MethodOperation _node,
                                                      OperationNode _operand,
                                                      String _op, AnalyzedPageEl _page) {
        AnaClassArgumentMatching operand_ = _operand.getResultClass();
        CustList<OperationNode> single_ = new CustList<OperationNode>(_operand);
        if (isNativeUnaryOperator(operand_,_op, _page)) {
            return null;
        }
        CustList<CustList<MethodInfo>> listsUnary_ = addUnaries(_page, operand_);
        ClassMethodIdReturn clMethImp_ = getCustResult(false, -1, listsUnary_, _op, single_, _page);
        if (clMethImp_ != null) {
            CustList<OperationNode> chidren_ = _node.getChildrenNodes();
            _node.setResultClass(voidToObject(new AnaClassArgumentMatching(clMethImp_.getReturnType(), _page.getPrimitiveTypes()), _page));
            MethodId id_ = clMethImp_.getRealId();
            InvokingOperation.unwrapArgsFct(id_, -1, EMPTY_STRING, chidren_, _page);
            return new OperatorConverter(clMethImp_);
        }
        ClassMethodIdReturn clId_ = getCustomOperatorOrMethod(_node, _op, _page);
        if (clId_ != null) {
            return new OperatorConverter(clId_);
        }
        CustList<StringList> groups_ = new CustList<StringList>();
        if (StringExpUtil.isUnNum(_op)) {
            StringList group_ = new StringList();
            group_.add(_page.getAliasPrimInteger());
            group_.add(_page.getAliasPrimLong());
            groups_.add(group_);
            group_ = new StringList();
            group_.add(_page.getAliasPrimFloat());
            group_.add(_page.getAliasPrimDouble());
            groups_.add(group_);
        } else if (StringUtil.quickEq(_op,"~")) {
            StringList group_ = new StringList();
            group_.add(_page.getAliasPrimBoolean());
            group_.add(_page.getAliasPrimInteger());
            group_.add(_page.getAliasPrimLong());
            groups_.add(group_);
        } else {
            StringList group_ = new StringList();
            group_.add(_page.getAliasPrimBoolean());
            groups_.add(group_);
        }
        for (StringList g: groups_) {
            CustList<CustList<MethodInfo>> lists_ = new CustList<CustList<MethodInfo>>();
            CustList<MethodInfo> list_ = new CustList<MethodInfo>();
            for (String p:g) {
                addVirtual(_op, _page, list_,p, p, new StringList(p));
            }
            lists_.add(list_);
            ClassMethodIdReturn clMeth_ = getCustResult(false, -1, lists_, _op, single_, _page);
            if (clMeth_ != null) {
                CustList<OperationNode> chidren_ = _node.getChildrenNodes();
                _node.setResultClass(voidToObject(new AnaClassArgumentMatching(clMeth_.getReturnType(), _page.getPrimitiveTypes()), _page));
                MethodId id_ = clMeth_.getRealId();
                InvokingOperation.unwrapArgsFct(id_, -1, EMPTY_STRING, chidren_, _page);
                return new OperatorConverter(clMeth_);
            }
        }
        return null;
    }

    private static CustList<CustList<MethodInfo>> addUnaries(AnalyzedPageEl _page, AnaClassArgumentMatching _operand) {
        CustList<CustList<MethodInfo>> listsUnary_ = new CustList<CustList<MethodInfo>>();
        CustList<MethodInfo> listUnary_ = new CustList<MethodInfo>();
        for (String n : _operand.getNames()) {
            fetchUnary(listUnary_, n, _page);
        }
        listsUnary_.add(listUnary_);
        return listsUnary_;
    }

    private static void addVirtual(String _op, AnalyzedPageEl _page, CustList<MethodInfo> _list,String _className, String _returnType, StringList _params) {
        MethodInfo mloc_ = new MethodInfo();
        MethodId id_ = new MethodId(MethodAccessKind.STATIC, _op, _params);
        mloc_.classMethodId(_className,id_);
        mloc_.setReturnType(_returnType);
        mloc_.format(true, _page);
        _list.add(mloc_);
    }

    static ClassMethodIdReturn getIncrDecrOperatorOrMethod(MethodOperation _node,
                                                           OperationNode _operand,
                                                           String _op, AnalyzedPageEl _page) {
        AnaClassArgumentMatching operand_ = _operand.getResultClass();
        CustList<OperationNode> single_ = new CustList<OperationNode>(_operand);
        if (isNativeUnaryOperator(operand_,_op, _page)) {
            return null;
        }
        CustList<CustList<MethodInfo>> listsUnary_ = addUnaries(_page, operand_);
        ClassMethodIdReturn clMethImp_ = getCustResult(false, -1, listsUnary_, _op, single_, _page);
        if (clMethImp_ != null) {
            CustList<OperationNode> chidren_ = _node.getChildrenNodes();
            _node.setResultClass(voidToObject(new AnaClassArgumentMatching(clMethImp_.getReturnType(), _page.getPrimitiveTypes()), _page));
            MethodId realId_ = clMethImp_.getRealId();
            InvokingOperation.unwrapArgsFct(realId_, -1, EMPTY_STRING, chidren_, _page);
            return clMethImp_;
        }
        return getCustomIncrDecrOperatorOrMethod(_node,_operand,_op, _page);
    }
    static OperatorConverter getBinaryOperatorOrMethod(MethodOperation _node,
                                                       OperationNode _left, OperationNode _right,
                                                       String _op, AnalyzedPageEl _page) {
        AnaClassArgumentMatching left_ = _left.getResultClass();
        AnaClassArgumentMatching right_ = _right.getResultClass();
        CustList<OperationNode> pair_ = new CustList<OperationNode>(_left,_right);
        if (isNativeBinaryOperator(left_,right_,_op, _page)) {
            return null;
        }
        CustList<CustList<MethodInfo>> listsBinary_ = new CustList<CustList<MethodInfo>>();
        ClassMethodIdReturn test_ = null;
        AbstractComparer cmp_ = new DefaultComparer();
        if (StringUtil.quickEq(_op,"&&")) {
            CustList<MethodInfo> listTrue_ = new CustList<MethodInfo>();
            for (String n:left_.getNames()) {
                fetchTests(listTrue_, n, null, _page, cmp_, _page.getFalses());
            }
            ClassMethodIdReturn clMethImp_ = getCustCastResult(listTrue_,  left_, _page, _page.getCurrentConstraints().getCurrentConstraints(), cmp_);
            if (clMethImp_ != null) {
                test_ = clMethImp_;
                addBinaries(_page, left_, right_, listsBinary_);
            }
        } else if (StringUtil.quickEq(_op,"||")) {
            CustList<MethodInfo> listTrue_ = new CustList<MethodInfo>();
            for (String n:left_.getNames()) {
                fetchTests(listTrue_,n, null, _page, cmp_, _page.getTrues());
            }
            ClassMethodIdReturn clMethImp_ = getCustCastResult(listTrue_,  left_, _page, _page.getCurrentConstraints().getCurrentConstraints(), cmp_);
            if (clMethImp_ != null) {
                test_ = clMethImp_;
                addBinaries(_page, left_, right_, listsBinary_);
            }
        } else {
            addBinaries(_page, left_, right_, listsBinary_);
        }
        ClassMethodIdReturn clMethImp_ = getCustResult(false, -1, listsBinary_, _op, pair_, _page);
        if (clMethImp_ != null) {
            CustList<OperationNode> chidren_ = _node.getChildrenNodes();
            _node.setResultClass(voidToObject(new AnaClassArgumentMatching(clMethImp_.getReturnType(), _page.getPrimitiveTypes()), _page));
            MethodId id_ = clMethImp_.getRealId();
            InvokingOperation.unwrapArgsFct(id_, -1, EMPTY_STRING, chidren_, _page);
            OperatorConverter op_ = new OperatorConverter(clMethImp_);
            op_.setTest(test_);
            return op_;
        }
        if (!listsBinary_.isEmpty()) {
            ClassMethodIdReturn clId_ = getCustomOperatorOrMethod(_node, _op, _page);
            if (clId_ != null) {
                OperatorConverter op_ = new OperatorConverter(clId_);
                op_.setTest(test_);
                return op_;
            }
        }
        CustList<CustList<ParamReturn>> groups_ = new CustList<CustList<ParamReturn>>();
        if (StringExpUtil.isBinNum(_op)) {
            CustList<ParamReturn> group_ = new CustList<ParamReturn>();
            group_.add(new ParamReturn(_page.getAliasPrimInteger(), _page.getAliasPrimInteger()));
            group_.add(new ParamReturn(_page.getAliasPrimLong(), _page.getAliasPrimLong()));
            groups_.add(group_);
            group_ = new CustList<ParamReturn>();
            group_.add(new ParamReturn(_page.getAliasPrimFloat(), _page.getAliasPrimFloat()));
            group_.add(new ParamReturn(_page.getAliasPrimDouble(), _page.getAliasPrimDouble()));
            groups_.add(group_);
        } else if (StringExpUtil.isCmp(_op)) {
            CustList<ParamReturn> group_ = new CustList<ParamReturn>();
            group_.add(new ParamReturn(_page.getAliasPrimInteger(), _page.getAliasPrimBoolean()));
            group_.add(new ParamReturn(_page.getAliasPrimLong(), _page.getAliasPrimBoolean()));
            groups_.add(group_);
            group_ = new CustList<ParamReturn>();
            group_.add(new ParamReturn(_page.getAliasPrimFloat(), _page.getAliasPrimBoolean()));
            group_.add(new ParamReturn(_page.getAliasPrimDouble(), _page.getAliasPrimBoolean()));
            groups_.add(group_);
        } else if (StringExpUtil.isBitwise(_op)) {
            CustList<ParamReturn> group_ = new CustList<ParamReturn>();
            group_.add(new ParamReturn(_page.getAliasPrimBoolean(), _page.getAliasPrimBoolean()));
            group_.add(new ParamReturn(_page.getAliasPrimInteger(), _page.getAliasPrimInteger()));
            group_.add(new ParamReturn(_page.getAliasPrimLong(), _page.getAliasPrimLong()));
            groups_.add(group_);
        } else if (StringExpUtil.isLogical(_op)) {
            CustList<ParamReturn> group_ = new CustList<ParamReturn>();
            group_.add(new ParamReturn(_page.getAliasPrimBoolean(), _page.getAliasPrimBoolean()));
            groups_.add(group_);
        } else if (StringExpUtil.isShiftOper(_op)) {
            CustList<ParamReturn> group_ = new CustList<ParamReturn>();
            group_.add(new ParamReturn(_page.getAliasPrimInteger(), _page.getAliasPrimInteger()));
            group_.add(new ParamReturn(_page.getAliasPrimLong(), _page.getAliasPrimLong()));
            groups_.add(group_);
        }
        for (CustList<ParamReturn> g: groups_) {
            CustList<CustList<MethodInfo>> lists_ = new CustList<CustList<MethodInfo>>();
            CustList<MethodInfo> list_ = new CustList<MethodInfo>();
            for (ParamReturn p:g) {
                addVirtual(_op,_page,list_,p.getParamType(),p.getReturnType(),new StringList(p.getParamType(),p.getParamType()));
            }
            lists_.add(list_);
            ClassMethodIdReturn clMeth_ = getCustResult(false, -1, lists_, _op, pair_, _page);
            if (clMeth_ != null) {
                CustList<OperationNode> chidren_ = _node.getChildrenNodes();
                _node.setResultClass(voidToObject(new AnaClassArgumentMatching(clMeth_.getReturnType(), _page.getPrimitiveTypes()), _page));
                MethodId id_ = clMeth_.getRealId();
                InvokingOperation.unwrapArgsFct(id_, -1, EMPTY_STRING, chidren_, _page);
                return new OperatorConverter(clMeth_);
            }
        }
        return null;
    }

    private static void addBinaries(AnalyzedPageEl _page, AnaClassArgumentMatching _left, AnaClassArgumentMatching _right, CustList<CustList<MethodInfo>> _listsBinary) {
        CustList<MethodInfo> listBinary_ = new CustList<MethodInfo>();
        for (String n : _left.getNames()) {
            for (String o : _right.getNames()) {
                fetchBinary(listBinary_, n, o, _page);
            }
        }
        _listsBinary.add(listBinary_);
    }

    private static ClassMethodIdReturn getCustomOperatorOrMethod(MethodOperation _node, String _op, AnalyzedPageEl _page) {
        StringList bounds_ = _page.getTypesWithInnerOperators();
        CustList<OperationNode> chidren_ = _node.getChildrenNodes();
        ClassMethodIdReturn clMeth_ = tryGetDeclaredCustMethod(
                bounds_, _op,
                chidren_, _page);
        if (clMeth_ != null) {
            _node.setResultClass(voidToObject(new AnaClassArgumentMatching(clMeth_.getReturnType(), _page.getPrimitiveTypes()), _page));
            MethodId realId_ = clMeth_.getRealId();
            InvokingOperation.unwrapArgsFct(realId_, -1, EMPTY_STRING, chidren_, _page);
            return clMeth_;
        }
        //implicit use of operator key word
        ClassMethodIdReturn cust_ = getOperator(_op, chidren_, _page);
        if (cust_ != null) {
            _node.setResultClass(voidToObject(new AnaClassArgumentMatching(cust_.getReturnType(), _page.getPrimitiveTypes()), _page));
            MethodId realId_ = cust_.getRealId();
            InvokingOperation.unwrapArgsFct(realId_, -1, EMPTY_STRING, chidren_, _page);
            return cust_;
        }
        return null;
    }
    private static ClassMethodIdReturn getCustomIncrDecrOperatorOrMethod(MethodOperation _node, OperationNode _arg, String _op, AnalyzedPageEl _page) {
        StringList bounds_ = _page.getTypesWithInnerOperators();
        ClassMethodIdReturn clMeth_ = tryGetDeclaredCustIncrDecrMethod(
                bounds_, _op,
                _arg.getResultClass(), _page);
        if (clMeth_ != null) {
            _node.setResultClass(voidToObject(new AnaClassArgumentMatching(clMeth_.getReturnType(), _page.getPrimitiveTypes()), _page));
            MethodId realId_ = clMeth_.getRealId();
            InvokingOperation.unwrapArgsFct(realId_, -1, EMPTY_STRING, new CustList<OperationNode>(_arg), _page);
            return clMeth_;
        }
        ClassMethodIdReturn cust_ = getIncrDecrOperator(_op, _arg.getResultClass(), _page);
        if (cust_ != null) {
            _node.setResultClass(voidToObject(new AnaClassArgumentMatching(cust_.getReturnType(), _page.getPrimitiveTypes()), _page));
            MethodId realId_ = cust_.getRealId();
            InvokingOperation.unwrapArgsFct(realId_, -1, EMPTY_STRING, new CustList<OperationNode>(_arg), _page);
            return cust_;
        }
        return null;
    }
    private static boolean isNativeUnaryOperator(AnaClassArgumentMatching _operand, String _op, AnalyzedPageEl _page) {
        if (StringUtil.quickEq(_op,"!")) {
            return _operand.isBoolType(_page);
        }
        if (StringUtil.quickEq(_op,"~")) {
            int order_ = AnaTypeUtil.getIntOrderClass(_operand, _page);
            return order_ != 0;
        }
        return AnaTypeUtil.isPureNumberClass(_operand, _page);
    }
    private static boolean isNativeBinaryOperator(AnaClassArgumentMatching _left, AnaClassArgumentMatching _right, String _op, AnalyzedPageEl _page) {
        if (StringUtil.quickEq(_op,"+")) {
            if (AnaTypeUtil.isIntOrderClass(_left,_right, _page)) {
                return true;
            }
            if (AnaTypeUtil.isFloatOrderClass(_left,_right, _page)) {
                return true;
            }
            if (_left.matchClass(_page.getAliasString())) {
                if (_right.matchClass(_page.getAliasNumber())) {
                    return true;
                }
                if (AnaTypeUtil.isPureNumberClass(_right, _page)) {
                    return true;
                }
                if (_right.isBoolType(_page)) {
                    return true;
                }
                if (_right.matchClass(_page.getAliasString())) {
                    return true;
                }
                if (_right.matchClass(_page.getAliasStringBuilder())) {
                    return true;
                }
                return _right.matchClass(_page.getAliasObject());
            }
            if (_right.matchClass(_page.getAliasString())) {
                if (_left.matchClass(_page.getAliasNumber())) {
                    return true;
                }
                if (AnaTypeUtil.isPureNumberClass(_left, _page)) {
                    return true;
                }
                if (_left.isBoolType(_page)) {
                    return true;
                }
                return _left.matchClass(_page.getAliasObject());
            }
            return false;
        }
        if (StringExpUtil.isBinNum(_op)) {
            if (AnaTypeUtil.isIntOrderClass(_left,_right, _page)) {
                return true;
            }
            return AnaTypeUtil.isFloatOrderClass(_left, _right, _page);
        }
        if (StringExpUtil.isCmp(_op)) {
            if (AnaTypeUtil.isIntOrderClass(_left,_right, _page)) {
                return true;
            }
            if (AnaTypeUtil.isFloatOrderClass(_left,_right, _page)) {
                return true;
            }
            return _left.matchClass(_page.getAliasString())
                    &&_right.matchClass(_page.getAliasString());
        }
        if (StringExpUtil.isBitwise(_op)) {
            if (AnaTypeUtil.isIntOrderClass(_left,_right, _page)) {
                return true;
            }
            return _left.isBoolType(_page)&&_right.isBoolType(_page);
        }
        if (StringExpUtil.isLogical(_op)) {
            return _left.isBoolType(_page)&&_right.isBoolType(_page);
        }
        if (StringExpUtil.isShiftOper(_op)) {
            return AnaTypeUtil.isIntOrderClass(_left,_right, _page);
        }
        if (AnaTypeUtil.isIntOrderClass(_left,_right, _page)) {
            return true;
        }
        if (AnaTypeUtil.isFloatOrderClass(_left,_right, _page)) {
            return true;
        }
        if (_left.matchClass(_page.getAliasNumber())
                &&_right.matchClass(_page.getAliasNumber())) {
            return true;
        }
        if (_left.isBoolType(_page)&&_right.isBoolType(_page)) {
            return true;
        }
        if (_left.matchClass(_page.getAliasString())
                &&_right.matchClass(_page.getAliasString())) {
            return true;
        }
        return _left.matchClass(_page.getAliasObject())
                && _right.matchClass(_page.getAliasObject());
    }

    private static ClassMethodIdReturn getIncrDecrOperator(String _op, AnaClassArgumentMatching _argsClass, AnalyzedPageEl _page) {
        //implicit use of operator key word
        CustList<MethodInfo> ops_ = getOperators(false, null, _page);
        CustList<CustList<MethodInfo>> o_ = new CustList<CustList<MethodInfo>>();
        o_.add(ops_);
        return getCustIncrDecrResult(o_, _op, _argsClass, _page);
    }
    static ClassMethodIdReturn getOperator(String _op, CustList<OperationNode> _argsClass, AnalyzedPageEl _page) {
        CustList<MethodInfo> ops_ = getOperators(false, null, _page);
        boolean uniq_ = uniq((ClassMethodId) null, -1);
        int varargOnly_ = fetchVarargOnlyBis(null, -1);
        CustList<CustList<MethodInfo>> o_ = new CustList<CustList<MethodInfo>>();
        o_.add(ops_);
        return getCustResult(uniq_, varargOnly_, o_, _op, _argsClass, _page);
    }
    static ClassMethodIdReturn getOperator(boolean _refRet, ClassMethodId _cl, int _varargOnly,
                                           String _op, String _param, NameParametersFilter _filter, AnalyzedPageEl _page) {
        CustList<MethodInfo> ops_ = getOperators(_refRet, _cl, _page);
        boolean uniq_ = uniq(_cl,_varargOnly);
        int varargOnly_ = fetchVarargOnlyBis(_cl, _varargOnly);
        CustList<CustList<MethodInfo>> o_ = new CustList<CustList<MethodInfo>>();
        o_.add(ops_);
        return getCustResult(uniq_, false,varargOnly_, o_, _op, _param,_filter, _page);
    }

    static ClassMethodIdReturn getOperatorLambda(ClassMethodId _cl, int _varargOnly,
                                                 String _op, AnalyzedPageEl _page, StringList _argsClass) {
        CustList<MethodInfo> ops_ = getOperators(false, _cl, _page);
        int varargOnly_ = fetchVarargOnlyBis(_cl, _varargOnly);
        CustList<CustList<MethodInfo>> o_ = new CustList<CustList<MethodInfo>>();
        o_.add(ops_);
        return getCustResultLambda(varargOnly_, o_, _op, _page, _argsClass);
    }

    private static int fetchVarargOnlyBis(ClassMethodId _cl, int _varargOnly) {
        int varargOnly_ = _varargOnly;
        if (_cl != null) {
            varargOnly_ = -1;
        }
        return varargOnly_;
    }

    private static boolean uniq(ClassMethodId _cl, int _varargOnly) {
        boolean uniq_ = false;
        if (_cl != null) {
            uniq_ = isUniqCtor(_varargOnly);
        }
        return uniq_;
    }

    private static boolean uniq(ClassMethodIdAncestor _cl, int _varargOnly) {
        boolean uniq_ = false;
        if (_cl != null) {
            uniq_ = isUniqCtor(_varargOnly);
        }
        return uniq_;
    }
    static CustList<MethodInfo> getOperators(boolean _retRef, ClassMethodId _cl, AnalyzedPageEl _page){
        CustList<MethodInfo> methods_;
        methods_ = new CustList<MethodInfo>();
        for (OperatorBlock e: _page.getAllOperators()) {
            if (_retRef) {
                if (!e.isRetRef()) {
                    continue;
                }
            }
            String ret_ = e.getImportedReturnType();
            MethodId id_ = e.getId();
            if (_cl != null) {
                if (!_cl.getConstraints().eq(id_)) {
                    continue;
                }
            }
            MethodInfo mloc_ = new MethodInfo();
            mloc_.classMethodId("",id_);
            mloc_.setReturnType(ret_);
            mloc_.setOriginalReturnType(ret_);
            mloc_.memberId(e);
            mloc_.setFileName(e.getFile().getFileName());
            mloc_.format(true, _page);
            methods_.add(mloc_);
        }
        return methods_;
    }

    private static CustList<MethodInfo> comCast(ClassMethodId _uniqueId, AnalyzedPageEl _page, AbstractComparer _cmp, StringMap<CustList<MethodHeaderInfo>> _one, StringMap<CustList<MethodHeaderInfo>> _two, StringMap<CustList<MethodHeaderInfo>> _three, PairMatchCast _pair) {
        CustList<MethodInfo> methods_ = getDeclaredCustImplicitCastFrom(_pair.getFrom(),_uniqueId, _page, _cmp, _one, _two);
        for (String n: _pair.getMatch().getNames()) {
            methods_.addAllElts(getDeclaredCustImplicitCast(_pair.getFrom(), _uniqueId, n, _page, _cmp, _three));
        }
        return methods_;
    }

    private static CustList<MethodInfo> getDeclaredCustImplicitCast(String _fromClass, ClassMethodId _uniqueId, String _single, AnalyzedPageEl _page, AbstractComparer _cmp, StringMap<CustList<MethodHeaderInfo>> _methods) {
        CustList<MethodInfo> methods_;
        methods_ = new CustList<MethodInfo>();
        String idFrom_ = StringExpUtil.getIdFromAllTypes(_single);
        CustList<MethodHeaderInfo> castsFrom_ = _methods.getVal(idFrom_);
        methods_.addAllElts(fetchCastMethods(_uniqueId, _fromClass,_single, castsFrom_, _page, _page.getCurrentConstraints().getCurrentConstraints(), _cmp));
        return methods_;
    }


    private static CustList<MethodInfo> getDeclaredCustImplicitCastFrom(String _fromClass, ClassMethodId _uniqueId, AnalyzedPageEl _page, AbstractComparer _cmp, StringMap<CustList<MethodHeaderInfo>> _listOne, StringMap<CustList<MethodHeaderInfo>> _listTwo) {
        CustList<MethodInfo> methods_;
        methods_ = new CustList<MethodInfo>();
        String id_ = StringExpUtil.getIdFromAllTypes(_fromClass);
        CustList<MethodHeaderInfo> casts_ = _listOne.getVal(id_);
        CustList<MethodHeaderInfo> castsId_ = _listTwo.getVal(id_);
        methods_.addAllElts(fetchCastMethods(_uniqueId, _fromClass,_fromClass, casts_, _page, _page.getCurrentConstraints().getCurrentConstraints(), _cmp));
        methods_.addAllElts(fetchCastMethods(_uniqueId, _fromClass,_fromClass, castsId_, _page, _page.getCurrentConstraints().getCurrentConstraints(), _cmp));
        return methods_;
    }

    private static CustList<MethodInfo> getDeclaredCustImplicitCast(String _fromClass, String _single, AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        CustList<MethodInfo> methods_;
        methods_ = new CustList<MethodInfo>();
        fetchTo(methods_, _fromClass,_fromClass, _page, _vars, _cmp);
        fetchFrom(methods_, _fromClass,_single, _page, _vars, _cmp);
        return methods_;
    }

    private static CustList<MethodInfo> getDeclaredCustImplicitCastFct(String _fromClass, AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        CustList<MethodInfo> methods_;
        methods_ = new CustList<MethodInfo>();
        fetchTo(methods_, _fromClass,_fromClass, _page, _vars, _cmp);
        return methods_;
    }

    private static void fetchFrom(CustList<MethodInfo> _methods, String _returnType, String _id, AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        if (!StringExpUtil.customCast(_id)) {
            return;
        }
        _methods.addAllElts(fetch(_id, null, _page, _cmp, _page.getImplicitFromCastMethods(), _returnType, _vars));
    }

    private static void fetchTo(CustList<MethodInfo> _methods, String _returnType, String _id, AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        if (!StringExpUtil.customCast(_id)) {
            return;
        }
        String di_ = StringExpUtil.getIdFromAllTypes(_id);
        CustList<MethodHeaderInfo> casts_ = _page.getImplicitCastMethods().getVal(di_);
        CustList<MethodHeaderInfo> castsId_ = _page.getImplicitIdCastMethods().getVal(di_);
        _methods.addAllElts(fetchCastMethods(null, _returnType,_id, casts_, _page, _vars, _cmp));
        _methods.addAllElts(fetchCastMethods(null, _returnType,_id, castsId_, _page, _vars, _cmp));
    }

    private static void fetchBinary(CustList<MethodInfo> _methods, String _first, String _second, AnalyzedPageEl _page) {
        if (StringExpUtil.customCast(_first)) {
            String di_ = StringExpUtil.getIdFromAllTypes(_first);
            RootBlock r_ = _page.getAnaClassBody(di_);
            if (r_ != null) {
                CustList<AnaFormattedRootBlock> allClasses_ = new CustList<AnaFormattedRootBlock>(new AnaFormattedRootBlock(r_));
                allClasses_.addAllElts(r_.getAllGenericSuperTypesInfo());
                for (AnaFormattedRootBlock s: allClasses_) {
                    String formatted_ = AnaInherits.quickFormat(r_,_first,s.getFormatted());
                    String supId_ = StringExpUtil.getIdFromAllTypes(formatted_);
                    CustList<MethodHeaderInfo> binaryFirst_ = _page.getBinaryFirst().getVal(supId_);
                    CustList<MethodHeaderInfo> binaryAll_ = _page.getBinaryAll().getVal(supId_);
                    fetchImproveOperators(_methods,formatted_, binaryFirst_, _page);
                    fetchImproveOperators(_methods,formatted_, binaryAll_, _page);
                }
            }
        }
        if (StringExpUtil.customCast(_second)) {
            String di_ = StringExpUtil.getIdFromAllTypes(_second);
            RootBlock r_ = _page.getAnaClassBody(di_);
            if (r_ != null) {
                CustList<AnaFormattedRootBlock> allClasses_ = new CustList<AnaFormattedRootBlock>(new AnaFormattedRootBlock(r_));
                allClasses_.addAllElts(r_.getAllGenericSuperTypesInfo());
                for (AnaFormattedRootBlock s: allClasses_) {
                    String formatted_ = AnaInherits.quickFormat(r_,_second,s.getFormatted());
                    String supId_ = StringExpUtil.getIdFromAllTypes(formatted_);
                    CustList<MethodHeaderInfo> binarySecond_ = _page.getBinarySecond().getVal(supId_);
                    CustList<MethodHeaderInfo> binaryAll_ = _page.getBinaryAll().getVal(supId_);
                    fetchImproveOperators(_methods,formatted_, binarySecond_, _page);
                    fetchImproveOperators(_methods,formatted_, binaryAll_, _page);
                }
            }
        }
    }
    private static void fetchUnary(CustList<MethodInfo> _methods, String _id, AnalyzedPageEl _page) {
        if (!StringExpUtil.customCast(_id)) {
            return;
        }
        String di_ = StringExpUtil.getIdFromAllTypes(_id);
        RootBlock r_ = _page.getAnaClassBody(di_);
        if (r_ == null) {
            return;
        }
        CustList<AnaFormattedRootBlock> allClasses_ = new CustList<AnaFormattedRootBlock>(new AnaFormattedRootBlock(r_));
        allClasses_.addAllElts(r_.getAllGenericSuperTypesInfo());
        for (AnaFormattedRootBlock s: allClasses_) {
            String formatted_ = AnaInherits.quickFormat(r_,_id,s.getFormatted());
            String supId_ = StringExpUtil.getIdFromAllTypes(formatted_);
            CustList<MethodHeaderInfo> castsFrom_ = _page.getUnary().getVal(supId_);
            fetchImproveOperators(_methods,formatted_, castsFrom_, _page);
        }
    }
    public static ClassMethodIdReturn fetchTrueOperator(AnaClassArgumentMatching _arg, AnalyzedPageEl _page) {
        StringList names_ = _arg.getNames();
        return fetchTestsOperator(names_, _arg, null, _page, new DefaultComparer(), _page.getTrues());
    }

    private static ClassMethodIdReturn fetchTestsOperator(StringList _names, AnaClassArgumentMatching _arg, ClassMethodId _uniqueId, AnalyzedPageEl _page, AbstractComparer _cmp, StringMap<CustList<MethodHeaderInfo>> _tests) {
        CustList<MethodInfo> listTrue_ = new CustList<MethodInfo>();
        for (String n: _names) {
            fetchTests(listTrue_,n, _uniqueId, _page, _cmp, _tests);
        }
        return getCustCastResult(listTrue_,  _arg, _page, _page.getCurrentConstraints().getCurrentConstraints(), _cmp);
    }
    private static void fetchTests(CustList<MethodInfo> _methods, String _id, ClassMethodId _uniqueId, AnalyzedPageEl _page, AbstractComparer _cmp, StringMap<CustList<MethodHeaderInfo>> _tests) {
        if (!StringExpUtil.customCast(_id)) {
            return;
        }
        _methods.addAllElts(fetch(_id, _uniqueId, _page, _cmp, _tests, _page.getAliasPrimBoolean(), _page.getCurrentConstraints().getCurrentConstraints()));
    }

    private static CustList<MethodInfo> fetch(String _id, ClassMethodId _uniqueId, AnalyzedPageEl _page, AbstractComparer _cmp, StringMap<CustList<MethodHeaderInfo>> _list, String _returnType, StringMap<StringList> _vars) {
        String di_ = StringExpUtil.getIdFromAllTypes(_id);
        RootBlock r_ = _page.getAnaClassBody(di_);
        if (r_ == null) {
            return new CustList<MethodInfo>();
        }
        CustList<MethodInfo> methods_ = new CustList<MethodInfo>();
        CustList<AnaFormattedRootBlock> allClasses_ = new CustList<AnaFormattedRootBlock>(new AnaFormattedRootBlock(r_));
        allClasses_.addAllElts(r_.getAllGenericSuperTypesInfo());
        for (AnaFormattedRootBlock s : allClasses_) {
            String formatted_ = AnaInherits.quickFormat(r_, _id, s.getFormatted());
            String supId_ = StringExpUtil.getIdFromAllTypes(formatted_);
            CustList<MethodHeaderInfo> castsFrom_ = _list.getVal(supId_);
            methods_.addAllElts(fetchCastMethods(_uniqueId, _returnType, formatted_, castsFrom_, _page, _vars, _cmp));
        }
        return methods_;
    }

    protected static CustList<CustList<MethodInfo>>
    getDeclaredCustMethodByType(MethodAccessKind _staticContext,
                                StringList _fromClasses, String _name, boolean _import, AnalyzedPageEl _page, ScopeFilter _sc, FormattedFilter _formattedFilter) {
        CustList<CustList<MethodInfo>> methods_;
        methods_ = new CustList<CustList<MethodInfo>>();
        fetchParamClassAncMethods(_fromClasses,_staticContext, methods_, _page, _sc, _formattedFilter);
        if (_import) {
            for (CustList<ImportedMethod> l: ResolvingImportTypes.lookupImportStaticMethods(_page.getGlobalClass(), _name, _page)) {
                CustList<MethodInfo> m_ = new CustList<MethodInfo>();
                for (ImportedMethod e:l) {
                    ClassMethodId m = e.getId();
                    String clName_ = m.getClassName();
                    MethodId id_ = m.getConstraints();
                    if (isCandidateMethod(_sc.getId(),0, clName_, id_)) {
                        continue;
                    }
                    MethodInfo mloc_ = new MethodInfo();
                    mloc_.classMethodId(e);
                    mloc_.format(true, _page);
                    mloc_.pairMemberId(e);
                    m_.add(mloc_);
                }
                methods_.add(m_);
            }
        }
        String stCall_ = _formattedFilter.getStCall();
        if (StringUtil.quickEq(stCall_,"<>")) {
            for (CustList<ImportedMethod> l: ResolvingImportTypes.lookupImportStaticCallMethods(_page.getGlobalClass(), _name, _page)) {
                CustList<MethodInfo> m_ = new CustList<MethodInfo>();
                for (ImportedMethod e:l) {
                    ClassMethodId m = e.getId();
                    String clName_ = m.getClassName();
                    MethodId id_ = m.getConstraints();
                    if (isCandidateMethod(_sc.getId(),0, clName_, id_)) {
                        continue;
                    }
                    MethodInfo mloc_ = new MethodInfo();
                    mloc_.classMethodId(e);
                    mloc_.setFormattedFilter(_formattedFilter);
                    mloc_.format(id_.getKind() == MethodAccessKind.STATIC, _page);
                    mloc_.pairMemberId(e);
                    m_.add(mloc_);
                }
                methods_.add(m_);
            }
        }
        return methods_;
    }

    protected static boolean isCandidateMethod(ClassMethodIdAncestor _uniqueId, int _ancestor,String _clName, MethodId _id) {
        if (_uniqueId != null) {
            if (_uniqueId.getAncestor() != _ancestor) {
                return true;
            }
            String className_ = _uniqueId.getClassMethodId().getClassName();
            if (!StringUtil.quickEq(StringExpUtil.getIdFromAllTypes(className_), StringExpUtil.getIdFromAllTypes(_clName))) {
                return true;
            }
            MethodId constraints_ = _uniqueId.getClassMethodId().getConstraints();
            return !constraints_.eq(_id);
        }
        return false;
    }


    public static void fetchParamClassAncMethods(StringList _fromClasses, CustList<CustList<MethodInfo>> _methods, AnalyzedPageEl _page) {
        fetchParamClassAncMethods(_fromClasses,MethodAccessKind.INSTANCE, _methods, _page, new ScopeFilter(null, true, true, false, _page.getGlobalClass()), new FormattedFilter());
    }
    private static void fetchParamClassAncMethods(StringList _fromClasses, MethodAccessKind _staticContext,
                                                  CustList<CustList<MethodInfo>> _methods, AnalyzedPageEl _page, ScopeFilter _sc, FormattedFilter _formattedFilter) {
        CustList<CustList<TypeInfo>> typeInfosGroups_ = typeLists(_fromClasses,_staticContext, _page);
        for (CustList<TypeInfo> g: typeInfosGroups_) {
            StringList baseTypes_ = new StringList();
            StringMap<String> superTypesBaseAncBis_ = new StringMap<String>();
            feedTypes(g, baseTypes_, superTypesBaseAncBis_);
            CustList<MethodInfo> methods_ = new CustList<MethodInfo>();
            for (TypeInfo t: g) {
                ScopeFilterType scType_ = new ScopeFilterType(_sc,t,t.getScope(), baseTypes_,superTypesBaseAncBis_, _formattedFilter);
                _page.getFieldFilter().fetchParamClassMethods(scType_, methods_, _page);
            }
            _methods.add(methods_);
        }
    }

    public static CustList<CustList<TypeInfo>> typeLists(StringList _fromClasses, MethodAccessKind _staticContext, AnalyzedPageEl _page) {
        CustList<TypeInfo> typeInfos_ = new CustList<TypeInfo>();
        CustList<CustList<TypeInfo>> typeInfosMap_ = new CustList<CustList<TypeInfo>>();
        for (String s: _fromClasses) {
            String baseCurName_ = StringExpUtil.getIdFromAllTypes(s);
            AnaGeneType root_ = _page.getAnaGeneType(baseCurName_);
            if (root_ instanceof RootBlock) {
                AnaFormattedRootBlock f_ = new AnaFormattedRootBlock((RootBlock)root_,s);
                addToList(typeInfos_,_staticContext,f_, new AnaFormattedRootBlock((RootBlock)root_), 0,true);
                for (AnaFormattedRootBlock m: ((RootBlock)root_).getAllGenericSuperTypesInfo()) {
                    addToList(typeInfos_,_staticContext,f_, m, 0,false);
                }
            }
            if (root_ instanceof StandardType) {
                String gene_ = root_.getGenericString();
                addToList(typeInfos_,_staticContext, (StandardType)root_,gene_, true);
                for (String m : ((StandardType)root_).getAllGenericSuperTypes()) {
                    StandardType sup_ = _page.getStandardsTypes().getVal(m);
                    addToList(typeInfos_,_staticContext, sup_,m, false);
                }
            }

        }
        typeInfosMap_.add(typeInfos_);
        int max_ = 0;
        for (TypeInfo t: typeInfos_) {
            AnaFormattedRootBlock form_ = t.getFormatted();
            RootBlock root_ = form_.getRootBlock();
            if (root_ == null) {
                continue;
            }
            CustList<RootBlock> pars_ = root_.getAllParentTypes();
            max_ = Math.max(max_,pars_.size());
        }
        for (int i = 1; i <= max_; i++) {
            typeInfosMap_.add(new CustList<TypeInfo>());
        }
        for (TypeInfo t: typeInfos_) {
            AnaFormattedRootBlock form_ = t.getFormatted();
            RootBlock root_ = form_.getRootBlock();
            if (root_ == null) {
                continue;
            }
            boolean add_ = !root_.withoutInstance();
            int anc_ = 1;
            MethodAccessKind scope_ = _staticContext;
            for (RootBlock p: root_.getAllParentTypes()) {
                CustList<TypeInfo> typeInfosInt_ = typeInfosMap_.get(anc_);
                if (!add_) {
                    scope_ = MethodAccessKind.STATIC;
                }
                addToList(typeInfosInt_,scope_,form_, new AnaFormattedRootBlock(p), anc_,true);
                for (AnaFormattedRootBlock m: p.getAllGenericSuperTypesInfo()) {
                    addToList(typeInfosInt_,scope_, form_, m, anc_,false);
                }
                if (p.withoutInstance()) {
                    add_ = false;
                }
                anc_++;
            }
        }
        return typeInfosMap_;
    }
    private static void addToList(CustList<TypeInfo> _list, MethodAccessKind _k, AnaFormattedRootBlock _f, AnaFormattedRootBlock _s, int _anc, boolean _base) {
        MethodAccessKind k_ = _k;
        AnaFormattedRootBlock type_;
        if (AnaInherits.correctNbParameters(_f.getRootBlock(),_f.getFormatted())) {
            type_ = AnaFormattedRootBlock.format(_f, _s);
        } else {
            k_ = MethodAccessKind.STATIC;
            type_ = _s;
        }
        TypeInfo t_ = new TypeInfo(type_, k_,_base,_anc);
        AnaGeneType r_ = t_.getRoot();
        for (TypeInfo t: _list) {
            if (t.getRoot() == r_) {
                return;
            }
        }
        _list.add(t_);
    }

    private static void addToList(CustList<TypeInfo> _list, MethodAccessKind _k, StandardType _secondType, String _second, boolean _base) {
        TypeInfo t_ = new TypeInfo(_secondType, _second, _k,_base);
        _list.add(t_);
    }

    private static CustList<MethodInfo> fetchCastMethods(ClassMethodId _uniqueId, String _returnType, String _cl, CustList<MethodHeaderInfo> _casts, AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        CustList<MethodInfo> methods_ = new CustList<MethodInfo>();
        ClassMethodIdAncestor uniq_ = tryBuildUniq(_uniqueId);
        for (MethodHeaderInfo e: nullToEmpty(_casts)) {
            MethodInfo stMeth_ = fetchedParamCastMethod(e,_returnType,_cl, uniq_, _page, _vars, _cmp);
            if (stMeth_ == null) {
                continue;
            }
            methods_.add(stMeth_);
        }
        return methods_;
    }

    private static ClassMethodIdAncestor tryBuildUniq(ClassMethodId _uniqueId) {
        ClassMethodIdAncestor uniq_ = null;
        if (_uniqueId != null) {
            uniq_ = new ClassMethodIdAncestor(new ClassMethodId(StringExpUtil.getIdFromAllTypes(_uniqueId.getClassName()), _uniqueId.getConstraints()),0);
        }
        return uniq_;
    }

    private static void fetchImproveOperators(CustList<MethodInfo> _methods, String _cl, CustList<MethodHeaderInfo> _casts, AnalyzedPageEl _page) {
        for (MethodHeaderInfo e: nullToEmpty(_casts)) {
            MethodInfo stMeth_ = fetchedParamImproveOperator(e, _cl, _page);
            _methods.add(stMeth_);
        }
    }
    private static CustList<MethodHeaderInfo> nullToEmpty(CustList<MethodHeaderInfo> _list) {
        if (_list == null) {
            return new CustList<MethodHeaderInfo>();
        }
        return _list;
    }

    public static void fetchParamClassMethods(ScopeFilterType _refRet,
                                              CustList<MethodInfo> _methods,
                                              AnalyzedPageEl _page) {
        AnaGeneType g_ = _refRet.getTypeInfo().getRoot();
        if (g_ instanceof RootBlock) {
            CustList<NamedCalledFunctionBlock> methods_ = new CustList<NamedCalledFunctionBlock>();
            for (NamedCalledFunctionBlock b: ((RootBlock) g_).getOverridableBlocks()) {
                methods_.add(b);
            }
            for (NamedCalledFunctionBlock b: ((RootBlock) g_).getAnnotationsMethodsBlocks()) {
                methods_.add(b);
            }
            for (NamedCalledFunctionBlock e: methods_) {
                MethodId id_ = e.getId();
                MethodAccessKind k_ = id_.getKind();
                if (filter(_refRet, id_.isRetRef(), k_)) {
                    continue;
                }
                MethodInfo stMeth_ = fetchedParamMethodCust(e, _refRet, _page, id_, e.getImportedReturnType());
                if (stMeth_ == null) {
                    continue;
                }
                _methods.add(stMeth_);
            }
            for (MethodInfo e: getPredefineStaticEnumMethods((RootBlock) g_, _refRet.getAnc(), _page)) {
                MethodId id_ = e.getConstraints();
                if (isCandidateMethod(_refRet.getId(),_refRet.getAnc(), _refRet.getFullName(), id_)) {
                    continue;
                }
                _methods.add(e);
            }
        }
        if (g_ instanceof StandardType) {
            String genericString_ = g_.getGenericString();
            for (StandardMethod e: ((StandardType) g_).getMethods()) {
                MethodId id_ = e.getId();
                MethodAccessKind k_ = id_.getKind();
                if (filter(_refRet, id_.isRetRef(), k_)) {
                    continue;
                }
                MethodInfo stMeth_ = fetchedParamMethod(e, _refRet, genericString_, _page, e.getId(), e.getImportedReturnType());
                if (stMeth_ == null) {
                    continue;
                }
                _methods.add(stMeth_);
            }
        }
    }

    private static boolean filter(ScopeFilterType _sc, boolean _retRef, MethodAccessKind _k) {
        MethodAccessKind kind_ = _sc.getKind();
        if (_sc.isRetRef()) {
            if (!_retRef) {
                return true;
            }
        }
        if (kind_ == MethodAccessKind.STATIC) {
            if (_k != MethodAccessKind.STATIC) {
                return true;
            }
        }
        if (kind_ == MethodAccessKind.STATIC_CALL) {
            if (_k != MethodAccessKind.STATIC) {
                if (_k != MethodAccessKind.STATIC_CALL) {
                    return true;
                }
            }
        }
        return filterMember(_sc.isBaseClass(), _sc.isSuperClass(), _sc.getSuperTypesBase(), _sc.getFullName());
    }

    private static boolean filterMember(boolean _baseClass, boolean _superClass,StringList _superTypesBase, String _fullName) {
        if (!_baseClass) {
            if (StringUtil.contains(_superTypesBase, _fullName)) {
                return true;
            }
        }
        if (!_superClass) {
            return !StringUtil.contains(_superTypesBase, _fullName);
        }
        return false;
    }

    private static MethodInfo fetchedParamMethod(StandardMethod _m, ScopeFilterType _scType, String _s,
                                                 AnalyzedPageEl _page, MethodId _id, String _importedReturnType) {
        String base_ = StringExpUtil.getIdFromAllTypes(_s);
        if (isCandidateMethod(_scType.getId(),_scType.getAnc(), base_, _id)) {
            return null;
        }
        String formattedClass_ = getFormattedClass(_s, _scType.getFormatted().getFormatted(), _page, base_);
        return getMethodInfo(_m, _scType.getAnc(), formattedClass_, _page, _id, _importedReturnType, _scType.getFormattedFilter());
    }

    private static MethodInfo fetchedParamMethodCust(NamedCalledFunctionBlock _m, ScopeFilterType _scType,
                                                     AnalyzedPageEl _page, MethodId _id, String _importedReturnType) {
        AnaFormattedRootBlock f_ = _scType.getFormatted();
        RootBlock r_ = f_.getRootBlock();
        String formattedClass_ = f_.getFormatted();
        String base_ = StringExpUtil.getIdFromAllTypes(formattedClass_);
        if (isCandidateMethod(_scType.getId(),_scType.getAnc(), base_, _id)) {
            return null;
        }
        if (AbsBk.isOverBlock(_m)) {
            Accessed a_ = new Accessed(_m.getAccess(), r_.getPackageName(), r_);
            if (cannotAccess(base_,a_,_scType.getGlClass(),_scType.getSuperTypesBaseAncBis(), _page)) {
                return null;
            }
        }
        return buildMethodInfoCust(_m,_scType, _page, _id, _importedReturnType);
    }

    private static String getFormattedClass(String _s, String _f, AnalyzedPageEl _page, String _base) {
        String formattedClass_;
        if (StringUtil.quickEq(_base, _page.getAliasFct())) {
            formattedClass_ = _s;
        } else {
            formattedClass_ = _f;
        }
        return formattedClass_;
    }

    private static MethodInfo fetchedParamCastMethod(MethodHeaderInfo _m, String _returnType, String _s,
                                                     ClassMethodIdAncestor _uniqueId,
                                                     AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        String base_ = StringExpUtil.getIdFromAllTypes(_s);
        StringMap<String> superTypesBaseAncBis_ = new StringMap<String>();
        superTypesBaseAncBis_.addEntry(base_, base_);
        MethodId id_ = _m.getId();
        if (isCandidateMethod(_uniqueId, 0, base_, id_)) {
            return null;
        }
        RootBlock root_ = _m.getRoot();
        Accessed a_ = new Accessed(_m.getAccess(), root_.getPackageName(), root_);
        String glClass_ = _page.getGlobalClass();
        if (cannotAccess(base_, a_,glClass_,superTypesBaseAncBis_, _page)) {
            return null;
        }
        return buildCastMethodInfo(_m,_uniqueId, _returnType,_s, _page, _vars, _cmp);
    }

    private static MethodInfo fetchedParamImproveOperator(MethodHeaderInfo _m, String _s, AnalyzedPageEl _page) {
        return buildImproveOperatorInfo(_m, _s, _page);
    }

    private static boolean cannotAccess(String _base, Accessed _acc,
                                        String _glClass, StringMap<String> _superTypesBaseMap, AnalyzedPageEl _page) {
        String subType_ = _superTypesBaseMap.getVal(_base);
        if (!ContextUtil.canAccess(subType_,_acc, _page)) {
            return true;
        }
        return !ContextUtil.canAccess(_glClass,_acc, _page);
    }

    private static MethodInfo buildMethodInfoCust(NamedCalledFunctionBlock _m, ScopeFilterType _scType, AnalyzedPageEl _page, MethodId _id, String _importedReturnType) {
        AnaFormattedRootBlock f_ = _scType.getFormatted();
        RootBlock r_ = f_.getRootBlock();
        String formattedClass_ = f_.getFormatted();
        MethodInfo mloc_ = new MethodInfo();
        mloc_.setFileName(_m.getFile().getFileName());
        if (_m.isMatchParamNames()) {
            mloc_.setParametersNames(_m.getParametersNames());
        }
        mloc_.pairMemberId(formattedClass_,_page,_importedReturnType,r_,_m,_id);
        mloc_.setAncestor(_scType.getAnc());
        mloc_.setFormattedFilter(_scType.getFormattedFilter());
        mloc_.format(_id.getKind() == MethodAccessKind.STATIC, _page);
        return mloc_;
    }

    public static MethodInfo getMethodInfo(StandardMethod _m, int _anc, String _formattedClass, AnalyzedPageEl _page, MethodId _id, String _importedReturnType, FormattedFilter _formatted) {
        MethodInfo mloc_ = new MethodInfo();
        mloc_.types(_formattedClass,_page,_importedReturnType);
        mloc_.setStandardMethod(_m);
        mloc_.setParametersNames(_m.getParametersNames());
        mloc_.classMethodId(_formattedClass,_id);
        mloc_.setAncestor(_anc);
        mloc_.setFormattedFilter(_formatted);
        mloc_.format(_id.getKind() == MethodAccessKind.STATIC, _page);
        return mloc_;
    }

    private static MethodInfo buildCastMethodInfo(MethodHeaderInfo _m, ClassMethodIdAncestor _uniqueId, String _returnType, String _formattedClass, AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        MethodInfo mloc_ = getOperatorMethod(_m, _formattedClass, _page);
        if (_uniqueId == null) {
            Mapping map_ = new Mapping();
            map_.setArg(mloc_.getReturnType());
            map_.setParam(_returnType);
            map_.setMapping(_vars);
            if (!_cmp.isCorrectOrNumbers(map_, _page)) {
                return null;
            }
        }
        return mloc_;
    }

    private static MethodInfo buildImproveOperatorInfo(MethodHeaderInfo _m, String _formattedClass, AnalyzedPageEl _page) {
        return getOperatorMethod(_m, _formattedClass, _page);
    }

    private static MethodInfo getOperatorMethod(MethodHeaderInfo _m, String _formattedClass, AnalyzedPageEl _page) {
        MethodInfo mloc_ = new MethodInfo();
        mloc_.pairMemberId(_formattedClass,_page,_m);
        mloc_.setAncestor(0);
        mloc_.format(false, _page);
        return mloc_;
    }

    private static CustList<MethodInfo> getPredefineStaticEnumMethods(RootBlock _r, int _ancestor, AnalyzedPageEl _page) {
        CustList<MethodInfo> methods_;
        methods_ = new CustList<MethodInfo>();
        if (!(_r instanceof EnumBlock)) {
            return methods_;
        }
        String idClass_ = StringExpUtil.getIdFromAllTypes(_r.getGenericString());
        String wildCardForm_ = _r.getWildCardString();
        String string_ = _page.getAliasString();
        String returnType_ = wildCardForm_;
        String valueOf_ = _page.getAliasEnumPredValueOf();
        MethodId realId_ = new MethodId(MethodAccessKind.STATIC, valueOf_, new StringList(string_));
        MethodInfo mloc_ = new MethodInfo();
        mloc_.setFileName(_r.getFile().getFileName());
        mloc_.classMethodId(idClass_,realId_);
        mloc_.format(true, _page);
        mloc_.setReturnType(returnType_);
        mloc_.setOriginalReturnType(returnType_);
        mloc_.setAncestor(_ancestor);
        mloc_.memberId(_r);
        methods_.add(mloc_);
        String values_ = _page.getAliasEnumValues();
        realId_ = new MethodId(MethodAccessKind.STATIC, values_, new StringList());
        mloc_ = new MethodInfo();
        mloc_.setFileName(_r.getFile().getFileName());
        mloc_.classMethodId(idClass_,realId_);
        mloc_.format(true, _page);
        returnType_ = StringExpUtil.getPrettyArrayType(returnType_);
        mloc_.setReturnType(returnType_);
        mloc_.setOriginalReturnType(returnType_);
        mloc_.setAncestor(_ancestor);
        mloc_.memberId(_r);
        methods_.add(mloc_);
        return methods_;
    }

    private static ClassMethodIdReturn getCustResultLambda(int _varargOnly,
                                                           CustList<CustList<MethodInfo>> _methods,
                                                           String _name, AnalyzedPageEl _page, StringList _argsClass) {
        CustList<CustList<MethodInfo>> signatures_ = new CustList<CustList<MethodInfo>>();
        for (CustList<MethodInfo> l: _methods) {
            CustList<MethodInfo> m_ = new CustList<MethodInfo>();
            for (MethodInfo e: l) {
                MethodId id_ = e.getConstraints();
                boolean varArg_ = id_.isVararg();
                if (_varargOnly > -1) {
                    if (!varArg_) {
                        continue;
                    }
                }
                if (!StringUtil.quickEq(id_.getName(), _name)) {
                    continue;
                }
                if (!isPossibleMethodLambda(e, _page, _argsClass)) {
                    continue;
                }
                m_.add(e);
            }
            signatures_.add(m_);
        }
        return tryGetLambdaResult(_page, signatures_);
    }

    protected static ClassMethodIdReturn getCustResultLambdaInfer(CustList<CustList<MethodInfo>> _methods,
                                                                String _name, AnalyzedPageEl _page, String _stCall, StringList _argsClass, String _retType) {
        CustList<CustList<MethodInfo>> next_ = filterInferredMethods(_methods, _name, _page, _stCall, _argsClass, _retType);

        return tryGetLambdaResult(_page, next_);
    }

    private static ClassMethodIdReturn tryGetLambdaResult(AnalyzedPageEl _page, CustList<CustList<MethodInfo>> _next) {
        StringMap<StringList> map_;
        map_ = _page.getCurrentConstraints().getCurrentConstraints();
        ArgumentsGroup gr_ = new ArgumentsGroup(_page, map_);
        return tryGetResult(gr_, _next);
    }

    protected static CustList<CustList<MethodInfo>> filterInferredMethods(CustList<CustList<MethodInfo>> _methods, String _name, AnalyzedPageEl _page, String _stCall, StringList _argsClass, String _retType) {
        CustList<CustList<MethodInfo>> infs_;
        if (_argsClass != null &&!_stCall.isEmpty()) {
            infs_ = new CustList<CustList<MethodInfo>>();
            CustList<AnaClassArgumentMatching> args_ = new CustList<AnaClassArgumentMatching>();
            for (String o: _argsClass) {
                args_.add(new AnaClassArgumentMatching(o));
            }
            for (CustList<MethodInfo> l: _methods) {
                CustList<MethodInfo> m_ = new CustList<MethodInfo>();
                for (MethodInfo e: l) {
                    if (e.getConstraints().getKind() == MethodAccessKind.STATIC) {
                        m_.add(e);
                        continue;
                    }
                    String result_ = AnaTemplates.tryInferMethod(-1, e.getClassName(), e.getConstraints(), _stCall,
                            _page.getCurrentConstraints().getCurrentConstraints(), args_, e.getOriginalReturnType(), _retType, _page);
                    if (result_.isEmpty()) {
                        continue;
                    }
                    e.reformat(result_, _page);
                    m_.add(e);
                }
                infs_.add(m_);
            }
        } else {
            infs_ = _methods;
        }
        CustList<CustList<MethodInfo>> signatures_ = new CustList<CustList<MethodInfo>>();
        for (CustList<MethodInfo> l: infs_) {
            CustList<MethodInfo> m_ = new CustList<MethodInfo>();
            for (MethodInfo e: l) {
                MethodId id_ = e.getConstraints();
                if (!StringUtil.quickEq(id_.getName(), _name)) {
                    continue;
                }
                if (!isPossibleMethodLambdaInfer(e, _page, _argsClass)) {
                    continue;
                }
                m_.add(e);
            }
            signatures_.add(m_);
        }
        CustList<CustList<MethodInfo>> next_;
        if (_argsClass == null) {
            next_ = signatures_;
        } else {
            next_ = new CustList<CustList<MethodInfo>>();
            for (CustList<MethodInfo> l: signatures_) {
                CustList<MethodInfo> m_ = new CustList<MethodInfo>();
                for (MethodInfo e: l) {
                    MethodId id_ = e.getConstraints();
                    if (!matchRefInf(_retType,id_.isRetRef())) {
                        continue;
                    }
                    if (!subsType(e.getReturnType(), _retType,id_.isRetRef(), _page)) {
                        continue;
                    }
                    m_.add(e);
                }
                next_.add(m_);
            }
        }
        return next_;
    }

    private static boolean subsType(String _arg, String _param, boolean _ref,AnalyzedPageEl _page) {
        if (StringUtil.quickEq(_arg,"?")) {
            return true;
        }
        StringMap<StringList> mapCtr_ = _page.getCurrentConstraints().getCurrentConstraints();
        String realParam_ = _param;
        if (_param.startsWith("~")) {
            realParam_ = _param.substring(1);
        } else if (StringUtil.quickEq(_param,"?")) {
            realParam_ = _page.getAliasObject();
        }
        String realArg_ = _arg;
        if (_arg.startsWith("~")) {
            realArg_ = _arg.substring(1);
        }
        if (_ref) {
            return StringUtil.quickEq(realArg_, realParam_);
        } else {
            Mapping map_ = new Mapping();
            map_.setArg(realArg_);
            map_.getMapping().putAllMap(mapCtr_);
            map_.setParam(realParam_);
            return AnaInherits.isCorrectOrNumbers(map_, _page);
        }
    }
    private static ClassMethodIdReturn getCustResult(boolean _unique, int _varargOnly,
                                                     CustList<CustList<MethodInfo>> _methods,
                                                     String _name, CustList<OperationNode> _argsClass, AnalyzedPageEl _page) {
        NameParametersFilter name_ = new NameParametersFilter();
        for (OperationNode a: _argsClass) {
            name_.addPos(a);
        }
        return getCustResult(_unique, true,_varargOnly,_methods,_name, "",name_, _page);
    }
    private static ClassMethodIdReturn getCustResult(boolean _unique, boolean _excludeVarargRef, int _varargOnly,
                                                     CustList<CustList<MethodInfo>> _methods,
                                                     String _name, String _param, NameParametersFilter _filter, AnalyzedPageEl _page) {
        CustList<CustList<MethodInfo>> named_ = new CustList<CustList<MethodInfo>>();
        for (CustList<MethodInfo> l: _methods) {
            CustList<MethodInfo> m_ = new CustList<MethodInfo>();
            for (MethodInfo e: l) {
                if (!isPossibleMethodNamed(e,_filter)) {
                    continue;
                }
                m_.add(e);
            }
            named_.add(m_);
        }
        CustList<CustList<MethodInfo>> next_;
        if (StringUtil.quickEq(_filter.getStaticCall(), "<>") || !_filter.getStaticCall().isEmpty()) {
            next_ = infer(_varargOnly, _filter, _page, named_);
        } else {
            next_ = named_;
        }
        CustList<CustList<MethodInfo>> signatures_ = new CustList<CustList<MethodInfo>>();
        for (CustList<MethodInfo> l: next_) {
            CustList<MethodInfo> m_ = new CustList<MethodInfo>();
            for (MethodInfo e: l) {
                MethodId id_ = e.getConstraints();
                boolean varArg_ = id_.isVararg();
                boolean ref_ = id_.isRef();
                if (_varargOnly > -1) {
                    if (!varArg_) {
                        continue;
                    }
                }
                if (_excludeVarargRef) {
                    if (ref_) {
                        continue;
                    }
                    if (varArg_) {
                        continue;
                    }
                }
                if (!StringUtil.quickEq(id_.getName(), _name)) {
                    continue;
                }
//                if (!isPossibleMethod(_unique,_varargOnly,e,_param,_filter,_page)) {
//
//                }
                if (!isPossibleArgs(_unique, _varargOnly, e, _param, _page)) {
                    continue;
                }
                m_.add(e);
            }
            signatures_.add(m_);
        }
        StringMap<StringList> map_;
        map_ = _page.getCurrentConstraints().getCurrentConstraints();
        ArgumentsGroup gr_ = new ArgumentsGroup(_page, map_);
        Parametrable found_ = sortFct(signatures_, gr_);
        if (!(found_ instanceof MethodInfo)) {
            return null;
        }
        MethodInfo m_ = (MethodInfo) found_;
        StaticCallAccessOperation staticCallOp_ = _filter.getStaticCallOp();
        if (staticCallOp_ != null) {
            if (!_filter.getStaticCall().isEmpty()) {
                ContextUtil.appendTitlePartsAbs(staticCallOp_.getLt(), staticCallOp_.getGt(), m_.getClassName(), staticCallOp_.getPartOffsets(), _page);
            }
        }
        CustList<CustList<ClassMethodIdReturn>> implicits_ = m_.getImplicits();
        CustList<OperationNode> allOps_ = m_.getAllOps();
        feedImplicitsInfos(implicits_, allOps_);
        Ints nameParametersFilterIndexes_ = m_.getNameParametersFilterIndexes();
        NamedFunctionBlock custMethod_ = m_.getCustMethod();
        feedNamedParams(_filter, nameParametersFilterIndexes_, custMethod_);
        MethodId id_ = m_.getFormatted();
        return buildResult(m_, id_);
    }

    private static CustList<CustList<MethodInfo>> infer(int _varargOnly, NameParametersFilter _filter, AnalyzedPageEl _page, CustList<CustList<MethodInfo>> _named) {
        CustList<CustList<MethodInfo>> next_ = new CustList<CustList<MethodInfo>>();
        for (CustList<MethodInfo> l : _named) {
            CustList<MethodInfo> m_ = new CustList<MethodInfo>();
            for (MethodInfo e : l) {
                if (e.getConstraints().getKind() == MethodAccessKind.STATIC) {
                    m_.add(e);
                    continue;
                }
                CustList<OperationNode> allOps_ = e.getAllOps();
                CustList<AnaClassArgumentMatching> args_ = new CustList<AnaClassArgumentMatching>();
                for (OperationNode o : allOps_) {
                    args_.add(o.getResultClass());
                }
                String result_ = AnaTemplates.tryInferMethod(_varargOnly, e.getClassName(), e.getConstraints(), _filter.getStaticCall(e.getClassName()),
                        _page.getCurrentConstraints().getCurrentConstraints(), args_, e.getOriginalReturnType(), _filter.getReturnType(), _page);
                if (result_.isEmpty()) {
                    continue;
                }
                e.reformat(result_, _page);
                m_.add(e);
            }
            next_.add(m_);
        }
        return next_;
    }

    private static ClassMethodIdReturn getCustIncrDecrResult(CustList<CustList<MethodInfo>> _methods,
                                                             String _name, AnaClassArgumentMatching _argsClass, AnalyzedPageEl _page) {
        CustList<CustList<MethodInfo>> signatures_ = new CustList<CustList<MethodInfo>>();
        for (CustList<MethodInfo> l: _methods) {
            CustList<MethodInfo> m_ = new CustList<MethodInfo>();
            for (MethodInfo e: l) {
                MethodId id_ = e.getConstraints();
                boolean varArg_ = id_.isVararg();
                if (varArg_ || id_.isRef()) {
                    continue;
                }
                if (!StringUtil.quickEq(id_.getName(), _name)) {
                    continue;
                }
                if (e.getGeneFormatted().getParametersTypesLength() != 1) {
                    continue;
                }
                if (!isPossibleIncrDecrMethod(e, _argsClass, _page)) {
                    continue;
                }
                m_.add(e);
            }
            signatures_.add(m_);
        }
        StringMap<StringList> map_;
        map_ = _page.getCurrentConstraints().getCurrentConstraints();
        ArgumentsGroup gr_ = new ArgumentsGroup(_page, map_);
        return tryGetResult(gr_,signatures_);
    }
    private static boolean isPossibleMethodLambda(Parametrable _id, AnalyzedPageEl _page,
                                                  StringList _argsClass) {
        int startOpt_ = _argsClass.size();
        boolean checkOnlyDem_ = true;
        int all_ = _id.getGeneFormatted().getParametersTypesLength();
        int last_ = all_-1;
        boolean vararg_ = _id.isVararg();
        if (!vararg_) {
            if (all_ != _argsClass.size()) {
                return false;
            }
        } else {
            if (all_ > _argsClass.size() + 1) {
                return false;
            }
            checkOnlyDem_ = false;
            startOpt_ = all_ - 1;
        }
        StringMap<StringList> mapCtr_ = _page.getCurrentConstraints().getCurrentConstraints();
        for (int i = IndexConstants.FIRST_INDEX; i < startOpt_; i++) {
            String wc_ = _id.getGeneFormatted().getParametersType(i);
            wc_ = wrap(i,all_,vararg_,wc_);
            String arg_ = _argsClass.get(i);
            if (!matchRef(arg_, _id.getGeneFormatted().getParametersRef(i))) {
                return false;
            }
            String real_ = arg_;
            if (arg_.startsWith("~")) {
                real_ = arg_.substring(1);
            }
            Mapping map_ = new Mapping();
            map_.setArg(real_);
            map_.getMapping().putAllMap(mapCtr_);
            map_.setParam(wc_);
            if (!AnaInherits.isCorrectOrNumbers(map_, _page)) {
                return false;
            }
        }
        if (checkOnlyDem_) {
            _id.setInvocation(InvocationMethod.STRICT);
            return true;
        }
        if (all_ == _argsClass.size()) {
            String arg_ = _argsClass.get(last_);
            if (!matchRef(arg_, _id.getGeneFormatted().getParametersRef(last_))) {
                return false;
            }
            String real_ = arg_;
            if (arg_.startsWith("~")) {
                real_ = arg_.substring(1);
            }
            Mapping map_ = new Mapping();
            map_.setArg(real_);
            map_.getMapping().putAllMap(mapCtr_);
            String wc_ = _id.getGeneFormatted().getParametersType(last_);
            if (wc_.isEmpty()) {
                return false;
            }
            String arr_ = StringExpUtil.getPrettyArrayType(wc_);
            map_.setParam(arr_);
            if (AnaInherits.isCorrectOrNumbers(map_, _page)) {
                _id.setInvocation(InvocationMethod.STRICT);
                return true;
            }
            map_.setParam(wc_);
            if (AnaInherits.isCorrectOrNumbers(map_, _page)) {
                _id.setInvocation(InvocationMethod.VARARG);
                return true;
            }
            return false;
        }
        int nbDem_ = _argsClass.size();
        Mapping map_ = new Mapping();
        map_.getMapping().putAllMap(mapCtr_);
        String wc_ = _id.getGeneFormatted().getParametersType(last_);
        map_.setParam(wc_);
        for (int i = startOpt_; i < nbDem_; i++) {
            String a_ = _argsClass.get(i);
            if (!matchRef(a_, _id.getGeneFormatted().getParametersRef(last_))) {
                return false;
            }
            String real_ = a_;
            if (a_.startsWith("~")) {
                real_ = a_.substring(1);
            }
            map_.setArg(real_);
            if (!AnaInherits.isCorrectOrNumbers(map_, _page)) {
                return false;
            }
        }
        _id.setInvocation(InvocationMethod.VARARG);
        return true;
    }
    private static boolean isPossibleMethodLambdaInfer(Parametrable _id, AnalyzedPageEl _page,
                                                  StringList _argsClass) {
        if (_argsClass == null) {
            return true;
        }
        int startOpt_ = _argsClass.size();
        int all_ = _id.getGeneFormatted().getParametersTypesLength();
        boolean vararg_ = _id.isVararg();
        if (all_ != _argsClass.size()) {
            return false;
        }
        for (int i = IndexConstants.FIRST_INDEX; i < startOpt_; i++) {
            String wc_ = _id.getGeneFormatted().getParametersType(i);
            wc_ = wrap(i,all_,vararg_,wc_);
            String arg_ = _argsClass.get(i);
            boolean ref_ = _id.getGeneFormatted().getParametersRef(i);
            if (!matchRefInf(arg_, ref_)) {
                return false;
            }
            if (!subsType(arg_,wc_,ref_,_page)) {
                return false;
            }
        }
        _id.setInvocation(InvocationMethod.STRICT);
        return true;
    }
    private static boolean matchRefInf(String _arg, boolean _ref) {
        if (StringUtil.quickEq(_arg,"?")) {
            return true;
        }
        return matchRef(_arg, _ref);
    }
    private static boolean matchRef(String _arg, boolean _ref) {
        if (_arg.startsWith("~")) {
            return _ref;
        }
        return !_ref;
    }
    private static boolean isPossibleMethodNamed(Parametrable _id,
                                                 NameParametersFilter _filter) {
        CustList<NamedArgumentOperation> parameterFilter_ = _filter.getParameterFilter();
        CustList<OperationNode> allOp_ = _filter.getAllOps();
//        CustList<OperationNode> positional_ = _filter.getPositional();
        int lengthArgs_ = _filter.posCount();
//        int sum_ = allOp_.size();
        CustList<OperationNode> allOps_ = _id.getAllOps();
        allOps_.addAllElts(allOp_);
//        for (int i = 0; i < sum_; i++) {
//            allOps_.add(allOp_.get(i));
//        }
        for (NamedArgumentOperation f: parameterFilter_) {
            int ind_ = StringUtil.indexOf(_id.getParametersNames(), f.getName());
            StringList formattedParams_ = _id.getFormattedParams();
            if (!formattedParams_.isValidIndex(ind_)) {
                return false;
            }
            if (ind_ < lengthArgs_) {
//            if (ind_ < Math.min(lengthArgs_, _filter.getIndex()))
                return false;
            }
            _id.getNameParametersFilterIndexes().add(ind_);
            allOps_.set(ind_,f);
        }
        return true;
    }

    private static boolean isPossibleArgs(boolean _unique, int _varargOnly, Parametrable _id, String _param, AnalyzedPageEl _page) {
        CustList<OperationNode> allOps_ = _id.getAllOps();
        int allArgsLen_ = allOps_.size();
        int startOpt_ = allArgsLen_;
        boolean checkOnlyDem_ = true;
        int paramLen_ = _id.getGeneFormatted().getParametersTypesLength();
        boolean vararg_ = _id.isVararg();
        if (!vararg_) {
            if (paramLen_ != startOpt_) {
                return false;
            }
        } else {
            if (paramLen_ > startOpt_ + 1) {
                return false;
            }
            if (_varargOnly != 0) {
                checkOnlyDem_ = false;
                startOpt_ = paramLen_ - 1;
            }
            if (_varargOnly > 0) {
                if (startOpt_ != _varargOnly - 1) {
                    return false;
                }
            } else if (_varargOnly == 0) {
                if (paramLen_ -1 != startOpt_) {
                    return false;
                }
            }
        }
        StringMap<StringList> mapCtr_ = _page.getCurrentConstraints().getCurrentConstraints();
        boolean allNotBoxUnbox_ = true;
        boolean implicit_ = false;
        for (int i = IndexConstants.FIRST_INDEX; i < startOpt_; i++) {
            String wc_ = _id.getGeneFormatted().getParametersType(i);
            wc_ = wrap(i,paramLen_,vararg_,wc_);
            CustList<ClassMethodIdReturn> l_ = new CustList<ClassMethodIdReturn>();
            _id.getImplicits().add(l_);
            OperationNode operationNode_ = allOps_.get(i);
            if (_id.getGeneFormatted().getParametersRef(i)) {
                if (!isWrapp(operationNode_)) {
                    return false;
                }
                if (!operationNode_.getResultClass().matchClass(wc_)) {
                    return false;
                }
                continue;
            }
            if (isWrapp(operationNode_)) {
                return false;
            }
            if (operationNode_.getResultClass().isVariable()) {
                if (AnaTypeUtil.isPrimitive(wc_, _page)) {
                    return false;
                }
                continue;
            }
            Mapping map_ = new Mapping();
            AnaClassArgumentMatching arg_ = operationNode_.getResultClass();
            map_.setArg(arg_);
            map_.getMapping().putAllMap(mapCtr_);
            if (wc_.isEmpty()) {
                return false;
            }
            map_.setParam(wc_);
            if (!AnaInherits.isCorrectOrNumbers(map_, _page)) {
                ClassMethodIdReturn res_ = OperationNode.tryGetDeclaredImplicitCast(wc_, arg_, _page);
                if (res_ != null) {
                    implicit_ = true;
                    l_.add(res_);
                    continue;
                }
                return false;
            }
            if (AnaTypeUtil.isPrimitive(wc_, _page)) {
                if (!arg_.isPrimitive(_page)) {
                    allNotBoxUnbox_ = false;
                }
            } else {
                if (arg_.isPrimitive(_page)) {
                    allNotBoxUnbox_ = false;
                }
            }
        }
        if (!match(_id,_param)) {
            return false;
        }
        if (checkOnlyDem_) {
            if (vararg_) {
                //paramLen_ -1 == allOps_.size()
                //startOpt_ == allOps_.size()
                _id.setVarArgWrap(true);
            }
            setWideInvoke(_id, vararg_, allNotBoxUnbox_, implicit_);
            return true;
        }
        int last_ = paramLen_ - 1;
        if (paramLen_ == allArgsLen_) {
            //startOpt_ == paramLen_ - 1;
            //=>startOpt_ + 1 == paramLen_
            //=>startOpt_ + 1 == allArgsLen_
            Mapping map_ = new Mapping();
            OperationNode operationNode_ = allOps_.get(last_);
            String wc_ = _id.getGeneFormatted().getParametersType(last_);
            CustList<ClassMethodIdReturn> l_ = new CustList<ClassMethodIdReturn>();
            _id.getImplicits().add(l_);
            if (_id.getGeneFormatted().getParametersRef(last_)) {
                if (!isWrapp(operationNode_)) {
                    return false;
                }
                setWideInvoke(_id, false, allNotBoxUnbox_, implicit_);
                return operationNode_.getResultClass().matchClass(StringExpUtil.getPrettyArrayType(wc_));
            }
            if (isWrapp(operationNode_)) {
                return false;
            }
            AnaClassArgumentMatching arg_ = operationNode_.getResultClass();
            map_.setArg(arg_);
            map_.getMapping().putAllMap(mapCtr_);
            if (wc_.isEmpty()) {
                if (arg_.isVariable()) {
                    setWideInvoke(_id, true, allNotBoxUnbox_, implicit_);
                    return true;
                }
                return false;
            }
            String arr_ = StringExpUtil.getPrettyArrayType(wc_);
            map_.setParam(arr_);
            if (AnaInherits.isCorrectOrNumbers(map_, _page)) {
                setWideInvoke(_id, false, allNotBoxUnbox_, implicit_);
                if (_unique) {
                    map_.setParam(wc_);
                    if (AnaInherits.isCorrectOrNumbers(map_, _page)) {
                        setVarargOrImplicit(_id, implicit_);
                        _id.setVarArgWrap(true);
                    }
                }
                return true;
            }
            map_.setParam(wc_);
            if (AnaInherits.isCorrectOrNumbers(map_, _page)) {
                setVarargOrImplicit(_id, implicit_);
                _id.setVarArgWrap(true);
                return true;
            }
            ClassMethodIdReturn res_ = OperationNode.tryGetDeclaredImplicitCast(wc_, arg_, _page);
            if (res_ != null) {
                _id.setInvocation(InvocationMethod.ALL);
                _id.setVarArgWrap(true);
                l_.add(res_);
                return true;
            }
            return false;
        }
        Mapping map_ = new Mapping();
        map_.getMapping().putAllMap(mapCtr_);
        String wc_ = _id.getGeneFormatted().getParametersType(last_);
        if (wc_.isEmpty()) {
            return false;
        }
        if (_id.getGeneFormatted().getParametersRef(last_)) {
            return false;
        }
        map_.setParam(wc_);
        for (int i = startOpt_; i < allArgsLen_; i++) {
            CustList<ClassMethodIdReturn> l_ = new CustList<ClassMethodIdReturn>();
            _id.getImplicits().add(l_);
            OperationNode operationNode_ = allOps_.get(i);
            if (isWrapp(operationNode_)) {
                return false;
            }
            AnaClassArgumentMatching a_ = operationNode_.getResultClass();
            map_.setArg(a_);
            if (!AnaInherits.isCorrectOrNumbers(map_, _page)) {
                ClassMethodIdReturn res_ = OperationNode.tryGetDeclaredImplicitCast(wc_, a_, _page);
                if (res_ != null) {
                    implicit_ = true;
                    l_.add(res_);
                    continue;
                }
                return false;
            }
        }
        setVarargOrImplicit(_id, implicit_);
        _id.setVarArgWrap(true);
        return true;
    }

    private static boolean isWrapp(OperationNode _op) {
        OperationNode operation_ = _op;
        if (operation_ instanceof NamedArgumentOperation) {
            operation_ = operation_.getFirstChild();
        }
        return operation_ instanceof WrappOperation;
    }
    private static boolean isPossibleIncrDecrMethod(Parametrable _id,
                                                    AnaClassArgumentMatching _argsClass, AnalyzedPageEl _page) {
        StringMap<StringList> mapCtr_ = _page.getCurrentConstraints().getCurrentConstraints();
        String wc_ = _id.getGeneFormatted().getParametersType(0);
        Mapping map_ = new Mapping();
        map_.setArg(_argsClass);
        map_.getMapping().putAllMap(mapCtr_);
        map_.setParam(wc_);
        if (!AnaInherits.isCorrectOrNumbers(map_, _page)) {
            return false;
        }
        map_.setArg(_id.getReturnType());
        if (!AnaInherits.isCorrectOrNumbers(map_, _page)) {
            return false;
        }
        _id.setInvocation(InvocationMethod.STRICT);
        return true;

    }
    private static boolean match(Parametrable _id,
                                 String _param) {
        int all_ = _id.getGeneFormatted().getParametersTypesLength();
        int last_ = all_-1;
        if (last_ >= 0&&_id.isVararg()&&!_param.isEmpty()) {
            String wc_ = _id.getGeneFormatted().getParametersType(last_);
            return StringUtil.quickEq(wc_, _param);
        }
        return true;
    }
    private static void setWideInvoke(Parametrable _id, boolean _vararg, boolean _allNotBoxUnbox, boolean _implicit) {
        if (_vararg||_implicit) {
            setVarargOrImplicit(_id, _implicit);
        } else {
            if (_allNotBoxUnbox) {
                _id.setInvocation(InvocationMethod.STRICT);
            } else {
                _id.setInvocation(InvocationMethod.BOX_UNBOX);
            }
        }
    }

    private static void setVarargOrImplicit(Parametrable _id, boolean _implicit) {
        if (!_implicit) {
            _id.setInvocation(InvocationMethod.VARARG);
        } else {
            _id.setInvocation(InvocationMethod.ALL);
        }
    }

    private static ClassMethodIdReturn getCustCastResult(CustList<MethodInfo> _methods,
                                                         AnaClassArgumentMatching _argsClass, AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        CustList<MethodInfo> signatures_ = new CustList<MethodInfo>();
        for (MethodInfo e: _methods) {
            if (!isPossibleMethod(e, _argsClass, _page, _vars, _cmp)) {
                continue;
            }
            signatures_.add(e);
        }
        ArgumentsGroup gr_ = new ArgumentsGroup(_page, _vars, _cmp);
        CustList<CustList<MethodInfo>> c_ = new CustList<CustList<MethodInfo>>();
        c_.add(signatures_);
        return tryGetResult(gr_, c_);
    }

    private static ClassMethodIdReturn tryGetResult(ArgumentsGroup _gr, CustList<CustList<MethodInfo>> _list) {
        Parametrable found_ = sortFct(_list, _gr);
        if (!(found_ instanceof MethodInfo)) {
            return null;
        }
        MethodInfo m_ = (MethodInfo) found_;
        MethodId id_ = m_.getFormatted();
        return buildResult(m_, id_);
    }

    public static ClassMethodIdReturn buildResult(MethodInfo _m, MethodId _id) {
        ClassMethodIdReturn res_ = new ClassMethodIdReturn();
        if (_m.isVarArgWrap()) {
            res_.setVarArgToCall(true);
        }
        MethodId constraints_ = _m.getConstraints();
        String baseClassName_ = _m.getClassName();
        res_.setId(new ClassMethodId(baseClassName_, _id));
        res_.setRealId(constraints_);
        res_.setRealClass(baseClassName_);
        res_.setReturnType(_m.getReturnType());
        res_.setOriginalReturnType(_m.getOriginalReturnType());
        res_.setFileName(_m.getFileName());
        res_.setStandardMethod(_m.getStandardMethod());
        res_.setPair(_m.getPair());
        res_.setFormattedType(_m.buildFormatted());
        res_.setMemberId(_m.getMemberId());
        res_.setAncestor(_m.getAncestor());
        res_.setAbstractMethod(_m.isAbstractMethod());
        return res_;
    }

    public static CustList<MethodInfo> getImplSgn(CustList<MethodInfo> _methods, String _argsClass, AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        CustList<MethodInfo> signatures_ = new CustList<MethodInfo>();
        for (MethodInfo e: _methods) {
            if (!isPossibleMethodFct(e, _argsClass, _page, _vars, _cmp)) {
                continue;
            }
            signatures_.add(e);
        }
        return signatures_;
    }

    private static boolean isPossibleMethod(MethodInfo _id,
                                            AnaClassArgumentMatching _argsClass, AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        CustList<String> params_ = _id.getFormatted().shiftFirst();
        int nbDem_ = params_.size();
        boolean allNotBoxUnbox_ = true;
        for (int i = IndexConstants.FIRST_INDEX; i < nbDem_; i++) {
            String wc_ = wrap(i,params_.size(), false,params_.get(i));
            Mapping map_ = new Mapping();
            map_.setArg(_argsClass);
            map_.getMapping().putAllMap(_vars);
            map_.setParam(wc_);
            if (!_cmp.isCorrectOrNumbers(map_, _page)) {
                return false;
            }
            if (AnaTypeUtil.isPrimitive(wc_, _page)) {
                if (!_argsClass.isPrimitive(_page)) {
                    allNotBoxUnbox_ = false;
                }
            } else {
                if (_argsClass.isPrimitive(_page)) {
                    allNotBoxUnbox_ = false;
                }
            }
        }
        if (allNotBoxUnbox_) {
            _id.setInvocation(InvocationMethod.STRICT);
        } else {
            _id.setInvocation(InvocationMethod.BOX_UNBOX);
        }
        return true;
    }

    private static boolean isPossibleMethodFct(MethodInfo _id,
                                            String _argsClass, AnalyzedPageEl _page, StringMap<StringList> _vars, AbstractComparer _cmp) {
        CustList<String> params_ = _id.getFormatted().shiftFirst();
        int nbDem_ = params_.size();
        for (int i = IndexConstants.FIRST_INDEX; i < nbDem_; i++) {
            String wc_ = wrap(i,params_.size(), false,params_.get(i));
            Mapping map_ = new Mapping();
            map_.setArg(wc_);
            map_.getMapping().putAllMap(_vars);
            map_.setParam(_page.getAliasFct());
            if (!_cmp.isCorrectOrNumbers(map_, _page)) {
                return false;
            }
        }
        if (StringExpUtil.getAllTypes(_argsClass).size() == 1) {
            return true;
        }
        for (int i = IndexConstants.FIRST_INDEX; i < nbDem_; i++) {
            String wc_ = wrap(i,params_.size(), false,params_.get(i));
            if (StringExpUtil.getAllTypes(wc_).size() != StringExpUtil.getAllTypes(_argsClass).size()) {
                return false;
            }
        }
        return true;
    }

    private static Parametrable sortFct(CustList<CustList<MethodInfo>> _fct, ArgumentsGroup _context) {
        CustList<CustList<Parametrable>> group_ = new CustList<CustList<Parametrable>>();
        CustList<CustList<Parametrable>> all_ = new CustList<CustList<Parametrable>>();
        for (CustList<MethodInfo> l: _fct) {
            CustList<Parametrable> filter_ = new CustList<Parametrable>();
            CustList<Parametrable> al_ = new CustList<Parametrable>();
            for (MethodInfo m: l) {
                if (!m.getConstraints().isRetRef()) {
                    filter_.add(m);
                }
                al_.add(m);
            }
            group_.add(filter_);
            all_.add(al_);
        }
        Parametrable parametrable_ = sortFctGroup(group_, _context);
        if (parametrable_ != null) {
            return parametrable_;
        }
        return sortFctGroup(all_, _context);
    }
    private static Parametrable sortFctGroup(CustList<CustList<Parametrable>> _fct, ArgumentsGroup _context) {
        for (CustList<Parametrable> l: _fct) {
            Parametrable meth_ = getFoundMethod(l, _context);
            if (meth_ != null) {
                return meth_;
            }
            CustList<Parametrable> instances_ = new CustList<Parametrable>();
            CustList<Parametrable> staticsCall_ = new CustList<Parametrable>();
            CustList<Parametrable> statics_ = new CustList<Parametrable>();
            for (Parametrable m : l) {
                MethodAccessKind kind_ = ((MethodInfo)m).getConstraints().getKind();
                if (kind_ == MethodAccessKind.STATIC_CALL) {
                    staticsCall_.add(m);
                    continue;
                }
                if (kind_ == MethodAccessKind.STATIC) {
                    statics_.add(m);
                    continue;
                }
                instances_.add(m);
            }
            MethodInfo best_ = (MethodInfo) getBest(instances_, _context);
            if (best_ != null) {
                return best_;
            }
            best_ = (MethodInfo) getBest(staticsCall_, _context);
            if (best_ != null) {
                return best_;
            }
            best_ = (MethodInfo) getBest(statics_, _context);
            if (best_ != null) {
                return best_;
            }
        }
        return null;
    }
    private static Parametrable getBest(CustList<Parametrable> _fct, ArgumentsGroup _context) {
        if (!_fct.isEmpty()) {
            int len_ = _fct.size();
            for (int i = IndexConstants.SECOND_INDEX; i < len_; i++) {
                Parametrable pFirst_ = _fct.first();
                Parametrable pCurrent_ = _fct.get(i);
                int res_ = compare(_context, pFirst_, pCurrent_);
                if (res_ == SortConstants.SWAP_SORT) {
                    _fct.swapIndexes(IndexConstants.FIRST_INDEX, i);
                }
            }
            if (!_fct.first().getParameters().isError()) {
                return _fct.first();
            }
        }
        return null;
    }

    private static Parametrable sortCtors(CustList<ConstructorInfo> _fct, ArgumentsGroup _context) {
        CustList<Parametrable> instances_ = new CustList<Parametrable>();
        for (Parametrable m : _fct) {
            instances_.add(m);
        }
        Parametrable ctor_ = getFoundConstructor(instances_, _context);
        if (ctor_ != null) {
            return ctor_;
        }
        return getBest(instances_,_context);
    }

    private static Parametrable getFoundMethod(CustList<Parametrable> _fct, ArgumentsGroup _context) {
        CustList<Parametrable> allMax_ = getAllMax(_fct, _context);
        if (allMax_.isEmpty()) {
            return null;
        }
        Parametrable first_ = allMax_.first();
        if (allMax_.size() == 1) {
            return first_;
        }
        CustList<MethodInfo> allMaxInst_ = new CustList<MethodInfo>();
        for (Parametrable m: allMax_) {
            if (((MethodInfo)m).getConstraints().getKind() == MethodAccessKind.INSTANCE) {
                allMaxInst_.add((MethodInfo) m);
            }
        }
        if (allMaxInst_.isEmpty()) {
            return null;
        }
        if (((MethodInfo)first_).getFormatted().getKind() != MethodAccessKind.INSTANCE) {
            return null;
        }
        int lenMax_ = allMaxInst_.size();
        CustList<MethodInfo> nonAbs_ = new CustList<MethodInfo>();
        CustList<MethodInfo> finals_ = new CustList<MethodInfo>();
        for (MethodInfo p: allMaxInst_) {
            if (!p.isFinalMethod()) {
                continue;
            }
            finals_.add(p);
        }
        if (finals_.size() == 1) {
            return finals_.first();
        }
        AnalyzedPageEl context_ = _context.getContext();
        for (MethodInfo p: allMaxInst_) {
            if (p.isAbstractMethod()) {
                continue;
            }
            if (p.getPair().getType() instanceof InterfaceBlock) {
                continue;
            }
            nonAbs_.add(p);
        }
        if (nonAbs_.size() == 1) {
            return nonAbs_.first();
        }
        nonAbs_.clear();
        for (MethodInfo p: allMaxInst_) {
            if (p.isAbstractMethod()) {
                continue;
            }
            nonAbs_.add(p);
        }
        if (nonAbs_.size() == 1) {
            return nonAbs_.first();
        }
        for (MethodInfo p: allMaxInst_) {
            if (p.getConstraints().isRetRef()) {
                return null;
            }
        }
        MethodId id_ = allMaxInst_.first().getFormatted();
        boolean allOvEq_ = true;
        for (int i = 1; i < lenMax_; i++) {
            if (!allMaxInst_.get(i).same(id_)) {
                allOvEq_ = false;
                break;
            }
        }
        if (allOvEq_) {
            CustList<MethodInfo> nonAbsNonRef_ = new CustList<MethodInfo>();
            CustList<MethodInfo> abs_ = new CustList<MethodInfo>();
            for (MethodInfo p: allMaxInst_) {
                if (p.isAbstractMethod()) {
                    abs_.add(p);
                    continue;
                }
                nonAbsNonRef_.add(p);
            }
            StringMap<StringList> map_;
            map_ = _context.getMap();
            int lenAllMax_ = nonAbsNonRef_.size();
            for (int i = 0; i < lenAllMax_; i++) {
                MethodInfo curMi_ = nonAbsNonRef_.get(i);
                boolean spec_ = true;
                String curRet_ = curMi_.getReturnType();
                for (int j = 0; j < lenAllMax_; j++) {
                    if (i == j) {
                        continue;
                    }
                    MethodInfo otherMi_ = nonAbsNonRef_.get(j);
                    String otherRet_ = otherMi_.getReturnType();
                    if (StringUtil.quickEq(curRet_, otherRet_)) {
                        spec_ = false;
                        break;
                    }
                    if (!AnaInherits.isReturnCorrect(otherRet_, curRet_, map_, context_)) {
                        spec_ = false;
                        break;
                    }
                }
                if (spec_) {
                    return curMi_;
                }
            }
            lenAllMax_ = abs_.size();
            for (int i = 0; i < lenAllMax_; i++) {
                MethodInfo curMi_ = abs_.get(i);
                boolean spec_ = true;
                String curRet_ = curMi_.getReturnType();
                for (int j = 0; j < lenAllMax_; j++) {
                    if (i == j) {
                        continue;
                    }
                    MethodInfo otherMi_ = abs_.get(j);
                    String otherRet_ = otherMi_.getReturnType();
                    if (!AnaInherits.isReturnCorrect(otherRet_, curRet_, map_, context_)) {
                        spec_ = false;
                        break;
                    }
                }
                if (spec_) {
                    return curMi_;
                }
            }
        }
        return null;
    }
    private static Parametrable getFoundConstructor(CustList<Parametrable> _fct, ArgumentsGroup _context) {
        CustList<Parametrable> allMax_ = getAllMax(_fct, _context);
        if (allMax_.size() == 1) {
            return allMax_.first();
        }
        return null;
    }

    private static CustList<Parametrable> getAllMax(CustList<Parametrable> _fct, ArgumentsGroup _context) {
        CustList<Parametrable> fct_ = new CustList<Parametrable>();
        for (Parametrable m : _fct) {
            if (m.getInvocation() == InvocationMethod.STRICT) {
                fct_.add(m);
            }
        }
        if (fct_.isEmpty()) {
            for (Parametrable m : _fct) {
                if (m.getInvocation() == InvocationMethod.BOX_UNBOX) {
                    fct_.add(m);
                }
            }
        }
        CustList<Parametrable> allMax_;
        if (!fct_.isEmpty()) {
            allMax_ = getAllMaximalSpecificFixArity(fct_, _context);
        } else {
            for (Parametrable m : _fct) {
                if (m.getInvocation() == InvocationMethod.VARARG) {
                    fct_.add(m);
                }
            }
            if (fct_.isEmpty()) {
                for (Parametrable m : _fct) {
                    fct_.add(m);
                }
            }
            allMax_ = getAllMaximalSpecificVariableArity(fct_, _context);
        }
        return allMax_;
    }

    private static CustList<Parametrable> getAllMaximalSpecificFixArity(CustList<Parametrable> _all, ArgumentsGroup _context) {
        CustList<Parametrable> list_ = new CustList<Parametrable>();
        int len_ = _all.size();
        for (int i = 0; i < len_; i++) {
            Parametrable current_ = _all.get(i);
            boolean max_ = true;
            for (int j = 0; j < len_; j++) {
                if (i == j) {
                    continue;
                }
                Parametrable other_ = _all.get(j);
                if (isStrictMoreSpecificThanFixArity(other_, current_, _context)) {
                    max_ = false;
                    break;
                }
            }
            if (max_) {
                list_.add(current_);
            }
        }
        return list_;
    }
    private static CustList<Parametrable> getAllMaximalSpecificVariableArity(CustList<Parametrable> _all, ArgumentsGroup _context) {
        CustList<Parametrable> list_ = new CustList<Parametrable>();
        int len_ = _all.size();
        for (int i = 0; i < len_; i++) {
            Parametrable current_ = _all.get(i);
            boolean max_ = true;
            for (int j = 0; j < len_; j++) {
                if (i == j) {
                    continue;
                }
                Parametrable other_ = _all.get(j);
                if (isStrictMoreSpecificThanVariableArity(other_, current_, _context)) {
                    max_ = false;
                    break;
                }
            }
            if (max_) {
                list_.add(current_);
            }
        }
        return list_;
    }
    private static boolean isStrictMoreSpecificThanVariableArity(Parametrable _varOne, Parametrable _varTwo, ArgumentsGroup _context) {
        if (isNotMoreSpecificThanVariableArity(_varOne, _varTwo, _context)) {
            return false;
        }
        return isNotMoreSpecificThanVariableArity(_varTwo, _varOne, _context);
    }
    private static boolean isStrictMoreSpecificThanFixArity(Parametrable _fixOne, Parametrable _fixTwo, ArgumentsGroup _context) {
        if (isNotMoreSpecificThanFixArity(_fixOne, _fixTwo, _context)) {
            return false;
        }
        return isNotMoreSpecificThanFixArity(_fixTwo, _fixOne, _context);
    }
    private static boolean isNotMoreSpecificThanFixArity(Parametrable _one, Parametrable _two, ArgumentsGroup _context) {
        AnalyzedPageEl context_ = _context.getContext();
        StringMap<StringList> map_;
        map_ = _context.getMap();
        int len_ = _one.getGeneFormatted().getParametersTypesLength();
        boolean all_ = true;
        Identifiable idOne_ = _one.getGeneFormatted();
        Identifiable idTwo_ = _two.getGeneFormatted();
        for (int i = IndexConstants.FIRST_INDEX; i < len_; i++) {
            String wcOne_ = idOne_.getParametersType(i);
            String wcTwo_ = idTwo_.getParametersType(i);
            wcOne_ = wrap(i,len_,idOne_.isVararg(),wcOne_);
            wcTwo_ = wrap(i,len_,idTwo_.isVararg(),wcTwo_);
            if (swapCasePreferred(wcOne_, wcTwo_, map_, context_, _context) == SortConstants.SWAP_SORT) {
                all_ = false;
                break;
            }
        }
        return !all_;
    }
    private static boolean isNotMoreSpecificThanVariableArity(Parametrable _one, Parametrable _two, ArgumentsGroup _context) {
        AnalyzedPageEl context_ = _context.getContext();
        StringMap<StringList> map_;
        map_ = _context.getMap();
        int lenOne_ = _one.getGeneFormatted().getParametersTypesLength();
        int lenTwo_ = _two.getGeneFormatted().getParametersTypesLength();
        int lastOne_ = lenOne_-1;
        int lastTwo_ = lenTwo_-1;
        boolean all_ = true;
        if (lenOne_ >= lenTwo_) {
            int pr_ = lenTwo_-1;
            for (int i = IndexConstants.FIRST_INDEX; i < pr_; i++) {
                String wcOne_ = _one.getGeneFormatted().getParametersType(i);
                String wcTwo_ = _two.getGeneFormatted().getParametersType(i);
                if (swapCasePreferred(wcOne_, wcTwo_, map_, context_, _context) == SortConstants.SWAP_SORT) {
                    all_ = false;
                    break;
                }
            }
            String wcTwo_ = _two.getGeneFormatted().getParametersType(lastTwo_);
            for (int i = pr_; i < lenOne_; i++) {
                String wcOne_ = _one.getGeneFormatted().getParametersType(i);
                if (swapCasePreferred(wcOne_, wcTwo_, map_, context_, _context) == SortConstants.SWAP_SORT) {
                    all_ = false;
                    break;
                }
            }
        } else {
            int pr_ = lenOne_-1;
            for (int i = IndexConstants.FIRST_INDEX; i < pr_; i++) {
                String wcOne_ = _one.getGeneFormatted().getParametersType(i);
                String wcTwo_ = _two.getGeneFormatted().getParametersType(i);
                if (swapCasePreferred(wcOne_, wcTwo_, map_, context_, _context) == SortConstants.SWAP_SORT) {
                    all_ = false;
                    break;
                }
            }
            String wcOne_ = _one.getGeneFormatted().getParametersType(lastOne_);
            for (int i = pr_; i < lenTwo_; i++) {
                String wcTwo_ = _two.getGeneFormatted().getParametersType(i);
                if (swapCasePreferred(wcOne_, wcTwo_, map_, context_, _context) == SortConstants.SWAP_SORT) {
                    all_ = false;
                    break;
                }
            }
        }
        return !all_;
    }
    private static int swapCasePreferred(String _paramFctOne, String _paramFctTwo, StringMap<StringList> _map, AnalyzedPageEl _ana, ArgumentsGroup _g) {
        if (_paramFctOne.isEmpty()) {
            if (_paramFctTwo.isEmpty()) {
                return SortConstants.EQ_CMP;
            }
            return SortConstants.SWAP_SORT;
        }
        if (_paramFctTwo.isEmpty()) {
            return SortConstants.NO_SWAP_SORT;
        }
        if (StringUtil.quickEq(_paramFctOne,_paramFctTwo)) {
            return SortConstants.EQ_CMP;
        }
        Mapping map_ = new Mapping();
        map_.setMapping(_map);
        map_.setArg(_paramFctOne);
        map_.setParam(_paramFctTwo);
        if (_g.getComparer().isCorrectOrNumbers(map_, _ana)) {
            return SortConstants.NO_SWAP_SORT;
        }
        return SortConstants.SWAP_SORT;
    }

    private static String wrap(int _i, int _len, boolean _vararg, String _type){
        if (_type.isEmpty()){
            return _type;
        }
        if (_i + 1 == _len && _vararg) {
            return StringExpUtil.getPrettyArrayType(_type);
        }
        return _type;
    }
    private static int compare(ArgumentsGroup _context, Parametrable _o1, Parametrable _o2) {
        int len_ = _o1.getGeneFormatted().getParametersTypesLength();
        AnalyzedPageEl context_ = _context.getContext();
        StringMap<StringList> map_;
        map_ = _context.getMap();
        String glClassOne_ = _o1.getClassName();
        String glClassTwo_ = _o2.getClassName();
        if (len_ != _o2.getGeneFormatted().getParametersTypesLength()) {
            if (len_ < _o2.getGeneFormatted().getParametersTypesLength()) {
                return SortConstants.SWAP_SORT;
            }
            return SortConstants.NO_SWAP_SORT;
        }
        for (int i = IndexConstants.FIRST_INDEX; i < len_; i++) {
            String wcOne_ = _o1.getGeneFormatted().getParametersType(i);
            String wcTwo_ = _o2.getGeneFormatted().getParametersType(i);
            int res_ = checkPreferred(wcOne_, wcTwo_, map_, context_, _o1, _o2, _context);
            if (res_ != SortConstants.EQ_CMP) {
                return res_;
            }
        }
        String baseTypeOne_ = StringExpUtil.getIdFromAllTypes(glClassOne_);
        String baseTypeTwo_ = StringExpUtil.getIdFromAllTypes(glClassTwo_);
        if (StringUtil.quickEq(baseTypeOne_, baseTypeTwo_)){
            if (_o1.sameParamsVararg(_o2)) {
                return SortConstants.NO_SWAP_SORT;
            }
        }
        if (!StringUtil.quickEq(_o2.getReturnType(), _o1.getReturnType())) {
            String p_ = _o1.getReturnType();
            String a_ = _o2.getReturnType();
            if (AnaInherits.isReturnCorrect(p_, a_, map_, context_)) {
                return SortConstants.SWAP_SORT;
            }
            a_ = _o1.getReturnType();
            p_ = _o2.getReturnType();
            if (AnaInherits.isReturnCorrect(p_, a_, map_, context_)) {
                return SortConstants.NO_SWAP_SORT;
            }
        } else if (StringUtil.quickEq(baseTypeOne_, baseTypeTwo_)){
            _o1.getParameters().setError(true);
            _o2.getParameters().setError(true);
            return SortConstants.NO_SWAP_SORT;
        }
        int res_ = AnaTypeUtil.cmpTypes(baseTypeOne_, baseTypeTwo_, context_);
        if (res_ != 0) {
            return res_;
        }
        //inherits types if static methods
        _o1.getParameters().setError(true);
        _o2.getParameters().setError(true);
        return SortConstants.NO_SWAP_SORT;
    }

    private static int checkPreferred(String _one, String _two, StringMap<StringList> _map, AnalyzedPageEl _an, Parametrable _p1, Parametrable _p2, ArgumentsGroup _g) {
        int res_ = swapCasePreferred(_one, _two, _map, _an, _g);
        if (res_ != SortConstants.EQ_CMP) {
            if (res_ == SortConstants.NO_SWAP_SORT) {
                return res_;
            }
            res_ = swapCasePreferred(_two, _one, _map, _an, _g);
            if (res_ == SortConstants.NO_SWAP_SORT) {
                return SortConstants.SWAP_SORT;
            }
            _p1.getParameters().setError(true);
            _p2.getParameters().setError(true);
            return SortConstants.NO_SWAP_SORT;
        }
        return res_;
    }
    public final MethodOperation getParent() {
        return parent;
    }

    public final OperationsSequence getOperations() {
        return operations;
    }

    public final int getOrder() {
        return content.getOrder();
    }

    public final void setOrder(int _order) {
        content.setOrder(_order);
    }

    public final int getFullIndexInEl() {
        String meth_ = getOperations().getFctName();
        int off_ = StringUtil.getFirstPrintableCharIndex(meth_);
        return off_+ content.getIndexInEl();
    }

    public final int getIndexInEl() {
        return content.getIndexInEl();
    }

    public final int getIndexChild() {
        return content.getIndexChild();
    }

    public final Argument getArgument() {
        return content.getArgument();
    }

    public final void setSimpleArgument(Argument _argument) {
        content.setArgument(_argument);
    }

    public final AnaClassArgumentMatching getResultClass() {
        return content.getResultClass();
    }

    public final void setResultClass(AnaClassArgumentMatching _resultClass) {
        content.setResultClass(_resultClass);
    }

    public AnaOperationContent getContent() {
        return content;
    }

    protected void processEmptyErrorChild() {
        MethodOperation.processEmptyError(getFirstChild(),errs);
    }
    protected String check(String _className, StringList _parts, StringMap<StringList> _inherit, AnalyzedPageEl _page) {
        return AnaInherits.check(errs,_className,_parts,_inherit,_page);
    }
    public void mergeErrs(OperationNode _err) {
        errs.addAllElts(_err.errs);
    }
    public void addErr(String _err) {
        errs.add(_err);
    }

    public StringList getErrs() {
        return errs;
    }
    public void addWarn(String _err) {
        warns.add(_err);
    }

    public StringList getWarns() {
        return warns;
    }

    public int getIndexInExp() {
        return indexInExp;
    }

    public void setIndexInExp(int _indexInExp) {
        indexInExp = _indexInExp;
    }
}
