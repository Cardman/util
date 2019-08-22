package aiki.gui;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.Socket;

import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import aiki.beans.PokemonStandards;
import aiki.db.DataBase;
import aiki.db.ImageHeroKey;
import aiki.db.LoadFlag;
import aiki.db.PerCent;
import aiki.gui.threads.*;
import aiki.sml.*;
import aiki.facade.FacadeGame;
import aiki.game.Game;
import aiki.game.player.enums.Sex;
import aiki.gui.components.fight.FrontBattle;
import aiki.gui.components.fight.FrontClickEvent;
import aiki.gui.components.labels.HeroLabel;
import aiki.gui.components.walk.ScenePanel;
import aiki.gui.dialogs.DialogDifficulty;
import aiki.gui.dialogs.DialogGameProgess;
import aiki.gui.dialogs.FrameHtmlData;
import aiki.gui.dialogs.ProgressingDialogPokemon;
import aiki.gui.dialogs.SoftParams;
import aiki.gui.events.ConfirmNewGameEvent;
import aiki.gui.events.LoadGameEvent;
import aiki.gui.events.LoadZipEvent;
import aiki.gui.events.ManageDifficultyEvent;
import aiki.gui.events.ManageLanguageEvent;
import aiki.gui.events.ManageParamsEvent;
import aiki.gui.events.ProponeNewGameEvent;
import aiki.gui.events.QuitEvent;
import aiki.gui.events.SaveGameEvent;
import aiki.gui.events.ShowDataFightEvent;
import aiki.gui.events.ShowDataWebEvent;
import aiki.gui.listeners.HeroSelect;
import aiki.main.AfterLoadGame;
import aiki.main.AfterLoadZip;
import aiki.main.LaunchingPokemon;
import aiki.main.LoadGame;
import aiki.main.VideoLoading;
import aiki.map.levels.enums.EnvironmentType;
import aiki.map.pokemon.PokemonPlayer;
import aiki.network.Net;
import aiki.network.SendReceiveServer;
import aiki.network.sml.DocumentReaderAikiMultiUtil;
import aiki.network.sml.DocumentWriterAikiMultiUtil;
import aiki.network.stream.CheckCompatibility;
import aiki.network.stream.IndexOfArriving;
import aiki.network.stream.InitTrading;
import aiki.network.stream.NetPokemon;
import aiki.network.stream.NewPlayer;
import aiki.network.stream.Ok;
import aiki.network.stream.Quit;
import code.gui.*;
import code.gui.document.RenderedPage;
import code.gui.events.QuittingEvent;
import code.network.AttemptConnecting;
import code.network.BasicClient;
import code.network.Exiting;
import code.network.NetGroupFrame;
import code.stream.StreamTextFile;
import code.stream.StreamZipFile;
import code.util.CustList;
import code.util.EnumMap;
import code.util.*;
import code.util.StringList;
import code.util.StringMap;
import code.util.consts.Constants;

public final class MainWindow extends NetGroupFrame {
    //implemented SettingInfosAfterCompiler

    public static final String OK = "ok";
    private static final String DIALOG_ACCESS = "aiki.gui.mainwindow";

    private static final String TITLE = "title";

    private static final String FILE = "file";
    private static final String ZIP_LOAD = "zipLoad";
    private static final String GAME_LOAD = "gameLoad";
    private static final String GAME_SAVE = "gameSave";
    private static final String LANGUAGE = "language";
    private static final String PARAMS = "params";
    private static final String DATA_GAME = "dataGame";
    private static final String NEW_GAME = "newGame";
    private static final String DATA_WEB = "dataWeb";

    private static final String NICKNAME = "nickname";

    private static final String SAVING = "saving";

    private static final String SAVING_TITLE = "savingTitle";

    private static final String ERROR_LOADING = "errorLoading";

    private static final String SUCCESSFUL_LOADING = "successfulLoading";

    private static final String TOO_MANY = "tooMany";

    private static final String NO_TRADE = "noTrade";

    private static final String LAST_SAVED_GAME = "lastSavedGame";

    private static final String OPEN_HTML = "openHtml";

    private static final String TITLE_WEB = "titleWeb";

    private static final String TITLE_BATTLE = "titleBattle";

    private static final String DIFFICULTY = "difficulty";

    private static final String TITLE_DIFFICULTY = "titleDifficulty";

    private static final String QUIT = "quit";

//    private static final String PARAMETRES = "parametres";

    private static final String HELP_INFO = "helpInfo";

    private static final String GAME_PROGRESS = "gameProgress";

    private static final String AVAILAIBLE_HELPS = "availableHelps";

//    private static final String EXCLUDED = "jdk";

    private static final String F_ONE = "F1";
    private static final String F_TWO = "F2";
    private static final String F_THREE = "F3";

//    private static final String LOG_FILE = LaunchingPokemon.getTempFolderSl()+"errors_compiling.txt";

//    private static final String CLASS_FILES_EXT = DataBase.CLASS_FILES_EXT;

//    private static final String LOCALE = "locale";

    //private static final boolean COMPILE = false;

    private StringMap<String> messages = new StringMap<String>();
    private final ProgressingDialogPokemon dialog = new ProgressingDialogPokemon(this);
    
//    private Timer timer;

    private BasicClient threadEmission;

    private LoadingGame loadingConf;

    private Menu file;

    private MenuItem zipLoad;

    private MenuItem gameLoad;

    private MenuItem gameSave;

    private MenuItem language;

    private MenuItem params;

    private MenuItem quit;

    private Menu dataGame;

    private MenuItem dataWeb;

    private MenuItem dataBattle;

    private MenuItem newGame;

    private MenuItem difficulty;

    private Panel mainPanel;

    private final CustList<FrameHtmlData> htmlDialogs = new CustList<FrameHtmlData>();

    private byte indexInGame = CustList.INDEX_NOT_FOUND_ELT;

    /**Est vrai si et seulement si une partie vient d'etre sauvegardee*/
    private boolean savedGame;

    private FacadeGame facade;

    private Panel beginGame;

    private final EnumMap<Sex,HeroLabel> herosLabels = new EnumMap<Sex,HeroLabel>();

    private TextField nickname;

    private Sex chosenSex;

    private Clock time;

    private TextLabel lastSavedGameDate;

    private String dateLastSaved = DataBase.EMPTY_STRING;

    private TextLabel helpInfo;

    private TextLabel availableHelps;

    private boolean inBattle;

    private ScenePanel scenePanel;

//    private Battle battle;
    private FrontBattle battle;

    private boolean enabledMove;

    private FightIntroThread fightIntroThread;
    private Thread fightIntroThreadLau;

