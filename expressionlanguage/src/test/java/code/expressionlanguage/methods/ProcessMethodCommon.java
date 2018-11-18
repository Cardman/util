package code.expressionlanguage.methods;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.DefaultInitializer;
import code.expressionlanguage.DefaultLockingClass;
import code.expressionlanguage.InitializationLgNames;
import code.expressionlanguage.VariableSuffix;
import code.expressionlanguage.classes.CustLgNames;
import code.expressionlanguage.opers.util.ConstructorId;
import code.expressionlanguage.opers.util.MethodId;
import code.expressionlanguage.options.KeyWords;
import code.expressionlanguage.options.KeyWordsMap;
import code.expressionlanguage.options.Options;
import code.expressionlanguage.stds.LgNames;
import code.util.CustList;
import code.util.StringList;

public abstract class ProcessMethodCommon {

    protected static final String ARR_NUMBER = "[java.lang.Number";
    protected static final String ARR_INTEGER = "[java.lang.Integer";
    protected static final String ARR_OBJECT = "[java.lang.Object";
    protected static final String ARR_ARR_OBJECT = "[[java.lang.Object";
    protected static final String ARR_CUST = "[pkg.ExThree";
    protected static final String ARR_ARR_CUST = "[[pkg.ExThree";
    protected static final String NUMBERS = "code.expressionlanguage.classes.Ints";
    protected static final String CUST = NUMBERS;
    protected static final String INTEGER = "$int";
    protected static final String STRING = "java.lang.String";
    protected static final String BOOLEAN = "$boolean";

    protected static void initializeClass(String _class, ContextEl _cont) {
//        ProcessMethod.initializeClass(_class, _cont);
    }
    protected static Argument calculateArgument(String _class, MethodId _method, CustList<Argument> _args, ContextEl _cont) {
        MethodId fct_ = new MethodId(_method.isStaticMethod(), _method.getName(),_method.getParametersTypes());
        MethodBlock method_ = Classes.getMethodBodiesById(_cont, _class, fct_).first();
        Block firstChild_ = method_.getFirstChild();
        if (firstChild_ == null) {
            Argument a_ = new Argument();
            return a_;
        }
        Argument argGlLoc_ = new Argument();
        return ProcessMethod.calculateArgument(argGlLoc_, _class, fct_, _args, _cont);
    }

    protected static MethodId getMethodId(String _name, String..._classNames) {
        StringList cl_ = new StringList();
        for (String c: _classNames) {
            cl_.add(c);
        }
        return new MethodId(true, _name, cl_);
    }

    protected static MethodId getMethodId(String _name, boolean _vararg, String..._classNames) {
        StringList cl_ = new StringList();
        for (String c: _classNames) {
            cl_.add(c);
        }
        return new MethodId(true, _name, cl_, _vararg);
    }

    protected static Argument instanceArgument(String _class, Argument _global, ConstructorId _id, CustList<Argument> _args, ContextEl _cont) {
        int len_ = _id.getParametersTypes().size();
        StringList constraints_ = new StringList();
        for (int i = CustList.FIRST_INDEX; i < len_; i++) {
            String n_ = _id.getParametersTypes().get(i);
            constraints_.add(n_);
        }
        ConstructorId id_ = new ConstructorId(_id.getName(),constraints_, false);
        return ProcessMethod.instanceArgument(_class, _global, id_, _args, _cont);
    }

    protected static ConstructorId getConstructorId(String _name, String..._classNames) {
        StringList cl_ = new StringList();
        for (String c: _classNames) {
            cl_.add(c);
        }
        return new ConstructorId(_name, cl_, false);
    }

    protected static ConstructorId getConstructorId(String _name, boolean _vararg, String..._classNames) {
        StringList cl_ = new StringList();
        for (String c: _classNames) {
            cl_.add(c);
        }
        return new ConstructorId(_name, cl_, _vararg);
    }
    protected static ContextEl contextEl(int... _m) {
        ContextEl ct_;
        if (_m.length == 0) {
            ct_ = new ContextEl();
        } else {
            ct_ = new ContextEl(_m[0]);
        }
        ct_.getOptions().setEndLineSemiColumn(false);
        ct_.getOptions().setSpecialEnumsMethods(false);
        ct_.getOptions().setSuffixVar(VariableSuffix.DISTINCT);
        InitializationLgNames.initAdvStandards(ct_);
        ct_.initError();
        return ct_;
    }
    protected static ContextEl contextEnElDefault() {
        KeyWordsMap map_ = new KeyWordsMap();
        KeyWords k_ = map_.getKeyWords("en");
        LgNames lgNames_ = new CustLgNames();
        ContextEl ct_ = new ContextEl(new DefaultLockingClass(),new DefaultInitializer(), new Options(), k_);
        lgNames_.setContext(ct_);
        map_.initEnStds(lgNames_);
        lgNames_.build();
        ct_.setStandards(lgNames_);
        lgNames_.setupOverrides(ct_);
        ct_.initError();
        return ct_;
    }
    protected static ContextEl contextElDefault(int... _m) {
        ContextEl ct_;
        if (_m.length == 0) {
            ct_ = new ContextEl();
        } else {
            ct_ = new ContextEl(_m[0]);
        }
        InitializationLgNames.initAdvStandards(ct_);
        ct_.initError();
        return ct_;
    }
    protected static ContextEl contextEl(boolean _multAff, boolean _eqPlus,int... _m) {
        ContextEl ct_;
        if (_m.length == 0) {
            ct_ = new ContextEl();
        } else {
            ct_ = new ContextEl(_m[0]);
        }
        ct_.getOptions().setEndLineSemiColumn(false);
        ct_.getOptions().setSpecialEnumsMethods(false);
        ct_.getOptions().setSuffixVar(VariableSuffix.DISTINCT);
        InitializationLgNames.initAdvStandards(ct_);
        ct_.initError();
        return ct_;
    }
    protected static ContextEl contextEl(VariableSuffix _suf,int... _m) {
        ContextEl ct_;
        if (_m.length == 0) {
            ct_ = new ContextEl();
        } else {
            ct_ = new ContextEl(_m[0]);
        }
        ct_.getOptions().setEndLineSemiColumn(false);
        ct_.getOptions().setSpecialEnumsMethods(false);
        ct_.getOptions().setSuffixVar(_suf);
        InitializationLgNames.initAdvStandards(ct_);
        ct_.initError();
        return ct_;
    }
    protected static ContextEl contextEl(VariableSuffix _suf,boolean _multAff, boolean _eqPlus,int... _m) {
        ContextEl ct_;
        if (_m.length == 0) {
            ct_ = new ContextEl();
        } else {
            ct_ = new ContextEl(_m[0]);
        }
        ct_.getOptions().setEndLineSemiColumn(false);
        ct_.getOptions().setSpecialEnumsMethods(false);
        ct_.getOptions().setSuffixVar(_suf);
        InitializationLgNames.initAdvStandards(ct_);
        ct_.initError();
        return ct_;
    }
}
