package cards.gui.dialogs;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import cards.consts.GameType;
import cards.facade.Nicknames;
import cards.facade.enumerations.GameEnum;
import cards.gui.MainWindow;
import cards.gui.comboboxes.StringComboBox;
import cards.gui.dialogs.enums.SaveDealMode;
import cards.gui.dialogs.events.BackToRulesEvent;
import cards.gui.dialogs.events.ListenerClickCardsList;
import cards.gui.dialogs.events.MoveCardsEvent;
import cards.gui.dialogs.events.SavingDealEvent;
import cards.gui.dialogs.events.ValidateRulesDealEvent;
import cards.gui.panels.CardsScrollableList;
import cards.gui.panels.PresidentCardsScrollableList;
import cards.president.DealPresident;
import cards.president.DisplayingPresident;
import cards.president.GamePresident;
import cards.president.HandPresident;
import code.gui.ConfirmDialog;
import code.gui.FileSaveDialog;
import code.gui.LabelButton;
import code.maths.montecarlo.AbMonteCarlo;
import code.stream.StreamTextFile;
import code.util.CustList;
import code.util.EqList;
import code.util.Numbers;
import code.util.StringList;
import code.util.consts.ConstFiles;
import code.util.consts.Constants;

public final class EditorPresident extends DialogPresident implements SetterSelectedCardList {
    private static final String DIALOG_ACCESS = "cards.gui.dialogs.EditorPresident";

    private static final EditorPresident DIALOG = new EditorPresident();
    private static final String BACK = "back";
    private static final String DEALER = "dealer";
    private static final String DEALING_CARDS = "dealingCards";
    private static final String DEALING_STACK = "dealingStack";
    private static final String ERROR_MOVE = "errorMove";
    private static final String ERROR_MOVE_TITLE = "errorMoveTitle";
    private static final String ERROR_REPARTITION = "errorRepartition";
    private static final String ERROR_REPARTITION_TITLE = "errorRepartitionTitle";
    private static final String ERROR_SAVE_FILE = "errorSaveFile";
    private static final String ERROR_SAVE_FILE_TITLE = "errorSaveFileTitle";
    private static final String MOVE_CARDS = "moveCards";
    private static final String NEXT = "next";
    private static final String PLAY_WITHOUT_SAVING = "playWithoutSaving";
    private static final String PLAYER_HAND = "playerHand";
    private static final String RANDOM = "random";
//    private static final String REMAINING = "remaining";
    private static final String SAVE_THEN_CLOSE = "saveThenClose";
    private static final String SAVE_THEN_PLAY = "saveThenPlay";
    private static final String SAVE_WITHOUT_CLOSING = "saveWithoutClosing";
    private static final String SELECTED_CARDS = "selectedCards";
    private static final String USER_HAND = "userHand";
    private static final String EMPTY_STRING = "";
    private boolean partieSauvegardee;
    private GamePresident partie;
    private int nombreCartesSelectionnees;
    private JPanel panelsCards;
    private PresidentCardsScrollableList stack;
    private CustList<PresidentCardsScrollableList> hands = new CustList<PresidentCardsScrollableList>();
    private JLabel labelSelectCards;
    private int nombreCartesSelectionneesPrecedent;
    private StringComboBox liste;
    private Nicknames nickNames;
    private StringComboBox listeTwo;
    private DisplayingPresident displayingPresident = new DisplayingPresident();
    private MainWindow window;
    private boolean setToNullGame;

    private EditorPresident() {
        setAccessFile(DIALOG_ACCESS);
    }

    public static void initEditorPresident(MainWindow _fenetre) {
        DIALOG.setDialogIcon(_fenetre);
        DIALOG.setTitle(GameEnum.PRESIDENT.toString());
        DIALOG.setReglesPresident(_fenetre.getReglesPresident());
        DIALOG.partie = null;
        DIALOG.setToNullGame = true;
        DIALOG.nombreCartesSelectionneesPrecedent = 0;
        DIALOG.nombreCartesSelectionnees = 0;
        DIALOG.partieSauvegardee = false;
        DIALOG.window = _fenetre;
        DIALOG.setLocationRelativeTo(_fenetre);
        DIALOG.nickNames = _fenetre.getPseudosJoueurs();
        DIALOG.displayingPresident = _fenetre.getDisplayingPresident();
        DIALOG.setDialogue(true, 0);
    }

    @Override
    public void closeWindow() {
        super.closeWindow();
        if (setToNullGame) {
            partie = null;
        }
    }

    @Override
    public String sauvegarder() {
        if(stack.taille()==0) {
            return validerEgalite();
        }
        return null;
    }

