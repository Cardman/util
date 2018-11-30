package aiki.map.levels;
import static aiki.EquallablePkUtil.assertEq;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import aiki.map.pokemon.WildPk;
import aiki.map.pokemon.enums.Gender;
import code.maths.LgInt;
import code.maths.montecarlo.MonteCarloEq;
import code.util.EqList;

@SuppressWarnings("static-method")
public class AreaApparitionTest {

    @Test
    public void random1Test() {
        EqList<WildPk> list_ = new EqList<WildPk>();
        MonteCarloEq<WildPk> law_ = AreaApparition.random(list_, AreaApparition.ALWAYS_APPARITION);
        assertEq(0, law_.events().size());
    }

    @Test
    public void random2Test() {
        EqList<WildPk> list_ = new EqList<WildPk>();
        MonteCarloEq<WildPk> law_ = AreaApparition.random(list_, 2);
        assertEq(1, law_.events().size());
        assertTrue(law_.events().first().hasJustBeenCreated());
    }

    @Test
    public void random3Test() {
        EqList<WildPk> list_ = new EqList<WildPk>();
        WildPk pk_;
        pk_ = new WildPk();
        pk_.setName("PIKACHU");
        pk_.setAbility("STATIK");
        pk_.setGender(Gender.NO_GENDER);
        pk_.setLevel((short) 3);
        pk_.setItem("");
        list_.add(pk_);
        MonteCarloEq<WildPk> law_ = AreaApparition.random(list_, AreaApparition.ALWAYS_APPARITION);
        assertEq(1, law_.events().size());
        WildPk event_;
        event_ = law_.events().first();
        assertEq("PIKACHU", event_.getName());
        assertEq("STATIK", event_.getAbility());
        assertEq("", event_.getItem());
        assertEq(Gender.NO_GENDER, event_.getGender());
        assertEq(3, event_.getLevel());
    }

    @Test
    public void random4Test() {
        EqList<WildPk> list_ = new EqList<WildPk>();
        WildPk pk_;
        pk_ = new WildPk();
        pk_.setName("PIKACHU");
        pk_.setAbility("STATIK");
        pk_.setGender(Gender.NO_GENDER);
        pk_.setLevel((short) 3);
        pk_.setItem("");
        list_.add(pk_);
        MonteCarloEq<WildPk> law_ = AreaApparition.random(list_, 3);
        assertEq(2, law_.events().size());
        assertTrue(law_.events().containsObj(pk_));
        assertTrue(law_.events().containsObj(new WildPk()));
        assertEq(new LgInt("1"), law_.rate(pk_));
        assertEq(new LgInt("2"), law_.rate(new WildPk()));
    }

    @Test
    public void random5Test() {
        EqList<WildPk> list_ = new EqList<WildPk>();
        WildPk pkOne_ = new WildPk();
        pkOne_.setName("PIKACHU");
        pkOne_.setAbility("STATIK");
        pkOne_.setGender(Gender.NO_GENDER);
        pkOne_.setLevel((short) 3);
        pkOne_.setItem("");
        list_.add(pkOne_);
        WildPk pkTwo_ = new WildPk();
        pkTwo_.setName("MELOFEE");
        pkTwo_.setAbility("STATIK");
        pkTwo_.setGender(Gender.NO_GENDER);
        pkTwo_.setLevel((short) 3);
        pkTwo_.setItem("");
        list_.add(pkTwo_);
        MonteCarloEq<WildPk> law_ = AreaApparition.random(list_, 3);
        assertEq(3, law_.events().size());
        assertTrue(law_.events().containsObj(pkOne_));
        assertTrue(law_.events().containsObj(pkTwo_));
        assertTrue(law_.events().containsObj(new WildPk()));
        assertEq(new LgInt("1"), law_.rate(pkOne_));
        assertEq(new LgInt("1"), law_.rate(pkTwo_));
        assertEq(new LgInt("4"), law_.rate(new WildPk()));
    }

