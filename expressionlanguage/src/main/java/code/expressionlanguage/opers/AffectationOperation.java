package code.expressionlanguage.opers;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.ElUtil;
import code.expressionlanguage.ExecutableCode;
import code.expressionlanguage.Mapping;
import code.expressionlanguage.OperationsSequence;
import code.expressionlanguage.PrimitiveTypeUtil;
import code.expressionlanguage.Templates;
import code.expressionlanguage.methods.Block;
import code.expressionlanguage.methods.util.ArgumentsPair;
import code.expressionlanguage.methods.util.BadImplicitCast;
import code.expressionlanguage.methods.util.TypeVar;
import code.expressionlanguage.methods.util.UnexpectedOperationAffect;
import code.expressionlanguage.opers.util.AssignedVariables;
import code.expressionlanguage.opers.util.Assignment;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.ConstructorId;
import code.expressionlanguage.opers.util.FieldInfo;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.variables.LocalVariable;
import code.util.CustList;
import code.util.EntryCust;
import code.util.IdMap;
import code.util.NatTreeMap;
import code.util.StringList;
import code.util.StringMap;

public final class AffectationOperation extends MethodOperation {

    private SettableElResult settable;

    public AffectationOperation(int _index, int _indexChild,
            MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    void calculateChildren() {
        NatTreeMap<Integer, String> vs_ = getOperations().getValues();
        getChildren().putAllMap(vs_);
    }

    @Override
    public void analyze(Analyzable _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        OperationNode root_ = chidren_.first();
        OperationNode right_ = chidren_.last();
        SettableElResult elt_ = tryGetSettable(this);
        boolean ok_ = elt_ != null;
        LgNames stds_ = _conf.getStandards();
        if (!ok_) {
            root_.setRelativeOffsetPossibleAnalyzable(root_.getIndexInEl(), _conf);
            UnexpectedOperationAffect un_ = new UnexpectedOperationAffect();
            un_.setFileName(_conf.getCurrentFileName());
            un_.setRc(_conf.getCurrentLocation());
            _conf.getClasses().addError(un_);
            setResultClass(new ClassArgumentMatching(stds_.getAliasObject()));
            return;
        }
        settable = elt_;
        setResultClass(elt_.getResultClass());
        elt_.setVariable(true);
        ClassArgumentMatching clMatchRight_ = right_.getResultClass();
        ClassArgumentMatching clMatchLeft_ = elt_.getResultClass();
        root_.setRelativeOffsetPossibleAnalyzable(root_.getIndexInEl(), _conf);

        if (clMatchRight_.isVariable()) {
            if (!clMatchLeft_.isPrimitive(_conf)) {
                return;
            }
            Mapping mapping_ = new Mapping();
            mapping_.setArg(clMatchRight_.getName());
            mapping_.setParam(clMatchLeft_.getName());
            BadImplicitCast cast_ = new BadImplicitCast();
            cast_.setMapping(mapping_);
            cast_.setFileName(_conf.getCurrentFileName());
            cast_.setRc(_conf.getCurrentLocation());
            _conf.getClasses().addError(cast_);
            return;
        }
        StringMap<StringList> vars_ = new StringMap<StringList>();
        boolean buildMap_ = true;
        if (_conf.isStaticContext()) {
            buildMap_ = false;
        } else if (_conf.getGlobalClass() == null) {
            buildMap_ = false;
        }
        if (buildMap_) {
            for (TypeVar t: Templates.getConstraints(_conf.getGlobalClass(), _conf)) {
                vars_.put(t.getName(), t.getConstraints());
            }
        }
        Mapping mapping_ = new Mapping();
        mapping_.setMapping(vars_);
        mapping_.setArg(clMatchRight_.getName());
        mapping_.setParam(clMatchLeft_.getName());
        if (!Templates.isCorrect(mapping_, _conf)) {
            BadImplicitCast cast_ = new BadImplicitCast();
            cast_.setMapping(mapping_);
            cast_.setFileName(_conf.getCurrentFileName());
            cast_.setRc(_conf.getCurrentLocation());
            _conf.getClasses().addError(cast_);
        }
        if (PrimitiveTypeUtil.isPrimitive(clMatchLeft_, _conf)) {
            right_.getResultClass().setUnwrapObject(clMatchLeft_);
        }
    }

    static SettableElResult tryGetSettable(MethodOperation _operation) {
        CustList<OperationNode> chidren_ = _operation.getChildrenNodes();
        OperationNode root_ = chidren_.first();
        SettableElResult elt_ = null;
        if (!(root_ instanceof DotOperation)) {
            if (root_ instanceof SettableElResult) {
                elt_ = (SettableElResult) root_;
            }
        } else {
            OperationNode beforeLast_ = ((MethodOperation)root_).getChildrenNodes().last();
            if (beforeLast_ instanceof SettableElResult) {
                elt_ = (SettableElResult) beforeLast_;
            }
        }
        return elt_;
    }
    @Override
    public void analyzeAssignmentAfter(Analyzable _conf) {
        Block block_ = _conf.getCurrentBlock();
        AssignedVariables vars_ = _conf.getAssignedVariables().getFinalVariables().getVal(block_);
        OperationNode firstChild_ = (OperationNode) settable;
        OperationNode lastChild_ = getChildrenNodes().last();
        StringMap<Assignment> fieldsAfter_ = new StringMap<Assignment>();
        CustList<StringMap<Assignment>> variablesAfter_ = new CustList<StringMap<Assignment>>();
        boolean isBool_;
        isBool_ = getResultClass().isBoolType(_conf);
        if (firstChild_ instanceof VariableOperation) {
            CustList<StringMap<Assignment>> variablesAfterLast_ = vars_.getVariables().getVal(lastChild_);
            String str_ = ((VariableOperation)firstChild_).getVariableName();
            for (StringMap<Assignment> s: variablesAfterLast_) {
                StringMap<Assignment> sm_ = new StringMap<Assignment>();
                int index_ = variablesAfter_.size();
                for (EntryCust<String, Assignment> e: s.entryList()) {
                    if (StringList.quickEq(str_, e.getKey())) {
                        LocalVariable locVar_ = _conf.getLocalVar(str_,index_);
                        if (!e.getValue().isUnassignedAfter()) {
                            if (locVar_ != null && locVar_.isFinalVariable()) {
                                //error
                                firstChild_.setRelativeOffsetPossibleAnalyzable(firstChild_.getIndexInEl(), _conf);
                                UnexpectedOperationAffect un_ = new UnexpectedOperationAffect();
                                un_.setFileName(_conf.getCurrentFileName());
                                un_.setRc(_conf.getCurrentLocation());
                                _conf.getClasses().addError(un_);
                            }
                        }
                    }
                    boolean ass_ = StringList.quickEq(str_, e.getKey()) || e.getValue().isAssignedAfter();
                    boolean unass_ = !StringList.quickEq(str_, e.getKey()) && e.getValue().isUnassignedAfter();
                    sm_.put(e.getKey(), e.getValue().assignChange(isBool_, ass_, unass_));
                }
                variablesAfter_.add(sm_);
            }
            
        } else {
            CustList<StringMap<Assignment>> variablesAfterLast_ = vars_.getVariables().getVal(lastChild_);
            for (StringMap<Assignment> s: variablesAfterLast_) {
                StringMap<Assignment> sm_ = new StringMap<Assignment>();
                for (EntryCust<String, Assignment> e: s.entryList()) {
                    sm_.put(e.getKey(), e.getValue().assign(isBool_));
                }
                variablesAfter_.add(sm_);
            }
        }
        vars_.getVariables().put(this, variablesAfter_);
        boolean fromCurClass_ = false;
        if (firstChild_ instanceof SettableAbstractFieldOperation) {
            SettableAbstractFieldOperation cst_ = (SettableAbstractFieldOperation)firstChild_;
            fromCurClass_ = cst_.isFromCurrentClass(_conf);
            StringMap<Assignment> fieldsAfterLast_ = vars_.getFields().getVal(lastChild_);
            if (ElUtil.checkFinalField(_conf, cst_, fieldsAfterLast_)) {
                ClassField cl_ = cst_.getFieldId();
                FieldInfo meta_ = _conf.getFieldInfo(cl_);
                if (meta_.isFinalField()) {
                    //error if final field
                    cst_.setRelativeOffsetPossibleAnalyzable(cst_.getIndexInEl(), _conf);
                    UnexpectedOperationAffect un_ = new UnexpectedOperationAffect();
                    un_.setFileName(_conf.getCurrentFileName());
                    un_.setRc(_conf.getCurrentLocation());
                    _conf.getClasses().addError(un_);
                }
            }
        }
        if (fromCurClass_) {
            SettableAbstractFieldOperation cst_ = (SettableAbstractFieldOperation)firstChild_;
            ClassField cl_ = cst_.getFieldId();
            StringMap<Assignment> fieldsAfterLast_ = vars_.getFields().getVal(lastChild_);
            for (EntryCust<String, Assignment> e: fieldsAfterLast_.entryList()) {
                boolean ass_ = StringList.quickEq(cl_.getFieldName(), e.getKey()) || e.getValue().isAssignedAfter();
                boolean unass_ = !StringList.quickEq(cl_.getFieldName(),e.getKey()) && e.getValue().isUnassignedAfter();
                fieldsAfter_.put(e.getKey(), e.getValue().assignChange(isBool_, ass_, unass_));
            }
        } else {
            StringMap<Assignment> fieldsAfterLast_ = vars_.getFields().getVal(lastChild_);
            for (EntryCust<String, Assignment> e: fieldsAfterLast_.entryList()) {
                fieldsAfter_.put(e.getKey(), e.getValue().assign(isBool_));
            }
        }
        vars_.getFields().put(this, fieldsAfter_);
    }
    @Override
    public void analyzeAssignmentBeforeNextSibling(Analyzable _conf,
            OperationNode _nextSibling, OperationNode _previous) {
        analyzeStdAssignmentBeforeNextSibling(_conf, _nextSibling, _previous);
    }
    public SettableElResult getSettable() {
        return settable;
    }
    @Override
    public void calculate(ExecutableCode _conf) {
        OperationNode right_ = getChildrenNodes().last();
        Argument rightArg_ = right_.getArgument();
        settable.calculateSetting(_conf, rightArg_);
        OperationNode op_ = (OperationNode)settable;
        setSimpleArgument(op_.getArgument(), _conf);
    }

    @Override
    public Argument calculate(IdMap<OperationNode, ArgumentsPair> _nodes,
            ContextEl _conf) {
        OperationNode right_ = getChildrenNodes().last();
        Argument rightArg_ = _nodes.getVal(right_).getArgument();
        Argument arg_ = settable.calculateSetting(_nodes, _conf, rightArg_);
        if (_conf.calls()) {
            return arg_;
        }
        setSimpleArgument(arg_, _conf, _nodes);
        return arg_;
    }

    @Override
    public ConstructorId getConstId() {
        return null;
    }

}
