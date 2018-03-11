package aiki.fight.items;
import aiki.DataBase;
import aiki.exceptions.DataException;
import code.maths.Rate;
import code.util.annot.RwXml;

@RwXml
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
        if (healedHpRate.isZero()) {
            throw new DataException();
        }
        if (!healedHpRate.isZeroOrGt()) {
            throw new DataException();
        }
    }

    public Rate getHealedHpRate() {
        return healedHpRate;
    }

    public void setHealedHpRate(Rate _healedHpRate) {
        healedHpRate = _healedHpRate;
    }
}
