package code.gui.initialize;

import code.gui.AbsGraphicList;
import code.gui.AbsInputGraphicList;
import code.gui.images.AbstractImageFactory;
import code.util.Ints;
import code.util.StringList;

public interface AbstractGraphicStringListGenerator {
    AbsGraphicList<String> createStrList(AbstractImageFactory _fact, StringList _objects);
    AbsInputGraphicList<String> createMultStrList(AbstractImageFactory _fact, StringList _objects, Ints _selectedIndexes, int _visibleRows);
}
