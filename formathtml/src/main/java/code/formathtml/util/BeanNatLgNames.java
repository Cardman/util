package code.formathtml.util;

import code.bean.Bean;
import code.bean.BeanInfo;
import code.bean.validator.Message;
import code.bean.validator.Validator;
import code.expressionlanguage.AnalyzedPageEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.ExecutableCode;
import code.expressionlanguage.inherits.Templates;
import code.expressionlanguage.opers.util.*;
import code.expressionlanguage.stds.*;
import code.expressionlanguage.structs.*;
import code.formathtml.*;
import code.formathtml.structs.*;
import code.sml.Element;
import code.util.*;
import code.util.ints.*;

public abstract class BeanNatLgNames extends BeanLgNames {

    private StringMapObject storedForms;

    static Object[] adaptedArgs(StringList _params, BeanNatLgNames _stds, Struct... _args) {
        int len_ = _params.size();
        Object[] args_ = new Object[len_];
        for (int i = 0; i < len_; i++) {
            Struct argStruct_ = _args[i];
            if (argStruct_ instanceof IntStruct) {
                args_[i] = ((NumberStruct) argStruct_).intStruct();
            } else {
                args_[i] = ((NumberStruct) argStruct_).longStruct();
            }
        }
        return args_;
    }

    static Object adaptedArg(BeanNatLgNames _stds, Struct _args) {
        if (_args == NullStruct.NULL_VALUE) {
            return null;
        }
        if (_args instanceof NumberStruct) {
            if (_args instanceof ShortStruct) {
                return ((ShortStruct) _args).shortStruct();
            }
            if (_args instanceof IntStruct) {
                return ((IntStruct) _args).intStruct();
            }
            return ((NumberStruct) _args).longStruct();
        }
        if (_args instanceof StringStruct) {
            return ((StringStruct)_args).getInstance();
        }
        if (_args instanceof BooleanStruct) {
            return ((BooleanStruct) _args).getInstance();
        }
        return ((RealInstanceStruct) _args).getInstance();
    }

    @Override
    public void preInitBeans(Configuration _conf) {
        for (EntryCust<String, BeanInfo> e: _conf.getBeansInfos().entryList()) {
            _conf.getBuiltBeans().addEntry(e.getKey(), NullStruct.NULL_VALUE);
        }
    }

    @Override
    public void initBeans(Configuration _conf,String _language,Struct _db) {
        int index_ = 0;
        for (EntryCust<String, BeanInfo> e: _conf.getBeansInfos().entryList()) {
            _conf.getBuiltBeans().setValue(index_, _conf.newSimpleBean(_language, _db, e.getValue()));
            index_++;
        }
    }
    @Override
    public String getInputClass(Element _write, Configuration _conf) {
        String class_ = _write.getAttribute(StringList.concat(_conf.getPrefix(),ATTRIBUTE_CLASS_NAME));
        if (!class_.isEmpty()) {
            return class_;
        }
        return super.getInputClass(_write,_conf);
    }
    @Override
    public void forwardDataBase(Struct _bean, Struct _to, Configuration _conf) {
        ((BeanStruct)_to).getBean().setDataBase(((BeanStruct)_bean).getBean().getDataBase());
    }
    public void storeForms(Struct _bean, Configuration _conf) {
        storedForms = ((BeanStruct)_bean).getBean().getForms();
    }

    @Override
    public void setStoredForms(Struct _bean, Configuration _conf) {
        ((BeanStruct)_bean).getBean().setForms(storedForms);
    }

    @Override
    protected void gearFw(Configuration _conf, Struct _mainBean, RendImport _node, boolean _keepField, Struct _bean) {

        StringMapObject forms_ = ((BeanStruct)_bean).getBean().getForms();
        StringMapObject formsMap_ = ((BeanStruct)_mainBean).getBean().getForms();
        if (_keepField) {
            for (RendBlock f_: RendBlock.getDirectChildren(_node)) {
                if (!(f_ instanceof RendImportForm)) {
                    continue;
                }
                String name_ = ((RendImportForm)f_).getName();
                forms_.put(name_,formsMap_.getVal(name_));
            }
        } else {
            //add option for copying forms (default copy)
            forms_.putAllMap(formsMap_);
        }
    }

