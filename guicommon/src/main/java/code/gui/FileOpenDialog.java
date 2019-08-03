package code.gui;
import java.awt.BorderLayout;
import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;

import code.gui.events.CancelSelectFileEvent;
import code.gui.events.SearchingEvent;
import code.gui.events.StopSearchingEvent;
import code.gui.events.SubmitKeyEvent;
import code.gui.events.SubmitMouseEvent;
import code.util.CustList;
import code.util.StringList;
import code.util.StringMap;

public final class FileOpenDialog extends FileDialog implements SingleFileSelection {
    private static final String DIALOG_ACCESS = "gui.fileopendialog";

    private static final FileOpenDialog DIALOG = new FileOpenDialog();
    private static final String SEARCH = "search";
    private static final String TYPE_TEXT = "typeText";
    private static final String CANCEL = "cancel";
    private static final String OPEN = "open";
    private static final String CANCEL_SEARCHING = "cancelSearching";
    private static final String STOP_SEARCHING = "stopSearching";
    private static final String FILE_COUNT = "fileCount";
    private static final String RESULT_COUNT = "resultCount";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String ERROR_TITLE = "errorTitle";
    private static final String ERROR_TYPING = "errorTyping";
    private static final int NB_COLS = 24;
    private TextField typedString = new TextField(NB_COLS);
    private Panel searchingPanel = Panel.newPageBox();

    private StringMap<String> messages;

    private ThreadSearchingFile thread;

    private AtomicBoolean keepSearching = new AtomicBoolean();

    private AtomicBoolean showNewResults = new AtomicBoolean();

    private TextLabel searchedFiles = new TextLabel("");

    private TextLabel foundFiles = new TextLabel("");
    private CommonFrame frame;

    private FileOpenDialog(){
        setAccessFile(DIALOG_ACCESS);
    }
    public static void setFileOpenDialog(GroupFrame _w,String _language,boolean _currentFolderRoot, String _extension, String _folder, String... _excludedFolders) {
        DIALOG.setFileDialogByFrame(_w, _language, _currentFolderRoot, _extension, _folder, _excludedFolders);
//        DIALOG.initFileOpenDialog(_w, _language, _currentFolderRoot, _extension, _folder, _excludedFolders);
        DIALOG.initFileOpenDialog(_w);
    }

//    private void initFileOpenDialog(GroupFrame _w,String _language,boolean _currentFolderRoot, String _extension, String _folder, String... _excludedFolders) {
//    }
    private void initFileOpenDialog(CommonFrame _c) {
        frame = _c;
        messages = getMessages(_c,GuiConstants.FOLDER_MESSAGES_GUI);
        getFileName().addActionListener(new SubmitKeyEvent(this));
        LabelButton action_ = new LabelButton(messages.getVal(OPEN));
        action_.addMouseListener(new SubmitMouseEvent(this));
        getButtons().add(action_);
        action_ = new LabelButton(messages.getVal(CANCEL));
        action_.addMouseListener(new CancelSelectFileEvent(this));
        getButtons().add(action_);
        TextLabel label_;
        label_ = new TextLabel(messages.getVal(TYPE_TEXT));
        LabelButton search_ = new LabelButton(messages.getVal(SEARCH));
        search_.addMouseListener(new SearchingEvent(this));
        searchingPanel.removeAll();
        Panel panel_ = new Panel();
        panel_.add(label_);
        panel_.add(typedString);
        panel_.add(search_);
        searchingPanel.add(panel_);
        LabelButton stop_ = new LabelButton(messages.getVal(STOP_SEARCHING));
        stop_.addMouseListener(new StopSearchingEvent(this, true));
        searchingPanel.add(stop_);
        LabelButton cancelSearching_ = new LabelButton(messages.getVal(CANCEL_SEARCHING));
        cancelSearching_.addMouseListener(new StopSearchingEvent(this, false));
        searchingPanel.add(cancelSearching_);
        searchedFiles = new TextLabel(StringList.simpleNumberFormat(messages.getVal(FILE_COUNT), 0));
        searchingPanel.add(searchedFiles);
        foundFiles = new TextLabel(StringList.simpleNumberFormat(messages.getVal(RESULT_COUNT), 0));
        searchingPanel.add(foundFiles);
        getPane().add(searchingPanel, BorderLayout.NORTH);
        pack();
    }

