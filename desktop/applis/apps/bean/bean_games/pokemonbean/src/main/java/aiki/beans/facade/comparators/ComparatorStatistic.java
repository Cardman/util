package aiki.beans.facade.comparators;
import aiki.fight.enums.Statistic;
import code.util.core.NumberUtil;
import code.util.ints.Comparing;

public final class ComparatorStatistic implements Comparing<Statistic> {

    @Override
    public int compare(Statistic _arg0, Statistic _arg1) {
        return NumberUtil.compareLg(_arg0.ordinal(), _arg1.ordinal());
    }
}