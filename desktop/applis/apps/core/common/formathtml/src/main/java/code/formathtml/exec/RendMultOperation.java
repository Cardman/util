package code.formathtml.exec;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.opers.MultOperation;
import code.expressionlanguage.exec.opers.ExecNumericOperation;
import code.expressionlanguage.inherits.ClassArgumentMatching;
import code.expressionlanguage.structs.NumberStruct;
import code.util.StringList;


public final class RendMultOperation extends RendStdNumericOperation {

    public RendMultOperation(MultOperation _m) {
        super(_m);
    }

    @Override
    Argument calculateOper(Argument _a, String _op, Argument _b, ContextEl _cont) {
        if (StringList.quickEq(_op.trim(), MULT)) {
            return new Argument(NumberStruct.calculateMult(ClassArgumentMatching.convertToNumber(_a.getStruct()),
                    ClassArgumentMatching.convertToNumber(_b.getStruct()), _cont, getResultClass()));
        }
        if (StringList.quickEq(_op.trim(), DIV)) {
            return ExecNumericOperation.calculateDivEx(_a, _cont, _b, getResultClass());
        }
        return ExecNumericOperation.calculateModEx(_a, _cont, _b, getResultClass());
    }

}
