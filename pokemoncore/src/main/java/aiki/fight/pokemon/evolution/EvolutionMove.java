package aiki.fight.pokemon.evolution;
import aiki.DataBase;
import aiki.exceptions.DataException;
import aiki.fight.pokemon.PokemonData;
import code.util.StringList;
import code.util.annot.RwXml;


@RwXml
public final class EvolutionMove extends Evolution {

    private String move;

    @Override
    public void validate(DataBase _dataBase,PokemonData _fPk) {
        if (StringList.quickEq(move,_dataBase.getDefaultMove())) {
            throw new DataException();
        }
        if (!_fPk.getMoveTutors().containsObj(move)) {
            throw new DataException();
        }
    }

    public String getMove() {
        return move;
    }

    public void setMove(String _move) {
        move = _move;
    }
}
