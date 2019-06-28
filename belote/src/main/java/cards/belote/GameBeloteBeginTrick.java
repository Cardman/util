package cards.belote;

import cards.belote.enumerations.CardBelote;
import cards.consts.Order;
import cards.consts.Status;
import cards.consts.Suit;
import code.util.CustList;
import code.util.EnumList;
import code.util.EnumMap;
import code.util.EqList;
import code.util.*;

public final class GameBeloteBeginTrick {
    private GameBeloteTeamsRelation teamsRelation;
    private GameBeloteTrickInfo info;

    private HandBelote currentHand;
    private GameBeloteCommonPlaying common;
    private Status currentStatus;
    private HandBelote playableCards;
    private BidBeloteSuit bid;
    private byte taker;
    private HandBelote lastSeenHand;

    public GameBeloteBeginTrick(GameBeloteTrickInfo _done, GameBeloteTeamsRelation _teamsRelation, HandBelote _currentHand) {
        teamsRelation = _teamsRelation;
        currentHand = _currentHand;
        info = _done;
        lastSeenHand = _done.getLastSeenHand();
        bid = _done.getBid();
        taker = _teamsRelation.getTaker();
        common = new GameBeloteCommonPlaying(_done,_teamsRelation);
        byte nbPlayers_ = _teamsRelation.getNombreDeJoueurs();
        TrickBelote trBelote_ = _done.getProgressingTrick();
        byte nextPlayer_ = trBelote_.getNextPlayer(nbPlayers_);
        playableCards = common.playableCards(currentHand.couleurs(bid));
        currentStatus = _teamsRelation.statutDe(nextPlayer_);
    }
    CardBelote entame() {
        if(playableCards.total()==1) {
            return playableCards.premiereCarte();
        }
        BeloteInfoPliEnCours info_ = initInformations();
        if(bid.getCouleurDominante()) {
            return entameCouleurDominante(info_);
        }
        return entameSansAtoutToutAtout(info_);
    }

    BeloteInfoPliEnCours initInformations() {
        return common.initInformations(currentHand);
    }

    CardBelote entameCouleurDominante(BeloteInfoPliEnCours _info) {
        if(_info.isMaitreJeu()) {
            return playBestCardsDom(_info);
        }
        if(GameBeloteCommon.hand(currentHand.couleurs(bid),bid.getCouleur()).total()==currentHand.total()) {
            return playWhenOnlyTrumps(_info);

        }
        if(GameBeloteCommon.hand(currentHand.couleurs(bid),bid.getCouleur()).total()+1==currentHand.total()) {
            return playWhenAtMostOneNormalSuit(_info);

        }
        if(currentStatus == Status.TAKER) {
            return playAsTakerDom(_info);
        }
        //Appele
        if(currentStatus == Status.CALLED_PLAYER) {
            return playAsCalledPlayerDom(_info);
        }
        return playAsDefenderDom(_info);
    }

    private CardBelote playBestCardsDom(BeloteInfoPliEnCours _info) {
        EnumMap<Suit,EqList<HandBelote>> suites_= _info.getSuitesTouteCouleur();
        EnumList<Suit> couleursMaitres_= _info.getCouleursMaitresses();
        return jeuMainMaitresseCouleurDominante(suites_,
                currentHand.couleurs(bid),
                couleursMaitres_,
                bid.getCouleur());
    }

