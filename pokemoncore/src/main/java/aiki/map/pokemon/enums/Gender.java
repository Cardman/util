package aiki.map.pokemon.enums;
import code.datacheck.CheckedData;
import code.util.EnumList;
import code.util.ints.Listable;

@CheckedData
public enum Gender {
    FEMALE,MALE,NO_GENDER;
    public static EnumList<Gender> getGendersWithSex() {
        EnumList<Gender> genders_ = new EnumList<Gender>();
        genders_.add(FEMALE);
        genders_.add(MALE);
        return genders_;
    }
    public static boolean equalsSet(Listable<Gender> _list1,Listable<Gender> _list2) {
        for (Gender a: _list2) {
            boolean contains_ = false;
            for (Gender b: _list1) {
                if (a == b) {
                    contains_ = true;
                    break;
                }
            }
            if (!contains_) {
                return false;
            }
        }
        for (Gender a: _list1) {
            boolean contains_ = false;
            for (Gender b: _list2) {
                if (a == b) {
                    contains_ = true;
                    break;
                }
            }
            if (!contains_) {
                return false;
            }
        }
        return true;
    }
}
