package code.expressionlanguage.instr;

import code.util.*;

public final class ResultAfterOperators {
    private ResultAfterDoubleDotted doubleDotted;
    private IntTreeMap<Character> parsBrackets;
    private boolean constString;
    private boolean constChar;
    private boolean constText;
    private boolean beginOrEnd;
    private boolean partOfString;
    private int nbChars;
    public ResultAfterDoubleDotted getDoubleDotted() {
        return doubleDotted;
    }

    public void setDoubleDotted(ResultAfterDoubleDotted _doubleDotted) {
        doubleDotted = _doubleDotted;
    }

    public IntTreeMap< Character> getParsBrackets() {
        return parsBrackets;
    }

    public void setParsBrackets(IntTreeMap< Character> _parsBrackets) {
        parsBrackets = _parsBrackets;
    }

    public boolean isConstString() {
        return constString;
    }

    public void setConstString(boolean _constString) {
        constString = _constString;
    }

    public boolean isConstChar() {
        return constChar;
    }

    public void setConstChar(boolean _constChar) {
        constChar = _constChar;
    }

    public boolean isConstText() {
        return constText;
    }

    public void setConstText(boolean _constText) {
        constText = _constText;
    }

    public boolean isBeginOrEnd() {
        return beginOrEnd;
    }

    public void setBeginOrEnd(boolean _beginOrEnd) {
        beginOrEnd = _beginOrEnd;
    }

    public int getNbChars() {
        return nbChars;
    }

    public void setNbChars(int _nbChars) {
        nbChars = _nbChars;
    }

    public boolean isPartOfString() {
        return partOfString;
    }

    public void setPartOfString(boolean _partOfString) {
        partOfString = _partOfString;
    }

}
