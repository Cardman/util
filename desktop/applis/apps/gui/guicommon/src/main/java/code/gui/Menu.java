package code.gui;
import javax.swing.JMenu;
import javax.swing.JMenuItem;

public final class Menu implements EnabledMenu {

    private Menu parentMenu;

    private JMenu menu;

    public Menu() {
        menu = new JMenu();
    }

    public Menu(String _s, boolean _b) {
        menu = new JMenu(_s, _b);
    }

    public Menu(String _s) {
        menu = new JMenu(_s);
    }

    @Override
    public Menu getParentMenu() {
        return parentMenu;
    }

    @Override
    public void setParentMenu(Menu _parentMenu) {
        parentMenu = _parentMenu;
    }

    public void setEnabledMenu(boolean _b) {
        setEnabled(_b);
        MenuItemUtils.setEnabled(_b, this);
    }

    public void addMenuItem(CheckBoxMenuItem _menuItem) {
        _menuItem.setParentMenu(this);
        menu.add(_menuItem.getMenu());
    }
    public void addMenuItem(MenuItem _menuItem) {
        _menuItem.setParentMenu(this);
        menu.add(_menuItem.getMenu());
    }
    public void addMenuItem(Menu _menuItem) {
        _menuItem.setParentMenu(this);
        menu.add(_menuItem.menu);
    }

    public void removeMenuItem(CheckBoxMenuItem _menuItem) {
        _menuItem.setParentMenu(null);
        menu.remove(_menuItem.getMenu());
    }
    public void removeMenuItem(MenuItem _menuItem) {
        _menuItem.setParentMenu(null);
        menu.remove(_menuItem.getMenu());
    }
    public void removeMenuItem(Menu _menuItem) {
        _menuItem.setParentMenu(null);
        menu.remove(_menuItem.menu);
    }
    JMenu getMenu() {
        return menu;
    }
    @Override
    public void setEnabled(boolean _enabled) {
        menu.setEnabled(_enabled);
    }

    @Override
    public String getText() {
        return menu.getText();
    }

    public void setText(String _val) {
        menu.setText(_val);
    }

    public boolean isEnabled() {
        return menu.isEnabled();
    }

    public void addSeparator() {
        menu.addSeparator();
    }

    JMenuItem getItem(int _i) {
        return menu.getItem(_i);
    }

    public int getItemCount() {
        return menu.getItemCount();
    }
}
