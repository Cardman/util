package code.expressionlanguage.opers;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.methods.Block;
import code.expressionlanguage.opers.util.*;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.BooleanStruct;
import code.expressionlanguage.structs.Struct;
import code.util.CustList;
import code.util.*;
import code.util.StringMap;

public final class UnaryBooleanOperation extends AbstractUnaryOperation implements SymbolOperation {
    private ClassMethodId classMethodId;
    private int opOffset;
    private boolean okNum;

    public UnaryBooleanOperation(int _index,
            int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    public void analyzeUnary(ContextEl _conf) {
        okNum = true;
        LgNames stds_ = _conf.getStandards();
        String booleanPrimType_ = stds_.getAliasPrimBoolean();
        OperationNode child_ = getFirstChild();
        ClassArgumentMatching clMatch_;
        clMatch_ = child_.getResultClass();
        opOffset = getOperations().getOperators().firstKey();
        String oper_ = getOperations().getOperators().firstValue();
        ClassMethodId clId_ = getUnaryOperatorOrMethod(this,clMatch_, oper_, _conf);
        if (clId_ != null) {
            classMethodId = clId_;
            return;
        }
        setRelativeOffsetPossibleAnalyzable(getIndexInEl(), _conf);
        if (!clMatch_.isBoolType(_conf)) {
            FoundErrorInterpret un_ = new FoundErrorInterpret();
            un_.setIndexFile(_conf.getAnalyzing().getLocalizer().getCurrentLocationIndex());
            un_.setFileName(_conf.getAnalyzing().getLocalizer().getCurrentFileName());
            //operator
            un_.buildError(_conf.getAnalysisMessages().getUnexpectedType(),
                    StringList.join(clMatch_.getNames(),"&"));
            _conf.getAnalyzing().getLocalizer().addError(un_);
        }
        clMatch_.setUnwrapObject(booleanPrimType_);
        child_.cancelArgument();
        setResultClass(new ClassArgumentMatching(booleanPrimType_));
    }

    @Override
    public void quickCalculate(ContextEl _conf) {
        tryGetArg(this,_conf);
    }

    public static void tryGetArg(MethodOperation _par, ContextEl _conf) {
        CustList<OperationNode> chidren_ = _par.getChildrenNodes();
        Argument arg_ = chidren_.first().getArgument();
        Struct value_ = arg_.getStruct();
        if (!(value_ instanceof BooleanStruct)) {
            return;
        }
        BooleanStruct o_ = (BooleanStruct) value_;
        Argument a_ = new Argument();
        a_.setStruct(o_.neg());
        _par.setSimpleArgumentAna(a_, _conf);
    }

    @Override
    public void analyzeAssignmentAfter(ContextEl _conf) {
        Block block_ = _conf.getAnalyzing().getCurrentBlock();
        AssignedVariables vars_ = _conf.getAssignedVariables().getFinalVariables().getVal(block_);
        OperationNode last_ = getFirstChild();
        StringMap<Assignment> fieldsAfterLast_ = vars_.getFields().getVal(last_);
        CustList<StringMap<Assignment>> variablesAfterLast_ = vars_.getVariables().getVal(last_);
        CustList<StringMap<Assignment>> mutableAfterLast_ = vars_.getMutableLoop().getVal(last_);
        vars_.getFields().put(this, AssignmentsUtil.neg(fieldsAfterLast_));
        vars_.getVariables().put(this, AssignmentsUtil.neg(variablesAfterLast_));
        vars_.getMutableLoop().put(this, AssignmentsUtil.neg(mutableAfterLast_));
    }

    @Override
    public ClassMethodId getClassMethodId() {
        return classMethodId;
    }
    @Override
    public int getOpOffset() {
        return opOffset;
    }

    @Override
    public boolean isOkNum() {
        return okNum;
    }
}
