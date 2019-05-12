package cards.tarot;

import cards.consts.Suit;
import cards.tarot.enumerations.*;
import code.util.CustList;
import code.util.EnumList;
import code.util.EnumMap;
import code.util.EqList;

public final class GameTarotDeclaring {
    private GameTarotTrickInfo doneTrickInfo;
    private GameTarotTeamsRelation teamsRelation;
    private HandTarot curHand;
    private CustList<EnumList<Handfuls>> declaresHandfuls;
    private EqList<EnumList<Miseres>> declaresMiseres;

    public GameTarotDeclaring(GameTarotTrickInfo _doneTrickInfo, GameTarotTeamsRelation _teamsRelation, HandTarot _curHand, CustList<EnumList<Handfuls>> _declaresHandfuls, EqList<EnumList<Miseres>> _declaresMiseres) {
        doneTrickInfo = _doneTrickInfo;
        teamsRelation = _teamsRelation;
        curHand = _curHand;
        declaresHandfuls = _declaresHandfuls;
        declaresMiseres = _declaresMiseres;
    }

    public EnumList<Handfuls> strategieAnnoncesPoignees(BidTarot _bid, HandTarot _calledCards,byte _numeroJoueur) {

        EnumList<Handfuls> va_ = new EnumList<Handfuls>();
        EnumMap<Suit,HandTarot> repartition_ = curHand.couleurs();
        // peuvent etre annoncees par le joueur si toutes les annonces etaient
        // autorisees
        HandTarot atouts_ = GameTarotCommonPlaying.atoutsPoignee(repartition_);
        boolean chienInvisible_ = _bid.getJeuChien() == PlayingDog.WITHOUT;
        if (_bid.getJeuChien() == PlayingDog.AGAINST) {
            chienInvisible_ = true;
        }
        if (teamsRelation.getTaker() != _numeroJoueur || teamsRelation.existeAppele() || !_calledCards.estVide()
                && chienInvisible_ || GameTarotBid.estUnJeuDeChelemSur(repartition_,doneTrickInfo.cartesJoueesEnCours(teamsRelation,_numeroJoueur).couleurs())) {
            EnumList<Handfuls> poigneesOrdonnees_ = teamsRelation.getRules().getPoigneesOrdonnees();
            EnumList<Handfuls> poigneesAutorisees_ = new EnumList<Handfuls>();
            for(Handfuls p: poigneesOrdonnees_) {
                if(!teamsRelation.getRules().poigneeAutorisee(p)) {
                    continue;
                }
                poigneesAutorisees_.add(p);
            }
            if(poigneesAutorisees_.isEmpty()) {
                return va_;
            }
            EnumMap<Handfuls,Integer> poigneesNbAtout_ = teamsRelation.getRules().getPoigneesAutorisees();
            if (atouts_.total() < poigneesNbAtout_.getVal(poigneesAutorisees_.first())) {
                return va_;
            }
            int nbHandfuls_ = poigneesOrdonnees_.size();
            for(int i = CustList.SECOND_INDEX; i<nbHandfuls_; i++) {
                Handfuls p_ = poigneesOrdonnees_.get(i);
                if(atouts_.total() < poigneesNbAtout_.getVal(p_)) {
                    va_.add(poigneesAutorisees_.getPrev(i));
                    return va_;
                }

            }
            va_.add(poigneesAutorisees_.last());
            return va_;
        }
        return va_;
    }
    EnumList<Handfuls> getAnnoncesPoigneesPossibles() {
        EnumMap<Suit,HandTarot> repartition_ = curHand.couleurs();
        int nombreAtoutsEx_ = GameTarotCommon.atoutsAvecExcuse(repartition_);
        EnumList<Handfuls> annoncesPossibles_ = new EnumList<Handfuls>();
        for (Handfuls poignee_ : teamsRelation.getRules().getPoigneesAutorisees().getKeys()) {
            if(nombreAtoutsEx_ < teamsRelation.getRules().getPoigneesAutorisees().getVal(poignee_)) {
                continue;
            }
            annoncesPossibles_.add(poignee_);
        }
        return annoncesPossibles_;

    }

    public HandTarot strategiePoignee(byte _numeroJoueur) {
        EnumMap<Suit,HandTarot> repartition_ = curHand.couleurs();
        HandTarot atouts_ = GameTarotCommonPlaying.atoutsPoignee(repartition_);
        HandTarot poignee_ = new HandTarot();
        for(Handfuls p: getAnnoncesPoignees(_numeroJoueur)) {
            byte max_ = teamsRelation.getRules().getPoigneesAutorisees().getVal(p).byteValue();
            byte trumpIndex_ = CustList.FIRST_INDEX;
            if(atouts_.total() == max_) {
                while (poignee_.total() < max_) {
                    poignee_.ajouter(atouts_.carte(trumpIndex_));
                    trumpIndex_++;
                }
            } else {
                //atouts_.total() > max_ because of strategieAnnoncesPoignees
                while (poignee_.total() < max_) {
                    CardTarot card_ = atouts_.carte(trumpIndex_);
                    if (card_.couleur() == Suit.TRUMP) {
                        poignee_.ajouter(card_);
                    }
                    trumpIndex_++;
                }
            }
        }
        return poignee_;
    }
    public EnumList<Miseres> getAnnoncesMiseresPossibles() {
        EnumMap<Suit,HandTarot> repartition_ = curHand.couleurs();
        int nombreAtoutsEx_ = GameTarotCommon.atoutsAvecExcuse(repartition_);
        EnumList<Miseres> annoncesPossibles_ = new EnumList<Miseres>();
        if (nombreAtoutsEx_ == 0) {
            annoncesPossibles_.add(Miseres.TRUMP);
        }
        if (nombreAtoutsEx_ + curHand.nombreDeFigures() == curHand
                .total()) {
            annoncesPossibles_.add(Miseres.LOW_CARDS);
        }
        if (nombreAtoutsEx_ == curHand.total()) {
            annoncesPossibles_.add(Miseres.SUIT);
        }
        if (curHand.nombreDeFigures() == 0) {
            annoncesPossibles_.add(Miseres.CHARACTER);
        }
        if (curHand.nombreDeFigures() + curHand.nombreDeBouts() == 0) {
            annoncesPossibles_.add(Miseres.POINT);
        }
        return annoncesPossibles_;
    }

    public EnumList<Miseres> strategieAnnoncesMiseres() {

        EnumList<Miseres> vaa_ = teamsRelation.getRules().getMiseres();
        EnumList<Miseres> vap_ = getAnnoncesMiseresPossibles();
        EnumList<Miseres> vainter_ = new EnumList<Miseres>();
        // Intersection entre
        // les annonces
        // auorisees par les
        // regles du jeu et
        // celles
        // peuvent etre annoncees par le joueur si toutes les annonces etaient
        // autorisees
        for (Miseres m: vaa_) {
            if (vap_.containsObj(m)) {
                vainter_.add(m);
            }
        }
        return vainter_;
    }
    EnumList<Handfuls> getAnnoncesPoignees(byte _numero) {
        return declaresHandfuls.get(_numero);
    }
}
