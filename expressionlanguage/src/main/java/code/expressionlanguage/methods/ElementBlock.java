package code.expressionlanguage.methods;
import code.expressionlanguage.AbstractInstancingPageEl;
import code.expressionlanguage.AbstractPageEl;
import code.expressionlanguage.Analyzable;
import code.expressionlanguage.AnalyzedPageEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.ElUtil;
import code.expressionlanguage.FieldInitPageEl;
import code.expressionlanguage.OffsetStringInfo;
import code.expressionlanguage.OffsetsBlock;
import code.expressionlanguage.methods.util.UnexpectedTagName;
import code.expressionlanguage.opers.Calculation;
import code.expressionlanguage.opers.ExpressionLanguage;
import code.expressionlanguage.opers.OperationNode;
import code.expressionlanguage.opers.util.AssignedVariables;
import code.expressionlanguage.opers.util.Assignment;
import code.expressionlanguage.opers.util.AssignmentBefore;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.SimpleAssignment;
import code.expressionlanguage.opers.util.Struct;
import code.sml.Element;
import code.util.CustList;
import code.util.EntryCust;
import code.util.IdMap;
import code.util.NatTreeMap;
import code.util.ObjectMap;
import code.util.StringList;

public final class ElementBlock extends Leaf implements InfoBlock{

    private static final String NEW = "$new ";

    private final String fieldName;

    private final String value;

    private CustList<OperationNode> opValue;

    private int fieldNameOffest;

    private int valueOffest;

    public ElementBlock(Element _el, ContextEl _importingPage, int _indexChild,
            BracedBlock _m) {
        super(_el, _importingPage, _indexChild, _m);
        fieldName = _el.getAttribute(ATTRIBUTE_NAME);
        value = _el.getAttribute(ATTRIBUTE_VALUE);
    }

    public ElementBlock(ContextEl _importingPage, int _indexChild,
            BracedBlock _m, OffsetStringInfo _fieldName, OffsetStringInfo _value, OffsetsBlock _offset) {
        super(_importingPage, _indexChild, _m, _offset);
        fieldNameOffest = _fieldName.getOffset();
        valueOffest = _value.getOffset();
        fieldName = _fieldName.getInfo();
        value = _value.getInfo();
    }

    @Override
    public int getFieldNameOffset() {
        return fieldNameOffest;
    }

    public int getValueOffest() {
        return valueOffest;
    }

    public ExpressionLanguage getValueEl() {
        return new ExpressionLanguage(opValue);
    }

    @Override
    public AccessEnum getAccess() {
        return AccessEnum.PUBLIC;
    }

    @Override
    public boolean isFinalField() {
        return true;
    }

    @Override
    public boolean isStaticField() {
        return true;
    }

    @Override
    public String getClassName() {
        Block b_ = getParent();
        RootBlock r_ = (RootBlock) b_;
        return r_.getFullName();
    }

    @Override
    public String getFieldName() {
        return fieldName;
    }

    public String getValue() {
        return value;
    }

