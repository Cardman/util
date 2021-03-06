package code.gui.document;

import java.awt.*;

import code.formathtml.render.MetaLabel;
import code.formathtml.render.MetaStyle;
import code.formathtml.render.SegmentPart;
import code.gui.CustComponent;
import code.gui.PreparedLabel;
import code.gui.images.AbstractImage;
import code.util.CustList;

public abstract class DualLabel extends DualLeaf {

    private final CustList<SegmentPart> segments = new CustList<SegmentPart>();

    private final String text;
    private final PreparedLabel label;

    protected DualLabel(DualContainer _container, MetaLabel _component, RenderedPage _page) {
        super(_container, _component, _page);
        text = _component.getText();
        label = PreparedLabel.prep(_page.getGene().getImageFactory());
        updateGraphics(label,_component);
    }

    public final void clearSegments() {
        segments.clear();
    }

    public final void addSegment(SegmentPart _segment) {
        segments.add(_segment);
    }

    public CustList<SegmentPart> getSegments() {
        return segments;
    }

    @Override
    protected void postAdd() {
        Color bGr_ = label.getParent().getBackground();
        Color fGr_ = label.getParent().getForeground();
        label.setBackground(bGr_);
        label.setForeground(fGr_);
        paint();
    }

    @Override
    public CustComponent getGraphic() {
        return getLabel();
    }

    public PreparedLabel getLabel() {
        return label;
    }

    public void paint() {
        MetaStyle style_ = getComponent().getStyle();
        Font copy_ = newFont(style_);
        int h_ = label.heightFont(copy_);
        int w_ = label.stringWidth(copy_,text);
        if (w_ == 0) {
            w_ = label.stringWidth(copy_," ");
        }
        AbstractImage img_ = getPage().getGene().getImageFactory().newImageRgb(w_, h_);
        img_.setFont(copy_);
        img_.setColor(new Color(style_.getBgColor()));
        img_.fillRect(0, 0, w_, h_);
        img_.setColor(Color.ORANGE);
        for (SegmentPart s: segments) {
            int beginIndex_ = s.getBegin();
            int b_ = label.stringWidth(copy_,text.substring(0, beginIndex_));
            int d_ = label.stringWidth(copy_,text.substring(beginIndex_, s.getEnd()));
            img_.fillRect(b_, 0, d_, h_);
        }
        img_.setColor(new Color(style_.getFgColor()));
        img_.drawString(text, 0, h_ - 1);
        label.setIcon(getPage().getGene().getImageFactory(), img_);
        img_.dispose();
    }

    public String getText() {
        return text;
    }
    protected static Font newFont(MetaStyle _style) {
        String fontFamily_ = _style.getFontFamily();
        int realSize_ = _style.getRealSize();
        return font(fontFamily_, _style.getItalic()+_style.getBold(), realSize_);
    }

    private static Font font(String _fontFamily, int _font, int _realSize) {
        return new Font(_fontFamily, _font, _realSize);
    }
}
