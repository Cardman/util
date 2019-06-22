package cards.belote;
import cards.consts.GameType;
import cards.consts.ResultsGame;
import code.maths.LgInt;
import code.maths.Rate;
import code.util.*;


public final class ResultsBelote {
    private ResultsGame res = new ResultsGame();
    private GameBelote game;

    private StringList nicknames;

    private byte user;

    private String loc;

    public void initialize(StringList _pseudos, CustList<Longs> _scores) {
        res.setScores(_scores);
        nicknames = _pseudos;
        Shorts scoresDeal_ = new Shorts();
        byte nombreJoueurs_=game.getNombreDeJoueurs();
        BidBeloteSuit bid_ = game.getContrat();
        if(bid_.jouerDonne()) {
            int pointsAttaqueTemporaire_;
            int pointsAttaqueDefinitif_;
            int pointsDefenseTemporaire_;
            int pointsDefenseDefinitif_;
            EndBeloteGame end_ = game.getEndBeloteGame();
            pointsAttaqueTemporaire_ = end_.pointsAttackWithBonus();
            pointsDefenseTemporaire_ = end_.pointsDefenseWithBonus();
            pointsAttaqueDefinitif_=end_.scoreDefinitifAttaque(pointsAttaqueTemporaire_, pointsDefenseTemporaire_);
            pointsDefenseDefinitif_=end_.scoreDefinitifDefense(pointsAttaqueDefinitif_,pointsDefenseTemporaire_);
            game.setScores(end_.scores(pointsAttaqueDefinitif_, pointsDefenseDefinitif_));
            scoresDeal_=game.getScores();
        }
        if(game.getType()==GameType.RANDOM&&game.getNombre()==0 || game.getType() == GameType.EDIT && game.getNombre() <= game.getRegles().getNombreParties()) {
            long variance9_=0;
            long esperance_=0;
            res.getScores().add(new Longs());
            if(res.getScores().size()==1) {
                for(short score_:scoresDeal_) {
                    res.getScores().last().add((long)score_);
                }
            } else {
                byte indice_=0;
                for(short score_:scoresDeal_) {
                    res.getScores().last().add(score_+res.getScores().get(res.getScores().size()-2).get(indice_));
                    indice_++;
                }
            }
            for(long score_:res.getScores().last()) {
                esperance_+=score_;
            }
            /*Somme des_ scores*/
            variance9_+=3*esperance_;
            /*Somme des_ scores fois_ trois_*/
            variance9_*=variance9_;
            /*Carre de_ la_ somme_ des_ scores fois_ trois_ (Le carre_ comprend_ le_ fois_ trois_)*/
            variance9_=-variance9_;
            /*Oppose du_ carre_ de_ la_ somme_ des_ scores fois_ trois_*/
            for(long score_:res.getScores().last()) {
                variance9_+=score_*score_*9*nombreJoueurs_;
            }
            /*variance9_ vaut_ neuf_ fois_ la_ variance_ des_ scores fois_ le_ carre_ du_ nombre_ de_ joueurs_*/
            res.getSigmas().add(new Rate(variance9_,nombreJoueurs_*nombreJoueurs_).rootAbs(new LgInt(2)));
            res.getSums().add(esperance_);
        }
    }
    public GameBelote getGame() {
        return game;
    }
    public void setGame(GameBelote _game) {
        game = _game;
    }
    public StringList getNicknames() {
        return nicknames;
    }
    public void setNicknames(StringList _nicknames) {
        nicknames = _nicknames;
    }
    public byte getUser() {
        return user;
    }
    public void setUser(byte _user) {
        user = _user;
    }
    public String getLoc() {
        return loc;
    }
    public void setLoc(String _loc) {
        loc = _loc;
    }

    public String getGlobalResultsPageTitle() {
        return res.getGlobalResultsPageTitle();
    }

    public void setGlobalResultsPageTitle(String _globalResultsPageTitle) {
        res.setGlobalResultsPageTitle(_globalResultsPageTitle);
    }

    public String getDetailResultsTitle() {
        return res.getDetailResultsTitle();
    }

    public void setDetailResultsTitle(String _detailResultsTitle) {
        res.setDetailResultsTitle(_detailResultsTitle);
    }

    public StringMap<String> getRenderedPages() {
        return res.getRenderedPages();
    }

    public EqList<Rate> getSigmas() {
        return res.getSigmas();
    }

    public Longs getSums() {
        return res.getSums();
    }

    public CustList<Longs> getScores() {
        return res.getScores();
    }

    public void setScores(CustList<Longs> _scores) {
        res.setScores(_scores);
    }

    public void setRenderedPages(StringMap<String> _renderedPages) {
        res.setRenderedPages(_renderedPages);
    }

    public void setSigmas(EqList<Rate> _sigmas) {
        res.setSigmas(_sigmas);
    }

    public void setSums(Longs _sums) {
        res.setSums(_sums);
    }
}
