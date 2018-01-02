package cards.tarot.enumerations;
import code.format.Format;
import code.util.consts.Constants;
import code.util.ints.Displayable;

/**Miseres utilisees au tarot*/
public enum Miseres implements Displayable {
    TRUMP(10),POINT(10),CHARACTER(5),SUIT(30),LOW_CARDS(20);
    private final int points;
    Miseres(int _points){
        points = _points;
    }
    public int getPoints(){
        return points;
    }

    public String toString(String _locale) {
        return Format.getConstanteLangue(ResoucesAccess.NOM_DOSSIER,ResoucesAccess.NOM_FICHIER, _locale, ResoucesAccess.TAROT_MISERES, name());
    }
    @Override
    public String display() {
        return toString(Constants.getLanguage());
    }
}
