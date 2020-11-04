package code.expressionlanguage.exec.types;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.StrTypes;
import code.util.CustList;
import code.util.IntTreeMap;

abstract class ExecLeafPartType extends ExecPartType {

    private String typeName;
    private String importedTypeName = EMPTY_STRING;
    private String previousSeparator;
    ExecLeafPartType(ExecParentPartType _parent, int _index, String _type, String _previousSeparator, String _previousOperator) {
        super(_parent, _index, _previousOperator);
        typeName = _type;
        previousSeparator = _previousSeparator;
    }
    abstract void checkDynExistence(ContextEl _an, CustList<StrTypes> _dels);
    final String exportHeader() {
        return importedTypeName;
    }
    void setImportedTypeName(String _importedTypeName) {
        importedTypeName = _importedTypeName;
    }

    final String getTypeName() {
        return typeName;
    }
    @Override
    final ExecPartType getFirstChild() {
        return null;
    }

    String getPreviousSeparator() {
        return previousSeparator;
    }

}
