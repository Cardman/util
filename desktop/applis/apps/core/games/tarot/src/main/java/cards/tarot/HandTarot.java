package cards.tarot;
import java.util.Iterator;

import cards.consts.CardChar;
import cards.consts.Suit;
import cards.tarot.comparators.CharactersTarotGreaterPointsComparator;
import cards.tarot.comparators.GameStrengthCardTarotComparator;
import cards.tarot.enumerations.CardTarot;
import code.maths.montecarlo.AbstractGenerator;
import code.maths.montecarlo.MonteCarloUtil;
import code.util.CustList;
import code.util.EnumList;
import code.util.EnumMap;
import code.util.core.IndexConstants;

/**
    */

public final class HandTarot implements Iterable<CardTarot> {

    private EnumList<CardTarot> cards=new EnumList<CardTarot>();

    public boolean validStack() {
        return CardTarot.equalsCards(cards, pileBase().cards);
//        return CustList.equalsSet(cards, pileBase().cards, true);
    }

    public static HandTarot pileBase() {
        HandTarot liste_ = new HandTarot();
        for(CardTarot carte_: CardTarot.values()) {
            if(!carte_.isPlayable()) {
                continue;
            }
            liste_.ajouter(carte_);
        }
        return liste_;
    }
    public static HandTarot atoutsSansExcuse() {
        HandTarot liste_ = new HandTarot();
        for(CardTarot carte_: pileBase()) {
            if(carte_.couleur() != Suit.TRUMP) {
                continue;
            }
            liste_.ajouter(carte_);
        }
        liste_.trierParForceEnCours(Suit.TRUMP);
        return liste_;
    }
    public static HandTarot trumpsPlusExcuse() {
        HandTarot list_ = new HandTarot();
        list_.ajouter(CardTarot.EXCUSE);
        list_.ajouterCartes(atoutsSansExcuse());
        return list_;
    }
    public static HandTarot couleurComplete(Suit _couleur) {
        HandTarot liste_ = new HandTarot();
        for(CardTarot carte_: pileBase()) {
            if(carte_.couleur() != _couleur) {
                continue;
            }
            liste_.ajouter(carte_);
        }
        liste_.trierParForceEnCours(_couleur);
        return liste_;
    }
    static HandTarot figuesCouleurs() {
        HandTarot liste_ = new HandTarot();
        for(CardTarot carte_:pileBase()) {
            if(!carte_.isCharacter()) {
                continue;
            }
            liste_.ajouter(carte_);
        }
        return liste_;
    }
    static HandTarot cartesCouleurs() {
        HandTarot liste_ = new HandTarot();
        for(CardTarot carte_: pileBase()) {
            if(carte_.couleur() == Suit.TRUMP) {
                continue;
            }
            if(CardTarot.eq(carte_, CardTarot.excuse())) {
                continue;
            }
            liste_.ajouter(carte_);
        }
        return liste_;
    }

    static HandTarot charCards(CardChar _figure) {
        HandTarot main_ = new HandTarot();
        for (CardTarot carte_ : pileBase()) {
            if(carte_.getNomFigure() != _figure) {
                continue;
            }
            main_.ajouter(carte_);
        }
        return main_;
    }

    static HandTarot figuresMain(EnumMap<Suit,HandTarot> _repartition, CardChar _figure) {
        HandTarot main_ = new HandTarot();
        for (CardTarot carte_ : charCards(_figure)) {
            if (_repartition.getVal(carte_.couleur()).contient(carte_)) {
                main_.ajouter(carte_);
            }
        }
        return main_;
    }

    static HandTarot figureCouleur(Suit _couleur, CardChar _figure) {
        HandTarot main_ = new HandTarot();
        for(CardTarot carte_: couleurComplete(_couleur)) {
            if(carte_.getNomFigure() != _figure) {
                continue;
            }
            main_.ajouter(carte_);
        }
        return main_;
    }

