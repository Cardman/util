package code.expressionlanguage.methods;

import code.expressionlanguage.Analyzable;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.OffsetAccessInfo;
import code.expressionlanguage.OffsetsBlock;
import code.expressionlanguage.Templates;
import code.expressionlanguage.common.GeneInterface;
import code.sml.RowCol;
import code.util.NatTreeMap;
import code.util.StringList;

public final class InterfaceBlock extends RootBlock implements GeneInterface {

    private final StringList allSuperClasses = new StringList();

    private final StringList allSuperTypes = new StringList();
    private StringList importedDirectSuperInterfaces = new StringList();
    private final boolean staticType;

    public InterfaceBlock(ContextEl _importingPage, int _indexChild,
            BracedBlock _m, int _idRowCol, int _categoryOffset ,String _name, String _packageName, OffsetAccessInfo _access,
            String _templateDef, NatTreeMap<Integer, String> _directSuperTypes, boolean _staticType, OffsetsBlock _offset) {
        super(_importingPage, _indexChild, _m, _idRowCol, _categoryOffset, _name, _packageName, _access, _templateDef, _directSuperTypes, _offset);
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
    public StringList getDirectGenericSuperTypesBuild(Analyzable _classes) {
        return new StringList(getDirectSuperTypes());
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
    boolean canBeIncrementedNextGroup() {
        return false;
    }

    @Override
    boolean canBeIncrementedCurGroup() {
        return false;
    }

    @Override
    boolean canBeLastOfBlockGroup() {
        return false;
    }

    @Override
    public StringList getDirectSuperClasses(Analyzable _classes) {
        StringList classes_ = new StringList();
        for (String s: getDirectSuperTypes()) {
            String base_ = Templates.getIdFromAllTypes(s);
            base_=_classes.resolveBaseTypeBuildInherits(base_,this);
            if (isAccessibleType(base_, _classes)) {
                classes_.add(base_);
            }
        }
        if (classes_.isEmpty()) {
            classes_.add(_classes.getStandards().getAliasObject());
        }
        return classes_;
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
    public StringList getAllInterfaces() {
        return getAllSuperClasses();
    }

    @Override
    public StringList getAllGenericInterfaces(Analyzable _classes) {
        Classes classes_ = _classes.getClasses();
        StringList allSuperTypes_ = getAllGenericSuperTypes(_classes);
        StringList allGenericSuperClasses_ = new StringList();
        for (String s: allSuperTypes_) {
            String base_ = Templates.getIdFromAllTypes(s);
            if (classes_.getClassBody(base_) instanceof InterfaceBlock) {
                allGenericSuperClasses_.add(s);
            }
        }
        return allGenericSuperClasses_;
    }

    @Override
    public void buildDirectGenericSuperTypes(Analyzable _classes) {
        NatTreeMap<Integer, String> rcs_;
        rcs_ = getRowColDirectSuperTypes();
        int i_ = 0;
        importedDirectSuperInterfaces.clear();
        for (String s: getDirectSuperTypes()) {
            int index_ = rcs_.getKey(i_);
            i_++;
            RowCol rc_ = getRowCol(0,index_);
            String s_ = _classes.resolveTypeMapping(s, this,rc_);
            String base_ = Templates.getIdFromAllTypes(s_);
            RootBlock r_ = _classes.getClasses().getClassBody(base_);
            if (!(r_ instanceof InterfaceBlock)) {
                continue;
            }
            importedDirectSuperInterfaces.add(s_);
        }
    }

    public StringList getImportedDirectSuperInterfaces() {
        return importedDirectSuperInterfaces;
    }

    @Override
    public StringList getImportedDirectSuperTypes() {
        return new StringList(importedDirectSuperInterfaces);
    }
}
