package code.gui;

import code.adv.ValueChanging;

import javax.swing.event.ListSelectionEvent;

public final class ValueChangingImpl implements ValueChanging {
    private final ListSelection listener;
    private final ListSelectionEvent event;

    public ValueChangingImpl(ListSelection _listener, ListSelectionEvent _event) {
        this.listener = _listener;
        this.event = _event;
    }

    @Override
    public boolean skip() {
        return event.getValueIsAdjusting();
    }

    @Override
    public void act() {
        listener.valueChanged(new SelectionInfo(event.getFirstIndex(),event.getLastIndex(),false));
    }
}
