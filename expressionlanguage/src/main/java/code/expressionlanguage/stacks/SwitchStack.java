package code.expressionlanguage.stacks;
import code.expressionlanguage.opers.util.NullStruct;
import code.expressionlanguage.opers.util.Struct;
import code.util.CustList;

public abstract class SwitchStack extends BlockStack implements BreakableStack {

    private static final String RETURN_LINE = "\n";

    private static final String HAS_NEXT = "value";

    private static final String SEP_KEY_VAL = ":";

    private boolean finished;

    private boolean entered;

    private Struct value = NullStruct.NULL_VALUE;

    private int visitedBlock = CustList.INDEX_NOT_FOUND_ELT;

    public String getInfos() {
        try {
            return HAS_NEXT+SEP_KEY_VAL+value+RETURN_LINE;
        } catch (Error _0) {
            return HAS_NEXT+RETURN_LINE;
        } catch (RuntimeException _0) {
            return HAS_NEXT+RETURN_LINE;
        }
    }

    public boolean isFinished() {
        return finished;
    }

    @Override
    public void setFinished(boolean _finished) {
        finished = _finished;
    }

    public boolean isEntered() {
        return entered;
    }

    public void setEntered(boolean _entered) {
        entered = _entered;
    }
    public Struct getStruct() {
        return value;
    }
    public void setStruct(Struct _value) {
        value = _value;
        if (value == null) {
            value = NullStruct.NULL_VALUE;
        }
    }

    public int getVisitedBlock() {
        return visitedBlock;
    }

    public void increment() {
        visitedBlock++;
    }

    public void setVisitedBlock(int _visitedBlock) {
        visitedBlock = _visitedBlock;
    }
}
