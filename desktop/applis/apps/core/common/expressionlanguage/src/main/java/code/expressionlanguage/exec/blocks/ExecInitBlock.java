package code.expressionlanguage.exec.blocks;

import code.expressionlanguage.files.OffsetsBlock;
import code.expressionlanguage.analyze.blocks.AloneBlock;

public abstract class ExecInitBlock extends ExecMemberCallingsBlock implements AloneBlock {
    ExecInitBlock(OffsetsBlock _offset) {
        super(_offset);
    }

}
