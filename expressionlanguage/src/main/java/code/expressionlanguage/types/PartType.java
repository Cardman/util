package code.expressionlanguage.types;

import code.util.NatTreeMap;


public abstract class PartType {

    protected static final String EMPTY_STRING = "";
    private ParentPartType parent;
    private PartType nextSibling;
    private int index;
    private int indexInType;
    public PartType(ParentPartType _parent, int _index, int _indexInType) {
        parent = _parent;
        index = _index;
        indexInType = _indexInType;
    }
    static PartType createPartType(ParentPartType _parent, int _index, int _indexInType, AnalyzingType _analyze, NatTreeMap<Integer, String> _dels, boolean _removedFirst) {
        if (_analyze.isError()) {
            return new EmptyPartType(_parent, _index, _indexInType, _dels.getValue(_index));
        }
        if (_analyze.getOperators().isEmpty()) {
            if (_analyze.getKind() == KindPartType.TYPE_NAME) {
                return new NamePartType(_parent, _index, _indexInType, _dels.getValue(_index));
            }
            return new VariablePartType(_parent, _index, _indexInType, _dels.getValue(_index));
        }
        if (_analyze.getPrio() == ParserType.TMP_PRIO) {
            return new TemplatePartType(_parent, _index, _indexInType);
        }
        if (_analyze.getPrio() == ParserType.INT_PRIO) {
            return new InnerPartType(_parent, _index, _indexInType, _removedFirst);
        }
        return new ArraryPartType(_parent, _index, _indexInType);
    }
    public int getIndex() {
        return index;
    }
    public int getIndexInType() {
        return indexInType;
    }
    public final ParentPartType getParent() {
        return parent;
    }
    public final PartType getNextSibling() {
        return nextSibling;
    }
    public abstract PartType getFirstChild();
    public void setNextSibling(PartType _child) {
        nextSibling = _child;
    }
}
