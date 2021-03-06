package aiki.fight.items;

import aiki.db.DataBase;
import code.maths.Rate;


public final class HealingHpStatus extends HealingStatus {

    public static final String ITEM = "aiki.fight.items.HealingHpStatus";

    private Rate healedHpRate;

    @Override
    public String getItemType() {
        return ITEM;
    }

    @Override
    public void validate(DataBase _data) {
        super.validate(_data);
        validateHealingStatus(_data);
        if (healedHpRate.isZero()) {
            _data.setError(true);
        }
        if (!healedHpRate.isZeroOrGt()) {
            _data.setError(true);

        }
    }

    public Rate getHealedHpRate() {
        return healedHpRate;
    }

    public void setHealedHpRate(Rate _healedHpRate) {
        healedHpRate = _healedHpRate;
    }
}
