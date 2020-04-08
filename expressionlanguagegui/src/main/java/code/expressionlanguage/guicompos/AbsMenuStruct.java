package code.expressionlanguage.guicompos;

import code.expressionlanguage.structs.BooleanStruct;
import code.expressionlanguage.structs.NullStruct;
import code.expressionlanguage.structs.StringStruct;
import code.expressionlanguage.structs.Struct;
import code.gui.EnabledMenu;

public abstract class AbsMenuStruct implements Struct {
    private Struct parentMenu = NullStruct.NULL_VALUE;
    @Override
    public Struct getParent() {
        return NullStruct.NULL_VALUE;
    }
    public Struct getParentMenu() {
        return parentMenu;
    }

    public void setParentMenu(Struct _parentMenu) {
        parentMenu = _parentMenu;
    }

    public Struct isEnabled() {
        return BooleanStruct.of(getMenu().isEnabled());
    }

    public void setEnabled(Struct _str) {
        getMenu().setEnabled(((BooleanStruct)_str).getInstance());
    }

    public void setDeepEnabled(Struct _str) {
        getMenu().setEnabledMenu(((BooleanStruct)_str).getInstance());
    }
    public Struct getText() {
        String txt_ = getMenu().getText();
        if (txt_ == null) {
            return NullStruct.NULL_VALUE;
        }
        return new StringStruct(txt_);
    }
    public void setText(Struct _str) {
        getMenu().setText(getValue(_str));
    }

    static String getValue(Struct _str) {
        if (_str instanceof StringStruct) {
            return ((StringStruct)_str).getInstance();
        }
        return "";
    }
    abstract EnabledMenu getMenu();

    @Override
    public boolean sameReference(Struct _other) {
        return this == _other;
    }
}
