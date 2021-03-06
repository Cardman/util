package aiki.beans.simulation;
import aiki.beans.*;
import code.bean.nat.SpecialNatClass;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.ClassField;
import code.expressionlanguage.common.NumParsers;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.functionid.MethodModifier;
import code.expressionlanguage.stds.ResultErrorStd;
import code.expressionlanguage.stds.StandardConstructor;
import code.bean.nat.StandardField;
import code.expressionlanguage.stds.StandardMethod;
import code.expressionlanguage.structs.BooleanStruct;
import code.expressionlanguage.structs.IntStruct;
import code.expressionlanguage.structs.NullStruct;
import code.expressionlanguage.structs.StringStruct;
import code.expressionlanguage.structs.Struct;
import code.formathtml.util.BeanLgNames;
import code.bean.nat.BeanNatLgNames;
import code.util.CustList;
import code.util.StringList;
import code.util.core.StringUtil;

public final class AikiBeansSimulationStd {
    public static final String TYPE_ADD_POKEMON_BEAN = "aiki.beans.simulation.AddPokemonBean";
    public static final String TYPE_EDIT_POKEMON_BEAN = "aiki.beans.simulation.EditPokemonBean";
    public static final String TYPE_EDIT_POKEMON_MOVES_BEAN = "aiki.beans.simulation.EditPokemonMovesBean";
    public static final String TYPE_EDIT_TRAINER_POKEMON_BEAN = "aiki.beans.simulation.EditTrainerPokemonBean";
    public static final String TYPE_SELECT_ABILITY_BEAN = "aiki.beans.simulation.SelectAbilityBean";
    public static final String TYPE_SELECT_ITEM_BEAN = "aiki.beans.simulation.SelectItemBean";
    public static final String TYPE_SELECT_POKEMON_BEAN = "aiki.beans.simulation.SelectPokemonBean";
    public static final String TYPE_SIMULATION_BEAN = "aiki.beans.simulation.SimulationBean";
    public static final String TYPE_SIMULATION_LEVEL_BEAN = "aiki.beans.simulation.SimulationLevelBean";

