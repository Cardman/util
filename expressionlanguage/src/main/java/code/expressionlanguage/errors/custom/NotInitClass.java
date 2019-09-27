package code.expressionlanguage.errors.custom;

import code.expressionlanguage.methods.Classes;
import code.util.StringList;

public final class NotInitClass extends FoundErrorInterpret {

    private static final String CLASS_NAME = "not initializd type";

    private String className;

    @Override
    public String display(Classes _classes) {
        return StringList.concat(super.display(_classes),CLASS_NAME,SEP_KEY_VAL,className,SEP_INFO);
    }

    public void setClassName(String _className) {
        className = _className;
    }

}
