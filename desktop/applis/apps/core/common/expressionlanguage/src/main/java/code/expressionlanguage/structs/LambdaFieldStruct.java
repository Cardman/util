package code.expressionlanguage.structs;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.ClassField;

public final class LambdaFieldStruct extends WithoutParentIdStruct implements LambdaStruct {

    private Argument instanceCall;

    private final String className;

    private final ClassField fid;

    private final boolean shiftInstance;

    private final int ancestor;
    private final boolean affect;
    private boolean staticField;
    private boolean finalField;
    private final String returnFieldType;

    public LambdaFieldStruct(String _className, ClassField _fid,
                             boolean _shiftInstance, int _ancestor, boolean _affect, String _returnFieldType) {
        className = _className;
        fid = _fid;
        shiftInstance = _shiftInstance;
        ancestor = _ancestor;
        affect = _affect;
        returnFieldType = _returnFieldType;
    }

    public Argument getInstanceCall() {
        return instanceCall;
    }

    public boolean isStaticField() {
        return staticField;
    }

    public void setStaticField(boolean staticField) {
        this.staticField = staticField;
    }

    public boolean isFinalField() {
        return finalField;
    }

    public void setFinalField(boolean finalField) {
        this.finalField = finalField;
    }

    public void setInstanceCall(Argument _instanceCall) {
        instanceCall = _instanceCall;
    }

    public ClassField getFid() {
        return fid;
    }

    public boolean isShiftInstance() {
        return shiftInstance;
    }

    public int getAncestor() {
        return ancestor;
    }
    public boolean isAffect() {
        return affect;
    }
    public String getReturnFieldType() {
        return returnFieldType;
    }

    @Override
    public String getClassName(ContextEl _contextEl) {
        return className;
    }


}
