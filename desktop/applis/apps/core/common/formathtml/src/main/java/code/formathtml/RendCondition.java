package code.formathtml;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.opers.OperationNode;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.exec.ConditionReturn;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.files.OffsetStringInfo;
import code.expressionlanguage.analyze.files.OffsetsBlock;
import code.expressionlanguage.functionid.ClassMethodId;
import code.expressionlanguage.analyze.util.ClassMethodIdReturn;
import code.expressionlanguage.stds.PrimitiveTypes;
import code.expressionlanguage.structs.BooleanStruct;
import code.formathtml.exec.RendDynOperationNode;
import code.formathtml.util.AnalyzingDoc;
import code.util.CustList;
import code.util.StringList;

public abstract class RendCondition extends RendParentBlock implements RendWithEl, RendReducableOperations {

    private String condition;

    private int conditionOffset;

    private CustList<RendDynOperationNode> opCondition;
    RendCondition(OffsetStringInfo _condition, OffsetsBlock _offset) {
        super(_offset);
        condition = _condition.getInfo();
        conditionOffset = _condition.getOffset();
    }

    @Override
    public void buildExpressionLanguage(Configuration _cont, RendDocumentBlock _doc, AnalyzingDoc _anaDoc, AnalyzedPageEl _page) {
        buildConditions(_cont, _anaDoc, _page);
    }

    protected void buildConditions(Configuration _cont, AnalyzingDoc _analyzingDoc, AnalyzedPageEl _page) {
        _page.setGlobalOffset(conditionOffset);
        _page.setOffset(0);
        _analyzingDoc.setAttribute(_cont.getRendKeyWords().getAttrCondition());
        opCondition = RenderExpUtil.getAnalyzedOperations(condition, 0, _analyzingDoc, _page);
        RendDynOperationNode elCondition_ = opCondition.last();
        OperationNode root_ = _page.getCurrentRoot();
        AnaClassArgumentMatching exp_ = root_.getResultClass();
        if (!exp_.isBoolType(_page)) {
            ClassMethodIdReturn res_ = OperationNode.tryGetDeclaredImplicitCast(_page.getStandards().getAliasPrimBoolean(), exp_, _page);
            if (res_.isFoundMethod()) {
                ClassMethodId cl_ = new ClassMethodId(res_.getId().getClassName(),res_.getRealId());
                exp_.getImplicits().add(cl_);
                exp_.setRootNumber(res_.getRootNumber());
                exp_.setMemberNumber(res_.getMemberNumber());
            } else {
                ClassMethodIdReturn trueOp_ = OperationNode.fetchTrueOperator(exp_, _page);
                if (trueOp_.isFoundMethod()) {
                    ClassMethodId cl_ = new ClassMethodId(trueOp_.getId().getClassName(),trueOp_.getRealId());
                    exp_.getImplicitsTest().add(cl_);
                    exp_.setRootNumberTest(trueOp_.getRootNumber());
                    exp_.setMemberNumberTest(trueOp_.getMemberNumber());
                } else {
                    FoundErrorInterpret un_ = new FoundErrorInterpret();
                    un_.setFileName(_analyzingDoc.getFileName());
                    un_.setIndexFile(conditionOffset);
                    un_.buildError(_page.getAnalysisMessages().getUnexpectedType(),
                            StringList.join(exp_.getNames(),AND_ERR));
                    Configuration.addError(un_, _analyzingDoc, _page);
                }
            }
        }
        exp_.setUnwrapObjectNb(PrimitiveTypes.BOOL_WRAP);
        RenderExpUtil.setImplicits(elCondition_, _page, root_);
    }

    @Override
    public void reduce(Configuration _context) {
        RendDynOperationNode r_ = opCondition.last();
        opCondition = RenderExpUtil.getReducedNodes(r_);
    }

    final ConditionReturn evaluateCondition(Configuration _context) {
        ImportingPage last_ = _context.getLastPage();
        last_.setOffset(conditionOffset);
        last_.setProcessingAttribute(_context.getRendKeyWords().getAttrCondition());
        Argument arg_ = RenderExpUtil.calculateReuse(opCondition,_context);
        if (_context.getContext().hasException()) {
            return ConditionReturn.CALL_EX;
        }
        if (BooleanStruct.isTrue(arg_.getStruct())) {
            return ConditionReturn.YES;
        }
        return ConditionReturn.NO;
    }
}
