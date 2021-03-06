package code.expressionlanguage.guicompos;

import code.expressionlanguage.structs.BooleanStruct;
import code.expressionlanguage.structs.NullStruct;
import code.expressionlanguage.structs.Struct;
import code.gui.AbsShortListTree;
import code.gui.AbsTreeGui;
import code.gui.CustComponent;
import code.gui.MutableTreeNodeUtil;

public final class TreeStruct extends CustComponentStruct {
    private final AbsTreeGui tree;
    protected TreeStruct(String _className, AbsTreeGui _tree) {
        super(_className);
        tree = _tree;
    }

    public BooleanStruct isRootVisible() {
        return BooleanStruct.of(tree.isRootVisible());
    }

    public void setRootVisible(Struct _rootVisible) {
        tree.setRootVisible(BooleanStruct.isTrue(_rootVisible));
    }

    public Struct getLastSelectedPathComponent() {
        return TreeNodeStruct.nodeOrNull(tree.selectEvt());
    }
    public void select(Struct _node) {
        if (_node instanceof TreeNodeStruct) {
            tree.select(((TreeNodeStruct) _node).getTreeNode());
        }
    }

    public void addTreeSelectionListener(Struct _tsl) {
        if (_tsl instanceof AbsShortListTree) {
            tree.addTreeSelectionListener((AbsShortListTree) _tsl);
        }
    }

    public void reload() {
        MutableTreeNodeUtil.reload(tree);
    }

    @Override
    protected CustComponent getComponent() {
        return tree.getTree();
    }
}
