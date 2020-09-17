package code.expressionlanguage.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.DefaultExiting;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.opers.*;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.blocks.*;
import code.expressionlanguage.exec.calls.AbstractPageEl;
import code.expressionlanguage.exec.calls.util.CustomFoundMethod;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.exec.inherits.Parameters;
import code.expressionlanguage.exec.util.ExecOverrideInfo;
import code.expressionlanguage.exec.util.ImplicitMethods;
import code.expressionlanguage.exec.util.PolymorphMethod;
import code.expressionlanguage.functionid.MethodAccessKind;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.inherits.ClassArgumentMatching;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.*;
import code.util.CustList;
import code.util.IdMap;
import code.util.StringList;

public abstract class ExecOperationNode {

    protected static final String ARR = "[";

    protected static final String MULT = "*";

    protected static final String DIV = "/";

    protected static final String PLUS = "+";

    protected static final String DIFF = "!=";

    protected static final String EMPTY_STRING = "";
    protected static final String RETURN_LINE = "\n";

    private ExecMethodOperation parent;

    private ExecOperationNode nextSibling;

    private Argument argument;

    private int indexInEl;

    private int order;

    private final int indexChild;

    private ClassArgumentMatching resultClass;

    private ExecPossibleIntermediateDotted siblingSet;

    private int indexBegin;

    private ImplicitMethods implicits = new ImplicitMethods();
    private ImplicitMethods implicitsTest = new ImplicitMethods();

    ExecOperationNode(OperationNode _oper) {
        indexInEl = _oper.getIndexInEl();
        indexBegin = _oper.getIndexBegin();
        indexChild = _oper.getIndexChild();
        resultClass = _oper.getResultClass();
        argument = _oper.getArgument();
        order = _oper.getOrder();
    }

    ExecOperationNode(int _indexChild, ClassArgumentMatching _res, int _order) {
        indexChild = _indexChild;
        resultClass = _res;
        order = _order;
    }

    public static ImplicitMethods fetchImplicits(ContextEl _context,ClassMethodId _clMet,int _root, int _member) {
        ExecNamedFunctionBlock conv_ = null;
        String converterClass = "";
        if (_clMet != null) {
            converterClass = _clMet.getClassName();
            conv_ = fetchFunction(_root,_member, _context.getAnalyzing());
        }
        if (conv_ != null) {
            ImplicitMethods converter = new ImplicitMethods();
            ExecRootBlock classBody_ = fetchType(_root, _context.getAnalyzing());
            converter.getConverter().add(conv_);
            converter.setOwnerClass(converterClass);
            converter.setRootBlock(classBody_);
            return converter;
        }
        return null;
    }
    public static ExecRootBlock fetchType(int _nbRoot, AnalyzedPageEl _page) {
        if (_page.getMapTypes().getKeys().isValidIndex(_nbRoot)) {
            return _page.getMapTypes().getValue(_nbRoot);
        }
        return null;
    }

    public static ExecInfoBlock fetchField(LambdaOperation _l, AnalyzedPageEl _page) {
        if (_page.getMapMembers().getKeys().isValidIndex(_l.getRootNumber())) {
            if (_page.getMapMembers().getValue(_l.getRootNumber()).getAllFields().getKeys().isValidIndex(_l.getMemberNumber())) {
                return _page.getMapMembers().getValue(_l.getRootNumber()).getAllFields().getValue(_l.getMemberNumber());
            }
        }
        return null;
    }
    public static MethodAccessKind getKind(ClassMethodId _cl) {
        if (_cl == null) {
            return MethodAccessKind.STATIC;
        }
        return _cl.getConstraints().getKind();
    }
    public static String getType(ClassMethodId _cl) {
        if (_cl == null) {
            return "";
        }
        return _cl.getClassName();
    }
    public static ExecNamedFunctionBlock fetchFunctionOp(int _rootNumber, int _memberNumber, AnalyzedPageEl _page) {
        return fetchFunction(_rootNumber,_memberNumber,_memberNumber, _page);
    }
    public static ExecNamedFunctionBlock fetchFunction(int _rootNumber, int _memberNumber, int _operatorNumber, AnalyzedPageEl _page) {
        if (_page.getMapMembers().getKeys().isValidIndex(_rootNumber)) {
            if (_page.getMapMembers().getValue(_rootNumber).getAllNamed().getKeys().isValidIndex(_memberNumber)) {
                return _page.getMapMembers().getValue(_rootNumber).getAllNamed().getValue(_memberNumber);
            }
            return null;
        }
        if (_page.getMapOperators().getKeys().isValidIndex(_operatorNumber)) {
            return _page.getMapOperators().getValue(_operatorNumber);
        }
        return null;
    }
    public static ExecNamedFunctionBlock fetchFunction(AbstractCallFctOperation _l, AnalyzedPageEl _page) {
        return fetchFunction(_l.getRootNumber(),_l.getMemberNumber(), _page);
    }
    public static ExecNamedFunctionBlock fetchFunction(int _nbRoot, int _nbMember, AnalyzedPageEl _page) {
        if (_page.getMapMembers().getKeys().isValidIndex(_nbRoot)) {
            if (_page.getMapMembers().getValue(_nbRoot).getAllNamed().getKeys().isValidIndex(_nbMember)) {
                return _page.getMapMembers().getValue(_nbRoot).getAllNamed().getValue(_nbMember);
            }
        }
        return null;
    }
    static int processConverter(ContextEl _conf, Argument _right, ImplicitMethods implicits_, int indexImplicit_) {
        ExecNamedFunctionBlock c = implicits_.get(indexImplicit_);
        DefaultExiting ex_ = new DefaultExiting(_conf);
        CustList<Argument> args_ = new CustList<Argument>(_right);
        AbstractPageEl last_ = _conf.getLastPage();
        if (ExecExplicitOperation.checkCustomOper(ex_,implicits_.getRootBlock(), c, args_, implicits_.getOwnerClass(), last_,_conf,_right)) {
            return indexImplicit_;
        }
        return indexImplicit_ +1;
    }