    private CardBelote playAsDefenderDom(BeloteInfoPliEnCours _info) {
        EnumMap<Suit,HandBelote> repartition_=currentHand.couleurs(bid);
        Suit couleurAtout_=bid.getCouleur();
        Bytes adversaire_ = _info.getJoueursNonConfiance();
        EnumMap<Suit,HandBelote> repartitionCartesJouees_= _info.getRepartitionCartesJouees();
        CustList<TrickBelote> plisFaits_= _info.getPlisFaits();
        EnumMap<Suit,EqList<HandBelote>> cartesPossibles_= _info.getCartesPossibles();
        EnumMap<Suit,EqList<HandBelote>> cartesCertaines_= _info.getCartesCertaines();
        EnumMap<Suit,HandBelote> cartesMaitresses_= _info.getCartesMaitresses();
        EnumList<Suit> couleursNonAtouts_=common.couleursNonAtouts();
        EnumList<Suit> couleursNonVides_ = GameBeloteCommon.couleursNonAtoutNonVides(currentHand, couleursNonAtouts_);
        EnumList<Suit> couleurs_=GameBeloteCommonPlaying.couleursNonOuvertesNonVides(currentHand, plisFaits_, couleursNonVides_);
        /*Cas d'un defenseur*/
        if(!couleurs_.isEmpty()) {
            return ouvrir(bid, couleurs_, repartition_, repartitionCartesJouees_, cartesMaitresses_);
        }
        couleurs_ = GameBeloteCommonPlaying.couleursPouvantEtreCoupees(adversaire_, cartesPossibles_, couleurAtout_, couleursNonVides_);
        if(!couleurs_.isEmpty()) {
            return faireCouper(bid, couleurs_,repartition_,repartitionCartesJouees_);
        }
        couleurs_ = GameBeloteCommonPlaying.couleursDefausseeParJoueurs(adversaire_, bid, cartesPossibles_, couleursNonVides_);
        couleurs_ = GameBeloteCommonPlaying.couleursNonCoupeeParJoueurs(adversaire_, bid, cartesPossibles_, cartesCertaines_, couleurs_);

        if(!couleurs_.isEmpty()) {
            return ouvrir(bid, couleurs_,repartition_,repartitionCartesJouees_,cartesMaitresses_);
        }
        return faireCouper(bid, couleursNonVides_,repartition_,repartitionCartesJouees_);
    }