    private VideoLoading videoLoading = new VideoLoading();
    private LoadFlag loadFlag = new LoadFlagImpl();
//    private KeyPadListener keyPadListener;

//    private ForwardingJavaCompiler compiling;

    //private boolean startThread;

//    private boolean successfulCompile;

//    private final boolean standalone;

    public MainWindow(String _lg) {
        super(_lg);
        setAccessFile(DIALOG_ACCESS);
        setFocusable(true);
        setFocusableWindowState(true);
        facade = new FacadeGame();
        StringList lgs_ = Constants.getAvailableLanguages();
        facade.setLanguages(lgs_);
        StringMap<String> displayLanguages_ = new StringMap<String>();
        for (String s: lgs_) {
            displayLanguages_.put(s, Constants.getDisplayLanguage(s));
        }
        facade.setDisplayLanguages(displayLanguages_);
        facade.setSimplyLanguage(_lg);
        setImageIconFrame(LaunchingPokemon.getIcon());
        mainPanel = Panel.newPageBox();
        scenePanel = new ScenePanel(this, facade);
        initBattle();
        initMenuBar();
        gameLoad.setEnabledMenu(false);
        gameSave.setEnabledMenu(false);
        dataGame.setEnabledMenu(false);
        battle.setVisibleFrontBattle(false);
        mainPanel.add(battle);
        mainPanel.add(scenePanel);
        time = new Clock();
        mainPanel.add(time);
        lastSavedGameDate = new TextLabel("");
        mainPanel.add(lastSavedGameDate);
        helpInfo = new TextLabel("");
        mainPanel.add(helpInfo);
        availableHelps = new TextLabel("");
        mainPanel.add(availableHelps);
        setContentPane(mainPanel);
        //setVisible(true);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new QuittingEvent(this));
        initMessages();
        setTitle(messages.getVal(TITLE));
    }

    @Override
    public void quit() {
        if (indexInGame != CustList.INDEX_NOT_FOUND_ELT) {
            Quit quit_ = new Quit();
            quit_.setClosing(true);
            quit_.setPlace(indexInGame);
            quit_.setLocale(getLanguageKey());
            try {
                sendObject(quit_);
            } catch (RuntimeException _0) {
                //LaunchingPokemon.decrement();
                exitFromTrading();
                resetIndexInGame();
                dispose();
//                if (Standalone.isStandalone()) {
//                    Constants.exit();
//                }
            }
            return;
        }
        if (battle != null) {
            while (isAliveThread()) {
                continue;
            }
        }
//        if (compiling.isAlive()) {
//            int adv_ = Constants.getPercentCompile();
//            String message_ = getCompilingString();
//            String formatted_ = MessageFormat.format(message_, adv_);
//            showErrorMessageDialog(formatted_);
//            return;
//        }
        if (loadingConf == null) {
            //LaunchingPokemon.decrement();
            dispose();
            return;
        }
        if (loadingConf.isSaveGameAtExit()) {
            if (loadingConf.getLastSavedGame().isEmpty()) {
                String name_ = StringList.concat(LaunchingPokemon.getTempFolderSl(),LoadingGame.DEFAULT_SAVE_GAME,Resources.GAME_EXT);
                loadingConf.setLastSavedGame(name_);
                save(name_);
                if (!new File(name_).exists()) {
                    name_ = StringList.concat(ConstFiles.getInitFolder(),LoadingGame.DEFAULT_SAVE_GAME,Resources.GAME_EXT);
                    int index_ = 0;
                    while (new File(name_).exists()) {
                        name_ = StringList.concat(ConstFiles.getInitFolder(),LoadingGame.DEFAULT_SAVE_GAME,Long.toString(index_),Resources.GAME_EXT);
                        index_++;
                    }
                    loadingConf.setLastSavedGame(name_);
                    save(name_);
                }
            } else {
                String path_ = new File(loadingConf.getLastSavedGame()).getAbsolutePath();
                path_ = StringList.replaceBackSlash(path_);
                save(path_);
            }
            StreamTextFile.saveTextFile(StringList.concat(LaunchingPokemon.getTempFolderSl(),Resources.LOAD_CONFIG_FILE), DocumentWriterAikiCoreUtil.setLoadingGame(loadingConf));
            //LaunchingPokemon.decrement();
            dispose();
        } else if (indexInGame == CustList.INDEX_NOT_FOUND_ELT && !savedGame) {
            if (facade.getGame() == null) {
                StreamTextFile.saveTextFile(StringList.concat(LaunchingPokemon.getTempFolderSl(),Resources.LOAD_CONFIG_FILE), DocumentWriterAikiCoreUtil.setLoadingGame(loadingConf));
                //LaunchingPokemon.decrement();
                dispose();
                return;
            }
            int choix_=saving();
            if(choix_!=JOptionPane.CANCEL_OPTION) {
                if(choix_==JOptionPane.YES_OPTION) {
                    String file_ = fileDialogSave();
                    if (!file_.isEmpty()) {
                        loadingConf.setLastSavedGame(file_);
                        save(file_);
                    }
                }
                savedGame = true;
                StreamTextFile.saveTextFile(StringList.concat(LaunchingPokemon.getTempFolderSl(),Resources.LOAD_CONFIG_FILE), DocumentWriterAikiCoreUtil.setLoadingGame(loadingConf));
                //LaunchingPokemon.decrement();
//                ecrireCoordonnees();
//                CustList<FrameHtmlData> frames_ = new CustList<>();
//                frames_.addAll(htmlDialogs);
//                frames_.addAll(battle.getHtmlDialogs());
//                for (FrameHtmlData f: frames_) {
//                    f.dispose();
//                }
//                clearHtmlDialogs();
//                battle.clearHtmlDialogs();
                dispose();
            }
        } else {
            //LaunchingPokemon.decrement();
//            ecrireCoordonnees();
//            CustList<FrameHtmlData> frames_ = new CustList<>();
//            frames_.addAll(htmlDialogs);
//            frames_.addAll(battle.getHtmlDialogs());
//            for (FrameHtmlData f: frames_) {
//                f.dispose();
//            }
//            clearHtmlDialogs();
//            battle.clearHtmlDialogs();
            dispose();
//            Constants.exit();
//            if (Standalone.isStandalone()) {
//                Constants.exit();
//            }
        }
    }
    @Override
    public void dispose() {
        while (isPaintingScene()) {
            continue;
        }
        ecrireCoordonnees();
        CustList<FrameHtmlData> frames_ = new CustList<FrameHtmlData>();
        frames_.addAllElts(htmlDialogs);
        frames_.addAllElts(battle.getHtmlDialogs());
        for (FrameHtmlData f: frames_) {
//            f.dispose();
            f.setVisible(false);
        }
        battle.getBattle().setVisible(false);
        //clearHtmlDialogs();
        //battle.clearHtmlDialogs();
        //removeAll();
        LaunchingPokemon.decrement();
        super.dispose();
        //facade = null;
    }
    public void initMessages() {
        facade.getData().setLanguage(facade.getLanguage());
        DocumentReaderAikiCoreUtil.initMessages(facade.getData(),facade.getLanguage());
        messages = getMessages(this,Resources.MESSAGES_FOLDER);
        file.setText(messages.getVal(FILE));
        zipLoad.setText(messages.getVal(ZIP_LOAD));
        gameLoad.setText(messages.getVal(GAME_LOAD));
        gameSave.setText(messages.getVal(GAME_SAVE));
        language.setText(messages.getVal(LANGUAGE));
        params.setText(messages.getVal(PARAMS));
        dataGame.setText(messages.getVal(DATA_GAME));
        quit.setText(messages.getVal(QUIT));
        newGame.setText(messages.getVal(NEW_GAME));
        //dataGame.setText(messages.getVal(NEW_GAME));
        dataWeb.setText(messages.getVal(DATA_WEB));
        dataBattle.setText(messages.getVal(TITLE_BATTLE));
        difficulty.setText(messages.getVal(DIFFICULTY));
//        lastSavedGameDate.setText(MessageFormat.format(messages.getVal(LAST_SAVED_GAME), dateLastSaved));
        lastSavedGameDate.setText(StringList.simpleStringsFormat(messages.getVal(LAST_SAVED_GAME), dateLastSaved));
        if (!helpInfo.getText().isEmpty()) {
            helpInfo.setText(messages.getVal(HELP_INFO));
        }
        if (!availableHelps.getText().isEmpty()) {
            availableHelps.setText(messages.getVal(AVAILAIBLE_HELPS));
        }
        scenePanel.initMessages(getLanguageKey());
        battle.getBattle().initMessages();
    }

