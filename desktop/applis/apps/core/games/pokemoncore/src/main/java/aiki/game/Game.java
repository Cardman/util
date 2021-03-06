package aiki.game;
import aiki.comments.Comment;
import aiki.db.DataBase;
import aiki.db.ImageHeroKey;
import aiki.fight.pokemon.PokemonData;
import aiki.fight.pokemon.enums.GenderRepartition;
import aiki.game.enums.InterfaceType;
import aiki.game.fight.BallNumberRate;
import aiki.game.fight.ChoiceOfEvolutionAndMoves;
import aiki.game.fight.Fight;
import aiki.game.fight.FightFacade;
import aiki.game.fight.Fighter;
import aiki.game.fight.Team;
import aiki.game.fight.TeamPosition;
import aiki.game.fight.actions.ActionMove;
import aiki.game.fight.actions.ActionSwitch;
import aiki.game.fight.enums.ActionType;
import aiki.game.fight.enums.FightState;
import aiki.game.fight.enums.FightType;
import aiki.game.params.Difficulty;
import aiki.game.player.Player;
import aiki.game.player.enums.Sex;
import aiki.map.Condition;
import aiki.map.DataMap;
import aiki.map.buildings.Building;
import aiki.map.buildings.Gym;
import aiki.map.buildings.PokemonCenter;
import aiki.map.characters.CharacterInRoadCave;
import aiki.map.characters.DealerItem;
import aiki.map.characters.DualFight;
import aiki.map.characters.GerantPokemon;
import aiki.map.characters.GymLeader;
import aiki.map.characters.GymTrainer;
import aiki.map.characters.Person;
import aiki.map.characters.Seller;
import aiki.map.characters.TempTrainer;
import aiki.map.characters.Trainer;
import aiki.map.characters.TrainerLeague;
import aiki.map.characters.TrainerMultiFights;
import aiki.map.characters.enums.GeranceType;
import aiki.map.characters.enums.SellType;
import aiki.map.enums.Direction;
import aiki.map.levels.AreaApparition;
import aiki.map.levels.Block;
import aiki.map.levels.Level;
import aiki.map.levels.LevelCave;
import aiki.map.levels.LevelIndoorGym;
import aiki.map.levels.LevelIndoorPokemonCenter;
import aiki.map.levels.LevelLeague;
import aiki.map.levels.LevelWithWildPokemon;
import aiki.map.levels.Link;
import aiki.map.levels.enums.EnvironmentType;
import aiki.map.places.Campaign;
import aiki.map.places.Cave;
import aiki.map.places.City;
import aiki.map.places.InitializedPlace;
import aiki.map.places.League;
import aiki.map.places.Place;
import aiki.map.pokemon.Egg;
import aiki.map.pokemon.PokemonPlayer;
import aiki.map.pokemon.UsablePokemon;
import aiki.map.pokemon.WildPk;
import aiki.map.pokemon.enums.Gender;
import aiki.map.util.ScreenCoords;
import aiki.util.*;
import code.maths.LgInt;
import code.maths.Rate;
import code.maths.montecarlo.EventFreq;
import code.maths.montecarlo.MonteCarloList;
import code.maths.montecarlo.MonteCarloString;
import code.util.*;
import code.util.core.IndexConstants;
import code.util.core.NumberUtil;
import code.util.core.StringUtil;


public final class Game {

    public static final String GAME = "aiki.game.game";

    public static final String END_GAME = "endGame";

    private static final int NB_HOSTED_POKEMON = 2;

    private static final String NO_BEATEN_TRAINER = "noBeatenTrainer";

    private static final String BEATEN_TRAINER_FULL = "beatenTrainerFull";

    private static final String BEATEN_TRAINER = "beatenTrainer";

    private static final String POSSIBLE_BEAT_LEADER = "possibleBeatLeader";

    private static final String REMAINING_TRAINERS_GYM = "remainingTrainersGym";

    private static final String MISSING_PK = "missingPk";

    private static final String SAME_PK = "samePk";

    private static final String SAME_GENDER = "sameGender";

    private static final String NO_COMMON_EGG = "noCommonEgg";

    private static final String RECEIVED_EGG = "receivedEgg";

    private static final String NOT_ENOUGH_PLACE = "notEnoughPlace";

    private static final String RECEIVED_EGG_PARENTS = "receivedEggParents";

    private static final String RECEIVED_EGG_WITHOUT_PARENT = "receivedEggWithoutParent";

    private static final String RECEIVED_PARENTS = "receivedParents";

    private static final String NOT_ENOUGH_PLACE_PARENTS = "notEnoughPlaceParents";

    private static final String CAUGHT_PK = "caughtPk";

    private static final String NOT_CAUGHT_PK = "notCaughtPk";

    private static final String WON_MONEY = "wonMoney";

    private static final String LOST_MONEY = "lostMoney";

    private static final String SEPARATOR_TRAINERS = " ";

    private String zippedRom;

    /**contient les elements modifies par l'player*/
    private Player player;

    /**nombre de dresseurs de la ligue battus (remis a zero une fois la ligue battue)*/
    private byte rankLeague;

    private ShortMap<EqList<Point>> beatGymTrainer;

    private ObjectMap<Coords,Boolean> beatGymLeader;

    /***/
    private ObjectMap<NbFightCoords,Boolean> beatTrainer;

    /***/
    private ObjectMap<Coords,Boolean> takenObjects;

    /***/
    private ObjectMap<Coords,Boolean> takenPokemon;

    /***/
    private Coords playerCoords;

    /**(0,1);(1,0);(0,-1);(-1,0)*/
    private Direction playerOrientation;

    /***/
    private ObjectMap<Coords,HostPokemonDuo> hostedPk;

    /***/
    private Fight fight;

    /***/
    private Difficulty difficulty;

    private int indexPeriodFishing;

    private int indexPeriod;

    private int indexStep;

    //private String comment;

    private Comment commentGame = new Comment();

    private boolean reinitInteraction;

    private ObjectMap<Coords,Boolean> visitedPlaces;
    private ShortMap<Boolean> visitedPlacesNb;

    private int nbSteps;

    private boolean placeChanged;

    private InterfaceType interfaceType;

    private boolean showEndGame;

    private StringList partiallyAccessiblePlaces = new StringList();

    private StringList fullAccessiblePlaces = new StringList();

    public Game(){
        difficulty = new Difficulty();
        setBeatGymTrainer(new ShortMap<EqList<Point>>());
        reinitInteraction=false;
        setInterfaceType(InterfaceType.RIEN);
        setVisitedPlacesNb(new ShortMap<Boolean>());
        setVisitedPlaces(new ObjectMap<Coords,Boolean>());
        setBeatGymLeader(new ObjectMap<Coords, Boolean>());
        setTakenObjects(new ObjectMap<Coords, Boolean>());
        setTakenPokemon(new ObjectMap<Coords, Boolean>());
        setBeatTrainer(new ObjectMap<NbFightCoords, Boolean>());
        setHostedPk(new ObjectMap<Coords, HostPokemonDuo>());
        setFight(new Fight());
        playerCoords = new Coords();
    }

    public Game(DataBase _d){
        fight = FightFacade.newFight();
        beatGymTrainer = new ShortMap<EqList<Point>>();
        difficulty = new Difficulty();
        reinitInteraction=false;
        interfaceType=InterfaceType.RIEN;
        DataMap d_=_d.getMap();
        visitedPlaces = new ObjectMap<Coords,Boolean>();
        visitedPlacesNb = new ShortMap<Boolean>();
        playerCoords= new Coords(d_.getBegin());
        playerOrientation=Direction.UP;
        takenPokemon = new ObjectMap<Coords,Boolean>();
        for (Coords c: d_.getTakenPokemon()){
            takenPokemon.put(c, false);
        }
        takenObjects = new ObjectMap<Coords,Boolean>();
        for (Coords c: d_.getTakenObjects()){
            takenObjects.put(c, false);
        }
        beatGymLeader = new ObjectMap<Coords,Boolean>();
        for (Coords c: d_.getBeatGymLeader()){
            beatGymLeader.put(c, false);
        }
        beatTrainer = new ObjectMap<NbFightCoords,Boolean>();
        for (NbFightCoords c: d_.getBeatTrainer()) {
            beatTrainer.put(c, false);
        }
        beatGymTrainer = new ShortMap<EqList<Point>>();
        for (Short c: d_.getBeatGymTrainer().getKeys()){
            beatGymTrainer.put(c, new EqList<Point>());
        }
        hostedPk = new ObjectMap<Coords,HostPokemonDuo>();
        for (Coords c: d_.getHostPokemons()){
            HostPokemonDuo h_ = new HostPokemonDuo();
            h_.setFirstPokemon(new PokemonPlayer());
            h_.setSecondPokemon(new PokemonPlayer());
            hostedPk.put(c, h_);
        }
        visitedPlaces = new ObjectMap<Coords,Boolean>();
        visitedPlacesNb = new ShortMap<Boolean>();
        for (Coords c: d_.getCities()) {
            visitedPlaces.put(c, false);
            visitedPlacesNb.put(c.getNumberPlace(), false);
        }
        indexStep=0;
        indexPeriod=0;
        indexPeriodFishing = 0;
        rankLeague=0;
    }

    public void visitFirstPlaces(DataBase _d) {
        DataMap d_=_d.getMap();
        visitedPlacesNb.clear();
        for (EntryCust<Coords,Boolean> e: visitedPlaces.entryList()) {
            visitedPlacesNb.put(e.getKey().getNumberPlace(),e.getValue());
        }
        for (Coords c: visitedPlaces.getKeys()) {
            if (d_.getAccessibility().getVal(c).isEmpty()) {
                visitedPlaces.put(c, true);
                visitedPlacesNb.put(c.getNumberPlace(), true);
            }
        }
    }

    public void initUserInteract(String _pseudo,Sex _sexeHeros,Difficulty _diff,DataBase _import){
        initUtilisateur(_pseudo, _sexeHeros, _diff, _import);
        visitFirstPlaces(_import);
        DataMap d_ = _import.getMap();
        directInteraction(d_);
    }

    public void initUtilisateur(String _pseudo,Sex _sexeHeros,Difficulty _diff,DataBase _import){
        player=new Player(_pseudo,_sexeHeros,_diff,true,_import);
    }

    public void initUtilisateurSimulation(String _pseudo,Sex _sexeHeros,Difficulty _diff,DataBase _import){
        player=new Player(_pseudo,_sexeHeros,_diff,false,_import);
    }

