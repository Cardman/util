package cards.gui.animations;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import cards.belote.DealBelote;
import cards.belote.GameBelote;
import cards.belote.HandBelote;
import cards.belote.RulesBelote;
import cards.belote.TrickBelote;
import cards.consts.GameType;
import cards.consts.MixCardsChoice;
import cards.facade.Games;
import cards.gui.MainWindow;
import cards.gui.containers.ContainerBelote;
import cards.gui.containers.ContainerGame;
import cards.gui.containers.ContainerSimuBelote;
import cards.gui.labels.GraphicBeloteCard;
import cards.gui.panels.CarpetBelote;
import cards.gui.panels.MiniCarpet;
import code.gui.LabelButton;
import code.gui.ThreadInvoker;
import code.util.CustList;
import code.util.EqList;
import code.util.NumberMap;
import code.util.StringList;
import code.util.consts.Constants;

/**This class thread is independant from EDT,
Thread safe class*/
public final class SimulationGameBelote extends Thread implements SimulationGame {
    private Games partieSimulee = new Games();
    private ContainerSimuBelote container;
    private LabelButton stopButton;
    /**This class thread is independant from EDT*/
    public SimulationGameBelote(ContainerSimuBelote _container) {
        container = _container;
        GameBelote.setChargementSimulation(0);
        HandBelote pile_=HandBelote.pileBase();
        DealBelote donne_=new DealBelote(0l,pile_);
        RulesBelote regles_ = container.getWindow().getReglesBelote();
        donne_.setRandomDealer(regles_.getRepartition().getNombreJoueurs());
        regles_.setCartesBattues(MixCardsChoice.EACH_DEAL);
        donne_.initDonne(regles_,container.getDisplayingBelote());
        partieSimulee.jouerBelote(new GameBelote(GameType.EDIT,donne_,regles_));
        stopButton=new LabelButton(container.getMessages().getVal(MainWindow.STOP_DEMO));
        stopButton.addMouseListener(new StopEvent(this));
    }
    @Override
    public void stopSimulation() {
        container.setArretDemo(true);
        container.getOwner().menuSoloGames();
    }
    private GameBelote partieBeloteSimulee() {
        return partieSimulee.partieBelote();
    }
    /**Pseudos utilis_&eacute_;s*/
    private StringList pseudosSimuleeBelote() {
        StringList pseudos_=new StringList();
        GameBelote partie_=partieBeloteSimulee();
        partie_.getNombreDeJoueurs();
        pseudos_.add(container.pseudo());
        pseudos_.addAllElts(container.getPseudosJoueurs().getPseudosBelote());
        return pseudos_;
    }
    @Override
    public void run() {
        ThreadInvoker.invokeNow(new SettingSimulationComponent(this));
        SwingUtilities.invokeLater(new GoSimulateGame(new GoSimulateBelote(partieSimulee, container, stopButton), container));
    }
    private void afficherMainUtilisateurSimuBelote(HandBelote _mainUtilisateur) {
        JPanel panneau1_=container.getPanelHand();
        panneau1_.removeAll();
        /*On place les cartes de l'utilisateur*/
        for (GraphicBeloteCard c: ContainerBelote.getGraphicCards(_mainUtilisateur)) {
            panneau1_.add(c);
        }
        panneau1_.repaint();
        panneau1_.revalidate();
//        boolean entered_ = false;
//        for(CardBelote c: _mainUtilisateur)
//        {
//            GraphicBeloteCard carte_=new GraphicBeloteCard(c,SwingConstants.RIGHT,!entered_);
//            carte_.setPreferredSize(entered_);
//            panneau1.add(carte_);
//            entered_ = true;
//        }

    }
    @Override
    public void setSimulationGui() {
        container.setArretDemo(false);
        //desactiver le menu Partie/aide au jeu
        container.getHelpGame().setEnabledMenu(false);
        //desactiver le menu Partie/Demo
        container.getDemo().setEnabledMenu(false);
        //Activer le menu Partie/Pause
        container.getPause().setEnabledMenu(true);
        EqList<HandBelote> mainsUtilisateurs_=new EqList<HandBelote>();
        GameBelote partie_=partieBeloteSimulee();
        partie_.simuler(Constants.getLanguage());
        byte nombreJoueurs_=partie_.getNombreDeJoueurs();
        if(partie_.getSimulationAvecContrats()) {
            CustList<TrickBelote> plisFaits_=partie_.unionPlis();
            mainsUtilisateurs_.add(0,new HandBelote());
            mainsUtilisateurs_.get(0).ajouter(plisFaits_.get(plisFaits_.size()-1).carteDuJoueur((byte)0, nombreJoueurs_));
            int indLastShownTrick_=plisFaits_.size()-2;
            for(int indicePli_=indLastShownTrick_;indicePli_>=CustList.FIRST_INDEX;indicePli_--) {
                TrickBelote pli_=plisFaits_.get(indicePli_);
                mainsUtilisateurs_.add(0,new HandBelote());
                mainsUtilisateurs_.get(0).ajouterCartes(mainsUtilisateurs_.get(1));
                mainsUtilisateurs_.get(0).ajouter(pli_.carteDuJoueur((byte)0, nombreJoueurs_));
                mainsUtilisateurs_.get(0).setOrdre(container.getDisplayingBelote().getOrdreAvantEncheres());
                mainsUtilisateurs_.get(0).trier(container.getDisplayingBelote().getCouleurs(),container.getDisplayingBelote().getDecroissant(),container.getDisplayingBelote().getOrdreAvantEncheres());
            }
            mainsUtilisateurs_.add(0,new HandBelote());
            mainsUtilisateurs_.get(0).ajouterCartes(mainsUtilisateurs_.get(1));
            mainsUtilisateurs_.get(0).supprimerCartes(partie_.getDistribution().derniereMain());
            mainsUtilisateurs_.get(0).setOrdre(container.getDisplayingBelote().getOrdreAvantEncheres());
            mainsUtilisateurs_.get(0).trier(container.getDisplayingBelote().getCouleurs(),container.getDisplayingBelote().getDecroissant(),container.getDisplayingBelote().getOrdreAvantEncheres());
        }else {
            mainsUtilisateurs_.add(partie_.getDistribution().main());
            mainsUtilisateurs_.get(0).setOrdre(container.getDisplayingBelote().getOrdreAvantEncheres());
            mainsUtilisateurs_.get(0).trier(container.getDisplayingBelote().getCouleurs(),container.getDisplayingBelote().getDecroissant(),container.getDisplayingBelote().getOrdreAvantEncheres());
        }
        JPanel contentPane_ = new JPanel();
        contentPane_.setLayout(new BoxLayout(contentPane_, BoxLayout.PAGE_AXIS));
        JPanel container_=new JPanel();
        container_.setLayout(new BorderLayout());
        container_.add(new JLabel(container.getMessages().getVal(MainWindow.HELP_GO_MENU),SwingConstants.CENTER),BorderLayout.NORTH);
        CarpetBelote tapis_=new CarpetBelote();
        StringList pseudos_ = pseudosSimuleeBelote();
        tapis_.initTapisBelote(partie_.getNombreDeJoueurs(),container.getDisplayingBelote().getHoraire(),pseudos_,1);
        container.getTapis().setTapisBelote(tapis_);
        container_.add(tapis_,BorderLayout.CENTER);
        JPanel panneau_=new JPanel();
        panneau_.setBackground(Color.BLUE);
        panneau_.setLayout(new FlowLayout(FlowLayout.LEFT,0,0));
        container.setPanelHand(panneau_);
        container_.add(panneau_,BorderLayout.SOUTH);
        JPanel panneau2_=new JPanel();
        panneau2_.setLayout(new BoxLayout(panneau2_, BoxLayout.PAGE_AXIS));
        container.setEvents(new JTextArea(ContainerBelote.EMPTY,8, 30));
        container.getEvents().setEditable(false);
        panneau2_.add(new JScrollPane(container.getEvents()));
        container.setMini(new MiniCarpet(partie_.getNombreDeJoueurs(),container.getDisplayingBelote().getHoraire(),pseudos_));
        panneau2_.add(container.getMini());
        container.setHandfuls(new NumberMap<Byte,JLabel>());
        container.setDeclaredHandfuls(new NumberMap<Byte,JPanel>());
        JPanel declaredHandfuls_ = new JPanel(new GridLayout(0,1));
        int nbPlayers_ = partie_.getNombreDeJoueurs();
        for (byte i=CustList.FIRST_INDEX;i<nbPlayers_;i++) {
            JPanel declaredHandfulGroup_ = new JPanel(new FlowLayout());
            JLabel lab_ = new JLabel(pseudos_.get(i));
            declaredHandfulGroup_.add(lab_);
            JLabel handful_ = new JLabel(ContainerGame.EMPTY_STRING);
            declaredHandfulGroup_.add(handful_);
            container.getHandfuls().put(i, handful_);
            JPanel declaredHandful_ = new JPanel(new FlowLayout(FlowLayout.LEFT,0,0));
            declaredHandfulGroup_.add(declaredHandful_);
            container.getDeclaredHandfuls().put(i, declaredHandful_);
            declaredHandfuls_.add(declaredHandfulGroup_);
        }
        JScrollPane scroll_ = new JScrollPane(declaredHandfuls_);
        panneau2_.add(scroll_);
        JPanel sousPanneau_=new JPanel(new GridLayout(0,1));
        container.setPanneauBoutonsJeu(sousPanneau_);
        panneau2_.add(sousPanneau_);
        container_.add(panneau2_,BorderLayout.EAST);
        if (!partie_.getDistribution().derniereMain().estVide()) {
            container.tapisBelote().setTalonBelote(partie_.getDistribution().derniereMain());
        }
        contentPane_.add(container_);
        contentPane_.add(container.getWindow().getClock());
        contentPane_.add(container.getWindow().getLastSavedGameDate());
        container.setContentPane(contentPane_);
        panneau_=container.getPanneauBoutonsJeu();
//        LabelButton bouton_=new LabelButton(container.getMessages().getVal(MainWindow.STOP_DEMO));
//        bouton_.addMouseListener(new MouseAdapter() {
//
//            @Override
//            public void mouseReleased(MouseEvent _e) {
//                container.setArretDemo(true);
//                container.getOwner().menuSoloGames();
//            }
//        });
        panneau_.add(stopButton);
        afficherMainUtilisateurSimuBelote(mainsUtilisateurs_.first());
    }
}
