package code.expressionlanguage.methods;
import code.expressionlanguage.AbstractPageEl;
import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.OffsetStringInfo;
import code.expressionlanguage.OffsetsBlock;
import code.expressionlanguage.ReadWrite;
import code.expressionlanguage.methods.util.EmptyTagName;
import code.expressionlanguage.opers.OperationNode;
import code.expressionlanguage.opers.util.AssignedBooleanVariables;
import code.expressionlanguage.opers.util.AssignedVariables;
import code.expressionlanguage.opers.util.Assignment;
import code.expressionlanguage.opers.util.AssignmentBefore;
import code.expressionlanguage.opers.util.BooleanAssignment;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.SimpleAssignment;
import code.expressionlanguage.stacks.IfBlockStack;
import code.expressionlanguage.stacks.RemovableVars;
import code.sml.Element;
import code.util.CustList;
import code.util.EntryCust;
import code.util.IdMap;
import code.util.NatTreeMap;
import code.util.ObjectMap;
import code.util.StringMap;

public final class IfCondition extends Condition implements BlockCondition, IncrCurrentGroup {

    private String label;
    private int labelOffset;

    public IfCondition(Element _el, ContextEl _importingPage, int _indexChild,
            BracedBlock _m) {
        super(_el, _importingPage, _indexChild, _m);
    }

    public IfCondition(ContextEl _importingPage, int _indexChild,
            BracedBlock _m, OffsetStringInfo _condition, OffsetStringInfo _label, OffsetsBlock _offset) {
        super(_importingPage, _indexChild, _m, _condition, _offset);
        label = _label.getInfo();
        labelOffset = _label.getOffset();
    }

    @Override
    public String getLabel() {
        return label;
    }

    public void setLabel(String _label) {
        label = _label;
    }

    public int getLabelOffset() {
        return labelOffset;
    }

