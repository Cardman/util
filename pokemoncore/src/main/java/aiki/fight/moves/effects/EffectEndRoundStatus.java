package aiki.fight.moves.effects;
import aiki.DataBase;
import aiki.exceptions.DataException;
import code.maths.Rate;
import code.util.annot.RwXml;

@RwXml
public abstract class EffectEndRoundStatus extends EffectEndRound {

    private Rate inflictedRateHpTarget;

    @Override
    public void validate(DataBase _data) {
        super.validate(_data);
        if (inflictedRateHpTarget == null) {
            throw new DataException();
        }
    }

    public Rate getInflictedRateHpTarget() {
        return inflictedRateHpTarget;
    }

    public void setInflictedRateHpTarget(Rate _inflictedRateHpTarget) {
        inflictedRateHpTarget = _inflictedRateHpTarget;
    }
}
