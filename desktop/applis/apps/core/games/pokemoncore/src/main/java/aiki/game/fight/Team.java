package aiki.game.fight;
import aiki.comments.Comment;
import aiki.comparators.ComparatorGroundPlaceKey;
import aiki.db.DataBase;
import aiki.fight.abilities.AbilityData;
import aiki.fight.util.ListEffectCombo;
import aiki.game.fight.util.ListActivityOfMove;
import aiki.game.fight.util.ListActivityOfMoves;
import aiki.game.params.Difficulty;
import aiki.game.player.Player;
import aiki.map.pokemon.PkTrainer;
import aiki.map.pokemon.PokemonPlayer;
import aiki.map.pokemon.WildPk;
import code.maths.LgInt;
import code.util.CustList;
import code.util.EntryCust;
import code.util.EqList;
import code.util.*;
import code.util.StringList;
import code.util.StringMap;
import code.util.core.IndexConstants;
import code.util.core.NumberUtil;
import code.util.core.StringUtil;


public final class Team {

    public static final String EQUIPE_NB_UTILISATION = "EQUIPE_NB_UTILISATION";
    public static final String EQUIPE_ADV_NB_UTILISATION = "EQUIPE_ADV_NB_UTILISATION";
    public static final String NB_UTILI_ATT_EQ_TOUR = "NB_UTILI_ATT_EQ_TOUR";

    public static final String TEAM = "aiki.game.fight.team";

    private static final String CANCEL_USE_ITEM = "cancelUseItem";

    private static final String USE_ITEM = "useItem";

    /***/
    private ListActivityOfMoves enabledMovesByGroup;

    /***/
    private StringMap<ActivityOfMove> enabledMoves;

    /***/
    private StringMap<Boolean> enabledMovesWhileSendingFoe;

    /***/
    private StringMap<LgInt> enabledMovesWhileSendingFoeUses;

    /***/
    private StringMap<Integer> nbUsesMoves;

    /***/
    private StringMap<Integer> nbUsesMovesRound;

    /***/
//    private Map<Pair<String,Byte>,Pair<Byte,Pair<Boolean,Boolean> > > healAfter;
    private StringMap<ByteMap<StacksOfUses>> healAfter;

    /***/
    //private Map<Pair<String,Byte>,Anticipation> movesAnticipation;
    private StringMap<ByteMap<Anticipation>> movesAnticipation;

    /***/
    private ByteMap<Fighter> members;

    /***/
    private ByteMap<Bytes> playerFightersAgainstFoe;

    /***/
    private byte nbKoRound;

    /***/
    private byte nbKoPreviousRound;

    /***/
    private StringList successfulMovesRound;

    /***/
    private Comment comment = new Comment();

    public Team(){

    }

    public Team(DataBase _import){
        enabledMoves = new StringMap<ActivityOfMove>();
        for(String e:_import.getMovesEffectTeam()){
            enabledMoves.put(e,new ActivityOfMove());
        }
        enabledMovesWhileSendingFoe = new StringMap<Boolean>();
        enabledMovesWhileSendingFoeUses = new StringMap<LgInt>();
        for(String e:_import.getMovesEffectWhileSending()){
            enabledMovesWhileSendingFoe.put(e,false);
            enabledMovesWhileSendingFoeUses.put(e,LgInt.zero());
        }
        enabledMovesByGroup = new ListActivityOfMoves();
        for(ListEffectCombo c:_import.getCombos().getEffects()){
            enabledMovesByGroup.add(new ListActivityOfMove(c.getList(),new ActivityOfMove()));
        }
        nbKoRound=0;
        nbKoPreviousRound=0;
        StringList attaques_ = new StringList();
        attaques_.addAllElts(_import.getVarParamsMove(EQUIPE_NB_UTILISATION));
        attaques_.addAllElts(_import.getVarParamsMove(EQUIPE_ADV_NB_UTILISATION));
        attaques_.removeDuplicates();
        nbUsesMoves = new StringMap<Integer>();
        for(String e:attaques_){
            nbUsesMoves.put(e,0);
        }
        attaques_.clear();
        attaques_.addAllElts(_import.getVarParamsMove(NB_UTILI_ATT_EQ_TOUR));
        nbUsesMovesRound = new StringMap<Integer>();
        for(String e:attaques_){
            nbUsesMovesRound.put(e,0);
        }
        members = new ByteMap<Fighter>();
        healAfter = new StringMap<ByteMap<StacksOfUses>>();
        movesAnticipation = new StringMap<ByteMap<Anticipation>>();
        successfulMovesRound = new StringList();
    }