    public boolean validate(DataBase _data) {
        visitFirstPlaces(_data);
        if (!player.validate(_data)) {
            return false;
        }
        boolean existNonKoNonEggPok_ = false;
        for (UsablePokemon u: player.getTeam()) {
            if (!(u instanceof PokemonPlayer)) {
                continue;
            }
            PokemonPlayer pk_ = (PokemonPlayer) u;
            if (pk_.isKo()) {
                continue;
            }
            existNonKoNonEggPok_ = true;
            break;
        }
        if (!existNonKoNonEggPok_) {
            return false;
        }
        DataMap map_ = _data.getMap();
        if (!Coords.equalsSet(map_.getCities(), visitedPlaces.getKeys())) {
            return false;
        }
        EqList<Coords> accessCond_ = new EqList<Coords>();
        for (CustList<Coords> l: map_.getAccessCondition().values()) {
            accessCond_.addAllElts(l);
        }
//        for (Coords c: visitedPlaces.getKeys(true))
        for (Coords c: getVisited()) {
            Condition cond_ = new Condition();
            for (EntryCust<Coords,Condition> e: map_.getAccessibility().entryList()) {
                if (!Coords.eq(e.getKey(),c)) {
                    continue;
                }
                cond_.addAllElts(e.getValue());
            }
//            cond_.addAllElts(map_.getAccessibility().getVal(c));
            cond_.retainAllElements(accessCond_);
//            if (!beatGymLeader.getKeys(true).containsAllObj(cond_)) {
//                return false;
//            }
            if (!getBeatenGymLeader().containsAllObj(cond_)) {
                return false;
            }
        }
        if (!NumberUtil.equalsSetShorts(beatGymTrainer.getKeys(), map_.getBeatGymTrainer().getKeys())) {
            return false;
        }
        for (Short k: beatGymTrainer.getKeys()) {
            if (!map_.getBeatGymTrainer().getVal(k).containsAllObj(beatGymTrainer.getVal(k))) {
                return false;
            }
        }
        if (!NbFightCoords.equalsSet(beatTrainer.getKeys(), map_.getBeatTrainer())) {
            return false;
        }
        if (!Coords.equalsSet(beatGymLeader.getKeys(), map_.getBeatGymLeader())) {
            return false;
        }
//        for (Coords c: beatGymLeader.getKeys(true))
        for (Coords c: getBeatenGymLeader()) {
            Condition cond_ = new Condition();
            Coords coords_ = new Coords(c);
            if (coords_.isInside()) {
                Point ext_ = new Point(coords_.getInsideBuilding());
                ext_.moveTo(Direction.DOWN);
                //not on the door of the building
                coords_.getLevel().setPoint(ext_);
                coords_.outside();
            }
            for (EntryCust<Coords,Condition> e: map_.getAccessibility().entryList()) {
                if (!Coords.eq(e.getKey(),coords_)) {
                    continue;
                }
                cond_.addAllElts(e.getValue());
            }
            cond_.retainAllElements(accessCond_);
//            if (!beatGymLeader.getKeys(true).containsAllObj(cond_)) {
//                return false;
//            }
            if (!getBeatenGymLeader().containsAllObj(cond_)) {
                return false;
            }
        }
//        for (Coords c: beatGymLeader.getKeys(true))
        for (Coords c: getBeatenGymLeader()) {
            if (beatGymTrainer.contains(c.getNumberPlace())) {
                beatGymTrainer.getVal(c.getNumberPlace()).clear();
                beatGymTrainer.getVal(c.getNumberPlace()).addAllElts(map_.getBeatGymTrainer().getVal(c.getNumberPlace()));
            }
        }
        if (!Coords.equalsSet(takenObjects.getKeys(), map_.getTakenObjects())) {
            return false;
        }
        if (!Coords.equalsSet(takenPokemon.getKeys(), map_.getTakenPokemon())) {
            return false;
        }
        boolean correctCoords_ = true;
        short numberPlace_ = playerCoords.getNumberPlace();
        Place curPlace_ = null;
        if (_data.getMap().getPlaces().isValidIndex(numberPlace_)) {
            curPlace_ = _data.getMap().getPlace(numberPlace_);
        }
        if (curPlace_ == null) {
            correctCoords_ = false;
        } else {
            if (playerCoords.isInside()) {
                if (curPlace_ instanceof City) {
                    Point bIncome_ = playerCoords.getInsideBuilding();
                    if (!((City)curPlace_).getBuildings().contains(bIncome_)) {
                        correctCoords_ = false;
                    } else {
                        Level lev_ = ((City) curPlace_).getBuildings().getVal(bIncome_).getLevel();
                        correctCoords_ = checkLevel(lev_);
                    }
                } else {
                    correctCoords_ = false;
                }
            } else {
                if (!curPlace_.getLevelsList().isValidIndex(playerCoords.getLevel().getLevelIndex())) {
                    correctCoords_ = false;
                } else {
                    Level curLevel_ = curPlace_.getLevelsList().get(playerCoords.getLevel().getLevelIndex());
                    correctCoords_ = checkLevel(curLevel_);
                }
            }
        }
        Coords coords_ = new Coords(playerCoords);
        if (correctCoords_) {
            if (!isEmpty(_data.getMap(), playerCoords)) {
                playerCoords.affect(map_.getBegin());
                rankLeague = 0;
            }
        } else {
            playerCoords.affect(map_.getBegin());
            rankLeague = 0;
        }
        if (coords_.isInside()) {
            Point ext_ = new Point(coords_.getInsideBuilding());
            ext_.moveTo(Direction.DOWN);
            //not on the door of the building
            coords_.getLevel().setPoint(ext_);
            coords_.outside();
        }
        if (!map_.getAccessibility().contains(coords_)) {
            correctCoords_ = false;
        }
        Condition cond_ = new Condition();
        if (correctCoords_) {
            for (EntryCust<Coords,Condition> e: map_.getAccessibility().entryList()) {
                if (!Coords.eq(e.getKey(),coords_)) {
                    continue;
                }
                cond_.addAllElts(e.getValue());
            }
        } else {
//            if (fight.getFightType().isExisting()) {
//                return false;
//            }
            playerCoords.affect(map_.getBegin());
            rankLeague = 0;
        }
        cond_.retainAllElements(accessCond_);
//        if (!beatGymLeader.getKeys(true).containsAllObj(cond_))
        if (!getBeatenGymLeader().containsAllObj(cond_)) {
//            if (fight.getFightType().isExisting()) {
//                return false;
//            }
            playerCoords.affect(map_.getBegin());
            rankLeague = 0;
        }
        difficulty.validate(_data);
        if (!FightFacade.validate(fight, _data, player, difficulty)) {
            return false;
        }
        if (!fight.getFightType().isWild()) {
            if (!isFrontOfTrainer(_data)) {
                playerCoords.affect(map_.getBegin());
                rankLeague = 0;
//                return false;
            }
        }
        if (!Coords.equalsSet(hostedPk.getKeys(), map_.getHostPokemons())) {
            return false;
        }
        for (Coords c: hostedPk.getKeys()) {
            if (!hostedPk.getVal(c).validate(_data)) {
                return false;
            }
            if (availableHosting(c)) {
                continue;
            }
            coords_ = new Coords(c);
            Point ext_ = new Point(coords_.getInsideBuilding());
            ext_.moveTo(Direction.DOWN);
            //not on the door of the building
            coords_.getLevel().setPoint(ext_);
            coords_.outside();
//            if (coords_.isInside()) {
//                Point ext_ = new Point(coords_.getInsideBuilding());
//                ext_.moveTo(Direction.DOWN);//not on the door of the building
//                coords_.getLevel().setPoint(ext_);
//                coords_.outside();
//            }
            cond_ = new Condition();
            for (EntryCust<Coords,Condition> e: map_.getAccessibility().entryList()) {
                if (!Coords.eq(e.getKey(),coords_)) {
                    continue;
                }
                cond_.addAllElts(e.getValue());
            }
            cond_.retainAllElements(accessCond_);
//            if (!beatGymLeader.getKeys(true).containsAllObj(cond_)) {
//                return false;
//            }
            if (!getBeatenGymLeader().containsAllObj(cond_)) {
                return false;
            }
        }
        if (indexStep < 0) {
            return false;
        }
        if (indexPeriod < 0) {
            return false;
        }
        if (indexPeriodFishing < 0) {
            return false;
        }
        Place currentPlace_ = map_.getPlace(playerCoords.getNumberPlace());
        if (currentPlace_ instanceof League) {
            if (rankLeague < playerCoords.getLevel().getLevelIndex()) {
                return false;
            }
            return rankLeague <= playerCoords.getLevel().getLevelIndex() + 1;
        } else {
            return rankLeague == 0;
        }
    }

    private boolean checkLevel(Level _l) {
        boolean correctCoords_ = true;
        if (!_l.getEnvBlockByPoint(playerCoords.getLevel().getPoint()).isValid()) {
            correctCoords_ = false;
        }
        return correctCoords_;
    }
    public void calculateImagesFromTiles(DataBase _data, int _dx, int _dy) {
        DataMap map_ = _data.getMap();
        for (ScreenCoords k: map_.getTiles().getKeys()) {
            Coords coords_ = map_.getTiles().getVal(k);
            if (!coords_.isValid()) {
                map_.getForegroundImages().put(k, new CustList<int[][]>());
                continue;
            }
            Place place_ = map_.getPlace(coords_.getNumberPlace());
            Level level_ = place_.getLevelByCoords(coords_);
            CustList<int[][]> images_ = new CustList<int[][]>();
            Point pt_ = coords_.getLevel().getPoint();
            for (Place p: map_.getPlaces()) {
                if (!(p instanceof League)) {
                    continue;
                }
                Coords access_ = ((League)p).getAccessCoords();
                if (Coords.eq(coords_, access_)) {
                    images_.add(_data.getLink(((League)p).getFileName()));
                    break;
                }
            }
            if (level_ instanceof LevelLeague) {
                LevelLeague lv_ = (LevelLeague) level_;
                if (Point.eq(pt_, lv_.getTrainerCoords())) {
                    Person person_ = lv_.getTrainer();
                    images_.add(_data.getPerson(person_.getImageMiniFileName()));
                }
                if (Point.eq(pt_, lv_.getAccessPoint())) {
                    images_.add(_data.getLink(lv_.getFileName()));
                }
            }
            if (level_ instanceof LevelWithWildPokemon) {
                LevelWithWildPokemon lv_ = (LevelWithWildPokemon) level_;
                if (lv_.getCharacters().contains(pt_)) {
                    Person person_ = lv_.getPerson(pt_);
                    images_.add(_data.getPerson(person_.getImageMiniFileName()));
                }
                if (lv_.containsPokemon(pt_) && !isEmpty(map_, coords_)) {
                    WildPk pk_ = lv_.getPokemon(pt_);
                    images_.add(_data.getMiniPk().getVal(pk_.getName()));
                }
                if (lv_.getItems().contains(pt_) && !isEmpty(map_, coords_)) {
                    images_.add(_data.getMiniItems().getVal(lv_.getItems().getVal(pt_)));
                }
                if (lv_.getTm().contains(pt_) && !isEmpty(map_, coords_)) {
                    images_.add(_data.getImageTmHm());
                }
                if (lv_.getHm().contains(pt_) && !isEmpty(map_, coords_)) {
                    images_.add(_data.getImageTmHm());
                }
                if (lv_.getDualFights().contains(pt_) && !isEmpty(map_, coords_)) {
                    DualFight dual_ = lv_.getDualFights().getVal(pt_);
                    TempTrainer tmp_ = dual_.getFoeTrainer();
                    images_.add(_data.getPerson(tmp_.getImageMiniFileName()));
                }
                for (DualFight d: lv_.getDualFights().values()) {
                    if (!Point.eq(d.getPt(), pt_)) {
                        continue;
                    }
                    if (!isEmpty(map_, coords_)) {
                        TempTrainer tmp_ = d.getFoeTrainer();
                        images_.add(_data.getPerson(tmp_.getImageMiniSecondTrainerFileName()));
                    }
                    break;
                }
            }
            if (coords_.isInside()) {
                City city_ = (City) place_;
                Building building_ = city_.getBuildings().getVal(coords_.getInsideBuilding());
                if (Point.eq(building_.getExitCity(), pt_)) {
                    images_.add(_data.getLink(building_.getImageFileName()));
                }
            }
            if (level_ instanceof LevelIndoorPokemonCenter) {
                LevelIndoorPokemonCenter lv_ = (LevelIndoorPokemonCenter) level_;
                if (lv_.getGerants().contains(pt_)) {
                    Person person_ = lv_.getGerants().getVal(pt_);
                    images_.add(_data.getPerson(person_.getImageMiniFileName()));
                }
                if (Point.eq(lv_.getStorageCoords(), pt_)) {
                    images_.add(_data.getStorage());
                }
            }
            if (level_ instanceof LevelIndoorGym) {
                LevelIndoorGym lv_ = (LevelIndoorGym) level_;
                if (lv_.getGymTrainers().contains(pt_)) {
                    Person person_ = lv_.getGymTrainers().getVal(pt_);
                    images_.add(_data.getPerson(person_.getImageMiniFileName()));
                }
                if (Point.eq(pt_, lv_.getGymLeaderCoords())) {
                    Person person_ = lv_.getGymLeader();
                    images_.add(_data.getPerson(person_.getImageMiniFileName()));
                }
            }
            if (place_ instanceof Cave) {
                Cave cave_ = (Cave) place_;
                LevelPoint lPoint_ = coords_.getLevel();
                if (cave_.getLinksWithOtherPlaces().contains(lPoint_)) {
                    Link link_ = cave_.getLinksWithOtherPlaces().getVal(lPoint_);
                    images_.add(_data.getLink(link_.getFileName()));
                }
                LevelCave lv_ = (LevelCave) level_;
                if (lv_.getLinksOtherLevels().contains(pt_)) {
                    Link link_ = lv_.getLinksOtherLevels().getVal(pt_);
                    images_.add(_data.getLink(link_.getFileName()));
                }
            }
            if (place_ instanceof InitializedPlace) {
                InitializedPlace init_ = (InitializedPlace) place_;
                if (init_.getLinksWithCaves().contains(pt_)) {
                    Link link_ = init_.getLinksWithCaves().getVal(pt_);
                    images_.add(_data.getLink(link_.getFileName()));
                }
            }
            map_.getForegroundImages().put(k, images_);
        }
        ScreenCoords center_;
        center_ = new ScreenCoords(map_.getSpaceBetweenLeftAndHeros()+_dx,map_.getSpaceBetweenTopAndHeros()+_dy);
        CustList<int[][]> images_ = map_.getForegroundImages().getVal(center_);
        images_.add(getMiniHeros(_data));
    }

    public int[][] getMiniHeros(DataBase _data) {
        DataMap map_ = _data.getMap();
        EnvironmentType currentEnv_ = map_.currentBlock(playerCoords).getType();
        Sex sex_ = player.getSex();
        ImageHeroKey key_;
        key_ = new ImageHeroKey(currentEnv_, playerOrientation, sex_);
        if (_data.getOverWorldHeros().contains(key_)) {
            return _data.getOverWorldHeros().getVal(key_);
        }
        key_ = new ImageHeroKey(EnvironmentType.ROAD, playerOrientation, sex_);
        return _data.getOverWorldHeros().getVal(key_);
    }

    public int[][] getBackHeros(DataBase _data) {
        ImageHeroKey key_;
        EnvironmentType currentEnv_ = _data.getMap().currentBlock(playerCoords).getType();
        Sex sex_ = player.getSex();
        key_ = new ImageHeroKey(currentEnv_, sex_);
        if (_data.getBackHeros().contains(key_)) {
            return _data.getBackHeros().getVal(key_);
        }
        key_ = new ImageHeroKey(EnvironmentType.ROAD, sex_);
        return _data.getBackHeros().getVal(key_);
    }

    public int[][] getBackHerosSexOpposite(DataBase _data) {
        ImageHeroKey key_;
        EnvironmentType currentEnv_ = _data.getMap().currentBlock(playerCoords).getType();
        Sex sex_ = player.getOppositeSex();
        key_ = new ImageHeroKey(currentEnv_, sex_);
        if (_data.getBackHeros().contains(key_)) {
            return _data.getBackHeros().getVal(key_);
        }
        key_ = new ImageHeroKey(EnvironmentType.ROAD, sex_);
        return _data.getBackHeros().getVal(key_);
    }

    public boolean isDualFight() {
        return fight.getFightType() == FightType.TMP_TRAINER;
    }

//    void prendrePokemon(Coords _coords){
//        takenPokemon.put(_coords,true);
//    }

//    boolean elementAbsent(Coords _coords){
//        if(takenPokemon.contains(_coords)){
//            return takenPokemon.getVal(_coords);
//        }
//        if(takenObjects.contains(_coords)){
//            return takenObjects.getVal(_coords);
//        }
//        return false;
//    }

