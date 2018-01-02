package cards.gui.containers;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import cards.facade.enumerations.GameEnum;
import cards.gui.MainWindow;
import cards.gui.animations.LoadingVideo;
import cards.gui.dialogs.FileConst;
import cards.gui.labels.GraphicPresidentCard;
import cards.gui.panels.CarpetPresident;
import cards.main.LaunchingCards;
import cards.president.HandPresident;
import cards.president.enumerations.CardPresident;
import code.gui.LabelButton;
import code.stream.StreamTextFile;
import code.util.CustList;
import code.util.Numbers;
import code.util.StringList;

public class ContainerPresident extends ContainerGame {

    public static final String EMPTY="";
    public static final String TAB="\t";

    private JPanel panelReceivedCards;
    private JPanel panelGivenCards;

    /**Renvoie tous les scores de toutes les parties non solitaires*/
    private CustList<Numbers<Long>> scores=new CustList<Numbers<Long>>();
    /**Maximum des valeurs absolues des scores centr&eacute;s par rapport &agrave; la moyenne*/
    private long maxAbsoluScore;
    /**Est vrai si et seulement si une partie vient d'etre sauvegardee*/
    private boolean partieSauvegardee;
    /**Vrai si et seulement si au moins une partie aleatoire a ete jouee depuis le dernier passage dans le menu principal*/
    private boolean partieAleatoireJouee;
    private LoadingVideo animChargement;

    private volatile boolean arretDemo;

    private boolean canDiscard;
    private boolean canPlay;

    private LabelButton noPlay;
    private LabelButton givingCardsOk;

    private CardPresident carteSurvoleePresident;

    private byte indexCard;

    private int nbGivenCards;

    private HandPresident givenCards = new HandPresident();

    private HandPresident receivedCards = new HandPresident();

    private HandPresident virtualHand = new HandPresident();

    private int nbStacks = 1;

    ContainerPresident(MainWindow _window) {
        super(_window);
    }

    @Override
    public boolean isSimple() {
        return false;
    }

    public static CustList<GraphicPresidentCard> getGraphicCards(Iterable<CardPresident> _hand) {
        CustList<GraphicPresidentCard> list_;
        list_ = new CustList<GraphicPresidentCard>();
        boolean entered_ = false;
        for(CardPresident c: _hand) {
            GraphicPresidentCard carte_=new GraphicPresidentCard(c,SwingConstants.RIGHT,!entered_);
            carte_.setPreferredSize(entered_);
            list_.add(carte_);
            entered_ = true;
        }
        return list_;
    }

    public StringList pseudosPresident(byte _nbPlayers) {
        StringList pseudosTwo_=new StringList();
        pseudosTwo_.add(pseudo());
        StringList pseudos_ = getPseudosJoueurs().getPseudosPresident();
        pseudosTwo_.addAllElts(pseudos_.mid(0, _nbPlayers - 1));
        return pseudosTwo_;
    }

    /**Permet de charger une main de distribution
    a partir d'un fichier*/
    protected static HandPresident chargerPilePresident(int _nbStacks) {
        HandPresident pile_=(HandPresident)StreamTextFile.loadObject(StringList.concat(LaunchingCards.getTempFolderSl(),FileConst.DECK_FOLDER,StreamTextFile.SEPARATEUR,GameEnum.PRESIDENT.name(),Long.toString(_nbStacks),FileConst.DECK_EXT));
        return pile_;
    }

    public void discard(byte _index) {
        CardPresident c_ = virtualHand.carte(_index);
        virtualHand.supprimerCarte(_index);
        givenCards.ajouter(c_);
    }

    public void cancelDiscard(byte _index) {
        CardPresident c_ = givenCards.carte(_index);
        givenCards.supprimerCarte(_index);
        virtualHand.ajouter(c_);
    }

    protected JPanel assemble() {
        JPanel panelCards_ = new JPanel();
        panelCards_.add(getPanelGivenCards());
        panelCards_.add(getPanelReceivedCards());
        return panelCards_;
    }

    public void updateCardsInPanelPresidentReceived() {
        getPanelReceivedCards().removeAll();
        for (GraphicPresidentCard c: getGraphicCards(getReceivedCards())) {
            getPanelReceivedCards().add(c);
        }
        getPanelReceivedCards().validate();
        getPanelReceivedCards().repaint();
    }

