package code.bean.nat;

import code.bean.Bean;
import code.bean.validator.Validator;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.analyze.AbstractFieldFilter;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.errors.AnalysisMessages;
import code.expressionlanguage.analyze.files.CommentDelimiters;
import code.expressionlanguage.analyze.instr.OperationsSequence;
import code.expressionlanguage.analyze.opers.StandardInstancingOperation;
import code.expressionlanguage.common.ClassField;
import code.expressionlanguage.analyze.instr.Delimiters;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.*;
import code.expressionlanguage.exec.opers.ExecArrayFieldOperation;
import code.expressionlanguage.exec.variables.ArgumentsPair;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.functionid.ConstructorId;
import code.expressionlanguage.functionid.MethodModifier;
import code.expressionlanguage.fwd.Forwards;
import code.expressionlanguage.options.*;
import code.expressionlanguage.stds.*;
import code.expressionlanguage.structs.*;
import code.formathtml.Configuration;
import code.formathtml.Navigation;
import code.formathtml.analyze.AnalyzingDoc;
import code.formathtml.exec.RendStackCall;
import code.formathtml.exec.blocks.RendImport;
import code.formathtml.exec.opers.RendDynOperationNode;
import code.formathtml.exec.opers.RendSettableFieldOperation;
import code.formathtml.exec.opers.RendStdFctOperation;
import code.formathtml.structs.BeanInfo;
import code.formathtml.structs.Message;
import code.formathtml.util.*;
import code.maths.montecarlo.DefaultGenerator;
import code.util.*;
import code.util.core.StringUtil;

public abstract class BeanNatCommonLgNames extends BeanLgNames {
    public static final String TYPE_LIST = "ls";
    public static final String TYPE_MAP = "lse";
    public static final String TYPE_DISPLAYABLE = "code.util.ints.Displayable";
    public static final String TYPE_VALIDATOR = "code.bean.validator.Validator";
    public static final String TYPE_BEAN = "code.bean.Bean";

    protected static final String TYPE_ENTRY = "$custentry";
    protected static final String TYPE_ITERATOR = "code.util.SimpleItr";
    protected static final String TYPE_COUNTABLE = "code.util.ints.Countable";
    private static final String TYPE_ENTRIES = "$custentries";
    private final StringMap<String> iterables = new StringMap<String>();
    private final StringMap<Bean> beans = new StringMap<Bean>();
    private final StringMap<Validator> validators = new StringMap<Validator>();

    public BeanNatCommonLgNames() {
        super(new DefaultGenerator());
    }

    @Override
    public void preInitBeans(Configuration _conf) {
        for (EntryCust<String, BeanInfo> e: _conf.getBeansInfos().entryList()) {
            _conf.getBuiltBeans().addEntry(e.getKey(), NullStruct.NULL_VALUE);
        }
    }

    @Override
    public void initBeans(Configuration _conf, String _language, Struct _db, ContextEl _ctx, RendStackCall _rendStack) {
        int index_ = 0;
        for (EntryCust<String, BeanInfo> e: _conf.getBeansInfos().entryList()) {
            _conf.getBuiltBeans().setValue(index_, newSimpleBean(_language, e.getValue(), _ctx, _rendStack.getStackCall()));
            index_++;
        }
    }

    protected abstract Struct newSimpleBean(String _language, BeanInfo _bean, ContextEl _ctx, StackCall _stackCall);

    public boolean setBeanForms(Configuration _conf, Struct _mainBean,
                                RendImport _node, boolean _keepField, String _beanName, ContextEl _ctx, RendStackCall _rendStack) {
        return true;
    }

    @Override
    public Argument getCommonArgument(RendSettableFieldOperation _rend, Argument _previous, ContextEl _context, RendStackCall _stack) {
        ClassField fieldId_ = _rend.getClassField();
        Struct default_ = _previous.getStruct();
        ResultErrorStd res_ = getOtherResult(_context, fieldId_, default_);
        return new Argument(res_.getResult());
    }

    @Override
    public Argument getCommonFctArgument(RendStdFctOperation _rend, Argument _previous, IdMap<RendDynOperationNode, ArgumentsPair> _all, ContextEl _context, RendStackCall _stack) {
        CustList<Argument> firstArgs_ = RendDynOperationNode.getArguments(_all,_rend);
        ClassMethodId classMethodId_ = _rend.getStdFctContent().getClassMethodId();
        ResultErrorStd res_ = LgNames.invokeMethod(_context, classMethodId_, _previous.getStruct(), null, _stack.getStackCall(), Argument.toArgArray(firstArgs_));
        return new Argument(res_.getResult());
    }

    protected Struct getBeanOrNull(Configuration _conf,String _currentBeanName) {
        return getBean(_conf,_currentBeanName);
    }

