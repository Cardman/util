package aiki.fight.items;
import code.datacheck.CheckedData;

@CheckedData
public final class SellingItem extends Item {

    private static final String ITEM = "dbpokemon.fight.items.SellingItem";

    @Override
    public String getItemType() {
        return ITEM;
    }

}
