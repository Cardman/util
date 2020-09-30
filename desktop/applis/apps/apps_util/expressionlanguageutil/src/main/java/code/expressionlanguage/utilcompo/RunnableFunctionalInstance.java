package code.expressionlanguage.utilcompo;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.ClassFieldStruct;
import code.expressionlanguage.exec.CommonExecutionInfos;
import code.expressionlanguage.exec.InitPhase;
import code.expressionlanguage.exec.calls.util.CustomReflectMethod;
import code.expressionlanguage.exec.opers.ExecInvokingOperation;
import code.expressionlanguage.common.ClassField;

import code.expressionlanguage.structs.*;
import code.util.CustList;

public final class RunnableFunctionalInstance extends WithoutParentIdStruct implements AbstractFunctionalInstance, Runnable,
        FieldableStruct {

    private final String className;

    private final LambdaStruct functional;

    private final CustList<ClassFieldStruct> fields;
    private final CommonExecutionInfos executionInfos;

    public RunnableFunctionalInstance(String _className, LambdaStruct _functional,
                                      CustList<ClassFieldStruct> _fields, ContextEl _contextEl) {
        className = _className;
        functional = _functional;
        executionInfos = _contextEl.getExecutionInfos();
        fields = _fields;
    }

    @Override
    public String getClassName(ContextEl _contextEl) {
        return className;
    }

    @Override
    public LambdaStruct getFunctional() {
        return functional;
    }

    @Override
    public void run() {
        callMethod(new RunnableContextEl(InitPhase.NOTHING, executionInfos), functional, new CustList<Argument>());
    }

    public static void callMethod(RunnableContextEl _localThread, Struct _functional, CustList<Argument> _arguments) {
        RunnableStruct.setupThread(_localThread);
        ExecInvokingOperation.prepareCallDyn(new Argument(_functional), _arguments, _localThread);
        if (_localThread.getCallingState() instanceof CustomReflectMethod) {
            CustomReflectMethod ref_ = (CustomReflectMethod) _localThread.getCallingState();
            RunnableStruct.reflect(ref_.getGl(), ref_.getArguments(),_localThread, ref_.getReflect(), ref_.isLambda());
        } else {
            _localThread.getCustInit().prExc(_localThread);
        }
    }

    @Override
    public ClassFieldStruct getEntryStruct(ClassField _classField) {
        return ClassFieldStruct.getPair(fields,_classField);
    }

    @Override
    public CustList<ClassFieldStruct> getFields() {
        return fields;
    }
}
