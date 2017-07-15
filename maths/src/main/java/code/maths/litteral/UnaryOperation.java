package code.maths.litteral;
import code.maths.Rate;
import code.maths.litteral.exceptions.NotNumberException;
import code.util.CustList;
import code.util.NatTreeMap;
import code.util.StringList;
import code.util.StringMap;

public final class UnaryOperation extends PrimitiveBoolOperation {

    public UnaryOperation(String _el, int _index, StringMap<String> _importingPage,
            int _indexChild, MethodOperation _m, OperationsSequence _op) {
        super(_el, _index, _importingPage, _indexChild, _m, _op);
    }

    @Override
    void analyze(CustList<OperationNode> _nodes, StringMap<String> _conf) {
        CustList<OperationNode> chidren_ = getChildrenAmong(_nodes, true);
//        ClassMatching cl_ = NumericOperation.toPrimitive(chidren_.first().getResultClass(), false);
        if (chidren_.first().getResultClass() != MathType.RATE) {
            throw new NotNumberException(String.valueOf(getIndexInEl()));
        }
        setResultClass(MathType.RATE);
    }

    @Override
    void calculate(CustList<OperationNode> _nodes, StringMap<String> _conf) {
        CustList<OperationNode> chidren_ = getChildrenAmong(_nodes, false);
        int key_ = getOperations().getOperators().firstKey();
        Argument arg_ = chidren_.first().getArgument();
        Argument a_ = new Argument();
        Object o_ = arg_.getObject();
        a_.setArgClass(MathType.RATE);
        if (StringList.quickEq(getOperations().getOperators().getVal(key_).trim(), UNARY_MINUS)) {
            a_.setObject(((Rate)o_).opposNb());
            setArgument(a_);
            return;
//            if (o_ instanceof Character) {
//                a_.setObject(-((Character)o_));
//                setArgument(a_);
//                return;
//            }
//            Number b_ = (Number) o_;
//            if (b_ instanceof Integer) {
//                a_.setObject(-((Integer)b_));
//            } else if (b_ instanceof Long) {
//                a_.setObject(-((Long)b_));
//            } else if (b_ instanceof Byte) {
//                a_.setObject(-((Byte)b_));
//            } else if (b_ instanceof Short) {
//                a_.setObject(-((Short)b_));
//            } else if (b_ instanceof Double) {
//                a_.setObject(-((Double)b_));
//            } else if (b_ instanceof Float) {
//                a_.setObject(-((Float)b_));
//            }
//            setArgument(a_);
//            return;
        }
        a_.setObject(o_);
        setArgument(a_);
    }

    @Override
    void calculateChildren() {
        NatTreeMap<Integer, String> vs_ = getOperations().getValues();
        getChildren().putAllMap(vs_);
    }
}
