package aiki.sml.init;
import aiki.instances.*;
import aiki.fight.util.*;
import aiki.fight.*;
import aiki.fight.effects.*;
import aiki.fight.moves.effects.*;
import aiki.fight.status.effects.*;
import aiki.fight.moves.enums.*;
import aiki.fight.status.*;
import aiki.fight.enums.*;
import code.maths.*;
import code.maths.montecarlo.*;
import code.util.*;
public final class CoInit{
private CoInit(){}
public static Combos co(){
Combos combos_ = Instances.newCombos();
ListEffectCombos listEffectCombos_=new ListEffectCombos(new CollCapacity(3));
EffectCombo effectCombo_=Instances.newEffectCombo();
effectCombo_.setMultEvtRateSecEff(Rate.newRate("2"));
MonteCarloNumber monteCarloNumber_=new MonteCarloNumber(new CollCapacity(1));
monteCarloNumber_.addQuickEvent(Rate.newRate("3"),LgInt.newLgInt("1"));
effectCombo_.setRepeatedRoundsLaw(monteCarloNumber_);
effectCombo_.setRankIncrementNbRound((short)15);
listEffectCombos_.add(new ListEffectCombo(new StringList("AIRE_DE_FEU","AIRE_D_EAU"),effectCombo_));
effectCombo_=Instances.newEffectCombo();
effectCombo_.setMultEvtRateSecEff(Rate.newRate("0"));
monteCarloNumber_=new MonteCarloNumber(new CollCapacity(1));
monteCarloNumber_.addQuickEvent(Rate.newRate("3"),LgInt.newLgInt("1"));
effectCombo_.setRepeatedRoundsLaw(monteCarloNumber_);
effectCombo_.setRankIncrementNbRound((short)15);
CustList<EffectTeam> custListEffectTeam_=new CustList<EffectTeam>(new CollCapacity(1));
EffectTeam effectTeam_=Instances.newEffectTeam();
EnumMap<Statistic,Rate> enumMapStatisticRate_=new EnumMap<Statistic,Rate>(new CollCapacity(1));
enumMapStatisticRate_.addEntry(Statistic.EVASINESS,Rate.newRate("1/2"));
effectTeam_.setMultStatisticFoe(enumMapStatisticRate_);
effectTeam_.setTargetChoice(TargetChoice.LANCEUR);
effectTeam_.setFail("");
custListEffectTeam_.add(effectTeam_);
effectCombo_.setTeamMove(custListEffectTeam_);
listEffectCombos_.add(new ListEffectCombo(new StringList("AIRE_D_EAU","AIRE_D_HERBE"),effectCombo_));
effectCombo_=Instances.newEffectCombo();
effectCombo_.setMultEvtRateSecEff(Rate.newRate("0"));
monteCarloNumber_=new MonteCarloNumber(new CollCapacity(1));
monteCarloNumber_.addQuickEvent(Rate.newRate("3"),LgInt.newLgInt("1"));
effectCombo_.setRepeatedRoundsLaw(monteCarloNumber_);
effectCombo_.setRankIncrementNbRound((short)15);
CustList<EffectEndRoundFoe> custListEffectEndRoundFoe_ = new CustList<EffectEndRoundFoe>(new CollCapacity(1));
EffectEndRoundFoe effectEndRoundFoe_= Instances.newEffectEndRoundFoe();
effectEndRoundFoe_.setInflictedRateHpTarget(Rate.newRate("1/8"));
effectEndRoundFoe_.setFailEndRound("");
effectEndRoundFoe_.setEndRoundRank(28);
effectEndRoundFoe_.setTargetChoice(TargetChoice.NOTHING);
effectEndRoundFoe_.setFail("");
custListEffectEndRoundFoe_.add(effectEndRoundFoe_);
effectCombo_.setEffectEndRound(custListEffectEndRoundFoe_);
listEffectCombos_.add(new ListEffectCombo(new StringList("AIRE_DE_FEU","AIRE_D_HERBE"),effectCombo_));
combos_.setEffects(listEffectCombos_);
return combos_;
}
}
