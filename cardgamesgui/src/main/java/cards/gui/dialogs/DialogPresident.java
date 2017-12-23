package cards.gui.dialogs;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerListModel;

import cards.consts.MixCardsChoice;
import cards.consts.Suit;
import cards.gui.comboboxes.ComboBoxEnumCards;
import cards.gui.comboboxes.ComboBoxMixCards;
import cards.gui.dialogs.events.ListenerEqualityPlaying;
import cards.gui.dialogs.events.ListenerPlayers;
import cards.gui.dialogs.events.ListenerStacks;
import cards.president.RulesPresident;
import cards.president.enumerations.EqualtyPlaying;
import code.util.EnumList;
import code.util.EnumMap;
import code.util.Numbers;
import code.util.StringList;
import code.util.StringMap;
import code.util.ints.Listable;

public abstract class DialogPresident extends DialogCards implements DialogVaryingPlayerNumber {

    private static final String EMPTY = "";
    private static final String DEALING = "dealing";
    private static final String MIX_CARDS = "mixCards";
    private static final String NUMBER_DEALS = "numberDeals";

    private static final String EQUALITY = "equality";
    private static final String STOP_ALL_PLAYED_CARDS = "stopAllPlayedCards";
    private static final String CAN_PASS = "canPass";
    private static final String POSSIBLE_REVERSING = "possibleReversing";
    private static final String RULES = "rules";

    private static final String LOOSE_FINISH_BEST_CARDS = "looseFinishBestCards";
    private static final String SWITCH_CARDS = "switchCards";
    private static final String LOOSER_STARTS_FIRST = "looserStartsFirst";
    private static final String END_DEAL = "endDeal";

    private static final String NUMBER_PLAYERS = "numberPlayers";
    private static final String NUMBER_STACKS = "numberStacks";
    private static final String REPARTITION = "repartition";

    private RulesPresident reglesPresident=new RulesPresident();
    private JSpinner nbGames;
    private StringMap<String> messages = new StringMap<String>();
    private ComboBoxMixCards listeChoix;

    private ComboBoxEnumCards<EqualtyPlaying> equality;
    private JLabel stopAllPlayedCards;
    private JCheckBox canPass;
    private JCheckBox possibleReversing;

    private JCheckBox looseFinishBestCards;
    private JCheckBox switchCards;
    private JCheckBox looserStartsFirst;

    private JSpinner nbJoueurs;
    private JSpinner nbStacks;

    protected DialogPresident() {
    }

    public abstract void setDialogue(boolean _enabledChangingNbPlayers,int _nbPlayers);

    protected void initMessageName() {
        setMessages(getMessages(FileConst.FOLDER_MESSAGES_GUI));
    }

