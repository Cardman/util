package code.expressionlanguage.analyze.inherits;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.blocks.AnnotationBlock;
import code.expressionlanguage.analyze.blocks.RootBlock;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.analyze.types.AnaTypeUtil;
import code.expressionlanguage.analyze.util.AnaFormattedRootBlock;
import code.expressionlanguage.analyze.util.ContextUtil;
import code.expressionlanguage.analyze.util.TypeVar;
import code.expressionlanguage.common.*;
import code.expressionlanguage.functionid.Identifiable;
import code.expressionlanguage.stds.PrimitiveType;
import code.expressionlanguage.stds.StandardType;
import code.util.CustList;
import code.util.Ints;
import code.util.StringList;
import code.util.StringMap;
import code.util.core.IndexConstants;
import code.util.core.StringUtil;

public final class AnaInherits {
    public static final String PREFIX_VAR_TYPE = "#";
    public static final String SUB_TYPE = "?";
    public static final String SUP_TYPE = "!";
    public static final char SEP_CLASS_CHAR = '.';

    private AnaInherits() {
    }

    public static String wildCardFormatReturn(String _first, String _second, AnalyzedPageEl _page) {
        if (!_second.contains(PREFIX_VAR_TYPE)) {
            return _second;
        }
        DimComp dc_ = StringExpUtil.getQuickComponentBaseType(_second);
        StringList types_ = StringExpUtil.getAllTypes(_first);
        String className_ = StringExpUtil.getQuickComponentBaseType(types_.first()).getComponent();
        AnaGeneType root_ = _page.getAnaGeneType(className_);
        CustList<TypeVar> typeVar_ = ContextUtil.getParamTypesMapValues(root_);
        String objType_ = _page.getAliasObject();
        if (dc_.getComponent().startsWith(PREFIX_VAR_TYPE)) {
            int arr_ = dc_.getDim();
            String name_ = _second.substring(PREFIX_VAR_TYPE.length()+arr_);

            int index_ = -1;
            for (TypeVar t: typeVar_) {
                index_++;
                if (StringUtil.quickEq(t.getName(), name_)) {
                    String formatted_ = types_.get(index_+1);
                    //return type, field getting
                    if (StringUtil.quickEq(formatted_, SUB_TYPE)) {
                        return StringExpUtil.getPrettyArrayType(objType_,arr_);
                    }
                    if (formatted_.startsWith(SUB_TYPE)) {
                        return StringExpUtil.getPrettyArrayType(formatted_.substring(SUB_TYPE.length()),arr_);
                    }
                    if (formatted_.startsWith(SUP_TYPE)) {
                        return StringExpUtil.getPrettyArrayType(objType_,arr_);
                    }
                    return StringExpUtil.getPrettyArrayType(formatted_,arr_);
                }
            }
            return objType_;
        }
        if (typeVar_.size() != types_.size() - 1){
            return objType_;
        }
        StringMap<String> varTypes_ = new StringMap<String>();
        int i_ = IndexConstants.FIRST_INDEX;
        for (TypeVar t: typeVar_) {
            i_++;
            String arg_ = types_.get(i_);
            varTypes_.put(t.getName(), arg_);
        }
        return StringExpUtil.getWildCardFormattedTypeReturn(_second, varTypes_);
    }