    private CardBelote playAsCalledPlayerDom(BeloteInfoPliEnCours _info) {
        EnumList<Suit> couleursNonAtouts_=common.couleursNonAtouts();
        Suit couleurAtout_=bid.getCouleur();
        EnumMap<Suit,HandBelote> repartition_=currentHand.couleurs(bid);
        EnumMap<Suit,HandBelote> repartitionCartesJouees_=_info.getRepartitionCartesJouees();
        CustList<TrickBelote> plisFaits_=_info.getPlisFaits();
        EnumMap<Suit,EqList<HandBelote>> cartesPossibles_=_info.getCartesPossibles();
        EnumMap<Suit,EqList<HandBelote>> cartesCertaines_=_info.getCartesCertaines();
        EnumMap<Suit,HandBelote> cartesMaitresses_=_info.getCartesMaitresses();
        Bytes partenaire_=_info.getJoueursConfiance();
        Bytes adversaire_ =_info.getJoueursNonConfiance();

        EnumList<Suit> couleursNonVides_ = GameBeloteCommon.couleursNonAtoutNonVides(currentHand, couleursNonAtouts_);
        if(!GameBeloteCommon.hand(cartesCertaines_,couleurAtout_,taker).estVide()) {
            if(!GameBeloteCommon.hand(repartition_,couleurAtout_).estVide()) {
                Order or_ = Order.TRUMP;
                boolean cartesMaitressesJouees_ = playedLeading(bid, taker, couleurAtout_, repartitionCartesJouees_, cartesCertaines_, or_);
                if(cartesMaitressesJouees_) {
                    return GameBeloteCommon.hand(repartition_,couleurAtout_).premiereCarte();
                }
            }
        }
        if(!GameBeloteCommon.hand(repartition_,couleurAtout_).estVide()&&GameBeloteCommon.hand(repartition_,couleurAtout_).derniereCarte().points(bid) < 8) {
            return GameBeloteCommon.hand(repartition_,couleurAtout_).derniereCarte();
        }
        if (!lastSeenHand.estVide()) {
            CardBelote carteDessus_=lastSeenHand.premiereCarte();
            Suit couleurDessus_=carteDessus_.couleur();

            if(couleurDessus_!=couleurAtout_
                    &&!GameBeloteCommon.hand(cartesCertaines_,couleurDessus_,taker).estVide()) {
                Order or_ = Order.SUIT;
                boolean cartesMaitressesJouees_ = playedLeading(bid, taker, couleurDessus_, repartitionCartesJouees_, cartesCertaines_, or_);
                if(cartesMaitressesJouees_) {
                    if(!GameBeloteCommon.hand(repartition_,couleurDessus_).estVide()) {
                        return GameBeloteCommon.hand(repartition_,couleurDessus_).premiereCarte();
                    }
                }
            }
        }


        EnumList<Suit> couleurs_=GameBeloteCommonPlaying.couleursNonOuvertesNonVides(currentHand, plisFaits_, couleursNonAtouts_);
        /*On considere que l'appele est place apres le preneur*/
        if(!couleurs_.isEmpty()) {
            return ouvrir(bid, couleurs_, repartition_, repartitionCartesJouees_, cartesMaitresses_);
        }
        couleurs_ = GameBeloteCommonPlaying.couleursPouvantEtreCoupees(partenaire_, cartesPossibles_, couleurAtout_, couleursNonVides_);
        couleurs_ = GameBeloteCommon.couleursAvecPoints(currentHand, bid, couleurs_);
        if(!couleurs_.isEmpty()) {
            return faireCouperPreneurFigure(bid, couleurs_,repartition_);
        }
        couleurs_ = GameBeloteCommonPlaying.couleursPouvantEtreCoupees(partenaire_, cartesPossibles_, couleurAtout_, couleursNonVides_);
        couleurs_ = GameBeloteCommon.couleursAvecNbPointsInfEg(currentHand, bid, couleurs_, 4);
        if(!couleurs_.isEmpty()) {
            return faireCouper(bid, couleurs_,repartition_,repartitionCartesJouees_);
        }
        couleurs_ = GameBeloteCommonPlaying.couleursDefausseeParJoueurs(adversaire_, bid, cartesPossibles_, couleursNonVides_);
        couleurs_ = GameBeloteCommonPlaying.couleursNonCoupeeParJoueurs(adversaire_, bid, cartesPossibles_, cartesCertaines_, couleurs_);
        if(!couleurs_.isEmpty()) {
            return ouvrir(bid, couleurs_,repartition_,repartitionCartesJouees_,cartesMaitresses_);
        }
        couleurs_ = GameBeloteCommonPlaying.couleursPouvantEtreCoupees(adversaire_, cartesPossibles_, couleurAtout_, couleursNonVides_);
        if(!couleurs_.isEmpty()) {
            return faireCouper(bid, couleurs_,repartition_,repartitionCartesJouees_);
        }
        return faireCouper(bid, couleursNonVides_,repartition_,repartitionCartesJouees_);
    }

    static boolean playedLeading(BidBeloteSuit _bid, byte _taker, Suit _couleurAtout, EnumMap<Suit, HandBelote> _repartitionCartesJouees, EnumMap<Suit, EqList<HandBelote>> _cartesCertaines, Order _or) {
        boolean cartesMaitressesJouees_ = true;
        CardBelote carteCertaine_ = GameBeloteCommon.hand(_cartesCertaines, _couleurAtout, _taker).premiereCarte();
        for (CardBelote c : HandBelote.couleurComplete(_couleurAtout, _or)) {
            if (c.strength(_couleurAtout, _bid)
                    < carteCertaine_.strength(_couleurAtout, _bid)) {
                continue;
            }
            if (_repartitionCartesJouees.getVal(_couleurAtout).contient(c)) {
                continue;
            }
            cartesMaitressesJouees_ = false;
        }
        return cartesMaitressesJouees_;
    }

