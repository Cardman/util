package code.gui;
import java.awt.BorderLayout;
import java.io.File;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

import code.gui.events.CancelSelectFileEvent;
import code.gui.events.CreateFolderEvent;
import code.gui.events.SubmitKeyEvent;
import code.gui.events.SubmitMouseEvent;
import code.stream.StreamTextFile;
import code.util.CharList;
import code.util.StringList;
import code.util.StringMap;

public final class FileSaveDialog extends FileDialog implements SingleFileSelection {
    private static final String DIALOG_ACCESS = "gui.filesavedialog";

    private static final FileSaveDialog DIALOG = new FileSaveDialog();

    private static final String EMPTY_STRING = "";

    private static final String CANCEL = "cancel";

    private static final String TITLE_CONF = "titleConf";

    private static final String BODY_CONF = "bodyConf";

    private static final String FORBIDDEN_SPECIAL_CHARS = "forbiddenSpecialChars";

    private static final String FORBIDDEN_SPACES = "forbiddenSpaces";

    private static final String FORBIDDEN = "forbidden";

    private static final String SAVE = "save";

    private static final String FOLDER_NAME = "folderName";

    private static final String CREATE = "+";

    private static final char QUOTE = 34;

    private static final char[] FORBIDDEN_CHARS_FILE_NAME = CharList.wrapCharArray('<', '>', '?', QUOTE, '*', '/', '\\', '|', ':');

    private static final int NB_COLS = 24;

    private TextField typedString = new TextField(NB_COLS);

    private Panel searchingPanel = Panel.newLineBox();

    private StringMap<String> messages;
    private CommonFrame frame;

    private FileSaveDialog() {
        setAccessFile(DIALOG_ACCESS);
    }

    public static void setFileSaveDialogByFrame(GroupFrame _w,String _language,boolean _currentFolderRoot, String _extension, String _folder, String... _excludedFolders) {
        DIALOG.setFileDialogByFrame(_w,_language,_currentFolderRoot,_extension, _folder, _excludedFolders);
        DIALOG.initSaveDialog(_w);
    }

    public static void setFileSaveDialog(CommonFrame _c,Dialog _w,String _language,boolean _currentFolderRoot, String _extension, String _folder, String... _excludedFolders) {
        DIALOG.setFileDialog(_c,_w,_language,_currentFolderRoot,_extension, _folder, _excludedFolders);
        DIALOG.initSaveDialog(_c);
    }

    private void initSaveDialog(CommonFrame _c) {
        frame =_c;
        messages = getMessages(_c, GuiConstants.FOLDER_MESSAGES_GUI);
        getFileName().addActionListener(new SubmitKeyEvent(this));
        LabelButton action_ = new LabelButton(messages.getVal(SAVE));
        action_.addMouseListener(new SubmitMouseEvent(this));
        getButtons().add(action_);
        action_ = new LabelButton(messages.getVal(CANCEL));
        action_.addMouseListener(new CancelSelectFileEvent(this));
        getButtons().add(action_);
        if (StringList.quickEq(getFolder(),ConstFiles.getHomePath())) {
            searchingPanel.removeAll();
            TextLabel label_;
            label_ = new TextLabel(messages.getVal(FOLDER_NAME));
            LabelButton search_ = new LabelButton(CREATE);
            search_.addMouseListener(new CreateFolderEvent(this));
            searchingPanel.add(label_);
            searchingPanel.add(typedString);
            searchingPanel.add(search_);
            getPane().add(searchingPanel, BorderLayout.NORTH);
        }
        pack();
    }

    public void createFolder() {
        if (typedString.getText().trim().isEmpty()) {
            return;
        }
        TreePath treePath_ = getFolderSystem().getSelectionPath();
        if (treePath_ == null) {
            new File(StringList.concat(getFolder(),StreamTextFile.SEPARATEUR,typedString.getText().trim())).mkdirs();
            applyTreeChange();
        } else {
            StringBuilder str_ = buildPath(treePath_);
            str_.append(typedString.getText());
            new File(str_.toString()).mkdirs();
            applyTreeChange(treePath_);
        }
    }

    @Override
    public void submitIfVisible() {
        if (!isVisible()) {
            return;
        }
        submit();
    }

    public void submit() {
        String errorTitle_ = messages.getVal(FORBIDDEN);
        String lg_ = frame.getLanguageKey();
        if (getFileName().getText().trim().isEmpty()) {
            String errorContent_ = messages.getVal(FORBIDDEN_SPACES);
            ConfirmDialog.showMessage(this, errorContent_, errorTitle_,lg_, JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(this, errorContent_, errorTitle_, JOptionPane.ERROR_MESSAGE);
            return;
        }
        boolean hasForbbidenChars_ = false;
        for (char c: getFileName().getText().toCharArray()) {
            for (char e: FORBIDDEN_CHARS_FILE_NAME) {
                if (c == e) {
                    hasForbbidenChars_ = true;
                    break;
                }
            }
            if (hasForbbidenChars_) {
                break;
            }
        }
//        if (!StringList.matchingRegExp(getFileName().getText(), EXCLUDED_CHARS).isEmpty()) {
//            String errorContent_ = messages.getVal(FORBIDDEN_SPECIAL_CHARS);
//            ConfirmDialog.showMessage(this, errorContent_, errorTitle_, Constants.getLanguage(), JOptionPane.ERROR_MESSAGE);
//            JOptionPane.showMessageDialog(this, errorContent_, errorTitle_, JOptionPane.ERROR_MESSAGE);
//            return;
//        }
        if (hasForbbidenChars_) {
            String errorContent_ = messages.getVal(FORBIDDEN_SPECIAL_CHARS);
            ConfirmDialog.showMessage(this, errorContent_, errorTitle_, lg_, JOptionPane.ERROR_MESSAGE);
            return;
        }
        //get selected row first table
        File file_ = new File(StringList.concat(getCurrentFolder(),getFileName().getText(),getExtension()));
        if (file_.exists()) {
            String mes_ = StringList.simpleStringsFormat(messages.getVal(BODY_CONF), StringList.concat(getCurrentFolder(),getFileName().getText()));
//            ConfirmDialog conf_ = new ConfirmDialog(
//                    this,
//                    mes_, messages.getVal(TITLE_CONF),
//                    getLang(),
//                    JOptionPane.YES_NO_OPTION);
            ConfirmDialog conf_ = ConfirmDialog.showMiniDialog(
                this,
                mes_, messages.getVal(TITLE_CONF),
                getLang(),
                JOptionPane.YES_NO_OPTION);
            int answer_ = conf_.getAnswer();
            if (answer_ == JOptionPane.NO_OPTION) {
                return;
            }
        }
        setSelectedPath(StringList.concat(getCurrentFolder(),getFileName().getText(),getExtension()));
        closeWindow();
    }

    public static String getStaticSelectedPath() {
        return DIALOG.getSelectedPath();
    }
}
