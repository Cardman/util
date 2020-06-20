package code.formathtml.exec;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.opers.BitOrOperation;
import code.expressionlanguage.structs.NumberStruct;

public final class RendBitOrOperation extends RendStdNumericOperation {

    public RendBitOrOperation(BitOrOperation _b) {
        super(_b);
    }

    @Override
    Argument calculateOper(Argument _a, String _op, Argument _b,
            ContextEl _cont) {
        return new Argument(NumberStruct.calculateOr(_a.getStruct(), _b.getStruct(), _cont, getResultClass()));
    }

}