    void initEquipeUtilisateur(Player _utilisateur, Difficulty _diff,
            byte _maxNumberFrontFighters,
            byte _mult,
            DataBase _import, CustList<PkTrainer> _team) {
        playerFightersAgainstFoe = new ByteMap<Bytes>();
        byte nbPks_=(byte) _utilisateur.getTeam().size();
        byte i_= IndexConstants.FIRST_INDEX;
        byte place_;
        byte back_ = Fighter.BACK;
        int max_ = IndexConstants.INDEX_NOT_FOUND_ELT;
        for(byte i = IndexConstants.FIRST_INDEX; i<nbPks_; i++){
            if (!(_utilisateur.getTeam().get(i) instanceof PokemonPlayer)){
                continue;
            }
            PokemonPlayer posPk_=(PokemonPlayer) _utilisateur.getTeam().get(i);
            if(!posPk_.isKo()){
                if (i_<_maxNumberFrontFighters) {
                    place_=i_;
                    i_++;
                }else{
                    place_=back_;
                }
            }else{
                place_=back_;
            }
            Fighter creatureCbt_= new Fighter(posPk_,_import,place_);
            creatureCbt_.initIvUt(_diff);
            max_ = i;
            members.put(i,creatureCbt_);
            playerFightersAgainstFoe.put(i,new Bytes());
        }
        nbPks_=(byte) _team.size();
        i_= IndexConstants.FIRST_INDEX;
        int dec_ = max_ + 1;
        byte diff_ = (byte) (_mult - _maxNumberFrontFighters);
        for(byte i = IndexConstants.FIRST_INDEX; i<nbPks_; i++){
            PkTrainer posPk_= _team.get(i);
            if(i_<diff_){
                place_=(byte) (i_+_maxNumberFrontFighters);
                i_++;
            }else{
                place_=back_;
            }
            Fighter creatureCbt_= new Fighter(posPk_,_import,place_);
            int nbPossibleGenders_ = _import.getPokemon(creatureCbt_.getName()).getGenderRep().getPossibleGenders().size();
            if (nbPossibleGenders_ > DataBase.ONE_POSSIBLE_CHOICE) {
                creatureCbt_.setGender(_utilisateur.getOppositeGenderForPokemon());
                creatureCbt_.setCurrentGender(_utilisateur.getOppositeGenderForPokemon());
            }
            creatureCbt_.initIvUt(_diff);
            creatureCbt_.setRemainingHp(creatureCbt_.pvMax());
            members.put((byte) (i+dec_),creatureCbt_);
        }
        for (String e: _import.getMovesHealingAfter()) {
            ByteMap<StacksOfUses> map_;
            map_ = new ByteMap<StacksOfUses>();
            for(byte i = IndexConstants.FIRST_INDEX; i<_mult; i++){
                map_.put(i,new StacksOfUses());
            }
            healAfter.put(e, map_);
        }
        for (String e: _import.getMovesAnticipation()) {
            ByteMap<Anticipation> map_;
            map_ = new ByteMap<Anticipation>();
            for(byte i = IndexConstants.FIRST_INDEX; i<_mult; i++){
                Anticipation ant_ = new Anticipation();
                ant_.setTargetPosition(new TargetCoords((short) 0, Fighter.BACK));
                map_.put(i,ant_);
            }
            movesAnticipation.put(e, map_);
        }
    }

