package code.util;

/**
    @author Cardman
*/
public final class LongTreeMap<V> extends NatTreeMap<Long, V>  {

    @Override
    long convert(Long _key) {
        return _key;
    }
}
