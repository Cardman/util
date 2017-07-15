package aiki.main;
import code.gui.GroupFrame;
import aiki.gui.MainWindow;

/**This class thread is used by EDT (invokeLater of SwingUtilities),
Thread safe class*/
public final class ShowOpeningDialog extends Thread {

    private GroupFrame window;

    /**This class thread is used by EDT (invokeLater of SwingUtilities)*/
    public ShowOpeningDialog(GroupFrame _window) {
        window = _window;
    }

    @Override
    public void run() {
        MainWindow.getDialog().init(window, VideoLoading.getVideo(), true);
    }
}
