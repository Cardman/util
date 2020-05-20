package aiki.gui.dialogs;
import java.awt.Dimension;

import javax.swing.WindowConstants;

import aiki.sml.Resources;
import aiki.gui.MainWindow;
import code.formathtml.Navigation;
import code.bean.nat.BeanNatLgNames;
import code.gui.*;
import code.gui.document.RenderedPage;
import code.util.StringMap;

public final class DialogHtmlData extends Dialog {
    private static final String DIALOG_ACCESS = "aiki.gui.dialogs.dialoghtmldata";

    private static final String TEXT = "0";

    private static final String SEARCH_LABEL = "searchLabel";

    private static final DialogHtmlData DIALOG = new DialogHtmlData();

//    private Timer timer;

    private RenderedPage session;

    private StringMap<String> messages;

    private DialogHtmlData() {
        setAccessFile(DIALOG_ACCESS);
    }

//    public static void setDialogHtmlData(JDialog _parent, String _title, SessionEditorPane _session) {

//    }
//    public static void setDialogHtmlData(Dialog _parent, String _title, SessionEditorPane _session, boolean _successCompile) {
//        //super(_parent, true);
//        DIALOG.setDialogIcon(_parent);
//        DIALOG.setTitle(_title);
//        DIALOG.init(_parent, _session);
//        DIALOG.initSession(_successCompile);
//    }
    public static void setDialogHtmlData(MainWindow _window, Dialog _parent, String _title, RenderedPage _session, Object _dataBase, Navigation _navigation, BeanNatLgNames _bean, String _lg) {
        //super(_parent, true);
        DIALOG.setDialogIcon(_parent);
        DIALOG.setTitle(_title);
        DIALOG.init(_window, _parent, _session);
        DIALOG.initSession(_dataBase,_navigation,_bean,_lg);
    }

//    public static void setDialogHtmlData(GroupFrame _parent, String _title, SessionEditorPane _session, boolean _successCompile) {
//        //super(_parent, true);
//        DIALOG.setDialogIcon(_parent);
//        DIALOG.setTitle(_title);
//        DIALOG.init(_parent, _session);
//        DIALOG.initSession(_successCompile);
//    }
    public static void setDialogHtmlData(MainWindow _parent, String _title, RenderedPage _session,Object _dataBase,Navigation _navigation, BeanNatLgNames _bean, String _lg) {
        //super(_parent, true);
        DIALOG.setDialogIcon(_parent);
        DIALOG.setTitle(_title);
        DIALOG.init(_parent, _parent, _session);
        DIALOG.initSession(_dataBase,_navigation,_bean,_lg);
    }

    private void init(MainWindow _window,Dialog _parent, RenderedPage _session) {
        messages = getMessages(_window,Resources.MESSAGES_FOLDER);
        setLocationRelativeTo(_parent);
        initSession(_session);
    }

    private void init(MainWindow _window,MainWindow _parent, RenderedPage _session) {
        messages = getMessages(_window,Resources.MESSAGES_FOLDER);
        setLocationRelativeTo(_parent);
        initSession(_session);
    }

    private void initSession(RenderedPage _session) {
        session = _session;
        _session.setFrame(this);
        Panel panel_ = Panel.newPageBox();
        TextLabel area_ = new TextLabel(TEXT);
        TextField field_;
//        LabelButton search_ = new LabelButton(MainWindow.OK);
        LabelButton search_ = new LabelButton(messages.getVal(SEARCH_LABEL));
        field_ = new TextField(20);
//        _session.setLabel(area_);
        _session.setSearchText(search_);
        _session.setField(field_);
        _session.addFinder();
//        JPanel group_ = new JPanel();
//        group_.setLayout(new BoxLayout(group_, BoxLayout.PAGE_AXIS));
        ScrollPane scrollSession_ = _session.getScroll();
        scrollSession_.setPreferredSize(new Dimension(400, 400));
//        group_.add(scrollSession_);
        panel_.add(scrollSession_);
        panel_.add(area_);
        panel_.add(field_);
        panel_.add(search_);
        setContentPane(panel_);
//        timer = new Timer(200, new Chronometer(area_, _session, 0));
//        timer.start();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        pack();
    }

    public void initSession(Object _dataBase, Navigation _navigation, BeanNatLgNames _bean, String _lg) {
        session.setFrame(this);
        session.initializeOnlyConf(_dataBase,_bean,_navigation,_lg);

        setVisible(true);
    }
}
