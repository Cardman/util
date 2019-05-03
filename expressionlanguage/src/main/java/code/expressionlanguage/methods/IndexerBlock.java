package code.expressionlanguage.methods;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.files.OffsetAccessInfo;
import code.expressionlanguage.files.OffsetStringInfo;
import code.expressionlanguage.files.OffsetsBlock;
import code.util.Numbers;
import code.util.StringList;

public final class IndexerBlock extends OverridableBlock {

    private final boolean indexerGet;

    public IndexerBlock(ContextEl _importingPage, boolean _indexerGet,BracedBlock _m, OffsetAccessInfo _access, OffsetStringInfo _retType, OffsetStringInfo _fctName, StringList _paramTypes, Numbers<Integer> _paramTypesOffset,
                        StringList _paramNames, Numbers<Integer> _paramNamesOffset, OffsetStringInfo _modifier, OffsetsBlock _offset) {
        super(_importingPage, _access, _retType, _fctName, _paramTypes, _paramTypesOffset, _paramNames, _paramNamesOffset,_modifier, _offset);
        indexerGet = _indexerGet;
    }

    public boolean isIndexerGet() {
        return indexerGet;
    }

}
