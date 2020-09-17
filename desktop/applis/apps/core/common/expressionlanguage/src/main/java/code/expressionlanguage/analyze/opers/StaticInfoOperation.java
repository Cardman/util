package code.expressionlanguage.analyze.opers;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.inherits.AnaTemplates;
import code.expressionlanguage.inherits.Templates;
import code.expressionlanguage.instr.OperationsSequence;
import code.expressionlanguage.instr.PartOffset;
import code.expressionlanguage.exec.opers.ReductibleOperable;
import code.expressionlanguage.inherits.ClassArgumentMatching;
import code.expressionlanguage.analyze.types.ResolvingImportTypes;
import code.expressionlanguage.structs.ClassMetaInfo;
import code.util.CustList;
import code.util.StringList;

public final class StaticInfoOperation extends LeafOperation implements ReductibleOperable {

    private String className;

    private CustList<PartOffset> partOffsets = new CustList<PartOffset>();

    public StaticInfoOperation(int _indexInEl, int _indexChild,
            MethodOperation _m, OperationsSequence _op) {
        super(_indexInEl, _indexChild, _m, _op);
    }

    @Override
    public void analyze(ContextEl _conf) {
        OperationsSequence op_ = getOperations();
        String originalStr_ = op_.getValues().getValue(CustList.FIRST_INDEX);
        String str_ = originalStr_.trim();
        int offset_ = StringList.getFirstPrintableCharIndex(originalStr_);
        setRelativeOffsetPossibleAnalyzable(getIndexInEl()+offset_, _conf);
        int afterLeftPar_ = str_.indexOf(PAR_LEFT) + 1;
        String realCl_ = str_.substring(afterLeftPar_, str_.lastIndexOf(PAR_RIGHT));
        AnalyzedPageEl page_ = _conf.getAnalyzing();
        if (StringList.quickEq(realCl_.trim(), page_.getStandards().getAliasVoid())) {
            className = realCl_.trim();
            setResultClass(new ClassArgumentMatching(page_.getStandards().getAliasClassType()));
            return;
        }
        int off_ = StringList.getFirstPrintableCharIndex(realCl_);
        String classStr_;
        classStr_ = ResolvingImportTypes.resolveCorrectType(_conf, afterLeftPar_ + off_, realCl_, realCl_.contains(Templates.TEMPLATE_BEGIN));
        partOffsets.addAllElts(page_.getCurrentParts());
        className = classStr_;
        setResultClass(new ClassArgumentMatching(page_.getStandards().getAliasClassType()));
    }

    @Override
    public void tryCalculateNode(ContextEl _conf) {
        setArg(this, _conf, className);
    }
    private static void setArg(OperationNode _current, ContextEl _conf,String _className) {
        if (_className.contains(AnaTemplates.PREFIX_VAR_TYPE)) {
            return;
        }
        ClassMetaInfo candidate_ = new ClassMetaInfo(_className);
        CustList<ClassMetaInfo> classMetaInfos_ = _conf.getAnalyzing().getClassMetaInfos();
        for (ClassMetaInfo c: classMetaInfos_) {
            if (c.sameReference(candidate_)) {
                Argument a_ = new Argument(c);
                _current.setSimpleArgumentAna(a_, _conf.getAnalyzing());
                return;
            }
        }
        classMetaInfos_.add(candidate_);
        Argument a_ = new Argument(candidate_);
        _current.setSimpleArgumentAna(a_, _conf.getAnalyzing());
    }

    public String getClassName() {
        return className;
    }

    public CustList<PartOffset> getPartOffsets() {
        return partOffsets;
    }
}
