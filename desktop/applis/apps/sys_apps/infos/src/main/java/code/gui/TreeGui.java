package code.gui;

import javax.swing.*;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.*;

public final class TreeGui implements AbsTreeGui {
    private final TreeComponent tree;
    private final DefaultTreeModel model;
    private final DefaultTreeSelectionModel selectionModel;
    private AbstractMutableTreeNode selected;

    public TreeGui(AbstractMutableTreeNode _t) {
        selected = _t;
        model = new DefaultTreeModel(convert(_t));
        tree = new TreeComponent(new JTree(model));
        selectionModel = new DefaultTreeSelectionModel();
        tree.setSelectionModel(selectionModel);
        setSelectionModel();
    }

    public static MutableTreeNode convert(AbstractMutableTreeNode _t) {
        return ((DefMutableTreeNode)_t).node();
    }

    public boolean isRootVisible() {
        return tree.isRootVisible();
    }

    public void setRootVisible(boolean _rootVisible) {
        tree.setRootVisible(_rootVisible);
    }

    public TreePath selectEvt() {
        TreePath selectionPath_ = getSelectionPath();
        selected = new DefMutableTreeNode(selected(selectionPath_));
        return selectionPath_;
    }
    public static MutableTreeNode selected(TreePath _path) {
        try {
            return (MutableTreeNode)_path.getLastPathComponent();
        } catch (Exception e) {
            return null;
        }
    }
    public AbstractMutableTreeNode getSelected() {
        return selected;
    }

    public void removeFromParent() {
        selected.removeFromParent();
    }

    public void removeAllChildren() {
        selected.removeAllChildren();
    }
    public TreePath getSelectionPath() {
        return tree.getSelectionPath();
    }

    public void select(AbstractMutableTreeNode _node) {
        tree.setSelectionPath(getTreePath(convert(_node)));
    }
    public void addTreeSelectionListener(TreeSelectionListener _tsl) {
        tree.addTreeSelectionListener(_tsl);
    }

    @Override
    public void reloadRoot() {
        model.reload();
    }

    @Override
    public void reload(AbstractMutableTreeNode _node) {
        model.reload(convert(_node));
    }

    private TreePath getTreePath(TreeNode _node) {
        return new TreePath(model.getPathToRoot(_node));
    }

    public CustComponent getTree() {
        return tree;
    }

    private void setSelectionModel() {
        selectionModel.setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
    }

    @Override
    public void add(String _info) {
        selected.add(new DefMutableTreeNode(_info));
    }

}
