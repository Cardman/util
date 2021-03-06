package code.gui.initialize;

import code.gui.AbsGraphicList;
import code.gui.images.AbstractImageFactory;

public interface AbstractGraphicListGenerator<T> {
    AbsGraphicList<T> create(AbstractImageFactory _fact, boolean _simple);
}
