package code.util.pagination;
import code.util.CustList;
import code.util.EnumMap;

public final class EnumFieldComparator<E extends Enum<E>> {

    private SelectedBoolean increasing = SelectedBoolean.YES_AND_NO;

    private int priority;

    private EnumMap<E,String> translations;

    public int compare(E _o1, E _o2) {
        if (increasing == SelectedBoolean.YES) {
            return translations.getVal(_o1).compareTo(translations.getVal(_o2));
        }
        if (increasing == SelectedBoolean.NO) {
            return translations.getVal(_o2).compareTo(translations.getVal(_o1));
        }
        return CustList.EQ_CMP;
    }

    public SelectedBoolean getIncreasing() {
        return increasing;
    }

    public void setIncreasing(SelectedBoolean _increasing) {
        increasing = _increasing;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int _priority) {
        priority = _priority;
    }

    public EnumMap<E, String> getTranslations() {
        return translations;
    }

    public void setTranslations(EnumMap<E, String> _translations) {
        translations = _translations;
    }
}