    public void setParent(ExecMethodOperation _parent) {
        parent = _parent;
    }

    public final void setRelativeOffsetPossibleLastPage(int _offset, ContextEl _cont) {
        _cont.setOffset(getIndexBegin()+_offset);
    }

    public final int getIndexBegin() {
        return indexBegin;
    }
    public static ExecOperationNode createExecOperationNode(OperationNode _anaNode, ContextEl _cont, AnalyzedPageEl _page) {
        if (_anaNode instanceof StaticInitOperation) {
            StaticInitOperation c_ = (StaticInitOperation) _anaNode;
            return new ExecStaticInitOperation(c_);
        }
        if (_anaNode instanceof ConstantOperation) {
            ConstantOperation c_ = (ConstantOperation) _anaNode;
            return new ExecConstantOperation(c_);
        }
        if (_anaNode instanceof AnnotationInstanceOperation) {
            AnnotationInstanceOperation n_ = (AnnotationInstanceOperation) _anaNode;
            return new ExecAnnotationInstanceOperation(n_,fetchType(n_.getRootNumber(), _page));
        }
        if (_anaNode instanceof FctOperation) {
            FctOperation f_ = (FctOperation) _anaNode;
            if (f_.isClonedMethod()) {
                return new ExecCloneOperation(f_);
            }
        }
        if (_anaNode instanceof InvokingOperation && _anaNode instanceof AbstractCallFctOperation) {
            InvokingOperation i_ = (InvokingOperation) _anaNode;
            AbstractCallFctOperation a_ = (AbstractCallFctOperation) _anaNode;
            ClassMethodId classMethodId_ = a_.getClassMethodId();
            if (classMethodId_ != null) {
                if (a_.getStandardMethod() != null) {
                    return new ExecStdFctOperation(i_,a_);
                }
                ExecRootBlock ex_ = fetchType(a_.getRootNumber(), _page);
                if (ex_ instanceof ExecAnnotationBlock) {
                    return new ExecAnnotationMethodOperation(i_,a_);
                }
                if (a_.isTrueFalse()) {
                    return new ExecExplicitOperation(i_,a_,
                            fetchFunction(a_, _page),
                            fetchType(a_.getRootNumber(), _page));
                }
                if (a_.isStaticMethod()) {
                    ExecNamedFunctionBlock fct_ = fetchFunction(a_, _page);
                    return new ExecStaticFctOperation(i_,a_, fct_,ex_);
                }
            }
        }
        if (_anaNode instanceof InterfaceFctConstructor) {
            InterfaceFctConstructor n_ = (InterfaceFctConstructor) _anaNode;
            return new ExecInterfaceFctConstructor(n_,_cont);
        }
        if (_anaNode instanceof InterfaceInvokingConstructor) {
            InterfaceInvokingConstructor n_ = (InterfaceInvokingConstructor) _anaNode;
            return new ExecInterfaceInvokingConstructor(n_,_cont);
        }
        if (_anaNode instanceof CurrentInvokingConstructor) {
            CurrentInvokingConstructor n_ = (CurrentInvokingConstructor) _anaNode;
            return new ExecCurrentInvokingConstructor(n_,_cont);
        }
        if (_anaNode instanceof SuperInvokingConstructor) {
            SuperInvokingConstructor n_ = (SuperInvokingConstructor) _anaNode;
            return new ExecSuperInvokingConstructor(n_,_cont);
        }
        if (_anaNode instanceof CallDynMethodOperation) {
            CallDynMethodOperation c_ = (CallDynMethodOperation) _anaNode;
            return new ExecCallDynMethodOperation(c_);
        }
        if (_anaNode instanceof InferArrayInstancing) {
            InferArrayInstancing i_ = (InferArrayInstancing) _anaNode;
            return new ExecArrayElementOperation(i_);
        }
        if (_anaNode instanceof ElementArrayInstancing) {
            ElementArrayInstancing e_ = (ElementArrayInstancing) _anaNode;
            return new ExecArrayElementOperation(e_);
        }
        if (_anaNode instanceof DimensionArrayInstancing) {
            DimensionArrayInstancing d_ = (DimensionArrayInstancing) _anaNode;
            return new ExecDimensionArrayInstancing(d_);
        }
        if (_anaNode instanceof StandardInstancingOperation) {
            StandardInstancingOperation s_ = (StandardInstancingOperation) _anaNode;
            ExecNamedFunctionBlock ctor_ = fetchFunctionOp(s_.getRootNumber(), s_.getMemberNumber(), _page);
            ExecRootBlock rootBlock_ = fetchType(s_.getRootNumber(), _page);
            if (rootBlock_ != null) {
                return new ExecStandardInstancingOperation(s_,rootBlock_,ctor_);
            }
            return new ExecDirectStandardInstancingOperation(s_);
        }
        if (_anaNode instanceof AnonymousInstancingOperation) {
            AnonymousInstancingOperation s_ = (AnonymousInstancingOperation) _anaNode;
            ExecAnonymousInstancingOperation exec_ = new ExecAnonymousInstancingOperation(s_);
            _page.getMapAnonymous().last().addEntry(s_,exec_);
            return exec_;
        }
        if (_anaNode instanceof ArrOperation) {
            ArrOperation a_ = (ArrOperation) _anaNode;
            ExecRootBlock ex_ = fetchType(a_.getRootNumber(), _page);
            ExecNamedFunctionBlock get_ = fetchFunction(a_.getRootNumber(), a_.getMemberNumber(), _page);
            ExecNamedFunctionBlock set_ = fetchFunction(a_.getRootNumber(), a_.getMemberNumberSet(), _page);
            if (a_.getClassMethodId() != null) {
                return new ExecCustArrOperation(a_, get_,set_,ex_);
            }
            return new ExecArrOperation(a_);
        }
        if (_anaNode instanceof DeclaringOperation) {
            DeclaringOperation d_ = (DeclaringOperation) _anaNode;
            return new ExecDeclaringOperation(d_);
        }
        if (_anaNode instanceof IdOperation) {
            IdOperation d_ = (IdOperation) _anaNode;
            if (d_.isStandard()) {
                return new ExecIdOperation(d_);
            }
            return new ExecMultIdOperation(d_);
        }
        if (_anaNode instanceof EnumValueOfOperation) {
            EnumValueOfOperation d_ = (EnumValueOfOperation) _anaNode;
            return new ExecEnumValueOfOperation(d_,_cont);
        }
        if (_anaNode instanceof ValuesOperation) {
            ValuesOperation d_ = (ValuesOperation) _anaNode;
            return new ExecValuesOperation(d_,_cont);
        }
        if (_anaNode instanceof AbstractTernaryOperation) {
            AbstractTernaryOperation t_ = (AbstractTernaryOperation) _anaNode;
            return new ExecTernaryOperation(t_);
        }
        if (_anaNode instanceof ChoiceFctOperation) {
            ChoiceFctOperation c_ = (ChoiceFctOperation) _anaNode;
            ExecRootBlock ex_ = fetchType(c_.getRootNumber(), _page);
            if (ex_ != null) {
                ExecNamedFunctionBlock fct_ = fetchFunction(c_, _page);
                return new ExecChoiceFctOperation(c_, fct_,ex_);
            }
        }
        if (_anaNode instanceof SuperFctOperation) {
            SuperFctOperation s_ = (SuperFctOperation) _anaNode;
            ExecRootBlock ex_ = fetchType(s_.getRootNumber(), _page);
            if (ex_ != null) {
                ExecNamedFunctionBlock fct_ = fetchFunction(s_, _page);
                return new ExecSuperFctOperation(s_, fct_,ex_);
            }
        }
        if (_anaNode instanceof FctOperation) {
            FctOperation f_ = (FctOperation) _anaNode;
            ExecNamedFunctionBlock fct_ = fetchFunction(f_, _page);
            ExecRootBlock ex_ = fetchType(f_.getRootNumber(), _page);
            if (ex_ != null) {
                return new ExecFctOperation(f_, fct_,ex_);
            }
        }
        if (_anaNode instanceof NamedArgumentOperation) {
            NamedArgumentOperation f_ = (NamedArgumentOperation) _anaNode;
            return new ExecNamedArgumentOperation(f_);
        }
        if (_anaNode instanceof FirstOptOperation) {
            FirstOptOperation f_ = (FirstOptOperation) _anaNode;
            return new ExecFirstOptOperation(f_);
        }
        if (_anaNode instanceof StaticAccessOperation) {
            LeafOperation f_ = (LeafOperation) _anaNode;
            return new ExecStaticAccessOperation(f_);
        }
        if (_anaNode instanceof StaticCallAccessOperation) {
            LeafOperation f_ = (LeafOperation) _anaNode;
            return new ExecStaticAccessOperation(f_);
        }
        if (_anaNode instanceof VarargOperation) {
            VarargOperation f_ = (VarargOperation) _anaNode;
            return new ExecVarargOperation(f_);
        }
        if (_anaNode instanceof DefaultValueOperation) {
            DefaultValueOperation f_ = (DefaultValueOperation) _anaNode;
            return new ExecDefaultValueOperation(f_);
        }
        if (_anaNode instanceof DefaultOperation) {
            DefaultOperation f_ = (DefaultOperation) _anaNode;
            return new ExecDefaultOperation(f_);
        }
        if (_anaNode instanceof IdFctOperation) {
            IdFctOperation f_ = (IdFctOperation) _anaNode;
            return new ExecIdFctOperation(f_);
        }
        if (_anaNode instanceof AnonymousLambdaOperation) {
            AnonymousLambdaOperation s_ = (AnonymousLambdaOperation) _anaNode;
            ExecAnonymousLambdaOperation exec_ = new ExecAnonymousLambdaOperation(s_);
            _page.getMapAnonymousLambda().last().addEntry(s_,exec_);
            return exec_;
        }
        if (_anaNode instanceof LambdaOperation) {
            LambdaOperation f_ = (LambdaOperation) _anaNode;
            if (f_.getStandardMethod() != null) {
                return new ExecStdMethodLambdaOperation(f_);
            }
            if (f_.getMethod() == null && f_.getRealId() == null) {
                return new ExecFieldLambdaOperation(f_,_cont);
            }
            if (f_.getMethod() == null) {
                return new ExecConstructorLambdaOperation(f_,_cont);
            }
            return new ExecMethodLambdaOperation(f_,_cont);
        }
        if (_anaNode instanceof StaticInfoOperation) {
            StaticInfoOperation f_ = (StaticInfoOperation) _anaNode;
            return new ExecStaticInfoOperation(f_);
        }
        if (_anaNode instanceof ThisOperation) {
            ThisOperation f_ = (ThisOperation) _anaNode;
            return new ExecThisOperation(f_);
        }
        if (_anaNode instanceof ParentInstanceOperation) {
            ParentInstanceOperation f_ = (ParentInstanceOperation) _anaNode;
            return new ExecParentInstanceOperation(f_);
        }
        if (_anaNode instanceof ForwardOperation) {
            ForwardOperation f_ = (ForwardOperation) _anaNode;
            return new ExecForwardOperation(f_);
        }
        if (_anaNode instanceof SettableAbstractFieldOperation) {
            SettableAbstractFieldOperation s_ = (SettableAbstractFieldOperation) _anaNode;
            if (s_.getFieldId() == null) {
                return new ExecErrorParentOperation(_anaNode);
            }
            return new ExecSettableFieldOperation(s_,fetchType(s_.getRootNumber(), _page));
        }
        if (_anaNode instanceof ArrayFieldOperation) {
            ArrayFieldOperation s_ = (ArrayFieldOperation) _anaNode;
            return new ExecArrayFieldOperation(s_);
        }
        if (_anaNode instanceof MutableLoopVariableOperation) {
            MutableLoopVariableOperation m_ = (MutableLoopVariableOperation) _anaNode;
            return new ExecStdVariableOperation(m_);
        }
        if (_anaNode instanceof VariableOperation) {
            VariableOperation m_ = (VariableOperation) _anaNode;
            return new ExecStdVariableOperation(m_);
        }
        if (_anaNode instanceof FinalVariableOperation) {
            FinalVariableOperation m_ = (FinalVariableOperation) _anaNode;
            return new ExecFinalVariableOperation(m_);
        }
        if (_anaNode instanceof DotOperation) {
            DotOperation m_ = (DotOperation) _anaNode;
            return new ExecDotOperation(m_);
        }
        if (_anaNode instanceof SafeDotOperation) {
            SafeDotOperation m_ = (SafeDotOperation) _anaNode;
            return new ExecSafeDotOperation(m_);
        }
        if (_anaNode instanceof ExplicitOperatorOperation) {
            ExplicitOperatorOperation m_ = (ExplicitOperatorOperation) _anaNode;
            return new ExecExplicitOperatorOperation(m_,_cont);
        }
        if (_anaNode instanceof SemiAffectationOperation) {
            SemiAffectationOperation m_ = (SemiAffectationOperation) _anaNode;
            return new ExecSemiAffectationOperation(m_,_cont);
        }
        if (_anaNode instanceof SymbolOperation) {
            SymbolOperation n_ = (SymbolOperation) _anaNode;
            if (!n_.isOkNum()) {
                return new ExecErrorParentOperation(_anaNode);
            }
            if (n_.getClassMethodId() != null) {
                return new ExecCustNumericOperation(n_, _anaNode, fetchFunctionOp(n_.getRootNumber(),n_.getMemberNumber(), _page),fetchType(n_.getRootNumber(), _page));
            }
        }
        if (_anaNode instanceof UnaryBooleanOperation) {
            UnaryBooleanOperation m_ = (UnaryBooleanOperation) _anaNode;
            return new ExecUnaryBooleanOperation(m_);
        }
        if (_anaNode instanceof UnaryBinOperation) {
            UnaryBinOperation m_ = (UnaryBinOperation) _anaNode;
            return new ExecUnaryBinOperation(m_);
        }
        if (_anaNode instanceof UnaryOperation) {
            UnaryOperation m_ = (UnaryOperation) _anaNode;
            return new ExecUnaryOperation(m_);
        }
        if (_anaNode instanceof CastOperation) {
            CastOperation m_ = (CastOperation) _anaNode;
            return new ExecCastOperation(m_);
        }
        if (_anaNode instanceof ExplicitOperation) {
            ExplicitOperation m_ = (ExplicitOperation) _anaNode;
            return new ExecExplicitOperation(m_,
                    fetchFunction(m_.getRootNumber(),m_.getMemberNumber(), _page),
                    fetchType(m_.getRootNumber(), _page));
        }
        if (_anaNode instanceof ImplicitOperation) {
            ImplicitOperation m_ = (ImplicitOperation) _anaNode;
            return new ExecImplicitOperation(m_,
                    fetchFunction(m_.getRootNumber(),m_.getMemberNumber(), _page),
                    fetchType(m_.getRootNumber(), _page));
        }
        if (_anaNode instanceof MultOperation) {
            MultOperation m_ = (MultOperation) _anaNode;
            return new ExecMultOperation(m_);
        }
        if (_anaNode instanceof AddOperation) {
            AddOperation m_ = (AddOperation) _anaNode;
            if (m_.isCatString()) {
                return new ExecCatOperation(m_);
            }
            return new ExecAddOperation(m_);
        }
        if (_anaNode instanceof ShiftLeftOperation) {
            ShiftLeftOperation m_ = (ShiftLeftOperation) _anaNode;
            return new ExecShiftLeftOperation(m_);
        }
        if (_anaNode instanceof ShiftRightOperation) {
            ShiftRightOperation m_ = (ShiftRightOperation) _anaNode;
            return new ExecShiftRightOperation(m_);
        }
        if (_anaNode instanceof BitShiftLeftOperation) {
            BitShiftLeftOperation m_ = (BitShiftLeftOperation) _anaNode;
            return new ExecBitShiftLeftOperation(m_);
        }
        if (_anaNode instanceof BitShiftRightOperation) {
            BitShiftRightOperation m_ = (BitShiftRightOperation) _anaNode;
            return new ExecBitShiftRightOperation(m_);
        }
        if (_anaNode instanceof RotateLeftOperation) {
            RotateLeftOperation m_ = (RotateLeftOperation) _anaNode;
            return new ExecRotateLeftOperation(m_);
        }
        if (_anaNode instanceof RotateRightOperation) {
            RotateRightOperation m_ = (RotateRightOperation) _anaNode;
            return new ExecRotateRightOperation(m_);
        }
        if (_anaNode instanceof CmpOperation) {
            CmpOperation c_ = (CmpOperation) _anaNode;
            if (!c_.isStringCompare()) {
                return new ExecNbCmpOperation(c_);
            }
            return new ExecStrCmpOperation(c_);
        }
        if (_anaNode instanceof InstanceOfOperation) {
            InstanceOfOperation c_ = (InstanceOfOperation) _anaNode;
            return new ExecInstanceOfOperation(c_);
        }
        if (_anaNode instanceof EqOperation) {
            EqOperation c_ = (EqOperation) _anaNode;
            return new ExecEqOperation(c_);
        }
        if (_anaNode instanceof BitAndOperation) {
            BitAndOperation c_ = (BitAndOperation) _anaNode;
            return new ExecBitAndOperation(c_);
        }
        if (_anaNode instanceof BitOrOperation) {
            BitOrOperation c_ = (BitOrOperation) _anaNode;
            return new ExecBitOrOperation(c_);
        }
        if (_anaNode instanceof BitXorOperation) {
            BitXorOperation c_ = (BitXorOperation) _anaNode;
            return new ExecBitXorOperation(c_);
        }
        if (_anaNode instanceof QuickOperation) {
            QuickOperation q_ = (QuickOperation) _anaNode;
            if (!q_.isOkNum()) {
                return new ExecErrorParentOperation(q_);
            }
        }
        if (_anaNode instanceof AndOperation) {
            AndOperation c_ = (AndOperation) _anaNode;
            return new ExecAndOperation(c_,_cont);
        }
        if (_anaNode instanceof OrOperation) {
            OrOperation c_ = (OrOperation) _anaNode;
            return new ExecOrOperation(c_,_cont);
        }
        if (_anaNode instanceof NullSafeOperation) {
            NullSafeOperation c_ = (NullSafeOperation) _anaNode;
            return new ExecNullSafeOperation(c_);
        }
        if (_anaNode instanceof AssocationOperation) {
            AssocationOperation c_ = (AssocationOperation) _anaNode;
            return new ExecAssocationOperation(c_);
        }
        if (_anaNode instanceof CompoundAffectationOperation) {
            CompoundAffectationOperation c_ = (CompoundAffectationOperation) _anaNode;
            return new ExecCompoundAffectationOperation(c_,_cont);
        }
        if (_anaNode instanceof AffectationOperation) {
            AffectationOperation a_ = (AffectationOperation) _anaNode;
            return new ExecAffectationOperation(a_);
        }
        return new ExecErrorParentOperation(_anaNode);
    }

