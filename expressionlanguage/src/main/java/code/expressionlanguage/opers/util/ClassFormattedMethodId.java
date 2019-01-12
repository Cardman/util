package code.expressionlanguage.opers.util;
import code.util.StringList;
import code.util.ints.Equallable;

public final class ClassFormattedMethodId implements Equallable<ClassFormattedMethodId> {

    private final String className;

    private final MethodId constraints;

    public ClassFormattedMethodId(String _className, MethodId _constraints) {
        className = _className;
        constraints = _constraints;
    }

    public String getClassName() {
        return className;
    }

    public MethodId getConstraints() {
        return constraints;
    }

    @Override
    public boolean eq(ClassFormattedMethodId _g) {
        if (!StringList.quickEq(className, _g.className)) {
            return false;
        }
        return constraints.eq(_g.constraints);
    }

}
