package aiki.map.pokemon;
import static aiki.EquallablePkUtil.assertEq;

import org.junit.Test;

import aiki.exceptions.GameLoadException;
import aiki.game.fight.InitializationDataBase;

@SuppressWarnings("static-method")
public class EggValidationTest extends InitializationDataBase {

    private static final char SEPARATOR = Egg.SEPARATOR;

    @Test
    public void isValid1Test() {
        Egg egg_ = new Egg(PIKACHU+SEPARATOR+1);
        egg_.validate(_data_);
    }

    @Test
    public void isValid2Test() {
        Egg egg_ = new Egg(PIKACHU);
        egg_.validate(_data_);
    }

    @Test(expected=GameLoadException.class)
    public void isValid3Test() {
        Egg egg_ = new Egg(INVALID_DATA_KEY+SEPARATOR+1);
        egg_.validate(_data_);
    }

    @Test(expected=GameLoadException.class)
    public void isValid4Test() {
        Egg egg_ = new Egg(PIKACHU+SEPARATOR+-1);
        egg_.validate(_data_);
    }

    @Test
    public void toString1Test() {
        Egg egg_ = new Egg(PIKACHU+SEPARATOR+1);
        assertEq(PIKACHU+SEPARATOR+1, egg_.display());
    }
}