    private CardBelote playAsTakerDom(BeloteInfoPliEnCours _info) {
        Suit couleurAtout_=bid.getCouleur();
        EnumMap<Suit,HandBelote> repartition_=currentHand.couleurs(bid);
        HandBelote cartesJouees_=_info.getCartesJouees();
        EnumMap<Suit,HandBelote> repartitionCartesJouees_=_info.getRepartitionCartesJouees();
        CustList<TrickBelote> plisFaits_=_info.getPlisFaits();
        EnumMap<Suit,EqList<HandBelote>> cartesPossibles_=_info.getCartesPossibles();
        boolean strictMaitreAtout_;
        EnumMap<Suit,HandBelote> cartesMaitresses_=_info.getCartesMaitresses();
        Bytes partenaire_=_info.getJoueursConfiance();
        Bytes adversaire_ =_info.getJoueursNonConfiance();

        strictMaitreAtout_=_info.isMaitreAtout();
        EnumList<Suit> couleursNonAtouts_=common.couleursNonAtouts();
        EnumList<Suit> couleursNonVides_ = GameBeloteCommon.couleursNonAtoutNonVides(currentHand, couleursNonAtouts_);
        EnumList<Suit> couleursMaitressesAvecPoints_ = GameBeloteCommon.couleursAvecCarteMaitresse(currentHand, cartesJouees_, bid, couleursNonVides_);
        couleursMaitressesAvecPoints_ = GameBeloteCommon.couleursAvecPoints(currentHand, bid, couleursMaitressesAvecPoints_);
        if(GameBeloteCommonPlaying.egaliteCouleurs(couleursMaitressesAvecPoints_, couleursNonVides_)) {
            if(GameBeloteTrickHypothesis.pasAtoutJoueurs(adversaire_, cartesPossibles_, couleurAtout_)) {
                if(!couleursNonVides_.isEmpty()) {
                    /*Si il existe une carte de couleur autre que l'atout*/
                    return GameBeloteCommonPlaying.carteMaitresse(bid, couleursNonVides_, cartesMaitresses_, currentHand, cartesJouees_);
                }
            }
            if(strictMaitreAtout_) {
                return GameBeloteCommon.hand(repartition_,couleurAtout_).premiereCarte();
            }
            EnumList<Suit> couleurs_=GameBeloteCommonPlaying.couleursPouvantEtreCoupees(adversaire_, cartesPossibles_, couleurAtout_, couleursNonVides_);
            if(!couleurs_.isEmpty()) {
                return faireCouper(bid, couleurs_,repartition_,repartitionCartesJouees_);
            }
            couleurs_=GameBeloteCommonPlaying.couleursPouvantEtreCoupees(partenaire_, cartesPossibles_, couleurAtout_, couleursNonVides_);
            if(!couleurs_.isEmpty()) {
                return faireCouperAppele(couleurs_,repartition_,repartitionCartesJouees_);
            }
            couleurs_ = GameBeloteCommonPlaying.couleursOuvertes(plisFaits_, couleursNonVides_);
            if(!couleurs_.isEmpty()) {
                return ouvrirCouleur(bid, couleurs_,repartition_);
            }
            return ouvrirCouleur(bid, couleursNonVides_,repartition_);
        }
        EnumList<Suit> couleurs_=GameBeloteCommonPlaying.couleursSansCarteMaitresse(currentHand, cartesJouees_, bid, couleursNonVides_);
        if(!couleurs_.isEmpty()) {
            return faireCouper(bid, couleurs_, repartition_, repartitionCartesJouees_);
        }
        return faireCouper(bid, couleursNonVides_, repartition_, repartitionCartesJouees_);
    }

