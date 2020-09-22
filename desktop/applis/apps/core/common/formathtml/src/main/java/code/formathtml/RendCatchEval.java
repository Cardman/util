package code.formathtml;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.ManageTokens;
import code.expressionlanguage.analyze.TokenErrorMessage;
import code.expressionlanguage.analyze.variables.AnaLocalVariable;
import code.expressionlanguage.common.ConstType;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.files.OffsetStringInfo;
import code.expressionlanguage.analyze.files.OffsetsBlock;
import code.expressionlanguage.analyze.types.ResolvingImportTypes;
import code.formathtml.util.AnalyzingDoc;
import code.util.StringList;

public final class RendCatchEval extends RendAbstractCatchEval {

    private final String className;

    private String importedClassName;

    private int classNameOffset;

    private final String variableName;

    private int variableNameOffset;
    RendCatchEval(OffsetStringInfo _className, OffsetStringInfo _variable, OffsetsBlock _offset) {
        super(_offset);
        className = _className.getInfo();
        classNameOffset = _className.getOffset();
        variableName = _variable.getInfo();
        variableNameOffset = _variable.getOffset();
    }

    public String getImportedClassName() {
        return importedClassName;
    }
    public String getVariableName() {
        return variableName;
    }

    @Override
    public void buildExpressionLanguage(Configuration _cont, RendDocumentBlock _doc, AnalyzingDoc _anaDoc, AnalyzedPageEl _page) {
        _page.setGlobalOffset(variableNameOffset);
        _page.setOffset(0);
        TokenErrorMessage res_ = ManageTokens.partVar(_page).checkTokenVar(variableName, _page);
        if (res_.isError()) {
            FoundErrorInterpret b_ = new FoundErrorInterpret();
            b_.setFileName(_anaDoc.getFileName());
            b_.setIndexFile(variableNameOffset);
            b_.setBuiltError(res_.getMessage());
            Configuration.addError(b_, _anaDoc, _page);
            return;
        }
        _page.setGlobalOffset(classNameOffset);
        importedClassName = ResolvingImportTypes.resolveCorrectType(className, _page);
        AnaLocalVariable lv_ = new AnaLocalVariable();
        lv_.setClassName(importedClassName);
        lv_.setConstType(ConstType.FIX_VAR);
        _page.getInfosVars().put(variableName, lv_);
        RendBlock pBlock_ = getPreviousSibling();
        if (!(pBlock_ instanceof RendAbstractCatchEval)) {
            if (!(pBlock_ instanceof RendTryEval)) {
                if (!(pBlock_ instanceof RendPossibleEmpty)) {
                    FoundErrorInterpret un_ = new FoundErrorInterpret();
                    un_.setFileName(_anaDoc.getFileName());
                    un_.setIndexFile(getOffset().getOffsetTrim());
                    un_.buildError(_page.getAnalysisMessages().getUnexpectedCatchElseFinally(),
                            _page.getKeyWords().getKeyWordCatch(),
                            StringList.join(
                                    new StringList(
                                            _page.getKeyWords().getKeyWordCatch(),
                                            _page.getKeyWords().getKeyWordTry()
                                    ),
                                    OR_ERR));
                    Configuration.addError(un_, _anaDoc, _page);
                } else if (!(pBlock_.getPreviousSibling() instanceof RendAbstractCatchEval)) {
                    if (!(pBlock_.getPreviousSibling() instanceof RendTryEval)) {
                        FoundErrorInterpret un_ = new FoundErrorInterpret();
                        un_.setFileName(_anaDoc.getFileName());
                        un_.setIndexFile(getOffset().getOffsetTrim());
                        un_.buildError(_page.getAnalysisMessages().getUnexpectedCatchElseFinally(),
                                _page.getKeyWords().getKeyWordCatch(),
                                StringList.join(
                                        new StringList(
                                                _page.getKeyWords().getKeyWordCatch(),
                                                _page.getKeyWords().getKeyWordTry()
                                        ),
                                        OR_ERR));
                        Configuration.addError(un_, _anaDoc, _page);
                    }
                }
            }
        }
    }

    @Override
    public void removeAllVars(ImportingPage _ip) {
        super.removeAllVars(_ip);
        String var_ = getVariableName();
        _ip.getValueVars().removeKey(var_);
    }

    @Override
    public void removeAllVars(AnalyzedPageEl _ip) {
        super.removeAllVars(_ip);
        String var_ = getVariableName();
        _ip.getInfosVars().removeKey(var_);
    }
}