    static HandTarot atoutsAuDessusDe(CardTarot _carte) {
        Suit couleur_ = _carte.couleur();
        HandTarot main_ = new HandTarot();
        for(CardTarot carte_: atoutsSansExcuse()) {
            if(carte_.strength(couleur_) < _carte.strength(couleur_)) {
                continue;
            }
            main_.ajouter(carte_);
        }
        main_.trierParForceEnCours(couleur_);
        return main_;
    }

    static HandTarot cartesCouleursAuDessusDe(CardTarot _carte) {
        Suit couleur_ = _carte.couleur();
        HandTarot main_ = new HandTarot();
        for(CardTarot carte_: couleurComplete(couleur_)) {
            if(carte_.strength(couleur_) < _carte.strength(couleur_)) {
                continue;
            }
            main_.ajouter(carte_);
        }
        main_.trierParForceEnCours(couleur_);
        return main_;
    }

    HandTarot figuresMain(CardChar _figure) {
        return figuresMain(couleurs(), _figure);
    }

    HandTarot figures() {
        HandTarot main_ = new HandTarot();
        for(CardTarot c: cards) {
            if(!c.isCharacter()) {
                continue;
            }
            main_.ajouter(c);
        }
        return main_;
    }

    /**Ajoute une carte a la fin de la main
    et precise le jeu
    @param _t la carte a ajouter*/
    public void ajouter(CardTarot _t) {
        cards.add(_t);
    }
    /**Ajoute une carte a la position donnee
    @param _t la carte a ajouter
    @param _a la position ou placer la carte*/
    public void ajouter(CardTarot _t,int _a) {
        cards.add(_a,_t);
    }
    /**Ajoute les cartes d'une main dans la main courante
    a la fin de celle-ci les cartes ajoutees conservent le
    meme ordre qu'a l'etat initial
    @param _cartes la main a ajouter*/
    public void ajouterCartes(HandTarot _cartes) {
        for(CardTarot carte_:_cartes) {
            cards.add(carte_);
        }
    }
    /**On prend la premiere carte de la main puis on la place a la fin
    dans le but de deplacer la moitie d'une main apres l'autre*/
    public void couper() {
        int taille_=total()/2;
        for (int i = IndexConstants.FIRST_INDEX; i<taille_; i++) {
            ajouter(jouer(0));
        }
    }
    /**<ol>
    <li>Choisit un nombre aleatoire entre 0 et le nombre total de carte dans la main moins une carte
    d'ou total() x Math.random() puis Math.floor(total() x Math.random())</li>
    <li>puis retire la carte choisie de la main courante et la retourne</li>
    </ol>
    @return la carte aleatoire choisie*/
    CardTarot tirerUneCarteAleatoire(AbstractGenerator _gene) {
//        return jouer((int)Math.floor(total()*MonteCarlo.randomDouble()));
        return jouer((int) MonteCarloUtil.randomLong(total(),_gene));
        //0<=total()*Math.random()<total()
        //Donc 0<=Math.floor(total()*Math.random())<Math.floor(total())=total()
    }
    /**Joue une carte a la position a
    @return la carte a jouer*/
    public CardTarot jouer(int _a) {
        CardTarot c_ = cards.get(_a);
        cards.remove(_a);
        return c_;
    }
    public void removeCardIfPresent(CardTarot _c) {
        cards.removeAllObj(_c);
    }
    public void jouer(CardTarot _c) {
        cards.remove(cards.indexOfObj(_c));
    }

    void supprimer(int _carte) {
        cards.remove(_carte);
    }

    /**Renvoie le nombre total de cartes dans la main*/
    public int total() {
        return cards.size();
    }
    /**Renvoie la carte a la position i*/
    public CardTarot carte(int _i) {
        return cards.get(_i);
    }
    public CardTarot premiereCarte() {
        return cards.first();
    }
    public CardTarot derniereCarte() {
        return cards.last();
    }
    /**Tester l'existence d'une carte dans une main*/
    public boolean contient(CardTarot _c) {
        return cards.containsObj(_c);
    }
    public boolean contientCartes(HandTarot _c) {
        return cards.containsAllObj(_c.cards);
    }
    /**Supprime les cartes d'une main tout en conservant les eventuels doublons*/
    public void supprimerCartes(HandTarot _m) {
        int indice_;
        for(CardTarot c:_m) {
            indice_=position(c);
            if(indice_>-1) {
                supprimer(indice_);
            }
        }
    }
    public void supprimerCartes() {
        cards.clear();
    }
    public int position(CardTarot _c) {
        return cards.indexOfObj(_c);
    }

