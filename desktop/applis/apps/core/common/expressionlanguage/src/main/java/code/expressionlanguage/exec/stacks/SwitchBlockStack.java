package code.expressionlanguage.exec.stacks;
import code.expressionlanguage.exec.blocks.ExecBracedBlock;

public final class SwitchBlockStack extends AbstractStask implements ConditionBlockStack {

    private ExecBracedBlock execBlock;

    private ExecBracedBlock execLastVisitedBlock;

    private ExecBracedBlock execCurrentVisitedBlock;

    public ExecBracedBlock getBlock() {
        return execBlock;
    }

    public void setExecBlock(ExecBracedBlock _execBlock) {
        execBlock = _execBlock;
    }

    @Override
    public void setCurrentVisitedBlock(ExecBracedBlock _execCurrentVisitedBlock) {
        this.execCurrentVisitedBlock = _execCurrentVisitedBlock;
    }

    @Override
    public ExecBracedBlock getCurrentVisitedBlock() {
        return execCurrentVisitedBlock;
    }

    public ExecBracedBlock getExecLastVisitedBlock() {
        return execLastVisitedBlock;
    }

    public void setExecLastVisitedBlock(ExecBracedBlock _execLastVisitedBlock) {
        execLastVisitedBlock = _execLastVisitedBlock;
    }

}
