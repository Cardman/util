package aiki.game.fight;
import code.maths.Rate;
import code.util.CustList;
import code.util.*;
import code.util.StringList;
import code.util.ints.Displayable;

public final class Anticipation implements Displayable{

    private static final char SEPARATOR=',';

    private Rate damage;

    private byte nbRounds;

    private TargetCoords targetPosition;

    private boolean incrementing;

    public Anticipation() {
        damage = Rate.zero();
        targetPosition = new TargetCoords();
    }

    public Anticipation(String _value) {
        StringList elts_ = StringList.splitChars(_value, SEPARATOR);
        damage = new Rate(elts_.first());
        nbRounds = (byte) Numbers.parseInt(elts_.get(CustList.SECOND_INDEX));
        targetPosition = new TargetCoords(elts_.last());
        incrementing = !Numbers.eq(targetPosition.getPosition(), Fighter.BACK);
    }

    
    public static Anticipation newAnticipation(String _string) {
        return new Anticipation(_string);
    }

    public boolean isValid() {
        if (!damage.isZeroOrGt()) {
            return false;
        }
        if (nbRounds < 0) {
            return false;
        }
        return true;
    }

    void increment() {
        if (nbRounds < 2) {
            nbRounds++;
        } else {
            nbRounds = 0;
            incrementing = false;
        }
    }

    public Rate getDamage() {
        return damage;
    }

    public void setDamage(Rate _damage) {
        damage = _damage;
    }

    public byte getNbRounds() {
        return nbRounds;
    }

    public void setNbRounds(byte _nbRounds) {
        nbRounds = _nbRounds;
    }

    public TargetCoords getTargetPosition() {
        return targetPosition;
    }

    public void setTargetPosition(TargetCoords _targetPosition) {
        targetPosition = _targetPosition;
    }

    public boolean isIncrementing() {
        return incrementing;
    }

    public void setIncrementing(boolean _incrementing) {
        incrementing = _incrementing;
    }

    
    @Override
    public String display() {
        StringBuilder str_ = new StringBuilder(damage.toNumberString());
        str_.append(SEPARATOR).append(nbRounds);
        str_.append(SEPARATOR).append(targetPosition.display());
        return str_.toString();
    }
}
