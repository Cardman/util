package code.expressionlanguage.analyze.blocks;
import code.expressionlanguage.analyze.AnalyzedPageEl;
import code.expressionlanguage.exec.blocks.ExecBreakBlock;
import code.expressionlanguage.analyze.errors.custom.FoundErrorInterpret;
import code.expressionlanguage.analyze.files.OffsetStringInfo;
import code.expressionlanguage.analyze.files.OffsetsBlock;
import code.util.IdList;
import code.util.IdMap;
import code.util.StringList;

public final class BreakBlock extends AbruptBlock {

    private String label;
    private int labelOffset;
    private int labelOffsetRef;
    private StringList errorsRefLabels = new StringList();

    public BreakBlock(OffsetStringInfo _label, OffsetsBlock _offset) {
        super(_offset);
        label = _label.getInfo();
        labelOffset = _label.getOffset();
    }

    public String getLabel() {
        return label;
    }

    public int getLabelOffset() {
        return labelOffset;
    }

    @Override
    public void buildExpressionLanguageReadOnly(AnalyzedPageEl _page) {
        checkBreakable(_page);
        ExecBreakBlock exec_ = new ExecBreakBlock(getOffset(),label);
        exec_.setFile(_page.getBlockToWrite().getFile());
        _page.getBlockToWrite().appendChild(exec_);
        _page.getCoverage().putBlockOperations(exec_,this);
    }

    private void checkBreakable(AnalyzedPageEl _page) {
        boolean childOfBreakable_ = false;
        BracedBlock b_ = getParent();
        while (b_ != null) {
            if (b_ instanceof BreakableBlock) {
                if (label.isEmpty()) {
                    if (b_ instanceof Loop || b_ instanceof SwitchBlock) {
                        childOfBreakable_ = true;
                        break;
                    }
                } else {
                    if (StringList.quickEq(label, ((BreakableBlock)b_).getRealLabel())){
                        childOfBreakable_ = true;
                        labelOffsetRef = ((BreakableBlock) b_).getRealLabelOffset();
                        break;
                    }
                }
            }
            b_ = b_.getParent();
        }
        if (!childOfBreakable_) {
            AnalyzedPageEl page_ = _page;
            page_.setGlobalOffset(getOffset().getOffsetTrim());
            page_.setOffset(0);
            FoundErrorInterpret un_ = new FoundErrorInterpret();
            un_.setFileName(getFile().getFileName());
            un_.setIndexFile(getOffset().getOffsetTrim());
            if (label.isEmpty()) {
                //key word len
                un_.buildError(_page.getAnalysisMessages().getUnexpectedAbrupt(),
                        _page.getKeyWords().getKeyWordBreak(),
                        StringList.join(
                                new StringList(
                                        _page.getKeyWords().getKeyWordSwitch(),
                                        _page.getKeyWords().getKeyWordFor(),
                                        _page.getKeyWords().getKeyWordForeach(),
                                        _page.getKeyWords().getKeyWordDo(),
                                        _page.getKeyWords().getKeyWordIter(),
                                        _page.getKeyWords().getKeyWordWhile()
                                ),
                                "|"));
            } else {
                //key word len
                un_.buildError(_page.getAnalysisMessages().getUnexpectedAbruptLab(),
                        _page.getKeyWords().getKeyWordBreak(),
                        label,
                        StringList.join(
                                new StringList(
                                        _page.getKeyWords().getKeyWordSwitch(),
                                        _page.getKeyWords().getKeyWordTry(),
                                        _page.getKeyWords().getKeyWordCatch(),
                                        _page.getKeyWords().getKeyWordFinally(),
                                        _page.getKeyWords().getKeyWordIf(),
                                        _page.getKeyWords().getKeyWordElseif(),
                                        _page.getKeyWords().getKeyWordElse(),
                                        _page.getKeyWords().getKeyWordFor(),
                                        _page.getKeyWords().getKeyWordForeach(),
                                        _page.getKeyWords().getKeyWordDo(),
                                        _page.getKeyWords().getKeyWordIter(),
                                        _page.getKeyWords().getKeyWordWhile()
                                ),
                                "|"));
            }
            _page.addLocError(un_);
            if (label.isEmpty()) {
                setReachableError(true);
                getErrorsBlock().add(un_.getBuiltError());
            } else {
                errorsRefLabels.add(un_.getBuiltError());
            }
        }
    }

    @Override
    public void abrupt(AnalyzingEl _anEl) {
        super.abrupt(_anEl);
        boolean childOfBreakable_ = false;
        BracedBlock b_ = getParent();
        while (b_ != null) {
            if (b_ instanceof BreakableBlock) {
                if (label.isEmpty()) {
                    if (b_ instanceof Loop || b_ instanceof SwitchBlock) {
                        childOfBreakable_ = true;
                        break;
                    }
                } else {
                    if (StringList.quickEq(label, ((BreakableBlock)b_).getRealLabel())){
                        childOfBreakable_ = true;
                        break;
                    }
                }
            }
            b_ = b_.getParent();
        }
        if (!childOfBreakable_) { 
            return;
        }
        IdMap<BreakBlock, BreakableBlock> breakables_ = _anEl.getBreakables();
        IdMap<BreakBlock, IdMap<BreakableBlock, IdList<BracedBlock>>> breakablesAncestors_ = _anEl.getBreakablesAncestors();
        BracedBlock par_ = getParent();
        IdList<BracedBlock> pars_ = new IdList<BracedBlock>();
        BracedBlock a_ = b_;
        while (par_ != a_) {
            pars_.add(par_);
            par_ = par_.getParent();
        }
        IdMap<BreakableBlock, IdList<BracedBlock>> id_;
        id_ = new IdMap<BreakableBlock, IdList<BracedBlock>>();
        id_.put((BreakableBlock) a_, pars_);
        breakablesAncestors_.put(this, id_);
        breakables_.put(this, (BreakableBlock) a_);
    }

    public int getLabelOffsetRef() {
        return labelOffsetRef;
    }

    public StringList getErrorsRefLabels() {
        return errorsRefLabels;
    }
}
