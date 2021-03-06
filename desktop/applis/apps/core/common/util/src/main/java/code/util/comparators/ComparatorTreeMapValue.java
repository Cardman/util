package code.util.comparators;
import code.util.AbsMap;
import code.util.core.NumberUtil;
import code.util.ints.Comparing;

public final class ComparatorTreeMapValue<T> implements Comparing<T> {

    private AbsMap<T, Integer> map;

    public ComparatorTreeMapValue(AbsMap<T, Integer> _map) {
        map = _map;
    }

    @Override
    public int compare(T _o1, T _o2) {
        return NumberUtil.compareLg(map.getVal(_o1), map.getVal(_o2));
    }

}
