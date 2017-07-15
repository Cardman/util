package aiki.fight.pokemon.evolution;
import code.util.StringList;
import code.util.annot.RwXml;
import aiki.DataBase;
import aiki.exceptions.DataException;
import aiki.fight.pokemon.PokemonData;

@RwXml
public class EvolutionMoveType extends Evolution {

    private String type;

    @Override
    public void validate(DataBase _dataBase,PokemonData _fPk) {
        StringList types_ = new StringList();
        for (String m: _fPk.getMoveTutors()) {
            types_.addAllElts(_dataBase.getMove(m).getTypes());
        }
        if (!types_.containsObj(type)) {
            throw new DataException();
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String _type) {
        type = _type;
    }
}
