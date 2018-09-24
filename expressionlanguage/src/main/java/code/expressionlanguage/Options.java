package code.expressionlanguage;

public final class Options {

    private boolean eqPlus;
    private boolean catChars;
    private boolean multipleAffectations = true;
    private boolean initializeStaticClassFirst = true;
    private boolean quickCompare = true;
    private VariableSuffix suffixVar = VariableSuffix.NONE;

    public boolean applyEqPlus() {
        if (!isMultipleAffectations()) {
            return true;
        }
        return eqPlus;
    }

    public boolean isEqPlus() {
        return eqPlus;
    }

    public void setEqPlus(boolean _eqPlus) {
        eqPlus = _eqPlus;
    }

    public boolean isCatChars() {
        return catChars;
    }

    public void setCatChars(boolean _catChars) {
        catChars = _catChars;
    }

    public boolean isMultipleAffectations() {
        return multipleAffectations;
    }

    public void setMultipleAffectations(boolean _multipleAffectations) {
        multipleAffectations = _multipleAffectations;
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

}
