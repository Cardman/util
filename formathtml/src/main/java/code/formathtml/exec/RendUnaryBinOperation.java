package code.formathtml.exec;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ExecutableCode;
import code.expressionlanguage.methods.ProcessMethod;
import code.expressionlanguage.opers.UnaryBinOperation;
import code.expressionlanguage.opers.util.ClassArgumentMatching;
import code.expressionlanguage.opers.util.ClassMethodId;
import code.expressionlanguage.opers.util.MethodId;
import code.expressionlanguage.structs.NumberStruct;
import code.util.CustList;

public final class RendUnaryBinOperation extends RendAbstractUnaryOperation {

    public RendUnaryBinOperation(UnaryBinOperation _u) {
        super(_u);
    }

    @Override
    public void calculate(ExecutableCode _conf) {
        CustList<RendDynOperationNode> chidren_ = getChildrenNodes();
        Argument arg_ = chidren_.first().getArgument();
        Argument a_ = getArgument(_conf, arg_);
        setSimpleArgument(a_, _conf);
    }

    Argument getArgument(ExecutableCode _conf,
            Argument _in) {
        Argument out_ = new Argument();
        setRelativeOffsetPossibleLastPage(getIndexInEl(), _conf);
        ClassArgumentMatching res_ = getResultClass();
        out_.setStruct(NumberStruct.negBinNumber((NumberStruct)_in.getStruct(), _conf, res_));
        return out_;
    }
}