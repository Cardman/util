package aiki.gui.listeners;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import aiki.gui.components.walk.ScenePanel;

public class PokemonSelectionTeam implements ListSelectionListener {

    private ScenePanel window;

    public PokemonSelectionTeam(ScenePanel _window) {
        window = _window;
    }

    @Override
    public void valueChanged(ListSelectionEvent _e) {
        if (_e.getValueIsAdjusting()) {
            return;
        }
        window.selectPkTeam();
    }

}