    public void setLabelOffset(int _labelOffset) {
        labelOffset = _labelOffset;
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

    @Override
    boolean canBeIncrementedNextGroup() {
        return false;
    }

    @Override
    boolean canBeIncrementedCurGroup() {
        Block next_ = getNextSibling();
        return next_ instanceof ElseIfCondition || next_ instanceof ElseCondition;
    }

    @Override
    public void setAssignmentBeforeChild(Analyzable _an, AnalyzingEl _anEl) {
        Block firstChild_ = getFirstChild();
        IdMap<Block, AssignedVariables> id_ = _an.getAssignedVariables().getFinalVariables();
        AssignedVariables parAss_ = id_.getVal(this);
        AssignedVariables assBl_ = firstChild_.buildNewAssignedVariable();
        AssignedBooleanVariables abv_ = (AssignedBooleanVariables) parAss_;
        for (EntryCust<ClassField, BooleanAssignment> e: abv_.getFieldsRootAfter().entryList()) {
            BooleanAssignment ba_ = e.getValue();
            AssignmentBefore ab_ = ba_.copyWhenTrue();
            assBl_.getFieldsRootBefore().put(e.getKey(), ab_);
        }
        for (StringMap<BooleanAssignment> s: abv_.getVariablesRootAfter()) {
            StringMap<AssignmentBefore> sm_ = new StringMap<AssignmentBefore>();
            for (EntryCust<String, BooleanAssignment> e: s.entryList()) {
                BooleanAssignment ba_ = e.getValue();
                AssignmentBefore ab_ = ba_.copyWhenTrue();
                sm_.put(e.getKey(), ab_);
            }
            assBl_.getVariablesRootBefore().add(sm_);
        }
        assBl_.getVariablesRootBefore().add(new StringMap<AssignmentBefore>());
        id_.put(firstChild_, assBl_);
    }
    @Override
    public void setAssignmentBeforeNextSibling(Analyzable _an, AnalyzingEl _anEl) {
        if (!canBeIncrementedCurGroup()) {
            super.setAssignmentBeforeNextSibling(_an, _anEl);
            return;
        }
        IdMap<Block, AssignedVariables> id_ = _an.getAssignedVariables().getFinalVariables();
        AssignedVariables prevAss_ = id_.getVal(this);
        Block nextSibling_ = getNextSibling();
        AssignedBooleanVariables assBool_ = (AssignedBooleanVariables) prevAss_;
        AssignedVariables assBl_ = nextSibling_.buildNewAssignedVariable();
        for (EntryCust<ClassField, BooleanAssignment> e: assBool_.getFieldsRootAfter().entryList()) {
            AssignmentBefore asBef_ = e.getValue().copyWhenFalse();
            assBl_.getFieldsRootBefore().put(e.getKey(), asBef_);
        }
        for (StringMap<BooleanAssignment> s: assBool_.getVariablesRootAfter()) {
            StringMap<AssignmentBefore> sm_ = new StringMap<AssignmentBefore>();
            for (EntryCust<String, BooleanAssignment> e: s.entryList()) {
                AssignmentBefore asBef_ = e.getValue().copyWhenFalse();
                sm_.put(e.getKey(), asBef_);
            }
            assBl_.getVariablesRootBefore().add(sm_);
        }
        id_.put(nextSibling_, assBl_);
    }
    @Override
    public void setAssignmentAfter(Analyzable _an, AnalyzingEl _anEl) {
        super.setAssignmentAfter(_an, _anEl);
        Block ch_ = getFirstChild();
        if (ch_ == null) {
            EmptyTagName un_ = new EmptyTagName();
            un_.setFileName(getFile().getFileName());
            un_.setRc(getRowCol(0, getOffset().getOffsetTrim()));
            _an.getClasses().getErrorsDet().add(un_);
            return;
        }
        if (canBeIncrementedCurGroup()) {
            return;
        }
        IdMap<BreakBlock, BreakableBlock> breakables_ = _anEl.getBreakables();
        IdMap<Block, AssignedVariables> id_ = _an.getAssignedVariables().getFinalVariables();
        AssignedBooleanVariables assTar_ = (AssignedBooleanVariables) id_.getVal(this);
        ObjectMap<ClassField,BooleanAssignment> fieldsCond_ = assTar_.getFieldsRootAfter();
        CustList<StringMap<BooleanAssignment>> varsCond_ = assTar_.getVariablesRootAfter();
        AssignedVariables ass_ = id_.getVal(this);
        ObjectMap<ClassField,SimpleAssignment> fields_ = ass_.getFieldsRoot();
        CustList<StringMap<SimpleAssignment>> vars_ = ass_.getVariablesRoot();
        ObjectMap<ClassField,SimpleAssignment> after_ = new ObjectMap<ClassField,SimpleAssignment>();
        CustList<StringMap<SimpleAssignment>> afterVars_ = new CustList<StringMap<SimpleAssignment>>();
        for (EntryCust<ClassField,SimpleAssignment> e: fields_.entryList()) {
            SimpleAssignment ab_ = e.getValue();
            ClassField key_ = e.getKey();
            BooleanAssignment condBa_ = fieldsCond_.getVal(key_);
            boolean assAfter_ = true;
            boolean unassAfter_ = true;
            if (_anEl.canCompleteStrictNormally(this)) {
                assAfter_ = ab_.isAssignedAfter();
                unassAfter_ = ab_.isUnassignedAfter();
            }
            if (assAfter_) {
                assAfter_ = condBa_.isAssignedAfterWhenFalse();
            }
            if (unassAfter_) {
                unassAfter_ = condBa_.isUnassignedAfterWhenFalse();
            }
            for (EntryCust<BreakBlock, BreakableBlock> b: breakables_.entryList()) {
                if (b.getValue() != this) {
                    continue;
                }
                AssignedVariables assBr_ = id_.getVal(b.getKey());
                if (!assBr_.getFieldsRootBefore().getVal(key_).isAssignedBefore()) {
                    assAfter_ = false;
                    break;
                }
            }
            for (EntryCust<BreakBlock, BreakableBlock> b: breakables_.entryList()) {
                if (b.getValue() != this) {
                    continue;
                }
                AssignedVariables assBr_ = id_.getVal(b.getKey());
                if (!assBr_.getFieldsRootBefore().getVal(key_).isUnassignedBefore()) {
                    unassAfter_ = false;
                    break;
                }
            }
            after_.put(key_, Assignment.assignClassic(assAfter_, unassAfter_));
        }
        assTar_.getFieldsRoot().putAllMap(after_);
        for (StringMap<SimpleAssignment> s: vars_) {
            StringMap<SimpleAssignment> sm_ = new StringMap<SimpleAssignment>();
            int index_ = afterVars_.size();
            for (EntryCust<String,SimpleAssignment> e: s.entryList()) {
                SimpleAssignment ab_ = e.getValue();
                String key_ = e.getKey();
                BooleanAssignment condBa_ = varsCond_.get(index_).getVal(key_);
                boolean assAfter_ = true;
                boolean unassAfter_ = true;
                if (_anEl.canCompleteStrictNormally(this)) {
                    assAfter_ = ab_.isAssignedAfter();
                    unassAfter_ = ab_.isUnassignedAfter();
                }
                if (assAfter_) {
                    assAfter_ = condBa_.isAssignedAfterWhenFalse();
                }
                if (unassAfter_) {
                    unassAfter_ = condBa_.isUnassignedAfterWhenFalse();
                }
                for (EntryCust<BreakBlock, BreakableBlock> b: breakables_.entryList()) {
                    if (b.getValue() != this) {
                        continue;
                    }
                    AssignedVariables assBr_ = id_.getVal(b.getKey());
                    CustList<StringMap<AssignmentBefore>> list_ = assBr_.getVariablesRootBefore();
                    if (!list_.get(index_).getVal(key_).isAssignedBefore()) {
                        assAfter_ = false;
                        break;
                    }
                }
                for (EntryCust<BreakBlock, BreakableBlock> b: breakables_.entryList()) {
                    if (b.getValue() != this) {
                        continue;
                    }
                    AssignedVariables assBr_ = id_.getVal(b.getKey());
                    CustList<StringMap<AssignmentBefore>> list_ = assBr_.getVariablesRootBefore();
                    if (!list_.get(index_).getVal(key_).isUnassignedBefore()) {
                        unassAfter_ = false;
                        break;
                    }
                }
                sm_.put(key_, Assignment.assignClassic(assAfter_, unassAfter_));
            }
            afterVars_.add(sm_);
        }
        assTar_.getVariablesRoot().clear();
        assTar_.getVariablesRoot().addAllElts(afterVars_);
    }
    @Override
    boolean canBeLastOfBlockGroup() {
        return false;
    }

    @Override
    public String getTagName() {
        return TAG_IF;
    }
    @Override
    public void abruptGroup(Analyzable _an, AnalyzingEl _anEl) {
        if (canBeIncrementedCurGroup()) {
            return;
        }
        OperationNode op_ = getElCondition().getRoot();
        boolean abr_ = true;
        Argument arg_ = op_.getArgument();
        if (arg_ == null) {
            abr_ = false;
        } else if (!(arg_.getObject() instanceof Boolean)) {
            abr_ = false;
        } else if (!(Boolean)arg_.getObject()) {
            abr_ = false;
        }
        if (!abr_) {
            return;
        }
        if (!_anEl.canCompleteNormally(this)) {
            _anEl.completeAbruptGroup(this);
        }
    }
    @Override
    public boolean accessibleCondition() {
        OperationNode op_ = getElCondition().getRoot();
        boolean accessible_ = false;
        Argument arg_ = op_.getArgument();
        if (arg_ == null) {
            accessible_ = true;
        } else if (!(arg_.getObject() instanceof Boolean)) {
            accessible_ = true;
        } else if ((Boolean)arg_.getObject()) {
            accessible_ = true;
        }
        return accessible_;
    }
    @Override
    public boolean accessibleForNext() {
        OperationNode op_ = getElCondition().getRoot();
        boolean accessible_ = false;
        Argument arg_ = op_.getArgument();
        if (arg_ == null) {
            accessible_ = true;
        } else if (!(arg_.getObject() instanceof Boolean)) {
            accessible_ = true;
        } else if (!(Boolean)arg_.getObject()) {
            accessible_ = true;
        }
        return accessible_;
    }
    @Override
    public void processEl(ContextEl _cont) {
        AbstractPageEl ip_ = _cont.getLastPage();
        ReadWrite rw_ = ip_.getReadWrite();
        if (!ip_.noBlock()) {
            RemovableVars bl_ = ip_.getLastStack();
            if (bl_.getBlock() == this) {
                ip_.removeLastBlock();
                processBlock(_cont);
                return;
            }
        }
        Boolean assert_ = evaluateCondition(_cont);
        if (assert_ == null) {
            return;
        }
        IfBlockStack if_ = new IfBlockStack();
        if_.setLastBlock(this);
        Block n_ = getNextSibling();
        while (true) {
            if (!(n_ instanceof ElseIfCondition) && !(n_ instanceof ElseCondition)) {
                break;
            }
            if_.setLastBlock((BracedBlock) n_);
            n_ = n_.getNextSibling();
        }
        if_.setBlock(this);
        if_.setCurentVisitedBlock(this);
        if (assert_) {
            ip_.addBlock(if_);
            if_.setEntered(true);
            rw_.setBlock(getFirstChild());
        } else {
            ip_.addBlock(if_);
            if (if_.getLastBlock() == this) {
                return;
            }
            rw_.setBlock(getNextSibling());
        }
    }

    @Override
    public void exitStack(ContextEl _context) {
        AbstractPageEl ip_ = _context.getLastPage();
        ReadWrite rw_ = ip_.getReadWrite();
        IfBlockStack if_ = (IfBlockStack) ip_.getLastStack();
        if (if_.getLastBlock() == this) {
            rw_.setBlock(this);
        } else {
            rw_.setBlock(getNextSibling());
        }
    }
}