    private CardBelote playWhenAtMostOneNormalSuit(BeloteInfoPliEnCours _info) {
        Suit couleurAtout_=bid.getCouleur();
        EnumMap<Suit,HandBelote> repartition_=currentHand.couleurs(bid);
        EnumMap<Suit,EqList<HandBelote>> suites_=_info.getSuitesTouteCouleur();
        EnumMap<Suit,EqList<HandBelote>> cartesPossibles_=_info.getCartesPossibles();
        boolean strictMaitreAtout_;
        EnumMap<Suit,HandBelote> cartesMaitresses_=_info.getCartesMaitresses();
        Bytes partenaire_=_info.getJoueursConfiance();
        Bytes adversaire_ =_info.getJoueursNonConfiance();

        strictMaitreAtout_=_info.isMaitreAtout();
        EnumList<Suit> couleursNonAtouts_=common.couleursNonAtouts();
        /*On cherche la couleur autre que l'atout non vide*/
        EnumList<Suit> couleursNonAtoutNonVides_ = GameBeloteCommon.couleursNonAtoutNonVides(repartition_, couleursNonAtouts_);
        Suit couleurNonAtout_ = couleursNonAtoutNonVides_.first();
        if(!GameBeloteCommon.hand(cartesMaitresses_,couleurNonAtout_).estVide()) {
            if(GameBeloteCommon.hand(suites_,couleurAtout_,0).total()==GameBeloteCommon.hand(cartesMaitresses_,couleurAtout_).total()) {
                /*Si le joueur n'a que des atouts maitres*/
                return GameBeloteCommon.hand(repartition_,couleurAtout_).premiereCarte();
            }
            if(GameBeloteCommon.hand(repartition_,couleurNonAtout_).premiereCarte().points(bid)==0) {
                return GameBeloteCommon.hand(repartition_,couleurNonAtout_).premiereCarte();
            }
            if(currentHand.total()==2) {
                return GameBeloteCommon.hand(repartition_,couleurNonAtout_).premiereCarte();
            }
            if(GameBeloteCommon.hand(cartesMaitresses_,couleurAtout_).estVide()) {
                return GameBeloteCommon.hand(repartition_,couleurNonAtout_).premiereCarte();
            }
            return GameBeloteCommon.hand(repartition_,couleurAtout_).premiereCarte();
        }
        EnumList<Suit> couleursPouvantEtreCoupees_ =
                GameBeloteCommonPlaying.couleursPouvantEtreCoupees(partenaire_, cartesPossibles_, couleurAtout_, couleursNonAtoutNonVides_);
        if(GameBeloteCommonPlaying.egaliteCouleurs(couleursPouvantEtreCoupees_, couleursNonAtoutNonVides_)) {
            return GameBeloteCommon.hand(repartition_,couleurNonAtout_).premiereCarte();
        }
        if(strictMaitreAtout_) {
            return GameBeloteCommon.hand(repartition_,couleurAtout_).premiereCarte();
        }
        if(GameBeloteCommon.hand(repartition_,couleurAtout_).total()==GameBeloteCommon.hand(cartesMaitresses_,couleurAtout_).total()) {
            Bytes joueursPouvantCouper_ = GameBeloteCommonPlaying.joueursSusceptiblesCoupe(cartesPossibles_,couleurNonAtout_,couleurAtout_,adversaire_);
            if(GameBeloteTeamsRelation.egaliteJoueurs(joueursPouvantCouper_,adversaire_)) {
                return GameBeloteCommon.hand(repartition_,couleurAtout_).premiereCarte();
            }
        }
        return GameBeloteCommon.hand(repartition_,couleurNonAtout_).premiereCarte();
    }

    private CardBelote playWhenOnlyTrumps(BeloteInfoPliEnCours _info) {
        Suit couleurAtout_=bid.getCouleur();
        EnumMap<Suit,HandBelote> repartition_=currentHand.couleurs(bid);
        EnumMap<Suit,EqList<HandBelote>> suites_=_info.getSuitesTouteCouleur();
        EnumMap<Suit,HandBelote> cartesMaitresses_=_info.getCartesMaitresses();
        /*Si le joueur ne possede que de l'atout*/
        if(GameBeloteCommon.suite(suites_,couleurAtout_).size()==1) {
            if(GameBeloteCommon.hand(suites_,couleurAtout_,0).total()==GameBeloteCommon.hand(cartesMaitresses_,couleurAtout_).total()) {
                /*Si le joueur n'a que des atouts maitres*/
                return GameBeloteCommon.hand(repartition_,couleurAtout_).premiereCarte();
            }
            return GameBeloteCommon.hand(repartition_,couleurAtout_).derniereCarte();
        }
        if(GameBeloteCommon.hand(cartesMaitresses_,couleurAtout_).estVide()) {
            return GameBeloteCommon.hand(repartition_,couleurAtout_).derniereCarte();
        }
        if(currentHand.total()<=2*GameBeloteCommon.hand(cartesMaitresses_,couleurAtout_).total()) {
            /*La GameBeloteCommon.main du joueur contient plus d atouts maitres que des atouts non maitres*/
            return GameBeloteCommon.hand(suites_,couleurAtout_,0).premiereCarte();
        }
        return GameBeloteCommon.hand(repartition_,couleurAtout_).derniereCarte();
    }

