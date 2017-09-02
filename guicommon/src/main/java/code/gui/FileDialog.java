package code.gui;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import code.gui.events.ClickHeaderEvent;
import code.gui.events.ClickRowEvent;
import code.gui.events.DeployTreeEvent;
import code.stream.StreamTextFile;
import code.stream.comparators.FileNameComparator;
import code.util.CustList;
import code.util.StringList;
import code.util.StringMap;
import code.util.consts.ConstFiles;
import code.util.consts.Constants;
import code.xml.util.ExtractFromFiles;

public abstract class FileDialog extends Dialog {
    private static final String DIALOG_ACCESS = "gui.FileDialog";

    private static final String FILES_PARAM = "filesParam";
    private static final String SPACE = " ";
    private static final String EMPTY_STRING = "";
    private static final String NAME = "name";
    private static final String FILES = "files";
//    private static final String SLASHES = "\\\\|/";
    private static final int NB_COLS = 32;
    private static final int DIALOG_WIDTH = 400;
    private static final int DIALOG_HEIGHT = 278;
    private static final Dimension DIM = new Dimension(DIALOG_WIDTH, DIALOG_HEIGHT);
    private JPanel contentPane = new JPanel();
    private JPanel buttons = new JPanel();
    private JTextField fileName = new JTextField(NB_COLS);
    private JTree folderSystem;
    private FileTable fileModel;
    private JTable fileTable;
    private boolean currentFolderRoot;
    private String selectedPath;
    private String selectedAbsolutePath;
    private String currentFolder;
    private String currentTitle;
    private String lang;
    private boolean addTypingFileName;
    private String extension = EMPTY_STRING;
    private String folder = EMPTY_STRING;
    private StringList excludedFolders = new StringList();

    private StringMap<String> messages;

    protected FileDialog(){
    }
    protected void setFileDialog(GroupFrame _w,String _language, boolean _currentFolderRoot, String _extension, String _folder, String... _excludedFolders) {
        init(_w,_language,_currentFolderRoot, true, _extension, _folder, _excludedFolders);
    }

    protected void init(GroupFrame _w,String _language, boolean _currentFolderRoot, boolean _addTypingFileName, String _extension, String _folder, String... _excludedFolders) {
        //super(_w,true);
        setDialogIcon(_w);
        setModal(true);
        setLocationRelativeTo(_w);
        extension = _extension;
        addTypingFileName = _addTypingFileName;
        folder = _folder;
        initDialog(_language, _currentFolderRoot, _excludedFolders);
    }

    protected void setFileDialog(Dialog _w,String _language, boolean _currentFolderRoot, String _extension, String _folder, String... _excludedFolders) {
        init(_w,_language,_currentFolderRoot, true, _extension, _folder, _excludedFolders);
    }

    protected void init(Dialog _w,String _language, boolean _currentFolderRoot, boolean _addTypingFileName, String _extension, String _folder, String... _excludedFolders) {
        //super(_w,true);
        setDialogIcon(_w);
        setModal(true);
        setLocationRelativeTo(_w);
        extension = _extension;
        addTypingFileName = _addTypingFileName;
        folder = _folder;
        initDialog(_language, _currentFolderRoot, _excludedFolders);
    }

