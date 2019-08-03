package cards.gui.dialogs;
import java.awt.BorderLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import cards.belote.DealBelote;
import cards.belote.DisplayingBelote;
import cards.belote.GameBelote;
import cards.belote.HandBelote;
import cards.belote.sml.DocumentWriterBeloteUtil;
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
import cards.gui.panels.BeloteCardsScrollableList;
import cards.gui.panels.CardsScrollableList;
import code.gui.*;
import code.maths.montecarlo.AbMonteCarlo;
import code.stream.StreamTextFile;
import code.util.CustList;
import code.util.EqList;
import code.util.Ints;
import code.util.StringList;

public final class EditorBelote extends DialogBelote implements SetterSelectedCardList{

    private static final String DIALOG_ACCESS = "cards.gui.dialogs.editorbelote";
    private static final EditorBelote DIALOG = new EditorBelote();
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
    private static final String REMAINING = "remaining";
    private static final String SAVE_THEN_CLOSE = "saveThenClose";
    private static final String SAVE_THEN_PLAY = "saveThenPlay";
    private static final String SAVE_WITHOUT_CLOSING = "saveWithoutClosing";
    private static final String SELECTED_CARDS = "selectedCards";
    private static final String USER_HAND = "userHand";
    private static final String EMPTY_STRING = "";
    private boolean partieSauvegardee;
    private GameBelote partie;
    private int nombreCartesSelectionnees;
    private Panel panelsCards;
    private BeloteCardsScrollableList stack;
    private CustList<BeloteCardsScrollableList> hands = new CustList<BeloteCardsScrollableList>();
    private BeloteCardsScrollableList remaining;
    private TextLabel labelSelectCards;
    private int nombreCartesSelectionneesPrecedent;
    private StringComboBox liste;
    private Nicknames nickNames;
    private StringComboBox listeTwo;
    private DisplayingBelote displayingBelote = new DisplayingBelote();
    private MainWindow window;
    private boolean setToNullGame;