    protected void initJt(JSpinner _nbGames, boolean _enabledChangingNbPlayers, int _nbPlayers) {
        setNbGames(_nbGames);
        JPanel dealing_=new JPanel();
        dealing_.setLayout(new GridLayout(0,2));
        //Sous - panneau Battre les cartes
        dealing_.add(new JLabel(getMessages().getVal(MIX_CARDS)));
        listeChoix=new ComboBoxMixCards();
        Listable<MixCardsChoice> mix_;
        mix_ = new EnumList<MixCardsChoice>(MixCardsChoice.values());
        EnumMap<MixCardsChoice, String> trMix_;
        trMix_ = new EnumMap<MixCardsChoice, String>();
        for (MixCardsChoice choix_: mix_) {
            trMix_.put(choix_, choix_.toString());
        }
        listeChoix.refresh(mix_, trMix_);
//        for (MixCardsChoice choix_:MixCardsChoice.values()) {
//            listeChoix.addItem(choix_);
//        }
        listeChoix.setSelectedItem(getReglesPresident().getMixedCards());
        dealing_.add(listeChoix);
        if (getNbGames() != null) {
            dealing_.add(new JLabel(getMessages().getVal(NUMBER_DEALS)));
            dealing_.add(getNbGames());
        }

        //Panneau Distribution
        getJt().add(getMessages().getVal(DEALING),dealing_);

        JPanel rules_=new JPanel();
        rules_.setLayout(new GridLayout(0,2));
        rules_.add(new JLabel(getMessages().getVal(EQUALITY)));
        equality = new ComboBoxEnumCards<EqualtyPlaying>();
        for (EqualtyPlaying choix_:EqualtyPlaying.values()) {
            equality.addItem(choix_);
        }
        equality.setSelectedItem(getReglesPresident().getEqualty());
        equality.addActionListener(new ListenerEqualityPlaying(this));
        rules_.add(equality);
        rules_.add(new JLabel());
        stopAllPlayedCards = new JLabel();
        if (getReglesPresident().getEqualty() == EqualtyPlaying.SKIP_DIFF_NEXT_STOP) {
            stopAllPlayedCards.setText(getMessages().getVal(STOP_ALL_PLAYED_CARDS));
        } else {
            stopAllPlayedCards.setText(EMPTY);
        }
        rules_.add(stopAllPlayedCards);
        rules_.add(new JLabel());
        canPass = new JCheckBox(getMessages().getVal(CAN_PASS));
        canPass.setSelected(!getReglesPresident().isHasToPlay());
        rules_.add(canPass);
        rules_.add(new JLabel());
        int nbSuits_ = Suit.couleursOrdinaires().size();
        nbSuits_ *= getReglesPresident().getNbStacks();
        String message_ = StringList.simpleNumberFormat(getMessages().getVal(POSSIBLE_REVERSING), nbSuits_);
        possibleReversing = new JCheckBox(message_);
        possibleReversing.setSelected(getReglesPresident().isPossibleReversing());
        rules_.add(possibleReversing);
        getJt().add(getMessages().getVal(RULES),rules_);

        JPanel endDeal_ = new JPanel();
        endDeal_.setLayout(new BoxLayout(endDeal_, BoxLayout.PAGE_AXIS));
        looseFinishBestCards = new JCheckBox(getMessages().getVal(LOOSE_FINISH_BEST_CARDS));
        looseFinishBestCards.setSelected(getReglesPresident().isLoosingIfFinishByBestCards());
        endDeal_.add(looseFinishBestCards);
        switchCards = new JCheckBox(getMessages().getVal(SWITCH_CARDS));
        switchCards.setSelected(getReglesPresident().isSwitchCards());
        endDeal_.add(switchCards);
        looserStartsFirst = new JCheckBox(getMessages().getVal(LOOSER_STARTS_FIRST));
        looserStartsFirst.setSelected(getReglesPresident().isLooserStartsFirst());
        endDeal_.add(looserStartsFirst);
        getJt().add(getMessages().getVal(END_DEAL),endDeal_);

        JPanel players_ = new JPanel();
        players_.setLayout(new GridLayout(2,0));
        players_.add(new JLabel(getMessages().getVal(NUMBER_PLAYERS)));
        players_.add(new JLabel(getMessages().getVal(NUMBER_STACKS)));

        Numbers<Integer> nombreJoueursPossible_=new Numbers<Integer>();
        int minJoueurs_ = RulesPresident.getNbMinPlayers();
        int maxJoueurs_ = RulesPresident.getNbMaxPlayers();
        for (int i = minJoueurs_; i <= maxJoueurs_; i++) {
            nombreJoueursPossible_.add(i);
        }
        SpinnerListModel spin_ = new SpinnerListModel(nombreJoueursPossible_.toArray());
        if (_nbPlayers != 0) {
            spin_.setValue(_nbPlayers);
        } else {
            spin_.setValue(getReglesPresident().getNbPlayers());
        }
        nbJoueurs=new JSpinner(spin_);
        if (_enabledChangingNbPlayers) {
            nbJoueurs.addChangeListener(new ListenerPlayers(this));
        } else {
            nbJoueurs.setEnabled(false);
        }
        players_.add(nbJoueurs);
        Numbers<Integer> stacks_ = new Numbers<Integer>();
        int minStacks_ = getReglesPresident().getNbMinStacks();
        int maxStacks_ = getReglesPresident().getNbMaxStacks();
        for (int i = minStacks_; i <= maxStacks_; i++) {
            stacks_.add(i);
        }
        spin_ = new SpinnerListModel(stacks_.toArray());
        spin_.setValue(getReglesPresident().getNbStacks());
        nbStacks=new JSpinner(spin_);
        nbStacks.addChangeListener(new ListenerStacks(this));
        players_.add(nbStacks);
        getJt().add(getMessages().getVal(REPARTITION),players_);
    }