    CardBelote entameSansAtoutToutAtout(BeloteInfoPliEnCours _info) {
        /*Jeu sans atout ou tout atout*/
        if(_info.isMaitreJeu()) {
            return playBestCards(_info);
        }
        if(currentStatus == Status.TAKER) {
            return playAsTaker(_info);
        }
        if(currentStatus == Status.CALLED_PLAYER) {
            return playAsCalledPlayer(_info);
        }
        return playAsDefender(_info);
    }

    private CardBelote playBestCards(BeloteInfoPliEnCours _info) {
        EnumMap<Suit,HandBelote> repartition_=currentHand.couleurs(bid);
        EnumList<Suit> couleursMaitres_= _info.getCouleursMaitresses();
        return jeuMainMaitresseCouleursEgales(repartition_,couleursMaitres_);
    }

    private CardBelote playAsDefender(BeloteInfoPliEnCours _info) {
        EnumList<Suit> couleurs_;
        EnumMap<Suit,HandBelote> repartition_=currentHand.couleurs(bid);
        EnumMap<Suit,HandBelote> repartitionCartesJouees_= _info.getRepartitionCartesJouees();
        CustList<TrickBelote> plisFaits_= _info.getPlisFaits();
        EnumMap<Suit,EqList<HandBelote>> cartesPossibles_= _info.getCartesPossibles();
        EnumMap<Suit,HandBelote> cartesMaitresses_= _info.getCartesMaitresses();
        Bytes adversaire_ = _info.getJoueursNonConfiance();
        EnumList<Suit> couleursNonAtouts_=GameBeloteCommon.couleurs();
        EnumList<Suit> couleursNonVides_ = GameBeloteCommon.couleursNonAtoutNonVides(currentHand, couleursNonAtouts_);
        /*Cas d'un defenseur*/
        couleurs_ = GameBeloteCommonPlaying.couleursNonOuvertesNonVides(currentHand, plisFaits_, couleursNonVides_);
        if(!couleurs_.isEmpty()) {
            return ouvrir(bid, couleurs_, repartition_, repartitionCartesJouees_, cartesMaitresses_);
        }
        couleurs_ = GameBeloteCommonPlaying.couleursDefausseeParJoueurs(adversaire_, bid, cartesPossibles_, couleursNonVides_);
        if(!couleurs_.isEmpty()) {
            return ouvrir(bid, couleurs_,repartition_,repartitionCartesJouees_,cartesMaitresses_);
        }
        return faireCouper(bid, couleursNonVides_,repartition_,repartitionCartesJouees_);
    }

    private CardBelote playAsCalledPlayer(BeloteInfoPliEnCours _info) {
        EnumList<Suit> couleurs_;
        EnumMap<Suit,HandBelote> repartition_=currentHand.couleurs(bid);
        EnumMap<Suit,HandBelote> repartitionCartesJouees_= _info.getRepartitionCartesJouees();
        CustList<TrickBelote> plisFaits_= _info.getPlisFaits();
        EnumMap<Suit,EqList<HandBelote>> cartesPossibles_= _info.getCartesPossibles();
        EnumMap<Suit,EqList<HandBelote>> cartesCertaines_= _info.getCartesCertaines();
        EnumMap<Suit,HandBelote> cartesMaitresses_= _info.getCartesMaitresses();
        Bytes adversaire_ = _info.getJoueursNonConfiance();
        EnumList<Suit> couleursNonAtouts_=GameBeloteCommon.couleurs();
        EnumList<Suit> couleursNonVides_ = GameBeloteCommon.couleursNonAtoutNonVides(currentHand, couleursNonAtouts_);
        if (!lastSeenHand.estVide()) {
            Suit couleurDessus_=lastSeenHand.premiereCarte().couleur();
            if(!GameBeloteCommon.hand(cartesCertaines_,couleurDessus_,taker).estVide()) {
                Order or_ = bid.getOrdre();
                boolean cartesMaitressesJouees_ = playedLeading(bid, taker, couleurDessus_, repartitionCartesJouees_, cartesCertaines_, or_);
                if(cartesMaitressesJouees_) {
                    if(!GameBeloteCommon.hand(repartition_,couleurDessus_).estVide()) {
                        return GameBeloteCommon.hand(repartition_,couleurDessus_).premiereCarte();
                    }
                }
            }
        }
        /*On considere que l'appele est place apres le preneur*/
        couleurs_ = GameBeloteCommonPlaying.couleursNonOuvertesNonVides(currentHand, plisFaits_, couleursNonVides_);
        if(!couleurs_.isEmpty()) {
            return ouvrir(bid, couleurs_, repartition_, repartitionCartesJouees_, cartesMaitresses_);
        }
        couleurs_ = GameBeloteCommonPlaying.couleursDefausseeParJoueurs(adversaire_, bid, cartesPossibles_, couleursNonVides_);
        if(!couleurs_.isEmpty()) {
            return ouvrir(bid, couleurs_,repartition_,repartitionCartesJouees_,cartesMaitresses_);
        }
        return faireCouper(bid, couleursNonVides_,repartition_,repartitionCartesJouees_);
    }

