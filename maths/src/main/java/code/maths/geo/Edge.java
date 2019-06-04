package code.maths.geo;
import code.util.CustList;
import code.util.EqList;
import code.util.ints.Displayable;

public final class Edge implements Displayable {

    private static final String SEPARATOR = " ";

    private CustPoint first;

    private CustPoint second;

    public Edge(CustPoint _first, CustPoint _second) {
        first = _first;
        second = _second;
    }

    public boolean isSame(Edge _other) {
        if (first == _other.first) {
            if (second == _other.second) {
                return true;
            }
        }
        if (first == _other.second) {
            if (second == _other.first) {
                return true;
            }
        }
        return false;
    }

    public boolean isEqual(Edge _other) {
        if (first.eq(_other.first)) {
            if (second.eq(_other.second)) {
                return true;
            }
        }
        if (first.eq(_other.second)) {
            if (second.eq(_other.first)) {
                return true;
            }
        }
        return false;
    }

    public boolean intersectNotContains(Edge _other) {
        return intersectBoundsOpt(_other,false);
    }

    public boolean intersectNotContainsBound(Edge _other) {
        EqList<CustPoint> points_ = new EqList<CustPoint>();
        points_.add(first);
        points_.add(second);
        points_.add(_other.first);
        points_.add(_other.second);
        if (first.eq(_other.first) && !second.eq(_other.second)) {
            return false;
        }
        if (first.eq(_other.second) && !second.eq(_other.first)) {
            return false;
        }
        if (second.eq(_other.first) && !first.eq(_other.second)) {
            return false;
        }
        if (second.eq(_other.second) && !first.eq(_other.first)) {
            return false;
        }
        if (containsPoint(_other.second)) {
            return true;
        }
        if (containsPoint(_other.first)) {
            return true;
        }
        if (_other.containsPoint(first)) {
            return true;
        }
        if (_other.containsPoint(second)) {
            return true;
        }
        return lookForIntersectEdges(points_);
    }

    private boolean lookForIntersectEdges(EqList<CustPoint> _points) {
        int index_ = CustList.FIRST_INDEX;
        for (CustPoint p: _points) {
            EqList<CustPoint> others_ = new EqList<CustPoint>();
            int next_;
            int nextOthOne_;
            int nextOthTwo_;
            if (index_ <= CustList.SECOND_INDEX) {
                if (index_ == CustList.FIRST_INDEX) {
                    next_ = CustList.SECOND_INDEX;
                } else {
                    next_ = CustList.FIRST_INDEX;
                }
                nextOthOne_ = CustList.SECOND_INDEX + CustList.ONE_ELEMENT;
                nextOthTwo_ = nextOthOne_ + CustList.ONE_ELEMENT;
            } else {
                if (index_ == CustList.SECOND_INDEX + CustList.ONE_ELEMENT) {
                    next_ = index_ + CustList.ONE_ELEMENT;
                } else {
                    next_ = index_ - CustList.ONE_ELEMENT;
                }
                nextOthOne_ = CustList.FIRST_INDEX;
                nextOthTwo_ = CustList.SECOND_INDEX;
            }
            CustPoint o_ = _points.get(next_);
            others_.add(_points.get(nextOthOne_));
            others_.add(_points.get(nextOthTwo_));
            CustList<Site> sites_ = new CustList<Site>();
            VectTwoDims v_ = new VectTwoDims(p, o_);
            for (CustPoint n: others_) {
                sites_.add(new SitePoint(n, p, v_));
            }
            sites_.sortElts(new SiteComparing());
            if (sites_.first().getInfo().getNumber() >= SiteInfo.QUAD_THREE) {
                return false;
            }
            if (sites_.last().getInfo().getNumber() < SiteInfo.QUAD_THREE) {
                return false;
            }
            index_++;
        }
        return true;
    }

    public boolean intersect(Edge _other) {
        return intersectBoundsOpt(_other,true);
    }

    private boolean intersectBoundsOpt(Edge _other, boolean _nonStrict) {
        EqList<CustPoint> points_ = new EqList<CustPoint>();
        points_.add(first);
        points_.add(second);
        points_.add(_other.first);
        points_.add(_other.second);
        if (containsPoint(_other.second)) {
            return _nonStrict;
        }
        if (_other.containsPoint(first)) {
            return _nonStrict;
        }
        if (_other.containsPoint(second)) {
            return _nonStrict;
        }
        if (containsPoint(_other.first)) {
            return _nonStrict;
        }
        return lookForIntersectEdges(points_);
    }

    public boolean containsPoint(CustPoint _c) {
        VectTwoDims one_ = new VectTwoDims(first, _c);
        VectTwoDims two_ = new VectTwoDims(second, _c);
        return one_.det(two_) == 0 && one_.scal(two_) <= 0;
    }

    public CustPoint getFirst() {
        return first;
    }

    public void setFirst(CustPoint _first) {
        first = _first;
    }

    public CustPoint getSecond() {
        return second;
    }

    public void setSecond(CustPoint _second) {
        second = _second;
    }

    @Override
    public String display() {
        StringBuilder str_ = new StringBuilder(first.display());
        str_.append(SEPARATOR);
        str_.append(second.display());
        return str_.toString();
    }
}
