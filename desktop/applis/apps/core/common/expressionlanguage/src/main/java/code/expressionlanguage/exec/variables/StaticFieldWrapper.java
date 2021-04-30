package code.expressionlanguage.exec.variables;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.ClassField;
import code.expressionlanguage.exec.StackCall;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.expressionlanguage.structs.Struct;

public final class StaticFieldWrapper extends FieldWrapper {
    public StaticFieldWrapper(Struct _container, String _fieldType, ExecRootBlock _rootBlock, ClassField _id) {
        super(_container, _fieldType, _rootBlock, _id);
    }
    public void setValue(StackCall _stack, ContextEl _conf, Argument _right) {
        ExecTemplates.setStaticField(_conf.getExiting(), getFieldType(), _right, _conf, _stack, getId());
    }

    public Struct getValue(StackCall _stack, ContextEl _conf) {

        return ExecTemplates.getStaticField(_conf.getExiting(), getFieldType(), _conf, _stack, getId()).getStruct();
    }
}
