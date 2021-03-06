package code.expressionlanguage.common;

import code.util.StringMap;

public final class TryReplacingType extends AbstractReplacingType {

    @Override
    protected boolean replace(String _type, StringMap<String> _varTypes, StringBuilder _str, int _i) {
        tryReplaceType(_type, _varTypes, _str, _i);
        return false;
    }
    private void tryReplaceType(String _type, StringMap<String> _varTypes, StringBuilder _str, int _i) {
        int diese_ = getDiese();
        String sub_ = _type.substring(diese_ + 1, _i);
        String val_ = _varTypes.getVal(sub_);
        if (val_ != null) {
            tryReplaceType(_str, val_);
        } else {
            sub_ = _type.substring(diese_, _i);
            _str.append(sub_);
        }
    }

    private static void tryReplaceType(StringBuilder _str, String _value) {
        int j_ = getMaxIndex(_str, _str.length() - 1);
        if (_value.startsWith(SUB_TYPE)) {
            if (!isSubOrSubChar(_str, j_)) {
                _str.insert(j_ + 1, SUB_TYPE);
            }
            _str.append(_value.substring(SUB_TYPE.length()));
        } else if (_value.startsWith(SUP_TYPE)) {
            if (!isSubOrSubChar(_str, j_)) {
                _str.insert(j_ + 1, SUP_TYPE);
            }
            _str.append(_value.substring(SUP_TYPE.length()));
        } else {
            _str.append(_value);
        }
    }

}