    void initEquipeUtilisateur(Player _utilisateur,Difficulty _diff,short _multiplicite,DataBase _import){
        playerFightersAgainstFoe = new ByteMap<Bytes>();
        byte nbPks_=(byte) _utilisateur.getTeam().size();
        byte i_= IndexConstants.FIRST_INDEX;
        byte place_;
        byte back_ = Fighter.BACK;
        for(byte i = IndexConstants.FIRST_INDEX; i<nbPks_; i++){
            if (!(_utilisateur.getTeam().get(i) instanceof PokemonPlayer)){
                continue;
            }
            PokemonPlayer posPk_=(PokemonPlayer) _utilisateur.getTeam().get(i);
            if(i_<_multiplicite&&!posPk_.isKo()){
                place_=i_;
                i_++;
            }else{
                place_=back_;
            }
            Fighter creatureCbt_= new Fighter(posPk_,_import,place_);
            creatureCbt_.initIvUt(_diff);
            members.put(i,creatureCbt_);
            playerFightersAgainstFoe.put(i,new Bytes());
        }
        for (String e: _import.getMovesHealingAfter()) {
            ByteMap<StacksOfUses> map_;
            map_ = new ByteMap<StacksOfUses>();
            for(byte i = IndexConstants.FIRST_INDEX; i<_multiplicite; i++){
                map_.put(i,new StacksOfUses());
            }
            healAfter.put(e, map_);
        }
        for (String e: _import.getMovesAnticipation()) {
            ByteMap<Anticipation> map_;
            map_ = new ByteMap<Anticipation>();
            for(byte i = IndexConstants.FIRST_INDEX; i<_multiplicite; i++){
                Anticipation ant_ = new Anticipation();
                ant_.setTargetPosition(new TargetCoords((short) 0, Fighter.BACK));
                map_.put(i,ant_);
            }
            movesAnticipation.put(e, map_);
        }
    }

    void initPokemonSauvage(Player _utilisateur,Difficulty _diff, int _index, WildPk _pokemon,DataBase _import){
        playerFightersAgainstFoe = new ByteMap<Bytes>();
        Fighter creatureCbt_= new Fighter(_pokemon,_import, (byte) _index);
        if(_utilisateur.estAttrape(_pokemon.getName())){
            creatureCbt_.initIvAdv(_diff,_import.getDefaultBall());
        }else{
            creatureCbt_.initIvAdv(_diff,DataBase.EMPTY_STRING);
        }
        members.put((byte)_index,creatureCbt_);
        for (String e: _import.getMovesHealingAfter()) {
            ByteMap<StacksOfUses> map_;
            map_ = new ByteMap<StacksOfUses>();
            map_.put((byte)_index,new StacksOfUses());
            healAfter.put(e, map_);
        }
        for (String e: _import.getMovesAnticipation()) {
            ByteMap<Anticipation> map_;
            map_ = new ByteMap<Anticipation>();
            Anticipation ant_ = new Anticipation();
            ant_.setTargetPosition(new TargetCoords((short) 0, Fighter.BACK));
            map_.put((byte)_index,ant_);
            movesAnticipation.put(e, map_);
        }
    }

    void initEquipeAdversaire(Player _utilisateur,CustList<PkTrainer> _equipe,Difficulty _diff, short _multiplicite,DataBase _import){
        playerFightersAgainstFoe = new ByteMap<Bytes>();
        byte nbPks_=(byte) _equipe.size();
        byte back_ = Fighter.BACK;
        for(byte i = IndexConstants.FIRST_INDEX; i<nbPks_; i++){
            PkTrainer p_=_equipe.get(i);
            byte place_;
            if(i<_multiplicite){
                place_=i;
            }else{
                place_=back_;
            }
            Fighter creatureCbt_= new Fighter(p_,_import,place_);
            if(_utilisateur.estAttrape(creatureCbt_.getName())){
                creatureCbt_.initIvAdv(_diff,_import.getDefaultBall());
            }else{
                creatureCbt_.initIvAdv(_diff,DataBase.EMPTY_STRING);
            }
            members.put(i,creatureCbt_);
        }
        for (String e: _import.getMovesHealingAfter()) {
            ByteMap<StacksOfUses> map_;
            map_ = new ByteMap<StacksOfUses>();
            for(byte i = IndexConstants.FIRST_INDEX; i<_multiplicite; i++){
                map_.put(i,new StacksOfUses());
            }
            healAfter.put(e, map_);
        }
        for (String e: _import.getMovesAnticipation()) {
            ByteMap<Anticipation> map_;
            map_ = new ByteMap<Anticipation>();
            for(byte i = IndexConstants.FIRST_INDEX; i<_multiplicite; i++){
                Anticipation ant_ = new Anticipation();
                ant_.setTargetPosition(new TargetCoords((short) 0, Fighter.BACK));
                map_.put(i,ant_);
            }
            movesAnticipation.put(e, map_);
        }
    }


