package code.expressionlanguage.exec.calls;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.opers.ExecAnnotationMethodOperation;
import code.expressionlanguage.exec.util.ArgumentListCall;
import code.expressionlanguage.structs.MethodMetaInfo;

public final class LambdaAnnotationRefectMethodPageEl extends AbstractRefectLambdaMethodPageEl {

    private final String name;
    public LambdaAnnotationRefectMethodPageEl(Argument _instance, ArgumentListCall _array, MethodMetaInfo _metaInfo) {
        super(_instance,_array, _metaInfo);
        name = _metaInfo.getRealId().getName();
    }

    @Override
    boolean initType(ContextEl _cont, StackCall _stack) {
        return false;
    }

    @Override
    boolean isAbstract(ContextEl _cont, StackCall _stack) {
        return false;
    }

    @Override
    boolean isPolymorph(ContextEl _cont, StackCall _stack) {
        return false;
    }

    @Override
    Argument prepare(ContextEl _context, ArgumentListCall _list, StackCall _stack) {
        return ExecAnnotationMethodOperation.getAnnotation(getInstance(),name,_context, _stack);
    }
}