    private static final String CANCEL = "cancel";
    private static final String ADD = "add";
    private static final String SEARCH = "search";
    private static final String GET_MINI_IMAGE = "getMiniImage";
    private static final String CLICK_LINK = "clickLink";
    private static final String TRANSLATE_NAME = "translateName";
    private static final String TRANSLATE_ITEM = "translateItem";
    private static final String CHOOSE_ITEM = "chooseItem";
    private static final String ADD_MOVES = "addMoves";
    private static final String DELETE_MOVES = "deleteMoves";
    private static final String EDIT = "edit";
    private static final String GET_TRANSLATED_STATISTIC = "getTranslatedStatistic";
    private static final String CHOOSE_NAME = "chooseName";
    private static final String CHOOSE_ABILITY = "chooseAbility";
    private static final String VALIDATE_TRAINER_PK = "validateTrainerPk";
    private static final String GET_TRANSLATED_NAME = "getTranslatedName";
    private static final String GET_TRANSLATED_ABILITY = "getTranslatedAbility";
    private static final String GET_TRANSLATED_ITEM = "getTranslatedItem";
    private static final String CLICK_ABILITY = "clickAbility";
    private static final String GET_TR_ABILITY = "getTrAbility";
    private static final String CANCEL_ITEM = "cancelItem";
    private static final String GET_REAL_STEP_NUMBER = "getRealStepNumber";
    private static final String QUIT = "quit";
    private static final String IS_DIFF_STATE = "isDiffState";
    private static final String VALIDATE_DIFF_CHOICE = "validateDiffChoice";
    private static final String IS_FOE_STATE = "isFoeState";
    private static final String ADD_PK_TRAINER = "addPkTrainer";
    private static final String SELECT_FOE_PK = "selectFoePk";
    private static final String GET_IMAGE_FOE = "getImageFoe";
    private static final String GET_NAME_FOE = "getNameFoe";
    private static final String GET_LEVEL_FOE = "getLevelFoe";
    private static final String GET_ABILITY_FOE = "getAbilityFoe";
    private static final String GET_GENDER_FOE = "getGenderFoe";
    private static final String GET_ITEM_FOE = "getItemFoe";
    private static final String GET_MOVES_FOE = "getMovesFoe";
    private static final String SELECT_ALLY_PK = "selectAllyPk";
    private static final String GET_IMAGE_ALLY = "getImageAlly";
    private static final String GET_NAME_ALLY = "getNameAlly";
    private static final String GET_LEVEL_ALLY = "getLevelAlly";
    private static final String GET_ABILITY_ALLY = "getAbilityAlly";
    private static final String GET_GENDER_ALLY = "getGenderAlly";
    private static final String GET_ITEM_ALLY = "getItemAlly";
    private static final String GET_MOVES_ALLY = "getMovesAlly";
    private static final String IS_MULTI_LAYER = "isMultiLayer";
    private static final String LAYERS = "layers";
    private static final String CLICK_LEVEL = "clickLevel";
    private static final String GET_TRAINER_NAME = "getTrainerName";
    private static final String CANCEL_DIFF_CHOICE = "cancelDiffChoice";
    private static final String VALIDATE_FOE_CHOICE_FREE = "validateFoeChoiceFree";
    private static final String VALIDATE_FOE_CHOICE = "validateFoeChoice";
    private static final String IS_TEAM_STATE = "isTeamState";
    private static final String SELECT_PK = "selectPk";
    private static final String GET_IMAGE = "getImage";
    private static final String GET_NAME = "getName";
    private static final String GET_LEVEL = "getLevel";
    private static final String GET_ABILITY = "getAbility";
    private static final String GET_GENDER = "getGender";
    private static final String GET_ITEM = "getItem";
    private static final String GET_MOVES = "getMoves";
    private static final String CANCEL_TEAM = "cancelTeam";
    private static final String VALIDATE_TEAM = "validateTeam";
    private static final String VALIDATE_FOE_CHOICE_SKIP_EVOLUTIONS = "validateFoeChoiceSkipEvolutions";
    private static final String IS_EVOLUTIONS_STATE = "isEvolutionsState";
    private static final String DISPLAY_EVOLUTIONS = "displayEvolutions";
    private static final String SELECTED_INDEX = "selectedIndex";
    private static final String VALIDATE_EVO = "validateEvo";
    private static final String CANCEL_EVO = "cancelEvo";
    private static final String CANCEL_EVOLUTIONS = "cancelEvolutions";
    private static final String VALIDATE_EVOLUTIONS = "validateEvolutions";
    private static final String IS_FRONT_STATE = "isFrontState";
    private static final String VALIDATE_FRONT_FIGHTER = "validateFrontFighter";
    private static final String CANCEL_FRONT_FIGHTERS = "cancelFrontFighters";
    private static final String VALIDATE_FRONT_FIGHTERS = "validateFrontFighters";
    private static final String IS_MOVES_STATE = "isMovesState";
    private static final String SELECTED_INDEX_FOR_MOVES = "selectedIndexForMoves";
    private static final String IS_AVAILABLE_MOVES = "isAvailableMoves";
    private static final String VALIDATE_MOVES = "validateMoves";
    private static final String IS_AVAILABLE_ABILITIES = "isAvailableAbilities";
    private static final String CANCEL_MOVES = "cancelMoves";
    private static final String CANCEL_MOVES_SETS = "cancelMovesSets";
    private static final String VALIDATE_MOVES_SETS = "validateMovesSets";
    private static final String IS_MOVES_FIGHT_STATE = "isMovesFightState";
    private static final String VALIDATE_MOVES_CHOICE = "validateMovesChoice";
    private static final String CANCEL_MOVES_EVOS = "cancelMovesEvos";
    private static final String SIMULATE_FIGHT = "simulateFight";
    private static final String IS_SIMULATION_STATE = "isSimulationState";
    private static final String IS_ISSUE = "isIssue";
    private static final String GET_KO_PLAYERS = "getKoPlayers";
    private static final String GET_NOT_KO_FRONT_FOES = "getNotKoFrontFoes";
    private static final String GET_KO_FOES = "getKoFoes";
    private static final String IS_RULES_ISSUE = "isRulesIssue";
    private static final String IS_RULES_MOVES_ISSUE = "isRulesMovesIssue";
    private static final String IS_RULES_LEARN_ISSUE = "isRulesLearnIssue";
    private static final String IS_RULES_SWITCH_ISSUE = "isRulesSwitchIssue";
    private static final String IS_SENDING_ISSUE = "isSendingIssue";
    private static final String IS_RANDOM_ISSUE = "isRandomIssue";
    private static final String IS_USING_ISSUE = "isUsingIssue";
    private static final String IS_HARD_SIMULATION_ISSUE = "isHardSimulationIssue";
    private static final String IS_ISSUE_AFTER_FIGHT = "isIssueAfterFight";
    private static final String GET_IMAGE_AFTER_FIGHT = "getImageAfterFight";
    private static final String GET_NAME_AFTER_FIGHT = "getNameAfterFight";
    private static final String GET_LEVEL_AFTER_FIGHT = "getLevelAfterFight";
    private static final String GET_ABILITY_AFTER_FIGHT = "getAbilityAfterFight";
    private static final String GET_GENDER_AFTER_FIGHT = "getGenderAfterFight";
    private static final String GET_ITEM_AFTER_FIGHT = "getItemAfterFight";
    private static final String GET_MOVES_AFTER_FIGHT = "getMovesAfterFight";
    private static final String GET_REMAINING_LIFE_RATE = "getRemainingLifeRate";
    private static final String NUMBER_NECESSARY_POINTS_FOR_GROWING_LEVEL = "numberNecessaryPointsForGrowingLevel";
    private static final String CHANGE_FIGHT = "changeFight";
    private static final String IS_FIGHT_AFTER = "isFightAfter";
    private static final String NEXT_FIGHT = "nextFight";
    private static final String DISPLAY_COMMENTS = "displayComments";
    private static final String HIDE_COMMENTS = "hideComments";
    private static final String IS_EVOLUTION_AFTER_FIGHT_STATE = "isEvolutionAfterFightState";
    private static final String SELECT_PK_AFTER_FIGHT = "selectPkAfterFight";
    private static final String VALIDATE_EVOLUTION_AFTER_FIGHT = "validateEvolutionAfterFight";
    private static final String CANCEL_EVOLUTIONS_AFTER_FIGHT = "cancelEvolutionsAfterFight";
    private static final String VALIDATE_MOVES_ABILITY_AFTER_FIGHT = "validateMovesAbilityAfterFight";
    private static final String CHANGE_FIGHT_WHILE_END = "changeFightWhileEnd";
    private static final String VALIDATE_MOVES_AFTER_FIGHT = "validateMovesAfterFight";
    private static final String GET_MAP_WIDTH = "getMapWidth";
    private static final String IS_FIRST_ROW = "isFirstRow";
    private static final String CLICK_TILE = "clickTile";
    private static final String NAME_PK = "namePk";
    private static final String ABILITIES = "abilities";
    private static final String ABILITY = "ability";
    private static final String GENDERS = "genders";
    private static final String GENDER = "gender";
    private static final String LEVEL = "level";
    private static final String TYPED_NAME = "typedName";
    private static final String TYPED_TYPE = "typedType";
    private static final String WHOLE_WORD = "wholeWord";
    private static final String BOOLEANS = "booleans";
    private static final String HAS_EVO = "hasEvo";
    private static final String IS_EVO = "isEvo";
    private static final String IS_LEG = "isLeg";
    private static final String POKEDEX = "pokedex";
    private static final String MOVES = "moves";
    private static final String EXPERIENCE = "experience";
    private static final String BALLS = "balls";
    private static final String BALL = "ball";
    private static final String HAPPINESS = "happiness";
    private static final String REMAINING_HP = "remainingHp";
    private static final String HEAL = "heal";
    private static final String EV = "ev";
    private static final String CATEGORIES = "categories";
    private static final String CATEGORY = "category";
    private static final String PLAYER = "player";
    private static final String AVAILABLE_MOVES_ONLY = "availableMovesOnly";
    private static final String ALLY_PK = "allyPk";
    private static final String TYPED_ABILITY = "typedAbility";
    private static final String SORTED_ABILITIES = "sortedAbilities";
    private static final String TYPED_PRICE = "typedPrice";
    private static final String TYPED_CLASS = "typedClass";
    private static final String ITEMS = "items";
    private static final String WIN_POINTS_FIGHT = "winPointsFight";
    private static final String DIFF_WINNING_EXP_PTS_FIGHT = "diffWinningExpPtsFight";
    private static final String ALLOW_CATCHING_KO = "allowCatchingKo";
    private static final String ALLOWED_SWITCH_PLACES_END_ROUND = "allowedSwitchPlacesEndRound";
    private static final String WIN_TRAINER_EXP = "winTrainerExp";
    private static final String RATE_WINNING_EXP_PTS_FIGHT = "rateWinningExpPtsFight";
    private static final String END_FIGHT_IF_ONE_TEAM_KO = "endFightIfOneTeamKo";
    private static final String IV_PLAYER = "ivPlayer";
    private static final String IV_FOE = "ivFoe";
    private static final String RATE_WIN_MONEY_BASE = "rateWinMoneyBase";
    private static final String RATE_LOOSE_MONEY_WIN = "rateLooseMoneyWin";
    private static final String RESTORED_MOVES_END_FIGHT = "restoredMovesEndFight";
    private static final String ENABLED_CLOSING = "enabledClosing";
    private static final String RANDOM_WILD_FIGHT = "randomWildFight";
    private static final String STILL_POSSIBLE_FLEE = "stillPossibleFlee";
    private static final String SKIP_LEARNING_MOVES_WHILE_NOT_GROWING_LEVEL = "skipLearningMovesWhileNotGrowingLevel";
    private static final String DAMAGE_RATES = "damageRates";
    private static final String DAMAGE_RATE_PLAYER = "damageRatePlayer";
    private static final String DAMAGE_RATE_PLAYER_TABLE = "damageRatePlayerTable";
    private static final String DAMAGE_RATE_LAW_FOE = "damageRateLawFoe";
    private static final String DAMAGE_RATE_FOE_TABLE = "damageRateFoeTable";
    private static final String FREE_TEAMS = "freeTeams";
    private static final String MULTIPLICITY = "multiplicity";
    private static final String ENVIRONMENTS = "environments";
    private static final String ENVIRONMENT = "environment";
    private static final String FOE_TEAM = "foeTeam";
    private static final String SELECTED_FOE_PK = "selectedFoePk";
    private static final String SELECTED_FOE_ACTION = "selectedFoeAction";
    private static final String ALLY_TEAM = "allyTeam";
    private static final String SELECTED_ALLY_PK = "selectedAllyPk";
    private static final String SELECTED_ALLY_ACTION = "selectedAllyAction";
    private static final String PLACES = "places";
    private static final String OK = "ok";
    private static final String TEAM = "team";
    private static final String SELECTED_PK = "selectedPk";
    private static final String SELECTED_ACTION = "selectedAction";
    private static final String AVAILABLE_EVOS = "availableEvos";
    private static final String CHOSEN_EVO = "chosenEvo";
    private static final String LEVEL_EVO = "levelEvo";
    private static final String ROUND = "round";
    private static final String SELECTED_ROUND = "selectedRound";
    private static final String PLACES_FIGHT = "placesFight";
    private static final String PLACE_FIGHT = "placeFight";
    private static final String DISPLAY_IF_ERROR = "displayIfError";
    private static final String CURRENT_ABILITY = "currentAbility";
    private static final String KEPT_MOVES = "keptMoves";
    private static final String MOVES_SET = "movesSet";
    private static final String SELECTED_MOVE = "selectedMove";
    private static final String ALLY_CHOICE = "allyChoice";
    private static final String TARGET_FIGHT = "targetFight";
    private static final String TARGET = "target";
    private static final String COMMENTS = "comments";
    private static final String TEAM_AFTER_FIGHT = "teamAfterFight";
    private static final String EVOLUTIONS_AFTER_FIGHT = "evolutionsAfterFight";
    private static final String EVOLUTION_AFTER_FIGHT = "evolutionAfterFight";
    private static final String ABILITIES_AFTER_FIGHT = "abilitiesAfterFight";
    private static final String ABILITY_AFTER_FIGHT = "abilityAfterFight";
    private static final String KEPT_MOVES_AFTER_FIGHT = "keptMovesAfterFight";
    private static final String POSSIBLE_MULTI_LAYER = "possibleMultiLayer";
    private static final String PLACE_NAME = "placeName";
    private static final String LEVEL_INDEX = "levelIndex";
    private static final String OUTSIDE = "outside";
    private static final String ROAD = "road";
    private static final String GYM = "gym";
    private static final String POKEMON_CENTER = "pokemonCenter";
    private static final String NO_FIGHT = "noFight";
    private static final String TILES = "tiles";

