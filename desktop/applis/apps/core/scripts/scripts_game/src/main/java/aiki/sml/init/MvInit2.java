package aiki.sml.init;
import aiki.instances.*;
import aiki.fight.util.*;
import aiki.fight.enums.*;
import aiki.fight.moves.*;
import aiki.fight.moves.effects.*;
import aiki.fight.moves.effects.enums.*;
import aiki.map.levels.enums.*;
import aiki.fight.moves.enums.*;
import code.maths.*;
import code.maths.montecarlo.*;
import code.util.*;
final class MvInit2{
private MvInit2(){}
static MoveData m50(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("PHYSIQUE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)30);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("PLANTE");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("PLANTE");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("25");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
return damagingMoveData_;
}
static MoveData m51(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("SPECIALE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)5);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("PSY");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("PSY");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(2));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("70");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.SPECIAL_ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.SPECIAL_DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
EffectStatistic effectStatistic_=Instances.newEffectStatistic();
EnumMap<Statistic,Byte> enumMapStatisticByte_=new EnumMap<Statistic,Byte>(new CollCapacity(1));
enumMapStatisticByte_.addEntry(Statistic.SPECIAL_ATTACK,(byte)-1);
effectStatistic_.setStatisVarRank(enumMapStatisticByte_);
EnumMap<Statistic,String> enumMapStatisticString_=new EnumMap<Statistic,String>(new CollCapacity(1));
enumMapStatisticString_.addEntry(Statistic.SPECIAL_ATTACK,"VAR__CIBLE_CLONE>0");
effectStatistic_.setLocalFailStatis(enumMapStatisticString_);
effectStatistic_.setEvtRate(Rate.newRate("1/2"));
effectStatistic_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
effectStatistic_.setFail("");
Ints ints_=new Ints(new CollCapacity(1));
ints_.add(0);
effectStatistic_.setRequiredSuccessfulEffects(ints_);
custListEffect_.add(effectStatistic_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
return damagingMoveData_;
}
static MoveData m52(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("PHYSIQUE");
damagingMoveData_.setDirect(true);
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)20);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("GLACE");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("GLACE");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("9/10");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("30*(min(1,VAR__LANCEUR_NB_UTILISATION__BOUL_ARMURE)+1)*puis(2,VAR__NB_TOUR__BALL_GLACE)");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
damagingMoveData_.setEffects(custListEffect_);
MonteCarloNumber monteCarloNumber_=new MonteCarloNumber(new CollCapacity(1));
monteCarloNumber_.addQuickEvent(Rate.newRate("5"),LgInt.newLgInt("1"));
damagingMoveData_.setRepeatRoundLaw(monteCarloNumber_);
damagingMoveData_.setRankIncrementNbRound((short)18);
damagingMoveData_.setConstUserChoice(true);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
return damagingMoveData_;
}
static MoveData m53(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("SPECIALE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)10);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("100*(cardinal({VAR__CLIMATS})+1)");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.SPECIAL_ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.SPECIAL_DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.ADJ_MULT);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setStoppableMoveMulti(true);
StringMap<String> stringMapString_=new StringMap<String>(new CollCapacity(6));
stringMapString_.addEntry("","NORMAL");
stringMapString_.addEntry("GRELE","GLACE");
stringMapString_.addEntry("ZENITH","FEU");
stringMapString_.addEntry("DANSE_PLUIE","EAU");
stringMapString_.addEntry("ORAGE","ELECTRIQUE");
stringMapString_.addEntry("TEMPETESABLE","ROCHE");
damagingMoveData_.setTypesByWeather(stringMapString_);
damagingMoveData_.setTargetChoice(TargetChoice.ADJ_MULT);
return damagingMoveData_;
}
static MoveData m54(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("SPECIALE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)15);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("SPECTRE");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("SPECTRE");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(2));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("90");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.SPECIAL_ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.SPECIAL_DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.ADJ_UNIQ);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
EffectStatistic effectStatistic_=Instances.newEffectStatistic();
EnumMap<Statistic,Byte> enumMapStatisticByte_=new EnumMap<Statistic,Byte>(new CollCapacity(1));
enumMapStatisticByte_.addEntry(Statistic.SPECIAL_DEFENSE,(byte)-1);
effectStatistic_.setStatisVarRank(enumMapStatisticByte_);
EnumMap<Statistic,String> enumMapStatisticString_=new EnumMap<Statistic,String>(new CollCapacity(1));
enumMapStatisticString_.addEntry(Statistic.SPECIAL_DEFENSE,"VAR__CIBLE_CLONE>0");
effectStatistic_.setLocalFailStatis(enumMapStatisticString_);
effectStatistic_.setEvtRate(Rate.newRate("1/5"));
effectStatistic_.setTargetChoice(TargetChoice.ADJ_UNIQ);
effectStatistic_.setFail("");
Ints ints_=new Ints(new CollCapacity(1));
ints_.add(0);
effectStatistic_.setRequiredSuccessfulEffects(ints_);
custListEffect_.add(effectStatistic_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setTargetChoice(TargetChoice.ADJ_UNIQ);
return damagingMoveData_;
}
static MoveData m55(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("SPECIALE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)10);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("140");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.SPECIAL_ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.SPECIAL_DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.ADJ_MULT);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setStoppableMoveMulti(true);
damagingMoveData_.setTargetChoice(TargetChoice.ADJ_MULT);
return damagingMoveData_;
}
static MoveData m56(){
StatusMoveData statusMoveData_ = Instances.newStatusMoveData();
statusMoveData_.setCounterableMove(true);
statusMoveData_.setPp((short)5);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
statusMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
statusMoveData_.setBoostedTypes(stringList_);
statusMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectTeam effectTeam_=Instances.newEffectTeam();
EnumMap<Statistic,Rate> enumMapStatisticRate_=new EnumMap<Statistic,Rate>(new CollCapacity(1));
enumMapStatisticRate_.addEntry(Statistic.ACCURACY,Rate.newRate("2"));
effectTeam_.setMultStatistic(enumMapStatisticRate_);
enumMapStatisticRate_=new EnumMap<Statistic,Rate>(new CollCapacity(1));
enumMapStatisticRate_.addEntry(Statistic.EVASINESS,Rate.newRate("1/2"));
effectTeam_.setMultStatisticFoe(enumMapStatisticRate_);
effectTeam_.setTargetChoice(TargetChoice.LANCEUR);
effectTeam_.setFail("");
custListEffect_.add(effectTeam_);
statusMoveData_.setEffects(custListEffect_);
statusMoveData_.setRankIncrementNbRound((short)12);
statusMoveData_.setStoppableMoveMulti(true);
statusMoveData_.setIgnVarAccurUserNeg(true);
statusMoveData_.setIgnVarEvasTargetPos(true);
statusMoveData_.setTargetChoice(TargetChoice.LANCEUR);
return statusMoveData_;
}
static MoveData m57(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("PHYSIQUE");
damagingMoveData_.setDirect(true);
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)10);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("TENEBRE");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("TENEBRE");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("10");
effectDamage_.setSummingUserTeamOkFighter(true);
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
return damagingMoveData_;
}
static MoveData m58(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("PHYSIQUE");
damagingMoveData_.setDirect(true);
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)20);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("VOL");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("VOL");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("80");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
return damagingMoveData_;
}
static MoveData m59(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("PHYSIQUE");
damagingMoveData_.setDirect(true);
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)20);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("17/20");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(2));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("90");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.ADJ_MULT);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
EffectDamageRate effectDamageRate_=Instances.newEffectDamageRate();
effectDamageRate_.setRateDamage(Rate.newRate("-1/4"));
effectDamageRate_.setTargetChoice(TargetChoice.LANCEUR);
effectDamageRate_.setFail("");
Ints ints_=new Ints(new CollCapacity(1));
ints_.add(0);
effectDamageRate_.setRequiredSuccessfulEffects(ints_);
custListEffect_.add(effectDamageRate_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setStoppableMoveMulti(true);
damagingMoveData_.setTargetChoice(TargetChoice.ADJ_MULT);
return damagingMoveData_;
}
static MoveData m60(){
StatusMoveData statusMoveData_ = Instances.newStatusMoveData();
statusMoveData_.setCounterableMove(true);
statusMoveData_.setPp((short)15);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
statusMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
statusMoveData_.setBoostedTypes(stringList_);
statusMoveData_.setAccuracy("11/20");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectStatus effectStatus_=Instances.newEffectStatus();
MonteCarloString monteCarloString_=new MonteCarloString(new CollCapacity(1));
monteCarloString_.addQuickEvent("SOMMEIL",LgInt.newLgInt("1"));
effectStatus_.setLawStatus(monteCarloString_);
StringMap<String> stringMapString_=new StringMap<String>(new CollCapacity(1));
stringMapString_.addEntry("SOMMEIL","cardinal(inter({VAR__CIBLE_STATUTS},{SOMMEIL;SOMMEIL_REPOS}))>0|VAR__CIBLE_CLONE>0");
effectStatus_.setLocalFailStatus(stringMapString_);
effectStatus_.setTargetChoice(TargetChoice.ADJ_MULT);
effectStatus_.setFail("");
custListEffect_.add(effectStatus_);
statusMoveData_.setEffects(custListEffect_);
statusMoveData_.setStoppableMoveSolo(true);
statusMoveData_.setStoppableMoveMulti(true);
statusMoveData_.setTargetChoice(TargetChoice.ADJ_MULT);
return statusMoveData_;
}
static MoveData m61(){
StatusMoveData statusMoveData_ = Instances.newStatusMoveData();
statusMoveData_.setPp((short)10);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
statusMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
statusMoveData_.setBoostedTypes(stringList_);
statusMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectInvoke effectInvoke_=Instances.newEffectInvoke();
effectInvoke_.setInvokingUserMoveWhileSleep(true);
stringList_=new StringList(new CollCapacity(24));
stringList_.add("ASSISTANCE");
stringList_.add("BABIL");
stringList_.add("BLABLA_DODO");
stringList_.add("BROUHAHA");
stringList_.add("CHUTE_LIBRE");
stringList_.add("COUD_KRANE");
stringList_.add("COUPE_VENT");
stringList_.add("ECLAIR_GELE");
stringList_.add("FEU_GLACE");
stringList_.add("INCENDIE");
stringList_.add("LANCE_SOLEIL");
stringList_.add("METRONOME");
stringList_.add("MIMIQUE");
stringList_.add("MITRA_POING");
stringList_.add("MOI_D_ABORD");
stringList_.add("PATIENCE");
stringList_.add("PHOTOCOPIE");
stringList_.add("PIQUE");
stringList_.add("PLONGEE");
stringList_.add("REBOND");
stringList_.add("REPOS");
stringList_.add("REVENANT");
stringList_.add("TUNNEL");
stringList_.add("ENVOL");
effectInvoke_.setMovesNotToBeInvoked(stringList_);
effectInvoke_.setTargetChoice(TargetChoice.ADJ_UNIQ);
effectInvoke_.setFail("VAR__PAS_ATTAQUE_INVOC|cardinal(inter({VAR__LANCEUR_STATUTS},{SOMMEIL;SOMMEIL_REPOS}))=0");
custListEffect_.add(effectInvoke_);
statusMoveData_.setEffects(custListEffect_);
statusMoveData_.setStoppableMoveSolo(true);
statusMoveData_.setIgnVarAccurUserNeg(true);
statusMoveData_.setIgnVarEvasTargetPos(true);
statusMoveData_.setTargetChoice(TargetChoice.ADJ_UNIQ);
stringList_=new StringList(new CollCapacity(2));
stringList_.add("SOMMEIL");
stringList_.add("SOMMEIL_REPOS");
statusMoveData_.setRequiredStatus(stringList_);
return statusMoveData_;
}
static MoveData m62(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("SPECIALE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)5);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("GLACE");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("GLACE");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("div(70+30*cardinal(inter({VAR__CLIMATS},{GRELE}))-15*cardinal(inter({VAR__CLIMATS},{ZENITH}))+10*cardinal(inter({VAR__CLIMATS},{ORAGE;DANSE_PLUIE})),100)");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(2));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("120");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.SPECIAL_ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.SPECIAL_DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.ADJ_ADV);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
EffectStatus effectStatus_=Instances.newEffectStatus();
MonteCarloString monteCarloString_=new MonteCarloString(new CollCapacity(2));
monteCarloString_.addQuickEvent("",LgInt.newLgInt("9"));
monteCarloString_.addQuickEvent("GEL",LgInt.newLgInt("1"));
effectStatus_.setLawStatus(monteCarloString_);
StringMap<String> stringMapString_=new StringMap<String>(new CollCapacity(1));
stringMapString_.addEntry("GEL","cardinal(inter({VAR__CIBLE_STATUTS},{GEL}))>0|VAR__CIBLE_CLONE>0|cardinal(inter({VAR__CIBLE_TYPES},{GLACE}))>0");
effectStatus_.setLocalFailStatus(stringMapString_);
effectStatus_.setTargetChoice(TargetChoice.ADJ_ADV);
effectStatus_.setFail("");
Ints ints_=new Ints(new CollCapacity(1));
ints_.add(0);
effectStatus_.setRequiredSuccessfulEffects(ints_);
custListEffect_.add(effectStatus_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setStoppableMoveMulti(true);
damagingMoveData_.setTargetChoice(TargetChoice.ADJ_ADV);
return damagingMoveData_;
}
static MoveData m63(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("PHYSIQUE");
damagingMoveData_.setDirect(true);
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)10);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setPriority((byte)3);
damagingMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(2));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("40");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.ADJ_MULT);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
EffectStatus effectStatus_=Instances.newEffectStatus();
MonteCarloString monteCarloString_=new MonteCarloString(new CollCapacity(1));
monteCarloString_.addQuickEvent("PEUR",LgInt.newLgInt("1"));
effectStatus_.setLawStatus(monteCarloString_);
StringMap<String> stringMapString_=new StringMap<String>(new CollCapacity(1));
stringMapString_.addEntry("PEUR","VAR__CIBLE_CLONE>0");
effectStatus_.setLocalFailStatus(stringMapString_);
effectStatus_.setTargetChoice(TargetChoice.ADJ_MULT);
effectStatus_.setFail("");
Ints ints_=new Ints(new CollCapacity(1));
ints_.add(0);
effectStatus_.setRequiredSuccessfulEffects(ints_);
custListEffect_.add(effectStatus_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setStoppableMoveMulti(true);
damagingMoveData_.setStoppableMovePrio(true);
damagingMoveData_.setTargetChoice(TargetChoice.ADJ_MULT);
return damagingMoveData_;
}
static MoveData m64(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("PHYSIQUE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)20);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("ACIER");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("ACIER");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("60");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.ADJ_UNIQ);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setIgnVarAccurUserNeg(true);
damagingMoveData_.setIgnVarEvasTargetPos(true);
damagingMoveData_.setTargetChoice(TargetChoice.ADJ_UNIQ);
return damagingMoveData_;
}
static MoveData m65(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("SPECIALE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)20);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("POISON");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("POISON");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(2));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("40");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.SPECIAL_ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.SPECIAL_DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.ADJ_UNIQ);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
EffectStatistic effectStatistic_=Instances.newEffectStatistic();
EnumMap<Statistic,Byte> enumMapStatisticByte_=new EnumMap<Statistic,Byte>(new CollCapacity(1));
enumMapStatisticByte_.addEntry(Statistic.SPECIAL_DEFENSE,(byte)-2);
effectStatistic_.setStatisVarRank(enumMapStatisticByte_);
EnumMap<Statistic,String> enumMapStatisticString_=new EnumMap<Statistic,String>(new CollCapacity(1));
enumMapStatisticString_.addEntry(Statistic.SPECIAL_DEFENSE,"VAR__CIBLE_CLONE>0");
effectStatistic_.setLocalFailStatis(enumMapStatisticString_);
effectStatistic_.setEvtRate(Rate.newRate("1"));
effectStatistic_.setTargetChoice(TargetChoice.ADJ_UNIQ);
effectStatistic_.setFail("");
Ints ints_=new Ints(new CollCapacity(1));
ints_.add(0);
effectStatistic_.setRequiredSuccessfulEffects(ints_);
custListEffect_.add(effectStatistic_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setTargetChoice(TargetChoice.ADJ_UNIQ);
return damagingMoveData_;
}
static MoveData m66(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("SPECIALE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)10);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("POISON");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("POISON");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(2));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("90");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.SPECIAL_ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.SPECIAL_DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.ADJ_MULT);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
EffectStatus effectStatus_=Instances.newEffectStatus();
MonteCarloString monteCarloString_=new MonteCarloString(new CollCapacity(2));
monteCarloString_.addQuickEvent("",LgInt.newLgInt("7"));
monteCarloString_.addQuickEvent("SIMPLE_POISON",LgInt.newLgInt("3"));
effectStatus_.setLawStatus(monteCarloString_);
StringMap<String> stringMapString_=new StringMap<String>(new CollCapacity(1));
stringMapString_.addEntry("SIMPLE_POISON","cardinal(inter({VAR__CIBLE_STATUTS},{SIMPLE_POISON;POISON_GRAVE}))>0|VAR__CIBLE_CLONE>0|cardinal(inter({VAR__CIBLE_TYPES},{POISON;ACIER}))>0");
effectStatus_.setLocalFailStatus(stringMapString_);
effectStatus_.setTargetChoice(TargetChoice.ADJ_MULT);
effectStatus_.setFail("");
Ints ints_=new Ints(new CollCapacity(1));
ints_.add(0);
effectStatus_.setRequiredSuccessfulEffects(ints_);
custListEffect_.add(effectStatus_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setStoppableMoveMulti(true);
damagingMoveData_.setTargetChoice(TargetChoice.ADJ_MULT);
return damagingMoveData_;
}
static MoveData m67(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("PHYSIQUE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)10);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("3/4");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("100");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.ADJ_MULT);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setStoppableMoveMulti(true);
damagingMoveData_.setTargetChoice(TargetChoice.ADJ_MULT);
return damagingMoveData_;
}
static MoveData m68(){
StatusMoveData statusMoveData_ = Instances.newStatusMoveData();
statusMoveData_.setPp((short)10);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
statusMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
statusMoveData_.setBoostedTypes(stringList_);
statusMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectStatistic effectStatistic_=Instances.newEffectStatistic();
effectStatistic_.setEvtRate(Rate.newRate("1"));
EnumList<Statistic> enumListStatistic_=new EnumList<Statistic>(new CollCapacity(7));
enumListStatistic_.add(Statistic.ATTACK);
enumListStatistic_.add(Statistic.DEFENSE);
enumListStatistic_.add(Statistic.SPECIAL_ATTACK);
enumListStatistic_.add(Statistic.SPECIAL_DEFENSE);
enumListStatistic_.add(Statistic.SPEED);
enumListStatistic_.add(Statistic.ACCURACY);
enumListStatistic_.add(Statistic.EVASINESS);
effectStatistic_.setCopyBoost(enumListStatistic_);
effectStatistic_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
effectStatistic_.setFail("");
custListEffect_.add(effectStatistic_);
statusMoveData_.setEffects(custListEffect_);
statusMoveData_.setStoppableMoveMulti(true);
statusMoveData_.setIgnVarAccurUserNeg(true);
statusMoveData_.setIgnVarEvasTargetPos(true);
statusMoveData_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
return statusMoveData_;
}
static MoveData m69(){
StatusMoveData statusMoveData_ = Instances.newStatusMoveData();
statusMoveData_.setThievableMove(true);
statusMoveData_.setPp((short)30);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("PSY");
statusMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("PSY");
statusMoveData_.setBoostedTypes(stringList_);
statusMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectStatistic effectStatistic_=Instances.newEffectStatistic();
EnumMap<Statistic,Byte> enumMapStatisticByte_=new EnumMap<Statistic,Byte>(new CollCapacity(1));
enumMapStatisticByte_.addEntry(Statistic.DEFENSE,(byte)2);
effectStatistic_.setStatisVarRank(enumMapStatisticByte_);
effectStatistic_.setEvtRate(Rate.newRate("1"));
effectStatistic_.setTargetChoice(TargetChoice.LANCEUR);
effectStatistic_.setFail("");
custListEffect_.add(effectStatistic_);
statusMoveData_.setEffects(custListEffect_);
statusMoveData_.setStoppableMoveMulti(true);
statusMoveData_.setIgnVarAccurUserNeg(true);
statusMoveData_.setIgnVarEvasTargetPos(true);
statusMoveData_.setTargetChoice(TargetChoice.LANCEUR);
return statusMoveData_;
}
static MoveData m70(){
StatusMoveData statusMoveData_ = Instances.newStatusMoveData();
statusMoveData_.setThievableMove(true);
statusMoveData_.setPp((short)10);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("ACIER");
statusMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("ACIER");
statusMoveData_.setBoostedTypes(stringList_);
statusMoveData_.setPriority((byte)4);
statusMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectCounterAttack effectCounterAttack_=Instances.newEffectCounterAttack();
EnumMap<Statistic,Byte> enumMapStatisticByte_=new EnumMap<Statistic,Byte>(new CollCapacity(1));
enumMapStatisticByte_.addEntry(Statistic.ATTACK,(byte)-2);
effectCounterAttack_.setDroppedStatDirectMove(enumMapStatisticByte_);
effectCounterAttack_.setSufferingDamageDirectMove(Rate.newRate("0"));
effectCounterAttack_.setProtectFail("");
effectCounterAttack_.setCounterFail("");
effectCounterAttack_.setTargetChoice(TargetChoice.LANCEUR);
effectCounterAttack_.setFail("");
custListEffect_.add(effectCounterAttack_);
statusMoveData_.setEffects(custListEffect_);
statusMoveData_.setStoppableMovePrio(true);
statusMoveData_.setIgnVarAccurUserNeg(true);
statusMoveData_.setIgnVarEvasTargetPos(true);
statusMoveData_.setTargetChoice(TargetChoice.LANCEUR);
return statusMoveData_;
}
static MoveData m71(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("SPECIALE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)10);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("SOL");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("SOL");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("17/20");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(2));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("65");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.SPECIAL_ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.SPECIAL_DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
EffectStatistic effectStatistic_=Instances.newEffectStatistic();
EnumMap<Statistic,Byte> enumMapStatisticByte_=new EnumMap<Statistic,Byte>(new CollCapacity(1));
enumMapStatisticByte_.addEntry(Statistic.ACCURACY,(byte)-1);
effectStatistic_.setStatisVarRank(enumMapStatisticByte_);
EnumMap<Statistic,String> enumMapStatisticString_=new EnumMap<Statistic,String>(new CollCapacity(1));
enumMapStatisticString_.addEntry(Statistic.ACCURACY,"VAR__CIBLE_CLONE>0");
effectStatistic_.setLocalFailStatis(enumMapStatisticString_);
effectStatistic_.setEvtRate(Rate.newRate("3/10"));
effectStatistic_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
effectStatistic_.setFail("");
Ints ints_=new Ints(new CollCapacity(1));
ints_.add(0);
effectStatistic_.setRequiredSuccessfulEffects(ints_);
custListEffect_.add(effectStatistic_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
return damagingMoveData_;
}
static MoveData m72(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("SPECIALE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)10);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("ELECTRIQUE");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("ELECTRIQUE");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("caracsemiouvertd(div(VAR__LANCEUR_STATIS__SPEED,VAR__CIBLE_STATIS__SPEED),0,2)*60+caracsemiouvertd(div(VAR__LANCEUR_STATIS__SPEED,VAR__CIBLE_STATIS__SPEED),2,3)*80+caracsemiouvertd(div(VAR__LANCEUR_STATIS__SPEED,VAR__CIBLE_STATIS__SPEED),3,4)*120+caracdroiteferme(div(VAR__LANCEUR_STATIS__SPEED,VAR__CIBLE_STATIS__SPEED),4)*150");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.SPECIAL_ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.SPECIAL_DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setTargetChoice(TargetChoice.AUTRE_UNIQ);
return damagingMoveData_;
}
static MoveData m73(){
DamagingMoveData damagingMoveData_ = Instances.newDamagingMoveData();
damagingMoveData_.setCategory("PHYSIQUE");
damagingMoveData_.setStoppableMoveKoSingle(true);
damagingMoveData_.setPp((short)25);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("ROCHE");
damagingMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("ROCHE");
damagingMoveData_.setBoostedTypes(stringList_);
damagingMoveData_.setAccuracy("9/10");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectDamage effectDamage_=Instances.newEffectDamage();
effectDamage_.setPower("25");
effectDamage_.setUserAttack(true);
effectDamage_.setStatisAtt(Statistic.ATTACK);
effectDamage_.setTargetDefense(true);
effectDamage_.setStatisDef(Statistic.DEFENSE);
effectDamage_.setTargetChoice(TargetChoice.ADJ_MULT);
effectDamage_.setFail("");
custListEffect_.add(effectDamage_);
damagingMoveData_.setEffects(custListEffect_);
damagingMoveData_.setStoppableMoveSolo(true);
damagingMoveData_.setStoppableMoveMulti(true);
damagingMoveData_.setTargetChoice(TargetChoice.ADJ_MULT);
return damagingMoveData_;
}
static MoveData m74(){
StatusMoveData statusMoveData_ = Instances.newStatusMoveData();
statusMoveData_.setThievableMove(true);
statusMoveData_.setPp((short)40);
StringList stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
statusMoveData_.setTypes(stringList_);
stringList_=new StringList(new CollCapacity(1));
stringList_.add("NORMAL");
statusMoveData_.setBoostedTypes(stringList_);
statusMoveData_.setAccuracy("1");
CustList<Effect> custListEffect_ = new CustList<Effect>(new CollCapacity(1));
EffectStatistic effectStatistic_=Instances.newEffectStatistic();
EnumMap<Statistic,Byte> enumMapStatisticByte_=new EnumMap<Statistic,Byte>(new CollCapacity(1));
enumMapStatisticByte_.addEntry(Statistic.DEFENSE,(byte)1);
effectStatistic_.setStatisVarRank(enumMapStatisticByte_);
effectStatistic_.setEvtRate(Rate.newRate("1"));
effectStatistic_.setTargetChoice(TargetChoice.LANCEUR);
effectStatistic_.setFail("");
custListEffect_.add(effectStatistic_);
statusMoveData_.setEffects(custListEffect_);
statusMoveData_.setStoppableMoveMulti(true);
statusMoveData_.setIgnVarAccurUserNeg(true);
statusMoveData_.setIgnVarEvasTargetPos(true);
statusMoveData_.setTargetChoice(TargetChoice.LANCEUR);
return statusMoveData_;
}
}
