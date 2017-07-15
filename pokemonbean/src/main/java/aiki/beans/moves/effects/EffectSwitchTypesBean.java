package aiki.beans.moves.effects;
import code.bean.Accessible;
import code.util.EnumMap;
import code.util.StringList;
import code.util.StringMap;
import code.util.TreeMap;
import aiki.DataBase;
import aiki.comparators.ComparatorTrStringEnv;
import aiki.comparators.ComparatorTrStrings;
import aiki.fight.moves.MoveData;
import aiki.fight.moves.effects.Effect;
import aiki.fight.moves.effects.EffectGlobal;
import aiki.fight.moves.effects.EffectSwitchTypes;
import aiki.fight.moves.effects.enums.ConstValuesType;
import aiki.fight.moves.effects.enums.ExchangeType;
import aiki.map.levels.enums.EnvironmentType;

public class EffectSwitchTypesBean extends EffectBean {

    @Accessible
    private TreeMap<EnvironmentType, String> chgtTypeByEnv;

    @Accessible
    private StringList globalMoves;

    private ConstValuesType constValuesType;

    private ExchangeType exchangeTypes;

    @Accessible
    private StringList constTypes;

    @Accessible
    private StringList addedTypes;

    @Override
    public void beforeDisplaying() {
        super.beforeDisplaying();
        EffectSwitchTypes effect_ = (EffectSwitchTypes) getEffect();
        DataBase data_ = (DataBase) getDataBase();
        EnumMap<EnvironmentType,String> translatedEnvironments_ = data_.getTranslatedEnvironment().getVal(getLanguage());
        StringMap<String> translatedMoves_ = data_.getTranslatedMoves().getVal(getLanguage());
        StringMap<String> translatedTypes_ = data_.getTranslatedTypes().getVal(getLanguage());
        TreeMap<EnvironmentType, String> chgtTypeByEnv_;
        chgtTypeByEnv_ = new TreeMap<EnvironmentType, String>(new ComparatorTrStringEnv(translatedEnvironments_));
        for (EnvironmentType env_: effect_.getChgtTypeByEnv().getKeys()) {
            String type_;
            type_ = effect_.getChgtTypeByEnv().getVal(env_);
            chgtTypeByEnv_.put(env_, translatedTypes_.getVal(type_));
        }
        chgtTypeByEnv = chgtTypeByEnv_;
        StringList globalMoves_;
        globalMoves_ = new StringList();
        for (String m: data_.getMoves().getKeys()) {
            MoveData move_ = data_.getMove(m);
            for (Effect e: move_.getEffects()) {
                if (!(e instanceof EffectGlobal)) {
                    continue;
                }
                EffectGlobal eff_ = (EffectGlobal) e;
                if (eff_.getChangedTypesTerrain().isEmpty()) {
                    continue;
                }
                globalMoves_.add(m);
            }
        }
        globalMoves_.removeDuplicates();
        globalMoves_.sortElts(new ComparatorTrStrings(translatedMoves_));
        globalMoves = globalMoves_;
        constValuesType = effect_.getConstValuesType();
        exchangeTypes = effect_.getExchangeTypes();
        StringList constTypes_;
        constTypes_ = new StringList();
        for (String type_: effect_.getConstTypes()) {
            constTypes_.add(type_);
        }
        constTypes_.sortElts(new ComparatorTrStrings(translatedTypes_));
        constTypes = constTypes_;
        StringList addedTypes_;
        addedTypes_ = new StringList();
        for (String type_: effect_.getAddedTypes()) {
            addedTypes_.add(type_);
        }
        addedTypes_.sortElts(new ComparatorTrStrings(translatedTypes_));
        addedTypes = addedTypes_;
    }

    @Accessible
    private boolean isConstTypes() {
        return constValuesType != ConstValuesType.NOTHING;
    }

    @Accessible
    private boolean isResTypes() {
        return constValuesType == ConstValuesType.TYPES_ATTAQUES_RES;
    }

    @Accessible
    private boolean isUserTypes() {
        return constValuesType == ConstValuesType.LANCEUR_ATTAQUES_TYPES;
    }

    @Accessible
    private String getTrEnv(Long _index) {
        EnvironmentType env_ = chgtTypeByEnv.getKey(_index.intValue());
        DataBase data_ = (DataBase) getDataBase();
        EnumMap<EnvironmentType,String> translatedTypes_ = data_.getTranslatedEnvironment().getVal(getLanguage());
        return translatedTypes_.getVal(env_);
    }

    @Accessible
    private String getTrGlobalMoveFctEnv(Long _index) {
        DataBase data_ = (DataBase) getDataBase();
        StringMap<String> translatedMoves_ = data_.getTranslatedMoves().getVal(getLanguage());
        String st_ = globalMoves.get(_index.intValue());
        return translatedMoves_.getVal(st_);
    }

    @Accessible
    private String clickGlobalMoveFctEnv(Long _index) {
        String st_ = globalMoves.get(_index.intValue());
        getForms().put(MOVE, st_);
        return MOVE;
    }

    @Accessible
    private boolean giveToTarget() {
        return exchangeTypes == ExchangeType.GIVE_TO_TARGET;
    }

    @Accessible
    private boolean giveToUser() {
        return exchangeTypes == ExchangeType.GIVE_TO_THROWER;
    }

    @Accessible
    private boolean giveConst() {
        return exchangeTypes == ExchangeType.GIVE_CONST;
    }

    @Accessible
    private boolean switchTypes() {
        return exchangeTypes == ExchangeType.EXCHANGE;
    }

    @Accessible
    private String getTrConstType(Long _index) {
        String type_ = constTypes.get(_index.intValue());
        DataBase data_ = (DataBase) getDataBase();
        StringMap<String> translatedTypes_ = data_.getTranslatedTypes().getVal(getLanguage());
        return translatedTypes_.getVal(type_);
    }

    @Accessible
    private String getTrAddedType(Long _index) {
        String type_ = addedTypes.get(_index.intValue());
        DataBase data_ = (DataBase) getDataBase();
        StringMap<String> translatedTypes_ = data_.getTranslatedTypes().getVal(getLanguage());
        return translatedTypes_.getVal(type_);
    }
}