    void sortCharsByGreaterPoints() {
        cards.sortElts(new CharactersTarotGreaterPointsComparator());
    }

    public int tailleCouleur(Suit _couleur) {
        int taille_=0;
        for(CardTarot c:cards) {
            if(c.couleur()==_couleur) {
                taille_++;
            }
        }
        return taille_;
    }



    public boolean estVide() {
        return cards.isEmpty();
    }
    /**Permet de trier les cartes selon les parametres
    @param _couleurs une chaine de couleurs espacee par des tirets
    @param sens croissant ou decroissant*/
    public void trier(EnumList<Suit> _couleurs, boolean _decroissant) {

        EnumList<CardTarot> nouvelleMain_ = new EnumList<CardTarot>();
        for(Suit couleur_: _couleurs) {
            HandTarot mainCouleur_ = new HandTarot();
            for(CardTarot carte_: cards) {
                if(carte_.couleur() == couleur_) {
                    mainCouleur_.ajouter(carte_);
                }
            }
            if(_decroissant) {
                mainCouleur_.trierParForceEnCours(couleur_);
            }else {
                mainCouleur_.trierParForceEcart(couleur_);
            }
            if(couleur_ == Suit.TRUMP && cards.containsObj(CardTarot.EXCUSE)) {
                if(_decroissant) {
                    mainCouleur_.ajouter(CardTarot.EXCUSE,0);
                }else {
                    mainCouleur_.ajouter(CardTarot.EXCUSE);
                }
            }

            nouvelleMain_.addAllElts(mainCouleur_.cards);
        }



        cards.clear();
        cards.addAllElts(nouvelleMain_);
    }

    void trierParForceEnCours(Suit _couleurDemandee) {
        cards.sortElts(new GameStrengthCardTarotComparator(_couleurDemandee, true));
    }
    //Tri par ordre croissant
    void trierParForceEcart(Suit _couleurDemandee) {
        cards.sortElts(new GameStrengthCardTarotComparator(_couleurDemandee, false));
    }

