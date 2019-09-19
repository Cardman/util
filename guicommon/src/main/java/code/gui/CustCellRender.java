package code.gui;

import java.awt.image.BufferedImage;

public abstract class CustCellRender {
    public abstract PreparedLabel getListCellRendererComponent(GraphicListable _list, Object _value,
            int _index, boolean _isSelected, boolean _cellHasFocus);
    public abstract int getHeight();
    public abstract int getWidth();
    public void paintComponent(CustGraphics _g) {
    }
    public void paintComponent(PreparedLabel _component) {
        BufferedImage buff_ = new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_RGB);
        CustGraphics gr_ = new CustGraphics(buff_.getGraphics());
        gr_.setFont(_component.getFont());
        paintComponent(gr_);
        _component.setIcon(buff_);
    }
}
