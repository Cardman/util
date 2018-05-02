package code.expressionlanguage.methods;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.ElUtil;
import code.expressionlanguage.OffsetStringInfo;
import code.expressionlanguage.OffsetsBlock;
import code.expressionlanguage.PageEl;
import code.expressionlanguage.ReadWrite;
import code.expressionlanguage.methods.util.BadConstructorCall;
import code.expressionlanguage.methods.util.EqualsEl;
import code.expressionlanguage.methods.util.UnexpectedTagName;
import code.expressionlanguage.methods.util.UnexpectedTypeError;
import code.expressionlanguage.opers.Calculation;
import code.expressionlanguage.opers.ExpressionLanguage;
import code.expressionlanguage.opers.OperationNode;
import code.expressionlanguage.opers.util.Struct;
import code.expressionlanguage.stacks.SwitchBlockStack;
import code.expressionlanguage.variables.LocalVariable;
import code.sml.Element;
import code.util.CustList;
import code.util.NatTreeMap;

public final class CaseCondition extends BracedStack implements StackableBlockGroup, IncrCurrentGroup, IncrNextGroup {

    private final String value;
    private CustList<OperationNode> opValue;
    private boolean possibleSkipNexts;

    private int valueOffset;

    public CaseCondition(Element _el, ContextEl _importingPage,
            int _indexChild, BracedBlock _m) {
        super(_el, _importingPage, _indexChild, _m);
        value = _el.getAttribute(ATTRIBUTE_VALUE);
    }

    public CaseCondition(ContextEl _importingPage,
            int _indexChild, BracedBlock _m, OffsetStringInfo _value, OffsetsBlock _offset) {
        super(_importingPage, _indexChild, _m, _offset);
        value = _value.getInfo();
        valueOffset = _value.getOffset();
    }

    public int getValueOffset() {
        return valueOffset;
    }

    public String getValue() {
        return value;
    }

    @Override
    public NatTreeMap<String,String> getClassNames(ContextEl _context) {
        NatTreeMap<String,String> tr_ = new NatTreeMap<String,String>();
        return tr_;
    }

    @Override
    public NatTreeMap<Integer,String> getClassNamesOffsets(ContextEl _context) {
        NatTreeMap<Integer,String> tr_ = new NatTreeMap<Integer,String>();
        return tr_;
    }
    public ExpressionLanguage getValueEl() {
        return new ExpressionLanguage(opValue);
    }

    @Override
    boolean isAlwaysExitable() {
        return getFirstChild() == null || !isPossibleSkipNexts();
    }

    @Override
    public void checkBlocksTree(ContextEl _cont) {
        PageEl page_ = _cont.getLastPage();
        BracedBlock b_ = getParent();
        if (!(b_ instanceof SwitchBlock)) {
            page_.setGlobalOffset(getOffset().getOffsetTrim());
            page_.setOffset(0);
            UnexpectedTagName un_ = new UnexpectedTagName();
            un_.setFileName(getFile().getFileName());
            un_.setRc(getRowCol(0, getOffset().getOffsetTrim()));
            _cont.getClasses().getErrorsDet().add(un_);
        }
    }

    boolean isPossibleSkipNexts() {
        return possibleSkipNexts;
    }

    void setPossibleSkipNexts(boolean _possibleSkipNexts) {
        possibleSkipNexts = _possibleSkipNexts;
    }

    @Override
    public void buildExpressionLanguage(ContextEl _cont) {
        FunctionBlock f_ = getFunction();
        PageEl page_ = _cont.getLastPage();
        page_.setGlobalOffset(valueOffset);
        page_.setOffset(0);
        _cont.setRootAffect(false);
        opValue = ElUtil.getAnalyzedOperations(value, _cont, Calculation.staticCalculation(f_.isStaticContext()));
        if (opValue.isEmpty()) {
            return;
        }
        if (opValue.last().isVoidArg(_cont)) {
            UnexpectedTypeError un_ = new UnexpectedTypeError();
            un_.setFileName(getFile().getFileName());
            un_.setRc(getRowCol(0, valueOffset));
            un_.setType(opValue.last().getResultClass().getName());
            _cont.getClasses().getErrorsDet().add(un_);
        }
    }

    @Override
    boolean canBeIncrementedNextGroup() {
        return true;
    }

    @Override
    boolean canBeIncrementedCurGroup() {
        return true;
    }

    @Override
    boolean canBeLastOfBlockGroup() {
        return false;
    }

