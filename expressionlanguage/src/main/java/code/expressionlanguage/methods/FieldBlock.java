package code.expressionlanguage.methods;
import code.expressionlanguage.Analyzable;
import code.expressionlanguage.AnalyzedPageEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.calls.AbstractPageEl;
import code.expressionlanguage.calls.FieldInitPageEl;
import code.expressionlanguage.calls.StaticInitPageEl;
import code.expressionlanguage.errors.custom.UnexpectedTagName;
import code.expressionlanguage.files.OffsetAccessInfo;
import code.expressionlanguage.files.OffsetBooleanInfo;
import code.expressionlanguage.files.OffsetStringInfo;
import code.expressionlanguage.files.OffsetsBlock;
import code.expressionlanguage.instr.ElUtil;
import code.expressionlanguage.opers.Calculation;
import code.expressionlanguage.opers.ExpressionLanguage;
import code.expressionlanguage.opers.exec.ExecDeclaringOperation;
import code.expressionlanguage.opers.exec.ExecMethodOperation;
import code.expressionlanguage.opers.exec.ExecOperationNode;
import code.expressionlanguage.opers.exec.ExecSettableFieldOperation;
import code.expressionlanguage.opers.util.AssignedVariables;
import code.expressionlanguage.opers.util.AssignmentsUtil;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.SimpleAssignment;
import code.expressionlanguage.structs.Struct;
import code.util.CustList;
import code.util.EntryCust;
import code.util.EqList;
import code.util.IdMap;
import code.util.Numbers;
import code.util.StringList;

public final class FieldBlock extends Leaf implements InfoBlock {

    private final StringList fieldName;

    private final String className;

    private int classNameOffset;

    private String importedClassName;

    private final String value;

    private int valueOffset;

    private final boolean staticField;

    private int staticFieldOffset;

    private final boolean finalField;

    private int finalFieldOffset;

    private final AccessEnum access;

    private int accessOffset;

    private CustList<ExecOperationNode> opValue;
    private StringList annotations = new StringList();
    private CustList<CustList<ExecOperationNode>> annotationsOps = new CustList<CustList<ExecOperationNode>>();
    private Numbers<Integer> annotationsIndexes = new Numbers<Integer>();

    public FieldBlock(ContextEl _importingPage,
            BracedBlock _m, OffsetAccessInfo _access,
            OffsetBooleanInfo _static, OffsetBooleanInfo _final,
            StringList _name, OffsetStringInfo _type, OffsetStringInfo _value, OffsetsBlock _offset) {
        super(_m, _offset);
        access = _access.getInfo();
        accessOffset = _access.getOffset();
        staticField = _static.isInfo();
        staticFieldOffset = _static.getOffset();
        finalField = _final.isInfo();
        finalFieldOffset = _final.getOffset();
        fieldName = _name;
        className = _type.getInfo();
        classNameOffset = _type.getOffset();
        value = _value.getInfo();
        valueOffset = _value.getOffset();
    }

    @Override
    public int getFieldNameOffset() {
        return valueOffset;
    }

    public int getClassNameOffset() {
        return classNameOffset;
    }

    public int getValueOffset() {
        return valueOffset;
    }

    public int getStaticFieldOffset() {
        return staticFieldOffset;
    }

    public int getFinalFieldOffset() {
        return finalFieldOffset;
    }

    public int getAccessOffset() {
        return accessOffset;
    }

    @Override
    public AccessEnum getAccess() {
        return access;
    }

    public ExpressionLanguage getValueEl() {
        return new ExpressionLanguage(opValue);
    }

    public Struct getDefaultValue() {
        if (!finalField) {
            return null;
        }
        Argument arg_ = opValue.last().getArgument();
        if (arg_ == null) {
            return null;
        }
        return arg_.getStruct();
    }

    public CustList<ExecOperationNode> getOpValue() {
        return opValue;
    }

    @Override
    public boolean isStaticField() {
        return staticField;
    }

    @Override
    public boolean isFinalField() {
        return finalField;
    }

    @Override
    public String getClassName() {
        return className;
    }

    @Override
    public String getImportedClassName() {
        return importedClassName;
    }

