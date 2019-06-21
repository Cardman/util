package aiki.game.fight;
import aiki.db.DataBase;
import aiki.fight.enums.Statistic;
import aiki.fight.moves.effects.EffectEndRoundSingleStatus;
import aiki.fight.moves.effects.EffectEndRoundStatus;
import aiki.fight.moves.effects.EffectEndRoundStatusRelation;
import aiki.fight.moves.enums.TargetChoice;
import aiki.fight.status.Status;
import aiki.fight.status.StatusBeginRound;
import aiki.fight.status.StatusBeginRoundAutoDamage;
import aiki.fight.status.StatusBeginRoundSimple;
import aiki.fight.status.StatusSimple;
import aiki.fight.status.StatusType;
import aiki.fight.status.effects.EffectPartnerStatus;
import code.maths.LgInt;
import code.maths.Rate;
import code.maths.montecarlo.MonteCarloBoolean;
import code.maths.montecarlo.MonteCarloNumber;
import code.util.CustList;
import code.util.EnumMap;
import code.util.Numbers;
import code.util.StringMap;

final class InitializationStatus {

    static final String SOMMEIL_REPOS = "SOMMEIL_REPOS";
    static final String SOMMEIL = "SOMMEIL";
    static final String PARALYSIE_FORTE = "PARALYSIE_FORTE";
    static final String PARALYSIE = "PARALYSIE";
    static final String CRAME_BIS = "CRAME_BIS";
    static final String CRAME = "CRAME";
    static final String BRULURE = "BRULURE";
    static final String FEU_GEL = "FEU_GEL";
    static final String ERE_GEL = "ERE_GEL";
    static final String GEL = "GEL";
    static final String NUIT_NOIRE = "NUIT_NOIRE";
    static final String NUIT_BLANCHE_BIS = "NUIT_BLANCHE_BIS";
    static final String NUIT_GRISE = "NUIT_GRISE";
    static final String NUIT_BLANCHE = "NUIT_BLANCHE";
    static final String CAUCHEMAR = "CAUCHEMAR";
    static final String AMOUR_TRES_MOU = "AMOUR_TRES_MOU";
    static final String AMOUR_MOU = "AMOUR_MOU";
    static final String AMOUR_FOU = "AMOUR_FOU";
    static final String AMOUR = "AMOUR";
    static final String VAMPIGRAINE = "VAMPIGRAINE";
    static final String POISON_GRAVE = "POISON_GRAVE";
    static final String TROUILLE = "TROUILLE";
    static final String PEUR = "PEUR";
    static final String COUP_DE_BEC = "COUP_DE_BEC";
    static final String PRISE_DE_TETE = "PRISE_DE_TETE";
    static final String LONGUE_CONFUSION_SANS_DOMMAGE = "LONGUE_CONFUSION_SANS_DOMMAGE";
    static final String LONGUE_CONFUSION_DOMMAGE = "LONGUE_CONFUSION_DOMMAGE";
    static final String CONFUSION = "CONFUSION";
    static final String POISON_ST = "POISON_ST";
    private static final String NULL_REF = InitializationDataBase.NULL_REF;

