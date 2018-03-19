package code.gui.document;

import java.awt.Cursor;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

import code.formathtml.render.MetaSimpleImage;
import code.sml.Element;


public class DualSimpleImage extends DualImage {
    private String href;

    public DualSimpleImage(DualContainer _container, MetaSimpleImage _component, RenderedPage _page) {
        super(_container, _component, _page);
        Element anchor_ = _component.getAnchor();
        href = "";
        if (anchor_ != null) {
            JLabel label_ = getGraphic();
            label_.setCursor(new Cursor(Cursor.HAND_CURSOR));
            label_.addMouseListener(new AnchorEvent(anchor_, _page, this));
            if (!anchor_.getAttribute("command").isEmpty()) {
                href = anchor_.getAttribute("command");
            } else if (!anchor_.getAttribute("href").isEmpty()) {
                href = anchor_.getAttribute("href");
            } else {
                href = "";
            }
        }
    }

    @Override
    public MetaSimpleImage getComponent() {
        return (MetaSimpleImage) super.getComponent();
    }

    @Override
    public void paint() {
        int[][] img_ = getComponent().getImage();
        if (img_.length == 0) {
            return;
        }
        BufferedImage imgBuf_ = new BufferedImage(img_[0].length, img_.length, BufferedImage.TYPE_INT_RGB);
        int y_ = 0;
        for (int[] r: img_) {
            int x_ = 0;
            for (int p: r) {
                imgBuf_.setRGB(x_, y_, p);
                x_++;
            }
            y_++;
        }
        getGraphic().setIcon(new ImageIcon(imgBuf_));
    }
    public String getHref() {
        return href;
    }
}
