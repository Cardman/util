package code.formathtml.render;

import code.util.Numbers;

public final class MetaNumberedLabel extends MetaLabel {

    private String number;
    private MetaNumberBase base;

    public MetaNumberedLabel(MetaContainer _parent, int _number, MetaNumberBase _base) {
        super(_parent);
        if (_base == MetaNumberBase.NUMBER) {
            number = Integer.toString(_number);
        } else if (_base == MetaNumberBase.LETTER || _base == MetaNumberBase.MAJ_LETTER) {
            number = "";
            char firstLetter_;
            if (_base == MetaNumberBase.LETTER) {
                firstLetter_ = 'a';
            } else {
                firstLetter_ = 'A';
            }
            Numbers<Integer> parts_ = new Numbers<Integer>();
            int current_ = _number;
            while (current_ > 0) {
                parts_.add(current_ % 26);
                current_ /= 26;
            }
            StringBuilder str_ = new StringBuilder(parts_.size());
            int delta_ = 0;
            for (int i: parts_.getReverse()) {
                char diff_ = (char)(i + firstLetter_ - delta_);
                str_.append(diff_);
                delta_ = 1;
            }
            number = str_.toString();
        } else {
            char firstUnit_ = 'i';
            char firstDemi_ = 'v';
            char secondUnit_ = 'x';
            char secondDemi_ = 'l';
            char thirdUnit_ = 'c';
            char thirdDemi_ = 'd';
            char fourthUnit_ = 'm';
            char fourthDemi_ = 'q';
            Numbers<Integer> parts_ = new Numbers<Integer>();
            int current_ = _number;
            while (current_ > 0) {
                parts_.add(current_ % 10000);
                current_ /= 10000;
            }
            StringBuilder str_ = new StringBuilder(parts_.size() * 4);
            for (int i: parts_.getReverse()) {
                if (i < 10) {
                    str_.append(getLatinString(i, firstUnit_, firstDemi_, secondUnit_));
                } else if (i < 100) {
                    str_.append(getLatinString(i / 10 % 10, secondUnit_, secondDemi_, thirdUnit_));
                    str_.append(getLatinString(i % 10, firstUnit_, firstDemi_, secondUnit_));
                } else if (i < 1000) {
                    str_.append(getLatinString(i  / 100 % 10, thirdUnit_, thirdDemi_, fourthUnit_));
                    str_.append(getLatinString(i / 10 % 10, secondUnit_, secondDemi_, thirdUnit_));
                    str_.append(getLatinString(i % 10, firstUnit_, firstDemi_, secondUnit_));
                } else {
                    if (i < 4) {
                        for (int j = 0; j < i; j++) {
                            str_.append(fourthUnit_);
                        }
                    } else if (i == 4) {
                        str_.append(fourthUnit_);
                        str_.append(fourthDemi_);
                    } else {
                        str_.append(fourthDemi_);
                        for (int j = 0; j < i - 5; j++) {
                            str_.append(fourthUnit_);
                        }
                    }
                    str_.append(getLatinString(i  / 100 % 10, thirdUnit_, thirdDemi_, fourthUnit_));
                    str_.append(getLatinString(i / 10 % 10, secondUnit_, secondDemi_, thirdUnit_));
                    str_.append(getLatinString(i % 10, firstUnit_, firstDemi_, secondUnit_));
                }
                str_.append(" ");
            }
            str_.deleteCharAt(str_.length() - 1);
            if (_base == MetaNumberBase.LATIN_MAJ) {
                number = toLatinUpperCase(str_.toString());
            } else {
                number = str_.toString();
            }
        }
        base = _base;
    }
    public static String toLatinUpperCase(String _string) {
        int len_ = _string.length();
        StringBuilder str_ = new StringBuilder(len_);
        for (int i = 0; i < len_; i++) {
            char curr_ = _string.charAt(i);
            if (curr_ <= ' ') {
                str_.append(" ");
                continue;
            }
            int char_ = curr_ - 'a' + 'A';
            str_.append((char)char_);
        }
        return str_.toString();
    }
    private static StringBuilder getLatinString(int _digit, char _unit, char _demi, char _nextUnit) {
        StringBuilder str_ = new StringBuilder(4);
        if (_digit < 4) {
            for (int j = 0; j < _digit; j++) {
                str_.append(_unit);
            }
        } else if (_digit == 4) {
            str_.append(_unit);
            str_.append(_demi);
        } else if (_digit < 9) {
            str_.append(_demi);
            for (int j = 0; j < _digit - 5; j++) {
                str_.append(_unit);
            }
        } else if (_digit == 9) {
            str_.append(_unit);
            str_.append(_nextUnit);
        }
        return str_;
    }
    public String getNumber() {
        return number;
    }

    public MetaNumberBase getBase() {
        return base;
    }
}
