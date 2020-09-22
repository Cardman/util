package code.expressionlanguage.exec.blocks;

import code.expressionlanguage.analyze.files.OffsetsBlock;
import code.expressionlanguage.functionid.MethodAccessKind;

public final class ExecStaticBlock extends ExecInitBlock {
    public ExecStaticBlock(OffsetsBlock _offset) {
        super(_offset);
    }
    public MethodAccessKind getStaticContext() {
        return MethodAccessKind.STATIC;
    }
}