    @Override
    public Message validate(Configuration _conf, NodeContainer _cont, String _validatorId) {
        Validator validator_ = _conf.getValidators().getVal(_validatorId);
        if (validator_ == null) {
            return null;
        }
        StringList v_ = _cont.getValue();
        NodeInformations nInfos_ = _cont.getNodeInformation();
        String className_ = nInfos_.getInputClass();
        ResultErrorStd resError_ = getStructToBeValidated(v_, className_, _conf.getContext());
        if (resError_.getError() != null) {
            String err_ = resError_.getError();
            _conf.getContext().setException(new ErrorStruct(_conf,err_));
            return null;
        }
        Struct obj_ = resError_.getResult();
        Object ad_ = adaptedArg(this, obj_);
        return validator_.validate(ad_);
    }

    public static ResultErrorStd getField(ContextEl _cont, ClassField _classField, Struct _instance) {
        BeanNatLgNames lgNames_ = (BeanNatLgNames) _cont.getStandards();
        return lgNames_.getOtherResult(_cont, _classField, _instance);
    }

    public ResultErrorStd getOtherResult(ContextEl _cont, ClassField _classField, Struct _instance) {
        return new ResultErrorStd();
    }
    public ResultErrorStd setOtherResult(ContextEl _cont, ClassField _classField, Struct _instance, Object _value) {
        return new ResultErrorStd();
    }

    public void buildBeans() {
        StringMap<StandardField> fields_;
        fields_ = new StringMap<StandardField>();
        StandardClass std_;
        ObjectMap<MethodId, StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        StringList params_;
        StandardMethod method_;
        constructors_ = new CustList<StandardConstructor>();
        std_ = new StandardClass(getBean(), fields_, constructors_, methods_, getAliasObject(), MethodModifier.NORMAL);
        getStandards().put(getBean(), std_);
        fields_ = new StringMap<StandardField>();
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        StandardClass cl_;
        cl_ = new StandardClass(getCustList(), fields_, constructors_, methods_, getAliasObject(), MethodModifier.NORMAL);
        cl_.getDirectInterfaces().add(getAliasCountable());
        getIterables().put(getCustList(),getAliasObject());
        getStandards().put(getCustList(), cl_);
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        cl_ = new StandardClass(getCustMap(), fields_, constructors_, methods_, getAliasObject(), MethodModifier.NORMAL);
        cl_.getDirectInterfaces().add(getAliasCountable());
        cl_.getDirectInterfaces().add(getCustEntries());
        getIterables().put(getCustMap(),getAliasObject());
        getStandards().put(getCustMap(), cl_);
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        cl_ = new StandardClass(getErrorEl(), fields_, constructors_, methods_, getAliasError(), MethodModifier.ABSTRACT);
        getStandards().put(getErrorEl(), cl_);
        params_ = new StringList();
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        StandardInterface stdi_ = new StandardInterface(getAliasCountable(), methods_, params_);
        params_ = new StringList();
        method_ = new StandardMethod(getAliasIsEmpty(), params_, getAliasPrimBoolean(), false, MethodModifier.ABSTRACT,stdi_);
        methods_.put(method_.getId(), method_);
        getStandards().put(getAliasCountable(), stdi_);
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new StringMap<StandardField>();
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        StandardClass stdcl_ = new StandardClass(getAliasSimpleIteratorType(), fields_, constructors_, methods_, getAliasObject(), MethodModifier.FINAL);
        std_ = stdcl_;
        getStandards().put(getAliasSimpleIteratorType(), std_);
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        stdi_ = new StandardInterface(getAliasDisplayable(), methods_, new StringList());
        getStandards().put(getAliasDisplayable(), stdi_);
        cl_ = new StandardClass(getValidator(), fields_, constructors_, methods_, getAliasObject(), MethodModifier.ABSTRACT);
        getStandards().put(getValidator(), cl_);
    }

