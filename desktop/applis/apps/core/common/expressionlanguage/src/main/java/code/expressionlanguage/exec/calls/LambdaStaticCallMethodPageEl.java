package code.expressionlanguage.exec.calls;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.inherits.StaticCallParamChecker;
import code.expressionlanguage.exec.util.ArgumentListCall;
import code.expressionlanguage.exec.util.ExecFormattedRootBlock;
import code.expressionlanguage.structs.MethodMetaInfo;

public final class LambdaStaticCallMethodPageEl extends AbstractRefectLambdaMethodPageEl {

    public LambdaStaticCallMethodPageEl(Argument _instance, ArgumentListCall _array, MethodMetaInfo _metaInfo) {
        super(_instance,_array, _metaInfo, new DefInitPreparerDir(_metaInfo));
    }

    @Override
    Argument prepare(ContextEl _context, ArgumentListCall _list, StackCall _stack) {
        MethodMetaInfo method_ = getMetaInfo();
        ExecFormattedRootBlock paramName_ = getClassName();
        return new StaticCallParamChecker(getPair(), _list).checkParams(paramName_,Argument.createVoid(), method_.getCache(), _context, _stack);
    }

}