    public static StringList wildCardFormatParams(String _first, Identifiable _params, AnalyzedPageEl _page) {
        StringList params_ = new StringList();
        int nbParams_ = _params.getParametersTypesLength();
        for (int i = 0; i < nbParams_; i++) {
            params_.add(wildCardFormatParam(_first,_params.getParametersType(i),_page));
        }
        return params_;
    }
    public static String wildCardFormatParam(String _first, String _second, AnalyzedPageEl _page) {
        if (!_second.contains(PREFIX_VAR_TYPE)) {
            return _second;
        }
        DimComp dc_ = StringExpUtil.getQuickComponentBaseType(_second);
        StringList types_ = StringExpUtil.getAllTypes(_first);
        String className_ = StringExpUtil.getQuickComponentBaseType(types_.first()).getComponent();
        AnaGeneType root_ = _page.getAnaGeneType(className_);
        CustList<TypeVar> typeVar_ = ContextUtil.getParamTypesMapValues(root_);
        String objType_ = _page.getAliasObject();
        if (dc_.getComponent().startsWith(PREFIX_VAR_TYPE)) {
            int arr_ = dc_.getDim();
            String name_ = _second.substring(PREFIX_VAR_TYPE.length()+arr_);

            int index_ = -1;
            for (TypeVar t: typeVar_) {
                index_++;
                if (StringUtil.quickEq(t.getName(), name_)) {
                    String formatted_ = types_.get(index_+1);
                    //parameters, field affectation
                    if (formatted_.startsWith(SUB_TYPE)) {
                        return "";
                    }
                    if (formatted_.startsWith(SUP_TYPE)) {
                        return StringExpUtil.getPrettyArrayType(formatted_.substring(SUP_TYPE.length()),arr_);
                    }
                    return StringExpUtil.getPrettyArrayType(formatted_,arr_);
                }
            }
            return "";
        }
        if (typeVar_.size() != types_.size() - 1){
            return "";
        }
        StringMap<String> varTypes_ = new StringMap<String>();
        int i_ = IndexConstants.FIRST_INDEX;
        for (TypeVar t: typeVar_) {
            i_++;
            String arg_ = types_.get(i_);
            varTypes_.put(t.getName(), arg_);
        }
        return StringExpUtil.getWildCardFormattedTypeParam(objType_,_second, varTypes_);
    }

    public static boolean isReturnCorrect(String _p, String _a, StringMap<StringList> _mapping, AnalyzedPageEl _page) {
        if (AnaTypeUtil.isPrimitive(_p, _page)) {
            if (!AnaTypeUtil.isPrimitive(_a, _page)) {
                return false;
            }
        }
        String void_ = _page.getAliasVoid();
        if (StringUtil.quickEq(_p, void_)) {
            return StringUtil.quickEq(_a, void_);
        }
        if (StringUtil.quickEq(_a, void_)) {
            return false;
        }
        Mapping map_ = new Mapping();
        map_.setArg(_a);
        map_.setParam(_p);
        map_.setMapping(_mapping);
        return isCorrectOrNumbers(map_, _page);
    }

    public static boolean isCorrectOrNumbers(Mapping _m, AnalyzedPageEl _page) {
        AnaClassArgumentMatching a_ = _m.getArg();
        AnaClassArgumentMatching p_ = _m.getParam();
        if (_m.getParam().isVariable()) {
            return false;
        }
        if (_m.getArg().isVariable()) {
            return !AnaTypeUtil.isPrimitive(p_, _page);
        }
        if (AnaTypeUtil.isPrimitive(p_, _page)) {
            Mapping m_ = new Mapping();
            m_.setArg(AnaTypeUtil.toPrimitive(a_, _page.getPrimitiveTypes()));
            m_.setParam(p_);
            m_.setMapping(_m.getMapping());
            return isCorrect(m_, _page);
        }
        return isCorrect(_m, _page);
    }

    public static boolean isCorrect(Mapping _m, AnalyzedPageEl _page) {
        AnaClassArgumentMatching arg_ = _m.getArg();
        AnaClassArgumentMatching param_ = _m.getParam();
        StringMap<StringList> generalMapping_ = _m.getMapping();
        for (String p: param_.getNames()) {
            boolean ok_ = false;
            StringList names_ = arg_.getNames();
            for (String a: names_) {
                AbstractInheritProcess inh_ = new AnaInheritProcess(_page,generalMapping_);
                if (inh_.isCorrectExecute(a, p)) {
                    ok_ = true;
                    break;
                }
            }
            if (!ok_) {
                return false;
            }
        }
        return true;
    }

