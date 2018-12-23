package aiki.game.fight;
import static aiki.db.EquallablePkUtil.assertEq;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import aiki.fight.enums.Statistic;
import aiki.game.params.Difficulty;
import aiki.game.player.Player;
import aiki.map.characters.GymLeader;
import aiki.map.levels.enums.EnvironmentType;
import aiki.map.pokemon.PkTrainer;
import aiki.map.pokemon.Pokemon;
import aiki.map.pokemon.PokemonPlayer;
import aiki.map.pokemon.WildPk;
import aiki.map.pokemon.enums.Gender;
import code.maths.Rate;
import code.util.CustList;
import code.util.StringList;
import code.util.StringMap;

@SuppressWarnings("static-method")
public class FightAbilitiesTest extends InitializationDataBase {

    private static final String PIKA = "PIKA";

    @Test
    public void disableAllStatusByEnabledWeather1Test() {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,true,_data_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(PIKACHU);
        foePokemon_.setItem(MAGNET);
        foePokemon_.setAbility(ATTENTION);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight((byte) 1);
        Fight fight_ = FightFacade.newFight();
        FightInitialization.initFight(fight_, player_, diff_, trainer_, _data_);
        FightInitialization.initFight(fight_, _data_);
        Fighter fighter_ = fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO);
        fighter_.setCurrentAbility(NULL_REF);
        fighter_.affecterStatut(PEUR);
        FightAbilities.disableAllStatusByEnabledWeather(fight_, POKEMON_FOE_FIGHTER_ZERO, NULL_REF, _data_);
        assertEq(1, fighter_.getStatusNbRound(PEUR).intValue());
    }

    @Test
    public void disableAllStatusByEnabledWeather2Test() {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,true,_data_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(PIKACHU);
        foePokemon_.setItem(MAGNET);
        foePokemon_.setAbility(GARDE_MAGIK);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight((byte) 1);
        Fight fight_ = FightFacade.newFight();
        FightInitialization.initFight(fight_, player_, diff_, trainer_, _data_);
        FightInitialization.initFight(fight_, _data_);
        Fighter fighter_ = fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO);
        fighter_.setCurrentAbility(NULL_REF);
        fighter_.affecterPseudoStatut(POKEMON_PLAYER_FIGHTER_ZERO, VAMPIGRAINE);
        FightAbilities.disableAllStatusByEnabledWeather(fight_, POKEMON_FOE_FIGHTER_ZERO, NULL_REF, _data_);
        assertEq(1, fighter_.getStatusRelatNbRound(new MoveTeamPosition(VAMPIGRAINE, POKEMON_PLAYER_FIGHTER_ZERO)).intValue());
    }

    @Test
    public void disableAllStatusByEnabledWeather3Test() {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,true,_data_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(PIKACHU);
        foePokemon_.setItem(MAGNET);
        foePokemon_.setAbility(FEUILLE_GARDE);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight((byte) 1);
        Fight fight_ = FightFacade.newFight();
        FightInitialization.initFight(fight_, player_, diff_, trainer_, _data_);
        FightInitialization.initFight(fight_, _data_);
        Fighter fighter_ = fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO);
        fighter_.affecterStatut(PEUR);
        FightAbilities.disableAllStatusByEnabledWeather(fight_, POKEMON_FOE_FIGHTER_ZERO, ORAGE, _data_);
        assertEq(1, fighter_.getStatusNbRound(PEUR).intValue());
    }

    @Test
    public void disableAllStatusByEnabledWeather4Test() {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,true,_data_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(PIKACHU);
        foePokemon_.setItem(MAGNET);
        foePokemon_.setAbility(ATTENTION);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight((byte) 1);
        Fight fight_ = FightFacade.newFight();
        FightInitialization.initFight(fight_, player_, diff_, trainer_, _data_);
        FightInitialization.initFight(fight_, _data_);
        Fighter fighter_ = fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO);
        fighter_.affecterStatut(PEUR);
        FightAbilities.disableAllStatusByEnabledWeather(fight_, POKEMON_FOE_FIGHTER_ZERO, NULL_REF, _data_);
        assertEq(0, fighter_.getStatusNbRound(PEUR).intValue());
    }

    @Test
    public void disableAllStatusByEnabledWeather5Test() {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,true,_data_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(PIKACHU);
        foePokemon_.setItem(MAGNET);
        foePokemon_.setAbility(FEUILLE_PETITE);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight((byte) 1);
        Fight fight_ = FightFacade.newFight();
        FightInitialization.initFight(fight_, player_, diff_, trainer_, _data_);
        FightInitialization.initFight(fight_, _data_);
        Fighter fighter_ = fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO);
        fighter_.affecterPseudoStatut(POKEMON_PLAYER_FIGHTER_ZERO, VAMPIGRAINE);
        FightAbilities.disableAllStatusByEnabledWeather(fight_, POKEMON_FOE_FIGHTER_ZERO, ZENITH, _data_);
        assertEq(0, fighter_.getStatusRelatNbRound(new MoveTeamPosition(VAMPIGRAINE, POKEMON_PLAYER_FIGHTER_ZERO)).intValue());
    }

    @Test
    public void enableAbilityByWeather1Test() {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,true,_data_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(PIKACHU);
        foePokemon_.setItem(MAGNET);
        foePokemon_.setAbility(ATTENTION);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight((byte) 2);
        Fight fight_ = FightFacade.newFight();
        FightInitialization.initFight(fight_, player_, diff_, trainer_, _data_);
        FightInitialization.initFight(fight_, _data_);
        fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).affecterStatut(PEUR);
        fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).setCurrentAbility(NULL_REF);
        FightAbilities.enableAbilityByWeather(fight_,POKEMON_FOE_FIGHTER_ZERO, _data_);
        assertEq(1, fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).getStatusNbRound(PEUR).intValue());
    }

    @Test
    public void enableAbilityByWeather2Test() {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,true,_data_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(PIKACHU);
        foePokemon_.setItem(MAGNET);
        foePokemon_.setAbility(ATTENTION);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight((byte) 2);
        Fight fight_ = FightFacade.newFight();
        FightInitialization.initFight(fight_, player_, diff_, trainer_, _data_);
        FightInitialization.initFight(fight_, _data_);
        fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).affecterStatut(PEUR);
        FightAbilities.enableAbilityByWeather(fight_,POKEMON_FOE_FIGHTER_ZERO, _data_);
        assertEq(0, fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).getStatusNbRound(PEUR).intValue());
    }

    @Test
    public void enableAbilityByWeather3Test() {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,true,_data_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(PIKACHU);
        foePokemon_.setItem(MAGNET);
        foePokemon_.setAbility(METEO);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight((byte) 2);
        Fight fight_ = FightFacade.newFight();
        FightInitialization.initFight(fight_, player_, diff_, trainer_, _data_);
        FightInitialization.initFight(fight_, _data_);
        fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).setCurrentAbility(NULL_REF);
        fight_.enableGlobalMove(ZENITH);
        FightAbilities.enableAbilityByWeather(fight_,POKEMON_FOE_FIGHTER_ZERO, _data_);
        StringList list_ = fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).getTypes();
        assertEq(1, list_.size());
        assertTrue(list_.containsObj(ELECTRIQUE));
    }

    @Test
    public void enableAbilityByWeather4Test() {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,true,_data_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(PIKACHU);
        foePokemon_.setItem(MAGNET);
        foePokemon_.setAbility(METEO);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight((byte) 2);
        Fight fight_ = FightFacade.newFight();
        FightInitialization.initFight(fight_, player_, diff_, trainer_, _data_);
        FightInitialization.initFight(fight_, _data_);
        fight_.enableGlobalMove(ZENITH);
        FightAbilities.enableAbilityByWeather(fight_,POKEMON_FOE_FIGHTER_ZERO, _data_);
        StringList list_ = fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).getTypes();
        assertEq(1, list_.size());
        assertTrue(list_.containsObj(FEU));
    }

    @Test
    public void enableAbilityByWeather5Test() {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,true,_data_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(PIKACHU);
        foePokemon_.setItem(MAGNET);
        foePokemon_.setAbility(METEO);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight((byte) 2);
        Fight fight_ = FightFacade.newFight();
        FightInitialization.initFight(fight_, player_, diff_, trainer_, _data_);
        FightInitialization.initFight(fight_, _data_);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).setAbility(AIR_LOCK);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).setCurrentAbility(AIR_LOCK);
        fight_.enableGlobalMove(ZENITH);
        FightAbilities.enableAbilityByWeather(fight_,POKEMON_FOE_FIGHTER_ZERO, _data_);
        StringList list_ = fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).getTypes();
        assertEq(1, list_.size());
        assertTrue(list_.containsObj(ELECTRIQUE));
    }

    private static Fight ignoreTargetAbility() {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,false,_data_);
        Pokemon pokemon_ = new WildPk();
        pokemon_.setName(YANMA);
        pokemon_.setItem(NULL_REF);
        pokemon_.setAbility(METEO);
        pokemon_.setGender(Gender.NO_GENDER);
        pokemon_.setLevel((short) 32);
        StringMap<Short> moves_ = new StringMap<Short>();
        moves_.put(A_LA_QUEUE, (short) 10);
        moves_.put(APRES_VOUS, (short) 10);
        moves_.put(SEISME, (short) 10);
        moves_.put(BROUHAHA, (short) 10);
        PokemonPlayer pokemonUser_ = new PokemonPlayer(pokemon_, _data_, moves_);
        pokemonUser_.initIv(new Difficulty());
        pokemonUser_.setNickname(PIKA);
        pokemonUser_.setUsedBallCatching(SUPER_BALL);
        pokemonUser_.setHappiness((short) 140);
        pokemonUser_.setWonExpSinceLastLevel(new Rate("3167"));
        player_.getTeam().add(pokemonUser_);
        pokemonUser_ = new PokemonPlayer(pokemon_, _data_, moves_);
        pokemonUser_.initIv(new Difficulty());
        pokemonUser_.setNickname(PIKA);
        pokemonUser_.setUsedBallCatching(SUPER_BALL);
        pokemonUser_.setHappiness((short) 140);
        pokemonUser_.setWonExpSinceLastLevel(new Rate("3167"));
        player_.getTeam().add(pokemonUser_);
        pokemonUser_ = new PokemonPlayer(pokemon_, _data_, moves_);
        pokemonUser_.initIv(new Difficulty());
        pokemonUser_.setNickname(PIKA);
        pokemonUser_.setUsedBallCatching(SUPER_BALL);
        pokemonUser_.setHappiness((short) 140);
        pokemonUser_.setWonExpSinceLastLevel(new Rate("3167"));
        player_.getTeam().add(pokemonUser_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(TARTARD);
        foePokemon_.setItem(PLAQUE_DRACO);
        foePokemon_.setAbility(MULTITYPE);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT,PREVENTION,RELAIS));
        foeTeam_.add(foePokemon_);
        foePokemon_ = new PkTrainer();
        foePokemon_.setName(TARTARD);
        foePokemon_.setItem(PLAQUE_DRACO);
        foePokemon_.setAbility(MULTITYPE);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT,PREVENTION,RELAIS));
        foeTeam_.add(foePokemon_);
        foePokemon_ = new PkTrainer();
        foePokemon_.setName(TARTARD);
        foePokemon_.setItem(PLAQUE_DRACO);
        foePokemon_.setAbility(MULTITYPE);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT,PREVENTION,RELAIS));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight((byte) 2);
        Fight fight_ = FightFacade.newFight();
        FightFacade.initFight(fight_,player_, diff_, trainer_, _data_);
        fight_.setEnvType(EnvironmentType.ROAD);
        return fight_;
    }

    @Test
    public void ignoreTargetAbility1Test() {
        Fight fight_ = ignoreTargetAbility();
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).setCurrentAbility(METEO);
        fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).setCurrentAbility(METEO);
        assertTrue(!FightAbilities.ignoreTargetAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, POKEMON_FOE_FIGHTER_ZERO, _data_));
        fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).setCurrentAbility(NULL_REF);
        assertTrue(FightAbilities.ignoreTargetAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, POKEMON_FOE_FIGHTER_ZERO, _data_));
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).setCurrentAbility(NULL_REF);
        fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).setCurrentAbility(METEO);
        assertTrue(!FightAbilities.ignoreTargetAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, POKEMON_FOE_FIGHTER_ZERO, _data_));
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).setCurrentAbility(TERA_VOLTAGE);
        fight_.getFighter(POKEMON_FOE_FIGHTER_ZERO).setCurrentAbility(ATTENTION);
        assertTrue(FightAbilities.ignoreTargetAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, POKEMON_FOE_FIGHTER_ZERO, _data_));
    }

    private static Fight enableAbility(String _firstAbility,String _secondAbility, byte _mult) {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,false,_data_);
        Pokemon pokemon_ = new WildPk();
        pokemon_.setName(ARTIKODIN);
        pokemon_.setItem(PLAQUE_DRACO);
        pokemon_.setAbility(_firstAbility);
        pokemon_.setGender(Gender.NO_GENDER);
        pokemon_.setLevel((short) 3);
        PokemonPlayer lasPk_ = new PokemonPlayer(pokemon_,_data_);
        lasPk_.initIv(diff_);
        lasPk_.initPvRestants(_data_);
        player_.getTeam().add(lasPk_);
        pokemon_ = new WildPk();
        pokemon_.setName(ARTIKODIN);
        pokemon_.setItem(PLAQUE_DRACO);
        pokemon_.setAbility(_secondAbility);
        pokemon_.setGender(Gender.NO_GENDER);
        pokemon_.setLevel((short) 3);
        lasPk_ = new PokemonPlayer(pokemon_,_data_);
        lasPk_.initIv(diff_);
        lasPk_.initPvRestants(_data_);
        player_.getTeam().add(lasPk_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(TARTARD);
        foePokemon_.setItem(PLAQUE_DRACO);
        foePokemon_.setAbility(MULTITYPE);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        foePokemon_ = new PkTrainer();
        foePokemon_.setName(TARTARD);
        foePokemon_.setItem(MAGNET);
        foePokemon_.setAbility(SECHERESSE);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 4);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight(_mult);
        Fight fight_ = FightFacade.newFight();
        FightFacade.initFight(fight_,player_, diff_, trainer_, _data_);
        return fight_;
    }

    @Test
    public void enableAbility1Test() {
        Fight fight_ = enableAbility(HYPER_CUTTER, MULTITYPE, (byte) 1);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).variationBoostStatistique(Statistic.ATTACK, (byte) -2);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        assertEq(0, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatisBoost().getVal(Statistic.ATTACK).intValue());
    }

    @Test
    public void enableAbility2Test() {
        Fight fight_ = enableAbility(PIED_VELOCE, MULTITYPE, (byte) 1);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).affecterStatut(PARALYSIE);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).variationBoostStatistique(Statistic.SPEED, (byte) -1);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        assertEq(0, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatisBoost().getVal(Statistic.SPEED).intValue());
        assertEq(1, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatusNbRound(PARALYSIE).intValue());
    }

    @Test
    public void enableAbility3Test() {
        Fight fight_ = enableAbility(MATINAL, MULTITYPE, (byte) 1);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).affecterStatut(PARALYSIE);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).incrementRoundsStatus(PARALYSIE);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        assertEq(0, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatusNbRound(PARALYSIE).intValue());
    }

    @Test
    public void enableAbility4Test() {
        Fight fight_ = enableAbility(FEUILLE_GARDE, SECHERESSE, (byte) 2);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).affecterStatut(PEUR);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        assertEq(0, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatusNbRound(PEUR).intValue());
    }

    @Test
    public void enableAbility5Test() {
        Fight fight_ = enableAbility(METEO, SECHERESSE, (byte) 2);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        StringList list_ = fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getTypes();
        assertEq(1, list_.size());
        assertTrue(list_.containsObj(FEU));
    }

    @Test
    public void enableAbility6Test() {
        Fight fight_ = enableAbility(MATINAL, MULTITYPE, (byte) 1);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).affecterStatut(PARALYSIE);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        assertEq(1, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatusNbRound(PARALYSIE).intValue());
    }

    @Test
    public void enableAbility7Test() {
        Fight fight_ = enableAbility(PIED_VELOCE, MULTITYPE, (byte) 1);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).affecterStatut(PARALYSIE);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).variationBoostStatistique(Statistic.SPEED, (byte) 2);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        assertEq(2, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatisBoost().getVal(Statistic.SPEED).intValue());
    }

    @Test
    public void enableAbility8Test() {
        Fight fight_ = enableAbility(PIED_VELOCE, MULTITYPE, (byte) 1);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).supprimerStatut(PARALYSIE);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).variationBoostStatistique(Statistic.SPEED, (byte) -1);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        assertEq(-1, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatisBoost().getVal(Statistic.SPEED).intValue());
    }

    @Test
    public void enableAbility9Test() {
        Fight fight_ = enableAbility(PIED_VELOCE, MULTITYPE, (byte) 1);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).variationBoostStatistique(Statistic.SPEED, (byte) -1);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).affecterStatut(BRULURE);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        assertEq(0, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatisBoost().getVal(Statistic.ATTACK).intValue());
        assertEq(0, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatisBoost().getVal(Statistic.SPEED).intValue());
    }

    @Test
    public void enableAbility10Test() {
        Fight fight_ = enableAbility(PIED_VELOCE, MULTITYPE, (byte) 1);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).setCurrentAbility(NULL_REF);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).variationBoostStatistique(Statistic.SPEED, (byte) -1);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).affecterStatut(BRULURE);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        assertEq(0, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatisBoost().getVal(Statistic.ATTACK).intValue());
        assertEq(-1, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatisBoost().getVal(Statistic.SPEED).intValue());
    }

    @Test
    public void enableAbility11Test() {
        Fight fight_ = enableAbility(HYPER_CUTTER, MULTITYPE, (byte) 1);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).variationBoostStatistique(Statistic.ATTACK, (byte) 2);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        assertEq(2, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatisBoost().getVal(Statistic.ATTACK).intValue());
    }

    @Test
    public void enableAbility12Test() {
        Fight fight_ = enableAbility(ATTENTION, MULTITYPE, (byte) 1);
        StringList typesThrower_ = fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getTypes();
        assertEq(1, typesThrower_.size());
        assertTrue(typesThrower_.containsObj(ELECTRIQUE));
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).setCurrentAbility(MULTITYPE);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        typesThrower_ = fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getTypes();
        assertEq(1, typesThrower_.size());
        assertTrue(typesThrower_.containsObj(DRAGON));
    }

    @Test
    public void enableAbility13Test() {
        Fight fight_ = enableAbility(MATINAL, MULTITYPE, (byte) 1);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).affecterStatut(POISON_GRAVE);
        FightAbilities.enableAbility(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        assertEq(1, fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getStatusNbRound(POISON_GRAVE).intValue());
    }

    private static Fight disableAbility(String _firstAbility,String _secondAbility, byte _mult) {
        Difficulty diff_= new Difficulty();
        Player player_ = new Player(NICKNAME,null,diff_,false,_data_);
        Pokemon pokemon_ = new WildPk();
        pokemon_.setName(ARTIKODIN);
        pokemon_.setItem(PLAQUE_DRACO);
        pokemon_.setAbility(_firstAbility);
        pokemon_.setGender(Gender.NO_GENDER);
        pokemon_.setLevel((short) 3);
        PokemonPlayer lasPk_ = new PokemonPlayer(pokemon_,_data_);
        lasPk_.initIv(diff_);
        lasPk_.initPvRestants(_data_);
        player_.getTeam().add(lasPk_);
        pokemon_ = new WildPk();
        pokemon_.setName(ARTIKODIN);
        pokemon_.setItem(PLAQUE_DRACO);
        pokemon_.setAbility(_secondAbility);
        pokemon_.setGender(Gender.NO_GENDER);
        pokemon_.setLevel((short) 3);
        lasPk_ = new PokemonPlayer(pokemon_,_data_);
        lasPk_.initIv(diff_);
        lasPk_.initPvRestants(_data_);
        player_.getTeam().add(lasPk_);
        CustList<PkTrainer> foeTeam_ = new CustList<PkTrainer>();
        PkTrainer foePokemon_ = new PkTrainer();
        foePokemon_.setName(TARTARD);
        foePokemon_.setItem(PLAQUE_DRACO);
        foePokemon_.setAbility(MULTITYPE);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 3);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        foePokemon_ = new PkTrainer();
        foePokemon_.setName(TARTARD);
        foePokemon_.setItem(MAGNET);
        foePokemon_.setAbility(SECHERESSE);
        foePokemon_.setGender(Gender.NO_GENDER);
        foePokemon_.setLevel((short) 4);
        foePokemon_.setMoves(new StringList(JACKPOT));
        foeTeam_.add(foePokemon_);
        GymLeader trainer_ = new GymLeader();
        trainer_.setTeam(foeTeam_);
        trainer_.setReward((short) 200);
        trainer_.setMultiplicityFight(_mult);
        Fight fight_ = FightFacade.newFight();
        FightFacade.initFight(fight_,player_, diff_, trainer_, _data_);
        return fight_;
    }

    @Test
    public void disableAbility1Test() {
        Fight fight_ = disableAbility(ATTENTION, ATTENTION, (byte)2);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).setCurrentAbility(AIR_LOCK);
        assertTrue(FightMoves.existenceAntiClimatActif(fight_, _data_));
        String ability_ = fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getAbility();
        FightAbilities.disableAbility(fight_, POKEMON_PLAYER_FIGHTER_ZERO, ability_, _data_);
        assertTrue(!FightMoves.existenceAntiClimatActif(fight_, _data_));
        assertEq(ATTENTION,fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getCurrentAbility());
    }

    @Test
    public void disableAbility2Test() {
        Fight fight_ = disableAbility(AIR_LOCK, ATTENTION, (byte)2);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).setCurrentAbility(ATTENTION);
        fight_.enableGlobalMove(ZENITH);
        assertTrue(fight_.getEnabledMoves().getVal(ZENITH).isEnabled());
        assertTrue(FightMoves.climatsActifs(fight_, _data_).containsObj(ZENITH));
        assertTrue(!FightMoves.existenceAntiClimatActif(fight_, _data_));
        assertTrue(fight_.getStillEnabledMoves().getVal(ZENITH));
        String ability_ = fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getAbility();
        FightAbilities.disableAbility(fight_, POKEMON_PLAYER_FIGHTER_ZERO, ability_, _data_);
        assertTrue(fight_.getEnabledMoves().getVal(ZENITH).isEnabled());
        assertTrue(!FightMoves.climatsActifs(fight_, _data_).containsObj(ZENITH));
        assertTrue(FightMoves.existenceAntiClimatActif(fight_, _data_));
        assertTrue(fight_.getStillEnabledMoves().getVal(ZENITH));
        assertEq(AIR_LOCK,fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getCurrentAbility());
    }

    @Test
    public void disableAbility3Test() {
        Fight fight_ = disableAbility(AIR_LOCK, ATTENTION, (byte)2);
        assertTrue(fight_.getEnabledMoves().getVal(ZENITH).isEnabled());
        assertTrue(!FightMoves.climatsActifs(fight_, _data_).containsObj(ZENITH));
        assertTrue(FightMoves.existenceAntiClimatActif(fight_, _data_));
        assertTrue(fight_.getStillEnabledMoves().getVal(ZENITH));
        FightAbilities.disableAbility(fight_, POKEMON_PLAYER_FIGHTER_ZERO, NULL_REF, _data_);
        assertTrue(fight_.getEnabledMoves().getVal(ZENITH).isEnabled());
        assertTrue(FightMoves.climatsActifs(fight_, _data_).containsObj(ZENITH));
        assertTrue(!FightMoves.existenceAntiClimatActif(fight_, _data_));
        assertTrue(fight_.getStillEnabledMoves().getVal(ZENITH));
    }

    @Test
    public void disableAbility4Test() {
        Fight fight_ = disableAbility(AIR_LOCK, AIR_LOCK, (byte)2);
        assertTrue(fight_.getEnabledMoves().getVal(ZENITH).isEnabled());
        assertTrue(!FightMoves.climatsActifs(fight_, _data_).containsObj(ZENITH));
        assertTrue(FightMoves.existenceAntiClimatActif(fight_, _data_));
        assertTrue(fight_.getStillEnabledMoves().getVal(ZENITH));
        FightAbilities.disableAbility(fight_, POKEMON_PLAYER_FIGHTER_ZERO, NULL_REF, _data_);
        assertTrue(fight_.getEnabledMoves().getVal(ZENITH).isEnabled());
        assertTrue(!FightMoves.climatsActifs(fight_, _data_).containsObj(ZENITH));
        assertTrue(FightMoves.existenceAntiClimatActif(fight_, _data_));
        assertTrue(fight_.getStillEnabledMoves().getVal(ZENITH));
    }

    @Test
    public void disableAbility5Test() {
        Fight fight_ = disableAbility(TELECHARGE, AIR_LOCK, (byte)2);
        assertTrue(fight_.getEnabledMoves().getVal(ZENITH).isEnabled());
        assertTrue(!FightMoves.climatsActifs(fight_, _data_).containsObj(ZENITH));
        assertTrue(FightMoves.existenceAntiClimatActif(fight_, _data_));
        assertTrue(fight_.getStillEnabledMoves().getVal(ZENITH));
        FightAbilities.disableAbility(fight_, POKEMON_PLAYER_FIGHTER_ZERO, NULL_REF, _data_);
        assertTrue(fight_.getEnabledMoves().getVal(ZENITH).isEnabled());
        assertTrue(!FightMoves.climatsActifs(fight_, _data_).containsObj(ZENITH));
        assertTrue(FightMoves.existenceAntiClimatActif(fight_, _data_));
        assertTrue(fight_.getStillEnabledMoves().getVal(ZENITH));
    }

    @Test
    public void disableAbility6Test() {
        Fight fight_ = disableAbility(AIR_LOCK, AIR_LOCK, (byte)2);
        assertTrue(fight_.getEnabledMoves().getVal(ZENITH).isEnabled());
        assertTrue(!FightMoves.climatsActifs(fight_, _data_).containsObj(ZENITH));
        assertTrue(FightMoves.existenceAntiClimatActif(fight_, _data_));
        assertTrue(fight_.getStillEnabledMoves().getVal(ZENITH));
        String ability_ = fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getAbility();
        FightAbilities.disableAbility(fight_, POKEMON_PLAYER_FIGHTER_ZERO, ability_, _data_);
        assertTrue(fight_.getEnabledMoves().getVal(ZENITH).isEnabled());
        assertTrue(!FightMoves.climatsActifs(fight_, _data_).containsObj(ZENITH));
        assertTrue(FightMoves.existenceAntiClimatActif(fight_, _data_));
        assertTrue(fight_.getStillEnabledMoves().getVal(ZENITH));
    }

    @Test
    public void disableAbility7Test() {
        Fight fight_ = disableAbility(METEO, SECHERESSE, (byte)2);
        FightAbilities.enableAbilityByWeather(fight_,POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        StringList types_ = fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getTypes();
        assertEq(1, types_.size());
        assertTrue(types_.containsObj(FEU));
        FightAbilities.disableAbility(fight_, POKEMON_PLAYER_FIGHTER_ZERO, NULL_REF, _data_);
        types_ = fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getTypes();
        assertEq(1, types_.size());
        assertTrue(types_.containsObj(ELECTRIQUE));
    }

    @Test
    public void disableAbility8Test() {
        Fight fight_ = disableAbility(DEGUISEMENT, SECHERESSE, (byte)2);
        fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).affecterTypes(VOL);
        FightAbilities.disableAbility(fight_, POKEMON_PLAYER_FIGHTER_ZERO, NULL_REF, _data_);
        StringList types_ = fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO).getTypes();
        assertEq(1, types_.size());
        assertTrue(types_.containsObj(ELECTRIQUE));
    }

    @Test
    public void disableAbility9Test() {
        Fight fight_ = disableAbility(MULTITYPE, SECHERESSE, (byte)2);
        FightSending.effectPlate(fight_, POKEMON_PLAYER_FIGHTER_ZERO, _data_);
        Fighter fighter_ = fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO);
        StringList typesThrower_ = fighter_.getTypes();
        assertEq(1, typesThrower_.size());
        assertTrue(typesThrower_.containsObj(DRAGON));
        FightAbilities.disableAbility(fight_, POKEMON_PLAYER_FIGHTER_ZERO, NULL_REF, _data_);
        fighter_ = fight_.getFighter(POKEMON_PLAYER_FIGHTER_ZERO);
        StringList types_ = fighter_.getTypes();
        assertEq(NULL_REF, fighter_.getCurrentAbility());
        assertEq(1, types_.size());
        assertTrue(types_.containsObj(ELECTRIQUE));
    }
}
