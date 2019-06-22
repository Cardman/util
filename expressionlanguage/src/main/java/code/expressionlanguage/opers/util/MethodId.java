package code.expressionlanguage.opers.util;
import code.expressionlanguage.Analyzable;
import code.expressionlanguage.inherits.Templates;
import code.util.CustList;
import code.util.StringList;
import code.util.ints.Equallable;

public final class MethodId implements Equallable<MethodId>, Identifiable {

    private static final String EMPTY = "";
    private static final String VARARG = "...";
    private static final String SEP_TYPE = ",";
    private static final String LEFT = "(";
    private static final String RIGHT = ")";

    private final boolean staticMethod;

    private final String name;

    private final StringList classNames;

    private final boolean vararg;

    public MethodId(MethodModifier _staticMethod,String _name, StringList _classNames) {
        this(_staticMethod == MethodModifier.STATIC, _name, _classNames, false);
    }

    public MethodId(boolean _staticMethod,String _name, StringList _classNames) {
        this(_staticMethod, _name, _classNames, false);
    }

    public MethodId(boolean _staticMethod,String _name, StringList _classNames, boolean _vararg) {
        staticMethod = _staticMethod;
        vararg = _vararg;
        name = _name;
        classNames = new StringList();
        for (String s: _classNames) {
            classNames.add(s);
        }
    }
    @Override
    public String getSignature(Analyzable _ana) {
        String pref_ = EMPTY;
        if (staticMethod) {
            pref_ = StringList.concat(_ana.getKeyWords().getKeyWordStatic()," ");
        }
        String suf_ = EMPTY;
        if (vararg) {
            suf_ = VARARG;
        }
        return StringList.concat(pref_,name,LEFT, StringList.join(classNames, SEP_TYPE),suf_,RIGHT);
    }

    @Override
    public boolean eq(MethodId _obj) {
        if (!StringList.quickEq(_obj.name, name)) {
            return false;
        }
        return eqPartial(_obj);
    }

    public boolean eqPartial(MethodId _other) {
        int len_ = classNames.size();
        if (len_ != _other.classNames.size()) {
            return false;
        }
        if (staticMethod != _other.staticMethod) {
            return false;
        }
        if (vararg != _other.vararg) {
            return false;
        }
        for (int i = 0; i < len_; i++) {
            String param_ = classNames.get(i);
            String paramOther_ = _other.classNames.get(i);
            if (!StringList.eq(param_,paramOther_)) {
                return false;
            }
        }
        return true;
    }

    public MethodId reflectFormat(String _genericClass, Analyzable _context) {
        String name_ = getName();
        StringList types_ = getParametersTypes();
        int len_ = types_.size();
        StringList pTypes_ = new StringList();
        for (int i = CustList.FIRST_INDEX; i < len_; i++) {
            String n_ = types_.get(i);
            String formatted_ = Templates.reflectFormat(_genericClass, n_, _context);
            pTypes_.add(formatted_);
        }
        return new MethodId(isStaticMethod(), name_, pTypes_, isVararg());
    }
    
    public MethodId quickFormat(String _genericClass, Analyzable _context) {
        String name_ = getName();
        StringList types_ = getParametersTypes();
        int len_ = types_.size();
        StringList pTypes_ = new StringList();
        for (int i = CustList.FIRST_INDEX; i < len_; i++) {
            String n_ = types_.get(i);
            String formatted_ = Templates.quickFormat(_genericClass, n_, _context);
            pTypes_.add(formatted_);
        }
        return new MethodId(isStaticMethod(), name_, pTypes_, isVararg());
    }


    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean isStaticMethod() {
        return staticMethod;
    }

    @Override
    public StringList getParametersTypes() {
        return new StringList(classNames);
    }

    @Override
    public boolean isVararg() {
        return vararg;
    }

}