    public static String getGeneric(String _arg, String _param, Mapping _map, AnalyzedPageEl _page) {
        String objType_ = _page.getAliasObject();
        DimComp dArg_ = StringExpUtil.getQuickComponentBaseType(_arg);
        String baseArrayArg_ = dArg_.getComponent();
        String generic_ = "";
        if (baseArrayArg_.startsWith(PREFIX_VAR_TYPE)) {
            StringMap<StringList> mapping_ = _map.getMapping();
            for (String c: Mapping.getAllUpperBounds(mapping_,baseArrayArg_.substring(PREFIX_VAR_TYPE.length()), objType_)) {
                String arr_ = StringExpUtil.getPrettyArrayType(c,dArg_.getDim());
                String idCl_ = StringExpUtil.getIdFromAllTypes(arr_);
                String compo_ = StringExpUtil.getQuickComponentBaseType(idCl_).getComponent();
                AnaGeneType info_ = _page.getAnaGeneType(compo_);
                generic_ = getFullTypeByBases(info_,arr_, _param, _page);
                if (!generic_.isEmpty()) {
                    break;
                }
            }
        } else {
            String idCl_ = StringExpUtil.getIdFromAllTypes(_arg);
            String compo_ = StringExpUtil.getQuickComponentBaseType(idCl_).getComponent();
            AnaGeneType info_ = _page.getAnaGeneType(compo_);
            generic_ = getFullTypeByBases(info_,_arg, _param, _page);
        }
        return generic_;
    }

    static MappingPairs getSimpleMapping(String _arg, String _param, StringMap<StringList> _inherit, AnalyzedPageEl _page) {
        DimComp dArg_ = StringExpUtil.getQuickComponentBaseType(_arg);
        DimComp dParam_ = StringExpUtil.getQuickComponentBaseType(_param);
        String baseArrayParam_ = dParam_.getComponent();
        Mapping map_ = new Mapping();
        map_.setMapping(_inherit);
        if (baseArrayParam_.startsWith(PREFIX_VAR_TYPE)) {
            if (_arg.isEmpty()) {
                return new MappingPairs();
            }
            return commonTypeVar(dArg_, dParam_, map_);
        }
        return getCommentMappingPairs(_arg, _param,_inherit, _page);
    }

    static MappingPairs commonTypeVar(DimComp _dArg, DimComp _dParam, Mapping _map) {
        String baseArrayParam_ = _dParam.getComponent();
        String baseArrayArg_ = _dArg.getComponent();
        if (!baseArrayArg_.startsWith(PREFIX_VAR_TYPE)) {
            return null;
        }
        if (_dArg.getDim() != _dParam.getDim()) {
            return null;
        }
        if (_map.inheritArgParam(baseArrayParam_.substring(1), baseArrayArg_.substring(1))) {
            return new MappingPairs();
        }
        return null;
    }

    static MappingPairs getCommentMappingPairs(String _arg, String _param, StringMap<StringList> _inherit, AnalyzedPageEl _page) {
        String idBaseArrayArg_ = StringExpUtil.getId(_arg);
        String idBaseArrayParam_ = StringExpUtil.getId(_param);
        String fct_ = _page.getAliasFct();
        if (StringUtil.quickEq(idBaseArrayArg_, fct_) || StringUtil.quickEq(idBaseArrayParam_, fct_)) {
            String obj_ = _page.getAliasObject();
            return FeedMappingTypePair.getMappingFctPairs(_arg,_param,fct_,obj_);
        }
        Mapping map_ = new Mapping();
        map_.setMapping(_inherit);
        String generic_ = getGeneric(_arg, _param, map_, _page);
        if (generic_.isEmpty()) {
            return null;
        }
        return FeedMappingTypePair.newMappingPairs(generic_, _param);
    }

    public static String getFullTypeByBases(AnaGeneType _typeSub, String _subType, String _superType, AnalyzedPageEl _page) {
        String idArg_ = StringExpUtil.getIdFromAllTypes(_subType);
        String idSuperType_ = StringExpUtil.getIdFromAllTypes(_superType);
        if (StringUtil.quickEq(idArg_,idSuperType_)) {
            return _subType;
        }
        DimComp dBaseParam_ = StringExpUtil.getQuickComponentBaseType(idSuperType_);
        int dim_ = dBaseParam_.getDim();
        String classParam_ = dBaseParam_.getComponent();
        DimComp dBaseArg_ = StringExpUtil.getQuickComponentBaseType(idArg_);
        String baseArr_ = dBaseArg_.getComponent();
        if (StringUtil.quickEq(classParam_, _page.getAliasObject())) {
            if (dBaseArg_.getDim() < dim_) {
                return "";
            }
            return _superType;
        }
        if (dBaseArg_.getDim() != dim_) {
            return "";
        }
        if (AnaTypeUtil.isPrimitive(baseArr_, _page)) {
            PrimitiveType pr_ = _page.getPrimitiveTypes().getVal(baseArr_);
            if (StringUtil.contains(pr_.getAllSuperType(_page), classParam_)) {
                return _superType;
            }
            return "";
        }
        if (StringUtil.quickEq(_subType, _page.getAliasVoid())) {
            return "";
        }
        if (StringUtil.quickEq(_superType, _page.getAliasVoid())) {
            return "";
        }
        String generic_ = getSuperGeneric(_typeSub, dim_, classParam_, _page);
        return format(_typeSub,_subType, generic_);
    }

