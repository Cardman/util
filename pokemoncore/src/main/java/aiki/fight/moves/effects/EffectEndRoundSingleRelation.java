package aiki.fight.moves.effects;
import code.maths.Rate;
import code.maths.montecarlo.MonteCarloNumber;
import code.util.NumberMap;
import code.util.annot.RwXml;
import aiki.DataBase;
import aiki.exceptions.DataException;
import aiki.fight.moves.effects.enums.RelationType;
import aiki.fight.moves.enums.TargetChoice;

@RwXml
public class EffectEndRoundSingleRelation extends EffectEndRound {

    private NumberMap<Long,Rate> rateDamageFunctionOfNbRounds;
    private MonteCarloNumber lawForEnablingEffect;

    @Override
    public void validate(DataBase _data) {
        super.validate(_data);
        lawForEnablingEffect.deleteZeroEvents();
        if (getTargetChoice() == TargetChoice.LANCEUR) {
            throw new DataException();
        }
        if (lawForEnablingEffect.events().isEmpty()) {
            throw new DataException();
        }
        Rate min_ = lawForEnablingEffect.minimum();
        if (!min_.isZeroOrGt()) {
            throw new DataException();
        }
        if (min_.isZero()) {
            throw new DataException();
        }
        for (Rate e: lawForEnablingEffect.events()) {
            if (!e.isInteger()) {
                throw new DataException();
            }
        }
        for (Rate v: rateDamageFunctionOfNbRounds.values()) {
            if (!v.isZeroOrGt()) {
                throw new DataException();
            }
        }
        if (!rateDamageFunctionOfNbRounds.isEmpty()) {
            return;
        }
        throw new DataException();
    }

    @Override
    public RelationType getRelation() {
        return RelationType.RELATION_UNIQUE;
    }

    public NumberMap<Long,Rate> getRateDamageFunctionOfNbRounds() {
        return rateDamageFunctionOfNbRounds;
    }
    public void setRateDamageFunctionOfNbRounds(NumberMap<Long,Rate> _rateDamageFunctionOfNbRounds) {
        rateDamageFunctionOfNbRounds = _rateDamageFunctionOfNbRounds;
    }
    public MonteCarloNumber getLawForEnablingEffect() {
        return lawForEnablingEffect;
    }
    public void setLawForEnablingEffect(MonteCarloNumber _lawForEnablingEffect) {
        lawForEnablingEffect = _lawForEnablingEffect;
    }
}