    public String getTooManyString() {
        return messages.getVal(TOO_MANY);
    }

    public String getNoTradeString() {
        return messages.getVal(NO_TRADE);
    }

    public String getOpenedHtmlString() {
        return messages.getVal(OPEN_HTML);
    }

    private void addBeginGame() {
        if (beginGame == null) {
            beginGame = Panel.newPageBox();
        }
        beginGame.removeAll();
        Panel heros_ = new Panel();
        for (Sex s: Sex.values()) {
            ImageHeroKey i_;
            i_ = new ImageHeroKey(EnvironmentType.ROAD, s);
            int[][] imgTxt_ = facade.getData().getFrontHeros().getVal(i_);
            HeroLabel label_ = new HeroLabel(imgTxt_);
            label_.setPreferredSize(new Dimension(imgTxt_[0].length, imgTxt_.length));
            label_.addMouseListener(new HeroSelect(this, s));
            herosLabels.put(s, label_);
            heros_.add(label_);
        }
        beginGame.add(heros_);
        Panel nickname_ = new Panel();
        nickname_.add(new TextLabel(messages.getVal(NICKNAME)));
        if (nickname == null) {
            nickname = new TextField(16);
        }
        nickname_.add(nickname);
        beginGame.add(nickname_);
        LabelButton ok_ = new LabelButton(OK);
        ok_.addMouseListener(new ConfirmNewGameEvent(this));
        beginGame.add(ok_);
        scenePanel.addBeginGame(beginGame);
    }

    public void confirmNewGame() {
        newGame();
        pack();
    }

    public void clearHtmlDialogs() {
        htmlDialogs.clear();
    }

    public void changeSex(Sex _sex) {
        chosenSex = _sex;
        herosLabels.getVal(_sex).setSelected(true);
        herosLabels.getVal(_sex.getOppositeSex()).setSelected(false);
        beginGame.repaint();
    }

    private void newGame() {
        facade.newGame(nickname.getText(), chosenSex);
        drawGame();
        savedGame = false;
        gameSave.setEnabledMenu(true);
        loadingConf.setLastSavedGame(DataBase.EMPTY_STRING);
    }

    /**thread safe method*/
    public void loadOnlyRom(String _file, PerCent _p) {
        if (!_file.isEmpty()) {
            //startThread = true;
            StringMap<String> files_ = StreamZipFile.zippedTextFiles(_file);
            DocumentReaderAikiCoreUtil.loadRomAndCheck(facade,_file, files_,_p,loadFlag);
            if (!facade.isLoadedData()) {
                DocumentReaderAikiCoreUtil.loadResources(facade,_p,loadFlag);
            }
            if (!loadFlag.get()) {
                return;
            }
        } else {
            DocumentReaderAikiCoreUtil.loadResources(facade,_p,loadFlag);
            if (!loadFlag.get()) {
                return;
            }
        }
        facade.initializePaginatorTranslations();
        ThreadInvoker.invokeNow(new AfterLoadZip(this));
    }

    /**thread safe method*/
    public void loadRomGame(LoadingGame _configuration, String _path, StringMap<Object> _files, boolean _param, PerCent _p) {
        String path_;
        if (!_configuration.getLastRom().isEmpty()) {
            File file_ = new File(StringList.replaceBackSlash(_configuration.getLastRom()));
            if (!file_.isAbsolute()) {
                path_ = StringList.concat(_path,_configuration.getLastRom());
            } else {
                path_ = file_.getAbsolutePath();
            }
            path_ = StringList.replaceBackSlash(path_);
            StringMap<String> files_ = StreamZipFile.zippedTextFiles(path_);
            DocumentReaderAikiCoreUtil.loadRomAndCheck(facade,path_, files_,_p,loadFlag);
            if (!facade.isLoadedData()) {
                DocumentReaderAikiCoreUtil.loadResources(facade,_p,loadFlag);
            }
            if (!loadFlag.get()) {
                return;
            }
        } else {
            DocumentReaderAikiCoreUtil.loadResources(facade,_p,loadFlag);
            if (!loadFlag.get()) {
                return;
            }
        }
        facade.initializePaginatorTranslations();
        ThreadInvoker.invokeNow(new AfterLoadZip(this));
        if (!_files.isEmpty() && _files.values().first() instanceof Game) {
            if (!facade.checkAndSetGame((Game) _files.values().first())) {
                loadFlag.set(false);
                if (_param) {
                    setLoadingConf(_configuration, false);
                }
                return;
            }
        } else {
            File file_ = new File(StringList.replaceBackSlash(_configuration.getLastSavedGame()));
            if (!file_.isAbsolute()) {
                path_ = StringList.concat(_path,_configuration.getLastSavedGame());
            } else {
                path_ = file_.getAbsolutePath();
            }
            path_ = StringList.replaceBackSlash(path_);
            DataBase db_ = facade.getData();
            Game game_ = load(path_, db_);
            if (game_ == null) {
                loadFlag.set(false);
                if (_param) {
                    setLoadingConf(_configuration, false);
                }
                return;
            }
            facade.load(game_);
        }
        facade.changeCamera();
        ThreadInvoker.invokeNow(new AfterLoadGame(this));
    }

