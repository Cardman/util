package cards.gui.dialogs.events;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cards.gui.dialogs.SetterSelectedCardList;

public class MoveCardsEvent extends MouseAdapter {

    private SetterSelectedCardList dialog;

    public MoveCardsEvent(SetterSelectedCardList _dialog) {
        dialog = _dialog;
    }

    @Override
    public void mouseReleased(MouseEvent _e) {
        dialog.deplacerCartes();
    }
}
