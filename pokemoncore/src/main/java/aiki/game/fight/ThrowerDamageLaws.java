package aiki.game.fight;
import code.maths.Rate;
import code.maths.montecarlo.MonteCarloNumber;
import code.util.ObjectMap;

public class ThrowerDamageLaws {

    private ObjectMap<TeamPosition,MonteCarloNumber> randomBase;

    private ObjectMap<TeamPosition,MonteCarloNumber> criticalHit;

    private MonteCarloNumber randomRate;

    private ObjectMap<TeamPosition,MonteCarloNumber> numberHits;

    public Rate min(TeamPosition _fighter) {
        Rate min_ = Rate.multiply(randomBase.getVal(_fighter).minimum(), criticalHit.getVal(_fighter).minimum());
        min_.multiplyBy(randomRate.minimum());
        min_.multiplyBy(numberHits.getVal(_fighter).minimum());
        return min_;
    }

    public Rate max(TeamPosition _fighter) {
        Rate min_ = Rate.multiply(randomBase.getVal(_fighter).maximum(), criticalHit.getVal(_fighter).maximum());
        min_.multiplyBy(randomRate.maximum());
        min_.multiplyBy(numberHits.getVal(_fighter).maximum());
        return min_;
    }

    public Rate avg(TeamPosition _fighter) {
        Rate min_ = Rate.multiply(randomBase.getVal(_fighter).getAvg(), criticalHit.getVal(_fighter).getAvg());
        min_.multiplyBy(randomRate.getAvg());
        min_.multiplyBy(numberHits.getVal(_fighter).getAvg());
        return min_;
    }

    public Rate var(TeamPosition _fighter) {
        Rate avgRandomBase_ = randomBase.getVal(_fighter).getAvg();
        Rate varRandomBase_ = randomBase.getVal(_fighter).getVar();
        Rate avgCriticalHit_ = criticalHit.getVal(_fighter).getAvg();
        Rate varCriticalHit_ = criticalHit.getVal(_fighter).getVar();
        Rate avgRandomRate_ = randomRate.getAvg();
        Rate varRandomRate_ = randomRate.getVar();
        Rate avgNumberHits_ = numberHits.getVal(_fighter).getAvg();
        Rate varNumberHits_ = numberHits.getVal(_fighter).getVar();
        Rate var_ = varFromIndependantLaws(varRandomBase_, avgRandomBase_, varCriticalHit_, avgCriticalHit_);
        Rate avg_ = Rate.multiply(avgRandomBase_, avgCriticalHit_);
        var_ = varFromIndependantLaws(varRandomRate_, avgRandomRate_, var_, avg_);
        avg_ = Rate.multiply(avg_, avgRandomRate_);
        return varFromIndependantLaws(varNumberHits_, avgNumberHits_, var_, avg_);
    }

    static Rate varFromIndependantLaws(Rate _var1, Rate _avg1,Rate _var2,Rate _avg2){
        Rate var_=Rate.zero();
        var_.addNb(Rate.multiply(_var1, _var2));
        var_.addNb(Rate.multiply(Rate.multiply(_avg2, _avg2),_var1));
        var_.addNb(Rate.multiply(Rate.multiply(_avg1, _avg1),_var2));
        return var_;
    }

    public ObjectMap<TeamPosition,MonteCarloNumber> getBase() {
        return randomBase;
    }

    public void setBase(ObjectMap<TeamPosition,MonteCarloNumber> _randomBase) {
        randomBase = _randomBase;
    }

    public ObjectMap<TeamPosition,MonteCarloNumber> getCriticalHit() {
        return criticalHit;
    }

    public void setCriticalHit(ObjectMap<TeamPosition,MonteCarloNumber> _criticalHit) {
        criticalHit = _criticalHit;
    }

    public MonteCarloNumber getRandomRate() {
        return randomRate;
    }

    public void setRandomRate(MonteCarloNumber _randomRate) {
        randomRate = _randomRate;
    }

    public ObjectMap<TeamPosition,MonteCarloNumber> getNumberHits() {
        return numberHits;
    }

    public void setNumberHits(ObjectMap<TeamPosition,MonteCarloNumber> _numberHits) {
        numberHits = _numberHits;
    }
}