    public void searchFile() {
        File currentFolder_ = new File(getCurrentFolder());
        if (!currentFolder_.exists()) {
            DefaultMutableTreeNode selected_;
            selected_ = (DefaultMutableTreeNode) getFolderSystem().getLastSelectedPathComponent();
            if (selected_ == null) {
                return;
            }
            selected_.removeFromParent();
            return;
        }
        if (thread != null && thread.isAlive()) {
            return;
        }
        CustList<File> backup_ = new CustList<File>(getFiles());
        init(getCurrentFolder(), getExtension());
        getFileModel().clear();
        setKeepSearching(true);
        thread = new ThreadSearchingFile(DIALOG, backup_, currentFolder_);
        thread.start();
//        setKeepSearching(true);
//        Cursor cursor_ = getCursor();
//        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//        CustList<File> results_;
//        results_ = new CustList<File>();
//        CustList<File> current_;
//        current_ = new CustList<File>(currentFolder_);
//        CustList<File> next_;
//        next_ = new CustList<File>();
//        while (true) {
//            for (File d: current_) {
//                File[] files_ = d.listFiles();
//                if (files_ == null) {
//                    continue;
//                }
//                for (File f: files_) {
//                    if (f.isDirectory()) {
//                        if (getExcludedFolders().containsObj(f.getAbsolutePath().replace(StringList.BACK_SLASH, StreamTextFile.SEPARATEUR))) {
//                            continue;
//                        }
//                        next_.add(f);
//                    } else {
//                        if (StringList.match(f.getName(), typedString.getText())) {
//                            if (f.getName().endsWith(getExtension())) {
//                                results_.add(f);
//                            }
//                        }
//                    }
//                }
//            }
//            if (next_.isEmpty()) {
//                break;
//            }
//            current_ = new CustList<File>(next_);
//            next_ = new CustList<File>();
//        }
//        getFileModel().setupFiles(results_,getCurrentFolder(), getExtension());
//        setCursor(cursor_);
//        setKeepSearching(false);
    }

    @Override
    public void clickRow() {
        int index_ = getFileTable().getSelectedRow();
        if (index_ == CustList.INDEX_NOT_FOUND_ELT) {
            return;
        }
        super.clickRow();
        submitIfVisible();
    }

    @Override
    public void submitIfVisible() {
        if (!isVisible()) {
            return;
        }
        submit();
    }