    void initRelationsCombattant(EqList<TeamPosition> _cbts,DataBase _import){
        for(Fighter f:members.values()){
            f.initCreatureRelationsAutre(_cbts,_import);
        }
    }

    //some tests with a clean data base with all type of move, ability, item, tree pokemon
    //This class is full tested
    public boolean validate(DataBase _data, byte _numberTeam, Fight _fight) {
        int mult_ = _fight.getMult();
        for (byte k: members.getKeys()) {
            if (!members.getVal(k).validate(_data, _numberTeam, _fight)) {
                return false;
            }
            if (members.getVal(k).getGroundPlaceSubst() >= _fight.getMult()) {
                return false;
            }
            if (members.getVal(k).getGroundPlace() >= _fight.getMult()) {
                return false;
            }
        }
        //later places_.getMin >= 0 && places_.getMax < _fight.getMult()
        // && places_.duplicates == 0 => places_.size <= _fight.getMult()
        //later placesSubst_.getMin >= 0 && placesSubst_.getMax <= _fight.getMult()
        // && placesSubst_.duplicates == 0 => placesSubst_.size <= _fight.getMult()
        if (!StringUtil.equalsSet(_data.getMovesEffectTeam(), enabledMoves.getKeys())) {
            return false;
        }
        for (String m: enabledMoves.getKeys()) {
            if (enabledMoves.getVal(m).getNbTurn() < 0) {
                return false;
            }
        }
        if (!StringUtil.equalsSet(_data.getMovesEffectWhileSending(), enabledMovesWhileSendingFoe.getKeys())) {
            return false;
        }
        if (!StringUtil.equalsSet(_data.getMovesEffectWhileSending(), enabledMovesWhileSendingFoeUses.getKeys())) {
            return false;
        }
        for (String m: enabledMovesWhileSendingFoeUses.getKeys()) {
            if (!enabledMovesWhileSendingFoeUses.getVal(m).isZeroOrGt()) {
                return false;
            }
        }
        if (!StringUtil.equalsStringListSet(_data.getCombos().getEffects().getKeys(), enabledMovesByGroup.getKeys())) {
            return false;
        }
        for (StringList m: enabledMovesByGroup.getKeys()) {
            if (enabledMovesByGroup.getVal(m).getNbTurn() < 0) {
                return false;
            }
        }
        if (nbKoRound < 0) {
            return false;
        }
        if (nbKoPreviousRound < 0) {
            return false;
        }
        StringList attaques_ = new StringList();
        attaques_.addAllElts(_data.getVarParamsMove(EQUIPE_NB_UTILISATION));
        attaques_.addAllElts(_data.getVarParamsMove(EQUIPE_ADV_NB_UTILISATION));
        if (!StringUtil.equalsSet(attaques_, nbUsesMoves.getKeys())) {
            return false;
        }
        for (String m: nbUsesMoves.getKeys()) {
            if (nbUsesMoves.getVal(m) < 0) {
                return false;
            }
        }
        attaques_.clear();
        attaques_.addAllElts(_data.getVarParamsMove(NB_UTILI_ATT_EQ_TOUR));
        if (!StringUtil.equalsSet(attaques_, nbUsesMovesRound.getKeys())) {
            return false;
        }
        for (String m: nbUsesMovesRound.getKeys()) {
            if (nbUsesMovesRound.getVal(m) < 0) {
                return false;
            }
        }
        Bytes keysMovesLatter_ = new Bytes();
        for (byte i = IndexConstants.FIRST_INDEX; i<mult_; i++) {
            keysMovesLatter_.add(i);
        }
        StringList movesTeam_ = new StringList();
        EqList<MoveUsesTeam> relMovesTh_;
        EqList<MoveUsesTeam> relMovesExp_;
        relMovesTh_ = new EqList<MoveUsesTeam>();
        movesTeam_.addAllElts(_data.getMovesHealingAfter());
        for (byte f: keysMovesLatter_) {
            for (String m: movesTeam_) {
                relMovesTh_.add(new MoveUsesTeam(m, f));
            }
        }
        relMovesExp_ = new EqList<MoveUsesTeam>();
        for (EntryCust<String,ByteMap<StacksOfUses>> k: healAfter.entryList()) {
            for (byte b: k.getValue().getKeys()) {
                relMovesExp_.add(new MoveUsesTeam(k.getKey(),b));
            }
        }
        if (!MoveUsesTeam.equalsSet(relMovesTh_, relMovesExp_)) {
            return false;
        }
        relMovesTh_ = new EqList<MoveUsesTeam>();
        relMovesExp_ = new EqList<MoveUsesTeam>();
        movesTeam_.clear();
        movesTeam_.addAllElts(_data.getMovesAnticipation());
        for (byte f: keysMovesLatter_) {
            for (String m: movesTeam_) {
                relMovesTh_.add(new MoveUsesTeam(m, f));
            }
        }
        for (EntryCust<String,ByteMap<Anticipation>> k: movesAnticipation.entryList()) {
            for (byte b: k.getValue().getKeys()) {
                relMovesExp_.add(new MoveUsesTeam(k.getKey(),b));
            }
        }
        if (!MoveUsesTeam.equalsSet(relMovesTh_, relMovesExp_)) {
            return false;
        }
        for (ByteMap<StacksOfUses> k: healAfter.values()) {
            for (StacksOfUses s: k.values()) {
                if (!s.isValid()) {
                    return false;
                }
            }
        }
        for (ByteMap<Anticipation> k: movesAnticipation.values()) {
            for (Anticipation s: k.values()) {
                if (!s.isValid()) {
                    return false;
                }
                if (!NumberUtil.eq(s.getTargetPosition().getPosition(),Fighter.BACK)) {
                    if (s.getTargetPosition().getPosition() < 0) {
                        return false;
                    }
                }
            }
        }
        ByteMap< Fighter> members_ = _fight.getFoeTeam().getMembers();
        if (_numberTeam == Fight.CST_PLAYER) {
            for (byte b:_fight.getUserTeam().getMembers().getKeys()) {
                if (!_fight.getUserTeam().getMembers().getVal(b).isBelongingToPlayer()) {
                    continue;
                }
                if (!members_.containsAllAsKeys(playerFightersAgainstFoe.getVal(b))) {
                    return false;
                }
            }
        }
        for (String m: successfulMovesRound) {
            if (!_data.getMoves().contains(m)) {
                return false;
            }
        }
        return !this.members.isEmpty();
    }

