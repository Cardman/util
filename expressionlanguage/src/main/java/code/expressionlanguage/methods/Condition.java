package code.expressionlanguage.methods;
import org.w3c.dom.Element;

import code.expressionlanguage.Argument;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.ElUtil;
import code.expressionlanguage.PageEl;
import code.expressionlanguage.PrimitiveTypeUtil;
import code.expressionlanguage.methods.exceptions.BadConditionExpressionException;
import code.expressionlanguage.methods.exceptions.BadConstructorCall;
import code.expressionlanguage.opers.Calculation;
import code.expressionlanguage.opers.ExpressionLanguage;
import code.expressionlanguage.opers.OperationNode;
import code.util.CustList;

public abstract class Condition extends BracedStack implements StackableBlockGroup {

    private String condition;

    private CustList<OperationNode> opCondition;
//    private ExpressionLanguage elCondition;

    public Condition(Element _el, ContextEl _importingPage, int _indexChild,
            BracedBlock _m) {
        super(_el, _importingPage, _indexChild, _m);
        condition = _el.getAttribute(ATTRIBUTE_CONDITION);
    }

    @Override
    public void buildExpressionLanguage(ContextEl _cont) {
        FunctionBlock f_ = getFunction();
        PageEl page_ = _cont.getLastPage();
//        page_.setProcessingNode(getAssociateElement());
        page_.setProcessingAttribute(ATTRIBUTE_CONDITION);
//        page_.setLookForAttrValue(true);
        page_.setOffset(0);
//        opCondition = ElUtil.getAnalyzedOperations(condition, _cont, f_.isStaticContext());
        opCondition = ElUtil.getAnalyzedOperations(condition, _cont, Calculation.staticCalculation(f_.isStaticContext()));
//        ExpressionLanguage elCondition_ = new ExpressionLanguage(condition, _cont, true, new Calculation(StepCalculation.RIGHT));
//        operations = elCondition_.getOperations();
        OperationNode elCondition_ = opCondition.last();
        if (!elCondition_.getResultClass().matchClass(PrimitiveTypeUtil.PRIM_BOOLEAN)) {
            if (!elCondition_.getResultClass().matchClass(Boolean.class)) {
                throw new BadConditionExpressionException(_cont.joinPages());
            }
        }
    }

    public final ExpressionLanguage getElCondition() {
//        return new ExpressionLanguage(condition, _cont, true, new Calculation(StepCalculation.RIGHT));
        return new ExpressionLanguage(opCondition);
    }

    public final String getCondition() {
        return condition;
    }

    @Override
    public final void checkCallConstructor(ContextEl _cont) {
        PageEl p_ = _cont.getLastPage();
//        p_.setProcessingNode(getAssociateElement());
        p_.setProcessingAttribute(ATTRIBUTE_CONDITION);
//        p_.setLookForAttrValue(true);
        for (OperationNode o: opCondition) {
            if (o.isSuperThis()) {
                int off_ = o.getFullIndexInEl();
                p_.setOffset(off_);
                throw new BadConstructorCall(_cont.joinPages());
            }
        }
    }
    final boolean evaluateCondition(ContextEl _context) {
        ExpressionLanguage exp_;
        PageEl last_ = _context.getLastPage();
        if (!last_.getCurrentEls().isEmpty()) {
            exp_ = last_.getCurrentEls().last();
        } else {
            exp_ = getElCondition();
            last_.setCurrentBlock(this);
            last_.setCurrentEls(new CustList<ExpressionLanguage>(exp_));
        }
        last_.setOffset(0);
//        last_.setLookForAttrValue(true);
        last_.setProcessingAttribute(ATTRIBUTE_CONDITION);
        Argument arg_ = exp_.calculateMember(_context);
        exp_.setCurrentOper(null);
        last_.getCurrentEls().clear();
        return (Boolean) arg_.getObject();
    }
}