    public int nombreDeFigures() {
        int nb_=0;
        for(Suit couleur_:Suit.couleursOrdinaires()) {
            nb_+=nombreDeFigures(couleur_);
        }
        return nb_;
    }
    public int nombreDeBouts() {
        return bouts().total();
    }
    public HandTarot bouts() {
        HandTarot mt_=new HandTarot();
        for(CardTarot carte_:cards) {
            if(carte_.estUnBout()) {
                mt_.ajouter(carte_);
            }
        }
        return mt_;
    }
    public int tailleRois() {
        int taille_=0;
        for(CardTarot c:cards) {
            if(c.getNomFigure() == CardChar.KING) {
                taille_++;
            }
        }
        return taille_;
    }
    public int tailleDames() {
        int taille_=0;
        for(CardTarot c:cards) {
            if(c.getNomFigure() == CardChar.QUEEN) {
                taille_++;
            }
        }
        return taille_;
    }
    public int tailleCavaliers() {
        int taille_=0;
        for(CardTarot c:cards) {
            if(c.getNomFigure() == CardChar.KNIGHT) {
                taille_++;
            }
        }
        return taille_;
    }
    public int tailleValets() {
        int taille_=0;
        for(CardTarot c:cards) {
            if(c.getNomFigure() == CardChar.JACK) {
                taille_++;
            }
        }
        return taille_;
    }
    /**Ne leve pas d exception*/
    public int nombreDeFigures(Suit _couleur) {
        int nb_= IndexConstants.SIZE_EMPTY;
        for(CardTarot c: cards) {
            if(c.isCharacter()&&c.couleur()==_couleur) {
                nb_++;
            }
        }
        return nb_;
    }
    public CustList<HandTarot> eclaterDebutPartie() {
        if(estVide()) {
            return new CustList<HandTarot>();
        }
        return eclaterEnCours(new HandTarot().couleurs(), premiereCarte().couleur());
    }
    public EnumMap<Suit,CustList<HandTarot>> eclaterToutEnCours(EnumMap<Suit, HandTarot> _cartesJouees) {
        EnumMap<Suit,CustList<HandTarot>> suitesTouteCouleur_ = new EnumMap<Suit,CustList<HandTarot>>();
        EnumMap<Suit,HandTarot> repartition_ = couleurs();
        suitesTouteCouleur_.put(CardTarot.EXCUSE.couleur(),repartition_.getVal(CardTarot.excuse().couleur()).eclaterEnCours(
                _cartesJouees, CardTarot.EXCUSE.couleur()));
        suitesTouteCouleur_.put(Suit.TRUMP,repartition_.getVal(Suit.TRUMP).eclaterEnCours(
                _cartesJouees, Suit.TRUMP));
        for (Suit i : Suit.couleursOrdinaires()) {
            suitesTouteCouleur_.put(i,repartition_.getVal(i).eclaterEnCours(
                    _cartesJouees, i));
            //les couleurs sont classees comme si elles etaient demandees
        }
        return suitesTouteCouleur_;
    }
    /**Eclate une couleur en suite en tenant compte des cartes jouees
    Exemple: pour l'atout 21 - 20 - 19 forment une suite de trois cartes*/
    public CustList<HandTarot> eclaterEnCours(EnumMap<Suit,HandTarot> _cartesJouees,Suit _couleurDemandee) {
        if(estVide()) {
            return new CustList<HandTarot>();
        }
        CustList<HandTarot> suites_=new CustList<HandTarot>();
        Suit couleur_=premiereCarte().couleur();
        boolean ajouterVec_ = true;
        if(couleur_ == CardTarot.excuse().couleur()) {
            suites_.add(new HandTarot());
            suites_.last().ajouter(CardTarot.excuse());
        }else if(couleur_ == Suit.TRUMP) {
            for(CardTarot carte_:atoutsSansExcuse()) {
                if(_cartesJouees.getVal(couleur_).contient(carte_)) {
                    continue;
                }
                if(!contient(carte_)) {
                    ajouterVec_ = true;
                    continue;
                }
                if(ajouterVec_) {
                    sortIfNotEmpty(_couleurDemandee, suites_);
                    suites_.add(new HandTarot());
                }
                ajouterVec_ = false;
                suites_.last().ajouter(carte_);
            }
            sortIfNotEmpty(_couleurDemandee, suites_);
        }else {
            for(CardTarot carte_:couleurComplete(couleur_)) {
                if(_cartesJouees.getVal(couleur_).contient(carte_)) {
                    continue;
                }
                if(!contient(carte_)) {
                    ajouterVec_ = true;
                    continue;
                }
                if(ajouterVec_) {
                    sortIfNotEmpty(_couleurDemandee, suites_);
                    suites_.add(new HandTarot());
                }
                ajouterVec_ = false;
                suites_.last().ajouter(carte_);
            }
            sortIfNotEmpty(_couleurDemandee, suites_);
        }
        return suites_;

    }

    private static void sortIfNotEmpty(Suit _couleurDemandee, CustList<HandTarot> _suites) {
        if(!_suites.isEmpty()) {
            _suites.last().trierParForceEnCours(_couleurDemandee);
        }
    }

