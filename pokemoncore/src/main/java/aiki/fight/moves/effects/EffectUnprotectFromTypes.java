package aiki.fight.moves.effects;

import aiki.db.DataBase;
import aiki.fight.util.TypesDuo;
import code.util.EqList;
import code.util.StringList;


public final class EffectUnprotectFromTypes extends Effect {

    private EqList<TypesDuo> types;

    private StringList disableImmuAgainstTypes;
    private StringList disableImmuFromMoves;
    private StringList attackTargetWithTypes;

    @Override
    public void validate(DataBase _data) {
        super.validate(_data);
        for (TypesDuo t : types) {
            if (!_data.getTypes().containsObj(t.getDamageType())) {
                _data.setError(true);
                return;

            }
            if (!_data.getTypes().containsObj(t.getPokemonType())) {
                _data.setError(true);
                return;

            }
        }
        if (!_data.getTypes().containsAllObj(disableImmuAgainstTypes)) {
            _data.setError(true);
            return;

        }
        if (!_data.getMoves().containsAllAsKeys(disableImmuFromMoves)) {
            _data.setError(true);
            return;

        }
        if (!_data.getTypes().containsAllObj(attackTargetWithTypes)) {
            _data.setError(true);
            return;

        }
    }

    public EqList<TypesDuo> getTypes() {
        return types;
    }

    public void setTypes(EqList<TypesDuo> _types) {
        types = _types;
    }

    public StringList getDisableImmuAgainstTypes() {
        return disableImmuAgainstTypes;
    }

    public void setDisableImmuAgainstTypes(StringList _disableImmuAgainstTypes) {
        disableImmuAgainstTypes = _disableImmuAgainstTypes;
    }

    public StringList getDisableImmuFromMoves() {
        return disableImmuFromMoves;
    }

    public void setDisableImmuFromMoves(StringList _disableImmuFromMoves) {
        disableImmuFromMoves = _disableImmuFromMoves;
    }

    public StringList getAttackTargetWithTypes() {
        return attackTargetWithTypes;
    }

    public void setAttackTargetWithTypes(StringList _attackTargetWithTypes) {
        attackTargetWithTypes = _attackTargetWithTypes;
    }

}
