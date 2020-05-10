package aiki.beans.pokemon;
import aiki.beans.AikiBeansStd;
import aiki.beans.DefaultStruct;
import aiki.beans.PokemonStandards;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.opers.util.ClassField;
import code.expressionlanguage.opers.util.ClassMethodId;
import code.expressionlanguage.opers.util.MethodId;
import code.expressionlanguage.opers.util.MethodModifier;
import code.expressionlanguage.stds.ResultErrorStd;
import code.expressionlanguage.stds.StandardClass;
import code.expressionlanguage.stds.StandardConstructor;
import code.expressionlanguage.stds.StandardField;
import code.expressionlanguage.stds.StandardMethod;
import code.expressionlanguage.structs.BooleanStruct;
import code.expressionlanguage.structs.IntStruct;
import code.expressionlanguage.structs.LongStruct;
import code.expressionlanguage.structs.NullStruct;
import code.formathtml.structs.RealInstanceStruct;
import code.expressionlanguage.structs.ShortStruct;
import code.expressionlanguage.structs.StringStruct;
import code.expressionlanguage.structs.Struct;
import code.formathtml.util.BeanLgNames;
import code.formathtml.util.BeanNatLgNames;
import code.util.CustList;
import code.util.ObjectMap;
import code.util.StringList;
import code.util.StringMap;
import aiki.facade.enums.SelectedBoolean;

public final class AikiBeansPokemonStd {
    public static final String TYPE_POKEDEX_BEAN = "aiki.beans.pokemon.PokedexBean";
    public static final String TYPE_POKEMON_BEAN = "aiki.beans.pokemon.PokemonBean";

    private static final String CLICK_POKEDEX = "clickPokedex";
    private static final String ROUND_WEIGHT = "roundWeight";
    private static final String ROUND_HEIGHT = "roundHeight";
    private static final String CLICK_ABILITY = "clickAbility";
    private static final String GET_TR_ABILITY = "getTrAbility";
    private static final String GET_PAGE = "getPage";
    private static final String CLICK_BASE = "clickBase";
    private static final String GET_BASE = "getBase";
    private static final String GET_EV = "getEv";
    private static final String CLICK_MOVE = "clickMove";
    private static final String CLICK_TECHNICAL_MOVE = "clickTechnicalMove";
    private static final String CLICK_HIDDEN_MOVE = "clickHiddenMove";
    private static final String CLICK_MOVE_TUTORS = "clickMoveTutors";
    private static final String GET_MOVE_TUTOR = "getMoveTutor";
    private static final String CLICK_EGG_PK = "clickEggPk";
    private static final String GET_EGG_PK = "getEggPk";
    private static final String IS_APPEARING_ANY_WHERE = "isAppearingAnyWhere";
    private static final String IS_APPEARING_PLACE = "isAppearingPlace";
    private static final String IS_MULTI_LAYER = "isMultiLayer";
    private static final String LAYERS = "layers";
    private static final String IS_APPEARING = "isAppearing";
    private static final String CLICK_LEVEL = "clickLevel";
    private static final String GET_MAP_WIDTH = "getMapWidth";
    private static final String IS_FIRST_ROW = "isFirstRow";
    private static final String GET_PLACE_NAME = "getPlaceName";
    private static final String GET_MINI_MAP_IMAGE = "getMiniMapImage";
    private static final String SEARCH = "search";
    private static final String GET_MINI_IMAGE = "getMiniImage";
    private static final String CLICK_LINK = "clickLink";
    private static final String DISPLAY_NAME = "displayName";
    private static final String BACK_IMAGE = "backImage";
    private static final String FRONT_IMAGE = "frontImage";
    private static final String WEIGHT = "weight";
    private static final String HEIGHT = "height";
    private static final String POSSIBLE_GENDERS = "possibleGenders";
    private static final String TYPES = "types";
    private static final String ABILITIES = "abilities";
    private static final String CATCHING_RATE = "catchingRate";
    private static final String EVOLUTIONS = "evolutions";
    private static final String NAME = "name";
    private static final String EVO_BASE = "evoBase";
    private static final String EXP_EVO = "expEvo";
    private static final String MAP_VARS = "mapVars";
    private static final String EXP_RATE = "expRate";
    private static final String STATISTICS = "statistics";
    private static final String LEV_MOVES = "levMoves";
    private static final String TECHNICAL_MOVES = "technicalMoves";
    private static final String HIDDEN_MOVES = "hiddenMoves";
    private static final String MOVE_TUTORS = "moveTutors";
    private static final String EGG_GROUPS_PK = "eggGroupsPk";
    private static final String HATCHING_STEPS = "hatchingSteps";
    private static final String PLACES = "places";
    private static final String IMAGES = "images";
    private static final String TYPED_NAME = "typedName";
    private static final String TYPED_TYPE = "typedType";
    private static final String WHOLE_WORD = "wholeWord";
    private static final String TYPED_MIN_NB_POSS_EVOS = "typedMinNbPossEvos";
    private static final String TYPED_MAX_NB_POSS_EVOS = "typedMaxNbPossEvos";
    private static final String BOOLEANS = "booleans";
    private static final String IS_EVO = "isEvo";
    private static final String IS_LEG = "isLeg";
    private static final String POKEDEX = "pokedex";

