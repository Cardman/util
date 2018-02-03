package cards.president.beans;
import cards.president.GamePresident;
import cards.president.ResultsPresident;
import code.bean.Bean;
import code.util.CustList;
import code.util.Numbers;
import code.util.StringList;

final class PresidentBean extends Bean {

    private GamePresident game;

    private StringList nicknames;

    private CustList<Numbers<Long>> scores;

    private byte user;

    private String loc;

    private CustList<LineDeal> linesDeal;

    @Override
    public void beforeDisplaying() {
        ResultsPresident res_ = getResults();
        setGame(res_.getGame());
        setNicknames(res_.getNicknames());
        setScores(res_.getScores());
        setUser(res_.getUser());
        setLoc(res_.getLoc());
        byte nombreJoueurs_ = getGame().getNombreDeJoueurs();
        linesDeal = new CustList<LineDeal>();
        int nbDeals_ = getScores().size();
        for(int i=CustList.FIRST_INDEX;i<nbDeals_;i++) {
            LineDeal l_ = new LineDeal();
            l_.setNumber(i);
            Numbers<Long> scores_ = new Numbers<Long>();
            for(byte joueur_=CustList.FIRST_INDEX;joueur_<nombreJoueurs_;joueur_++) {
                scores_.add(getScores().get(i).get(joueur_));
            }
            l_.setScores(scores_);
            linesDeal.add(l_);
        }
    }

    GamePresident getGame() {
        return game;
    }

    void setGame(GamePresident _game) {
        game = _game;
    }

    StringList getNicknames() {
        return nicknames;
    }

    void setNicknames(StringList _nicknames) {
        nicknames = _nicknames;
    }

    CustList<Numbers<Long>> getScores() {
        return scores;
    }

    void setScores(CustList<Numbers<Long>> _scores) {
        scores = _scores;
    }

    byte getUser() {
        return user;
    }

    void setUser(byte _user) {
        user = _user;
    }

    String getLoc() {
        return loc;
    }

    void setLoc(String _loc) {
        loc = _loc;
    }

    CustList<LineDeal> getLinesDeal() {
        return linesDeal;
    }

    ResultsPresident getResults() {
        return (ResultsPresident) getDataBase();
    }
}