    public void afterLoadZip() {
        dataGame.setEnabledMenu(true);
        gameLoad.setEnabledMenu(true);
        gameSave.setEnabledMenu(false);
    }

    public void afterLoadGame() {
        gameSave.setEnabledMenu(true);
        drawGame();
        savedGame = true;
    }

    private void drawGame() {
        scenePanel.setMessages();
        if (facade.isChangeToFightScene()) {
            if (battle != null) {
                battle.setWild(false);
            }
            setFight(true, false, false);
            return;
        }
        drawGameWalking(true);
        pack();
    }

    public void drawGameWalking(boolean _setPreferredSize) {
//        if (battle != null) {
//            mainPanel.remove(battle);
//        }
        difficulty.setEnabledMenu(true);
        battle.setVisibleFrontBattle(false);
        scenePanel.setVisible(true);
        inBattle = false;
        dataBattle.setEnabledMenu(false);
        scenePanel.drawGameWalking(_setPreferredSize);
    }

//    public void addToArea() {
//        String comment_ = getComment();
//        scenePanel.setTextArea(comment_);
//    }

    public String getComment() {
        return scenePanel.getComment();
    }

//    public void setSceneKepPad(Scene _scene) {
//        keyPadListener.setSceneKepPad(_scene);
//    }

    private void initMenuBar() {
        MenuBar bar_ = new MenuBar();
        file = new Menu();
        zipLoad = new MenuItem();
        zipLoad.addActionListener(new LoadZipEvent(this));
        zipLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, InputEvent.CTRL_DOWN_MASK));
        file.addMenuItem(zipLoad);
        gameLoad = new MenuItem();
        gameLoad.addActionListener(new LoadGameEvent(this));
        gameLoad.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
        file.addMenuItem(gameLoad);
        gameSave = new MenuItem();
        gameSave.addActionListener(new SaveGameEvent(this));
        gameSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        file.addMenuItem(gameSave);
        file.addSeparator();
        language = new MenuItem();
        language.addActionListener(new ManageLanguageEvent(this));
//        if (Standalone.isStandalone()) {
//            file.add(language);
//        }
        file.addMenuItem(language);
        params = new MenuItem();
        params.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK));
        params.addActionListener(new ManageParamsEvent(this));
        file.addMenuItem(params);
        file.addSeparator();
        quit = new MenuItem();
        quit.setAccelerator(KeyStroke.getKeyStroke((char)KeyEvent.VK_ESCAPE));
        quit.addActionListener(new QuitEvent(this));
        file.addMenuItem(quit);
        bar_.add(file);
        dataGame = new Menu();
