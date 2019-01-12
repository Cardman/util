package code.expressionlanguage.opers.util;
import code.expressionlanguage.Analyzable;
import code.util.CustList;
import code.util.StringList;
import code.util.StringMap;

public final class ArgumentsGroup {

    private Analyzable context;

    private boolean ambigous;

    private StringMap<StringList> map;

    private String globalClass;

    private CustList<ClassArgumentMatching> arguments;

    public ArgumentsGroup(Analyzable _context, StringMap<StringList> _map, ClassArgumentMatching[] _elements) {
        arguments = new CustList<ClassArgumentMatching>(_elements);
        context = _context;
        map = _map;
    }

    public Analyzable getContext() {
        return context;
    }

    public StringMap<StringList> getMap() {
        return map;
    }

    public boolean isAmbigous() {
        return ambigous;
    }

    public void setAmbigous(boolean _ambigous) {
        ambigous = _ambigous;
    }

    public void setGlobalClass(String _globalClass) {
        globalClass = _globalClass;
    }

    public ClassArgumentMatching get(int _index) {
        return arguments.get(_index);
    }

}
