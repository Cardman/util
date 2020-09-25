package code.expressionlanguage.fwd.opers;

import code.expressionlanguage.analyze.blocks.AnonymousTypeBlock;

public final class AnaInstancingAnonContent {

    private final AnonymousTypeBlock block;
    private int rootNumber = -1;
    private int memberNumber = -1;

    public AnaInstancingAnonContent(AnonymousTypeBlock _block) {
        block = _block;
    }
    public AnonymousTypeBlock getBlock() {
        return block;
    }

    public int getRootNumber() {
        return rootNumber;
    }

    public void setRootNumber(int rootNumber) {
        this.rootNumber = rootNumber;
    }

    public int getMemberNumber() {
        return memberNumber;
    }

    public void setMemberNumber(int memberNumber) {
        this.memberNumber = memberNumber;
    }
}
