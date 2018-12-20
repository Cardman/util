package code.expressionlanguage.methods;
import code.expressionlanguage.AnalyzedPageEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Delimiters;
import code.expressionlanguage.ElResolver;
import code.expressionlanguage.ElUtil;
import code.expressionlanguage.OffsetStringInfo;
import code.expressionlanguage.OffsetsBlock;
import code.expressionlanguage.OperationsSequence;
import code.expressionlanguage.PrimitiveTypeUtil;
import code.expressionlanguage.ReadWrite;
import code.expressionlanguage.Templates;
import code.expressionlanguage.calls.AbstractPageEl;
import code.expressionlanguage.common.GeneField;
import code.expressionlanguage.common.GeneType;
import code.expressionlanguage.errors.custom.UnexpectedTagName;
import code.expressionlanguage.errors.custom.UnexpectedTypeError;
import code.expressionlanguage.opers.Calculation;
import code.expressionlanguage.opers.ExpressionLanguage;
import code.expressionlanguage.opers.OperationNode;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.stacks.SwitchBlockStack;
import code.util.CustList;
import code.util.StringList;

public final class CaseCondition extends SwitchPartBlock {

    private final String value;
    private CustList<OperationNode> opValue;

    private int valueOffset;

    public CaseCondition(ContextEl _importingPage,
            BracedBlock _m, OffsetStringInfo _value, OffsetsBlock _offset) {
        super(_importingPage, _m, _offset);
        value = _value.getInfo();
        valueOffset = _value.getOffset();
    }

    public int getValueOffset() {
        return valueOffset;
    }

    public String getValue() {
        return value;
    }

    public ExpressionLanguage getValueEl() {
        return new ExpressionLanguage(opValue);
    }

    @Override
    public void buildExpressionLanguage(ContextEl _cont) {
        FunctionBlock f_ = getFunction();
        AnalyzedPageEl page_ = _cont.getAnalyzing();
        page_.setGlobalOffset(valueOffset);
        page_.setOffset(0);
        BracedBlock par_ = getParent();
        if (!(par_ instanceof SwitchBlock)) {
            page_.setGlobalOffset(getOffset().getOffsetTrim());
            page_.setOffset(0);
            UnexpectedTagName un_ = new UnexpectedTagName();
            un_.setFileName(getFile().getFileName());
            un_.setIndexFile(getOffset().getOffsetTrim());
            _cont.getClasses().addError(un_);
            opValue = ElUtil.getAnalyzedOperations(value, _cont, Calculation.staticCalculation(f_.isStaticContext()));
            return;
        }
        SwitchBlock sw_ = (SwitchBlock) par_;
        ClassArgumentMatching resSwitch_ = sw_.getOpValue().last().getResultClass();
        StringList names_ = resSwitch_.getNames();
        if (names_.size() == 1) {
            String exp_ = names_.first();
            String id_ = Templates.getIdFromAllTypes(exp_);
            GeneType g_ = _cont.getClassBody(id_);
            if (g_ instanceof EnumBlock) {
                for (GeneField f: ContextEl.getFieldBlocks(g_)) {
                    if (!(f instanceof ElementBlock)) {
                        continue;
                    }
                    ElementBlock e_ = (ElementBlock) f;
                    if (!StringList.quickEq(e_.getUniqueFieldName(), value.trim())) {
                        continue;
                    }
                    _cont.setLookLocalClass(id_);
                    _cont.setStaticContext(true);
                    Delimiters d_ = ElResolver.checkSyntax(value, _cont, CustList.FIRST_INDEX);
                    OperationsSequence opTwo_ = ElResolver.getOperationsSequence(CustList.FIRST_INDEX, value, _cont, d_);
                    OperationNode op_ = OperationNode.createOperationNode(CustList.FIRST_INDEX, CustList.FIRST_INDEX, null, opTwo_, _cont);
                    defaultAssignmentBefore(_cont, op_);
                    op_.setStaticBlock(true);
                    op_.analyze(_cont);
                    _cont.setLookLocalClass(EMPTY_STRING);
                    op_.tryAnalyzeAssignmentAfter(_cont);
                    op_.setOrder(0);
                    opValue = new CustList<OperationNode>();
                    opValue.add(op_);
                    defaultAssignmentAfter(_cont, op_);
                    checkDuplicateEnumCase(_cont);
                    return;
                }
                opValue = ElUtil.getAnalyzedOperations(value, _cont, Calculation.staticCalculation(f_.isStaticContext()));
                Argument a_ = opValue.last().getArgument();
                if (Argument.isNullValue(a_)) {
                    checkDuplicateCase(_cont, a_);
                    return;
                }
                UnexpectedTypeError un_ = new UnexpectedTypeError();
                un_.setFileName(getFile().getFileName());
                un_.setIndexFile(valueOffset);
                un_.setType(opValue.last().getResultClass());
                _cont.getClasses().addError(un_);
                return;
            }
        }
        opValue = ElUtil.getAnalyzedOperations(value, _cont, Calculation.staticCalculation(f_.isStaticContext()));
        OperationNode op_ = opValue.last();
        ClassArgumentMatching resCase_ = op_.getResultClass();
        if (resCase_.matchVoid(_cont)) {
            UnexpectedTypeError un_ = new UnexpectedTypeError();
            un_.setFileName(getFile().getFileName());
            un_.setIndexFile(valueOffset);
            un_.setType(resCase_);
            _cont.getClasses().addError(un_);
        }
        Argument arg_ = op_.getArgument();
        if (arg_ == null) {
            UnexpectedTypeError un_ = new UnexpectedTypeError();
            un_.setFileName(getFile().getFileName());
            un_.setIndexFile(valueOffset);
            un_.setType(resCase_);
            _cont.getClasses().addError(un_);
        } else {
            checkDuplicateCase(_cont, arg_);
        }
        if (!PrimitiveTypeUtil.canBeUseAsArgument(false, resSwitch_, resCase_, _cont)) {
            UnexpectedTypeError un_ = new UnexpectedTypeError();
            un_.setFileName(getFile().getFileName());
            un_.setIndexFile(valueOffset);
            un_.setType(resCase_);
            _cont.getClasses().addError(un_);
        }
    }

