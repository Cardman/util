package code.maths.litteral;
import code.util.CustList;
import code.util.StringMap;

public final class UnaryBooleanOperation extends PrimitiveBoolOperation {

    public UnaryBooleanOperation(int _index,
                                 int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_index, _indexChild, _m, _op);
    }

    @Override
    void analyze(StringMap<String> _conf, ErrorStatus _error) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        if (chidren_.first().getResultClass()!= MathType.BOOLEAN) {
            _error.setIndex(getIndexInEl());
            _error.setError(true);
            return;
        }
        setResultClass(MathType.BOOLEAN);
    }
    @Override
    void calculate(StringMap<String> _conf, ErrorStatus _error) {
        CustList<OperationNode> chidren_ = getChildrenNodes();
        Argument arg_ = chidren_.first().getArgument();
        boolean b_ = arg_.isBoolVal();
        b_ = !b_;
        Argument a_ = new Argument();
        a_.setArgClass(MathType.BOOLEAN);
        a_.setObject(b_);
        setArgument(a_);
    }

    @Override
    void calculateChildren() {
        StrTypes vs_ = getOperations().getValues();
        getChildren().addAllEntries(vs_);
    }
}