    private Struct getBean(Configuration _conf,String _beanName) {
        return _conf.getBuiltBeans().getVal(_beanName);
    }
    @Override
    public Message validate(Configuration _conf, NodeContainer _cont, String _validatorId, ContextEl _ctx, RendStackCall _rendStack) {
        Validator validator_ = validators.getVal(_validatorId);
        if (validator_ == null) {
            return null;
        }
        StringList v_ = _cont.getValue();
        NodeInformations nInfos_ = _cont.getNodeInformation();
        String className_ = nInfos_.getInputClass();
        ResultErrorStd resError_ = getStructToBeValidated(v_, className_, _conf, _ctx, _rendStack);
        if (_ctx.callsOrException(_rendStack.getStackCall())) {
            return null;
        }
        Struct obj_ = resError_.getResult();
        return validator_.validate(obj_);
    }

    public abstract ResultErrorStd getOtherResult(ContextEl _cont, ClassField _classField, Struct _instance);

    protected void buildBeans() {
        CustList<StandardField> fields_;
        fields_ = new CustList<StandardField>();
        SpecialNatClass std_;
        CustList<StandardMethod> methods_;
        CustList<StandardConstructor> constructors_;
        methods_ = new CustList<StandardMethod>();
        StringList params_;
        StandardMethod method_;
        constructors_ = new CustList<StandardConstructor>();
        std_ = new SpecialNatClass(TYPE_BEAN, fields_, constructors_, methods_, getAliasObject(), MethodModifier.NORMAL);
        getStandards().addEntry(TYPE_BEAN, std_);
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        SpecialNatClass cl_;
        cl_ = new SpecialNatClass(TYPE_LIST, fields_, constructors_, methods_, getAliasObject(), MethodModifier.NORMAL);
        cl_.getDirectInterfaces().add(TYPE_COUNTABLE);
        getIterables().put(TYPE_LIST, getAliasObject());
        getStandards().addEntry(TYPE_LIST, cl_);
        methods_ = new CustList<StandardMethod>();
        cl_ = new SpecialNatClass(TYPE_MAP, fields_, constructors_, methods_, getAliasObject(), MethodModifier.NORMAL);
        cl_.getDirectInterfaces().add(TYPE_COUNTABLE);
        cl_.getDirectInterfaces().add(TYPE_ENTRIES);
        getIterables().put(TYPE_MAP, getAliasObject());
        getStandards().addEntry(TYPE_MAP, cl_);
        params_ = new StringList();
        methods_ = new CustList<StandardMethod>();
        StandardInterface stdi_ = new StandardInterface(TYPE_COUNTABLE, methods_, params_);
        params_ = new StringList();
        method_ = new StandardMethod(getContent().getCharSeq().getAliasIsEmpty(), params_, getAliasPrimBoolean(), false, MethodModifier.ABSTRACT);
        methods_.add(method_);
        getStandards().addEntry(TYPE_COUNTABLE, stdi_);
        methods_ = new CustList<StandardMethod>();
        stdi_ = new StandardInterface(TYPE_ENTRIES, methods_, new StringList());
        getStandards().addEntry(TYPE_ENTRIES, stdi_);
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        methods_ = new CustList<StandardMethod>();
        std_ = new SpecialNatClass(TYPE_ITERATOR, fields_, constructors_, methods_, getAliasObject(), MethodModifier.FINAL);
        getStandards().addEntry(TYPE_ITERATOR, std_);
        methods_ = new CustList<StandardMethod>();
        stdi_ = new StandardInterface(TYPE_DISPLAYABLE, methods_, new StringList());
        getStandards().addEntry(TYPE_DISPLAYABLE, stdi_);
        cl_ = new SpecialNatClass(TYPE_VALIDATOR, fields_, constructors_, methods_, getAliasObject(), MethodModifier.ABSTRACT);
        getStandards().addEntry(TYPE_VALIDATOR, cl_);
    }


    @Override
    public String getStringKey(Struct _instance, ContextEl _ctx, RendStackCall _stack) {
        ResultErrorStd res_ = getName(_ctx, _instance);
        Struct str_ = res_.getResult();
        return processString(new Argument(str_), _ctx, _stack);
    }

    public ResultErrorStd getName(ContextEl _cont, Struct _instance) {
        return getOtherName(_cont, _instance);
    }
    public abstract ResultErrorStd getOtherName(ContextEl _cont, Struct _instance);
//    @Override
//    public void beforeDisplaying(Struct _arg, Configuration _cont, ContextEl _ctx, StackCall _stack, RendStackCall _rendStack) {
//        ((BeanStruct)_arg).getBean().beforeDisplaying();
//    }


    public ArrayStruct getStringArray(StringList _ls) {
        return getArray(_ls,getAliasString());
    }

    public ArrayStruct getLongsArray(CustList<Longs> _ls) {
        ArrayStruct arr_ = new ArrayStruct(_ls.size(), StringExpUtil.getPrettyArrayType(TYPE_LIST));
        int j_ = 0;
        for (Longs s:_ls) {
            arr_.set(j_,getLongArray(s));
            j_++;
        }
        return arr_;
    }

