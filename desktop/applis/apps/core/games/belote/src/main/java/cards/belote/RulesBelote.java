package cards.belote;
import cards.belote.enumerations.BeloteTrumpPartner;
import cards.belote.enumerations.BidBelote;
import cards.belote.enumerations.DealingBelote;
import cards.belote.enumerations.DeclaresBelote;
import cards.consts.MixCardsChoice;
import code.util.EntryCust;
import code.util.EnumList;
import code.util.EnumMap;
import code.util.Ints;


public final class RulesBelote {

    public static final int DIVISIONS = 10;

    private MixCardsChoice mixedCards=MixCardsChoice.EACH_LAUNCHING;
    private EnumMap<DeclaresBelote,Boolean> allowedDeclares = new EnumMap<DeclaresBelote,Boolean>();
    private boolean underTrumpFoe;
    private BeloteTrumpPartner trumpPartner=BeloteTrumpPartner.NO_UNDERTRUMP_NO_OVERTRUMP;
    private EnumMap<BidBelote,Boolean> allowedBids = new EnumMap<BidBelote,Boolean>();
    private DealingBelote dealing = DealingBelote.CLASSIC_2_VS_2;
    private boolean classicCountPoints=true;
    private int nbDeals;
    private String general="";
    private String specific="";

    public RulesBelote() {
        for(DeclaresBelote a:DeclaresBelote.annoncesValides()){
            allowedDeclares.put(a, false);
        }
        for(BidBelote e:BidBelote.values()){
            if(e.getToujoursPossibleAnnoncer()){
                allowedBids.put(e, true);
            }else{
                allowedBids.put(e, false);
            }
        }
    }

    public RulesBelote(RulesBelote _reglesBelote) {
        mixedCards = _reglesBelote.mixedCards;
        allowedDeclares = new EnumMap<DeclaresBelote,Boolean>(_reglesBelote.allowedDeclares);
        underTrumpFoe = _reglesBelote.underTrumpFoe;
        trumpPartner = _reglesBelote.trumpPartner;
        allowedBids = new EnumMap<BidBelote,Boolean>(_reglesBelote.allowedBids);
        dealing = _reglesBelote.dealing;
        classicCountPoints = _reglesBelote.classicCountPoints;
        nbDeals = _reglesBelote.nbDeals;
        setSpecific(_reglesBelote.getSpecific());
        setGeneral(_reglesBelote.getGeneral());
    }
    public boolean isValidRules() {
        for(DeclaresBelote a:DeclaresBelote.annoncesValides()){
            if (!allowedDeclares.contains(a)) {
                return false;
            }
        }
        for(BidBelote b:BidBelote.values()){
            if (!allowedBids.contains(b)) {
                return false;
            }
        }
        return true;
    }

    public boolean dealAll() {
        return dealing.getRemainingCards() == 0;
    }

    public static Ints getPoints() {
        Ints list_ = new Ints();
        int sum_ = HandBelote.pointsTotauxDixDeDer();
        int min_ = sum_ / 2;
        min_ = (min_ / DIVISIONS) * DIVISIONS;
        while (min_ < sum_) {
            list_.add(min_);
            min_ += DIVISIONS;
        }
        list_.add(sum_);
        return list_;
    }

    public MixCardsChoice getCartesBattues() {
        return mixedCards;
    }
    public void setCartesBattues(MixCardsChoice _distribution) {
        mixedCards = _distribution;
    }
    public EnumList<DeclaresBelote> getListeAnnoncesAutorisees() {
        EnumList<DeclaresBelote> l_;
        l_ = new EnumList<DeclaresBelote>();
        for (EntryCust<DeclaresBelote,Boolean> e: allowedDeclares.entryList()) {
            if (e.getValue()) {
                l_.add(e.getKey());
            }
        }
        return l_;
    }
    public EnumMap<DeclaresBelote,Boolean> getAnnoncesAutorisees() {
        return allowedDeclares;
    }
    public void setAnnoncesAutorisees(EnumMap<DeclaresBelote,Boolean> _annoncesAutorisees) {
        allowedDeclares = _annoncesAutorisees;
    }
    public boolean getSousCoupeAdv() {
        return isUnderTrumpFoe();
    }
    public void setSousCoupeAdv(boolean _sousCoupeAdv) {
        setUnderTrumpFoe(_sousCoupeAdv);
    }
    public BeloteTrumpPartner getGestionCoupePartenaire() {
        return getTrumpPartner();
    }
    public void setGestionCoupePartenaire(
            BeloteTrumpPartner _gestionCoupePartenaire) {
        setTrumpPartner(_gestionCoupePartenaire);
    }
    public EnumList<BidBelote> getListeEncheresAutorisees() {
        EnumList<BidBelote> l_;
        l_ = new EnumList<BidBelote>();
        for (EntryCust<BidBelote,Boolean> e: allowedBids.entryList()) {
            if (e.getValue()) {
                l_.add(e.getKey());
            }
        }
        return l_;
    }

    public EnumMap<BidBelote,Boolean> getEncheresAutorisees() {
        return allowedBids;
    }

    public void setEncheresAutorisees(EnumMap<BidBelote,Boolean> _encheresAutorisees) {
        allowedBids = _encheresAutorisees;
    }


    public DealingBelote getRepartition() {
        return dealing;
    }

    public void setRepartition(DealingBelote _repartition) {
        dealing = _repartition;
    }

    public boolean getComptePointsClassique() {
        return isClassicCountPoints();
    }
    public void setComptePointsClassique(boolean _comptePointsClassique) {
        setClassicCountPoints(_comptePointsClassique);
    }

    public int getNombreParties() {
        return nbDeals;
    }
    public void setNombreParties(int _nombreParties) {
        nbDeals = _nombreParties;
    }

    public MixCardsChoice getMixedCards() {
        return mixedCards;
    }

    public void setMixedCards(MixCardsChoice _mixedCards) {
        mixedCards = _mixedCards;
    }

    public EnumMap<DeclaresBelote, Boolean> getAllowedDeclares() {
        return allowedDeclares;
    }

    public void setAllowedDeclares(EnumMap<DeclaresBelote, Boolean> _allowedDeclares) {
        allowedDeclares = _allowedDeclares;
    }

    public boolean isUnderTrumpFoe() {
        return underTrumpFoe;
    }

    public void setUnderTrumpFoe(boolean _underTrumpFoe) {
        underTrumpFoe = _underTrumpFoe;
    }

    public BeloteTrumpPartner getTrumpPartner() {
        return trumpPartner;
    }

    public void setTrumpPartner(BeloteTrumpPartner _trumpPartner) {
        trumpPartner = _trumpPartner;
    }

    public EnumMap<BidBelote, Boolean> getAllowedBids() {
        return allowedBids;
    }

    public void setAllowedBids(EnumMap<BidBelote, Boolean> _allowedBids) {
        allowedBids = _allowedBids;
    }

    public DealingBelote getDealing() {
        return dealing;
    }

    public void setDealing(DealingBelote _dealing) {
        dealing = _dealing;
    }

    public boolean isClassicCountPoints() {
        return classicCountPoints;
    }

    public void setClassicCountPoints(boolean _classicCountPoints) {
        classicCountPoints = _classicCountPoints;
    }

    public int getNbDeals() {
        return nbDeals;
    }

    public void setNbDeals(int _nbDeals) {
        nbDeals = _nbDeals;
    }

    public String getGeneral() {
        return general;
    }

    public void setGeneral(String _general) {
        this.general = _general;
    }

    public String getSpecific() {
        return specific;
    }

    public void setSpecific(String _specific) {
        this.specific = _specific;
    }
}
