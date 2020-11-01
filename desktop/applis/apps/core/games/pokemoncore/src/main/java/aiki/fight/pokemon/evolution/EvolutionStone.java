package aiki.fight.pokemon.evolution;

import aiki.db.DataBase;
import aiki.fight.items.EvolvingStone;
import aiki.fight.pokemon.PokemonData;


public abstract class EvolutionStone extends Evolution {

    private String stone;

    protected final void validateEvolutionStone(DataBase _dataBase) {
        if (_dataBase.getItem(stone) instanceof EvolvingStone) {
            return;
        }
        _dataBase.setError(true);

    }

    public String getStone() {
        return stone;
    }

    public void setStone(String _stone) {
        stone = _stone;
    }
}
