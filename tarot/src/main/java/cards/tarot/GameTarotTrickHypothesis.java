package cards.tarot;

import cards.consts.PossibleTrickWinner;
import cards.consts.Status;
import cards.consts.Suit;
import cards.tarot.enumerations.CardTarot;
import code.maths.Rate;
import code.util.CustList;
import code.util.EnumList;
import code.util.EnumMap;
import code.util.EqList;
import code.util.Numbers;

final class GameTarotTrickHypothesis {

    private GameTarotTrickHypothesis(){
    }
    static void hypothesesRepartitionsJoueurs(GameTarotTeamsRelation _teamReal, HandTarot _calledCards,
                                              CustList<TrickTarot> _plisFaits, byte _numero,
                                              EnumMap<Suit, EqList<HandTarot>> _cartesPossibles,
                                              EnumMap<Suit, EqList<HandTarot>> _cartesCertaines) {
        Status st_ = _teamReal.statutDe(_numero);
        byte nombreJoueurs_ = _teamReal.getNombreDeJoueurs();
        CustList<TrickTarot> fullTricksProg_ = new CustList<TrickTarot>();
        for (TrickTarot t:_plisFaits) {
            if (!t.getVuParToutJoueur()) {
                continue;
            }
            fullTricksProg_.add(t);
        }
        boolean appelesTousConnus_ = _teamReal.allKnownCalledPlayers(_calledCards,_cartesCertaines,
                nombreJoueurs_);
        Numbers<Byte> joueursNonConfiancePresqueSure_ = new Numbers<Byte>();
        Numbers<Byte> possibleAlly_ = new Numbers<Byte>();
        possibleAlly_.add(_numero);
        if(appelesTousConnus_) {
            if (st_ == Status.DEFENDER) {
                for(CardTarot c: _calledCards) {
                    for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
                        if (joueur_ == _teamReal.getTaker()) {
                            continue;
                        }
                        if (!_cartesCertaines.getVal(c.couleur())
                                .get(joueur_).contient(c)) {
                            possibleAlly_.add(joueur_);
                        }
                    }
                }
            } else {
                for(CardTarot c: _calledCards) {
                    for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
                        if (_cartesCertaines.getVal(c.couleur())
                                .get(joueur_).contient(c)) {
                            possibleAlly_.add(joueur_);
                        }
                    }
                }
            }
            for (byte b: GameTarotTeamsRelation.autresJoueurs(possibleAlly_,nombreJoueurs_)) {
                joueursNonConfiancePresqueSure_.add(b);
            }
        }
        boolean arreterRechercheJoueurJoueCartePoint_;
        for (byte j = CustList.FIRST_INDEX; j < nombreJoueurs_; j++) {
            //iteration sur la confiance du joueur numero en le joueur j
            //vis a vis du Petit joue en premier atout mais jamais virtuellement maitre
            //le Petit doit etre ramasse par le plus grand atout encore en jeu
            //ramasse par un autre atout
            byte ramasseur_ = -1;
            for(TrickTarot p: filter(fullTricksProg_,nombreJoueurs_,j)) {
                CardTarot carte_ = p.carteDuJoueur(j, nombreJoueurs_);
                if(carte_.couleur() != Suit.TRUMP) {
                    continue;
                }
                //arret de la boucle sur les plis des que le joueur a joue un atout
                if(carte_ != CardTarot.petit()) {
                    //Si le joueur j n'a pas joue le Petit en tant que premier atout,
                    //alors on passe au joueur suivant
                    break;
                }
                Suit couleurDemandee_ = p.couleurDemandee();
                byte forcePetit_ = carte_.strength(couleurDemandee_);
                boolean petitRamasse_ = false;
                for(byte j2_: p.joueursAyantJoueAvant(j, nombreJoueurs_,_teamReal.getRules().getDealing())) {
                    if(p.carteDuJoueur(j2_, nombreJoueurs_).strength(couleurDemandee_) < forcePetit_) {
                        continue;
                    }
                    //la carte du joueur j2 est un atout ramassant temporairement le Petit
                    petitRamasse_ = true;
                    break;
                }
                if(!petitRamasse_) {
                    break;
                }
                //carte == CarteTarot.petit()
                ramasseur_ = p.getRamasseur(nombreJoueurs_);
                //ramasseur != -1 && passerAuJoueurSuivant = false
                break;
            }
            if(ramasseur_ == -1) {
                continue;
            }
            res(j, possibleAlly_, ramasseur_, _teamReal, st_, joueursNonConfiancePresqueSure_, _numero);
        }
        arreterRechercheJoueurJoueCartePoint_ = false;
        for (byte j = CustList.FIRST_INDEX; j < nombreJoueurs_; j++) {
            //boucle cherchant l'entameur du Petit
            byte ramasseur_ = -1;
            for(TrickTarot p: filter(fullTricksProg_,nombreJoueurs_,j)) {
                if(j != p.getEntameur()) {
                    continue;
                }
                //le joueur j a entame
                CardTarot carte_ = p.carteDuJoueur(j,nombreJoueurs_);
                if(carte_ != CardTarot.petit()) {
                    continue;
                }
                //Entame du Petit par le joueur j
                ramasseur_ = p.getRamasseur(nombreJoueurs_);
                if(ramasseur_ == j) {
                    //Si le joueur j a entame et ramasse le pli avec le Petit,
                    //alors on passe au joueur suivant
                    arreterRechercheJoueurJoueCartePoint_ = true;
                }
                //ramasseur != -1 && passerAuJoueurSuivant = false
                break;
            }
            if(ramasseur_ == -1) {
                continue;
            }
            if(arreterRechercheJoueurJoueCartePoint_) {
                break;
            }
            res(j, possibleAlly_, ramasseur_, _teamReal, st_, joueursNonConfiancePresqueSure_, _numero);
        }
        arreterRechercheJoueurJoueCartePoint_ = false;
        for (byte j = CustList.FIRST_INDEX; j < nombreJoueurs_; j++) {
            byte ramasseur_ = -1;
            boolean petitJoueDemandeAtout_ = false;
            boolean defausse_ = _cartesPossibles.getVal(Suit.TRUMP).get(j).estVide();
            for(TrickTarot p: filter(fullTricksProg_,nombreJoueurs_,j)) {
                CardTarot carte_ = p.carteDuJoueur(j,nombreJoueurs_);
                if(petitJoueDemandeAtout_) {
                    if(!defausse_) {
                        arreterRechercheJoueurJoueCartePoint_ = true;
                        break;
                    }
                }
                if(carte_.couleur() != Suit.TRUMP) {
                    continue;
                }
                if(p.couleurDemandee() != Suit.TRUMP) {
                    continue;
                }
                if(carte_ != CardTarot.petit()) {
                    continue;
                }
                ramasseur_ = p.getRamasseur(nombreJoueurs_);
                if(ramasseur_ != _teamReal.getTaker()) {
                    arreterRechercheJoueurJoueCartePoint_ = true;
                    break;
                }
                petitJoueDemandeAtout_ = true;
            }
            if(ramasseur_ == -1) {
                continue;
            }
            if(arreterRechercheJoueurJoueCartePoint_) {
                break;
            }
            //le ramasseur du pli et le joueur du Petit (entameur) sont dans la meme equipe
            if(st_ == Status.DEFENDER) {
                //confiance de j en ramasseur, qui n'est pas le preneur ni l'appele
                //car il serait absurde de jouer le Petit en premier sur un joueur dont l'equipe n'est pas connu
                //de plus numero est un defenseur
                Numbers<Byte> found_ = new Numbers<Byte>();
                found_.add(j);
                found_.add(_numero);
                for (byte b: GameTarotTeamsRelation.autresJoueurs(found_,nombreJoueurs_)) {
                    joueursNonConfiancePresqueSure_.add(b);
                }
                possibleAlly_.add(j);
                // confiance en j
            } else {
                //!aPourDefenseur(numero) ==> numero == preneur ==> mefiance de j
                joueursNonConfiancePresqueSure_.add(j);
            }
        }
        for (byte j = CustList.FIRST_INDEX; j < nombreJoueurs_; j++) {
            //boucle cherchant les figures jouees au premier tour d'une couleur ordinaire demandee
            for(Suit c: Suit.couleursOrdinaires()) {
                HandTarot cartesCouleurJouees_ = new HandTarot();
                byte ramasseurVirtuel_ = -1;
                for(TrickTarot p: filter(fullTricksProg_,nombreJoueurs_,j)) {
                    Suit couleurDemandee_ = p.couleurDemandee();
                    if(couleurDemandee_ != c) {
                        continue;
                    }
                    CardTarot carteJouee_ = p.carteDuJoueur(j,nombreJoueurs_);
                    //Premier tour a la couleur demandee c
                    if(carteJouee_.couleur() != c) {
                        break;
                    }
                    //carteJouee est une carte de la couleur demandee au premier tour
                    if(!carteJouee_.isCharacter()) {
                        break;
                    }
                    //carteJouee est une figure de la couleur demandee au premier tour
                    boolean carteJoueeRamassee_ = false;
                    byte max_ = carteJouee_.strength(c);
                    for(byte j2_: p.joueursAyantJoueAvant(j,nombreJoueurs_, _teamReal.getRules().getDealing())) {
                        CardTarot carteJoueeAvant_ = p.carteDuJoueur(j2_,nombreJoueurs_);
                        if(carteJoueeAvant_.strength(c) < max_) {
                            continue;
                        }
                        max_ = carteJoueeAvant_.strength(c);
                        ramasseurVirtuel_ = j2_;
                        carteJoueeRamassee_ = true;
                    }
                    if(!carteJoueeRamassee_) {
                        break;
                    }
                    cartesCouleurJouees_.ajouter(carteJouee_);
                    break;
                }
                if(ramasseurVirtuel_ == -1) {
                    continue;
                }
                if(onlyOnePlayedCard(fullTricksProg_, nombreJoueurs_, cartesCouleurJouees_, j)) {
                    continue;
                }
                res(j, possibleAlly_, ramasseurVirtuel_, _teamReal, st_, joueursNonConfiancePresqueSure_, _numero);
            }

        }
        for (byte j = CustList.FIRST_INDEX; j < nombreJoueurs_; j++) {
            //boucle cherchant les figures defaussese sur demande d'atout
            for(Suit c: Suit.couleursOrdinaires()) {
                HandTarot cartesCouleurJouees_ = new HandTarot();
                byte ramasseurVirtuel_ = -1;
                for(TrickTarot p: filter(fullTricksProg_,nombreJoueurs_,j)) {
                    Suit couleurDemandee_ = p.couleurDemandee();
                    if(couleurDemandee_ != Suit.TRUMP) {
                        continue;
                    }
                    CardTarot carteJouee_ = p.carteDuJoueur(j,nombreJoueurs_);
                    //Premier tour d'atout
                    if(carteJouee_.couleur() != c) {
                        continue;
                    }
                    //carteJouee est une carte defaussee de la couleur c
                    if(!carteJouee_.isCharacter()) {
                        continue;
                    }
                    //carteJouee est une figure defaussee de la couleur c
                    ramasseurVirtuel_ = p.getRamasseur(nombreJoueurs_);
                    //ramasseurVirtuel != j, car une defause ne permet JAMAIS de prendre la main
                    cartesCouleurJouees_.ajouter(carteJouee_);
                    break;
                }
                if(ramasseurVirtuel_ == -1) {
                    continue;
                }
                if(onlyOnePlayedCard(fullTricksProg_, nombreJoueurs_, cartesCouleurJouees_, j)) {
                    continue;
                }
                res(j, possibleAlly_, ramasseurVirtuel_, _teamReal, st_, joueursNonConfiancePresqueSure_, _numero);
            }

        }
        if (st_ == Status.DEFENDER) {
            for(CardTarot c: _calledCards) {
                for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
                    if (joueur_ == _teamReal.getTaker()) {
                        continue;
                    }
                    if (_cartesPossibles.getVal(c.couleur())
                            .get(joueur_).estVide()) {
                        possibleAlly_.add(joueur_);
                    }
                }
            }
        } else {
            for(CardTarot c: _calledCards) {
                for (byte joueur_ = CustList.FIRST_INDEX; joueur_ < nombreJoueurs_; joueur_++) {
                    if (_cartesPossibles.getVal(c.couleur())
                            .get(joueur_).estVide()) {
                        joueursNonConfiancePresqueSure_.add(joueur_);
                    }
                }
            }
        }
        possibleAlly_.removeDuplicates();
        if (st_ == Status.DEFENDER) {
            joueursNonConfiancePresqueSure_.add(_teamReal.getTaker());
        }
        joueursNonConfiancePresqueSure_.removeDuplicates();
        Numbers<Byte> allConf_ = new Numbers<Byte>();
        allConf_.addAllElts(possibleAlly_);
        allConf_.addAllElts(joueursNonConfiancePresqueSure_);
        if (Numbers.equalsSetBytes(allConf_, GameTarotTeamsRelation.tousJoueurs(nombreJoueurs_))) {
            for (byte b: possibleAlly_) {
                _teamReal.faireConfiance(_numero,b);
            }
            for (byte b: joueursNonConfiancePresqueSure_) {
                _teamReal.faireMefiance(_numero,b);
            }
        } else if (st_ == Status.DEFENDER) {
            for (byte b: possibleAlly_) {
                _teamReal.faireConfiance(_numero,b);
            }
        } else {
            if (possibleAlly_.size() < 3) {
                for (byte b: possibleAlly_) {
                    _teamReal.faireConfiance(_numero,b);
                }
            }
        }
    }
    private static boolean onlyOnePlayedCard(CustList<TrickTarot> _full, byte _nbPl, HandTarot _played, byte _cur) {
        boolean autreCarteCouleurJouee_ = false;
        boolean figureJouee_ = false;
        CardTarot premiereFigureJouee_ = _played.premiereCarte();
        for(TrickTarot p: filter(_full,_nbPl,_cur)) {
            CardTarot carteJouee_ = p.carteDuJoueur(_cur,_nbPl);
            if(carteJouee_.couleur() != premiereFigureJouee_.couleur()) {
                continue;
            }
            if(carteJouee_ == premiereFigureJouee_) {
                figureJouee_ = true;
                continue;
            }
            if(!figureJouee_) {
                continue;
            }
            if(carteJouee_.points() > premiereFigureJouee_.points()) {
                continue;
            }
            //la carte jouee est une carte inferieure a la premiere en points mais de la meme couleur
            autreCarteCouleurJouee_ = true;
            break;
        }
        return !autreCarteCouleurJouee_;
    }
    private static CustList<TrickTarot> filter(CustList<TrickTarot> _tricks, byte _nbPl, byte _current) {
        CustList<TrickTarot> l_ = new CustList<TrickTarot>();
        for (TrickTarot t: _tricks) {
            if (!t.aJoue(_current,_nbPl)) {
                continue;
            }
            l_.add(t);
        }
        return l_;
    }
    private static void res(byte _current, Numbers<Byte> _possibleAlly, byte _ram, GameTarotTeamsRelation _teamReal, Status _st,
                            Numbers<Byte> _potentialFoesNearlySure, byte _numero) {
        //ramasseur != j && ramasseur != -1
        byte nbPl_ = _teamReal.getNombreDeJoueurs();
        Numbers<Byte> all_ = GameTarotTeamsRelation.tousJoueurs(nbPl_);
        if(_st == Status.DEFENDER) {
            if(_ram == _teamReal.getTaker() || _current == _teamReal.getTaker()) {
                all_.removeObj(_current);
                all_.removeObj(_ram);
                all_.removeObj(_teamReal.getTaker());
                _possibleAlly.addAllElts(all_);
                return;
            }
            //confiance de j en ramasseur, qui n'est pas le preneur ni l'appele
            //car il serait absurde de jouer le Petit en premier sur un joueur dont l'equipe n'est pas connu
            //de plus numero est un defenseur
            Numbers<Byte> found_ = new Numbers<Byte>();
            found_.add(_current);
            found_.add(_ram);
            _possibleAlly.add(_current);
            _possibleAlly.add(_ram);
            return;
        }
        //!aPourDefenseur(numero) ==> numero == preneur ==> j == appele
        if(_numero == _ram) {
            Numbers<Byte> found_ = new Numbers<Byte>();
            found_.add(_current);
            _possibleAlly.add(_current);
            return;
        }
        addPotentialFoePlayers(_potentialFoesNearlySure, _current,
                _ram);
    }
    private static void addPotentialFoePlayers(Numbers<Byte> _potentialFoesNearlySure,
                                               byte _otherPlayer, byte _leader) {
        _potentialFoesNearlySure.add(_leader);
        _potentialFoesNearlySure.add(_otherPlayer);
    }
    /**
     Renvoie un entier 0 si joueur de non confiance qui va faire le pli 1 si
     joueur de confiance va faire le pli et -1 sinon
     @param cartes_possibles
     l'ensemble des cartes probablement possedees par les joueurs
     @param cartes_certaines
     l'ensemble des cartes surement possedees par les joueurs
     @param ramasseur_virtuel
     le joueur, qui sans les cartes jouees par les derniers joueurs
     du pli est ramasseur
     @param _carteForte
     la carte qui est en train de dominer le pli
     @param joueurs_non_joue
     l'ensemble des joueurs n'ayant pas encore joue leur carte
     @param joueurs_confiance
     l'ensemble des joueurs de confiance
     @param joueurs_non_confiance
     l'ensemble des joueurs de non confiance
     @param _numero
     le numero du joueur qui va jouer
     @param couleur_appelee
     la couleur appelee si elle existe -1 sinon
     @param carte_appelee_jouee
     une valeur booleenne vrai si et seulement si la carte appelee
     est jouee
     */
    static PossibleTrickWinner equipeQuiVaFairePli(
            TarotInfoPliEnCours _info,
            CardTarot _carteForte) {
        if (Suit.couleursOrdinaires().containsObj(_info.getProgressingTrick().couleurDemandee())) {
            return getPossibleTrickWinnerNormalSuit(_info, _carteForte);
        }
        return getPossibleTrickWinnerTrump(_info, _carteForte);
    }

    static PossibleTrickWinner getPossibleTrickWinnerNormalSuit(TarotInfoPliEnCours _info, CardTarot _carteForte) {
        if (_carteForte.couleur() == Suit.TRUMP) {
            return getPossibleTrickWinnerTrumpSuit(_info, _carteForte);
        }
        return getPossibleTrickWinnerNoTrump(_info, _carteForte);
    }

    static PossibleTrickWinner getPossibleTrickWinnerTrumpSuit(TarotInfoPliEnCours _info, CardTarot _carteForte) {
        EnumMap<Suit,EqList<HandTarot>> cartesPossibles_ = _info.getCartesPossibles();
        EnumMap<Suit,EqList<HandTarot>> cartesCertaines_ = _info.getCartesCertaines();
        byte ramasseurVirtuel_ = _info.getRamasseurVirtuel();
        Numbers<Byte> joueursNonJoue_ = _info.getJoueursNonJoue();
        EnumList<Suit> couleursAppelees_ = _info.getCalledSuits();
        boolean carteAppeleeJouee_ =_info.isCarteAppeleeJouee();
        Numbers<Byte> joueursNonConfianceNonJoue_ = new Numbers<Byte>(
                joueursNonJoue_);
        Numbers<Byte> joueursConfianceNonJoue_ = new Numbers<Byte>(
                joueursNonJoue_);
        byte player_ = _info.getCurrentPlayer();
        Numbers<Byte> joueursConfiance_ = _info.getJoueursConfiance();
        Numbers<Byte> joueursNonConfiance_ = _info.getJoueursNonConfiance();
        joueursNonConfianceNonJoue_ = GameTarotTeamsRelation.intersectionJoueurs(joueursNonConfianceNonJoue_,joueursNonConfiance_);
        joueursConfianceNonJoue_ = GameTarotTeamsRelation.intersectionJoueurs(joueursConfianceNonJoue_,joueursConfiance_);
        Numbers<Byte> joueursJoue_ = _info.getJoueursJoue();
        Suit couleurDemandee_ = _info.getProgressingTrick().couleurDemandee();
        /*
        Le pli est coupe
        */
        if (!cartesCertaines_.getVal(couleurDemandee_).get(player_).estVide()
                || cartesCertaines_.getVal(Suit.TRUMP).get(player_).estVide()
                || cartesCertaines_.getVal(Suit.TRUMP).get(player_).premiereCarte()
                .strength(couleurDemandee_) < _carteForte.strength(couleurDemandee_)) {
                /*
                Le joueur
                numero ne
                peut pas
                prendre la
                main
                */
            if (joueursConfiance_.containsObj(ramasseurVirtuel_)) {
                boolean def_ = _info.isDefender();
                if (couleursAppelees_.containsObj(couleurDemandee_)
                        && !carteAppeleeJouee_ && def_) {
                    /* The player, probably called by the current taker
                    and still owing one called card of the current led suit,
                    must follow a card belonging to the current demanded suit.*/
                    Numbers<Byte> joueursNonConfiancePreneur_ = new Numbers<Byte>();
                    for (byte j: joueursNonConfianceNonJoue_) {
                        if (j != _info.getTaker()) {
                            continue;
                        }
                        joueursNonConfiancePreneur_.add(j);
                    }
                    joueursNonConfianceNonJoue_ = joueursNonConfiancePreneur_;
                }
                /*
                On cherche a savoir si le ramasseur virtuel (joueur de
                confiance) va avec sa coupe sur la couleur demandee
                dominer tous les atouts des joueurs de non confiance
                eventuels
                */
                if (ramasseurBatAdvSur(joueursNonConfianceNonJoue_,
                        couleurDemandee_, _carteForte, cartesPossibles_,
                        cartesCertaines_)) {
                    return PossibleTrickWinner.TEAM;
                }
                /*
                On cherche les joueurs de confiance battant de maniere
                certaine les joueurs de non confiance n'ayant pas joue ou
                possedant des cartes que les joueurs ayant joue n'ont pas
                ainsi que les joueurs de non confiance n'ayant pas joue
                */
                if (existeJoueurNonJoueBattantAdv(
                        joueursNonConfianceNonJoue_,
                        joueursConfianceNonJoue_, couleurDemandee_,
                        cartesPossibles_, cartesCertaines_)) {
                    return PossibleTrickWinner.TEAM;
                }
                /*
                On cherche les joueurs de confiance battant de maniere
                certaine les joueurs de non confiance n'ayant pas joue ou
                possedant des cartes que les joueurs ayant joue n'ont pas
                ainsi que les joueurs de non confiance n'ayant pas joue
                */
                if (existeJoueurNonJoueBattantPtm(
                        joueursNonConfianceNonJoue_,
                        joueursConfianceNonJoue_, joueursJoue_,
                        couleurDemandee_, cartesPossibles_,
                        cartesCertaines_)) {
                    return PossibleTrickWinner.TEAM;
                }
                /*
                On cherche les joueurs de non confiance battant de
                maniere certaine les joueurs de confiance n'ayant pas
                joue ou possedant des cartes que les joueurs ayant joue
                n'ont pas ainsi que les joueurs de non confiance n'ayant
                pas joue
                */
                if (existeJoueurAdvRamBatAdvSur(
                        joueursConfianceNonJoue_,
                        joueursNonConfianceNonJoue_, couleurDemandee_,
                        _carteForte, cartesPossibles_, cartesCertaines_)) {
                    return PossibleTrickWinner.FOE_TEAM;
                }
                /*
                On cherche les joueurs de non confiance battant de
                maniere certaine les joueurs de confiance n'ayant pas
                joue ou possedant des cartes que les joueurs ayant joue
                n'ont pas ainsi que les joueurs de non confiance n'ayant
                pas joue
                */
                if (existeJoueurNonJoueBattantPtm(
                        joueursConfianceNonJoue_,
                        joueursNonConfianceNonJoue_, joueursJoue_,
                        couleurDemandee_, cartesPossibles_,
                        cartesCertaines_)) {
                    return PossibleTrickWinner.FOE_TEAM;
                }
                return PossibleTrickWinner.UNKNOWN;
            }
            /*
            ramasseurVirtuel n'est pas un joueur de confiance pour le
            joueur numero
            */
            if (couleursAppelees_.containsObj(couleurDemandee_) && !carteAppeleeJouee_
                    && player_ == _info.getTaker()) {
                joueursConfianceNonJoue_ = new Numbers<Byte>();
            }
            /*
            On cherche a savoir si le ramasseur virtuel (joueur de non
            confiance) bat tous les joueurs de confiance n'ayant pas joue
            */
            if (ramasseurBatAdvSur(joueursConfianceNonJoue_,
                    couleurDemandee_, _carteForte, cartesPossibles_,
                    cartesCertaines_)) {
                return PossibleTrickWinner.FOE_TEAM;
            }
            /*
            On cherche les joueurs de non confiance battant de maniere
            certaine les joueurs de confiance n'ayant pas joue ou
            possedant des cartes que les joueurs ayant joue n'ont pas
            ainsi que les joueurs de non confiance n'ayant pas joue
            */
            if (existeJoueurNonJoueBattantAdv(
                    joueursConfianceNonJoue_,
                    joueursNonConfianceNonJoue_, couleurDemandee_,
                    cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.FOE_TEAM;
            }
            /*
            On cherche les joueurs de confiance battant de maniere
            certaine les joueurs de non confiance n'ayant pas joue ou
            possedant des cartes que les joueurs ayant joue n'ont pas
            ainsi que les joueurs de non confiance n'ayant pas joue
            */
            if (existeJoueurNonJoueBattantPtm(
                    joueursConfianceNonJoue_,
                    joueursNonConfianceNonJoue_, joueursJoue_,
                    couleurDemandee_, cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.FOE_TEAM;
            }
            /*
            On cherche les joueurs de confiance battant de maniere
            certaine les joueurs de non confiance n'ayant pas joue ou
            possedant des cartes que les joueurs ayant joue n'ont pas
            ainsi que les joueurs de non confiance n'ayant pas joue
            */
            if (existeJoueurAdvRamBatAdvSur(
                    joueursNonConfianceNonJoue_,
                    joueursConfianceNonJoue_, couleurDemandee_,
                    _carteForte, cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.TEAM;
            }
            /*
            On cherche les joueurs de confiance battant de maniere
            certaine les joueurs de non confiance n'ayant pas joue ou
            possedant des cartes que les joueurs ayant joue n'ont pas
            ainsi que les joueurs de non confiance n'ayant pas joue
            */
            if (existeJoueurNonJoueBattantPtm(
                    joueursNonConfianceNonJoue_,
                    joueursConfianceNonJoue_, joueursJoue_,
                    couleurDemandee_, cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.TEAM;
            }
            return PossibleTrickWinner.UNKNOWN;
            /* Fin joueurDeConfiance.contains(ramasseurVirtuel) */
        }
        /*
        Fin
        !cartesCertaines.get(couleurDemandee).get(numero).estVide()||
        cartesCertaines
        .get(1).get(numero).estVide()||cartesCertaines.get
        (1).get(numero)
        .premiereCarte().getforceJeu(couleurDemandee)<carteForte.getforceJeu(couleurDemandee) (fin test de
        possibilite pour le joueur numero de prendre le pli)
        */
        /*
        Le joueur numero peut prendre la main en surcoupant le ramasseur
        virtuel
        */
        /*
        On cherche les joueurs de confiance battant de maniere certaine
        les joueurs de non confiance n'ayant pas joue ou possedant des
        cartes que les joueurs ayant joue n'ont pas ainsi que les joueurs
        de non confiance n'ayant pas joue
        */
        if (existeJoueurBatAdvNum(joueursNonConfianceNonJoue_,
                joueursConfianceNonJoue_, player_, couleurDemandee_,
                cartesPossibles_, cartesCertaines_)) {
            return PossibleTrickWinner.TEAM;
        }
        /*
        On cherche les joueurs de confiance battant de maniere certaine
        les joueurs de non confiance n'ayant pas joue ou possedant des
        cartes que les joueurs ayant joue n'ont pas ainsi que les joueurs
        de non confiance n'ayant pas joue
        */
        if (existeJoueurBatPtmNum(joueursNonConfianceNonJoue_,
                joueursConfianceNonJoue_, joueursJoue_, player_,
                couleurDemandee_, cartesPossibles_, cartesCertaines_)) {
            return PossibleTrickWinner.TEAM;
        }
        /*
        On cherche les joueurs de non confiance battant de maniere
        certaine les joueurs de confiance n'ayant pas joue ou possedant
        des cartes que les joueurs ayant joue n'ont pas ainsi que les
        joueurs de non confiance n'ayant pas joue
        */
        if (existeJoueurBatAdvNum(joueursConfianceNonJoue_,
                joueursNonConfianceNonJoue_, player_, couleurDemandee_,
                cartesPossibles_, cartesCertaines_)) {
            return PossibleTrickWinner.FOE_TEAM;
        }
        /*
        On cherche les joueurs de confiance battant de maniere certaine
        les joueurs de non confiance n'ayant pas joue ou possedant des
        cartes que les joueurs ayant joue n'ont pas ainsi que les joueurs
        de non confiance n'ayant pas joue
        */
        if (existeJoueurBatPtmNum(joueursConfianceNonJoue_,
                joueursNonConfianceNonJoue_, joueursJoue_, player_,
                couleurDemandee_, cartesPossibles_, cartesCertaines_)) {
            return PossibleTrickWinner.FOE_TEAM;
        }
        return PossibleTrickWinner.UNKNOWN;
    }

    static PossibleTrickWinner getPossibleTrickWinnerNoTrump(TarotInfoPliEnCours _info, CardTarot _carteForte) {
        EnumMap<Suit,EqList<HandTarot>> cartesPossibles_ = _info.getCartesPossibles();
        EnumMap<Suit,EqList<HandTarot>> cartesCertaines_ = _info.getCartesCertaines();
        byte ramasseurVirtuel_ = _info.getRamasseurVirtuel();
        Numbers<Byte> joueursNonJoue_ = _info.getJoueursNonJoue();
        boolean ramasseurVirtuelEgalCertain_;
        Numbers<Byte> joueursNonConfianceNonJoue_ = new Numbers<Byte>(
                joueursNonJoue_);
        Numbers<Byte> joueursConfianceNonJoue_ = new Numbers<Byte>(
                joueursNonJoue_);
        byte player_ = _info.getCurrentPlayer();
        Numbers<Byte> joueursConfiance_ = _info.getJoueursConfiance();
        Numbers<Byte> joueursNonConfiance_ = _info.getJoueursNonConfiance();
        joueursNonConfianceNonJoue_ = GameTarotTeamsRelation.intersectionJoueurs(joueursNonConfianceNonJoue_,joueursNonConfiance_);
        joueursConfianceNonJoue_ = GameTarotTeamsRelation.intersectionJoueurs(joueursConfianceNonJoue_,joueursConfiance_);
        Numbers<Byte> joueursJoue_ = _info.getJoueursJoue();
        Suit couleurDemandee_ = _info.getProgressingTrick().couleurDemandee();
        /* La couleur demandee n 'est pas de l 'atout et le pli n 'est pas coupe */
        ramasseurVirtuelEgalCertain_ = false;
        for (byte joueur_ : joueursConfianceNonJoue_) {
            if (vaCouper(couleurDemandee_, joueur_, cartesPossibles_, cartesCertaines_)) {
                ramasseurVirtuelEgalCertain_ = true;
            }
        }
        if (ramasseurVirtuelEgalCertain_) {
            /*
        Si un joueur de confiance n
        ayant pas joue va surement
        couper le pli
        */
            for (byte joueur_ : joueursNonConfianceNonJoue_) {
                if (!(nePeutCouper(couleurDemandee_, joueur_, cartesPossibles_, cartesCertaines_))) {
                    ramasseurVirtuelEgalCertain_ = false;
                }
            }
            if (ramasseurVirtuelEgalCertain_) {
                /*
                Si aucun joueur de non
                confiance n ayant pas
                joue ne va couper le pli
                */
                return PossibleTrickWinner.TEAM;
            }
            if (existeJoueurNonJoueBattantAdv(
                    joueursNonConfianceNonJoue_,
                    joueursConfianceNonJoue_, couleurDemandee_,
                    cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.TEAM;
            }
            if (existeJoueurNonJoueBattantPtm(
                    joueursNonConfianceNonJoue_,
                    joueursConfianceNonJoue_, joueursJoue_,
                    couleurDemandee_, cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.TEAM;
            }
            if (existeJoueurNonJoueBattantAdv(
                    joueursConfianceNonJoue_,
                    joueursNonConfianceNonJoue_, couleurDemandee_,
                    cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.FOE_TEAM;
            }
            if (existeJoueurNonJoueBattantPtm(
                    joueursConfianceNonJoue_,
                    joueursNonConfianceNonJoue_, joueursJoue_,
                    couleurDemandee_, cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.FOE_TEAM;
            }
            return PossibleTrickWinner.UNKNOWN;
        }
        for (byte joueur_ : joueursNonConfianceNonJoue_) {
            if (vaCouper(couleurDemandee_, joueur_, cartesPossibles_, cartesCertaines_)) {
                ramasseurVirtuelEgalCertain_ = true;
            }
        }
        if (ramasseurVirtuelEgalCertain_) {
            /*
            Si un joueur de non confiance
            n ayant pas joue va surement
            couper le pli
            */
            for (byte joueur_ : joueursConfianceNonJoue_) {
                if (!(nePeutCouper(couleurDemandee_, joueur_, cartesPossibles_, cartesCertaines_))) {
                    ramasseurVirtuelEgalCertain_ = false;
                }
            }
            if (ramasseurVirtuelEgalCertain_) {
                /*
                Si aucun joueur de
                confiance n ayant pas
                joue ne va couper le pli
                */
                return PossibleTrickWinner.FOE_TEAM;
            }
            if (existeJoueurNonJoueBattantAdv(
                    joueursConfianceNonJoue_,
                    joueursNonConfianceNonJoue_, couleurDemandee_,
                    cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.FOE_TEAM;
            }
            if (existeJoueurNonJoueBattantPtm(
                    joueursConfianceNonJoue_,
                    joueursNonConfianceNonJoue_, joueursJoue_,
                    couleurDemandee_, cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.FOE_TEAM;
            }
            if (existeJoueurNonJoueBattantAdv(
                    joueursNonConfianceNonJoue_,
                    joueursConfianceNonJoue_, couleurDemandee_,
                    cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.TEAM;
            }
            if (existeJoueurNonJoueBattantPtm(
                    joueursNonConfianceNonJoue_,
                    joueursConfianceNonJoue_, joueursJoue_,
                    couleurDemandee_, cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.TEAM;
            }
            return PossibleTrickWinner.UNKNOWN;
        }
        if (!cartesPossibles_.getVal(couleurDemandee_).get(player_).estVide()
                && cartesPossibles_.getVal(couleurDemandee_).get(player_)
                .premiereCarte().strength(couleurDemandee_) > _carteForte.strength(couleurDemandee_)) {
            /* Si le joueur numero peut prendre la main sans couper */
            /*
            On ne sait pas si un joueur n'ayant pas joue va couper le pli
            ou non
            */
            if (joueursConfiance_.containsObj(ramasseurVirtuel_)) {
                if (ramasseurBatSsCprAdv(
                        joueursNonConfianceNonJoue_, couleurDemandee_,
                        _carteForte, cartesPossibles_, cartesCertaines_)) {
                    return PossibleTrickWinner.TEAM;
                }
                return PossibleTrickWinner.UNKNOWN;
            }
            /* Fin joueursDeConfiance.contains(ramasseurVirtuel) */
            return PossibleTrickWinner.UNKNOWN;
        }
        /* Fin si le joueur numero peut prendre la main sans couper */
        if (peutCouper(couleurDemandee_, player_, cartesPossibles_)) {
            /* Si le joueur
            numero peut
            prendre la
            main en coupant
            */
            /*
            On cherche les joueurs de confiance battant de maniere
            certaine les joueurs de non confiance n'ayant pas joue ou
            possedant des cartes que les joueurs ayant joue n'ont pas
            ainsi que les joueurs de non confiance n'ayant pas joue
            */
            if (existeJoueurBatAdvNum(joueursNonConfianceNonJoue_,
                    joueursConfianceNonJoue_, player_, couleurDemandee_,
                    cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.TEAM;
            }
            /*
            On cherche les joueurs de confiance battant de maniere
            certaine les joueurs de non confiance n'ayant pas joue ou
            possedant des cartes que les joueurs ayant joue n'ont pas
            ainsi que les joueurs de non confiance n'ayant pas joue
            */
            if (existeJoueurBatPtmNum(joueursNonConfianceNonJoue_,
                    joueursConfianceNonJoue_, joueursJoue_, player_,
                    couleurDemandee_, cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.TEAM;
            }
            /*
            On cherche les joueurs de non confiance battant de maniere
            certaine les joueurs de confiance n'ayant pas joue ou
            possedant des cartes que les joueurs ayant joue n'ont pas
            ainsi que les joueurs de non confiance n'ayant pas joue
            */
            if (existeJoueurBatAdvNum(joueursConfianceNonJoue_,
                    joueursNonConfianceNonJoue_, player_,
                    couleurDemandee_, cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.FOE_TEAM;
            }
            /*
            On cherche les joueurs de non confiance battant de maniere
            certaine les joueurs de confiance n'ayant pas joue ou
            possedant des cartes que les joueurs ayant joue n'ont pas
            ainsi que les joueurs de non confiance n'ayant pas joue
            */
            if (existeJoueurBatPtmNum(joueursConfianceNonJoue_,
                    joueursNonConfianceNonJoue_, joueursJoue_, player_,
                    couleurDemandee_, cartesPossibles_, cartesCertaines_)) {
                return PossibleTrickWinner.FOE_TEAM;
            }
            return PossibleTrickWinner.UNKNOWN;
        }
        /* Le joueur numero ne peut pas prendre la main */
        if (joueursConfiance_.containsObj(ramasseurVirtuel_)) {
            if (ramasseurBatSsCprAdv(joueursNonConfianceNonJoue_,
                    couleurDemandee_, _carteForte, cartesPossibles_,
                    cartesCertaines_)) {
                return PossibleTrickWinner.TEAM;
            }
            return PossibleTrickWinner.UNKNOWN;
        }
        /* Fin joueursDeConfiance.contains(ramasseurVirtuel) */
        /* Maintenant le ramasseur virtuel n'est pas un joueur de confiance */
        if (ramasseurBatSsCprAdv(joueursConfianceNonJoue_,
                couleurDemandee_, _carteForte, cartesPossibles_,
                cartesCertaines_)) {
            return PossibleTrickWinner.FOE_TEAM;
        }
        return PossibleTrickWinner.UNKNOWN;
    }

    static PossibleTrickWinner getPossibleTrickWinnerTrump(TarotInfoPliEnCours _info, CardTarot _carteForte) {
        EnumMap<Suit,EqList<HandTarot>> cartesPossibles_ = _info.getCartesPossibles();
        EnumMap<Suit,EqList<HandTarot>> cartesCertaines_ = _info.getCartesCertaines();
        byte ramasseurVirtuel_ = _info.getRamasseurVirtuel();
        Numbers<Byte> joueursNonJoue_ = _info.getJoueursNonJoue();
        Numbers<Byte> joueursNonConfianceNonJoue_ = new Numbers<Byte>(
                joueursNonJoue_);
        Numbers<Byte> joueursConfianceNonJoue_ = new Numbers<Byte>(
                joueursNonJoue_);
        byte player_ = _info.getCurrentPlayer();
        Numbers<Byte> joueursConfiance_ = _info.getJoueursConfiance();
        Numbers<Byte> joueursNonConfiance_ = _info.getJoueursNonConfiance();
        joueursNonConfianceNonJoue_ = GameTarotTeamsRelation.intersectionJoueurs(joueursNonConfianceNonJoue_,joueursNonConfiance_);
        joueursConfianceNonJoue_ = GameTarotTeamsRelation.intersectionJoueurs(joueursConfianceNonJoue_,joueursConfiance_);
        Numbers<Byte> joueursJoue_ = _info.getJoueursJoue();
        /* Le pli n'est pas coupe et la couleur demandee est l'atout */
        if (cartesCertaines_.getVal(Suit.TRUMP).get(player_).estVide()
                || cartesCertaines_.getVal(Suit.TRUMP).get(player_).premiereCarte().strength(Suit.TRUMP) < _carteForte
                .strength(Suit.TRUMP)) {
            /*
            Si le joueur numero ne peut pas prendre
            la main sur demande d'atout
            */
            if (joueursConfiance_.containsObj(ramasseurVirtuel_)) {
                /*
                Si le ramasseur virtuel (de confiance, ici) domine
                certainement les joueurs de non confiance n'ayant pas joue
                */
                if (ramasseurBatAdvDemat(joueursNonConfianceNonJoue_,
                        _carteForte, cartesPossibles_)) {
                    return PossibleTrickWinner.TEAM;
                }
                /*
                On cherche les joueurs de confiance battant de maniere
                certaine les joueurs de non confiance n'ayant pas joue ou
                possedant des cartes que les joueurs ayant joue n'ont pas
                ainsi que les joueurs de non confiance n'ayant pas joue
                */
                if (existeJouBatAdvDemat(joueursNonConfianceNonJoue_,
                        joueursConfianceNonJoue_, cartesPossibles_,
                        cartesCertaines_)) {
                    return PossibleTrickWinner.TEAM;
                }
                /*
                On cherche les joueurs de confiance battant de maniere
                certaine les joueurs de non confiance n'ayant pas joue ou
                possedant des cartes que les joueurs ayant joue n'ont pas
                ainsi que les joueurs de non confiance n'ayant pas joue
                */
                if (existeJouBatPtmDemat(joueursNonConfianceNonJoue_,
                        joueursConfianceNonJoue_, joueursJoue_,
                        cartesPossibles_)) {
                    return PossibleTrickWinner.TEAM;
                }
                /*
                On cherche les joueurs de non confiance battant de maniere
                certaine les joueurs de confiance n'ayant pas joue ou
                possedant des cartes que les joueurs ayant joue n'ont pas
                ainsi que les joueurs de non confiance n'ayant pas joue
                */
                if (existeJouBatAdvSurDemat(joueursConfianceNonJoue_,
                        joueursNonConfianceNonJoue_, _carteForte,
                        cartesPossibles_, cartesCertaines_)) {
                    return PossibleTrickWinner.FOE_TEAM;
                }
                /*
                On cherche les joueurs de non confiance battant de maniere
                certaine les joueurs de confiance n'ayant pas joue ou
                possedant des cartes que les joueurs ayant joue n'ont pas
                ainsi que les joueurs de non confiance n'ayant pas joue
                */
                if (existeJouBatPtmSurDemat(joueursConfianceNonJoue_,
                        joueursNonConfianceNonJoue_, joueursJoue_,
                        _carteForte, cartesPossibles_)) {
                    return PossibleTrickWinner.FOE_TEAM;
                }
                return PossibleTrickWinner.UNKNOWN;
            }
            /*
            ramasseurVirtuel n'est pas un joueur de confiance pour le joueur
            numero
            */
            /*
            Si le ramasseur virtuel (de non confiance, ici) domine
            certainement les joueurs de non confiance n'ayant pas joue
            */
            if (ramasseurBatAdvDemat(joueursConfianceNonJoue_,
                    _carteForte, cartesPossibles_)) {
                return PossibleTrickWinner.FOE_TEAM;
            }
            /*
            On cherche les joueurs de non confiance battant de maniere
            certaine les joueurs de confiance n'ayant pas joue ou possedant
            des cartes que les joueurs ayant joue n'ont pas ainsi que les
            joueurs de non confiance n'ayant pas joue
            */
            if (existeJouBatAdvDemat(joueursConfianceNonJoue_,
                    joueursNonConfianceNonJoue_, cartesPossibles_,
                    cartesCertaines_)) {
                return PossibleTrickWinner.FOE_TEAM;
            }
            /*
            On cherche les joueurs de non confiance battant de maniere
            certaine les joueurs de confiance n'ayant pas joue ou possedant
            des cartes que les joueurs ayant joue n'ont pas ainsi que les
            joueurs de non confiance n'ayant pas joue
            */
            if (existeJouBatPtmDemat(joueursConfianceNonJoue_,
                    joueursNonConfianceNonJoue_, joueursJoue_,
                    cartesPossibles_)) {
                return PossibleTrickWinner.FOE_TEAM;
            }
            /*
            On cherche les joueurs de confiance battant de maniere certaine
            les joueurs de non confiance n'ayant pas joue ou possedant des
            cartes que les joueurs ayant joue n'ont pas ainsi que les joueurs
            de non confiance n'ayant pas joue
            */
            if (existeJouBatAdvSurDemat(joueursNonConfianceNonJoue_,
                    joueursConfianceNonJoue_, _carteForte, cartesPossibles_,
                    cartesCertaines_)) {
                return PossibleTrickWinner.TEAM;
            }
            /*
            On cherche les joueurs de confiance battant de maniere certaine
            les joueurs de non confiance n'ayant pas joue ou possedant des
            cartes que les joueurs ayant joue n'ont pas ainsi que les joueurs
            de non confiance n'ayant pas joue
            */
            if (existeJouBatPtmSurDemat(joueursNonConfianceNonJoue_,
                    joueursConfianceNonJoue_, joueursJoue_, _carteForte,
                    cartesPossibles_)) {
                return PossibleTrickWinner.TEAM;
            }
            return PossibleTrickWinner.UNKNOWN;
            /* Fin joueurDeConfiance.contains(ramasseurVirtuel) */
        }
        /*
        Fin !cartesCertaines.get(couleurDemandee).get(numero).estVide()||
        cartesCertaines
        .get(1).get(numero).estVide()||cartesCertaines.get(Suit.TRUMP)
        .get(numero).premiereCarte().getforceJeu(couleurDemandee)<carteForte.getforceJeu(couleurDemandee) (fin
        test de possibilite pour le joueur numero de prendre le pli)
        */
        /*
        Le joueur numero peut prendre la main en utilisant un atout sur
        demande d'atout
        */
        /*
        On cherche les joueurs de confiance battant de maniere certaine les
        joueurs de non confiance n'ayant pas joue ou possedant des cartes que
        les joueurs ayant joue n'ont pas ainsi que les joueurs de non
        confiance n'ayant pas joue
        */
        if (existeJouBatAdvNumDemat(joueursNonConfianceNonJoue_,
                joueursConfianceNonJoue_, player_, cartesPossibles_,
                cartesCertaines_)) {
            return PossibleTrickWinner.TEAM;
        }
        /*
        On cherche les joueurs de confiance battant de maniere certaine les
        joueurs de non confiance n'ayant pas joue ou possedant des cartes que
        les joueurs ayant joue n'ont pas ainsi que les joueurs de non
        confiance n'ayant pas joue
        */
        if (existeJouBatPtmNumDemat(joueursNonConfianceNonJoue_,
                joueursConfianceNonJoue_, joueursJoue_, player_,
                cartesPossibles_)) {
            return PossibleTrickWinner.TEAM;
        }
        /*
        On cherche les joueurs de non confiance battant de maniere certaine
        les joueurs de confiance n'ayant pas joue ou possedant des cartes que
        les joueurs ayant joue n'ont pas ainsi que les joueurs de non
        confiance n'ayant pas joue
        */
        if (existeJouBatAdvNumDemat(joueursConfianceNonJoue_,
                joueursNonConfianceNonJoue_, player_, cartesPossibles_,
                cartesCertaines_)) {
            return PossibleTrickWinner.FOE_TEAM;
        }
        /*
        On cherche les joueurs de non confiance battant de maniere certaine
        les joueurs de confiance n'ayant pas joue ou possedant des cartes que
        les joueurs ayant joue n'ont pas ainsi que les joueurs de non
        confiance n'ayant pas joue
        */
        if (existeJouBatPtmNumDemat(joueursConfianceNonJoue_,
                joueursNonConfianceNonJoue_, joueursJoue_, player_,
                cartesPossibles_)) {
            return PossibleTrickWinner.FOE_TEAM;
        }
        return PossibleTrickWinner.UNKNOWN;
    }

    static boolean existeJouBatPtmNumDemat(
            Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom,
            Numbers<Byte> _joueursJoue, byte _numero,
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles) {
        byte strength_ = _cartesPossibles.getVal(Suit.TRUMP).get(_numero).premiereCarte().strength(Suit.TRUMP);
        return beatListTrumpDemand(_equipeABattre, _equipeDom, _joueursJoue, _cartesPossibles, strength_);
    }

    static boolean existeJouBatAdvNumDemat(
            Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom, byte _numero,
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
            EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        byte strength_ = _cartesPossibles.getVal(Suit.TRUMP).get(_numero).premiereCarte().strength(Suit.TRUMP);
        return beatSureListTrumpDemand(_equipeABattre, _equipeDom, _cartesPossibles, _cartesCertaines, strength_);
    }

    static boolean existeJouBatPtmSurDemat(
            Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom,
            Numbers<Byte> _joueursJoue, CardTarot _carteForte,
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles) {
        byte strength_ = _carteForte.strength(Suit.TRUMP);
        return beatListTrumpDemand(_equipeABattre, _equipeDom, _joueursJoue, _cartesPossibles, strength_);
    }

    static boolean existeJouBatAdvSurDemat(
            Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom,
            CardTarot _carteForte, EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
            EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        byte strength_ = _carteForte.strength(Suit.TRUMP);
        return beatSureListTrumpDemand(_equipeABattre, _equipeDom, _cartesPossibles, _cartesCertaines, strength_);
    }

    static boolean existeJouBatPtmDemat(
            Numbers<Byte> _equipeABattre,Numbers<Byte> _equipeDom,
            Numbers<Byte> _joueursJoue,
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles) {
        boolean ramasseurDeter_ = false;
        for (byte joueur_ : _equipeDom) {
            if (!_cartesPossibles.getVal(Suit.TRUMP).get(joueur_).estVide()) {
                boolean joueurBatAdversaire_ = beatPossiblePlayedTrumpDemand(_equipeABattre, _joueursJoue, _cartesPossibles, joueur_);
                if (joueurBatAdversaire_) {
                    ramasseurDeter_ = true;
                }
            }
        }
        return ramasseurDeter_;
    }

    static boolean existeJouBatAdvDemat(
            Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom,
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
            EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        boolean ramasseurDeter_ = false;
        for (byte joueur_ : _equipeDom) {
            if (!_cartesCertaines.getVal(Suit.TRUMP).get(joueur_).estVide()) {
                boolean joueurBatAdversaire_ = beatFoeTrumpDemand(_equipeABattre, _cartesPossibles, _cartesCertaines, joueur_);
                if (joueurBatAdversaire_) {
                    ramasseurDeter_ = true;
                }
            }
        }
        return ramasseurDeter_;
    }

    static boolean ramasseurBatAdvDemat(
            Numbers<Byte> _equipeABattre, CardTarot _carteForte,
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles) {
        byte strength_ = _carteForte.strength(Suit.TRUMP);
        return beatSureListTrumpDemand(_equipeABattre, _cartesPossibles, strength_);
    }


    static boolean existeJoueurBatPtmNum(
            Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom,
            Numbers<Byte> _joueursJoue, byte _numero, Suit _couleurDemandee,
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
            EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        boolean ramasseurDeter_ = false;
        for (byte joueur_ : _equipeDom) {
            if (vaCouper(_couleurDemandee, joueur_, _cartesPossibles,
                    _cartesCertaines)) {
                boolean joueurBatAdversaire_ = beatBySureTrumpNormal(_equipeABattre, _joueursJoue, _couleurDemandee, _cartesPossibles, _cartesCertaines, joueur_);
                if (_cartesPossibles.getVal(Suit.TRUMP).get(joueur_).premiereCarte().strength(_couleurDemandee) <= _cartesCertaines.getVal(Suit.TRUMP).get(_numero).premiereCarte().strength(_couleurDemandee)) {
                    joueurBatAdversaire_ = false;
                }
                if (joueurBatAdversaire_) {
                    ramasseurDeter_ = true;
                }
            }
        }
        return ramasseurDeter_;
    }

    static boolean existeJoueurBatAdvNum(
            Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom, byte _numero,
            Suit _couleurDemandee, EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
            EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        byte strength_ = _cartesCertaines.getVal(Suit.TRUMP).get(_numero).premiereCarte().strength(_couleurDemandee);
        return beatSureListTrumpNormalSuit(_equipeABattre, _equipeDom, _couleurDemandee, _cartesPossibles, _cartesCertaines, strength_);
    }

    static boolean existeJoueurAdvRamBatAdvSur(
            Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom,
            Suit _couleurDemandee, CardTarot _carteForte,
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
            EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        byte strength_ = _carteForte.strength(_couleurDemandee);
        return beatSureListTrumpNormalSuit(_equipeABattre, _equipeDom, _couleurDemandee, _cartesPossibles, _cartesCertaines, strength_);
    }


    static boolean existeJoueurNonJoueBattantPtm(
            Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom,
            Numbers<Byte> _joueursJoue, Suit _couleurDemandee,
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
            EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        boolean ramasseurDeter_ = false;
        for (byte joueur_ : _equipeDom) {
            if (vaCouper(_couleurDemandee, joueur_, _cartesPossibles,
                    _cartesCertaines)) {
                boolean joueurBatAdversaire_ = beatBySureTrumpNormal(_equipeABattre, _joueursJoue, _couleurDemandee, _cartesPossibles, _cartesCertaines, joueur_);
                if (joueurBatAdversaire_) {
                    ramasseurDeter_ = true;
                }
            }
        }
        return ramasseurDeter_;
    }

    static boolean existeJoueurNonJoueBattantAdv(
            Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom,
            Suit _couleurDemandee, EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
            EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        boolean ramasseurDeter_ = false;
        for (byte joueur_ : _equipeDom) {
            /*
        On cherche les joueurs de confiance
        battant de maniere certaine les
        joueurs de non confiance n'ayant pas
        joue ou possedant des cartes que les
        joueurs ayant joue n'ont pas ainsi
        que les joueurs de non confiance
        n'ayant pas joue
        */
            if (vaCouper(_couleurDemandee, joueur_, _cartesPossibles,
                    _cartesCertaines)) {
                boolean joueurBatAdversaire_ = beatByTrumpNormalSuit(_equipeABattre, _couleurDemandee, _cartesPossibles, _cartesCertaines, joueur_);
                if (joueurBatAdversaire_) {
                    ramasseurDeter_ = true;
                }
            }
        }
        return ramasseurDeter_;
    }

    static boolean ramasseurBatSsCprAdv(
            Numbers<Byte> _equipeABattre, Suit _couleurDemandee,
            CardTarot _carteForte, EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
            EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        boolean ramasseurDeter_ = true;
        for (byte joueur_ : _equipeABattre) {
            boolean ramasseurVirtuelEgalCertain_;
            ramasseurVirtuelEgalCertain_ = !_cartesCertaines
                    .getVal(_couleurDemandee).get(joueur_).estVide();
            if (ramasseurVirtuelEgalCertain_) {
                if (_carteForte.strength(_couleurDemandee) <= _cartesPossibles.getVal(_couleurDemandee).get(joueur_).premiereCarte().strength(_couleurDemandee)) {
                    ramasseurVirtuelEgalCertain_ = false;
                }
            }
            if (defausse(_cartesPossibles, joueur_, _couleurDemandee)) {
                ramasseurVirtuelEgalCertain_ = true;
            }
            if (!ramasseurVirtuelEgalCertain_) {
                ramasseurDeter_ = false;
            }
        }
        return ramasseurDeter_;
    }

    static boolean ramasseurBatAdvSur(Numbers<Byte> _equipeABattre,
                                      Suit _couleurDemandee, CardTarot _carteForte,
                                      EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
                                      EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        byte strength_ = _carteForte.strength(_couleurDemandee);
        return beatByTrumpNormalSuitStrength(_equipeABattre, _couleurDemandee, _cartesPossibles, _cartesCertaines, strength_);
    }

    static boolean beatListTrumpDemand(Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom, Numbers<Byte> _joueursJoue, EnumMap<Suit, EqList<HandTarot>> _cartesPossibles, byte _strength) {
        boolean ramasseurDeter_ = false;
        for (byte joueur_ : _equipeDom) {
            if (!_cartesPossibles.getVal(Suit.TRUMP).get(joueur_).estVide()) {
                boolean joueurBatAdversaire_ = beatPossiblePlayedTrumpDemand(_equipeABattre, _joueursJoue, _cartesPossibles, joueur_);
                if (_cartesPossibles.getVal(Suit.TRUMP).get(joueur_).premiereCarte().strength(Suit.TRUMP) <= _strength) {
                    joueurBatAdversaire_ = false;
                }
                if (joueurBatAdversaire_) {
                    ramasseurDeter_ = true;
                }
            }
        }
        return ramasseurDeter_;
    }

    static boolean beatSureListTrumpDemand(Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom, EnumMap<Suit, EqList<HandTarot>> _cartesPossibles, EnumMap<Suit, EqList<HandTarot>> _cartesCertaines, byte _strength) {
        boolean ramasseurDeter_ = false;
        for (byte joueur_ : _equipeDom) {
            if (!_cartesCertaines.getVal(Suit.TRUMP).get(joueur_).estVide()) {
                boolean joueurBatAdversaire_ = beatFoeTrumpDemand(_equipeABattre, _cartesPossibles, _cartesCertaines, joueur_);
                if (_cartesCertaines.getVal(Suit.TRUMP).get(joueur_).premiereCarte().strength(Suit.TRUMP) <= _strength) {
                    joueurBatAdversaire_ = false;
                }
                if (joueurBatAdversaire_) {
                    ramasseurDeter_ = true;
                }
            }
        }
        return ramasseurDeter_;
    }

    static boolean beatPossiblePlayedTrumpDemand(Numbers<Byte> _equipeABattre, Numbers<Byte> _joueursJoue, EnumMap<Suit, EqList<HandTarot>> _cartesPossibles, byte _joueur) {
        boolean joueurBatAdversaire_ = beatFoeTrumpDemand(_equipeABattre, _cartesPossibles, _cartesPossibles, _joueur);
        byte strength_ = _cartesPossibles.getVal(Suit.TRUMP).get(_joueur).premiereCarte().strength(Suit.TRUMP);
        for (byte joueur_ : _joueursJoue) {
            boolean ramasseurVirtuelEgalCertain_;
            ramasseurVirtuelEgalCertain_ = _cartesPossibles.getVal(Suit.TRUMP)
                    .get(joueur_).estVide();
            if (!ramasseurVirtuelEgalCertain_) {
                if (strength_ > _cartesPossibles.getVal(Suit.TRUMP).get(joueur_).premiereCarte().strength(Suit.TRUMP)) {
                    ramasseurVirtuelEgalCertain_ = true;
                }
            }
            if (!ramasseurVirtuelEgalCertain_) {
                joueurBatAdversaire_ = false;
            }
        }
        return joueurBatAdversaire_;
    }

    static boolean beatFoeTrumpDemand(Numbers<Byte> _equipeABattre, EnumMap<Suit, EqList<HandTarot>> _cartesPossibles, EnumMap<Suit, EqList<HandTarot>> _cartesCertaines, byte _joueur) {
        byte strength_ = _cartesCertaines.getVal(Suit.TRUMP).get(_joueur).premiereCarte().strength(Suit.TRUMP);
        return beatSureListTrumpDemand(_equipeABattre, _cartesPossibles, strength_);
    }

    static boolean beatSureListTrumpDemand(Numbers<Byte> _equipeABattre, EnumMap<Suit, EqList<HandTarot>> _cartesPossibles, byte _strength) {
        boolean joueurBatAdversaire_ = true;
        for (byte joueur_ : _equipeABattre) {
            boolean ramasseurVirtuelEgalCertain_;
            ramasseurVirtuelEgalCertain_ = _cartesPossibles.getVal(Suit.TRUMP)
                    .get(joueur_).estVide();
            if (!ramasseurVirtuelEgalCertain_) {
                if (_strength > _cartesPossibles.getVal(Suit.TRUMP).get(joueur_).premiereCarte().strength(Suit.TRUMP)) {
                    ramasseurVirtuelEgalCertain_ = true;
                }
            }
            if (!ramasseurVirtuelEgalCertain_) {
                joueurBatAdversaire_ = false;
            }
        }
        return joueurBatAdversaire_;
    }
    static boolean beatSureListTrumpNormalSuit(Numbers<Byte> _equipeABattre, Numbers<Byte> _equipeDom, Suit _couleurDemandee, EnumMap<Suit, EqList<HandTarot>> _cartesPossibles, EnumMap<Suit, EqList<HandTarot>> _cartesCertaines, byte _strength) {
        boolean ramasseurDeter_ = false;
        for (byte joueur_ : _equipeDom) {
            if (vaCouper(_couleurDemandee, joueur_, _cartesPossibles,
                    _cartesCertaines)) {
                boolean joueurBatAdversaire_ = beatByTrumpNormalSuit(_equipeABattre, _couleurDemandee, _cartesPossibles, _cartesCertaines, joueur_);
                if (_cartesCertaines.getVal(Suit.TRUMP).get(joueur_).premiereCarte().strength(_couleurDemandee) <= _strength) {
                    joueurBatAdversaire_ = false;
                }
                if (joueurBatAdversaire_) {
                    ramasseurDeter_ = true;
                }
            }
        }
        return ramasseurDeter_;
    }

    static boolean beatBySureTrumpNormal(Numbers<Byte> _equipeABattre, Numbers<Byte> _joueursJoue, Suit _couleurDemandee, EnumMap<Suit, EqList<HandTarot>> _cartesPossibles, EnumMap<Suit, EqList<HandTarot>> _cartesCertaines, byte _joueur) {
        boolean joueurBatAdversaire_ = true;
        byte strength_ = _cartesPossibles.getVal(Suit.TRUMP).get(_joueur).premiereCarte().strength(_couleurDemandee);
        for (byte joueur_ : _equipeABattre) {
            boolean ramasseurVirtuelEgalCertain_;
            ramasseurVirtuelEgalCertain_ = _cartesPossibles.getVal(Suit.TRUMP)
                    .get(joueur_).estVide();
            if (!ramasseurVirtuelEgalCertain_) {
                if (strength_ > _cartesPossibles.getVal(Suit.TRUMP).get(joueur_).premiereCarte().strength(_couleurDemandee)) {
                    ramasseurVirtuelEgalCertain_ = true;
                }
            }
            if (!_cartesCertaines.getVal(_couleurDemandee).get(joueur_).estVide()) {
                ramasseurVirtuelEgalCertain_ = true;
            }
            if (!ramasseurVirtuelEgalCertain_) {
                joueurBatAdversaire_ = false;
            }
        }
        for (byte joueur_ : _joueursJoue) {
            boolean ramasseurVirtuelEgalCertain_;
            ramasseurVirtuelEgalCertain_ = _cartesPossibles.getVal(Suit.TRUMP)
                    .get(joueur_).estVide();
            if (!ramasseurVirtuelEgalCertain_) {
                if (strength_ > _cartesPossibles.getVal(Suit.TRUMP).get(joueur_).premiereCarte().strength(_couleurDemandee)) {
                    ramasseurVirtuelEgalCertain_ = true;
                }
            }
            if (!ramasseurVirtuelEgalCertain_) {
                joueurBatAdversaire_ = false;
            }
        }
        return joueurBatAdversaire_;
    }
    static boolean beatByTrumpNormalSuit(Numbers<Byte> _equipeABattre, Suit _couleurDemandee, EnumMap<Suit, EqList<HandTarot>> _cartesPossibles, EnumMap<Suit, EqList<HandTarot>> _cartesCertaines, byte _joueur) {
        byte strength_ = _cartesCertaines.getVal(Suit.TRUMP).get(_joueur).premiereCarte().strength(_couleurDemandee);
        return beatByTrumpNormalSuitStrength(_equipeABattre, _couleurDemandee, _cartesPossibles, _cartesCertaines, strength_);
    }


    static boolean beatByTrumpNormalSuitStrength(Numbers<Byte> _equipeABattre, Suit _couleurDemandee, EnumMap<Suit, EqList<HandTarot>> _cartesPossibles, EnumMap<Suit, EqList<HandTarot>> _cartesCertaines, byte _strength) {
        boolean joueurBatAdversaire_ = true;
        for (byte joueur_ : _equipeABattre) {
            boolean ramasseurVirtuelEgalCertain_;
            ramasseurVirtuelEgalCertain_ = _cartesPossibles.getVal(Suit.TRUMP)
                    .get(joueur_).estVide();
            if (!ramasseurVirtuelEgalCertain_) {
                if (_strength > _cartesPossibles.getVal(Suit.TRUMP).get(joueur_).premiereCarte().strength(_couleurDemandee)) {
                    ramasseurVirtuelEgalCertain_ = true;
                }
            }
            if (!_cartesCertaines.getVal(_couleurDemandee).get(joueur_).estVide()) {
                ramasseurVirtuelEgalCertain_ = true;
            }
            if (!ramasseurVirtuelEgalCertain_) {
                joueurBatAdversaire_ = false;
            }
        }
        return joueurBatAdversaire_;
    }
    static EnumList<Suit> couleursPouvantEtreCoupees(HandTarot _main,
                                                     Numbers<Byte> _joueurs,
                                                     EnumMap<Suit,EqList<HandTarot>> _cartesPossibles, EnumList<Suit> _couleurs) {
        EnumList<Suit> couleurs_ = new EnumList<Suit>();
        for (Suit couleur_ : _couleurs) {
            if (_main.couleur(couleur_).estVide()) {
                continue;
            }
            if(joueursSusceptiblesCoupe(_cartesPossibles, couleur_, _joueurs).isEmpty()) {
                continue;
            }
            couleurs_.add(couleur_);
        }
        return couleurs_;
    }
    static Numbers<Byte> joueursSusceptiblesCoupe(
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
            Suit _couleurDemandee,
            Numbers<Byte> _joueurs) {
        Numbers<Byte> joueursSusceptiblesDeCouper_ = new Numbers<Byte>();
        for (byte joueur_ : _joueurs) {
            if (peutCouper(_couleurDemandee, joueur_,
                    _cartesPossibles)) {
                joueursSusceptiblesDeCouper_
                        .add(joueur_);
            }
        }
        return joueursSusceptiblesDeCouper_;
    }
    static Numbers<Byte> joueursPouvantCouperCouleurs(HandTarot _main,
                                                      Numbers<Byte> _joueurs,
                                                      EnumMap<Suit,EqList<HandTarot>> _cartesPossibles, EnumList<Suit> _couleurs) {
        Numbers<Byte> joueurs_ = new Numbers<Byte>();
        for (byte joueur_ : _joueurs) {
            if(joueurs_.containsObj(joueur_)) {
                continue;
            }
            for (Suit couleur_ : _couleurs) {
                if (_main.couleur(couleur_).estVide()) {
                    continue;
                }
                if (peutCouper(couleur_, joueur_, _cartesPossibles)) {
                    joueurs_.add(joueur_);
                    break;
                }
            }
        }
        return joueurs_;
    }
    static Numbers<Byte> joueursPossedantAtout(Numbers<Byte> _joueurs,
                                               EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        Numbers<Byte> joueurs_ = new Numbers<Byte>();
        for (byte joueur_ : _joueurs) {
            if (_cartesCertaines.getVal(Suit.TRUMP).get(joueur_).estVide()) {
                continue;
            }
            joueurs_.add(joueur_);
        }
        return joueurs_;
    }
    static Numbers<Byte> joueursPouvantPossederPetit(Numbers<Byte> _joueurs,
                                                     EnumMap<Suit,EqList<HandTarot>> _cartesPossibles) {
        Numbers<Byte> joueurs_ = new Numbers<Byte>();
        EqList<HandTarot> atoutsPossibles_ = _cartesPossibles
                .getVal(Suit.TRUMP);
        for (byte joueur_ : _joueurs) {
            if (!atoutsPossibles_.get(joueur_).contient(CardTarot.petit())) {
                continue;
            }
            joueurs_.add(joueur_);
        }
        return joueurs_;
    }
    static Numbers<Byte> joueursPossedantAtoutMaitre(Numbers<Byte> _joueurs,
                                                     EnumMap<Suit,EqList<HandTarot>> _cartesCertaines, HandTarot _cartesJouees) {
        EnumMap<Suit,HandTarot> cartesJouees_ = _cartesJouees.couleurs();
        Numbers<Byte> joueurs_ = new Numbers<Byte>();
        EqList<HandTarot> atoutsCertains_ = _cartesCertaines
                .getVal(Suit.TRUMP);
        for (byte joueur_ : _joueurs) {
            if (atoutsCertains_.get(joueur_).estVide()) {
                continue;
            }
            HandTarot atouts_ = new HandTarot();
            atouts_.ajouterCartes(atoutsCertains_.get(joueur_));
            HandTarot atoutsMaitres_ = atouts_.atoutsMaitres(cartesJouees_);
            if (atoutsMaitres_.estVide()) {
                continue;
            }
            joueurs_.add(joueur_);
        }
        return joueurs_;
    }
    static Numbers<Byte> joueursPossedantNbAtout(Numbers<Byte> _joueurs,
                                                 EnumMap<Suit,EqList<HandTarot>> _cartesCertaines, Rate _nbAtoutMin) {
        Numbers<Byte> joueurs_ = new Numbers<Byte>();
        for (byte joueur_ : _joueurs) {
            if (_cartesCertaines.getVal(Suit.TRUMP).get(joueur_).total() < _nbAtoutMin.ll()) {
                continue;
            }
            joueurs_.add(joueur_);
        }
        return joueurs_;
    }
    static boolean nePeutAvoirFigures(
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles, byte _numero,
            Suit _couleur) {
        return _cartesPossibles.getVal(_couleur).get(_numero).estVide()
                || !_cartesPossibles.getVal(_couleur).get(_numero).premiereCarte()
                .isCharacter();
    }
    static boolean pasAtout(
            Numbers<Byte> _joueursDeNonConfianceNonJoue,
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles) {
        boolean pasAtout_ = true;
        for (byte joueur_ : _joueursDeNonConfianceNonJoue) {
            if (!_cartesPossibles.getVal(Suit.TRUMP).get(joueur_).estVide()) {
                pasAtout_ = false;
            }
        }
        return pasAtout_;
    }

    static boolean vaSurcouper(
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
            EnumMap<Suit,EqList<HandTarot>> _cartesCertaines, byte _numero,
            byte _numeroBis, Suit _couleurDemandee) {
        return _cartesPossibles.getVal(_couleurDemandee).get(_numeroBis).estVide()
                && !_cartesCertaines.getVal(Suit.TRUMP).get(_numeroBis).estVide()
                && _cartesCertaines.getVal(Suit.TRUMP).get(_numeroBis).premiereCarte()
                .strength(_couleurDemandee) > _cartesCertaines.getVal(Suit.TRUMP).get(_numero)
                .premiereCarte().strength(_couleurDemandee);
    }

    static boolean peutSurcouper(
            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles, byte _numero,
            byte _numeroBis, Suit _couleurDemandee) {
        return _cartesPossibles.getVal(_couleurDemandee).get(_numeroBis).estVide()
                && !_cartesPossibles.getVal(Suit.TRUMP).get(_numeroBis).estVide()
                && _cartesPossibles.getVal(Suit.TRUMP).get(_numeroBis).premiereCarte()
                .strength(_couleurDemandee) > _cartesPossibles.getVal(Suit.TRUMP).get(_numero)
                .premiereCarte().strength(_couleurDemandee);
    }

    static boolean defausse(EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
                            byte _numero, Suit _couleur) {
        return _cartesPossibles.getVal(Suit.TRUMP).get(_numero).estVide()
                && _cartesPossibles.getVal(_couleur).get(_numero).estVide();
    }
    static boolean vaCouper(Suit _couleur, byte _joueur,
                            EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
                            EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        return _cartesPossibles.getVal(_couleur).get(_joueur).estVide()
                && !_cartesCertaines.getVal(Suit.TRUMP).get(_joueur).estVide();
    }
    static boolean peutCouper(Suit _couleur, byte _numero,
                              EnumMap<Suit,EqList<HandTarot>> _cartesPossibles) {
        return _cartesPossibles.getVal(_couleur).get(_numero).estVide()
                && !_cartesPossibles.getVal(Suit.TRUMP).get(_numero).estVide();
    }
    static boolean nePeutCouper(Suit _couleur, byte _numero,
                                EnumMap<Suit,EqList<HandTarot>> _cartesPossibles,
                                EnumMap<Suit,EqList<HandTarot>> _cartesCertaines) {
        return _cartesPossibles.getVal(Suit.TRUMP).get(_numero).estVide()
                || !_cartesCertaines.getVal(_couleur).get(_numero).estVide();
    }
}