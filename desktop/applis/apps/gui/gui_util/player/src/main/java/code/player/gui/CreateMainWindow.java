package code.player.gui;

import code.gui.initialize.AbstractProgramInfos;
import code.util.StringMap;

public class CreateMainWindow implements Runnable {
    private final AbstractProgramInfos list;
    private String lg;
    private StringMap<Object> args;

    public CreateMainWindow(String _lg, StringMap<Object> _args, AbstractProgramInfos _list) {
        lg = _lg;
        args = _args;
        list = _list;
    }

    @Override
    public void run() {
        MainWindow window_ = new MainWindow(lg, list);
        if (!args.isEmpty()) {
            window_.loadList(args.firstKey());
        }
    }
}
