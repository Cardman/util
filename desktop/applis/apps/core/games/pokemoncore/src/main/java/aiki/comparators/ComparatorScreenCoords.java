package aiki.comparators;
import aiki.map.util.ScreenCoords;
import code.util.core.SortConstants;
import code.util.ints.Comparing;

public final class ComparatorScreenCoords implements Comparing<ScreenCoords> {

    @Override
    public int compare(ScreenCoords _o1,
            ScreenCoords _o2) {
        int cmp_ = _o1.getXcoords() - _o2.getXcoords();
        if (cmp_ != SortConstants.EQ_CMP) {
            return cmp_;
        }
        return _o1.getYcoords() - _o2.getYcoords();
    }

}