    public abstract ExecOperationNode getFirstChild();

    public final ExecOperationNode getNextSibling() {
        return nextSibling;
    }
    final void setNextSibling(ExecOperationNode _nextSibling) {
        nextSibling = _nextSibling;
    }

    protected Argument getPreviousArg(ExecPossibleIntermediateDotted _possible,IdMap<ExecOperationNode,ArgumentsPair> _nodes,  ContextEl _conf) {
        Argument previous_;
        if (_possible.isIntermediateDottedOperation()) {
            previous_ = getPreviousArgument(_nodes, this);
        } else {
            previous_ = _conf.getLastPage().getGlobalArgument();
        }
        return previous_;
    }
    protected static CustList<Argument> getArguments(IdMap<ExecOperationNode,ArgumentsPair> _nodes, ExecMethodOperation _method) {
        CustList<Argument> a_ = new CustList<Argument>();
        for (ExecOperationNode o: _method.getChildrenNodes()) {
            a_.add(getArgument(_nodes, o));
        }
        return a_;
    }
    protected static Argument getArgument(IdMap<ExecOperationNode,ArgumentsPair> _nodes, ExecOperationNode _node) {
        return Argument.getNullableValue(getArgumentPair(_nodes,_node).getArgument());
    }
    protected static ArgumentsPair getArgumentPair(IdMap<ExecOperationNode,ArgumentsPair> _nodes, ExecOperationNode _node) {
        return _nodes.getValue(_node.getOrder());
    }
    protected static Argument getPreviousArgument(IdMap<ExecOperationNode,ArgumentsPair> _nodes, ExecOperationNode _node) {
        return Argument.getNullableValue(_nodes.getValue(_node.getOrder()).getPreviousArgument());
    }

