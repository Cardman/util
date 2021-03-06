package code.gui.events;

import code.gui.OtherConfirmDialog;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class OtherAnswerTextEvent extends MouseAdapter {

    private OtherConfirmDialog dialog;

    private int answer;

    public OtherAnswerTextEvent(OtherConfirmDialog _dialog, int _answer) {
        dialog = _dialog;
        answer = _answer;
    }

    @Override
    public void mouseReleased(MouseEvent _e) {
        dialog.closeWindowText(answer);
    }
}
