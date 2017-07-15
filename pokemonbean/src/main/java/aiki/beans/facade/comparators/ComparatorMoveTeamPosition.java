package aiki.beans.facade.comparators;
import java.util.Comparator;

import code.util.Numbers;
import aiki.game.fight.MoveTeamPosition;

public class ComparatorMoveTeamPosition implements Comparator<MoveTeamPosition> {

    @Override
    public int compare(MoveTeamPosition _o1, MoveTeamPosition _o2) {
        int res_ = _o1.getMove().compareTo(_o2.getMove());
        if (res_ != 0) {
            return res_;
        }
        res_ = Numbers.compare(_o1.getTeamPosition().getTeam(), _o2.getTeamPosition().getTeam());
        if (res_ != 0) {
            return res_;
        }
        return Numbers.compare(_o1.getTeamPosition().getPosition(), _o2.getTeamPosition().getPosition());
    }

}
