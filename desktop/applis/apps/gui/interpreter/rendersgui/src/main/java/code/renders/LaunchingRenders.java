package code.renders;

import code.gui.*;
import code.gui.initialize.AbstractProgramInfos;
import code.gui.initialize.LoadLanguageUtil;
import code.stream.StreamTextFile;
import code.util.StringMap;

public class LaunchingRenders extends AdvSoftApplicationCore {

    private static final String TEMP_FOLDER = "renders_sites";

    public LaunchingRenders(AbstractProgramInfos _frames) {
        super(_frames);
    }

    protected static void loadLaungage(String[] _args, LaunchingRenders _soft) {
        LoadLanguageUtil.loadLaungage(_soft, TEMP_FOLDER, _args);
    }
    @Override
    public Object getObject(String _fileName) {
        return StreamTextFile.contentsOfFile(_fileName, getFrames().getFileCoreStream(), getFrames().getStreams());
    }

    @Override
    protected void launch(String _language, StringMap<Object> _args) {
        ThreadInvoker.invokeNow(getFrames().getThreadFactory(),new CreateMainWindow(_language,_args, getFrames()));
    }

    @Override
    protected String getApplicationName() {
        return getMainWindowClass();
    }
    public static String getMainWindowClass() {
        return "renders_sites";
    }

}