    public static void build(PokemonStandards _std) {
        buildAddPokemonBean(_std);
        buildEditPokemonBean(_std);
        buildEditPokemonMovesBean(_std);
        buildEditTrainerPokemonBean(_std);
        buildSelectAbilityBean(_std);
        buildSelectItemBean(_std);
        buildSelectPokemonBean(_std);
        buildSimulationBean(_std);
        buildSimulationLevelBean(_std);
    }
    private static void buildAddPokemonBean(PokemonStandards _std) {
        SpecialNatClass type_;
        CustList<StandardField> fields_;
        CustList<StandardConstructor> constructors_;
        CustList<StandardMethod> methods_;
        StandardMethod method_;
        StringList params_;
        methods_ = new CustList<StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        type_ = new SpecialNatClass(TYPE_ADD_POKEMON_BEAN, fields_, constructors_, methods_, AikiBeansStd.TYPE_COMMON_BEAN, MethodModifier.NORMAL);
        fields_.add(new StandardField(NAME_PK,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(ABILITIES, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(ABILITY,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(GENDERS, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(GENDER,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(LEVEL,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(TYPED_NAME,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(TYPED_TYPE,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(WHOLE_WORD,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(BOOLEANS, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(HAS_EVO,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(IS_EVO,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(IS_LEG,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(POKEDEX, BeanNatLgNames.TYPE_LIST,false,false,type_));
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(ADD,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SEARCH,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_MINI_IMAGE,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(CLICK_LINK,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        _std.getStandards().addEntry(TYPE_ADD_POKEMON_BEAN, type_);
    }
    private static void buildEditPokemonBean(PokemonStandards _std) {
        SpecialNatClass type_;
        CustList<StandardField> fields_;
        CustList<StandardConstructor> constructors_;
        CustList<StandardMethod> methods_;
        StandardMethod method_;
        StringList params_;
        methods_ = new CustList<StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        type_ = new SpecialNatClass(TYPE_EDIT_POKEMON_BEAN, fields_, constructors_, methods_, AikiBeansStd.TYPE_COMMON_BEAN, MethodModifier.NORMAL);
        fields_.add(new StandardField(LEVEL,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(NAME_PK,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(MOVES, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.add(new StandardField(EXPERIENCE,PokemonStandards.TYPE_RATE,false,false,type_));
        fields_.add(new StandardField(BALLS, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(BALL,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(HAPPINESS,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(REMAINING_HP,PokemonStandards.TYPE_RATE,false,false,type_));
        fields_.add(new StandardField(HEAL,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(EV, BeanNatLgNames.TYPE_MAP,false,false,type_));
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(TRANSLATE_NAME,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(TRANSLATE_ITEM,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CHOOSE_ITEM,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(ADD_MOVES,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(DELETE_MOVES,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(EDIT,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_TRANSLATED_STATISTIC,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        _std.getStandards().addEntry(TYPE_EDIT_POKEMON_BEAN, type_);
    }
    private static void buildEditPokemonMovesBean(PokemonStandards _std) {
        SpecialNatClass type_;
        CustList<StandardField> fields_;
        CustList<StandardConstructor> constructors_;
        CustList<StandardMethod> methods_;
        StandardMethod method_;
        StringList params_;
        methods_ = new CustList<StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        type_ = new SpecialNatClass(TYPE_EDIT_POKEMON_MOVES_BEAN, fields_, constructors_, methods_, AikiBeansStd.TYPE_COMMON_BEAN, MethodModifier.NORMAL);
        fields_.add(new StandardField(TYPED_NAME,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(CATEGORIES, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(CATEGORY,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(TYPED_TYPE,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(WHOLE_WORD,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(PLAYER,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(AVAILABLE_MOVES_ONLY,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(MOVES, BeanNatLgNames.TYPE_LIST,false,false,type_));
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SEARCH,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(ADD_MOVES,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        _std.getStandards().addEntry(TYPE_EDIT_POKEMON_MOVES_BEAN, type_);
    }
    private static void buildEditTrainerPokemonBean(PokemonStandards _std) {
        SpecialNatClass type_;
        CustList<StandardField> fields_;
        CustList<StandardConstructor> constructors_;
        CustList<StandardMethod> methods_;
        StandardMethod method_;
        StringList params_;
        methods_ = new CustList<StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        type_ = new SpecialNatClass(TYPE_EDIT_TRAINER_POKEMON_BEAN, fields_, constructors_, methods_, AikiBeansStd.TYPE_COMMON_BEAN, MethodModifier.NORMAL);
        fields_.add(new StandardField(MOVES, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.add(new StandardField(GENDERS, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(GENDER,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(LEVEL,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(ADD,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(ALLY_PK,_std.getAliasPrimBoolean(),false,false,type_));
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CHOOSE_NAME,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CHOOSE_ITEM,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CHOOSE_ABILITY,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(ADD_MOVES,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(DELETE_MOVES,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_TRAINER_PK,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_TRANSLATED_NAME,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_TRANSLATED_ABILITY,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_TRANSLATED_ITEM,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        _std.getStandards().addEntry(TYPE_EDIT_TRAINER_POKEMON_BEAN, type_);
    }
    private static void buildSelectAbilityBean(PokemonStandards _std) {
        SpecialNatClass type_;
        CustList<StandardField> fields_;
        CustList<StandardConstructor> constructors_;
        CustList<StandardMethod> methods_;
        StandardMethod method_;
        StringList params_;
        methods_ = new CustList<StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        type_ = new SpecialNatClass(TYPE_SELECT_ABILITY_BEAN, fields_, constructors_, methods_, AikiBeansStd.TYPE_COMMON_BEAN, MethodModifier.NORMAL);
        fields_.add(new StandardField(TYPED_ABILITY,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(SORTED_ABILITIES, BeanNatLgNames.TYPE_LIST,false,false,type_));
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SEARCH,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(CLICK_ABILITY,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_TR_ABILITY,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        _std.getStandards().addEntry(TYPE_SELECT_ABILITY_BEAN, type_);
    }
    private static void buildSelectItemBean(PokemonStandards _std) {
        SpecialNatClass type_;
        CustList<StandardField> fields_;
        CustList<StandardConstructor> constructors_;
        CustList<StandardMethod> methods_;
        StandardMethod method_;
        StringList params_;
        methods_ = new CustList<StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        type_ = new SpecialNatClass(TYPE_SELECT_ITEM_BEAN, fields_, constructors_, methods_, AikiBeansStd.TYPE_COMMON_BEAN, MethodModifier.NORMAL);
        fields_.add(new StandardField(TYPED_NAME,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(TYPED_PRICE,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(TYPED_CLASS,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(ITEMS, BeanNatLgNames.TYPE_LIST,false,false,type_));
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL_ITEM,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SEARCH,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_MINI_IMAGE,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(CLICK_LINK,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        _std.getStandards().addEntry(TYPE_SELECT_ITEM_BEAN, type_);
    }
    private static void buildSelectPokemonBean(PokemonStandards _std) {
        SpecialNatClass type_;
        CustList<StandardField> fields_;
        CustList<StandardConstructor> constructors_;
        CustList<StandardMethod> methods_;
        StandardMethod method_;
        StringList params_;
        methods_ = new CustList<StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        type_ = new SpecialNatClass(TYPE_SELECT_POKEMON_BEAN, fields_, constructors_, methods_, AikiBeansStd.TYPE_COMMON_BEAN, MethodModifier.NORMAL);
        fields_.add(new StandardField(TYPED_NAME,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(TYPED_TYPE,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(WHOLE_WORD,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(BOOLEANS, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(HAS_EVO,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(IS_EVO,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(IS_LEG,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(POKEDEX, BeanNatLgNames.TYPE_LIST,false,false,type_));
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SEARCH,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_MINI_IMAGE,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(CLICK_LINK,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        _std.getStandards().addEntry(TYPE_SELECT_POKEMON_BEAN, type_);
    }
    private static void buildSimulationBean(PokemonStandards _std) {
        SpecialNatClass type_;
        CustList<StandardField> fields_;
        CustList<StandardConstructor> constructors_;
        CustList<StandardMethod> methods_;
        StandardMethod method_;
        StringList params_;
        methods_ = new CustList<StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        type_ = new SpecialNatClass(TYPE_SIMULATION_BEAN, fields_, constructors_, methods_, AikiBeansStd.TYPE_COMMON_BEAN, MethodModifier.NORMAL);
        fields_.add(new StandardField(WIN_POINTS_FIGHT, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(DIFF_WINNING_EXP_PTS_FIGHT,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(ALLOW_CATCHING_KO,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(ALLOWED_SWITCH_PLACES_END_ROUND,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(WIN_TRAINER_EXP,PokemonStandards.TYPE_RATE,false,false,type_));
        fields_.add(new StandardField(RATE_WINNING_EXP_PTS_FIGHT,PokemonStandards.TYPE_RATE,false,false,type_));
        fields_.add(new StandardField(END_FIGHT_IF_ONE_TEAM_KO,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(IV_PLAYER,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(IV_FOE,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(RATE_WIN_MONEY_BASE,PokemonStandards.TYPE_RATE,false,false,type_));
        fields_.add(new StandardField(RATE_LOOSE_MONEY_WIN,PokemonStandards.TYPE_RATE,false,false,type_));
        fields_.add(new StandardField(RESTORED_MOVES_END_FIGHT,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(ENABLED_CLOSING,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(RANDOM_WILD_FIGHT,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(STILL_POSSIBLE_FLEE,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(SKIP_LEARNING_MOVES_WHILE_NOT_GROWING_LEVEL,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(DAMAGE_RATES, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(DAMAGE_RATE_PLAYER,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(DAMAGE_RATE_PLAYER_TABLE, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(DAMAGE_RATE_LAW_FOE,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(DAMAGE_RATE_FOE_TABLE, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(FREE_TEAMS,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(MULTIPLICITY,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(ENVIRONMENTS, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(ENVIRONMENT,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(FOE_TEAM, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.add(new StandardField(SELECTED_FOE_PK,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(SELECTED_FOE_ACTION,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(ALLY_TEAM, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.add(new StandardField(SELECTED_ALLY_PK,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(SELECTED_ALLY_ACTION,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(PLACES, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.add(new StandardField(OK,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(TEAM, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.add(new StandardField(SELECTED_PK,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(SELECTED_ACTION,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(AVAILABLE_EVOS, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(CHOSEN_EVO,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(LEVEL_EVO,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(ROUND, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(SELECTED_ROUND,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(PLACES_FIGHT, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(PLACE_FIGHT,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(DISPLAY_IF_ERROR,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(ABILITIES, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(CURRENT_ABILITY,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(KEPT_MOVES, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.add(new StandardField(MOVES_SET, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.add(new StandardField(SELECTED_MOVE,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(ALLY_CHOICE,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(TARGET_FIGHT, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(TARGET,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(COMMENTS, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.add(new StandardField(TEAM_AFTER_FIGHT, BeanNatLgNames.TYPE_LIST,false,false,type_));
        fields_.add(new StandardField(EVOLUTIONS_AFTER_FIGHT, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(EVOLUTION_AFTER_FIGHT,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(ABILITIES_AFTER_FIGHT, BeanNatLgNames.TYPE_MAP,false,false,type_));
        fields_.add(new StandardField(ABILITY_AFTER_FIGHT,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(KEPT_MOVES_AFTER_FIGHT, BeanNatLgNames.TYPE_LIST,false,false,type_));
        params_ = new StringList();
        method_ = new StandardMethod(GET_REAL_STEP_NUMBER,params_,_std.getAliasPrimInteger(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(QUIT,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_DIFF_STATE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_DIFF_CHOICE,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_FOE_STATE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(ADD_PK_TRAINER,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SELECT_FOE_PK,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_IMAGE_FOE,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_NAME_FOE,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_LEVEL_FOE,params_,_std.getAliasPrimInteger(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_ABILITY_FOE,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_GENDER_FOE,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_ITEM_FOE,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_MOVES_FOE,params_, BeanNatLgNames.TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SELECT_ALLY_PK,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_IMAGE_ALLY,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_NAME_ALLY,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_LEVEL_ALLY,params_,_std.getAliasPrimInteger(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_ABILITY_ALLY,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_GENDER_ALLY,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_ITEM_ALLY,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_MOVES_ALLY,params_, BeanNatLgNames.TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(IS_MULTI_LAYER,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(LAYERS,params_, BeanNatLgNames.TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger(),_std.getAliasPrimInteger());
        method_ = new StandardMethod(CLICK_LEVEL,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_TRAINER_NAME,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL_DIFF_CHOICE,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_FOE_CHOICE_FREE,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_FOE_CHOICE,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_TEAM_STATE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(ADD,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SELECT_PK,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_IMAGE,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_NAME,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_LEVEL,params_,_std.getAliasPrimInteger(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_ABILITY,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_GENDER,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_ITEM,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_MOVES,params_, BeanNatLgNames.TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL_TEAM,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_TEAM,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_FOE_CHOICE_SKIP_EVOLUTIONS,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_EVOLUTIONS_STATE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(DISPLAY_EVOLUTIONS,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SELECTED_INDEX,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_EVO,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL_EVO,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL_EVOLUTIONS,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_EVOLUTIONS,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_FRONT_STATE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_FRONT_FIGHTER,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL_FRONT_FIGHTERS,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_FRONT_FIGHTERS,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_MOVES_STATE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SELECTED_INDEX_FOR_MOVES,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_AVAILABLE_MOVES,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_MOVES,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_AVAILABLE_ABILITIES,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL_MOVES,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL_MOVES_SETS,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_MOVES_SETS,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_MOVES_FIGHT_STATE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_MOVES_CHOICE,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL_MOVES_EVOS,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SIMULATE_FIGHT,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_SIMULATION_STATE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_ISSUE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_KO_PLAYERS,params_, BeanNatLgNames.TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_NOT_KO_FRONT_FOES,params_, BeanNatLgNames.TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_KO_FOES,params_, BeanNatLgNames.TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_RULES_ISSUE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_RULES_MOVES_ISSUE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_RULES_LEARN_ISSUE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_RULES_SWITCH_ISSUE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_SENDING_ISSUE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_RANDOM_ISSUE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_USING_ISSUE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_HARD_SIMULATION_ISSUE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_ISSUE_AFTER_FIGHT,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_IMAGE_AFTER_FIGHT,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_NAME_AFTER_FIGHT,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_LEVEL_AFTER_FIGHT,params_,_std.getAliasPrimInteger(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_ABILITY_AFTER_FIGHT,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_GENDER_AFTER_FIGHT,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_ITEM_AFTER_FIGHT,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_MOVES_AFTER_FIGHT,params_, BeanNatLgNames.TYPE_LIST, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(GET_REMAINING_LIFE_RATE,params_,PokemonStandards.TYPE_LG_INT, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(NUMBER_NECESSARY_POINTS_FOR_GROWING_LEVEL,params_,PokemonStandards.TYPE_RATE, false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CHANGE_FIGHT,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_FIGHT_AFTER,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(NEXT_FIGHT,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(DISPLAY_COMMENTS,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(HIDE_COMMENTS,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(IS_EVOLUTION_AFTER_FIGHT_STATE,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(SELECT_PK_AFTER_FIGHT,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_EVOLUTION_AFTER_FIGHT,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL_EVOLUTIONS_AFTER_FIGHT,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_MOVES_ABILITY_AFTER_FIGHT,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(CHANGE_FIGHT_WHILE_END,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(VALIDATE_MOVES_AFTER_FIGHT,params_,_std.getAliasVoid(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        _std.getStandards().addEntry(TYPE_SIMULATION_BEAN, type_);
    }
    private static void buildSimulationLevelBean(PokemonStandards _std) {
        SpecialNatClass type_;
        CustList<StandardField> fields_;
        CustList<StandardConstructor> constructors_;
        CustList<StandardMethod> methods_;
        StandardMethod method_;
        StringList params_;
        methods_ = new CustList<StandardMethod>();
        constructors_ = new CustList<StandardConstructor>();
        fields_ = new CustList<StandardField>();
        type_ = new SpecialNatClass(TYPE_SIMULATION_LEVEL_BEAN, fields_, constructors_, methods_, AikiBeansStd.TYPE_COMMON_BEAN, MethodModifier.NORMAL);
        fields_.add(new StandardField(POSSIBLE_MULTI_LAYER,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(PLACE_NAME,_std.getAliasString(),false,false,type_));
        fields_.add(new StandardField(LEVEL_INDEX,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(OUTSIDE,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(ROAD,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(GYM,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(POKEMON_CENTER,_std.getAliasPrimBoolean(),false,false,type_));
        fields_.add(new StandardField(NO_FIGHT,_std.getAliasPrimInteger(),false,false,type_));
        fields_.add(new StandardField(TILES, BeanNatLgNames.TYPE_MAP,false,false,type_));
        params_ = new StringList();
        method_ = new StandardMethod(CANCEL,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList();
        method_ = new StandardMethod(GET_MAP_WIDTH,params_,_std.getAliasPrimInteger(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(IS_FIRST_ROW,params_,_std.getAliasPrimBoolean(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        params_ = new StringList(_std.getAliasPrimInteger());
        method_ = new StandardMethod(CLICK_TILE,params_,_std.getAliasString(), false, MethodModifier.NORMAL);
        methods_.add( method_);
        _std.getStandards().addEntry(TYPE_SIMULATION_LEVEL_BEAN, type_);
    }
    public static ResultErrorStd getResultAddPokemonBean(ContextEl _cont, ClassField _classField, Struct _instance) {
        BeanNatLgNames std_ = (BeanNatLgNames) _cont.getStandards();
        ResultErrorStd res_ = new ResultErrorStd();
        AddPokemonBean instance_ = (AddPokemonBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,NAME_PK)) {
            res_.setResult(new StringStruct(instance_.getNamePk()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ABILITIES)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getAbilities()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ABILITY)) {
            res_.setResult(new StringStruct(instance_.getAbility()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,GENDERS)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getGenders()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,GENDER)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getGender()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,LEVEL)) {
            res_.setResult(new IntStruct(instance_.getLevel()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TYPED_NAME)) {
            res_.setResult(new StringStruct(instance_.getTypedName()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TYPED_TYPE)) {
            res_.setResult(new StringStruct(instance_.getTypedType()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,WHOLE_WORD)) {
            res_.setResult(BooleanStruct.of(instance_.getWholeWord()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,BOOLEANS)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getBooleans()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,HAS_EVO)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getHasEvo()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,IS_EVO)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getIsEvo()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,IS_LEG)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getIsLeg()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,POKEDEX)) {
            res_.setResult(PokemonStandards.getPkLine(instance_.getPokedex()));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd getResultEditPokemonBean(ContextEl _cont, ClassField _classField, Struct _instance) {
        BeanNatLgNames std_ = (BeanNatLgNames) _cont.getStandards();
        ResultErrorStd res_ = new ResultErrorStd();
        EditPokemonBean instance_ = (EditPokemonBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,LEVEL)) {
            res_.setResult(new IntStruct(instance_.getLevel()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,NAME_PK)) {
            res_.setResult(new StringStruct(instance_.getNamePk()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,MOVES)) {
            res_.setResult(PokemonStandards.getSelectLineMove(instance_.getMoves()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,EXPERIENCE)) {
            res_.setResult(new RateStruct(instance_.getExperience(),PokemonStandards.TYPE_RATE));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,BALLS)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getBalls()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,BALL)) {
            res_.setResult(new StringStruct(instance_.getBall()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,HAPPINESS)) {
            res_.setResult(new IntStruct(instance_.getHappiness()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,REMAINING_HP)) {
            res_.setResult(new RateStruct(instance_.getRemainingHp(),PokemonStandards.TYPE_RATE));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,HEAL)) {
            res_.setResult(BooleanStruct.of(instance_.getHeal()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,EV)) {
            res_.setResult(PokemonStandards.getEvLine(_cont,instance_.getEv()));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd getResultEditPokemonMovesBean(ContextEl _cont, ClassField _classField, Struct _instance) {
        BeanNatLgNames std_ = (BeanNatLgNames) _cont.getStandards();
        ResultErrorStd res_ = new ResultErrorStd();
        EditPokemonMovesBean instance_ = (EditPokemonMovesBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,TYPED_NAME)) {
            res_.setResult(new StringStruct(instance_.getTypedName()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,CATEGORIES)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getCategories()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,CATEGORY)) {
            res_.setResult(new StringStruct(instance_.getCategory()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TYPED_TYPE)) {
            res_.setResult(new StringStruct(instance_.getTypedType()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,WHOLE_WORD)) {
            res_.setResult(BooleanStruct.of(instance_.getWholeWord()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,PLAYER)) {
            res_.setResult(BooleanStruct.of(instance_.getPlayer()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,AVAILABLE_MOVES_ONLY)) {
            res_.setResult(BooleanStruct.of(instance_.getAvailableMovesOnly()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,MOVES)) {
            res_.setResult(PokemonStandards.getSelectLineMove(instance_.getMoves()));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd getResultEditTrainerPokemonBean(ContextEl _cont, ClassField _classField, Struct _instance) {
        BeanNatLgNames std_ = (BeanNatLgNames) _cont.getStandards();
        ResultErrorStd res_ = new ResultErrorStd();
        EditTrainerPokemonBean instance_ = (EditTrainerPokemonBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,MOVES)) {
            res_.setResult(PokemonStandards.getSelectLineMove(instance_.getMoves()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,GENDERS)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getGenders()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,GENDER)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getGender()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,LEVEL)) {
            res_.setResult(new IntStruct(instance_.getLevel()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ADD)) {
            res_.setResult(BooleanStruct.of(instance_.getAdd()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ALLY_PK)) {
            res_.setResult(BooleanStruct.of(instance_.getAllyPk()));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd getResultSelectAbilityBean(ContextEl _cont, ClassField _classField, Struct _instance) {
        BeanNatLgNames std_ = (BeanNatLgNames) _cont.getStandards();
        ResultErrorStd res_ = new ResultErrorStd();
        SelectAbilityBean instance_ = (SelectAbilityBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,TYPED_ABILITY)) {
            res_.setResult(new StringStruct(instance_.getTypedAbility()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SORTED_ABILITIES)) {
            res_.setResult(std_.getStringArray(instance_.getSortedAbilities()));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd getResultSelectItemBean(ContextEl _cont, ClassField _classField, Struct _instance) {
        BeanNatLgNames std_ = (BeanNatLgNames) _cont.getStandards();
        ResultErrorStd res_ = new ResultErrorStd();
        SelectItemBean instance_ = (SelectItemBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,TYPED_NAME)) {
            res_.setResult(new StringStruct(instance_.getTypedName()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TYPED_PRICE)) {
            res_.setResult(new StringStruct(instance_.getTypedPrice()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TYPED_CLASS)) {
            res_.setResult(new StringStruct(instance_.getTypedClass()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ITEMS)) {
            res_.setResult(PokemonStandards.getItLine(instance_.getItems()));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd getResultSelectPokemonBean(ContextEl _cont, ClassField _classField, Struct _instance) {
        BeanNatLgNames std_ = (BeanNatLgNames) _cont.getStandards();
        ResultErrorStd res_ = new ResultErrorStd();
        SelectPokemonBean instance_ = (SelectPokemonBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,TYPED_NAME)) {
            res_.setResult(new StringStruct(instance_.getTypedName()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TYPED_TYPE)) {
            res_.setResult(new StringStruct(instance_.getTypedType()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,WHOLE_WORD)) {
            res_.setResult(BooleanStruct.of(instance_.getWholeWord()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,BOOLEANS)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getBooleans()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,HAS_EVO)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getHasEvo()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,IS_EVO)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getIsEvo()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,IS_LEG)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getIsLeg()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,POKEDEX)) {
            res_.setResult(PokemonStandards.getPkLine(instance_.getPokedex()));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd getResultSimulationBean(ContextEl _cont, ClassField _classField, Struct _instance) {
        BeanNatLgNames std_ = (BeanNatLgNames) _cont.getStandards();
        ResultErrorStd res_ = new ResultErrorStd();
        SimulationBean instance_ = (SimulationBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,WIN_POINTS_FIGHT)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getWinPointsFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,DIFF_WINNING_EXP_PTS_FIGHT)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getDiffWinningExpPtsFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ALLOW_CATCHING_KO)) {
            res_.setResult(BooleanStruct.of(instance_.getAllowCatchingKo()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ALLOWED_SWITCH_PLACES_END_ROUND)) {
            res_.setResult(BooleanStruct.of(instance_.getAllowedSwitchPlacesEndRound()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,WIN_TRAINER_EXP)) {
            res_.setResult(new RateStruct(instance_.getWinTrainerExp(),PokemonStandards.TYPE_RATE));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,RATE_WINNING_EXP_PTS_FIGHT)) {
            res_.setResult(new RateStruct(instance_.getRateWinningExpPtsFight(),PokemonStandards.TYPE_RATE));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,END_FIGHT_IF_ONE_TEAM_KO)) {
            res_.setResult(BooleanStruct.of(instance_.getEndFightIfOneTeamKo()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,IV_PLAYER)) {
            res_.setResult(new IntStruct(instance_.getIvPlayer()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,IV_FOE)) {
            res_.setResult(new IntStruct(instance_.getIvFoe()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,RATE_WIN_MONEY_BASE)) {
            res_.setResult(new RateStruct(instance_.getRateWinMoneyBase(),PokemonStandards.TYPE_RATE));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,RATE_LOOSE_MONEY_WIN)) {
            res_.setResult(new RateStruct(instance_.getRateLooseMoneyWin(),PokemonStandards.TYPE_RATE));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,RESTORED_MOVES_END_FIGHT)) {
            res_.setResult(BooleanStruct.of(instance_.getRestoredMovesEndFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ENABLED_CLOSING)) {
            res_.setResult(BooleanStruct.of(instance_.getEnabledClosing()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,RANDOM_WILD_FIGHT)) {
            res_.setResult(BooleanStruct.of(instance_.getRandomWildFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,STILL_POSSIBLE_FLEE)) {
            res_.setResult(BooleanStruct.of(instance_.getStillPossibleFlee()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SKIP_LEARNING_MOVES_WHILE_NOT_GROWING_LEVEL)) {
            res_.setResult(BooleanStruct.of(instance_.getSkipLearningMovesWhileNotGrowingLevel()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,DAMAGE_RATES)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getDamageRates()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,DAMAGE_RATE_PLAYER)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getDamageRatePlayer()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,DAMAGE_RATE_PLAYER_TABLE)) {
            res_.setResult(PokemonStandards.getRateRate(_cont,instance_.getDamageRatePlayerTable()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,DAMAGE_RATE_LAW_FOE)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getDamageRateLawFoe()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,DAMAGE_RATE_FOE_TABLE)) {
            res_.setResult(PokemonStandards.getRateRate(_cont,instance_.getDamageRateFoeTable()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,FREE_TEAMS)) {
            res_.setResult(BooleanStruct.of(instance_.getFreeTeams()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,MULTIPLICITY)) {
            res_.setResult(new IntStruct(instance_.getMultiplicity()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ENVIRONMENTS)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getEnvironments()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ENVIRONMENT)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getEnvironment()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,FOE_TEAM)) {
            res_.setResult(PokemonStandards.getPkTrDto(instance_.getFoeTeam()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_FOE_PK)) {
            res_.setResult(new IntStruct(instance_.getSelectedFoePk()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_FOE_ACTION)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getSelectedFoeAction()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ALLY_TEAM)) {
            res_.setResult(PokemonStandards.getPkTrDto(instance_.getAllyTeam()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_ALLY_PK)) {
            res_.setResult(new IntStruct(instance_.getSelectedAllyPk()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_ALLY_ACTION)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getSelectedAllyAction()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,PLACES)) {
            res_.setResult(PokemonStandards.getPlInd(instance_.getPlaces()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,OK)) {
            res_.setResult(BooleanStruct.of(instance_.getOk()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TEAM)) {
            res_.setResult(PokemonStandards.getPkPlDto(instance_.getTeam()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_PK)) {
            res_.setResult(new IntStruct(instance_.getSelectedPk()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_ACTION)) {
            res_.setResult(BeanLgNames.wrapStd(instance_.getSelectedAction()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,AVAILABLE_EVOS)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getAvailableEvos()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,CHOSEN_EVO)) {
            res_.setResult(new StringStruct(instance_.getChosenEvo()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,LEVEL_EVO)) {
            res_.setResult(new IntStruct(instance_.getLevelEvo()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ROUND)) {
            res_.setResult(PokemonStandards.getIntIntMap(_cont,instance_.getRound()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_ROUND)) {
            res_.setResult(new StringStruct(instance_.getSelectedRound()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,PLACES_FIGHT)) {
            res_.setResult(PokemonStandards.getIntStr(_cont,instance_.getPlacesFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,PLACE_FIGHT)) {
            res_.setResult(new StringStruct(instance_.getPlaceFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,DISPLAY_IF_ERROR)) {
            res_.setResult(BooleanStruct.of(instance_.getDisplayIfError()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ABILITIES)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getAbilities()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,CURRENT_ABILITY)) {
            res_.setResult(new StringStruct(instance_.getCurrentAbility()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,KEPT_MOVES)) {
            res_.setResult(PokemonStandards.getSelectLineMove(instance_.getKeptMoves()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,MOVES_SET)) {
            res_.setResult(PokemonStandards.getRdMvLine(instance_.getMovesSet()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_MOVE)) {
            res_.setResult(new IntStruct(instance_.getSelectedMove()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ALLY_CHOICE)) {
            res_.setResult(BooleanStruct.of(instance_.getAllyChoice()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TARGET_FIGHT)) {
            res_.setResult(PokemonStandards.getIntStr(_cont,instance_.getTargetFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TARGET)) {
            res_.setResult(new StringStruct(instance_.getTarget()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,COMMENTS)) {
            res_.setResult(std_.getStringArray(instance_.getComments()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TEAM_AFTER_FIGHT)) {
            res_.setResult(PokemonStandards.getPkPlayerArray(instance_.getTeamAfterFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,EVOLUTIONS_AFTER_FIGHT)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getEvolutionsAfterFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,EVOLUTION_AFTER_FIGHT)) {
            res_.setResult(new StringStruct(instance_.getEvolutionAfterFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ABILITIES_AFTER_FIGHT)) {
            res_.setResult(PokemonStandards.getStrStr(_cont,instance_.getAbilitiesAfterFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ABILITY_AFTER_FIGHT)) {
            res_.setResult(new StringStruct(instance_.getAbilityAfterFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,KEPT_MOVES_AFTER_FIGHT)) {
            res_.setResult(PokemonStandards.getSelectLineMove(instance_.getKeptMovesAfterFight()));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd getResultSimulationLevelBean(ContextEl _cont, ClassField _classField, Struct _instance) {
        BeanNatLgNames std_ = (BeanNatLgNames) _cont.getStandards();
        ResultErrorStd res_ = new ResultErrorStd();
        SimulationLevelBean instance_ = (SimulationLevelBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,POSSIBLE_MULTI_LAYER)) {
            res_.setResult(BooleanStruct.of(instance_.getPossibleMultiLayer()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,PLACE_NAME)) {
            res_.setResult(new StringStruct(instance_.getPlaceName()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,LEVEL_INDEX)) {
            res_.setResult(new IntStruct(instance_.getLevelIndex()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,OUTSIDE)) {
            res_.setResult(BooleanStruct.of(instance_.getOutside()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ROAD)) {
            res_.setResult(BooleanStruct.of(instance_.getRoad()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,GYM)) {
            res_.setResult(BooleanStruct.of(instance_.getGym()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,POKEMON_CENTER)) {
            res_.setResult(BooleanStruct.of(instance_.getPokemonCenter()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,NO_FIGHT)) {
            res_.setResult(new IntStruct(instance_.getNoFight()));
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TILES)) {
            res_.setResult(PokemonStandards.getPtStr(_cont,instance_.getTiles()));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd setResultAddPokemonBean(ContextEl _cont, ClassField _classField, Struct _instance, Struct _val) {
        ResultErrorStd res_ = new ResultErrorStd();
        AddPokemonBean instance_ = (AddPokemonBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,ABILITY)) {
            instance_.setAbility(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,GENDER)) {
            instance_.setGender(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,LEVEL)) {
            instance_.setLevel(NumParsers.convertToNumber(_val).intStruct());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TYPED_NAME)) {
            instance_.setTypedName(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TYPED_TYPE)) {
            instance_.setTypedType(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,WHOLE_WORD)) {
            instance_.setWholeWord(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,HAS_EVO)) {
            instance_.setHasEvo(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,IS_EVO)) {
            instance_.setIsEvo(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,IS_LEG)) {
            instance_.setIsLeg(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd setResultEditPokemonBean(ContextEl _cont, ClassField _classField, Struct _instance, Struct _val) {
        ResultErrorStd res_ = new ResultErrorStd();
        EditPokemonBean instance_ = (EditPokemonBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,EXPERIENCE)) {
            instance_.setExperience(PokemonStandards.convertToRate(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,BALL)) {
            instance_.setBall(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,HAPPINESS)) {
            instance_.setHappiness(NumParsers.convertToNumber(_val).intStruct());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,REMAINING_HP)) {
            instance_.setRemainingHp(PokemonStandards.convertToRate(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,HEAL)) {
            instance_.setHeal(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd setResultEditPokemonMovesBean(ContextEl _cont, ClassField _classField, Struct _instance, Struct _val) {
        ResultErrorStd res_ = new ResultErrorStd();
        EditPokemonMovesBean instance_ = (EditPokemonMovesBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,TYPED_NAME)) {
            instance_.setTypedName(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,CATEGORY)) {
            instance_.setCategory(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TYPED_TYPE)) {
            instance_.setTypedType(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,WHOLE_WORD)) {
            instance_.setWholeWord(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,AVAILABLE_MOVES_ONLY)) {
            instance_.setAvailableMovesOnly(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd setResultEditTrainerPokemonBean(ContextEl _cont, ClassField _classField, Struct _instance, Struct _val) {
        ResultErrorStd res_ = new ResultErrorStd();
        EditTrainerPokemonBean instance_ = (EditTrainerPokemonBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,GENDER)) {
            instance_.setGender(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,LEVEL)) {
            instance_.setLevel(NumParsers.convertToNumber(_val).intStruct());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ALLY_PK)) {
            instance_.setAllyPk(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd setResultSelectAbilityBean(ContextEl _cont, ClassField _classField, Struct _instance, Struct _val) {
        ResultErrorStd res_ = new ResultErrorStd();
        SelectAbilityBean instance_ = (SelectAbilityBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,TYPED_ABILITY)) {
            instance_.setTypedAbility(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd setResultSelectItemBean(ContextEl _cont, ClassField _classField, Struct _instance, Struct _val) {
        ResultErrorStd res_ = new ResultErrorStd();
        SelectItemBean instance_ = (SelectItemBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,TYPED_NAME)) {
            instance_.setTypedName(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TYPED_PRICE)) {
            instance_.setTypedPrice(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TYPED_CLASS)) {
            instance_.setTypedClass(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd setResultSelectPokemonBean(ContextEl _cont, ClassField _classField, Struct _instance, Struct _val) {
        ResultErrorStd res_ = new ResultErrorStd();
        SelectPokemonBean instance_ = (SelectPokemonBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,TYPED_NAME)) {
            instance_.setTypedName(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TYPED_TYPE)) {
            instance_.setTypedType(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,WHOLE_WORD)) {
            instance_.setWholeWord(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,HAS_EVO)) {
            instance_.setHasEvo(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,IS_EVO)) {
            instance_.setIsEvo(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,IS_LEG)) {
            instance_.setIsLeg(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd setResultSimulationBean(ContextEl _cont, ClassField _classField, Struct _instance, Struct _val) {
        ResultErrorStd res_ = new ResultErrorStd();
        SimulationBean instance_ = (SimulationBean) ((PokemonBeanStruct)_instance).getInstance();
        String fieldName_ = _classField.getFieldName();
        if (StringUtil.quickEq(fieldName_,DIFF_WINNING_EXP_PTS_FIGHT)) {
            instance_.setDiffWinningExpPtsFight(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ALLOW_CATCHING_KO)) {
            instance_.setAllowCatchingKo(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ALLOWED_SWITCH_PLACES_END_ROUND)) {
            instance_.setAllowedSwitchPlacesEndRound(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,WIN_TRAINER_EXP)) {
            instance_.setWinTrainerExp(PokemonStandards.convertToRate(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,RATE_WINNING_EXP_PTS_FIGHT)) {
            instance_.setRateWinningExpPtsFight(PokemonStandards.convertToRate(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,END_FIGHT_IF_ONE_TEAM_KO)) {
            instance_.setEndFightIfOneTeamKo(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,IV_PLAYER)) {
            instance_.setIvPlayer(NumParsers.convertToNumber(_val).intStruct());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,IV_FOE)) {
            instance_.setIvFoe(NumParsers.convertToNumber(_val).intStruct());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,RATE_WIN_MONEY_BASE)) {
            instance_.setRateWinMoneyBase(PokemonStandards.convertToRate(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,RATE_LOOSE_MONEY_WIN)) {
            instance_.setRateLooseMoneyWin(PokemonStandards.convertToRate(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,RESTORED_MOVES_END_FIGHT)) {
            instance_.setRestoredMovesEndFight(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ENABLED_CLOSING)) {
            instance_.setEnabledClosing(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,RANDOM_WILD_FIGHT)) {
            instance_.setRandomWildFight(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,STILL_POSSIBLE_FLEE)) {
            instance_.setStillPossibleFlee(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SKIP_LEARNING_MOVES_WHILE_NOT_GROWING_LEVEL)) {
            instance_.setSkipLearningMovesWhileNotGrowingLevel(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,DAMAGE_RATE_PLAYER)) {
            instance_.setDamageRatePlayer(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,DAMAGE_RATE_LAW_FOE)) {
            instance_.setDamageRateLawFoe(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,FREE_TEAMS)) {
            instance_.setFreeTeams(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,MULTIPLICITY)) {
            instance_.setMultiplicity(NumParsers.convertToNumber(_val).intStruct());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ENVIRONMENT)) {
            instance_.setEnvironment(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_FOE_PK)) {
            instance_.setSelectedFoePk(NumParsers.convertToNumber(_val).intStruct());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_FOE_ACTION)) {
            instance_.setSelectedFoeAction(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_ALLY_PK)) {
            instance_.setSelectedAllyPk(NumParsers.convertToNumber(_val).intStruct());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_ALLY_ACTION)) {
            instance_.setSelectedAllyAction(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_PK)) {
            instance_.setSelectedPk(NumParsers.convertToNumber(_val).intStruct());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_ACTION)) {
            instance_.setSelectedAction(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,CHOSEN_EVO)) {
            instance_.setChosenEvo(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,LEVEL_EVO)) {
            instance_.setLevelEvo(NumParsers.convertToNumber(_val).intStruct());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_ROUND)) {
            instance_.setSelectedRound(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,PLACE_FIGHT)) {
            instance_.setPlaceFight(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,CURRENT_ABILITY)) {
            instance_.setCurrentAbility(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,SELECTED_MOVE)) {
            instance_.setSelectedMove(NumParsers.convertToNumber(_val).intStruct());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ALLY_CHOICE)) {
            instance_.setAllyChoice(BooleanStruct.isTrue(_val));
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,TARGET)) {
            instance_.setTarget(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,EVOLUTION_AFTER_FIGHT)) {
            instance_.setEvolutionAfterFight(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(fieldName_,ABILITY_AFTER_FIGHT)) {
            instance_.setAbilityAfterFight(NumParsers.getString(_val).getInstance());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd invokeMethodAddPokemonBean(ContextEl _cont, Struct _instance, ClassMethodId _method, Struct... _args) {
        AddPokemonBean instance_ = (AddPokemonBean) ((PokemonBeanStruct)_instance).getInstance();
        String methodName_ = _method.getConstraints().getName();
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringUtil.quickEq(methodName_,CANCEL)) {
            res_.setResult(new StringStruct(AddPokemonBean.cancel()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,ADD)) {
            res_.setResult(new StringStruct(instance_.add()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,SEARCH)) {
            instance_.search();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_MINI_IMAGE)) {
            res_.setResult(new StringStruct(instance_.getMiniImage(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CLICK_LINK)) {
            instance_.clickLink(NumParsers.convertToNumber(_args[0]).intStruct());
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd invokeMethodEditPokemonBean(ContextEl _cont, Struct _instance, ClassMethodId _method, Struct... _args) {
        EditPokemonBean instance_ = (EditPokemonBean) ((PokemonBeanStruct)_instance).getInstance();
        String methodName_ = _method.getConstraints().getName();
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringUtil.quickEq(methodName_,CANCEL)) {
            res_.setResult(new StringStruct(EditPokemonBean.cancel()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,TRANSLATE_NAME)) {
            res_.setResult(new StringStruct(instance_.translateName()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,TRANSLATE_ITEM)) {
            res_.setResult(new StringStruct(instance_.translateItem()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CHOOSE_ITEM)) {
            res_.setResult(new StringStruct(instance_.chooseItem()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,ADD_MOVES)) {
            res_.setResult(new StringStruct(instance_.addMoves()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,DELETE_MOVES)) {
            instance_.deleteMoves();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,EDIT)) {
            res_.setResult(new StringStruct(instance_.edit()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_TRANSLATED_STATISTIC)) {
            res_.setResult(new StringStruct(instance_.getTranslatedStatistic(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd invokeMethodEditPokemonMovesBean(ContextEl _cont, Struct _instance, ClassMethodId _method, Struct... _args) {
        EditPokemonMovesBean instance_ = (EditPokemonMovesBean) ((PokemonBeanStruct)_instance).getInstance();
        String methodName_ = _method.getConstraints().getName();
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringUtil.quickEq(methodName_,CANCEL)) {
            res_.setResult(new StringStruct(instance_.cancel()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,SEARCH)) {
            instance_.search();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,ADD_MOVES)) {
            res_.setResult(new StringStruct(instance_.addMoves()));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd invokeMethodEditTrainerPokemonBean(ContextEl _cont, Struct _instance, ClassMethodId _method, Struct... _args) {
        EditTrainerPokemonBean instance_ = (EditTrainerPokemonBean) ((PokemonBeanStruct)_instance).getInstance();
        String methodName_ = _method.getConstraints().getName();
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringUtil.quickEq(methodName_,CANCEL)) {
            res_.setResult(new StringStruct(instance_.cancel()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CHOOSE_NAME)) {
            res_.setResult(new StringStruct(instance_.chooseName()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CHOOSE_ITEM)) {
            res_.setResult(new StringStruct(instance_.chooseItem()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CHOOSE_ABILITY)) {
            res_.setResult(new StringStruct(instance_.chooseAbility()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,ADD_MOVES)) {
            res_.setResult(new StringStruct(instance_.addMoves()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,DELETE_MOVES)) {
            instance_.deleteMoves();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_TRAINER_PK)) {
            res_.setResult(new StringStruct(instance_.validateTrainerPk()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_TRANSLATED_NAME)) {
            res_.setResult(new StringStruct(instance_.getTranslatedName()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_TRANSLATED_ABILITY)) {
            res_.setResult(new StringStruct(instance_.getTranslatedAbility()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_TRANSLATED_ITEM)) {
            res_.setResult(new StringStruct(instance_.getTranslatedItem()));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd invokeMethodSelectAbilityBean(ContextEl _cont, Struct _instance, ClassMethodId _method, Struct... _args) {
        SelectAbilityBean instance_ = (SelectAbilityBean) ((PokemonBeanStruct)_instance).getInstance();
        String methodName_ = _method.getConstraints().getName();
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringUtil.quickEq(methodName_,CANCEL)) {
            res_.setResult(new StringStruct(SelectAbilityBean.cancel()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,SEARCH)) {
            res_.setResult(new StringStruct(instance_.search()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CLICK_ABILITY)) {
            res_.setResult(new StringStruct(instance_.clickAbility(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_TR_ABILITY)) {
            res_.setResult(new StringStruct(instance_.getTrAbility(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd invokeMethodSelectItemBean(ContextEl _cont, Struct _instance, ClassMethodId _method, Struct... _args) {
        SelectItemBean instance_ = (SelectItemBean) ((PokemonBeanStruct)_instance).getInstance();
        String methodName_ = _method.getConstraints().getName();
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringUtil.quickEq(methodName_,CANCEL)) {
            res_.setResult(new StringStruct(instance_.cancel()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CANCEL_ITEM)) {
            res_.setResult(new StringStruct(instance_.cancelItem()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,SEARCH)) {
            res_.setResult(new StringStruct(instance_.search()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_MINI_IMAGE)) {
            res_.setResult(new StringStruct(instance_.getMiniImage(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CLICK_LINK)) {
            res_.setResult(new StringStruct(instance_.clickLink(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd invokeMethodSelectPokemonBean(ContextEl _cont, Struct _instance, ClassMethodId _method, Struct... _args) {
        SelectPokemonBean instance_ = (SelectPokemonBean) ((PokemonBeanStruct)_instance).getInstance();
        String methodName_ = _method.getConstraints().getName();
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringUtil.quickEq(methodName_,CANCEL)) {
            res_.setResult(new StringStruct(SelectPokemonBean.cancel()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,SEARCH)) {
            res_.setResult(new StringStruct(instance_.search()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_MINI_IMAGE)) {
            res_.setResult(new StringStruct(instance_.getMiniImage(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CLICK_LINK)) {
            res_.setResult(new StringStruct(instance_.clickLink(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd invokeMethodSimulationBean(ContextEl _cont, Struct _instance, ClassMethodId _method, Struct... _args) {
        BeanNatLgNames std_ = (BeanNatLgNames) _cont.getStandards();
        SimulationBean instance_ = (SimulationBean) ((PokemonBeanStruct)_instance).getInstance();
        String methodName_ = _method.getConstraints().getName();
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringUtil.quickEq(methodName_,GET_REAL_STEP_NUMBER)) {
            res_.setResult(new IntStruct(instance_.getRealStepNumber()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,QUIT)) {
            res_.setResult(new StringStruct(instance_.quit()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_DIFF_STATE)) {
            res_.setResult(BooleanStruct.of(instance_.isDiffState()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_DIFF_CHOICE)) {
            instance_.validateDiffChoice();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_FOE_STATE)) {
            res_.setResult(BooleanStruct.of(instance_.isFoeState()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,ADD_PK_TRAINER)) {
            res_.setResult(new StringStruct(instance_.addPkTrainer()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,SELECT_FOE_PK)) {
            res_.setResult(new StringStruct(instance_.selectFoePk()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_IMAGE_FOE)) {
            res_.setResult(new StringStruct(instance_.getImageFoe(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_NAME_FOE)) {
            res_.setResult(new StringStruct(instance_.getNameFoe(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_LEVEL_FOE)) {
            res_.setResult(new IntStruct(instance_.getLevelFoe(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_ABILITY_FOE)) {
            res_.setResult(new StringStruct(instance_.getAbilityFoe(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_GENDER_FOE)) {
            res_.setResult(new StringStruct(instance_.getGenderFoe(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_ITEM_FOE)) {
            res_.setResult(new StringStruct(instance_.getItemFoe(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_MOVES_FOE)) {
            res_.setResult(std_.getStringArray(instance_.getMovesFoe(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,SELECT_ALLY_PK)) {
            res_.setResult(new StringStruct(instance_.selectAllyPk()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_IMAGE_ALLY)) {
            res_.setResult(new StringStruct(instance_.getImageAlly(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_NAME_ALLY)) {
            res_.setResult(new StringStruct(instance_.getNameAlly(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_LEVEL_ALLY)) {
            res_.setResult(new IntStruct(instance_.getLevelAlly(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_ABILITY_ALLY)) {
            res_.setResult(new StringStruct(instance_.getAbilityAlly(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_GENDER_ALLY)) {
            res_.setResult(new StringStruct(instance_.getGenderAlly(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_ITEM_ALLY)) {
            res_.setResult(new StringStruct(instance_.getItemAlly(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_MOVES_ALLY)) {
            res_.setResult(std_.getStringArray(instance_.getMovesAlly(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_MULTI_LAYER)) {
            res_.setResult(BooleanStruct.of(instance_.isMultiLayer(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,LAYERS)) {
            res_.setResult(PokemonStandards.getLayers(_cont,instance_.layers(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CLICK_LEVEL)) {
            res_.setResult(new StringStruct(instance_.clickLevel(NumParsers.convertToNumber(_args[0]).intStruct(),NumParsers.convertToNumber(_args[1]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_TRAINER_NAME)) {
            res_.setResult(new StringStruct(instance_.getTrainerName()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CANCEL_DIFF_CHOICE)) {
            instance_.cancelDiffChoice();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_FOE_CHOICE_FREE)) {
            instance_.validateFoeChoiceFree();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_FOE_CHOICE)) {
            instance_.validateFoeChoice();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_TEAM_STATE)) {
            res_.setResult(BooleanStruct.of(instance_.isTeamState()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,ADD)) {
            res_.setResult(new StringStruct(instance_.add()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,SELECT_PK)) {
            res_.setResult(new StringStruct(instance_.selectPk()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_IMAGE)) {
            res_.setResult(new StringStruct(instance_.getImage(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_NAME)) {
            res_.setResult(new StringStruct(instance_.getName(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_LEVEL)) {
            res_.setResult(new IntStruct(instance_.getLevel(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_ABILITY)) {
            res_.setResult(new StringStruct(instance_.getAbility(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_GENDER)) {
            res_.setResult(new StringStruct(instance_.getGender(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_ITEM)) {
            res_.setResult(new StringStruct(instance_.getItem(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_MOVES)) {
            res_.setResult(std_.getStringArray(instance_.getMoves(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CANCEL_TEAM)) {
            instance_.cancelTeam();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_TEAM)) {
            instance_.validateTeam();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_FOE_CHOICE_SKIP_EVOLUTIONS)) {
            instance_.validateFoeChoiceSkipEvolutions();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_EVOLUTIONS_STATE)) {
            res_.setResult(BooleanStruct.of(instance_.isEvolutionsState()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,DISPLAY_EVOLUTIONS)) {
            instance_.displayEvolutions();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,SELECTED_INDEX)) {
            res_.setResult(BooleanStruct.of(instance_.selectedIndex()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_EVO)) {
            instance_.validateEvo();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CANCEL_EVO)) {
            instance_.cancelEvo();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CANCEL_EVOLUTIONS)) {
            instance_.cancelEvolutions();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_EVOLUTIONS)) {
            instance_.validateEvolutions();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_FRONT_STATE)) {
            res_.setResult(BooleanStruct.of(instance_.isFrontState()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_FRONT_FIGHTER)) {
            instance_.validateFrontFighter();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CANCEL_FRONT_FIGHTERS)) {
            instance_.cancelFrontFighters();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_FRONT_FIGHTERS)) {
            instance_.validateFrontFighters();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_MOVES_STATE)) {
            res_.setResult(BooleanStruct.of(instance_.isMovesState()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,SELECTED_INDEX_FOR_MOVES)) {
            res_.setResult(BooleanStruct.of(instance_.selectedIndexForMoves()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_AVAILABLE_MOVES)) {
            res_.setResult(BooleanStruct.of(instance_.isAvailableMoves()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_MOVES)) {
            instance_.validateMoves();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_AVAILABLE_ABILITIES)) {
            res_.setResult(BooleanStruct.of(instance_.isAvailableAbilities()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CANCEL_MOVES)) {
            instance_.cancelMoves();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CANCEL_MOVES_SETS)) {
            instance_.cancelMovesSets();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_MOVES_SETS)) {
            instance_.validateMovesSets();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_MOVES_FIGHT_STATE)) {
            res_.setResult(BooleanStruct.of(instance_.isMovesFightState()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_MOVES_CHOICE)) {
            instance_.validateMovesChoice();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CANCEL_MOVES_EVOS)) {
            instance_.cancelMovesEvos();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,SIMULATE_FIGHT)) {
            instance_.simulateFight();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_SIMULATION_STATE)) {
            res_.setResult(BooleanStruct.of(instance_.isSimulationState()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_ISSUE)) {
            res_.setResult(BooleanStruct.of(instance_.isIssue()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_KO_PLAYERS)) {
            res_.setResult(std_.getStringArray(instance_.getKoPlayers()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_NOT_KO_FRONT_FOES)) {
            res_.setResult(std_.getStringArray(instance_.getNotKoFrontFoes()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_KO_FOES)) {
            res_.setResult(std_.getStringArray(instance_.getKoFoes()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_RULES_ISSUE)) {
            res_.setResult(BooleanStruct.of(instance_.isRulesIssue()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_RULES_MOVES_ISSUE)) {
            res_.setResult(BooleanStruct.of(instance_.isRulesMovesIssue()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_RULES_LEARN_ISSUE)) {
            res_.setResult(BooleanStruct.of(instance_.isRulesLearnIssue()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_RULES_SWITCH_ISSUE)) {
            res_.setResult(BooleanStruct.of(instance_.isRulesSwitchIssue()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_SENDING_ISSUE)) {
            res_.setResult(BooleanStruct.of(instance_.isSendingIssue()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_RANDOM_ISSUE)) {
            res_.setResult(BooleanStruct.of(instance_.isRandomIssue()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_USING_ISSUE)) {
            res_.setResult(BooleanStruct.of(instance_.isUsingIssue()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_HARD_SIMULATION_ISSUE)) {
            res_.setResult(BooleanStruct.of(instance_.isHardSimulationIssue()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_ISSUE_AFTER_FIGHT)) {
            res_.setResult(BooleanStruct.of(instance_.isIssueAfterFight()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_IMAGE_AFTER_FIGHT)) {
            res_.setResult(new StringStruct(instance_.getImageAfterFight(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_NAME_AFTER_FIGHT)) {
            res_.setResult(new StringStruct(instance_.getNameAfterFight(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_LEVEL_AFTER_FIGHT)) {
            res_.setResult(new IntStruct(instance_.getLevelAfterFight(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_ABILITY_AFTER_FIGHT)) {
            res_.setResult(new StringStruct(instance_.getAbilityAfterFight(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_GENDER_AFTER_FIGHT)) {
            res_.setResult(new StringStruct(instance_.getGenderAfterFight(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_ITEM_AFTER_FIGHT)) {
            res_.setResult(new StringStruct(instance_.getItemAfterFight(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_MOVES_AFTER_FIGHT)) {
            res_.setResult(std_.getStringArray(instance_.getMovesAfterFight(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_REMAINING_LIFE_RATE)) {
            res_.setResult(new LgIntStruct(instance_.getRemainingLifeRate(NumParsers.convertToNumber(_args[0]).intStruct()),PokemonStandards.TYPE_LG_INT));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,NUMBER_NECESSARY_POINTS_FOR_GROWING_LEVEL)) {
            res_.setResult(new RateStruct(instance_.numberNecessaryPointsForGrowingLevel(NumParsers.convertToNumber(_args[0]).intStruct()),PokemonStandards.TYPE_RATE));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CHANGE_FIGHT)) {
            instance_.changeFight();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_FIGHT_AFTER)) {
            res_.setResult(BooleanStruct.of(instance_.isFightAfter()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,NEXT_FIGHT)) {
            instance_.nextFight();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,DISPLAY_COMMENTS)) {
            instance_.displayComments();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,HIDE_COMMENTS)) {
            instance_.hideComments();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_EVOLUTION_AFTER_FIGHT_STATE)) {
            res_.setResult(BooleanStruct.of(instance_.isEvolutionAfterFightState()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,SELECT_PK_AFTER_FIGHT)) {
            instance_.selectPkAfterFight();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_EVOLUTION_AFTER_FIGHT)) {
            instance_.validateEvolutionAfterFight();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CANCEL_EVOLUTIONS_AFTER_FIGHT)) {
            instance_.cancelEvolutionsAfterFight();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_MOVES_ABILITY_AFTER_FIGHT)) {
            instance_.validateMovesAbilityAfterFight();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CHANGE_FIGHT_WHILE_END)) {
            instance_.changeFightWhileEnd();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        if (StringUtil.quickEq(methodName_,VALIDATE_MOVES_AFTER_FIGHT)) {
            instance_.validateMovesAfterFight();
            res_.setResult(NullStruct.NULL_VALUE);
            return res_;
        }
        return res_;
    }
    public static ResultErrorStd invokeMethodSimulationLevelBean(ContextEl _cont, Struct _instance, ClassMethodId _method, Struct... _args) {
        SimulationLevelBean instance_ = (SimulationLevelBean) ((PokemonBeanStruct)_instance).getInstance();
        String methodName_ = _method.getConstraints().getName();
        ResultErrorStd res_ = new ResultErrorStd();
        if (StringUtil.quickEq(methodName_,CANCEL)) {
            res_.setResult(new StringStruct(SimulationLevelBean.cancel()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,GET_MAP_WIDTH)) {
            res_.setResult(new IntStruct(instance_.getMapWidth()));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,IS_FIRST_ROW)) {
            res_.setResult(BooleanStruct.of(instance_.isFirstRow(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        if (StringUtil.quickEq(methodName_,CLICK_TILE)) {
            res_.setResult(new StringStruct(instance_.clickTile(NumParsers.convertToNumber(_args[0]).intStruct())));
            return res_;
        }
        return res_;
    }
}
