package code.maths.montecarlo;
import code.maths.LgInt;
import code.maths.Rate;
import code.util.CollCapacity;
import code.util.CustList;
import code.util.ObjectMap;


public final class MonteCarloNumber extends AbMonteCarlo<Rate> {

    private ObjectMap<Rate,LgInt> law;

    public MonteCarloNumber() {
        setLaw(new ObjectMap<Rate,LgInt>());
    }
    
    public MonteCarloNumber(CollCapacity _capacity) {
        law = new ObjectMap<Rate,LgInt>(_capacity);
    }
    /**Retourne l'esperance d'une loi de probabilite.*/
    public Rate getAvg(){
        Rate sum_ = Rate.zero();
        for(Rate c:events()){
            sum_.addNb(Rate.multiply(c, normalizedRate(c)));
        }
        return sum_;
    }

    /**Retourne l'esperance d'une loi de probabilite en tenant compte du biais du reste.*/
    public Rate getRealAvg(LgInt _max){
        Rate sommeNum_=Rate.zero();
        LgInt sommeDen_=LgInt.zero();
        ObjectMap<Rate,LgInt> law_=new ObjectMap<Rate,LgInt>();
        CustList<Rate> evenements_=events();
        for(Rate c:evenements_){
            sommeDen_.addNb(getLaw().getVal(c));
            law_.put(c, rate(c));
        }
        if (sommeDen_.isZero()) {
            return Rate.zero();
        }
        LgInt plusGdNbAlea_=maxNumber(_max);
        plusGdNbAlea_.increment();
        LgInt quotient_ = LgInt.divide(plusGdNbAlea_, sommeDen_);
        LgInt remain_ = LgInt.remain(plusGdNbAlea_, sommeDen_);
        LgInt sumDenTwo_=LgInt.zero();
        int maxIndice_;
        int nbEvenements_=evenements_.size();
        int i_ = CustList.FIRST_INDEX;
        while (true) {
            sumDenTwo_.addNb(law_.getVal(evenements_.get(i_)));
            if (LgInt.strLower(remain_, sumDenTwo_)) {
                maxIndice_=i_;
                break;
            }
            i_++;
        }
        if (!remain_.isZero()) {
            ObjectMap<Rate,LgInt> lawTwo_=new ObjectMap<Rate,LgInt>();
            for (Rate c:evenements_) {
                lawTwo_.put(c, new LgInt(law_.getVal(c)));
            }
            for (int i = CustList.FIRST_INDEX;i<nbEvenements_;i++){
                LgInt effectif_=lawTwo_.getVal(evenements_.get(i));
                LgInt tmp_=new LgInt(effectif_);
                effectif_.multiplyBy(quotient_);
                if(i<maxIndice_){
                    effectif_.addNb(tmp_);
                } else if (i==maxIndice_) {
                    sumDenTwo_.affectZero();
                    for (int j = CustList.FIRST_INDEX;j<maxIndice_;j++){
                        sumDenTwo_.addNb(law_.getVal(evenements_.get(j)));
                    }
                    effectif_.addNb(LgInt.minus(remain_,sumDenTwo_));
                }
            }
            law_=lawTwo_;
        }
        for(Rate c:evenements_){
            LgInt effectif_=law_.getVal(c);
            sommeNum_.addNb(Rate.multiply(c, new Rate(effectif_)));
        }
        if(!remain_.isZero()){
            return Rate.divide(sommeNum_, new Rate(plusGdNbAlea_));
        }
        return Rate.divide(sommeNum_, new Rate(sommeDen_));
    }

    /**Retourne la variance d'une loi de probabilite.*/
    public Rate getVar(){
        Rate sum_ = Rate.zero();
        for(Rate c:events()){
            Rate res_ = Rate.multiply(c, c);
            sum_.addNb(Rate.multiply(res_, normalizedRate(c)));
        }
        Rate avg_ = getAvg();
        return Rate.minus(sum_, Rate.multiply(avg_, avg_));
    }

    /**Retourne le minimum de la loi de probabilite (soit le plus petit Xi)*/
    public Rate minimum() {
        Rate min_=Rate.zero();
        CustList<Rate> evenements_=events();
        if(evenements_.isEmpty()){
            return min_;
        }
        //Il faut bien initialiser le minimum, car 0 n'est pas forcement inferieur ou egal au minimum
        min_.affect(evenements_.first());
        for(Rate c:evenements_){
            if(Rate.strLower(c, min_)){
                min_.affect(c);
            }
        }
        return min_;
    }

    /**Retourne le maximum de la loi de probabilite (soit le plus petit Xi)*/
    public Rate maximum() {
        Rate max_=Rate.zero();
        CustList<Rate> evenements_=events();
        if(evenements_.isEmpty()){
            return max_;
        }
        //Il faut bien initialiser le maximum, car 0 n'est pas forcement superieur ou egal au maximum
        max_.affect(evenements_.first());
        for(Rate c:evenements_){
            if(Rate.strGreater(c, max_)){
                max_.affect(c);
            }
        }
        return max_;
    }

    public MonteCarloBoolean knowingLower(Rate _event) {
        MonteCarloBoolean loi_ = new MonteCarloBoolean();
        if(!containsEvent(_event)){
            if(Rate.lowerEq(_event, minimum())){
                loi_.addEvent(false,new LgInt(1));
                return loi_;
            }
            loi_.addEvent(true,new LgInt(1));
            return loi_;
        }
        LgInt somme_=LgInt.zero();
        for(Rate e:events()){
            if(Rate.strLower(e, _event)){
                somme_.addNb(rate(e));
            }
        }
        loi_.addEvent(false,rate(_event));
        if(!somme_.isZero()){
            loi_.addEvent(true,somme_);
        }
        return loi_;
    }

    public MonteCarloBoolean knowingGreater(Rate _event) {
        MonteCarloBoolean loi_ = new MonteCarloBoolean();
        if(!containsEvent(_event)){
            if(Rate.greaterEq(_event, maximum())){
                loi_.addEvent(false,new LgInt(1));
                return loi_;
            }
            loi_.addEvent(true,new LgInt(1));
            return loi_;
        }
        LgInt somme_=LgInt.zero();
        for(Rate e:events()){
            if(Rate.strGreater(e, _event)){
                somme_.addNb(rate(e));
            }
        }
        loi_.addEvent(false,rate(_event));
        if(!somme_.isZero()){
            loi_.addEvent(true,somme_);
        }
        return loi_;
    }

    @Override
    public ObjectMap<Rate,LgInt> getLaw() {
        return law;
    }

    public void setLaw(ObjectMap<Rate, LgInt> _law) {
        law = _law;
    }
}