    public HandTarot atoutsMaitres(EnumMap<Suit,HandTarot> _cartesJouees) {
        if(estVide()) {
            return new HandTarot();
        }
        HandTarot couleurTotale_ = atoutsSansExcuse();
        Suit couleur_=Suit.TRUMP;
        HandTarot cartes_ = couleur(couleur_);
        HandTarot cartesJoueesOuPossedees_ = new HandTarot();
        HandTarot played_ = new HandTarot();
        if(!_cartesJouees.isEmpty()) {
            played_.ajouterCartes(_cartesJouees.getVal(couleur_));
        }
        //C'est_ la_ reunion_ des_ cartes_ jouees_ dans_ le_ jeu_ et_ de_ celles_ du_ joueur_
        cartesJoueesOuPossedees_.ajouterCartes(played_);
        cartesJoueesOuPossedees_.ajouterCartes(cartes_);
        cartesJoueesOuPossedees_.trierParForceEnCours(couleur_);
        HandTarot cartesMaitresses_ = new HandTarot();
        int nbPlayedOrOwnedCards_ = cartesJoueesOuPossedees_.total();
        for (byte c = IndexConstants.FIRST_INDEX; c < nbPlayedOrOwnedCards_; c++) {
            if (!CardTarot.eq(cartesJoueesOuPossedees_.carte(c),
                    couleurTotale_.carte(c))) {
                break;
            }
            if (!cartes_.contient(cartesJoueesOuPossedees_.carte(c))) {
                continue;
            }
            cartesMaitresses_.ajouter(cartesJoueesOuPossedees_.carte(c));
        }
        if (cartesMaitresses_.total() >= couleurTotale_
                .total() - played_.total() - cartes_.total()) {
            for (CardTarot carte_ : cartes_) {
                if (!cartesMaitresses_.contient(carte_)) {
                    cartesMaitresses_.ajouter(carte_);
                }
            }
        }

        cartesMaitresses_.trierParForceEnCours(couleur_);
        return cartesMaitresses_;
    }
    public HandTarot charCardsBySuit(Suit _couleur) {
        HandTarot m=new HandTarot();
        for(CardTarot carte_:cards) {
            if(carte_.couleur()==_couleur&&carte_.isCharacter()) {
                m.ajouter(carte_);
            }
        }
        return m;
    }
    public HandTarot cartesBasses(Suit _couleur) {
        HandTarot m=new HandTarot();
        for(CardTarot carte_:cards) {
            if(carte_.couleur()==_couleur&&!carte_.isCharacter()) {
                m.ajouter(carte_);
            }
        }
        return m;
    }
    /**Construit une nouvelle main telle que les cartes aient toutes leurs couleurs valant
    la couleur passee en parametres et trie la main dans l'ordre decroissant des valeurs
    @param _couleur le numero de couleur a passer en parametres (0:Excuse 1:Atout(Excuse exclue) 2:Coeur 3:Pique 4:Carreau 5:Tr&egrave;fle)
    @return une main triee avec les cartes de meme couleur que celle passee en parametres*/
    public HandTarot couleur(Suit _couleur) {
        HandTarot m=new HandTarot();
        for(CardTarot c:cards) {
            if(c.couleur()==_couleur) {
                m.ajouter(c);
            }
        }
        m.trierParForceEnCours(_couleur);
        return m;
    }
    /**Donne la repartition des cartes de la main dans les differentes couleurs
    pour une main de tarot les cartes d'une meme couleur sont classees par ordre decroissant de la valeur
    @return un vecteur de mains ordonnee par numero de couleur croissant*/
    public EnumMap<Suit,HandTarot> couleurs() {
        EnumMap<Suit,HandTarot> mains_=new EnumMap<Suit,HandTarot>();
        mains_.put(CardTarot.EXCUSE.couleur(),couleur(CardTarot.excuse().couleur()));
        mains_.put(Suit.TRUMP,couleur(Suit.TRUMP));
        for(Suit b: Suit.couleursOrdinaires()) {
            mains_.put(b,couleur(b));
        }
        return mains_;
    }
    static HandTarot reunion(EnumMap<Suit,HandTarot> _couleurs) {
        HandTarot main_ = new HandTarot();
        for(HandTarot m: _couleurs.values()) {
            main_.ajouterCartes(m);
        }
        return main_;
    }
    public static boolean equalsSet(HandTarot _handOne, HandTarot _handTwo) {
        return CardTarot.equalsCards(_handOne.cards, _handTwo.cards);
//        return CustList.equalsSet(_handOne.cards, _handTwo.cards, true);
    }

    @Override
    public Iterator<CardTarot> iterator() {
        return cards.iterator();
    }

    public EnumList<CardTarot> getCards() {
        return cards;
    }

    public void setCards(EnumList<CardTarot> _cards) {
        cards = _cards;
    }


}
