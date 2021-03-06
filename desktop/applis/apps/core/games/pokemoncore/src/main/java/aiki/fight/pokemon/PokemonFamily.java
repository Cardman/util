package aiki.fight.pokemon;
import aiki.db.DataBase;
import code.util.CustList;
import code.util.StringList;
import code.util.core.StringUtil;

public final class PokemonFamily {

    private final CustList<StringList> stages = new CustList<StringList>();

    public PokemonFamily(DataBase _data, String _pokemonBase) {
        stages.add(new StringList(_pokemonBase));
        StringList evolutionsLevels_ = new StringList();
        StringList currentEvolutions_ = new StringList();
        currentEvolutions_.add(_pokemonBase);
        evolutionsLevels_.add(_pokemonBase);
        StringList newEvolutions_;
        while (true) {
            newEvolutions_ = new StringList();
            for (String e: currentEvolutions_) {
                PokemonData fPk_ = _data.getPokemon(e);
                if (fPk_ == null) {
                    continue;
                }
                for (String e_: fPk_.getEvolutions().getKeys()) {
                    PokemonData evo_ = _data.getPokemon(e_);
                    if (evo_ == null) {
                        continue;
                    }
                    if (!StringUtil.quickEq(evo_.getBaseEvo(), _pokemonBase)) {
                        _data.setError(true);
                    }
                    if (StringUtil.contains(evolutionsLevels_, e_)) {
                        _data.setError(true);
                        return;
                    }
                    if (StringUtil.contains(newEvolutions_, e_)) {
                        _data.setError(true);
                        return;
                    }
                    newEvolutions_.add(e_);
                }
            }
            if (newEvolutions_.isEmpty()) {
                break;
            }
            stages.add(newEvolutions_);
            evolutionsLevels_.addAllElts(newEvolutions_);
            currentEvolutions_ = new StringList(newEvolutions_);
        }
    }

    public CustList<StringList> getStages() {
        return stages;
    }

    public StringList getAllPokemon() {
        StringList list_ = new StringList();
        for (StringList s: stages) {
            list_.addAllElts(s);
        }
        return list_;
    }
}
