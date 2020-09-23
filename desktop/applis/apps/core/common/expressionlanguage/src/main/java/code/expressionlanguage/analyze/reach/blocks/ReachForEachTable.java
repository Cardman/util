package code.expressionlanguage.analyze.reach.blocks;

import code.expressionlanguage.Argument;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.analyze.blocks.AnalyzingEl;
import code.expressionlanguage.analyze.blocks.ForEachTable;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.instr.ElUtil;
import code.expressionlanguage.analyze.opers.OperationNode;
import code.expressionlanguage.analyze.reach.opers.ReachOperationUtil;
import code.expressionlanguage.exec.blocks.ExecForEachTable;
import code.expressionlanguage.exec.opers.ExecOperationNode;
import code.util.CustList;

public final class ReachForEachTable extends ReachBracedBlock implements ReachLoop {
    private ForEachTable meta;
    private String label;
    protected ReachForEachTable(ForEachTable _info) {
        super(_info);
        meta = _info;
        label = _info.getLabel();
    }

    @Override
    public String getRealLabel() {
        return label;
    }

    @Override
    public void buildExpressionLanguageReadOnly(AnalyzedPageEl _page) {
        _page.setGlobalOffset(meta.getExpressionOffset());
        _page.setOffset(0);
        OperationNode root_ = meta.getRoot();
        Argument argument_ = ReachOperationUtil.tryCalculate(root_, _page);
        if (Argument.isNullValue(argument_)) {
            FoundErrorInterpret static_ = new FoundErrorInterpret();
            static_.setFileName(_page.getCurrentBlock().getFile().getFileName());
            static_.setIndexFile(_page.getTraceIndex());
            //separator char
            static_.buildError(_page.getAnalysisMessages().getNullValue(),
                    _page.getStandards().getAliasNullPe());
            _page.addLocError(static_);
            meta.getSepErrors().add(static_.getBuiltError());
        }
//        _page.getCoverage().putBlockOperationsLoops(getInfo());
//        CustList<ExecOperationNode> op_ = ElUtil.getExecutableNodes(_page, root_);
//        ExecForEachTable exec_ = new ExecForEachTable(getOffset(),label, meta.getImportedClassNameFirst(),
//                meta.getImportedClassNameSecond(),
//                meta.getImportedClassIndexName(), meta.getVariableNameFirst(),
//                meta.getVariableNameSecond(), meta.getExpressionOffset(),op_);
//        exec_.setFile(_page.getBlockToWrite().getFile());
//        _page.getBlockToWrite().appendChild(exec_);
//        _page.getAnalysisAss().getReachMappingBracedMembers().put(this,exec_);
//        _page.getCoverage().putBlockOperations(exec_,getInfo());
    }

    @Override
    public void abruptGroup(AnalyzingEl _anEl) {
        if (!_anEl.isReachable(this)) {
            _anEl.completeAbruptGroup(this);
        }
    }
}
