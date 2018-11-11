package aiki.map.pokemon;
import aiki.DataBase;
import code.sml.FromAndToString;
import code.util.StringList;
import code.util.ints.Displayable;

public final class Egg implements UsablePokemon, Displayable {

    static final char SEPARATOR = ';';

    /**name du pokemon a donner lors de l'eclosion*/
    private final String name;

    /**nombre de pas depuis l'obtention de l'oeuf.*/
    private int steps;

//    private Egg() {
//    }

    /**@param _name the name of the pokemon which will hatch of its egg*/
    public Egg(String _name) {
        StringList list_ = StringList.splitChars(_name, SEPARATOR);
        name = list_.first();
        String steps_ = list_.last();
        if (StringList.isNumber(steps_)) {
            steps = Integer.parseInt(steps_);
        }
    }

    @FromAndToString
    public static Egg newEgg(String _string) {
        return new Egg(_string);
    }

//    @FromAndToString
//    public static Egg newEgg(String _string) {
//        StringList list_ = new StringList(_string.split(SEPARATOR));
//        Egg egg_ = new Egg();
//        egg_.name = list_.first();
//        egg_.steps = Integer.parseInt(list_.last());
//        return egg_;
//    }

//    @FromAndToString
//    public String write() {
//        return name+SEPARATOR+steps;
//    }

    public void versEclosion(short _nb) {
        steps += _nb;
    }

    @Override
    public boolean validate(DataBase _data) {
        if (!_data.getPokedex().contains(name)) {
            return false;
        }
        if (steps < 0) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public int getSteps() {
        return steps;
    }

    @FromAndToString
    @Override
    public String display() {
        StringBuilder str_ = new StringBuilder(name);
        str_.append(SEPARATOR);
        str_.append(steps);
        return str_.toString();
    }
}
