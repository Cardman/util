package code.formathtml.render;

import code.sml.Element;


public abstract class MetaImage extends MetaLeaf {

    private final Element anchor;

    public MetaImage(MetaContainer _parent, String _title, Element _anchor) {
        super(_parent, _title);
        anchor = _anchor;
    }
    public Element getAnchor() {
        return anchor;
    }
}
