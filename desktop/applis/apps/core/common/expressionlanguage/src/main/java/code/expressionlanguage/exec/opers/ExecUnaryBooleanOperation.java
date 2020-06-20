package code.expressionlanguage.exec.opers;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.analyze.opers.UnaryBooleanOperation;
import code.expressionlanguage.inherits.ClassArgumentMatching;
import code.expressionlanguage.structs.BooleanStruct;
import code.util.CustList;
import code.util.IdMap;

public final class ExecUnaryBooleanOperation extends ExecAbstractUnaryOperation {

    public ExecUnaryBooleanOperation(UnaryBooleanOperation _u) {
        super(_u);
    }

    @Override
    public void calculate(IdMap<ExecOperationNode,ArgumentsPair> _nodes, ContextEl _conf) {
        CustList<ExecOperationNode> chidren_ = getChildrenNodes();
        Argument arg_ = getArgument(_nodes,chidren_.first());
        BooleanStruct o_ = ClassArgumentMatching.convertToBoolean(arg_.getStruct());
        setRelativeOffsetPossibleLastPage(getIndexInEl(), _conf);
        Argument a_ = new Argument();
        a_.setStruct(o_.neg());
        setSimpleArgument(a_, _conf, _nodes);
    }

}
