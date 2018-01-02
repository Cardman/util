package code.expressionlanguage.methods.util;

import code.util.StringList;

public final class BadVariableName extends FoundErrorInterpret {

    private static final String CLASS_NAME = "bad variable name";

    private String varName;

    @Override
    public String display() {
        return StringList.concat(super.display(),CLASS_NAME,SEP_KEY_VAL,varName,SEP_INFO);
    }

    public String getVarName() {
        return varName;
    }

    public void setVarName(String _className) {
        varName = _className;
    }

}
