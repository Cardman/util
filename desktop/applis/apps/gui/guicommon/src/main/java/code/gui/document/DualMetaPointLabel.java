package code.gui.document;

import java.awt.*;

import code.formathtml.render.MetaPointForm;
import code.formathtml.render.MetaPointLabel;
import code.formathtml.render.MetaStyle;
import code.gui.PreparedLabel;
import code.gui.images.AbstractImage;

public final class DualMetaPointLabel extends DualLabel {

    private MetaPointForm form;
    public DualMetaPointLabel(DualContainer _container, MetaPointLabel _component,
            RenderedPage _page) {
        super(_container, _component, _page);
        getLabel().setOpaque(true);
        form = _component.getForm();
    }

    @Override
    public void paint() {
        PreparedLabel lab_ = getLabel();
        MetaStyle style_ = getComponent().getStyle();
        Font copy_ =  newFont(style_);
        int h_ = lab_.heightFont(copy_);
        int w_ = h_;
        AbstractImage img_ = getPage().getGene().getImageFactory().newImageRgb(w_, h_);
        img_.setFont(copy_);
        img_.setColor(new Color(style_.getBgColor()));
        img_.fillRect(0, 0, w_, h_);
        img_.setColor(new Color(style_.getFgColor()));
        if (form == MetaPointForm.DISK) {
            img_.fillOval(0, 0, w_, h_);
        } else if (form == MetaPointForm.CIRCLE) {
            img_.drawOval(0, 0, w_, h_);
        } else if (form == MetaPointForm.SQUARRE) {
            img_.fillRect(2, 2, w_ - 4, h_ - 4);
        } else {
            img_.drawRect(2, 2, w_ - 4, h_ - 4);
        }
        lab_.setIcon(getPage().getGene().getImageFactory(), img_);
        img_.dispose();
    }
}
