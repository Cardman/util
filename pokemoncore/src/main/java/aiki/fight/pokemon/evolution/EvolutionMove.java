package aiki.fight.pokemon.evolution;

import aiki.DataBase;
import aiki.fight.pokemon.PokemonData;
import code.util.StringList;
import code.util.annot.RwXml;

@RwXml
public final class EvolutionMove extends Evolution {

    private String move;

    @Override
    public void validate(DataBase _dataBase, PokemonData _fPk) {
        if (StringList.quickEq(move, _dataBase.getDefaultMove())) {
            _dataBase.setError(true);
            return;

        }
        if (!_fPk.getMoveTutors().containsObj(move)) {
            _dataBase.setError(true);
            return;

        }
    }

    public String getMove() {
        return move;
    }

    public void setMove(String _move) {
        move = _move;
    }
}