    public boolean availableHosting(Coords _coords){
        return hostedPk.getVal(_coords).isFree();
    }

    public void attemptForStoringPokemonToHost(short _pos1,short _pos2,DataBase _d){
        commentGame.clearMessages();
        DataMap d_=_d.getMap();
        int nb_ = player.getPokemonPlayerList().size();
        reinitInteraction = false;
        if (nb_ > NB_HOSTED_POKEMON) {
            if(canStorePokemonToHost(_pos1,_pos2,_d)){
                Coords voisin_ = closestTile(d_);
                storePokemonToHost(_pos1,_pos2,voisin_);
            } else {
                reinitInteraction = true;
            }
        } else {
            int nbMin_ = NB_HOSTED_POKEMON;
            nbMin_++;
            nbMin_ -= nb_;
            StringMap<String> mess_ = _d.getMessagesGame();
            commentGame.addMessage(mess_.getVal(MISSING_PK), Long.toString(nbMin_));
            reinitInteraction = true;
        }
    }

    boolean canStorePokemonToHost(short _pos1,short _pos2,DataBase _d){
        if(_pos1==_pos2){
            StringMap<String> mess_ = _d.getMessagesGame();
            commentGame.addMessage(mess_.getVal(SAME_PK));
            return false;
        }
        PokemonPlayer pkOne_=(PokemonPlayer) player.getTeam().get(_pos1);
        PokemonPlayer pkTwo_=(PokemonPlayer) player.getTeam().get(_pos2);
        return canStoreThesePokemonToHost(commentGame, pkOne_, pkTwo_, _d);
    }

    static boolean canStoreThesePokemonToHost(Comment _commentGame, PokemonPlayer _pkOne, PokemonPlayer _pkTwo,DataBase _d) {
        PokemonData pkDataOne_=_d.getPokedex().getVal(_pkOne.getName());
        StringList groupsOne_= pkDataOne_.getEggGroups();
        PokemonData pkDataTwo_=_d.getPokedex().getVal(_pkTwo.getName());
        StringList groupsTwo_= pkDataTwo_.getEggGroups();
        StringMap<String> mess_ = _d.getMessagesGame();
        if(Gender.getGendersWithSex().containsObj(_pkOne.getGender())){
            if(Gender.getGendersWithSex().containsObj(_pkTwo.getGender())){
                boolean canStore_ = true;
                if (_pkOne.getGender() == _pkTwo.getGender()) {
                    canStore_ = false;
                    _commentGame.addMessage(mess_.getVal(SAME_GENDER));
                }
                if (StringUtil.contains(groupsOne_, _d.getDefaultEggGroup())) {
                    return canStore_;
                }
                if (StringUtil.contains(groupsTwo_, _d.getDefaultEggGroup())) {
                    return canStore_;
                }
                if (StringUtil.quickEq(pkDataOne_.getBaseEvo(), pkDataTwo_.getBaseEvo())) {
                    return canStore_;
                }
                boolean vide_=true;
                for(String e:groupsOne_){
                    if(StringUtil.contains(groupsTwo_, e)){
                        vide_=false;
                        break;
                    }
                }
                if (vide_) {
                    _commentGame.addMessage(mess_.getVal(NO_COMMON_EGG));
                    return false;
                }
                return canStore_;
            }
            if (StringUtil.contains(groupsTwo_, _d.getDefaultEggGroup())) {
                return true;
            }
            _commentGame.addMessage(mess_.getVal(NO_COMMON_EGG));
            return false;
        }
        if(Gender.getGendersWithSex().containsObj(_pkTwo.getGender())){
            if (StringUtil.contains(groupsOne_, _d.getDefaultEggGroup())) {
                return true;
            }
            _commentGame.addMessage(mess_.getVal(NO_COMMON_EGG));
            return false;
        }
        if (StringUtil.contains(groupsOne_, _d.getDefaultEggGroup())) {
            return true;
        }
        if (StringUtil.contains(groupsTwo_, _d.getDefaultEggGroup())) {
            return true;
        }
        _commentGame.addMessage(mess_.getVal(NO_COMMON_EGG));
        return false;
    }

    void storePokemonToHost(short _pos1,short _pos2,Coords _coords){
        HostPokemonDuo valeur_=hostedPk.getVal(_coords);
        valeur_.setFirstPokemon((PokemonPlayer) player.getTeam().get(_pos1));
        valeur_.setSecondPokemon((PokemonPlayer) player.getTeam().get(_pos2));
        valeur_.setNbSteps(0);
        player.reindexAfterStoringToHost(_pos1,_pos2);
    }

    boolean takablePokemonFromHost(DataBase _import){
        int nb_ = player.getTeam().size();
        return nb_ + NB_HOSTED_POKEMON <= _import.getNbMaxTeam();
    }

    void takePokemonFromHost(Coords _coords){
        HostPokemonDuo valeur_=hostedPk.getVal(_coords);
        player.takeHostedPokemon(valeur_.getFirstPokemon(),valeur_.getSecondPokemon());
        valeur_.setFirstPokemon(new PokemonPlayer());
        valeur_.setSecondPokemon(new PokemonPlayer());
        valeur_.setNbSteps(0);
    }

    void incrementStepsToLayEggs(DataBase _d){
        for(Coords c:hostedPk.getKeys()){
            if (availableHosting(c)) {
                continue;
            }
            HostPokemonDuo valeur_=hostedPk.getVal(c);
            int nbSteps_ = valeur_.getNbSteps();
            if (nbSteps_<_d.getNbMaxSteps()) {
                valeur_.setNbSteps(nbSteps_+1);
            }
        }
    }


    public int nbRemainingSteps(Coords _coords,DataBase _d){
        HostPokemonDuo valeur_=hostedPk.getVal(_coords);
        PokemonData pkDataOne_=_d.getPokedex().getVal(valeur_.getFirstPokemon().getName());
        PokemonData pkDataTwo_=_d.getPokedex().getVal(valeur_.getSecondPokemon().getName());
        if(StringUtil.quickEq(pkDataOne_.getBaseEvo(),pkDataTwo_.getBaseEvo())){
            return _d.getNbMaxStepsSameEvoBase()-valeur_.getNbSteps();
        }
        return _d.getNbMaxSteps()-valeur_.getNbSteps();
    }

    boolean canGetEgg(Coords _coords,DataBase _d){
        if(availableHosting(_coords)){
            return false;
        }
        return nbRemainingSteps(_coords, _d) <= 0;
    }

    Egg productedEgg(Coords _coords,DataBase _d){
        LgInt maxRd_ = _d.getMaxRd();
        return new Egg(lawForProductedEgg(_coords, _d).editNumber(maxRd_,_d.getGenerator()));
    }

    MonteCarloString lawForProductedEgg(Coords _coords,DataBase _d) {
        MonteCarloString law_ = new MonteCarloString();
        HostPokemonDuo valeur_=hostedPk.getVal(_coords);
        PokemonPlayer firstPokemon_ = valeur_.getFirstPokemon();
        PokemonPlayer secondPokemon_ = valeur_.getSecondPokemon();
        boolean oppositeSex_ = false;
        if (Gender.getGendersWithSex().containsObj(firstPokemon_.getGender())) {
            if (Gender.getGendersWithSex().containsObj(secondPokemon_.getGender())) {
                //because firstPokemon_ and secondPokemon_ have a sex
                //and pokemon with same sex cannot be stored to host
                //so firstPokemon_ and secondPokemon_ have an opposite sex.
                //i.e. firstPokemon_.getGender() != secondPokemon_.getGender()
                oppositeSex_ = true;
            }
        }
        if (oppositeSex_) {
            PokemonData fPk_ = _d.getPokemon(firstPokemon_.getName());
            String event_ = fPk_.getBaseEvo();
            law_.addQuickEvent(event_, DataBase.defElementaryEvent());
            fPk_ = _d.getPokemon(secondPokemon_.getName());
            if (!StringUtil.quickEq(fPk_.getBaseEvo(), event_)) {
                law_.addQuickEvent(fPk_.getBaseEvo(), DataBase.defElementaryEvent());
            }
            return law_;
        }
        PokemonData fPk_ = _d.getPokemon(firstPokemon_.getName());
        if (StringUtil.contains(fPk_.getEggGroups(), _d.getDefaultEggGroup())) {
            fPk_ = _d.getPokemon(secondPokemon_.getName());
        }
        law_.addQuickEvent(fPk_.getBaseEvo(), DataBase.defElementaryEvent());
        return law_;
    }

    void takeProductedEgg(Coords _coords){
        hostedPk.getVal(_coords).setNbSteps(0);
    }

    public void receiveOnlyEgg(DataBase _d) {
        commentGame.clearMessages();
        DataMap d_=_d.getMap();
        Coords voisin_= closestTile(d_);
        reinitInteraction=true;
        int total_=player.getTeam().size();
        if(!canGetEgg(voisin_,_d)){
            reinitInteraction = total_ + NB_HOSTED_POKEMON > _d.getNbMaxTeam();
            return;
        }
        StringMap<String> mess_ = _d.getMessagesGame();
        if(total_<_d.getNbMaxTeam()){
            Egg oeuf_=productedEgg(voisin_,_d);
            player.recupererOeufPensions(oeuf_);
            takeProductedEgg(voisin_);
            String name_ = _d.translatePokemon(oeuf_.getName());
            commentGame.addMessage(mess_.getVal(RECEIVED_EGG), name_);
            reinitInteraction=false;
            //interactions->raz_interaction();
        }else{
            commentGame.addMessage(mess_.getVal(NOT_ENOUGH_PLACE));
        }
    }

    public void receiveEggOrParents(DataBase _d) {
        commentGame.clearMessages();
        DataMap d_=_d.getMap();
        Coords voisin_= closestTile(d_);
        reinitInteraction=false;
        StringMap<String> mess_ = _d.getMessagesGame();
        if(canGetEgg(voisin_,_d)){
            int total_=player.getTeam().size();
            if(total_ + NB_HOSTED_POKEMON < _d.getNbMaxTeam()){
                Egg oeuf_=productedEgg(voisin_,_d);
                takePokemonFromHost(voisin_);
                player.recupererOeufPensions(oeuf_);
                takeProductedEgg(voisin_);
                String name_ = _d.translatePokemon(oeuf_.getName());
                commentGame.addMessage(mess_.getVal(RECEIVED_EGG_PARENTS), name_);
            }else if(total_<_d.getNbMaxTeam()){
                Egg oeuf_=productedEgg(voisin_,_d);
                player.recupererOeufPensions(oeuf_);
                takeProductedEgg(voisin_);
                String name_ = _d.translatePokemon(oeuf_.getName());
                commentGame.addMessage(mess_.getVal(RECEIVED_EGG_WITHOUT_PARENT), name_);
                reinitInteraction = true;
                //interactions->raz_interaction();
            }else{
                commentGame.addMessage(mess_.getVal(NOT_ENOUGH_PLACE));
                reinitInteraction = true;
            }
        }else{
            if(takablePokemonFromHost(_d)){
                takePokemonFromHost(voisin_);
                commentGame.addMessage(mess_.getVal(RECEIVED_PARENTS));
                reinitInteraction = false;
            } else {
                commentGame.addMessage(mess_.getVal(NOT_ENOUGH_PLACE_PARENTS));
                reinitInteraction = true;
            }
        }
    }

    public void takeObject(DataBase _d) {
        DataMap d_=_d.getMap();
        Coords voisin_ = closestTile(d_);
        Place pl_ = d_.getPlace(voisin_.getNumberPlace());
        LevelWithWildPokemon l_ = (LevelWithWildPokemon) pl_.getLevelByCoords(voisin_);
        Point pt_ = voisin_.getLevel().getPoint();
        if (l_.getItems().contains(pt_)) {
            String obj_ = l_.getItems().getVal(pt_);
            player.getItem(obj_);
            takenObjects.put(voisin_, true);
            interfaceType = InterfaceType.RIEN;
        } else if (l_.getTm().contains(pt_)) {
            short obj_ = l_.getTm().getVal(pt_);
            player.getTm(obj_);
            takenObjects.put(voisin_, true);
            interfaceType = InterfaceType.RIEN;
        } else if (l_.getHm().contains(pt_)) {
            short obj_ = l_.getHm().getVal(pt_);
            player.getHm(obj_);
            takenObjects.put(voisin_, true);
            interfaceType = InterfaceType.RIEN;
        } else {
            //l_.getCharacters().contains(pt_)
            CharacterInRoadCave person_ = l_.getCharacters().getVal(pt_);
            //person_ instanceof DealerItem
            for (String o: ((DealerItem)person_).getItems()) {
                player.getItem(o);
            }
            for (Short t: ((DealerItem)person_).getTechnicalMoves()) {
                player.getTm(t);
            }
            takenObjects.put(voisin_, true);
            interfaceType = InterfaceType.RIEN;
        }
    }

