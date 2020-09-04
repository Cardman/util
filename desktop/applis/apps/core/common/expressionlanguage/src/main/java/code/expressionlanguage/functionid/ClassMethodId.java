package code.expressionlanguage.functionid;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.exec.inherits.ExecTemplates;
import code.util.StringList;

public final class ClassMethodId {

    private final String className;

    private final MethodId constraints;

    public ClassMethodId(String _className, MethodId _constraints) {
        className = _className;
        constraints = _constraints;
    }

    public String formatType(String _type, ContextEl _conf) {
        if (getConstraints().getKind() == MethodAccessKind.STATIC_CALL) {
            return _conf.getLastPage().formatVarType(_type,_conf);
        }
        return _type;
    }

    public String formatType(ExecRootBlock _rootBlock, String _owner, String _formatted) {
        if (getConstraints().getKind() == MethodAccessKind.STATIC_CALL) {
            return ExecTemplates.quickFormat(_rootBlock,_owner, _formatted);
        }
        return _formatted;
    }

    public String getClassName() {
        return className;
    }

    public MethodId getConstraints() {
        return constraints;
    }

    public boolean eq(ClassMethodId _g) {
        if (!StringList.quickEq(className, _g.className)) {
            return false;
        }
        return constraints.eq(_g.constraints);
    }

}