    void initRoundTeam() {
        clearSuccessfuleMovesRound();
        chooseMoves();
        setNbKoRound();
    }

    void move(byte _decalage){
        CustList<GroundPlaceKey> combattantsPositions_ = new CustList<GroundPlaceKey>();
        for(byte c:members.getKeys()){
            Fighter membre_=members.getVal(c);
            if(!membre_.estArriere()){
                byte place_=membre_.getGroundPlace();
                combattantsPositions_.add(new GroundPlaceKey(place_,c));
            }
        }
        combattantsPositions_.sortElts(new ComparatorGroundPlaceKey());
        short i_=0;
        for(GroundPlaceKey e:combattantsPositions_){
            byte cle_= (byte) e.getKey();
            members.getVal(cle_).setGroundPlace((byte) (i_+_decalage));
            i_++;
        }
    }

    void activerEffetEquipe(String _attaque){
        enabledMoves.getVal(_attaque).enableReset();
    }

    void desactiverEffetEquipe(String _attaque){
        enabledMoves.getVal(_attaque).disable();
        enabledMoves.getVal(_attaque).reset();
    }

    void ajouterEffetEquipeEntreeAdv(String _attaque){
        enabledMovesWhileSendingFoe.put(_attaque,true);
        enabledMovesWhileSendingFoeUses.getVal(_attaque).increment();
    }