    private void initDialog(String _language, boolean _currentFolderRoot, String... _excludedFolders) {
        messages = ExtractFromFiles.getMessagesFromLocaleClass(GuiConstants.FOLDER_MESSAGES_GUI, Constants.getLanguage(), DIALOG_ACCESS);
        lang = _language;
        currentFolderRoot = _currentFolderRoot;
        selectedPath = EMPTY_STRING;
        selectedAbsolutePath = EMPTY_STRING;
        excludedFolders = new StringList();
        if (currentFolderRoot) {
            String root_ = StringList.replaceBackSlash(new File(folder).getAbsolutePath());
            currentFolder = root_+StreamTextFile.SEPARATEUR;
            if (StringList.quickEq(currentFolder,ConstFiles.getInitFolder())) {
                for (String f: _excludedFolders) {
                    excludedFolders.add(currentFolder+f);
                }
            }
        }
        fileModel = new FileTable();
        currentTitle = messages.getVal(FILES);
        if (currentFolderRoot) {
            currentTitle += SPACE+ currentFolder;
        }
        setTitle(currentTitle);
        fileTable = new JTable(fileModel);
        fileTable.getTableHeader().setReorderingAllowed(false);
        fileTable.getTableHeader().addMouseListener(new ClickHeaderEvent(this));
        fileTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        fileTable.getSelectionModel().addListSelectionListener(new ClickRowEvent(this));
        contentPane.setLayout(new BorderLayout());
        JPanel openSaveFile_ = new JPanel();
        openSaveFile_.setLayout(new BoxLayout(openSaveFile_,BoxLayout.PAGE_AXIS));
        if (addTypingFileName) {
            JPanel fieldFile_ = new JPanel();
            fieldFile_.add(new JLabel(messages.getVal(NAME)));
            fieldFile_.add(fileName);
            openSaveFile_.add(fieldFile_);
        }
        buttons = new JPanel();
        openSaveFile_.add(buttons);
        contentPane.add(openSaveFile_, BorderLayout.SOUTH);
        if (currentFolderRoot) {
            DefaultMutableTreeNode default_ = new DefaultMutableTreeNode(currentFolder.substring(0, currentFolder.length() - 1));
            File[] files_ = new File(currentFolder).listFiles();
            if (files_ != null) {
                CustList<File> currentFiles_ = new CustList<File>(files_);
                currentFiles_.sortElts(new FileNameComparator());
                CustList<File> filesList_ = new CustList<File>();
                for (File f: currentFiles_) {
                    if (f.isDirectory()) {
                        if (excludedFolders.containsObj(StringList.replaceBackSlash(f.getAbsolutePath()))) {
                            continue;
                        }
                        default_.add(new DefaultMutableTreeNode(f.getName()));
                    } else if (f.getName().endsWith(extension)){
                        filesList_.add(f);
                    }
                }
                fileModel.setupFiles(filesList_, currentFolder, extension);
            }
            folderSystem = new JTree(default_);
        } else {
            DefaultMutableTreeNode default_ = new DefaultMutableTreeNode(EMPTY_STRING);
            for (File f: File.listRoots()) {
//                default_.add(new DefaultMutableTreeNode(f.getAbsolutePath().replaceAll(SLASHES, EMPTY_STRING)));
                String path_ = f.getAbsolutePath();
                path_ = StringList.replaceBackSlash(path_);
//                default_.add(new DefaultMutableTreeNode(StringList.splitStrings(f.getAbsolutePath(), StreamTextFile.SEPARATEUR_WIN, StreamTextFile.SEPARATEUR).join(EMPTY_STRING)));
                default_.add(new DefaultMutableTreeNode(StringList.splitStrings(path_, StreamTextFile.SEPARATEUR).join(EMPTY_STRING)));
            }
            folderSystem = new JTree(default_);
            folderSystem.setRootVisible(false);
        }
        folderSystem.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        JSplitPane fileSelector_ = new JSplitPane();
        fileSelector_.setLeftComponent(new JScrollPane(folderSystem));
        folderSystem.addTreeSelectionListener(new DeployTreeEvent(this));
        fileSelector_.setRightComponent(new JScrollPane(fileTable));
        contentPane.add(fileSelector_, BorderLayout.CENTER);
        contentPane.add(openSaveFile_, BorderLayout.SOUTH);
        setContentPane(contentPane);
    }

    public void clickHeader(MouseEvent _e) {
        int col_ = fileTable.columnAtPoint(_e.getPoint());
        fileModel.setSorting(col_);
    }

    public void clickRow() {
        // do some actions here, for example
        // print first column value from selected row
        int index_ = fileTable.getSelectedRow();
        if (index_ == CustList.INDEX_NOT_FOUND_ELT) {
            return;
        }
        selectedPath = fileTable.getValueAt(index_, FileTable.PATH_INDEX).toString();
        selectedAbsolutePath = fileModel.getSelectedFilePath(index_);
        if (addTypingFileName) {
            fileName.setText(fileTable.getValueAt(index_, FileTable.NAME_INDEX).toString());
        }
    }

    public void deployTree(TreeSelectionEvent _e) {
        DefaultMutableTreeNode selected_;
        selected_ = (DefaultMutableTreeNode) folderSystem.getLastSelectedPathComponent();
        if (selected_ == null) {
            return;
        }
        applyTreeChange(_e.getPath());
    }

