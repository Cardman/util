package code.expressionlanguage.exec.opers;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.DefaultExiting;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.ExecutingUtil;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.analyze.opers.SuperFctOperation;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.functionid.MethodId;
import code.util.CustList;
import code.util.IdMap;
import code.util.StringList;

public final class ExecSuperFctOperation extends ExecInvokingOperation {

    private String methodName;

    private ClassMethodId classMethodId;

    private boolean staticMethod;

    private String lastType;

    private int naturalVararg = -1;
    private int anc;
    private int delta;
    public ExecSuperFctOperation(SuperFctOperation _s) {
        super(_s);
        methodName = _s.getMethodName();
        classMethodId = _s.getClassMethodId();
        staticMethod = _s.isStaticMethod();
        lastType = _s.getLastType();
        naturalVararg = _s.getNaturalVararg();
        anc = _s.getAnc();
        delta = _s.getDelta();
    }

    @Override
    public void calculate(IdMap<ExecOperationNode, ArgumentsPair> _nodes,
                          ContextEl _conf) {
        CustList<Argument> arguments_ = getArguments(_nodes, this);
        Argument previous_ = getPreviousArg(this, _nodes, _conf);
        Argument res_ = getArgument(previous_, arguments_, _conf);
        setSimpleArgument(res_, _conf, _nodes);
    }
    Argument getArgument(Argument _previous, CustList<Argument> _arguments, ContextEl _conf) {
        CustList<ExecOperationNode> chidren_ = getChildrenNodes();
        int off_ = StringList.getFirstPrintableCharIndex(methodName);
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off_, _conf);
        CustList<Argument> firstArgs_;
        MethodId methodId_ = classMethodId.getConstraints();
        String lastType_ = lastType;
        int naturalVararg_ = naturalVararg;
        String classNameFound_;
        Argument prev_ = new Argument();
        if (!staticMethod) {
            prev_.setStruct(_previous.getStruct());
            classNameFound_ = classMethodId.getClassName();
            prev_.setStruct(ExecTemplates.getParent(anc, classNameFound_, prev_.getStruct(), _conf));
            if (_conf.callsOrException()) {
                return new Argument();
            }
            String argClassName_ = prev_.getObjectClassName(_conf);
            String base_ = StringExpUtil.getIdFromAllTypes(classNameFound_);
            String fullClassNameFound_ = ExecTemplates.getSuperGeneric(argClassName_, base_, _conf);
            lastType_ = ExecTemplates.quickFormat(fullClassNameFound_, lastType_, _conf);
            firstArgs_ = listArguments(chidren_, naturalVararg_, lastType_, _arguments);
            methodId_ = classMethodId.getConstraints();
        } else {
            classNameFound_ = classMethodId.getClassName();
            classNameFound_ = classMethodId.formatType(classNameFound_,_conf);
            lastType_ = classMethodId.formatType(classNameFound_,lastType_,_conf);
            firstArgs_ = listArguments(chidren_, naturalVararg_, lastType_, _arguments);
            if (ExecutingUtil.hasToExit(_conf,classNameFound_)) {
                return Argument.createVoid();
            }
        }
        return callPrepare(new DefaultExiting(_conf),_conf, classNameFound_, methodId_, prev_, firstArgs_, null);
    }

    public ClassMethodId getClassMethodId() {
        return classMethodId;
    }

    public int getNaturalVararg() {
        return naturalVararg;
    }

}
