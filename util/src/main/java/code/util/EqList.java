package code.util;
import code.util.ints.Equallable;
import code.util.ints.Listable;

/** Not compared in tests */
public final class EqList<T extends Equallable<T>> extends AbEqList<T> implements Equallable<EqList<T>> {
    public EqList() {
    }

    public EqList(T... _elements) {
        super(_elements);
    }
    public EqList(Listable<T> _c) {
        super(_c);
    }

    
    public EqList(CollCapacity _capacity) {
        super(_capacity);
    }

    public void removeDuplicates()  {
        int i_ = FIRST_INDEX;
        while (true) {
            if(i_ >= size()) {
                break;
            }
            T e_ = get(i_);
            boolean rem_ = false;
            int next_ = indexOfObj(e_, i_ + 1);
            while (next_ != INDEX_NOT_FOUND_ELT) {
                removeAt(next_);
                rem_ = true;
                next_ = indexOfObj(e_, next_ + 1);
            }
            if (!rem_) {
                i_++;
            }
        }
    }
    @Override
    public int indexOfObj(T _element, int _from) {
        int s_ = size();
        for (int i = _from; i < s_; i++) {
            T e_ = get(i);
            if (_element.eq(e_)) {
                return i;
            }
        }
        return INDEX_NOT_FOUND_ELT;
    }
    @Override
    public boolean eq(EqList<T> _b) {
        int len_ = size();
        if (_b.size() != len_) {
            return false;
        }
        for (int i = FIRST_INDEX; i < len_; i++) {
            T e_ = get(i);
            T f_ = _b.get(i);
            if (!e_.eq(f_)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public EqList<T> subAbEq(int _from, int _to) {
        return sub(_from, _to);
    }

    @Override
    public EqList<T> sub(int _from, int _to) {
        if (_from > _to) {
            return new EqList<T>();
        }
        return new EqList<T>(super.sub(_from, _to));
    }

}