//        dataGame = new JMenuItem();
//        dataGame.addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mouseReleased(MouseEvent _e) {
////                if (!newGame.isEnabled()) {
////                    return;
////                }
//                if (!dataGame.isEnabled()) {
//                    return;
//                }
//                if (battle != null) {
//                    while (isAliveThread()) {
//                        continue;
//                    }
//                }
//                if (!Numbers.eq(indexInGame, CustList.INDEX_NOT_FOUND_ELT)) {
//                    return;
//                }
//                addBeginGame();
//                pack();
//            }
//        });
        dataWeb = new MenuItem();
        dataWeb.setAccelerator(KeyStroke.getKeyStroke(F_ONE));
        dataWeb.addActionListener(new ShowDataWebEvent(this));
        dataGame.addMenuItem(dataWeb);
        dataBattle = new MenuItem();
        dataBattle.setEnabledMenu(false);
        dataBattle.setAccelerator(KeyStroke.getKeyStroke(F_TWO));
        dataBattle.addActionListener(new ShowDataFightEvent(this));
        dataGame.addMenuItem(dataBattle);
        newGame = new MenuItem();
        newGame.addActionListener(new ProponeNewGameEvent(this));
        newGame.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK));
        dataGame.addMenuItem(newGame);
        difficulty = new MenuItem();
        difficulty.setEnabledMenu(false);
        difficulty.addActionListener(new ManageDifficultyEvent(this));
        difficulty.setAccelerator(KeyStroke.getKeyStroke(F_THREE));
        dataGame.addMenuItem(difficulty);
        bar_.add(dataGame);
        setJMenuBar(bar_);
    }

    public void loadZip() {
        if (openedHmlFrames()) {
            //error message if data html
            String message_ = getOpenedHtmlString();
            showErrorMessageDialog(message_);
            return;
        }
//        if (showErrorMessageDialog(ForwardingJavaCompiler.getMess(Constants.getLanguage()))) {
//            return;
//        }
//        if (!zipLoad.isEnabled()) {
//            return;
//        }
        if (!Numbers.eq(indexInGame, CustList.INDEX_NOT_FOUND_ELT)) {
            return;
        }
        if (battle != null) {
            while (isAliveThread()) {
                continue;
            }
        }
        while (isPaintingScene()) {
            continue;
        }
        if (!savedGame && facade.getGame() != null) {
            int choix_=saving();
            if(choix_==JOptionPane.CANCEL_OPTION) {
                return;
            }
            loadingConf.setLastSavedGame(DataBase.EMPTY_STRING);
            if(choix_==JOptionPane.YES_OPTION) {
                String file_ = fileDialogSave();
                if (!file_.isEmpty()) {
                    loadingConf.setLastSavedGame(file_);
                    save(file_);
                    dateLastSaved = Clock.getDateTimeText();
                    lastSavedGameDate.setText(StringList.simpleStringsFormat(messages.getVal(LAST_SAVED_GAME), dateLastSaved));
                    savedGame = true;
                }
            }
            StreamTextFile.saveTextFile(StringList.concat(LaunchingPokemon.getTempFolderSl(),Resources.LOAD_CONFIG_FILE), DocumentWriterAikiCoreUtil.setLoadingGame(loadingConf));
        }
        String fileName_ = fileDialogLoad(Resources.ZIPPED_DATA_EXT, true);
        if (fileName_.isEmpty()) {
            return;
        }
        PerCent p_ = new PerCentIncr();
        loadFlag.set(true);
        LoadingThread load_ = new LoadingThread(this, fileName_,p_);
        LoadGame opening_ = new LoadGame(this,p_);
        CustComponent.newThread(load_).start();
        CustComponent.newThread(opening_).start();
    }

    public void loadGame() {
        if (!Numbers.eq(indexInGame, CustList.INDEX_NOT_FOUND_ELT)) {
            return;
        }
        if (battle != null) {
            while (isAliveThread()) {
                continue;
            }
        }
        while (isPaintingScene()) {
            continue;
        }
        if (!savedGame && facade.getGame() != null) {
            int choix_=saving();
            if(choix_==JOptionPane.CANCEL_OPTION) {
                return;
            }
            loadingConf.setLastSavedGame(DataBase.EMPTY_STRING);
            if(choix_==JOptionPane.YES_OPTION) {
                String file_ = fileDialogSave();
                if (!file_.isEmpty()) {
                    loadingConf.setLastSavedGame(file_);
                    save(file_);
                    dateLastSaved = Clock.getDateTimeText();
//                    lastSavedGameDate.setText(MessageFormat.format(messages.getVal(LAST_SAVED_GAME), dateLastSaved));
                    lastSavedGameDate.setText(StringList.simpleStringsFormat(messages.getVal(LAST_SAVED_GAME), dateLastSaved));
                    savedGame = true;
                }
            }
            StreamTextFile.saveTextFile(StringList.concat(LaunchingPokemon.getTempFolderSl(),Resources.LOAD_CONFIG_FILE), DocumentWriterAikiCoreUtil.setLoadingGame(loadingConf));
        }
        String fileName_ = fileDialogLoad(Resources.GAME_EXT, false);
        if (fileName_.isEmpty()) {
            return;
        }
        boolean error_ = false;
        DataBase db_ = facade.getData();
        Game game_ = load(fileName_, db_);
        if (game_ != null) {
            facade.load(game_);
            gameSave.setEnabledMenu(true);
            facade.changeCamera();
            drawGame();
            savedGame = true;
            if (battle != null) {
                battle.resetWindows();
            }
        } else {
            error_ = true;
        }
        if (error_) {
            showErrorMessageDialog(fileName_);
        }
    }

    public static Game load(String _fileName,DataBase _data) {
        Game game_ = DocumentReaderAikiCoreUtil.getGame(StreamTextFile.contentsOfFile(_fileName));
        if (game_ == null) {
            return null;
        }
        if (!game_.checkAndInitialize(_data)) {
            return null;
        }
        return game_;
    }

    public void saveGame() {
        String fileName_ = fileDialogSave();
        if (fileName_.isEmpty()) {
            return;
        }
        if (battle != null) {
            while (isAliveThread()) {
                continue;
            }
        }
        save(fileName_);
        fileName_ = StringList.replaceBackSlash(fileName_);
        loadingConf.setLastSavedGame(fileName_);
        dateLastSaved = Clock.getDateTimeText();
        lastSavedGameDate.setText(StringList.simpleStringsFormat(messages.getVal(LAST_SAVED_GAME), dateLastSaved));
        savedGame = true;
    }

    //Save option
    public void save(String _fileName) {
        Game game_ = facade.getGame();
        if (game_ == null) {
            return;
        }
        game_.setZippedRom(facade.getZipName());
        StreamTextFile.saveTextFile(_fileName, DocumentWriterAikiCoreUtil.setGame(game_));
    }

    public void manageLanguage() {
        if (!GroupFrame.canChangeLanguageAll()) {
            GroupFrame.showDialogError(this);
            return;
        }
        LanguageDialog.setLanguageDialog(this, messages.getVal(LANGUAGE));
        String langue_ = LanguageDialog.getStaticLanguage();
        if(langue_ == null || langue_.isEmpty()) {
            return;
        }
        GroupFrame.changeStaticLanguage(langue_);
        SoftApplicationCore.saveLanguage(LaunchingPokemon.getTempFolder(), langue_);
    }

    public void manageParams() {
        if (battle != null) {
            while (isAliveThread()) {
                continue;
            }
        }
        while (isPaintingScene()) {
            continue;
        }
        SoftParams.setSoftParams(this, loadingConf);
        SoftParams.setParams(loadingConf);
        if (SoftParams.isOk()) {
            StreamTextFile.saveTextFile(StringList.concat(LaunchingPokemon.getTempFolderSl(),Resources.LOAD_CONFIG_FILE), DocumentWriterAikiCoreUtil.setLoadingGame(loadingConf));
        }
    }

    public void proponeNewGame() {
        if (battle != null) {
            while (isAliveThread()) {
                continue;
            }
        }
        while (isPaintingScene()) {
            continue;
        }
        if (!Numbers.eq(indexInGame, CustList.INDEX_NOT_FOUND_ELT)) {
            return;
        }
        addBeginGame();
        pack();
    }

    public void manageDifficulty() {
//        if (showErrorMessageDialog(ForwardingJavaCompiler.getMess(Constants.getLanguage()))) {
//            return;
//        }
        DialogDifficulty.setDialogDifficulty(this, messages.getVal(TITLE_DIFFICULTY), facade);
    }

    @Override
    public boolean canChangeLanguage() {
        if (!inBattle) {
            if (isPaintingScene()) {
                return false;
            }
            if (!scenePanel.isMenusVisible()) {
                return false;
            }
        } else {
            if (battle != null) {
                if (isAliveThread()) {
                    return false;
                }
                return battle.isEnabledChangeLanguage();
            }
        }
        return true;
    }

    @Override
    public void changeLanguage(String _language) {
        setLanguageKey(_language);
        facade.setLanguage(_language);
        initMessages();
        if (inBattle) {
            battle.setMessages();
        } else {
            scenePanel.setMessages();
        }
        pack();
        for (FrameHtmlData f: htmlDialogs) {
            f.setTitle(messages.getVal(TITLE_WEB));
            if (!f.isVisible()) {
                continue;
            }
            f.refresh(this);
            f.pack();
        }
        if (battle != null) {
            battle.refreshSession();
        }
    }

    //EDT (mouse event, key event, ...) can not happen at the same time
    public void showDataWeb() {
        if (!dataWeb.isEnabled()) {
            return;
        }
//        if (showErrorMessageDialog(ForwardingJavaCompiler.getMess(Constants.getLanguage()))) {
//            return;
//        }
        if (!htmlDialogs.isEmpty()) {
            if (!htmlDialogs.first().isVisible()) {
                reinitWebData();
                htmlDialogs.first().setVisible(true);
            }
            return;
        }

        //JTextArea area_ = new JTextArea();
        RenderedPage session_;
        session_ = new RenderedPage(new ScrollPane());
        session_.setLanguage(facade.getLanguage());
        session_.setDataBase(facade.getData());
        session_.setProcess(videoLoading.getVideo());
        FrameHtmlData dialog_ = new FrameHtmlData(this, messages.getVal(TITLE_WEB), session_);
//        dialog_.initSession(facade.getData().getWebFiles(), successfulCompile, Resources.CONFIG_DATA, Resources.ACCESS_TO_DEFAULT_DATA);
        dialog_.initSession(Resources.ACCESS_TO_DEFAULT_DATA);
        htmlDialogs.add(dialog_);
    }

    public void showGameProgressing() {
//        if (showErrorMessageDialog(ForwardingJavaCompiler.getMess(Constants.getLanguage()))) {
//            return;
//        }
        DialogGameProgess.setGameProgress(this, messages.getVal(GAME_PROGRESS), facade);
    }

    private void reinitWebData() {
        htmlDialogs.first().setTitle(messages.getVal(TITLE_WEB));
//        htmlDialogs.first().getSession().setFiles(facade.getData().getWebFiles(), Resources.ACCESS_TO_DEFAULT_FILES);
        htmlDialogs.first().getSession().setFiles(Resources.ACCESS_TO_DEFAULT_FILES);
        htmlDialogs.first().getSession().setDataBase(facade.getData());
        htmlDialogs.first().getSession().initializeOnlyConf(Resources.ACCESS_TO_DEFAULT_DATA, new PokemonStandards());
        htmlDialogs.first().pack();
    }

    private boolean openedHmlFrames() {
        if (battle != null) {
            if (battle.openedHtmlFrames()) {
                return true;
            }
        }
        if (htmlDialogs.isEmpty()) {
            return false;
        }
        return htmlDialogs.first().isVisible();
    }
    private void ecrireCoordonnees() {
        Point point_=getLocation();
        SoftApplicationCore.saveCoords(LaunchingPokemon.getTempFolder(),Resources.COORDS, point_.x,point_.y);
    }

    public void processLoad(String _fileName, PerCent _p) {
        StringMap<String> files_ = StreamZipFile.zippedTextFiles(_fileName);
        DocumentReaderAikiCoreUtil.loadRomAndCheck(facade,_fileName, files_,_p,loadFlag);
        if (!facade.isLoadedData()) {
            DocumentReaderAikiCoreUtil.loadResources(facade,_p,loadFlag);
        }
        if (!loadFlag.get()) {
            return;
        }
        facade.clearGame();
        facade.initializePaginatorTranslations();
        inBattle = false;
        ThreadInvoker.invokeNow(new ReinitComponents(this));
//        battle.setVisible(false);
//        scenePanel.reinit();
//        String ext_ = StringList.escape(CLASS_FILES_EXT)+StringList.END_REG_EXP;
//        compiling = new ForwardingJavaCompiler(this, facade.getData().getJavaBeans(), StreamZipFile.getFilesInJar().filter(ext_));
//        compiling = new ForwardingJavaCompiler(this, facade.getData().getJavaBeans(), StreamZipFile.getFilesInJar().filterEndsWith(CLASS_FILES_EXT));
//        ForwardingJavaCompiler.initialize(this, facade.isCompileFiles());
//        for (EntryCust<String, String> e: facade.getData().getJavaBeans().entryList()) {
//            ForwardingJavaCompiler.addSourceCode(e.getKey(), e.getValue());
//        }
//        ThreadInvoker.invokeNow(new AfterCompiling(this, false, false));
        ThreadInvoker.invokeNow(new AfterLoadZip(this));
        loadingConf.setLastRom(_fileName);
//        pack();
//        //reInitAllSession
//        for (FrameHtmlData f: htmlDialogs) {
//            f.reInitAllSession(Resources.CONFIG_DATA, facade.getLanguage(), facade.getData(), facade.getData().getWebFiles());
//            f.pack();
//        }
//        if (battle != null) {
//            battle.closeWindows();
//        }
    }

    public void reinitComponents() {
        battle.setVisibleFrontBattle(false);
        scenePanel.reinit();
    }

    public void afterLoading() {
        pack();
//        for (FrameHtmlData f: htmlDialogs) {
//            f.reInitAllSession(Resources.CONFIG_DATA, facade.getLanguage(), facade.getData(), facade.getData().getWebFiles());
//            f.pack();
//        }
        if (battle != null) {
            battle.closeWindows();
        }
    }

    public void close() {
        closeConnexion();
        //LaunchingPokemon.decrement();
        dispose();
    }

    public void exitFromTrading() {
        setSavedGame(false);
        facade.closeTrading();
        scenePanel.exitInteraction();
        getZipLoad().setEnabledMenu(true);
        getGameLoad().setEnabledMenu(true);
        getNewGame().setEnabledMenu(true);
    }

