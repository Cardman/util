package code.util.comparators;
import java.util.Comparator;

import code.util.ints.ListableEntries;

public class ComparatorMapValue<T> implements Comparator<T> {

    private ListableEntries<T, String> map;

    public ComparatorMapValue(ListableEntries<T, String> _map) {
        map = _map;
    }

    @Override
    public int compare(T _o1, T _o2) {
        return map.getVal(_o1).compareTo(map.getVal(_o2));
    }

}
