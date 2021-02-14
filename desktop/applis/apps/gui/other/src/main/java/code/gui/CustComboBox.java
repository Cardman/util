package code.gui;
import javax.swing.JComboBox;
import javax.swing.JComponent;

import code.adv.SafeRemoveAdvUtil;
import code.util.StringList;
import code.util.Ints;
import code.util.core.StringUtil;

import java.util.Optional;

public final class CustComboBox extends CustComponent implements GraphicComboGrInt {

    private final JComboBox<String> combo = new JComboBox<>();

    private ListSelection listener;

    public CustComboBox(StringList _list) {
        this(_list,0);
    }

    public CustComboBox(StringList _list, int _selectedIndex) {
        _list.list().forEach(this::addItem);
        selectItem(_selectedIndex);
    }

    public String getSelectedItem() {
        return (String) Optional.ofNullable(combo.getSelectedItem())
                .filter(o -> o instanceof String).orElse(null);
    }

    @Override
    public void addItem(String _object) {
        combo.addItem(StringUtil.nullToEmpty(_object));
    }

    @Override
    public int getItemCount() {
        return combo.getItemCount();
    }

    @Override
    public void simpleRemoveAllItems() {
        combo.removeAllItems();
    }

    @Override
    public void removeAllItems() {
        combo.removeAllItems();
    }

    @Override
    public ListSelection getListener() {
        return listener;
    }

    @Override
    public void setListener(ListSelection _listener) {
		combo.addItemListener(new LocalItemListener(combo,_listener));
        listener = _listener;
    }

    @Override
    public void popup() {
        combo.showPopup();
    }
    @Override
    public void simpleRemoveItem(int _index) {
        innerRemoveAtIndex(_index);
    }
    @Override
    public void removeItem(int _index) {
        innerRemoveAtIndex(_index);
    }

    private void innerRemoveAtIndex(int _index) {
        SafeRemoveAdvUtil.safeRemove(new SafeRemoveImpl(combo),_index);
    }

    @Override
    public int getSelectedIndex() {
        return combo.getSelectedIndex();
    }

    @Override
    public void selectItem(int _index) {
		int old_ = combo.getSelectedIndex();
        simpleSelectItem(_index);
        CustComponent.invokeLater(new SelectionComboEvent(_index, _index, this,listener,old_));
    }
    public void simpleSelectItem(int _index) {
        int index_ = Math.min(combo.getItemCount(),_index);
        index_ = Math.max(index_,-1);
		combo.setSelectedIndex(index_);
    }

    public CustComponent getCurrentSelected() {
        return this;
    }

    @Override
    public CustComponent getGlobal() {
        return this;
    }

    @Override
    public Ints getSelectedIndexes() {
        return Ints.singleOrEmpty(combo.getSelectedIndex());
    }

    public boolean isEnabled() {
        return combo.isEnabled();
    }

    public void setEnabled(boolean _enabled) {
        combo.setEnabled(_enabled);
    }

    @Override
    protected JComponent getComponent() {
        return combo;
    }

    @Override
    public CustComponent self() {
        return this;
    }

}
