package code.expressionlanguage.assign.blocks;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.blocks.Condition;
import code.expressionlanguage.assign.opers.AssOperationNode;
import code.expressionlanguage.assign.opers.AssUtil;
import code.expressionlanguage.assign.util.AssignedBooleanVariables;
import code.expressionlanguage.assign.util.AssignedVariables;
import code.expressionlanguage.assign.util.AssignedVariablesBlock;
import code.util.CustList;

public abstract class AssCondition extends AssBracedStack implements AssBuildableElMethod {

    private CustList<AssOperationNode> opCondition;
    AssCondition(boolean _completeNormally, boolean _completeNormallyGroup, Condition _c) {
        super(_completeNormally, _completeNormallyGroup);
        opCondition = AssUtil.getExecutableNodes(_c.getRoot());
    }

    @Override
    public void buildExpressionLanguage(AssignedVariablesBlock _a, AnalyzedPageEl _page) {
        AssUtil.getSortedDescNodes(_a,opCondition.last(),this, _page);
        buildConditions(_a);
    }

    @Override
    protected AssignedVariables buildNewAssignedVariable() {
        return new AssignedBooleanVariables();
    }
}