    public void initTrainerFight(DataBase _d) {
        DataMap d_=_d.getMap();
        Coords voisin_= closestTile(d_);
        Place pl_ = d_.getPlace(voisin_.getNumberPlace());
        if (pl_ instanceof League) {
            TrainerLeague tr_ = ((League)pl_).getRooms().get(voisin_.getLevel().getLevelIndex()).getTrainer();
            FightFacade.initFight(fight,player,difficulty,tr_,_d);
            FightFacade.initTypeEnv(fight,playerCoords,difficulty,_d);
            commentGame.addComment(fight.getComment());
            return;
        }
        if (pl_ instanceof City) {
            //voisin_.isInside()
            Level l_ = pl_.getLevelByCoords(voisin_);
            //l_ instanceof LevelIndoorGym
            LevelIndoorGym gym_ = (LevelIndoorGym) l_;
            if(gym_.getGymTrainers().contains(voisin_.getLevel().getPoint())) {
                GymTrainer gymTr_ = gym_.getGymTrainers().getVal(voisin_.getLevel().getPoint());
                FightFacade.initFight(fight,player,difficulty,gymTr_,_d);
                FightFacade.initTypeEnv(fight,playerCoords,difficulty,_d);
            } else {
                //if(Point.eq(gym_.getGymLeaderCoords(),voisin_.getLevel().getPoint()))
                GymLeader gymTr_ = gym_.getGymLeader();
                FightFacade.initFight(fight,player,difficulty,gymTr_,_d);
                FightFacade.initTypeEnv(fight,playerCoords,difficulty,_d);
            }
            commentGame.addComment(fight.getComment());
            return;
        }
        LevelWithWildPokemon l_ = (LevelWithWildPokemon) pl_.getLevelByCoords(voisin_);
        for (PointParam<DualFight> e: l_.getDualFights().entryList()) {
            DualFight dual_ = e.getValue();
            if (!Point.eq(e.getKey(), voisin_.getLevel().getPoint())) {
                if (!Point.eq(dual_.getPt(), voisin_.getLevel().getPoint())) {
                    continue;
                }
            }
            FightFacade.initFight(fight,player,difficulty,dual_,_d);
            FightFacade.initTypeEnv(fight,playerCoords,difficulty,_d);
            commentGame.addComment(fight.getComment());
            return;
        }
        //l_.getCharacters().contains(voisin_.getLevel().getPoint())
        CharacterInRoadCave ch_ = l_.getCharacters().getVal(voisin_.getLevel().getPoint());
        //ch_ instanceof TrainerMultiFights
        int nb_=0;
        for(NbFightCoords d:beatTrainer.getKeys()){
            if(Coords.eq(d.getCoords(),voisin_)){
                if(!beatTrainer.getVal(new NbFightCoords(d.getCoords(),nb_))){
                    break;
                }
                nb_++;
            }
        }
        TrainerMultiFights dr_=(TrainerMultiFights)ch_;
        if (!dr_.getTeamsRewards().isValidIndex(nb_)) {
            nb_ = dr_.getTeamsRewards().size() - 1;
        }
        FightFacade.initFight(fight,player,difficulty,dr_,nb_,_d);
        FightFacade.initTypeEnv(fight,playerCoords,difficulty,_d);
        commentGame.addComment(fight.getComment());
    }

    boolean isFrontOfTrainer(DataBase _d) {
        DataMap d_=_d.getMap();
        Coords voisin_= closestTile(d_);
        if (!voisin_.isValid()) {
            return false;
        }
        Place pl_ = d_.getPlace(voisin_.getNumberPlace());
        if (pl_ instanceof League) {
            League league_ = (League) pl_;
            LevelLeague level_ = league_.getRooms().get(voisin_.getLevel().getLevelIndex());
            return Point.eq(level_.getTrainerCoords(), voisin_.getLevel().getPoint());
        }
        if (pl_ instanceof City) {
            if (voisin_.isInside()) {
                Level l_ = pl_.getLevelByCoords(voisin_);
                if (l_ instanceof LevelIndoorGym) {
                    LevelIndoorGym gym_ = (LevelIndoorGym) l_;
                    if(gym_.getGymTrainers().contains(voisin_.getLevel().getPoint())) {
                        return true;
                    }
                    return Point.eq(gym_.getGymLeaderCoords(),voisin_.getLevel().getPoint());
                }
            }
            return false;
        }
        LevelWithWildPokemon l_ = (LevelWithWildPokemon) pl_.getLevelByCoords(voisin_);
        for (PointParam<DualFight> e: l_.getDualFights().entryList()) {
            DualFight dual_ = e.getValue();
            if (!Point.eq(e.getKey(), voisin_.getLevel().getPoint())) {
                if (!Point.eq(dual_.getPt(), voisin_.getLevel().getPoint())) {
                    continue;
                }
            }
            return true;
        }
        if(l_.getCharacters().contains(voisin_.getLevel().getPoint())) {
            CharacterInRoadCave ch_ = l_.getCharacters().getVal(voisin_.getLevel().getPoint());
            return ch_ instanceof TrainerMultiFights;
        }
        return false;
    }

    public int[][] getTrainerImage(DataBase _d) {
        if (!isFrontOfTrainer(_d)) {
            return new int[0][0];
        }
        DataMap d_=_d.getMap();
        Coords voisin_= closestTile(d_);
        Place pl_ = d_.getPlace(voisin_.getNumberPlace());
        if (pl_ instanceof League) {
            TrainerLeague tr_ = ((League)pl_).getRooms().get(voisin_.getLevel().getLevelIndex()).getTrainer();
            return _d.getTrainer(tr_.getImageMaxiFileName());
        }
        if (pl_ instanceof City) {
            Level l_ = pl_.getLevelByCoords(voisin_);
            LevelIndoorGym gym_ = (LevelIndoorGym) l_;
            if(gym_.getGymTrainers().contains(voisin_.getLevel().getPoint())) {
                GymTrainer gymTr_ = gym_.getGymTrainers().getVal(voisin_.getLevel().getPoint());
                return _d.getTrainer(gymTr_.getImageMaxiFileName());
            }
            //if(Point.eq(gym_.getGymLeaderCoords(),voisin_.getLevel().getPoint()))
            GymLeader gymTr_ = gym_.getGymLeader();
            return _d.getTrainer(gymTr_.getImageMaxiFileName());
        }
        LevelWithWildPokemon l_ = (LevelWithWildPokemon) pl_.getLevelByCoords(voisin_);
        for (PointParam<DualFight> e: l_.getDualFights().entryList()) {
            DualFight dual_ = e.getValue();
            if (!Point.eq(e.getKey(), voisin_.getLevel().getPoint())) {
                if (!Point.eq(dual_.getPt(), voisin_.getLevel().getPoint())) {
                    continue;
                }
            }
            return _d.getTrainer(dual_.getFoeTrainer().getImageMaxiFileName());
        }
        CharacterInRoadCave ch_ = l_.getCharacters().getVal(voisin_.getLevel().getPoint());
        TrainerMultiFights dr_=(TrainerMultiFights)ch_;
        return _d.getTrainer(dr_.getImageMaxiFileName());
    }

    public void initFishing(DataBase _d) {
        DataMap d_=_d.getMap();
        Coords voisin_ = closestTile(d_);
        AreaApparition area_ = d_.getAreaByCoords(voisin_);
        if (area_.isVirtual()) {
            return;
        }
        if (area_.getPokemonListLength(false) == IndexConstants.SIZE_EMPTY) {
            return;
        }
        if(!difficulty.getRandomWildFight()){
            newIndex(false, indexPeriodFishing, area_, _d);
            return;
        }
        newRandomPokemon(area_.getWildPokemonRandFishing(), _d);
    }


    public void initLegendaryPokemonFight(DataBase _d){
        DataMap d_=_d.getMap();
        Coords voisin_= closestTile(d_);
        Place place_ = d_.getPlace(voisin_.getNumberPlace());
        Level level_ = place_.getLevelByCoords(voisin_);
        Point pt_ = voisin_.getLevel().getPoint();
        WildPk pkLeg_ = ((LevelWithWildPokemon)level_).getPokemon(pt_);
        FightFacade.initFight(fight,player,difficulty,pkLeg_,_d);
        FightFacade.initTypeEnv(fight,playerCoords,difficulty,_d);
        commentGame.addComment(fight.getComment());
    }

    public void setChosenTeamPokemon(short _pk) {
        player.setChosenTeamPokemon(_pk);
    }

    public void nickname(String _texte, DataBase _import){
        player.nickname(_texte, _import);
    }

    public void chooseFrontFighter(byte _pos, DataBase _import) {
        FightFacade.chooseFrontFighter(fight, _pos,difficulty,_import);
    }

    public void changeAction(ActionType _action, DataBase _import){
        FightFacade.changeAction(fight, _action, _import);
    }

    public void chooseBackFighter(byte _pos, DataBase _import) {
        FightFacade.chooseBackFighter(fight, _pos,_import);
    }

    public void chooseMove(String _move, DataBase _import) {
        FightFacade.chooseMove(fight, _move, difficulty, _import);
    }

    public void deselect() {
        FightFacade.deselect(fight);
    }

    public void setFirstChosenMoveFoeTarget(byte _cible){
        FightFacade.setFirstChosenMoveFoeTarget(fight,_cible);
    }

    public void setFirstChosenMovePlayerTarget(byte _cible){
        FightFacade.setFirstChosenMovePlayerTarget(fight,_cible);
    }

    public void setChosenHealingItem(String _objet,DataBase _import){
        FightFacade.setChosenHealingItem(fight,_objet,_import);
    }

    public NatStringTreeMap<BallNumberRate> calculateCatchingRates(DataBase _import) {
        return FightFacade.calculateCatchingRates(fight, difficulty, player, _import);
    }

    //in a bean
    public ObjectMap<TeamPosition,StringMap<ObjectMap<TeamPosition,Rate>>>
            remainingThrowersTargetsHp(DataBase _import) {
        return FightFacade.remainingThrowersTargetsHp(fight, difficulty, _import);
    }

    //in a bean
    public NatStringTreeMap<EqList<TeamPosition>>
        sortedFightersBeginRoundWildFight(
            DataBase _data) {
        return FightFacade.sortedFightersBeginRoundWildFight(fight, _data);
    }

    //in a bean
    public EqList<TeamPosition>
        sortedFightersBeginRound(
            DataBase _data) {
        return FightFacade.sortedFightersBeginRound(fight, _data);
    }

    public void setSubstituteEndRound(byte _remplacant){
        FightFacade.setSubstituteEndRound(fight,_remplacant);
    }

