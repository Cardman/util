package code.sys;

import cards.belote.enumerations.CardBelote;
import cards.consts.Suit;
import cards.main.CardFactories;
import cards.main.LaunchingCards;
import cards.president.enumerations.CardPresident;
import cards.tarot.enumerations.CardTarot;
import code.sys.impl.GraphicStringListGenerator;
import code.sys.impl.GraphicComboBoxGenerator;
import code.sys.impl.GraphicListGenerator;
import code.sys.impl.ProgramInfos;

public class LaunchingCardsSys extends LaunchingCards {
    public LaunchingCardsSys() {
        super(new ProgramInfos(new GraphicStringListGenerator(), new GraphicComboBoxGenerator()),new CardFactories(new GraphicListGenerator<CardBelote>(), new GraphicListGenerator<CardPresident>(), new GraphicListGenerator<CardTarot>(), new GraphicListGenerator<Suit>()));
    }
}
