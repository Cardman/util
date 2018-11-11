package code.gui;
import code.util.EntryCust;
import code.util.EnumList;
import code.util.EnumMap;
import code.util.TreeMap;
import code.util.comparators.ComparatorIndexes;
import code.util.comparators.ComparatorMapValue;
import code.util.ints.Listable;
import code.util.ints.ListableEntries;

public abstract class ComboBox<T extends Enum<T>> extends GraphicCombo {

    private static final String EMPTY_STRING = "";

    private EnumList<T> order = new EnumList<T>();

    private TreeMap<T,String> elements;

    private boolean withDefaultValue;
    public ComboBox(){
    }

    public ComboBox(TreeMap<T,String> _tr){
        elements = _tr;
    }

    protected TreeMap<T, String> getElements() {
        return elements;
    }

    public void refresh(Listable<T> _order,ListableEntries<T,String> _tr) {
        order.clear();
        order.addAllElts(_order);
        super.removeAllItems();
        elements = new TreeMap<T,String>(new ComparatorIndexes<T>(order));
        EnumMap<T,String> m_ = createMap(_tr);
        elements.putAllMap(m_);
        for (T e: elements.getKeys()) {
            addItem(_tr.getVal(e));
        }
    }

    public void refresh(ListableEntries<T,String> _tr) {
        super.removeAllItems();
        EnumMap<T,String> m_ = createMap(_tr);
        elements = new TreeMap<T,String>(new ComparatorMapValue<T>(m_));
        elements.putAllMap(m_);
        for (T e: elements.getKeys()) {
            addItem(_tr.getVal(e));
        }
    }

    private EnumMap<T,String> createMap(ListableEntries<T,String> _tr) {
        EnumMap<T,String> m_ = new EnumMap<T,String>(_tr);
        if (withDefaultValue) {
            m_.put(null, EMPTY_STRING);
        }
        return m_;
    }

    @Override
    public void removeItem(int _anIndex) {
        TreeMap<T, String> tr_;
        tr_ = getElements();
        T e_ = tr_.getKey(_anIndex);
        tr_.removeKey(e_);
        super.removeItem(_anIndex);
    }

    public void addItem(T _t, String _dis) {
        TreeMap<T, String> tr_;
        tr_ = getElements();
        tr_.put(_t, _dis);
        addItem(_dis);
    }
    public void setSelectedItem(T t) {
        int i_ = 0;
        for (EntryCust<T, String> e: getElements().entryList()) {
            if (e.getKey() == t) {
                selectItem(i_);
                return;
            }
            i_++;
        }
    }
    @Override
    public void removeAllItems() {
        getElements().clear();
        super.removeAllItems();
    }
    public T getCurrent() {
        int index_ = getSelectedIndex();
        if (index_ < 0) {
            return null;
        }
        return elements.getKey(index_);
    }
    public boolean isSelectNullCurrent() {
        int index_ = getSelectedIndex();
        if (index_ < 0) {
            return false;
        }
        return elements.getKey(index_) == null;
    }

//    public void setCurrent(T _current) {
//        List<T> keys_ = new List<T>(elements.getKeys());
//        int index_ = keys_.indexOfObj(_current);
//        if (index_ < 0) {
//            return;
//        }
//        setSelectedIndex(index_);
//    }

    public void setWithDefaultValue(boolean _withDefaultValue) {
        withDefaultValue = _withDefaultValue;
    }

    public boolean isWithDefaultValue() {
        return withDefaultValue;
    }
}
