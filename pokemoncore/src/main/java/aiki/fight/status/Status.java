package aiki.fight.status;

import aiki.DataBase;
import aiki.fight.enums.Statistic;
import aiki.fight.moves.effects.EffectEndRoundStatus;
import aiki.fight.status.effects.EffectPartnerStatus;
import code.maths.Rate;
import code.util.CustList;
import code.util.EnumMap;
import code.util.annot.RwXml;

@RwXml
public abstract class Status {

    private StatusType statusType;
    private Rate catchingRate;
    private CustList<EffectEndRoundStatus> effectEndRound;
    private CustList<EffectPartnerStatus> effectsPartner;
    private boolean disabledEffIfSwitch;
    private int incrementEndRound;
    private boolean incrementingEndRound;
    private EnumMap<Statistic, Rate> multStat;
    private String fail;

    public void validate(DataBase _data) {
        if (statusType == null) {
            _data.setError(true);
            return;

        }
        if (!Statistic.getStatisticsWithBoost().containsAllObj(
                multStat.getKeys())) {
            _data.setError(true);
            return;

        }
        for (Rate v : multStat.values()) {
            if (!v.isZeroOrGt()) {
                _data.setError(true);
                return;

            }
            if (v.isZero()) {
                _data.setError(true);
                return;

            }
        }
        if (!catchingRate.isZeroOrGt()) {
            _data.setError(true);
            return;

        }
        if (catchingRate.isZero()) {
            _data.setError(true);
            return;

        }
        if (effectEndRound.size() > 1) {
            _data.setError(true);
            return;

        }
        if (effectsPartner.size() > 1) {
            _data.setError(true);
            return;

        }
        if (!effectEndRound.isEmpty()) {
            effectEndRound.first().validate(_data);
        }
        if (!effectEndRound.isEmpty()) {
            if (incrementEndRound == effectEndRound.first().getEndRoundRank()) {
                _data.setError(true);
                return;

            }
        }
        if (!effectsPartner.isEmpty()) {
            effectsPartner.first().validate(_data);
        }
    }

    public void validateForEditing() {
        if (effectEndRound.size() > 1) {
            EffectEndRoundStatus e_ = effectEndRound.first();
            effectEndRound.clear();
            effectEndRound.add(e_);
        }
        if (effectsPartner.size() > 1) {
            EffectPartnerStatus e_ = effectsPartner.first();
            effectsPartner.clear();
            effectsPartner.add(e_);
        }
    }

    public Rate rateHealtHpPartner() {
        if (effectsPartner.isEmpty()) {
            return Rate.zero();
        }
        EffectPartnerStatus effetPart_ = effectsPartner.first();
        return effetPart_.getRestoredHpRateLovedAlly();
    }

    public boolean estActifPartenaire() {
        return !effectsPartner.isEmpty();
    }

    public StatusType getStatusType() {
        return statusType;
    }

    public void setStatusType(StatusType _statusType) {
        statusType = _statusType;
    }

    public Rate getCatchingRate() {
        return catchingRate;
    }

    public void setCatchingRate(Rate _catchingRate) {
        catchingRate = _catchingRate;
    }

    public CustList<EffectEndRoundStatus> getEffectEndRound() {
        return effectEndRound;
    }

    public void setEffectEndRound(CustList<EffectEndRoundStatus> _effectEndRound) {
        effectEndRound = _effectEndRound;
    }

    public CustList<EffectPartnerStatus> getEffectsPartner() {
        return effectsPartner;
    }

    public void setEffectsPartner(CustList<EffectPartnerStatus> _effectsPartner) {
        effectsPartner = _effectsPartner;
    }

    public boolean getDisabledEffIfSwitch() {
        return disabledEffIfSwitch;
    }

    public void setDisabledEffIfSwitch(boolean _disabledEffIfSwitch) {
        disabledEffIfSwitch = _disabledEffIfSwitch;
    }

    public boolean getIncrementingEndRound() {
        return incrementingEndRound;
    }

    public void setIncrementingEndRound(boolean _incrementingEndRound) {
        incrementingEndRound = _incrementingEndRound;
    }

    public int getIncrementEndRound() {
        return incrementEndRound;
    }

    public void setIncrementEndRound(int _incrementEndRound) {
        incrementEndRound = _incrementEndRound;
    }

    public EnumMap<Statistic, Rate> getMultStat() {
        return multStat;
    }

    public void setMultStat(EnumMap<Statistic, Rate> _multStat) {
        multStat = _multStat;
    }

    public String getFail() {
        return fail;
    }

    public void setFail(String _fail) {
        fail = _fail;
    }
}