    @Test
    public void random6Test() {
        EqList<WildPk> list_ = new EqList<WildPk>();
        WildPk pkOne_ = new WildPk();
        pkOne_.setName("PIKACHU");
        pkOne_.setAbility("STATIK");
        pkOne_.setGender(Gender.NO_GENDER);
        pkOne_.setLevel((short) 3);
        pkOne_.setItem("");
        list_.add(pkOne_);
        WildPk pkTwo_ = new WildPk();
        pkTwo_.setName("MELOFEE");
        pkTwo_.setAbility("STATIK");
        pkTwo_.setGender(Gender.NO_GENDER);
        pkTwo_.setLevel((short) 3);
        pkTwo_.setItem("");
        list_.add(pkTwo_);
        WildPk pkThree_ = new WildPk();
        pkThree_.setName("MELOFEE");
        pkThree_.setAbility("STATIK");
        pkThree_.setGender(Gender.NO_GENDER);
        pkThree_.setLevel((short) 3);
        pkThree_.setItem("");
        list_.add(pkThree_);
        MonteCarloEq<WildPk> law_ = AreaApparition.random(list_, 3);
        assertEq(3, law_.events().size());
        assertTrue(law_.events().containsObj(pkOne_));
        assertTrue(law_.events().containsObj(pkTwo_));
        assertTrue(law_.events().containsObj(new WildPk()));
        assertEq(new LgInt("1"), law_.rate(pkOne_));
        assertEq(new LgInt("2"), law_.rate(pkTwo_));
        assertEq(new LgInt("6"), law_.rate(new WildPk()));
    }

    @Test
    public void random7Test() {
        EqList<WildPk> list_ = new EqList<WildPk>();
        WildPk pkOne_ = new WildPk();
        pkOne_.setName("PIKACHU");
        pkOne_.setAbility("STATIK");
        pkOne_.setGender(Gender.NO_GENDER);
        pkOne_.setLevel((short) 3);
        pkOne_.setItem("");
        list_.add(pkOne_);
        WildPk pkTwo_ = new WildPk();
        pkTwo_.setName("MELOFEE");
        pkTwo_.setAbility("STATIK");
        pkTwo_.setGender(Gender.NO_GENDER);
        pkTwo_.setLevel((short) 3);
        pkTwo_.setItem("");
        list_.add(pkTwo_);
        MonteCarloEq<WildPk> law_ = AreaApparition.random(list_, AreaApparition.ALWAYS_APPARITION);
        assertEq(2, law_.events().size());
        assertTrue(law_.events().containsObj(pkOne_));
        assertTrue(law_.events().containsObj(pkTwo_));
        assertEq(new LgInt("1"), law_.rate(pkOne_));
        assertEq(new LgInt("1"), law_.rate(pkTwo_));
    }

    @Test
    public void random8Test() {
        EqList<WildPk> list_ = new EqList<WildPk>();
        WildPk pkOne_ = new WildPk();
        pkOne_.setName("PIKACHU");
        pkOne_.setAbility("STATIK");
        pkOne_.setGender(Gender.NO_GENDER);
        pkOne_.setLevel((short) 3);
        pkOne_.setItem("");
        list_.add(pkOne_);
        WildPk pkTwo_ = new WildPk();
        pkTwo_.setName("MELOFEE");
        pkTwo_.setAbility("STATIK");
        pkTwo_.setGender(Gender.NO_GENDER);
        pkTwo_.setLevel((short) 3);
        pkTwo_.setItem("");
        list_.add(pkTwo_);
        WildPk pkThree_ = new WildPk();
        pkThree_.setName("MELOFEE");
        pkThree_.setAbility("STATIK");
        pkThree_.setGender(Gender.NO_GENDER);
        pkThree_.setLevel((short) 3);
        pkThree_.setItem("");
        list_.add(pkThree_);
        MonteCarloEq<WildPk> law_ = AreaApparition.random(list_, AreaApparition.ALWAYS_APPARITION);
        assertEq(2, law_.events().size());
        assertTrue(law_.events().containsObj(pkOne_));
        assertTrue(law_.events().containsObj(pkTwo_));
        assertEq(new LgInt("1"), law_.rate(pkOne_));
        assertEq(new LgInt("2"), law_.rate(pkTwo_));
    }
}