    public void buildIterables(Configuration _context) {
        ContextEl context_ = _context.getContext();
        context_.setAnalyzing(new AnalyzedPageEl());
    }
    @Override
    public IterableAnalysisResult getCustomType(StringList _names, ContextEl _context) {
        StringList out_ = new StringList();
        Boolean nativeCmp_ = null;
        for (String f: _names) {
            String type_ = getIterableFullTypeByStds(f, _context);
            nativeCmp_ = true;
            if (type_ != null) {
                out_.add(type_);
            }
        }
        out_.removeDuplicates();
        return new NativeIterableAnalysisResult(out_, nativeCmp_);
    }
    private static String getIterableFullTypeByStds(String _subType, ContextEl _context) {
        BeanLgNames lgNames_ = (BeanLgNames) _context.getStandards();
        String it_ = lgNames_.getIterables().getVal(_subType);
        if (it_ == null) {
            return null;
        }
        return StringList.concat(lgNames_.getAliasIterable(),"<",it_,">");
    }


    @Override
    public String getStringKey(Configuration _conf, Struct _instance) {
        ContextEl cont_ = _conf.getContext();
        ResultErrorStd res_ = getName(cont_, _instance);
        Struct str_ = res_.getResult();
        return processString(new Argument(str_),_conf);
    }

    public ResultErrorStd getName(ContextEl _cont, Struct _instance) {
        ResultErrorStd res_ = new ResultErrorStd();
        if (_instance instanceof DisplayableStruct) {
            res_.setResult(_instance);
            return res_;
        }
        return getOtherName(_cont, _instance);
    }
    public ResultErrorStd getOtherName(ContextEl _cont, Struct _instance) {
        return new ResultErrorStd();
    }
    @Override
    public void beforeDisplaying(Struct _arg, Configuration _cont) {
        ((BeanStruct)_arg).getBean().beforeDisplaying();
    }

    @Override
    public String getScope(Struct _bean, Configuration _cont) {
        return ((BeanStruct)_bean).getBean().getScope();
    }

    @Override
    public void setScope(Struct _bean, String _scope, Configuration _cont) {
        ((BeanStruct)_bean).getBean().setScope(_scope);
    }

    public void setLanguage(Struct _bean, String _scope,Configuration _cont) {
        ((BeanStruct)_bean).getBean().setLanguage(_scope);
    }


    @Override
    public Argument iteratorMultTable(Struct _arg, Configuration _cont) {
        Object instance_ = ((RealInstanceStruct) _arg).getInstance();
        SimpleIterable db_ = ((SimpleEntries)instance_).entries();
        SimpleItr it_ = db_.simpleIterator();
        String itStr_ = getCustEntry();
        return new Argument(StdStruct.newInstance(it_, StringList.concat(getAliasSimpleIteratorType(),Templates.TEMPLATE_BEGIN,itStr_,Templates.TEMPLATE_END)));
    }

    @Override
    public Argument hasNextPair(Struct _arg, Configuration _conf) {
        Object instance_ = ((RealInstanceStruct) _arg).getInstance();
        SimpleItr it_ = (SimpleItr) instance_;
        return new Argument(new BooleanStruct(it_.hasNext()));
    }

    @Override
    public Argument nextPair(Struct _arg, Configuration _conf) {
        Object instance_ = ((RealInstanceStruct) _arg).getInstance();
        Object resObj_ = ((SimpleItr)instance_).next();
        return new Argument(StdStruct.newInstance(resObj_,getCustEntry()));
    }

    @Override
    public Argument first(Struct _arg, Configuration _conf) {
        Object instance_ = ((RealInstanceStruct) _arg).getInstance();
        Object resObj_ = ((SimpleEntry)instance_).getSimpleKey();
        return new Argument(wrapStd(resObj_, _conf));
    }

