package cards.belote.comparators;
import cards.belote.HandBelote;
import cards.consts.Suit;
import code.util.EnumMap;
import code.util.Numbers;
import code.util.ints.Comparing;

public final class HandBeloteShortLengthComparator implements Comparing<Suit> {

    private EnumMap<Suit,HandBelote> hand;

    public HandBeloteShortLengthComparator(EnumMap<Suit,HandBelote> _hand) {
        hand = _hand;
    }

    @Override
    public int compare(Suit _suit1, Suit _suit2) {
        int total1_ = hand.getVal(_suit1).total();
        int total2_ = hand.getVal(_suit2).total();
        return Numbers.compare(total1_, total2_);
    }

}
