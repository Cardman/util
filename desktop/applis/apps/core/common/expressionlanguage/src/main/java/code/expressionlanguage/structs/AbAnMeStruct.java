package code.expressionlanguage.structs;
import code.expressionlanguage.ContextEl;
import code.expressionlanguage.common.AccessEnum;

public abstract class AbAnMeStruct extends AbsAnnotatedStruct {
    private final AbsRetType retType;

    protected AbAnMeStruct(AbsRetType _retType, AccessEnum _access, String _fileName) {
        super(_access,_fileName);
        retType = _retType;
    }

    protected AbsRetType type() {
        return retType;
    }

    public String getType() {
        return retType.retType();
    }

    public ClassMetaInfo typeGene(ContextEl _contextEl) {
        return ClassMetaInfo.getExtendedClassMetaInfo(_contextEl,type().retTypeGene(),this);
    }

    public ClassMetaInfo getFormattedReturnType(ContextEl _contextEl) {
        return ClassMetaInfo.getExtendedClassMetaInfo(_contextEl,tryFormatType(_contextEl,this),this);
    }

    protected String tryFormatType(ContextEl _cont, AnnotatedStruct _member) {
        return tryFormatType(_cont,_member.getFormatted(),retType.retType());
    }

}
