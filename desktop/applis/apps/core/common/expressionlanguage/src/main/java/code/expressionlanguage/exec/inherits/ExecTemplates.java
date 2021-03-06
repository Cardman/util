package code.expressionlanguage.exec.inherits;

import code.expressionlanguage.*;
import code.expressionlanguage.common.*;
import code.expressionlanguage.exec.*;
import code.expressionlanguage.exec.blocks.ExecAbstractSwitchMethod;
import code.expressionlanguage.exec.blocks.ExecNamedFunctionBlock;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.blocks.ExecSwitchInstanceMethod;
import code.expressionlanguage.exec.calls.AbstractPageEl;
import code.expressionlanguage.exec.calls.util.CustomFoundExc;
import code.expressionlanguage.exec.calls.util.CustomFoundMethod;
import code.expressionlanguage.exec.calls.util.CustomFoundSwitch;
import code.expressionlanguage.exec.opers.ExecArrayFieldOperation;
import code.expressionlanguage.exec.types.ExecClassArgumentMatching;
import code.expressionlanguage.exec.util.*;
import code.expressionlanguage.exec.variables.*;
import code.expressionlanguage.functionid.Identifiable;
import code.expressionlanguage.fwd.blocks.ExecTypeFunction;
import code.expressionlanguage.fwd.opers.ExecVariableContent;
import code.expressionlanguage.stds.LgNames;
import code.expressionlanguage.structs.*;
import code.util.*;
import code.util.core.IndexConstants;
import code.util.core.StringUtil;

public final class ExecTemplates {

    public static final String ARR_BEG_STRING = "[";
    public static final String TEMPLATE_SEP = ",";
    public static final String TEMPLATE_END = ">";
    public static final String TEMPLATE_BEGIN = "<";
    public static final String PREFIX_VAR_TYPE = "#";
    public static final String SUB_TYPE = "?";
    public static final String SUP_TYPE = "!";

    private static final String RETURN_LINE = "\n";

    private ExecTemplates(){
    }

