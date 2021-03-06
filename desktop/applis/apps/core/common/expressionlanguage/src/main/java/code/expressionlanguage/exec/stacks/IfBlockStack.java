package code.expressionlanguage.exec.stacks;
import code.expressionlanguage.exec.blocks.ExecBracedBlock;


public final class IfBlockStack extends AbstractStask implements ConditionBlockStack,EnteredStack {

    private ExecBracedBlock execBlock;
    private ExecBracedBlock execLastBlock;
    private ExecBracedBlock execCurentVisitedBlock;

    private boolean entered;

    public boolean isEntered() {
        return entered;
    }

    public void setEntered(boolean _entered) {
        entered = _entered;
    }

    public ExecBracedBlock getBlock() {
        return execBlock;
    }

    public void setCurrentVisitedBlock(ExecBracedBlock _execCurentVisitedBlock) {
        execCurentVisitedBlock = _execCurentVisitedBlock;
    }

    public void setExecBlock(ExecBracedBlock _execBlock) {
        execBlock = _execBlock;
    }

    public ExecBracedBlock getLastBlock() {
        return execLastBlock;
    }

    public void setExecLastBlock(ExecBracedBlock _execLastBlock) {
        this.execLastBlock = _execLastBlock;
    }

    public ExecBracedBlock getCurrentVisitedBlock() {
        return execCurentVisitedBlock;
    }
}