    @Override
    public void releverErreurs() {
        erreur(stack);
    }

    @Override
    public void setDialogue(boolean _enabledChangingNbPlayers, int _nbPlayers) {
        getJt().removeAll();
        Container container_=new Container();
        container_.setLayout(new BorderLayout());
        initMessageName();
        Numbers<Integer> decks_ = new Numbers<Integer>();
        //Panneau Distribution
        for(int b=FileConst.MIN_DEALS;b<=FileConst.MAX_DEALS;b++) {
            decks_.add(b);
        }
        initJt(new JSpinner(new SpinnerListModel(decks_.toArray())),_enabledChangingNbPlayers,_nbPlayers);
        container_.add(getJt(),BorderLayout.CENTER);
        JPanel panneau_=new JPanel();
        LabelButton bouton_=new LabelButton(getMessages().getVal(NEXT));
        bouton_.addMouseListener(new ValidateRulesDealEvent(this));
        panneau_.add(bouton_);
        container_.add(panneau_,BorderLayout.SOUTH);
        setContentPane(container_);
        pack();
    }

    @Override
    public void validateRulesDeal() {
        validateRules();
        getReglesPresident().setNbDeals((Integer)getNbGames().getValue());
        distribuer();
    }

    private void distribuer() {
        setTitle(getMessages().getVal(DEALING_CARDS));
        Container c=new Container();
        c.setLayout(new BorderLayout());
        JPanel panneau_=new JPanel();
        byte nbCartesPJ_;

        int nbCards_ = getReglesPresident().getNbStacks() * HandPresident.pileBase().total();
        int nbPlayers_ = getReglesPresident().getNbPlayers();
        int rem_ = nbCards_ % nbPlayers_;
        boolean noRem_ = rem_ == 0;
        int nbCardsPerPlayer_ = nbCards_ / nbPlayers_;
        if (noRem_) {
            nbCartesPJ_ = (byte) nbCardsPerPlayer_;
        } else {
            nbCartesPJ_ = (byte) (nbCardsPerPlayer_ + 1);
        }

        int nbStacks_ = getReglesPresident().getNbStacks();
        HandPresident pile_= HandPresident.stack(nbStacks_);
//        for (int i = List.FIRST_INDEX; i < nbStacks_; i++) {
//            pile_.ajouterCartes(HandPresident.pileBase());
//        }
        panneau_.add(new JLabel(getMessages().getVal(DEALER)));
        liste=new StringComboBox();
        liste.addItem(nickNames.getPseudo());
        for(String n: nickNames.getPseudosPresident()) {
            if (liste.getItemCount() == nbPlayers_) {
                break;
            }
            liste.addItem(n);
        }
        liste.addItem(getMessages().getVal(RANDOM));
        panneau_.add(liste);
        c.add(panneau_,BorderLayout.NORTH);
        pile_.sortCards(displayingPresident.getDecroissant(), false);
        PresidentCardsScrollableList plc_=new PresidentCardsScrollableList(nbCartesPJ_,pile_.total(),getMessages().getVal(DEALING_STACK));
        plc_.setTriPresident(displayingPresident.getCouleurs(), displayingPresident.getDecroissant());
        plc_.iniPilePresident(pile_);
        plc_.initSelectionCartePresident();
        plc_.getListe().addListSelectionListener(new ListenerClickCardsList(getMessages().getVal(SELECTED_CARDS), this));
        panelsCards=new JPanel();
        stack = plc_;
        panelsCards.add(plc_);
        plc_=new PresidentCardsScrollableList(nbCartesPJ_,nbCartesPJ_,getMessages().getVal(USER_HAND));
        plc_.initSelectionCartePresident();
        plc_.getListe().addListSelectionListener(new ListenerClickCardsList(getMessages().getVal(SELECTED_CARDS), this));
        plc_.setTriPresident(displayingPresident.getCouleurs(), displayingPresident.getDecroissant());
        panelsCards.add(plc_);
        hands.clear();
        hands.add(plc_);
//        int i_=0;
        int h_ = 10*(nbCartesPJ_+6);
        for(String n: nickNames.getPseudosPresident()) {
            if (hands.size() == nbPlayers_) {
                break;
            }
//            if (i_ == nbJ_ - 1) {
//                break;
//            }
            String message_ = getMessages().getVal(PLAYER_HAND);
            message_ = StringList.simpleStringsFormat(message_, n);
            plc_=new PresidentCardsScrollableList(nbCartesPJ_,nbCartesPJ_,message_);
            plc_.initSelectionCartePresident();
            plc_.getListe().addListSelectionListener(new ListenerClickCardsList(getMessages().getVal(SELECTED_CARDS), this));
            plc_.setTriPresident(displayingPresident.getCouleurs(), displayingPresident.getDecroissant());
            panelsCards.add(plc_);
            hands.add(plc_);
//            i_++;
        }
        JScrollPane scroll_ = new JScrollPane(panelsCards);
        scroll_.setPreferredSize(new Dimension(500, h_));
        panneau_=new JPanel();
        panneau_.setLayout(new BorderLayout());
        panneau_.add(scroll_,BorderLayout.CENTER);
        JPanel sousPanneau_=new JPanel();
        LabelButton bouton_=new LabelButton(getMessages().getVal(MOVE_CARDS));
        bouton_.addMouseListener(new MoveCardsEvent(this));
        sousPanneau_.add(bouton_);
        listeTwo=new StringComboBox();
        listeTwo.addItem(getMessages().getVal(DEALING_STACK));
        listeTwo.addItem(getMessages().getVal(USER_HAND));
        for(String n: nickNames.getPseudosPresident()) {
            if (listeTwo.getItemCount() == getReglesPresident().getNbPlayers() + 1) {
                break;
            }
            String message_ = getMessages().getVal(PLAYER_HAND);
            message_ = StringList.simpleStringsFormat(message_, n);
            listeTwo.addItem(message_);
        }
        sousPanneau_.add(listeTwo);
        labelSelectCards = new JLabel(StringList.simpleNumberFormat(getMessages().getVal(SELECTED_CARDS),nombreCartesSelectionnees));
        sousPanneau_.add(labelSelectCards);
        panneau_.add(sousPanneau_,BorderLayout.SOUTH);
        c.add(panneau_,BorderLayout.CENTER);

        panneau_=new JPanel();
        bouton_=new LabelButton(getMessages().getVal(BACK));
        bouton_.addMouseListener(new BackToRulesEvent(this));
        panneau_.add(bouton_);
        bouton_=new LabelButton(getMessages().getVal(SAVE_WITHOUT_CLOSING));
        bouton_.addMouseListener(new SavingDealEvent(this, SaveDealMode.SAVE_WITHOUT_CLOSING));
        panneau_.add(bouton_);
        bouton_=new LabelButton(getMessages().getVal(SAVE_THEN_PLAY));
        bouton_.addMouseListener(new SavingDealEvent(this, SaveDealMode.SAVE_THEN_PLAY));
        panneau_.add(bouton_);
        bouton_=new LabelButton(getMessages().getVal(PLAY_WITHOUT_SAVING));
        bouton_.addMouseListener(new SavingDealEvent(this, SaveDealMode.PLAY_WITHOUT_SAVING));
        panneau_.add(bouton_);
        bouton_=new LabelButton(getMessages().getVal(SAVE_THEN_CLOSE));
        bouton_.addMouseListener(new SavingDealEvent(this, SaveDealMode.SAVE_THEN_CLOSE));
        panneau_.add(bouton_);
        c.add(panneau_,BorderLayout.SOUTH);
        setContentPane(c);
        pack();

    }

