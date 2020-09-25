package code.expressionlanguage.exec.opers;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.common.NumParsers;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.fwd.opers.ExecOperationContent;
import code.expressionlanguage.structs.BooleanStruct;
import code.util.CustList;
import code.util.IdMap;

public final class ExecUnaryBooleanOperation extends ExecAbstractUnaryOperation {

    public ExecUnaryBooleanOperation(ExecOperationContent _opCont) {
        super(_opCont);
    }

    @Override
    public void calculate(IdMap<ExecOperationNode,ArgumentsPair> _nodes, ContextEl _conf) {
        CustList<ExecOperationNode> chidren_ = getChildrenNodes();
        Argument arg_ = getArgument(_nodes,chidren_.first());
        BooleanStruct o_ = NumParsers.convertToBoolean(arg_.getStruct());
        setRelativeOffsetPossibleLastPage(getIndexInEl(), _conf);
        Argument a_ = new Argument(o_.neg());
        setSimpleArgument(a_, _conf, _nodes);
    }

}