    private CardBelote playAsTaker(BeloteInfoPliEnCours _info) {
        EnumMap<Suit,HandBelote> repartition_=currentHand.couleurs(bid);
        HandBelote cartesJouees_= _info.getCartesJouees();
        EnumMap<Suit,HandBelote> repartitionCartesJouees_= _info.getRepartitionCartesJouees();
        EnumMap<Suit,HandBelote> cartesMaitresses_= _info.getCartesMaitresses();
        EnumList<Suit> couleursNonAtouts_=GameBeloteCommon.couleurs();
        EnumList<Suit> couleursNonVides_ = GameBeloteCommon.couleursNonAtoutNonVides(currentHand, couleursNonAtouts_);
        EnumList<Suit> couleursMaitressesAvecPoints_ = GameBeloteCommon.couleursAvecCarteMaitresse(currentHand, cartesJouees_, bid, couleursNonVides_);
        couleursMaitressesAvecPoints_ = GameBeloteCommon.couleursAvecPoints(currentHand, bid, couleursMaitressesAvecPoints_);
        EnumList<Suit> couleurs_;
        if(GameBeloteCommonPlaying.egaliteCouleurs(couleursNonVides_, couleursMaitressesAvecPoints_)) {
            /*Il existe une carte de couleur autre que l'atout*/
            return GameBeloteCommonPlaying.carteMaitresse(bid, couleursNonVides_, cartesMaitresses_, currentHand, cartesJouees_);
        }
        couleurs_ = GameBeloteCommonPlaying.couleursSansCarteMaitresse(currentHand, cartesJouees_, bid, couleursNonVides_);
        if(!couleurs_.isEmpty()) {
            return faireCouper(bid, couleurs_, repartition_, repartitionCartesJouees_);
        }
        return faireCouper(bid, couleursNonVides_, repartition_, repartitionCartesJouees_);
    }

    private static CardBelote jeuMainMaitresseCouleurDominante(
            EnumMap<Suit,EqList<HandBelote>> _suites,
            EnumMap<Suit,HandBelote> _repartition,
            EnumList<Suit> _couleursMaitres,
            Suit _couleurAtout) {
        if(!GameBeloteCommon.suite(_suites,_couleurAtout).isEmpty()) {
            return GameBeloteCommon.hand(_suites,_couleurAtout,0).premiereCarte();
        }
        EnumList<Suit> couleurs_ = GameBeloteCommon.couleursNonAtoutNonVides(_repartition, _couleursMaitres);
        return GameBeloteCommon.hand(_repartition,couleurs_.first()).premiereCarte();
    }
    private static CardBelote jeuMainMaitresseCouleursEgales(
            EnumMap<Suit,HandBelote> _repartition,
            EnumList<Suit> _couleursMaitres) {
        EnumList<Suit> couleurs_ = GameBeloteCommon.couleursNonAtoutNonVides(_repartition, _couleursMaitres);
        return GameBeloteCommon.hand(_repartition,couleurs_.first()).premiereCarte();
    }
    public static byte nombreCartesPoints(EnumMap<Suit,HandBelote> _repartition,BidBeloteSuit _contrat,Suit _couleur) {
        return _repartition.getVal(_couleur).nombreCartesPoints(_contrat);
    }