    public void endFight(DataBase _import) {
        boolean existBall_ = player.existBall(_import);
        FightState etat_=FightFacade.fightStatement(fight,existBall_,difficulty);
        if (etat_==FightState.CAPTURE_KO) {
            fight.setState(etat_);
            return;
        }
        //sortie de cette si pk sauvage
        StringMap<String> mess_ = _import.getMessagesGame();
        if (fight.getFightType().isWild()) {
            if (FightFacade.loose(fight)) {
                LgInt money_ = new LgInt(player.getMoney());
                player.winMoneyFight(new LgInt(-2000));
                commentGame.addMessage(mess_.getVal(LOST_MONEY), LgInt.minus(money_, player.getMoney()).absNb().toNumberString());
                player.healTeamWithoutUsingObject(_import);
            } else if (FightFacade.win(fight)) {
                player.affectEndFight(fight,difficulty,_import);
            } else {
                player.healTeamWithoutUsingObject(_import);
            }
            FightFacade.endFight(fight);
            directInteraction(closestTile(_import.getMap()), _import.getMap());
            return;
        }
        player.affectEndFight(fight,difficulty,_import);
        if (!isFrontOfTrainer(_import)) {
            player.healTeamWithoutUsingObject(_import);
            FightFacade.endFight(fight);
            directInteraction(closestTile(_import.getMap()), _import.getMap());
            return;
        }
        DataMap d_=_import.getMap();
        Coords coordsFoe_ = closestTile(d_);
        Place pl_ = d_.getPlace(playerCoords.getNumberPlace());
        int sommeNiveau_ = 0;
        Team equipeAdv_=fight.getFoeTeam();
        for(byte c:equipeAdv_.getMembers().getKeys()){
            Fighter creature_=equipeAdv_.getMembers().getVal(c);
            sommeNiveau_ += creature_.getLevel();
        }
        LgInt money_ = new LgInt(player.getMoney());
        if(FightFacade.win(fight)){
            Coords beatenImportantTrainer_ = new Coords();
            if (pl_ instanceof League) {
                TrainerLeague tr_ = ((League)pl_).getRooms().get(playerCoords.getLevel().getLevelIndex()).getTrainer();
                rankLeague++;
                Rate gainArgent_=new Rate(fight.getWinningMoney());
                gainArgent_.addNb(new Rate(sommeNiveau_*tr_.getReward()*10));
                gainArgent_.multiplyBy(difficulty.getRateWinMoneyBase());
                player.winMoneyFight(gainArgent_.intPart());
                commentGame.addMessage(mess_.getVal(WON_MONEY), LgInt.minus(money_, player.getMoney()).absNb().toNumberString());
                int nbPlateaux_=((League)pl_).getRooms().size();
                if(rankLeague==nbPlateaux_){
                    Coords coords_ = new Coords();
                    coords_.setNumberPlace(playerCoords.getNumberPlace());
                    coords_.setLevel(new LevelPoint());
                    coords_.getLevel().setLevelIndex((byte) 0);
                    coords_.getLevel().setPoint(((League) pl_).getBegin());
                    beatGymLeader.put(coords_, true);
                    beatenImportantTrainer_ = coords_;
                    player.healTeamWithoutUsingObject(_import);
                }
                addBeatenTrainer(beatenImportantTrainer_, _import);
                FightFacade.endFight(fight);
                directInteraction(closestTile(_import.getMap()), _import.getMap());
                return;
            }
            if (pl_ instanceof City) {
                //playerCoords.isInside()
                Level l_ = pl_.getLevelByCoords(playerCoords);
                //l_ instanceof LevelIndoorGym
                LevelIndoorGym gym_ = (LevelIndoorGym) l_;
                if(gym_.getGymTrainers().contains(coordsFoe_.getLevel().getPoint())) {
                    GymTrainer gymTr_ = gym_.getGymTrainers().getVal(coordsFoe_.getLevel().getPoint());
                    Rate gainArgent_=new Rate(fight.getWinningMoney());
                    gainArgent_.addNb(new Rate(sommeNiveau_*gymTr_.getReward()*10));
                    gainArgent_.multiplyBy(difficulty.getRateWinMoneyBase());
                    player.winMoneyFight(gainArgent_.intPart());
                    commentGame.addMessage(mess_.getVal(WON_MONEY), LgInt.minus(money_, player.getMoney()).absNb().toNumberString());
                    beatGymTrainer.getVal(playerCoords.getNumberPlace()).add(coordsFoe_.getLevel().getPoint());
                    addPossibleBeatLeader(_import);
                    FightFacade.endFight(fight);
                    directInteraction(closestTile(_import.getMap()), _import.getMap());
                    return;
                }
                //if(Point.eq(gym_.getGymLeaderCoords(),coordsFoe_.getLevel().getPoint()))
                GymLeader gymTr_ = gym_.getGymLeader();
                Rate gainArgent_=new Rate(fight.getWinningMoney());
                gainArgent_.addNb(new Rate(sommeNiveau_*gymTr_.getReward()*10));
                gainArgent_.multiplyBy(difficulty.getRateWinMoneyBase());
                player.winMoneyFight(gainArgent_.intPart());
                commentGame.addMessage(mess_.getVal(WON_MONEY), LgInt.minus(money_, player.getMoney()).absNb().toNumberString());
                beatGymLeader.put(coordsFoe_, true);
                beatenImportantTrainer_ = coordsFoe_;
                addBeatenTrainer(beatenImportantTrainer_, _import);
                //player.obtentionCs(gymTr_.getCs());
                player.getTm(gymTr_.getTm());
                FightFacade.endFight(fight);
                directInteraction(closestTile(_import.getMap()), _import.getMap());
                return;
            }
            LevelWithWildPokemon l_ = (LevelWithWildPokemon) pl_.getLevelByCoords(playerCoords);
            for (PointParam<DualFight> e: l_.getDualFights().entryList()) {
                DualFight dual_ = e.getValue();
                if (!Point.eq(e.getKey(), coordsFoe_.getLevel().getPoint())) {
                    if (!Point.eq(dual_.getPt(), coordsFoe_.getLevel().getPoint())) {
                        continue;
                    }
                }
                Rate gainArgent_=new Rate(fight.getWinningMoney());
                gainArgent_.addNb(new Rate(sommeNiveau_*dual_.getFoeTrainer().getReward()*10));
                gainArgent_.multiplyBy(difficulty.getRateWinMoneyBase());
                player.winMoneyFight(gainArgent_.intPart());
                commentGame.addMessage(mess_.getVal(WON_MONEY), LgInt.minus(money_, player.getMoney()).absNb().toNumberString());
                player.healTeamWithoutUsingObject(_import);
                Coords key_ = new Coords(playerCoords);
                key_.getLevel().getPoint().affect(e.getKey());
                beatGymLeader.put(key_, true);
                beatenImportantTrainer_ = key_;
                addBeatenTrainer(beatenImportantTrainer_, _import);
                FightFacade.endFight(fight);
                directInteraction(closestTile(_import.getMap()), _import.getMap());
                return;
            }
            for (PointParam<CharacterInRoadCave> e:l_.getCharacters().entryList()) {
                if (!Point.eq(e.getKey(),coordsFoe_.getLevel().getPoint())) {
                    continue;
                }
                CharacterInRoadCave ch_ = e.getValue();
                //ch_ instanceof TrainerMultiFights
                int nb_=0;
                for(NbFightCoords d:beatTrainer.getKeys()){
                    if(Coords.eq(d.getCoords(),coordsFoe_)){
                        if(!beatTrainer.getVal(new NbFightCoords(d.getCoords(),nb_))){
                            break;
                        }
                        nb_++;
                    }
                }
                TrainerMultiFights dr_=(TrainerMultiFights)ch_;
                if (!dr_.getTeamsRewards().isValidIndex(nb_)) {
                    nb_ = dr_.getTeamsRewards().size() - 1;
                }
                Rate gainArgent_=new Rate(fight.getWinningMoney());
                gainArgent_.addNb(new Rate(sommeNiveau_*dr_.getTeamsRewards().get(nb_).getReward()*10));
                gainArgent_.multiplyBy(difficulty.getRateWinMoneyBase());
                player.winMoneyFight(gainArgent_.intPart());
                commentGame.addMessage(mess_.getVal(WON_MONEY), LgInt.minus(money_, player.getMoney()).absNb().toNumberString());
                beatTrainer.put(new NbFightCoords(coordsFoe_,nb_), true);
                //player.obtentionCs(gymTr_.getCs());
                FightFacade.endFight(fight);
                directInteraction(closestTile(_import.getMap()), _import.getMap());
            }
            return;
        }
        if(FightFacade.equality(fight)){
            if (pl_ instanceof League) {
                int nbPlateaux_=((League)pl_).getRooms().size();
                if(rankLeague+1==nbPlateaux_){
                    TrainerLeague tr_ = ((League)pl_).getRooms().get(playerCoords.getLevel().getLevelIndex()).getTrainer();
                    rankLeague++;
                    Rate gainArgent_=new Rate(fight.getWinningMoney());
                    gainArgent_.addNb(new Rate(sommeNiveau_*tr_.getReward()*10));
                    gainArgent_.multiplyBy(difficulty.getRateWinMoneyBase());
                    player.winMoneyFight(gainArgent_.intPart());
                    commentGame.addMessage(mess_.getVal(WON_MONEY), LgInt.minus(money_, player.getMoney()).absNb().toNumberString());
                    Coords coords_ = new Coords();
                    coords_.setNumberPlace(playerCoords.getNumberPlace());
                    coords_.setLevel(new LevelPoint());
                    coords_.getLevel().setLevelIndex((byte) 0);
                    coords_.getLevel().setPoint(((League) pl_).getBegin());
                    beatGymLeader.put(coords_, true);
                    addBeatenTrainer(coords_, _import);
                    player.healTeamWithoutUsingObject(_import);
                    FightFacade.endFight(fight);
                    directInteraction(closestTile(_import.getMap()), _import.getMap());
                    return;
                }
            }
        }
        //FightFacade.equality(fight) or FightFacade.loose(fight)
        if (pl_ instanceof League) {
            TrainerLeague tr_ = ((League)pl_).getRooms().get(playerCoords.getLevel().getLevelIndex()).getTrainer();
            Rate gainArgent_=new Rate(fight.getWinningMoney());
            gainArgent_.addNb(new Rate(sommeNiveau_*tr_.getReward()*10));
            gainArgent_.multiplyBy(difficulty.getRateWinMoneyBase());
            player.winMoneyFight(gainArgent_.intPart().opposNb());
            commentGame.addMessage(mess_.getVal(LOST_MONEY), LgInt.minus(money_, player.getMoney()).absNb().toNumberString());
            FightFacade.endFight(fight);
            //begin of league => just before league
            playerCoords.affect(((League)pl_).getAccessCoords());
            player.healTeamWithoutUsingObject(_import);
            directInteraction(closestTile(_import.getMap()), _import.getMap());
            return;
        }
        if (pl_ instanceof City) {
            //playerCoords.isInside()
            Level l_ = pl_.getLevelByCoords(playerCoords);
            //l_ instanceof LevelIndoorGym
            LevelIndoorGym gym_ = (LevelIndoorGym) l_;
            if(gym_.getGymTrainers().contains(coordsFoe_.getLevel().getPoint())) {
                GymTrainer gymTr_ = gym_.getGymTrainers().getVal(coordsFoe_.getLevel().getPoint());
                Rate gainArgent_=new Rate(fight.getWinningMoney());
                gainArgent_.addNb(new Rate(sommeNiveau_*gymTr_.getReward()*10));
                gainArgent_.multiplyBy(difficulty.getRateWinMoneyBase());
                player.winMoneyFight(gainArgent_.intPart().opposNb());
                commentGame.addMessage(mess_.getVal(LOST_MONEY), LgInt.minus(money_, player.getMoney()).absNb().toNumberString());
                FightFacade.endFight(fight);
                //begin of the game
                playerCoords.affect(d_.getBegin());
                player.healTeamWithoutUsingObject(_import);
                directInteraction(closestTile(_import.getMap()), _import.getMap());
                return;
            }
            //if(Point.eq(gym_.getGymLeaderCoords(),coordsFoe_.getLevel().getPoint()))
            GymLeader gymTr_ = gym_.getGymLeader();
            Rate gainArgent_=new Rate(fight.getWinningMoney());
            gainArgent_.addNb(new Rate(sommeNiveau_*gymTr_.getReward()*10));
            gainArgent_.multiplyBy(difficulty.getRateWinMoneyBase());
            player.winMoneyFight(gainArgent_.intPart().opposNb());
            commentGame.addMessage(mess_.getVal(LOST_MONEY), LgInt.minus(money_, player.getMoney()).absNb().toNumberString());
            FightFacade.endFight(fight);
            //begin of the game
            playerCoords.affect(d_.getBegin());
            player.healTeamWithoutUsingObject(_import);
            directInteraction(closestTile(_import.getMap()), _import.getMap());
            return;
        }
        LevelWithWildPokemon l_ = (LevelWithWildPokemon) pl_.getLevelByCoords(playerCoords);
        for (PointParam<DualFight> e: l_.getDualFights().entryList()) {
            DualFight dual_ = e.getValue();
            if (!Point.eq(e.getKey(), coordsFoe_.getLevel().getPoint())) {
                if (!Point.eq(dual_.getPt(), coordsFoe_.getLevel().getPoint())) {
                    continue;
                }
            }
            Rate gainArgent_=new Rate(fight.getWinningMoney());
            gainArgent_.addNb(new Rate(sommeNiveau_*dual_.getFoeTrainer().getReward()*10));
            gainArgent_.multiplyBy(difficulty.getRateWinMoneyBase());
            player.winMoneyFight(gainArgent_.intPart().opposNb());
            commentGame.addMessage(mess_.getVal(LOST_MONEY), LgInt.minus(money_, player.getMoney()).absNb().toNumberString());
            FightFacade.endFight(fight);
            //begin of the game
            playerCoords.affect(d_.getBegin());
            player.healTeamWithoutUsingObject(_import);
            directInteraction(closestTile(_import.getMap()), _import.getMap());
            return;
        }
        for (PointParam<CharacterInRoadCave> e:l_.getCharacters().entryList()) {
            if (!Point.eq(e.getKey(),coordsFoe_.getLevel().getPoint())) {
                continue;
            }
            CharacterInRoadCave ch_ = e.getValue();
            //ch_ instanceof TrainerMultiFights
            int nb_=0;
            for(NbFightCoords d:beatTrainer.getKeys()){
                if(Coords.eq(d.getCoords(),coordsFoe_)){
                    if(!beatTrainer.getVal(new NbFightCoords(d.getCoords(),nb_))){
                        break;
                    }
                    nb_++;
                }
            }
            TrainerMultiFights dr_=(TrainerMultiFights)ch_;
            if (!dr_.getTeamsRewards().isValidIndex(nb_)) {
                nb_ = dr_.getTeamsRewards().size() - 1;
            }
            Rate gainArgent_=new Rate(fight.getWinningMoney());
            gainArgent_.addNb(new Rate(sommeNiveau_*dr_.getTeamsRewards().get(nb_).getReward()*10));
            gainArgent_.multiplyBy(difficulty.getRateWinMoneyBase());
            player.winMoneyFight(gainArgent_.intPart().opposNb());
            commentGame.addMessage(mess_.getVal(LOST_MONEY), LgInt.minus(money_, player.getMoney()).absNb().toNumberString());
            FightFacade.endFight(fight);
            //begin of the game
            playerCoords.affect(d_.getBegin());
            player.healTeamWithoutUsingObject(_import);
            directInteraction(closestTile(_import.getMap()), _import.getMap());
        }
    }

    void addBeatenTrainer(Coords _coords, DataBase _import) {
        if (!_coords.isValid()) {
            return;
        }
        DataMap map_ = _import.getMap();
        EqList<Coords> accessible_ = new EqList<Coords>();
        EqList<Coords> inaccessible_ = new EqList<Coords>();
        EqList<Coords> beaten_ = getBeatenGymLeader();
        for (Coords c: map_.getAccessibility().getKeys()) {
            if (!map_.getAccessibility().getVal(c).containsObj(_coords)) {
                continue;
            }
            if (beaten_.containsAllObj(map_.getAccessibility().getVal(c))) {
                accessible_.add(c);
            } else {
                inaccessible_.add(c);
            }
        }
        Shorts accessiblePlaces_ = new Shorts();
        for (Coords c: accessible_) {
            accessiblePlaces_.add(c.getNumberPlace());
        }
        accessiblePlaces_.removeDuplicates();
        Shorts inaccessiblePlaces_ = new Shorts();
        for (Coords c: inaccessible_) {
            inaccessiblePlaces_.add(c.getNumberPlace());
        }
        inaccessiblePlaces_.removeDuplicates();
        StringList partiallyAccessiblePlaces_ = new StringList();
        StringList fullAccessiblePlaces_ = new StringList();
        for (Short c: accessiblePlaces_) {
            Place pl_ = map_.getPlace(c);
            if (inaccessiblePlaces_.containsObj(c)) {
                partiallyAccessiblePlaces_.add(pl_.getName());
            } else {
                fullAccessiblePlaces_.add(pl_.getName());
            }
        }
        fullAccessiblePlaces_.sort();
        partiallyAccessiblePlaces_.sort();
        fullAccessiblePlaces = fullAccessiblePlaces_;
        partiallyAccessiblePlaces = partiallyAccessiblePlaces_;
        StringMap<String> mess_ = _import.getMessagesGame();
        if (!fullAccessiblePlaces_.isEmpty()) {
            commentGame.addMessage(mess_.getVal(BEATEN_TRAINER_FULL), StringUtil.join(fullAccessiblePlaces_, SEPARATOR_TRAINERS));
        }
        if (!partiallyAccessiblePlaces_.isEmpty()) {
            commentGame.addMessage(mess_.getVal(BEATEN_TRAINER), StringUtil.join(partiallyAccessiblePlaces_, SEPARATOR_TRAINERS));
        }
    }