    public CustList<OperationNode> getOpValue() {
        return opValue;
    }

    private void checkDuplicateCase(ContextEl _cont, Argument _arg) {
        BracedBlock par_ = getParent();
        Block first_ = par_.getFirstChild();
        while (first_ != this) {
            if (first_ instanceof CaseCondition) {
                CaseCondition c_ = (CaseCondition) first_;
                OperationNode curOp_ = c_.opValue.last();
                Argument a_ = curOp_.getArgument();
                if (a_ != null) {
                    if (_arg.getStruct().sameReference(a_.getStruct())) {
                        UnexpectedTagName un_ = new UnexpectedTagName();
                        un_.setFileName(getFile().getFileName());
                        un_.setIndexFile(getValueOffset()+ getOffset().getOffsetTrim());
                        _cont.getClasses().addError(un_);
                        break;
                    }
                }
            }
            first_ = first_.getNextSibling();
        }
    }
    private void checkDuplicateEnumCase(ContextEl _cont) {
        BracedBlock par_ = getParent();
        Block first_ = par_.getFirstChild();
        while (first_ != this) {
            if (first_ instanceof CaseCondition) {
                CaseCondition c_ = (CaseCondition) first_;
                String v_ = c_.value.trim();
                if (StringList.quickEq(v_, value.trim())) {
                    UnexpectedTagName un_ = new UnexpectedTagName();
                    un_.setFileName(getFile().getFileName());
                    un_.setIndexFile(getValueOffset()+ getOffset().getOffsetTrim());
                    _cont.getClasses().addError(un_);
                    break;
                }
                
            }
            first_ = first_.getNextSibling();
        }
    }
    @Override
    public void processEl(ContextEl _cont) {
        AbstractPageEl ip_ = _cont.getLastPage();
        ReadWrite rw_ = ip_.getReadWrite();
        SwitchBlockStack sw_ = (SwitchBlockStack) ip_.getLastStack();
        sw_.setCurentVisitedBlock(this);
        if (sw_.isEntered()) {
            rw_.setBlock(getFirstChild());
            return;
        }
        ip_.setGlobalOffset(valueOffset);
        ip_.setOffset(0);
        sw_.setEntered(true);
        rw_.setBlock(getFirstChild());
    }

    @Override
    public void exitStack(ContextEl _context) {
        AbstractPageEl ip_ = _context.getLastPage();
        ReadWrite rw_ = ip_.getReadWrite();
        SwitchBlockStack if_ = (SwitchBlockStack) ip_.getLastStack();
        if (if_.getLastVisitedBlock() == this) {
            if_.setFinished(true);
            rw_.setBlock(if_.getBlock());
        } else {
            rw_.setBlock(getNextSibling());
        }
    }
}
