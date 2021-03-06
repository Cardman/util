package cards.president;

import cards.president.enumerations.CardPresident;
import cards.president.enumerations.Playing;
import code.util.CustList;
import code.util.*;
import code.util.core.IndexConstants;
import code.util.core.NumberUtil;

public final class CheckerGamePresidentWithRules {

    private static final String MESSAGE_ERROR = "error";

    private CheckerGamePresidentWithRules() {
    }
    public static void check(GamePresident _loadedGame) {
        RulesPresident rules_ = _loadedGame.getRegles();
        if (!rules_.isValidRules()) {
            _loadedGame.setError(MESSAGE_ERROR);
            return;
        }
        byte nbPlayers_ = (byte) rules_.getNbPlayers();
        if (_loadedGame.getDistribution().nombreDeMains() != nbPlayers_) {
            _loadedGame.setError(MESSAGE_ERROR);
            return;
        }
        if (_loadedGame.getScores().size() != nbPlayers_) {
            _loadedGame.setError(MESSAGE_ERROR);
            return;
        }
        Bytes ranks_ = _loadedGame.getRanks();
        _loadedGame.loadGame();
        CustList<TrickPresident> allTricks_ = _loadedGame.unionPlis();
        HandPresident cards_ = new HandPresident();
        for (TrickPresident t : allTricks_) {
            for (HandPresident c : t) {
                cards_.ajouterCartes(c);
            }
        }
        CustList<TrickPresident> allTricksPlusCurr_ = new CustList<TrickPresident>(allTricks_);
        allTricksPlusCurr_.add(_loadedGame.getProgressingTrick());
        for (TrickPresident t: allTricksPlusCurr_) {
            byte nbCards_ = t.getNombreDeCartesParJoueur();
            if (nbCards_ == 0) {
                if (!t.getCartes().estVide()) {
                    _loadedGame.setError(MESSAGE_ERROR);
                    return;
                }
            }
        }
        for (HandPresident c : _loadedGame.getProgressingTrick()) {
            cards_.ajouterCartes(c);
        }
        for (HandPresident c : _loadedGame.getDistribution()) {
            cards_.ajouterCartes(c);
        }
        for (HandPresident s : _loadedGame.getSwitchedCards()) {
            cards_.ajouterCartes(s);
        }
        for (CardPresident e : cards_) {
            if (!e.isPlayable()) {
                _loadedGame.setError(MESSAGE_ERROR);
                return;
            }
        }
        DealPresident deal_ = new DealPresident(_loadedGame.getDistribution());
        int indexCurTrick_ = IndexConstants.FIRST_INDEX;
        for (TrickPresident t : allTricks_) {
            int index_ = IndexConstants.FIRST_INDEX;
            for (HandPresident c : t) {
                byte player_ = t.getPlayer(index_, nbPlayers_);
                deal_.hand(player_).ajouterCartes(c);
                index_++;
            }
        }
        for (HandPresident c : _loadedGame.getProgressingTrick()) {
            byte player_ = _loadedGame.getProgressingTrick().getPlayer(
                    indexCurTrick_, nbPlayers_);
            deal_.hand(player_).ajouterCartes(c);
            indexCurTrick_++;
        }
        if (!ranks_.isEmpty()) {
            if (ranks_.size() != nbPlayers_) {
                _loadedGame.setError(MESSAGE_ERROR);
                return;
            }
            Bytes copyRanks_ = new Bytes(ranks_);
            if (copyRanks_.hasDuplicates()) {
                _loadedGame.setError(MESSAGE_ERROR);
                return;
            }
        }
        boolean readyToPlay_ = true;
        if (_loadedGame.availableSwitchingCards()) {
            if (_loadedGame.getSwitchedCards().size() != nbPlayers_) {
                _loadedGame.setError(MESSAGE_ERROR);
                return;
            }
            for (byte w : _loadedGame.getWinners()) {
                int ind_ = _loadedGame.getWinners().indexOfNb(w);
                int nbGivenCards_ = _loadedGame.nombresCartesEchangesMax()
                        - ind_;
                if (w == DealPresident.NUMERO_UTILISATEUR) {
                    if (!_loadedGame.getSwitchedCards().get(w).estVide()) {
                        if (_loadedGame.getSwitchedCards().get(w).total() != nbGivenCards_) {
                            _loadedGame.setError(MESSAGE_ERROR);
                            return;
                        }
                    } else {
                        readyToPlay_ = false;
                    }
                    continue;
                }
                if (_loadedGame.getSwitchedCards().get(w).total() != nbGivenCards_) {
                    _loadedGame.setError(MESSAGE_ERROR);
                    return;
                }
            }
            for (byte l : _loadedGame.getLoosers()) {
                int ind_ = _loadedGame.getLoosers().indexOfNb(l);
                int nbGivenCards_ = _loadedGame.nombresCartesEchangesMax()
                        - ind_;
                if (_loadedGame.getSwitchedCards().get(l).total() != nbGivenCards_) {
                    _loadedGame.setError(MESSAGE_ERROR);
                    return;
                }
            }
            for (byte p = IndexConstants.FIRST_INDEX; p < nbPlayers_; p++) {
                if (_loadedGame.getWinners().containsObj(p)) {
                    continue;
                }
                if (_loadedGame.getLoosers().containsObj(p)) {
                    continue;
                }
                if (!_loadedGame.getSwitchedCards().get(p).estVide()) {
                    _loadedGame.setError(MESSAGE_ERROR);
                    return;
                }
            }
            if (!readyToPlay_) {
                if (!_loadedGame.getTricks().isEmpty()
                        || !_loadedGame.getProgressingTrick().estVide()) {
                    _loadedGame.setError(MESSAGE_ERROR);
                    return;
                }
                for (byte w : _loadedGame.getWinners()) {
                    if (w == DealPresident.NUMERO_UTILISATEUR) {
                        continue;
                    }
                    byte pl_ = _loadedGame.getMatchingLoser(w);
                    HandPresident hl_ = new HandPresident(deal_.hand(w));
                    hl_.ajouterCartes(_loadedGame.getSwitchedCards()
                            .get(pl_));
                    if (!hl_.containsCards(_loadedGame.getSwitchedCards()
                            .get(w))) {
                        _loadedGame.setError(MESSAGE_ERROR);
                        return;
                    }
                }
            } else {
                for (byte w : _loadedGame.getWinners()) {
                    byte pl_ = _loadedGame.getMatchingLoser(w);
                    HandPresident hl_ = new HandPresident(deal_.hand(pl_));
                    hl_.ajouterCartes(_loadedGame.getSwitchedCards()
                            .get(pl_));
                    if (!hl_.containsCards(_loadedGame.getSwitchedCards()
                            .get(w))) {
                        _loadedGame.setError(MESSAGE_ERROR);
                        return;
                    }
                }
                for (byte i = 0; i< nbPlayers_; i++) {
                    deal_.hand(i).ajouterCartes(
                            _loadedGame.getSwitchedCards().get(i));
                }
            }
            for (byte l : _loadedGame.getLoosers()) {
                int ind_ = _loadedGame.getLoosers().indexOfNb(l);
                byte pl_ = _loadedGame.getWinners().get(ind_);
                deal_.hand(l).supprimerCartes(
                        _loadedGame.getSwitchedCards().get(pl_));
            }
            for (byte w : _loadedGame.getWinners()) {
                int ind_ = _loadedGame.getWinners().indexOfNb(w);
                byte pl_ = _loadedGame.getLoosers().get(ind_);
                deal_.hand(w).supprimerCartes(
                        _loadedGame.getSwitchedCards().get(pl_));
            }
            for (byte l : _loadedGame.getLoosers()) {
                HandPresident hCopy_ = new HandPresident(deal_.hand(l));
                HandPresident hSwitchCopy_ = new HandPresident(_loadedGame
                        .getSwitchedCards().get(l));
                hCopy_.sortCardsBegin();
                hSwitchCopy_.sortCardsBegin();
                int lenGiv_ = hSwitchCopy_.total();
                for (int i = IndexConstants.FIRST_INDEX; i < lenGiv_; i++) {
                    byte strGiv_ = hSwitchCopy_.carte(i).strength(false);
                    byte str_ = hCopy_.carte(i).strength(false);
                    if (strGiv_ != str_) {
                        _loadedGame.setError(MESSAGE_ERROR);
                        return;
                    }
                }
            }
        } else {
            if (!_loadedGame.getSwitchedCards().isEmpty()) {
                _loadedGame.setError(MESSAGE_ERROR);
                return;
            }
        }
        int nbCards_ = rules_.getNbStacks() * HandPresident.pileBase().total();
        int rem_ = nbCards_ % nbPlayers_;
        boolean noRem_ = rem_ == 0;
        int nbCardsPerPlayer_ = nbCards_ / nbPlayers_;
        if (noRem_) {
            for (HandPresident h : deal_) {
                if (h.total() != nbCardsPerPlayer_) {
                    _loadedGame.setError(MESSAGE_ERROR);
                    return;
                }
            }
        } else {
            for (HandPresident h : deal_) {
                if (h.total() > nbCardsPerPlayer_ + 1) {
                    _loadedGame.setError(MESSAGE_ERROR);
                    return;
                }
                if (h.total() < nbCardsPerPlayer_) {
                    _loadedGame.setError(MESSAGE_ERROR);
                    return;
                }
            }
        }
        boolean allCardsUsedNb_ = true;
        for (CardPresident c : HandPresident.pileBase()) {
            int nbUses_ = 0;
            for (HandPresident t : deal_) {
                for (CardPresident c2_ : t) {
                    if (c == c2_) {
                        nbUses_++;
                    }
                }
            }
            if (nbUses_ == rules_.getNbStacks()) {
                continue;
            }
            allCardsUsedNb_ = false;
            break;
        }
        if (!allCardsUsedNb_) {
            _loadedGame.setError(MESSAGE_ERROR);
            return;
        }
        if (!readyToPlay_) {
            return;
        }
        if (_loadedGame.availableSwitchingCards()) {
            for (byte w : _loadedGame.getWinners()) {
                int ind_ = _loadedGame.getWinners().indexOfNb(w);
                byte pl_ = _loadedGame.getLoosers().get(ind_);
                deal_.hand(w).ajouterCartes(
                        _loadedGame.getSwitchedCards().get(pl_));
            }
            for (byte l : _loadedGame.getLoosers()) {
                int ind_ = _loadedGame.getLoosers().indexOfNb(l);
                byte pl_ = _loadedGame.getWinners().get(ind_);
                deal_.hand(l).ajouterCartes(
                        _loadedGame.getSwitchedCards().get(pl_));
            }
            for (byte i = 0; i< nbPlayers_; i++) {
                deal_.hand(i).supprimerCartes(
                        _loadedGame.getSwitchedCards().get(i));
            }
        }
        if (allTricks_.isEmpty() && _loadedGame.getProgressingTrick().estVide()) {
            return;
        }
        GamePresident loadedGameCopy_ = new GamePresident(
                _loadedGame.getType(), deal_, rules_, ranks_);
        loadedGameCopy_.copySwitchCards(_loadedGame.getSwitchedCards());
        int ind_ = 0;
        loadedGameCopy_.initializeFirstTrick();
        TrickPresident firstTrick_;
        if (!allTricks_.isEmpty()) {
            firstTrick_ = allTricks_.first();
        } else {
            firstTrick_ = _loadedGame.getProgressingTrick();
        }
        while (true) {
            TrickPresident trick_;
            if (ind_ == 0) {
                trick_ = firstTrick_;
            } else if (ind_ + 1 <= allTricks_.size()) {
                trick_ = allTricks_.get(ind_);
            } else {
                trick_ = _loadedGame.getProgressingTrick();
            }
            if (trick_.estVide()) {
                return;
            }
            if (ind_ > allTricks_.size()) {
                return;
            }
            int nbCardsPerPlayerTrick_ = trick_.getNombreDeCartesParJoueur();
            int nbHands_ = trick_.total();
            if (nbCardsPerPlayerTrick_ == 0) {
                for (int i = IndexConstants.FIRST_INDEX; i < nbHands_; i++) {
                    byte player_ = trick_.getPlayer(i, nbPlayers_);
                    HandPresident curHand_ = trick_.carte(i);
                    loadedGameCopy_.checkPlayerHand(player_,MESSAGE_ERROR);
                    loadedGameCopy_.getProgressingTrick().ajouter(curHand_,
                            player_);
                }
                loadedGameCopy_.unionPlis().add(
                        loadedGameCopy_.getProgressingTrick());
                loadedGameCopy_.initializeTrick(trick_.getPlayer(
                        trick_.total(), nbPlayers_));
            } else {
                for (int i = IndexConstants.FIRST_INDEX; i < nbHands_; i++) {
                    byte player_ = trick_.getPlayer(i, nbPlayers_);
                    HandPresident curHand_ = trick_.carte(i);
                    Bytes str_ = new Bytes();
                    for (CardPresident c : curHand_) {
                        str_.add(c.strength(loadedGameCopy_.isReversed()));
                    }
                    if (!str_.isEmpty()) {
                        boolean same_ = NumberUtil.eq(str_.getMinimum((byte) 0),
                                str_.getMaximum((byte) 0));
                        if (!same_ || str_.size() != nbCardsPerPlayerTrick_) {
                            _loadedGame.setError(MESSAGE_ERROR);
                            return;
                        }
                    }
                    if (curHand_.estVide()) {
                        if (loadedGameCopy_.getProgressingTrick().getCartes().estVide()) {
                            _loadedGame.setError(MESSAGE_ERROR);
                            return;
                        }
                        if (!loadedGameCopy_.canPass(player_)) {
                            _loadedGame.setError(MESSAGE_ERROR);
                            return;
                        }
                        if (loadedGameCopy_.getStatus(player_) == Playing.CAN_PLAY) {
                            loadedGameCopy_.getPassOrFinish()
                                    .set(player_, true);
                        }
                        loadedGameCopy_.addEmptyHandToCurrentTrick(player_);
                    } else {
                        if (!loadedGameCopy_.allowPlaying(player_, curHand_)) {
                            _loadedGame.setError(MESSAGE_ERROR);
                            return;
                        }
                        loadedGameCopy_.addCardsToCurrentTrick(player_,
                                curHand_);
                        if (loadedGameCopy_.getDistribution().hand(player_)
                                .estVide()) {
                            loadedGameCopy_.getPassOrFinish()
                                    .set(player_, true);
                        }
                    }
                    if (loadedGameCopy_.getProgressingTrick().estVide() && i + 1 < nbHands_ && !trick_.carte(i + 1).estVide()) {
                        _loadedGame.setError(MESSAGE_ERROR);
                        return;
                    }
                }
            }
            for (byte p = IndexConstants.FIRST_INDEX; p < nbPlayers_; p++) {
                loadedGameCopy_.getPassOrFinish().set(p,
                        loadedGameCopy_.getDistribution().hand(p).estVide());
            }
            if (ind_ >= loadedGameCopy_.unionPlis().size()) {
                return;
            }
            ind_++;
            if (!loadedGameCopy_.keepPlayingCurrentGame()) {
                break;
            }
        }
    }
}
