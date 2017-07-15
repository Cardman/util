package aiki.gui.components.labels;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import code.images.ConverterBufferedImage;
import code.util.Numbers;
import aiki.facade.FacadeGame;
import aiki.util.SortingItem;

public class ItemLabel extends SelectableLabel {

    private static int _sideLength_;

    private static final int FIRST_LINE = HEIGTH_CHARS;

    private static final int SECOND_LINE = FIRST_LINE + HEIGTH_CHARS;

    private static final int THIRD_LINE = SECOND_LINE + HEIGTH_CHARS;

    private static final int FOURTH_LINE = THIRD_LINE + HEIGTH_CHARS;

    private SortingItem item;

    private BufferedImage miniImageItem;

    public ItemLabel(SortingItem _item) {
        item = _item;
    }

    public void setImagesResults(FacadeGame _facade) {
        String miniItem_ = _facade.getData().getMiniItems().getVal(item.getKeyName());
        miniImageItem = ConverterBufferedImage.decodeToImage(miniItem_);
        _sideLength_ = _facade.getMap().getSideLength();
        int h_ = _sideLength_;
        if (h_ < FOURTH_LINE) {
            h_ = FOURTH_LINE;
        }
        Numbers<Integer> widths_ = new Numbers<Integer>();
        widths_.add(getFontMetrics(getFont()).stringWidth(item.getName()));
        widths_.add(getFontMetrics(getFont()).stringWidth(item.getItemClass()));
        widths_.add(getFontMetrics(getFont()).stringWidth(Integer.toString(item.getPrice())));
        widths_.add(getFontMetrics(getFont()).stringWidth(item.getNumber().toString()));
        setPreferredSize(new Dimension(widths_.getMaximum(),h_));
    }

    @Override
    public void paintComponent(Graphics _g) {
        _g.setColor(Color.WHITE);
        _g.fillRect(0,0,getWidth(),getHeight());
        _g.drawImage(miniImageItem, 0, 0, null);
        _g.setColor(Color.BLACK);
        _g.drawString(item.getName(), _sideLength_, FIRST_LINE);
        _g.drawString(item.getItemClass(), _sideLength_, SECOND_LINE);
        _g.drawString(Integer.toString(item.getPrice()), _sideLength_, THIRD_LINE);
        _g.drawString(item.getNumber().toString(), _sideLength_, FOURTH_LINE);
        super.paintComponent(_g);
    }
}
