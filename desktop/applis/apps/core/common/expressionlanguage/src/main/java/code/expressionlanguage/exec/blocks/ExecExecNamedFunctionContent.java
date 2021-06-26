package code.expressionlanguage.exec.blocks;

import code.util.CustList;
import code.util.StringList;

public final class ExecExecNamedFunctionContent {

    private final String name;

    private final StringList importedParametersTypes;
    private final CustList<Boolean> parametersRef;

    private final StringList parametersNames;

    private final boolean retRef;
    private final boolean varargs;

    public ExecExecNamedFunctionContent(String _name, StringList _importedParametersTypes, CustList<Boolean> _parametersRef, StringList _parametersNames, boolean _retRef, boolean _varargs) {
        this.name = _name;
        this.importedParametersTypes = _importedParametersTypes;
        this.parametersRef = _parametersRef;
        this.parametersNames = _parametersNames;
        this.retRef = _retRef;
        this.varargs = _varargs;
    }

    public CustList<Boolean> getParametersRef() {
        return parametersRef;
    }

    public String getName() {
        return name;
    }

    public StringList getImportedParametersTypes() {
        return importedParametersTypes;
    }

    public StringList getParametersNames() {
        return parametersNames;
    }

    public boolean isRetRef() {
        return retRef;
    }

    public boolean isVarargs() {
        return varargs;
    }
}
