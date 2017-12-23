package code.expressionlanguage.methods.util;

import code.util.CustList;
import code.util.ints.Displayable;
import code.util.ints.Listable;

public class ErrorList extends CustList<FoundErrorInterpret> implements Displayable {

    private static final String SEP_INFO = "\n\n";

    public ErrorList() {
    }

    public ErrorList(FoundErrorInterpret... _elements) {
        super(_elements);
    }

    public ErrorList(Listable<FoundErrorInterpret> _c) {
        super(_c);
    }

    @Override
    public String display() {
        if (isEmpty()) {
            return EMPTY_STRING;
        }
        StringBuilder return_ = new StringBuilder(first().display());
        int size_ = size();
        for (int i=SECOND_INDEX;i<size_;i++) {
            return_.append(SEP_INFO);
            return_.append(get(i).display());
        }
        return return_.toString();
    }

}
