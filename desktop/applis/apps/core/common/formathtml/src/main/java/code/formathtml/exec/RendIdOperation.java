package code.formathtml.exec;
import code.expressionlanguage.Argument;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.analyze.opers.IdOperation;
import code.formathtml.Configuration;
import code.util.CustList;
import code.util.IdMap;

public final class RendIdOperation extends RendAbstractUnaryOperation {

    public RendIdOperation(IdOperation _i) {
        super(_i);
    }

    @Override
    public void calculate(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, Configuration _conf) {
        CustList<RendDynOperationNode> chidren_ = getChildrenNodes();
        RendDynOperationNode o_ = chidren_.first();
        Argument a_ = getArgument(_nodes,o_);
        boolean simple_ = false;
        if (o_ instanceof RendSettableElResult) {
            RendSettableElResult s_ = (RendSettableElResult) o_;
            if (s_.resultCanBeSet()) {
                simple_ = true;
            }
        }
        if (simple_) {
            setQuickNoConvertSimpleArgument(a_, _conf,_nodes);
        } else {
            setSimpleArgument(a_, _conf,_nodes);
        }
    }
}