    private void setNextSiblingsArg(ContextEl _cont, IdMap<ExecOperationNode, ArgumentsPair> _nodes) {
        if (_cont.callsOrException()) {
            return;
        }
        String un_ = resultClass.getUnwrapObject();
        Argument last_ = Argument.getNullableValue(_nodes.getValue(getOrder()).getArgument());
        if (resultClass.isCheckOnlyNullPe() || !un_.isEmpty()) {
            if (last_.isNull()) {
                LgNames stds_ = _cont.getStandards();
                String null_;
                null_ = stds_.getAliasNullPe();
                setRelativeOffsetPossibleLastPage(getIndexInEl(), _cont);
                _cont.setException(new ErrorStruct(_cont,null_));
                return;
            }
        }
        if (!un_.isEmpty()) {
            Argument arg_ = new Argument(PrimitiveTypeUtil.unwrapObject(un_, last_.getStruct(), _cont.getStandards()));
            _nodes.getValue(getOrder()).setArgument(arg_);
        }
    }


    public final ExecMethodOperation getParent() {
        return parent;
    }
    public static int getNextIndex(ExecOperationNode _operation, Struct _value) {
        int index_ = _operation.getIndexChild();
        ExecMethodOperation par_ = _operation.getParent();
        if (par_ instanceof ExecCompoundAffectationOperation) {
            ExecCompoundAffectationOperation p_ = (ExecCompoundAffectationOperation)par_;
            if (StringList.quickEq(p_.getOper(),"??=")) {
                if (_value != NullStruct.NULL_VALUE) {
                    return par_.getOrder();
                }
            }
        }
        if (par_ instanceof ExecCompoundAffectationOperation) {
            ExecCompoundAffectationOperation p_ = (ExecCompoundAffectationOperation)par_;
            if (StringList.quickEq(p_.getOper(),"&&=")) {
                if (BooleanStruct.isFalse(_value)) {
                    return par_.getOrder();
                }
            }
            if (StringList.quickEq(p_.getOper(),"||=")) {
                if (BooleanStruct.isTrue(_value)) {
                    return par_.getOrder();
                }
            }
        }
        if (par_ instanceof ExecSafeDotOperation) {
            if (_value == NullStruct.NULL_VALUE) {
                ExecOperationNode last_ = par_.getChildrenNodes().last();
                boolean skip_ = true;
                if (last_ instanceof ExecAbstractLambdaOperation) {
                    skip_ = false;
                }
                if (skip_) {
                    ExecMethodOperation p_ = par_;
                    while (p_ != null) {
                        ExecSettableElResult set_ = null;
                        if (p_ instanceof ExecCompoundAffectationOperation) {
                            set_ = ((ExecCompoundAffectationOperation)p_).getSettable();
                        }
                        if (p_ instanceof ExecAffectationOperation) {
                            set_ = ((ExecAffectationOperation)p_).getSettable();
                        }
                        if (set_ == last_) {
                            return p_.getOrder();
                        }
                        p_ = p_.getParent();
                    }
                    return par_.getOrder();
                }
            }
        }
        if (par_ instanceof ExecNullSafeOperation) {
            if (_value != NullStruct.NULL_VALUE) {
                return par_.getOrder();
            }
        }
        if (par_ instanceof ExecQuickOperation) {

            ExecQuickOperation q_ = (ExecQuickOperation) par_;
            if (q_.match(_value)) {
                return par_.getOrder();
            }
        }
        if (par_ instanceof ExecTernaryOperation) {
            if (index_ == 1) {
                return par_.getOrder();
            }
            if (index_ == 0) {
                if (BooleanStruct.isFalse(_value)) {
                    return _operation.getNextSibling().getOrder() + 1;
                }
            }
        }
        return _operation.getOrder() + 1;
    }

