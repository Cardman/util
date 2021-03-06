package code.expressionlanguage.guicompos;

import code.expressionlanguage.ContextEl;
import code.expressionlanguage.structs.*;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class MouseEventStruct extends WithoutParentIdStruct implements Struct {

    private int first;
    private int second;
    private boolean ctrl;
    private boolean alt;
    private boolean shift;
    private boolean left;
    private boolean middle;
    private boolean right;
    private int clicks;
    private String className;
    public MouseEventStruct(MouseEvent _action, String _className) {
        first = _action.getX();
        second = _action.getY();
        ctrl = _action.isControlDown();
        alt = _action.isAltDown();
        shift = _action.isShiftDown();
        left = SwingUtilities.isLeftMouseButton(_action);
        middle = SwingUtilities.isMiddleMouseButton(_action);
        right = SwingUtilities.isRightMouseButton(_action);
        clicks = _action.getClickCount();
        className = _className;
    }
    public MouseEventStruct(String _className) {
        className = _className;
    }

    public void setFirst(Struct _first) {
        first = ((NumberStruct)_first).intStruct();
    }

    public void setSecond(Struct _second) {
        second = ((NumberStruct)_second).intStruct();
    }

    public void setAlt(Struct _alt) {
        alt = BooleanStruct.isTrue(_alt);
    }

    public void setCtrl(Struct _ctrl) {
        ctrl = BooleanStruct.isTrue(_ctrl);
    }

    public void setShift(Struct _shift) {
        shift = BooleanStruct.isTrue(_shift);
    }

    public void setLeft(Struct _left) {
        left = BooleanStruct.isTrue(_left);
    }

    public void setMiddle(Struct _middle) {
        middle = BooleanStruct.isTrue(_middle);
    }

    public void setRight(Struct _right) {
        right = BooleanStruct.isTrue(_right);
    }

    public void setClicks(Struct _clicks) {
        clicks = ((NumberStruct)_clicks).intStruct();
    }

    @Override
    public String getClassName(ContextEl _contextEl) {
        return className;
    }

    public BooleanStruct isAlt() {
        return BooleanStruct.of(alt);
    }

    public BooleanStruct isCtrl() {
        return BooleanStruct.of(ctrl);
    }

    public BooleanStruct isLeft() {
        return BooleanStruct.of(left);
    }

    public BooleanStruct isMiddle() {
        return BooleanStruct.of(middle);
    }

    public BooleanStruct isRight() {
        return BooleanStruct.of(right);
    }

    public BooleanStruct isShift() {
        return BooleanStruct.of(shift);
    }

    public IntStruct getClicks() {
        return new IntStruct(clicks);
    }

    public IntStruct getFirst() {
        return new IntStruct(first);
    }

    public IntStruct getSecond() {
        return new IntStruct(second);
    }

}
