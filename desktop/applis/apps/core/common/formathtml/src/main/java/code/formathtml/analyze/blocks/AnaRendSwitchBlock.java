package code.formathtml.analyze.blocks;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.blocks.EnumBlock;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.files.OffsetStringInfo;
import code.expressionlanguage.analyze.opers.OperationNode;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.analyze.types.AnaTypeUtil;
import code.expressionlanguage.analyze.util.ContextUtil;
import code.expressionlanguage.common.AnaGeneType;
import code.expressionlanguage.common.StringExpUtil;
import code.formathtml.analyze.RenderAnalysis;
import code.formathtml.analyze.AnalyzingDoc;
import code.util.StringList;
import code.util.core.StringUtil;

public final class AnaRendSwitchBlock  extends AnaRendParentBlock implements AnaRendLocBreakableBlock {

    private final String label;
    private final int labelOffset;

    private final String value;
    private final int valueOffset;

    private OperationNode root;
    private AnaClassArgumentMatching result;

    private boolean instance;
    private String instanceTest = "";
    AnaRendSwitchBlock(OffsetStringInfo _value, OffsetStringInfo _label, int _offset) {
        super(_offset);
        value = _value.getInfo();
        valueOffset = _value.getOffset();
        label = _label.getInfo();
        labelOffset = _label.getOffset();
    }

    @Override
    public void buildExpressionLanguage(AnaRendDocumentBlock _doc, AnalyzingDoc _anaDoc, AnalyzedPageEl _page) {
        _page.setGlobalOffset(valueOffset);
        _page.zeroOffset();
        _anaDoc.setAttribute(_anaDoc.getRendKeyWords().getAttrValue());
        root = RenderAnalysis.getRootAnalyzedOperations(value, 0, _anaDoc, _page);
        result = root.getResultClass();
        AnaClassArgumentMatching clArg_ = root.getResultClass();
        String type_ = clArg_.getSingleNameOrEmpty();
        if (type_.isEmpty()) {
            FoundErrorInterpret un_ = new FoundErrorInterpret();
            un_.setFileName(_anaDoc.getFileName());
            un_.setIndexFile(valueOffset);
            un_.buildError(_page.getAnalysisMessages().getUnknownType(),
                    type_);
            AnalyzingDoc.addError(un_, _anaDoc, _page);
        } else {
            String id_ = StringExpUtil.getIdFromAllTypes(type_);
            AnaGeneType classBody_ = _page.getAnaGeneType(id_);
            boolean final_ = true;
            if (classBody_ != null) {
                final_ = ContextUtil.isHyperAbstract(classBody_);
            } else if (type_.startsWith("[")) {
                final_ = false;
            }
            if (!AnaTypeUtil.isPrimitiveOrWrapper(id_, _page) && !StringUtil.quickEq(id_, _page.getAliasString()) && !(classBody_ instanceof EnumBlock)) {
                if (!final_) {
                    instance = true;
                    instanceTest = type_;
                } else {
                    FoundErrorInterpret un_ = new FoundErrorInterpret();
                    un_.setFileName(_anaDoc.getFileName());
                    un_.setIndexFile(valueOffset);
                    un_.buildError(_page.getAnalysisMessages().getUnexpectedType(),
                            id_);
                    AnalyzingDoc.addError(un_, _anaDoc, _page);
                }
            } else {
                instanceTest = type_;
            }
        }
        AnaRendBlock first_ = getFirstChild();
        while (first_ != null) {
            AnaRendBlock elt_ = first_;
            if (elt_ instanceof  AnaRendCaseCondition) {
                first_ = first_.getNextSibling();
                continue;
            }
            if (isPossibleEmpty(elt_)) {
                first_ = first_.getNextSibling();
                continue;
            }
            if (elt_ instanceof  AnaRendDefaultCondition) {
                first_ = first_.getNextSibling();
                continue;
            }
            _page.setGlobalOffset(getOffset());
            _page.zeroOffset();
            FoundErrorInterpret un_ = new FoundErrorInterpret();
            un_.setFileName(_anaDoc.getFileName());
            un_.setIndexFile(getOffset());
            un_.buildError(_page.getAnalysisMessages().getUnexpectedSwitch(),
                    _page.getKeyWords().getKeyWordSwitch(),
                    StringUtil.join(
                            new StringList(
                                    _page.getKeyWords().getKeyWordCase(),
                                    _page.getKeyWords().getKeyWordDefault()
                            ),
                            OR_ERR));
            AnalyzingDoc.addError(un_, _anaDoc, _page);
            first_ = first_.getNextSibling();
        }
    }

    @Override
    public String getRealLabel() {
        return label;
    }

    @Override
    public int getRealLabelOffset() {
        return labelOffset;
    }

    public boolean isInstance() {
        return instance;
    }

    public String getInstanceTest() {
        return instanceTest;
    }

    public AnaClassArgumentMatching getResult() {
        return result;
    }

    public OperationNode getRoot() {
        return root;
    }

    public int getValueOffset() {
        return valueOffset;
    }
}