    @Override
    public Argument second(Struct _arg, Configuration _conf) {
        Object instance_ = ((RealInstanceStruct) _arg).getInstance();
        Object resObj_ = ((SimpleEntry)instance_).getSimpleValue();
        return new Argument(wrapStd(resObj_, _conf));
    }

    @Override
    public Argument iterator(Struct _arg, Configuration _cont) {
        Object instance_ = ((RealInstanceStruct) _arg).getInstance();
        String typeInst_ = getStructClassName(_arg, _cont.getContext());
        String it_ = getIterables().getVal(typeInst_);
        return new Argument(StdStruct.newInstance(((SimpleIterable) instance_).simpleIterator(), StringList.concat(getAliasSimpleIteratorType(),Templates.TEMPLATE_BEGIN,it_,Templates.TEMPLATE_END)));
    }

    @Override
    public Argument next(Struct _arg, Configuration _cont) {
        Object instance_ = ((RealInstanceStruct) _arg).getInstance();
        Object resObj_ = ((SimpleItr)instance_).next();
        return new Argument(wrapStd(resObj_, _cont));
    }

    @Override
    public Argument hasNext(Struct _arg, Configuration _cont) {
        Object instance_ = ((RealInstanceStruct) _arg).getInstance();
        SimpleItr it_ = (SimpleItr) instance_;
        return new Argument(new BooleanStruct(it_.hasNext()));
    }

    protected Struct wrapStd(Object _element, ExecutableCode _ex){
        return NullStruct.NULL_VALUE;
    }
    @Override
    public String processString(Argument _arg, Configuration _cont) {
        Struct struct_ = _arg.getStruct();
        if (struct_ instanceof DisplayableStruct) {
            return ((DisplayableStruct)struct_).getDisplayedString(_cont).getInstance();
        }
        if (struct_ instanceof RealInstanceStruct) {
            Object inst_ = ((RealInstanceStruct) struct_).getInstance();
            if (inst_ instanceof Displayable) {
                return ((Displayable)inst_).display();
            }
        }
        ContextEl context_ = _cont.getContext();
        return _arg.getObjectClassName(context_);
    }

    public static ResultErrorStd setField(ContextEl _cont, ClassField _classField, Struct _instance, Struct _value) {
        BeanNatLgNames lgNames_ = (BeanNatLgNames) _cont.getStandards();
        Object value_ = adaptedArg(lgNames_, _value);
        return lgNames_.setOtherResult(_cont, _classField, _instance, value_);
    }

    @Override
    public ResultErrorStd getOtherResult(ContextEl _cont,
                                         ConstructorId _method, Struct... _args) {
        StringList list_ = _method.getParametersTypes();
        Object[] argsObj_ = adaptedArgs(list_, this, _args);
        return getOtherResultBean(_cont, _method, argsObj_);
    }

    public ResultErrorStd getOtherResultBean(ContextEl _cont,
                                             ConstructorId _method, Object... _args) {
        return new ResultErrorStd();
    }

    @Override
    public ResultErrorStd getOtherResult(ContextEl _cont, Struct _instance,
                                         ClassMethodId _method, Struct... _args) {
        ResultErrorStd res_ = new ResultErrorStd();
        StringList list_ = _method.getConstraints().getParametersTypes();
        Object[] argsObj_ = adaptedArgs(list_, this, _args);
        Object instance_ = ((RealInstanceStruct)_instance).getInstance();
        if (instance_ instanceof Countable) {
            res_.setResult(new BooleanStruct(((Countable) instance_).isEmpty()));
            return res_;
        }
        return getOtherResultBean(_cont, _instance, _method, argsObj_);
    }
    public ResultErrorStd getOtherResultBean(ContextEl _cont, Struct _instance,
                                             ClassMethodId _method, Object... _args) {
        return new ResultErrorStd();
    }
    public String getStdBeanStructClassName(Object _struct, ContextEl _context) {
        String cl_ = getOtherBeanStructClassName(_struct, _context);
        return cl_;
    }
    public String getOtherBeanStructClassName(Object _struct, ContextEl _context) {
        return getAliasObject();
    }

}