    public void submit() {
        String fileName_ = getFileName().getText();
        String extFileName_ = StringList.concat(fileName_,getExtension());
        String selectedRelPath_ = StringList.concat(getCurrentFolder(), extFileName_);
        if (new File(selectedRelPath_).exists()) {
            closeWindow();
            setSelectedPath(selectedRelPath_);
            return;
        }
        String selectedPath_ = getSelectedAbsolutePath();
        String lg_ = frame.getLanguageKey();
        if (selectedPath_ != null) {
            selectedPath_ = StringList.replaceBackSlash(selectedPath_);
            if (!new File(selectedPath_).exists()) {
                ConfirmDialog.showMessage(this, StringList.simpleStringsFormat(messages.getVal(ERROR_MESSAGE), selectedPath_), messages.getVal(ERROR_TITLE), lg_, JOptionPane.ERROR_MESSAGE);
                selectedPath_ = null;
                setSelectedPath(selectedPath_);
                setSelectedAbsolutePath(selectedPath_);
                return;
            }
            closeWindow();
            setSelectedPath(selectedPath_);
            return;
        }
        if (getFileTable().getSelectedRowCount() == 1) {
            selectedPath_ = getFileModel().getSelectedFilePath(getFileTable().getSelectedRow());
            if (!new File(selectedPath_).exists()) {
                ConfirmDialog.showMessage(this, StringList.simpleStringsFormat(messages.getVal(ERROR_MESSAGE), selectedPath_), messages.getVal(ERROR_TITLE), lg_, JOptionPane.ERROR_MESSAGE);
                selectedPath_ = null;
                setSelectedPath(selectedPath_);
                setSelectedAbsolutePath(selectedPath_);
                return;
            }
            setSelectedPath(selectedPath_);
            closeWindow();
            return;
        }
//        if (getFileTable().getSelectedRowCount() == 1) {
////            String selectedPath_ = getFileModel().getSelectedFilePath(getFileTable().getSelectedRow());
////            String selectedPath_ = getSelectedPath();
////            if (selectedPath_ != null) {
////
////            }
//            selectedPath_ = selectedPath_.replace(StringList.BACK_SLASH, StreamTextFile.SEPARATEUR);
//            if (!new File(selectedPath_).exists()) {
//                ConfirmDialog.showMessage(this, StringList.simpleFormat(messages.getVal(ERROR_MESSAGE), selectedPath_), messages.getVal(ERROR_TITLE), Constants.getLanguage(), JOptionPane.ERROR_MESSAGE);
//                selectedPath_ = null;
//                setSelectedPath(selectedPath_);
//                return;
//            }
//            setSelectedPath(selectedPath_);
//            closeWindow();
//            return;
//        }
        if (fileName_.isEmpty()) {
            ConfirmDialog.showMessage(this, messages.getVal(ERROR_TYPING), messages.getVal(ERROR_TITLE),lg_, JOptionPane.ERROR_MESSAGE);
            return;
//            if (getFileTable().getSelectedRowCount() != 1) {
//                return;
//            }
//            String selectedPath_ = getFileModel().getSelectedFilePath(getFileTable().getSelectedRow());
//            selectedPath_ = selectedPath_.replace(StringList.BACK_SLASH, StreamTextFile.SEPARATEUR);
//            if (!new File(selectedPath_).exists()) {
//                ConfirmDialog.showMessage(this, StringList.simpleFormat(messages.getVal(ERROR_MESSAGE), selectedPath_), messages.getVal(ERROR_TITLE), Constants.getLanguage(), JOptionPane.ERROR_MESSAGE);
//                selectedPath_ = null;
//                setSelectedPath(selectedPath_);
//                return;
//            }
//            setSelectedPath(selectedPath_);
//            closeWindow();
//            return;
        }
        if (!new File(extFileName_).isAbsolute()) {
            selectedPath_ = StringList.concat(getCurrentFolder(), extFileName_);
        } else {
            selectedPath_ = extFileName_;
        }
        if (!new File(selectedPath_).exists()) {
            ConfirmDialog.showMessage(this, StringList.simpleStringsFormat(messages.getVal(ERROR_MESSAGE), selectedPath_), messages.getVal(ERROR_TITLE), lg_, JOptionPane.ERROR_MESSAGE);
            selectedPath_ = null;
            setSelectedPath(selectedPath_);
            return;
        }
        setSelectedPath(selectedPath_);
        closeWindow();
    }

    public void setInformations(int _s, int _f) {
        searchedFiles.setText(StringList.simpleNumberFormat(messages.getVal(FILE_COUNT), _s));
        foundFiles.setText(StringList.simpleNumberFormat(messages.getVal(RESULT_COUNT), _f));
    }

    @Override
    public void closeWindow() {
//        if (thread != null) {
//            while (thread.isAlive()) {
//                continue;
//            }
//        }
        setKeepSearching(false);
        super.closeWindow();
    }

    public static String getStaticSelectedPath() {
        return DIALOG.getSelectedPath();
    }

    public String getTypedString() {
        return typedString.getText();
    }

    protected void applyChanges() {
        getFileModel().applyChanges();
    }

    protected void init(String _folder, String _extension) {
        getFileModel().init(_folder, _extension);
    }

    protected CustList<File> getFiles() {
        return getFileModel().getFiles();
    }

    public boolean isKeepSearching() {
        return keepSearching.get();
    }

    public void setKeepSearching(boolean _keepSearching) {
        keepSearching.set(_keepSearching);
    }

    public boolean isShowNewResults() {
        return showNewResults.get();
    }

    public void setShowNewResults(boolean _showNewResults) {
        showNewResults.set(_showNewResults);
    }
}