    @Override
    public void backToRules() {
        nombreCartesSelectionneesPrecedent=0;
        nombreCartesSelectionnees = 0;
        partieSauvegardee=false;
        setDialogue(true,0);
    }

    private void erreur(PresidentCardsScrollableList _plc) {
        String mes_ = getMessages().getVal(ERROR_REPARTITION);
        mes_ = StringList.simpleNumberFormat(mes_, _plc.taille());
        ConfirmDialog.showMessage(this, mes_, getMessages().getVal(ERROR_REPARTITION_TITLE), Constants.getLanguage(), JOptionPane.ERROR_MESSAGE);
        //JOptionPane.showMessageDialog(this,mes_,getMessages().getVal(ERROR_REPARTITION_TITLE), JOptionPane.ERROR_MESSAGE);
    }

    @Override
    public void cancelDeal() {
        partie = null;
    }

    @Override
    public void setPartie() {
        int nombreDeJoueurs_;

        EqList<HandPresident> mains_=new EqList<HandPresident>();
        CustList<CardsScrollableList> hands_ = getHands(false);
        for(CardsScrollableList l: hands_) {
            HandPresident m=new HandPresident();
            m.ajouterCartes(((PresidentCardsScrollableList) l).valMainPresident());
            m.sortCards(displayingPresident.getDecroissant(), false);
            mains_.add(m);
        }
        nombreDeJoueurs_=hands_.size();
        byte donneur_ = (byte) liste.getSelectedIndex();
        if (donneur_ == nombreDeJoueurs_) {
//            donneur_=(byte)Math.floor(nombreDeJoueurs_*MonteCarlo.randomDouble());
            donneur_=(byte)AbMonteCarlo.randomInt(nombreDeJoueurs_);
        }
        DealPresident donne_=new DealPresident(mains_,donneur_);
        partie = new GamePresident(GameType.EDIT,donne_,getReglesPresident(), new Numbers<Byte>());
    }