    public ArrayStruct getLongArray(Longs _ls) {
        ArrayStruct arr_ = new ArrayStruct(_ls.size(), StringExpUtil.getPrettyArrayType(getAliasPrimLong()));
        int j_ = 0;
        for (Long s:_ls) {
            arr_.set(j_,new LongStruct(s));
            j_++;
        }
        return arr_;
    }

    public static ArrayStruct getArray(StringList _ls, String _cl) {
        ArrayStruct arr_ = new ArrayStruct(_ls.size(), StringExpUtil.getPrettyArrayType(_cl));
        int j_ = 0;
        for (String s:_ls) {
            arr_.set(j_,new StringStruct(StringUtil.nullToEmpty(s)));
            j_++;
        }
        return arr_;
    }
    public static void initInstancesPattern(Configuration _conf, AnalyzingDoc _anaDoc) {
        for (EntryCust<String, BeanInfo> e: _conf.getBeansInfos().entryList()) {
            BeanInfo info_ = e.getValue();
            OperationsSequence seq_ = new OperationsSequence();
            seq_.setValue("",0);
            seq_.setDelimiter(new Delimiters());
            StandardInstancingOperation root_ = new StandardInstancingOperation(0,0,null,seq_);
            root_.setConstId(new ConstructorId(info_.getClassName(), new StringList(), false));
            info_.setResolvedClassName(info_.getClassName());
            _anaDoc.getBeansInfos().addEntry(root_,info_);
        }
    }

    protected StringMap<String> getIterables() {
        return iterables;
    }

    @Override
    public ContextEl newContext(Options _opt,Forwards _options) {
        return new NativeContextEl(new CommonExecutionInfos(_opt.getTabWidth(),_opt.getStack(),this, _options.getClasses(), _options.getCoverage(),new DefaultLockingClass(),new DefaultInitializer()));
    }

    @Override
    public AbstractFieldFilter newFieldFilter() {
        return new NativeFieldFilter();
    }

    public Forwards setupNative(AnalyzedPageEl _page, DualConfigurationContext _context) {
        Options options_ = _context.getOptions();
        Forwards forwards_ = new Forwards(this, _page.getFileBuilder(), options_);
        _page.setLogErr(forwards_.getGenerator());
        _page.setOptions(options_);
        CustList<CommentDelimiters> comments_ = options_.getComments();
        CommentsUtil.checkAndUpdateComments(comments_,new CustList<CommentDelimiters>());
        _page.setComments(comments_);
        _page.setDefaultAccess(options_.getDefaultAccess());
        _page.setAnalysisMessages(new AnalysisMessages());
        _page.setKeyWords(new KeyWords());
        _page.setStandards(forwards_.getGenerator().getContent());
        _page.setCalculator(forwards_.getConstantsCalculator());
        _page.setFieldFilter(forwards_.getFieldFilter());
        _page.setFileBuilder(forwards_.getFileBuilder());
        _page.setResources(forwards_.getClasses().getResources());
        _page.setStaticFields(forwards_.getClasses().getStaticFields());
        _page.setTabWidth(options_.getTabWidth());
        _page.setGettingErrors(options_.isGettingErrors());
        build();
        ValidatorStandard.setupOverrides(_page);
        return forwards_;
    }
    public void rendRefresh(Navigation _navigation, ContextEl _context, RendStackCall _rendStack) {
        for (Bean b: beans.values()) {
            b.setLanguage(_navigation.getLanguage());
        }
        _navigation.getSession().setCurrentLanguage(_navigation.getLanguage());
        _navigation.processRendAnchorRequest(_navigation.getCurrentUrl(), this, _context, _rendStack);
    }

    public StringMap<Bean> getBeans() {
        return beans;
    }

    public StringMap<Validator> getValidators() {
        return validators;
    }

    public static PairStruct getPairStruct(Struct _arg, ContextEl _ctx) {
        if (_arg instanceof PairStruct) {
            return (PairStruct)_arg;
        }
        String typeInst_ = _ctx.getStandards().getCoreNames().getAliasObject();
        return new PairStruct(StringUtil.concat(TYPE_ENTRY,StringExpUtil.TEMPLATE_BEGIN,typeInst_,",",typeInst_,StringExpUtil.TEMPLATE_END),NullStruct.NULL_VALUE,NullStruct.NULL_VALUE);
    }

    public static SimpleItrStruct getSimpleItrStruct(Struct _arg, ContextEl _ctx) {
        if (_arg instanceof SimpleItrStruct) {
            return (SimpleItrStruct)_arg;
        }
        ArrayStruct array_ = ExecArrayFieldOperation.getArray(_arg, _ctx);
        String typeInst_ = StringUtil.nullToEmpty(StringExpUtil.getQuickComponentType(array_.getClassName(_ctx)));
        return new SimpleItrStruct(StringUtil.concat(TYPE_ITERATOR,StringExpUtil.TEMPLATE_BEGIN,typeInst_,StringExpUtil.TEMPLATE_END),array_);
    }

}
