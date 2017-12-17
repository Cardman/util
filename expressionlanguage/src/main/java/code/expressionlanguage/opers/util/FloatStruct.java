package code.expressionlanguage.opers.util;

import code.expressionlanguage.ContextEl;
import code.util.ObjectMap;

public final class FloatStruct extends Struct {

    private final float value;

    public FloatStruct(float _value) {
        value = _value;
    }

    @Override
    public boolean isNull() {
        return false;
    }

    @Override
    public Boolean isJavaObject() {
        return true;
    }

    @Override
    public String getClassName(ContextEl _context) {
        return Float.class.getName();
    }

    @Override
    public Object getInstance() {
        return value;
    }

    @Override
    public ObjectMap<ClassField, Struct> getFields() {
        return null;
    }
}