    private static CardBelote faireCouperPreneurFigure(BidBeloteSuit _bid,
                                                       EnumList<Suit> _couleurs, EnumMap<Suit, HandBelote> _repartition) {
        EnumList<Suit> couleurs_ = GameBeloteCommon.couleursLesPlusLongues(_repartition, _couleurs);
        couleurs_ = GameBeloteCommon.couleursLesPlusBasses(_repartition, _bid, couleurs_);
        couleurs_ = GameBeloteCommon.couleursAvecLePlusPetitNbPoints(_repartition, _bid, couleurs_);
        return GameBeloteCommon.hand(_repartition,couleurs_.first()).premiereCarte();
    }
    static CardBelote ouvrir(BidBeloteSuit _bid, EnumList<Suit> _couleurs,
                                     EnumMap<Suit, HandBelote> _repartition,
                                     EnumMap<Suit, HandBelote> _repartitionCartesJouees,
                                     EnumMap<Suit, HandBelote> _cartesMaitresses) {
        EnumList<Suit> couleurs_ = GameBeloteCommon.couleursLesPlusLongues(_cartesMaitresses, _couleurs);
        //maitre
        couleurs_ = GameBeloteCommon.couleursLesPlusCourtes(_repartitionCartesJouees, couleurs_);
        //jouees
        couleurs_ = GameBeloteCommon.couleursLesPlusLongues(_repartition, couleurs_);
        //longues
        couleurs_ = GameBeloteCommon.couleursLesPlusBasses(_repartition, _bid, couleurs_);
        //basses
        if(!GameBeloteCommon.hand(_cartesMaitresses,couleurs_.first()).estVide()) {
            return GameBeloteCommon.hand(_repartition,couleurs_.first()).premiereCarte();
        }
        return GameBeloteCommon.hand(_repartition,couleurs_.first()).derniereCarte();
    }
    static CardBelote ouvrirCouleur(BidBeloteSuit _bid,
                                            EnumList<Suit> _couleurs, EnumMap<Suit, HandBelote> _repartition) {
        EnumList<Suit> couleurs_ = GameBeloteCommon.couleursLesPlusLongues(_repartition, _couleurs);
        couleurs_ = GameBeloteCommon.couleursLesPlusBasses(_repartition, _bid, couleurs_);
        return GameBeloteCommon.hand(_repartition,couleurs_.first()).premiereCarte();
    }

    static CardBelote faireCouper(BidBeloteSuit _bid, EnumList<Suit> _couleurs,
                                          EnumMap<Suit, HandBelote> _repartition,
                                          EnumMap<Suit, HandBelote> _repartitionCartesJouees) {
        EnumList<Suit> couleurs_ = GameBeloteCommon.couleursLesPlusLongues(_repartitionCartesJouees, _couleurs);
        couleurs_ = GameBeloteCommon.couleursLesPlusLongues(_repartition, couleurs_);
        couleurs_ = GameBeloteCommon.couleursLesPlusBasses(_repartition, _bid, couleurs_);
        return GameBeloteCommon.hand(_repartition,couleurs_.first()).derniereCarte();
    }
    static CardBelote faireCouperAppele(
            EnumList<Suit> _couleurs,EnumMap<Suit,HandBelote> _repartition,
            EnumMap<Suit,HandBelote> _repartitionCartesJouees) {
        EnumList<Suit> couleurs_ = GameBeloteCommon.couleursLesPlusCourtes(_repartitionCartesJouees, _couleurs);
        couleurs_ = GameBeloteCommon.couleursLesPlusCourtes(_repartition, couleurs_);
        return GameBeloteCommon.hand(_repartition,couleurs_.first()).derniereCarte();
    }
}
