package aiki.beans.simulation;
import code.bean.Accessible;
import code.images.ConverterBufferedImage;
import code.util.CustList;
import code.util.EnumMap;
import code.util.StringList;
import code.util.StringMap;
import code.util.TreeMap;
import code.util.pagination.SelectedBoolean;
import aiki.DataBase;
import aiki.beans.CommonBean;
import aiki.beans.facade.dto.PokemonLine;
import aiki.comparators.ComparatorTrStringBoolean;
import aiki.comparators.ComparatorTrStrings;
import aiki.fight.pokemon.PokemonData;
import aiki.fight.pokemon.enums.GenderRepartition;

public class SelectPokemonBean extends CommonBean {

    @Accessible
    private CustList<PokemonLine> pokedex = new CustList<PokemonLine>();

    @Accessible
    private String typedName = DataBase.EMPTY_STRING;

    @Accessible
    private String typedType = DataBase.EMPTY_STRING;

    @Accessible
    private SelectedBoolean hasEvo = SelectedBoolean.YES_AND_NO;

    @Accessible
    private SelectedBoolean isEvo = SelectedBoolean.YES_AND_NO;

    @Accessible
    private SelectedBoolean isLeg = SelectedBoolean.YES_AND_NO;

    @Accessible
    private boolean wholeWord;

    @Accessible
    private TreeMap<SelectedBoolean,String> booleans;

    @Override
    public void beforeDisplaying() {
        DataBase data_ = (DataBase) getDataBase();
        EnumMap<SelectedBoolean,String> translatedBooleans_;
        translatedBooleans_ = data_.getTranslatedBooleans().getVal(getLanguage());
        booleans = new TreeMap<SelectedBoolean, String>(new ComparatorTrStringBoolean(translatedBooleans_));
        for (SelectedBoolean s: translatedBooleans_.getKeys()) {
            booleans.put(s, translatedBooleans_.getVal(s));
        }
        StringList pokedex_ = (StringList) getForms().getVal(POKEMON_SET);
        pokedex.clear();
        StringMap<String> translationsPk_;
        translationsPk_ = data_.getTranslatedPokemon().getVal(getLanguage());
        StringMap<String> translationsTypes_;
        translationsTypes_ = data_.getTranslatedTypes().getVal(getLanguage());
        for (String k: pokedex_) {
            PokemonData pkData_ = data_.getPokedex().getVal(k);
            PokemonLine line_ = new PokemonLine();
            line_.setName(k);
            line_.setDisplayName(translationsPk_.getVal(k));
            StringList types_ = new StringList();
            for (String t: pkData_.getTypes()) {
                types_.add(translationsTypes_.getVal(t));
            }
            line_.setTypes(types_);
            line_.setEvolutions(pkData_.getEvolutions().size());
            pokedex.add(line_);
        }
        typedName = escapedStringQuote(typedName);
        typedType = escapedStringQuote(typedType);
    }

    @Accessible
    private static String cancel() {
        return POKEMON;
    }

    @Accessible
    private String search() {
        DataBase data_ = (DataBase) getDataBase();
        StringMap<String> translationsPk_;
        translationsPk_ = data_.getTranslatedPokemon().getVal(getLanguage());
        StringMap<String> translationsTypes_;
        translationsTypes_ = data_.getTranslatedTypes().getVal(getLanguage());
        StringList pokedex_ = new StringList();
        for (String k: data_.getPokedex().getKeys()) {
            String displayName_ = translationsPk_.getVal(k);
            if (!StringList.match(displayName_, typedName)) {
                continue;
            }
            PokemonData pkData_ = data_.getPokedex().getVal(k);
            boolean atLeastMatchType_ = false;
            for (String t: pkData_.getTypes()) {
                String displayType_;
                displayType_ = translationsTypes_.getVal(t);
                if (wholeWord) {
                    if (typedType == null) {
                        continue;
                    }
                    if (!StringList.quickEq(displayType_, typedType)) {
                        continue;
                    }
                } else {
                    if (!StringList.match(displayType_, typedType)) {
                        continue;
                    }
                }
                atLeastMatchType_ = true;
            }
            if (!atLeastMatchType_) {
                continue;
            }
            if (hasEvo != SelectedBoolean.YES_AND_NO) {
                if (pkData_.getEvolutions().isEmpty() && hasEvo.isSelected()) {
                    continue;
                }
                if (!pkData_.getEvolutions().isEmpty() && !hasEvo.isSelected()) {
                    continue;
                }
            }
            if (isEvo != SelectedBoolean.YES_AND_NO) {
                boolean isBaseEvo_ = StringList.quickEq(k, pkData_.getBaseEvo());
                if (isBaseEvo_ && isEvo.isSelected()) {
                    continue;
                }
                if (!isBaseEvo_ && !isEvo.isSelected()) {
                    continue;
                }
            }
            if (isLeg != SelectedBoolean.YES_AND_NO) {
                boolean isLeg_ = pkData_.getGenderRep() == GenderRepartition.LEGENDARY;
                if (isLeg_ && !isLeg.isSelected()) {
                    continue;
                }
                if (!isLeg_ && isLeg.isSelected()) {
                    continue;
                }
            }
            pokedex_.add(k);
        }
        pokedex_.sortElts(new ComparatorTrStrings(translationsPk_));
        getForms().put(POKEMON_SET, pokedex_);
        if (pokedex_.size() == DataBase.ONE_POSSIBLE_CHOICE) {
            getForms().put(POKEMON_NAME_EDIT,pokedex_.first());
            return POKEMON;
        }
        return POKEMON_SET;
    }

    @Accessible
    private String getMiniImage(Long _number) {
        String name_ = pokedex.get(_number.intValue()).getName();
        DataBase data_ = (DataBase) getDataBase();
//        return ConverterBufferedImage.toBaseSixtyFour(data_.getMiniPk().getVal(name_));
        return ConverterBufferedImage.surroundImage(data_.getMiniPk().getVal(name_));
        //return ConverterBufferedImage.toBaseSixtyFour(data_.getMiniPk().getVal(name_));
    }

    @Accessible
    private String clickLink(Long _number) {
        getForms().put(POKEMON_NAME_EDIT,pokedex.get(_number.intValue()).getName());
        return POKEMON;
    }
}
