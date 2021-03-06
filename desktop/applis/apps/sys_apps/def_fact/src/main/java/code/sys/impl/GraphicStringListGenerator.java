package code.sys.impl;

import code.gui.AbsGraphicList;
import code.gui.AbsInputGraphicList;
import code.gui.GraphicStringList;
import code.gui.GraphicStringListMult;
import code.gui.images.AbstractImageFactory;
import code.gui.initialize.AbstractGraphicStringListGenerator;
import code.util.Ints;
import code.util.StringList;

public final class GraphicStringListGenerator implements AbstractGraphicStringListGenerator {
    @Override
    public AbsGraphicList<String> createStrList(AbstractImageFactory _fact, StringList _objects) {
        return new GraphicStringList(_fact,_objects);
    }

    @Override
    public AbsInputGraphicList<String> createMultStrList(AbstractImageFactory _fact, StringList _objects, Ints _selectedIndexes, int _visibleRows) {
        return new GraphicStringListMult(_fact,_objects, _selectedIndexes, _visibleRows);
    }
}
