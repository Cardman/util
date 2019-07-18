package code.formathtml.exec;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ExecutableCode;
import code.expressionlanguage.calls.PageEl;
import code.expressionlanguage.inherits.PrimitiveTypeUtil;
import code.expressionlanguage.instr.ConstType;
import code.expressionlanguage.opers.FinalVariableOperation;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.LongStruct;
import code.expressionlanguage.structs.Struct;
import code.expressionlanguage.variables.LocalVariable;
import code.expressionlanguage.variables.LoopVariable;

public final class RendFinalVariableOperation extends RendLeafOperation implements RendCalculableOperation {

    private String variableName;
    private int off;
    private ConstType type;

    public RendFinalVariableOperation(FinalVariableOperation _f) {
        super(_f);
        off = _f.getOff();
        type = _f.getType();
        variableName = _f.getVariableName();
    }

    @Override
    public void calculate(ExecutableCode _conf) {
        Argument arg_ = getCommonArgument(_conf);
        setSimpleArgument(arg_, _conf);
    }

    Argument getCommonArgument(ExecutableCode _conf) {
        Argument a_;
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off, _conf);
        PageEl ip_ = _conf.getOperationPageEl();
        if (type == ConstType.CATCH_VAR) {
            LocalVariable locVar_ = ip_.getCatchVars().getVal(variableName);
            a_ = new Argument();
            a_.setStruct(locVar_.getStruct());
            return a_;
        }
        if (type == ConstType.LOOP_INDEX) {
            LoopVariable locVar_ = ip_.getVars().getVal(variableName);
            a_ = new Argument();
            ClassArgumentMatching clArg_ = new ClassArgumentMatching(locVar_.getIndexClassName());
            LgNames stds_ = _conf.getStandards();
            LongStruct str_ = new LongStruct(locVar_.getIndex());
            Struct value_ = PrimitiveTypeUtil.convertObject(clArg_, str_, stds_);
            a_.setStruct(value_);
            return a_;
        }
        LoopVariable locVar_ = ip_.getVars().getVal(variableName);
        a_ = new Argument();
        a_.setStruct(locVar_.getStruct());
        return a_;
    }

}