    void supprimerEffetEquipeEntreeAdv(String _attaque){
        enabledMovesWhileSendingFoe.put(_attaque,false);
        enabledMovesWhileSendingFoeUses.put(_attaque,LgInt.zero());
    }

    void addSuccessfulMoveRound(String _attaque){
        successfulMovesRound.add(_attaque);
        for(StringList c:enabledMovesByGroup.getKeys()){
            if(!successfulMovesRound.containsAllObj(c)){
                continue;
            }
            enabledMovesByGroup.getVal(c).enableReset();
        }
    }

    void clearSuccessfuleMovesRound(){
        successfulMovesRound.clear();
        for(String e:nbUsesMovesRound.getKeys()){
            nbUsesMovesRound.put(e, 0);
        }
        for (Fighter f: members.values()) {
            f.setSuccessfulMove(false);
        }
    }

    void ajouterCombattantsContreAdv(byte _membre,byte _adv){
        playerFightersAgainstFoe.getVal(_membre).add(_adv);
        playerFightersAgainstFoe.getVal(_membre).removeDuplicates();
    }

    void toutSupprimerCombattantsContreAdvMembre(byte _membre){
        playerFightersAgainstFoe.getVal(_membre).clear();
    }

    void toutSupprimerCombattantsContreAdv(byte _adv){
        for(byte c:playerFightersAgainstFoe.getKeys()){
            playerFightersAgainstFoe.getVal(c).removeOneNumber(_adv);
        }
    }

    Bytes notKoPartnersWithoutStatus(byte _position) {
        Bytes list_ = new Bytes();
        for (byte c: members.getKeys()) {
            if (NumberUtil.eq(c, _position)) {
                continue;
            }
            Fighter fighter_ = members.getVal(c);
            if (fighter_.estKo()) {
                continue;
            }
            boolean ok_=true;
            for(String c2_:fighter_.getStatusSet()){
                if(!NumberUtil.eq(fighter_.getStatusNbRoundShort(c2_), 0)){
                    ok_=false;
                    break;
                }
            }
            if(!ok_){
                continue;
            }
            list_.add(c);
        }
        return list_;
    }

    void useItemsEndRound(DataBase _import) {
        boolean cancelUsingItems_ = false;
        for (byte f_: members.getKeys()) {
            Fighter fighter_= refPartMembres(f_);
            if (fighter_.estArriere()) {
                continue;
            }
            if (!fighter_.capaciteActive()) {
                continue;
            }
            AbilityData ab_ = fighter_.ficheCapaciteActuelle(_import);
            if (ab_.isGiveItemToAllyHavingUsed()) {
                cancelUsingItems_ = true;
                break;
            }
        }
        StringMap<String> mess_ = _import.getMessagesTeam();
        for(byte c2_: getMembers().getKeys()){
            Fighter creature_= refPartMembres(c2_);
            creature_.setLastUsedMove();
            creature_.cancelActions();
            String name_ = _import.translatePokemon(creature_.getName());
            if (!cancelUsingItems_) {
                if (creature_.isUsingItem()) {
                    comment.addMessage(mess_.getVal(USE_ITEM), name_);
                }
                creature_.tossLastUsedObject();
            } else {
                comment.addMessage(mess_.getVal(CANCEL_USE_ITEM), name_);
            }
            creature_.setUsingItem(false);
        }
    }

    void setNbKoRound(){
        nbKoPreviousRound=nbKoRound;
        nbKoRound=0;
    }

    void chooseMoves(){
        for(byte c:members.getKeys()){
            members.getVal(c).choisirAttaqueFin();
        }
    }

    Bytes fightersAtCurrentPlace(short _place){
        Bytes cbts_ = new Bytes();
        for(byte c:members.getKeys()){
            Fighter membre_=members.getVal(c);
            if(NumberUtil.eq(membre_.getGroundPlace(),_place)){
                cbts_.add(c);
            }
        }
        return cbts_;
    }

