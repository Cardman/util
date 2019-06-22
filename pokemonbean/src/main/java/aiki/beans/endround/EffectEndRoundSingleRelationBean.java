package aiki.beans.endround;
import aiki.fight.moves.effects.EffectEndRoundSingleRelation;
import code.maths.LgInt;
import code.maths.Rate;
import code.util.NatCmpTreeMap;
import code.util.*;

public class EffectEndRoundSingleRelationBean extends EffectEndRoundBean {
    private LongTreeMap< Rate> rateDamageFunctionOfNbRounds;
    private NatCmpTreeMap<LgInt, Rate> lawForEnablingEffect;

    @Override
    public void beforeDisplaying() {
        super.beforeDisplaying();
        EffectEndRoundSingleRelation effect_ = (EffectEndRoundSingleRelation) getEffect();
        LongTreeMap< Rate> rateDamageFunctionOfNbRounds_;
        rateDamageFunctionOfNbRounds_ = new LongTreeMap< Rate>();
        for (Long k: effect_.getRateDamageFunctionOfNbRounds().getKeys()) {
            rateDamageFunctionOfNbRounds_.put(k, effect_.getRateDamageFunctionOfNbRounds().getVal(k));
        }
        rateDamageFunctionOfNbRounds = rateDamageFunctionOfNbRounds_;
        NatCmpTreeMap<LgInt, Rate> lawForEnablingEffect_;
        lawForEnablingEffect_ = new NatCmpTreeMap<LgInt, Rate>();
        for (Rate k: effect_.getLawForEnablingEffect().events()) {
            lawForEnablingEffect_.put(k.intPart(), effect_.getLawForEnablingEffect().normalizedRate(k));
        }
        lawForEnablingEffect = lawForEnablingEffect_;
    }

    public LongTreeMap<Rate> getRateDamageFunctionOfNbRounds() {
        return rateDamageFunctionOfNbRounds;
    }

    public NatCmpTreeMap<LgInt,Rate> getLawForEnablingEffect() {
        return lawForEnablingEffect;
    }
}