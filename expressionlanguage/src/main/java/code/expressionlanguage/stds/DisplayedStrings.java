package code.expressionlanguage.stds;

public final class DisplayedStrings {
    private String trueString;
    private String falseString;
    private String nullString;

    private String staticString;
    private String staticCallString;

    public String getTrueString() {
        return trueString;
    }
    public void setTrueString(String _trueString) {
        trueString = _trueString;
    }
    public String getFalseString() {
        return falseString;
    }
    public void setFalseString(String _falseString) {
        falseString = _falseString;
    }
    public String getNullString() {
        return nullString;
    }
    public void setNullString(String _nullString) {
        nullString = _nullString;
    }

    public String getStaticString() {
        return staticString;
    }

    public void setStaticString(String _staticString) {
        staticString = _staticString;
    }

    public String getStaticCallString() {
        return staticCallString;
    }

    public void setStaticCallString(String _staticCallString) {
        staticCallString = _staticCallString;
    }
}
