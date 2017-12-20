package code.expressionlanguage.stds;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.opers.util.MethodId;
import code.util.CustList;
import code.util.ObjectMap;
import code.util.StringList;
import code.util.StringMap;

public final class StandardInterface extends StandardType {

    private final StringList superInterfaces;

    protected StandardInterface(String _name,
            ObjectMap<MethodId, StandardMethod> _methods,
            StringList _superInterfaces) {
        super(_name, new StringMap<StandardField>(), new CustList<StandardConstructor>(), _methods);
        superInterfaces = _superInterfaces;
    }

    public StringList getSuperInterfaces() {
        return superInterfaces;
    }

    @Override
    public StringList getDirectSuperClasses(ContextEl _context) {
        return getDirectInterfaces(_context);
    }

    @Override
    public StringList getDirectInterfaces(ContextEl _context) {
        return new StringList(superInterfaces);
    }

    @Override
    public StringList getDirectSuperTypes(ContextEl _context) {
        return getDirectInterfaces(_context);
    }

    @Override
    public boolean mustImplement() {
        return false;
    }
}
