package code.expressionlanguage.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.DefaultExiting;
import code.expressionlanguage.analyze.opers.*;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.exec.util.ArgumentList;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.functionid.MethodId;
import code.expressionlanguage.structs.Struct;
import code.util.CustList;
import code.util.IdMap;
import code.util.StringList;

public final class ExecStdFctOperation extends ExecInvokingOperation {

    private String methodName;

    private ClassMethodId classMethodId;

    private boolean staticMethod;

    private String lastType;

    private int naturalVararg;

    protected ExecStdFctOperation(InvokingOperation _inv, AbstractCallFctOperation _fct) {
        super(_inv);
        methodName = _fct.getMethodName();
        classMethodId = _fct.getClassMethodId();
        staticMethod = _fct.isStaticMethod();
        lastType = _fct.getLastType();
        naturalVararg = _fct.getNaturalVararg();
    }

    @Override
    public void calculate(IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf) {
        Argument previous_ = getPreviousArg(this, _nodes, _conf);
        Argument res_ = getArgument(previous_,_nodes, _conf);
        setSimpleArgument(res_, _conf, _nodes);
    }

    Argument getArgument(Argument _previous, IdMap<ExecOperationNode, ArgumentsPair> _nodes, ContextEl _conf) {
        int off_ = StringList.getFirstPrintableCharIndex(methodName);
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off_, _conf);
        MethodId methodId_ = classMethodId.getConstraints();
        String classNameFound_;
        Argument prev_;
        if (!staticMethod) {
            classNameFound_ = classMethodId.getClassName();
            Struct argPrev_ = _previous.getStruct();
            prev_ = new Argument(ExecTemplates.getParent(0, classNameFound_, argPrev_, _conf));
            if (_conf.callsOrException()) {
                return new Argument();
            }
        } else {
            prev_ = new Argument();
        }
        classNameFound_ = classMethodId.getClassName();
        CustList<Argument> firstArgs_ = getArgs(_nodes);
        return callStd(new DefaultExiting(_conf),_conf, classNameFound_, methodId_, prev_, firstArgs_, null);
    }

    private CustList<Argument> getArgs(IdMap<ExecOperationNode, ArgumentsPair> _nodes) {
        return fectchArgs(_nodes,lastType,naturalVararg);
    }

    public ClassMethodId getClassMethodId() {
        return classMethodId;
    }

    public int getNaturalVararg() {
        return naturalVararg;
    }
}
