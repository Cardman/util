package aiki.map.places;
import aiki.DataBase;
import aiki.map.characters.Person;
import aiki.map.levels.Level;
import aiki.map.tree.PlaceArea;
import aiki.util.Coords;
import code.datacheck.CheckedData;
import code.util.CustList;
import code.util.NumberMap;

@CheckedData
public abstract class Place {

    public boolean hasValidImage(DataBase _data) {
        for (Level l : getLevels().values()) {
            if (!l.hasValidImage(_data)) {
                return false;
            }
        }
        return true;
    }

    public abstract void validate(DataBase _data,PlaceArea _placeArea);
    public abstract boolean isEmptyForAdding(Coords _coords);
    public abstract void validateForEditing(DataBase _data);
    public abstract Level getLevelByCoords(Coords _coords);
    public abstract NumberMap<Byte,? extends Level> getLevels();
    public abstract CustList<? extends Level> getLevelsList();
    public abstract Person getPerson(Coords _coords);
    public abstract boolean containsPerson(Coords _coords);
    public abstract void setName(String _name);
    public abstract String getName();
}
