package code.expressionlanguage.stds;

import code.expressionlanguage.common.GeneInterface;
import code.util.CustList;
import code.util.StringList;

public final class StandardInterface extends StandardType implements GeneInterface {

    private final StringList allSuperTypes = new StringList();

    private final StringList superInterfaces;

    public StandardInterface(String _name,
                             CustList<StandardMethod> _methods,
            StringList _superInterfaces) {
        super(_name, new CustList<StandardConstructor>(), _methods);
        superInterfaces = _superInterfaces;
    }

    @Override
    public StringList getDirectInterfaces() {
        return new StringList(superInterfaces);
    }

    @Override
    public StringList getDirectSuperTypes() {
        return getDirectInterfaces();
    }

    @Override
    public StringList getAllGenericSuperTypes() {
        return allSuperTypes;
    }
    @Override
    public StringList getAllSuperTypes() {
        return allSuperTypes;
    }

}
