package code.formathtml.exec;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.blocks.ExecNamedFunctionBlock;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.exec.opers.ExecInvokingOperation;
import code.expressionlanguage.exec.util.ExecOverrideInfo;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.analyze.opers.FctOperation;
import code.expressionlanguage.functionid.MethodAccessKind;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.fwd.blocks.ForwardInfos;
import code.expressionlanguage.structs.Struct;
import code.expressionlanguage.exec.types.ExecClassArgumentMatching;
import code.formathtml.Configuration;
import code.formathtml.util.AdvancedExiting;
import code.util.CustList;
import code.util.IdMap;
import code.util.StringList;

public final class RendFctOperation extends RendInvokingOperation implements RendCalculableOperation,RendCallable {

    private String methodName;

    private String className;

    private boolean staticChoiceMethod;

    private String lastType;

    private int naturalVararg;

    private int anc;
    private ExecNamedFunctionBlock named;
    private ExecRootBlock rootBlock;
    public RendFctOperation(FctOperation _fct, ExecNamedFunctionBlock _named, ExecRootBlock _rootBlock) {
        super(_fct);
        methodName = _fct.getCallFctContent().getMethodName();
        className = ForwardInfos.getType(_fct.getClassMethodId());
        staticChoiceMethod = _fct.isStaticChoiceMethod();
        lastType = _fct.getCallFctContent().getLastType();
        naturalVararg = _fct.getCallFctContent().getNaturalVararg();
        anc = _fct.getAnc();
        named = _named;
        rootBlock = _rootBlock;
    }

    public RendFctOperation(ExecClassArgumentMatching _res,
                            ClassMethodId _classMethodId,
                            int _child, int _order, ExecNamedFunctionBlock _named, ExecRootBlock _rootBlock) {
        super(_child,_res,_order,true);
        className = _classMethodId.getClassName();
        methodName = _classMethodId.getConstraints().getName();
        naturalVararg = -1;
        lastType = "";
        named = _named;
        rootBlock = _rootBlock;
    }
    @Override
    public void calculate(IdMap<RendDynOperationNode, ArgumentsPair> _nodes, Configuration _conf) {
        Argument previous_ = getPreviousArg(this,_nodes,_conf);
        Argument argres_ = processCall(this, this, previous_,_nodes, _conf, null);
        setSimpleArgument(argres_,_conf,_nodes);
    }

    public Argument getArgument(Argument _previous, IdMap<RendDynOperationNode, ArgumentsPair> _all, Configuration _conf, Argument _right) {
        CustList<RendDynOperationNode> chidren_ = getChildrenNodes();
        int off_ = StringList.getFirstPrintableCharIndex(getMethodName());
        setRelativeOffsetPossibleLastPage(getIndexInEl()+off_, _conf);
        CustList<Argument> firstArgs_;
        String lastType_ = getLastType();
        int naturalVararg_ = getNaturalVararg();
        String classNameFound_;
        classNameFound_ =className;
        ContextEl ctx_ = _conf.getContext();
        Argument prev_ = new Argument(ExecTemplates.getParent(getAnc(), classNameFound_, _previous.getStruct(), ctx_));
        if (ctx_.hasException()) {
            return new Argument();
        }
        String base_ = StringExpUtil.getIdFromAllTypes(classNameFound_);
        CustList<Argument> first_ = RendInvokingOperation.listNamedArguments(_all, chidren_).getArguments();
        Struct pr_ = prev_.getStruct();
        String cl_ = pr_.getClassName(ctx_);
        String clGen_ = ExecTemplates.getSuperGeneric(cl_, base_, ctx_);
        lastType_ = ExecTemplates.quickFormat(rootBlock, clGen_, lastType_);
        firstArgs_ = RendInvokingOperation.listArguments(chidren_, naturalVararg_, lastType_, first_);
        ExecOverrideInfo polymorph_ =  ExecInvokingOperation.polymorphOrSuper(isStaticChoiceMethod(),ctx_,pr_,classNameFound_,rootBlock,named);
        ExecNamedFunctionBlock fct_ = polymorph_.getOverridableBlock();
        ExecRootBlock type_ = polymorph_.getRootBlock();
        classNameFound_ = polymorph_.getClassName();
        return ExecInvokingOperation.callPrepare(new AdvancedExiting(_conf), ctx_, classNameFound_,type_, prev_, firstArgs_, null,fct_, MethodAccessKind.INSTANCE,"");
    }

    public int getNaturalVararg() {
        return naturalVararg;
    }

    public int getAnc() {
        return anc;
    }

    public String getLastType() {
        return lastType;
    }

    public String getMethodName() {
        return methodName;
    }

    public boolean isStaticChoiceMethod() {
        return staticChoiceMethod;
    }

}
