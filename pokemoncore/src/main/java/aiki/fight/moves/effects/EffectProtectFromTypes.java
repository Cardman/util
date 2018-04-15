package aiki.fight.moves.effects;

import aiki.DataBase;
import code.util.StringList;
import code.util.annot.RwXml;

@RwXml
public final class EffectProtectFromTypes extends Effect {

    private StringList immuAgainstTypes;

    @Override
    public void validate(DataBase _data) {
        super.validate(_data);
        if (immuAgainstTypes.isEmpty()) {
            _data.setError(true);
            return;

        }
        if (!_data.getTypes().containsAllObj(immuAgainstTypes)) {
            _data.setError(true);
            return;

        }
    }

    public StringList getImmuAgainstTypes() {
        return immuAgainstTypes;
    }

    public void setImmuAgainstTypes(StringList _immuAgainstTypes) {
        immuAgainstTypes = _immuAgainstTypes;
    }

}