    public static Struct newCustomArrayOrExc(String _className, Ints _dims, ContextEl _cont, StackCall _stackCall) {
        return newCustomArrayOrExc(new Ints(),_className,_dims,_cont, _stackCall);
    }
    public static Struct newCustomArrayOrExc(Ints _filter, String _className, Ints _dims, ContextEl _cont, StackCall _stackCall) {
        Ints dims_ = new Ints();
        String size_;
        LgNames lgNames_ = _cont.getStandards();
        size_ = lgNames_.getContent().getCoreNames().getAliasBadSize();
        if (_dims.isEmpty()) {
            return new ErrorStruct(_cont,size_, _stackCall);
        }
        int j_ = 0;
        for (int s: _dims) {
            if (s < 0) {
                if (_filter.isValidIndex(j_)) {
                    int off_ = _filter.get(j_);
                    _stackCall.setOffset(off_);
                }
                return new ErrorStruct(_cont, StringUtil.concat(Long.toString(s),"<0"),size_, _stackCall);
            }
            dims_.add(s);
            j_++;
        }
        return newCustomArray(_className, dims_, _cont);
    }
    public static ArrayStruct newCustomArray(String _className, Ints _dims, ContextEl _cont) {
        TreeMap<Ints,Struct> indexesArray_;
        indexesArray_ = new TreeMap<Ints,Struct>(new IndexesComparator());
        ArrayStruct output_ = new ArrayStruct(_dims.first(), StringExpUtil.getPrettyArrayType(_className, _dims.size()));
        Ints dims_ = new Ints();
        indexesArray_.put(new Ints(), output_);
        int glDim_ = _dims.size();
        int i_ = IndexConstants.FIRST_INDEX;
        Struct defClass_ = ExecClassArgumentMatching.defaultValue(_className, _cont);
        for (int i : _dims) {
            dims_.add(i);
            int nextIndex_ = i_ + 1;
            if (nextIndex_ >= glDim_) {
                for (Ints k: dims_.getAllIndexes()) {
                    indexesArray_.put(k, defClass_);
                }
                continue;
            }
            int curDim_ = _dims.get(nextIndex_);
            String formattedClass_ = StringExpUtil.getPrettyArrayType(_className, glDim_-nextIndex_);
            for (Ints k: dims_.getAllIndexes()) {
                Struct value_ = new ArrayStruct(curDim_, formattedClass_);
                indexesArray_.put(k, value_);
            }
            i_++;
        }
        for (EntryCust<Ints,Struct> e: indexesArray_.entryList()) {
            Ints key_ = e.getKey();
            Struct value_ = e.getValue();
            if (key_.isEmpty()) {
                continue;
            }
            Ints ind_ = new Ints(key_);
            ind_.removeQuicklyLast();
            int lastIndex_ = key_.last();
            Struct str_ = indexesArray_.getVal(ind_);
            ArrayStruct arr_ = ExecArrayFieldOperation.getArray(str_, _cont);
            trySet(arr_,lastIndex_,value_);
        }
        return output_;
    }
    static void trySet(ArrayStruct _arr, int _index, Struct _value) {
        if (_index < 0 || _index >= _arr.getLength()) {
            return;
        }
        _arr.set(_index, _value);
    }
    /** nb calls of getParent - super type - arg object
     use class parent of object
     */
    public static Struct getParent(int _nbAncestors, Struct _current, ContextEl _an, StackCall _stackCall) {
        Struct out_ = getParent(_nbAncestors, _current, _stackCall);
        if (out_ == NullStruct.NULL_VALUE) {
            String npe_ = _an.getStandards().getContent().getCoreNames().getAliasNullPe();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_an, npe_, _stackCall)));
            return _current;
        }
        return out_;
    }

    public static Struct getParent(int _nbAncestors, Struct _current, StackCall _stackCall) {
        Struct arg_ = _current;
        for (int i = 0; i < _nbAncestors; i++) {
            Struct enc_ = arg_;
            Struct par_ = enc_.getParent();
            _stackCall.getInitializingTypeInfos().addSensibleField(enc_, par_);
            arg_=par_;
        }
        return Argument.getNull(arg_);
    }

    public static String correctClassPartsDynamicWildCard(String _className, ContextEl _context) {
        if (StringExpUtil.isWildCard(_className)) {
            return "";
        }
        CustList<String> allArgTypes_ = StringExpUtil.getAllTypes(_className).mid(1);
        return getMade(_className, _context, allArgTypes_);
    }
    public static String correctClassPartsDynamicNotWildCard(String _className, ContextEl _context) {
        CustList<String> allArgTypes_ = StringExpUtil.getAllTypes(_className).mid(1);
        return getMade(_className, _context, allArgTypes_);
    }

    private static String getMade(String _className, ContextEl _context, CustList<String> _allArgTypes) {
        String madeVarTypes_ = getMadeVarTypes(_className, new StringList(_allArgTypes), _context);
        if (madeVarTypes_ == null) {
            return "";
        }
        return madeVarTypes_;
    }

    public static boolean checkObject(String _param, Argument _arg, ContextEl _context, StackCall _stackCall) {
        Struct str_ = _arg.getStruct();
        byte cast_ = ExecClassArgumentMatching.getPrimitiveWrapCast(_param, _context.getStandards());
        _arg.setStruct(NumParsers.convertObject(cast_, str_));
        return checkQuick(_param, _arg.getStruct().getClassName(_context), _context, _stackCall);
    }

    public static boolean checkQuick(String _param, String _arg, ContextEl _context, StackCall _stackCall) {
        Struct ex_ = checkObjectEx(_param,_arg,_context, _stackCall);
        if (ex_ != null) {
            _stackCall.setCallingState(new CustomFoundExc(ex_));
            return false;
        }
        return true;
    }

    public static Struct checkObjectEx(String _param, String _arg, ContextEl _context, StackCall _stackCall) {
        LgNames stds_ = _context.getStandards();
        ErrorType err_ = ExecInherits.safeObject(_param, _arg, _context);
        if (err_ == ErrorType.CAST) {
            String cast_ = stds_.getContent().getCoreNames().getAliasCastType();
            return new ErrorStruct(_context, getBadCastMessage(_param, _arg),cast_, _stackCall);
        }
        if (err_ == ErrorType.NPE) {
            String npe_ = stds_.getContent().getCoreNames().getAliasNullPe();
            return new ErrorStruct(_context,npe_, _stackCall);
        }
        return null;
    }
    public static void setCheckedElements(CustList<Argument> _args, Struct _arr, ContextEl _context, StackCall _stackCall) {
        int len_ = _args.size();
        for (int i = IndexConstants.FIRST_INDEX; i < len_; i++) {
            Argument chArg_ = _args.get(i);
            IntStruct ind_ = new IntStruct(i);
            setElement(_arr, ind_, chArg_.getStruct(), _context, _stackCall);
            if (_context.callsOrException(_stackCall)) {
                return;
            }
        }
    }

    public static String checkParams(ContextEl _conf, String _classNameFound, Identifiable _methodId,
                                     Argument _previous, CustList<Argument> _firstArgs, StackCall _stackCall) {
        LgNames stds_ = _conf.getStandards();
        String cast_ = stds_.getContent().getCoreNames().getAliasCastType();
        String classFormat_ = _classNameFound;
        if (!_methodId.isStaticMethod()) {
            String className_ = Argument.getNullableValue(_previous).getStruct().getClassName(_conf);
            classFormat_ = ExecInherits.getQuickFullTypeByBases(className_, classFormat_, _conf);
            if (classFormat_.isEmpty()) {
                _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, getBadCastMessage(_classNameFound, className_), cast_, _stackCall)));
                return "";
            }
        }
        if (okArgsSet(_methodId, _firstArgs, _conf, _stackCall) != null) {
            return "";
        }
        return classFormat_;
    }

    private static String getBadCastMessage(String _classNameFound, String _className) {
        return StringUtil.concat(_className, RETURN_LINE, _classNameFound, RETURN_LINE);
    }

    public static Struct okArgsSet(Identifiable _id, CustList<Argument> _firstArgs, ContextEl _conf, StackCall _stackCall) {
        Struct ex_ = okArgsEx(_id, _firstArgs, _conf, _stackCall);
        if (ex_ != null) {
            _stackCall.setCallingState(new CustomFoundExc(ex_));
        }
        return ex_;
    }

    public static Parameters okArgsSet(ExecNamedFunctionBlock _id, ExecFormattedRootBlock _classNameFound, Cache _cache, ArgumentListCall _firstArgs, ContextEl _conf, StackCall _stackCall) {
        Parameters p_ = new Parameters();
        possibleCheck(_classNameFound, _cache, _conf, _stackCall, p_);
        CustList<ArgumentWrapper> argumentWrappers_ = _firstArgs.getArgumentWrappers();
        ParametersTypes params_ = fetchParamTypes(_id, _classNameFound);
        checkNb(_conf, _stackCall, p_, argumentWrappers_, params_);
        checkArgs(_conf, _stackCall, p_, argumentWrappers_, params_);
        procRight(_id, _classNameFound, _conf, _firstArgs.getRight(), _stackCall, p_);
        if (p_.getError() != null) {
            _stackCall.setCallingState(new CustomFoundExc(p_.getError()));
        }
        return p_;
    }

    public static void okArgsSetSwCall(ExecAbstractSwitchMethod _id, ContextEl _conf, StackCall _stackCall, Argument _value) {
        AbstractPageEl last_ = _stackCall.getLastPage();
        Argument instance_ = last_.getGlobalArgument();
        ExecFormattedRootBlock glClass_ = last_.getGlobalClass();
        ExecRootBlock type_ = last_.getBlockRootType();
        if (_id instanceof ExecSwitchInstanceMethod) {
            Parameters out_ = okArgsExSw(_id, glClass_, new HiddenCache(last_), _conf, _stackCall, _value);
            if (out_.getError() == null) {
                _stackCall.setCallingState(new CustomFoundSwitch(instance_, glClass_, type_, _id, out_.getCache(), _value));
            } else {
                _stackCall.setCallingState(new CustomFoundExc(out_.getError()));
            }
            return;
        }
        _stackCall.setCallingState(new CustomFoundSwitch(instance_,glClass_,type_,_id, new HiddenCache(last_),_value));
    }

    public static Parameters okArgsSetSw(ExecAbstractSwitchMethod _id, ExecFormattedRootBlock _classNameFound, Cache _cache, ContextEl _conf, StackCall _stackCall, CustList<Argument> _arguments) {
        if (_arguments.isEmpty()) {
            Parameters p_ = new Parameters();
            LgNames stds_ = _conf.getStandards();
            String cast_ = stds_.getContent().getCoreNames().getAliasBadArgNumber();
            StringBuilder mess_ = countDiff(0, 1);
            ErrorStruct error_ = new ErrorStruct(_conf, mess_.toString(), cast_, _stackCall);
            _stackCall.setCallingState(new CustomFoundExc(error_));
            p_.setError(error_);
            return p_;
        }
        Parameters ex_ = okArgsExSw(_id, _classNameFound,_cache, _conf, _stackCall, _arguments.first());
        if (ex_.getError() != null) {
            _stackCall.setCallingState(new CustomFoundExc(ex_.getError()));
        }
        return ex_;
    }

    private static Struct okArgsEx(Identifiable _id, CustList<Argument> _firstArgs, ContextEl _conf, StackCall _stackCall) {
        StringList params_ = new StringList();
        int len_ = _id.getParametersTypesLength();
        for (int i = 0; i < len_; i++){
            String c_ = _id.getParametersType(i);
            if (i + 1 == len_ && _id.isVararg()) {
                c_ = StringExpUtil.getPrettyArrayType(c_);
            }
            params_.add(c_);
        }
        if (_firstArgs.size() != params_.size()) {
            LgNames stds_ = _conf.getStandards();
            String cast_ = stds_.getContent().getCoreNames().getAliasBadArgNumber();
            StringBuilder mess_ = countDiff(_firstArgs.size(), params_.size());
            return new ErrorStruct(_conf,mess_.toString(),cast_, _stackCall);
        }
        int i_ = IndexConstants.FIRST_INDEX;
        for (Argument a: _firstArgs) {
            String param_ = params_.get(i_);
            Struct ex_ = checkObjectEx(param_, a.getStruct().getClassName(_conf), _conf, _stackCall);
            if (ex_ != null) {
                return ex_;
            }
            i_++;
        }
        return null;
    }

    private static void checkNb(ContextEl _conf, StackCall _stackCall, Parameters _p, CustList<ArgumentWrapper> _argumentWrappers, ParametersTypes _params) {
        if (_p.getError() != null) {
            return;
        }
        for (Sizes s: new CustList<Sizes>(
                new Sizes(_argumentWrappers.size(), _params.getTypesAll().size())
        )) {
            if (s.getArg() != s.getParam()) {
                LgNames stds_ = _conf.getStandards();
                String cast_ = stds_.getContent().getCoreNames().getAliasBadArgNumber();
                StringBuilder mess_ = countDiff(s.getArg(), s.getParam());
                _p.setError(new ErrorStruct(_conf,mess_.toString(),cast_, _stackCall));
                break;
            }
        }
    }

    private static void checkArgs(ContextEl _conf, StackCall _stackCall, Parameters _p, CustList<ArgumentWrapper> _argumentWrappers, ParametersTypes _params) {
        int i_ = IndexConstants.FIRST_INDEX;
        for (ArgumentWrapper a: _argumentWrappers) {
            checkArg(_conf, _stackCall, _params,i_, _p, a);
            i_++;
        }
    }

    private static void checkArg(ContextEl _conf, StackCall _stackCall, ParametersTypes _params, int _i, Parameters _p, ArgumentWrapper _a) {
        if (_p.getError() != null) {
            return;
        }
        String param_ = _params.getTypesAll().get(_i);
        AbstractWrapper w_ = _a.getWrapper();
        if (w_ == null) {
            Argument a_ = _a.getValue();
            Struct ex_ = checkObjectEx(param_, a_.getStruct().getClassName(_conf), _conf, _stackCall);
            if (ex_ != null) {
                _p.setError(ex_);
                return;
            }
            Struct struct_ = a_.getStruct();
            LocalVariable lv_ = LocalVariable.newLocalVariable(struct_,param_);
            _p.getRefParameters().addEntry(_params.getNamesAll().get(_i),new VariableWrapper(lv_));
        } else {
            Struct ex_ = checkObjectEx(param_, w_.getClassName(_stackCall, _conf), _conf, _stackCall);
            if (ex_ != null) {
                _p.setError(ex_);
                return;
            }
            _p.getRefParameters().addEntry(_params.getNamesAll().get(_i),getWrap(w_));
        }
    }

    private static void procRight(ExecNamedFunctionBlock _id, ExecFormattedRootBlock _classNameFound, ContextEl _conf, Argument _right, StackCall _stackCall, Parameters _p) {
        if (_p.getError() != null) {
            return;
        }
        if (_id != null&&_right != null) {
            String type_ = _id.getImportedReturnType();
            type_ = ExecInherits.quickFormat(_classNameFound, type_);
            Struct ex_ = checkObjectEx(type_, _right.getStruct().getClassName(_conf), _conf, _stackCall);
            if (ex_ != null) {
                _p.setError(ex_);
            } else {
                _p.setRight(_right);
                LocalVariable lv_ = LocalVariable.newLocalVariable(_right.getStruct(),type_);
                _p.getRefParameters().addEntry(_conf.getClasses().getKeyWordValue(),new VariableWrapper(lv_));
            }
        }
    }

    private static Parameters okArgsExSw(ExecAbstractSwitchMethod _id, ExecFormattedRootBlock _classNameFound, Cache _cache, ContextEl _conf, StackCall _stackCall, Argument _value) {
        Parameters p_ = new Parameters();
        possibleCheck(_classNameFound, _cache, _conf, _stackCall, p_);
        if (p_.getError() != null) {
            return p_;
        }
        String c_ = _id.getImportedParamType();
        c_ = ExecInherits.quickFormat(_classNameFound, c_);
        Struct ex_ = checkObjectEx(c_, _value.getStruct().getClassName(_conf), _conf, _stackCall);
        if (ex_ != null) {
            p_.setError(ex_);
        }
        return p_;
    }

    private static void possibleCheck(ExecFormattedRootBlock _classNameFound, Cache _cache, ContextEl _conf, StackCall _stackCall, Parameters _p) {
        if (_cache != null) {
            _p.setCache(_cache);
            Struct err_ = _cache.checkCache(_classNameFound, _conf, _stackCall);
            if (err_ != null) {
                _p.setError(err_);
            }
        }
    }

    public static AbstractWrapper getWrap(AbstractWrapper _w) {
        if (_w == null) {
            return new VariableWrapper(LocalVariable.newLocalVariable(NullStruct.NULL_VALUE,""));
        }
        return _w;
    }
    public static Argument getArgValue(AbstractWrapper _w, ContextEl _context, StackCall _stackCall) {
        return new Argument(getValue(_w, _context, _stackCall));
    }
    public static Struct getValue(AbstractWrapper _w, ContextEl _context, StackCall _stackCall) {
        if (_w == null) {
            return NullStruct.NULL_VALUE;
        }
        return Argument.getNull(_w.getValue(_stackCall, _context));
    }
    public static StringBuilder countDiff(int _argsCount, int _paramsCount) {
        StringBuilder mess_ = new StringBuilder();
        mess_.append(_argsCount);
        mess_.append("!=");
        mess_.append(_paramsCount);
        return mess_;
    }

    private static ParametersTypes fetchParamTypes(ExecNamedFunctionBlock _id, ExecFormattedRootBlock _classNameFound) {
        ParametersTypes parametersTypes_ = new ParametersTypes();
        StringList paramsAll_ = new StringList();
        StringList namesAll_ = new StringList();
        if (_id == null) {
            parametersTypes_.setNamesAll(namesAll_);
            parametersTypes_.setTypesAll(paramsAll_);
            return parametersTypes_;
        }
        int i_ = 0;
        for (String c: _id.getImportedParametersTypes()) {
            paramsAll_.add(varType(c,_classNameFound,_id,i_));
            namesAll_.add(_id.getParametersName(i_));
            i_++;
        }
        parametersTypes_.setNamesAll(namesAll_);
        parametersTypes_.setTypesAll(paramsAll_);
        return parametersTypes_;
    }

    public static void wrapAndCallDirect(ArgumentListCall _in,ExecTypeFunction _pair, CustList<Argument> _firstArgs, Argument _right,ExecFormattedRootBlock _classFormat) {
        ExecNamedFunctionBlock fct_ = _pair.getFct();
        if (fct_ == null) {
            return;
        }
        int i_ = 0;
        for (String c: fct_.getImportedParametersTypes()) {
            if (fct_.getParametersRef(i_)) {
                Struct struct_ = _firstArgs.get(i_).getStruct();
                LocalVariable local_ = LocalVariable.newLocalVariable(struct_, varType(c,_classFormat,fct_,i_));
                ReflectVariableWrapper v_ = new ReflectVariableWrapper(local_);
//                out_.getWrappers().add(v_);
                _in.getArgumentWrappers().add(new ArgumentWrapper(_firstArgs.get(i_),v_));
            } else {
//                out_.getArguments().add(_firstArgs.get(i_));
                _in.getArgumentWrappers().add(new ArgumentWrapper(_firstArgs.get(i_),null));
            }
            i_++;
        }
        _in.setRight(_right);
    }

    private static String varType(String _c,ExecFormattedRootBlock _classFormat, ExecNamedFunctionBlock _fct, int _i) {
        String c_ = ExecInherits.quickFormat(_classFormat, _c);
        if (_i + 1 == _fct.getImportedParametersTypes().size() && _fct.isVarargs()) {
            return StringExpUtil.getPrettyArrayType(c_);
        }
        return c_;
    }
    public static Parameters wrapAndCall(ExecTypeFunction _pair, ExecFormattedRootBlock _formatted, Argument _previous, ContextEl _conf, StackCall _stackCall, ArgumentListCall _argList) {
        ExecNamedFunctionBlock fct_ = _pair.getFct();
        Parameters p_ = okArgsSet(fct_,_formatted,null,_argList,_conf,_stackCall);
        if (p_.getError() == null) {
            _stackCall.setCallingState(new CustomFoundMethod(_previous,_formatted, _pair, p_));
        }
        return p_;
    }

    public static Struct getElement(Struct _struct, Struct _index, ContextEl _conf, StackCall _stackCall) {
        LgNames stds_ = _conf.getStandards();
        if (!(_struct instanceof ArrayStruct)) {
            return procNoArr(_struct, _conf, _stackCall);
        }
        if (_index == NullStruct.NULL_VALUE) {
            String npe_ = stds_.getContent().getCoreNames().getAliasNullPe();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, npe_, _stackCall)));
            return NullStruct.NULL_VALUE;
        }
        if (!(_index instanceof NumberStruct)) {
            String cast_ = stds_.getContent().getCoreNames().getAliasCastType();
            String type_ = _struct.getClassName(_conf);
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, type_, cast_, _stackCall)));
            return NullStruct.NULL_VALUE;
        }
        ArrayStruct arr_ = (ArrayStruct) _struct;
        int index_ = NumParsers.convertToNumber(_index).intStruct();
        if (index_ < 0 || index_ >= arr_.getLength()) {
            String cast_ = stds_.getContent().getCoreNames().getAliasBadIndex();
            StringBuilder mess_ = getIndexMessage(_index, arr_);
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, mess_.toString(), cast_, _stackCall)));
            return NullStruct.NULL_VALUE;
        }
        Struct elt_ = arr_.get(index_);
        _stackCall.getInitializingTypeInfos().addSensibleField(arr_, elt_);
        return elt_;
    }

    public static Struct getRange(Struct _struct, Struct _index, ContextEl _conf, StackCall _stackCall) {
        LgNames stds_ = _conf.getStandards();
        if (!(_struct instanceof ArrayStruct)) {
            return procNoArr(_struct, _conf, _stackCall);
        }
        if (_index == NullStruct.NULL_VALUE) {
            String npe_ = stds_.getContent().getCoreNames().getAliasNullPe();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, npe_, _stackCall)));
            return NullStruct.NULL_VALUE;
        }
        if (!(_index instanceof RangeStruct)) {
            String cast_ = stds_.getContent().getCoreNames().getAliasCastType();
            String type_ = _struct.getClassName(_conf);
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, type_, cast_, _stackCall)));
            return NullStruct.NULL_VALUE;
        }
        RangeStruct ind_ = (RangeStruct) _index;
        ArrayStruct arr_ = (ArrayStruct) _struct;
        return getRange(_index, _conf, _stackCall, ind_, arr_);
    }

    private static Struct procNoArr(Struct _struct, ContextEl _conf, StackCall _stackCall) {
        LgNames stds_ = _conf.getStandards();
        if (_struct == NullStruct.NULL_VALUE) {
            String npe_ = stds_.getContent().getCoreNames().getAliasNullPe();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, npe_, _stackCall)));
            return NullStruct.NULL_VALUE;
        }
        String cast_ = stds_.getContent().getCoreNames().getAliasCastType();
        String type_ = Argument.getNull(_struct).getClassName(_conf);
        _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, type_, cast_, _stackCall)));
        return NullStruct.NULL_VALUE;
    }

    private static WithoutParentIdStruct getRange(Struct _index, ContextEl _conf, StackCall _stackCall, RangeStruct _ind, ArrayStruct _arr) {
        LgNames stds_ = _conf.getStandards();
        if (_ind.isUnlimited()) {
            if (_ind.getLower() > _arr.getLength()) {
                String cast_ = stds_.getContent().getCoreNames().getAliasBadIndex();
                StringBuilder mess_ = getIndexMessage(_index, _arr);
                _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, mess_.toString(), cast_, _stackCall)));
                return NullStruct.NULL_VALUE;
            }
        } else {
            if (_ind.getUpper() > _arr.getLength()) {
                String cast_ = stds_.getContent().getCoreNames().getAliasBadIndex();
                StringBuilder mess_ = getIndexMessage(_index, _arr);
                _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, mess_.toString(), cast_, _stackCall)));
                return NullStruct.NULL_VALUE;
            }
        }
        long lower_ = _ind.getLower();
        long step_ = _ind.getStep();
        long upper_;
        if (_ind.isUnlimited()) {
            upper_ = _arr.getLength();
        } else {
            upper_ = _ind.getUpper();
        }
        if (step_ < 0){
            int count_ = 0;
            long i_ = upper_-1L;
            while (i_ >= lower_) {
                count_++;
                i_ += step_;
            }
            ArrayStruct sub_ = new ArrayStruct(count_, _arr.getClassName());
            int insert_ = 0;
            i_ = upper_-1L;
            while (i_ >= lower_) {
                sub_.set(insert_, _arr.get((int) i_));
                insert_++;
                i_ += step_;
            }
            addSensible(_arr, _stackCall,sub_);
            return sub_;
        }
        int count_ = 0;
        long i_ = lower_;
        while (i_ < upper_) {
            count_++;
            i_ += step_;
        }
        ArrayStruct sub_ = new ArrayStruct(count_, _arr.getClassName());
        int insert_ = 0;
        i_ = lower_;
        while (i_ < upper_) {
            sub_.set(insert_, _arr.get((int) i_));
            insert_++;
            i_ += step_;
        }
        addSensible(_arr, _stackCall,sub_);
        return sub_;
    }

    private static void addSensible(Struct _struct, StackCall _stackCall, ArrayStruct _elt) {
        for (Struct s: _elt.list()){
            _stackCall.getInitializingTypeInfos().addSensibleField(_struct, s);
        }
    }

    public static void setElement(Struct _struct, Struct _index, Struct _value, ContextEl _conf, StackCall _stackCall) {
        LgNames stds_ = _conf.getStandards();
        if (_struct == NullStruct.NULL_VALUE) {
            String npe_ = stds_.getContent().getCoreNames().getAliasNullPe();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, npe_, _stackCall)));
            return;
        }
        if (!(_struct instanceof ArrayStruct)) {
            String cast_ = stds_.getContent().getCoreNames().getAliasCastType();
            String type_ = Argument.getNull(_struct).getClassName(_conf);
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, type_, cast_, _stackCall)));
            return;
        }
        if (_index == NullStruct.NULL_VALUE) {
            String npe_ = stds_.getContent().getCoreNames().getAliasNullPe();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, npe_, _stackCall)));
            return;
        }
        if (!(_index instanceof NumberStruct)) {
            String cast_ = stds_.getContent().getCoreNames().getAliasCastType();
            String type_ = _struct.getClassName(_conf);
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, type_, cast_, _stackCall)));
            return;
        }
        ArrayStruct arr_ = (ArrayStruct) _struct;
        int index_ = NumParsers.convertToNumber(_index).intStruct();
        if (index_ < 0 || index_ >= arr_.getLength()) {
            String cast_ = stds_.getContent().getCoreNames().getAliasBadIndex();
            StringBuilder mess_ = getIndexMessage(_index, arr_);
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, mess_.toString(), cast_, _stackCall)));
            return;
        }
        Struct value_ = Argument.getNull(_value);
        ErrorType errorType_ = safeObjectArr(value_.getClassName(_conf), _conf, arr_);
        if (errorType_ == ErrorType.NPE) {
            String npe_ = stds_.getContent().getCoreNames().getAliasNullPe();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, npe_, _stackCall)));
            return;
        }
        if (errorType_ != ErrorType.NOTHING) {
            String cast_ = stds_.getContent().getCoreNames().getAliasStore();
            StringBuilder mess_ = buildStoreError(value_, _conf, arr_);
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, mess_.toString(), cast_, _stackCall)));
            return;
        }
        if (_stackCall.getInitializingTypeInfos().isContainedSensibleFields(arr_)) {
            _stackCall.getInitializingTypeInfos().failInitEnums();
            return;
        }
        arr_.set(index_, value_);
    }

    private static ErrorType safeObjectArr(String _value, ContextEl _context, ArrayStruct _arr) {
        String arrType_ = _arr.getClassName();
        String param_ = StringUtil.nullToEmpty(StringExpUtil.getQuickComponentType(arrType_));
        return ExecInherits.safeObject(param_,_value,_context);
    }

    private static StringBuilder buildStoreError(Struct _value, ContextEl _context, ArrayStruct _arr) {
        String arrType_ = _arr.getClassName();
        String param_ = StringUtil.nullToEmpty(StringExpUtil.getQuickComponentType(arrType_));
        String arg_ = _value.getClassName(_context);
        StringBuilder mess_ = new StringBuilder();
        mess_.append(arg_);
        mess_.append("!=");
        mess_.append(param_);
        return mess_;
    }

    private static StringBuilder getIndexMessage(Struct _index, ArrayStruct _arr) {
        int index_ = NumParsers.convertToNumber(_index).intStruct();
        StringBuilder mess_ = new StringBuilder();
        if (index_ < 0) {
            mess_.append(index_);
            mess_.append("<0");
        } else {
            mess_.append(index_);
            mess_.append(">=");
            mess_.append(_arr.getLength());
        }
        return mess_;
    }
    public static String getMadeVarTypes(String _className, StringList _classNames,ContextEl _context) {
        String type_ = StringExpUtil.getIdFromAllTypes(_className);
        String fct_ = _context.getStandards().getContent().getReflect().getAliasFct();
        if (StringUtil.quickEq(type_, fct_)) {
            return fctMadeVarTypes(_classNames, fct_);
        }
        return defMadeVarTypes(_classNames, _context, type_);
    }

    private static String defMadeVarTypes(StringList _classNames, ContextEl _context, String _type) {
        GeneType root_ = _context.getClassBody(_type);
        if (root_ == null) {
            return null;
        }
        String pref_ = root_.getGenericString();
        StringMap<String> varTypes_ = new StringMap<String>();
        CustList<ExecTypeVar> typeVar_ = root_.getParamTypesMapValues();
        int len_ = typeVar_.size();
        if (len_ != _classNames.size()) {
            return null;
        }
        int i_ = IndexConstants.FIRST_INDEX;
        for (ExecTypeVar t: typeVar_) {
            String arg_ = _classNames.get(i_);
            if (arg_.contains(PREFIX_VAR_TYPE)) {
                return null;
            }
            arg_ = extractRef(arg_);

            varTypes_.addEntry(t.getName(), arg_);
            i_++;
        }
        String formatted_ = StringExpUtil.getQuickFormattedType(pref_, varTypes_);
        for (int i = 0; i < len_; i++) {
            ExecTypeVar t = typeVar_.get(i);
            for (String b:t.getConstraints()) {
                String arg_ = _classNames.get(i);
                if (arg_.startsWith("?") || arg_.startsWith("!")) {
                    continue;
                }
                arg_ = extractRef(arg_);
                String param_ = ExecInherits.format(root_,formatted_, b);
                if (!ExecInherits.isCorrectExecute(arg_,param_,_context)) {
                    return null;
                }
            }
        }
        return formatted_;
    }

    private static String extractRef(String _part) {
        if (_part.startsWith("~")) {
            return _part.substring(1);
        }
        return _part;
    }
    private static String fctMadeVarTypes(StringList _classNames, String _fct) {
        if (_classNames.isEmpty()) {
            return null;
        }
        StringList parts_ = new StringList();
        for (String s: _classNames) {
            parts_.add(extractWc(s));
        }
        StringBuilder str_ = new StringBuilder(_fct);
        str_.append(StringExpUtil.TEMPLATE_BEGIN);
        str_.append(StringUtil.join(parts_, StringExpUtil.TEMPLATE_SEP));
        str_.append(StringExpUtil.TEMPLATE_END);
        return str_.toString();
    }

    private static String extractWc(String _part) {
        if (StringUtil.quickEq(_part, StringExpUtil.SUB_TYPE)) {
            return _part;
        }
        if (_part.startsWith(StringExpUtil.SUB_TYPE)) {
            return _part.substring(StringExpUtil.SUB_TYPE.length());
        }
        if (_part.startsWith(StringExpUtil.SUP_TYPE)) {
            return _part.substring(StringExpUtil.SUP_TYPE.length());
        }
        return _part;
    }

    public static Argument getIndexLoop(ContextEl _context, ExecVariableContent _varCont, StackCall _stackCall) {
        return getIndexLoop(_context, _varCont, _stackCall.getLastPage().getCache(), _stackCall.getLastPage().getVars(), _stackCall);
    }
    public static Argument getIndexLoop(ContextEl _context, ExecVariableContent _varCont, Cache _cache, StringMap<LoopVariable> _vars, StackCall _stackCall) {
        return getIndexLoop(_context, _varCont.getVariableName(), _varCont.getDeep(), _cache, _vars, _stackCall);
    }
    public static Argument getIndexLoop(ContextEl _context, String _val, int _deep, Cache _cache, StringMap<LoopVariable> _vars, StackCall _stackCall) {
        LgNames stds_ = _context.getStandards();
        if (_cache != null) {
            LoopVariable loopVar_ = _cache.getLoopVar(_val,_deep);
            if (loopVar_ != null) {
                byte cast_ = ClassArgumentMatching.getPrimitiveCast(loopVar_.getIndexClassName(), _context.getStandards().getPrimTypes());
                LongStruct str_ = new LongStruct(loopVar_.getIndex());
                Struct value_ = NumParsers.convertToInt(cast_, str_);
                return new Argument(value_);
            }
        }
        LoopVariable locVar_ = _vars.getVal(_val);
        if (locVar_ == null) {
            String npe_ = stds_.getContent().getCoreNames().getAliasNullPe();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_context, npe_, _stackCall)));
            return new Argument(new IntStruct(0));
        }
        byte cast_ = ClassArgumentMatching.getPrimitiveCast(locVar_.getIndexClassName(), _context.getStandards().getPrimTypes());
        LongStruct str_ = new LongStruct(locVar_.getIndex());
        Struct value_ = NumParsers.convertToInt(cast_, str_);
        return new Argument(value_);
    }

    public static void incrIndexLoop(ContextEl _context, String _val, int _deep, Cache _cache, StringMap<LoopVariable> _vars, StackCall _stackCall) {
        if (_context.callsOrException(_stackCall)) {
            return;
        }
        if (_cache != null) {
            LoopVariable loopVar_ = _cache.getLoopVar(_val, _deep);
            if (loopVar_ != null) {
                loopVar_.setIndex(loopVar_.getIndex() + 1);
                return;
            }
        }
        LoopVariable locVar_ = _vars.getVal(_val);
        LgNames stds_ = _context.getStandards();
        if (locVar_ == null) {
            String npe_ = stds_.getContent().getCoreNames().getAliasNullPe();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_context, npe_, _stackCall)));
            return;
        }
        locVar_.setIndex(locVar_.getIndex() + 1);
    }

    public static Argument getWrapValue(ContextEl _context, String _val, int _deep, Cache _cache, StringMap<AbstractWrapper> _refParams, StackCall _stackCall) {
        AbstractWrapper wrapper_ = getWrapper(_val, _deep, _cache, _refParams);
        return new Argument(getValue(wrapper_, _context, _stackCall));
    }

    public static AbstractWrapper getWrapper(ExecVariableContent _varCont, StackCall _stack) {
        AbstractPageEl ip_ = _stack.getLastPage();
        return getWrapper(_varCont, ip_.getCache(), _stack.getLastPage().getRefParams());
    }

    public static AbstractWrapper getWrapper(ExecVariableContent _varCont, Cache _cache, StringMap<AbstractWrapper> _refParams) {
        return getWrapper(_varCont.getVariableName(),_varCont.getDeep(), _cache, _refParams);
    }
    public static AbstractWrapper getWrapper(String _val, int _deep, Cache _cache, StringMap<AbstractWrapper> _refParams) {
        if (_cache != null) {
            AbstractWrapper wr_ = _cache.getLocalWrapper(_val, _deep);
            if (wr_ != null) {
                return wr_;
            }
        }
        return _refParams.getVal(_val);
    }

    public static Argument getValueVar(String _val, StringMap<LocalVariable> _valueVars, ContextEl _context, StackCall _stackCall) {
        LocalVariable locVar_ = _valueVars.getVal(_val);
        if (locVar_ == null) {
            LgNames stds_ = _context.getStandards();
            String npe_ = stds_.getContent().getCoreNames().getAliasNullPe();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_context, npe_, _stackCall)));
            return new Argument();
        }
        return new Argument(locVar_.getStruct());
    }

    public static Argument setWrapValue(ContextEl _context, ExecVariableContent _varCont, Argument _value, StackCall _stackCall) {
        return setWrapValue(_context,_varCont,_value, _stackCall.getLastPage().getCache(), _stackCall.getLastPage().getRefParams(), _stackCall);
    }
    public static Argument setWrapValue(ContextEl _context, ExecVariableContent _varCont, Argument _value, Cache _cache, StringMap<AbstractWrapper> _vars, StackCall _stackCall) {
        return setWrapValue(_context,_varCont.getVariableName(),_value,_varCont.getDeep(), _cache, _vars, _stackCall);
    }
    public static Argument setWrapValue(ContextEl _context, String _val, Argument _value, int _deep, Cache _cache, StringMap<AbstractWrapper> _refParams, StackCall _stackCall) {
        if (_context.callsOrException(_stackCall)) {
            return new Argument();
        }
        if (_cache != null) {
            AbstractWrapper wr_ = _cache.getLocalWrapper(_val, _deep);
            if (wr_ != null) {
                wr_.setValue(_stackCall, _context,_value);
                return _value;
            }
        }
        AbstractWrapper wr_ = _refParams.getVal(_val);
        return trySetArgument(_context,_value,wr_, _stackCall);
    }

    public static Argument checkSet(ContextEl _conf, LocalVariable _loc, Argument _right, StackCall _stackCall) {
        String formattedClassVar_ = _loc.getClassName();
        if (!checkQuick(formattedClassVar_, _right.getStruct().getClassName(_conf), _conf, _stackCall)) {
            return Argument.createVoid();
        }
        _loc.setStruct(_right.getStruct());
        return _right;
    }

    public static Argument getField(FieldMetaInfo _meta, Argument _previous, ContextEl _conf, StackCall _stackCall) {
        String baseClass_ = _meta.getFormatted().getFormatted();
        baseClass_ = StringExpUtil.getIdFromAllTypes(baseClass_);
        String fieldName_ = _meta.getName();
        boolean isStaticField_ = _meta.isStaticField();
        String type_ = _meta.getType();

        ClassField fieldId_ = new ClassField(baseClass_, fieldName_);
        if (isStaticField_) {
            return getStaticField(_conf.getExiting(),_meta.getFormatted().getRootBlock(), type_, _conf, _stackCall, fieldId_);
        }
        return getInstanceField(_previous, _conf, _stackCall, fieldId_);
    }

    public static Argument getSafeInstanceField(int _anc,Argument _previous, ContextEl _conf, StackCall _stackCall, ClassField _fieldId) {
        Argument prev_ = new Argument(ExecTemplates.getParent(_anc, _previous.getStruct(), _conf, _stackCall));
        if (_conf.callsOrException(_stackCall)) {
            return Argument.createVoid();
        }
        return getInstanceField(prev_, _conf, _stackCall, _fieldId);
    }
    public static Argument getInstanceField(Argument _previous, ContextEl _conf, StackCall _stackCall, ClassField _fieldId) {
        LgNames stds_ = _conf.getStandards();
        String cast_;
        cast_ = stds_.getContent().getCoreNames().getAliasCastType();
        String className_ = _fieldId.getClassName();
        Struct previous_ = _previous.getStruct();
        String argClassName_ = previous_.getClassName(_conf);
        if (!(previous_ instanceof FieldableStruct)) {
            if (previous_ == NullStruct.NULL_VALUE) {
                String npe_;
                npe_ = stds_.getContent().getCoreNames().getAliasNullPe();
                _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, npe_, _stackCall)));
                return Argument.createVoid();
            }
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, getBadCastMessage(className_, argClassName_), cast_, _stackCall)));
            return _previous;
        }
        ClassFieldStruct entry_ = ((FieldableStruct) previous_).getEntryStruct(_fieldId);
        if (entry_ == null) {
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, getBadCastMessage(className_, argClassName_), cast_, _stackCall)));
            return _previous;
        }
        Struct struct_ = entry_.getStruct();
        _stackCall.getInitializingTypeInfos().addSensibleField(previous_, struct_);
        return new Argument(struct_);
    }

    public static Argument getStaticField(AbstractExiting _exit,GeneType _init, String _ret, ContextEl _conf, StackCall _stackCall, ClassField _fieldId) {
        Classes classes_ = _conf.getClasses();
        if (_exit.hasToExit(_stackCall, _init)) {
            return Argument.createVoid();
        }
        Struct struct_ = classes_.getStaticField(_fieldId,_ret, _conf);
        String className_ = _fieldId.getClassName();
        _stackCall.getInitializingTypeInfos().addSensibleField(className_, struct_, _stackCall);
        return new Argument(struct_);
    }

    public static Argument setField(FieldMetaInfo _meta, Argument _previous, Argument _right, ContextEl _conf, StackCall _stackCall) {
        ExecRootBlock declaring_ = _meta.getDeclaring();
        if (declaring_ == null) {
            LgNames stds_ = _conf.getStandards();
            String npe_;
            npe_ = stds_.getContent().getCoreNames().getAliasIllegalArg();
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, npe_, _stackCall)));
            return Argument.createVoid();
        }
        String baseClass_ = _meta.getFormatted().getFormatted();
        baseClass_ = StringExpUtil.getIdFromAllTypes(baseClass_);
        String fieldName_ = _meta.getName();
        boolean isStaticField_ = _meta.isStaticField();
        boolean isFinalField_ = _meta.isFinalField();
        String type_ = _meta.getType();
        ClassField fieldId_ = new ClassField(baseClass_, fieldName_);
        if (isStaticField_) {
            LgNames stds_ = _conf.getStandards();
            if (isFinalField_) {
                String npe_;
                npe_ = stds_.getContent().getCoreNames().getAliasIllegalArg();
                _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, npe_, _stackCall)));
                return Argument.createVoid();
            }
            return setStaticField(_conf.getExiting(), declaring_, type_, _right, _conf, _stackCall, fieldId_);
        }
        return setInstanceField(_previous, _right, _conf, _stackCall, fieldId_, new ExecTypeReturn(declaring_, type_));
    }

    public static Argument setSafeInstanceField(int _anc,Argument _previous, Argument _right, ContextEl _conf, StackCall _stackCall, ClassField _fieldId, ExecTypeReturn _ex) {
        Argument prev_ = new Argument(ExecTemplates.getParent(_anc, _previous.getStruct(), _conf, _stackCall));
        if (_conf.callsOrException(_stackCall)) {
            return Argument.createVoid();
        }
        return setInstanceField(prev_, _right, _conf, _stackCall, _fieldId, _ex);
    }
    public static Argument setInstanceField(Argument _previous, Argument _right, ContextEl _conf, StackCall _stackCall, ClassField _fieldId, ExecTypeReturn _ex) {
        String className_ = _fieldId.getClassName();
        LgNames stds_ = _conf.getStandards();
        Struct previous_ = _previous.getStruct();
        String argClassName_ = previous_.getClassName(_conf);
        String cast_ = stds_.getContent().getCoreNames().getAliasCastType();
        if (!(previous_ instanceof FieldableStruct)) {
            if (previous_ == NullStruct.NULL_VALUE) {
                String npe_ = stds_.getContent().getCoreNames().getAliasNullPe();
                _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, npe_, _stackCall)));
                return Argument.createVoid();
            }
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, getBadCastMessage(className_, argClassName_), cast_, _stackCall)));
            return Argument.createVoid();
        }
        ClassFieldStruct entry_ = ((FieldableStruct) previous_).getEntryStruct(_fieldId);
        if (entry_ == null) {
            _stackCall.setCallingState(new CustomFoundExc(new ErrorStruct(_conf, getBadCastMessage(className_, argClassName_), cast_, _stackCall)));
            return Argument.createVoid();
        }
        String fieldType_ = formatType(_conf, _ex.getRootBlock(), _ex.getReturnType(),argClassName_);
        if (!checkQuick(fieldType_, _right.getStruct().getClassName(_conf), _conf, _stackCall)) {
            return Argument.createVoid();
        }
        if (_stackCall.getInitializingTypeInfos().isContainedSensibleFields(previous_)) {
            _stackCall.getInitializingTypeInfos().failInitEnums();
            return _right;
        }
        entry_.setStruct(_right.getStruct());
        return _right;
    }

    public static Argument setStaticField(AbstractExiting _exit,GeneType _init, String _returnType, Argument _right, ContextEl _conf, StackCall _stackCall, ClassField _fieldId) {
        Classes classes_ = _conf.getClasses();
        if (_exit.hasToExit(_stackCall, _init)) {
            return Argument.createVoid();
        }
        if (!checkQuick(_returnType, _right.getStruct().getClassName(_conf), _conf, _stackCall)) {
            return Argument.createVoid();
        }
        String className_ = _fieldId.getClassName();
        if (_stackCall.getInitializingTypeInfos().isSensibleField(className_, _stackCall)) {
            _stackCall.getInitializingTypeInfos().failInitEnums();
            return _right;
        }
        NumParsers.getStaticFieldMap(className_, classes_.getStaticFields()).set(_fieldId.getFieldName(), _right.getStruct());
        return _right;
    }

    public static String formatType(ContextEl _conf, ExecRootBlock _rootBlock, String _lastType, String _cl) {
        String base_ = _rootBlock.getFullName();
        String clGen_ = ExecInherits.getFullObject(_cl, base_, _conf);
        return ExecInherits.quickFormat(_rootBlock,clGen_, _lastType);
    }
    public static Argument trySetArgument(ContextEl _conf, Argument _res, ArgumentsPair _pair, StackCall _stackCall) {
        AbstractWrapper wrapper_ = _pair.getWrapper();
        return trySetArgument(_conf, _res, wrapper_, _stackCall);
    }

    private static Argument trySetArgument(ContextEl _conf, Argument _res, AbstractWrapper _wrapper, StackCall _stackCall) {
        if (_wrapper == null || _conf.callsOrException(_stackCall)) {
            return _res;
        }
        _wrapper.setValue(_stackCall, _conf, _res);
        return _res;
    }
}
