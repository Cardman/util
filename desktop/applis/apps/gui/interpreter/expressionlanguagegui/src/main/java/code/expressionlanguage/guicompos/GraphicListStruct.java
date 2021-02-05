package code.expressionlanguage.guicompos;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.StringExpUtil;
import code.expressionlanguage.structs.*;
import code.gui.*;
import code.util.CustList;
import code.util.Ints;

import java.awt.*;

public final class GraphicListStruct extends InputStruct {

    private final CustList<Struct> list;
    private final CustList<PreparedLabelStruct> listComponents = new CustList<PreparedLabelStruct>();
    private final CustList<IndexableListener> indexableMouse = new CustList<IndexableListener>();
    private final CustList<IndexableListener> indexableKey = new CustList<IndexableListener>();
    private Ints selectedIndexes;

    private Struct render = NullStruct.NULL_VALUE;

    private Struct listener = NullStruct.NULL_VALUE;

    private final PanelStruct panel;
    private final ScrollPaneStruct scroll;

    private int firstIndex = -1;

    private int lastIndex = -1;

    private final boolean simple;

    private int visibleRowCount = 8;

    private boolean enabledList = true;

    public GraphicListStruct(GuiContextEl _ctx,String _className,boolean _simple) {
        this(_ctx,_className,_simple,
                new ArrayStruct(0,StringExpUtil.getPrettyArrayType(_ctx.getStandards().getContent().getPrimTypes().getAliasPrimInteger())),
                new ArrayStruct(0,StringExpUtil.getPrettyArrayType(_ctx.getStandards().getContent().getCoreNames().getAliasObject())));
    }

    public GraphicListStruct(GuiContextEl _ctx,String _className,boolean _simple, Struct _selectedIndexes, Struct _objects) {
        super(_className);
        selectedIndexes = new Ints();
        if (_selectedIndexes instanceof ArrayStruct) {
            for (Struct s : ((ArrayStruct)_selectedIndexes).list()) {
                selectedIndexes.add(((NumberStruct) s).intStruct());
            }
        }
        if (_objects instanceof ArrayStruct) {
            list = ((ArrayStruct)_objects).list();
        } else {
            list = new CustList<Struct>();
        }
        simple = _simple;
        LgNamesGui stds_ = (LgNamesGui) _ctx.getStandards();
        panel = PanelStruct.newPageBox(stds_.getGuiAliases().getAliasPanel());
        panel.setAutoscrolls(BooleanStruct.of(true));
        scroll = ScrollPaneStruct.newScroll(panel, stds_.getGuiAliases().getAliasScrollPane());
    }
    public void add(ContextEl _ctx, int _index, Struct _img, Struct _elt) {
        if (!(_img instanceof PreparedLabelStruct)) {
            return;
        }
        PreparedLabelStruct img_ = (PreparedLabelStruct) _img;
        panel.add(img_,_index);
        list.add(_index, _elt);
        listComponents.add(_index, img_);
        if (simple) {
            SimpleSelectEltListStruct i_ = new SimpleSelectEltListStruct(_ctx, this, _index);
            img_.addMouseListener(i_);
            indexableMouse.add(i_);
            reindex(indexableMouse);
        } else {
            MultSelectKeyEltListStruct i_ = new MultSelectKeyEltListStruct(_ctx,this, _index);
            img_.addKeyListener(i_);
            indexableKey.add(i_);
            reindex(indexableKey);
            MultSelectEltListStruct j_ = new MultSelectEltListStruct(_ctx, this, _index);
            img_.addMouseListener(j_);
            indexableMouse.add(j_);
            reindex(indexableMouse);
        }
    }
    public void set(ContextEl _ctx,int _index, Struct _img, Struct _elt) {
        if (!(_img instanceof PreparedLabelStruct)) {
            return;
        }
        if (!list.isValidIndex(_index)) {
            return;
        }
        PreparedLabelStruct img_ = (PreparedLabelStruct) _img;
        Struct c_ = panel.remove(_index);
        if (!(c_ instanceof CustComponentStruct)) {
            return;
        }
        panel.add(img_,_index);
        list.set(_index, _elt);
        listComponents.set(_index, img_);
        if (simple) {
            SimpleSelectEltListStruct i_ = new SimpleSelectEltListStruct(_ctx, this, _index);
            img_.addMouseListener(i_);
            indexableMouse.set(_index,i_);
        } else {
            MultSelectKeyEltListStruct i_ = new MultSelectKeyEltListStruct(_ctx,this, _index);
            img_.addKeyListener(i_);
            indexableKey.set(_index,i_);
            MultSelectEltListStruct j_ = new MultSelectEltListStruct(_ctx, this, _index);
            img_.addMouseListener(j_);
            indexableMouse.set(_index,j_);
        }
    }
    public ArrayStruct getListView(ContextEl _ctx) {
        int len_ = list.size();
        String obj_ = StringExpUtil.getPrettyArrayType(_ctx.getStandards().getContent().getCoreNames().getAliasObject());
        ArrayStruct arr_ = new ArrayStruct(len_, obj_);
        for (int i = 0; i < len_; i++) {
            arr_.set(i, list.get(i));
        }
        return arr_;
    }

