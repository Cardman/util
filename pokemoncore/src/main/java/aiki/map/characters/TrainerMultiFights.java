package aiki.map.characters;
import aiki.DataBase;
import aiki.exceptions.DataException;
import aiki.map.pokemon.PokemonTeam;
import code.util.CustList;
import code.util.annot.RwXml;

@RwXml
public class TrainerMultiFights extends Trainer implements CharacterInRoadCave, Fightable {

    private CustList<PokemonTeam> teamsRewards;

    @Override
    public void validate(DataBase _data) {
        validate();
        for (PokemonTeam p: teamsRewards) {
            p.validate(_data);
        }
        if (teamsRewards.isEmpty()) {
            throw new DataException();
        }
    }

    @Override
    public void validateForEditing(DataBase _data) {
        super.validateForEditing(_data);
        CustList<PokemonTeam> valid_ = new CustList<PokemonTeam>();
        for (PokemonTeam p: teamsRewards) {
            valid_.add(p);
        }
        teamsRewards.clear();
        teamsRewards.addAllElts(valid_);
    }

    public CustList<PokemonTeam> getTeamsRewards() {
        return teamsRewards;
    }

    public void setTeamsRewards(CustList<PokemonTeam> _teamsRewards) {
        teamsRewards = _teamsRewards;
    }

    @Override
    public int getMult() {
        return getMultiplicityFight();
    }

}
