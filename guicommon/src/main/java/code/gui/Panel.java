package code.gui;

import code.util.CustList;

import java.awt.*;

import javax.swing.*;

public final class Panel extends CustComponent {

    private JPanel panel;

    private Panel() {
        this(new JPanel());
    }

    private Panel(LayoutManager _panel) {
        this(new JPanel(_panel));
    }

    private Panel(BorderLayout _panel) {
        this(new JPanel(_panel));
    }

    private Panel(GridLayout _panel) {
        this(new JPanel(_panel));
    }

    private Panel(JPanel _panel) {
        panel = _panel;
    }

    public static Panel newAbsolute() {
        return new Panel((LayoutManager)null);
    }

    public static Panel newBorder() {
        return new Panel(new BorderLayout());
    }

    public static Panel newGrid(int _row,int _col) {
        return new Panel(new GridLayout(_row,_col));
    }

    public static Panel newGrid(int _row,int _col, int _h, int _v) {
        return new Panel(new GridLayout(_row,_col,_h,_v));
    }
    public static Panel newPageBox() {
        Panel panel_ = new Panel();
        panel_.setLayout(new BoxLayout(panel_.getComponent(), BoxLayout.PAGE_AXIS));
        return panel_;
    }

    public static Panel newLineBox() {
        Panel panel_ = new Panel();
        panel_.setLayout(new BoxLayout(panel_.getComponent(), BoxLayout.LINE_AXIS));
        return panel_;
    }

    public int getComponentCount() {
        return panel.getComponentCount();
    }

    public CustComponent getComponent(int _n) {
        return getChildren().get(_n);
    }

    public void add(Clock _comp) {
        add(_comp.getComponent());
    }

    public void add(CustComponent _comp) {
        if (_comp.getParent() != null) {
            return;
        }
        _comp.setParent(this);
        getChildren().add(_comp);
        panel.add(_comp.getComponent());
    }

    public void add(CustComponent _comp, int _index) {
        if (_comp.getParent() != null) {
            return;
        }
        _comp.setParent(this);
        getChildren().add(_index,_comp);
        panel.add(_comp.getComponent(), _index);
    }

    public void add(CustComponent _comp, String _constraints) {
        if (_comp.getParent() != null) {
            return;
        }
        _comp.setParent(this);
        getChildren().add(_comp);
        panel.add(_comp.getComponent(), _constraints);
    }

    public void remove(int _index) {
        getChildren().get(_index).setParent(null);
        getChildren().remove(_index);
        panel.remove(_index);
    }

    public int remove(CustComponent _cust) {
        int i_ = 0;
        int index_ = -1;
        CustList<CustComponent> rem_ = new CustList<CustComponent>();
        for (CustComponent c: getChildren()) {
            if (c.getComponent() == _cust.getComponent()) {
                c.setParent(null);
                _cust.setParent(null);
                index_ = i_;
            } else {
                rem_.add(c);
            }
            i_++;
        }
        getChildren().clear();
        panel.removeAll();
        for (CustComponent c: rem_) {
            getChildren().add(c);
            panel.add(c.getComponent());
        }
        return index_;
    }
    public void removeAll() {
        for (CustComponent c: getChildren()) {
            c.setParent(null);
        }
        getChildren().clear();
        panel.removeAll();
    }

    public void repaintSecondChildren() {
        for (CustComponent c: getChildren()) {
            if (c instanceof PaintableLabel) {
                ((PaintableLabel)c).repaintLabel();
            } else if (c instanceof Panel) {
                for (CustComponent d: c.getChildren()) {
                    if (d instanceof PaintableLabel) {
                        ((PaintableLabel)d).repaintLabel();
                    }
                }
                ((Panel) c).validate();
            }
        }
        validate();
    }
    public void repaintChildren() {
        for (CustComponent c: getChildren()) {
            if (c instanceof PaintableLabel) {
                ((PaintableLabel)c).repaintLabel();
            }
        }
        validate();
    }

    public void invalidate() {
        panel.invalidate();
    }

    public void validate() {
        panel.validate();
    }

    public FontMetrics getFontMetrics(Font _font) {
        return panel.getFontMetrics(_font);
    }

    public void setLocation(int _x, int _y) {
        panel.setLocation(new Point(_x, _y));
    }

    public void setSize(int _width, int _height) {
        panel.setSize(_width, _height);
    }

    public void setForeground(Color _fg) {
        panel.setForeground(_fg);
    }

    public void setBackground(Color _bg) {
        panel.setBackground(_bg);
    }


    public boolean isOpaque() {
        return panel.isOpaque();
    }

    public void setOpaque(boolean _isOpaque) {
        panel.setOpaque(_isOpaque);
    }

    public boolean isValidateRoot() {
        return panel.isValidateRoot();
    }

    @Override
    protected JComponent getComponent() {
        return panel;
    }

    private void setLayout(BoxLayout _borderLayout) {
        panel.setLayout(_borderLayout);
    }

}
