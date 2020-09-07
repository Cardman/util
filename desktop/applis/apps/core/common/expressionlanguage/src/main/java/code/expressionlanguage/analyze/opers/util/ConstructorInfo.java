package code.expressionlanguage.analyze.opers.util;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.blocks.NamedFunctionBlock;
import code.expressionlanguage.analyze.inherits.AnaTemplates;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.functionid.ConstructorId;
import code.expressionlanguage.functionid.Identifiable;
import code.expressionlanguage.functionid.IdentifiableUtil;
import code.util.CustList;
import code.util.Ints;
import code.util.StringList;

public final class ConstructorInfo implements Parametrable {

    private ConstructorId constraints;
    private ConstructorId formatted;

    private String className;

    private ParametersGroup parameters;

    private boolean varArgWrap;
    private InvocationMethod invocation;
    private int memberNumber=-1;
    private CustList<CustList<ImplicitInfos>> implicits = new CustList<CustList<ImplicitInfos>>();
    private StringList parametersNames = new StringList();
    private NamedFunctionBlock customCtor;
    private Ints nameParametersFilterIndexes = new Ints();
    public ConstructorId getConstraints() {
        return constraints;
    }

    public void setConstraints(ConstructorId _constraints) {
        constraints = _constraints;
    }

    @Override
    public ParametersGroup getParameters() {
        return parameters;
    }

    public void setParameters(ParametersGroup _parameters) {
        parameters = _parameters;
    }

    @Override
    public String getClassName() {
        return className;
    }

    public void setClassName(String _className) {
        className = _className;
    }

    @Override
    public String getReturnType() {
        return constraints.getName();
    }

    @Override
    public boolean isVararg() {
        return constraints.isVararg();
    }

    @Override
    public boolean sameParamsVararg(Parametrable _id) {
        return IdentifiableUtil.eqPartial(constraints,_id.getPartialId());
    }

    @Override
    public Identifiable getPartialId() {
        return constraints;
    }

    @Override
    public boolean isVarArgWrap() {
        return varArgWrap;
    }

    @Override
    public void setVarArgWrap(boolean _v) {
        varArgWrap = _v;
    }

    public void format(ContextEl _an) {
        StringList params_ = new StringList();
        for (String p: constraints.getParametersTypes()) {
            params_.add(AnaTemplates.wildCardFormatParam(className,p,_an));
        }
        formatted = new ConstructorId(className, params_, isVararg());
    }

    public StringList getFormattedParams() {
        return formatted.getParametersTypes();
    }
    @Override
    public Identifiable getGeneFormatted() {
        return getFormatted();
    }

    public ConstructorId getFormatted() {
        return formatted;
    }

    @Override
    public InvocationMethod getInvocation() {
        return invocation;
    }

    @Override
    public void setInvocation(InvocationMethod _invocation) {
        invocation = _invocation;
    }

    @Override
    public CustList<CustList<ImplicitInfos>> getImplicits() {
        return implicits;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(int memberNumber) {
        this.memberNumber = memberNumber;
    }

    public StringList getParametersNames() {
        return parametersNames;
    }

    public void setParametersNames(StringList parametersNames) {
        this.parametersNames = parametersNames;
    }

    public NamedFunctionBlock getCustomCtor() {
        return customCtor;
    }

    public void setCustomCtor(NamedFunctionBlock customCtor) {
        this.customCtor = customCtor;
    }

    public Ints getNameParametersFilterIndexes() {
        return nameParametersFilterIndexes;
    }

}
