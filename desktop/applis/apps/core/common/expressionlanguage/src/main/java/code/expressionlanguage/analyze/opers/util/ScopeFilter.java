package code.expressionlanguage.analyze.opers.util;

import code.expressionlanguage.analyze.util.ClassMethodIdAncestor;

public final class ScopeFilter {
    private final ClassMethodIdAncestor id;
    private final boolean baseClass;
    private final boolean superClass;
    private final boolean retRef;
    private final String glClass;

    public ScopeFilter(ClassMethodIdAncestor _id, boolean _baseAccess, boolean _superClass, boolean _retRef,
                       String _glClass) {
        this.id = _id;
        this.baseClass = _baseAccess;
        this.superClass = _superClass;
        this.retRef = _retRef;
        this.glClass = _glClass;
    }

    public boolean isSuperClass() {
        return superClass;
    }

    public boolean isBaseClass() {
        return baseClass;
    }

    public boolean isRetRef() {
        return retRef;
    }

    public String getGlClass() {
        return glClass;
    }

    public ClassMethodIdAncestor getId() {
        return id;
    }
}