    public final int getOrder() {
        return order;
    }

    public final void setOrder(int _order) {
        order = _order;
    }

    public final int getIndexInEl() {
        return indexInEl;
    }

    public final int getIndexChild() {
        return indexChild;
    }

    public final Argument getArgument() {
        return argument;
    }

    public final void setSimpleArgument(Argument _argument, ContextEl _conf, IdMap<ExecOperationNode, ArgumentsPair> _nodes) {
        setQuickConvertSimpleArgument(_argument, _conf, _nodes);
        setNextSiblingsArg(_conf, _nodes);
    }

    public final void setConstantSimpleArgument(Argument _argument, ContextEl _conf, IdMap<ExecOperationNode, ArgumentsPair> _nodes) {
        setQuickSimpleArgument(false,_argument, _conf, _nodes);
        setNextSiblingsArg(_conf, _nodes);
    }

    protected final void setQuickNoConvertSimpleArgument(Argument _argument, ContextEl _conf, IdMap<ExecOperationNode, ArgumentsPair> _nodes) {
        setQuickSimpleArgument(false,_argument, _conf, _nodes);
    }
    protected final void setQuickConvertSimpleArgument(Argument _argument, ContextEl _conf, IdMap<ExecOperationNode, ArgumentsPair> _nodes) {
        setQuickSimpleArgument(true,_argument, _conf, _nodes);
    }
    protected final void setQuickSimpleArgument(boolean _possiblePartial,Argument _argument, ContextEl _conf, IdMap<ExecOperationNode, ArgumentsPair> _nodes) {
        if (_conf.callsOrException()) {
            return;
        }
        ArgumentsPair pair_ = getArgumentPair(_nodes,this);
        ImplicitMethods implicitsTest_ = pair_.getImplicitsTest();
        int indexImplicitTest_ = pair_.getIndexImplicitTest();
        Argument before_ = _argument;
        if (!implicitsTest_.isEmpty()) {
            if (implicitsTest_.isValidIndex(indexImplicitTest_)) {
                pair_.setArgumentBeforeTest(_argument);
                pair_.setIndexImplicitTest(processConverter(_conf,_argument,implicitsTest_,indexImplicitTest_));
                return;
            }
            if (!pair_.isCalcArgumentTest()) {
                pair_.setArgumentTest(BooleanStruct.isTrue(Argument.getNull(_argument.getStruct())));
                pair_.setCalcArgumentTest(true);
                before_ = Argument.getNullableValue(pair_.getArgumentBeforeTest());
            }
            ExecMethodOperation parent_ = getParent();
            if (parent_ == null||parent_ instanceof ExecTernaryOperation) {
                calcArg(_possiblePartial, _conf, _nodes, _argument);
                return;
            }
        } else {
            if (getNextSibling() != null) {
                ExecMethodOperation parent_ = getParent();
                if (parent_ instanceof ExecCompoundAffectationOperation) {
                    ExecCompoundAffectationOperation par_ = (ExecCompoundAffectationOperation) parent_;
                    if (StringList.quickEq(par_.getOper(), "&&=")){
                        if (!pair_.isCalcArgumentTest()) {
                            pair_.setArgumentTest(BooleanStruct.isFalse(Argument.getNull(_argument.getStruct())));
                            pair_.setCalcArgumentTest(true);
                        }
                    }
                    if (StringList.quickEq(par_.getOper(), "||=")){
                        if (!pair_.isCalcArgumentTest()) {
                            pair_.setArgumentTest(BooleanStruct.isTrue(Argument.getNull(_argument.getStruct())));
                            pair_.setCalcArgumentTest(true);
                        }
                    }
                } else if (parent_ instanceof ExecAndOperation) {
                    if (!pair_.isCalcArgumentTest()) {
                        pair_.setArgumentTest(BooleanStruct.isFalse(Argument.getNull(_argument.getStruct())));
                        pair_.setCalcArgumentTest(true);
                    }
                } else if (parent_ instanceof ExecOrOperation) {
                    if (!pair_.isCalcArgumentTest()) {
                        pair_.setArgumentTest(BooleanStruct.isTrue(Argument.getNull(_argument.getStruct())));
                        pair_.setCalcArgumentTest(true);
                    }
                }
            }
        }
        if (pair_.isArgumentTest()) {
            calcArg(_possiblePartial, _conf, _nodes, before_);
            return;
        }
        ImplicitMethods implicits_ = pair_.getImplicits();
        int indexImplicit_ = pair_.getIndexImplicit();
        if (implicits_.isValidIndex(indexImplicit_)) {
            pair_.setIndexImplicit(processConverter(_conf,before_,implicits_,indexImplicit_));
            return;
        }
        Argument arg_ = before_;
        if (resultClass.isConvertToString()){
            arg_ = processString(before_,_conf);
            if (_conf.getCallingState() != null) {
                return;
            }
        }
        calcArg(_possiblePartial, _conf, _nodes, arg_);
    }

