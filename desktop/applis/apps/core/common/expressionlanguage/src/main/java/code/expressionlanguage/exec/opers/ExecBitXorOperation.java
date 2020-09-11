package code.expressionlanguage.exec.opers;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.opers.BitXorOperation;
import code.expressionlanguage.stds.AliasNumber;

public final class ExecBitXorOperation extends ExecStdNumericOperation {

    public ExecBitXorOperation(BitXorOperation _b) {
        super(_b);
    }


    @Override
    Argument calculateOper(Argument _a, String _op, Argument _b,
                           ContextEl _cont) {
        return new Argument(AliasNumber.calculateXor(_a.getStruct(), _b.getStruct(), getResultClass(), _cont.getStandards()));
    }

}