    public static String getOverridingFullTypeByBases(String _subType, String _superType, AnalyzedPageEl _page) {
        AnaGeneType type_ = _page.getAnaGeneType(StringExpUtil.getIdFromAllTypes(_subType));
        String idArg_ = StringExpUtil.getIdFromAllTypes(_subType);
        String idSuperType_ = StringExpUtil.getIdFromAllTypes(_superType);
        if (StringUtil.quickEq(idArg_,idSuperType_)) {
            return _subType;
        }
        String generic_ = getSuperGeneric(type_, 0, idSuperType_, _page);
        return quickFormat(type_,_subType, generic_);
    }

    public static AnaFormattedRootBlock getFormattedOverridingFullTypeByBases(AnaFormattedRootBlock _subType, AnaGeneType _superType) {
        AnaFormattedRootBlock res_ = getOverridingFullTypeByBases(_subType.getRootBlock(), _superType);
        if (res_ == null) {
            return null;
        }
        return AnaFormattedRootBlock.quickFormat(_subType,res_);
    }
    public static AnaFormattedRootBlock getOverridingFullTypeByBases(RootBlock _subType, AnaGeneType _superType) {
        if (_subType == null) {
            return null;
        }
        if (_subType == _superType) {
            return new AnaFormattedRootBlock(_subType);
        }
        for (AnaFormattedRootBlock g: _subType.getAllGenericSuperTypesInfo()) {
            if (g.getRootBlock() == _superType) {
                return g;
            }
        }
        return null;
    }

    public static String getSuperGeneric(AnaGeneType _subType, int _dim, String _classParam, AnalyzedPageEl _page) {
        if (_subType == null) {
            return "";
        }
        String param_ = StringExpUtil.getIdFromAllTypes(_classParam);
        if (_subType instanceof AnnotationBlock) {
            if (StringUtil.quickEq(param_, _page.getAliasAnnotationType())) {
                return StringExpUtil.getPrettyArrayType(param_,_dim);
            }
        }
        String generic_ = generic(_subType, param_);
        if (generic_.isEmpty()) {
            return "";
        }
        return StringExpUtil.getPrettyArrayType(generic_,_dim);
    }

    static String generic(AnaGeneType _subType, String _param) {
        String generic_ = "";
        if (_subType instanceof RootBlock) {
            generic_ = new AnaLookingSuperTypes(((RootBlock) _subType).getAllGenericSuperTypesInfo()).find(_param);
        }
        if (_subType instanceof StandardType) {
            generic_ = new CommonLookingSuperTypes(((StandardType) _subType).getAllGenericSuperTypes()).find(_param);
        }
        return generic_;
    }

    public static String format(AnaFormattedRootBlock _root, String _second) {
        StringMap<String> varTypes_ = getVarTypes(_root);
        return StringExpUtil.getFormattedType(_second, varTypes_);
    }

    public static String format(AnaGeneType _root, String _first, String _second) {
        StringMap<String> varTypes_ = getVarTypes(_root,_first);
        return StringExpUtil.getFormattedType(_second, varTypes_);
    }

    public static String quickFormat(AnaFormattedRootBlock _root, String _second) {
        StringMap<String> varTypes_ = getVarTypes(_root);
        return StringExpUtil.getQuickFormattedType(_second, varTypes_);
    }
    public static String quickFormat(AnaGeneType _root, String _first, String _second) {
        StringMap<String> varTypes_ = getVarTypes(_root,_first);
        return StringExpUtil.getQuickFormattedType(_second, varTypes_);
    }

