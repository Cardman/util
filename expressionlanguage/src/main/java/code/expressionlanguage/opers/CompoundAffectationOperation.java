package code.expressionlanguage.opers;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.errors.custom.BadImplicitCast;
import code.expressionlanguage.errors.custom.UnexpectedOperationAffect;
import code.expressionlanguage.inherits.Mapping;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.inherits.Templates;
import code.expressionlanguage.instr.ElUtil;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.methods.Block;
import code.expressionlanguage.opers.util.*;
import code.expressionlanguage.stds.LgNames;
import code.util.*;

public final class CompoundAffectationOperation extends MethodOperation {

    private SettableElResult settable;
    private String oper;
    private ClassMethodId classMethodId;

    public CompoundAffectationOperation(int _index, int _indexChild,
            MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
        oper = _op.getOperators().firstValue();
    }

    @Override
    void calculateChildren() {
        IntTreeMap< String> vs_ = getOperations().getValues();
        getChildren().putAllMap(vs_);
    }

    @Override
    public void analyze(Analyzable _conf) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        OperationNode root_ = chidren_.first();
        OperationNode right_ = chidren_.last();
        SettableElResult elt_ = AffectationOperation.tryGetSettable(this);
        boolean ok_ = elt_ != null;
        LgNames stds_ = _conf.getStandards();
        if (!ok_) {
            root_.setRelativeOffsetPossibleAnalyzable(root_.getIndexInEl(), _conf);
            UnexpectedOperationAffect un_ = new UnexpectedOperationAffect();
            un_.setFileName(_conf.getCurrentFileName());
            un_.setIndexFile(_conf.getCurrentLocationIndex());
            _conf.getClasses().addError(un_);
            setResultClass(new ClassArgumentMatching(stds_.getAliasObject()));
            return;
        }
        settable = elt_;
        if (settable instanceof SettableAbstractFieldOperation) {
            SettableAbstractFieldOperation cst_ = (SettableAbstractFieldOperation)settable;
            StringMap<Assignment> fieldsAfterLast_ = _conf.getAnalyzing().getDeclaredAssignments();
            if (ElUtil.checkFinalFieldReadOnly(_conf, cst_, fieldsAfterLast_)) {
                cst_.setRelativeOffsetPossibleAnalyzable(cst_.getIndexInEl(), _conf);
                UnexpectedOperationAffect un_ = new UnexpectedOperationAffect();
                un_.setFileName(_conf.getCurrentFileName());
                un_.setIndexFile(_conf.getCurrentLocationIndex());
                _conf.getClasses().addError(un_);
            }
        }
        IntTreeMap< String> ops_ = getOperations().getOperators();
        ClassArgumentMatching c_ = chidren_.last().getResultClass();
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+ops_.firstKey(), _conf);
        String op_ = ops_.firstValue();
        op_ = op_.substring(0, op_.length() - 1);
        ClassMethodIdReturn cust_ = getOperator(_conf, op_, elt_.getResultClass(), c_);
        if (cust_.isFoundMethod()) {
            ClassArgumentMatching out_ = new ClassArgumentMatching(cust_.getReturnType());
            setResultClass(out_);
            String foundClass_ = cust_.getRealClass();
            foundClass_ = Templates.getIdFromAllTypes(foundClass_);
            MethodId id_ = cust_.getRealId();
            classMethodId = new ClassMethodId(foundClass_, id_);
            MethodId realId_ = cust_.getRealId();
            CustList<ClassArgumentMatching> firstArgs_ = new CustList<ClassArgumentMatching>();
            for (OperationNode o: chidren_) {
                firstArgs_.add(o.getResultClass());
            }
            CustList<OperationNode> chUnwrap_ = new CustList<OperationNode>();
            InvokingOperation.unwrapArgsFct(chUnwrap_, realId_, -1, EMPTY_STRING, firstArgs_, _conf);
            Mapping map_ = new Mapping();
            map_.setArg(out_);
            map_.setParam(elt_.getResultClass());
            if (!Templates.isCorrectOrNumbers(map_, _conf)) {
                BadImplicitCast cast_ = new BadImplicitCast();
                cast_.setMapping(map_);
                cast_.setFileName(_conf.getCurrentFileName());
                cast_.setIndexFile(_conf.getCurrentLocationIndex());
                _conf.getClasses().addError(cast_);
            }
            return;
        }
        setResultClass(new ClassArgumentMatching(elt_.getResultClass()));
        elt_.setVariable(false);
        String stringType_ = stds_.getAliasString();
        boolean isString_ = elt_.getResultClass().matchClass(stringType_);
        if (isString_) {
            settable.setCatenizeStrings();
        }
        ClassArgumentMatching clMatchRight_ = right_.getResultClass();
        ClassArgumentMatching clMatchLeft_ = elt_.getResultClass();
        root_.setRelativeOffsetPossibleAnalyzable(root_.getIndexInEl(), _conf);

        Mapping mapping_ = new Mapping();
        mapping_.setArg(clMatchRight_);
        mapping_.setParam(clMatchLeft_);
        if (StringList.quickEq(oper, Block.PLUS_EQ)) {
            if (!PrimitiveTypeUtil.isPureNumberClass(clMatchLeft_, _conf)) {
                if (!isString_) {
                    BadImplicitCast cast_ = new BadImplicitCast();
                    cast_.setMapping(mapping_);
                    cast_.setFileName(_conf.getCurrentFileName());
                    cast_.setIndexFile(_conf.getCurrentLocationIndex());
                    _conf.getClasses().addError(cast_);
                    return;
                }
                clMatchRight_.setConvertToString(true);
            } else if (!PrimitiveTypeUtil.isPureNumberClass(clMatchRight_, _conf)) {
                BadImplicitCast cast_ = new BadImplicitCast();
                cast_.setMapping(mapping_);
                cast_.setFileName(_conf.getCurrentFileName());
                cast_.setIndexFile(_conf.getCurrentLocationIndex());
                _conf.getClasses().addError(cast_);
                return;
            }
        } else if (StringList.quickEq(oper, Block.AND_EQ) || StringList.quickEq(oper, Block.OR_EQ) || StringList.quickEq(oper, Block.XOR_EQ)) {
            boolean okRes_ = false;
            if (clMatchLeft_.isBoolType(_conf) && clMatchRight_.isBoolType(_conf)) {
                okRes_ = true;
            } else {
                int oa_ = PrimitiveTypeUtil.getIntOrderClass(clMatchLeft_, _conf);
                int ob_ = PrimitiveTypeUtil.getIntOrderClass(clMatchRight_, _conf);
                if (oa_ > 0 && ob_ > 0) {
                    okRes_ = true;
                }
            }
            if (!okRes_) {
                BadImplicitCast cast_ = new BadImplicitCast();
                cast_.setMapping(mapping_);
                cast_.setFileName(_conf.getCurrentFileName());
                cast_.setIndexFile(_conf.getCurrentLocationIndex());
                _conf.getClasses().addError(cast_);
                return;
            }
        } else if (!PrimitiveTypeUtil.isPureNumberClass(clMatchLeft_, _conf)) {
            BadImplicitCast cast_ = new BadImplicitCast();
            cast_.setMapping(mapping_);
            cast_.setFileName(_conf.getCurrentFileName());
            cast_.setIndexFile(_conf.getCurrentLocationIndex());
            _conf.getClasses().addError(cast_);
            return;
        } else if (!PrimitiveTypeUtil.isPureNumberClass(clMatchRight_, _conf)) {
            BadImplicitCast cast_ = new BadImplicitCast();
            cast_.setMapping(mapping_);
            cast_.setFileName(_conf.getCurrentFileName());
            cast_.setIndexFile(_conf.getCurrentLocationIndex());
            _conf.getClasses().addError(cast_);
            return;
        }
        ClassArgumentMatching unwrapped_ = PrimitiveTypeUtil.toPrimitive(clMatchLeft_, _conf);
        if (!isString_) {
            elt_.getResultClass().setUnwrapObject(unwrapped_);
            right_.getResultClass().setUnwrapObject(unwrapped_);
            ((OperationNode) elt_).cancelArgument();
            right_.cancelArgument();
        }
    }

    @Override
    public void analyzeAssignmentAfter(Analyzable _conf) {
        Block block_ = _conf.getCurrentBlock();
        AssignedVariables vars_ = _conf.getContextEl().getAssignedVariables().getFinalVariables().getVal(block_);
        OperationNode firstChild_ = (OperationNode) settable;
        OperationNode lastChild_ = getChildrenNodes().last();
        StringMap<Assignment> fieldsAfter_ = new StringMap<Assignment>();
        CustList<StringMap<Assignment>> variablesAfter_ = new CustList<StringMap<Assignment>>();
        CustList<StringMap<Assignment>> mutableAfter_ = new CustList<StringMap<Assignment>>();
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
                        if (!e.getValue().isUnassignedAfter()) {
                            if (_conf.getContextEl().isFinalLocalVar(str_,index_)) {
                                //error
                                firstChild_.setRelativeOffsetPossibleAnalyzable(firstChild_.getIndexInEl(), _conf);
                                UnexpectedOperationAffect un_ = new UnexpectedOperationAffect();
                                un_.setFileName(_conf.getCurrentFileName());
                                un_.setIndexFile(_conf.getCurrentLocationIndex());
                                _conf.getClasses().addError(un_);
                            }
                        }
                    }
                    sm_.put(e.getKey(), Assignment.assign(str_, e.getKey(),isBool_, e.getValue()));
                }
                variablesAfter_.add(sm_);
            }
            
        } else {
            CustList<StringMap<Assignment>> variablesAfterLast_ = vars_.getVariables().getVal(lastChild_);
            variablesAfter_.addAllElts(AssignmentsUtil.assignGene(isBool_,variablesAfterLast_));
        }
        vars_.getVariables().put(this, variablesAfter_);
        if (firstChild_ instanceof MutableLoopVariableOperation) {
            CustList<StringMap<Assignment>> variablesAfterLast_ = vars_.getMutableLoop().getVal(lastChild_);
            String str_ = ((MutableLoopVariableOperation)firstChild_).getVariableName();
            for (StringMap<Assignment> s: variablesAfterLast_) {
                StringMap<Assignment> sm_ = new StringMap<Assignment>();
                int index_ = mutableAfter_.size();
                for (EntryCust<String, Assignment> e: s.entryList()) {
                    if (StringList.quickEq(str_, e.getKey())) {
                        if (!e.getValue().isUnassignedAfter()) {
                            if (_conf.getContextEl().isFinalMutableLoopVar(str_,index_)) {
                                //error
                                firstChild_.setRelativeOffsetPossibleAnalyzable(firstChild_.getIndexInEl(), _conf);
                                UnexpectedOperationAffect un_ = new UnexpectedOperationAffect();
                                un_.setFileName(_conf.getCurrentFileName());
                                un_.setIndexFile(_conf.getCurrentLocationIndex());
                                _conf.getClasses().addError(un_);
                            }
                        }
                    }
                    sm_.put(e.getKey(), Assignment.assign(str_, e.getKey(),isBool_, e.getValue()));
                }
                mutableAfter_.add(sm_);
            }
            
        } else {
            CustList<StringMap<Assignment>> variablesAfterLast_ = vars_.getMutableLoop().getVal(lastChild_);
            mutableAfter_.addAllElts(AssignmentsUtil.assignGene(isBool_,variablesAfterLast_));
        }
        vars_.getMutableLoop().put(this, mutableAfter_);
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
                    un_.setIndexFile(_conf.getCurrentLocationIndex());
                    _conf.getClasses().addError(un_);
                }
            }
        }
        if (fromCurClass_) {
            SettableAbstractFieldOperation cst_ = (SettableAbstractFieldOperation)firstChild_;
            ClassField cl_ = cst_.getFieldId();
            StringMap<Assignment> fieldsAfterLast_ = vars_.getFields().getVal(lastChild_);
            for (EntryCust<String, Assignment> e: fieldsAfterLast_.entryList()) {
                fieldsAfter_.put(e.getKey(), Assignment.assign(cl_.getFieldName(), e.getKey(),isBool_, e.getValue()));
            }
        } else {
            StringMap<Assignment> fieldsAfterLast_ = vars_.getFields().getVal(lastChild_);
            fieldsAfter_.putAllMap(AssignmentsUtil.assignGene(isBool_,fieldsAfterLast_));
        }
        vars_.getFields().put(this, fieldsAfter_);
    }
    @Override
    public void analyzeAssignmentBeforeNextSibling(Analyzable _conf,
            OperationNode _nextSibling, OperationNode _previous) {
        analyzeStdAssignmentBeforeNextSibling(_conf, _nextSibling, _previous);
    }

    public String getOper() {
        return oper;
    }

    public ClassMethodId getClassMethodId() {
        return classMethodId;
    }

}
