package code.expressionlanguage.analyze.blocks;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.exec.blocks.ExecRootBlock;
import code.expressionlanguage.files.OffsetAccessInfo;
import code.expressionlanguage.files.OffsetsBlock;
import code.util.*;

public final class ClassBlock extends RootBlock implements UniqueRootedBlock {

    private final boolean finalType;
    private final boolean abstractType;
    private final boolean staticType;

    public ClassBlock(int _idRowCol, int _categoryOffset, String _name, String _packageName, OffsetAccessInfo _access,
                      String _templateDef, IntMap< String> _directSuperTypes,
                      boolean _finalType,
                      boolean _abstractType, boolean _staticType,
                      OffsetsBlock _offset) {
        super(_idRowCol, _name, _packageName, _access, _templateDef, _directSuperTypes, _offset);
        finalType = _finalType;
        abstractType = _abstractType;
        staticType = _staticType;
    }

    @Override
    public boolean isStaticType() {
        return staticType;
    }
    @Override
    public void setupBasicOverrides(ContextEl _context,ExecRootBlock _exec) {
        checkAccess(_context,_exec);
    }

    public boolean isFinalType() {
        return finalType;
    }

    public boolean isAbstractType() {
        return abstractType;
    }

    @Override
    public boolean mustImplement() {
        return !isAbstractType();
    }

}
