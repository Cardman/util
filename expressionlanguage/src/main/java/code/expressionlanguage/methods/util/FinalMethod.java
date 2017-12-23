package code.expressionlanguage.methods.util;

import code.expressionlanguage.opers.util.MethodId;

public final class FinalMethod extends FoundErrorInterpret {

    private static final String SEP_CLASS_METHOD = ".";

    private static final String CLASS_NAME = "final method";

    private String className;

    private MethodId id;

    @Override
    public String display() {
        return super.display()+CLASS_NAME+SEP_KEY_VAL+className+SEP_CLASS_METHOD+id.getSignature()+SEP_INFO;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String _className) {
        className = _className;
    }

    public MethodId getId() {
        return id;
    }

    public void setId(MethodId _id) {
        id = _id;
    }
}
