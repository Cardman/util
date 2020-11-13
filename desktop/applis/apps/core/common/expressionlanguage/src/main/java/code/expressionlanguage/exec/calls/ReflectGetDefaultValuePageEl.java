package code.expressionlanguage.exec.calls;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.blocks.ExecAnnotableBlock;
import code.expressionlanguage.exec.blocks.ExecAnnotationMethodBlock;
import code.expressionlanguage.exec.types.ExecClassArgumentMatching;
import code.expressionlanguage.exec.ExpressionLanguage;
import code.expressionlanguage.exec.opers.ExecOperationNode;
import code.expressionlanguage.structs.MethodMetaInfo;
import code.expressionlanguage.structs.Struct;
import code.util.CustList;

public final class ReflectGetDefaultValuePageEl extends AbstractReflectPageEl {

    private boolean init;
    private CustList<ExecOperationNode> ops = new CustList<ExecOperationNode>();
    private MethodMetaInfo metaInfo;

    public ReflectGetDefaultValuePageEl(CustList<Argument> _arguments, MethodMetaInfo _metaInfo) {
        super(_arguments);
        setGlobalArgumentStruct(_metaInfo);
        metaInfo = _metaInfo;
    }

    @Override
    public boolean checkCondition(ContextEl _context) {
        ExecAnnotableBlock annotableBlock_ = metaInfo.getAnnotableBlock();
        if (!(annotableBlock_ instanceof ExecAnnotationMethodBlock)) {
            setReturnedArgument(new Argument());
            return true;
        }
        if (!init) {
            ExecAnnotationMethodBlock ann_ = (ExecAnnotationMethodBlock) annotableBlock_;
            ops = ann_.getOpValue();
            if (ops.isEmpty()) {
                String clMethod_ = ann_.getImportedReturnType();
                Struct value_ = ExecClassArgumentMatching.defaultValue(clMethod_, _context);
                Argument out_ = new Argument(value_);
                setReturnedArgument(out_);
                return true;
            }
            init = true;
        }
        ExpressionLanguage el_ = getCurrentEl(0,ops);
        Argument ret_ = ExpressionLanguage.tryToCalculate(_context,el_,0);
        if (_context.callsOrException()) {
            return false;
        }
        clearCurrentEls();
        setReturnedArgument(ret_);
        return true;
    }

    @Override
    public void receive(Argument _argument, ContextEl _context) {
        basicReceive(_argument,_context);
    }

    public MethodMetaInfo getMetaInfo() {
        return metaInfo;
    }
}
