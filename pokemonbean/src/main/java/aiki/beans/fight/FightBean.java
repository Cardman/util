package aiki.beans.fight;
import code.bean.Accessible;
import code.maths.LgInt;
import code.maths.Rate;
import code.util.NatTreeMap;
import code.util.StringMap;
import aiki.DataBase;
import aiki.facade.FacadeGame;
import aiki.game.fight.ActivityOfMove;
import aiki.game.fight.Fight;

public class FightBean extends CommonFightBean {

    @Accessible
    private byte mult;

    @Accessible
    private NatTreeMap<String,ActivityOfMove> enabledMoves;

    @Accessible
    private NatTreeMap<String,Boolean> stillEnabledMoves;

    @Accessible
    private short nbFleeAttempt;

    @Accessible
    private LgInt nbRounds;

    @Accessible
    private Rate winningMoney;

    @Override
    public void beforeDisplaying() {
        FacadeGame dataBaseFight_ = (FacadeGame) getDataBase();
        DataBase data_ = dataBaseFight_.getData();
        StringMap<String> translationsMoves_;
        translationsMoves_ = data_.getTranslatedMoves().getVal(getLanguage());
        Fight fight_ = dataBaseFight_.getGame().getFight();
        mult = fight_.getMult();
        nbRounds = fight_.getNbRounds();
        nbFleeAttempt = fight_.getNbFleeAttempt();
        winningMoney = fight_.getWinningMoney();
        NatTreeMap<String,ActivityOfMove> enabledMoves_;
        enabledMoves_ = new NatTreeMap<String,ActivityOfMove>();
        for (String m: fight_.getEnabledMoves().getKeys()) {
            enabledMoves_.put(translationsMoves_.getVal(m), fight_.getEnabledMoves().getVal(m));
        }
        enabledMoves = enabledMoves_;
        NatTreeMap<String,Boolean> stillEnabledMoves_;
        stillEnabledMoves_ = new NatTreeMap<String,Boolean>();
        for (String m: fight_.getStillEnabledMoves().getKeys()) {
            stillEnabledMoves_.put(translationsMoves_.getVal(m), fight_.getStillEnabledMoves().getVal(m));
        }
        stillEnabledMoves = stillEnabledMoves_;
    }

    @Accessible
    private boolean isStillEnabled(Long _index) {
        String key_ = enabledMoves.getKey(_index.intValue());
        return stillEnabledMoves.contains(key_);
    }

    @Accessible
    private String clickPlayer() {
        getForms().put(NO_TEAM, Fight.PLAYER);
        return TEAM;
    }

    @Accessible
    private String clickFoe() {
        getForms().put(NO_TEAM, Fight.FOE);
        return TEAM;
    }
}
