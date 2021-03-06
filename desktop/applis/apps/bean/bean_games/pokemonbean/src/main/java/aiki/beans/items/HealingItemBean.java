package aiki.beans.items;
import aiki.comparators.ComparatorTrStrings;
import aiki.db.DataBase;
import aiki.fight.items.HealingItem;
import code.util.StringMap;
import code.util.TreeMap;

public class HealingItemBean extends ItemBean {
    static final String HEALING_ITEM_BEAN="web/html/items/healingitem.html";
    private TreeMap<String, Short> happiness;
    private boolean healingTeam;

    @Override
    public void beforeDisplaying() {
        beforeDisplayingItem();
        DataBase data_ = (DataBase) getDataBase();
        HealingItem item_ = (HealingItem) getItem();
        healingTeam = item_.getHealingTeam();
        StringMap<String> translatedItems_ = data_.getTranslatedItems().getVal(getLanguage());
        TreeMap<String, Short> happiness_;
        happiness_ = new TreeMap<String, Short>(new ComparatorTrStrings(translatedItems_));
        for (String i: item_.getHappiness().getKeys()) {
            happiness_.put(i, item_.getHappiness().getVal(i));
        }
        happiness = happiness_;
    }
    public boolean isBall(int _index) {
        String item_ = happiness.getKey(_index);
        return !item_.isEmpty();
    }
    public String getTrHappiness(int _index) {
        DataBase data_ = (DataBase) getDataBase();
        StringMap<String> translatedItems_ = data_.getTranslatedItems().getVal(getLanguage());
        String item_ = happiness.getKey(_index);
        return translatedItems_.getVal(item_);
    }
    public String clickHappiness(int _index) {
        String item_ = happiness.getKey(_index);
        getForms().put(CST_ITEM, item_);
        return CST_BALL;
    }

    public boolean getHealingTeam() {
        return healingTeam;
    }

    public TreeMap<String,Short> getHappiness() {
        return happiness;
    }
}