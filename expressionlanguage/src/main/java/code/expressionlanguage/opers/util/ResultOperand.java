package code.expressionlanguage.opers.util;

public final class ResultOperand {

    private ClassArgumentMatching result;

    private boolean catString;
    private boolean catChars;
    private boolean ok = true;
    public ClassArgumentMatching getResult() {
        return result;
    }
    public void setResult(ClassArgumentMatching _result) {
        result = _result;
    }
    public boolean isCatString() {
        return catString;
    }
    public void setCatString(boolean _catString) {
        catString = _catString;
    }
    public boolean isCatChars() {
        return catChars;
    }
    public void setCatChars(boolean _catChars) {
        catChars = _catChars;
    }
    public boolean isOk() {
        return ok;
    }
    public void setOk(boolean _ok) {
        ok = _ok;
    }
}