    void addPossibleBeatLeader(DataBase _import) {
        Coords next_ = closestTile(_import.getMap());
        City city_ = (City) _import.getMap().getPlace(next_.getNumberPlace());
        Gym building_ = (Gym) city_.getBuildings().getVal(next_.getInsideBuilding());
        LevelIndoorGym lev_ = (LevelIndoorGym) city_.getLevelByCoords(next_);
        boolean canBeat_ = beatGymTrainer.getVal(next_.getNumberPlace()).containsAllObj(building_.getIndoor().getGymTrainers().getKeys());
        if (canBeat_) {
             StringMap<String> mess_ = _import.getMessagesGame();
            commentGame.addMessage(mess_.getVal(POSSIBLE_BEAT_LEADER), lev_.getGymLeader().getName());
        }
    }

    public void roundWhileKoPlayer(DataBase _import, boolean _enableAnimation) {
        commentGame.clearMessages();
        FightFacade.roundWhileKoPlayer(fight, difficulty, player, _import, _enableAnimation);
        commentGame.addComment(fight.getComment());
        if (!_enableAnimation) {
            if(FightFacade.koTeam(fight)){
                endFight(_import);
            }
        }
    }

    public void endRoundFightKoUser(DataBase _import) {
        commentGame.clearMessages();
        if (fight.isEndRoundFightKoPlayer()) {
            FightFacade.endRoundFightBasic(fight, difficulty, player, _import);
        }
        //fight.setState(FightState.SWITCH_WHILE_KO_USER);
        commentGame.addComment(fight.getComment());
        if(FightFacade.koTeam(fight)){
            endFight(_import);
        }
    }

    public void sendSubstitutes(DataBase _import){
        commentGame.clearMessages();
        FightFacade.sendSubstitutesChooseActions(fight,difficulty, player,_import);
        commentGame.addComment(fight.getComment());
        if (fight.getState() == FightState.APPRENDRE_EVOLUER) {
            return;
        }
        if(FightFacade.koTeam(fight)){
            endFight(_import);
        }
    }

    public void roundAllThrowers(DataBase _import, boolean _enableAnimation){
        //LUTTE est a proposer en ihm
        commentGame.clearMessages();
        FightFacade.regularRoundAllThrowersChooseActionsFoe(fight,difficulty,player,_import, _enableAnimation);
        commentGame.addComment(fight.getComment());
        if (!_enableAnimation) {
            for(String c:fight.getUsedItemsWhileRound().getKeys()){
                short quantite_=fight.getUsedItemsWhileRound().getVal(c);
                for(byte i = IndexConstants.FIRST_INDEX; i<quantite_; i++){
                    player.useInInventory(c);
                }
            }
            if (fight.getState() == FightState.APPRENDRE_EVOLUER) {
                return;
            }
            if (FightFacade.koTeam(fight)) {
                endFight(_import);
            }
        }
    }

    public void roundUser(DataBase _import) {
        commentGame.clearMessages();
        FightFacade.roundUser(fight, difficulty, _import);
        commentGame.addComment(fight.getComment());
    }

    public void endRoundFightBasic(DataBase _import) {
        commentGame.clearMessages();
        FightFacade.endRoundFightBasic(fight, difficulty, player, _import);
        commentGame.addComment(fight.getComment());
        for(String c:fight.getUsedItemsWhileRound().getKeys()){
            short quantite_=fight.getUsedItemsWhileRound().getVal(c);
            for(byte i = IndexConstants.FIRST_INDEX; i<quantite_; i++){
                player.useInInventory(c);
            }
        }
        if (fight.getState() == FightState.APPRENDRE_EVOLUER) {
            return;
        }
        if (FightFacade.koTeam(fight)) {
            endFight(_import);
        }
    }

    public ByteTreeMap<Fighter> getPlayerTeam() {
        return FightFacade.getPlayerTeam(fight);
    }

    public ByteTreeMap<Fighter> getFoeFrontTeam() {
        return FightFacade.getFoeFrontTeam(fight);
    }

    public ByteTreeMap<Fighter> getUnionFrontTeam() {
        return FightFacade.getUnionFrontTeam(fight);
    }

    public ByteTreeMap<Fighter> getPlayerFrontTeam() {
        return FightFacade.getPlayerFrontTeam(fight);
    }

    public ByteTreeMap<Fighter> getPlayerBackTeam() {
        return FightFacade.getPlayerBackTeam(fight);
    }

    public ByteTreeMap<Fighter> getPlayerFrontTeamForSubstituting() {
        return FightFacade.getPlayerFrontTeamForSubstituting(fight);
    }

    public ByteTreeMap<Fighter> getPlayerBackTeamForSubstituting() {
        return FightFacade.getPlayerBackTeamForSubstituting(fight);
    }

    public boolean isChosableForLearningAndEvolving(byte _key) {
        return FightFacade.isChosableForLearningAndEvolving(fight, _key);
    }

    public void choosePokemonForLearningAndEvolving(byte _key, DataBase _d) {
        FightFacade.choosePokemonForLearningAndEvolving(fight, _key, _d);
    }

    public byte getChosenIndex() {
        return fight.getChosenIndex();
    }

    public NatStringTreeMap<Boolean> getMoves() {
        return fight.getMoves();
    }

    public TreeMap<String,Boolean> getEvolutions() {
        return fight.getEvolutions();
    }

    public StringList getAbilities() {
        return fight.getAbilities();
    }

    public String getAbility() {
        return fight.getAbility();
    }

    public void addOrForgetMove(String _move) {
        FightFacade.addOrForgetMove(fight,_move);
    }

    public void setAbility(String _ability) {
        FightFacade.setAbility(fight,_ability);
    }

    public void setEvolution(String _evo) {
        FightFacade.setEvolution(fight,_evo);
    }

    public void learnAndEvolve(DataBase _import) {
        commentGame.clearMessages();
        if (FightFacade.possibleChoices(fight, _import)) {
            FightFacade.learnAndEvolveAttack(fight, difficulty, _import);
            if (FightFacade.koTeam(fight)) {
                endFight(_import);
            }
        }
        commentGame.addComment(fight.getComment());
    }

    public void attemptFlee(DataBase _import, boolean _enableAnimation){
        FightFacade.attemptFlee(fight,difficulty,player,_import, _enableAnimation);
        if (!_enableAnimation) {
            if(fight.getState()==FightState.REDESSIN_SCENE){
                player.affectEndFight(fight,difficulty,_import);
                FightFacade.endFight(fight);
                directInteraction(closestTile(_import.getMap()), _import.getMap());
            } else if (FightFacade.koTeam(fight)) {
                endFight(_import);
            }
        }
    }

    public void endRoundFightFlee(DataBase _import) {
        if(fight.getState()!=FightState.REDESSIN_SCENE){
            endRoundFightBasic(_import);
        }
        if(fight.getState()==FightState.REDESSIN_SCENE){
            player.affectEndFight(fight,difficulty,_import);
            FightFacade.endFight(fight);
            directInteraction(closestTile(_import.getMap()), _import.getMap());
        } else if (FightFacade.koTeam(fight)) {
            endFight(_import);
        }
    }

    public Rate calculateFleeingRate(DataBase _import) {
        return FightFacade.calculateFleeingRate(fight, difficulty, _import);
    }

    public void attemptCatchingWildPokemon(String _ball,DataBase _import, boolean _enableAnimation){
        boolean present_=player.estAttrape(fight.wildPokemon().getName());
        FightFacade.attemptCatching(fight,_ball,present_,difficulty,player,_import, _enableAnimation);
        player.useInInventory(_ball);
        StringMap<String> mess_ = _import.getMessagesGame();
        if (!_enableAnimation) {
            if (fight.getState() == FightState.SURNOM) {
                commentGame.addMessage(mess_.getVal(CAUGHT_PK));
            } else if (FightFacade.koTeam(fight)) {
                endFight(_import);
            } else {
                commentGame.addMessage(mess_.getVal(NOT_CAUGHT_PK));
            }
        }
    }

    public void endRoundFightBall(DataBase _import) {
        endRoundFightBasic(_import);
        endRoundFightSuccessBall(_import);
    }

    public void endRoundFightSuccessBall(DataBase _import) {
        StringMap<String> mess_ = _import.getMessagesGame();
        if (fight.getState() == FightState.SURNOM) {
            commentGame.addMessage(mess_.getVal(CAUGHT_PK));
        } else if (FightFacade.koTeam(fight)) {
            endFight(_import);
        } else {
            commentGame.addMessage(mess_.getVal(NOT_CAUGHT_PK));
        }
    }

    public void notCatchKoWildPokemon(DataBase _import){
        player.affectEndFight(fight,difficulty,_import);
        FightFacade.endFight(fight);
        directInteraction(closestTile(_import.getMap()), _import.getMap());
    }

    public void catchKoWildPokemon(String _ball, String _pseudo,DataBase _import){
        player.useInInventory(_ball);
        fight.setCatchingBall(_ball);
        catchWildPokemon(_pseudo, _import);
    }

    public void catchWildPokemon(String _pseudo,DataBase _import){
        player.affectEndFight(fight,difficulty,_import);
        player.catchWildPokemon(fight.wildPokemon(),_pseudo,fight.getCatchingBall(),difficulty,_import);
        PokemonData fPk_ = _import.getPokemon(fight.wildPokemon().getName());
        if (fPk_.getGenderRep() == GenderRepartition.LEGENDARY) {
            if (nextLegPk(_import)) {
                Coords n_ = closestTile(_import.getMap());
                takenPokemon.put(n_, true);
            }
        }
        FightFacade.endFight(fight);
        directInteraction(closestTile(_import.getMap()), _import.getMap());
    }

    boolean nextLegPk(DataBase _import) {
        Coords n_ = closestTile(_import.getMap());
        if (!n_.isValid()) {
            return false;
        }
        Place place_ = _import.getMap().getPlace(n_.getNumberPlace());
        Level level_ = place_.getLevelByCoords(n_);
        Point pt_ = n_.getLevel().getPoint();
        if (level_ instanceof LevelWithWildPokemon) {
            return ((LevelWithWildPokemon) level_).containsPokemon(pt_);
        }
        return false;
    }

    void catchAll(DataBase _d) {
        for(String p:_d.getPokedex().getKeys()){
            player.getCaughtPk().put(p, true);
        }
    }

    boolean endGame(DataBase _d){
        boolean attrape_=true;
        for(String p:_d.getPokedex().getKeys()){
            if (!player.estAttrape(p)) {
                attrape_ = false;
            }
        }
        if(!attrape_){
            return false;
        }
        if (!getUnBeatenGymLeader().isEmpty()) {
            return false;
        }
        for (NbFightCoords k: beatTrainer.getKeys()) {
            Coords coords_ = k.getCoords();
            Campaign place_ = (Campaign) _d.getMap().getPlace(coords_.getNumberPlace());
            LevelWithWildPokemon level_ = place_.getLevelCompaignByCoords(coords_);
            TrainerMultiFights trainer_ = (TrainerMultiFights) level_.getCharacters().getVal(coords_.getLevel().getPoint());
            if (k.getNbFight() == trainer_.getTeamsRewards().getLastIndex()) {
                if (!beatTrainer.getVal(k)) {
                    return false;
                }
            }
        }
        if (!getUnVisited().isEmpty()) {
            return false;
        }
        if (!player.getEggsList().isEmpty()) {
            return false;
        }
        CustList<PokemonPlayer> pks_ = new CustList<PokemonPlayer>();
        pks_.addAllElts(player.getPokemonPlayerList().values());
        for (UsablePokemon u: player.getBox()) {
            if (u instanceof Egg) {
                return false;
            }
            PokemonPlayer pk_ = (PokemonPlayer) u;
            pks_.add(pk_);
        }
        for (PokemonPlayer p: pks_) {
            if (p.getLevel() < _d.getMaxLevel()) {
                return false;
            }
            if (p.getHappiness() < _d.getHappinessMax()) {
                return false;
            }
        }
        return true;
    }

    public void simuler(CustList<CustList<ActionMove>> _actionsTour,
            CustList<CustList<ActionSwitch>> _actionsSubstitutingFront,
            CustList<CustList<ActionSwitch>> _actionsSubstitutingBack,
            CustList<ByteMap<ChoiceOfEvolutionAndMoves>> _evolutions,
            DataBase _import){
        //_evolutions for each round: key position before fight, value choices
        FightFacade.simulate(fight,_actionsTour,
                _actionsSubstitutingFront,_actionsSubstitutingBack,
                _evolutions,
                player,difficulty,_import);
    }

    public void healTeamWithoutUsingObject(DataBase _import){
        player.healTeamWithoutUsingObject(_import);
    }

