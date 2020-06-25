package code.expressionlanguage.analyze.util;

import code.util.StringList;

public final class TypeVar {

    private String name;

    private StringList constraints;
    private final StringList errors = new StringList();

    private int offset;

    private int length;

    public String getName() {
        return name;
    }

    public void setName(String _name) {
        name = _name;
    }

    public StringList getConstraints() {
        return constraints;
    }

    public void setConstraints(StringList _constraints) {
        constraints = _constraints;
    }

    public StringList getErrors() {
        return errors;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int _offset) {
        offset = _offset;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int _length) {
        length = _length;
    }
}
