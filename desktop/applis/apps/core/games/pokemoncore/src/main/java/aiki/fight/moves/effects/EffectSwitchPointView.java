package aiki.fight.moves.effects;

import aiki.db.DataBase;
import aiki.fight.moves.effects.enums.PointViewChangementType;
import aiki.fight.moves.enums.TargetChoice;


public final class EffectSwitchPointView extends Effect {

    private PointViewChangementType pointViewChangement;

    @Override
    public void validate(DataBase _data) {
        super.validate(_data);
        boolean checkTargetChoice_ = false;
        if (pointViewChangement == PointViewChangementType.MIRROR_AGAINST_THROWER) {
            checkTargetChoice_ = true;
        } else if (pointViewChangement == PointViewChangementType.ATTRACT_DAMAGES_MOVES) {
            checkTargetChoice_ = true;
        }
        if (checkTargetChoice_) {
            if (getTargetChoice() != TargetChoice.LANCEUR) {
                _data.setError(true);
            }
            return;
        }
        if (pointViewChangement == PointViewChangementType.THIEF_BONUSES) {
            if (getTargetChoice() == TargetChoice.LANCEUR) {
                _data.setError(true);
            }
            return;
        }
        _data.setError(true);

    }

    public PointViewChangementType getPointViewChangement() {
        return pointViewChangement;
    }

    public void setPointViewChangement(
            PointViewChangementType _pointViewChangement) {
        pointViewChangement = _pointViewChangement;
    }
}