    private void calcArg(boolean _possiblePartial, ContextEl _conf, IdMap<ExecOperationNode, ArgumentsPair> _nodes, Argument arg_) {
        ExecPossibleIntermediateDotted n_ = getSiblingSet();
        if (n_ instanceof ExecOperationNode) {
            _nodes.getValue(((ExecOperationNode)n_).getOrder()).setPreviousArgument(arg_);
        }
        ArgumentsPair pair_ = _nodes.getValue(getOrder());
        pair_.setArgument(arg_);
        Struct valueStruct_ = getValueStruct(this, pair_);
        _conf.getCoverage().passBlockOperation(_conf, this,new Argument(valueStruct_),!_possiblePartial);
    }
    private static Struct getValueStruct(ExecOperationNode _oper, ArgumentsPair _v) {
        Argument res_ = Argument.getNullableValue(_v.getArgument());
        Struct v_ = res_.getStruct();
        if (_oper.getNextSibling() != null&&!_oper.getResultClass().getImplicitsTest().isEmpty()){
            ExecMethodOperation par_ = _oper.getParent();
            if (par_ instanceof ExecAndOperation){
                v_ = BooleanStruct.of(!_v.isArgumentTest());
            }
            if (par_ instanceof ExecOrOperation){
                v_ = BooleanStruct.of(_v.isArgumentTest());
            }
            if (par_ instanceof ExecCompoundAffectationOperation){
                ExecCompoundAffectationOperation p_ = (ExecCompoundAffectationOperation) par_;
                if (StringList.quickEq(p_.getOper(),"&&=")) {
                    v_ = BooleanStruct.of(!_v.isArgumentTest());
                }
                if (StringList.quickEq(p_.getOper(),"||=")) {
                    v_ = BooleanStruct.of(_v.isArgumentTest());
                }
            }
        }
        return v_;
    }
    public static Argument processString(Argument _argument, ContextEl _conf) {
        Struct struct_ = Argument.getNullableValue(_argument).getStruct();
        String argClassName_ = _conf.getStandards().getStructClassName(struct_, _conf);
        String idCl_ = StringExpUtil.getIdFromAllTypes(argClassName_);
        PolymorphMethod valBody_ = _conf.getClasses().getToStringMethodsToCallBodies().getVal(idCl_);
        String clCall_ = "";
        ExecNamedFunctionBlock methodCallBody_ = null;
        ExecRootBlock type_ = null;
        if (valBody_ != null) {
            ExecOverrideInfo polymorphMethod_ = ExecInvokingOperation.polymorph(_conf, struct_, valBody_.getRootBlock(), valBody_.getNamed());
            methodCallBody_ = polymorphMethod_.getOverridableBlock();
            clCall_ = polymorphMethod_.getClassName();
            type_ = polymorphMethod_.getRootBlock();
        } else {
            argClassName_ = " ";
        }
        clCall_ = ExecTemplates.getOverridingFullTypeByBases(argClassName_,clCall_,_conf);
        if (methodCallBody_ == null) {
            return new Argument(ExecCatOperation.getDisplayable(_argument,_conf));
        }
        Parameters parameters_ = new Parameters();
        Argument out_ = new Argument(struct_);
        _conf.setCallingState(new CustomFoundMethod(out_,clCall_,type_,methodCallBody_,parameters_));
        return out_;
    }

    public final ClassArgumentMatching getResultClass() {
        return resultClass;
    }

    public final ExecPossibleIntermediateDotted getSiblingSet() {
        return siblingSet;
    }

    public final void setSiblingSet(ExecPossibleIntermediateDotted _siblingSet) {
        siblingSet = _siblingSet;
    }

    public ImplicitMethods getImplicits() {
        return implicits;
    }

    public ImplicitMethods getImplicitsTest() {
        return implicitsTest;
    }
}