    public void moving(Direction _direction,DataBase _d){
        commentGame.clearMessages();
        placeChanged = false;
        incrementStepsToLayEggs(_d);
        DataMap d_=_d.getMap();
        Coords voisin_;
        nbSteps = 0;
        if(_direction != playerOrientation){
            playerOrientation = _direction;
            directInteraction(d_);
            return;
        }
        movingHero(_d);
        if (visitedPlaces.contains(playerCoords)) {
            visitedPlaces.put(playerCoords, true);
            visitedPlacesNb.put(playerCoords.getNumberPlace(),true);
        }
        voisin_ = closestTile(d_);
        player.moveLoop(nbSteps, difficulty, _d);
        commentGame.addComment(player.getCommentGame());
        if (nbSteps > 0) {
            processWalkingAreaApparition(voisin_, _d);
        }
        showEndGame = false;
        if (!fight.getFightType().isExisting()) {
            if (endGame(_d)) {
                //show by an other kind the end of game
                //rather than modal dialog box
                showEndGame = true;
            }
        }
    }

    void directInteraction(DataMap _d) {
        Coords voisin_ = closestTile(_d);
        if (voisin_.isValid()) {
            directInteraction(voisin_, _d);
            return;
        }
        interfaceType=InterfaceType.RIEN;
    }

    void processWalkingAreaApparition(Coords _coords, DataBase _d) {
        DataMap d_ = _d.getMap();
        if (player.getRepousseActif()) {
            directInteractionValid(_coords, d_);
            return;
        }
        AreaApparition area_ = d_.getAreaByCoords(playerCoords);
        if (area_.isVirtual()) {
            directInteractionValid(_coords, d_);
            return;
        }
        if(!difficulty.getRandomWildFight()){
            incrementPeriod(area_, _d);
        } else {
            indexPeriod=0;
            indexStep=0;
            newRandomPokemon(area_.getWildPokemonRand(), _d);
        }
        if (!fight.getFightType().isExisting()) {
            directInteractionValid(_coords, d_);
        }
    }

    private void directInteractionValid(Coords _coords, DataMap _d) {
        if (_coords.isValid()) {
            directInteraction(_coords, _d);
            return;
        }
        interfaceType=InterfaceType.RIEN;
    }
    void incrementPeriod(AreaApparition _area, DataBase _d) {
        indexStep++;
        if(indexStep >= _area.getAvgNbSteps()){
            newIndex(true, indexPeriod, _area, _d);
            indexStep=0;
        }
    }

    void movingHero(DataBase _db) {
        DataMap map_ = _db.getMap();
        Coords voisin_= closestTile(map_);
        if(!voisin_.isValid()){
            return;
        }
        Place nextPl_ = map_.getPlace(voisin_.getNumberPlace());
        if (nextPl_ instanceof League) {
            if (!isEmpty(map_, voisin_)) {
                return;
            }
            LevelLeague nextLevel_ = ((League)nextPl_).getRooms().get(voisin_.getLevel().getLevelIndex());
            if(Point.eq(nextLevel_.getAccessPoint(),voisin_.getLevel().getPoint())) {
                if(rankLeague>voisin_.getLevel().getLevelIndex()){
                    placeChanged = true;
                    nbSteps++;
                    if(rankLeague == ((League)nextPl_).getRooms().size()){
                        playerCoords.affect(map_.getBegin());
                        rankLeague = 0;
                        return;
                    }
                    playerCoords.getLevel().setLevelIndex((byte) (playerCoords.getLevel().getLevelIndex()+1));
                    playerCoords.getLevel().getPoint().affect(nextLevel_.getNextLevelTarget());
                    return;
                }
            }
            nbSteps++;
            playerCoords.affect(voisin_);
            return;
        }
        StringMap<String> mess_ = _db.getMessagesGame();
        if(map_.getAccessCondition().contains(voisin_)){
            EqList<Coords> leaders_ = getBeatenGymLeader();
            if (!leaders_.containsAllObj(map_.getAccessCondition().getVal(voisin_))) {
                EqList<Coords> noBeaten_ = new EqList<Coords>(map_.getAccessCondition().getVal(voisin_));
                noBeaten_.removeAllElements(leaders_);
                for (Coords c: noBeaten_) {
                    Place pl_ = map_.getPlace(c.getNumberPlace());
                    String name_ = map_.getTrainerName(c);
                    commentGame.addMessage(mess_.getVal(NO_BEATEN_TRAINER), name_, pl_.getName());
                }
                return;
            }
        }
        int nbPlaces_ = map_.getPlaces().size();
        for (short p = IndexConstants.FIRST_INDEX; p<nbPlaces_; p++) {
            Place place_ = map_.getPlace(p);
            if (!(place_ instanceof League)) {
                continue;
            }
            Coords coords_ = ((League)place_).getAccessCoords();
            if (Coords.eq(coords_, voisin_)) {
                rankLeague = 0;
                nbSteps++;
                playerCoords.setNumberPlace(p);
                playerCoords.getLevel().setLevelIndex((byte) 0);
                playerCoords.getLevel().getPoint().affect(((League)place_).getBegin());
                placeChanged = true;
                return;
            }
        }
        Level nextlevel_;
        nextlevel_ = nextPl_.getLevelByCoords(voisin_);
        Point nextPt_ = voisin_.getLevel().getPoint();
        if(nextPl_ instanceof City && !playerCoords.isInside()) {
            Points<Building> buildings_ = ((City)nextPl_).getBuildings();
            if (buildings_.contains(nextPt_)) {
                nbSteps++;
                playerCoords.getLevel().getPoint().affect(buildings_.getVal(nextPt_).getExitCity());
                playerCoords.affectInside(nextPt_);
                playerCoords.getLevel().setLevelIndex((byte) 0);
                placeChanged = true;
                return;
            }
        }
        if (nextPl_ instanceof Cave) {
            Cave cave_ = (Cave) nextPl_;
            if (cave_.getLinksWithOtherPlaces().contains(voisin_.getLevel())) {
                nbSteps++;
                playerCoords.affect(cave_.getLinksWithOtherPlaces().getVal(voisin_.getLevel()).getCoords());
                placeChanged = true;
                return;
            }
        }
        if(nextPl_ instanceof InitializedPlace) {
            if (((InitializedPlace)nextPl_).getLinksWithCaves().contains(nextPt_)) {
                Link to_ = ((InitializedPlace)nextPl_).getLinksWithCaves().getVal(nextPt_);
                nbSteps++;
                playerCoords.affect(to_.getCoords());
                placeChanged = true;
                return;
            }
        }
        if (nextlevel_.getBlockByPoint(nextPt_).getType() == EnvironmentType.NOTHING) {
            return;
        }
        if (nextPl_ instanceof City && playerCoords.isInside()) {
            Point inside_ = playerCoords.getInsideBuilding();
            Building building_ = ((City)nextPl_).getBuildings().getVal(inside_);
            if (Point.eq(building_.getExitCity(),nextPt_)) {
                inside_.moveTo(Direction.DOWN);
                //not on the door of the building
                nbSteps++;
                playerCoords.getLevel().getPoint().affect(inside_);
                playerCoords.getLevel().setLevelIndex((byte) 0);
                playerCoords.outside();
                placeChanged = true;
                return;
            }
            if (!isEmpty(map_, voisin_)) {
                return;
            }
            nbSteps++;
            playerCoords.getLevel().getPoint().affect(nextPt_);
            return;
        }
        if(!(nextPl_ instanceof Cave)) {
            if (!isEmpty(map_, voisin_)) {
                return;
            }
            nbSteps++;
            playerCoords.affect(voisin_);
            return;
        }
        //nextPl_ instanceof Cave
        Cave cave_ = (Cave) nextPl_;
        LevelPoint lPoint_ = voisin_.getLevel();
        LevelCave levelCave_ = (LevelCave)cave_.getLevelsMap().getVal(lPoint_.getLevelIndex());
        if (levelCave_.getLinksOtherLevels().contains(lPoint_.getPoint())) {
            nbSteps++;
            playerCoords.affect(levelCave_.getLinksOtherLevels().getVal(lPoint_.getPoint()).getCoords());
            placeChanged = true;
            return;
        }
        if (!isEmpty(map_, voisin_)) {
            return;
        }
        nbSteps++;
        playerCoords.getLevel().getPoint().affect(nextPt_);
    }

    public void directInteraction(Coords _voisin, DataMap _map) {
        if (!_voisin.isValid()) {
            interfaceType=InterfaceType.RIEN;
            return;
        }
        Place pl_ = _map.getPlace(_voisin.getNumberPlace());
        Point pt_ = _voisin.getLevel().getPoint();
        if (pl_ instanceof League) {
            LevelLeague level_ = ((League)pl_).getRooms().get(_voisin.getLevel().getLevelIndex());
            if (Point.eq(level_.getTrainerCoords(),_voisin.getLevel().getPoint())) {
                if (NumberUtil.eq(rankLeague,_voisin.getLevel().getLevelIndex())){
                    interfaceType=InterfaceType.DRESSEUR;
                } else {
                    interfaceType=InterfaceType.PERSONNAGE;
                }
                return;
            }
            interfaceType=InterfaceType.RIEN;
            return;
        }
        if (pl_ instanceof City) {
            if (_voisin.isInside()) {
                Building building_ = ((City)pl_).getBuildings().getVal(_voisin.getInsideBuilding());
                if (building_ instanceof Gym) {
                    if (((Gym)building_).getIndoor().getGymTrainers().contains(_voisin.getLevel().getPoint())) {
                        if (!beatGymTrainer.getVal(_voisin.getNumberPlace()).containsObj(_voisin.getLevel().getPoint())) {
                            interfaceType=InterfaceType.DRESSEUR;
                            return;
                        }
                        interfaceType=InterfaceType.RIEN;
                        return;
                    }
                    if (Point.eq(((Gym)building_).getIndoor().getGymLeaderCoords(),_voisin.getLevel().getPoint())) {
                        if (beatGymTrainer.getVal(_voisin.getNumberPlace()).containsAllObj(((Gym)building_).getIndoor().getGymTrainers().getKeys())) {
                            if (!beatGymLeader.getVal(_voisin)) {
                                interfaceType=InterfaceType.DRESSEUR;
                            } else {
                                interfaceType=InterfaceType.RIEN;
                            }
                        } else {
                            interfaceType=InterfaceType.GYM_LEADER;
                        }
                        return;
                    }
                    interfaceType=InterfaceType.RIEN;
                    return;
                }
                //building_ instanceof PokemonCenter
                Points<Person> gerants_ =((PokemonCenter)building_).getIndoor().getGerants();
                if (gerants_.contains(_voisin.getLevel().getPoint())) {
                    Person gear_ = gerants_.getVal(_voisin.getLevel().getPoint());
                    if (gear_ instanceof Seller) {
                        SellType sell_ = ((Seller) gear_).getSell();
                        interfaceType=InterfaceType.RIEN;
                        if (sell_ == SellType.TM) {
                            interfaceType = InterfaceType.ACHATS_CT;
                        }
                        if (sell_ == SellType.ITEM) {
                            interfaceType = InterfaceType.ACHATS;
                        }
                        if (sell_ == SellType.MOVE) {
                            interfaceType = InterfaceType.MOVE_TUTORS;
                        }
                        return;
                    }
                    GeranceType gerance_ = ((GerantPokemon) gear_).getGerance();
                    interfaceType=InterfaceType.RIEN;
                    if (gerance_ == GeranceType.FOSSILE) {
                        interfaceType=InterfaceType.FOSSILE;
                    }
                    if (gerance_ == GeranceType.HEAL) {
                        interfaceType=InterfaceType.SOIN_PK;
                    }
                    if (gerance_ == GeranceType.HOST) {
                        interfaceType=InterfaceType.PENSION;
                    }
                    return;
                }
                if (Point.eq(((PokemonCenter)building_).getIndoor().getStorageCoords(),_voisin.getLevel().getPoint())) {
                    interfaceType = InterfaceType.ECH_BOITE;
                    return;
                }
            }
            interfaceType=InterfaceType.RIEN;
            return;
        }
        //pl_ instanceof Campaign
        Campaign campaign_ = (Campaign) pl_;
        LevelWithWildPokemon level_ = (LevelWithWildPokemon) campaign_.getLevelsMap().getVal(_voisin.getLevel().getLevelIndex());
        if (level_.getCharacters().contains(_voisin.getLevel().getPoint())) {
            CharacterInRoadCave char_ = level_.getCharacters().getVal(_voisin.getLevel().getPoint());
            if (char_ instanceof Trainer) {
                interfaceType=InterfaceType.DRESSEUR;
                return;
            }
            //char_ instanceof DealerItem
            if (!takenObjects.getVal(_voisin)) {
                interfaceType=InterfaceType.DON_OBJET;
                return;
            }
            interfaceType=InterfaceType.RIEN;
            return;
        }
        for (PointParam<DualFight> e: level_.getDualFights().entryList()) {
            DualFight dual_ = e.getValue();
            if (!Point.eq(e.getKey(), pt_)) {
                if (!Point.eq(dual_.getPt(), pt_)) {
                    continue;
                }
            }
            Coords coords_ = new Coords(_voisin);
            coords_.getLevel().getPoint().affect(e.getKey());
            if (!beatGymLeader.getVal(coords_)) {
                interfaceType=InterfaceType.DRESSEUR;
                return;
            }
            break;
        }
        if (level_.getTm().contains(_voisin.getLevel().getPoint())) {
            if (takenObjects.getVal(_voisin)) {
                interfaceType=InterfaceType.RIEN;
            } else {
                interfaceType=InterfaceType.OBJ_RAMAS;
            }
            return;
        }
        if (level_.getHm().contains(_voisin.getLevel().getPoint())) {
            if (takenObjects.getVal(_voisin)) {
                interfaceType=InterfaceType.RIEN;
            } else {
                interfaceType=InterfaceType.OBJ_RAMAS;
            }
            return;
        }
        if (level_.getItems().contains(_voisin.getLevel().getPoint())) {
            if (takenObjects.getVal(_voisin)) {
                interfaceType=InterfaceType.RIEN;
            } else {
                interfaceType=InterfaceType.OBJ_RAMAS;
            }
            return;
        }
        if (level_.containsPokemon(_voisin.getLevel().getPoint())) {
            WildPk pk_ = level_.getPokemon(_voisin.getLevel().getPoint());
            if (!player.estAttrape(pk_.getName())) {
                interfaceType=InterfaceType.PK_LEG;
            } else {
                interfaceType=InterfaceType.RIEN;
            }
            return;
        }
        Block bl_ = level_.getBlockByPoint(_voisin.getLevel().getPoint());
        if (bl_.isValid() && bl_.getType() == EnvironmentType.WATER) {
            interfaceType=InterfaceType.PECHE;
            return;
        }
        interfaceType=InterfaceType.RIEN;
    }

