package cards.tarot;

import cards.consts.CardChar;
import cards.consts.Hypothesis;
import cards.consts.Suit;
import cards.tarot.enumerations.*;
import code.util.*;

public final class DoneTrickInfo {

    private TrickTarot progressingTrick;
    private CustList<TrickTarot> tricks;

    private EqList<EnumList<Miseres>> declaresMiseres;

    private EqList<HandTarot> handfuls;
    private BidTarot bid;
    private HandTarot calledCards;
    private Numbers<Integer> handLengths;
    private HandTarot playedCards;
    private boolean playedCalledCard;

    public DoneTrickInfo(TrickTarot _progressingTrick, CustList<TrickTarot> _tricks,
                         EqList<EnumList<Miseres>> _declaresMiseres,
                         EqList<HandTarot> _handfuls, BidTarot _bid, HandTarot _calledCards,
                         Numbers<Integer> _handLengths) {
        progressingTrick = _progressingTrick;
        tricks = _tricks;
        declaresMiseres = _declaresMiseres;
        handfuls = _handfuls;
        bid = _bid;
        calledCards = _calledCards;
        handLengths = _handLengths;
    }

    /**
     Retourne l'ensemble des cartes des couleurs (avec l'Excuse) probablement
     possedees par les autres joueurs Pour premier indice (premier get)
     couleur, deuxieme indice joueur
     @param _numero
     */
    public EnumMap<Suit,EqList<HandTarot>> cartesPossibles(TeamsRelation _teamRel,
                                                           CustList<TrickTarot> _plisFaits,
                                                           HandTarot _cartesJoueur, byte _numero,
                                                           HandTarot _lastHand) {
        playedCards = cartesJoueesEnCours(_teamRel,_numero);
        EnumMap<Suit,HandTarot> plRep_ = playedCards.couleurs();
        boolean plExcuse_ = playedCards.contient(CardTarot.EXCUSE);
        playedCalledCard = playedCards.contientCartes(calledCards);
        EnumMap<Suit,HandTarot> curRep_ = _cartesJoueur.couleurs();
        boolean containsExcuse_ = _cartesJoueur.contient(CardTarot.EXCUSE);
        EnumMap<Suit,EqList<HandTarot>> m = new EnumMap<Suit,EqList<HandTarot>>();
        EqList<HandTarot> possibleExcuse_ = new EqList<HandTarot>();
        byte nombreJoueurs_ = _teamRel.getNombreDeJoueurs();
        for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
            possibleExcuse_.add(new HandTarot());
            if (containsExcuse_) {
                possibleExcuse_.last().ajouter(CardTarot.excuse());
                continue;
            }
            if (!plExcuse_) {
                possibleExcuse_.last().ajouter(CardTarot.excuse());
            }
            if (declaresMiseres.get(joueur_).containsObj(Miseres.POINT)
                    || declaresMiseres.get(joueur_).containsObj(Miseres.TRUMP)) {
                possibleExcuse_.get(joueur_).supprimerCartes();
            }
        }
        possibleExcuse_.add(new HandTarot());
        if (bid.getJeuChien() != PlayingDog.WITH) {
            if (!plExcuse_ && !containsExcuse_) {
                possibleExcuse_.last().ajouter(CardTarot.excuse());
            }
        } else {
            /*
            Si le contrat
            est Petite ou
            Garde alors
            l'Excuse ne
            peut pas
            appartenir au
            chien
            */
            for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
                if (joueur_ == _numero) {
                    continue;
                }
                if (_teamRel.getTaker() != joueur_) {
                    // L'Excuse du chien (si il est vu) ne
                    // peut etre possedee que par le preneur
                    if (!possibleExcuse_.get(joueur_).estVide()
                            && _lastHand.contient(
                            CardTarot.excuse())) {
                        possibleExcuse_.get(joueur_).jouer(CardTarot.excuse());
                    }
                }
            }
        }
        for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
            // L'Excuse dans
            // une poignee
            // annule toute
            // possibilite
            // qu'un autre
            // joueur ait
            // celle-ci
            if (joueur_ == _numero) {
                continue;
            }
            int nbHandfuls_ = handfuls.size();
            for (byte i = CustList.FIRST_INDEX; i < nbHandfuls_; i++) {
                if (!getPoignee(i).contient(CardTarot.excuse())) {
                    continue;
                }
                /* The current poignee contains the Excuse*/
                if (i != joueur_) {
                    possibleExcuse_.get(joueur_).supprimerCartes();
                }
            }
            if (progressingTrick.contient(CardTarot.excuse())) {
                possibleExcuse_.get(joueur_).supprimerCartes();
            }
        }
        if (bid.getJeuChien() != PlayingDog.WITH) {
            for (HandTarot poignee_ : handfuls) {
                if (!possibleExcuse_.last().estVide()
                        && poignee_.contient(CardTarot.excuse())) {
                    possibleExcuse_.last().jouer(CardTarot.excuse());
                }
            }
            if (!possibleExcuse_.last().estVide()
                    && progressingTrick.contient(CardTarot.excuse())) {
                possibleExcuse_.last().jouer(CardTarot.excuse());
            }
        }
        m.put(CardTarot.EXCUSE.couleur(), possibleExcuse_);
        m.put(Suit.TRUMP,atoutsPossibles(_teamRel,plRep_.getVal(Suit.TRUMP), _plisFaits,
                _cartesJoueur, _numero,_lastHand));
        for (Suit couleur_ : Suit.couleursOrdinaires()) {
            // On fait une boucle sur les
            // couleurs autres que l'atout
            m.put(couleur_,cartesPossibles(_teamRel,couleur_,
                    _plisFaits,_numero,_cartesJoueur, _lastHand));
        }
        return m;
    }

    /**
     Retourne l'ensemble des atouts (sans l'Excuse) probablement possedes par
     les autres joueurs
     @param _numero
     */
    EqList<HandTarot> atoutsPossibles(TeamsRelation _teamRel,HandTarot _atoutsJoues,
                                              CustList<TrickTarot> _plisFaits, HandTarot _curHand, byte _numero,
                                              HandTarot _lastHand) {
        EnumMap<Suit,HandTarot> curRep_ = _curHand.couleurs();
        HandTarot curTr_ = curRep_.getVal(Suit.TRUMP);
        EqList<HandTarot> m = new EqList<HandTarot>();
        byte nombreJoueurs_ = _teamRel.getNombreDeJoueurs();

        for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
            m.add(new HandTarot());
            if(joueur_ == _numero) {
                m.last().ajouterCartes(curTr_);
                continue;
            }
            for (CardTarot carte_ : HandTarot.atoutsSansExcuse()) {
                if (!_atoutsJoues.contient(carte_)
                        && !curTr_.contient(carte_)) {
                    m.last().ajouter(carte_);
                }
            }
            if (DoneTrickInfo.defausseTarot(joueur_, _plisFaits)) {
                // Les joueurs se defaussant
                // sur atout ou couleur
                // demandee ne peuvent pas
                // avoir de l'atout
                m.get(joueur_).supprimerCartes();
            }
            if (declaresMiseres.get(joueur_).containsObj(Miseres.TRUMP)) {
                m.get(joueur_).supprimerCartes();
            }
            if (declaresMiseres.get(joueur_).containsObj(Miseres.POINT)) {
                m.get(joueur_).supprimerCartes(m.get(joueur_).bouts());
            }
        }
        m.add(new HandTarot());
        if (bid.getJeuChien() == PlayingDog.WITH) {
            /*
            Les atouts ecartes sont annonces donc certains de faire partie du
            chien
            */
            m.last().ajouterCartes(
                    _plisFaits.first().getCartes().couleur(Suit.TRUMP));
        } else {
            /*
            Si le chien est inconnu de tous alors n'importe quel atout non
            joue et non possede par le joueur peut etre dans le chien
            */
            for (CardTarot carte_ : HandTarot.atoutsSansExcuse()) {
                if (!_atoutsJoues.contient(carte_)
                        && !curTr_.contient(carte_)) {
                    m.last().ajouter(carte_);
                }
            }
        }
        CustList<TrickTarot> plis_ = new CustList<TrickTarot>(_plisFaits);
        plis_.add(progressingTrick);
        for(TrickTarot pli_:plis_) {
            if (!pli_.getVuParToutJoueur()) {
                continue;
            }
            Suit couleurDemande_=pli_.couleurDemandee();
            for(CardTarot c: pli_) {
                byte joueur_ = pli_.joueurAyantJouePliEnCours(c,nombreJoueurs_);
                if (joueur_ == _numero) {
                    continue;
                }
                Numbers<Byte> joueursAvant_ = pli_.joueursAyantJoueAvant(joueur_,nombreJoueurs_, _teamRel.getRules().getDealing());
                byte forceLoc_ = c.strength(couleurDemande_);
                byte max_ = 0;
                byte ramasseurVirtuel_ = joueur_;
                //joueursAvant non vide
                for(byte j: joueursAvant_) {
                    CardTarot carte_ = pli_.carteDuJoueur(j,nombreJoueurs_);
                    byte forceLoc2_ = carte_.strength(couleurDemande_);
                    if(forceLoc2_ < forceLoc_) {
                        continue;
                    }
                    if(forceLoc2_ < max_) {
                        continue;
                    }
                    max_ = forceLoc2_;
                    ramasseurVirtuel_ = j;
                }
                if(ramasseurVirtuel_ == joueur_) {
                    continue;
                }
                HandTarot cartesExclues_ = new HandTarot();
                for(CardTarot c2_: m.get(joueur_)) {
                    if(c2_.strength(couleurDemande_) < max_) {
                        continue;
                    }
                    cartesExclues_.ajouter(c2_);
                }
                m.get(joueur_).supprimerCartes(cartesExclues_);
            }

        }
        if (bid.getJeuChien() == PlayingDog.WITH) {
            for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
                if (joueur_ == _numero) {
                    continue;
                }
                if (_teamRel.getTaker() != joueur_) {
                    // Les atouts du chien (si il est vu) ne peuvent possedes
                    // que par le preneur
                    for (CardTarot carte_ : _lastHand) {
                        if (!Suit.couleursOrdinaires().containsObj(carte_.couleur())
                                && m.get(joueur_).contient(carte_)) {
                            m.get(joueur_).jouer(carte_);
                        }
                    }
                }
                /*
                Les atouts eventuellement ecartes au chien sont vus par les
                autres joueurs et ne peuvent pas etre joues dans les plis
                suivants
                */
                for (CardTarot carte_ : _plisFaits.first()) {
                    if (carte_.couleur() == Suit.TRUMP && m.get(joueur_).contient(carte_)) {
                        m.get(joueur_).jouer(carte_);
                    }
                }
            }
        }
        for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
            if (joueur_ == _numero) {
                continue;
            }
            int nbHandfuls_ = handfuls.size();
            for (byte joueur2_ = CustList.FIRST_INDEX; joueur2_ < nbHandfuls_; joueur2_++) {
                if (joueur2_ != joueur_) {
                    m.get(joueur_).supprimerCartes(getPoignee(joueur2_));
                } else if (getPoignee(joueur_).contient(CardTarot.excuse())) {
                    HandTarot atoutsPoignee_ = new HandTarot();
                    for (CardTarot c: m.get(joueur_)) {
                        if (!getPoignee(joueur_)
                                .contient(c)) {
                            continue;
                        }
                        atoutsPoignee_.ajouter(c);
                    }
                    m.set(joueur_, atoutsPoignee_);
                }
            }
            for (CardTarot carte_ : progressingTrick) {
                if (m.get(joueur_).contient(carte_)) {
                    m.get(joueur_).jouer(carte_);
                }
            }
            if (!progressingTrick.aJoue(joueur_, nombreJoueurs_)) {
                continue;
            }
            CardTarot carteDuJoueur_ = progressingTrick.carteDuJoueur(
                    joueur_, nombreJoueurs_);
            Suit couleurDemandee_ = progressingTrick.couleurDemandee();
            if (Suit.couleursOrdinaires().containsObj(carteDuJoueur_.couleur())
                    && couleurDemandee_ != carteDuJoueur_.couleur()) {
                /*
                    Si le
                    joueur
                    a
                    joue
                    une
                    carte
                    autre
                    que
                    l'atout
                    et
                    l'Excuse
                    et
                    que
                    la
                    couleur
                    demandee
                    alors
                    il se
                    defausse
                    */
                m.get(joueur_).supprimerCartes();
            }
        }
        if (bid.getJeuChien() != PlayingDog.WITH) {
            for (HandTarot main_ : handfuls) {
                for (CardTarot carte_ : main_) {
                    if (m.last().contient(carte_)) {
                        m.last().jouer(carte_);
                    }
                }
            }
            for (CardTarot carte_ : progressingTrick) {
                if (m.last().contient(carte_)) {
                    m.last().jouer(carte_);
                }
            }
        }
        byte joueur_ = 0;
        for (HandTarot main_ : m) {
            if (joueur_ == nombreJoueurs_) {
                break;
            }
            if (joueur_ == _numero) {
                joueur_++;
                continue;
            }
            if (main_.estVide()) {
                joueur_++;
                continue;
            }
            //filtre sur le jeu d'une carte couleur atout apres un adversaire ramasseur
            HandTarot atoutsFiltres_ = sousCoupeTarot(_teamRel,_numero, _curHand,joueur_,
                    main_, _plisFaits);
            m.set( joueur_, atoutsFiltres_);
            joueur_++;
        }
        joueur_ = 0;
        for (HandTarot main_ : m) {
            if (joueur_ == nombreJoueurs_) {
                break;
            }
            if (joueur_ == _numero) {
                joueur_++;
                continue;
            }
            if (main_.estVide()) {
                joueur_++;
                continue;
            }
            //filtre sur la fourniture d'un atout a une couleur
            HandTarot atoutsFiltres_ = coupeTarot(_teamRel,_numero, _curHand,joueur_,
                    main_, _plisFaits);
            m.set( joueur_, atoutsFiltres_);
            joueur_++;
        }
        if(playedCalledCard) {
            joueur_ = 0;
            for (HandTarot main_ : m) {
                if (joueur_ == nombreJoueurs_) {
                    break;
                }
                if (joueur_ == _numero) {
                    joueur_++;
                    continue;
                }
                if (main_.estVide()) {
                    joueur_++;
                    continue;
                }
                if(petitJoueDemandeAtoutRamasseurAdv(_teamRel,joueur_,_plisFaits)) {
                    main_.supprimerCartes();
                }
                //filtre sur la fourniture d'un atout a une couleur
                joueur_++;
            }
        }
        return m;
    }

    /**
     Retourne l'ensemble des cartes d'une meme couleur autre que l'atout
     probablement possedees par les autres joueurs on tient compte du pli en
     cours
     @param _numero
     */
    EqList<HandTarot> cartesPossibles(TeamsRelation _teamRel,Suit _couleur,
                                              CustList<TrickTarot> _plisFaits,
                                              byte _numero,HandTarot _curHand,
                                              HandTarot _lastHand) {
        EqList<HandTarot> m = new EqList<HandTarot>();
        byte nombreJoueurs_ = _teamRel.getNombreDeJoueurs();
        for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
            m.add(new HandTarot());
            if(joueur_ == _numero) {
                m.last().ajouterCartes(_curHand.couleur(_couleur));
                continue;
            }
            for (CardTarot carte_ : HandTarot.couleurComplete(_couleur)) {
                if (!playedCards.contient(carte_)
                        && !_curHand.contient(carte_)) {
                    m.last().ajouter(carte_);
                }
            }
            if (DoneTrickInfo.defausseTarot(joueur_, _couleur, _plisFaits)
                    || DoneTrickInfo.coupeTarot(_couleur, joueur_, _plisFaits)) {
                // Les joueurs
                // se defaussant
                // sur atout ou
                // couleur
                // demandee ne
                // peuvent pas
                // avoir de
                // l'atout
                m.get(joueur_).supprimerCartes();
            }
            if (declaresMiseres.get(joueur_).containsObj(Miseres.SUIT)) {
                m.get(joueur_).supprimerCartes();
            }
            if (declaresMiseres.get(joueur_).containsObj(Miseres.POINT)
                    || declaresMiseres.get(joueur_).containsObj(Miseres.CHARACTER)) {
                m.get(joueur_).supprimerCartes(m.get(joueur_).charCardsBySuit(_couleur));
            }
            if (declaresMiseres.get(joueur_).containsObj(Miseres.LOW_CARDS)) {
                m.get(joueur_).supprimerCartes(
                        m.get(joueur_).cartesBasses(_couleur));
            }
        }
        m.add(new HandTarot());
        if (bid.getJeuChien() != PlayingDog.WITH) {
            for (CardTarot carte_ : HandTarot.couleurComplete(_couleur)) {
                if (!playedCards.contient(carte_)
                        && !_curHand.contient(carte_)) {
                    m.last().ajouter(carte_);
                }
            }
        } else {
            if (_numero == _teamRel.getTaker()) {
                /*
            Le preneur sait ce qu'il a mis au chien
            pour une Petite ou une Garde
            */
                m.last().ajouterCartes(
                        _plisFaits.first().getCartes().couleur(_couleur));
            } else {
                if (_plisFaits.first().getCartes().tailleCouleur(Suit.TRUMP) > 0) {
                    /* Si le preneur est oblige
                    d 'ecarter des atouts
                    alors les cartes autre que
                    le roi de couleur
                    du chien sont
                    certainement ecartees*/
                    for (CardTarot carte_ : _lastHand
                            .couleur(_couleur)) {
                        if (carte_.getNomFigure() == CardChar.KING) {
                            continue;
                        }
                        m.last().ajouter(carte_);
                    }
                } else {
                    for (CardTarot carte_ : HandTarot.couleurComplete(_couleur)) {
                        if (carte_.getNomFigure() == CardChar.KING) {
                            continue;
                        }
                        if (!playedCards.contient(carte_)
                                && !_curHand.contient(carte_)) {
                            m.last().ajouter(carte_);
                        }
                    }
                }
            }
            for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
                if (joueur_ == _numero) {
                    continue;
                }
                if (_teamRel.getTaker() != joueur_) {
                    // Les cartes d'une couleur du chien (si il est vu) ne
                    // peuvent possedes que par le preneur ou etre ecartees
                    for (CardTarot carte_ : _lastHand) {
                        if (carte_.couleur() == _couleur
                                && m.get(joueur_).contient(carte_)) {
                            m.get(joueur_).jouer(carte_);
                        }
                    }
                } else if (_plisFaits.first().getCartes().tailleCouleur(Suit.TRUMP) > 0) {
                    /* Si le preneur a ecarte des
                    atouts dans le chien alors
                    les cartes autres que
                    les atouts incluant
                    l 'Excuse et les rois
                    ne peuvent pas etre
                    possedees par le preneur*/
                    for (CardTarot carte_ : HandTarot.couleurComplete(_couleur)) {
                        if (carte_.getNomFigure() == CardChar.KING) {
                            continue;
                        }
                        if (m.get(_teamRel.getTaker()).contient(carte_)) {
                            m.get(_teamRel.getTaker()).jouer(carte_);
                        }
                    }
                }
            }
        }
        /*
        Les cartes jouees dans le pli en cours ne peuvent pas (ou plus) etre
        possedees par les joueurs ni faire partie de l'ecart
        */
        for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
            if (joueur_ == _numero) {
                continue;
            }
            for (CardTarot carte_ : progressingTrick.getCartes()) {
                if (m.get(joueur_).contient(carte_)) {
                    m.get(joueur_).jouer(carte_);
                }
            }
        }
        for (CardTarot carte_ : progressingTrick.getCartes()) {
            if (m.last().contient(carte_)) {
                m.last().jouer(carte_);
            }
        }
        if (progressingTrick.couleurDemandee() == _couleur) {
            for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
                if (joueur_ == _numero) {
                    continue;
                }
                if (!progressingTrick.aJoue(joueur_, nombreJoueurs_)) {
                    continue;
                }
                CardTarot carteJouee_ = progressingTrick.carteDuJoueur(
                        joueur_, nombreJoueurs_);
                if (carteJouee_.couleur() != _couleur
                        && carteJouee_ != CardTarot.EXCUSE) {
                    /*
                        Si un joueur a joue
                        une carte autre que
                        l'Excuse et pas de la
                        couleur demandee dans
                        le pli en cours,
                        alors il coupe ou se
                        defausse
                        */
                    m.get(joueur_).supprimerCartes();
                }
            }
        }
        byte joueur_ = 0;
        for (HandTarot couleurLoc_ : m) {
            if (joueur_ == nombreJoueurs_) {
                break;
            }
            if (joueur_ == _numero) {
                joueur_++;
                continue;
            }
            if (couleurLoc_.estVide()) {
                joueur_++;
                continue;
            }
            Suit noCouleur_ = couleurLoc_.premiereCarte()
                    .couleur();
            //filtre sur le jeu d'une carte couleur ordinaire apres un adversaire ramasseur
            HandTarot atoutsFiltres_ = joueCarteBasseTarot(_teamRel,_numero,_curHand,
                    joueur_, noCouleur_, couleurLoc_, _plisFaits);
            m.set( joueur_, atoutsFiltres_);
            joueur_++;
        }
        if (playedCalledCard) {
            joueur_ = 0;
            for (HandTarot couleurLoc_ : m) {
                if (joueur_ == nombreJoueurs_) {
                    break;
                }
                if (joueur_ == _numero) {
                    joueur_++;
                    continue;
                }
                if (couleurLoc_.estVide()) {
                    joueur_++;
                    continue;
                }
                Suit noCouleur_ = couleurLoc_.premiereCarte()
                        .couleur();
                HandTarot filteredCharacters_ = playCharacterCardTarot(
                        _teamRel,joueur_, noCouleur_, couleurLoc_, _plisFaits);
                m.set( joueur_, filteredCharacters_);
                joueur_++;
            }
        }
        //playCharacterCardTarot
        return m;
    }

    /**
     Retourne l'ensemble des cartes certainement possedees par les joueurs
     classees par couleur puis par joueurs
     @param cartesPossibles
     l'ensemble des cartes probablement possedees par les joueurs
     ou a l'ecart (visible uniquement pour un preneur ayant demande
     petite ou garde ou partiellement lorsque des atouts sont
     ecartes) Cet ensemble peut etre reduit apres appel de methode
     @return l'ensemble des cartes dont on connait par deduction la main
     */
    public EnumMap<Hypothesis,EnumMap<Suit,EqList<HandTarot>>> cartesCertaines(
            TeamsRelation _teamRel,
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles) {
        Numbers<Byte> joueursRepartitionConnue_ = new Numbers<Byte>();
        Numbers<Byte> joueursRepartitionConnue2_ = new Numbers<Byte>();
        Numbers<Byte> joueursRepartitionConnueMemo_ = new Numbers<Byte>();
        Numbers<Byte> joueursRepartitionInconnue_ = new Numbers<Byte>();
        EnumMap<Suit,EqList<HandTarot>> cartesCertaines_ = new EnumMap<Suit,EqList<HandTarot>>();
        EnumMap<Suit,EqList<HandTarot>> cartesPossibles_ = new EnumMap<Suit,EqList<HandTarot>>(
                _cartesPossibles);
        EnumMap<Hypothesis,EnumMap<Suit,EqList<HandTarot>>> retour_ = new EnumMap<Hypothesis,EnumMap<Suit,EqList<HandTarot>>>();
        byte nombreJoueurs_ = _teamRel.getNombreDeJoueurs();
        int nombreDApparitionCarte_;
        /*
        Indique le nombre de mains pour les
        cartes possibles ou apparait la carte
        */
        EnumList<Suit> toutesCouleurs_ = new EnumList<Suit>();
        toutesCouleurs_.add(CardTarot.EXCUSE.couleur());
        toutesCouleurs_.add(Suit.TRUMP);
        toutesCouleurs_.addAllElts(Suit.couleursOrdinaires());
        for(Suit couleur_: toutesCouleurs_) {
            cartesCertaines_.put(couleur_,new EqList<HandTarot>());
        }
        for (Suit couleur_:cartesCertaines_.getKeys()) {
            for (byte joueur_ = CustList.FIRST_INDEX; joueur_ <= nombreJoueurs_; joueur_++) {
                cartesCertaines_.getVal(couleur_).add(new HandTarot());
            }
        }
        int nombreCartesPossiblesJoueur_;
        for (byte joueur_ = CustList.FIRST_INDEX; joueur_ <= nombreJoueurs_; joueur_++) {
            nombreCartesPossiblesJoueur_ = 0;
            for (Suit couleur_: toutesCouleurs_) {
                nombreCartesPossiblesJoueur_ += cartesPossibles_.getVal(couleur_)
                        .get(joueur_).total();
            }
            if (nombreCartesPossiblesJoueur_ ==handLengths
                    .get(joueur_)) {
                /*
                    L'ensemble des cartes d'un joueur
                    reellement possedees est inclus
                    dans l'ensemble des cartes
                    probablement possedees par ce
                    joueur
                    */

                for (Suit couleur_:toutesCouleurs_) {
                    cartesCertaines_.getVal(couleur_)
                            .get(joueur_).ajouterCartes(
                            cartesPossibles_.getVal(couleur_).get(joueur_));
                }
                joueursRepartitionConnue_.add(joueur_);
                joueursRepartitionConnueMemo_.add(joueur_);
            }
        }
        while (!joueursRepartitionConnue_.isEmpty()) {
            /*
        Tant qu'on arrive a
        deduire la
        repartition exacte
        des joueurs on boucle
        sur l'ensemble des
        joueurs dont la
        repartition vient
        juste d'etre connue
        pour eliminer les
        cartes impossibles
        d'etre possedees par
        les joueurs
        */
            for (byte joueur_ : joueursRepartitionConnue_) {
                for (byte joueur2_ = CustList.FIRST_INDEX; joueur2_ <= nombreJoueurs_; joueur2_++) {
                    if (!joueursRepartitionConnueMemo_.containsObj(joueur2_)) {
                        for (Suit couleur_:toutesCouleurs_) {
                            cartesPossibles_.getVal(couleur_)
                                    .get(joueur2_).supprimerCartes(
                                    cartesCertaines_.getVal(couleur_).get(
                                            joueur_));
                        }
                    }
                    nombreCartesPossiblesJoueur_ = 0;
                    for (Suit couleur_:toutesCouleurs_) {
                        nombreCartesPossiblesJoueur_ += cartesPossibles_
                                .getVal(couleur_).get(joueur2_).total();
                    }
                    if (nombreCartesPossiblesJoueur_ == handLengths
                            .get(joueur2_)
                            && !joueursRepartitionConnueMemo_
                            .containsObj(joueur2_)) {
                        for (Suit couleur_:toutesCouleurs_) {
                            cartesCertaines_.getVal(couleur_).get(joueur2_)
                                    .supprimerCartes();
                            cartesCertaines_.getVal(couleur_)
                                    .get(joueur2_).ajouterCartes(
                                    cartesPossibles_.getVal(couleur_).get(
                                            joueur2_));
                        }
                        joueursRepartitionConnue2_.add(joueur2_);
                        joueursRepartitionConnueMemo_.add(joueur2_);
                    }
                }
            }
            for (byte joueur_ = CustList.FIRST_INDEX; joueur_ <= nombreJoueurs_; joueur_++) {
                if (!joueursRepartitionConnueMemo_.containsObj(joueur_)) {
                    joueursRepartitionInconnue_.add(joueur_);
                }
            }
            for (byte joueur_ : joueursRepartitionInconnue_) {
                for (Suit couleur_:toutesCouleurs_) {
                    for (CardTarot carte_ : cartesPossibles_.getVal(couleur_).get(
                            joueur_)) {
                        nombreDApparitionCarte_ = 0;
                        for (byte joueur2_ = CustList.FIRST_INDEX; joueur2_ <= nombreJoueurs_; joueur2_++) {
                            if (cartesPossibles_.getVal(couleur_).get(joueur2_)
                                    .contient(carte_)) {
                                nombreDApparitionCarte_++;
                            }
                        }
                        if (nombreDApparitionCarte_ == 1
                                && !cartesCertaines_.getVal(couleur_).get(joueur_)
                                .contient(carte_)) {
                            cartesCertaines_.getVal(couleur_).get(joueur_)
                                    .ajouter(carte_);
                        }
                    }
                }
                nombreCartesPossiblesJoueur_ = 0;
                for (Suit couleur_:toutesCouleurs_) {
                    nombreCartesPossiblesJoueur_ += cartesCertaines_
                            .getVal(couleur_).get(joueur_).total();
                }
                if (nombreCartesPossiblesJoueur_ == handLengths.get(
                        joueur_)
                        && !joueursRepartitionConnueMemo_.containsObj(joueur_)) {
                    cartesPossibles_.getVal(Suit.TRUMP).get(joueur_).supprimerCartes();
                    cartesPossibles_.getVal(Suit.TRUMP).get(joueur_).ajouterCartes(
                            cartesCertaines_.getVal(Suit.TRUMP).get(joueur_));
                    cartesPossibles_.getVal(Suit.TRUMP).get(joueur_).trierParForceEnCours(Suit.TRUMP);

                    for (Suit couleur_ : Suit.couleursOrdinaires()) {
                        cartesPossibles_.getVal(couleur_).get(joueur_)
                                .supprimerCartes();
                        cartesPossibles_
                                .getVal(couleur_).get(joueur_).ajouterCartes(
                                cartesCertaines_.getVal(couleur_).get(
                                        joueur_));
                        cartesPossibles_.getVal(couleur_).get(joueur_).trierParForceEnCours(couleur_);
                    }
                    joueursRepartitionConnueMemo_.add(joueur_);
                    joueursRepartitionConnue2_.add(joueur_);
                }
            }
            joueursRepartitionInconnue_.clear();
            joueursRepartitionConnue_.clear();
            joueursRepartitionConnue_.addAllElts(joueursRepartitionConnue2_);
            joueursRepartitionConnue2_.clear();
        }
        for (byte joueur_ = CustList.FIRST_INDEX; joueur_ <= nombreJoueurs_; joueur_++) {
            if (!joueursRepartitionConnueMemo_.containsObj(joueur_)) {
                joueursRepartitionInconnue_.add(joueur_);
            }
        }
        for (byte joueur_ : joueursRepartitionInconnue_) {
            cartesCertaines_.getVal(Suit.TRUMP).get(joueur_).trierParForceEnCours(Suit.TRUMP);
            for (Suit couleur_ : Suit.couleursOrdinaires()) {
                cartesCertaines_.getVal(couleur_).get(joueur_).trierParForceEnCours(couleur_);
            }
        }
        retour_.put(Hypothesis.POSSIBLE, cartesPossibles_);
        retour_.put(Hypothesis.SURE, cartesCertaines_);
        return retour_;
    }

    HandTarot playCharacterCardTarot(TeamsRelation _teamRel,byte _numero,
                                             Suit _couleur, HandTarot _probablyCharacterCard,
                                             CustList<TrickTarot> _unionPlis) {
        HandTarot retour_ = new HandTarot();
        retour_.ajouterCartes(_probablyCharacterCard);
        HandTarot playedCards_ = new HandTarot();
        NumberMap<Byte,Boolean> defausses_ = new NumberMap<Byte,Boolean>();
        byte nombreDeJoueurs_ = _teamRel.getNombreDeJoueurs();
        for (byte j = CustList.FIRST_INDEX;j<nombreDeJoueurs_;j++) {
            defausses_.put(j, DoneTrickInfo.defausseTarot(j, _unionPlis));
        }
        for (TrickTarot pli_ : _unionPlis) {
            if (!pli_.getVuParToutJoueur()) {
                continue;
            }
            if (pli_.getEntameur() == _numero) {
                continue;
            }
            CardTarot carteObservee_ = pli_.carteDuJoueur(_numero);
            if (carteObservee_.couleur() != _couleur) {
                continue;
            }
            if (!carteObservee_.isCharacter()) {
                continue;
            }
            if (_teamRel.confiance(_numero,pli_.getRamasseur())) {
                continue;
            }
            //winner of the trick foe for the viewed player
            if (!pli_.joueursAyantJoueAvant(_numero, _teamRel.getRules().getDealing()).containsObj(pli_.getRamasseur())) {
                continue;
            }
            boolean entameSurExcuse_ = true;
            for(byte j: pli_.joueursAyantJoueAvant(_numero, _teamRel.getRules().getDealing())) {
                CardTarot carteJouee_ = pli_.carteDuJoueur(j);
                if(carteJouee_ != CardTarot.EXCUSE) {
                    entameSurExcuse_ = false;
                    break;
                }
            }
            if(entameSurExcuse_) {
                continue;
            }
            boolean defausseToutJoueurApres_ = true;
            for(byte j: pli_.joueursAyantJoueApres(_numero, _teamRel.getRules().getDealing())) {
                if(defausses_.getVal(j)) {
                    continue;
                }
                defausseToutJoueurApres_ = false;
                break;
            }
            if(!defausseToutJoueurApres_) {
                continue;
            }
            // Plis (sur)coupes (couleur demandee) sans joueur pouvant sur/sous/couper
            // Plis fournis (demande atout) sans joueur pouvant fournir un atout
            playedCards_.ajouter(carteObservee_);
        }
        if (!playedCards_.estVide()) {
            CardTarot maxCarte_ = playedCards_.premiereCarte();
            HandTarot cartesImpossibles_ = new HandTarot();
            for (CardTarot atout_ : HandTarot.couleurComplete(_couleur)) {
                if (atout_.strength(_couleur) >= maxCarte_.strength(_couleur)) {
                    continue;
                }
                cartesImpossibles_.ajouter(atout_);
            }
            retour_.supprimerCartes(cartesImpossibles_);
        }
        return retour_;
    }

    HandTarot joueCarteBasseTarot(TeamsRelation _teamRel,byte _joueurCourant, HandTarot _curHand,byte _numero,
                                          Suit _couleur, HandTarot _cartesCouleurPossibles,
                                          CustList<TrickTarot> _unionPlis) {
        HandTarot retour_ = new HandTarot();
        retour_.ajouterCartes(_cartesCouleurPossibles);
        HandTarot cartesJouees_ = cartesJoueesEnCours(_teamRel,_joueurCourant);
        HandTarot playedCards_ = new HandTarot();
        for (TrickTarot pli_ : _unionPlis) {
            if (!pli_.getVuParToutJoueur()) {
                continue;
            }
            CardTarot carteObservee_ = pli_.carteDuJoueur(_numero);
            if (carteObservee_.couleur() != _couleur) {
                continue;
            }
            if (carteObservee_.isCharacter()) {
                continue;
            }
            playedCards_.ajouter(carteObservee_);
            // Plis sous coupes (couleur demandee) ou avec un atout joue en
            // dessous du ramasseur (demande atout)
        }
        HandTarot cartesVues_ = new HandTarot();
        cartesVues_.ajouterCartes(cartesJouees_.couleur(_couleur).cartesBasses(
                _couleur));
        cartesVues_.ajouterCartes(_curHand.couleur(_couleur).cartesBasses(
                _couleur));
        cartesVues_.trierParForceEnCours(_couleur);
        playedCards_.trierParForceEnCours(_couleur);
        if (!playedCards_.estVide()) {
            HandTarot mainLocale_ = new HandTarot();
            CardTarot carteObservee_ = playedCards_.derniereCarte();
            for (CardTarot carte_ : cartesVues_) {
                if (carte_.strength(_couleur) < carteObservee_
                        .strength(_couleur)) {
                    mainLocale_.ajouter(carte_);
                }
            }
            if (!mainLocale_.estVide()) {
                CardTarot maxCarte_ = mainLocale_.premiereCarte();
                HandTarot cartesImpossibles_ = new HandTarot();
                for (CardTarot atout_ : HandTarot.couleurComplete(_couleur)
                        .cartesBasses(_couleur)) {
                    if (atout_.strength(_couleur) >= maxCarte_.strength(_couleur)) {
                        continue;
                    }
                    cartesImpossibles_.ajouter(atout_);
                }
                retour_.supprimerCartes(cartesImpossibles_);
            }
        }
        return retour_;
    }

    HandTarot sousCoupeTarot(TeamsRelation _teamRel,byte _joueurCourant, HandTarot _curHand,byte _numero,
                                     HandTarot _atoutsPossibles, CustList<TrickTarot> _unionPlis) {
        HandTarot retour_ = new HandTarot();
        retour_.ajouterCartes(_atoutsPossibles);
        HandTarot cartesJouees_ = cartesJoueesEnCours(_teamRel,_joueurCourant);
        HandTarot playedCards_ = new HandTarot();
        for (TrickTarot pli_ : _unionPlis) {
            if (!pli_.getVuParToutJoueur()) {
                continue;
            }
            CardTarot carteObservee_ = pli_.carteDuJoueur(_numero);
            if (carteObservee_.couleur() != Suit.TRUMP) {
                continue;
            }
            boolean sousCoupe_ = false;
            Suit couleurDemandee_ = pli_.couleurDemandee();
            byte force_ = carteObservee_.strength(couleurDemandee_);
            for(byte j: pli_.joueursAyantJoueAvant(_joueurCourant, _teamRel.getRules().getDealing())) {
                if(pli_.carteDuJoueur(j).strength(couleurDemandee_) < force_) {
                    continue;
                }
                sousCoupe_ = true;
                break;
            }
            if (!sousCoupe_) {
                continue;
            }
            // Plis sous coupes (couleur demandee) ou avec un atout joue en
            // dessous du ramasseur (demande atout)
            playedCards_.ajouter(carteObservee_);
        }
        HandTarot cartesVues_ = new HandTarot();
        cartesVues_.ajouterCartes(cartesJouees_.couleur(Suit.TRUMP));
        cartesVues_.ajouterCartes(_curHand.couleur(Suit.TRUMP));
        cartesVues_.trierParForceEnCours(Suit.TRUMP);
        playedCards_.trierParForceEnCours(Suit.TRUMP);
        if (!playedCards_.estVide()) {
            HandTarot mainLocale_ = new HandTarot();
            CardTarot carteObservee_ = playedCards_.derniereCarte();
            for (CardTarot carte_ : cartesVues_) {
                if (carte_.strength(Suit.TRUMP) < carteObservee_
                        .strength(Suit.TRUMP)) {
                    mainLocale_.ajouter(carte_);
                }
            }
            if (!mainLocale_.estVide()) {
                CardTarot maxCarte_ = mainLocale_.premiereCarte();
                HandTarot atoutsImpossibles_ = new HandTarot();
                for (CardTarot atout_ : HandTarot.atoutsSansExcuse()) {
                    if (CardTarot.eq(atout_, CardTarot.petit())) {
                        continue;
                    }
                    if (atout_.strength(Suit.TRUMP) >= maxCarte_
                            .strength(Suit.TRUMP)) {
                        continue;
                    }
                    atoutsImpossibles_.ajouter(atout_);
                }
                retour_.supprimerCartes(atoutsImpossibles_);
            }
        }
        return retour_;
    }

    HandTarot coupeTarot(TeamsRelation _teamRel,byte _joueurCourant, HandTarot _curHand,byte _numero,
                                 HandTarot _atoutsPossibles, CustList<TrickTarot> _unionPlis) {
        HandTarot retour_ = new HandTarot();
        retour_.ajouterCartes(_atoutsPossibles);
        HandTarot cartesJouees_ = cartesJoueesEnCours(_teamRel,_joueurCourant);
        CustList<TrickTarot> plis_ = new CustList<TrickTarot>();
        NumberMap<Byte,HandTarot> atoutsJouesPlis_ = new NumberMap<Byte,HandTarot>();
        NumberMap<Byte,Boolean> defausses_ = new NumberMap<Byte,Boolean>();
        byte nombreDeJoueurs_ = _teamRel.getNombreDeJoueurs();
        for (byte j = CustList.FIRST_INDEX;j<nombreDeJoueurs_;j++) {
            defausses_.put(j, DoneTrickInfo.defausseTarot(j, _unionPlis));
        }
        byte key_ = 0;
        for (TrickTarot pli_ : _unionPlis) {
            if (!pli_.getVuParToutJoueur()) {
                key_++;
                continue;
            }
            if (pli_.getEntameur() == _numero) {
                key_++;
                continue;
            }
            CardTarot carteObservee_ = pli_.carteDuJoueur(_numero);
            if (carteObservee_.couleur() != Suit.TRUMP) {
                key_++;
                continue;
            }
            Suit couleurDemandee_ = pli_.couleurDemandee();
            boolean coupe_ = true;
            byte force_ = carteObservee_.strength(couleurDemandee_);
            HandTarot atoutsJouesAvant_ = new HandTarot();
            boolean entameSurExcuse_ = true;
            for(byte j: pli_.joueursAyantJoueAvant(_numero, _teamRel.getRules().getDealing())) {
                CardTarot carteJouee_ = pli_.carteDuJoueur(j);
                if(carteJouee_ != CardTarot.EXCUSE) {
                    entameSurExcuse_ = false;
                }
                if(carteJouee_.strength(couleurDemandee_) < force_) {
                    if(carteJouee_.couleur() == Suit.TRUMP) {
                        atoutsJouesAvant_.ajouter(carteJouee_);
                    }
                    continue;
                }
                coupe_ = false;
                break;
            }
            if(entameSurExcuse_) {
                key_++;
                continue;
            }
            if(!coupe_) {
                key_++;
                continue;
            }
            boolean defausseToutJoueurApres_ = true;
            for(byte j: pli_.joueursAyantJoueApres(_numero, _teamRel.getRules().getDealing())) {
                if(defausses_.getVal(j)) {
                    continue;
                }
                defausseToutJoueurApres_ = false;
                break;
            }
            if(!defausseToutJoueurApres_) {
                key_++;
                continue;
            }
            atoutsJouesPlis_.put(key_, atoutsJouesAvant_);
            // Plis (sur)coupes (couleur demandee) sans joueur pouvant sur/sous/couper
            // Plis fournis (demande atout) sans joueur pouvant fournir un atout
            plis_.add(pli_);
            key_++;
        }
        HandTarot cartesVues_ = new HandTarot();
        cartesVues_.ajouterCartes(cartesJouees_.couleur(Suit.TRUMP));
        cartesVues_.ajouterCartes(_curHand.couleur(Suit.TRUMP));
        cartesVues_.trierParForceEnCours(Suit.TRUMP);
        key_ = 0;
        for (TrickTarot pli_ : plis_) {
            if (!atoutsJouesPlis_.contains(key_)) {
                key_++;
                continue;
            }
            HandTarot atoutsJouesPli_ = atoutsJouesPlis_.getVal(key_);
            CardTarot carteObservee_ = pli_.carteDuJoueur(_numero);
            HandTarot mainLocale_ = new HandTarot();
            for (CardTarot carte_ : cartesVues_) {
                if (carte_.strength(Suit.TRUMP) < carteObservee_
                        .strength(Suit.TRUMP)) {
                    mainLocale_.ajouter(carte_);
                }
            }
            //mainLocale: cartesVues inferieures a la carte observee
            if (mainLocale_.estVide()) {
                //le joueur courant ne possede pas de carte en dessous de la carte observee
                //aucune carte en dessous de la carte observee n'a ete jouee
                //tout reste possible
                key_++;
                continue;
            }
            CardTarot maxCarte_ = mainLocale_.premiereCarte();
            HandTarot atoutsImpossibles_ = new HandTarot();
            if(atoutsJouesPli_.estVide()) {
                for (CardTarot atout_ : HandTarot.atoutsSansExcuse()) {
                    if (atout_.strength(Suit.TRUMP) >= maxCarte_
                            .strength(Suit.TRUMP)) {
                        continue;
                    }
                    atoutsImpossibles_.ajouter(atout_);
                }
            } else {
                atoutsJouesPli_.trierParForceEnCours(pli_.couleurDemandee());
                CardTarot maxAtoutJouePli_ = atoutsJouesPli_.premiereCarte();
                for (CardTarot atout_ : HandTarot.atoutsSansExcuse()) {
                    if (atout_.strength(Suit.TRUMP) >= maxCarte_
                            .strength(Suit.TRUMP)) {
                        continue;
                    }
                    if (atout_.strength(Suit.TRUMP) <= maxAtoutJouePli_
                            .strength(Suit.TRUMP)) {
                        continue;
                    }
                    atoutsImpossibles_.ajouter(atout_);
                }
            }
            retour_.supprimerCartes(atoutsImpossibles_);
            key_++;
        }
        return retour_;
    }
    boolean petitJoueDemandeAtoutRamasseurAdv(TeamsRelation _teamRel,
            byte _numero,
            CustList<TrickTarot> _unionPlis) {
        boolean playedSmall_ = false;
        for (TrickTarot pli_ : _unionPlis) {
            if(pli_.couleurDemandee() != Suit.TRUMP) {
                continue;
            }
            CardTarot carteObservee_ = pli_.carteDuJoueur(_numero);
            if(carteObservee_ != CardTarot.petit()) {
                continue;
            }
            //jeu du Petit sur demande d'atout
            if(!_teamRel.confiance(_numero,pli_.getRamasseur())) {
                playedSmall_ = true;
            }
            break;
        }
        return playedSmall_;
    }

    public HandTarot cartesJoueesEnCours(TeamsRelation _teamRel,byte _numero) {
        HandTarot retour_ = cartesJouees(_teamRel,_numero);
        retour_.ajouterCartes(progressingTrick.getCartes());
        return retour_;
    }
    HandTarot cartesJouees(TeamsRelation _teamRel,byte _numero) {
        HandTarot m = new HandTarot();
        if (!_teamRel.existePreneur()) {
            for (TrickTarot t: tricks) {
                if (!t.getVuParToutJoueur()) {
                    continue;
                }
                m.ajouterCartes(t.getCartes());
            }
        } else if (_numero == _teamRel.getTaker() && bid.getJeuChien() == PlayingDog.WITH) {
            for (TrickTarot t: tricks) {
                m.ajouterCartes(t.getCartes());
            }
        } else {
            for (TrickTarot t: tricks) {
                if (!t.getVuParToutJoueur()) {
                    continue;
                }
                m.ajouterCartes(t.getCartes());
            }
        }
        return m;
    }

    /**
     Retourne vrai si le joueur ne peut pas jouer de l'atout sur demande
     d'atout ou sur demande de coupe de couleur sauf pli en cours
     */
    static boolean defausseTarot(byte _numero, CustList<TrickTarot> _unionPlis) {
        boolean coupe_ = false;
        // coupe retourne vrai si on sait que le joueur ne
        // peut que jouer de l'atout sur des couleurs
        if (coupeTarot(Suit.TRUMP, _numero, _unionPlis)) {
            coupe_ = true;
        }
        for (Suit couleur_ : Suit.couleursOrdinaires()) {
            if (coupeTarot(couleur_, _numero, _unionPlis)) {
                coupe_ = true;
            }
        }
        // coupe est vrai si et seulement si il existe au moins une coupe a une
        // des couleurs
        if (!coupe_) {
            return false;
        }
        if (coupeTarot(Suit.TRUMP, _numero, _unionPlis)) {
            // Si le joueur ne
            // joue pas d'atout
            // sur demande
            // d'atout
            return true;
        }
        // Le joueur a deja joue une carte d'une autre couleur que celle
        // demandee differente de l'atout
        int lastIndex_ = _unionPlis.getLastIndex();
        for (int indicePli_ = lastIndex_; indicePli_ >= CustList.FIRST_INDEX; indicePli_--) {
            /*
            On effectue une boucle
            sur les plis faits
            par les joueurs en
            commencant par
            le plus recent
            ( numero le plus eleve )
            */
            TrickTarot pli_ = _unionPlis.get(indicePli_);
            if (!pli_.getVuParToutJoueur()) {
                continue;
            }
            Suit couleurDemandee_ = pli_.couleurDemandee();
            Suit couleurCarte_ = pli_.carteDuJoueur(_numero).couleur();
            if (couleurCarte_ == couleurDemandee_) {
                continue;
            }
            if (couleurCarte_ == Suit.TRUMP) {
                continue;
            }
            return true;
        }
        return false;
    }

    /**
     Retourne vrai si le joueur ne peut pas jouer de l'atout ni fournir sur
     demande de la couleur "couleur" sauf pli en cours
     */
    static boolean defausseTarot(byte _numero, Suit _couleurDonnee,
                                         CustList<TrickTarot> _unionPlis) {
        boolean coupe_ = false;
        // coupe retourne vrai si on sait que le joueur ne
        // peut que jouer de l'atout sur des couleurs
        if (coupeTarot(Suit.TRUMP, _numero, _unionPlis)) {
            coupe_ = true;
        }
        for (Suit couleur_ : Suit.couleursOrdinaires()) {
            if (coupeTarot(couleur_, _numero, _unionPlis)) {
                coupe_ = true;
            }
        }
        // coupe est vrai si et seulement si il existe au moins une coupe a une
        // des couleurs
        if (!coupe_) {
            return false;
        }
        // Le joueur a deja joue une carte d'une autre couleur que celle
        // demandee differente de l'atout
        int lastIndex_ = _unionPlis.size() - 1;
        for (int b = lastIndex_; b >= CustList.FIRST_INDEX; b--) {
            TrickTarot pli_ = _unionPlis.get(b);
            if (!pli_.getVuParToutJoueur()) {
                continue;
            }
            Suit couleurDemandee_ = pli_.couleurDemandee();
            if (_couleurDonnee != couleurDemandee_) {
                continue;
            }
            Suit couleurCarte_ = pli_.carteDuJoueur(_numero).couleur();
            if (couleurCarte_ == couleurDemandee_) {
                continue;
            }
            if (couleurCarte_ == Suit.TRUMP) {
                continue;
            }
            return true;
        }
        return false;
    }

    /**
     Retourne vrai dans les cas suivants
     <ol>
     <li>Si couleur vaut 1(C'est la couleur de l'atout (Excuse exclue)), alors
     vrai est retourne lorsque le joueur a joue une couleur differente de
     l'atout sur entame atout ou sur une entame de couleur differente de
     l'atout en ayant fourni une carte autre que de l'atout et celle qui est
     demandee et l'Excuse sauf pli en cours</li>
     <li>Sinon vrai est retourne lorsque le joueur a joue un atout sur entame
     d'une couleur autre que de l'atout sauf pli en cours</li>
     </ol>
     */
    static boolean coupeTarot(Suit _couleur, byte _numero,
                                      CustList<TrickTarot> _unionPlis) {
        if (Suit.couleursOrdinaires().containsObj(_couleur)) {
            int lastIndex_ = _unionPlis.size() - 1;
            for (int b = lastIndex_; b >= CustList.FIRST_INDEX; b--) {
                TrickTarot pli_ = _unionPlis.get(b);
                if (!pli_.getVuParToutJoueur()) {
                    continue;
                }
                if (pli_.couleurDemandee() != _couleur) {
                    continue;
                }
                // On ne cherche que les plis dont la couleur demande
                // est couleur
                if (pli_.carteDuJoueur(_numero)
                        .couleur() != Suit.TRUMP) {
                    continue;
                }
                return true;
            }
            return false;
        }
        // Le joueur ne coupe pas la couleur passee en parametre a ce niveau si
        // couleur > 1
        // couleur == Couleu.Atout
        int lastIndex_ = _unionPlis.size() - 1;
        for (int b = lastIndex_; b >= CustList.FIRST_INDEX; b--) {
            TrickTarot pli_ = _unionPlis.get(b);
            if (!pli_.getVuParToutJoueur()) {
                continue;
            }

            Suit couleurDemandee_ = pli_.couleurDemandee();
            Suit couleurJoueur_ = pli_.carteDuJoueur(_numero).couleur();
            if (!Suit.couleursOrdinaires().containsObj(couleurJoueur_)) {
                continue;
            }
            if (couleurDemandee_ == _couleur) {
                /*
                Si la couleur demandee
                est atout alors il suffit
                que le joueur n'ait pas
                joue de l'atout pour
                conclure qu'il ne possede
                pas d'atout sinon on
                verifie de plus que la
                couleur fournie par le
                joueur est une autre
                couleur que celle
                demandee
                */
                return true;
            }
            //couleurDemandee est une couleur ordinaire
            //couleurJoueur est une couleur ordinaire
            //donc le joueur se defausse
            if (couleurJoueur_ != couleurDemandee_) {
                return true;
            }
        }

        return false;
    }
    static boolean plisTousFaitsPar(Numbers<Byte> _joueurs,
                                    CustList<TrickTarot> _unionPlis, byte _nombreJoueurs) {
        Numbers<Byte> autresJoueurs_ = TeamsRelation.autresJoueurs(_joueurs, _nombreJoueurs);
        for (TrickTarot pli_ : _unionPlis) {
            if (!pli_.getVuParToutJoueur()) {
                continue;
            }
            if (autresJoueurs_.containsObj(pli_.getRamasseur())) {
                return false;
            }
        }
        return true;
    }
    HandTarot getPoignee(byte _b) {
        return handfuls.get(_b);
    }
}
