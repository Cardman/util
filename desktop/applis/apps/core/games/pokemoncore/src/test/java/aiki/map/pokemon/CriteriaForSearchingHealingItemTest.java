package aiki.map.pokemon;

import aiki.db.DataBase;
import code.util.core.StringUtil;
import org.junit.Before;
import org.junit.Test;

import aiki.fight.enums.Statistic;
import aiki.game.fight.InitializationDataBase;
import code.maths.Rate;
import code.util.StringList;
import aiki.facade.enums.SearchingMode;
import aiki.facade.enums.SelectedBoolean;


public class CriteriaForSearchingHealingItemTest extends InitializationDataBase {

    private DataBase data;
    @Before
    public void initTests() {
        data = initDb();
    }
    @Test
    public void matchClasses1Test() {
        assertTrue(CriteriaForSearchingHealingItem.matchClasses(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchClasses2Test() {
        assertTrue(CriteriaForSearchingHealingItem.matchClasses(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchClasses3Test() {
        assertTrue(!CriteriaForSearchingHealingItem.matchClasses(data.getItem(MULTI_EXP)));
    }

    @Test
    public void getStatus1Test() {
        assertEq(0, CriteriaForSearchingHealingItem.getStatus(data.getItem(BAIE_MEPO)).size());
    }

    @Test
    public void getStatus2Test() {
        assertEq(0, CriteriaForSearchingHealingItem.getStatus(data.getItem(EAU_FRAICHE)).size());
    }

    @Test
    public void getStatus3Test() {
        StringList st_ = CriteriaForSearchingHealingItem.getStatus(data.getItem(BAIE_CERIZ));
        assertEq(1, st_.size());
        assertEq(PARALYSIE, st_.first());
    }

    @Test
    public void getStatus4Test() {
        StringList st_ = CriteriaForSearchingHealingItem.getStatus(data.getItem(TOTAL_SOIN));
        assertEq(7, st_.size());
        assertTrue(StringUtil.contains(st_, BRULURE));
        assertTrue(StringUtil.contains(st_, GEL));
        assertTrue(StringUtil.contains(st_, PARALYSIE));
        assertTrue(StringUtil.contains(st_, POISON_ST));
        assertTrue(StringUtil.contains(st_, POISON_GRAVE));
        assertTrue(StringUtil.contains(st_, SOMMEIL));
        assertTrue(StringUtil.contains(st_, SOMMEIL_REPOS));
    }

    @Test
    public void matchStatus1Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setTranslatedStatus(data.getTranslatedStatus().getVal(LANGUAGE));
        assertTrue(criteria_.matchStatus(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchStatus2Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setTranslatedStatus(data.getTranslatedStatus().getVal(LANGUAGE));
        assertTrue(criteria_.matchStatus(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchStatus3Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setTranslatedStatus(data.getTranslatedStatus().getVal(LANGUAGE));
        assertTrue(criteria_.matchStatus(data.getItem(BAIE_CERIZ)));
    }

    @Test
    public void matchStatus4Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setTranslatedStatus(data.getTranslatedStatus().getVal(LANGUAGE));
        assertTrue(criteria_.matchStatus(data.getItem(TOTAL_SOIN)));
    }

    @Test
    public void matchStatus5Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setTranslatedStatus(data.getTranslatedStatus().getVal(LANGUAGE));
        criteria_.setContentOfStatus(CONFUSION);
        criteria_.setSearchModeStatus(SearchingMode.WHOLE_STRING);
        assertTrue(!criteria_.matchStatus(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchStatus6Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setTranslatedStatus(data.getTranslatedStatus().getVal(LANGUAGE));
        criteria_.setContentOfStatus(CONFUSION);
        criteria_.setSearchModeStatus(SearchingMode.WHOLE_STRING);
        assertTrue(!criteria_.matchStatus(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchStatus7Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setTranslatedStatus(data.getTranslatedStatus().getVal(LANGUAGE));
        criteria_.setContentOfStatus(CONFUSION);
        criteria_.setSearchModeStatus(SearchingMode.WHOLE_STRING);
        assertTrue(!criteria_.matchStatus(data.getItem(BAIE_CERIZ)));
    }

    @Test
    public void matchStatus8Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setTranslatedStatus(data.getTranslatedStatus().getVal(LANGUAGE));
        criteria_.setContentOfStatus(CONFUSION);
        criteria_.setSearchModeStatus(SearchingMode.WHOLE_STRING);
        assertTrue(!criteria_.matchStatus(data.getItem(TOTAL_SOIN)));
    }

    @Test
    public void matchStatus9Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setTranslatedStatus(data.getTranslatedStatus().getVal(LANGUAGE));
        criteria_.setContentOfStatus(PARALYSIE);
        criteria_.setSearchModeStatus(SearchingMode.WHOLE_STRING);
        assertTrue(criteria_.matchStatus(data.getItem(BAIE_CERIZ)));
    }

    @Test
    public void matchStatus10Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setTranslatedStatus(data.getTranslatedStatus().getVal(LANGUAGE));
        criteria_.setContentOfStatus(SOMMEIL);
        criteria_.setSearchModeStatus(SearchingMode.WHOLE_STRING);
        assertTrue(criteria_.matchStatus(data.getItem(TOTAL_SOIN)));
    }

    @Test
    public void matchHp1Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchHp(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchHp2Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchHp(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchHp3Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchHp(data.getItem(BAIE_CERIZ)));
    }

    @Test
    public void matchHp4Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchHp(data.getItem(TOTAL_SOIN)));
    }

    /*@Test
    public void matchHp5Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        assertTrue(!criteria_.matchHp(data.getItem(EAU_FRAICHE)));
    }*/

    @Test
    public void matchHp6Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.NO);
        assertTrue(criteria_.matchHp(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchHp7Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.NO);
        criteria_.setMinHp(new Rate("100"));
        assertTrue(!criteria_.matchHp(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchHp8Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.NO);
        criteria_.setMaxHp(new Rate("100"));
        assertTrue(criteria_.matchHp(data.getItem(EAU_FRAICHE)));
    }

    /*@Test
    public void matchHp9Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        assertTrue(!criteria_.matchHp(data.getItem(BAIE_ORAN)));
    }*/

    @Test
    public void matchHp10Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.NO);
        assertTrue(criteria_.matchHp(data.getItem(BAIE_ORAN)));
    }

    @Test
    public void matchHp11Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.NO);
        criteria_.setMinHp(new Rate("100"));
        assertTrue(!criteria_.matchHp(data.getItem(BAIE_ORAN)));
    }

    @Test
    public void matchHp12Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.NO);
        criteria_.setMaxHp(new Rate("100"));
        assertTrue(criteria_.matchHp(data.getItem(BAIE_ORAN)));
    }

