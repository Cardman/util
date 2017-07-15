package code.util.ints;
import java.util.ListIterator;

import code.util.Numbers;

public interface Listable<T> extends Iterable<T> {

    Listable<T> getReverse();

    int size();

    boolean isEmpty();

//    Iterator<T> iterator();

    Object[] toArray();

    void add(T _e);

    void addAllElts(Listable<? extends T> _c);
//    boolean addAll(Collection<? extends T> c);

    void clear();

    boolean isValidIndex(int _index);

    T get(int _index);

    void set(int _index, T _element);

    void add(int _index, T _element);

    void removeAt(Number _index);

//    T remove(int index);
    void remove(int _index);

    ListIterator<T> listIterator();

    ListIterator<T> listIterator(int _index);

//    List<T> subList(int fromIndex, int toIndex);

    T first();

    T last();

    boolean containsNull();

    void removeNull();

    int indexOfNull();
    int indexOfNull(int _from);

    int lastIndexOfNull();

    Numbers<Integer> indexesOfNull();

    String join(char _char);

    String join(String _string);

    Listable<T> sub(int _from, int _to);

    void swapIndexes(int _i, int _j);
}
