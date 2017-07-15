package aiki.comparators;
import java.util.Comparator;

import code.util.EnumMap;
import aiki.DataBase;
import aiki.map.pokemon.enums.Gender;

public class ComparatorTrStringGender implements Comparator<Gender> {

    private EnumMap<Gender,String> translator;

    public ComparatorTrStringGender(EnumMap<Gender,String> _translator) {
        translator = _translator;
    }

    @Override
    public int compare(Gender _e1, Gender _e2) {
        return compare(translator, _e1, _e2);
    }

    public static int compare(EnumMap<Gender,String> _translator, Gender _e1, Gender _e2) {
        String trOne_;
        if (_translator.contains(_e1)) {
            trOne_ = _translator.getVal(_e1);
        } else {
            trOne_ = DataBase.EMPTY_STRING;
        }
        String trTwo_;
        if (_translator.contains(_e2)) {
            trTwo_ = _translator.getVal(_e2);
        } else {
            trTwo_ = DataBase.EMPTY_STRING;
        }
        return trOne_.compareTo(trTwo_);
    }
}