    Bytes fightersAtCurrentPlaceIndex(short _index, boolean _belongToPlayer){
        ByteTreeMap<Byte> cbts_ = new ByteTreeMap<Byte>();
        for(byte c:members.getKeys()){
            Fighter membre_=members.getVal(c);
            if (membre_.estArriere()) {
                continue;
            }
            if (_belongToPlayer) {
                if (!membre_.isBelongingToPlayer()) {
                    continue;
                }
            }
            cbts_.put(membre_.getGroundPlace(), c);
        }
        Bytes res_ = new Bytes();
        if (cbts_.getKeys().isValidIndex(_index)) {
            res_.add(cbts_.getValue(_index));
        }
        return res_;
    }

    byte substituteAtIndex(short _index) {
        byte substitute_ = Fighter.BACK;
        byte i_ = IndexConstants.FIRST_INDEX;
        Bytes list_ = new Bytes(members.getKeys());
        list_.sort();
        for (byte k: list_) {
            Fighter fighter_ = members.getVal(k);
            if (!fighter_.estArriere()) {
                continue;
            }
            if (NumberUtil.eq(i_, _index)) {
                substitute_ = k;
                break;
            }
            i_++;
        }
        return substitute_;
    }

    int indexOfSubstitute(byte _sub) {
        Bytes list_ = new Bytes(members.getKeys());
        list_.sort();
        int ind_ = IndexConstants.INDEX_NOT_FOUND_ELT;
        int i_ = IndexConstants.FIRST_INDEX;
        for (byte b: list_) {
            Fighter f_ = members.getVal(b);
            if (!f_.estArriere()) {
                continue;
            }
            if (NumberUtil.eq(b, _sub)) {
                ind_ = i_;
                break;
            }
            i_++;
        }
        return ind_;
    }

    byte fighterAtIndex(short _index) {
        byte index_ = Fighter.BACK;
        byte i_ = IndexConstants.FIRST_INDEX;
        Bytes list_ = new Bytes(members.getKeys());
        list_.sort();
        for (byte k: list_) {
            if (!members.getVal(k).isBelongingToPlayer()) {
                continue;
            }
            if (NumberUtil.eq(i_, _index)) {
                index_ = k;
                break;
            }
            i_++;
        }
        return index_;
    }

    boolean estKo(){
        for(byte c:members.getKeys()){
            if(!members.getVal(c).estKo()){
                return false;
            }
        }
        return true;
    }

    StringList enabledTeamMoves() {
        StringList list_ = new StringList();
        for(String m:enabledMoves.getKeys()){
            if(!enabledMoves.getVal(m).isEnabled()){
                continue;
            }
            list_.add(m);
        }
        return list_;
    }

    CustList<StringList> enabledTeamGroupMoves() {
        CustList<StringList> list_ = new CustList<StringList>();
        for(StringList m:enabledMovesByGroup.getKeys()){
            if(!enabledMovesByGroup.getVal(m).isEnabled()){
                continue;
            }
            list_.add(m);
        }
        return list_;
    }

    Bytes frontFighters(){
        Bytes cbts_ = new Bytes();
        for(byte c2_: members.getKeys()){
            if(!members.getVal(c2_).estArriere()){
                cbts_.add(c2_);
            }
        }
        return cbts_;
    }

    public Fighter refPartMembres(byte _position) {
        return members.getVal(_position);
    }

    public Comment getComment() {
        return comment;
    }

    void setComment(Comment _comment) {
        comment = _comment;
    }

    public ListActivityOfMoves getEnabledMovesByGroup() {
        return enabledMovesByGroup;
    }

    public void setEnabledMovesByGroup(ListActivityOfMoves _enabledMoves) {
        enabledMovesByGroup = _enabledMoves;
    }

    public StringMap<ActivityOfMove> getEnabledMoves() {
        return enabledMoves;
    }

    public void setEnabledMoves(StringMap<ActivityOfMove> _enabledMoves) {
        enabledMoves = _enabledMoves;
    }

    public StringMap<Boolean> getEnabledMovesWhileSendingFoe() {
        return enabledMovesWhileSendingFoe;
    }