    public static StringMap<String> getVarTypes(AnaFormattedRootBlock _root) {
        return getVarTypes(_root.getRootBlock(),_root.getFormatted());
    }
    public static StringMap<String> getVarTypes(AnaGeneType _root, String _className) {
        StringMap<String> varTypes_ = new StringMap<String>();
        if (_root == null) {
            return varTypes_;
        }
        return StringExpUtil.getVarTypes(_root.getParamTypesValues(),_className);
    }

    public static boolean correctNbParameters(AnaGeneType _info, String _genericClass, AnalyzedPageEl _page) {
        //From analyze
        String idCl_ = StringExpUtil.getIdFromAllTypes(_genericClass);
        String compo_ = StringExpUtil.getQuickComponentBaseType(idCl_).getComponent();
        if (_info == null) {
            if (AnaTypeUtil.isPrimitive(compo_, _page)) {
                return true;
            }
            return StringUtil.quickEq(compo_, _page.getAliasVoid());
        }
        String fct_ = _page.getAliasFct();
        Ints rep_ = _info.getTypeVarCounts();
        return StringExpUtil.commonCorrectType(_genericClass, fct_, rep_);
    }

    public static boolean correctNbParameters(AnaGeneType _info, String _genericClass) {
        //From analyze
        Ints rep_ = _info.getTypeVarCounts();
        return StringExpUtil.normalCorrectType(_genericClass, rep_);
    }
    /** Splits by single dot the input string into parts regarding packages<br/>
     Let this code:<br/>
     <code><pre>public class my.pkg.MyClass{}</pre>
     <pre>public class my.pkg.MySecondClass{</pre>
     <pre>     public class Inner{}</pre>
     <pre>}</pre></code><br/>
     <pre>public class my.pkg.MyThirdClass{</pre>
     <pre>     public class Inner{</pre>
     <pre>         public class SecInner{}</pre>
     <pre>     }</pre></code>
     <pre>}</pre></code><br/>
     Sample 1: "int" => ["int"]<br/>
     Sample 2: "my.pkg.MyClass" => ["my.pkg.MyClass"]<br/>
     Sample 3: "my.pkg.MySecondClass.Inner" => ["my.pkg.MySecondClass","Inner"]<br/>
     Sample 4: "my.pkg.MyThirdClass.Inner.SecInner" => ["my.pkg.MySecondClass","Inner","SecInner"]<br/>
     */
    public static StringList getAllInnerTypes(String _type, AnalyzedPageEl _page) {
        return getAllInnerTypes(_type, _page.getPackagesFound());
    }

    public static StringList getAllInnerTypes(String _type, StringList _pkg) {
        StringList types_ = new StringList();
        int len_ = _type.length();
        StringBuilder builtId_ = new StringBuilder();
        int i_ = 0;
        while (i_ < len_) {
            char cur_ = _type.charAt(i_);
            if (cur_ == SEP_CLASS_CHAR) {
                //if builtId_.toString() is a type => inner_ is true
                String foundId_ = builtId_.toString();
                String tr_ = StringExpUtil.removeDottedSpaces(foundId_);
                if (StringExpUtil.nextCharIs(_type,i_+1,len_,SEP_CLASS_CHAR)||!StringUtil.contains(_pkg,tr_)) {
                    break;
                }
            }
            builtId_.append(cur_);
            i_++;
        }
        while (i_ < len_) {
            char cur_ = _type.charAt(i_);
            if (cur_ == SEP_CLASS_CHAR) {
                //if builtId_.toString() is a type => inner_ is true
                types_.add(builtId_.toString());
                builtId_.delete(0, builtId_.length());
                if (StringExpUtil.nextCharIs(_type,i_+1,len_,SEP_CLASS_CHAR)) {
                    i_++;
                    types_.add("..");
                } else {
                    types_.add(".");
                }
            } else {
                builtId_.append(cur_);
            }
            i_++;
        }
        types_.add(builtId_.toString());
        return types_;
    }

