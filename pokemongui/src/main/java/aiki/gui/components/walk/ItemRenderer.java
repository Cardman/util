package aiki.gui.components.walk;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import code.gui.StringCellRenderer;
import code.images.ConverterBufferedImage;
import aiki.facade.FacadeGame;

public class ItemRenderer extends StringCellRenderer {

    private static int _sideLength_;

    private FacadeGame facade;

    private boolean selected;

    private String name;

    private String displayName;

    private int price;

    private int maxWordWidth;

    private BufferedImage miniItem;

    public ItemRenderer(FacadeGame _facade) {
        facade = _facade;
        _sideLength_ = facade.getMap().getSideLength();
    }

    @Override
    public Component getListCellRendererComponent(
            String _item, int _arg2,
            boolean _selected, boolean _arg4) {
        selected = _selected;
        name = _item;
        displayName = facade.translateItem(name);
        price = facade.getData().getItem(name).getPrice();
        String img_ = facade.getData().getMiniItems().getVal(name);
        miniItem = ConverterBufferedImage.decodeToImage(img_);
        maxWordWidth = 0;
        for (String i: facade.getChosenItemsForBuyOrSell().getKeys()) {
            String disp_ = facade.translateItem(i);
            int w_ = getFontMetrics(getFont()).stringWidth(disp_);
            if (w_ > maxWordWidth) {
                maxWordWidth = w_;
            }
        }
        setPreferredSize(new Dimension(maxWordWidth+_sideLength_ *2,_sideLength_));
        return this;
    }

    @Override
    protected void paintComponent(Graphics _g) {
        _g.drawImage(miniItem, 0, 0, null);
        _g.setColor(Color.BLACK);
        _g.drawString(displayName, _sideLength_, getHeight());
        _g.drawString(facade.getChosenItemsForBuyOrSell().getVal(name).toString(), maxWordWidth+_sideLength_, getHeight());
        _g.drawString(Integer.toString(price), maxWordWidth+_sideLength_ * 2, getHeight());
        if (selected) {
            _g.setColor(Color.RED);
            _g.drawRect(0,0,getWidth()-1,getHeight()-1);
        }
    }
}