    public void applyTreeChange() {
        String str_ = getFolder();
        currentFolder = str_;
        currentTitle = StringList.simpleFormat(messages.getVal(FILES_PARAM), currentFolder);
        setTitle(currentTitle);
        File currentFolder_ = new File(str_);
        if (!currentFolder_.exists()) {
            return;
        }
        StringList folderList_ = new StringList();
        CustList<File> files_ = new CustList<File>();
        File[] filesArray_ = currentFolder_.listFiles();
        if (filesArray_ != null) {
            CustList<File> currentFiles_ = new CustList<File>(filesArray_);
            currentFiles_.sortElts(new FileNameComparator());
            for (File l: currentFiles_) {
                if (l.isDirectory()) {
                    if (excludedFolders.containsObj(StringList.replaceBackSlash(l.getAbsolutePath()))) {
                        continue;
                    }
                    folderList_.add(l.getName());
                } else if (l.getName().endsWith(extension)) {
                    files_.add(l);
                }
            }
        }
        folderList_.sort();
        DefaultTreeModel d_;
        d_ = (DefaultTreeModel) folderSystem.getModel();
        d_.reload();
        fileModel.setupFiles(files_,currentFolder, extension);
    }

    public void applyTreeChange(TreePath _treePath) {
        DefaultMutableTreeNode selected_;
        selected_ = (DefaultMutableTreeNode) folderSystem.getLastSelectedPathComponent();
//        if (selected_ == null) {
//            return;
//        }
        StringList pathFull_ = new StringList();
        for (Object o: _treePath.getPath()) {
            pathFull_.add(o.toString());
        }
        pathFull_.removeObj(EMPTY_STRING);
        String str_ = EMPTY_STRING;
        for (Object o: pathFull_) {
            str_ += o.toString() + StreamTextFile.SEPARATEUR;
        }
        currentFolder = str_;
        currentTitle = StringList.simpleFormat(messages.getVal(FILES_PARAM), currentFolder);
        setTitle(currentTitle);
        File currentFolder_ = new File(str_);
        if (!currentFolder_.exists()) {
            selected_.removeFromParent();
            return;
        }
        selected_.removeAllChildren();
        StringList folderList_ = new StringList();
        CustList<File> files_ = new CustList<File>();
        File[] filesArray_ = currentFolder_.listFiles();
        if (filesArray_ != null) {
            CustList<File> currentFiles_ = new CustList<File>(filesArray_);
            currentFiles_.sortElts(new FileNameComparator());
            for (File l: currentFiles_) {
                if (l.isDirectory()) {
                    if (excludedFolders.containsObj(StringList.replaceBackSlash(l.getAbsolutePath()))) {
                        continue;
                    }
                    folderList_.add(l.getName());
                } else if (l.getName().endsWith(extension)) {
                    files_.add(l);
                }
            }
        }
        folderList_.sort();
        for (String f: folderList_) {
            selected_.add(new DefaultMutableTreeNode(f));
        }
        DefaultTreeModel d_;
        d_ = (DefaultTreeModel) folderSystem.getModel();
        d_.reload(selected_);
        fileModel.setupFiles(files_,currentFolder, extension);
    }
    @Override
    public void pack() {
        setSize(DIM);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        super.pack();
        setVisible(true);
    }

    protected String getSelectedPath() {
        return selectedPath;
    }

    protected String getSelectedAbsolutePath() {
        return selectedAbsolutePath;
    }

    protected String getExtension() {
        return extension;
    }

    protected JPanel getButtons() {
        return buttons;
    }

    protected JTextField getFileName() {
        return fileName;
    }

    protected JTree getFolderSystem() {
        return folderSystem;
    }

    protected FileTable getFileModel() {
        return fileModel;
    }

    protected JTable getFileTable() {
        return fileTable;
    }

    protected boolean isCurrentFolderRoot() {
        return currentFolderRoot;
    }

    public void setSelectedPath(String _selectedPath) {
        selectedPath = _selectedPath;
    }

    protected void setSelectedAbsolutePath(String _selectedAbsolutePath) {
        selectedAbsolutePath = _selectedAbsolutePath;
    }

    protected void setCurrentFolder(String _currentFolder) {
        currentFolder = _currentFolder;
    }

    protected String getCurrentFolder() {
        return currentFolder;
    }

    protected void setCurrentTitle(String _currentTitle) {
        currentTitle = _currentTitle;
    }

    protected String getCurrentTitle() {
        return currentTitle;
    }

    protected String getLang() {
        return lang;
    }

    protected String getFolder() {
        return folder;
    }

    protected StringList getExcludedFolders() {
        return excludedFolders;
    }
}