    public Ints getSelectedIndexes() {
        return selectedIndexes;
    }

    public void setSelectedIndexes(Struct _selectedIndexes) {
        Ints selectedIndexes_ = new Ints();
        if (_selectedIndexes instanceof ArrayStruct) {
            for (Struct s : ((ArrayStruct)_selectedIndexes).list()) {
                selectedIndexes_.add(((NumberStruct) s).intStruct());
            }
            selectedIndexes = selectedIndexes_;
        }
    }

    public void clearSelection() {
        selectedIndexes.clear();
    }

    public ArrayStruct getSelectedIndexes(ContextEl _ctx) {
        int len_ = selectedIndexes.size();
        String obj_ = StringExpUtil.getPrettyArrayType(_ctx.getStandards().getContent().getPrimTypes().getAliasPrimInteger());
        ArrayStruct arr_ = new ArrayStruct(len_, obj_);
        for (int i = 0; i < len_; i++) {
            arr_.set(i, new IntStruct(selectedIndexes.get(i)));
        }
        return arr_;
    }
    private static void reindex(CustList<IndexableListener> _list) {
        int index_ = 0;
        for (IndexableListener c: _list) {
            c.setIndex(index_);
            index_++;
        }
    }

    public Struct getVisibleRowCount() {
        return new IntStruct(visibleRowCount);
    }

    public void setVisibleRowCount(Struct _visibleRowCount) {
        int value_ = ((NumberStruct)_visibleRowCount).intStruct();
        if (value_ < 0) {
            value_ = 0;
        }
        visibleRowCount = value_;
        updateGraphics();
    }

    void updateGraphics() {
        int width_ = 0;
        for (PreparedLabelStruct c: getListComponents()) {
            width_ = Math.max(width_, c.getWidth());
        }
        int h_ = 0;
        int c_ = 0;
        for (PreparedLabelStruct c: getListComponents()) {
            h_ = Math.max(h_,c.getHeight());
            c_++;
        }
        scroll.setPreferredSize(new Dimension(width_ + 24, (h_ + 2)* Math.min(c_, visibleRowCount)));
        scroll.revalidate();
    }
    public void clear() {
        list.clear();
        listComponents.clear();
        panel.removeAll();
        selectedIndexes.clear();
        indexableKey.clear();
        indexableMouse.clear();
    }
    public void remove(int _index) {
        if (!list.isValidIndex(_index)) {
            return;
        }
        list.remove(_index);
        listComponents.remove(_index);
        panel.remove(_index);
        selectedIndexes.removeObj(_index);

        if (!simple) {
            indexableKey.remove(_index);
            indexableMouse.remove(_index);
            reindex(indexableMouse);
            reindex(indexableKey);
        } else {
            indexableMouse.remove(_index);
            reindex(indexableMouse);
        }
        updateGraphics();
    }
    public void setListener(Struct _listener) {
        listener = _listener;
    }

    public Struct getListener() {
        return listener;
    }

    public int getFirstIndex() {
        return firstIndex;
    }

    public void setFirstIndex(int _firstIndex) {
        firstIndex = _firstIndex;
    }

    public int getLastIndex() {
        return lastIndex;
    }

    public void setLastIndex(int _lastIndex) {
        lastIndex = _lastIndex;
    }

    public void addRange() {
        int min_ = Math.min(firstIndex, lastIndex);
        int max_ = Math.max(firstIndex, lastIndex);
        for (int i = min_; i <= max_; i++) {
            selectedIndexes.add(i);
        }
    }
    public void clearAllRange() {
        selectedIndexes.clear();
    }

    public void clearRange() {
        int min_ = Math.min(firstIndex, lastIndex);
        int max_ = Math.max(firstIndex, lastIndex);
        for (int i = min_; i <= max_; i++) {
            selectedIndexes.removeObj(i);
        }
    }

    public CustList<PreparedLabelStruct> getListComponents() {
        return listComponents;
    }

    public Struct getRender() {
        return render;
    }

    public void setRender(Struct _render) {
        this.render = _render;
    }

    public boolean isEnabledList() {
        return enabledList;
    }

    @Override
    protected CustComponent getVisibleComponent() {
        return panel.getComponent();
    }

    @Override
    protected CustComponent getComponent() {
        return scroll.getComponent();
    }

    @Override
    public Struct isEnabled() {
        return BooleanStruct.of(enabledList);
    }

    @Override
    public void setEnabled(Struct _enabled) {
        enabledList = BooleanStruct.isTrue(_enabled);
    }
}
