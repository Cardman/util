package code.expressionlanguage.functionid;

import code.util.CustList;
import code.util.core.StringUtil;

public abstract class AbsractIdentifiableCommon implements Identifiable {

    private final String name;

    private final CustList<Boolean> refParams = new CustList<Boolean>();
    private final CustList<String> classNames = new CustList<String>();

    private final boolean vararg;

    protected AbsractIdentifiableCommon(String _name, boolean _vararg) {
        this.name = _name;
        this.vararg = _vararg;
    }

    protected void feedParamTypes(CustList<String> _classNames) {
        for (String s: _classNames) {
            classNames.add(StringUtil.nullToEmpty(s));
            refParams.add(false);
        }
    }

    protected void feedParamTypes(CustList<String> _classNames, CustList<Boolean> _refParams) {
        int min_ = Math.min(_classNames.size(),_refParams.size());
        for (String s: _classNames.left(min_)) {
            classNames.add(StringUtil.nullToEmpty(s));
        }
        for (boolean s: _refParams.left(min_)) {
            refParams.add(s);
        }
    }
    protected CustList<Boolean> getRefParams() {
        return refParams;
    }

    protected CustList<String> getClassNames() {
        return classNames;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isVararg() {
        return vararg;
    }

    @Override
    public int getParametersTypesLength() {
        return classNames.size();
    }

    @Override
    public String getParametersType(int _index) {
        return classNames.get(_index);
    }

    @Override
    public boolean getParametersRef(int _index) {
        return refParams.get(_index);
    }
}
