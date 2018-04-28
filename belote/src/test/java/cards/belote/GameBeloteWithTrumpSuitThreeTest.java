package cards.belote;
import static cards.belote.EquallableBeloteUtil.assertEq;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import cards.belote.enumerations.BeloteTrumpPartner;
import cards.belote.enumerations.BidBelote;
import cards.belote.enumerations.CardBelote;
import cards.consts.GameType;
import cards.consts.MixCardsChoice;
import cards.consts.Suit;
import code.util.EnumMap;
import code.util.EqList;

public class GameBeloteWithTrumpSuitThreeTest extends GameBeloteTest {

    static DealBelote initializeHands() {
        EqList<HandBelote> mains_ = new EqList<HandBelote>();
        HandBelote main_ = new HandBelote();
        main_.ajouter(CardBelote.DIAMOND_QUEEN);
        main_.ajouter(CardBelote.HEART_10);
        main_.ajouter(CardBelote.SPADE_9);
        main_.ajouter(CardBelote.SPADE_JACK);
        main_.ajouter(CardBelote.DIAMOND_8);
        mains_.add(main_);
        main_ = new HandBelote();
        main_.ajouter(CardBelote.HEART_1);
        main_.ajouter(CardBelote.HEART_KING);
        main_.ajouter(CardBelote.CLUB_JACK);
        main_.ajouter(CardBelote.CLUB_7);
        main_.ajouter(CardBelote.HEART_7);
        mains_.add(main_);
        main_ = new HandBelote();
        main_.ajouter(CardBelote.SPADE_7);
        main_.ajouter(CardBelote.SPADE_QUEEN);
        main_.ajouter(CardBelote.SPADE_8);
        main_.ajouter(CardBelote.DIAMOND_JACK);
        main_.ajouter(CardBelote.SPADE_10);
        mains_.add(main_);
        main_ = new HandBelote();
        main_.ajouter(CardBelote.DIAMOND_1);
        main_.ajouter(CardBelote.DIAMOND_KING);
        main_.ajouter(CardBelote.CLUB_QUEEN);
        main_.ajouter(CardBelote.SPADE_1);
        main_.ajouter(CardBelote.CLUB_8);
        mains_.add(main_);
        main_ = new HandBelote();
        main_.ajouter(CardBelote.DIAMOND_9);

        main_.ajouter(CardBelote.HEART_9);
        main_.ajouter(CardBelote.SPADE_KING);
        main_.ajouter(CardBelote.DIAMOND_7);

        main_.ajouter(CardBelote.CLUB_KING);
        main_.ajouter(CardBelote.HEART_QUEEN);

        main_.ajouter(CardBelote.CLUB_9);
        main_.ajouter(CardBelote.HEART_8);
        main_.ajouter(CardBelote.CLUB_10);

        main_.ajouter(CardBelote.DIAMOND_10);
        main_.ajouter(CardBelote.HEART_JACK);
        main_.ajouter(CardBelote.CLUB_1);
        mains_.add(main_);
        return new DealBelote(mains_,(byte)2);
    }    @Test
    public void playableCards_WhileDiscarding1Test(){
        RulesBelote regles_=new RulesBelote();
        regles_.setGestionCoupePartenaire(BeloteTrumpPartner.UNDERTRUMP_ONLY);
        regles_.setCartesBattues(MixCardsChoice.NEVER);
        game = new GameBelote(GameType.RANDOM,initializeHands(),regles_);
        //game.resetNbPlisTotal();
        biddingTrumpSuit(BidBelote.OTHER_SUIT,Suit.HEART);
        game.setPliEnCours();
        assertEq(3,game.getEntameur());
        HandBelote hand_ = game.getDistribution().main(game.getEntameur());
        assertTrue(hand_.contient(CardBelote.DIAMOND_1));
        game.getDistribution().jouer(game.getEntameur(),CardBelote.DIAMOND_1);
        game.ajouterUneCarteDansPliEnCours(CardBelote.DIAMOND_1);
        assertNotEquals(game.couleurAtout(), game.getPliEnCours().couleurDemandee());
        byte player_ = game.playerAfter(game.getEntameur());
        hand_ = game.getDistribution().main(player_);
        assertTrue(hand_.contient(CardBelote.DIAMOND_QUEEN));
        game.getDistribution().jouer(player_,CardBelote.DIAMOND_QUEEN);
        game.ajouterUneCarteDansPliEnCours(CardBelote.DIAMOND_QUEEN);
        player_ = game.playerAfter(player_);
        assertTrue(game.memeEquipe(player_, game.getEntameur()));
        hand_ = game.getDistribution().main(player_);
        EnumMap<Suit,HandBelote> suits_ = hand_.couleurs(game.getContrat());
        assertTrue(!suits_.getVal(game.couleurAtout()).estVide());
        HandBelote playableCards_ = game.playableCards(suits_);
        assertEq(hand_.total(),playableCards_.total());
        assertTrue(playableCards_.display(),playableCards_.contientCartes(hand_));
    }
    @Test
    public void playableCards_WhileDiscarding2Test(){
        RulesBelote regles_=new RulesBelote();
        regles_.setGestionCoupePartenaire(BeloteTrumpPartner.NO_UNDERTRUMP_NO_OVERTRUMP);
        regles_.setCartesBattues(MixCardsChoice.NEVER);
        game = new GameBelote(GameType.RANDOM,initializeHands(),regles_);
        //game.resetNbPlisTotal();
        biddingTrumpSuit(BidBelote.OTHER_SUIT,Suit.HEART);
        game.setPliEnCours();
        assertEq(3,game.getEntameur());
        HandBelote hand_ = game.getDistribution().main(game.getEntameur());
        assertTrue(hand_.contient(CardBelote.DIAMOND_1));
        game.getDistribution().jouer(game.getEntameur(),CardBelote.DIAMOND_1);
        game.ajouterUneCarteDansPliEnCours(CardBelote.DIAMOND_1);
        assertNotEquals(game.couleurAtout(), game.getPliEnCours().couleurDemandee());
        byte player_ = game.playerAfter(game.getEntameur());
        hand_ = game.getDistribution().main(player_);
        assertTrue(hand_.contient(CardBelote.DIAMOND_QUEEN));
        game.getDistribution().jouer(player_,CardBelote.DIAMOND_QUEEN);
        game.ajouterUneCarteDansPliEnCours(CardBelote.DIAMOND_QUEEN);
        player_ = game.playerAfter(player_);
        assertTrue(game.memeEquipe(player_, game.getEntameur()));
        hand_ = game.getDistribution().main(player_);
        EnumMap<Suit,HandBelote> suits_ = hand_.couleurs(game.getContrat());
        assertTrue(!suits_.getVal(game.couleurAtout()).estVide());
        HandBelote playableCards_ = game.playableCards(suits_);
        assertEq(hand_.total(),playableCards_.total());
        assertTrue(playableCards_.display(),playableCards_.contientCartes(hand_));
    }
}