    @Test
    public void matchHp13Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.NO);
        assertTrue(!criteria_.matchHp(data.getItem(BAIE_ENIGMA)));
    }

    /*@Test
    public void matchHp14Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        assertTrue(criteria_.matchHp(data.getItem(BAIE_ENIGMA)));
    }*/

    @Test
    public void matchHp15Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        criteria_.setMinHp(new Rate("1/2"));
        assertTrue(!criteria_.matchHp(data.getItem(BAIE_ENIGMA)));
    }

    /*@Test
    public void matchHp16Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        criteria_.setMaxHp(new Rate("1/2"));
        assertTrue(criteria_.matchHp(data.getItem(BAIE_ENIGMA)));
    }*/

    @Test
    public void matchHp17Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.NO);
        assertTrue(!criteria_.matchHp(data.getItem(BAIE_GOWAV)));
    }

    /*@Test
    public void matchHp18Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        assertTrue(criteria_.matchHp(data.getItem(BAIE_GOWAV)));
    }*/

    @Test
    public void matchHp19Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        criteria_.setMinHp(new Rate("1/2"));
        assertTrue(!criteria_.matchHp(data.getItem(BAIE_GOWAV)));
    }

    /*@Test
    public void matchHp20Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        criteria_.setMaxHp(new Rate("1/2"));
        assertTrue(criteria_.matchHp(data.getItem(BAIE_GOWAV)));
    }*/

    /*@Test
    public void matchHp21Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setMinHp(new Rate("1/16"));
        criteria_.setMaxHp(new Rate("1/2"));
        assertTrue(criteria_.matchHp(data.getItem(BAIE_ENIGMA)));
    }*/

    @Test
    public void matchHp22Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setMinHp(new Rate("1/16"));
        criteria_.setMaxHp(new Rate("50"));
        assertTrue(criteria_.matchHp(data.getItem(BAIE_ORAN)));
    }

    /*@Test
    public void matchHp23Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setMinHp(new Rate("1/16"));
        criteria_.setMaxHp(new Rate("50"));
        assertTrue(criteria_.matchHp(data.getItem(BAIE_GOWAV)));
    }*/

    @Test
    public void matchHp24Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setMinHp(new Rate("1/4"));
        criteria_.setMaxHp(new Rate("50"));
        assertTrue(!criteria_.matchHp(data.getItem(BAIE_GOWAV)));
    }

    @Test
    public void matchHp25Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        assertTrue(!criteria_.matchHp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchHp26Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.NO);
        assertTrue(!criteria_.matchHp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchRateHp1Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchRateHp(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchRateHp2Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchRateHp(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchRateHp3Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchRateHp(data.getItem(BAIE_CERIZ)));
    }

    @Test
    public void matchRateHp4Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchRateHp(data.getItem(TOTAL_SOIN)));
    }

    @Test
    public void matchRateHp5Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        assertTrue(!criteria_.matchRateHp(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchRateHp6Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        assertTrue(!criteria_.matchRateHp(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchRateHp7Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        assertTrue(!criteria_.matchRateHp(data.getItem(BAIE_CERIZ)));
    }

    @Test
    public void matchRateHp8Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        assertTrue(!criteria_.matchRateHp(data.getItem(TOTAL_SOIN)));
    }

    @Test
    public void matchRateHp9Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        assertTrue(criteria_.matchRateHp(data.getItem(BAIE_ENIGMA)));
    }

    @Test
    public void matchRateHp10Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        assertTrue(criteria_.matchRateHp(data.getItem(PETIT_RAPPEL)));
    }

    @Test
    public void matchRateHp11Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        criteria_.setMinRateHp(new Rate("1/2"));
        assertTrue(!criteria_.matchRateHp(data.getItem(BAIE_ENIGMA)));
    }

    @Test
    public void matchRateHp12Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRateHp(SelectedBoolean.YES);
        criteria_.setMinRateHp(new Rate("1/2"));
        assertTrue(!criteria_.matchRateHp(data.getItem(PETIT_RAPPEL)));
    }

    @Test
    public void matchRateHp13Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchRateHp(data.getItem(PETIT_RAPPEL)));
    }

    @Test
    public void matchPp1Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchPp(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchPp2Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchPp(data.getItem(BAIE_ORAN)));
    }

    @Test
    public void matchPp3Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchPp(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchPp4Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchPp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchPp5Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchPp(data.getItem(HUILE)));
    }

    @Test
    public void matchPp6Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.YES);
        assertTrue(!criteria_.matchPp(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchPp7Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.YES);
        assertTrue(!criteria_.matchPp(data.getItem(BAIE_ORAN)));
    }

    @Test
    public void matchPp8Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.YES);
        assertTrue(!criteria_.matchPp(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchPp9Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.YES);
        assertTrue(!criteria_.matchPp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchPp10Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.YES);
        assertTrue(!criteria_.matchPp(data.getItem(HUILE)));
    }

    @Test
    public void matchPp11Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.YES);
        assertTrue(criteria_.matchPp(data.getItem(MAX_ELIXIR)));
    }

    @Test
    public void matchPp12Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.YES);
        assertTrue(criteria_.matchPp(data.getItem(HUILE_MAX)));
    }

    @Test
    public void matchPp13Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        assertTrue(!criteria_.matchPp(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchPp14Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        assertTrue(!criteria_.matchPp(data.getItem(BAIE_ORAN)));
    }

    @Test
    public void matchPp15Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        assertTrue(criteria_.matchPp(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchPp16Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        assertTrue(criteria_.matchPp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchPp17Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        assertTrue(criteria_.matchPp(data.getItem(HUILE)));
    }

    @Test
    public void matchPp18Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        assertTrue(!criteria_.matchPp(data.getItem(MAX_ELIXIR)));
    }

    @Test
    public void matchPp19Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        assertTrue(!criteria_.matchPp(data.getItem(HUILE_MAX)));
    }

    @Test
    public void matchPp20Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        criteria_.setMinPp((long) 20);
        assertTrue(!criteria_.matchPp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchPp21Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        criteria_.setMinPp((long) 20);
        assertTrue(!criteria_.matchPp(data.getItem(HUILE)));
    }

    @Test
    public void matchPp22Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        criteria_.setMaxPp((long) 20);
        assertTrue(criteria_.matchPp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchPp23Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        criteria_.setMaxPp((long) 20);
        assertTrue(criteria_.matchPp(data.getItem(HUILE)));
    }

    @Test
    public void matchPp24Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        criteria_.setHealOneMove(SelectedBoolean.NO);
        assertTrue(!criteria_.matchPp(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchPp25Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.YES);
        criteria_.setHealOneMove(SelectedBoolean.YES);
        assertTrue(!criteria_.matchPp(data.getItem(MAX_ELIXIR)));
    }

    @Test
    public void matchPp26Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.YES);
        criteria_.setHealOneMove(SelectedBoolean.YES);
        assertTrue(criteria_.matchPp(data.getItem(HUILE_MAX)));
    }

    @Test
    public void matchPp27Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        criteria_.setHealOneMove(SelectedBoolean.YES);
        assertTrue(!criteria_.matchPp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchPp28Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        criteria_.setHealOneMove(SelectedBoolean.YES);
        assertTrue(criteria_.matchPp(data.getItem(HUILE)));
    }

    @Test
    public void matchPp29Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        criteria_.setHealOneMove(SelectedBoolean.YES);
        criteria_.setMinPp((long) 20);
        assertTrue(!criteria_.matchPp(data.getItem(HUILE)));
    }

    @Test
    public void matchPp30Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        criteria_.setHealOneMove(SelectedBoolean.NO);
        assertTrue(criteria_.matchPp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchPp31Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        criteria_.setHealOneMove(SelectedBoolean.NO);
        assertTrue(!criteria_.matchPp(data.getItem(HUILE)));
    }

    @Test
    public void matchPp32Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.YES);
        criteria_.setHealOneMove(SelectedBoolean.NO);
        assertTrue(criteria_.matchPp(data.getItem(MAX_ELIXIR)));
    }

    @Test
    public void matchPp33Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.YES);
        criteria_.setHealOneMove(SelectedBoolean.NO);
        assertTrue(!criteria_.matchPp(data.getItem(HUILE_MAX)));
    }

    @Test
    public void matchPp34Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setRelativeRatePp(SelectedBoolean.NO);
        criteria_.setHealOneMove(SelectedBoolean.NO);
        criteria_.setMinPp((long) 20);
        assertTrue(!criteria_.matchPp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchPp35Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setHealOneMove(SelectedBoolean.NO);
        assertTrue(!criteria_.matchPp(data.getItem(HUILE_MAX)));
    }

    @Test
    public void matchPp36Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setHealOneMove(SelectedBoolean.YES);
        assertTrue(!criteria_.matchPp(data.getItem(MAX_ELIXIR)));
    }

    @Test
    public void matchPp37Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setHealOneMove(SelectedBoolean.YES);
        assertTrue(criteria_.matchPp(data.getItem(HUILE)));
    }

    @Test
    public void matchPp38Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setHealOneMove(SelectedBoolean.NO);
        assertTrue(criteria_.matchPp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchPp39Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setHealOneMove(SelectedBoolean.YES);
        criteria_.setMinPp((long) 20);
        assertTrue(!criteria_.matchPp(data.getItem(HUILE)));
    }

    @Test
    public void matchPp40Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setHealOneMove(SelectedBoolean.NO);
        criteria_.setMinPp((long) 20);
        assertTrue(!criteria_.matchPp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchPp41Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setHealOneMove(SelectedBoolean.YES);
        assertTrue(criteria_.matchPp(data.getItem(HUILE_MAX)));
    }

    @Test
    public void matchPp42Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setHealOneMove(SelectedBoolean.NO);
        assertTrue(criteria_.matchPp(data.getItem(MAX_ELIXIR)));
    }

    @Test
    public void matchPp43Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchPp(data.getItem(HUILE_MAX)));
    }

    @Test
    public void matchPp44Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchPp(data.getItem(MAX_ELIXIR)));
    }

    @Test
    public void matchPp45Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setMinPp((long) 20);
        assertTrue(!criteria_.matchPp(data.getItem(HUILE)));
    }

    @Test
    public void matchPp46Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setMinPp((long) 20);
        assertTrue(!criteria_.matchPp(data.getItem(ELIXIR)));
    }

    @Test
    public void matchStatistic1Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchStatistic(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchStatistic2Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchStatistic(data.getItem(BAIE_ORAN)));
    }

    @Test
    public void matchStatistic3Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchStatistic(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchStatistic4Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchStatistic(data.getItem(ELIXIR)));
    }

    @Test
    public void matchStatistic5Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchStatistic(data.getItem(HUILE)));
    }

    @Test
    public void matchStatistic6Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setStatistic(Statistic.ATTACK);
        assertTrue(!criteria_.matchStatistic(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchStatistic7Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setStatistic(Statistic.ATTACK);
        assertTrue(!criteria_.matchStatistic(data.getItem(BAIE_ORAN)));
    }

    @Test
    public void matchStatistic8Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setStatistic(Statistic.ATTACK);
        assertTrue(!criteria_.matchStatistic(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchStatistic9Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setStatistic(Statistic.ATTACK);
        assertTrue(!criteria_.matchStatistic(data.getItem(ELIXIR)));
    }

    @Test
    public void matchStatistic10Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setStatistic(Statistic.ATTACK);
        assertTrue(!criteria_.matchStatistic(data.getItem(HUILE)));
    }

    @Test
    public void matchStatistic11Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setStatistic(Statistic.ATTACK);
        assertTrue(!criteria_.matchStatistic(data.getItem(BAIE_LANSAT)));
    }

    @Test
    public void matchStatistic12Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setStatistic(Statistic.CRITICAL_HIT);
        assertTrue(criteria_.matchStatistic(data.getItem(BAIE_LANSAT)));
    }

    @Test
    public void matchKo1Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchKo(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchKo2Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchKo(data.getItem(BAIE_ORAN)));
    }

    @Test
    public void matchKo3Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchKo(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchKo4Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchKo(data.getItem(ELIXIR)));
    }

    @Test
    public void matchKo5Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchKo(data.getItem(HUILE)));
    }

    @Test
    public void matchKo6Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        assertTrue(criteria_.matchKo(data.getItem(RAPPEL)));
    }

    @Test
    public void matchKo7Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setKo(SelectedBoolean.NO);
        assertTrue(criteria_.matchKo(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchKo8Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setKo(SelectedBoolean.NO);
        assertTrue(criteria_.matchKo(data.getItem(BAIE_ORAN)));
    }

    @Test
    public void matchKo9Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setKo(SelectedBoolean.NO);
        assertTrue(criteria_.matchKo(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchKo10Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setKo(SelectedBoolean.NO);
        assertTrue(criteria_.matchKo(data.getItem(ELIXIR)));
    }

    @Test
    public void matchKo11Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setKo(SelectedBoolean.NO);
        assertTrue(criteria_.matchKo(data.getItem(HUILE)));
    }

    @Test
    public void matchKo12Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setKo(SelectedBoolean.NO);
        assertTrue(!criteria_.matchKo(data.getItem(RAPPEL)));
    }

    @Test
    public void matchKo13Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setKo(SelectedBoolean.YES);
        assertTrue(!criteria_.matchKo(data.getItem(EAU_FRAICHE)));
    }

    @Test
    public void matchKo14Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setKo(SelectedBoolean.YES);
        assertTrue(!criteria_.matchKo(data.getItem(BAIE_ORAN)));
    }

    @Test
    public void matchKo15Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setKo(SelectedBoolean.YES);
        assertTrue(!criteria_.matchKo(data.getItem(BAIE_MEPO)));
    }

    @Test
    public void matchKo16Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setKo(SelectedBoolean.YES);
        assertTrue(!criteria_.matchKo(data.getItem(ELIXIR)));
    }

    @Test
    public void matchKo17Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setKo(SelectedBoolean.YES);
        assertTrue(!criteria_.matchKo(data.getItem(HUILE)));
    }

    @Test
    public void matchKo18Test() {
        CriteriaForSearchingHealingItem criteria_;
        criteria_ = new CriteriaForSearchingHealingItem();
        criteria_.setKo(SelectedBoolean.YES);
        assertTrue(criteria_.matchKo(data.getItem(RAPPEL)));
    }
}