    public void setEnabledMovesWhileSendingFoe(StringMap<Boolean> _enabledMovesWhileSendingFoe) {
        enabledMovesWhileSendingFoe = _enabledMovesWhileSendingFoe;
    }

    public StringMap<LgInt> getEnabledMovesWhileSendingFoeUses() {
        return enabledMovesWhileSendingFoeUses;
    }

    public void setEnabledMovesWhileSendingFoeUses(StringMap<LgInt> _enabledMovesWhileSendingFoeUses) {
        enabledMovesWhileSendingFoeUses = _enabledMovesWhileSendingFoeUses;
    }

    public StringMap<Integer> getNbUsesMoves() {
        return nbUsesMoves;
    }

    public void setNbUsesMoves(StringMap<Integer> _nbUsesMoves) {
        nbUsesMoves = _nbUsesMoves;
    }

    public StringMap<Integer> getNbUsesMovesRound() {
        return nbUsesMovesRound;
    }

    public void setNbUsesMovesRound(StringMap<Integer> _nbUsesMovesRound) {
        nbUsesMovesRound = _nbUsesMovesRound;
    }

    public CustList<String> getHealAfterSet() {
        return healAfter.getKeys();
    }

    public CustList<Byte> getHealAfterSet(String _move) {
        return healAfter.getVal(_move).getKeys();
    }

    public StacksOfUses getHealAfterVal(String _move, byte _key) {
        return healAfter.getVal(_move).getVal(_key);
    }

    public StringMap<ByteMap<StacksOfUses>> getHealAfter() {
        return healAfter;
    }

    public void setHealAfter(StringMap<ByteMap<StacksOfUses>> _healAfter) {
        healAfter = _healAfter;
    }

    public CustList<String> getMovesAnticipationSet() {
        return movesAnticipation.getKeys();
    }

    public CustList<Byte> getMovesAnticipationSet(String _move) {
        return movesAnticipation.getVal(_move).getKeys();
    }

    public Anticipation getMovesAnticipationVal(String _move, byte _key) {
        return movesAnticipation.getVal(_move).getVal(_key);
    }

    /**Only for checking player actions*/
    CustList<ByteMap<Anticipation>> getMovesAnticipationValues() {
        return movesAnticipation.values();
    }

    public StringMap<ByteMap<Anticipation>> getMovesAnticipation() {
        return movesAnticipation;
    }

    public void setMovesAnticipation(StringMap<ByteMap<Anticipation>> _movesAnticipation) {
        movesAnticipation = _movesAnticipation;
    }

    public ByteMap<Fighter> getMembers() {
        return members;
    }

    public void setMembers(ByteMap<Fighter> _members) {
        members = _members;
    }

    public CustList<Byte> getPlayerFightersAgainstFoeSet() {
        return playerFightersAgainstFoe.getKeys();
    }

    public Bytes getPlayerFightersAgainstFoeVal(byte _key) {
        return playerFightersAgainstFoe.getVal(_key);
    }

    public boolean playerFightersAgainstFoeHas(byte _key, byte _value) {
        return playerFightersAgainstFoe.getVal(_key).containsObj(_value);
    }

    public ByteMap<Bytes> getPlayerFightersAgainstFoe() {
        return playerFightersAgainstFoe;
    }

    public void setPlayerFightersAgainstFoe(ByteMap<Bytes> _playerFightersAgainstFoe) {
        playerFightersAgainstFoe = _playerFightersAgainstFoe;
    }

    public byte getNbKoRound() {
        return nbKoRound;
    }

    public void setNbKoRound(byte _nbKoRound) {
        nbKoRound = _nbKoRound;
    }

    public byte getNbKoPreviousRound() {
        return nbKoPreviousRound;
    }

    public void setNbKoPreviousRound(byte _nbKoPreviousRound) {
        nbKoPreviousRound = _nbKoPreviousRound;
    }

    public StringList getSuccessfulMovesRound() {
        return successfulMovesRound;
    }

    public void setSuccessfulMovesRound(StringList _successfulMovesRound) {
        successfulMovesRound = _successfulMovesRound;
    }
}
