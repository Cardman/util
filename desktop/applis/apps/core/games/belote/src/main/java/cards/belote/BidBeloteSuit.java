package cards.belote;
import cards.belote.enumerations.BidBelote;
import cards.consts.Order;
import cards.consts.Suit;


public final class BidBeloteSuit {

    private BidBelote bid = BidBelote.FOLD;

    private Suit suit = Suit.UNDEFINED;

    private int points;

    public BidBelote getEnchere() {
        return bid;
    }

    public void setEnchere(BidBelote _enchere) {
        bid = _enchere;
    }

    public Suit getCouleur() {
        return suit;
    }

    public void setCouleur(Suit _couleur) {
        suit = _couleur;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int _points) {
        points = _points;
    }

    public boolean strongerThan(BidBeloteSuit _contrat) {
        return bid.getForce() > _contrat.bid.getForce();
    }

    public boolean estDemandable(BidBeloteSuit _contrat) {
        return bid.estDemandable(_contrat.bid);
    }

    public boolean getCouleurDominante() {
        return bid.getCouleurDominante();
    }

    public boolean ordreCouleur() {
        return bid.ordreCouleur();
    }

    public boolean ordreAtout() {
        return bid.ordreAtout();
    }

    public Order getOrdre() {
        return bid.getOrdre();
    }

    public boolean jouerDonne() {
        return bid.jouerDonne();
    }

    public boolean eq(BidBeloteSuit _obj) {
        if (bid != _obj.bid) {
            return false;
        }
        if (suit != _obj.suit) {
            return false;
        }
        if (points != _obj.points) {
            return false;
        }
        return true;
    }

    public BidBelote getBid() {
        return bid;
    }

    public void setBid(BidBelote _bid) {
        bid = _bid;
    }

    public Suit getSuit() {
        return suit;
    }

    public void setSuit(Suit _suit) {
        suit = _suit;
    }
}
