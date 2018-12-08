package code.expressionlanguage.errors.custom;

import code.expressionlanguage.methods.Classes;
import code.expressionlanguage.opers.util.ClassName;
import code.expressionlanguage.opers.util.MethodId;
import code.util.StringList;

public class StaticInstanceOverriding extends FoundErrorInterpret {

    private static final String SEP_LOC_INFO = ",";
    private static final String SEP_CLASS_PATH = ".";
    private static final String STATIC_BASE = "static method base";
    private static final String INSTANCE_BASE = "instance method base";
    private static final String STATIC_SUBCLASS = "static method subclass";
    private static final String INSTANCE_SUBCLASS = "instance method subclass";

    private ClassName baseClass;

    private MethodId methodeId;

    private boolean staticBaseMethod;

    @Override
    public String display(Classes _classes) {
        if (staticBaseMethod) {
            return StringList.concat(super.display(_classes),INSTANCE_SUBCLASS,SEP_LOC_INFO,
                    STATIC_BASE,
                    SEP_KEY_VAL,baseClass.getName(),
                    SEP_CLASS_PATH,methodeId.getSignature(),SEP_INFO);
        }
        return StringList.concat(super.display(_classes),STATIC_SUBCLASS,SEP_LOC_INFO,
                INSTANCE_BASE,
                SEP_KEY_VAL,baseClass.getName(),
                SEP_CLASS_PATH,methodeId.getSignature(),SEP_INFO);
    }

    public MethodId getMethodeId() {
        return methodeId;
    }

    public void setMethodeId(MethodId _methodeId) {
        methodeId = _methodeId;
    }

    public ClassName getBaseClass() {
        return baseClass;
    }

    public void setBaseClass(ClassName _baseClass) {
        baseClass = _baseClass;
    }

    public boolean isStaticBaseMethod() {
        return staticBaseMethod;
    }

    public void setStaticBaseMethod(boolean _staticBaseMethod) {
        staticBaseMethod = _staticBaseMethod;
    }
}