//    public boolean loop(Object _readObject) {
//        if (_readObject instanceof IndexOfArriving) {
//            return true;
//        }
//        if (_readObject instanceof InitTrading) {
//            if (indexInGame == CustList.FIRST_INDEX) {
//                return true;
//            }
//            if (indexInGame == CustList.SECOND_INDEX) {
//                return true;
//            }
//            return false;
//        }
//        if (_readObject instanceof NetPokemon) {
//            return true;
//        }
//        if (_readObject instanceof PokemonPlayer) {
//            return true;
//        }
//        if (_readObject instanceof Ok) {
//            return true;
//        }
//        return false;
//    }

    @Override
    public void initIndexInGame(boolean _first) {
        if (_first) {
            setIndexInGame(CustList.FIRST_INDEX);
        }
    }

    @Override
    public void gearClient(Socket _newSocket) {
        Net.getSockets().put(Net.getSockets().size(), _newSocket);
        SendReceiveServer sendReceiveServer_=new SendReceiveServer(_newSocket,this);
        CustComponent.newThread(sendReceiveServer_).start();
        Net.getConnectionsServer().put(Net.getSockets().size()-1,sendReceiveServer_);
        IndexOfArriving index_ = new IndexOfArriving();
        index_.setIndex(Net.getSockets().size()-1);
        Net.getReadyPlayers().put(Net.getSockets().size()-1,false);
        Net.getPlacesPlayers().put(Net.getSockets().size()-1,(byte)(Net.getSockets().size()-1));
        Net.sendObject(_newSocket,index_);
    }

    @Override
    public void loop(Object _readObject, Socket _socket) {
        if (_readObject instanceof AttemptConnecting) {
            if (!StringList.quickEq(((AttemptConnecting)_readObject).getServerName(),Net.getPokemon())) {
                NewPlayer p_ = new NewPlayer();
                p_.setAcceptable(false);
                p_.setArriving(true);
                p_.setIndex(indexInGame);
                p_.setLanguage(getLanguageKey());
                p_.setPseudo(facade.getGame().getPlayer().getNickname());
                Net.sendObject(_socket,p_);
                return;
            }
        }
        if (_readObject instanceof IndexOfArriving) {
            NewPlayer p_ = new NewPlayer();
            p_.setAcceptable(true);
            p_.setArriving(true);
            p_.setIndex(indexInGame);
            //p_.setPseudo(pseudo());
            p_.setLanguage(getLanguageKey());
            p_.setPseudo(facade.getGame().getPlayer().getNickname());
            if (indexInGame == CustList.FIRST_INDEX) {
                scenePanel.setNetworkPanel();
            }
            pack();
            Net.sendObject(_socket,p_);
            return;
        }
        if (_readObject instanceof InitTrading) {
            if (indexInGame == CustList.FIRST_INDEX) {
                facade.initTrading();
                CheckCompatibility ch_ = new CheckCompatibility();
                ch_.setData(facade.getExchangeData());
                ch_.setIndex(indexInGame);
                ch_.setTeam(facade.getGame().getPlayer().getTeam());
                Net.sendObject(_socket,ch_);
                return;
            }
            if (indexInGame == CustList.SECOND_INDEX) {
                facade.initTrading();
                CheckCompatibility ch_ = new CheckCompatibility();
                ch_.setData(facade.getExchangeData());
                ch_.setIndex(indexInGame);
                ch_.setTeam(facade.getGame().getPlayer().getTeam());
                Net.sendObject(_socket,ch_);
                return;
            }
            return;
        }
        if (_readObject instanceof NetPokemon) {
            NetPokemon net_ = (NetPokemon) _readObject;
            if (indexInGame == CustList.SECOND_INDEX) {
                scenePanel.setNetworkPanel();
            }
            scenePanel.setTradable(net_.getTradablePokemon());
            pack();
            return;
        }
        if (_readObject instanceof PokemonPlayer) {
            PokemonPlayer pk_ = (PokemonPlayer) _readObject;
            facade.receivePokemonPlayer(pk_);
            scenePanel.seeNetPokemonDetail();
            return;
        }
        if (_readObject instanceof Ok) {
            facade.applyTrading();
            ByteTreeMap< PokemonPlayer> tree_ = facade.getExchangeData().getTeam(facade.getGame().getPlayer().getTeam());
            scenePanel.setTradableAfterTrading(tree_);
            pack();
            return;
        }
    }

    @Override
    public void quitNetwork(Exiting _exit, Socket _socket) {
        exitFromTrading();
        resetIndexInGame();
        try {
            closeConnexion();
            _socket.close();
        } catch (Exception _0) {
        }
        if (_exit.isClosing()) {
            dispose();
            return;
        }
        pack();
        if (_exit.isForced() && !_exit.isBusy()) {
            if (_exit.isTooManyPlayers()) {
                ConfirmDialog.showMessage(this, getTooManyString(), getTooManyString(), getLanguageKey(), JOptionPane.ERROR_MESSAGE);
                //JOptionPane.showMessageDialog(window, MainWindow.getTooManyString(), MainWindow.getTooManyString(), JOptionPane.INFORMATION_MESSAGE);
            } else {
                ConfirmDialog.showMessage(this, getNoTradeString(), getNoTradeString(), getLanguageKey(), JOptionPane.ERROR_MESSAGE);
                //JOptionPane.showMessageDialog(window, MainWindow.getNoTradeString(), MainWindow.getNoTradeString(), JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private int saving() {
        //warning message
        return confirm(messages.getVal(SAVING),messages.getVal(SAVING_TITLE));
    }

    private int confirm(String _message,String _titre) {
        //warning message
        return ConfirmDialog.getAnswer(this,_message,_titre, getLanguageKey(),JOptionPane.YES_NO_CANCEL_OPTION);
    }

    /**Sauvegarder une partie dans un fichier*/
    private String fileDialogSave() {
        boolean saveConfig_ = false;
        if (loadingConf.isSaveHomeFolder()) {
            saveConfig_ = true;
            FileSaveDialog.setFileSaveDialogByFrame(this, getLanguageKey(), true, Resources.GAME_EXT, ConstFiles.getHomePath());
        } else {
            FileSaveDialog.setFileSaveDialogByFrame(this, getLanguageKey(), true, Resources.GAME_EXT, DataBase.EMPTY_STRING, Resources.EXCLUDED);
        }
        String path_ = FileSaveDialog.getStaticSelectedPath();
        if (path_ == null) {
            path_ = DataBase.EMPTY_STRING;
        } else if (saveConfig_) {
            loadingConf.setLastSavedGame(path_);
            loadingConf.setLastRom(facade.getZipName());
            String configPath_ = StringList.replaceExtension(path_, Resources.GAME_EXT, Resources.CONF_EXT);
            //String configPath_ = path_.replaceAll(StringList.quote(Resources.GAME_EXT)+StringList.END_REG_EXP, Resources.CONF_EXT);
            StreamTextFile.saveTextFile(configPath_, DocumentWriterAikiCoreUtil.setLoadingGame(loadingConf));
            //configPath_ +=
        }
        return path_;
    }

    /**Sauvegarder une partie dans un fichier*/
    private String fileDialogLoad(String _ext, boolean _zipFile) {
        if (_zipFile) {
            if (loadingConf != null && loadingConf.isLoadHomeFolder()) {
                FileOpenDialog.setFileOpenDialog(this,getLanguageKey(),true, _ext, ConstFiles.getHomePath());
            } else {
                FileOpenDialog.setFileOpenDialog(this,getLanguageKey(),true, _ext, ConstFiles.getInitFolder(), Resources.EXCLUDED);
            }
//            FileOpenDialog.setFileOpenDialog(this,Constants.getLanguage(),true, _ext, SoftApplication.getFolderJarPath(), Resources.EXCLUDED);
        } else {
            if (loadingConf.isSaveHomeFolder()) {
                FileOpenDialog.setFileOpenDialog(this,getLanguageKey(),true, _ext, ConstFiles.getHomePath());
            } else {
                FileOpenDialog.setFileOpenDialog(this,getLanguageKey(),true, _ext, DataBase.EMPTY_STRING, Resources.EXCLUDED);
            }
        }
        String path_=FileOpenDialog.getStaticSelectedPath();
        if (path_ == null) {
            path_ = DataBase.EMPTY_STRING;
        }
        return path_;
    }

    public void setFight(boolean _pack, boolean _animate, boolean _wild) {
        difficulty.setEnabledMenu(false);
        facade.setChangeToFightScene(false);
        enabledMove = false;
        battle.setVisibleFrontBattle(true);
        scenePanel.setVisible(false);
//        mainPanel.remove(scenePanel);
        //initBattle();
        battle.enableAnimation(loadingConf.isEnableAnimation());
        battle.initializeFight(false);
        if (!_animate) {
            battle.repaint();
        }
//        mainPanel.add(battle, CustList.FIRST_INDEX);
        inBattle = true;
        dataBattle.setEnabledMenu(true);
        if (_pack) {
            pack();
        }
        if (loadingConf.isEnableAnimation() && _animate) {
            if (_wild) {
                fightIntroThread = new FightWildIntroThread(facade, battle.getBattle());
            } else {
                fightIntroThread = new FightTrainerIntroThread(facade, battle.getBattle());
            }
            fightIntroThreadLau = CustComponent.newThread(fightIntroThread);
            fightIntroThreadLau.start();
        } else {
            battle.setComments();
            battle.display();
        }
    }

    public void setComments() {
        battle.setComments();
    }

    public void interact() {
        difficulty.setEnabledMenu(true);
        scenePanel.interact();
    }

    private void initBattle() {
        battle = new FrontBattle(this, facade);
        battle.addMouseListener(new FrontClickEvent(battle));
    }

    public LoadingGame getLoadingConf() {
        return loadingConf;
    }

    public void setLoadingConf(LoadingGame _loadingConf, boolean _save) {
        loadingConf = _loadingConf;
        if (_save) {
            StreamTextFile.saveTextFile(StringList.concat(LaunchingPokemon.getTempFolderSl(),Resources.LOAD_CONFIG_FILE), DocumentWriterAikiCoreUtil.setLoadingGame(loadingConf));
        }
    }

    public void resetIndexInGame() {
        setIndexInGame(CustList.INDEX_NOT_FOUND_ELT);
    }

    public void setIndexInGame(byte _indexInGame) {
        indexInGame = _indexInGame;
    }

    public byte getIndexInGame() {
        return indexInGame;
    }

    public boolean isEnabledMove() {
        return enabledMove;
    }

    public void setEnabledMove(boolean _enabledMove) {
        enabledMove = _enabledMove;
    }

//    public boolean isStartThread() {
//        return startThread;
//    }

    public boolean isAliveThread() {
        return battle.isAliveThread() || fightIntroThreadLau != null && fightIntroThreadLau.isAlive();
    }

    public boolean isClickButtonsPad() {
        if (loadingConf == null) {
            return true;
        }
        return loadingConf.isClickButtonsPad();
    }

    public boolean isEnabledKeyPad() {
        if (loadingConf == null) {
            return false;
        }
        return loadingConf.isEnabledKeyPad();
    }

    public BasicClient getThreadEmission() {
        return threadEmission;
    }

    public void setThreadEmission(BasicClient _threadEmission) {
        threadEmission = _threadEmission;
    }

//    public Timer getTimer() {
//        return timer;
//    }
//
//    public void setTimer(Timer _timer) {
//        timer = _timer;
//    }

    public void setSavedGame(boolean _savedGame) {
        savedGame = _savedGame;
    }

    public boolean isInBattle() {
        return inBattle;
    }

//    public boolean isSuccessfulCompile() {
//        return successfulCompile;
//    }

//    public void setSuccessfulCompile(boolean _successfulCompile, boolean _display) {
//        if (_display) {
//            successfulCompile = _successfulCompile;
//            availableHelps.setText(messages.getVal(AVAILAIBLE_HELPS));
//            helpInfo.setText(messages.getVal(HELP_INFO));
//        } else {
//            availableHelps.setText(DataBase.EMPTY_STRING);
//            helpInfo.setText(DataBase.EMPTY_STRING);
//        }
//        pack();
////        availableHelps.repaint();
////        helpInfo.repaint();
////        scenePanel.repaintInteraction();
//        //revalidate();
//        //repaint();
//    }

    public void showFightData() {
        battle.showFightData();
    }

    public boolean showErrorMessageDialog(String _fileName) {
        if (_fileName.isEmpty()) {
            return false;
        }
        ConfirmDialog.showMessage(this, _fileName, messages.getVal(ERROR_LOADING), getLanguageKey(), JOptionPane.ERROR_MESSAGE);
        return true;
    }

    public void showSuccessfulMessageDialogThenLoadHelp(String _fileName) {
        ConfirmDialog.showMessage(this, _fileName, messages.getVal(SUCCESSFUL_LOADING), getLanguageKey(), JOptionPane.INFORMATION_MESSAGE);
        availableHelps.setText(messages.getVal(AVAILAIBLE_HELPS));
        helpInfo.setText(messages.getVal(HELP_INFO));
        pack();
//        SecurityManagerUtil.setForbiddenCalls(DataBase.getBeansPackage());
//        ForwardingJavaCompiler.startCompiling();
    }

    public boolean isPaintingScene() {
        return scenePanel.isPaintingScene();
    }

    public void setPaintingScene(boolean _paintingScene) {
//        difficulty.setEnabled(!_paintingScene);
        ThreadInvoker.invokeNow(new ChangeEnabledDifficulty(difficulty, !_paintingScene));
        ThreadInvoker.invokeNow(new PaintingScene(scenePanel, _paintingScene));
//        scenePanel.setPaintingScene(_paintingScene);
    }

    public void setNoPaintingScene() {
        scenePanel.setPaintingScene(false);
    }

    public boolean isAnimateMovingHero() {
        if (loadingConf == null) {
            return false;
        }
        return loadingConf.isEnableMovingHerosAnimation();
    }

//    @Override
//    public void setInfos(boolean _success, String _results) {
//        if (!_success) {
//            facade.initializeDefaultHtmlFiles();
//            successfulCompile = false;
//        } else {
//            facade.initializeHtmlFiles();
//            successfulCompile = true;
//        }
//        StreamTextFile.saveTextFile(LOG_FILE, _results);
//        CustComponent.invokeLater(new AfterCompiling(this, _success, true));
//    }

    public void setTextArea(String _text, int _messageType) {
        scenePanel.setTextArea(_text, _messageType);
    }

    public ProgressingDialogPokemon getDialog() {
        return dialog;
    }

    public MenuItem getZipLoad() {
        return zipLoad;
    }

    public MenuItem getGameLoad() {
        return gameLoad;
    }

    public MenuItem getNewGame() {
        return newGame;
    }

    public FacadeGame getFacade() {
        return facade;
    }
    public VideoLoading getVideoLoading() {
        return videoLoading;
    }

    @Override
    public String getApplicationName() {
        return LaunchingPokemon.getMainWindowClass();
    }

    @Override
    public String setObject(Object _object) {
        return DocumentWriterAikiMultiUtil.setObject(_object);
    }

    @Override
    public Object getObject(String _object) {
        return DocumentReaderAikiMultiUtil.getObject(_object);
    }

    public LoadFlag getLoadFlag() {
        return loadFlag;
    }
}