    private InitializationStatus() {
    }
    static void initAllStatus(DataBase _data) {
        Status statut_;
        EffectEndRoundSingleStatus effectEndRoundSingleStatus_;
        StatusBeginRound statusBeginRound_;
        EffectEndRoundStatusRelation effectEndRoundStatusRelation_;
        EffectPartnerStatus effectPartnerStatus_;
        StatusBeginRoundAutoDamage statusBeginRoundAutoDamage_;
        statusBeginRoundAutoDamage_ = defaultStatusBeginRoundAutoDamage();
        statusBeginRoundAutoDamage_.setPower(new Rate("40"));
        statusBeginRoundAutoDamage_.setAttack(Statistic.ATTACK);
        statusBeginRoundAutoDamage_.setDefense(Statistic.DEFENSE);
        statusBeginRoundAutoDamage_.getLawForUsingAMoveNbRound().addEvent(new Rate("3"),new LgInt("4"));
        statusBeginRoundAutoDamage_.getLawForUsingAMoveNbRound().addEvent(new Rate("1"),new LgInt("9"));
        statusBeginRoundAutoDamage_.getLawForUsingAMoveNbRound().addEvent(new Rate("4"),new LgInt("8"));
        statusBeginRoundAutoDamage_.getLawForUsingAMoveNbRound().addEvent(new Rate("2"),new LgInt("6"));
        statusBeginRoundAutoDamage_.getLawForUsingAMove().addEvent(false,new LgInt("3"));
        statusBeginRoundAutoDamage_.getLawForUsingAMove().addEvent(true,new LgInt("1"));
        statusBeginRoundAutoDamage_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRoundAutoDamage_.setCatchingRate(new Rate("3"));
        statusBeginRoundAutoDamage_.setDisabledEffIfSwitch(true);
        _data.completeMembers(CONFUSION,statusBeginRoundAutoDamage_);
        statusBeginRoundAutoDamage_ = defaultStatusBeginRoundAutoDamage();
        statusBeginRoundAutoDamage_.setPower(new Rate("40"));
        statusBeginRoundAutoDamage_.setAttack(Statistic.ATTACK);
        statusBeginRoundAutoDamage_.setDefense(Statistic.DEFENSE);
        statusBeginRoundAutoDamage_.getLawForUsingAMoveNbRound().addEvent(new Rate("4"),new LgInt("8"));
        statusBeginRoundAutoDamage_.getLawForUsingAMove().addEvent(false,new LgInt("1"));
        statusBeginRoundAutoDamage_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRoundAutoDamage_.setCatchingRate(new Rate("3"));
        statusBeginRoundAutoDamage_.setDisabledEffIfSwitch(true);
        _data.completeMembers(LONGUE_CONFUSION_DOMMAGE,statusBeginRoundAutoDamage_);
        statusBeginRoundAutoDamage_ = defaultStatusBeginRoundAutoDamage();
        statusBeginRoundAutoDamage_.setPower(new Rate("40"));
        statusBeginRoundAutoDamage_.setAttack(Statistic.ATTACK);
        statusBeginRoundAutoDamage_.setDefense(Statistic.DEFENSE);
        statusBeginRoundAutoDamage_.getLawForUsingAMoveNbRound().addEvent(new Rate("4"),new LgInt("8"));
        statusBeginRoundAutoDamage_.getLawForUsingAMove().addEvent(true,new LgInt("1"));
        statusBeginRoundAutoDamage_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRoundAutoDamage_.setCatchingRate(new Rate("3"));
        statusBeginRoundAutoDamage_.setDisabledEffIfSwitch(true);
        _data.completeMembers(LONGUE_CONFUSION_SANS_DOMMAGE,statusBeginRoundAutoDamage_);
        statusBeginRoundAutoDamage_ = defaultStatusBeginRoundAutoDamage();
        statusBeginRoundAutoDamage_.setPower(new Rate("40"));
        statusBeginRoundAutoDamage_.setAttack(Statistic.ATTACK);
        statusBeginRoundAutoDamage_.setDefense(Statistic.DEFENSE);
        statusBeginRoundAutoDamage_.getLawForUsingAMoveNbRound().addEvent(new Rate("4"),new LgInt("8"));
        statusBeginRoundAutoDamage_.getLawForUsingAMove().addEvent(false,new LgInt("1"));
        statusBeginRoundAutoDamage_.setStatusType(StatusType.RELATION_UNIQUE);
        statusBeginRoundAutoDamage_.setCatchingRate(new Rate("3"));
        statusBeginRoundAutoDamage_.setDisabledEffIfSwitch(true);
        _data.completeMembers(PRISE_DE_TETE,statusBeginRoundAutoDamage_);
        statusBeginRoundAutoDamage_ = defaultStatusBeginRoundAutoDamage();
        statusBeginRoundAutoDamage_.setPower(new Rate("40"));
        statusBeginRoundAutoDamage_.setAttack(Statistic.ATTACK);
        statusBeginRoundAutoDamage_.setDefense(Statistic.DEFENSE);
        statusBeginRoundAutoDamage_.getLawForUsingAMoveNbRound().addEvent(new Rate("3"),new LgInt("1"));
        statusBeginRoundAutoDamage_.getLawForUsingAMoveNbRound().addEvent(new Rate("4"),new LgInt("1"));
        statusBeginRoundAutoDamage_.getLawForUsingAMove().addEvent(false,new LgInt("1"));
        statusBeginRoundAutoDamage_.setStatusType(StatusType.RELATION_UNIQUE);
        statusBeginRoundAutoDamage_.setCatchingRate(new Rate("3"));
        statusBeginRoundAutoDamage_.setDisabledEffIfSwitch(true);
        _data.completeMembers(COUP_DE_BEC,statusBeginRoundAutoDamage_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("0"),new LgInt("1"));
        statusBeginRound_.getLawForUsingAMove().addEvent(true,new LgInt("1"));
        statusBeginRound_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRound_.setCatchingRate(new Rate("5/4"));
        //statusBeginRound_.setIncrementEndRound(41);
        statusBeginRound_.setIncrementEndRound(43);
        statusBeginRound_.setIncrementingEndRound(true);
        statusBeginRound_.setDisabledEffIfSwitch(true);
        _data.completeMembers(PEUR,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("2"),new LgInt("1"));
        statusBeginRound_.getLawForUsingAMove().addEvent(false,new LgInt("1"));
        statusBeginRound_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRound_.setCatchingRate(new Rate("5/4"));
        //statusBeginRound_.setIncrementEndRound(41);
        statusBeginRound_.setIncrementEndRound(44);
        statusBeginRound_.setIncrementingEndRound(true);
        statusBeginRound_.setDisabledEffIfSwitch(true);
        _data.completeMembers(TROUILLE,statusBeginRound_);
        statut_ = defaultStatut();
        statut_.setStatusType(StatusType.INDIVIDUEL);
        statut_.setCatchingRate(new Rate("3/2"));
        effectEndRoundSingleStatus_ = defaultEffectEndRoundSingleStatus();
        effectEndRoundSingleStatus_.setIncrementingDamageByRounds(true);
        effectEndRoundSingleStatus_.setInflictedRateHpTarget(new Rate("1/4"));
//        effectEndRoundSingleStatus_.setEndRoundRank(35);
        effectEndRoundSingleStatus_.setEndRoundRank(52);
        effectEndRoundSingleStatus_.setTargetChoice(TargetChoice.NOTHING);
        statut_.getEffectEndRound().add(effectEndRoundSingleStatus_);
        _data.completeMembers(POISON_GRAVE,statut_);
        statut_ = defaultStatut();
        statut_.setStatusType(StatusType.RELATION_UNIQUE);
        statut_.setCatchingRate(new Rate("3/2"));
        effectEndRoundStatusRelation_ = defaultEffectEndRoundStatusRelation();
        effectEndRoundStatusRelation_.setThievedHpRateTargetToUser(new Rate("1/8"));
        effectEndRoundStatusRelation_.setEndRoundRank(29);
        effectEndRoundStatusRelation_.setTargetChoice(TargetChoice.NOTHING);
        statut_.getEffectEndRound().add(effectEndRoundStatusRelation_);
        _data.completeMembers(VAMPIGRAINE,statut_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.getLawForUsingAMoveIfFoe().addEvent(false,new LgInt("1"));
        statusBeginRound_.getLawForUsingAMoveIfFoe().addEvent(true,new LgInt("1"));
        statusBeginRound_.setStatusType(StatusType.RELATION_UNIQUE);
        statusBeginRound_.setCatchingRate(new Rate("3"));
        effectPartnerStatus_ = defaultEffectPartnerStatus();
        effectPartnerStatus_.setMultDamageAgainstFoe(new Rate("2"));
        effectPartnerStatus_.setRestoredHpRateLovedAlly(new Rate("1"));
        effectPartnerStatus_.setWeddingAlly(true);
        statusBeginRound_.getEffectsPartner().add(effectPartnerStatus_);
        _data.completeMembers(AMOUR,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.getLawForUsingAMoveIfFoe().addEvent(true,new LgInt("1"));
        statusBeginRound_.setStatusType(StatusType.RELATION_UNIQUE);
        statusBeginRound_.setCatchingRate(new Rate("3"));
        effectPartnerStatus_ = defaultEffectPartnerStatus();
        effectPartnerStatus_.setMultDamageAgainstFoe(new Rate("2"));
        effectPartnerStatus_.setWeddingAlly(true);
        statusBeginRound_.getEffectsPartner().add(effectPartnerStatus_);
        _data.completeMembers(AMOUR_FOU,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.getLawForUsingAMoveIfFoe().addEvent(false,new LgInt("1"));
        statusBeginRound_.setStatusType(StatusType.RELATION_UNIQUE);
        statusBeginRound_.setCatchingRate(new Rate("3"));
        effectPartnerStatus_ = defaultEffectPartnerStatus();
        effectPartnerStatus_.setMultDamageAgainstFoe(new Rate("2"));
        effectPartnerStatus_.setWeddingAlly(true);
        statusBeginRound_.getEffectsPartner().add(effectPartnerStatus_);
        _data.completeMembers(AMOUR_MOU,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.getLawForUsingAMoveIfFoe().addEvent(false,new LgInt("1"));
        statusBeginRound_.getLawForUsingAMoveIfFoe().addEvent(true,new LgInt("1"));
        statusBeginRound_.setStatusType(StatusType.RELATION_UNIQUE);
        statusBeginRound_.setCatchingRate(new Rate("3"));
        effectPartnerStatus_ = defaultEffectPartnerStatus();
        effectPartnerStatus_.setMultDamageAgainstFoe(new Rate("2"));
        effectPartnerStatus_.setRestoredHpRateLovedAlly(new Rate("1"));
        statusBeginRound_.getEffectsPartner().add(effectPartnerStatus_);
        _data.completeMembers(AMOUR_TRES_MOU,statusBeginRound_);
        statut_ = defaultStatut();
        statut_.setStatusType(StatusType.RELATION_UNIQUE);
        statut_.setCatchingRate(new Rate("3/2"));
        effectEndRoundStatusRelation_ = defaultEffectEndRoundStatusRelation();
        effectEndRoundStatusRelation_.setInflictedRateHpTarget(new Rate("1/4"));
        effectEndRoundStatusRelation_.setEndRoundRank(37);
        effectEndRoundStatusRelation_.setTargetChoice(TargetChoice.NOTHING);
        effectEndRoundStatusRelation_.setFail("cardinal(inter({VAR__CIBLE_STATUTS},{SOMMEIL;SOMMEIL_REPOS}))=0");
        effectEndRoundStatusRelation_.setFailEndRound("cardinal(inter({VAR__CIBLE_STATUTS},{SOMMEIL;SOMMEIL_REPOS}))=0");
        statut_.getEffectEndRound().add(effectEndRoundStatusRelation_);
        _data.completeMembers(CAUCHEMAR,statut_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.setStatusType(StatusType.RELATION_UNIQUE);
        statusBeginRound_.setCatchingRate(new Rate("3/2"));
        statusBeginRound_.setIncrementingEndRound(true);
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("4"),new LgInt("9"));
        effectEndRoundStatusRelation_ = defaultEffectEndRoundStatusRelation();
        effectEndRoundStatusRelation_.setInflictedRateHpTarget(new Rate("1/4"));
//        effectEndRoundStatusRelation_.setEndRoundRank(37);
        effectEndRoundStatusRelation_.setEndRoundRank(50);
        effectEndRoundStatusRelation_.setTargetChoice(TargetChoice.NOTHING);
        effectEndRoundStatusRelation_.setFail("cardinal(inter({VAR__CIBLE_STATUTS},{SOMMEIL;SOMMEIL_REPOS}))=0");
        effectEndRoundStatusRelation_.setFailEndRound("cardinal(inter({VAR__CIBLE_STATUTS},{SOMMEIL;SOMMEIL_REPOS}))=0");
        statusBeginRound_.getEffectEndRound().add(effectEndRoundStatusRelation_);
        statusBeginRound_.setIncrementEndRound(45);
        _data.completeMembers(NUIT_BLANCHE,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.setStatusType(StatusType.RELATION_UNIQUE);
        statusBeginRound_.setCatchingRate(new Rate("3/2"));
        statusBeginRound_.setIncrementingEndRound(true);
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("4"),new LgInt("1"));
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("5"),new LgInt("1"));
        effectEndRoundStatusRelation_ = defaultEffectEndRoundStatusRelation();
        effectEndRoundStatusRelation_.setInflictedRateHpTarget(new Rate("1/4"));
//        effectEndRoundStatusRelation_.setEndRoundRank(37);
        effectEndRoundStatusRelation_.setEndRoundRank(49);
        effectEndRoundStatusRelation_.setTargetChoice(TargetChoice.NOTHING);
        effectEndRoundStatusRelation_.setFail("cardinal(inter({VAR__CIBLE_STATUTS},{SOMMEIL;SOMMEIL_REPOS}))=0");
        effectEndRoundStatusRelation_.setFailEndRound("cardinal(inter({VAR__CIBLE_STATUTS},{SOMMEIL;SOMMEIL_REPOS}))=0");
        statusBeginRound_.getEffectEndRound().add(effectEndRoundStatusRelation_);
        statusBeginRound_.setIncrementEndRound(46);
        _data.completeMembers(NUIT_BLANCHE_BIS,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.setStatusType(StatusType.RELATION_UNIQUE);
        statusBeginRound_.setCatchingRate(new Rate("3/2"));
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("4"),new LgInt("9"));
        statusBeginRound_.setIncrementingEndRound(true);
        _data.completeMembers(NUIT_GRISE,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.setStatusType(StatusType.RELATION_UNIQUE);
        statusBeginRound_.setCatchingRate(new Rate("3/2"));
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("4"),new LgInt("9"));
        effectEndRoundStatusRelation_ = defaultEffectEndRoundStatusRelation();
        effectEndRoundStatusRelation_.setInflictedRateHpTarget(new Rate("1/4"));
//        effectEndRoundStatusRelation_.setEndRoundRank(37);
        effectEndRoundStatusRelation_.setEndRoundRank(48);
        effectEndRoundStatusRelation_.setTargetChoice(TargetChoice.NOTHING);
        effectEndRoundStatusRelation_.setFail("cardinal(inter({VAR__CIBLE_STATUTS},{SOMMEIL;SOMMEIL_REPOS}))=0");
        effectEndRoundStatusRelation_.setFailEndRound("cardinal(inter({VAR__CIBLE_STATUTS},{SOMMEIL;SOMMEIL_REPOS}))=0");
        statusBeginRound_.getEffectEndRound().add(effectEndRoundStatusRelation_);
        _data.completeMembers(NUIT_NOIRE,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.getLawForUsingAMove().addEvent(false,new LgInt("9"));
        statusBeginRound_.getLawForUsingAMove().addEvent(true,new LgInt("1"));
        statusBeginRound_.getLawForFullHealIfMove().addEvent(true,new LgInt("1"));
        statusBeginRound_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRound_.setCatchingRate(new Rate("5/2"));
        _data.completeMembers(GEL,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.getLawForUsingAMove().addEvent(false,new LgInt("9"));
        statusBeginRound_.getLawForFullHealIfMove().addEvent(true,new LgInt("1"));
        statusBeginRound_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRound_.setCatchingRate(new Rate("5/2"));
        _data.completeMembers(ERE_GEL,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.getLawForUsingAMove().addEvent(true,new LgInt("9"));
        statusBeginRound_.getLawForFullHealIfMove().addEvent(true,new LgInt("1"));
        statusBeginRound_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRound_.setCatchingRate(new Rate("5/2"));
        _data.completeMembers(FEU_GEL,statusBeginRound_);
        statut_ = defaultStatut();
        statut_.setStatusType(StatusType.INDIVIDUEL);
        statut_.setCatchingRate(new Rate("3/2"));
        statut_.getMultStat().put(Statistic.ATTACK,new Rate("1/2"));
        statut_.getMultStat().put(Statistic.SPEED,new Rate("2"));
        statut_.setFail("VAR__FIGHTER_CLONE>0|cardinal(inter({VAR__FIGHTER_TYPES},{FEU}))>0");
        effectEndRoundSingleStatus_ = defaultEffectEndRoundSingleStatus();
        effectEndRoundSingleStatus_.setInflictedRateHpTarget(new Rate("1/8"));
        effectEndRoundSingleStatus_.setEndRoundRank(35);
        effectEndRoundSingleStatus_.setTargetChoice(TargetChoice.NOTHING);
        statut_.getEffectEndRound().add(effectEndRoundSingleStatus_);
        _data.completeMembers(BRULURE,statut_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.setIncrementingEndRound(true);
        statusBeginRound_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRound_.setCatchingRate(new Rate("3/2"));
        statusBeginRound_.getMultStat().put(Statistic.ATTACK,new Rate("1/2"));
        statusBeginRound_.getMultStat().put(Statistic.SPEED,new Rate("2"));
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("2"),new LgInt("1"));
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("3"),new LgInt("1"));
        statusBeginRound_.setFail("VAR__FIGHTER_CLONE>0|cardinal(inter({VAR__FIGHTER_TYPES},{FEU}))>0");
        effectEndRoundSingleStatus_ = defaultEffectEndRoundSingleStatus();
        effectEndRoundSingleStatus_.setInflictedRateHpTarget(new Rate("1/8"));
//        effectEndRoundSingleStatus_.setEndRoundRank(35);
        effectEndRoundSingleStatus_.setEndRoundRank(53);
        effectEndRoundSingleStatus_.setTargetChoice(TargetChoice.NOTHING);
        statusBeginRound_.getEffectEndRound().add(effectEndRoundSingleStatus_);
        statusBeginRound_.setIncrementEndRound(47);
        _data.completeMembers(CRAME,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRound_.setCatchingRate(new Rate("3/2"));
        statusBeginRound_.getMultStat().put(Statistic.ATTACK,new Rate("1/2"));
        statusBeginRound_.getMultStat().put(Statistic.SPEED,new Rate("2"));
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("2"),new LgInt("1"));
        statusBeginRound_.setFail("VAR__FIGHTER_CLONE>0|cardinal(inter({VAR__FIGHTER_TYPES},{FEU}))>0");
        effectEndRoundSingleStatus_ = defaultEffectEndRoundSingleStatus();
        effectEndRoundSingleStatus_.setInflictedRateHpTarget(new Rate("1/8"));
//        effectEndRoundSingleStatus_.setEndRoundRank(35);
        effectEndRoundSingleStatus_.setEndRoundRank(54);
        effectEndRoundSingleStatus_.setTargetChoice(TargetChoice.NOTHING);
        statusBeginRound_.getEffectEndRound().add(effectEndRoundSingleStatus_);
        _data.completeMembers(CRAME_BIS,statusBeginRound_);
        statut_ = defaultStatut();
        statut_.setStatusType(StatusType.INDIVIDUEL);
        statut_.setCatchingRate(new Rate("3/2"));
        effectEndRoundSingleStatus_ = defaultEffectEndRoundSingleStatus();
        effectEndRoundSingleStatus_.setInflictedRateHpTarget(new Rate("1/8"));
//        effectEndRoundSingleStatus_.setEndRoundRank(35);
        effectEndRoundSingleStatus_.setEndRoundRank(51);
        effectEndRoundSingleStatus_.setTargetChoice(TargetChoice.NOTHING);
        statut_.getEffectEndRound().add(effectEndRoundSingleStatus_);
        _data.completeMembers(POISON_ST,statut_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRound_.setCatchingRate(new Rate("3/2"));
        statusBeginRound_.getMultStat().put(Statistic.SPEED,new Rate("1/4"));
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("3"), new LgInt("1"));
        _data.completeMembers(PARALYSIE,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRound_.setCatchingRate(new Rate("3/2"));
        statusBeginRound_.getMultStat().put(Statistic.SPEED,new Rate("1/4"));
        statusBeginRound_.getLawForUsingAMove().addEvent(false, LgInt.one());
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("3"), new LgInt("1"));
        _data.completeMembers(PARALYSIE_FORTE,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRound_.setCatchingRate(new Rate("5/2"));
        statusBeginRound_.getLawForUsingAMove().addEvent(false, LgInt.one());
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("2"), new LgInt("1"));
        _data.completeMembers(SOMMEIL,statusBeginRound_);
        statusBeginRound_ = defaultStatusBeginRound();
        statusBeginRound_.setStatusType(StatusType.INDIVIDUEL);
        statusBeginRound_.setCatchingRate(new Rate("5/2"));
        statusBeginRound_.getLawForUsingAMove().addEvent(false, LgInt.one());
        statusBeginRound_.getLawForUsingAMoveNbRound().addEvent(new Rate("2"), new LgInt("1"));
        _data.completeMembers(SOMMEIL_REPOS,statusBeginRound_);
    }

    private static Status defaultStatut() {
        Status object_ = new StatusSimple();
        object_.setStatusType(StatusType.INDIVIDUEL);
        object_.setCatchingRate(Rate.zero());
        object_.setEffectEndRound(new CustList<EffectEndRoundStatus>());
        object_.setEffectsPartner(new CustList<EffectPartnerStatus>());
        object_.setMultStat(new EnumMap<Statistic,Rate>());
        object_.setFail(NULL_REF);
        return object_;
    }

    private static EffectEndRoundSingleStatus defaultEffectEndRoundSingleStatus() {
        EffectEndRoundSingleStatus object_ = new EffectEndRoundSingleStatus();
        object_.setMultDamageStatus(new StringMap<Rate>());
        object_.setInflictedRateHpTarget(Rate.zero());
        object_.setTargetChoice(TargetChoice.NOTHING);
        object_.setFail(NULL_REF);
        object_.setRequiredSuccessfulEffects(new Numbers<Integer>());
        object_.setFailEndRound(NULL_REF);
        return object_;
    }

    private static StatusBeginRound defaultStatusBeginRound() {
        StatusBeginRound object_ = new StatusBeginRoundSimple();
        object_.setLawForUsingAMove(new MonteCarloBoolean());
        object_.setLawForUsingAMoveNbRound(new MonteCarloNumber());
        object_.setLawForUsingAMoveIfFoe(new MonteCarloBoolean());
        object_.setLawForFullHealIfMove(new MonteCarloBoolean());
        object_.setStatusType(StatusType.INDIVIDUEL);
        object_.setCatchingRate(Rate.zero());
        object_.setEffectEndRound(new CustList<EffectEndRoundStatus>());
        object_.setEffectsPartner(new CustList<EffectPartnerStatus>());
        object_.setMultStat(new EnumMap<Statistic,Rate>());
        object_.setFail(NULL_REF);
        return object_;
    }

    private static EffectEndRoundStatusRelation defaultEffectEndRoundStatusRelation() {
        EffectEndRoundStatusRelation object_ = new EffectEndRoundStatusRelation();
        object_.setThievedHpRateTargetToUser(Rate.zero());
        object_.setInflictedRateHpTarget(Rate.zero());
        object_.setTargetChoice(TargetChoice.NOTHING);
        object_.setFail(NULL_REF);
        object_.setRequiredSuccessfulEffects(new Numbers<Integer>());
        object_.setFailEndRound(NULL_REF);
        return object_;
    }

    private static EffectPartnerStatus defaultEffectPartnerStatus() {
        EffectPartnerStatus object_ = new EffectPartnerStatus();
        object_.setMultDamageAgainstFoe(Rate.zero());
        object_.setRestoredHpRateLovedAlly(Rate.zero());
        return object_;
    }

    private static StatusBeginRoundAutoDamage defaultStatusBeginRoundAutoDamage() {
        StatusBeginRoundAutoDamage object_ = new StatusBeginRoundAutoDamage();
        object_.setPower(Rate.zero());
        object_.setAttack(Statistic.ATTACK);
        object_.setDefense(Statistic.DEFENSE);
        object_.setLawForUsingAMove(new MonteCarloBoolean());
        object_.setLawForUsingAMoveNbRound(new MonteCarloNumber());
        object_.setLawForUsingAMoveIfFoe(new MonteCarloBoolean());
        object_.setLawForFullHealIfMove(new MonteCarloBoolean());
        object_.setStatusType(StatusType.INDIVIDUEL);
        object_.setCatchingRate(Rate.zero());
        object_.setEffectEndRound(new CustList<EffectEndRoundStatus>());
        object_.setEffectsPartner(new CustList<EffectPartnerStatus>());
        object_.setMultStat(new EnumMap<Statistic,Rate>());
        object_.setFail(NULL_REF);
        return object_;
    }
}
