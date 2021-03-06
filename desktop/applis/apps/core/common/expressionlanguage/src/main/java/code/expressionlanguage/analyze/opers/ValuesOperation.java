package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.accessing.Accessed;
import code.expressionlanguage.analyze.blocks.EnumBlock;
import code.expressionlanguage.analyze.blocks.RootBlock;
import code.expressionlanguage.analyze.types.AnaClassArgumentMatching;
import code.expressionlanguage.analyze.types.ResolvingTypes;
import code.expressionlanguage.analyze.util.ContextUtil;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.instr.OperationsSequence;
import code.expressionlanguage.analyze.instr.PartOffset;
import code.expressionlanguage.fwd.opers.AnaValuesContent;
import code.maths.litteralcom.StrTypes;
import code.util.*;
import code.util.core.StringUtil;

public final class ValuesOperation extends LeafOperation {

    private final String className;
    private final AnaValuesContent valuesContent;

    private final CustList<PartOffset> partOffsets = new CustList<PartOffset>();

    public ValuesOperation(int _indexInEl, int _indexChild, MethodOperation _m,
            OperationsSequence _op) {
        super(_indexInEl, _indexChild, _m, _op);
        StrTypes vs_ = getOperations().getValues();
        className = vs_.firstValue();
        valuesContent = new AnaValuesContent(0);
    }

    @Override
    public void analyze(AnalyzedPageEl _page) {
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+ valuesContent.getArgOffset(), _page);
        String glClass_ = _page.getGlobalClass();
        int leftPar_ = className.indexOf('(')+1;
        String sub_ = className.substring(leftPar_,className.lastIndexOf(')'));
        leftPar_ += StringUtil.getFirstPrintableCharIndex(sub_);
        String clName_;
        clName_ = ResolvingTypes.resolveAccessibleIdType(leftPar_,sub_, _page);
        partOffsets.addAllElts(_page.getCurrentParts());
        RootBlock r_ = _page.getAnaClassBody(clName_);
        if (!(r_ instanceof EnumBlock)) {
            FoundErrorInterpret un_ = new FoundErrorInterpret();
            un_.setFileName(_page.getLocalizer().getCurrentFileName());
            un_.setIndexFile(_page.getLocalizer().getCurrentLocationIndex());
            //className len
            un_.buildError(_page.getAnalysisMessages().getUnexpectedType(),
                    clName_);
            _page.getLocalizer().addError(un_);
            addErr(un_.getBuiltError());
            String argClName_ = _page.getAliasObject();
            setResultClass(new AnaClassArgumentMatching(argClName_));
            return;
        }
        valuesContent.setNumberEnum(r_.getNumberAll());
        String curClassBase_ = StringExpUtil.getIdFromAllTypes(glClass_);
        Accessed a_ = new Accessed(r_.getAccess(), r_.getPackageName(), r_.getParentType(), r_);
        if (!ContextUtil.canAccessType(curClassBase_, a_, _page)) {
            FoundErrorInterpret badAccess_ = new FoundErrorInterpret();
            badAccess_.setIndexFile(_page.getLocalizer().getCurrentLocationIndex());
            badAccess_.setFileName(_page.getLocalizer().getCurrentFileName());
            //className len
            badAccess_.buildError(_page.getAnalysisMessages().getInaccessibleType(),
                    clName_,
                    curClassBase_);
            _page.getLocalizer().addError(badAccess_);
            addErr(badAccess_.getBuiltError());
        }
        String ret_ = StringExpUtil.getPrettyArrayType(r_.getWildCardElement());
        setResultClass(new AnaClassArgumentMatching(ret_));
    }

    public CustList<PartOffset> getPartOffsets() {
        return partOffsets;
    }

    public AnaValuesContent getValuesContent() {
        return valuesContent;
    }
}
