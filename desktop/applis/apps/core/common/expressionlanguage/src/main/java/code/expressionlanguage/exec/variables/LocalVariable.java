package code.expressionlanguage.exec.variables;
import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.structs.NullStruct;
import code.expressionlanguage.structs.Struct;
import code.util.core.StringUtil;

public final class LocalVariable {

    private Struct element = NullStruct.NULL_VALUE;

    private String className;

    public static LocalVariable newLocalVariable(Struct _struct, ContextEl _cont) {
        Struct struct_ = Argument.getNull(_struct);
        return newLocalVariable(struct_, struct_.getClassName(_cont));
    }

    public static LocalVariable newLocalVariable(Struct _struct, String _type) {
        LocalVariable loc_ = new LocalVariable();
        loc_.setStruct(_struct);
        loc_.setClassName(_type);
        return loc_;
    }
    public Struct getStruct() {
        return element;
    }

    public void setStruct(Struct _element) {
        element = Argument.getNull(_element);
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String _className) {
        className = StringUtil.nullToEmpty(_className);
    }

}
