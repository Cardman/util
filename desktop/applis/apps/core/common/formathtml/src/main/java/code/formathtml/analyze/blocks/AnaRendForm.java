package code.formathtml.analyze.blocks;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.files.OffsetsBlock;
import code.expressionlanguage.analyze.inherits.AnaTemplates;
import code.expressionlanguage.analyze.inherits.Mapping;
import code.expressionlanguage.analyze.opers.OperationNode;
import code.expressionlanguage.analyze.variables.AnaLocalVariable;
import code.formathtml.analyze.RenderAnalysis;
import code.formathtml.analyze.ResultText;
import code.formathtml.analyze.AnalyzingDoc;
import code.sml.Element;
import code.util.CustList;
import code.util.StringList;

public final class AnaRendForm extends AnaRendElement {
    private CustList<OperationNode> roots;
    private OperationNode root;

    private StringList texts = new StringList();
    private StringList varNames = new StringList();
    AnaRendForm(Element _elt, OffsetsBlock _offset) {
        super(_elt, _offset);
    }

    @Override
    protected void processAttributes(AnaRendDocumentBlock _doc, Element _read, StringList _list, AnalyzingDoc _anaDoc, AnalyzedPageEl _page) {
        _list.removeAllString(StringList.concat(_anaDoc.getPrefix(),_anaDoc.getRendKeyWords().getAttrCommand()));
        _list.removeAllString(_anaDoc.getRendKeyWords().getAttrAction());
        roots = new CustList<OperationNode>();
        String href_ = _read.getAttribute(StringList.concat(_anaDoc.getPrefix(),_anaDoc.getRendKeyWords().getAttrCommand()));
        ResultText r_ = new ResultText();
        if (href_.startsWith(CALL_METHOD)) {
            String lk_ = href_.substring(1);
            int rowsGrId_ = getAttributeDelimiter(StringList.concat(_anaDoc.getPrefix(),_anaDoc.getRendKeyWords().getAttrCommand()));
            r_.buildAna(lk_, rowsGrId_, _anaDoc, _page);
            texts = r_.getTexts();
            roots = r_.getOpExpRoot();
            for (OperationNode e: r_.getOpExpRoot()) {
                Mapping m_ = new Mapping();
                m_.setArg(e.getResultClass());
                m_.setParam(_page.getAliasLong());
                if (!AnaTemplates.isCorrectOrNumbers(m_, _page)) {
                    FoundErrorInterpret badEl_ = new FoundErrorInterpret();
                    badEl_.setFileName(_anaDoc.getFileName());
                    badEl_.setIndexFile(rowsGrId_);
                    badEl_.buildError(_page.getAnalysisMessages().getBadImplicitCast(),
                            StringList.join(e.getResultClass().getNames(),AND_ERR),
                            _page.getAliasLong());
                    AnalyzingDoc.addError(badEl_, _anaDoc, _page);
                }
            }
            int l_ = roots.size();
            StringList formArg_ = new StringList();
            StringList varNames_ = new StringList();
            for (int i = 0; i< l_; i++) {
                String varLoc_ = AnaRendBlock.lookForVar(varNames_, _page);
                varNames_.add(varLoc_);
            }
            varNames = varNames_;
            int i_ = 0;
            for (String v:varNames_) {
                AnaLocalVariable lv_ = new AnaLocalVariable();
                lv_.setClassName(roots.get(i_).getResultClass().getSingleNameOrEmpty());
                _page.getInfosVars().addEntry(v,lv_);
                formArg_.add(StringList.concat(AnaRendBlock.LEFT_PAR, v,AnaRendBlock.RIGHT_PAR));
                i_++;
            }
            String pref_ = r_.quickRender(lk_, formArg_);
            if (pref_.indexOf('(') < 0) {
                pref_ = StringList.concat(pref_,AnaRendBlock.LEFT_PAR,AnaRendBlock.RIGHT_PAR);
            }
            root = RenderAnalysis.getRootAnalyzedOperations(pref_, 0, _anaDoc, _page);
            for (String v:varNames_) {
                _page.getInfosVars().removeKey(v);
            }
        }
    }

    public StringList getTexts() {
        return texts;
    }

    public OperationNode getRoot() {
        return root;
    }

    public StringList getVarNames() {
        return varNames;
    }

    public CustList<OperationNode> getRoots() {
        return roots;
    }
}