    private String validerEgalite() {
        if (window.isSaveHomeFolder()) {
            FileSaveDialog.setFileSaveDialog(this, Constants.getLanguage(), true, FileConst.GAME_EXT, ConstFiles.getHomePath(), FileConst.EXCLUDED);
        } else {
            FileSaveDialog.setFileSaveDialog(this, Constants.getLanguage(), true, FileConst.GAME_EXT, EMPTY_STRING, FileConst.EXCLUDED);
        }
        String fichier_=FileSaveDialog.getStaticSelectedPath();
        if (fichier_ == null) {
            fichier_ = EMPTY_STRING;
        }
        if(!fichier_.isEmpty()) {
            validerSauvegarde(fichier_);
        }
        return fichier_;
    }

    /**Lorsqu'on veut sauvegarder une partie*/
    private void validerSauvegarde(String _s) {
        StreamTextFile.saveObject(_s, partie);
    }

    @Override
    public void deplacerCartes() {
        HandPresident m=new HandPresident();
        for (CardsScrollableList l: getHands(true)) {
            PresidentCardsScrollableList c_ = (PresidentCardsScrollableList) l;
            HandPresident cartesSelectionnees_= c_.getCartesPresidentSelectionnees();
            m.ajouterCartes(cartesSelectionnees_);
        }
        int numero_= listeTwo.getSelectedIndex();
        PresidentCardsScrollableList panneauSelectionne_=(PresidentCardsScrollableList) getHands(true).get(numero_);
        int taille_=panneauSelectionne_.taille();
        int max_=panneauSelectionne_.getMax();
        if(taille_+m.total()<max_+1) {
            for (CardsScrollableList l: getHands(true)) {
                PresidentCardsScrollableList c_ = (PresidentCardsScrollableList) l;
                HandPresident cartesSelectionnees_= c_.getCartesPresidentSelectionnees();
                c_.supprimerCartesPresident(cartesSelectionnees_);
            }
            panneauSelectionne_.ajouterCartesPresident(m);
            nombreCartesSelectionnees=0;
        } else {
            String mes_ = getMessages().getVal(ERROR_MOVE);
            mes_ = StringList.simpleStringsFormat(mes_, Long.toString(m.total()), Long.toString(max_-taille_), listeTwo.getSelectedComboItem());
            ConfirmDialog.showMessage(this, mes_, getMessages().getVal(ERROR_MOVE_TITLE), Constants.getLanguage(), JOptionPane.ERROR_MESSAGE);
        }
    }

    public static GamePresident getPartie() {
        DIALOG.setVisible(true);
        return DIALOG.partie;
    }

    @Override
    public void doNotSetToNullGame() {
        setToNullGame = false;
    }

    @Override
    public String getErrorSaveMessage() {
        return getMessages().getVal(ERROR_SAVE_FILE);
    }

    @Override
    public String getErrorSaveTitle() {
        return getMessages().getVal(ERROR_SAVE_FILE_TITLE);
    }

    @Override
    public boolean isPartieSauvegardee() {
        return partieSauvegardee;
    }

    @Override
    public void setPartieSauvegardee(boolean _partieSauvegardee) {
        partieSauvegardee = _partieSauvegardee;
    }

    @Override
    public int getNombreCartesSelectionnees() {
        return nombreCartesSelectionnees;
    }

    @Override
    public void setNombreCartesSelectionnees(int _nombreCartesSelectionnees) {
        nombreCartesSelectionnees = _nombreCartesSelectionnees;
    }

    @Override
    public int getNombreCartesSelectionneesPrecedent() {
        return nombreCartesSelectionneesPrecedent;
    }

    @Override
    public void setNombreCartesSelectionneesPrecedent(
            int _nombreCartesSelectionneesPrecedent) {
        nombreCartesSelectionneesPrecedent = _nombreCartesSelectionneesPrecedent;
    }

    @Override
    public JPanel getPanelsCards() {
        return panelsCards;
    }

    @Override
    public JLabel getLabelSelectCards() {
        return labelSelectCards;
    }

    @Override
    public CustList<CardsScrollableList> getHands(boolean _addStack) {
        CustList<CardsScrollableList> hands_;
        hands_ = new CustList<CardsScrollableList>();
        if (_addStack) {
            hands_.add(stack);
        }
        for (CardsScrollableList c: hands) {
            hands_.add(c);
        }
        return hands_;
    }
}