    private EditorBelote() {
        setAccessFile(DIALOG_ACCESS);
    }
    public static void initEditorBelote(MainWindow _fenetre) {
        //super(GameEnum.BELOTE.toString(),_fenetre,_fenetre.getReglesBelote());
        String lg_ = _fenetre.getLanguageKey();
        DIALOG.setMain(_fenetre);
        DIALOG.setDialogIcon(_fenetre);
        DIALOG.setTitle(GameEnum.BELOTE.toString(lg_));
        DIALOG.setReglesBelote(_fenetre.getReglesBelote());
        DIALOG.partie = null;
        DIALOG.setToNullGame = true;
        DIALOG.nombreCartesSelectionneesPrecedent = 0;
        DIALOG.nombreCartesSelectionnees = 0;
        DIALOG.partieSauvegardee = false;
        DIALOG.window = _fenetre;
        DIALOG.setLocationRelativeTo(_fenetre);
        DIALOG.nickNames = _fenetre.getPseudosJoueurs();
        DIALOG.displayingBelote = _fenetre.getDisplayingBelote();
        DIALOG.setDialogue(_fenetre);
//        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
//        addWindowListener(new WindowAdapter() {
//            @Override
//            public void windowClosing(WindowEvent _e) {
//                partie = null;
//                dispose();
//            }
//        });
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
//        erreur((BeloteCardsScrollableList)panelsCards.getComponent(0));
        erreur(stack);
    }
    @Override
    public void setDialogue(MainWindow _parent) {
        getJt().removeAll();
        Panel container_=Panel.newBorder();
        initMessageName(_parent);
        Ints decks_ = new Ints();
        //Panneau Distribution
        for(int b=FileConst.MIN_DEALS;b<=FileConst.MAX_DEALS;b++) {
            decks_.add(b);
        }
        String lg_ = _parent.getLanguageKey();
        initJt(new Spinner(new SpinnerListModel(decks_.toArray())),lg_);
        container_.add(getJt(),BorderLayout.CENTER);
        Panel panneau_=new Panel();
        LabelButton bouton_=new LabelButton(getMessages().getVal(NEXT));
        bouton_.addMouseListener(new ValidateRulesDealEvent(this, _parent));
        panneau_.add(bouton_);
        container_.add(panneau_,BorderLayout.SOUTH);
        setContentPane(container_);
        pack();
    }
    @Override
    public void validateRulesDeal(MainWindow _parent) {
        validateRules();
        getReglesBelote().setNombreParties((Integer) getNbGames().getValue());
        distribuer(_parent);
    }
    private String validerEgalite() {
        String lg_ = window.getLanguageKey();
        if (window.isSaveHomeFolder()) {
            FileSaveDialog.setFileSaveDialog(window,this, lg_, true, FileConst.GAME_EXT, ConstFiles.getHomePath(), FileConst.EXCLUDED);
        } else {
            FileSaveDialog.setFileSaveDialog(window,this, lg_, true, FileConst.GAME_EXT, EMPTY_STRING, FileConst.EXCLUDED);
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
    public static GameBelote getPartie() {
        DIALOG.setVisible(true);
        return DIALOG.partie;
    }

    private void distribuer(MainWindow _parent) {
        setTitle(getMessages().getVal(DEALING_CARDS));
        Panel c=Panel.newBorder();
        Panel panneau_=new Panel();
        panneau_.add(new TextLabel(getMessages().getVal(DEALER)));
        liste=new StringComboBox();
        liste.addItem(nickNames.getPseudo());
        int nbPlayers_ = getReglesBelote().getRepartition().getNombreJoueurs();
        for(String n: nickNames.getPseudosBelote()) {
            if (liste.getItemCount() == nbPlayers_) {
                break;
            }
            liste.addItem(n);
        }
        liste.addItem(getMessages().getVal(RANDOM));
        panneau_.add(liste);
        c.add(panneau_,BorderLayout.NORTH);
        panneau_=Panel.newBorder();
        panelsCards=new Panel();
        HandBelote pile_=HandBelote.pileBase();
        pile_.trier(displayingBelote.getSuits(), displayingBelote.isDecreasing(), displayingBelote.getOrderBeforeBids());
        BeloteCardsScrollableList plc_=new BeloteCardsScrollableList(12,pile_.total(),getMessages().getVal(DEALING_STACK));
        plc_.initSelectionCarteBelote(_parent);
        plc_.setTriBelote(displayingBelote.getSuits(), displayingBelote.getOrderBeforeBids(), displayingBelote.isDecreasing());
        plc_.iniPileBelote(pile_);
        plc_.getListe().setListener(new ListenerClickCardsList(getMessages().getVal(SELECTED_CARDS), this));
        stack = plc_;
        panelsCards.add(plc_.getContainer());
//        hands.add(plc_);
        int firstCards_ = getReglesBelote().getRepartition().getFirstCards();
        int lastCards_ = getReglesBelote().getRepartition().getRemainingCards();
        plc_=new BeloteCardsScrollableList(firstCards_,firstCards_,getMessages().getVal(USER_HAND));
        plc_.initSelectionCarteBelote(_parent);
        plc_.getListe().setListener(new ListenerClickCardsList(getMessages().getVal(SELECTED_CARDS), this));
        plc_.setTriBelote(displayingBelote.getSuits(), displayingBelote.getOrderBeforeBids(), displayingBelote.isDecreasing());
        panelsCards.add(plc_.getContainer());
        hands.clear();
        hands.add(plc_);
//        int i_=0;
//        int nbPlayers_ = getReglesBelote().getRepartition().getNombreJoueurs();
        for(String n: nickNames.getPseudosBelote()) {
            if (hands.size() == nbPlayers_) {
                break;
            }
//            if (i_ == nbPlayers_ - 1) {
//                break;
//            }
            String message_ = getMessages().getVal(PLAYER_HAND);
            message_ = StringList.simpleStringsFormat(message_, n);
            plc_=new BeloteCardsScrollableList(firstCards_,firstCards_,message_);
            plc_.initSelectionCarteBelote(_parent);
            plc_.getListe().setListener(new ListenerClickCardsList(getMessages().getVal(SELECTED_CARDS), this));
            plc_.setTriBelote(displayingBelote.getSuits(), displayingBelote.getOrderBeforeBids(), displayingBelote.isDecreasing());
            panelsCards.add(plc_.getContainer());
            hands.add(plc_);
//            i_++;
        }
        plc_=new BeloteCardsScrollableList(lastCards_,lastCards_,getMessages().getVal(REMAINING));
        plc_.initSelectionCarteBelote(_parent);
        plc_.getListe().setListener(new ListenerClickCardsList(getMessages().getVal(SELECTED_CARDS), this));
        panelsCards.add(plc_.getContainer());
        remaining = plc_;
        panneau_.add(panelsCards,BorderLayout.CENTER);
        Panel sousPanneau_=new Panel();
        LabelButton bouton_=new LabelButton(getMessages().getVal(MOVE_CARDS));
        bouton_.addMouseListener(new MoveCardsEvent(this));
        sousPanneau_.add(bouton_);
        listeTwo=new StringComboBox();
        listeTwo.addItem(getMessages().getVal(DEALING_STACK));
        listeTwo.addItem(getMessages().getVal(USER_HAND));
        for(String n: nickNames.getPseudosBelote()) {
            if (listeTwo.getItemCount() == getReglesBelote().getRepartition().getNombreJoueurs() + 1) {
                break;
            }
            String message_ = getMessages().getVal(PLAYER_HAND);
            message_ = StringList.simpleStringsFormat(message_, n);
            listeTwo.addItem(message_);
        }
        listeTwo.addItem(getMessages().getVal(REMAINING));
        sousPanneau_.add(listeTwo);
        labelSelectCards = new TextLabel(StringList.simpleNumberFormat(getMessages().getVal(SELECTED_CARDS),nombreCartesSelectionnees));
        sousPanneau_.add(labelSelectCards);
        panneau_.add(sousPanneau_,BorderLayout.SOUTH);
        c.add(panneau_,BorderLayout.CENTER);
        panneau_=new Panel();
        bouton_=new LabelButton(getMessages().getVal(BACK));
        bouton_.addMouseListener(new BackToRulesEvent(this, _parent));
        panneau_.add(bouton_);
        bouton_=new LabelButton(getMessages().getVal(SAVE_WITHOUT_CLOSING));
        bouton_.addMouseListener(new SavingDealEvent(this, SaveDealMode.SAVE_WITHOUT_CLOSING, _parent));
        panneau_.add(bouton_);
        bouton_=new LabelButton(getMessages().getVal(SAVE_THEN_PLAY));
        bouton_.addMouseListener(new SavingDealEvent(this, SaveDealMode.SAVE_THEN_PLAY, _parent));
        panneau_.add(bouton_);
        bouton_=new LabelButton(getMessages().getVal(PLAY_WITHOUT_SAVING));
        bouton_.addMouseListener(new SavingDealEvent(this, SaveDealMode.PLAY_WITHOUT_SAVING, _parent));
        panneau_.add(bouton_);
        bouton_=new LabelButton(getMessages().getVal(SAVE_THEN_CLOSE));
        bouton_.addMouseListener(new SavingDealEvent(this, SaveDealMode.SAVE_THEN_CLOSE, _parent));
        panneau_.add(bouton_);
        c.add(panneau_,BorderLayout.SOUTH);
        setContentPane(c);
        pack();
    }
    @Override
    public void backToRules(MainWindow _parent) {
        nombreCartesSelectionnees=0;
        nombreCartesSelectionneesPrecedent=0;
        partieSauvegardee=false;
        setDialogue(_parent);
    }
    @Override
    public void cancelDeal() {
        partie = null;
    }
    @Override
    public void setPartie() {
//        BeloteCardsScrollableList plc_;
//        int nombreDeMains_=panelsCards.getComponentCount();
        int nombreDeJoueurs_;

        CustList<HandBelote> mains_=new CustList<HandBelote>();
        for (BeloteCardsScrollableList l: hands) {
            HandBelote m=new HandBelote();
            m.ajouterCartes(l.valMainBelote());
            m.setOrdre(displayingBelote.getOrderBeforeBids());
            m.trier(displayingBelote.getSuits(), displayingBelote.isDecreasing(), displayingBelote.getOrderBeforeBids());
            mains_.add(m);
        }
        HandBelote m=new HandBelote();
        m.ajouterCartes(remaining.valMainBelote());
        m.setOrdre(displayingBelote.getOrderBeforeBids());
        mains_.add(m);
//        for(int i=1;i<nombreDeMains_;i++) {
//            plc_=(BeloteCardsScrollableList)panelsCards.getComponent(i);
//            HandBelote m=new HandBelote();
//            m.ajouterCartes(plc_.valMainBelote());
//            m.setOrdre(displayingBelote.getOrdreAvantEncheres());
//            if(i<nombreDeMains_-1) {//On trie toutes les mains sauf le talon car l'ordre des cartes au talon est important
//                m.trier(displayingBelote.getCouleurs(), displayingBelote.getDecroissant(), displayingBelote.getOrdreAvantEncheres());
//            }
//            mains_.add(m);
//        }
//        nombreDeJoueurs_=nombreDeMains_-1;
        nombreDeJoueurs_=getHands(false).size();
        byte donneur_ = (byte) liste.getSelectedIndex();
        if (donneur_ == nombreDeJoueurs_) {
//          donneur_=(byte)Math.floor(nombreDeJoueurs_*MonteCarlo.randomDouble());
            donneur_=(byte)AbMonteCarlo.randomLong(nombreDeJoueurs_);
        }
        DealBelote donne_=new DealBelote(mains_,donneur_);
        partie = new GameBelote(GameType.EDIT,donne_,getReglesBelote());

    }
    /**Lorsqu'on veut sauvegarder une partie*/
    private void validerSauvegarde(String _s) {
        StreamTextFile.saveTextFile(_s, DocumentWriterBeloteUtil.setGameBelote(partie));
    }
    private void erreur(BeloteCardsScrollableList _plc) {
        String lg_ = getMain().getLanguageKey();
        String mes_ = getMessages().getVal(ERROR_REPARTITION);
        mes_ = StringList.simpleNumberFormat(mes_, _plc.taille());
        ConfirmDialog.showMessage(this, mes_, getMessages().getVal(ERROR_REPARTITION_TITLE), lg_, JOptionPane.ERROR_MESSAGE);
        //JOptionPane.showMessageDialog(this,mes_,getMessages().getVal(ERROR_REPARTITION_TITLE), JOptionPane.ERROR_MESSAGE);
    }
    @Override
    public void deplacerCartes() {
        String lg_ = getMain().getLanguageKey();
//        int nombreDeMains_=panelsCards.getComponentCount();

        HandBelote m=new HandBelote(displayingBelote.getOrderBeforeBids());
//        for (int i = List.FIRST_INDEX;i<nombreDeMains_;i++) {
//            HandBelote cartesSelectionnees_=((BeloteCardsScrollableList)panelsCards.getComponent(i)).getCartesBeloteSelectionnees();
//            m.ajouterCartes(cartesSelectionnees_);
//        }
        for (CardsScrollableList l: getHands(true)) {
            BeloteCardsScrollableList c_ = (BeloteCardsScrollableList) l;
            HandBelote cartesSelectionnees_= c_.getCartesBeloteSelectionnees();
            m.ajouterCartes(cartesSelectionnees_);
        }
        int numero_=listeTwo.getSelectedIndex();
        BeloteCardsScrollableList panneauSelectionne_=(BeloteCardsScrollableList) getHands(true).get(numero_);
        //(BeloteCardsScrollableList)panelsCards.getComponent(numero_);
//        BeloteCardsScrollableList panneau2_;
        int taille_= panneauSelectionne_.taille();
        int max_=panneauSelectionne_.getMax();
        if(taille_+m.total()<max_+1) {
//            for (int i = List.FIRST_INDEX;i<nombreDeMains_;i++) {
//                panneau2_=(BeloteCardsScrollableList)panelsCards.getComponent(i);
//                HandBelote cartesSelectionnees_=panneau2_.getCartesBeloteSelectionnees();
//                panneau2_.supprimerCartesBelote(cartesSelectionnees_);
//            }
            for (CardsScrollableList l: getHands(true)) {
                BeloteCardsScrollableList c_ = (BeloteCardsScrollableList) l;
                HandBelote cartesSelectionnees_=c_.getCartesBeloteSelectionnees();
                c_.supprimerCartesBelote(cartesSelectionnees_);
            }
            if(numero_ != getHands(false).size()) {
                panneauSelectionne_.ajouterCartesBelote(m);
            } else {
                panneauSelectionne_.ajouterCartesBeloteFin(m);
            }
            nombreCartesSelectionnees=0;
        } else {
            String mes_ = getMessages().getVal(ERROR_MOVE);
            mes_ = StringList.simpleStringsFormat(mes_, Long.toString(m.total()), Long.toString(max_-taille_), listeTwo.getSelectedComboItem());
            ConfirmDialog.showMessage(this, mes_, getMessages().getVal(ERROR_MOVE_TITLE), lg_, JOptionPane.ERROR_MESSAGE);
            //JOptionPane.showMessageDialog(this,mes_, getMessages().getVal(ERROR_MOVE_TITLE), JOptionPane.ERROR_MESSAGE);
        }

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
    public Panel getPanelsCards() {
        return panelsCards;
    }
    @Override
    public TextLabel getLabelSelectCards() {
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
        hands_.add(remaining);
        return hands_;
    }
}
