package code.expressionlanguage.fwd.opers;

public final class AnaLambdaFieldContent {
    private boolean staticField;
    private boolean finalField;
    private boolean affField;
    private boolean instanceField;

    public boolean isStaticField() {
        return staticField;
    }

    public void setStaticField(boolean _staticField) {
        this.staticField = _staticField;
    }

    public boolean isFinalField() {
        return finalField;
    }

    public void setFinalField(boolean _finalField) {
        this.finalField = _finalField;
    }

    public boolean isAffField() {
        return affField;
    }

    public void setAffField(boolean _affField) {
        this.affField = _affField;
    }

    public boolean isInstanceField() {
        return instanceField;
    }

    public void setInstanceField(boolean _instanceField) {
        instanceField = _instanceField;
    }
}
