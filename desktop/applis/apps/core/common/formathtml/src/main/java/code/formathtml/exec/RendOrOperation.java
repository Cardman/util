package code.formathtml.exec;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.opers.OrOperation;
import code.expressionlanguage.structs.BooleanStruct;
import code.expressionlanguage.structs.Struct;

public final class RendOrOperation extends RendQuickOperation {

    public RendOrOperation(OrOperation _o, AnalyzedPageEl _page) {
        super(_o, _page);
    }

    @Override
    public boolean match(Struct _struct) {
        return BooleanStruct.isTrue(_struct);
    }
}