    @Override
    public void checkCallConstructor(ContextEl _cont) {
        PageEl p_ = _cont.getLastPage();
        p_.setGlobalOffset(valueOffset);
        for (OperationNode o: opValue) {
            if (o.isSuperThis()) {
                int off_ = o.getFullIndexInEl();
                p_.setOffset(off_);
                BadConstructorCall call_ = new BadConstructorCall();
                call_.setFileName(getFile().getFileName());
                call_.setRc(getRowCol(0, valueOffset));
                call_.setLocalOffset(getRowCol(o.getFullIndexInEl(), valueOffset));
                _cont.getClasses().getErrorsDet().add(call_);
            }
        }
    }

    @Override
    public String getTagName() {
        return TAG_CASE;
    }

    @Override
    public void processEl(ContextEl _cont) {
        PageEl ip_ = _cont.getLastPage();
        ReadWrite rw_ = ip_.getReadWrite();
        SwitchBlockStack sw_ = (SwitchBlockStack) ip_.getLastStack();
        Struct str_ = sw_.getStruct();
        Argument virtualArg_ = new Argument();
        virtualArg_.setStruct(str_);
        sw_.setVisitedBlock(getIndexInGroup());
        if (sw_.isEntered()) {
            if (!hasChildNodes()) {
                if (sw_.lastVisitedBlock() == this) {
                    sw_.setFinished(true);
                    rw_.setBlock(sw_.getBlock());
                    return;
                }
                rw_.setBlock(getNextSibling());
                return;
            }
            rw_.setBlock(getFirstChild());
            return;
        } else {
            ip_.setGlobalOffset(valueOffset);
            ip_.setOffset(0);
            ExpressionLanguage el_ = ip_.getCurrentEl(_cont,this, CustList.FIRST_INDEX, false,CustList.FIRST_INDEX);
            Argument arg_ = el_.calculateMember(_cont);
            if (_cont.callsOrException()) {
                return;
            }
            el_.setCurrentOper(null);
            ip_.clearCurrentEls();
            boolean enter_ = false;
            if (str_.isNull()) {
                if (arg_.isNull()) {
                    enter_ = true;
                }
            } else {
                Classes cl_ = _cont.getClasses();
                EqualsEl eq_ = cl_.getNatEqEl();
                String loc_ = eq_.getFirstArg();
                LocalVariable local_ = new LocalVariable();
                local_.setStruct(str_);
                local_.setClassName(_cont.getStandards().getAliasObject());
                ip_.putLocalVar(loc_, local_);
                String locSec_ = eq_.getSecondArg();
                local_ = new LocalVariable();
                local_.setStruct(arg_.getStruct());
                local_.setClassName(_cont.getStandards().getAliasObject());
                ip_.putLocalVar(locSec_, local_);
                Argument eqArg_ = cl_.getEqNatEl().calculateMember(_cont);
                if (_cont.callsOrException()) {
                    return;
                }
                boolean b_ = (Boolean) eqArg_.getObject();
                ip_.removeLocalVar(loc_);
                ip_.removeLocalVar(locSec_);
                if (b_) {
                    enter_ = true;
                }
            }
            if (enter_) {
                if (hasChildNodes()) {
                    sw_.setEntered(true);
                } else {
                    if (sw_.lastVisitedBlock() != this) {
                        sw_.setEntered(true);
                        rw_.setBlock(getNextSibling());
                        return;
                    } else {
                        sw_.setFinished(true);
                        rw_.setBlock(sw_.getBlock());
                        return;
                    }
                }
                rw_.setBlock(getFirstChild());
                return;
            }
            if (sw_.lastVisitedBlock() == this) {
                sw_.setFinished(true);
                rw_.setBlock(sw_.getBlock());
                return;
            }
            rw_.setBlock(getNextSibling());
            return;
        }
    }

    @Override
    public void exitStack(ContextEl _context) {
        PageEl ip_ = _context.getLastPage();
        ReadWrite rw_ = ip_.getReadWrite();
        SwitchBlockStack if_ = (SwitchBlockStack) ip_.getLastStack();
        if (if_.lastVisitedBlock() == this) {
            if_.setFinished(true);
            rw_.setBlock(if_.getBlock());
        } else {
            rw_.setBlock(getNextSibling());
        }
    }

    @Override
    public ExpressionLanguage getEl(ContextEl _context, boolean _native,
            int _indexProcess) {
        return getValueEl();
    }
}