    @Override
    public StringList getFieldName() {
        return fieldName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void setAssignmentBefore(Analyzable _an, AnalyzingEl _anEl) {
        if (!(getParent() instanceof RootBlock)) {
            UnexpectedTagName un_ = new UnexpectedTagName();
            un_.setFileName(getFile().getFileName());
            un_.setIndexFile(getOffset().getOffsetTrim());
            _an.getClasses().addError(un_);
            return;
        }
        Block prev_ = getPreviousSibling();
        while (prev_ != null) {
            if (prev_ instanceof InitBlock) {
                if (((InitBlock)prev_).isStaticContext() == isStaticField()) {
                    break;
                }
            }
            if (prev_ instanceof InfoBlock) {
                if (((InfoBlock)prev_).isStaticField() == isStaticField()) {
                    break;
                }
            }
            prev_ = prev_.getPreviousSibling();
        }
        AssignedVariables ass_;
        if (prev_ == null) {
            ass_ = _an.getAssignedVariables().getFinalVariablesGlobal();
            IdMap<Block, AssignedVariables> id_ = _an.getAssignedVariables().getFinalVariables();
            id_.put(this, ass_);
        } else {
            IdMap<Block, AssignedVariables> id_ = _an.getAssignedVariables().getFinalVariables();
            AssignedVariables parAss_ = id_.getVal(prev_);
            if (parAss_ == null) {
                parAss_ = _an.getAssignedVariables().getFinalVariablesGlobal();
            }
            AssignedVariables assBl_ = buildNewAssignedVariable();
            assBl_.getFieldsRootBefore().putAllMap(AssignmentsUtil.assignSimpleBefore(parAss_.getFieldsRoot()));
            assBl_.getFieldsRoot().putAllMap(parAss_.getFieldsRoot());
            id_.put(this, assBl_);
        }
    }
    @Override
    public void buildImportedType(ContextEl _cont) {
        AnalyzedPageEl page_ = _cont.getAnalyzing();
        page_.setGlobalOffset(getClassNameOffset());
        page_.setOffset(0);
        page_.setCurrentBlock(this);
        importedClassName = _cont.resolveCorrectType(className);
    }
    @Override
    public void buildExpressionLanguage(ContextEl _cont) {
        AnalyzedPageEl page_ = _cont.getAnalyzing();
        page_.setGlobalOffset(valueOffset);
        page_.setOffset(0);
        opValue = ElUtil.getAnalyzedOperations(value, _cont, Calculation.staticCalculation(staticField));
        if (_cont.isGearConst()) {
            opValue = ElUtil.getReducedNodes(opValue.last());
        }
    }
    @Override
    public void buildAnnotations(ContextEl _context) {
        annotationsOps = new CustList<CustList<ExecOperationNode>>();
        for (String a: annotations) {
            Calculation c_ = Calculation.staticCalculation(true);
            annotationsOps.add(ElUtil.getAnalyzedOperations(a, _context, c_));
        }
    }
    @Override
    public void reduce(ContextEl _context) {
        CustList<CustList<ExecOperationNode>> annotationsOps_;
        annotationsOps_ = new CustList<CustList<ExecOperationNode>>();
        for (CustList<ExecOperationNode> a: annotationsOps) {
            ExecOperationNode r_ = a.last();
            annotationsOps_.add(ElUtil.getReducedNodes(r_));
        }
        annotationsOps = annotationsOps_;
        ExecOperationNode r_ = opValue.last();
        opValue = ElUtil.getReducedNodes(r_);
    }
    @Override
    public StringList getAnnotations() {
        return annotations;
    }
    @Override
    public CustList<CustList<ExecOperationNode>> getAnnotationsOps() {
        return annotationsOps;
    }
    @Override
    public Numbers<Integer> getAnnotationsIndexes() {
        return annotationsIndexes;
    }

    public EqList<ClassField> getStaticConstantDependencies(Analyzable _an, String _name) {
        ExecOperationNode last_ = opValue.last();
        if (!(last_ instanceof ExecDeclaringOperation)) {
            EqList<ClassField> eq_;
            eq_ = getDeps(_an, opValue.mid(1));
            return eq_;
        }
        EqList<ClassField> eq_;
        ExecMethodOperation m_ = (ExecMethodOperation)last_;
        int index_ = fieldName.indexOfObj(_name);
        CustList<ExecOperationNode> ch_ = m_.getChildrenNodes();
        int from_;
        int to_ = ch_.get(index_).getOrder();
        if (index_ == 0) {
            from_ = 0;
        } else {
            from_ = ch_.get(index_-1).getOrder() + 1;
        }
        eq_ = getDeps(_an, opValue.sub(from_, to_));
        return eq_;
    }

    private static EqList<ClassField> getDeps(Analyzable _an, CustList<ExecOperationNode> _op) {
        EqList<ClassField> eq_ = new EqList<ClassField>();
        for (ExecOperationNode o: _op) {
            ClassField key_ = getDep(_an, o);
            if (key_ == null) {
                continue;
            }
            eq_.add(key_);
        }
        return eq_;
    }
    private static ClassField getDep(Analyzable _an, ExecOperationNode _op) {
        if (!(_op instanceof ExecSettableFieldOperation)) {
            return null;
        }
        ExecSettableFieldOperation cst_ = (ExecSettableFieldOperation) _op;
        return cst_.getFieldId();
    }
    @Override
    public void processEl(ContextEl _cont) {
        AbstractPageEl ip_ = _cont.getLastPage();
        boolean static_ = isStaticField();
        boolean in_ = false;
        if (ip_ instanceof FieldInitPageEl && !static_) {
            in_ = true;
        } else if (ip_ instanceof StaticInitPageEl && static_) {
            in_ = true;
        }
        if (in_) {
            ip_.setGlobalOffset(valueOffset);
            ip_.setOffset(0);
            ExpressionLanguage el_ = ip_.getCurrentEl(_cont,this, CustList.FIRST_INDEX, CustList.FIRST_INDEX);
            el_.calculateMember(_cont);
            if (_cont.callsOrException()) {
                return;
            }
            ip_.clearCurrentEls();
        }
        processBlock(_cont);
    }

    @Override
    public RootBlock belong() {
        return (RootBlock) getParent();
    }

    @Override
    public ExpressionLanguage getEl(ContextEl _context,
            int _indexProcess) {
        return getValueEl();
    }
}
