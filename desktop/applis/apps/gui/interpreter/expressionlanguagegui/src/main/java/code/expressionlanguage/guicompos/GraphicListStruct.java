package code.expressionlanguage.guicompos;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.exec.blocks.ExecNamedFunctionBlock;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.structs.*;
import code.gui.*;
import code.util.CustList;
import code.util.Ints;

public final class GraphicListStruct extends InputStruct {

    private Struct render = NullStruct.NULL_VALUE;

    private Struct listener = NullStruct.NULL_VALUE;

    private final AbsGraphicListStr grList;

    public GraphicListStruct(GuiContextEl _ctx,String _className,boolean _simple) {
        super(_className);
        grList = ((LgNamesGui)_ctx.getStandards()).getGuiExecutingBlocks().getWindow().getFact().getGraphicListGenerator().create(_simple, new AdvGraphicListPainter(_ctx.getExecutionInfos()));
        grList.setCell(this, _ctx,null,null,new DefSpecSelectionCtx(_ctx.getExecutionInfos()),null);
    }

    public boolean isCust() {
        return grList instanceof AbsCustGraphicListStr;
    }
    public void add(int _index, Struct _img, Struct _elt) {
        if (!(_img instanceof PreparedLabelStruct)) {
            return;
        }
        PreparedLabel textLabel_ = ((PreparedLabelStruct) _img).getTextLabel();
        grList.add(_index,textLabel_, _elt);
    }
    public void add(int _index, Struct _elt) {
        grList.add(_index, _elt);
    }
    void updateGraphics() {
        grList.updateGraphics();
    }
    public void set(int _index, Struct _img, Struct _elt) {
        if (!(_img instanceof PreparedLabelStruct)) {
            return;
        }
        if (!grList.getList().isValidIndex(_index)) {
            return;
        }
        PreparedLabelStruct img_ = (PreparedLabelStruct) _img;
        PreparedLabel textLabel_ = img_.getTextLabel();
        grList.set(_index, textLabel_,_elt);
    }
    public void set(int _index, Struct _elt) {
        if (!grList.getList().isValidIndex(_index)) {
            return;
        }
        grList.set(_index,_elt);
    }
    public ArrayStruct getListView(ContextEl _ctx) {
        CustList<Struct> list_ = grList.getList();
        int len_ = list_.size();
        String obj_ = StringExpUtil.getPrettyArrayType(_ctx.getStandards().getContent().getCoreNames().getAliasObject());
        ArrayStruct arr_ = new ArrayStruct(len_, obj_);
        for (int i = 0; i < len_; i++) {
            arr_.set(i, list_.get(i));
        }
        return arr_;
    }

    public Ints getSelectedIndexes() {
        return grList.getSelectedIndexes();
    }

    public void setSelectedIndexes(Struct _selectedIndexes) {
        Ints selectedIndexes_ = new Ints();
        if (_selectedIndexes instanceof ArrayStruct) {
            for (Struct s : ((ArrayStruct)_selectedIndexes).list()) {
                selectedIndexes_.add(((NumberStruct) s).intStruct());
            }
            grList.setSelectedIndexes(selectedIndexes_);
        }
    }

    public void clearSelection() {
        if (isCust()) {
            grList.clearSelection();
            return;
        }
        grList.getSelectedIndexes().clear();
    }

    public ArrayStruct getSelectedIndexes(ContextEl _ctx) {
        Ints selectedIndexes_ = grList.getSelectedIndexes();
        int len_ = selectedIndexes_.size();
        String obj_ = StringExpUtil.getPrettyArrayType(_ctx.getStandards().getContent().getPrimTypes().getAliasPrimInteger());
        ArrayStruct arr_ = new ArrayStruct(len_, obj_);
        for (int i = 0; i < len_; i++) {
            arr_.set(i, new IntStruct(selectedIndexes_.get(i)));
        }
        return arr_;
    }

    public Struct getVisibleRowCount() {
        return new IntStruct(grList.getVisibleRowCount());
    }

    public void setVisibleRowCount(Struct _visibleRowCount) {
        int value_ = ((NumberStruct)_visibleRowCount).intStruct();
        grList.setVisibleRowCount(value_);
    }

    public void clear() {
        grList.clear();
    }
    public void remove(int _index) {
        grList.remove(_index);
    }
    public void setListener(Struct _listener) {
        if (_listener instanceof ListSelection) {
            grList.setListener((ListSelection)_listener);
        } else {
            grList.setListener(null);
        }
        listener = _listener;
    }

    public Struct getListener() {
        return listener;
    }

    public void addRange(int _first, int _last) {
        int min_ = Math.min(_first, _last);
        int max_ = Math.max(_first, _last);
        for (int i = min_; i <= max_; i++) {
            grList.getSelectedIndexes().add(i);
        }
    }
    public void clearAllRange() {
        grList.getSelectedIndexes().clear();
    }

    public void clearRange(int _first, int _last) {
        int min_ = Math.min(_first, _last);
        int max_ = Math.max(_first, _last);
        for (int i = min_; i <= max_; i++) {
            grList.getSelectedIndexes().removeObj(i);
        }
    }

    public Struct getRender() {
        return render;
    }

    public void setRender(GuiContextEl _ctx, Struct _render) {
        this.render = _render;
        DefSpecSelectionCtx create_ = new DefSpecSelectionCtx(_ctx.getExecutionInfos());
        if (_render instanceof RenderStruct) {
            RenderStruct rend_ = (RenderStruct) _render;
            Struct paint_ = rend_.getPaint();
            if (paint_ instanceof LambdaStruct) {
                String aliasImageLabel_ = ((LgNamesGui) _ctx.getStandards()).getGuiAliases().getAliasImageLabel();
                PreparedLabelStruct im_ = new PreparedLabelStruct(aliasImageLabel_);
                PreparedLabel lab_ = im_.getTextLabel();
                grList.setCell(this, _ctx, lab_, im_, create_,
                        new DefSpecSelectionStruct(_ctx, this)
                );
            } else {
                grList.setCell(this, _ctx, null, null, create_, null);
            }
        } else {
            grList.setCell(this, _ctx,null,null, create_,null);
        }
    }

    public boolean isEnabledList() {
        return grList.isEnabled();
    }

    @Override
    protected CustComponent getVisibleComponent() {
        return grList.visible();
    }

    @Override
    protected CustComponent getComponent() {
        return grList.scroll();
    }

    @Override
    public Struct isEnabled() {
        return BooleanStruct.of(grList.isEnabled());
    }

    @Override
    public void setEnabled(Struct _enabled) {
        grList.setEnabled(BooleanStruct.isTrue(_enabled));
    }
}