    public void updateCardsInPanelPresidentGiven() {
        getPanelGivenCards().removeAll();
        for (GraphicPresidentCard c: getGraphicCards(getGivenCards())) {
            getPanelGivenCards().add(c);
        }
        getPanelGivenCards().validate();
        getPanelGivenCards().repaint();
    }

    public void discard() {
    }

    public void noPlay() {
    }
    public String pseudo() {
        return getPseudosJoueurs().getPseudo();
    }
    public CarpetPresident tapisPresident() {
        return getTapis().getTapisPresident();
    }
    protected LoadingVideo getAnimChargement() {
        return animChargement;
    }
    protected void setAnimChargement(LoadingVideo _animChargement) {
        animChargement = _animChargement;
    }
    public boolean isArretDemo() {
        return arretDemo;
    }
    public void setArretDemo(boolean _arretDemo) {
        arretDemo = _arretDemo;
    }
    public CustList<Numbers<Long>> getScores() {
        return scores;
    }
    protected void setScores(CustList<Numbers<Long>> _scores) {
        scores = _scores;
    }

    protected LabelButton getNoPlay() {
        return noPlay;
    }

    protected void setNoPlay(LabelButton _noPlay) {
        noPlay = _noPlay;
    }

    protected LabelButton getGivingCardsOk() {
        return givingCardsOk;
    }

    protected void setGivingCardsOk(LabelButton _givingCardsOk) {
        givingCardsOk = _givingCardsOk;
    }

    protected JPanel getPanelReceivedCards() {
        return panelReceivedCards;
    }

    public void setPanelReceivedCards(JPanel _panelReceivedCards) {
        panelReceivedCards = _panelReceivedCards;
    }

    protected JPanel getPanelGivenCards() {
        return panelGivenCards;
    }

    public void setPanelGivenCards(JPanel _panelGivenCards) {
        panelGivenCards = _panelGivenCards;
    }

    public boolean isCanDiscard() {
        return canDiscard;
    }

    public void setCanDiscard(boolean _canDiscard) {
        canDiscard = _canDiscard;
    }

    public boolean isCanPlay() {
        return canPlay;
    }
    public void setCanPlay(boolean _canPlay) {
        canPlay = _canPlay;
    }

    public CardPresident getCarteSurvoleePresident() {
        return carteSurvoleePresident;
    }

    public void setCarteSurvoleePresident(CardPresident _carteSurvoleePresident) {
        carteSurvoleePresident = _carteSurvoleePresident;
    }

    public byte getIndexCard() {
        return indexCard;
    }

    public void setIndexCard(byte _indexCard) {
        indexCard = _indexCard;
    }
    protected long getMaxAbsoluScore() {
        return maxAbsoluScore;
    }
    protected void setMaxAbsoluScore(long _maxAbsoluScore) {
        maxAbsoluScore = _maxAbsoluScore;
    }
    protected boolean isPartieSauvegardee() {
        return partieSauvegardee;
    }
    protected void setPartieSauvegardee(boolean _partieSauvegardee) {
        partieSauvegardee = _partieSauvegardee;
    }
    protected boolean isPartieAleatoireJouee() {
        return partieAleatoireJouee;
    }
    protected void setPartieAleatoireJouee(boolean _partieAleatoireJouee) {
        partieAleatoireJouee = _partieAleatoireJouee;
    }

    public HandPresident getGivenCards() {
        return givenCards;
    }

    public void setGivenCards(HandPresident _givenCards) {
        givenCards = _givenCards;
    }

    public HandPresident getReceivedCards() {
        return receivedCards;
    }

    public void setReceivedCards(HandPresident _receivedCards) {
        receivedCards = _receivedCards;
    }

    protected HandPresident getVirtualHand() {
        return virtualHand;
    }

    protected void setVirtualHand(HandPresident _virtualHand) {
        virtualHand = _virtualHand;
    }

    public int getNbGivenCards() {
        return nbGivenCards;
    }

    public void setNbGivenCards(int _nbGivenCards) {
        nbGivenCards = _nbGivenCards;
    }

    protected int getNbStacks() {
        return nbStacks;
    }

    protected void setNbStacks(int _nbStacks) {
        nbStacks = _nbStacks;
    }
}
