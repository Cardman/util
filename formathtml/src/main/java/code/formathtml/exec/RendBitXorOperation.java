package code.formathtml.exec;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ExecutableCode;
import code.expressionlanguage.opers.BitXorOperation;
import code.expressionlanguage.structs.NumberStruct;

public final class RendBitXorOperation extends RendStdNumericOperation {

    public RendBitXorOperation(BitXorOperation _b) {
        super(_b);
    }


    @Override
    Argument calculateOper(Argument _a, String _op, Argument _b,
            ExecutableCode _cont) {
        return new Argument(NumberStruct.calculateXor(_a.getStruct(), _b.getStruct(), _cont, getResultClass()));
    }

}