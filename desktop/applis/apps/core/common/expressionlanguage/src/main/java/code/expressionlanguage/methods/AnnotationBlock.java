package code.expressionlanguage.methods;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.files.OffsetAccessInfo;
import code.expressionlanguage.files.OffsetsBlock;
import code.util.*;
import code.util.StringList;

public final class AnnotationBlock extends RootBlock {

    private final StringList allSuperTypes = new StringList();

    private StringList importedDirectSuperInterfaces = new StringList();
    public AnnotationBlock(int _idRowCol, int _categoryOffset, String _name,
                           String _packageName, OffsetAccessInfo _access, String _templateDef,
                           IntMap< String> _directSuperTypes, OffsetsBlock _offset) {
        super(_idRowCol, _categoryOffset, _name,
                _packageName, _access, _templateDef, _directSuperTypes, _offset);
    }

    @Override
    public boolean isStaticType() {
        return true;
    }

    @Override
    public boolean mustImplement() {
        return false;
    }

    @Override
    public void setupBasicOverrides(ContextEl _context,ExecRootBlock _exec) {
        useSuperTypesOverrides(_context);
    }

    @Override
    public void buildDirectGenericSuperTypes(ContextEl _classes,ExecRootBlock _exec) {
        importedDirectSuperInterfaces.clear();
        importedDirectSuperInterfaces.add(_classes.getStandards().getAliasAnnotationType());
    }

    @Override
    public void buildErrorDirectGenericSuperTypes(ContextEl _classes) {
    }

    @Override
    public StringList getImportedDirectSuperTypes() {
        return allSuperTypes;
    }
    
}