    public void displayMessagePlaying() {
        if (equality.getSelectedItem() == EqualtyPlaying.SKIP_DIFF_NEXT_STOP) {
            stopAllPlayedCards.setText(getMessages().getVal(STOP_ALL_PLAYED_CARDS));
        } else {
            stopAllPlayedCards.setText(EMPTY);
        }
    }

    @Override
    public void validateNbPlayers() {
        Numbers<Integer> stacks_ = new Numbers<Integer>();
        int minStacks_ = RulesPresident.getNbMinStacks((Integer) nbJoueurs.getValue());
        int maxStacks_ = RulesPresident.getNbMaxStacks((Integer) nbJoueurs.getValue());
        for (int i = minStacks_; i <= maxStacks_; i++) {
            stacks_.add(i);
        }
        int v_ = (Integer) nbStacks.getValue();
        if (v_ < minStacks_) {
            v_ = minStacks_;
        }
        if (v_ > maxStacks_) {
            v_ = maxStacks_;
        }
        SpinnerListModel spin_ = new SpinnerListModel(stacks_.toArray());
        nbStacks.setModel(spin_);
        nbStacks.setValue(v_);
        int nbSuits_ = Suit.couleursOrdinaires().size();
        nbSuits_ *= v_;
        String message_ = StringList.simpleNumberFormat(getMessages().getVal(POSSIBLE_REVERSING), nbSuits_);
        possibleReversing.setText(message_);
    }

    public void validateStacks() {
        int nbSuits_ = Suit.couleursOrdinaires().size();
        nbSuits_ *= (Integer) nbStacks.getValue();
        String message_ = StringList.simpleNumberFormat(getMessages().getVal(POSSIBLE_REVERSING), nbSuits_);
        possibleReversing.setText(message_);
    }

    protected void validateRules() {
//        getReglesPresident().setMixedCards((MixCardsChoice)listeChoix.getSelectedItem());
        getReglesPresident().setMixedCards(listeChoix.getCurrent());
        getReglesPresident().setEqualty((EqualtyPlaying)equality.getSelectedItem());
        getReglesPresident().setHasToPlay(!canPass.isSelected());
        getReglesPresident().setPossibleReversing(possibleReversing.isSelected());
        getReglesPresident().setLoosingIfFinishByBestCards(looseFinishBestCards.isSelected());
        getReglesPresident().setSwitchCards(switchCards.isSelected());
        getReglesPresident().setLooserStartsFirst(looserStartsFirst.isSelected());
        getReglesPresident().setNbPlayers((Integer) nbJoueurs.getValue());
        getReglesPresident().setNbStacks((Integer) nbStacks.getValue());
    }

    protected StringMap<String> getMessages() {
        return messages;
    }

    protected void setMessages(StringMap<String> _messages) {
        messages = _messages;
    }

    protected RulesPresident getReglesPresident() {
        return reglesPresident;
    }

    protected void setReglesPresident(RulesPresident _reglesPresident) {
        reglesPresident = _reglesPresident;
    }

    protected JSpinner getNbGames() {
        return nbGames;
    }

    protected void setNbGames(JSpinner _nbGames) {
        nbGames = _nbGames;
    }
}
