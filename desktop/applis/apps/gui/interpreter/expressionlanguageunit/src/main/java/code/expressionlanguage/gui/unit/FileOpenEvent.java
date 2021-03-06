package code.expressionlanguage.gui.unit;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public final class FileOpenEvent implements ActionListener {
    private MainWindow mainWindow;
    private TestableFrame tested;

    public FileOpenEvent(MainWindow _mainWindow, TestableFrame _tested) {
        mainWindow = _mainWindow;
        tested = _tested;
    }

    @Override
    public void actionPerformed(ActionEvent _e) {
        mainWindow.selectFile(tested);
    }
}
