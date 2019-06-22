package code.expressionlanguage.methods;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.GeneInterface;
import code.expressionlanguage.files.OffsetAccessInfo;
import code.expressionlanguage.files.OffsetsBlock;
import code.expressionlanguage.inherits.Templates;
import code.util.*;
import code.util.StringList;

public final class InterfaceBlock extends RootBlock implements GeneInterface {

    private final StringList allSuperClasses = new StringList();

    private final StringList allSuperTypes = new StringList();
    private StringList importedDirectSuperInterfaces = new StringList();
    private final boolean staticType;

    public InterfaceBlock(int _idRowCol, int _categoryOffset, String _name, String _packageName, OffsetAccessInfo _access,
                          String _templateDef, IntTreeMap< String> _directSuperTypes, boolean _staticType, OffsetsBlock _offset) {
        super(_idRowCol, _categoryOffset, _name, _packageName, _access, _templateDef, _directSuperTypes, _offset);
        staticType = _staticType;
    }

    @Override
    public boolean isStaticType() {
        return staticType;
    }
    @Override
    public void setupBasicOverrides(ContextEl _context) {
        useSuperTypesOverrides(_context);
    }

    @Override
    public StringList getDirectGenericSuperTypes(Analyzable _classes) {
        StringList interfaces_ = new StringList();
        interfaces_.addAllElts(importedDirectSuperInterfaces);
        return interfaces_;
    }

    @Override
    public StringList getAllSuperClasses() {
        return allSuperClasses;
    }
    
    @Override
    public StringList getAllSuperTypes() {
        return allSuperTypes;
    }

    @Override
    public RootBlock belong() {
        return this;
    }

    @Override
    public boolean isFinalType() {
        return false;
    }

    @Override
    public boolean isAbstractType() {
        return true;
    }

    @Override
    public boolean mustImplement() {
        return false;
    }

    @Override
    public void buildDirectGenericSuperTypes(ContextEl _classes) {
        IntTreeMap< String> rcs_;
        rcs_ = getRowColDirectSuperTypes();
        int i_ = 0;
        importedDirectSuperInterfaces.clear();
        for (String s: getDirectSuperTypes()) {
            int index_ = rcs_.getKey(i_);
            String s_ = _classes.resolveTypeInherits(s, this,index_, i_);
            i_++;
            String base_ = Templates.getIdFromAllTypes(s_);
            RootBlock r_ = _classes.getClasses().getClassBody(base_);
            if (!(r_ instanceof InterfaceBlock)) {
                continue;
            }
            importedDirectSuperInterfaces.add(s_);
        }
    }

    @Override
    public void buildErrorDirectGenericSuperTypes(ContextEl _classes) {
        importedDirectSuperInterfaces.clear();
    }
    public StringList getImportedDirectSuperInterfaces() {
        return importedDirectSuperInterfaces;
    }

    @Override
    public StringList getImportedDirectSuperTypes() {
        return new StringList(importedDirectSuperInterfaces);
    }
}
