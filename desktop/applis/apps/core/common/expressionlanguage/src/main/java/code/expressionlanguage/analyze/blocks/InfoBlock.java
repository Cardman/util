package code.expressionlanguage.analyze.blocks;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.AccessEnum;
import code.expressionlanguage.common.GeneField;
import code.expressionlanguage.instr.PartOffset;
import code.util.CustList;
import code.util.StringList;

public interface InfoBlock extends GeneField, AnnotableBlock {
    int getFieldNameOffset();
    int getFieldNumber();
    void setFieldNumber(int _nb);
    FileBlock getFile();
    String getImportedClassName();
    String getRealImportedClassName();
    void buildImportedType(ContextEl _cont);
    void retrieveNames(ContextEl _cont, StringList _fieldNames);
    CustList<PartOffset> getTypePartOffsets();

    boolean isFinalField();

    AccessEnum getAccess();
    CustList<AnonymousTypeBlock> getAnonymous();
    CustList<AnonymousFunctionBlock> getAnonymousFct();
}
