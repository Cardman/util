package code.expressionlanguage.errors.custom;

import code.expressionlanguage.methods.Classes;
import code.expressionlanguage.opers.util.ConstructorId;
import code.util.StringList;

public final class UndefinedConstructorError extends FoundErrorInterpret {

    private static final String CLASS_NAME = "undefined constructor";

    private String className;

    private ConstructorId id;

    @Override
    public String display(Classes _classes) {
        return StringList.concat(super.display(_classes),SEP_INFO,CLASS_NAME,SEP_KEY_VAL,className,".",id.getSignature(),SEP_INFO);
    }

    public void setClassName(String _className) {
        className = _className;
    }

    public ConstructorId getId() {
        return id;
    }

    public void setId(ConstructorId _id) {
        id = _id;
    }
}
