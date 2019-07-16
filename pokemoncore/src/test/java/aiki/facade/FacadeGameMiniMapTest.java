package aiki.facade;

import aiki.db.DataBase;
import aiki.game.Game;
import aiki.game.fight.InitializationDataBase;
import aiki.game.params.Difficulty;
import aiki.map.enums.Direction;
import aiki.map.pokemon.PokemonPlayer;
import aiki.util.Coords;
import aiki.util.LevelPoint;
import aiki.util.Point;
import org.junit.Before;
import org.junit.Test;

import static aiki.db.EquallablePkUtil.assertEq;

public final class FacadeGameMiniMapTest extends InitializationDataBase {

    private DataBase data;
    private Game game;
    private FacadeGame facadeGame;
    @Before
    public void initTests() {
        data = initDb();
        Game game_ = new Game(data);
        Difficulty diff_ = new Difficulty();
        game_.initUtilisateur(NICKNAME, null, diff_, data);
        game_.setPlayerOrientation(Direction.UP);
        game_.getDifficulty().setRandomWildFight(false);
        game_.getPlayer().getItem(LAVA);
        game_.getPlayer().doRevivingFossil(LAVA, diff_, data);
        PokemonPlayer pk_ = (PokemonPlayer) game_.getPlayer().getTeam().get(1);
        pk_.setItem(PIERRE_LUNE);
        game = game_;
        FacadeGame facadeGame_ = new FacadeGame();
        facadeGame_.setData(data);
        facadeGame_.setLanguage(LANGUAGE);
        facadeGame_.setGame(game_);
        facadeGame_.directInteraction();
        facadeGame_.interact();
        facadeGame = facadeGame_;
    }

    @Test
    public void chooseCity1Test() {
        assertEq(11,facadeGame.getImages().size());
        facadeGame.setMiniMapCoords(0,0);
        assertEq(NULL_REF,facadeGame.getChosenCity());
        facadeGame.choosePlace();
        assertEq(newCoords(0,0,0,0),game.getPlayerCoords());
    }

    @Test
    public void chooseCity2Test() {
        assertEq(11,facadeGame.getImages().size());
        facadeGame.setMiniMapCoords(0,1);
        assertEq(CITY,facadeGame.getChosenCity());
        facadeGame.choosePlace();
        assertEq(newCoords(0,0,0,0),game.getPlayerCoords());
    }
    @Test
    public void chooseCity3Test() {
        game.visitFirstPlaces(data);
        assertEq(11,facadeGame.getImages().size());
        facadeGame.setMiniMapCoords(0,1);
        assertEq(CITY,facadeGame.getChosenCity());
        facadeGame.choosePlace();
        assertEq(newCoords(1,0,1,2),game.getPlayerCoords());
    }

    private static Coords newCoords(int _place, int _level, int _x, int _y) {
        Coords begin_ = new Coords();
        begin_.setNumberPlace((short) _place);
        begin_.setLevel(new LevelPoint());
        begin_.getLevel().setLevelIndex((byte) _level);
        begin_.getLevel().setPoint(new Point((short)_x, (short)_y));
        return begin_;
    }

}