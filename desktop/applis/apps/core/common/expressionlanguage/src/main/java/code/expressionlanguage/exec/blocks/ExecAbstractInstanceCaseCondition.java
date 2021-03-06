package code.expressionlanguage.exec.blocks;

import code.expressionlanguage.exec.calls.AbstractPageEl;

public final class ExecAbstractInstanceCaseCondition extends ExecAbstractCaseCondition {
    private final String importedClassName;
    private final boolean specific;

    private final String variableName;

    public ExecAbstractInstanceCaseCondition(String _variableName, String _importedClassName, boolean _spec) {
        variableName = _variableName;
        importedClassName = _importedClassName;
        specific = _spec;
    }

    @Override
    public void removeAllVars(AbstractPageEl _ip) {
        super.removeAllVars(_ip);
        _ip.removeRefVar(variableName);
    }

    public boolean isSpecific(){
        return specific;
    }
    public String getImportedClassName() {
        return importedClassName;
    }
    public String getVariableName() {
        return variableName;
    }
}
