package code.expressionlanguage.analyze.reach.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.opers.NumericOperation;
import code.expressionlanguage.common.NumParsers;

public final class ReachBitOrOperation extends ReachNumericOperation {
    ReachBitOrOperation(NumericOperation _info) {
        super(_info);
    }

    @Override
    Argument calculateOperAna(Argument _a, String _op, Argument _b, AnalyzedPageEl _page) {
        return new Argument(NumParsers.calculateOr(_a.getStruct(), _b.getStruct(), getResultClass().getUnwrapObjectNb()));
    }
}
