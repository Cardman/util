package cards.gui.events;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import cards.belote.BidBeloteSuit;
import cards.gui.containers.ContainerMultiBelote;
import cards.network.belote.actions.BiddingBelote;

public class ListenerBidBeloteMulti extends MouseAdapter {

    private ContainerMultiBelote container;
    private BidBeloteSuit texte = new BidBeloteSuit();

    public ListenerBidBeloteMulti(
            ContainerMultiBelote _container,
            BidBeloteSuit _texteBouton) {
        container = _container;
        texte = _texteBouton;
    }

    @Override
    public void mouseReleased(MouseEvent _e) {
        if (!container.isCanBid()) {
            return;
        }
        container.setCanBid(false);
        BiddingBelote bid_ = new BiddingBelote();
        bid_.setPlace(container.getIndexInGame());
        bid_.setBidBelote(texte);
        String lg_ = container.getOwner().getLanguageKey();
        bid_.setLocale(lg_);
        container.getOwner().sendObject(bid_);
    }
}