    public static void build(BeanLgNames _std) {
        buildPokedexBean(_std);
        buildPokemonBean(_std);
    }
    private static void buildPokedexBean(BeanLgNames _std) {
        StandardClass type_;
        StringMap<StandardField> fields_;
        CustList<StandardConstructor> constructors_;
        ObjectMap<MethodId, StandardMethod> methods_;
        StandardMethod method_;
        StringList params_;
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new StringMap<StandardField>();
        type_ = new StandardClass(TYPE_POKEDEX_BEAN, fields_, constructors_, methods_, AikiBeansStd.TYPE_COMMON_BEAN, MethodModifier.NORMAL);
        fields_.put(TYPED_NAME,new StandardField(TYPED_NAME,_std.getAliasString(),false,false,type_));
        fields_.put(TYPED_TYPE,new StandardField(TYPED_TYPE,_std.getAliasString(),false,false,type_));
        fields_.put(WHOLE_WORD,new StandardField(WHOLE_WORD,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.put(TYPED_MIN_NB_POSS_EVOS,new StandardField(TYPED_MIN_NB_POSS_EVOS,_std.getAliasString(),false,false,type_));
        fields_.put(TYPED_MAX_NB_POSS_EVOS,new StandardField(TYPED_MAX_NB_POSS_EVOS,_std.getAliasString(),false,false,type_));
        fields_.put(BOOLEANS,new StandardField(BOOLEANS, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.put(IS_EVO,new StandardField(IS_EVO,((PokemonStandards)_std).getSelectedBoolean(),false,false,type_));
        fields_.put(IS_LEG,new StandardField(IS_LEG,((PokemonStandards)_std).getSelectedBoolean(),false,false,type_));
        fields_.put(POKEDEX,new StandardField(POKEDEX, BeanNatLgNames.TYPE_LIST,false,false,type_));
        params_ = new StringList();
        method_ = new StandardMethod(SEARCH,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(GET_MINI_IMAGE,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(CLICK_LINK,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        _std.getStandards().put(TYPE_POKEDEX_BEAN, type_);
    }
    private static void buildPokemonBean(BeanLgNames _std) {
        StandardClass type_;
        StringMap<StandardField> fields_;
        CustList<StandardConstructor> constructors_;
        ObjectMap<MethodId, StandardMethod> methods_;
        StandardMethod method_;
        StringList params_;
        methods_ = new ObjectMap<MethodId, StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new StringMap<StandardField>();
        type_ = new StandardClass(TYPE_POKEMON_BEAN, fields_, constructors_, methods_, AikiBeansStd.TYPE_COMMON_BEAN, MethodModifier.NORMAL);
        fields_.put(DISPLAY_NAME,new StandardField(DISPLAY_NAME,_std.getAliasString(),false,false,type_));
        fields_.put(BACK_IMAGE,new StandardField(BACK_IMAGE,_std.getAliasString(),false,false,type_));
        fields_.put(FRONT_IMAGE,new StandardField(FRONT_IMAGE,_std.getAliasString(),false,false,type_));
        fields_.put(WEIGHT,new StandardField(WEIGHT,PokemonStandards.TYPE_RATE,false,false,type_));
        fields_.put(HEIGHT,new StandardField(HEIGHT,PokemonStandards.TYPE_RATE,false,false,type_));
        fields_.put(POSSIBLE_GENDERS,new StandardField(POSSIBLE_GENDERS, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.put(TYPES,new StandardField(TYPES, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.put(ABILITIES,new StandardField(ABILITIES, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.put(CATCHING_RATE,new StandardField(CATCHING_RATE,_std.getAliasPrimShort(),false,false,type_));
        fields_.put(EVOLUTIONS,new StandardField(EVOLUTIONS, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.put(NAME,new StandardField(NAME,_std.getAliasString(),false,false,type_));
        fields_.put(EVO_BASE,new StandardField(EVO_BASE,_std.getAliasString(),false,false,type_));
        fields_.put(EXP_EVO,new StandardField(EXP_EVO,_std.getAliasString(),false,false,type_));
        fields_.put(MAP_VARS,new StandardField(MAP_VARS, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.put(EXP_RATE,new StandardField(EXP_RATE,_std.getAliasPrimLong(),false,false,type_));
        fields_.put(STATISTICS,new StandardField(STATISTICS, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.put(LEV_MOVES,new StandardField(LEV_MOVES, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.put(TECHNICAL_MOVES,new StandardField(TECHNICAL_MOVES, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.put(HIDDEN_MOVES,new StandardField(HIDDEN_MOVES, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.put(MOVE_TUTORS,new StandardField(MOVE_TUTORS, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.put(EGG_GROUPS_PK,new StandardField(EGG_GROUPS_PK, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.put(HATCHING_STEPS,new StandardField(HATCHING_STEPS,PokemonStandards.TYPE_LG_INT,false,false,type_));
        fields_.put(PLACES,new StandardField(PLACES, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.put(IMAGES,new StandardField(IMAGES, BeanNatLgNames.TYPE_MAP,false,false,type_));
        params_ = new StringList();
        method_ = new StandardMethod(CLICK_POKEDEX,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList();
        method_ = new StandardMethod(ROUND_WEIGHT,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList();
        method_ = new StandardMethod(ROUND_HEIGHT,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(CLICK_ABILITY,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(GET_TR_ABILITY,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(GET_PAGE,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList();
        method_ = new StandardMethod(CLICK_BASE,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(GET_BASE,params_,_std.getAliasPrimShort(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(GET_EV,params_,_std.getAliasPrimShort(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(CLICK_MOVE,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(CLICK_TECHNICAL_MOVE,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(CLICK_HIDDEN_MOVE,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(CLICK_MOVE_TUTORS,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(GET_MOVE_TUTOR,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(CLICK_EGG_PK,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(GET_EGG_PK,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_APPEARING_ANY_WHERE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(IS_APPEARING_PLACE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(IS_MULTI_LAYER,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(LAYERS,params_, BeanNatLgNames.TYPE_LIST, false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong(),_std.getAliasLong());
        method_ = new StandardMethod(IS_APPEARING,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong(),_std.getAliasLong());
        method_ = new StandardMethod(CLICK_LEVEL,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_MAP_WIDTH,params_,_std.getAliasPrimInteger(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(IS_FIRST_ROW,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(GET_PLACE_NAME,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        params_ = new StringList(_std.getAliasLong());
        method_ = new StandardMethod(GET_MINI_MAP_IMAGE,params_,_std.getAliasString(), false, MethodModifier.NORMAL,type_);
        methods_.put(method_.getId(), method_);
        _std.getStandards().put(TYPE_POKEMON_BEAN, type_);
    }
    public static ResultErrorStd getResultPokedexBean(ContextEl _cont, ClassField _classField, Struct _instance) {
        BeanNatLgNames std_ = (BeanNatLgNames) _cont.getStandards();
        ResultErrorStd res_ = new ResultErrorStd();
        PokedexBean instance_ = (PokedexBean) ((RealInstanceStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringList.quickEq(fieldName_,TYPED_NAME)) {
            res_.setResult(new StringStruct(instance_.getTypedName()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,TYPED_TYPE)) {
            res_.setResult(new StringStruct(instance_.getTypedType()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,WHOLE_WORD)) {
            res_.setResult(BooleanStruct.of(instance_.getWholeWord()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,TYPED_MIN_NB_POSS_EVOS)) {
            res_.setResult(new StringStruct(instance_.getTypedMinNbPossEvos()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,TYPED_MAX_NB_POSS_EVOS)) {
            res_.setResult(new StringStruct(instance_.getTypedMaxNbPossEvos()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,BOOLEANS)) {
            res_.setResult(new DefaultStruct(instance_.getBooleans(), BeanNatLgNames.TYPE_MAP));
            return res_;
        }
        if (StringList.quickEq(fieldName_,IS_EVO)) {
            res_.setResult(std_.wrapStd(instance_.getIsEvo(),_cont));
            return res_;
        }
        if (StringList.quickEq(fieldName_,IS_LEG)) {
            res_.setResult(std_.wrapStd(instance_.getIsLeg(),_cont));
            return res_;
        }
        if (StringList.quickEq(fieldName_,POKEDEX)) {
            res_.setResult(new DefaultStruct(instance_.getPokedex(), BeanNatLgNames.TYPE_LIST));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd getResultPokemonBean(ContextEl _cont, ClassField _classField, Struct _instance) {
        BeanLgNames std_ = (BeanLgNames) _cont.getStandards();
        ResultErrorStd res_ = new ResultErrorStd();
        PokemonBean instance_ = (PokemonBean) ((RealInstanceStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringList.quickEq(fieldName_,DISPLAY_NAME)) {
            res_.setResult(new StringStruct(instance_.getDisplayName()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,BACK_IMAGE)) {
            res_.setResult(new StringStruct(instance_.getBackImage()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,FRONT_IMAGE)) {
            res_.setResult(new StringStruct(instance_.getFrontImage()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,WEIGHT)) {
            res_.setResult(new DefaultStruct(instance_.getWeight(),PokemonStandards.TYPE_RATE));
            return res_;
        }
        if (StringList.quickEq(fieldName_,HEIGHT)) {
            res_.setResult(new DefaultStruct(instance_.getHeight(),PokemonStandards.TYPE_RATE));
            return res_;
        }
        if (StringList.quickEq(fieldName_,POSSIBLE_GENDERS)) {
            res_.setResult(new DefaultStruct(instance_.getPossibleGenders(), BeanNatLgNames.TYPE_LIST));
            return res_;
        }
        if (StringList.quickEq(fieldName_,TYPES)) {
            res_.setResult(new DefaultStruct(instance_.getTypes(), BeanNatLgNames.TYPE_LIST));
            return res_;
        }
        if (StringList.quickEq(fieldName_,ABILITIES)) {
            res_.setResult(new DefaultStruct(instance_.getAbilities(), BeanNatLgNames.TYPE_LIST));
            return res_;
        }
        if (StringList.quickEq(fieldName_,CATCHING_RATE)) {
            res_.setResult(new ShortStruct(instance_.getCatchingRate()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,EVOLUTIONS)) {
            res_.setResult(new DefaultStruct(instance_.getEvolutions(), BeanNatLgNames.TYPE_LIST));
            return res_;
        }
        if (StringList.quickEq(fieldName_,NAME)) {
            res_.setResult(new StringStruct(instance_.getName()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,EVO_BASE)) {
            res_.setResult(new StringStruct(instance_.getEvoBase()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,EXP_EVO)) {
            res_.setResult(new StringStruct(instance_.getExpEvo()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,MAP_VARS)) {
            res_.setResult(new DefaultStruct(instance_.getMapVars(), BeanNatLgNames.TYPE_MAP));
            return res_;
        }
        if (StringList.quickEq(fieldName_,EXP_RATE)) {
            res_.setResult(new LongStruct(instance_.getExpRate()));
            return res_;
        }
        if (StringList.quickEq(fieldName_,STATISTICS)) {
            res_.setResult(new DefaultStruct(instance_.getStatistics(), BeanNatLgNames.TYPE_LIST));
            return res_;
        }
        if (StringList.quickEq(fieldName_,LEV_MOVES)) {
            res_.setResult(new DefaultStruct(instance_.getLevMoves(), BeanNatLgNames.TYPE_LIST));
            return res_;
        }
        if (StringList.quickEq(fieldName_,TECHNICAL_MOVES)) {
            res_.setResult(new DefaultStruct(instance_.getTechnicalMoves(), BeanNatLgNames.TYPE_MAP));
            return res_;
        }
        if (StringList.quickEq(fieldName_,HIDDEN_MOVES)) {
            res_.setResult(new DefaultStruct(instance_.getHiddenMoves(), BeanNatLgNames.TYPE_MAP));
            return res_;
        }
        if (StringList.quickEq(fieldName_,MOVE_TUTORS)) {
            res_.setResult(new DefaultStruct(instance_.getMoveTutors(), BeanNatLgNames.TYPE_LIST));
            return res_;
        }
        if (StringList.quickEq(fieldName_,EGG_GROUPS_PK)) {
            res_.setResult(new DefaultStruct(instance_.getEggGroupsPk(), BeanNatLgNames.TYPE_LIST));
            return res_;
        }
        if (StringList.quickEq(fieldName_,HATCHING_STEPS)) {
            res_.setResult(new DefaultStruct(instance_.getHatchingSteps(),PokemonStandards.TYPE_LG_INT));
            return res_;
        }
        if (StringList.quickEq(fieldName_,PLACES)) {
            res_.setResult(new DefaultStruct(instance_.getPlaces(), BeanNatLgNames.TYPE_LIST));
            return res_;
        }
        if (StringList.quickEq(fieldName_,IMAGES)) {
            res_.setResult(new DefaultStruct(instance_.getImages(), BeanNatLgNames.TYPE_MAP));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd setResultPokedexBean(ContextEl _cont, ClassField _classField, Struct _instance, Object _value) {
        ResultErrorStd res_ = new ResultErrorStd();
        PokedexBean instance_ = (PokedexBean) ((RealInstanceStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringList.quickEq(fieldName_,TYPED_NAME)) {
            instance_.setTypedName((String) _value);
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringList.quickEq(fieldName_,TYPED_TYPE)) {
            instance_.setTypedType((String) _value);
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringList.quickEq(fieldName_,WHOLE_WORD)) {
            instance_.setWholeWord((Boolean) _value);
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringList.quickEq(fieldName_,TYPED_MIN_NB_POSS_EVOS)) {
            instance_.setTypedMinNbPossEvos((String) _value);
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringList.quickEq(fieldName_,TYPED_MAX_NB_POSS_EVOS)) {
            instance_.setTypedMaxNbPossEvos((String) _value);
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringList.quickEq(fieldName_,IS_EVO)) {
            instance_.setIsEvo((SelectedBoolean) _value);
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringList.quickEq(fieldName_,IS_LEG)) {
            instance_.setIsLeg((SelectedBoolean) _value);
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd invokeMethodPokedexBean(ContextEl _cont, Struct _instance, ClassMethodId _method, Object... _args) {
        PokedexBean instance_ = (PokedexBean) ((RealInstanceStruct)_instance).getInstance();
        String methodName_ = _method.getConstraints().getName();
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringList.quickEq(methodName_,SEARCH)) {
            res_.setResult(new StringStruct(instance_.search()));
            return res_;
        }
        if (StringList.quickEq(methodName_,GET_MINI_IMAGE)) {
            res_.setResult(new StringStruct(instance_.getMiniImage((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,CLICK_LINK)) {
            res_.setResult(new StringStruct(instance_.clickLink((Long)_args[0])));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd invokeMethodPokemonBean(ContextEl _cont, Struct _instance, ClassMethodId _method, Object... _args) {
        BeanLgNames std_ = (BeanLgNames) _cont.getStandards();
        PokemonBean instance_ = (PokemonBean) ((RealInstanceStruct)_instance).getInstance();
        String methodName_ = _method.getConstraints().getName();
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringList.quickEq(methodName_,CLICK_POKEDEX)) {
            res_.setResult(new StringStruct(instance_.clickPokedex()));
            return res_;
        }
        if (StringList.quickEq(methodName_,ROUND_WEIGHT)) {
            res_.setResult(new StringStruct(instance_.roundWeight()));
            return res_;
        }
        if (StringList.quickEq(methodName_,ROUND_HEIGHT)) {
            res_.setResult(new StringStruct(instance_.roundHeight()));
            return res_;
        }
        if (StringList.quickEq(methodName_,CLICK_ABILITY)) {
            res_.setResult(new StringStruct(instance_.clickAbility((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,GET_TR_ABILITY)) {
            res_.setResult(new StringStruct(instance_.getTrAbility((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,GET_PAGE)) {
            res_.setResult(new StringStruct(instance_.getPage((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,CLICK_BASE)) {
            res_.setResult(new StringStruct(instance_.clickBase()));
            return res_;
        }
        if (StringList.quickEq(methodName_,GET_BASE)) {
            res_.setResult(new ShortStruct(instance_.getBase((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,GET_EV)) {
            res_.setResult(new ShortStruct(instance_.getEv((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,CLICK_MOVE)) {
            res_.setResult(new StringStruct(instance_.clickMove((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,CLICK_TECHNICAL_MOVE)) {
            res_.setResult(new StringStruct(instance_.clickTechnicalMove((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,CLICK_HIDDEN_MOVE)) {
            res_.setResult(new StringStruct(instance_.clickHiddenMove((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,CLICK_MOVE_TUTORS)) {
            res_.setResult(new StringStruct(instance_.clickMoveTutors((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,GET_MOVE_TUTOR)) {
            res_.setResult(new StringStruct(instance_.getMoveTutor((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,CLICK_EGG_PK)) {
            res_.setResult(new StringStruct(instance_.clickEggPk((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,GET_EGG_PK)) {
            res_.setResult(new StringStruct(instance_.getEggPk((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,IS_APPEARING_ANY_WHERE)) {
            res_.setResult(BooleanStruct.of(instance_.isAppearingAnyWhere()));
            return res_;
        }
        if (StringList.quickEq(methodName_,IS_APPEARING_PLACE)) {
            res_.setResult(BooleanStruct.of(instance_.isAppearingPlace((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,IS_MULTI_LAYER)) {
            res_.setResult(BooleanStruct.of(instance_.isMultiLayer((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,LAYERS)) {
            res_.setResult(new DefaultStruct(instance_.layers((Long)_args[0]), BeanNatLgNames.TYPE_LIST));
            return res_;
        }
        if (StringList.quickEq(methodName_,IS_APPEARING)) {
            res_.setResult(BooleanStruct.of(instance_.isAppearing((Long)_args[0],(Long)_args[1])));
            return res_;
        }
        if (StringList.quickEq(methodName_,CLICK_LEVEL)) {
            res_.setResult(new StringStruct(instance_.clickLevel((Long)_args[0],(Long)_args[1])));
            return res_;
        }
        if (StringList.quickEq(methodName_,GET_MAP_WIDTH)) {
            res_.setResult(new IntStruct(instance_.getMapWidth()));
            return res_;
        }
        if (StringList.quickEq(methodName_,IS_FIRST_ROW)) {
            res_.setResult(BooleanStruct.of(instance_.isFirstRow((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,GET_PLACE_NAME)) {
            res_.setResult(new StringStruct(instance_.getPlaceName((Long)_args[0])));
            return res_;
        }
        if (StringList.quickEq(methodName_,GET_MINI_MAP_IMAGE)) {
            res_.setResult(new StringStruct(instance_.getMiniMapImage((Long)_args[0])));
            return res_;
        }
        return res_;
    }
}