    @Override
    public void setAssignmentBefore(Analyzable _an, AnalyzingEl _anEl) {
        Block prev_ = getPreviousSibling();
        if (prev_ != null && !(prev_ instanceof ElementBlock)) {
            UnexpectedTagName un_ = new UnexpectedTagName();
            un_.setFileName(prev_.getFile().getFileName());
            un_.setRc(prev_.getRowCol(0, getOffset().getOffsetTrim()));
            _an.getClasses().getErrorsDet().add(un_);
            return;
        }
        if (!(getParent() instanceof EnumBlock)) {
            UnexpectedTagName un_ = new UnexpectedTagName();
            un_.setFileName(getFile().getFileName());
            un_.setRc(getRowCol(0, getOffset().getOffsetTrim()));
            _an.getClasses().getErrorsDet().add(un_);
            return;
        }
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
            AssignedVariables assBl_ = buildNewAssignedVariable();
            for (EntryCust<ClassField, SimpleAssignment> e: parAss_.getFieldsRoot().entryList()) {
                AssignmentBefore asBef_ = new AssignmentBefore();
                if (e.getValue().isAssignedAfter()) {
                    asBef_.setAssignedBefore(true);
                }
                if (e.getValue().isUnassignedAfter()) {
                    asBef_.setUnassignedBefore(true);
                }
                assBl_.getFieldsRootBefore().put(e.getKey(), asBef_);
            }
            assBl_.getFieldsRoot().putAllMap(parAss_.getFieldsRoot());
            id_.put(this, assBl_);
        }
    }

    @Override
    public void buildExpressionLanguage(ContextEl _cont) {
        AnalyzedPageEl page_ = _cont.getAnalyzing();
        page_.setGlobalOffset(fieldNameOffest);
        page_.setOffset(0);
        String className_ = getClassName();
        String fullInstance_ = StringList.concat(NEW,className_, PAR_LEFT, value, PAR_RIGHT);
        page_.setTranslatedOffset(valueOffest - fieldNameOffest - 1 - NEW.length() - className_.length());
        int index_ = 0;
        Block n_ = getPreviousSibling();
        while (n_ != null) {
            index_++;
            n_ = n_.getPreviousSibling();
        }
        _cont.setCurrentChildTypeIndex(index_);
        _cont.setRootAffect(false);
        opValue = ElUtil.getAnalyzedOperations(fullInstance_, _cont, new Calculation(fieldName));
        page_.setTranslatedOffset(0);
    }
    @Override
    public void setAssignmentAfter(Analyzable _an, AnalyzingEl _anEl) {
        String className_ = getClassName();
        AssignedVariablesBlock glAss_ = _an.getAssignedVariables();
        AssignedVariables varsAss_ = glAss_.getFinalVariables().getVal(this);
        ObjectMap<ClassField,SimpleAssignment> as_ = varsAss_.getFieldsRoot();
        for (EntryCust<ClassField, AssignmentBefore> e: varsAss_.getFieldsRootBefore().entryList()) {
            as_.put(e.getKey(), e.getValue().assignAfterClassic());
        }
        as_.put(new ClassField(className_, fieldName), Assignment.assignClassic(true, false));
    }
    @Override
    boolean canBeLastOfBlockGroup() {
        return false;
    }

    @Override
    public NatTreeMap<String, String> getClassNames(ContextEl _context) {
        NatTreeMap<String,String> tr_ = new NatTreeMap<String,String>();
        tr_.put(ATTRIBUTE_CLASS, getClassName());
        return tr_;
    }

    @Override
    public NatTreeMap<Integer,String> getClassNamesOffsets(ContextEl _context) {
        NatTreeMap<Integer,String> tr_ = new NatTreeMap<Integer,String>();
        return tr_;
    }

    @Override
    public String getTagName() {
        return TAG_ELEMENT;
    }

    @Override
    public void processEl(ContextEl _cont) {
        AbstractPageEl ip_ = _cont.getLastPage();
        if (!(ip_ instanceof AbstractInstancingPageEl) && !(ip_ instanceof FieldInitPageEl)) {
            ip_.setGlobalOffset(fieldNameOffest);
            ip_.setOffset(0);
            String name_ = getFieldName();
            Struct struct_;
            ExpressionLanguage el_ = ip_.getCurrentEl(_cont, this, CustList.FIRST_INDEX, false, CustList.FIRST_INDEX);
            String className_ = getClassName();
            Argument arg_ = el_.calculateMember(_cont, valueOffest - fieldNameOffest - 1 - NEW.length() - className_.length());
            if (_cont.callsOrException()) {
                return;
            }
            struct_ = arg_.getStruct();
            el_.setCurrentOper(null);
            ip_.clearCurrentEls();
            RootBlock r_ = getRooted();
            ClassField staticField_ = new ClassField(r_.getFullName(), name_);
            _cont.getClasses().initializeStaticField(staticField_, struct_);
        }
        processBlock(_cont);
    }

    @Override
    public RootBlock belong() {
        return (RootBlock) getParent();
    }

    @Override
    public ExpressionLanguage getEl(ContextEl _context, boolean _native,
            int _indexProcess) {
        return getValueEl();
    }
}
