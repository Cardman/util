package cards.gui.menus;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import cards.gui.MainWindow;

public class LoadGameEvent implements ActionListener {

    private MainWindow window;

    public LoadGameEvent(MainWindow _window) {
        window = _window;
    }

    @Override
    public void actionPerformed(ActionEvent _e) {
        window.loadGame();
    }
}