    public static DimComp getComponentForm(String _type) {
        int j_ = _type.length()-1;
        boolean arr_ = true;
        int count_ = 0;
        while (arr_) {
            while (j_ >= 0) {
                char locChar_ = _type.charAt(j_);
                if (StringUtil.isWhitespace(locChar_)) {
                    j_--;
                    continue;
                }
                if (locChar_ != ']') {
                    arr_ = false;
                }
                break;
            }
            if (arr_) {
                j_--;
                while (j_ >= 0) {
                    char locChar_ = _type.charAt(j_);
                    if (StringUtil.isWhitespace(locChar_)) {
                        j_--;
                        continue;
                    }
                    break;
                }
            }
            if (j_ < 0) {
                break;
            }
            if (arr_) {
                j_--;
                count_++;
            }
        }
        if (j_ >= 0) {
            return new DimComp(count_,_type.substring(0, j_+1));
        }
        return new DimComp(0,"");
    }

    public static String check(StringList _errs, String _className, StringList _parts, StringMap<StringList> _inherit, AnalyzedPageEl _page) {
        String res_ = tryGetAllInners(_className, _parts, _inherit, _page);
        if (res_.isEmpty()) {
            int rc_ = _page.getLocalizer().getCurrentLocationIndex();
            FoundErrorInterpret un_ = new FoundErrorInterpret();
            un_.setFileName(_page.getLocalizer().getCurrentFileName());
            un_.setIndexFile(rc_);
            //original type len
            String realClassName_ = getRealClassName(_className, _parts);
            un_.buildError(_page.getAnalysisMessages().getBadParamerizedType(),
                    realClassName_);
            _page.getLocalizer().addError(un_);
            _errs.add(un_.getBuiltError());
            res_ = _page.getAliasObject();
        }
        return res_;
    }

    public static String tryGetAllInners(String _className, StringList _parts, StringMap<StringList> _inherit, AnalyzedPageEl _page) {
        String realClassName_ = getRealClassName(_className, _parts);
        return getCorrectTemplateAll(realClassName_, _parts, _inherit, _page);
    }

    public static String getRealClassName(String _className, CustList<String> _parts) {
        String realClassName_;
        if (_parts.isEmpty()) {
            realClassName_ = _className;
        } else {
            realClassName_ = StringUtil.concat(_className,"<", StringUtil.join(_parts, ","),">");
        }
        return realClassName_;
    }

    public static String getCorrectTemplateAll(String _realClassName, StringList _parts, StringMap<StringList> _inherit, AnalyzedPageEl _page) {
        String id_ = StringExpUtil.getIdFromAllTypes(_realClassName);
        RootBlock g_ = _page.getAnaClassBody(id_);
        if (g_ == null) {
            return "";
        }
        CustList<StringList> bounds_ = g_.getBoundAll();
        return check(_realClassName, _parts, _inherit, _page, g_, bounds_);
    }

    public static String getCorrectTemplateAllAll(String _realClassName, StringList _parts, StringMap<StringList> _inherit, AnalyzedPageEl _page) {
        String id_ = StringExpUtil.getIdFromAllTypes(_realClassName);
        AnaGeneType g_ = _page.getAnaGeneType(id_);
        if (g_ == null) {
            return "";
        }
        CustList<StringList> bounds_ = ContextUtil.getBoundAllAll(g_);
        return check(_realClassName, _parts, _inherit, _page, g_, bounds_);
    }

    private static String check(String _realClassName, StringList _parts, StringMap<StringList> _inherit, AnalyzedPageEl _page, AnaGeneType _g, CustList<StringList> _bounds) {
        int len_ = _bounds.size();
        if (len_ != _parts.size()) {
            return "";
        }
        for (int i = 0; i < len_; i++) {
            StringList b_ = _bounds.get(i);
            for (String b : b_) {
                Mapping mapp_ = new Mapping();
                mapp_.setArg(_parts.get(i));
                String param_ = format(_g, _realClassName, b);
                mapp_.setParam(param_);
                mapp_.setMapping(_inherit);
                if (!isCorrect(mapp_, _page)) {
                    return "";
                }
            }
        }
        return _realClassName;
    }

    public static CustList<StringList> getBoundAll(AnaGeneType _ana) {
        if (_ana == null) {
            return new CustList<StringList>();
        }
        return _ana.getBoundAll();
    }
}
