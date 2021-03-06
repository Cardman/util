package code.sys.impl;

import code.expressionlanguage.AbstractExiting;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.calls.util.CustomFoundExc;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.functionid.ConstructorId;
import code.expressionlanguage.stds.ApplyCoreMethodUtil;
import code.expressionlanguage.stds.ResultErrorStd;
import code.expressionlanguage.structs.ErrorStruct;
import code.expressionlanguage.structs.Struct;
import code.expressionlanguage.utilcompo.AbstractInterceptor;

public final class DefInterceptor implements AbstractInterceptor {
    @Override
    public ResultErrorStd instance(String _cl,ContextEl _cont, ConstructorId _method, StackCall _stackCall, Argument... _args) {
        try {
            return ApplyCoreMethodUtil.instanceBase(_cont, _method, _args, _stackCall);
        } catch (RuntimeException e) {
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_cont, _cl, _stackCall)));
            return new ResultErrorStd();
        }
    }

    @Override
    public ResultErrorStd invoke(String _cl,ContextEl _cont, ClassMethodId _method, Struct _struct, AbstractExiting _exit, StackCall _stackCall, Argument... _args) {
        try {
            return ApplyCoreMethodUtil.invokeBase(_cont, _method, _struct, _exit, _args, _stackCall);
        } catch (RuntimeException e) {
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_cont, _cl, _stackCall)));
            return new ResultErrorStd();
        }
    }
}
