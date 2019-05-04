package code.util;
import code.util.ints.ListableEntries;




public final class EnumMap<K extends Enum<K>, V> extends AbsMap<K,V> {

//    //list cannot be null, even by reflection
//    private final CustList<EntryCust<K,V>> list = new CustList<EntryCust<K,V>>();

    public EnumMap() {
    }

    public EnumMap(ListableEntries<K, V> _arg0) {
        putAllMap(_arg0);
    }
    
    public EnumMap(CollCapacity _capacity) {
        super(_capacity);
    }

    @Override
    public EnumList<K> getKeys() {
        EnumList<K> s_ = new EnumList<K>();
        for (EntryCust<K, V> e: getList()) {
            s_.add(e.getKey());
        }
        return s_;
    }

    @Override
    int indexOfEntry(K _key) {
        int index_ = CustList.FIRST_INDEX;
        for (EntryCust<K, V> e:getList()) {
            if (e.getKey() == _key) {
                return index_;
            }
            index_++;
        }
        return CustList.INDEX_NOT_FOUND_ELT;
    }

}
