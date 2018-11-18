package code.expressionlanguage.options;

import code.expressionlanguage.VariableSuffix;

public final class Options {

    private boolean initializeStaticClassFirst = true;
    private boolean quickCompare = true;
    private VariableSuffix suffixVar = VariableSuffix.NONE;
    private boolean varTypeFirst = true;
    private boolean upperLong;
    private boolean endLineSemiColumn = true;
    private boolean specialEnumsMethods = true;
    private boolean allParametersSort = true;

    public char getEndLine() {
        if (endLineSemiColumn) {
            return ';';
        }
        return ':';
    }
    public char getSuffix() {
        if (!endLineSemiColumn) {
            return ';';
        }
        return ':';
    }
    public boolean isInitializeStaticClassFirst() {
        return initializeStaticClassFirst;
    }

    public void setInitializeStaticClassFirst(boolean _initializeStaticClassFirst) {
        initializeStaticClassFirst = _initializeStaticClassFirst;
    }

    public boolean isQuickCompare() {
        return quickCompare;
    }

    public void setQuickCompare(boolean _quickCompare) {
        quickCompare = _quickCompare;
    }

    public VariableSuffix getSuffixVar() {
        return suffixVar;
    }

    public void setSuffixVar(VariableSuffix _suffixVar) {
        suffixVar = _suffixVar;
    }

    public boolean isVarTypeFirst() {
        return varTypeFirst;
    }

    public void setVarTypeFirst(boolean _varTypeFirst) {
        varTypeFirst = _varTypeFirst;
    }

    public boolean isUpperLong() {
        return upperLong;
    }

    public void setUpperLong(boolean _upperLong) {
        upperLong = _upperLong;
    }

    public boolean isEndLineSemiColumn() {
        return endLineSemiColumn;
    }

    public void setEndLineSemiColumn(boolean _endLineSemiColumn) {
        endLineSemiColumn = _endLineSemiColumn;
    }
    public boolean isSpecialEnumsMethods() {
        return specialEnumsMethods;
    }
    public void setSpecialEnumsMethods(boolean _specialEnumsMethods) {
        specialEnumsMethods = _specialEnumsMethods;
    }
    public boolean isAllParametersSort() {
        return allParametersSort;
    }
    public void setAllParametersSort(boolean _allParametersSort) {
        allParametersSort = _allParametersSort;
    }

}