    public void addMessageGymLeader(DataBase _import) {
        commentGame.clearMessages();
        StringMap<String> mess_ = _import.getMessagesGame();
        commentGame.addMessage(mess_.getVal(REMAINING_TRAINERS_GYM));
    }

    public void clearMessages() {
        commentGame.clearMessages();
    }

    public void newIndex(boolean _walking, int _index, AreaApparition _area, DataBase _d) {
        //CustList<WildPokemon> _pokemon,
//        int nb_=_pokemon.size();
        int nb_= _area.getPokemonListLength(_walking);
        //nb_ > 0
        int tmpIndexPeriod_ = _index;
        if (tmpIndexPeriod_ >= nb_) {
            tmpIndexPeriod_ = nb_ - 1;
        }
        //tmpIndexPeriod_ >= 0
        int i_ = tmpIndexPeriod_;
        //i_ >= 0
        for (int i = IndexConstants.FIRST_INDEX; i < nb_; i++) {
//            WildPokemon pk_ = _pokemon.get((i + i_) % nb_);
            WildPk pk_ = _area.getWildPokemon((i + i_) % nb_, _walking);
            if (player.estAttrape(pk_.getName())) {
                PokemonData fPk_=_d.getPokedex().getVal(pk_.getName());
                if (fPk_.getGenderRep() == GenderRepartition.LEGENDARY) {
                    continue;
                }
            }
            FightFacade.initFight(fight,player,difficulty,pk_,_d);
            FightFacade.initTypeEnv(fight,playerCoords,difficulty,_d);
            commentGame.addComment(fight.getComment());
            interfaceType = InterfaceType.COMBAT_PK_SAUV;
            break;
        }
        if (_walking) {
            indexPeriod = (i_ + 1) % nb_;
        } else {
            indexPeriodFishing = (i_ + 1) % nb_;
        }
    }

    void newRandomPokemon(MonteCarloList<WildPk> _law, DataBase _d) {
        MonteCarloList<WildPk> lawCopy_ = lawCopy(_law, _d);
        LgInt maxRd_ = _d.getMaxRd();
        WildPk pkAlea_=lawCopy_.editNumber(maxRd_,_d.getGenerator());
        if(pkAlea_.hasJustBeenCreated()){
            return;
        }
        FightFacade.initFight(fight,player,difficulty, pkAlea_, _d);
        FightFacade.initTypeEnv(fight,playerCoords,difficulty,_d);
        commentGame.addComment(fight.getComment());
        interfaceType=InterfaceType.COMBAT_PK_SAUV;
    }

    MonteCarloList<WildPk> lawCopy(MonteCarloList<WildPk> _law, DataBase _d) {
        MonteCarloList<WildPk> lawCopy_ = new MonteCarloList<WildPk>();
        for(EventFreq<WildPk> i:_law.getEvents()){
            WildPk e = i.getEvent();
            LgInt f_ = i.getFreq();
            if(e.getName().isEmpty()){
                lawCopy_.addQuickEvent(e,f_);
                continue;
            }
            if(player.estAttrape(e.getName())){
                PokemonData fPk_=_d.getPokedex().getVal(e.getName());
                if(fPk_.getGenderRep() == GenderRepartition.LEGENDARY){
                    lawCopy_.addQuickEvent(new WildPk(),f_);
                    continue;
                }
            }
            lawCopy_.addQuickEvent(e,f_);
        }
        return lawCopy_;
    }

    public boolean isEmpty(DataMap _map, Coords _coords) {
        Place place_ = _map.getPlace(_coords.getNumberPlace());
        Level level_ = place_.getLevelByCoords(_coords);
        Point pt_ = _coords.getLevel().getPoint();
        if (!(level_ instanceof LevelWithWildPokemon)) {
            return level_.isEmpty(pt_);
        }
        LevelWithWildPokemon levelWildPk_ = (LevelWithWildPokemon) level_;
        if (levelWildPk_.containsPokemon(pt_)) {
            WildPk pk_ = levelWildPk_.getPokemon(pt_);
            return player.estAttrape(pk_.getName());
        }
        if (levelWildPk_.getItems().contains(pt_)) {
            return takenObjects.getVal(_coords);
        }
        if (levelWildPk_.getTm().contains(pt_)) {
            return takenObjects.getVal(_coords);
        }
        if (levelWildPk_.getHm().contains(pt_)) {
            return takenObjects.getVal(_coords);
        }
        for (PointParam<DualFight> e: levelWildPk_.getDualFights().entryList()) {
            if (Point.eq(e.getKey(), pt_)) {
                return beatGymLeader.getVal(_coords);
            }
            if (Point.eq(e.getValue().getPt(), pt_)) {
                Coords coords_ = new Coords(_coords);
                coords_.getLevel().setPoint(e.getKey());
                return beatGymLeader.getVal(coords_);
            }
        }
        return level_.isEmpty(pt_);
    }

    public void initIv(DataBase _data) {
        commentGame = new Comment();
        reinitInteraction = false;
        nbSteps = 0;
        placeChanged = false;
        showEndGame = false;
        getPlayer().initIv(getDifficulty(), _data);
        commentGame.addComment(getPlayer().getCommentGame());
        for (HostPokemonDuo h: hostedPk.values()) {
            if (h.isFree()) {
                continue;
            }
            h.getFirstPokemon().initIv(getDifficulty());
            h.getFirstPokemon().initHp(_data);
            h.getSecondPokemon().initIv(getDifficulty());
            h.getSecondPokemon().initHp(_data);
        }
    }

    public boolean checkAndInitialize(DataBase _data) {
        initIv(_data);
        FightFacade.initializeFromSavedGame(fight, difficulty, player, _data);
        if (zippedRom == null) {
            zippedRom = DataBase.EMPTY_STRING;
        }
        if (!validate(_data)) {
            return false;
        }
        DataMap d_ = _data.getMap();
        interfaceType = InterfaceType.RIEN;
        directInteraction(d_);
        showEndGame = false;
        if (!getFight().getFightType().isExisting()) {
            if (endGame(_data)) {
                showEndGame = true;
            }
        }
        return true;
    }

    public Coords closestTile(DataMap _map) {
        return _map.closestTile(playerCoords, playerOrientation);
    }

    public void getItem(String _item) {
        player.getItem(_item);
    }

    public CustList<UsablePokemon> getTeam() {
        return player.getTeam();
    }

    public void doRevivingFossil(String _fossilName,DataBase _import){
        player.doRevivingFossil(_fossilName, difficulty, _import);
    }
    public EqList<Coords> getBeatenGymLeader() {
        EqList<Coords> k_ = new EqList<Coords>();
        for (EntryCust<Coords, Boolean> e: beatGymLeader.entryList()) {
            if (e.getValue()) {
                k_.add(e.getKey());
            }
        }
        return k_;
    }

    public EqList<Coords> getUnBeatenGymLeader() {
        EqList<Coords> k_ = new EqList<Coords>();
        for (EntryCust<Coords, Boolean> e: beatGymLeader.entryList()) {
            if (!e.getValue()) {
                k_.add(e.getKey());
            }
        }
        return k_;
    }

    public void beatGymLeader(Coords _coords) {
        beatGymLeader.set(_coords, true);
    }

    public void beatTrainer(NbFightCoords _coords) {
        beatTrainer.set(_coords, true);
    }

    void takenObjects(Coords _coords) {
        takenObjects.set(_coords, true);
    }

    void takenPokemon(Coords _coords) {
        takenPokemon.set(_coords, true);
    }

    public EqList<Coords> getVisited() {
        EqList<Coords> k_ = new EqList<Coords>();
        for (EntryCust<Coords, Boolean> e: visitedPlaces.entryList()) {
            if (e.getValue()) {
                k_.add(e.getKey());
            }
        }
        return k_;
    }

    public EqList<Coords> getUnVisited() {
        EqList<Coords> k_ = new EqList<Coords>();
        for (EntryCust<Coords, Boolean> e: visitedPlaces.entryList()) {
            if (!e.getValue()) {
                k_.add(e.getKey());
            }
        }
        return k_;
    }

    void visitPlace(Coords _coords) {
        visitedPlaces.set(_coords, true);
        visitedPlacesNb.set(_coords.getNumberPlace(), true);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player _player) {
        player = _player;
    }

    public byte getRankLeague() {
        return rankLeague;
    }

    public void setRankLeague(byte _rankLeague) {
        rankLeague = _rankLeague;
    }

    public ObjectMap<Coords,Boolean> getBeatGymLeader() {
        return beatGymLeader;
    }

    public void setBeatGymLeader(ObjectMap<Coords,Boolean> _beatGymLeader) {
        beatGymLeader = _beatGymLeader;
    }

    public ObjectMap<Coords,Boolean> getTakenObjects() {
        return takenObjects;
    }

    public void setTakenObjects(ObjectMap<Coords,Boolean> _takenObjects) {
        takenObjects = _takenObjects;
    }

    public ObjectMap<Coords,Boolean> getTakenPokemon() {
        return takenPokemon;
    }

    public void setTakenPokemon(ObjectMap<Coords,Boolean> _takenPokemon) {
        takenPokemon = _takenPokemon;
    }

    public Coords getPlayerCoords() {
        return playerCoords;
    }

    public void setPlayerCoords(Coords _playerCoords) {
        playerCoords = _playerCoords;
    }

    public ObjectMap<Coords,HostPokemonDuo> getHostedPk() {
        return hostedPk;
    }

    public void setHostedPk(ObjectMap<Coords,HostPokemonDuo> _hostedPk) {
        hostedPk = _hostedPk;
    }

    public Fight getFight() {
        return fight;
    }

    public void setFight(Fight _fight) {
        fight = _fight;
    }

    public Difficulty getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(Difficulty _difficulty) {
        difficulty = _difficulty;
    }

    public int getIndexPeriodFishing() {
        return indexPeriodFishing;
    }

    public void setIndexPeriodFishing(int _indexPeriodFishing) {
        indexPeriodFishing = _indexPeriodFishing;
    }

    public int getIndexPeriod() {
        return indexPeriod;
    }

    public void setIndexPeriod(int _indexPeriod) {
        indexPeriod = _indexPeriod;
    }

    public int getIndexStep() {
        return indexStep;
    }

    public void setIndexStep(int _indexStep) {
        indexStep = _indexStep;
    }

    public Comment getCommentGame() {
        return commentGame;
    }

    public boolean isReinitInteraction() {
        return reinitInteraction;
    }

    public ObjectMap<Coords,Boolean> getVisitedPlaces() {
        return visitedPlaces;
    }

    public void setVisitedPlaces(ObjectMap<Coords,Boolean> _visitedPlaces) {
        visitedPlaces = _visitedPlaces;
    }

    public ShortMap<Boolean> getVisitedPlacesNb() {
        return visitedPlacesNb;
    }

    public void setVisitedPlacesNb(ShortMap<Boolean> _visitedPlacesNb) {
        visitedPlacesNb = _visitedPlacesNb;
    }

    public int getNbSteps() {
        return nbSteps;
    }

    public boolean isPlaceChanged() {
        return placeChanged;
    }

    public InterfaceType getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(InterfaceType _interfaceType) {
        interfaceType = _interfaceType;
    }

    public void setBeatGymTrainer(ShortMap<EqList<Point>> _beatGymTrainer) {
        beatGymTrainer = _beatGymTrainer;
    }

    public void setBeatTrainer(ObjectMap<NbFightCoords,Boolean> _beatTrainer) {
        beatTrainer = _beatTrainer;
    }

    public void setPlayerOrientation(Direction _playerOrientation) {
        playerOrientation = _playerOrientation;
    }

    public ObjectMap<NbFightCoords,Boolean> getBeatTrainer() {
        return beatTrainer;
    }

    StringList getPartiallyAccessiblePlaces() {
        return partiallyAccessiblePlaces;
    }

    StringList getFullAccessiblePlaces() {
        return fullAccessiblePlaces;
    }

    public ShortMap<EqList<Point>> getBeatGymTrainer() {
        return beatGymTrainer;
    }

    public Direction getPlayerOrientation() {
        return playerOrientation;
    }

    public boolean isShowEndGame() {
        return showEndGame;
    }

    public String getZippedRom() {
        return zippedRom;
    }

    public void setZippedRom(String _zippedRom) {
        zippedRom = _zippedRom;
    }
}
