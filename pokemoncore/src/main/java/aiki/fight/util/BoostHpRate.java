package aiki.fight.util;
import code.maths.Rate;
import code.serialize.CheckedData;
import code.sml.FromAndToString;
import code.util.StringList;
import code.util.ints.Displayable;

@CheckedData
public final class BoostHpRate implements Displayable {

    private static final char SEPARATOR = ';';

    private final byte boost;

    private final Rate hpRate;

    public BoostHpRate(String _str) {
        StringList elements_ = StringList.splitChars(_str, SEPARATOR);
        boost = Byte.parseByte(elements_.first());
        hpRate = new Rate(elements_.last());
    }

    public BoostHpRate(byte _boost, Rate _hpRate) {
        boost = _boost;
        hpRate = _hpRate;
    }

    @FromAndToString
    public static BoostHpRate newBoostHpRate(String _string) {
        return new BoostHpRate(_string);
    }

    @Override
    public String toString() {
        return display();
    }

    public byte getBoost() {
        return boost;
    }

    public Rate getHpRate() {
        return hpRate;
    }

    @FromAndToString
    @Override
    public String display() {
        StringBuilder str_ = new StringBuilder();
        str_.append(boost);
        str_.append(SEPARATOR);
        str_.append(hpRate.toNumberString());
        return str_.toString();
    }
}
