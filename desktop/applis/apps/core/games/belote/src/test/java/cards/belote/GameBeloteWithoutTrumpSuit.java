package cards.belote;
import cards.belote.enumerations.BidBelote;

public class GameBeloteWithoutTrumpSuit extends EquallableBeloteUtil {

    void bidding(GameBelote _game,BidBelote _bid) {
        assertTrue("Bad bid arg",!_bid.getCouleurDominante());
        byte playerAfterDealer_ = _game.playerAfter(_game.getDistribution().getDealer());
        BidBeloteSuit contratTmp_ = new BidBeloteSuit();
        contratTmp_.setEnchere(_bid);
        _game.ajouterContrat(contratTmp_,playerAfterDealer_);
        _game.completerDonne();
        assertTrue(!_game.getContrat().getCouleurDominante());
    }
}
