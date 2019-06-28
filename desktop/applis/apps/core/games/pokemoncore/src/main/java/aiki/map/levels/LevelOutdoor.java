package aiki.map.levels;
import aiki.util.Point;

public final class LevelOutdoor extends Level {

    @Override
    public boolean isEmptyForAdding(Point _point) {
        return true;
    }
    @Override
    public boolean isEmpty(Point _point) {
        return true;
    }
}
