package code.expressionlanguage.exec.types;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.StrTypes;
import code.util.CustList;

final class ExecEmptyPartType extends ExecLeafPartType {
    ExecEmptyPartType(ExecParentPartType _parent, int _index, String _type, String _previousSeparator, String _previousOperator) {
        super(_parent, _index, _type, _previousSeparator, _previousOperator);
    }

    @Override
    void checkDynExistence(ContextEl _an, CustList<StrTypes> _dels) {
    }

}
