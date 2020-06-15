package code.expressionlanguage.types;

abstract class ParentPartType extends PartType {

    private PartType firstChild;
    ParentPartType(ParentPartType _parent, int _index) {
        super(_parent, _index);
    }
    void appendChild(PartType _child) {
        if (firstChild == null) {
            firstChild = _child;
            return;
        }
        PartType p_ = firstChild;
        while (p_.getNextSibling() != null) {
            p_ = p_.getNextSibling();
        }
        p_.setNextSibling(_child);
    }

    @Override
    final PartType getFirstChild() {
        return firstChild;
    }

}
