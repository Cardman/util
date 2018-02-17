package code.formathtml.render;

import code.sml.Element;
import code.util.opers.BaseSixtyFourUtil;

public final class MetaSimpleImage extends MetaImage {

    private int[][] image;

    public MetaSimpleImage(MetaContainer _parent, String _image) {
        this(_parent, _image, EMPTY_STRING,null);
    }

    public MetaSimpleImage(MetaContainer _parent, String _image, String _title, Element _anchor) {
        super(_parent,_title, _anchor);
        image = BaseSixtyFourUtil.getImageByString(_image);
    }

    public int[][] getImage() {
        return image;
    }
}
