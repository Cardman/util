package code.expressionlanguage.analyze.blocks;
import code.expressionlanguage.analyze.files.OffsetsBlock;
import code.expressionlanguage.functionid.MethodAccessKind;

public final class StaticBlock extends InitBlock {

    private int staticNb;
    public StaticBlock(OffsetsBlock _offset) {
        super(_offset);
    }

    @Override
    public MethodAccessKind getStaticContext() {
        return MethodAccessKind.STATIC;
    }
    public int getStaticNb() {
        return staticNb;
    }
    public void setStaticNb(int _staticNb){
        staticNb = _staticNb;
    }
}
