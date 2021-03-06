package code.gui.document;

import code.formathtml.render.IntComboList;
import code.formathtml.render.MetaComboList;
import code.util.Ints;
import code.util.StringList;

public final class DualComboList extends DualInput implements IntComboList {

    private final StringList choicesValues;

    public DualComboList(DualContainer _container, MetaComboList _component,
                         RenderedPage _page) {
        super(_container, _component, _page.getGene().getGeneGraphicList().createMultStrList(_page.getGene().getImageFactory(), new StringList(_component.getChoicesStrings()), _component.getSelected(),_component.getVisible()), _page);
        choicesValues = _component.getChoicesValues();
        updateGraphics(getSelect().getGlobal(),_component);
    }

    @Override
    public StringList getValue() {
        Ints indexes_ = getSelect().getSelectedIndexes();
        StringList values_ = new StringList();
        for (int i: indexes_) {
            values_.add(choicesValues.get(i));
        }
        return values_;
    }

}
